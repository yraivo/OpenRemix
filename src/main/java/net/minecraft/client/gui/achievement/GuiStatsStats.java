package net.minecraft.client.gui.achievement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class GuiStatsStats extends GuiSlot {

    protected List statsHolder;
    GuiStats avn;
    protected int x;
    protected Comparator statSorter;
    public int B;

    private static String[] packedTextFragments;
    public int A;

    protected void method_bc4804773aaae4095cc7(StatBase statBase, int i, int j, boolean flag) {
        ResourceLocation.cB();

        if (statBase != null) {
            String text = statBase.format(GuiStats.access$100(this.avn).writeStat(statBase));
            this.avn.drawString(
                    GuiStats.method_fc02cfeae2f71c669f44(this.avn),
                    text,
                    i - GuiStats.method_59f8adf188812b0ee5f1(this.avn).getStringWidth(text),
                    j + 5,
                    flag ? 16777215 : 9474192);

            return;
        }

        String textValue = packedTextFragments[0];
        this.avn.drawString(
                GuiStats.method_e155aae9999a3100be3b(this.avn),
                textValue,
                i - GuiStats.method_67a8da4dc8a72a6d62b3(this.avn).getStringWidth(textValue),
                j + 5,
                flag ? 16777215 : 9474192);
    }

    protected final int getSize() {
        return this.statsHolder.size();
    }

    static {
        net.minecraft.client.gui.achievement.GuiStatsStats.packedTextFragments = new String[5];
        net.minecraft.client.gui.achievement.GuiStatsStats.packedTextFragments[0] = new String("-");
        net.minecraft.client.gui.achievement.GuiStatsStats.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.achievement.GuiStatsStats.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.achievement.GuiStatsStats.packedTextFragments[3] = new String("");
    }

    protected void drawBackground() {
        this.avn.drawDefaultBackground();
    }

    protected void drawListHeader(int i, int j, Tessellator tessellator) {
        ResourceLocation.cB();

        if (!Mouse.isButtonDown(0)) {
            this.x = -1;
        }

        {
            if (this.x == 0) {
                GuiStats.access$400(this.avn, i + 115 - 18, j + 1, 0, 0);

                
            } else {

                GuiStats.access$400(this.avn, i + 115 - 18, j + 1, 0, 18);
            }
        }

        {
            if (this.x == 1) {
                GuiStats.access$400(this.avn, i + 165 - 18, j + 1, 0, 0);

                
            } else {

                GuiStats.access$400(this.avn, i + 165 - 18, j + 1, 0, 18);
            }
        }

        {
            if (this.x == 2) {
                GuiStats.access$400(this.avn, i + 215 - 18, j + 1, 0, 0);

                
            } else {

                GuiStats.access$400(this.avn, i + 215 - 18, j + 1, 0, 18);
            }
        }

        if (this.A != -1) {
            short short1;
            byte b0;
            {
                short1 = 79;
                b0 = 18;
                if (this.A == 1) {
                    short1 = 129;

                    
                } else {

                    if (this.A == 2) {
                        short1 = 179;
                    }
                }
            }

            if (this.B == 1) {
                b0 = 36;
            }

            GuiStats.access$400(this.avn, i + short1, j + 1, b0, 0);
        }
    }

    protected GuiStatsStats(GuiStats avnValue, MinecraftClient minecraftClient) {
        super(minecraftClient, avnValue.width, avnValue.height, 32, avnValue.height - 64, 20);
        this.avn = avnValue;
        this.x = -1;
        this.A = -1;
        this.setShowSelectionBox(false);
        this.setHasListHeader(true, 20);
    }

    protected void method_98d37317de9aef781741(int i, int j) {
        ResourceLocation.cB();

        if (j >= this.top && j <= this.bottom) {
            int k = this.getSlotIndexFromScreenCoords(i, j);
            int l = this.width / 2 - 92 - 16;
            if (k >= 0) {
                if (i < l + 40 || i > l + 40 + 20) {
                    return;
                }

                StatCrafting statCrafting = this.W(k);
                this.method_8c3ec0158d5af32123bf(statCrafting, i, j);

                return;
            }

            String text;
            {
                text = packedTextFragments[1];
                if (i >= l + 115 - 18 && i <= l + 115) {
                    text = this.S(0);

                    
                } else {

                    if (i >= l + 165 - 18 && i <= l + 165) {
                        text = this.S(1);


                    } else {

                        if (i < l + 215 - 18 || i > l + 215) {
                            return;
                        }

                        text = this.S(2);
                    }
                }
            }

            text = (packedTextFragments[2] + I18n.format(text, new Object[0])).trim();
            if (text.length() > 0) {
                int i1 = i + 12;
                int j1 = j - 12;
                int k1 = GuiStats.method_0ebf035ab3137d8a150c(this.avn).getStringWidth(text);
                GuiStats.method_cbd02ac2e0461207835b(
                        this.avn,
                        i1 - 3,
                        j1 - 3,
                        i1 + k1 + 3,
                        j1 + 8 + 3,
                        -1073741824,
                        -1073741824);
                GuiStats.method_4f4b0d0c4dd7acaa818c(this.avn)
                        .drawStringWithShadow(text, i1, j1, -1);
            }
        }
    }

    protected void method_8c3ec0158d5af32123bf(StatCrafting statCrafting, int i, int j) {
        if (statCrafting != null) {
            Item item = statCrafting.qT();
            ItemStack itemStack = new ItemStack(item);
            String text = itemStack.getUnlocalizedName();
            String textValue =
                    (packedTextFragments[3] + I18n.format(text + ".name", new Object[0])).trim();
            if (textValue.length() > 0) {
                int k = i + 12;
                int l = j - 12;
                int stringWidth =
                        GuiStats.method_0698556f3b5f6b9e7f8e(this.avn).getStringWidth(textValue);
                GuiStats.method_990a434a36afc3fcb2ef(
                        this.avn,
                        k - 3,
                        l - 3,
                        k + stringWidth + 3,
                        l + 8 + 3,
                        -1073741824,
                        -1073741824);
                GuiStats.method_bcc5bf2a4a47a9926a6e(this.avn)
                        .drawStringWithShadow(textValue, k, l, -1);
            }
        }
    }

    protected void method_facbdb7a0205fcce736a(int i) {
        {
            ResourceLocation.cB();

            if (i != this.A) {
                this.A = i;
                this.B = -1;

                
            } else {

                if (this.B == -1) {
                    this.B = 1;


                } else {

                    this.A = -1;
                    this.B = 0;
                }
            }
        }

        Collections.sort(this.statsHolder, this.statSorter);
    }

    protected boolean isSelected(int i) {
        return false;
    }

    protected final StatCrafting W(int i) {
        return (StatCrafting) this.statsHolder.get(i);
    }

    protected void elementClicked(int i, boolean flag, int j, int k) {}

    protected void method_80c3ce64938e63de8c07(int i, int j) {
        {
            ResourceLocation.cB();

            this.x = -1;

            if (i >= 79 && i < 115) {
                this.x = 0;

                
            } else {

                if (i >= 129 && i < 165) {
                    this.x = 1;


                } else {

                    if (i >= 179 && i < 215) {
                        this.x = 2;
                    }
                }
            }
        }

        if (this.x >= 0) {
            this.method_facbdb7a0205fcce736a(this.x);
            this.mc
                    .getSoundHandler()
                    .playSound(
                            PositionedSoundRecord.createPositionedSoundRecord(
                                    new ResourceLocation("gui.button.press"), 1.0F));
        }
    }

    protected abstract String S(int i);
}
