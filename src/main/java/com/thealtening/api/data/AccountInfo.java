package com.thealtening.api.data;

import com.google.gson.annotations.SerializedName;

public class AccountInfo {
    @SerializedName("labymod.cape")
    private boolean e;

    private static String[] g;

    @SerializedName("5zig.cape")
    private boolean f;

    @SerializedName("mineplex.rank")
    private String d;

    @SerializedName("hypixel.lvl")
    private int a;

    @SerializedName("hypixel.rank")
    private String b;

    private static String h;

    @SerializedName("mineplex.lvl")
    private int c;

    static {
        if (g() == null) {
            b(new String[4]);
        }
        h = "AccountInfo[%s:%s:%s:%s:%s:%s]";
    }

    public int method_beea3e16fe3ec0995eb9() {
        return this.c;
    }

    public boolean f() {
        return this.f;
    }

    @Override
    public String toString() {
        g();

        String text = String.format(h, this.a, this.b, this.c, this.d, this.e, this.f);

        return text;
    }

    public static void b(String[] astring) {
        g = astring;
    }

    public boolean e() {
        return this.e;
    }

    public String d() {
        return this.d;
    }

    public String method_c60693f457aa70be7160() {
        return this.b;
    }

    public static String[] g() {
        return g;
    }

    public int method_9b417d27949cba33a0d0() {
        return this.a;
    }
}
