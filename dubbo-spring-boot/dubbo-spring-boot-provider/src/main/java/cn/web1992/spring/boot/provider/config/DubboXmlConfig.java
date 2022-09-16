package cn.web1992.spring.boot.provider.config;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@ImportResource("dubbo/dubbo-provider.xml")
@SpringBootConfiguration
@Profile("xml")
public class DubboXmlConfig {
}
