package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public final class BootstrapBlockDispenserDispenserBehavior2 extends BehaviorDefaultDispenseItem {
    private final BehaviorDefaultDispenseItem baM = new BehaviorDefaultDispenseItem();

    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        World world;
        double d0;
        double d1;
        double d2;
        double d3;
        {
            int j = Bootstrap.cR();
            Facing facing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
            world = iBlockSource.getWorld();
            d0 = iBlockSource.getX() + facing.getFrontOffsetX() * 1.125F;
            int i = j;
            d1 = iBlockSource.getY() + facing.getFrontOffsetY() * 1.125F;
            d2 = iBlockSource.getZ() + facing.getFrontOffsetZ() * 1.125F;
            BlockPos blockPos = iBlockSource.getBlockPos().offset(facing);
            Material material = world.getBlockState(blockPos).getBlock().getMaterial();
            if (Material.water.equals(material)) {
                d3 = 1.0;
                if (!(i != 0)) {

                    if (!Material.AIR.equals(material)
                            || !Material.water.equals(
                                    world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial())) {
                        return this.baM.dispense(iBlockSource, itemStack);
                    }

                    d3 = 0.0;
                }
            } else {

                if (!Material.AIR.equals(material)
                        || !Material.water.equals(
                                world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial())) {
                    return this.baM.dispense(iBlockSource, itemStack);
                }

                d3 = 0.0;
            }
        }

        EntityBoat entityBoat = new EntityBoat(world, d0, d1 + d3, d2);
        world.spawnEntityInWorld(entityBoat);
        itemStack.splitStack(1);
        return itemStack;
    }

    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
    }
}
