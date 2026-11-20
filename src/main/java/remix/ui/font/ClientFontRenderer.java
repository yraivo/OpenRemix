package remix.ui.font;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class ClientFontRenderer extends GlyphAtlasFont {
    private float[] charWidths;
    private final int[] colorCodes = new int[32];
    protected DynamicTexture italicTexture;
    protected DynamicTexture boldItalicTexture;
    private static String[] packedTextFragments;
    protected GlyphMetrics[] italicGlyphs;
    private byte[] glyphWidths;

    protected DynamicTexture boldTexture;
    private final String allowedCharacters = "0123456789abcdefklmnor";
    protected GlyphMetrics[] boldGlyphs = new GlyphMetrics[256];
    protected GlyphMetrics[] boldItalicGlyphs;

    private void initializeColorCodes() {
        for (int i = 0; i < 32; i++) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;
            if (i == 6) {
                k += 85;
            }

            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCodes[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
        }
    }

    public int measureTextWidth(String text) {
        return this.measureAtlasWidth(text);
    }

    private int measureAtlasWidth(String text) {
        boolean legacyFirstCharacter = GlyphAtlasFont.getLegacyFontFlowMarker() != null;
        if (text == null) {
            return 0;
        }

        int width = 0;
        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            if (character == '\u00a7') {
                // The original width pass skips the pair and always uses the regular atlas.
                index++;
                if (!legacyFirstCharacter) {
                    continue;
                }
            }

            if (character < this.glyphs.length) {
                width += this.glyphs[character].width - 8 + this.characterOffset;
            }
            if (legacyFirstCharacter) {
                break;
            }
        }
        return width / 2;
    }

    private void drawTextDecorationLine(
            double p171_0, double p171_1, double p171_2, double p171_3, float p171_4) {
        org.lwjgl.opengl.GL11.glDisable(3553);
        org.lwjgl.opengl.GL11.glLineWidth(p171_4);
        org.lwjgl.opengl.GL11.glBegin(1);
        org.lwjgl.opengl.GL11.glVertex2d(p171_0, p171_1);
        org.lwjgl.opengl.GL11.glVertex2d(p171_2, p171_3);
        org.lwjgl.opengl.GL11.glEnd();
        org.lwjgl.opengl.GL11.glEnable(3553);
    }

    public float drawCenteredString(String text, float p171_1, float p171_2, int p171_3) {
        return this.drawString(text, p171_1 - this.getStringWidth(text) / 2.0F, p171_2, p171_3);
    }

    public void setFractionalMetrics(boolean flag) {
        super.setFractionalMetrics(flag);
        this.rebuildStyleTextures();
    }

    public void setAntiAlias(boolean flag) {
        super.setAntiAlias(flag);
        this.rebuildStyleTextures();
    }

    private float getCharacterWidth(char c0) {
        if (c0 == 167) {
            return -1.0F;
        } else if (c0 == ' ') {
            return this.charWidths[32];
        } else {
            int i =
                    "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
                            .indexOf(c0);
            if (c0 > 0 && i != -1) {
                return this.charWidths[i];
            } else if (this.glyphWidths[c0] != 0) {
                int j = this.glyphWidths[c0] >>> 4;
                int k = this.glyphWidths[c0] & 15;
                j &= 15;
                k++;
                return (k - j) / 2 + 1;
            } else {
                return 0.0F;
            }
        }
    }

    public List<String> wrapTextAtWords(String text, double maxWidth) {
        boolean legacyFirstWord = GlyphAtlasFont.getLegacyFontFlowMarker() != null;
        List<String> lines = new ArrayList<>();
        if (this.getStringWidth(text) > maxWidth) {
            String[] words = text.split(packedTextFragments[0]);
            String line = packedTextFragments[1];
            char lastFormat = '\uffff';
            for (String word : words) {
                for (int index = 0; index < word.length(); index++) {
                    if (word.charAt(index) == '\u00a7' && index < word.length() - 1) {
                        lastFormat = word.charAt(index + 1);
                    }
                    if (legacyFirstWord) {
                        break;
                    }
                }

                boolean wordFits =
                        this.getStringWidth(line + word + packedTextFragments[2]) < maxWidth;
                if (wordFits) {
                    line += word + packedTextFragments[3];
                }
                if (!wordFits || legacyFirstWord) {
                    lines.add(line);
                    line = packedTextFragments[4] + lastFormat + word + packedTextFragments[5];
                }
                if (legacyFirstWord) {
                    break;
                }
            }

            if (!line.isEmpty()) {
                boolean lineFits = this.getStringWidth(line) < maxWidth;
                if (lineFits) {
                    lines.add(packedTextFragments[6] + lastFormat + line + packedTextFragments[7]);
                    line = packedTextFragments[8];
                }
                if (!lineFits || legacyFirstWord) {
                    for (String fragment : this.wrapTextAtCharacters(line, maxWidth)) {
                        lines.add(fragment);
                        if (legacyFirstWord) {
                            break;
                        }
                    }
                }
            }
            if (!legacyFirstWord) {
                return lines;
            }
        }

        lines.add(text);
        return lines;
    }

    static {
        remix.ui.font.ClientFontRenderer.packedTextFragments = new String[12];
        remix.ui.font.ClientFontRenderer.packedTextFragments[0] = new String(" ");
        remix.ui.font.ClientFontRenderer.packedTextFragments[1] = new String("");
        remix.ui.font.ClientFontRenderer.packedTextFragments[2] = new String(" ");
        remix.ui.font.ClientFontRenderer.packedTextFragments[3] = new String(" ");
        remix.ui.font.ClientFontRenderer.packedTextFragments[4] = new String("\u00a7");
        remix.ui.font.ClientFontRenderer.packedTextFragments[5] = new String(" ");
        remix.ui.font.ClientFontRenderer.packedTextFragments[6] = new String("\u00a7");
        remix.ui.font.ClientFontRenderer.packedTextFragments[7] = new String(" ");
        remix.ui.font.ClientFontRenderer.packedTextFragments[8] = new String("");
        remix.ui.font.ClientFontRenderer.packedTextFragments[9] = new String("");
        remix.ui.font.ClientFontRenderer.packedTextFragments[10] = new String("\u00a7");
    }

    private void rebuildStyleTextures() {
        this.boldTexture =
                this.createFontTexture(
                        this.font.deriveFont(1),
                        this.antiAlias,
                        this.fractionalMetrics,
                        this.boldGlyphs);
        this.italicTexture =
                this.createFontTexture(
                        this.font.deriveFont(2),
                        this.antiAlias,
                        this.fractionalMetrics,
                        this.italicGlyphs);
        this.boldItalicTexture =
                this.createFontTexture(
                        this.font.deriveFont(3),
                        this.antiAlias,
                        this.fractionalMetrics,
                        this.boldItalicGlyphs);
    }

    public String trimStringToWidth(String text, int maxWidth, boolean fromEnd) {
        boolean legacyFirstCharacter = GlyphAtlasFont.getLegacyFontFlowMarker() != null;
        StringBuilder trimmed = new StringBuilder();
        float width = 0.0F;
        int start = fromEnd ? text.length() - 1 : 0;
        int step = fromEnd ? -1 : 1;
        boolean afterFormatPrefix = false;
        boolean bold = false;
        for (int index = start; index >= 0 && index < text.length() && width < maxWidth; index += step) {
            char character = text.charAt(index);
            float characterWidth = this.getCharacterWidth(character);
            boolean countWidth = true;
            if (afterFormatPrefix) {
                afterFormatPrefix = false;
                switch (character) {
                    case 'r':
                    case 'R':
                        bold = false;
                        // The legacy path falls through and counts the code as bold text.
                        if (!legacyFirstCharacter) {
                            countWidth = false;
                            break;
                        }
                    case 'l':
                    case 'L':
                        bold = true;
                        countWidth = legacyFirstCharacter;
                        break;
                    default:
                        countWidth = false;
                }
            }

            if (countWidth && characterWidth < 0.0F) {
                afterFormatPrefix = true;
                countWidth = legacyFirstCharacter;
            }
            if (countWidth) {
                width += characterWidth;
                if (bold) {
                    width++;
                }
            }
            if (width > maxWidth && !legacyFirstCharacter) {
                break;
            }

            if (fromEnd) {
                trimmed.insert(0, character);
            }
            if (!fromEnd || legacyFirstCharacter) {
                trimmed.append(character);
            }
            if (legacyFirstCharacter) {
                break;
            }
        }

        String result = trimmed.toString();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            GlyphAtlasFont.setLegacyFontFlowMarker(new Entity[2]);
        }
        return result;
    }

    private static Exception preserveFontException(Exception exception) {
        return exception;
    }

    // region String decoding support

    // endregion

    public float drawString(String text, float f, float f1, int i) {
        return this.drawStringInternal(text, f, f1, i, false);
    }

    public int getStringWidth(String text) {
        return this.measureAtlasWidth(text);
    }

    // region String decoding support

    // endregion

    public float drawStringInternal(String text, double x, double y, int color, boolean shadow) {
        boolean legacyFirstCharacter = GlyphAtlasFont.getLegacyFontFlowMarker() != null;
        x--;
        if (text == null) {
            return 0.0F;
        }
        if (color == 553648127) {
            color = 16777215;
        }
        if ((color & -67108864) == 0) {
            color |= -16777216;
        }
        if (shadow) {
            color = (color & 16579836) >> 2 | color & 0xFF000000;
        }

        GlyphMetrics[] activeGlyphs = this.glyphs;
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        x *= 2.0;
        y = (y - 3.0) * 2.0;

        GL11.glPushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        applyTextColor(color, alpha);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.texture.getGlTextureId());
        GlStateManager.clear(256);
        GL11.glBindTexture(3553, this.texture.getGlTextureId());

        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            if (character == '\u00a7') {
                int format = 21;
                try {
                    format = this.allowedCharacters.indexOf(text.charAt(index + 1));
                } catch (Exception failure) {
                    failure.printStackTrace();
                }

                if (format < 16) {
                    bold = italic = strikethrough = underline = false;
                    GlStateManager.bindTexture(this.texture.getGlTextureId());
                    activeGlyphs = this.glyphs;
                    int paletteIndex = (format < 0 ? 15 : format) + (shadow ? 16 : 0);
                    applyTextColor(this.colorCodes[paletteIndex], alpha);
                } else {
                    switch (format) {
                        case 17:
                            bold = true;
                            if (italic) {
                                GlStateManager.bindTexture(this.boldItalicTexture.getGlTextureId());
                                activeGlyphs = this.boldItalicGlyphs;
                            }
                            if (!italic || legacyFirstCharacter) {
                                GlStateManager.bindTexture(this.boldTexture.getGlTextureId());
                                activeGlyphs = this.boldGlyphs;
                            }
                            break;
                        case 18:
                            strikethrough = true;
                            break;
                        case 19:
                            underline = true;
                            break;
                        case 20:
                            italic = true;
                            if (bold) {
                                GlStateManager.bindTexture(this.boldItalicTexture.getGlTextureId());
                                activeGlyphs = this.boldItalicGlyphs;
                            }
                            if (!bold || legacyFirstCharacter) {
                                GlStateManager.bindTexture(this.italicTexture.getGlTextureId());
                                activeGlyphs = this.italicGlyphs;
                            }
                            break;
                        case 21:
                            bold = italic = strikethrough = underline = false;
                            applyTextColor(color, alpha);
                            GlStateManager.bindTexture(this.texture.getGlTextureId());
                            activeGlyphs = this.glyphs;
                            break;
                        default:
                            // Obfuscated-text formatting (16) was never drawn specially here.
                            break;
                    }
                }

                index++;
                if (!legacyFirstCharacter) {
                    continue;
                }
            }

            if (character < activeGlyphs.length) {
                GL11.glBegin(4);
                this.drawGlyph(activeGlyphs, character, (float) x, (float) y);
                GL11.glEnd();
                GlyphMetrics glyph = activeGlyphs[character];
                if (strikethrough) {
                    double lineY = y + glyph.height / 2;
                    this.drawTextDecorationLine(x, lineY, x + glyph.width - 8.0, lineY, 1.0F);
                }
                if (underline) {
                    double lineY = y + glyph.height - 2.0;
                    this.drawTextDecorationLine(x, lineY, x + glyph.width - 8.0, lineY, 1.0F);
                }
                x += glyph.width - 8 + this.characterOffset;
            }
            if (legacyFirstCharacter) {
                break;
            }
        }

        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
        GL11.glPopMatrix();
        return (float) x / 2.0F;
    }

    private static void applyTextColor(int color, float alpha) {
        GlStateManager.color(
                (color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F,
                (color & 0xFF) / 255.0F, alpha);
    }

    public List<String> wrapTextAtCharacters(String text, double maxWidth) {
        boolean legacyFirstCharacter = GlyphAtlasFont.getLegacyFontFlowMarker() != null;
        List<String> lines = new ArrayList<>();
        String line = packedTextFragments[9];
        char lastFormat = '\uffff';
        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            if (character == '\u00a7' && index < text.length() - 1) {
                lastFormat = text.charAt(index + 1);
            }

            boolean characterFits = this.getStringWidth(line + character) < maxWidth;
            if (characterFits) {
                line += character;
            }
            if (!characterFits || legacyFirstCharacter) {
                lines.add(line);
                line = packedTextFragments[10] + lastFormat + character;
            }
            if (legacyFirstCharacter) {
                break;
            }
        }
        if (!line.isEmpty()) {
            lines.add(line);
        }
        return lines;
    }

    public void setFont(Font font) {
        super.setFont(font);
        this.rebuildStyleTextures();
    }

    public float drawStringWithShadow(String text, double p171_1, double p171_2, int p171_3) {
        float f = this.drawStringInternal(text, p171_1 + 0.5, p171_2 + 0.5, p171_3, true);
        return java.lang.Math.max(f, this.drawStringInternal(text, p171_1, p171_2, p171_3, false));
    }

    public int measureTextWidthLegacy(String text) {
        return this.measureAtlasWidth(text);
    }

    public ClientFontRenderer(Font font, boolean flag, boolean flag1) {
        super(font, flag, flag1);
        this.italicGlyphs = new GlyphMetrics[256];
        this.boldItalicGlyphs = new GlyphMetrics[256];
        this.glyphWidths = new byte[65536];
        this.charWidths = new float[256];
        this.initializeColorCodes();
        this.rebuildStyleTextures();
    }

    public String trimStringToWidth(String text, int i) {
        return this.trimStringToWidth(text, i, false);
    }

    public float drawCenteredStringWithShadow(String text, float f, float f1, int i) {
        float f2 =
                this.drawStringInternal(
                        text, f - this.getStringWidth(text) / 2.0 + 0.5, f1 + 0.5, i, true);
        return this.drawString(text, f - this.getStringWidth(text) / 2.0F, f1, i);
    }
}
