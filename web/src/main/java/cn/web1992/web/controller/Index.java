package cn.web1992.web.controller;

import cn.web1992.web.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

    private HelloService helloService;

    @Autowired
    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }

    @RequestMapping(name = "index", path = "/")
    public String index() {
        return "welcome !" + helloService.say();
    }
}
