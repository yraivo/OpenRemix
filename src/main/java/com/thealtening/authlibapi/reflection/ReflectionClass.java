package com.thealtening.authlibapi.reflection;

import net.minecraft.entity.Entity;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ReflectionClass {
    private static String c;
    private HashMap<String, ReflectionField> b;
    private Class<?> a;

    private static ClassNotFoundException a(ClassNotFoundException classnotfoundexception) {
        return classnotfoundexception;
    }

    public ReflectionClass(String text) {
        this(text, null);
    }

    static {
        c = "AuthLib not located in the classpath";
    }

    public ReflectionClass(Class<?> oclass, Object object) {
        ReflectionField.b();

        this.b = new HashMap<>();

        this.a = oclass;

        for (Field field : oclass.getDeclaredFields()) {
            this.b.put(field.getName(), new ReflectionField(oclass, field, object));
        }
    }

    public HashMap<String, ReflectionField> a() {
        return this.b;
    }

    public ReflectionField a(String text) {
        return this.b.get(text);
    }

    public ReflectionClass(String text, Object object) {
        ReflectionField.b();

        this.b = new HashMap<>();

        try {
            this.a = Class.forName(text);

            for (Field field : this.a.getDeclaredFields()) {
                this.b.put(field.getName(), new ReflectionField(this.a, field, object));
            }
        } catch (ClassNotFoundException classnotfoundexception) {
            throw new RuntimeException(c);
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ReflectionField.b(new String[4]);
        }
    }

    public ReflectionClass(Class<?> oclass) {
        this(oclass, null);
    }
}
