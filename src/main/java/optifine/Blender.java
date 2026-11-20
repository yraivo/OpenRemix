package optifine;

import net.minecraft.client.renderer.GlStateManager;

public class Blender {
    public static final int dD = 3;
    public static final int f = 5;

    public static final int tB = 1;
    public static final int Ao = 6;
    public static final int a = 0;

    public static final int c = 2;
    public static final int mu = 8;
    public static final int cz = 1;
    public static final int ef = 4;
    public static final int sR = 7;

    public static void clearBlend(float f) {
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        GlStateManager.color(1.0F, 1.0F, 1.0F, f);
    }

    public static int parseBlend(String text) {
        if (text == null) {
            return 1;
        }

        text = text.toLowerCase().trim();
        if (text.equals("alpha")) {
            return 0;
        }

        if (text.equals("add")) {
            return 1;
        }

        if (text.equals("subtract")) {
            return 2;
        }

        if (text.equals("multiply")) {
            return 3;
        }

        if (text.equals("dodge")) {
            return 4;
        }

        if (text.equals("burn")) {
            return 5;
        }

        if (text.equals("screen")) {
            return 6;
        }

        if (text.equals("overlay")) {
            return 7;
        }

        if (text.equals("replace")) {
            return 8;
        }

        OptiFineConfig.warn("Unknown blend: " + text);
        return 1;
    }

    public static void setupBlend(int i, float f) {
        int j = ReflectorMethod.f();
        switch (i) {
            case 0:
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.color(1.0F, 1.0F, 1.0F, f);
                if (j == 0) {
                    break;
                }
            case 1:
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 1);
                GlStateManager.color(1.0F, 1.0F, 1.0F, f);
                if (j == 0) {
                    break;
                }
            case 2:
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(775, 0);
                GlStateManager.color(f, f, f, 1.0F);
                if (j == 0) {
                    break;
                }
            case 3:
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(774, 771);
                GlStateManager.color(f, f, f, f);
                if (j == 0) {
                    break;
                }
            case 4:
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(1, 1);
                GlStateManager.color(f, f, f, 1.0F);
                if (j == 0) {
                    break;
                }
            case 5:
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(0, 769);
                GlStateManager.color(f, f, f, 1.0F);
                if (j == 0) {
                    break;
                }
            case 6:
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(1, 769);
                GlStateManager.color(f, f, f, 1.0F);
                if (j == 0) {
                    break;
                }
            case 7:
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(774, 768);
                GlStateManager.color(f, f, f, 1.0F);
                if (j == 0) {
                    break;
                }
            case 8:
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, f);
        }

        GlStateManager.enableTexture2D();
    }


}
