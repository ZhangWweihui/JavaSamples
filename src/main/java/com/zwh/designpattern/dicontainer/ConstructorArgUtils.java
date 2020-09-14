package com.zwh.designpattern.dicontainer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConstructorArgUtils {

    private static Map<Class, Function> funcMap = new HashMap<>();
    private static Map<String, Class> primitiveTypeMap = new HashMap<>();

    static {
        funcMap.put(Integer.class, (Function<String, Integer>) s -> Integer.valueOf(s));
        funcMap.put(String.class, (Function<String, String>) s -> s);
        funcMap.put(Double.class, (Function<String, Double>) s -> Double.valueOf(s));
        funcMap.put(Long.class, (Function<String, Long>) s -> Long.valueOf(s));
        funcMap.put(BigDecimal.class, (Function<String, BigDecimal>) s -> new BigDecimal(s));

        primitiveTypeMap.put("string", String.class);
        primitiveTypeMap.put("int", Integer.class);
        primitiveTypeMap.put("integer", Integer.class);
        primitiveTypeMap.put("double", Double.class);
        primitiveTypeMap.put("long", Long.class);
        primitiveTypeMap.put("float", Float.class);
        primitiveTypeMap.put("bigdecimal", BigDecimal.class);
    }

    public static Object valueOf(Class clazz, String value) {
        return funcMap.get(clazz).apply(value);
    }

    public static Class getClassForPrimitive(String type) {
        return primitiveTypeMap.get(type.toLowerCase());
    }
}
