package com.thealtening.authlibapi.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionField {
    private Object b;

    private static String[] d;
    private Class<?> a;
    private Field c;

    public static String[] b() {
        return d;
    }

    private void a(int i) throws IllegalAccessException, NoSuchFieldException {
        Field field = Field.class.getDeclaredField("modifiers");
        field.setAccessible(true);
        field.setInt(this.c, i);
    }

    public ReflectionField(Class<?> oclass, Field field, Object object) {
        this.a = oclass;
        this.c = field;
        this.b = object;
    }

    public static void b(String[] astring) {
        d = astring;
    }

    public boolean method_c7b139ccf3ff22603b21(Object object) throws Exception {
        b();

        boolean accessible = this.c.isAccessible();

        this.c.setAccessible(true);
        int i = -1;

        try {
            if (Modifier.isFinal(this.c.getModifiers())) {
                i = this.c.getModifiers();
                this.method_8d9b536bcdc9991c48fb();
            }

            this.c.set(this.b, object);
            this.c.setAccessible(accessible);
            if (i != -1) {
                this.a(i);
            }
        } catch (IllegalAccessException | NoSuchFieldException illegalaccessexception) {
            this.c.setAccessible(accessible);
            throw new Exception(illegalaccessexception);
        }

        return true;
    }

    private static IllegalAccessException a(IllegalAccessException illegalaccessexception) {
        return illegalaccessexception;
    }

    private void method_8d9b536bcdc9991c48fb() throws IllegalAccessException, NoSuchFieldException {
        Field field = Field.class.getDeclaredField("modifiers");
        field.setAccessible(true);
        field.setInt(this.c, this.c.getModifiers() & -17);
    }

    static {
        String[] astring1 = new String[4];
        b(astring1);
    }
}
