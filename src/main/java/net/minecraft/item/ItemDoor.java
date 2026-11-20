package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoorEnumDoorHalf;
import net.minecraft.block.BlockDoorEnumHingePosition;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemDoor extends Item {
    private Block block;

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float p171_5,
            float p171_6,
            float p171_7) {
        if (facing != net.minecraft.util.Facing.UP) {
            return false;
        }

        net.minecraft.block.state.BlockState blockState = world.getBlockState(blockPos);
        net.minecraft.block.Block block = blockState.getBlock();
        if (!block.isReplaceable(world, blockPos)) {
            blockPos = blockPos.offset(facing);
        }

        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)) {
            return false;
        }

        if (!this.block.canPlaceBlockAt(world, blockPos)) {
            return false;
        }

        net.minecraft.item.ItemDoor.placeDoor(
                world, blockPos, net.minecraft.util.Facing.fromAngle(playerEntity.rotationYaw), this.block);
        itemStack.stackSize--;
        return true;
    }

    public static void placeDoor(World world, BlockPos blockPos, Facing facing, Block block) {
        BlockPos blockPosValue = blockPos.offset(facing.rotateY());
        BlockPos blockPosValue2 = blockPos.offset(facing.rotateYCCW());
        int i =
                (world.getBlockState(blockPosValue2).getBlock().isNormalCube() ? 1 : 0)
                        + (world.getBlockState(blockPosValue2.offsetUp()).getBlock().isNormalCube()
                                ? 1
                                : 0);
        int j =
                (world.getBlockState(blockPosValue).getBlock().isNormalCube() ? 1 : 0)
                        + (world.getBlockState(blockPosValue.offsetUp()).getBlock().isNormalCube()
                                ? 1
                                : 0);
        int k =
                world.getBlockState(blockPosValue2).getBlock() != block
                                && world.getBlockState(blockPosValue2.offsetUp()).getBlock()
                                        != block
                        ? 0
                        : 1;
        int l =
                world.getBlockState(blockPosValue).getBlock() != block
                                && world.getBlockState(blockPosValue.offsetUp()).getBlock() != block
                        ? 0
                        : 1;
        byte b0 = 0;
        if (k != 0 && l == 0 || j > i) {
            b0 = 1;
        }

        BlockPos blockPosValue3 = blockPos.offsetUp();
        BlockState blockState =
                block.getDefaultState()
                        .withProperty(BlockDoor.FACING_PROP, facing)
                        .withProperty(
                                BlockDoor.HINGEPOSITION_PROP,
                                b0 != 0
                                        ? BlockDoorEnumHingePosition.RIGHT
                                        : BlockDoorEnumHingePosition.LEFT);
        world.setBlockState(
                blockPos,
                blockState.withProperty(BlockDoor.HALF_PROP, BlockDoorEnumDoorHalf.LOWER),
                2);
        world.setBlockState(
                blockPosValue3,
                blockState.withProperty(BlockDoor.HALF_PROP, BlockDoorEnumDoorHalf.UPPER),
                2);
        world.notifyNeighborsOfStateChange(blockPos, block);
        world.notifyNeighborsOfStateChange(blockPosValue3, block);
    }

    public ItemDoor(Block blWValue) {
        this.block = blWValue;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
}
