package net.minecraft.entity.boss;

import net.minecraft.util.ChatComponent;

public interface IBossDisplayData {
    float getMaxHealth();

    ChatComponent getDisplayName();

    float getHealth();
}
