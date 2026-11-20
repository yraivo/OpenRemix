package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import optifine.CustomColors;
import optifine.FontUtils;
import optifine.OptiFineConfig;

import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

import remix.client.RemixClient;
import remix.event.impl.TextRenderEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class FontRenderer implements IResourceManagerReloadListener {
    private static final String DEFAULT_GLYPHS =
            "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
                + " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000";

    private float baseBlue;
    private float[] charWidth;
    private ResourceLocation resolvedFontTexture;
    private boolean strikethroughStyle;

    public boolean enabled;
    public ResourceLocation baseFontTexture;
    public int[] colorCode;
    private int textColor;
    public GameSettings gameSettings;
    private static String[] packedTextFragments;
    private boolean unicodeFlag;
    private boolean underlineStyle;
    private TextureManager renderEngine;
    private byte[] glyphWidth;
    private boolean randomStyle;
    private float baseRed;
    private float alpha;
    private boolean boldStyle;
    public int FONT_HEIGHT;
    public Random fontRandom;
    private float baseGreen;
    private float penY;
    private float penX;
    private boolean bidiFlag;
    private static ResourceLocation[] unicodePageLocations;
    public float boldGlyphOffset;
    private boolean italicStyle;

    public String trimStringToWidth(String text, int p171_1) {
        return this.trimStringToWidth(text, p171_1, false);
    }

    private float getCharWidthFloat(char c0) {
        if (c0 == 167) {
            return -1.0F;
        } else if (c0 == ' ') {
            return this.charWidth[32];
        } else {
            int i = DEFAULT_GLYPHS.indexOf(c0);
            if (c0 > 0 && i != -1 && !this.unicodeFlag) {
                return this.charWidth[i];
            } else if (this.glyphWidth[c0] != 0) {
                int j = this.glyphWidth[c0] >>> 4;
                int k = this.glyphWidth[c0] & 15;
                j &= 15;
                k++;
                return (k - j) / 2 + 1;
            } else {
                return 0.0F;
            }
        }
    }

    private int renderStringAligned(
            String text, int p171_1, int p171_2, int p171_3, int p171_4, boolean p171_5) {
        if (this.bidiFlag) {
            int stringWidth = this.getStringWidth(this.bidiReorder(text));
            p171_1 = p171_1 + p171_3 - stringWidth;
        }

        return this.renderString(text, p171_1, p171_2, p171_4, p171_5);
    }

    private static boolean isFormatSpecial(char p171_0) {
        return (!((p171_0 < 107 || p171_0 > 111)
                && (p171_0 < 75 || p171_0 > 79)
                && p171_0 != 114
                && p171_0 != 82));
    }

    private int renderString(String text, float f, float f1, int i, boolean flag) {
        TextRenderEvent textRenderEvent = new TextRenderEvent(text);
        RemixClient.eventBus.post(textRenderEvent);
        text = textRenderEvent.getText();
        if (text != null && !textRenderEvent.isCancelled()) {
            if (this.bidiFlag) {
                text = this.bidiReorder(text);
            }

            if ((i & -67108864) == 0) {
                i |= -16777216;
            }

            if (flag) {
                i = (i & 16579836) >> 2 | i & 0xFF000000;
            }

            this.baseRed = (i >> 16 & 0xFF) / 255.0F;
            this.baseGreen = (i >> 8 & 0xFF) / 255.0F;
            this.baseBlue = (i & 0xFF) / 255.0F;
            this.alpha = (i >> 24 & 0xFF) / 255.0F;
            this.setColor(this.baseRed, this.baseGreen, this.baseBlue, this.alpha);
            this.penX = f;
            this.penY = f1;
            this.renderStringAtPos(text, flag);
            return (int) this.penX;
        } else {
            return 0;
        }
    }

    private void bindUnicodePage(int i) {
        this.bindTexture(this.getUnicodePageLocation(i));
    }

    public String trimStringToWidth(String text, int maximumWidth, boolean reverse) {
        boolean legacyFirstGlyphOnly = Gui.getLegacyGuiRenderMarker() != null;
        StringBuilder trimmed = new StringBuilder();
        float width = 0.0F;
        boolean nextIsFormat = false;
        boolean bold = false;
        int start = reverse ? text.length() - 1 : 0;
        int direction = reverse ? -1 : 1;
        for (int index = start;
                index >= 0 && index < text.length() && width < maximumWidth;
                index += direction) {
            char glyph = text.charAt(index);
            float glyphWidth = this.getCharWidthFloat(glyph);
            boolean measure = true;
            if (nextIsFormat) {
                nextIsFormat = false;
                if (glyph == 'l' || glyph == 'L') {
                    bold = true;
                } else if (glyph == 'r' || glyph == 'R') {
                    bold = legacyFirstGlyphOnly;
                } else {
                    measure = false;
                }
                measure &= legacyFirstGlyphOnly;
            }

            if (measure && glyphWidth < 0.0F) {
                nextIsFormat = true;
                measure = legacyFirstGlyphOnly;
            }
            if (measure) {
                width += glyphWidth;
                if (bold) {
                    width++;
                }
            }
            if (width > maximumWidth && !legacyFirstGlyphOnly) {
                break;
            }

            if (reverse) {
                trimmed.insert(0, glyph);
            }
            if (!reverse || legacyFirstGlyphOnly) {
                trimmed.append(glyph);
            }
            if (legacyFirstGlyphOnly) {
                break;
            }
        }
        return trimmed.toString();
    }

    public List listFormattedStringToWidth(String text, int p171_1) {
        return java.util.Arrays.asList(
                this.wrapFormattedStringToWidth(text, p171_1)
                        .split(net.minecraft.client.gui.FontRenderer.packedTextFragments[4]));
    }

    public int drawStringWithShadow(String text, float p171_1, float p171_2, int p171_3) {
        return this.drawString(text, p171_1, p171_2, p171_3, true);
    }

    private void renderSplitString(String text, int i, int j, int k, boolean flag) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        List list = this.listFormattedStringToWidth(text, k);
        String[] astring = astring1;

        for (java.util.Iterator<?> __recoveryIterator171_8353 = (list).iterator();
                (__recoveryIterator171_8353).hasNext(); ) {
            String textValue = (String) (__recoveryIterator171_8353).next();
            this.renderStringAligned(textValue, i, j, k, this.textColor, flag);
            j += this.FONT_HEIGHT;
            if (astring != null) {
                break;
            }
        }
    }

    private void readFontTexture() {
        String[] astring = Gui.getLegacyGuiRenderMarker();

        BufferedImage bufferedimage;
        try {
            bufferedimage =
                    TextureUtil.readBufferedImage(
                            this.getResourceInputStream(this.resolvedFontTexture));
        } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        }

        Properties properties = FontUtils.readFontProperties(this.resolvedFontTexture);
        int width = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int k = width / 16;
        int l = j / 16;
        float f = width / 128.0F;
        float f1 = OptiFineConfig.limit(f, 1.0F, 2.0F);
        this.boldGlyphOffset = 1.0F / f1;
        float f2 = FontUtils.readFloat(properties, "offsetBold", -1.0F);
        if (f2 >= 0.0F) {
            this.boldGlyphOffset = f2;
        }

        int[] aint = new int[width * j];
        bufferedimage.getRGB(0, 0, width, j, aint, 0, width);
        int i1 = 0;

        while (i1 < 256) {
            int j1 = i1 % 16;
            int k1 = i1 / 16;

            int l1 = k - 1;

            while (l1 >= 0) {
                int i2 = j1 * k + l1;
                byte b1 = 1;
                int j2 = 0;

                while (j2 < l && b1 != 0) {
                    int k2 = (k1 * l + j2) * width;
                    int l2 = aint[i2 + k2];
                    int i3 = l2 >> 24 & 0xFF;
                    if (i3 > 16) {
                        b1 = 0;
                    }

                    j2++;
                    if (astring != null) {
                        break;
                    }
                }

                if (b1 == 0) {
                    break;
                }

                l1--;
                if (astring != null) {
                    break;
                }
            }

            if (i1 == 65) {
                i1 = i1;
            }

            if (i1 == 32) {
                l1 = (int) ((k <= 8 && astring == null ? 2.0F : 1.5F) * f);
            }

            this.charWidth[i1] = (l1 + 1) / f + 1.0F;
            i1++;
            if (astring != null) {
                break;
            }
        }

        FontUtils.readCustomCharWidths(properties, this.charWidth);
    }

    public boolean getBidiFlag() {
        return this.bidiFlag;
    }

    public int drawString(String text, float p171_1, float p171_2, int p171_3, boolean p171_4) {
        this.enableAlpha();
        java.lang.String[] astring1 = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        this.resetStyles();
        java.lang.String[] astring = astring1;
        if (p171_4) {
            int j = this.renderString(text, p171_1 + 1.0F, p171_2 + 1.0F, p171_3, true);
            j = java.lang.Math.max(j, this.renderString(text, p171_1, p171_2, p171_3, false));
            if (astring == null) {
                return j;
            }
        }

        return this.renderString(text, p171_1, p171_2, p171_3, false);
    }

    protected InputStream getResourceInputStream(ResourceLocation resourceLocation)
            throws IOException {
        return MinecraftClient.getMinecraft()
                .getResourceManager()
                .getResource(resourceLocation)
                .getInputStream();
    }

    private float renderDefaultChar(int i, boolean flag) {
        float f = i % 16 * 8;
        float f1 = i / 16 * 8;
        float f2 = flag ? 1.0F : 0.0F;
        this.bindTexture(this.resolvedFontTexture);
        float f3 = 7.99F;
        GL11.glBegin(5);
        GL11.glTexCoord2f(f / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.penX + f2, this.penY, 0.0F);
        GL11.glTexCoord2f(f / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.penX - f2, this.penY + 7.99F, 0.0F);
        GL11.glTexCoord2f((f + f3 - 1.0F) / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.penX + f3 - 1.0F + f2, this.penY, 0.0F);
        GL11.glTexCoord2f((f + f3 - 1.0F) / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.penX + f3 - 1.0F - f2, this.penY + 7.99F, 0.0F);
        GL11.glEnd();
        return this.charWidth[i];
    }

    private float renderCharAtPos(int i, char c0, boolean flag) {
        return c0 == ' '
                ? (!this.unicodeFlag ? this.charWidth[c0] : 4.0F)
                : (c0 == ' '
                        ? 4.0F
                        : (DEFAULT_GLYPHS.indexOf(c0) != -1 && !this.unicodeFlag
                                ? this.renderDefaultChar(i, flag)
                                : this.renderUnicodeChar(c0, flag)));
    }

    public int getCharWidth(char p171_0) {
        return java.lang.Math.round(this.getCharWidthFloat(p171_0));
    }

    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    public void setUnicodeFlag(boolean unicodeFlagValue) {
        this.unicodeFlag = unicodeFlagValue;
    }

    static {
        net.minecraft.client.gui.FontRenderer.packedTextFragments = new String[8];
        net.minecraft.client.gui.FontRenderer.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.FontRenderer.packedTextFragments[1] = new String("\u00a7");
        net.minecraft.client.gui.FontRenderer.packedTextFragments[2] = new String("\u00a7");
        net.minecraft.client.gui.FontRenderer.packedTextFragments[3] = new String("\n");
        net.minecraft.client.gui.FontRenderer.packedTextFragments[4] = new String("\n");
        net.minecraft.client.gui.FontRenderer.packedTextFragments[5] = new String("\n");
        net.minecraft.client.gui.FontRenderer.unicodePageLocations = new ResourceLocation[256];
    }

    public void drawSplitString(
            String text, int p171_1, int p171_2, int p171_3, int textColorValue) {
        this.resetStyles();
        this.textColor = textColorValue;
        text = this.trimStringNewline(text);
        this.renderSplitString(text, p171_1, p171_2, p171_3, false);
    }

    String wrapFormattedStringToWidth(String text, int p171_1) {
        int var3 = this.sizeStringToWidth(text, p171_1);
        if (((int) (((java.lang.String) (text)).length())) <= var3) {
            return text;
        }

        java.lang.String var4 =
                ((java.lang.String) (((java.lang.String) (text)).substring(0, ((int) (var3)))));
        char var5 = ((char) (((java.lang.String) (text)).charAt(((int) (var3)))));
        int var6 = var5 != ' ' && var5 != '\n' ? 0 : 1;
        java.lang.String var7 =
                ((java.lang.String)
                        (((java.lang.StringBuilder)
                                        (((java.lang.StringBuilder)
                                                (((java.lang.StringBuilder)
                                                                (((java.lang.StringBuilder)
                                                                        (((java.lang.StringBuilder)
                                                                                        (new java
                                                                                                .lang
                                                                                                .StringBuilder()))
                                                                                .append(
                                                                                        ((java.lang
                                                                                                        .String)
                                                                                                (net
                                                                                                        .minecraft
                                                                                                        .client
                                                                                                        .gui
                                                                                                        .FontRenderer
                                                                                                        .getFormatFromString(
                                                                                                                var4))))))))
                                                        .append(
                                                                ((java.lang.String)
                                                                        (((java.lang.String)
                                                                                (((java.lang.String)
                                                                                                (text))
                                                                                        .substring(
                                                                                                ((int)
                                                                                                        (var3
                                                                                                                + (var6
                                                                                                                                != 0
                                                                                                                        ? 1
                                                                                                                        : 0)))))))))))))
                                .toString()));
        return ((java.lang.String)
                (((java.lang.StringBuilder)
                                (((java.lang.StringBuilder)
                                        (((java.lang.StringBuilder)
                                                        (((java.lang.StringBuilder)
                                                                (((java.lang.StringBuilder)
                                                                                (((java.lang
                                                                                                .StringBuilder)
                                                                                        (((java.lang
                                                                                                                .StringBuilder)
                                                                                                        (new java
                                                                                                                .lang
                                                                                                                .StringBuilder()))
                                                                                                .append(
                                                                                                        ((java
                                                                                                                        .lang
                                                                                                                        .String)
                                                                                                                (var4)))))))
                                                                        .append(
                                                                                ((java.lang.String)
                                                                                        (net
                                                                                                .minecraft
                                                                                                .client
                                                                                                .gui
                                                                                                .FontRenderer
                                                                                                .packedTextFragments[
                                                                                                5])))))))
                                                .append(
                                                        ((java.lang.String)
                                                                (this.wrapFormattedStringToWidth(
                                                                        var7, p171_1))))))))
                        .toString()));
    }

    private String trimStringNewline(String text) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();

        while (text != null
                && text.endsWith(net.minecraft.client.gui.FontRenderer.packedTextFragments[3])) {
            text = text.substring(0, text.length() - 1);
            if (astring != null) {
                break;
            }
        }

        return text;
    }

    private static boolean isFormatColor(char p171_0) {
        return (!((p171_0 < 48 || p171_0 > 57)
                && (p171_0 < 97 || p171_0 > 102)
                && (p171_0 < 65 || p171_0 > 70)));
    }

    private void renderStringAtPos(String text, boolean shadow) {
        boolean legacyFirstGlyphOnly = Gui.getLegacyGuiRenderMarker() != null;
        for (int index = 0; index < text.length(); index++) {
            char glyph = text.charAt(index);
            if (glyph == '\u00a7' && index + 1 < text.length()) {
                int format = "0123456789abcdefklmnor".indexOf(text.toLowerCase().charAt(++index));
                this.applyTextFormat(format, shadow, legacyFirstGlyphOnly);
                if (!legacyFirstGlyphOnly) {
                    continue;
                }
            }

            this.drawFormattedGlyph(glyph, shadow);
            if (legacyFirstGlyphOnly) {
                break;
            }
        }
    }

    private void applyTextFormat(int format, boolean shadow, boolean legacyFlow) {
        if (format < 16) {
            this.clearGlyphStyles();
            int paletteIndex = (format < 0 ? 15 : format) + (shadow ? 16 : 0);
            int color = this.colorCode[paletteIndex];
            if (OptiFineConfig.isCustomColors()) {
                color = CustomColors.getCustomTextColor(paletteIndex, color);
            }
            this.textColor = color;
            this.setColor(
                    (color >> 16) / 255.0F,
                    (color >> 8 & 0xFF) / 255.0F,
                    (color & 0xFF) / 255.0F,
                    this.alpha);
            if (!legacyFlow) {
                return;
            }
            format = paletteIndex;
        }

        switch (format) {
            case 16:
                this.randomStyle = true;
                break;
            case 17:
                this.boldStyle = true;
                break;
            case 18:
                this.strikethroughStyle = true;
                break;
            case 19:
                this.underlineStyle = true;
                break;
            case 20:
                this.italicStyle = true;
                break;
            case 21:
                this.clearGlyphStyles();
                this.setColor(this.baseRed, this.baseGreen, this.baseBlue, this.alpha);
                break;
            default:
                break;
        }
    }

    private void clearGlyphStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.strikethroughStyle = false;
        this.underlineStyle = false;
        this.italicStyle = false;
    }

    private void drawFormattedGlyph(char glyph, boolean shadow) {
        int glyphIndex = DEFAULT_GLYPHS.indexOf(glyph);
        if (this.randomStyle && glyphIndex != -1) {
            int replacement;
            do {
                replacement = this.fontRandom.nextInt(this.charWidth.length);
            } while ((int) this.charWidth[glyphIndex] != (int) this.charWidth[replacement]);
            glyphIndex = replacement;
        }

        float offset = glyphIndex != -1 && !this.unicodeFlag ? this.boldGlyphOffset : 0.5F;
        boolean shiftShadow = shadow && (glyph == 0 || glyphIndex == -1 || this.unicodeFlag);
        float advance = this.drawGlyphWithShadowOffset(glyphIndex, glyph, offset, shiftShadow);
        if (this.boldStyle) {
            this.penX += offset;
            if (shiftShadow) {
                this.penX -= offset;
                this.penY -= offset;
            }
            this.renderCharAtPos(glyphIndex, glyph, this.italicStyle);
            this.penX -= offset;
            if (shiftShadow) {
                this.penX += offset;
                this.penY += offset;
            }
            advance += offset;
        }

        if (this.strikethroughStyle) {
            this.drawGlyphRule(this.penX, this.penX + advance, this.penY + this.FONT_HEIGHT / 2);
        }
        if (this.underlineStyle) {
            this.drawGlyphRule(this.penX - 1.0F, this.penX + advance, this.penY + this.FONT_HEIGHT);
        }
        this.penX += advance;
    }

    private float drawGlyphWithShadowOffset(
            int glyphIndex, char glyph, float offset, boolean shiftShadow) {
        if (shiftShadow) {
            this.penX -= offset;
            this.penY -= offset;
        }
        float advance = this.renderCharAtPos(glyphIndex, glyph, this.italicStyle);
        if (shiftShadow) {
            this.penX += offset;
            this.penY += offset;
        }
        return advance;
    }

    private void drawGlyphRule(float left, float right, float bottom) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertices = tessellator.getWorldRenderer();
        GlStateManager.disableTexture2D();
        vertices.startDrawingQuads();
        vertices.addVertex(left, bottom, 0.0);
        vertices.addVertex(right, bottom, 0.0);
        vertices.addVertex(right, bottom - 1.0F, 0.0);
        vertices.addVertex(left, bottom - 1.0F, 0.0);
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    public static String getFormatFromString(String text) {
        String active = packedTextFragments[0];
        boolean legacyFirstFormatOnly = Gui.getLegacyGuiRenderMarker() != null;
        int index = -1;
        while ((index = text.indexOf('\u00a7', index + 1)) != -1) {
            if (index >= text.length() - 1) {
                continue;
            }
            char code = text.charAt(index + 1);
            boolean colorCode = isFormatColor(code);
            if (colorCode) {
                active = packedTextFragments[1] + code;
            }
            if ((!colorCode || legacyFirstFormatOnly) && isFormatSpecial(code)) {
                active += packedTextFragments[2] + code;
            }
            if (legacyFirstFormatOnly) {
                break;
            }
        }
        return active;
    }

    private void readGlyphSizes() {
        InputStream inputstream = null;

        try {
            inputstream = this.getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
            inputstream.read(this.glyphWidth);
        } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        } finally {
            IOUtils.closeQuietly(inputstream);
        }
    }

    // region String decoding support

    // endregion

    public int getColorCode(char c0) {
        int i = "0123456789abcdef".indexOf(c0);
        if (i >= 0 && i < this.colorCode.length) {
            int j = this.colorCode[i];
            if (OptiFineConfig.isCustomColors()) {
                j = CustomColors.getCustomTextColor(i, j);
            }

            return j;
        } else {
            return 16777215;
        }
    }

    private String bidiReorder(String text) {
        try {
            com.ibm.icu.text.Bidi bidi =
                    new com.ibm.icu.text.Bidi(
                            new com.ibm.icu.text.ArabicShaping(8).shape(text), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (com.ibm.icu.text.ArabicShapingException arabicshapingexception) {
            return text;
        }
    }

    public int splitStringWidth(String text, int p171_1) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(text, p171_1).size();
    }

    private int sizeStringToWidth(String text, int maxWidth) {
        int length = text.length();
        boolean legacyFlow = Gui.getLegacyGuiRenderMarker() != null;
        float width = 0.0F;
        int index = 0;
        int lastBreak = -1;
        boolean bold = false;

        while (index < length) {
            char glyph = text.charAt(index);
            if (glyph == '\n' && !legacyFlow) {
                return index;
            }

            boolean measureGlyph = true;
            if (glyph == '\n' || glyph == '§') {
                if (glyph == '\n') {
                    index--;
                }
                if (index >= length - 1) {
                    measureGlyph = false;
                } else {
                    char format = text.charAt(++index);
                    if (format == 'l' || format == 'L') {
                        bold = true;
                    } else if (format == 'r' || format == 'R' || isFormatColor(format)) {
                        bold = legacyFlow;
                    }
                    measureGlyph = legacyFlow;
                    if (legacyFlow) {
                        lastBreak = index;
                    }
                }
            } else if (glyph == ' ') {
                lastBreak = index;
            }
            if (measureGlyph) {
                width += this.getCharWidthFloat(glyph);
                if (bold) {
                    width++;
                }
            }

            if (glyph == '\n') {
                lastBreak = ++index;
            }
            if (width > maxWidth && !legacyFlow) {
                break;
            }
            index++;
            if (legacyFlow) {
                break;
            }
        }
        return index != length && lastBreak >= 0 && lastBreak < index ? lastBreak : index;
    }

    public int getStringWidth(String text) {
        boolean legacyFirstGlyphOnly = Gui.getLegacyGuiRenderMarker() != null;
        if (text == null) {
            return 0;
        }

        float width = 0.0F;
        boolean bold = false;
        for (int index = 0; index < text.length(); index++) {
            float glyphWidth = this.getCharWidthFloat(text.charAt(index));
            if (glyphWidth < 0.0F && index < text.length() - 1) {
                char code = text.charAt(++index);
                if (code == 'l' || code == 'L') {
                    bold = true;
                } else if (code == 'r' || code == 'R') {
                    bold = legacyFirstGlyphOnly;
                }
                glyphWidth = 0.0F;
            }

            width += glyphWidth;
            if (bold && glyphWidth > 0.0F) {
                width += this.unicodeFlag ? 1.0F : this.boldGlyphOffset;
            }
            if (legacyFirstGlyphOnly) {
                break;
            }
        }
        return (int) width;
    }

    protected void bindTexture(ResourceLocation resourceLocation) {
        this.renderEngine.bindTexture(resourceLocation);
    }

    public void setBidiFlag(boolean bidiFlagValue) {
        this.bidiFlag = bidiFlagValue;
    }

    protected void setColor(float p171_0, float p171_1, float p171_2, float p171_3) {
        net.minecraft.client.renderer.GlStateManager.color(p171_0, p171_1, p171_2, p171_3);
    }

    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.resolvedFontTexture = FontUtils.getHdFontLocation(this.baseFontTexture);
        int i = 0;
        String[] astring = astring1;

        while (i < net.minecraft.client.gui.FontRenderer.unicodePageLocations.length) {
            net.minecraft.client.gui.FontRenderer.unicodePageLocations[i] = null;
            i++;
            if (astring != null) {
                break;
            }
        }

        this.readFontTexture();
        this.readGlyphSizes();
    }

    private ResourceLocation getUnicodePageLocation(int i) {
        if (net.minecraft.client.gui.FontRenderer.unicodePageLocations[i] == null) {
            net.minecraft.client.gui.FontRenderer.unicodePageLocations[i] =
                    new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", i));
            net.minecraft.client.gui.FontRenderer.unicodePageLocations[i] =
                    FontUtils.getHdFontLocation(
                            net.minecraft.client.gui.FontRenderer.unicodePageLocations[i]);
        }

        return net.minecraft.client.gui.FontRenderer.unicodePageLocations[i];
    }

    protected void enableAlpha() {
        GlStateManager.enableAlpha();
    }

    public FontRenderer(
            GameSettings gameSettings,
            ResourceLocation resourceLocation,
            TextureManager textureManager,
            boolean flag) {
        super();
        String[] initialFlowMarker = Gui.getLegacyGuiRenderMarker();

        String[] astring1 = initialFlowMarker;

        this.FONT_HEIGHT = 9;
        this.fontRandom = new Random();
        this.enabled = true;
        this.boldGlyphOffset = 1.0F;
        String[] astring = astring1;
        this.charWidth = new float[256];
        this.glyphWidth = new byte[65536];
        this.colorCode = new int[32];
        this.gameSettings = gameSettings;
        this.baseFontTexture = resourceLocation;
        this.resolvedFontTexture = resourceLocation;
        this.renderEngine = textureManager;
        this.unicodeFlag = flag;
        this.resolvedFontTexture = FontUtils.getHdFontLocation(this.baseFontTexture);
        this.bindTexture(this.resolvedFontTexture);
        int i = 0;

        while (i < 32) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i >> 0 & 1) * 170 + j;
            if (i == 6) {
                k += 85;
            }

            if (gameSettings.anaglyph) {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
            i++;
            if (astring != null) {
                break;
            }
        }

        this.readGlyphSizes();
    }

    private float renderUnicodeChar(char p171_0, boolean p171_1) {
        try {
            if (this.glyphWidth[p171_0] == 0) {
                return 0.0F;
            }
        } catch (java.lang.RuntimeException runtimeexception1) {
            throw (runtimeexception1);
        }

        int i = p171_0 / 256;
        this.bindUnicodePage(i);
        int j = this.glyphWidth[p171_0] >>> 4;
        int k = this.glyphWidth[p171_0] & 15;
        j &= 15;
        float f = j;
        float f1 = k + 1;
        float f2 = p171_0 % 16 * 16 + f;
        float f3 = (p171_0 & 255) / 16 * 16;
        float f4 = f1 - f - 0.02F;

        float f5 = p171_1 ? 1.0F : 0.0F;
        org.lwjgl.opengl.GL11.glBegin(5);
        org.lwjgl.opengl.GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
        org.lwjgl.opengl.GL11.glVertex3f(this.penX + f5, this.penY, 0.0F);
        org.lwjgl.opengl.GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
        org.lwjgl.opengl.GL11.glVertex3f(this.penX - f5, this.penY + 7.99F, 0.0F);
        org.lwjgl.opengl.GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
        org.lwjgl.opengl.GL11.glVertex3f(this.penX + f4 / 2.0F + f5, this.penY, 0.0F);
        org.lwjgl.opengl.GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
        org.lwjgl.opengl.GL11.glVertex3f(this.penX + f4 / 2.0F - f5, this.penY + 7.99F, 0.0F);
        org.lwjgl.opengl.GL11.glEnd();
        return (f1 - f) / 2.0F + 1.0F;
    }

    public int drawString(String text, int p171_1, int p171_2, int p171_3) {
        try {
            if (!this.enabled) {
                return 0;
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw (runtimeexception);
        }

        return this.drawString(text, p171_1, p171_2, p171_3, false);
    }
}
