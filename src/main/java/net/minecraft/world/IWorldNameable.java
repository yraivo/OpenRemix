package net.minecraft.world;

import net.minecraft.util.ChatComponent;

public interface IWorldNameable {
    String getName();

    ChatComponent getDisplayName();

    boolean hasCustomName();
}
