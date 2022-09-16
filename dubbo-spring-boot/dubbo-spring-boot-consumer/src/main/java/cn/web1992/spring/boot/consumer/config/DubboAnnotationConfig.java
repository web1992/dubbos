package cn.web1992.spring.boot.consumer.config;

import cn.web1992.dubbo.demo.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootConfiguration
@Profile("default")
@DubboComponentScan
public class DubboAnnotationConfig {

    @Reference
    private DemoService demoService;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-annotation-consumer");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        //registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setAddress("nacos://124.223.35.219:8848");
        return registryConfig;
    }

//    @Bean
//    public DemoService demoService() {
//        return demoService;
//    }

}
