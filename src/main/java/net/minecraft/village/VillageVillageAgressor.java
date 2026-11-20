package net.minecraft.village;

import net.minecraft.entity.LivingEntity;

class VillageVillageAgressor {
    public int agressionTime;
    public LivingEntity agressor;
    Village bGW;

    VillageVillageAgressor(Village bGWValue, LivingEntity aZkValue, int i) {
        this.bGW = bGWValue;
        this.agressor = aZkValue;
        this.agressionTime = i;
    }
}
