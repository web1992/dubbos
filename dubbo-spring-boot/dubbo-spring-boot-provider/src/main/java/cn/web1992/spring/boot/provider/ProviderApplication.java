package cn.web1992.spring.boot.provider;

import cn.web1992.spring.boot.provider.config.DubboAnnotationConfig;
import cn.web1992.spring.boot.provider.config.DubboXmlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 修改配置：
 *
 * @see cn.web1992.spring.boot.provider.impl.annotation.AnnotationDemoServiceImpl
 * @see cn.web1992.spring.boot.provider.impl.xml.XmlDemoServiceImpl
 * @see DubboAnnotationConfig
 * @see DubboXmlConfig
 * <p>
 * 默认是走的 DubboAnnotationConfig 配置
 */
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
