package cn.web1992.spring.boot.provider.impl.xml;


import cn.web1992.dubbo.demo.Demo;
import cn.web1992.dubbo.demo.DemoService;

@org.springframework.stereotype.Service
public class XmlDemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return name + " XmlDemoServiceImpl Hello spring boot";
    }

    @Override
    public Demo demo(Demo demo) {
        demo.setName("XmlDemoServiceImpl spring boot");
        return demo;
    }
}
