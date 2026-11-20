package net.minecraft.world.storage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public interface IPlayerFileData {
    String[] getAvailablePlayerDat();

    void writePlayerData(PlayerEntity playerEntity);

    CompoundTag readPlayerData(PlayerEntity playerEntity);
}
