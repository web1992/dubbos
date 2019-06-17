package cn.web1992.spring.boot.provider.impl.annotation;

import cn.web1992.dubbo.demo.Demo;
import cn.web1992.dubbo.demo.DemoService;


@org.apache.dubbo.config.annotation.Service
public class AnnotationDemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return name + " AnnotationDemoServiceImpl hello";
    }

    @Override
    public Demo demo(Demo demo) {
        demo.setName("AnnotationDemoServiceImpl");
        return demo;
    }
}
