package net.minecraft.init;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public final class BootstrapEntityArrowDispenserBehavior extends BehaviorProjectileDispense {
    protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
        net.minecraft.entity.projectile.EntityArrow entityArrow =
                new net.minecraft.entity.projectile.EntityArrow(world, iPosition.getX(), iPosition.getY(), iPosition.getZ());
        entityArrow.canBePickedUp = 1;
        return entityArrow;
    }
}
