package cn.web1992.web.service;

/**
 * @author web1992
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String say() {
        return "BBB Hello from HelloService B";
    }

    @Override
    public String bye() {
        return "AAA Bye bye from HelloService";
    }

    @Override
    public String test() {
        return null;
    }
}
