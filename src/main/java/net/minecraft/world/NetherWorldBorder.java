package net.minecraft.world;

import net.minecraft.world.border.WorldBorder;

class NetherWorldBorder extends WorldBorder {
    WorldProviderHell bII;

    NetherWorldBorder(WorldProviderHell bIIValue) {
        this.bII = bIIValue;
    }

    public double getCenterZ() {
        return super.getCenterZ() / 8.0;
    }

    public double getCenterX() {
        return super.getCenterX() / 8.0;
    }
}
