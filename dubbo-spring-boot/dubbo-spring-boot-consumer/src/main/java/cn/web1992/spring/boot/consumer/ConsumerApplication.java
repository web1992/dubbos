package cn.web1992.spring.boot.consumer;

import cn.web1992.dubbo.demo.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 默认配置在 DubboAnnotationConfig 中，使用Nacos 作为注册中心
 */
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConsumerApplication.class, args);

        DemoService demoService = context.getBean(DemoService.class);
        String s = demoService.sayHello("ConsumerApplication");
        System.out.println(s);
    }
}
