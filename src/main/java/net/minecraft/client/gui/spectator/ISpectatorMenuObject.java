package net.minecraft.client.gui.spectator;

import net.minecraft.util.ChatComponent;

public interface ISpectatorMenuObject {
    boolean method_b417d7500ec59d69e373();

    ChatComponent getSpectatorName();

    void method_8b0a960c841ff568c052(float f, int i);

    void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu);
}
