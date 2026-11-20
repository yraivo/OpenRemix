package com.thealtening.authlibapi;

public class TheAlteningAuthLibAPI {
    private ServiceSwitcher b;
    private final SSLController a = new SSLController();
    private EnumAltService c;

    public TheAlteningAuthLibAPI() {
        this.b = new ServiceSwitcher();
    }

    public EnumAltService b() {
        return this.c;
    }

    public void method_8d9b536bcdc9991c48fb() {
        this.c = EnumAltService.MOJANG;
        this.a(this.c);
        this.b.method_8d9b536bcdc9991c48fb();
    }

    public void a(EnumAltService enumaltservice) {
        if (enumaltservice != EnumAltService.MOJANG)
            throw new UnsupportedOperationException("Third-party account service is disabled");
        this.c = EnumAltService.MOJANG;
    }
}
