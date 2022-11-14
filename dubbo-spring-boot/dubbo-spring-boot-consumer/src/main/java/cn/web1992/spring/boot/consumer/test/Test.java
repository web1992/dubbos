package cn.web1992.spring.boot.consumer.test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.DuplicateMemberException;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @link {https://juejin.cn/post/6844903588716609543}
 */
public class Test {

    public static final String DESC_ANNO = "net.pocrd.annotation.Description";
    public static final String DESC_ANNO_VALUE = "value";
    public static final String SERIALIZABLE = "java.io.Serializable";
    public static final String serialVersionUID_F = "serialVersionUID";


    public static void main1(String[] args) throws Exception {

        String path = "D:\\dev\\github\\dubbos\\dubbo-spring-boot\\dubbo-spring-boot-consumer\\src\\main\\resources\\AAATest.java";

        Path path1 = Paths.get(path);
        byte[] bytes = Files.readAllBytes(path1);

        String s = new String(bytes);
        System.out.println(s);

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("Point");
        cc.addField(null);


        Class<?> aClass = cc.toClass();
    }


    public static void main(String[] args) throws Exception {

        ClassFile cf = new ClassFile(false, "test.Foo", null);
        cf.setInterfaces(new String[]{SERIALIZABLE});
        ConstPool constPool = cf.getConstPool();

        FieldInfo seriField = new FieldInfo(cf.getConstPool(), serialVersionUID_F, "J");
        int flag = AccessFlag.PRIVATE | AccessFlag.STATIC | AccessFlag.FINAL;
        seriField.setAccessFlags(flag);
        cf.addField(seriField);

        AnnotationsAttribute classAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation descriptionClazz = new Annotation(DESC_ANNO, constPool);
        descriptionClazz.addMemberValue(DESC_ANNO_VALUE, new StringMemberValue("Foo", constPool));
        classAttr.addAnnotation(descriptionClazz);
        cf.addAttribute(classAttr);
        FieldInfo f = new FieldInfo(cf.getConstPool(), "width", "I");
        f.setAccessFlags(AccessFlag.PUBLIC);

        AnnotationsAttribute fieldAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        //        Annotation descriptionField = new Annotation("net.pocrd.annotation.Description", constPool);
        Annotation descriptionField = new Annotation(DESC_ANNO, constPool);
        descriptionField.addMemberValue(DESC_ANNO_VALUE, new StringMemberValue("标题", constPool));
        fieldAttr.addAnnotation(descriptionField);
        f.addAttribute(fieldAttr);

        cf.addField(f);

        cf.write(new DataOutputStream(new FileOutputStream("Foo.class")));
    }
}
