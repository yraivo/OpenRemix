package net.minecraft.network;

import net.minecraft.util.ChatComponent;

public interface PacketHandler {
    void onDisconnect(ChatComponent chatComponent);
}
