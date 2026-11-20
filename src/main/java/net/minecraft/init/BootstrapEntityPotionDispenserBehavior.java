package net.minecraft.init;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

class BootstrapEntityPotionDispenserBehavior extends BehaviorProjectileDispense {
    BootstrapItemPotionDispenserBehavior baK;
    ItemStack baL;

    protected float aK() {
        return super.aK() * 0.5F;
    }

    protected IProjectile getProjectileEntity(World world, IPosition iPosition) {
        return new EntityPotion(
                world, iPosition.getX(), iPosition.getY(), iPosition.getZ(), this.baL.copy());
    }

    protected float aN() {
        return super.aN() * 1.25F;
    }

    BootstrapEntityPotionDispenserBehavior(BootstrapItemPotionDispenserBehavior baKValue, ItemStack baLValue) {
        this.baK = baKValue;
        this.baL = baLValue;
    }
}
