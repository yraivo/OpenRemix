package net.minecraft.dispenser;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.world.World;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem {
    private static Entity[] b;

    private int func_82488_a(Facing facing) {
        return facing.getFrontOffsetX() + 1 + (facing.getFrontOffsetZ() + 1) * 3;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    static {
        if (method_da2ac5c94686ee11e9e6() != null) {
            b(new Entity[5]);
        }
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public final ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
        net.minecraft.item.ItemStack itemStackValue = this.dispenseStack(iBlockSource, itemStack);
        this.playDispenseSound(iBlockSource);
        this.spawnDispenseParticles(
                iBlockSource, net.minecraft.block.BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
        return itemStackValue;
    }

    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        net.minecraft.util.Facing facing = net.minecraft.block.BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
        net.minecraft.dispenser.IPosition dispensePosition = net.minecraft.block.BlockDispenser.getDispensePosition(iBlockSource);
        net.minecraft.item.ItemStack itemStackValue = itemStack.splitStack(1);
        net.minecraft.dispenser.BehaviorDefaultDispenseItem.doDispense(
                iBlockSource.getWorld(), itemStackValue, 6, facing, dispensePosition);
        return itemStack;
    }

    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
    }

    protected void spawnDispenseParticles(IBlockSource iBlockSource, Facing facing) {
        iBlockSource
                .getWorld()
                .playAuxSFX(2000, iBlockSource.getBlockPos(), this.func_82488_a(facing));
    }

    public static void doDispense(
            World world, ItemStack itemStack, int i, Facing facing, IPosition iPosition) {
        double d0;
        double d1;
        double d2;
        label13:
        {
            d0 = iPosition.getX();
            Entity[] entityArray = method_da2ac5c94686ee11e9e6();
            d1 = iPosition.getY();
            d2 = iPosition.getZ();
            Entity[] entityArrayValue = entityArray;
            if (facing.getAxis() == FacingAxis.Y) {
                d1 -= 0.125;
                if (entityArrayValue == null) {
                    break label13;
                }

                Entity.setLegacyGlobalFlowMarker(new String[5]);
            }

            d1 -= 0.15625;
        }

        EntityItem entityItem = new EntityItem(world, d0, d1, d2, itemStack);
        double d3 = world.rand.nextDouble() * 0.1 + 0.2;
        entityItem.motionX = facing.getFrontOffsetX() * d3;
        entityItem.motionY = 0.2F;
        entityItem.motionZ = facing.getFrontOffsetZ() * d3;
        entityItem.motionX = entityItem.motionX + world.rand.nextGaussian() * 0.0075F * i;
        entityItem.motionY = entityItem.motionY + world.rand.nextGaussian() * 0.0075F * i;
        entityItem.motionZ = entityItem.motionZ + world.rand.nextGaussian() * 0.0075F * i;
        world.spawnEntityInWorld(entityItem);
    }
}
