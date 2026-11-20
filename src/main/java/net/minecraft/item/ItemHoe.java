package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemHoe extends Item {
    protected ItemToolMaterial theToolMaterial;

    public ItemHoe(ItemToolMaterial buNValue) {
        this.theToolMaterial = buNValue;
        this.maxStackSize = 1;
        this.setMaxDamage(buNValue.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public boolean isFull3D() {
        return true;
    }

    protected boolean useHoe(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            BlockState blockState) {
        world.playSoundEffect(
                blockPos.getX() + 0.5F,
                blockPos.getY() + 0.5F,
                blockPos.getZ() + 0.5F,
                blockState.getBlock().stepSound.getStepSound(),
                (blockState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F,
                blockState.getBlock().stepSound.getFrequency() * 0.8F);
        if (world.isRemote) {
            return true;
        }

        world.setBlockState(blockPos, blockState);
        itemStack.damageItem(1, playerEntity);
        return true;
    }

    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (!playerEntity.canPlayerEdit(blockPos.offset(facing), facing, itemStack)) {
            return false;
        }

        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (facing != Facing.DOWN
                && world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial()
                        == Material.AIR) {
            if (block == Blocks.grass) {
                return this.useHoe(
                        itemStack,
                        playerEntity,
                        world,
                        blockPos,
                        Blocks.farmland.getDefaultState());
            }

            if (block == Blocks.dirt) {
                switch (((BlockDirtDirtType) blockState.getValue(BlockDirt.VARIANT))) {
                    case DIRT:
                        return this.useHoe(
                                itemStack,
                                playerEntity,
                                world,
                                blockPos,
                                Blocks.farmland.getDefaultState());
                    case COARSE_DIRT:
                        return this.useHoe(
                                itemStack,
                                playerEntity,
                                world,
                                blockPos,
                                Blocks.dirt
                                        .getDefaultState()
                                        .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.DIRT));
                }
            }
        }

        return false;
    }
}
