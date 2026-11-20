package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public final class BootstrapBlockDispenserDispenserBehavior extends BehaviorDefaultDispenseItem {

    private static String c;

    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        World world = iBlockSource.getWorld();
        BlockPos blockPos =
                iBlockSource
                        .getBlockPos()
                        .offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
        EntityTNTPrimed entityTNTPrimed =
                new EntityTNTPrimed(
                        world,
                        blockPos.getX() + 0.5,
                        blockPos.getY(),
                        blockPos.getZ() + 0.5,
                        (LivingEntity) null);
        world.spawnEntityInWorld(entityTNTPrimed);
        world.playSoundAtEntity(entityTNTPrimed, c, 1.0F, 1.0F);
        itemStack.stackSize--;
        return itemStack;
    }

    static {
        c = "game.tnt.primed";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
