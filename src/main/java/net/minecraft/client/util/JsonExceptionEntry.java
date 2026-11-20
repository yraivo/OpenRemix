package net.minecraft.client.util;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class JsonExceptionEntry {
    private static String[] packedTextFragments;

    private List uQ;
    private String a = null;

    private JsonExceptionEntry() {
        this.uQ = Lists.newArrayList();
    }

    @Override
    public String toString() {
        return this.a != null
                ? (!this.uQ.isEmpty()
                        ? this.a + packedTextFragments[0] + this.method_e0e3092a59fee58fae52()
                        : this.a)
                : (!this.uQ.isEmpty()
                        ? "(Unknown file) " + this.method_e0e3092a59fee58fae52()
                        : "(Unknown file)");
    }

    static {
        net.minecraft.client.util.JsonExceptionEntry.packedTextFragments = new String[2];
        net.minecraft.client.util.JsonExceptionEntry.packedTextFragments[0] = new String(" ");
    }

    static String access$202(JsonExceptionEntry jsonExceptionEntry, String text) {
        return jsonExceptionEntry.a = text;
    }

    JsonExceptionEntry(Object object) {
        this();
    }

    private void method_be01d1eb5e587c583ed6(String text) {
        this.uQ.add(0, text);
    }

    static void access$100(JsonExceptionEntry jsonExceptionEntry, String text) {
        jsonExceptionEntry.method_be01d1eb5e587c583ed6(text);
    }

    public String method_e0e3092a59fee58fae52() {
        return StringUtils.join(this.uQ, "->");
    }
}
