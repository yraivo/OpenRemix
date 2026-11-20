package net.minecraft.init;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.world.World;

public final class BootstrapEntitySnowballDispenserBehavior extends BehaviorProjectileDispense {
    protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
        return new EntitySnowball(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
    }
}
