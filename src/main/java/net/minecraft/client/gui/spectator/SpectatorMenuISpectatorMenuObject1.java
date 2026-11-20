package net.minecraft.client.gui.spectator;

import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;

public final class SpectatorMenuISpectatorMenuObject1 implements ISpectatorMenuObject {
    private static String[] packedTextFragments;

    public void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu) {}

    public void method_8b0a960c841ff568c052(float f, int i) {}

    public ChatComponent getSpectatorName() {
        return new ChatComponentText(packedTextFragments[0]);
    }

    public boolean method_b417d7500ec59d69e373() {
        return false;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.spectator.SpectatorMenuISpectatorMenuObject1.packedTextFragments[0] = new String("");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion
}
