
package cn.web1992.spring.boot.provider;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ImportResource;

@ImportResource("dubbo/dubbo-provider.xml")
@SpringBootConfiguration
public class DubboConfig {
}
