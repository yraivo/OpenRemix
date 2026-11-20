package remix.ui.font;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GlyphAtlasFont {
    protected DynamicTexture texture;
    protected GlyphMetrics[] glyphs = new GlyphMetrics[256];
    protected int characterOffset;
    private float textureSize;
    protected boolean antiAlias;
    protected Font font;
    private static Entity[] legacyFontFlowMarker;
    protected int fontHeight = -1;
    protected boolean fractionalMetrics;

    public float getUnscaledHeight() {
        return (this.fontHeight - 8.0F) / 2.0F;
    }

    public int getStringWidth(String text) {
        boolean legacyFirstCharacter = getLegacyFontFlowMarker() != null;
        int width = 0;
        for (char character : text.toCharArray()) {
            if (character < this.glyphs.length) {
                width += this.glyphs[character].width - 8 + this.characterOffset;
            }
            if (legacyFirstCharacter) {
                break;
            }
        }
        return width / 2;
    }

    public static void setLegacyFontFlowMarker(Entity[] entityArray) {
        remix.ui.font.GlyphAtlasFont.legacyFontFlowMarker = entityArray;
    }

    public float getHeight() {
        return (this.fontHeight - 8) / 2.0F * 0.9F;
    }

    public boolean usesFractionalMetrics() {
        return this.fractionalMetrics;
    }

    static {
        if (getLegacyFontFlowMarker() != null) {
            setLegacyFontFlowMarker(new Entity[4]);
        }
    }

    private static ArrayIndexOutOfBoundsException preserveAtlasBoundsException(
            ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
        return arrayindexoutofboundsexception;
    }

    public void setAntiAlias(boolean antiAliasValue) {
        try {
            if (this.antiAlias != antiAliasValue) {
                this.antiAlias = antiAliasValue;
                this.texture =
                        this.createFontTexture(
                                this.font, antiAliasValue, this.fractionalMetrics, this.glyphs);
            }
        } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw preserveAtlasBoundsException(arrayindexoutofboundsexception);
        }
    }

    public double getLineHeight() {
        return (this.fontHeight - 8.0) / 2.0;
    }

    public void setFont(Font font) {
        this.font = font;
        this.texture =
                this.createFontTexture(font, this.antiAlias, this.fractionalMetrics, this.glyphs);
    }

    public GlyphAtlasFont(Font font, boolean antiAliasValue, boolean fractionalMetricsValue) {
        this.characterOffset = 0;
        this.textureSize = 512.0F;
        this.font = font;
        this.antiAlias = antiAliasValue;
        this.fractionalMetrics = fractionalMetricsValue;
        this.texture =
                this.createFontTexture(font, antiAliasValue, fractionalMetricsValue, this.glyphs);
    }

    public float getTextHeight(String text) {
        return this.getHeight();
    }

    public void drawGlyph(GlyphMetrics[] glyphMetricsArray, char c0, float f, float f1)
            throws ArrayIndexOutOfBoundsException {
        try {
            this.drawTexturedQuad(
                    f,
                    f1,
                    glyphMetricsArray[c0].width,
                    glyphMetricsArray[c0].height,
                    glyphMetricsArray[c0].atlasX,
                    glyphMetricsArray[c0].atlasY,
                    glyphMetricsArray[c0].width,
                    glyphMetricsArray[c0].height);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected BufferedImage createGlyphAtlas(
            Font font, boolean flag, boolean flag1, GlyphMetrics[] glyphMetricsArray) {
        int textureSizeValue = (int) this.textureSize;
        BufferedImage bufferedimage = new BufferedImage(textureSizeValue, textureSizeValue, 2);
        Graphics2D graphics2d = (Graphics2D) bufferedimage.getGraphics();

        graphics2d.setFont(font);
        graphics2d.setColor(new Color(255, 255, 255, 0));
        graphics2d.fillRect(0, 0, textureSizeValue, textureSizeValue);
        graphics2d.setColor(Color.WHITE);
        graphics2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                flag1 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                flag ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        graphics2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                flag ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        FontMetrics fontmetrics = graphics2d.getFontMetrics();
        int j = 0;
        int k = 0;
        int l = 1;

        for (int i1 = 0; i1 < glyphMetricsArray.length; i1++) {
            char c0 = (char) i1;
            GlyphMetrics glyphMetrics = new GlyphMetrics(this);
            Rectangle2D rectangle2d = fontmetrics.getStringBounds(String.valueOf(c0), graphics2d);
            glyphMetrics.width = rectangle2d.getBounds().width + 8;
            glyphMetrics.height = rectangle2d.getBounds().height;
            if (k + glyphMetrics.width >= textureSizeValue) {
                k = 0;
                l += j;
                j = 0;
            }

            if (glyphMetrics.height > j) {
                j = glyphMetrics.height;
            }

            try {
                glyphMetrics.atlasX = k;
                glyphMetrics.atlasY = l;
                if (glyphMetrics.height > this.fontHeight) {
                    this.fontHeight = glyphMetrics.height;
                }
            } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                throw preserveAtlasBoundsException(arrayindexoutofboundsexception);
            }

            glyphMetricsArray[i1] = glyphMetrics;
            graphics2d.drawString(String.valueOf(c0), k + 2, l + fontmetrics.getAscent());
            k += glyphMetrics.width;
        }

        return bufferedimage;
    }

    public void setFractionalMetrics(boolean fractionalMetricsValue) {
        try {
            if (this.fractionalMetrics != fractionalMetricsValue) {
                this.fractionalMetrics = fractionalMetricsValue;
                this.texture =
                        this.createFontTexture(
                                this.font, this.antiAlias, fractionalMetricsValue, this.glyphs);
            }
        } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw preserveAtlasBoundsException(arrayindexoutofboundsexception);
        }
    }

    protected void drawTexturedQuad(
            float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = f4 / this.textureSize;
        float f9 = f5 / this.textureSize;
        float f10 = f6 / this.textureSize;
        float f11 = f7 / this.textureSize;
        Entity[] entityArray = getLegacyFontFlowMarker();
        GL11.glTexCoord2f(f8 + f10, f9);
        GL11.glVertex2d(f + f2, f1);
        Entity[] entityArrayValue = entityArray;

        try {
            GL11.glTexCoord2f(f8, f9);
            GL11.glVertex2d(f, f1);
            GL11.glTexCoord2f(f8, f9 + f11);
            GL11.glVertex2d(f, f1 + f3);
            GL11.glTexCoord2f(f8, f9 + f11);
            GL11.glVertex2d(f, f1 + f3);
            GL11.glTexCoord2f(f8 + f10, f9 + f11);
            GL11.glVertex2d(f + f2, f1 + f3);
            GL11.glTexCoord2f(f8 + f10, f9);
            GL11.glVertex2d(f + f2, f1);
            if (entityArrayValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[5]);
            }
        } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw preserveAtlasBoundsException(arrayindexoutofboundsexception);
        }
    }

    public float getTextHeightLegacy(String text) {
        return this.getHeight();
    }

    public boolean isAntiAlias() {
        return this.antiAlias;
    }

    protected DynamicTexture createFontTexture(
            Font font, boolean flag, boolean flag1, GlyphMetrics[] glyphMetricsArray) {
        BufferedImage bufferedimage = this.createGlyphAtlas(font, flag, flag1, glyphMetricsArray);

        try {
            return new DynamicTexture(bufferedimage);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Entity[] getLegacyFontFlowMarker() {
        return remix.ui.font.GlyphAtlasFont.legacyFontFlowMarker;
    }

    public Font getFont() {
        return this.font;
    }
}
