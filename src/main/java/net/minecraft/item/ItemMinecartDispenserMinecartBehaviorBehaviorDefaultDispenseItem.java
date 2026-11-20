package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBaseEnumRailDirection;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public final class ItemMinecartDispenserMinecartBehaviorBehaviorDefaultDispenseItem extends BehaviorDefaultDispenseItem {
    private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
    }

    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        World world;
        double d0;
        double d1;
        double d2;
        double d3;
        label52:
        {
            Facing facing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
            world = iBlockSource.getWorld();
            String text = ItemStack.getLegacyItemStackFlowMarker();
            d0 = iBlockSource.getX() + facing.getFrontOffsetX() * 1.125;
            d1 = Math.floor(iBlockSource.getY()) + facing.getFrontOffsetY();
            d2 = iBlockSource.getZ() + facing.getFrontOffsetZ() * 1.125;
            BlockPos blockPos = iBlockSource.getBlockPos().offset(facing);
            String textValue = text;
            BlockState blockState = world.getBlockState(blockPos);
            BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection =
                    blockState.getBlock() instanceof BlockRailBase
                            ? (BlockRailBaseEnumRailDirection)
                                    blockState.getValue(
                                            ((BlockRailBase) blockState.getBlock())
                                                    .getShapeProperty())
                            : BlockRailBaseEnumRailDirection.NORTH_SOUTH;
            if (BlockRailBase.isRailBlock(blockState)) {
                if (blockRailBaseEnumRailDirection.isAscending()) {
                    d3 = 0.6;
                    if (textValue == null) {
                        break label52;
                    }
                }

                d3 = 0.1;
                if (textValue == null) {
                    break label52;
                }
            }

            if (blockState.getBlock().getMaterial() != Material.AIR
                    || !BlockRailBase.isRailBlock(world.getBlockState(blockPos.offsetDown()))) {
                return this.behaviourDefaultDispenseItem.dispense(iBlockSource, itemStack);
            }

            BlockState blockStateValue = world.getBlockState(blockPos.offsetDown());
            BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirectionValue =
                    blockStateValue.getBlock() instanceof BlockRailBase
                            ? (BlockRailBaseEnumRailDirection)
                                    blockStateValue.getValue(
                                            ((BlockRailBase) blockStateValue.getBlock())
                                                    .getShapeProperty())
                            : BlockRailBaseEnumRailDirection.NORTH_SOUTH;
            if (facing != Facing.DOWN && blockRailBaseEnumRailDirectionValue.isAscending()) {
                d3 = -0.4;
                if (textValue == null) {
                    break label52;
                }
            }

            d3 = -0.9;
        }

        EntityMinecart entityMinecart =
                EntityMinecart.createMinecart(
                        world,
                        d0,
                        d1 + d3,
                        d2,
                        ItemMinecart.access$000((ItemMinecart) itemStack.getItem()));
        if (itemStack.hasDisplayName()) {
            entityMinecart.setCustomNameTag(itemStack.getDisplayName());
        }

        world.spawnEntityInWorld(entityMinecart);
        itemStack.splitStack(1);
        return itemStack;
    }
}
