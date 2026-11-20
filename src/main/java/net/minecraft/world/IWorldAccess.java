package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;

public interface IWorldAccess {
    void sendBlockBreakProgress(int i, BlockPos blockPos, int j);

    void markBlockForUpdate(BlockPos blockPos);

    void notifyLightSet(BlockPos blockPos);

    void onEntityRemoved(Entity entity);

    void playSoundToNearExcept(
            PlayerEntity playerEntity,
            String text,
            double d0,
            double d1,
            double d2,
            float f,
            float f1);

    void broadcastSound(int i, BlockPos blockPos, int j);

    void playAuxSFX(PlayerEntity playerEntity, int i, BlockPos blockPos, int j);

    void onEntityAdded(Entity entity);

    void playSound(String text, double d0, double d1, double d2, float f, float f1);

    void spawnParticle(
            int i,
            boolean flag,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... j);

    void markBlockRangeForRenderUpdate(int i, int j, int k, int l, int i1, int j1);

    void playRecord(String text, BlockPos blockPos);
}
