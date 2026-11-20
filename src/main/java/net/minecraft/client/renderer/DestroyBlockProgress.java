package net.minecraft.client.renderer;

import net.minecraft.util.BlockPos;

public class DestroyBlockProgress {
    private int partialBlockProgress;
    private BlockPos position;
    private int createdAtCloudUpdateTick;
    private int miningPlayerEntId;

    public void setCloudUpdateTick(int i) {
        this.createdAtCloudUpdateTick = i;
    }

    public void setPartialBlockDamage(int i) {
        if (i > 10) {
            i = 10;
        }

        this.partialBlockProgress = i;
    }

    public int getCreationCloudUpdateTick() {
        return this.createdAtCloudUpdateTick;
    }

    public DestroyBlockProgress(int i, BlockPos blockPos) {
        this.miningPlayerEntId = i;
        this.position = blockPos;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public int getPartialBlockDamage() {
        return this.partialBlockProgress;
    }
}
