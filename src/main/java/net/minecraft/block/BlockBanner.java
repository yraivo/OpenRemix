package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBanner extends BlockContainer {
    public static PropertyInteger ROTATION_PROP;

    public static PropertyDirection FACING_PROP;

    private static String[] packedTextFragments;

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public void harvestBlock(
            World world,
            PlayerEntity playerEntity,
            BlockPos blockPos,
            BlockState blockState,
            TileEntity tileEntity) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (tileEntity instanceof TileEntityBanner) {
            ItemStack itemStack =
                    new ItemStack(Items.banner, 1, ((TileEntityBanner) tileEntity).getBaseColor());
            CompoundTag compoundTag = new CompoundTag();
            tileEntity.writeToNBT(compoundTag);
            compoundTag.removeTag(packedTextFragments[3]);
            compoundTag.removeTag(packedTextFragments[4]);
            compoundTag.removeTag(packedTextFragments[5]);
            compoundTag.removeTag("id");
            itemStack.setTagInfo("BlockEntityTag", compoundTag);
            spawnAsEntity(world, blockPos, itemStack);
            if (i != 0) {
                return;
            }
        }

        super.harvestBlock(world, playerEntity, blockPos, blockState, (TileEntity) null);
    }

    static {
        net.minecraft.block.BlockBanner.packedTextFragments = new String[8];
        net.minecraft.block.BlockBanner.packedTextFragments[0] = new String("x");
        net.minecraft.block.BlockBanner.packedTextFragments[1] = new String("y");
        net.minecraft.block.BlockBanner.packedTextFragments[2] = new String("z");
        net.minecraft.block.BlockBanner.packedTextFragments[3] = new String("x");
        net.minecraft.block.BlockBanner.packedTextFragments[4] = new String("y");
        net.minecraft.block.BlockBanner.packedTextFragments[5] = new String("z");
        net.minecraft.block.BlockBanner.FACING_PROP = PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
        net.minecraft.block.BlockBanner.ROTATION_PROP =
                PropertyInteger.create("rotation", 0, 15);
    }

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        TileEntity tileEntity = world.getTileEntity(blockPos);
        int j = k;
        if (tileEntity instanceof TileEntityBanner) {
            ItemStack itemStack =
                    new ItemStack(Items.banner, 1, ((TileEntityBanner) tileEntity).getBaseColor());
            CompoundTag compoundTag = new CompoundTag();
            tileEntity.writeToNBT(compoundTag);
            compoundTag.removeTag(packedTextFragments[0]);
            compoundTag.removeTag(packedTextFragments[1]);
            compoundTag.removeTag(packedTextFragments[2]);
            compoundTag.removeTag("id");
            itemStack.setTagInfo("BlockEntityTag", compoundTag);
            spawnAsEntity(world, blockPos, itemStack);
            if (j == 0) {
                return;
            }
        }

        super.dropBlockAsItemWithChance(world, blockPos, blockState, f, i);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.banner;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return true;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityBanner();
    }

    public boolean isFullCube() {
        return false;
    }

    protected BlockBanner() {
        super(Material.wood);
        float f = 0.25F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.banner;
    }
}
