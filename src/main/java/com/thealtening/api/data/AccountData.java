package com.thealtening.api.data;

import com.google.gson.annotations.SerializedName;

import net.minecraft.entity.Entity;

public class AccountData {
    @SerializedName("password")
    private String b;

    @SerializedName("info")
    private AccountInfo e;

    private static int f;

    @SerializedName("username")
    private String c;

    private static String g;

    @SerializedName("token")
    private String a;

    @SerializedName("limit")
    private boolean d;

    public static int g() {
        int i = f();
        return i == 0 ? 21 : 0;
    }

    static {
        if (g() != 0) {
            b(78);
        }
        g = "AccountData[%s:%s:%s:%s]";
    }

    public String method_c60693f457aa70be7160() {
        return this.b;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AccountData)) {
            return false;
        }

        AccountData accountdata1 = (AccountData) object;
        return accountdata1.method_cee993844cce4b97ed62().equals(this.c)
                && accountdata1.getToken().equals(this.a);
    }

    public String method_cee993844cce4b97ed62() {
        return this.c;
    }

    public AccountInfo e() {
        return this.e;
    }

    public static int f() {
        return f;
    }

    public static void b(int i) {
        f = i;
    }

    @Override
    public String toString() {
        int i = g();
        String text = String.format(g, this.a, this.c, this.b, this.d);
        if (i != 0) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }

        return text;
    }

    public boolean d() {
        return this.d;
    }

    public String getToken() {
        return this.a;
    }
}
