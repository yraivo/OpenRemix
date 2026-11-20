package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.World;

public abstract class EntityGolem extends EntityCreature implements IAnimals {

    protected String getDeathSound() {
        return "none";
    }


    public EntityGolem(World worldValue) {
        super(worldValue);
    }

    protected String getHurtSound() {
        return "none";
    }

    protected boolean canDespawn() {
        return false;
    }

    protected String getLivingSound() {
        return "none";
    }

    public int getTalkInterval() {
        return 120;
    }

    public void fall(float f, float f1) {}
}
