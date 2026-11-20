package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals {
    public boolean allowLeashing() {
        return false;
    }

    public EntityAmbientCreature(World worldValue) {
        super(worldValue);
    }

    protected boolean interact(PlayerEntity playerEntity) {
        return false;
    }
}
