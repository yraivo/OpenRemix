package net.minecraft.client.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;

import com.google.gson.JsonObject;

import org.lwjgl.opengl.GL14;

public class JsonBlendingMode {
    private int field_148114_d;

    private int field_148116_b;
    private boolean field_148119_h;

    private static String[] packedTextFragments;
    private int field_148112_f;
    private boolean field_148113_g;
    private int field_148117_c;
    private static JsonBlendingMode field_148118_a;
    private int field_148115_e;

    public JsonBlendingMode() {
        this(false, true, 1, 0, 1, 0, 32774);
    }

    private static int method_538d40b2bd6c72868266(String text) {
        String textValue = text.trim().toLowerCase();
        return textValue.equals("add")
                ? 32774
                : (textValue.equals("subtract")
                        ? 32778
                        : (textValue.equals("reversesubtract")
                                ? 32779
                                : (textValue.equals("reverse_subtract")
                                        ? 32779
                                        : (textValue.equals("min")
                                                ? 32775
                                                : (textValue.equals("max") ? 32776 : 32774)))));
    }

    public JsonBlendingMode(int i, int j, int k, int l, int i1) {
        this(true, false, i, j, k, l, i1);
    }

    public boolean func_148111_b() {
        return this.field_148119_h;
    }

    private static int method_7e9ca755b89de09da85b(String text) {
        String textValue = text.trim().toLowerCase();
        textValue = textValue.replaceAll(packedTextFragments[0], packedTextFragments[1]);
        textValue = textValue.replaceAll("one", packedTextFragments[2]);
        textValue = textValue.replaceAll("zero", packedTextFragments[3]);
        textValue = textValue.replaceAll("minus", packedTextFragments[4]);
        return textValue.equals(packedTextFragments[5])
                ? 0
                : (textValue.equals(packedTextFragments[6])
                        ? 1
                        : (textValue.equals("srccolor")
                                ? 768
                                : (textValue.equals("1-srccolor")
                                        ? 769
                                        : (textValue.equals("dstcolor")
                                                ? 774
                                                : (textValue.equals("1-dstcolor")
                                                        ? 775
                                                        : (textValue.equals("srcalpha")
                                                                ? 770
                                                                : (textValue.equals("1-srcalpha")
                                                                        ? 771
                                                                        : (textValue.equals(
                                                                                        "dstalpha")
                                                                                ? 772
                                                                                : (textValue.equals(
                                                                                                "1-dstalpha")
                                                                                        ? 773
                                                                                        : -1)))))))));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof JsonBlendingMode)) {
            return false;
        }

        JsonBlendingMode jsonBlendingMode = (JsonBlendingMode) object;
        return this.field_148112_f != jsonBlendingMode.field_148112_f
                ? false
                : (this.field_148115_e != jsonBlendingMode.field_148115_e
                        ? false
                        : (this.field_148114_d != jsonBlendingMode.field_148114_d
                                ? false
                                : (this.field_148119_h != jsonBlendingMode.field_148119_h
                                        ? false
                                        : (this.field_148113_g != jsonBlendingMode.field_148113_g
                                                ? false
                                                : (this.field_148117_c
                                                                != jsonBlendingMode.field_148117_c
                                                        ? false
                                                        : this.field_148116_b
                                                                == jsonBlendingMode
                                                                        .field_148116_b)))));
    }

    @Override
    public int hashCode() {
        JsonException.b();
        int i = this.field_148116_b;
        i = 31 * i + this.field_148117_c;
        i = 31 * i + this.field_148114_d;
        i = 31 * i + this.field_148115_e;
        i = 31 * i + this.field_148112_f;
        i = 31 * i + (this.field_148113_g ? 1 : 0);
        i = 31 * i + (this.field_148119_h ? 1 : 0);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            JsonException.b(new String[4]);
        }

        return i;
    }

    public void method_8d9b536bcdc9991c48fb() {
        String[] astring = JsonException.b();
        if (!this.equals(net.minecraft.client.util.JsonBlendingMode.field_148118_a)) {
            if (net.minecraft.client.util.JsonBlendingMode.field_148118_a == null
                    || this.field_148119_h
                            != net.minecraft.client.util.JsonBlendingMode.field_148118_a.func_148111_b()) {
                net.minecraft.client.util.JsonBlendingMode.field_148118_a = this;
                if (this.field_148119_h) {
                    GlStateManager.disableBlend();
                    return;
                }

                GlStateManager.enableBlend();
            }

            GL14.glBlendEquation(this.field_148112_f);
            if (this.field_148113_g) {
                GlStateManager.tryBlendFuncSeparate(
                        this.field_148116_b,
                        this.field_148114_d,
                        this.field_148117_c,
                        this.field_148115_e);
                if (astring == null) {
                    return;
                }
            }

            GlStateManager.blendFunc(this.field_148116_b, this.field_148114_d);
        }
    }

    public static JsonBlendingMode method_630ec23117d00eb89950(JsonObject jsonobject) {
        if (jsonobject == null) {
            return new JsonBlendingMode();
        }

        int i = 32774;
        int j = 1;
        int k = 0;
        int l = 1;
        int i1 = 0;
        byte b0 = 1;
        byte b1 = 0;
        if (JsonUtils.method_7cc6238b82e560d487c5(jsonobject, "func")) {
            i = method_538d40b2bd6c72868266(jsonobject.get("func").getAsString());
            if (i != 32774) {
                b0 = 0;
            }
        }

        if (JsonUtils.method_7cc6238b82e560d487c5(jsonobject, "srcrgb")) {
            j = method_7e9ca755b89de09da85b(jsonobject.get("srcrgb").getAsString());
            if (j != 1) {
                b0 = 0;
            }
        }

        if (JsonUtils.method_7cc6238b82e560d487c5(jsonobject, "dstrgb")) {
            k = method_7e9ca755b89de09da85b(jsonobject.get("dstrgb").getAsString());
            if (k != 0) {
                b0 = 0;
            }
        }

        if (JsonUtils.method_7cc6238b82e560d487c5(jsonobject, "srcalpha")) {
            l = method_7e9ca755b89de09da85b(jsonobject.get("srcalpha").getAsString());
            if (l != 1) {
                b0 = 0;
            }

            b1 = 1;
        }

        if (JsonUtils.method_7cc6238b82e560d487c5(jsonobject, "dstalpha")) {
            i1 = method_7e9ca755b89de09da85b(jsonobject.get("dstalpha").getAsString());
            if (i1 != 0) {
                b0 = 0;
            }

            b1 = 1;
        }

        return b0 != 0
                ? new JsonBlendingMode()
                : (b1 != 0 ? new JsonBlendingMode(j, k, l, i1, i) : new JsonBlendingMode(j, k, i));
    }

    static {
        net.minecraft.client.util.JsonBlendingMode.packedTextFragments = new String[9];
        net.minecraft.client.util.JsonBlendingMode.packedTextFragments[0] = new String("_");
        net.minecraft.client.util.JsonBlendingMode.packedTextFragments[1] = new String("");
        net.minecraft.client.util.JsonBlendingMode.packedTextFragments[2] = new String("1");
        net.minecraft.client.util.JsonBlendingMode.packedTextFragments[3] = new String("0");
        net.minecraft.client.util.JsonBlendingMode.packedTextFragments[4] = new String("-");
        net.minecraft.client.util.JsonBlendingMode.packedTextFragments[5] = new String("0");
        net.minecraft.client.util.JsonBlendingMode.packedTextFragments[6] = new String("1");
        net.minecraft.client.util.JsonBlendingMode.field_148118_a = null;
    }

    private JsonBlendingMode(boolean flag, boolean flag1, int i, int j, int k, int l, int i1) {
        this.field_148113_g = flag;
        this.field_148116_b = i;
        this.field_148114_d = j;
        this.field_148117_c = k;
        this.field_148115_e = l;
        this.field_148119_h = flag1;
        this.field_148112_f = i1;
    }

    public JsonBlendingMode(int i, int j, int k) {
        this(false, false, i, j, i, j, k);
    }
}
