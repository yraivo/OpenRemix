package com.thealtening.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thealtening.api.data.AccountData;
import com.thealtening.api.data.LicenseData;
import com.thealtening.api.utils.HttpUtils;
import com.thealtening.api.utils.exceptions.TheAlteningException;

import net.minecraft.entity.Entity;

import java.io.IOException;
import java.util.logging.Logger;

public class TheAltening extends HttpUtils {
    private final String c = "http://api.thealtening.com/v1/";
    private Gson e;
    public String apiToken;

    private final Logger d = Logger.getLogger("TheAltening");

    private static int[] f;

    public TheAltening(String text) {
        this.e = new Gson();
        this.apiToken = text;
    }

    public static int[] c() {
        return f;
    }

    public boolean jvm171_b_6af870e5e469af(String text) throws TheAlteningException {
        c();

        byte b0;
        try {
            String textValue =
                    this.jvm171_a_6abc28ad8430a3(
                            "http://api.thealtening.com/v1/favorite?acctoken="
                                    + text
                                    + "&token="
                                    + this.apiToken);
            JsonObject jsonobject = (JsonObject) this.e.fromJson(textValue, JsonObject.class);
            if (jsonobject == null) {
                throw new TheAlteningException(
                        "JSON",
                        "Unable to parse JSON data, here's what is in there: \n" + textValue);
            }

            if (jsonobject.has("success")) {
                return jsonobject.get("success").getAsBoolean();
            }

            if (jsonobject.has("error") && jsonobject.has("errorMessage")) {
                throw new TheAlteningException(
                        jsonobject.get("error").getAsString(),
                        jsonobject.get("errorMessage").getAsString());
            }

            b0 = 0;
        } catch (IOException ioexception) {
            throw new TheAlteningException(
                    "IOException",
                    "Unable to establish a connection, here's why: \n" + ioexception.getCause());
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public LicenseData fetchLicense() throws TheAlteningException {
        try {
            String text =
                    this.jvm171_a_6abc28ad8430a3(
                            "http://api.thealtening.com/v1/license?token=" + this.apiToken);
            JsonObject jsonobject = (JsonObject) this.e.fromJson(text, JsonObject.class);
            if (jsonobject == null) {
                throw new TheAlteningException(
                        "JSON", "Unable to parse JSON data, here's what is in there: \n" + text);
            } else if (jsonobject.has("error") && jsonobject.has("errorMessage")) {
                throw new TheAlteningException(
                        jsonobject.get("error").getAsString(),
                        jsonobject.get("errorMessage").getAsString());
            } else {
                return (LicenseData) this.e.fromJson(jsonobject, LicenseData.class);
            }
        } catch (IOException ioexception) {
            throw new TheAlteningException(
                    "IOException",
                    "Unable to establish a connection, here's why: \n" + ioexception.getCause());
        }
    }

    public static void b(int[] aint) {
        f = aint;
    }

    static {
        int[] aint = new int[5];
        b(aint);
    }

    public AccountData generateAccount() throws TheAlteningException {
        try {
            String text =
                    this.jvm171_a_6abc28ad8430a3(
                            "http://api.thealtening.com/v1/generate?info=true&token="
                                    + this.apiToken);
            JsonObject jsonobject = (JsonObject) this.e.fromJson(text, JsonObject.class);
            if (jsonobject == null) {
                return null;
            } else {
                return jsonobject.has("error") && jsonobject.has("errorMessage")
                        ? null
                        : (AccountData) this.e.fromJson(jsonobject, AccountData.class);
            }
        } catch (IOException ioexception) {
            return null;
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public boolean jvm171_a_12d19207cee439(String text) throws TheAlteningException {
        c();

        byte b0;
        try {
            String textValue =
                    this.jvm171_a_6abc28ad8430a3(
                            "http://api.thealtening.com/v1/private?acctoken="
                                    + text
                                    + "&token="
                                    + this.apiToken);
            JsonObject jsonobject = (JsonObject) this.e.fromJson(textValue, JsonObject.class);
            if (jsonobject == null) {
                throw new TheAlteningException(
                        "JSON",
                        "Unable to parse JSON data, here's what is in there: \n" + textValue);
            }

            if (jsonobject.has("success")) {
                return jsonobject.get("success").getAsBoolean();
            }

            if (jsonobject.has("error") && jsonobject.has("errorMessage")) {
                throw new TheAlteningException(
                        jsonobject.get("error").getAsString(),
                        jsonobject.get("errorMessage").getAsString());
            }

            b0 = 0;
        } catch (IOException ioexception) {
            throw new TheAlteningException(
                    "IOException",
                    "Unable to establish a connection, here's why: \n" + ioexception.getCause());
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new int[4]);
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }
}
