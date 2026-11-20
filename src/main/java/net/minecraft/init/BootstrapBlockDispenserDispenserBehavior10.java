package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;

public final class BootstrapBlockDispenserDispenserBehavior10 extends BehaviorDefaultDispenseItem {
    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        Facing facing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
        double d0 = iBlockSource.getX() + facing.getFrontOffsetX();
        double d1 = iBlockSource.getBlockPos().getY() + 0.2F;
        double d2 = iBlockSource.getZ() + facing.getFrontOffsetZ();
        Entity entity =
                ItemMonsterPlacer.spawnCreature(
                        iBlockSource.getWorld(), itemStack.getItemDamage(), d0, d1, d2);
        if (entity instanceof LivingEntity && itemStack.hasDisplayName()) {
            ((EntityLiving) entity).setCustomNameTag(itemStack.getDisplayName());
        }

        itemStack.splitStack(1);
        return itemStack;
    }
}
