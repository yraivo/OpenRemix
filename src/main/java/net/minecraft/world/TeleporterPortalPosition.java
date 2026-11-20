package net.minecraft.world;

import net.minecraft.util.BlockPos;

public class TeleporterPortalPosition extends BlockPos {
    public long lastUpdateTime;
    Teleporter bGO;

    public TeleporterPortalPosition(
            Teleporter bGOValue, BlockPos blockPos, long bGNValue) {
        super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        this.bGO = bGOValue;
        this.lastUpdateTime = bGNValue;
    }
}
