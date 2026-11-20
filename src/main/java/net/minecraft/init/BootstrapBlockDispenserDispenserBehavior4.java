package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public final class BootstrapBlockDispenserDispenserBehavior4 extends BehaviorDefaultDispenseItem {
    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playAuxSFX(1009, iBlockSource.getBlockPos(), 0);
    }

    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        Facing facing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
        int j = Bootstrap.f();
        IPosition dispensePosition = BlockDispenser.getDispensePosition(iBlockSource);
        double d0 = dispensePosition.getX() + facing.getFrontOffsetX() * 0.3F;
        double d1 = dispensePosition.getY() + facing.getFrontOffsetX() * 0.3F;
        int i = j;
        double d2 = dispensePosition.getZ() + facing.getFrontOffsetZ() * 0.3F;
        World world = iBlockSource.getWorld();
        Random random = world.rand;
        double d3 = random.nextGaussian() * 0.05 + facing.getFrontOffsetX();
        double d4 = random.nextGaussian() * 0.05 + facing.getFrontOffsetY();
        double d5 = random.nextGaussian() * 0.05 + facing.getFrontOffsetZ();
        world.spawnEntityInWorld(new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
        itemStack.splitStack(1);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Bootstrap.b(++i);
        }

        return itemStack;
    }
}
