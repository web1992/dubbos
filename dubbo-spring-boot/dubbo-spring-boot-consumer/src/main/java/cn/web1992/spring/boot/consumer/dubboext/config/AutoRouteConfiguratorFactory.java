package cn.web1992.spring.boot.consumer.dubboext.config;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.cluster.Configurator;
import org.apache.dubbo.rpc.cluster.ConfiguratorFactory;

@Activate
public class AutoRouteConfiguratorFactory implements ConfiguratorFactory {

    @Override
    public Configurator getConfigurator(URL url) {
        System.out.println("AutoRouteConfiguratorFactory");
        return new AutoRouteConfigurator(url);
    }
}
