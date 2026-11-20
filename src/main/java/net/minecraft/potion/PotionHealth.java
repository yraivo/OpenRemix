package net.minecraft.potion;

import net.minecraft.util.ResourceLocation;

public class PotionHealth extends Potion {
    public PotionHealth(
            int i, ResourceLocation resourceLocation, boolean flag, int j) {
        super(i, resourceLocation, flag, j);
    }

    public boolean isReady(int p171_0, int p171_1) {
        return p171_0 >= 1;
    }

    public boolean isInstant() {
        return true;
    }
}
