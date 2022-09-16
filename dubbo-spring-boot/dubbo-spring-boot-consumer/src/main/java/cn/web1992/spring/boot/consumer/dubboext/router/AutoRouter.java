package cn.web1992.spring.boot.consumer.dubboext.router;


import java.util.List;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Constants;
import org.apache.dubbo.rpc.cluster.Router;
import org.apache.dubbo.rpc.cluster.router.AbstractRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AutoRouter extends AbstractRouter implements Router {

    private static final Logger log = LoggerFactory.getLogger(AutoRouter.class);

    private static final int DEFAULT_PRIORITY = 2;

    public AutoRouter(URL url) {
        this.url = url;
        this.priority = url.getParameter(Constants.PRIORITY_KEY, DEFAULT_PRIORITY);
    }

    @Override
    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        if (CollectionUtils.isEmpty(invokers)) {
            return invokers;
        }
        return invokers;
    }

}
