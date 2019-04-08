package cn.web1992.dubbo.demo;

import java.io.Serializable;

public class Demo implements Serializable {
    private static final long serialVersionUID = -1054557587985019465L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}