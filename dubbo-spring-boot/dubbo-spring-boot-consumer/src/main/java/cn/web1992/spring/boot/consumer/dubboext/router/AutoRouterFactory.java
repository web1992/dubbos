package cn.web1992.spring.boot.consumer.dubboext.router;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.cluster.CacheableRouterFactory;
import org.apache.dubbo.rpc.cluster.Router;


@Activate
public class AutoRouterFactory extends CacheableRouterFactory {

    @Override
    protected Router createRouter(URL url) {
        return new AutoRouter(url);
    }
}
