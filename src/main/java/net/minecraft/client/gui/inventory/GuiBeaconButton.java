package net.minecraft.client.gui.inventory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

class GuiBeaconButton extends GuiButton {
    private boolean kX;
    private int nO;
    private int jn;
    private static String[] packedTextFragments;
    private ResourceLocation aoF;

    // region String decoding support

    // endregion

    public boolean method_b417d7500ec59d69e373() {
        return this.kX;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    protected GuiBeaconButton(int i, int j, int k, ResourceLocation aoFValue, int l, int i1) {
        super(i, j, k, 22, 22, packedTextFragments[0]);
        this.aoF = aoFValue;
        this.nO = l;
        this.jn = i1;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.inventory.GuiBeaconButton.packedTextFragments[0] = new String("");
    }

    // endregion

    public void method_5a0449839d28b4a71843(boolean flag) {
        this.kX = flag;
    }

    public void drawButton(MinecraftClient minecraftClient, int i, int j) {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (this.visible) {
            short short1;
            int k;
            label42:
            {
                minecraftClient.getTextureManager().bindTexture(GuiBeacon.access$000());
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (i >= this.xPosition
                                                && j >= this.yPosition
                                                && i < this.xPosition + this.width
                                                && j < this.yPosition + this.height
                                        ? 1
                                        : 0));
                short1 = 219;
                k = 0;
                if (!this.enabled) {
                    k += this.width * 2;
                    if (aint == null) {
                        break label42;
                    }
                }

                if (this.kX) {
                    k += this.width * 1;
                    if (aint == null) {
                        break label42;
                    }
                }

                if (this.hovered) {
                    k += this.width * 3;
                }
            }

            this.drawTexturedModalRect(
                    this.xPosition, this.yPosition, k, short1, this.width, this.height);
            if (!GuiBeacon.access$000().equals(this.aoF)) {
                minecraftClient.getTextureManager().bindTexture(this.aoF);
            }

            this.drawTexturedModalRect(
                    this.xPosition + 2, this.yPosition + 2, this.nO, this.jn, 18, 18);
        }
    }
}
