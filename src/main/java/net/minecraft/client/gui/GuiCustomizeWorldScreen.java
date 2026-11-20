package net.minecraft.client.gui;

import com.google.common.base.Predicate;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettingsFactory;

import java.io.IOException;
import java.util.Random;

public class GuiCustomizeWorldScreen extends GuiScreen
        implements GuiSliderFormatHelper, GuiPageButtonListGuiResponder {
    private GuiButton apD;
    private GuiButton apM;

    private static String[] apN;
    private Predicate apO;
    private GuiPageButtonList apP;
    private GuiCreateWorld apA;
    private GuiButton anZ;
    private ChunkProviderSettingsFactory apQ;
    protected String jR = "Customize World Settings";
    protected String[] nB;
    private GuiButton apR;
    private GuiButton anU;
    protected String kg = "Page 1 of 3";
    protected String jU = "Basic Settings";
    private ChunkProviderSettingsFactory apS;
    private int L;
    private GuiButton apT;
    private GuiButton apH;
    private boolean jS;

    private boolean anO;
    private GuiButton apU;
    private Random random;

    private void method_f244da1c65e8123ad041(float p171_0) {
        java.lang.String[] astring1 = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        net.minecraft.client.gui.Gui gui = this.apP.vm();
        java.lang.String[] astring = astring1;
        if (gui instanceof net.minecraft.client.gui.GuiTextField) {
            float f1;
            label36:
            {
                f1 = p171_0;
                if (net.minecraft.client.gui.GuiScreen.isShiftKeyDown()) {
                    f1 = p171_0 * 0.1F;
                    if (!net.minecraft.client.gui.GuiScreen.isCtrlKeyDown()) {
                        break label36;
                    }

                    f1 *= 0.1F;

                    try {
                        if (astring == null) {
                            break label36;
                        }
                    } catch (java.lang.NumberFormatException numberformatexception1) {
                        throw remix.compat.compiler.JvmThrowTypes.unchecked(
                                net.minecraft.client.gui.GuiCustomizeWorldScreen.a(
                                        numberformatexception1));
                    }
                }

                try {
                    if (!net.minecraft.client.gui.GuiScreen.isCtrlKeyDown()) {
                        break label36;
                    }
                } catch (java.lang.NumberFormatException numberformatexception) {
                    throw remix.compat.compiler.JvmThrowTypes.unchecked(
                            net.minecraft.client.gui.GuiCustomizeWorldScreen.a(
                                    numberformatexception));
                }

                f1 = p171_0 * 10.0F;
                if (net.minecraft.client.gui.GuiScreen.isAltKeyDown()) {
                    f1 *= 10.0F;
                }
            }

            net.minecraft.client.gui.GuiTextField guiTextField =
                    (net.minecraft.client.gui.GuiTextField) gui;
            java.lang.Float floatValue =
                    com.google.common.primitives.Floats.tryParse(guiTextField.getText());
            if (floatValue != null) {
                floatValue = floatValue + f1;
                int i = guiTextField.getId();
                java.lang.String text =
                        this.method_fec76e51035ea9395d25(guiTextField.getId(), floatValue);
                guiTextField.setText(text);
                this.func_175319_a(i, text);
            }
        }
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.apP.handleMouseInput();
    }

    public void func_175319_a(int i, String text) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        float f = 0.0F;
        String[] astring = astring1;

        try {
            f = Float.parseFloat(text);
        } catch (NumberFormatException numberformatexception) {
        }

        float f1 = 0.0F;
        switch (i) {
            case 132:
                f1 = this.apQ.mainNoiseScaleX = MathHelper.clamp(f, 1.0F, 5000.0F);
                if (astring == null) {
                    break;
                }
            case 133:
                f1 = this.apQ.mainNoiseScaleY = MathHelper.clamp(f, 1.0F, 5000.0F);
                if (astring == null) {
                    break;
                }
            case 134:
                f1 = this.apQ.mainNoiseScaleZ = MathHelper.clamp(f, 1.0F, 5000.0F);
                if (astring == null) {
                    break;
                }
            case 135:
                f1 = this.apQ.depthNoiseScaleX = MathHelper.clamp(f, 1.0F, 2000.0F);
                if (astring == null) {
                    break;
                }
            case 136:
                f1 = this.apQ.depthNoiseScaleZ = MathHelper.clamp(f, 1.0F, 2000.0F);
                if (astring == null) {
                    break;
                }
            case 137:
                f1 = this.apQ.depthNoiseScaleExponent = MathHelper.clamp(f, 0.01F, 20.0F);
                if (astring == null) {
                    break;
                }
            case 138:
                f1 = this.apQ.baseSize = MathHelper.clamp(f, 1.0F, 25.0F);
                if (astring == null) {
                    break;
                }
            case 139:
                f1 = this.apQ.coordinateScale = MathHelper.clamp(f, 1.0F, 6000.0F);
                if (astring == null) {
                    break;
                }
            case 140:
                f1 = this.apQ.heightScale = MathHelper.clamp(f, 1.0F, 6000.0F);
                if (astring == null) {
                    break;
                }
            case 141:
                f1 = this.apQ.stretchY = MathHelper.clamp(f, 0.01F, 50.0F);
                if (astring == null) {
                    break;
                }
            case 142:
                f1 = this.apQ.upperLimitScale = MathHelper.clamp(f, 1.0F, 5000.0F);
                if (astring == null) {
                    break;
                }
            case 143:
                f1 = this.apQ.lowerLimitScale = MathHelper.clamp(f, 1.0F, 5000.0F);
                if (astring == null) {
                    break;
                }
            case 144:
                f1 = this.apQ.biomeDepthWeight = MathHelper.clamp(f, 1.0F, 20.0F);
                if (astring == null) {
                    break;
                }
            case 145:
                f1 = this.apQ.biomeDepthOffset = MathHelper.clamp(f, 0.0F, 20.0F);
                if (astring == null) {
                    break;
                }
            case 146:
                f1 = this.apQ.biomeScaleWeight = MathHelper.clamp(f, 1.0F, 20.0F);
                if (astring == null) {
                    break;
                }
            case 147:
                f1 = this.apQ.biomeScaleOffset = MathHelper.clamp(f, 0.0F, 20.0F);
        }

        if (f1 != f && f != 0.0F) {
            ((GuiTextField) this.apP.R(i)).setText(this.method_fec76e51035ea9395d25(i, f1));
        }

        ((GuiSlider) this.apP.R(i - 132 + 100)).method_b692b0816d225bcf968a(f1, false);
        if (!this.apQ.equals(this.apS)) {
            this.jS = true;
        }
    }

    private String method_fec76e51035ea9395d25(int i, float f) {
        switch (i) {
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 107:
            case 108:
            case 110:
            case 111:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 139:
            case 140:
            case 142:
            case 143:
                return String.format("%5.3f", f);
            case 105:
            case 106:
            case 109:
            case 112:
            case 113:
            case 114:
            case 115:
            case 137:
            case 138:
            case 141:
            case 144:
            case 145:
            case 146:
            case 147:
                return String.format("%2.3f", f);
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            default:
                return String.format("%d", (int) f);
            case 162:
                if (f < 0.0F) {
                    return I18n.format("gui.all", new Object[0]);
                } else if ((int) f >= BiomeGenBase.hell.biomeID) {
                    BiomeGenBase biomeGenBase = BiomeGenBase.getBiomeGenArray()[(int) f + 2];
                    return biomeGenBase != null ? biomeGenBase.biomeName : apN[18];
                } else {
                    BiomeGenBase biomeGenBaseValue = BiomeGenBase.getBiomeGenArray()[(int) f];
                    return biomeGenBaseValue != null ? biomeGenBaseValue.biomeName : apN[19];
                }
        }
    }

    private void ha() {
        this.apQ.func_177863_a();
        this.method_8d9b536bcdc9991c48fb();
    }

    public void onTick(int p171_0, float biomeScaleOffset) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        switch (p171_0) {
            case 100:
                this.apQ.mainNoiseScaleX = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 101:
                this.apQ.mainNoiseScaleY = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 102:
                this.apQ.mainNoiseScaleZ = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 103:
                this.apQ.depthNoiseScaleX = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 104:
                this.apQ.depthNoiseScaleZ = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 105:
                this.apQ.depthNoiseScaleExponent = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 106:
                this.apQ.baseSize = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 107:
                this.apQ.coordinateScale = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 108:
                this.apQ.heightScale = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 109:
                this.apQ.stretchY = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 110:
                this.apQ.upperLimitScale = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 111:
                this.apQ.lowerLimitScale = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 112:
                this.apQ.biomeDepthWeight = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 113:
                this.apQ.biomeDepthOffset = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 114:
                this.apQ.biomeScaleWeight = biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 115:
                this.apQ.biomeScaleOffset = biomeScaleOffset;
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 161:
            case 188:
            default:
                if (astring == null) {
                    break;
                }
            case 157:
                this.apQ.dungeonChance = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 158:
                this.apQ.waterLakeChance = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 159:
                this.apQ.lavaLakeChance = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 160:
                this.apQ.seaLevel = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 162:
                this.apQ.fixedBiome = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 163:
                this.apQ.biomeSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 164:
                this.apQ.riverSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 165:
                this.apQ.dirtSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 166:
                this.apQ.dirtCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 167:
                this.apQ.dirtMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 168:
                this.apQ.dirtMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 169:
                this.apQ.gravelSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 170:
                this.apQ.gravelCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 171:
                this.apQ.gravelMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 172:
                this.apQ.gravelMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 173:
                this.apQ.graniteSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 174:
                this.apQ.graniteCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 175:
                this.apQ.graniteMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 176:
                this.apQ.graniteMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 177:
                this.apQ.dioriteSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 178:
                this.apQ.dioriteCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 179:
                this.apQ.dioriteMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 180:
                this.apQ.dioriteMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 181:
                this.apQ.andesiteSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 182:
                this.apQ.andesiteCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 183:
                this.apQ.andesiteMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 184:
                this.apQ.andesiteMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 185:
                this.apQ.coalSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 186:
                this.apQ.coalCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 187:
                this.apQ.coalMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 189:
                this.apQ.coalMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 190:
                this.apQ.ironSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 191:
                this.apQ.ironCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 192:
                this.apQ.ironMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 193:
                this.apQ.ironMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 194:
                this.apQ.goldSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 195:
                this.apQ.goldCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 196:
                this.apQ.goldMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 197:
                this.apQ.goldMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 198:
                this.apQ.redstoneSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 199:
                this.apQ.redstoneCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 200:
                this.apQ.redstoneMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 201:
                this.apQ.redstoneMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 202:
                this.apQ.diamondSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 203:
                this.apQ.diamondCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 204:
                this.apQ.diamondMinHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 205:
                this.apQ.diamondMaxHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 206:
                this.apQ.lapisSize = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 207:
                this.apQ.lapisCount = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 208:
                this.apQ.lapisCenterHeight = (int) biomeScaleOffset;
                if (astring == null) {
                    break;
                }
            case 209:
                this.apQ.lapisSpread = (int) biomeScaleOffset;
        }

        if (p171_0 >= 100 && p171_0 < 116) {
            net.minecraft.client.gui.Gui gui = this.apP.R(p171_0 - 100 + 110 - 174 + 154 - 24 + 66);
            if (gui != null) {
                ((net.minecraft.client.gui.GuiTextField) gui)
                        .setText(this.method_fec76e51035ea9395d25(p171_0, biomeScaleOffset));
            }
        }

        if (!this.apQ.equals(this.apS)) {
            this.jS = true;
        }
    }

    protected void mouseClicked(int p171_0, int p171_1, int p171_2) throws IOException {
        super.mouseClicked(p171_0, p171_1, p171_2);
        if (this.L == 0 && !this.anO) {
            this.apP.mouseClicked(p171_0, p171_1, p171_2);
        }
    }

    private void method_5a0449839d28b4a71843(boolean visible) {
        this.apT.visible = visible;
        this.apR.visible = visible;
        this.anU.enabled = remix.compat.compiler.JvmBooleanSlots.fromInt((!visible ? 1 : 0));
        this.anZ.enabled = remix.compat.compiler.JvmBooleanSlots.fromInt((!visible ? 1 : 0));
        this.apM.enabled = remix.compat.compiler.JvmBooleanSlots.fromInt((!visible ? 1 : 0));
        this.apU.enabled = remix.compat.compiler.JvmBooleanSlots.fromInt((!visible ? 1 : 0));
        this.apD.enabled = remix.compat.compiler.JvmBooleanSlots.fromInt((!visible ? 1 : 0));
        this.apH.enabled = remix.compat.compiler.JvmBooleanSlots.fromInt((!visible ? 1 : 0));
    }

    public void method_be01d1eb5e587c583ed6(String text) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (text != null && ((int) (((java.lang.String) (text)).length())) != 0) {
            this.apQ = net.minecraft.world.gen.ChunkProviderSettingsFactory.jsonToFactory(text);
            if (astring == null) {
                return;
            }
        }

        this.apQ = new net.minecraft.world.gen.ChunkProviderSettingsFactory();
    }

    static {
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN = new String[21];
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[0] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[1] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[2] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[3] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[4] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[5] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[6] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[7] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[8] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[9] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[10] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[11] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[12] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[13] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[14] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[15] = new String(":");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[16] = new String("\n");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[17] = new String("");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[18] = new String("?");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[19] = new String("?");
        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[20] =
                new String(
                        "\u00df98n:\u00f2\u008d\u00fb\u00af8\u00b9H\u00ab\u00f0\n"
                            + "`\u009a\u008f8\\A\u0093SE\u0002\u0087%\u0000\u000bwJ(\u00e8\u0001\u00f1\u00a2\u008a\u00d4\u00a2,\u00ec\u00e8^T\u00f6\u00b5\u0014\u0013\u009c%\u00df\u00ebg\u0084\u0093\u00ee\u00a9^^f\u008d\u00d4\u00ca\u00ad1\u009aC\u00a3\u00c7\u0012\u00db\u00bb\u00d3\u00e8\u0098Cl\u00e9\u00dc\u001c\u00c6\u00ef\u00f5\u00ea");
    }

    private void a(int p171_0) {
        this.L = p171_0;
        this.method_5a0449839d28b4a71843(true);
    }

    public void func_175321_a(int p171_0, boolean aop) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        switch (p171_0) {
            case 148:
                this.apQ.useCaves = aop;
                if (astring == null) {
                    break;
                }
            case 149:
                this.apQ.useDungeons = aop;
                if (astring == null) {
                    break;
                }
            case 150:
                this.apQ.useStrongholds = aop;
                if (astring == null) {
                    break;
                }
            case 151:
                this.apQ.useVillages = aop;
                if (astring == null) {
                    break;
                }
            case 152:
                this.apQ.useMineShafts = aop;
                if (astring == null) {
                    break;
                }
            case 153:
                this.apQ.useTemples = aop;
                if (astring == null) {
                    break;
                }
            case 154:
                this.apQ.useRavines = aop;
                if (astring == null) {
                    break;
                }
            case 155:
                this.apQ.useWaterLakes = aop;
                if (astring == null) {
                    break;
                }
            case 156:
                this.apQ.useLavaLakes = aop;
                if (astring == null) {
                    break;
                }
            case 161:
                this.apQ.useLavaOceans = aop;
                if (astring == null) {
                    break;
                }
            case 210:
                this.apQ.useMonuments = aop;
        }

        if (!this.apQ.equals(this.apS)) {
            this.jS = true;
        }
    }

    protected void keyTyped(char p171_0, int p171_1) throws IOException {
        java.lang.String[] astring1 = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        super.keyTyped(p171_0, p171_1);
        java.lang.String[] astring = astring1;
        if (this.L == 0) {
            switch (p171_1) {
                case 200:
                    this.method_f244da1c65e8123ad041(1.0F);
                    if (astring == null) {
                        break;
                    }
                case 208:
                    this.method_f244da1c65e8123ad041(-1.0F);
                    if (astring == null) {
                        break;
                    }
                default:
                    this.apP.method_f66a7ec06282ab1b380e(p171_0, p171_1);
            }
        }
    }

    public String method_c60693f457aa70be7160() {
        return this.apQ
                .toString()
                .replace(
                        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[16],
                        net.minecraft.client.gui.GuiCustomizeWorldScreen.apN[17]);
    }

    public void initGui() {
        this.jR = I18n.format("options.customizeTitle", new Object[0]);
        this.buttonList.clear();
        this.buttonList.add(
                this.apM =
                        new GuiButton(
                                302,
                                20,
                                5,
                                80,
                                20,
                                I18n.format("createWorld.customize.custom.prev", new Object[0])));
        this.buttonList.add(
                this.apU =
                        new GuiButton(
                                303,
                                this.width - 100,
                                5,
                                80,
                                20,
                                I18n.format("createWorld.customize.custom.next", new Object[0])));
        this.buttonList.add(
                this.apD =
                        new GuiButton(
                                304,
                                this.width / 2 - 187,
                                this.height - 27,
                                90,
                                20,
                                I18n.format(
                                        "createWorld.customize.custom.defaults", new Object[0])));
        this.buttonList.add(
                this.anU =
                        new GuiButton(
                                301,
                                this.width / 2 - 92,
                                this.height - 27,
                                90,
                                20,
                                I18n.format(
                                        "createWorld.customize.custom.randomize", new Object[0])));
        this.buttonList.add(
                this.apH =
                        new GuiButton(
                                305,
                                this.width / 2 + 3,
                                this.height - 27,
                                90,
                                20,
                                I18n.format(
                                        "createWorld.customize.custom.presets", new Object[0])));
        this.buttonList.add(
                this.anZ =
                        new GuiButton(
                                300,
                                this.width / 2 + 98,
                                this.height - 27,
                                90,
                                20,
                                I18n.format("gui.done", new Object[0])));
        this.apT =
                new GuiButton(
                        306,
                        this.width / 2 - 55,
                        160,
                        50,
                        20,
                        I18n.format("gui.yes", new Object[0]));
        this.apT.visible = false;
        this.buttonList.add(this.apT);
        this.apR =
                new GuiButton(
                        307, this.width / 2 + 5, 160, 50, 20, I18n.format("gui.no", new Object[0]));
        this.apR.visible = false;
        this.buttonList.add(this.apR);
        this.method_8d9b536bcdc9991c48fb();
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public GuiCustomizeWorldScreen(GuiScreen guiScreen, String text) {
        this.nB = new String[4];
        this.jS = false;
        this.L = 0;
        this.anO = false;
        this.apO = new NonNegativeFloatInputPredicate(this);
        this.apS = new ChunkProviderSettingsFactory();
        this.random = new Random();
        this.apA = (GuiCreateWorld) guiScreen;
        this.method_be01d1eb5e587c583ed6(text);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.apP.drawScreen(i, j, f);
        this.drawCenteredString(this.fontRendererObj, this.jR, this.width / 2, 2, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.kg, this.width / 2, 12, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.jU, this.width / 2, 22, 16777215);
        super.drawScreen(i, j, f);
        if (this.L != 0) {
            drawRect(0.0, 0.0, this.width, this.height, Integer.MIN_VALUE);
            this.method_f418b0f77f1897435d21(
                    this.width / 2 - 91, this.width / 2 + 90, 99, -2039584);
            this.method_f418b0f77f1897435d21(
                    this.width / 2 - 91, this.width / 2 + 90, 185, -6250336);
            this.method_366505046587ede28c94(this.width / 2 - 91, 99, 185, -2039584);
            this.method_366505046587ede28c94(this.width / 2 + 90, 99, 185, -6250336);

            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.Gui.optionsBackground);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            worldRenderer.startDrawingQuads();
            worldRenderer.setOpaqueVertexColorFromRgb(4210752);
            worldRenderer.addVertexWithUV(this.width / 2 - 90, 185.0, 0.0, 0.0, 2.65625);
            worldRenderer.addVertexWithUV(this.width / 2 + 90, 185.0, 0.0, 5.625, 2.65625);
            worldRenderer.addVertexWithUV(this.width / 2 + 90, 100.0, 0.0, 5.625, 0.0);
            worldRenderer.addVertexWithUV(this.width / 2 - 90, 100.0, 0.0, 0.0, 0.0);
            instance.draw();
            this.drawCenteredString(
                    this.fontRendererObj,
                    I18n.format("createWorld.customize.custom.confirmTitle", new Object[0]),
                    this.width / 2,
                    105,
                    16777215);
            this.drawCenteredString(
                    this.fontRendererObj,
                    I18n.format("createWorld.customize.custom.confirm1", new Object[0]),
                    this.width / 2,
                    125,
                    16777215);
            this.drawCenteredString(
                    this.fontRendererObj,
                    I18n.format("createWorld.customize.custom.confirm2", new Object[0]),
                    this.width / 2,
                    135,
                    16777215);
            this.apT.drawButton(this.mc, i, j);
            this.apR.drawButton(this.mc, i, j);
        }
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            switch (guiButton.id) {
                case 300:
                    this.apA.chunkProviderSettingsJson = this.apQ.toString();
                    this.mc.displayGuiScreen(this.apA);
                    if (astring == null) {
                        break;
                    }
                case 301:
                    int i = 0;

                    while (i < this.apP.getSize()) {
                        GuiPageButtonListGuiEntry guiPageButtonListGuiEntry;
                        guiPageButtonListGuiEntry = this.apP.Q(i);
                        Gui gui = guiPageButtonListGuiEntry.vi();
                        if (gui instanceof GuiButton) {
                            GuiButton guiButtonValue = (GuiButton) gui;
                            if (guiButtonValue instanceof GuiSlider) {
                                float f =
                                        ((GuiSlider) guiButtonValue).getSliderPosition()
                                                        * (0.75F + this.random.nextFloat() * 0.5F)
                                                + (this.random.nextFloat() * 0.1F - 0.05F);
                                ((GuiSlider) guiButtonValue)
                                        .method_f244da1c65e8123ad041(
                                                MathHelper.clamp(f, 0.0F, 1.0F));
                                if (!(astring == null)) {

                                    if (guiButtonValue instanceof GuiListButton) {
                                        ((GuiListButton) guiButtonValue)
                                                .method_5a0449839d28b4a71843(
                                                        this.random.nextBoolean());
                                    }
                                }
                            } else {

                                if (guiButtonValue instanceof GuiListButton) {
                                    ((GuiListButton) guiButtonValue)
                                            .method_5a0449839d28b4a71843(this.random.nextBoolean());
                                }
                            }
                        }

                        Gui guiValue = guiPageButtonListGuiEntry.vj();
                        if (guiValue instanceof GuiButton) {
                            GuiButton guiButtonValue2 = (GuiButton) guiValue;
                            if (guiButtonValue2 instanceof GuiSlider) {
                                float f1 =
                                        ((GuiSlider) guiButtonValue2).getSliderPosition()
                                                        * (0.75F + this.random.nextFloat() * 0.5F)
                                                + (this.random.nextFloat() * 0.1F - 0.05F);
                                ((GuiSlider) guiButtonValue2)
                                        .method_f244da1c65e8123ad041(
                                                MathHelper.clamp(f1, 0.0F, 1.0F));
                                if (!(astring == null)) {

                                    if (guiButtonValue2 instanceof GuiListButton) {
                                        ((GuiListButton) guiButtonValue2)
                                                .method_5a0449839d28b4a71843(
                                                        this.random.nextBoolean());
                                    }
                                }
                            } else {

                                if (guiButtonValue2 instanceof GuiListButton) {
                                    ((GuiListButton) guiButtonValue2)
                                            .method_5a0449839d28b4a71843(this.random.nextBoolean());
                                }
                            }
                        }

                        i++;
                        if (astring != null) {
                            break;
                        }
                    }

                    return;
                case 302:
                    this.apP.cS();
                    this.ix();
                    if (astring == null) {
                        break;
                    }
                case 303:
                    this.apP.fF();
                    this.ix();
                    if (astring == null) {
                        break;
                    }
                case 304:
                    if (!this.jS) {
                        break;
                    }

                    this.a(304);
                    if (astring == null) {
                        break;
                    }
                case 305:
                    this.mc.displayGuiScreen(new GuiScreenCustomizePresets(this));
                    if (astring == null) {
                        break;
                    }
                case 306:
                    this.iQ();
                    if (astring == null) {
                        break;
                    }
                case 307:
                    this.L = 0;
                    this.iQ();
            }
        }
    }

    private void iQ() throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        switch (this.L) {
            case 300:
                this.actionPerformed((GuiListButton) this.apP.R(300));
                if (astring == null) {
                    break;
                }
            case 304:
                this.ha();
        }

        this.L = 0;
        this.anO = true;
        this.method_5a0449839d28b4a71843(false);
    }

    protected void mouseReleased(int p171_0, int p171_1, int p171_2) {
        java.lang.String[] astring1 = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        super.mouseReleased(p171_0, p171_1, p171_2);
        java.lang.String[] astring = astring1;
        if (this.anO) {
            this.anO = false;
            if (astring == null) {
                return;
            }
        }

        if (this.L == 0) {
            this.apP.releaseMouseOnEntries(p171_0, p171_1, p171_2);
        }
    }

    private void method_8d9b536bcdc9991c48fb() {
        GuiPageButtonListGuiListEntry[] guiPageButtonListGuiListEntryArray =
                new GuiPageButtonListGuiListEntry[] {
                    new GuiPageButtonListGuiSlideEntry(
                            160,
                            I18n.format("createWorld.customize.custom.seaLevel", new Object[0]),
                            true,
                            this,
                            1.0F,
                            255.0F,
                            this.apQ.seaLevel),
                    new GuiPageButtonListGuiButtonEntry(
                            148,
                            I18n.format("createWorld.customize.custom.useCaves", new Object[0]),
                            true,
                            this.apQ.useCaves),
                    new GuiPageButtonListGuiButtonEntry(
                            150,
                            I18n.format(
                                    "createWorld.customize.custom.useStrongholds", new Object[0]),
                            true,
                            this.apQ.useStrongholds),
                    new GuiPageButtonListGuiButtonEntry(
                            151,
                            I18n.format("createWorld.customize.custom.useVillages", new Object[0]),
                            true,
                            this.apQ.useVillages),
                    new GuiPageButtonListGuiButtonEntry(
                            152,
                            I18n.format(
                                    "createWorld.customize.custom.useMineShafts", new Object[0]),
                            true,
                            this.apQ.useMineShafts),
                    new GuiPageButtonListGuiButtonEntry(
                            153,
                            I18n.format("createWorld.customize.custom.useTemples", new Object[0]),
                            true,
                            this.apQ.useTemples),
                    new GuiPageButtonListGuiButtonEntry(
                            210,
                            I18n.format("createWorld.customize.custom.useMonuments", new Object[0]),
                            true,
                            this.apQ.useMonuments),
                    new GuiPageButtonListGuiButtonEntry(
                            154,
                            I18n.format("createWorld.customize.custom.useRavines", new Object[0]),
                            true,
                            this.apQ.useRavines),
                    new GuiPageButtonListGuiButtonEntry(
                            149,
                            I18n.format("createWorld.customize.custom.useDungeons", new Object[0]),
                            true,
                            this.apQ.useDungeons),
                    new GuiPageButtonListGuiSlideEntry(
                            157,
                            I18n.format(
                                    "createWorld.customize.custom.dungeonChance", new Object[0]),
                            true,
                            this,
                            1.0F,
                            100.0F,
                            this.apQ.dungeonChance),
                    new GuiPageButtonListGuiButtonEntry(
                            155,
                            I18n.format(
                                    "createWorld.customize.custom.useWaterLakes", new Object[0]),
                            true,
                            this.apQ.useWaterLakes),
                    new GuiPageButtonListGuiSlideEntry(
                            158,
                            I18n.format(
                                    "createWorld.customize.custom.waterLakeChance", new Object[0]),
                            true,
                            this,
                            1.0F,
                            100.0F,
                            this.apQ.waterLakeChance),
                    new GuiPageButtonListGuiButtonEntry(
                            156,
                            I18n.format("createWorld.customize.custom.useLavaLakes", new Object[0]),
                            true,
                            this.apQ.useLavaLakes),
                    new GuiPageButtonListGuiSlideEntry(
                            159,
                            I18n.format(
                                    "createWorld.customize.custom.lavaLakeChance", new Object[0]),
                            true,
                            this,
                            10.0F,
                            100.0F,
                            this.apQ.lavaLakeChance),
                    new GuiPageButtonListGuiButtonEntry(
                            161,
                            I18n.format(
                                    "createWorld.customize.custom.useLavaOceans", new Object[0]),
                            true,
                            this.apQ.useLavaOceans),
                    new GuiPageButtonListGuiSlideEntry(
                            162,
                            I18n.format("createWorld.customize.custom.fixedBiome", new Object[0]),
                            true,
                            this,
                            -1.0F,
                            37.0F,
                            this.apQ.fixedBiome),
                    new GuiPageButtonListGuiSlideEntry(
                            163,
                            I18n.format("createWorld.customize.custom.biomeSize", new Object[0]),
                            true,
                            this,
                            1.0F,
                            8.0F,
                            this.apQ.biomeSize),
                    new GuiPageButtonListGuiSlideEntry(
                            164,
                            I18n.format("createWorld.customize.custom.riverSize", new Object[0]),
                            true,
                            this,
                            1.0F,
                            5.0F,
                            this.apQ.riverSize)
                };
        GuiPageButtonListGuiListEntry[] guiPageButtonListGuiListEntryArrayValue =
                new GuiPageButtonListGuiListEntry[] {
                    new GuiPageButtonListGuiLabelEntry(
                            416, I18n.format("tile.dirt.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            165,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.dirtSize),
                    new GuiPageButtonListGuiSlideEntry(
                            166,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.dirtCount),
                    new GuiPageButtonListGuiSlideEntry(
                            167,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.dirtMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            168,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.dirtMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            417, I18n.format("tile.gravel.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            169,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.gravelSize),
                    new GuiPageButtonListGuiSlideEntry(
                            170,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.gravelCount),
                    new GuiPageButtonListGuiSlideEntry(
                            171,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.gravelMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            172,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.gravelMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            418, I18n.format("tile.stone.granite.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            173,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.graniteSize),
                    new GuiPageButtonListGuiSlideEntry(
                            174,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.graniteCount),
                    new GuiPageButtonListGuiSlideEntry(
                            175,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.graniteMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            176,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.graniteMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            419, I18n.format("tile.stone.diorite.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            177,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.dioriteSize),
                    new GuiPageButtonListGuiSlideEntry(
                            178,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.dioriteCount),
                    new GuiPageButtonListGuiSlideEntry(
                            179,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.dioriteMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            180,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.dioriteMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            420, I18n.format("tile.stone.andesite.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            181,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.andesiteSize),
                    new GuiPageButtonListGuiSlideEntry(
                            182,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.andesiteCount),
                    new GuiPageButtonListGuiSlideEntry(
                            183,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.andesiteMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            184,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.andesiteMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            421, I18n.format("tile.oreCoal.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            185,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.coalSize),
                    new GuiPageButtonListGuiSlideEntry(
                            186,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.coalCount),
                    new GuiPageButtonListGuiSlideEntry(
                            187,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.coalMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            189,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.coalMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            422, I18n.format("tile.oreIron.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            190,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.ironSize),
                    new GuiPageButtonListGuiSlideEntry(
                            191,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.ironCount),
                    new GuiPageButtonListGuiSlideEntry(
                            192,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.ironMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            193,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.ironMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            423, I18n.format("tile.oreGold.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            194,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.goldSize),
                    new GuiPageButtonListGuiSlideEntry(
                            195,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.goldCount),
                    new GuiPageButtonListGuiSlideEntry(
                            196,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.goldMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            197,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.goldMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            424, I18n.format("tile.oreRedstone.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            198,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.redstoneSize),
                    new GuiPageButtonListGuiSlideEntry(
                            199,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.redstoneCount),
                    new GuiPageButtonListGuiSlideEntry(
                            200,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.redstoneMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            201,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.redstoneMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            425, I18n.format("tile.oreDiamond.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            202,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.diamondSize),
                    new GuiPageButtonListGuiSlideEntry(
                            203,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.diamondCount),
                    new GuiPageButtonListGuiSlideEntry(
                            204,
                            I18n.format("createWorld.customize.custom.minHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.diamondMinHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            205,
                            I18n.format("createWorld.customize.custom.maxHeight", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.diamondMaxHeight),
                    new GuiPageButtonListGuiLabelEntry(
                            426, I18n.format("tile.oreLapis.name", new Object[0]), false),
                    null,
                    new GuiPageButtonListGuiSlideEntry(
                            206,
                            I18n.format("createWorld.customize.custom.size", new Object[0]),
                            false,
                            this,
                            1.0F,
                            50.0F,
                            this.apQ.lapisSize),
                    new GuiPageButtonListGuiSlideEntry(
                            207,
                            I18n.format("createWorld.customize.custom.count", new Object[0]),
                            false,
                            this,
                            0.0F,
                            40.0F,
                            this.apQ.lapisCount),
                    new GuiPageButtonListGuiSlideEntry(
                            208,
                            I18n.format("createWorld.customize.custom.center", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.lapisCenterHeight),
                    new GuiPageButtonListGuiSlideEntry(
                            209,
                            I18n.format("createWorld.customize.custom.spread", new Object[0]),
                            false,
                            this,
                            0.0F,
                            255.0F,
                            this.apQ.lapisSpread)
                };
        GuiPageButtonListGuiListEntry[] guiPageButtonListGuiListEntryArrayValue2 =
                new GuiPageButtonListGuiListEntry[] {
                    new GuiPageButtonListGuiSlideEntry(
                            100,
                            I18n.format(
                                    "createWorld.customize.custom.mainNoiseScaleX", new Object[0]),
                            false,
                            this,
                            1.0F,
                            5000.0F,
                            this.apQ.mainNoiseScaleX),
                    new GuiPageButtonListGuiSlideEntry(
                            101,
                            I18n.format(
                                    "createWorld.customize.custom.mainNoiseScaleY", new Object[0]),
                            false,
                            this,
                            1.0F,
                            5000.0F,
                            this.apQ.mainNoiseScaleY),
                    new GuiPageButtonListGuiSlideEntry(
                            102,
                            I18n.format(
                                    "createWorld.customize.custom.mainNoiseScaleZ", new Object[0]),
                            false,
                            this,
                            1.0F,
                            5000.0F,
                            this.apQ.mainNoiseScaleZ),
                    new GuiPageButtonListGuiSlideEntry(
                            103,
                            I18n.format(
                                    "createWorld.customize.custom.depthNoiseScaleX", new Object[0]),
                            false,
                            this,
                            1.0F,
                            2000.0F,
                            this.apQ.depthNoiseScaleX),
                    new GuiPageButtonListGuiSlideEntry(
                            104,
                            I18n.format(
                                    "createWorld.customize.custom.depthNoiseScaleZ", new Object[0]),
                            false,
                            this,
                            1.0F,
                            2000.0F,
                            this.apQ.depthNoiseScaleZ),
                    new GuiPageButtonListGuiSlideEntry(
                            105,
                            I18n.format(
                                    "createWorld.customize.custom.depthNoiseScaleExponent",
                                    new Object[0]),
                            false,
                            this,
                            0.01F,
                            20.0F,
                            this.apQ.depthNoiseScaleExponent),
                    new GuiPageButtonListGuiSlideEntry(
                            106,
                            I18n.format("createWorld.customize.custom.baseSize", new Object[0]),
                            false,
                            this,
                            1.0F,
                            25.0F,
                            this.apQ.baseSize),
                    new GuiPageButtonListGuiSlideEntry(
                            107,
                            I18n.format(
                                    "createWorld.customize.custom.coordinateScale", new Object[0]),
                            false,
                            this,
                            1.0F,
                            6000.0F,
                            this.apQ.coordinateScale),
                    new GuiPageButtonListGuiSlideEntry(
                            108,
                            I18n.format("createWorld.customize.custom.heightScale", new Object[0]),
                            false,
                            this,
                            1.0F,
                            6000.0F,
                            this.apQ.heightScale),
                    new GuiPageButtonListGuiSlideEntry(
                            109,
                            I18n.format("createWorld.customize.custom.stretchY", new Object[0]),
                            false,
                            this,
                            0.01F,
                            50.0F,
                            this.apQ.stretchY),
                    new GuiPageButtonListGuiSlideEntry(
                            110,
                            I18n.format(
                                    "createWorld.customize.custom.upperLimitScale", new Object[0]),
                            false,
                            this,
                            1.0F,
                            5000.0F,
                            this.apQ.upperLimitScale),
                    new GuiPageButtonListGuiSlideEntry(
                            111,
                            I18n.format(
                                    "createWorld.customize.custom.lowerLimitScale", new Object[0]),
                            false,
                            this,
                            1.0F,
                            5000.0F,
                            this.apQ.lowerLimitScale),
                    new GuiPageButtonListGuiSlideEntry(
                            112,
                            I18n.format(
                                    "createWorld.customize.custom.biomeDepthWeight", new Object[0]),
                            false,
                            this,
                            1.0F,
                            20.0F,
                            this.apQ.biomeDepthWeight),
                    new GuiPageButtonListGuiSlideEntry(
                            113,
                            I18n.format(
                                    "createWorld.customize.custom.biomeDepthOffset", new Object[0]),
                            false,
                            this,
                            0.0F,
                            20.0F,
                            this.apQ.biomeDepthOffset),
                    new GuiPageButtonListGuiSlideEntry(
                            114,
                            I18n.format(
                                    "createWorld.customize.custom.biomeScaleWeight", new Object[0]),
                            false,
                            this,
                            1.0F,
                            20.0F,
                            this.apQ.biomeScaleWeight),
                    new GuiPageButtonListGuiSlideEntry(
                            115,
                            I18n.format(
                                    "createWorld.customize.custom.biomeScaleOffset", new Object[0]),
                            false,
                            this,
                            0.0F,
                            20.0F,
                            this.apQ.biomeScaleOffset)
                };
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GuiPageButtonListGuiListEntry[] guiPageButtonListGuiListEntryArrayValue3 =
                new GuiPageButtonListGuiListEntry[] {
                    new GuiPageButtonListGuiLabelEntry(
                            400,
                            I18n.format(
                                            "createWorld.customize.custom.mainNoiseScaleX",
                                            new Object[0])
                                    + apN[0],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            132, String.format("%5.3f", this.apQ.mainNoiseScaleX), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            401,
                            I18n.format(
                                            "createWorld.customize.custom.mainNoiseScaleY",
                                            new Object[0])
                                    + apN[1],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            133, String.format("%5.3f", this.apQ.mainNoiseScaleY), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            402,
                            I18n.format(
                                            "createWorld.customize.custom.mainNoiseScaleZ",
                                            new Object[0])
                                    + apN[2],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            134, String.format("%5.3f", this.apQ.mainNoiseScaleZ), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            403,
                            I18n.format(
                                            "createWorld.customize.custom.depthNoiseScaleX",
                                            new Object[0])
                                    + apN[3],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            135,
                            String.format("%5.3f", this.apQ.depthNoiseScaleX),
                            false,
                            this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            404,
                            I18n.format(
                                            "createWorld.customize.custom.depthNoiseScaleZ",
                                            new Object[0])
                                    + apN[4],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            136,
                            String.format("%5.3f", this.apQ.depthNoiseScaleZ),
                            false,
                            this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            405,
                            I18n.format(
                                            "createWorld.customize.custom.depthNoiseScaleExponent",
                                            new Object[0])
                                    + apN[5],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            137,
                            String.format("%2.3f", this.apQ.depthNoiseScaleExponent),
                            false,
                            this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            406,
                            I18n.format("createWorld.customize.custom.baseSize", new Object[0])
                                    + apN[6],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            138, String.format("%2.3f", this.apQ.baseSize), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            407,
                            I18n.format(
                                            "createWorld.customize.custom.coordinateScale",
                                            new Object[0])
                                    + apN[7],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            139, String.format("%5.3f", this.apQ.coordinateScale), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            408,
                            I18n.format("createWorld.customize.custom.heightScale", new Object[0])
                                    + apN[8],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            140, String.format("%5.3f", this.apQ.heightScale), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            409,
                            I18n.format("createWorld.customize.custom.stretchY", new Object[0])
                                    + apN[9],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            141, String.format("%2.3f", this.apQ.stretchY), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            410,
                            I18n.format(
                                            "createWorld.customize.custom.upperLimitScale",
                                            new Object[0])
                                    + apN[10],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            142, String.format("%5.3f", this.apQ.upperLimitScale), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            411,
                            I18n.format(
                                            "createWorld.customize.custom.lowerLimitScale",
                                            new Object[0])
                                    + apN[11],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            143, String.format("%5.3f", this.apQ.lowerLimitScale), false, this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            412,
                            I18n.format(
                                            "createWorld.customize.custom.biomeDepthWeight",
                                            new Object[0])
                                    + apN[12],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            144,
                            String.format("%2.3f", this.apQ.biomeDepthWeight),
                            false,
                            this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            413,
                            I18n.format(
                                            "createWorld.customize.custom.biomeDepthOffset",
                                            new Object[0])
                                    + apN[13],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            145,
                            String.format("%2.3f", this.apQ.biomeDepthOffset),
                            false,
                            this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            414,
                            I18n.format(
                                            "createWorld.customize.custom.biomeScaleWeight",
                                            new Object[0])
                                    + apN[14],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            146,
                            String.format("%2.3f", this.apQ.biomeScaleWeight),
                            false,
                            this.apO),
                    new GuiPageButtonListGuiLabelEntry(
                            415,
                            I18n.format(
                                            "createWorld.customize.custom.biomeScaleOffset",
                                            new Object[0])
                                    + apN[15],
                            false),
                    new GuiPageButtonListEditBoxEntry(
                            147, String.format("%2.3f", this.apQ.biomeScaleOffset), false, this.apO)
                };
        String[] astring = astring1;
        this.apP =
                new GuiPageButtonList(
                        this.mc,
                        this.width,
                        this.height,
                        32,
                        this.height - 32,
                        25,
                        this,
                        new GuiPageButtonListGuiListEntry[][] {
                            guiPageButtonListGuiListEntryArray,
                            guiPageButtonListGuiListEntryArrayValue,
                            guiPageButtonListGuiListEntryArrayValue2,
                            guiPageButtonListGuiListEntryArrayValue3
                        });
        int i = 0;

        while (i < 4) {
            this.nB[i] = I18n.format("createWorld.customize.custom.page" + i, new Object[0]);
            i++;
            if (astring != null) {
                break;
            }
        }

        this.ix();
    }

    public String getText(int i, String text, float f) {
        return text + ": " + this.method_fec76e51035ea9395d25(i, f);
    }

    private void ix() {
        this.apM.enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.apP.method_beea3e16fe3ec0995eb9() != 0 ? 1 : 0));
        this.apU.enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.apP.method_beea3e16fe3ec0995eb9() != this.apP.aW() - 1 ? 1 : 0));
        this.kg =
                I18n.format(
                        "book.pageIndicator",
                        new Object[] {this.apP.method_beea3e16fe3ec0995eb9() + 1, this.apP.aW()});
        this.jU = this.nB[this.apP.method_beea3e16fe3ec0995eb9()];
        this.anU.enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.apP.method_beea3e16fe3ec0995eb9() != this.apP.aW() - 1 ? 1 : 0));
    }

    // Helper used only by this class.
    public static class NonNegativeFloatInputPredicate implements com.google.common.base.Predicate {
        net.minecraft.client.gui.GuiCustomizeWorldScreen asn;

        public boolean a(java.lang.String text) {
            java.lang.Float floatValue = com.google.common.primitives.Floats.tryParse(text);
            return text.length() == 0
                    || floatValue != null
                            && com.google.common.primitives.Floats.isFinite(floatValue)
                            && floatValue >= 0.0F;
        }

        public boolean apply(java.lang.Object object) {
            return this.a((java.lang.String) object);
        }

        public NonNegativeFloatInputPredicate(
                net.minecraft.client.gui.GuiCustomizeWorldScreen asnValue) {
            this.asn = asnValue;
        }
    }
}
