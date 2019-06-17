
package cn.web1992.spring.boot.consumer;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ImportResource;

@ImportResource("dubbo/dubbo-consumer.xml")
@SpringBootConfiguration
public class DubboConfig {
}
