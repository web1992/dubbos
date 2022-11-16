//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.web1992.spring.boot.consumer.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class RegisterUtils {
    public RegisterUtils() {
    }

    public static RegisterClazzInfo genClazzInfo(Class<?> clazz) {
        RegisterClazzInfo clazzInfo = new RegisterClazzInfo();
        if (null == clazz) {
            return null;
        } else if (null == clazz.getAnnotation(RegisterEnable.class)) {
            return null;
        } else {
            Description annotation = (Description)clazz.getAnnotation(Description.class);
            if (null == annotation) {
                return null;
            } else {
                Field[] declaredFields = clazz.getDeclaredFields();
                if (declaredFields.length == 0) {
                    return null;
                } else {
                    String clazzName = clazz.getName();
                    clazzInfo.setApiDesc(annotation.value());
                    clazzInfo.setClassName(clazzName);
                    List<RegisterFieldInfo> fieldInfoList = new ArrayList();
                    clazzInfo.setFieldInfoList(fieldInfoList);
                    Field[] var6 = declaredFields;
                    int var7 = declaredFields.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Field field = var6[var8];
                        RegisterFieldInfo fieldInfo = getFieldInfo(field);
                        if (null != fieldInfo) {
                            fieldInfoList.add(fieldInfo);
                        }
                    }

                    return clazzInfo;
                }
            }
        }
    }

    private static RegisterFieldInfo getFieldInfo(Field field) {
        Description annoDesc = (Description)field.getAnnotation(Description.class);
        if (null == annoDesc) {
            return null;
        } else {
            String annoVal = annoDesc.value();
            String fieldName = field.getName();
            int modifiers = field.getModifiers();
            String typeDesc = field.getType().getName();
            RegisterFieldInfo registerFieldInfo = new RegisterFieldInfo();
            registerFieldInfo.setName(fieldName);
            registerFieldInfo.setTypeDesc(typeDesc);
            registerFieldInfo.setApiDesc(annoVal);
            registerFieldInfo.setAccessFlags(modifiers);
            return registerFieldInfo;
        }
    }
}
