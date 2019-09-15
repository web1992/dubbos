package cn.web1992.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {


    @RequestMapping(name = "index", path = "/")
    public String index() {
        return "welcome !";
    }
}
