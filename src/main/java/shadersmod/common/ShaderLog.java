package shadersmod.common;

import net.minecraft.entity.Entity;
import shadersmod.common.SMCLogLevel;
import shadersmod.common.SMCLogger;
import shadersmod.common.ShaderLogAccessMarker;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ShaderLog {
    private static Entity[] PK;
    public static Level PL;
    public static Logger PM;
    public static String PREFIX;
    public static Level PN;

    public static Level PP;
    public static Level PQ;
    public static Level PR;

    public static void method_f293f253906d266e1f11(String text, Object... aobject) {
        if (PM.isLoggable(Level.WARNING)) {
            PM.log(Level.WARNING, String.format(text, aobject));
        }
    }

    public static void method_ad454fae748de881860b(String text, Object... aobject) {
        if (PM.isLoggable(PQ)) {
            PM.log(PQ, String.format(text, aobject));
        }
    }

    public static void severe(String text) {
        if (PM.isLoggable(Level.SEVERE)) {
            PM.log(Level.SEVERE, text);
        }
    }

    // region String decoding support

    // endregion

    public static void fine(String text) {
        if (PM.isLoggable(PN)) {
            PM.log(PN, text);
        }
    }

    public static void method_940da8a8e278fb8fbea3(String text, Object... aobject) {
        if (PM.isLoggable(PR)) {
            PM.log(PR, String.format(text, aobject));
        }
    }

    static {
        b((Entity[]) null);
        shadersmod.common.ShaderLog.PREFIX = "SMC";
        PM = new SMCLogger("SMC");
        PR = new SMCLogLevel("INF", 850, (ShaderLogAccessMarker) null);
        PQ = new SMCLogLevel("CFG", 840, (ShaderLogAccessMarker) null);
        PP = new SMCLogLevel("FNE", 830, (ShaderLogAccessMarker) null);
        PN = new SMCLogLevel("FNR", 820, (ShaderLogAccessMarker) null);
        PL = new SMCLogLevel("FNT", 810, (ShaderLogAccessMarker) null);
    }

    public static void method_75df6c73644e20229bce(String text) {
        if (PM.isLoggable(PQ)) {
            PM.log(PQ, text);
        }
    }

    public static void b(Entity[] entityArray) {
        PK = entityArray;
    }

    public static void info(String text) {
        if (PM.isLoggable(PR)) {
            PM.log(PR, text);
        }
    }

    public static void method_520c3a439faf81715af3(Level level, String text) {
        if (PM.isLoggable(level)) {
            PM.log(level, text);
        }
    }

    public static void method_03ea4787aeaef436d386(String text) {
        if (PM.isLoggable(PP)) {
            PM.log(PP, text);
        }
    }

    public static void method_8faaab9d19a7c4eef9ff(String text, Object... aobject) {
        if (PM.isLoggable(PL)) {
            PM.log(PL, String.format(text, aobject));
        }
    }

    public static void v(String text) {
        if (PM.isLoggable(PL)) {
            PM.log(PL, text);
        }
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return PK;
    }

    public static void method_15bfa2c6901ba2c7c2bb(String text, Object... aobject) {
        if (PM.isLoggable(Level.SEVERE)) {
            PM.log(Level.SEVERE, String.format(text, aobject));
        }
    }

    // region String decoding support

    // endregion

    public static void method_b7173a9df9dd59e690c2(String text, Object... aobject) {
        if (PM.isLoggable(PP)) {
            PM.log(PP, String.format(text, aobject));
        }
    }

    public static void method_17de7d5414bb4dc987d5(String text, Object... aobject) {
        if (PM.isLoggable(PN)) {
            PM.log(PN, String.format(text, aobject));
        }
    }

    public static void method_5f636bf5483f951a1ca0(Level level, String text, Object... aobject) {
        if (PM.isLoggable(level)) {
            PM.log(level, String.format(text, aobject));
        }
    }

    // region String decoding support

    // endregion

    public static void warning(String text) {
        if (PM.isLoggable(Level.WARNING)) {
            PM.log(Level.WARNING, text);
        }
    }
}
