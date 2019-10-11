package com.zwh.javassist;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author ZhangWeihui
 * @date 2019/9/11 10:29
 */
@Slf4j
public class JavassistUtils {

    private static final String CLASSPATH = "F:\\IdeaProjects\\JavaSamples\\target\\classes";

    /**
     * 给指定的方法添加执行计时
     * @param ctClass
     * @param methodName
     */
    public static void addTiming(CtClass ctClass, String methodName) throws NotFoundException, CannotCompileException, IOException {
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
        String newMethodName = methodName + "$Impl";
        boolean hasMethod = hasMethod(ctClass, newMethodName);
        CtMethod copyMethod;
        if(hasMethod) {
            log.info("类[{}]中已包含一个同名的方法[{}]", ctClass.getName(), newMethodName);
            copyMethod = ctClass.getDeclaredMethod(newMethodName);
        } else {
            ctMethod.setName(newMethodName);
            copyMethod = CtNewMethod.copy(ctMethod, methodName, ctClass, null);
        }
        StringBuffer buf = new StringBuffer();
        String returnType = ctMethod.getReturnType().getName();
        buf.append("{\n");//CAUTION
        buf.append("long startTime = System.currentTimeMillis();\n");
        if (!"void".equalsIgnoreCase(returnType)){
            buf.append(returnType + " result = ");
        }
        buf.append(newMethodName + "($$);\n ");
        buf.append("long cost = System.currentTimeMillis() - startTime;\n");
        buf.append("System.out.println(\"call method [" + newMethodName + "], cost time [\" + cost + \"] ms.\");\n");
        if (!"void".equalsIgnoreCase(returnType)) {
            buf.append("System.out.println(\"the result is [\" + result + \"]\");\n");
            buf.append("return result;\n");
        }
        buf.append("}\n");//CAUTION
        copyMethod.setBody(buf.toString());
        if (!hasMethod) {
            ctClass.addMethod(copyMethod);
        }
        log.info("Interceptor method body : {}", buf.toString());
        ctClass.writeFile(CLASSPATH);
    }

    public static Object addTimingBySubClass(CtClass ctClass, String methodName) throws NotFoundException,
            CannotCompileException, IOException,IllegalAccessException,InstantiationException {
        String packageName = ctClass.getPackageName();
        String subClassName = packageName + "." + ctClass.getSimpleName() + "$SubClass";
        ClassPool classPool = ClassPool.getDefault();
        CtClass subClass = classPool.makeClass(subClassName, ctClass);
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
        StringBuffer buf = new StringBuffer();
        String returnType = ctMethod.getReturnType().getName();
        StringBuffer buf2 = new StringBuffer();
        CtClass[] paramTypes = ctMethod.getParameterTypes();
        if (paramTypes!=null && paramTypes.length>0) {
            for (int i=0; i<paramTypes.length; i++) {
                CtClass pt = paramTypes[i];
                buf2.append(pt.getName() + " pt" +i);
            }
        }
        buf.append("public "+ returnType +" "+ methodName+"("+ buf2.toString() +"){\n");//CAUTION
        buf.append("long startTime = System.currentTimeMillis();\n");
        if (!"void".equalsIgnoreCase(returnType)){
            buf.append(returnType + " result = ");
        }
        buf.append("super." + methodName + "($$);\n ");
        buf.append("long cost = System.currentTimeMillis() - startTime;\n");
        buf.append("System.out.println(\"call method [" + methodName + "], cost time [\" + cost + \"] ms.\");\n");
        if (!"void".equalsIgnoreCase(returnType)) {
            buf.append("System.out.println(\"the result is [\" + result + \"]\");\n");
            buf.append("return result;\n");
        }
        buf.append("}\n");//CAUTION
        CtMethod copyMethod = CtNewMethod.make(buf.toString(), subClass);

        //添加Override注解
        ConstPool constPool = ctClass.getClassFile().getConstPool();
        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation("Override", constPool);
        annotationsAttribute.addAnnotation(annotation);
        ctMethod.getMethodInfo().addAttribute(annotationsAttribute);

        subClass.addMethod(copyMethod);
        log.info("Interceptor method body : {}", buf.toString());
        ctClass.writeFile(CLASSPATH);

        return subClass.toClass().newInstance();
    }

    public static boolean hasMethod(CtClass ctClass, String methodName) {
        CtMethod[] methods = ctClass.getDeclaredMethods();
        if (methods==null || methods.length==0) {
            return false;
        }
        for (CtMethod method : methods) {
            if(method.getName().equals(methodName)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] argv) {
        try {
            StrBuilder inst = new StrBuilder();
            for (int i = 0; i < argv.length; i++) {
                String result = inst.buildString(Integer.parseInt(argv[i]));
                log.info("Constructed string [{}] of length [{}]", result, result.length());
            }

            System.out.println("after adding timing &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            CtClass ctClass = ClassPool.getDefault().getCtClass("com.zwh.javassist.StrBuilder");
            //JavassistUtils.addTiming(ctClass, "buildString");
            StrBuilder inst2 = (StrBuilder) JavassistUtils.addTimingBySubClass(ctClass, "buildString");
            for (int i = 0; i < argv.length; i++) {
                String result = inst2.buildString(Integer.parseInt(argv[i]));
                log.info("Constructed string [{}] of length [{}]", result, result.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}