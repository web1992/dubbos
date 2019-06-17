package cn.web1992.spring.boot.provider.impl;


import cn.web1992.dubbo.demo.Demo;
import cn.web1992.dubbo.demo.DemoService;
import org.springframework.stereotype.Service;

@Service
public class SpringBootDemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return name + " Hello spring boot";
    }

    @Override
    public Demo demo(Demo demo) {
        demo.setName("spring boot");
        return demo;
    }
}
