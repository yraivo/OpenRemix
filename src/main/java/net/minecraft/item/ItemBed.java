package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBedEnumPartType;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBed extends Item {
    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (world.isRemote) {
            return true;
        }

        if (facing != Facing.UP) {
            return false;
        }

        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        boolean replaceable = block.isReplaceable(world, blockPos);
        if (!replaceable) {
            blockPos = blockPos.offsetUp();
        }

        int i = MathHelper.floor(playerEntity.rotationYaw * 4.0F / 360.0F + 0.5) & 3;
        Facing facingValue = Facing.getHorizontal(i);
        BlockPos blockPosValue = blockPos.offset(facingValue);
        boolean replaceableValue = block.isReplaceable(world, blockPosValue);
        int j = !world.isAirBlock(blockPos) && !replaceable ? 0 : 1;
        int k = !world.isAirBlock(blockPosValue) && !replaceableValue ? 0 : 1;
        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)
                || !playerEntity.canPlayerEdit(blockPosValue, facing, itemStack)) {
            return false;
        }

        if (j != 0
                && k != 0
                && World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                && World.doesBlockHaveSolidTopSurface(world, blockPosValue.offsetDown())) {
            int l = facingValue.getHorizontalIndex();
            BlockState blockStateValue =
                    Blocks.bed
                            .getDefaultState()
                            .withProperty(BlockBed.OCCUPIED_PROP, false)
                            .withProperty(BlockBed.AGE, facingValue)
                            .withProperty(BlockBed.PART_PROP, BlockBedEnumPartType.FOOT);
            if (world.setBlockState(blockPos, blockStateValue, 3)) {
                BlockState blockStateValue2 =
                        blockStateValue.withProperty(BlockBed.PART_PROP, BlockBedEnumPartType.HEAD);
                world.setBlockState(blockPosValue, blockStateValue2, 3);
            }

            itemStack.stackSize--;
            return true;
        } else {
            return false;
        }
    }

    public ItemBed() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
