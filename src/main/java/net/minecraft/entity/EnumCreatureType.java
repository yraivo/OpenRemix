package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityWaterMob;

public enum EnumCreatureType {
    MONSTER("MONSTER", 0, IMob.class, 70, Material.AIR, false, false),
    CREATURE("CREATURE", 1, AnimalEntity.class, 10, Material.AIR, true, true),
    AMBIENT("AMBIENT", 2, EntityAmbientCreature.class, 15, Material.AIR, true, false),
    WATER_CREATURE("WATER_CREATURE", 3, EntityWaterMob.class, 5, Material.water, true, false);

    private static EnumCreatureType[] aff;

    private Material creatureMaterial;
    private int maxNumberOfCreature;
    private Class creatureClass;
    private boolean d;
    private boolean e;

    // region String decoding support

    // endregion

    public Class getCreatureClass() {
        return this.creatureClass;
    }

    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }

    EnumCreatureType(
            String text,
            int k,
            Class oclass,
            int j,
            Material afeValue,
            boolean flag,
            boolean flag1) {
        this.creatureClass = oclass;
        this.maxNumberOfCreature = j;
        this.creatureMaterial = afeValue;
        this.d = flag;
        this.e = flag1;
    }

    // region String decoding support

    // endregion

    static {
        aff =
                new EnumCreatureType[] {
                    net.minecraft.entity.EnumCreatureType.MONSTER,
                    net.minecraft.entity.EnumCreatureType.CREATURE,
                    net.minecraft.entity.EnumCreatureType.AMBIENT,
                    net.minecraft.entity.EnumCreatureType.WATER_CREATURE
                };
    }

    public boolean method_d87039b07bd6961907f2() {
        return this.d;
    }

    public boolean d() {
        return this.e;
    }
}
