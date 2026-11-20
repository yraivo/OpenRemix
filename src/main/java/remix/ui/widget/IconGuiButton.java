package remix.ui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import remix.util.render.Render2D3DUtils;

public final class IconGuiButton extends GuiButton {
    private int nO;
    private int kZ;
    private ResourceLocation resourceLocation;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.ui.widget.IconGuiButton.packedTextFragments[0] = new String("");
        remix.ui.widget.IconGuiButton.packedTextFragments[1] = new String("");
    }

    // endregion

    public void drawButton(MinecraftClient minecraftClient, int i, int j) {
        this.hovered =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (i >= this.xPosition - this.width
                                        && j >= this.yPosition - this.height
                                        && i <= this.xPosition + this.width
                                        && j <= this.yPosition + this.height
                                ? 1
                                : 0));
        Render2D3DUtils.drawIcon(
                this.resourceLocation,
                this.xPosition + this.width - this.kZ / 2,
                this.yPosition + this.height - this.nO / 2,
                this.kZ,
                this.nO);
    }

    public IconGuiButton(
            int i, int j, int k, int l, int i1, int j1, int k1, ResourceLocation resourceLocation) {
        super(i, j, k, l, i1, packedTextFragments[1]);
        this.resourceLocation = resourceLocation;
        this.kZ = j1;
        this.nO = k1;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion

    public IconGuiButton(int i, int j, int k, ResourceLocation resourceLocation) {
        super(i, j, k, packedTextFragments[0]);
        this.resourceLocation = resourceLocation;
        this.kZ = 20;
        this.nO = 25;
    }
}
