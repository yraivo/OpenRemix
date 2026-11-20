package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class GuiLabel extends Gui {
    private int jM;
    private int rz;
    public int mF;
    public int mu;
    private List tX;
    private int jE;
    private int kZ;
    public boolean visible;
    private FontRenderer fontRenderer;
    protected int Ao;
    private boolean centered;
    public int sR;
    private int nO;
    protected int f = 200;
    private boolean labelBgEnabled;

    public void method_be01d1eb5e587c583ed6(String text) {
        this.tX.add(I18n.format(text, new Object[0]));
    }

    public GuiLabel(
            FontRenderer aoWValue,
            int p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            int p171_6) {
        this.visible = true;
        this.fontRenderer = aoWValue;
        this.mF = p171_1;
        this.sR = p171_2;
        this.mu = p171_3;
        this.f = p171_4;
        this.Ao = p171_5;
        this.tX = com.google.common.collect.Lists.newArrayList();
        this.centered = false;
        this.labelBgEnabled = false;
        this.jM = p171_6;
        this.rz = -1;
        this.kZ = -1;
        this.nO = -1;
        this.jE = 0;
    }

    public GuiLabel setCentered() {
        this.centered = true;
        return this;
    }

    public void drawLabel(MinecraftClient minecraftClient, int p171_1, int p171_2) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (this.visible) {
            net.minecraft.client.renderer.GlStateManager.enableBlend();
            net.minecraft.client.renderer.GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.drawLabelBackground(minecraftClient, p171_1, p171_2);
            int k = this.mu + this.Ao / 2 + this.jE / 2;
            int l = k - this.tX.size() * 10 / 2;
            int i1 = 0;

            while (i1 < this.tX.size()) {
                {
                    if (this.centered) {
                        this.drawCenteredString(
                                this.fontRenderer,
                                (java.lang.String) this.tX.get(i1),
                                this.sR + this.f / 2,
                                l + i1 * 10,
                                this.jM);
                        if (!(astring == null)) {

                            this.drawString(
                                    this.fontRenderer,
                                    (java.lang.String) this.tX.get(i1),
                                    this.sR,
                                    l + i1 * 10,
                                    this.jM);
                        }
                    } else {

                        this.drawString(
                                this.fontRenderer,
                                (java.lang.String) this.tX.get(i1),
                                this.sR,
                                l + i1 * 10,
                                this.jM);
                    }
                }

                i1++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    protected void drawLabelBackground(
            MinecraftClient minecraftClient, int p171_1, int p171_2) {
        if (this.labelBgEnabled) {
            int k = this.f + this.jE * 2;
            int l = this.Ao + this.jE * 2;
            int i1 = this.sR - this.jE;
            int j1 = this.mu - this.jE;
            net.minecraft.client.gui.Gui.drawRect(i1, j1, i1 + k, j1 + l, this.rz);
            this.method_f418b0f77f1897435d21(i1, i1 + k, j1, this.kZ);
            this.method_f418b0f77f1897435d21(i1, i1 + k, j1 + l, this.nO);
            this.method_366505046587ede28c94(i1, j1, j1 + l, this.kZ);
            this.method_366505046587ede28c94(i1 + k, j1, j1 + l, this.nO);
        }
    }
}
