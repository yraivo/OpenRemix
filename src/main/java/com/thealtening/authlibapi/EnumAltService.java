package com.thealtening.authlibapi;

public enum EnumAltService {
    MOJANG,
    THEALTENING;

    private static String[] b;


    public static String[] b() {
        return b;
    }

    public static void b(String[] astring) {
        b = astring;
    }

    private EnumAltService() {}
}
