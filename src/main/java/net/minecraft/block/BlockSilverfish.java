package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockSilverfish extends Block {

    public static PropertyEnum VARIANT_PROP;

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockSilverfish.VARIANT_PROP});
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockSilverfish.VARIANT_PROP,
                        BlockSilverfishEnumType.byMetadata(i));
    }

    public BlockSilverfish() {
        super(Material.clay);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockSilverfish.VARIANT_PROP,
                                BlockSilverfishEnumType.STONE));
        this.setHardness(0.0F);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            EntitySilverfish entitySilverfish = new EntitySilverfish(world);
            entitySilverfish.setLocationAndAngles(
                    blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0.0F, 0.0F);
            world.spawnEntityInWorld(entitySilverfish);
            entitySilverfish.spawnExplosionParticle();
        }
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    protected ItemStack createStackedBlock(BlockState blockState) {
        switch (((BlockSilverfishEnumType)
                blockState.getValue(net.minecraft.block.BlockSilverfish.VARIANT_PROP))) {
            case COBBLESTONE:
                return new ItemStack(Blocks.cobblestone);
            case STONEBRICK:
                return new ItemStack(Blocks.stonebrick);
            case MOSSY_STONEBRICK:
                return new ItemStack(
                        Blocks.stonebrick, 1, BlockStoneBrickEnumType.MOSSY.getMetaFromState());
            case CRACKED_STONEBRICK:
                return new ItemStack(
                        Blocks.stonebrick, 1, BlockStoneBrickEnumType.CRACKED.getMetaFromState());
            case CHISELED_STONEBRICK:
                return new ItemStack(
                        Blocks.stonebrick, 1, BlockStoneBrickEnumType.CHISELED.getMetaFromState());
            default:
                return new ItemStack(Blocks.stone);
        }
    }

    public int getDamageValue(World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState);
    }

    public static boolean canContainSilverfish(BlockState blockState) {
        Block block = blockState.getBlock();
        return blockState
                        == Blocks.stone
                                .getDefaultState()
                                .withProperty(BlockStone.VARIANT_PROP, BlockStoneEnumType.STONE)
                || block == Blocks.cobblestone
                || block == Blocks.stonebrick;
    }

    static {
        net.minecraft.block.BlockSilverfish.VARIANT_PROP =
                PropertyEnum.create("variant", BlockSilverfishEnumType.class);
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockSilverfishEnumType)
                        blockState.getValue(net.minecraft.block.BlockSilverfish.VARIANT_PROP))
                .getMetadata();
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockSilverfishEnumType[] blockSilverfishEnumTypeArray = BlockSilverfishEnumType.values();
        int i = j;

        for (BlockSilverfishEnumType blockSilverfishEnumType : blockSilverfishEnumTypeArray) {
            list.add(new ItemStack(item, 1, blockSilverfishEnumType.getMetadata()));
            if (i != 0) {
                break;
            }
        }
    }
}
