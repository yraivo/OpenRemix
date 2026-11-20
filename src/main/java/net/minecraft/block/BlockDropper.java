package net.minecraft.block;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockDropper extends BlockDispenser {
    private final IBehaviorDispenseItem dropBehavior = new BehaviorDefaultDispenseItem();

    protected void dispense(World world, BlockPos blockPos) {
        BlockSourceImpl blockSourceImpl =
                new BlockSourceImpl(world, blockPos);
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        TileEntityDispenser blockTileEntity =
                (TileEntityDispenser) blockSourceImpl.getBlockTileEntity();
        int i = k;
        if (blockTileEntity != null) {
            int j = blockTileEntity.getDispenseSlot();
            if (j < 0) {
                world.playAuxSFX(1001, blockPos, 0);
                if (i == 0) {
                    return;
                }
            }

            ItemStack itemStack = blockTileEntity.getStackInSlot(j);
            if (itemStack != null) {
                ItemStack itemStackValue;
                label27:
                {
                    Facing facing =
                            (Facing)
                                    world.getBlockState(blockPos)
                                            .getValue(net.minecraft.block.BlockDispenser.FACING);
                    BlockPos blockPosValue = blockPos.offset(facing);
                    IInventory iInventory =
                            TileEntityHopper.getInventoryAtPosition(
                                    world,
                                    blockPosValue.getX(),
                                    blockPosValue.getY(),
                                    blockPosValue.getZ());
                    if (iInventory == null) {
                        itemStackValue =
                                this.dropBehavior.dispense(blockSourceImpl, itemStack);
                        if (itemStackValue == null || itemStackValue.stackSize != 0) {
                            break label27;
                        }

                        itemStackValue = null;
                        if (i == 0) {
                            break label27;
                        }
                    }

                    itemStackValue =
                            TileEntityHopper.putStackInInventoryAllSlots(
                                    iInventory,
                                    itemStack.copy().splitStack(1),
                                    facing.getOpposite());
                    if (itemStackValue == null) {
                        itemStackValue = itemStack.copy();
                        if (--itemStackValue.stackSize == 0) {
                            itemStackValue = null;
                        }
                    } else {
                        itemStackValue = itemStack.copy();
                    }
                }

                blockTileEntity.setInventorySlotContents(j, itemStackValue);
            }
        }
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityDropper();
    }

    protected IBehaviorDispenseItem getBehavior(ItemStack itemStack) {
        return this.dropBehavior;
    }
}
