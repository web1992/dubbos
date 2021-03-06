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
package org.apache.dubbo.samples.router;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


public class TagRouterTest {
    private static CuratorFramework client;

    public static void main(String[] args) throws InterruptedException {
        init();
        normalTagRuleTest();
    }

    public static void init() throws InterruptedException {
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 60 * 1000, 60 * 1000, new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.blockUntilConnected();
    }

    public static void normalTagRuleTest() {
        String serviceStr = "---\n" +
                "force: false\n" +
                "runtime: true\n" +
                "enabled: true\n" +
                "priority: 1\n" +
                "key: demo-provider\n" +
                "tags:\n" +
                "  - name: tag1\n" +
                "    addresses: [\"10.108.3.7:20890\"]\n" +
                "  - name: tag2\n" +
                "    addresses: [\"10.108.3.7:20891\"]\n" +
                "...";
        //        String serviceStr = "";
        try {
            String servicePath = "/dubbo/config/demo-provider/tag-router";
            if (client.checkExists().forPath(servicePath) == null) {
                client.create().creatingParentsIfNeeded().forPath(servicePath);
            }
            setData(servicePath, serviceStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setData(String path, String data) throws Exception {
        client.setData().forPath(path, data.getBytes());
    }
}
