package cn.web1992.web.service;

/**
 * @author web1992
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String say() {
        return "Hello from HelloService B";
    }

    @Override
    public String bye() {
        return "Bye bye from HelloService";
    }
}
