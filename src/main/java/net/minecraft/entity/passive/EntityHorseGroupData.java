package net.minecraft.entity.passive;

import net.minecraft.entity.IEntityLivingData;

public class EntityHorseGroupData implements IEntityLivingData {
    public int horseType;
    public int horseVariant;

    public EntityHorseGroupData(int p171_0, int p171_1) {
        this.horseType = p171_0;
        this.horseVariant = p171_1;
    }
}
