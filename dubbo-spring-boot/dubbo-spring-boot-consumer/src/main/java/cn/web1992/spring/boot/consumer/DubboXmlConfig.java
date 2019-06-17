package cn.web1992.spring.boot.consumer;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@ImportResource("dubbo/dubbo-consumer.xml")
@SpringBootConfiguration
@Profile("xml")
public class DubboXmlConfig {
}
