package remix.ui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiSlot;

public class ScrollableGuiSlot extends GuiSlot {
    private static String[] packedTextFragments;
    private static String jR;

    public ScrollableGuiSlot(
            MinecraftClient minecraftClient, int i, int j, int k, int l, int i1) {
        super(minecraftClient, i, j, k, l, i1);
    }

    protected void elementClicked(int i, boolean flag, int j, int k) {}

    public int getSize() {
        return 0;
    }

    public static String getLegacySlotFlowMarker() {
        return jR;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (getLegacySlotFlowMarker() != null) {
            setLegacySlotFlowMarker(packedTextFragments[0]);
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.ui.widget.ScrollableGuiSlot.packedTextFragments[0] = new String("Yukic");
    }

    // endregion

    public static void setLegacySlotFlowMarker(String text) {
        jR = text;
    }

    protected void drawSlot(int i, int j, int k, int l, int i1, int j1) {}

    protected boolean isSelected(int i) {
        return false;
    }

    protected void drawBackground() {}

    // region String decoding support

    // endregion
}
