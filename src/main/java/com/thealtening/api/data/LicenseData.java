package com.thealtening.api.data;

import com.google.gson.annotations.SerializedName;

import net.minecraft.entity.Entity;

public class LicenseData {
    private boolean b;
    private static String e;

    @SerializedName("expires")
    private String d;

    @SerializedName("premium_name")
    private String c;

    private String a;

    @Override
    public String toString() {
        int i = AccountData.g();
        String text = String.format(e, this.a, this.b, this.c, this.d);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AccountData.b(++i);
        }

        return text;
    }

    public String d() {
        return this.d;
    }

    public String method_e0e3092a59fee58fae52() {
        return this.a;
    }

    static {
        e = "LicenseData[%s:%s:%s:%s]";
    }

    public boolean method_b417d7500ec59d69e373() {
        return this.b;
    }

    public String method_cee993844cce4b97ed62() {
        return this.c;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LicenseData)) {
            return false;
        }

        LicenseData licensedata1 = (LicenseData) object;
        return licensedata1.d().equals(this.d())
                && licensedata1
                        .method_cee993844cce4b97ed62()
                        .equals(this.method_cee993844cce4b97ed62())
                && licensedata1.method_b417d7500ec59d69e373() == this.method_b417d7500ec59d69e373()
                && licensedata1
                        .method_e0e3092a59fee58fae52()
                        .equals(this.method_e0e3092a59fee58fae52());
    }
}
