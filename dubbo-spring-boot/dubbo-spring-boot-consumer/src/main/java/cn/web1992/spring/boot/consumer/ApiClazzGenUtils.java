
package com.internet.msgcenter.client.util;

import com.internet.msgcenter.entity.msgevent.MsgEventAttach;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.extern.slf4j.Slf4j;
import net.pocrd.entity.RegisterClazzInfo;
import net.pocrd.entity.RegisterFieldInfo;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
@Slf4j
public class ApiClazzGenUtils {

    private static final String EVENT_CLASS = "com.internet.msgcenter.entity.msgevent.MsgEventAttach";
    private static final String DESC_ANNO = "net.pocrd.annotation.Description";
    private static final String REGISTER_ENABLE = "net.pocrd.annotation.RegisterEnable";
    private static final String DESC_ANNO_VALUE = "value";

    private static final ConcurrentMap<String, Class<?>> CLASS_INSTANCES = new ConcurrentHashMap<>(64);


    public static Class<?> genClazz(String name) throws Exception {

        try {
            if (CLASS_INSTANCES.containsKey(name)) {
                return CLASS_INSTANCES.get(name);
            }

            RegisterClazzInfo registerClazzInfo = ApiClazzRegisterUtils.getRegisterClazzInfo(name);

            if (null == registerClazzInfo || registerClazzInfo.getFieldInfoList() == null) {
                return null;
            }

            ClassPool pool = getClassPool();
            CtClass ctClass = pool.makeClass(name);
            ClassFile classFile = ctClass.getClassFile();
            ConstPool constPool = classFile.getConstPool();
            classFile.setSuperclass(EVENT_CLASS);

            // 类注解
            String apiDescClazz = registerClazzInfo.getApiDesc();
            AnnotationsAttribute classAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
            Annotation descriptionClazz = new Annotation(DESC_ANNO, constPool);
            descriptionClazz.addMemberValue(DESC_ANNO_VALUE, new StringMemberValue(apiDescClazz, constPool));
            classAttr.addAnnotation(descriptionClazz);

            Annotation re = new Annotation(REGISTER_ENABLE, constPool);
            classAttr.addAnnotation(re);

            classFile.addAttribute(classAttr);

            for (RegisterFieldInfo registerFieldInfo : registerClazzInfo.getFieldInfoList()) {

                String fName = registerFieldInfo.getName();
                String fType = registerFieldInfo.getTypeDesc();

                int accessFlags = registerFieldInfo.getAccessFlags();
                String apiDesc = registerFieldInfo.getApiDesc();

                // 方法
                FieldInfo f = new FieldInfo(constPool, fName, getTypeDesc(fType));
                f.setAccessFlags(accessFlags);

                // 方法注解
                AnnotationsAttribute fieldAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                Annotation descriptionField = new Annotation(DESC_ANNO, constPool);
                descriptionField.addMemberValue(DESC_ANNO_VALUE, new StringMemberValue(apiDesc, constPool));
                fieldAttr.addAnnotation(descriptionField);
                f.addAttribute(fieldAttr);

                classFile.addField(f);
                // set get
                buildSetGetMethod(pool, fType, fName, ctClass);
            }

            ctClass.writeFile(name + ".class");
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//            log.info("ApiClazzGenUtils#genClazz contextClassLoader {}", contextClassLoader);
//            log.info("ApiClazzGenUtils#genClazz MsgEventAttach contextClassLoader {}", MsgEventAttach.class.getClassLoader());
//            Class<?> clazz = ctClass.toClass(MsgEventAttach.class.getClassLoader(), null);
            Class<?> clazz = ctClass.toClass();

            CLASS_INSTANCES.putIfAbsent(name, clazz);
            return clazz;
        } catch (Exception e) {
            log.error("ApiClazzGenUtils#genClazz ex", e);
            return null;
        }

    }

    private static ClassPool getClassPool() {
        ClassPool pool = ClassPool.getDefault();
        //将当前ClassLoader添加到ClassPath
        pool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
        return pool;
    }

    private static void buildSetGetMethod(ClassPool pool, String fType, String fName, CtClass ctClass) throws Exception {
        CtField param = new CtField(pool.get(fType), fName, ctClass);

        String name = captureName(fName);
        ctClass.addMethod(CtNewMethod.setter("set" + name, param));
        ctClass.addMethod(CtNewMethod.setter("get" + name, param));
    }

    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static String getTypeDesc(String type) {
        if (abbreviationMap.containsKey(type)) {
            return abbreviationMap.get(type);
        }
        return type;
    }

    private static final Map<String, String> abbreviationMap = new HashMap<>();

    private static void addAbbreviation(String primitive, String abbreviation) {
        abbreviationMap.put(primitive, abbreviation);
    }


    static {
        addAbbreviation("int", "I");
        addAbbreviation("boolean", "Z");
        addAbbreviation("float", "F");
        addAbbreviation("long", "J");
        addAbbreviation("short", "S");
        addAbbreviation("byte", "B");
        addAbbreviation("double", "D");
        addAbbreviation("char", "C");
    }

}
