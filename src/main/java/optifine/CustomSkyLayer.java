package optifine;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import java.util.Properties;

public class CustomSkyLayer {
    private int x;
    private RangeListInt days;
    private int blend;
    private boolean rotate;
    public String source = null;
    private float speed;
    private int endFadeOut;
    private float[] axis;
    public int textureId;
    private int startFadeOut;
    public static float[] DEFAULT_AXIS;

    private int startFadeIn = -1;
    private int endFadeIn = -1;

    private static String[] packedTextFragments;

    private void renderSide(Tessellator tessellator, int p171_1) {
        net.minecraft.client.renderer.WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        double d0 = p171_1 % 3 / 3.0;
        double d1 = p171_1 / 3 / 2.0;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-100.0, -100.0, -100.0, d0, d1);
        worldRenderer.addVertexWithUV(-100.0, -100.0, 100.0, d0, d1 + 0.5);
        worldRenderer.addVertexWithUV(100.0, -100.0, 100.0, d0 + 0.3333333333333333, d1 + 0.5);
        worldRenderer.addVertexWithUV(100.0, -100.0, -100.0, d0 + 0.3333333333333333, d1);
        tessellator.draw();
    }

    private int normalizeTime(int i) {
        int j = ReflectorMethod.cR();

        while (i >= 24000) {
            i -= 24000;
            if (j == 0) {
                break;
            }
        }

        while (i < 0) {
            i += 24000;
            if (j == 0) {
                break;
            }
        }

        return i;
    }

    private float getFadeBrightness(int p171_0) {
        if (this.timeBetween(p171_0, this.startFadeIn, this.endFadeIn)) {
            int l = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            int i1 = this.normalizeTime(p171_0 - this.startFadeIn);
            return (float) i1 / l;
        } else if (this.timeBetween(p171_0, this.endFadeIn, this.startFadeOut)) {
            return 1.0F;
        } else if (this.timeBetween(p171_0, this.startFadeOut, this.endFadeOut)) {
            int j = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            int k = this.normalizeTime(p171_0 - this.startFadeOut);
            return 1.0F - (float) k / j;
        } else {
            return 0.0F;
        }
    }

    private float[] parseAxis(String text, float[] afloat) {
        int i = ReflectorMethod.f();
        if (text == null) {
            return afloat;
        }

        String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[1]);
        if (astring.length != 3) {
            OptiFineConfig.warn("Invalid axis: " + text);
            return afloat;
        }

        float[] afloat1 = new float[3];

        for (int j = 0; j < astring.length; j++) {
            afloat1[j] = OptiFineConfig.parseFloat(astring[j], Float.MIN_VALUE);
            if (afloat1[j] == Float.MIN_VALUE) {
                OptiFineConfig.warn("Invalid axis: " + text);
                return afloat;
            }

            if (afloat1[j] < -1.0F || afloat1[j] > 1.0F) {
                OptiFineConfig.warn("Invalid axis values: " + text);
                return afloat;
            }
            break;
        }

        float f2 = afloat1[0];
        float fx = afloat1[1];
        float f1 = afloat1[2];
        if (f2 * f2 + fx * fx + f1 * f1 < 1.0E-5F) {
            OptiFineConfig.warn("Invalid axis values: " + text);
            return afloat;
        } else {
            return new float[] {f1, fx, -f2};
        }
    }

    static {
        optifine.CustomSkyLayer.packedTextFragments = new String[4];
        optifine.CustomSkyLayer.packedTextFragments[0] = new String(":");
        optifine.CustomSkyLayer.packedTextFragments[1] = new String(" ");
        optifine.CustomSkyLayer.DEFAULT_AXIS = new float[] {1.0F, 0.0F, 0.0F};
    }

    private boolean timeBetween(int p171_0, int p171_1, int p171_2) {
        return p171_1 <= p171_2
                ? p171_0 >= p171_1 && p171_0 <= p171_2
                : p171_0 >= p171_1 || p171_0 <= p171_2;
    }

    public boolean isActive(World world, int i) {
        int j = ReflectorMethod.f();
        if (this.timeBetween(i, this.endFadeOut, this.startFadeIn)) {
            return false;
        }

        if (this.days != null) {
            long k = world.getWorldTime();
            long l = k - this.startFadeIn;

            do {

                if (l >= 0L) {
                    break;
                }

                l += 24000 * this.x;
            } while (j == 0);

            int i1 = (int) (l / 24000L);
            int j1 = i1 % this.x;
            if (!this.days.isInRange(j1)) {
                return false;
            }
        }

        return true;
    }

    public void render(int p171_0, float p171_1, float p171_2) {
        float f2 = p171_2 * this.getFadeBrightness(p171_0);
        f2 = optifine.OptiFineConfig.limit(f2, 0.0F, 1.0F);
        if (f2 >= 1.0E-4F) {
            net.minecraft.client.renderer.GlStateManager.bindTexture(this.textureId);
            optifine.Blender.setupBlend(this.blend, f2);
            net.minecraft.client.renderer.GlStateManager.pushMatrix();
            if (this.rotate) {
                net.minecraft.client.renderer.GlStateManager.rotate(
                        p171_1 * 360.0F * this.speed, this.axis[0], this.axis[1], this.axis[2]);
            }

            net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
            net.minecraft.client.renderer.GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            net.minecraft.client.renderer.GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(instance, 4);
            net.minecraft.client.renderer.GlStateManager.pushMatrix();
            net.minecraft.client.renderer.GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            this.renderSide(instance, 1);
            net.minecraft.client.renderer.GlStateManager.popMatrix();
            net.minecraft.client.renderer.GlStateManager.pushMatrix();
            net.minecraft.client.renderer.GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            this.renderSide(instance, 0);
            net.minecraft.client.renderer.GlStateManager.popMatrix();
            net.minecraft.client.renderer.GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(instance, 5);
            net.minecraft.client.renderer.GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(instance, 2);
            net.minecraft.client.renderer.GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(instance, 3);
            net.minecraft.client.renderer.GlStateManager.popMatrix();
        }
    }

    public boolean isValid(String text) {
        if (this.source == null) {
            OptiFineConfig.warn("No source texture: " + text);
            return false;
        }

        this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(text));
        if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
            int i = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            if (this.startFadeOut < 0) {
                this.startFadeOut = this.normalizeTime(this.endFadeOut - i);
                if (this.timeBetween(
                        this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
                    this.startFadeOut = this.endFadeIn;
                }
            }

            int j = this.normalizeTime(this.startFadeOut - this.endFadeIn);
            int k = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            int l = this.normalizeTime(this.startFadeIn - this.endFadeOut);
            int i1 = i + j + k + l;
            if (i1 != 24000) {
                OptiFineConfig.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + i1);
                return false;
            } else if (this.speed < 0.0F) {
                OptiFineConfig.warn("Invalid speed: " + this.speed);
                return false;
            } else if (this.x <= 0) {
                OptiFineConfig.warn("Invalid daysLoop: " + this.x);
                return false;
            } else {
                return true;
            }
        } else {
            OptiFineConfig.warn(
                    "Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
        }
    }

    private float parseFloat(String text, float f) {
        if (text == null) {
            return f;
        } else {
            float f1 = OptiFineConfig.parseFloat(text, Float.MIN_VALUE);
            if (f1 == Float.MIN_VALUE) {
                OptiFineConfig.warn("Invalid value: " + text);
                return f;
            } else {
                return f1;
            }
        }
    }

    private int parseTime(String text) {
        if (text == null) {
            return -1;
        }

        String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[0]);
        if (astring.length != 2) {
            OptiFineConfig.warn("Invalid time: " + text);
            return -1;
        }

        String textValue = astring[0];
        String textValue2 = astring[1];
        int i = OptiFineConfig.parseInt(textValue, -1);
        int j = OptiFineConfig.parseInt(textValue2, -1);
        if (i >= 0 && i <= 23 && j >= 0 && j <= 59) {
            i -= 6;
            if (i < 0) {
                i += 24;
            }

            return i * 1000 + (int) (j / 60.0 * 1000.0);
        } else {
            OptiFineConfig.warn("Invalid time: " + text);
            return -1;
        }
    }

    public CustomSkyLayer(Properties properties, String text) {
        this.startFadeOut = -1;
        this.endFadeOut = -1;
        this.blend = 1;
        this.rotate = false;
        this.speed = 1.0F;
        this.axis = optifine.CustomSkyLayer.DEFAULT_AXIS;
        this.days = null;
        this.x = 8;
        this.textureId = -1;
        ConnectedParser connectedParser = new ConnectedParser("CustomSky");
        this.source = properties.getProperty("source", text);
        this.startFadeIn = this.parseTime(properties.getProperty("startFadeIn"));
        this.endFadeIn = this.parseTime(properties.getProperty("endFadeIn"));
        this.startFadeOut = this.parseTime(properties.getProperty("startFadeOut"));
        this.endFadeOut = this.parseTime(properties.getProperty("endFadeOut"));
        this.blend = Blender.parseBlend(properties.getProperty("blend"));
        this.rotate = this.parseBoolean(properties.getProperty("rotate"), true);
        this.speed = this.parseFloat(properties.getProperty("speed"), 1.0F);
        this.axis =
                this.parseAxis(properties.getProperty("axis"), optifine.CustomSkyLayer.DEFAULT_AXIS);
        this.days = connectedParser.parseRangeListInt(properties.getProperty("days"));
        this.x = connectedParser.parseIntOrDefault(properties.getProperty("daysLoop"), 8);
    }

    private boolean parseBoolean(String text, boolean flag) {
        if (text == null) {
            return flag;
        }

        String textValue = text.toLowerCase();
        if (textValue.equals("true")) {
            return true;
        }

        if (textValue.equals("false")) {
            return false;
        }

        OptiFineConfig.warn("Unknown boolean: " + text);
        return flag;
    }
}
