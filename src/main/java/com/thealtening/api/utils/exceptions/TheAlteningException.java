package com.thealtening.api.utils.exceptions;

import net.minecraft.entity.Entity;

public class TheAlteningException extends RuntimeException {

    private static String a;
    private static Entity[] b;

    private static TheAlteningException a(TheAlteningException thealteningexception) {
        return thealteningexception;
    }

    public TheAlteningException(String text, String textValue) {
        super(String.format(a, text, textValue));
    }

    static {
        if (method_da2ac5c94686ee11e9e6() == null) {
            b(new Entity[1]);
        }
        a = "[%s]: %s";
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }
}
