//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.web1992.spring.boot.consumer.utils;

import java.io.Serializable;

public class RegisterFieldInfo implements Serializable {
    private static final long serialVersionUID = -1L;
    private String name;
    private String typeDesc;
    private String apiDesc;
    private int accessFlags;

    public RegisterFieldInfo() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeDesc() {
        return this.typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getApiDesc() {
        return this.apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }
}
