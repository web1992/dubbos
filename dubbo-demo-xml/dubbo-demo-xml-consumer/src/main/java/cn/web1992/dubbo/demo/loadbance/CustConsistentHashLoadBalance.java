/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.web1992.dubbo.demo.loadbance;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.apache.dubbo.rpc.support.RpcUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static org.apache.dubbo.common.constants.CommonConstants.COMMA_SPLIT_PATTERN;

/**
 * ConsistentHashLoadBalance
 */
public class CustConsistentHashLoadBalance extends AbstractLoadBalance {
    private static final Logger logger = LoggerFactory.getLogger(CustConsistentHashLoadBalance.class);
    public static final String NAME = "custconsistenthash";

    /**
     * Hash nodes name
     */
    public static final String HASH_NODES = "hash.nodes";

    /**
     * Hash arguments name
     */
    public static final String HASH_ARGUMENTS = "hash.arguments";

    private final ConcurrentMap<String, ConsistentHashSelector<?>> selectors = new ConcurrentHashMap<String, ConsistentHashSelector<?>>();


    /**
     * 0= init ,1= initing ,2=init done
     */
    private volatile int initState = 0;

    private static final int INIT_STATE = 0;
    private static final int INIT_ING_STATE = 1;
    private static final int DONE_STATE = 2;

    AtomicIntegerFieldUpdater<CustConsistentHashLoadBalance> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(CustConsistentHashLoadBalance.class, "initState");

    private static final ExecutorService EXECUTORSERVICE = Executors.newCachedThreadPool(new NamedThreadFactory("CustConsistentHashLoadBalance"));


    @SuppressWarnings("unchecked")
    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {

        String methodName = RpcUtils.getMethodName(invocation);
        String key = invokers.get(0).getUrl().getServiceKey() + "." + methodName;
        int identityHashCode = System.identityHashCode(invokers);
        if (STATE_UPDATER.get(this) == INIT_STATE) {
            if (STATE_UPDATER.compareAndSet(this, INIT_STATE, INIT_ING_STATE)) {
                initVirtualInvokers(invokers, url, invocation, identityHashCode);
            }
        } else if (STATE_UPDATER.get(this) == DONE_STATE) {
            ConsistentHashSelector<?> consistentHashSelector = selectors.get(key);
            if (consistentHashSelector.identityHashCode == identityHashCode) {
                return (Invoker<T>) consistentHashSelector.select(invocation);
            } else {
                if (STATE_UPDATER.compareAndSet(this, DONE_STATE, INIT_ING_STATE)) {
                    initVirtualInvokers(invokers, url, invocation, identityHashCode);
                }
            }
        }
        // init or initing state will random one
        logger.info("CustConsistentHashLoadBalance#doSelect is init");
        return invokers.get(ThreadLocalRandom.current().nextInt(invokers.size()));

    }

    private <T> void initVirtualInvokers(List<Invoker<T>> invokers, URL url, Invocation invocation, int identityHashCode) {
        startWorker(new Worker(invokers, url, invocation, identityHashCode));
    }

    private void startWorker(Worker worker) {
        EXECUTORSERVICE.submit(worker);
    }

    private final class Worker<T> implements Runnable {

        List<Invoker<T>> invokers;
        URL url;
        Invocation invocation;
        int identityHashCode;

        public Worker(List<Invoker<T>> invokers, URL url, Invocation invocation, int identityHashCode) {
            this.invokers = invokers;
            this.url = url;
            this.invocation = invocation;
            this.identityHashCode = identityHashCode;
        }


        @Override
        public void run() {
            logger.info("CustConsistentHashLoadBalance start");
            long start = System.nanoTime();
            String methods = url.getParameter("methods");
            String[] split = methods.split(",");
            if (split.length > 0) {
                for (String methodName : split) {
                    String key = invokers.get(0).getUrl().getServiceKey() + "." + methodName;
                    selectors.put(key, new ConsistentHashSelector<T>(invokers, methodName, identityHashCode));
                }
            }

            STATE_UPDATER.compareAndSet(CustConsistentHashLoadBalance.this, INIT_ING_STATE, DONE_STATE);

            logger.info("CustConsistentHashLoadBalance cost time = " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + " ms");
        }
    }


    private static final class ConsistentHashSelector<T> {

        private final TreeMap<Long, Invoker<T>> virtualInvokers;

        private final int replicaNumber;

        private final int identityHashCode;

        private final int[] argumentIndex;

        ConsistentHashSelector(List<Invoker<T>> invokers, String methodName, int identityHashCode) {
            this.virtualInvokers = new TreeMap<Long, Invoker<T>>();
            this.identityHashCode = identityHashCode;
            URL url = invokers.get(0).getUrl();
            this.replicaNumber = url.getMethodParameter(methodName, HASH_NODES, 160);
            String[] index = COMMA_SPLIT_PATTERN.split(url.getMethodParameter(methodName, HASH_ARGUMENTS, "0"));
            argumentIndex = new int[index.length];
            for (int i = 0; i < index.length; i++) {
                argumentIndex[i] = Integer.parseInt(index[i]);
            }
            for (Invoker<T> invoker : invokers) {
                String address = invoker.getUrl().getAddress();
                for (int i = 0; i < replicaNumber / 4; i++) {
                    byte[] digest = md5(address + i);
                    for (int h = 0; h < 4; h++) {
                        long m = hash(digest, h);
                        virtualInvokers.put(m, invoker);
                    }
                }
            }
        }

        public Invoker<T> select(Invocation invocation) {
            String key = toKey(invocation.getArguments());
            byte[] digest = md5(key);
            return selectForKey(hash(digest, 0));
        }

        private String toKey(Object[] args) {
            StringBuilder buf = new StringBuilder();
            for (int i : argumentIndex) {
                if (i >= 0 && i < args.length) {
                    buf.append(args[i]);
                }
            }
            return buf.toString();
        }

        private Invoker<T> selectForKey(long hash) {
            Map.Entry<Long, Invoker<T>> entry = virtualInvokers.ceilingEntry(hash);
            if (entry == null) {
                entry = virtualInvokers.firstEntry();
            }
            return entry.getValue();
        }

        private long hash(byte[] digest, int number) {
            return (((long) (digest[3 + number * 4] & 0xFF) << 24) | ((long) (digest[2 + number * 4] & 0xFF) << 16) | ((long) (digest[1 + number * 4] & 0xFF) << 8) | (digest[number * 4] & 0xFF)) & 0xFFFFFFFFL;
        }

        private byte[] md5(String value) {
            MessageDigest md5;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            md5.reset();
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            md5.update(bytes);
            return md5.digest();
        }

    }

}
