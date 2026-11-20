package net.minecraft.init;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.world.World;

public final class BootstrapEntityExpBottleDispenserBehavior extends BehaviorProjectileDispense {
    protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
        return new EntityExpBottle(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
    }

    protected float aN() {
        return super.aN() * 1.25F;
    }

    protected float aK() {
        return super.aK() * 0.5F;
    }
}
