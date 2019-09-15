package cn.web1992.web.service;

import org.springframework.stereotype.Service;

/**
 * @author web1992
 */
@Service("HelloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String nihao() {
        return " 你好！";
    }

    @Override
    public String say() {
        return " Hello from HelloService";
    }

    @Override
    public String bye() {
        return " Bye bye from HelloService";
    }
}
