//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.web1992.spring.boot.consumer.utils;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.List;

public class RegisterClazzInfo implements Serializable {
    private static final long serialVersionUID = -1L;
    private String className;
    private String apiDesc;
    private List<RegisterFieldInfo> fieldInfoList;

    public RegisterClazzInfo() {
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getApiDesc() {
        return this.apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public List<RegisterFieldInfo> getFieldInfoList() {
        return this.fieldInfoList;
    }

    public void setFieldInfoList(List<RegisterFieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
