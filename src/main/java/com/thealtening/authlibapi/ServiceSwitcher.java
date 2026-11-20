package com.thealtening.authlibapi;

import com.thealtening.authlibapi.reflection.ReflectionClass;

import java.net.MalformedURLException;
import java.net.URL;

class ServiceSwitcher {
    private ReflectionClass b;

    private ReflectionClass a;


    void method_8d9b536bcdc9991c48fb() {
        // Preserve the official authlib host allowlist.
    }

    private String b(EnumAltService enumaltservice) {
        String text = enumaltservice == EnumAltService.THEALTENING ? "http" : "https";
        return text + "://authserver." + enumaltservice.name().toLowerCase() + ".com/";
    }

    private String c(EnumAltService enumaltservice) {
        String text = enumaltservice == EnumAltService.THEALTENING ? "http" : "https";
        return text + "://sessionserver." + enumaltservice.name().toLowerCase() + ".com/";
    }

    EnumAltService a(EnumAltService enumaltservice) {
        if (enumaltservice != EnumAltService.MOJANG)
            throw new UnsupportedOperationException("Third-party account service is disabled");
        return EnumAltService.MOJANG;
    }

    ServiceSwitcher() {
        super();
        EnumAltService.b();

        this.a = null;
        this.b = null;
    }

    private URL a(String text) throws MalformedURLException {
        return new URL(text);
    }
}
