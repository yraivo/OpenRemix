package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

import java.util.List;
import java.util.Random;

public class BlockDoublePlant extends BlockBush implements IGrowable {
    public static PropertyEnum HALF_PROP;

    public static PropertyEnum VARIANT_PROP;

    protected void checkAndDropBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            int i =
                    blockState.getValue(net.minecraft.block.BlockDoublePlant.HALF_PROP)
                                    == BlockDoublePlantEnumBlockHalf.UPPER
                            ? 1
                            : 0;
            BlockPos blockPosValue = i != 0 ? blockPos : blockPos.offsetUp();
            BlockPos blockPosValue2 = i != 0 ? blockPos.offsetDown() : blockPos;
            Object object = i != 0 ? this : world.getBlockState(blockPosValue).getBlock();
            Object object1 = i != 0 ? world.getBlockState(blockPosValue2).getBlock() : this;
            if (object == this) {
                world.setBlockState(blockPosValue, Blocks.air.getDefaultState(), 3);
            }

            if (object1 == this) {
                world.setBlockState(blockPosValue2, Blocks.air.getDefaultState(), 3);
                if (i == 0) {
                    this.dropBlockAsItem(world, blockPosValue2, blockState, 0);
                }
            }
        }
    }

    public void harvestBlock(
            World world,
            PlayerEntity playerEntity,
            BlockPos blockPos,
            BlockState blockState,
            TileEntity tileEntity) {
        if (world.isRemote
                || playerEntity.getCurrentEquippedItem() == null
                || playerEntity.getCurrentEquippedItem().getItem() != Items.shears
                || blockState.getValue(net.minecraft.block.BlockDoublePlant.HALF_PROP)
                        != BlockDoublePlantEnumBlockHalf.LOWER
                || !this.onHarvest(world, blockPos, blockState, playerEntity)) {
            super.harvestBlock(world, playerEntity, blockPos, blockState, tileEntity);
        }
    }

    static {
        net.minecraft.block.BlockDoublePlant.VARIANT_PROP =
                PropertyEnum.create("variant", BlockDoublePlantEnumPlantType.class);
        net.minecraft.block.BlockDoublePlant.HALF_PROP =
                PropertyEnum.create("half", BlockDoublePlantEnumBlockHalf.class);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        if (blockState.getValue(net.minecraft.block.BlockDoublePlant.HALF_PROP)
                == BlockDoublePlantEnumBlockHalf.UPPER) {
            return null;
        }

        BlockDoublePlantEnumPlantType value =
                (BlockDoublePlantEnumPlantType)
                        blockState.getValue(net.minecraft.block.BlockDoublePlant.VARIANT_PROP);
        return value == BlockDoublePlantEnumPlantType.FERN
                ? null
                : (value == BlockDoublePlantEnumPlantType.GRASS
                        ? (random.nextInt(8) == 0 ? Items.wheat_seeds : null)
                        : Item.getItemFromBlock(this));
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        BlockDoublePlantEnumPlantType blockDoublePlantEnumPlantType =
                this.getVariant(iBlockAccess, blockPos);
        return blockDoublePlantEnumPlantType != BlockDoublePlantEnumPlantType.GRASS
                        && blockDoublePlantEnumPlantType != BlockDoublePlantEnumPlantType.FERN
                ? 16777215
                : BiomeColorHelper.getGrassColorAtPos(iBlockAccess, blockPos);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    private boolean onHarvest(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        BlockDoublePlantEnumPlantType value =
                (BlockDoublePlantEnumPlantType)
                        blockState.getValue(net.minecraft.block.BlockDoublePlant.VARIANT_PROP);
        if (value != BlockDoublePlantEnumPlantType.FERN
                && value != BlockDoublePlantEnumPlantType.GRASS) {
            return false;
        }

        playerEntity.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
        int meta =
                (value == BlockDoublePlantEnumPlantType.GRASS
                                ? BlockTallGrassEnumType.GRASS
                                : BlockTallGrassEnumType.FERN)
                        .getMeta();
        spawnAsEntity(world, blockPos, new ItemStack(Blocks.tallgrass, 2, meta));
        return true;
    }

    public boolean canBlockStay(World world, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(net.minecraft.block.BlockDoublePlant.HALF_PROP)
                == BlockDoublePlantEnumBlockHalf.UPPER) {
            return world.getBlockState(blockPos.offsetDown()).getBlock() == this;
        }

        BlockState blockStateValue = world.getBlockState(blockPos.offsetUp());
        return blockStateValue.getBlock() == this
                && super.canBlockStay(world, blockPos, blockStateValue);
    }

    public boolean canUseBonemeal(
            World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public BlockEnumOffsetType getOffsetType() {
        return BlockEnumOffsetType.XZ;
    }

    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        spawnAsEntity(
                world,
                blockPos,
                new ItemStack(this, 1, this.getVariant(world, blockPos).getMeta()));
    }

    public int getDamageValue(World world, BlockPos blockPos) {
        return this.getVariant(world, blockPos).getMeta();
    }

    public BlockDoublePlantEnumPlantType getVariant(IBlockAccess iBlockAccess, BlockPos blockPos) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            blockState = this.getActualState(blockState, iBlockAccess, blockPos);
            return (BlockDoublePlantEnumPlantType)
                    blockState.getValue(net.minecraft.block.BlockDoublePlant.VARIANT_PROP);
        } else {
            return BlockDoublePlantEnumPlantType.FERN;
        }
    }

    public boolean isReplaceable(World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != this) {
            return true;
        }

        BlockDoublePlantEnumPlantType value =
                (BlockDoublePlantEnumPlantType)
                        this.getActualState(blockState, world, blockPos)
                                .getValue(net.minecraft.block.BlockDoublePlant.VARIANT_PROP);
        return value == BlockDoublePlantEnumPlantType.FERN
                || value == BlockDoublePlantEnumPlantType.GRASS;
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockDoublePlantEnumPlantType[] blockDoublePlantEnumPlantTypeArray =
                BlockDoublePlantEnumPlantType.values();
        int j = blockDoublePlantEnumPlantTypeArray.length;
        int i = l;
        int k = 0;

        while (k < j) {
            BlockDoublePlantEnumPlantType blockDoublePlantEnumPlantType =
                    blockDoublePlantEnumPlantTypeArray[k];
            list.add(new ItemStack(item, 1, blockDoublePlantEnumPlantType.getMeta()));
            k++;
            if (i == 0) {
                break;
            }
        }
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        world.setBlockState(
                blockPos.offsetUp(),
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.HALF_PROP,
                                BlockDoublePlantEnumBlockHalf.UPPER),
                2);
    }

    public void onBlockHarvested(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        label48:
        {
            int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
            if (blockState.getValue(net.minecraft.block.BlockDoublePlant.HALF_PROP)
                    == BlockDoublePlantEnumBlockHalf.UPPER) {
                if (world.getBlockState(blockPos.offsetDown()).getBlock() != this) {
                    break label48;
                }

                if (!playerEntity.capabilities.isCreativeMode) {
                    label52:
                    {
                        BlockState blockStateValue = world.getBlockState(blockPos.offsetDown());
                        BlockDoublePlantEnumPlantType value =
                                (BlockDoublePlantEnumPlantType)
                                        blockStateValue.getValue(
                                                net.minecraft.block.BlockDoublePlant.VARIANT_PROP);
                        if (value != BlockDoublePlantEnumPlantType.FERN
                                && value != BlockDoublePlantEnumPlantType.GRASS) {
                            world.destroyBlock(blockPos.offsetDown(), true);
                            if (i != 0) {
                                break label52;
                            }
                        }

                        if (!world.isRemote) {
                            if (playerEntity.getCurrentEquippedItem() != null
                                    && playerEntity.getCurrentEquippedItem().getItem()
                                            == Items.shears) {
                                this.onHarvest(
                                        world, blockPos, blockStateValue, playerEntity);
                                world.setBlockToAir(blockPos.offsetDown());
                                if (i != 0) {
                                    break label52;
                                }
                            }

                            world.destroyBlock(blockPos.offsetDown(), true);
                            if (i != 0) {
                                break label52;
                            }
                        }

                        world.setBlockToAir(blockPos.offsetDown());
                    }

                    if (i != 0) {
                        break label48;
                    }
                }

                world.setBlockToAir(blockPos.offsetDown());
                if (i != 0) {
                    break label48;
                }
            }

            if (playerEntity.capabilities.isCreativeMode
                    && world.getBlockState(blockPos.offsetUp()).getBlock() == this) {
                world.setBlockState(blockPos.offsetUp(), Blocks.air.getDefaultState(), 2);
            }
        }

        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    public BlockState getStateFromMeta(int i) {
        return (i & 8) > 0
                ? this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.HALF_PROP,
                                BlockDoublePlantEnumBlockHalf.UPPER)
                : this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.HALF_PROP,
                                BlockDoublePlantEnumBlockHalf.LOWER)
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.VARIANT_PROP,
                                BlockDoublePlantEnumPlantType.byMetadata(i & 7));
    }

    public int getMetaFromState(BlockState blockState) {
        return blockState.getValue(net.minecraft.block.BlockDoublePlant.HALF_PROP)
                        == BlockDoublePlantEnumBlockHalf.UPPER
                ? 8
                : ((BlockDoublePlantEnumPlantType)
                                blockState.getValue(net.minecraft.block.BlockDoublePlant.VARIANT_PROP))
                        .getMeta();
    }

    public boolean isStillGrowing(
            World world, BlockPos blockPos, BlockState blockState, boolean flag) {
        BlockDoublePlantEnumPlantType blockDoublePlantEnumPlantType =
                this.getVariant(world, blockPos);
        return blockDoublePlantEnumPlantType != BlockDoublePlantEnumPlantType.GRASS
                && blockDoublePlantEnumPlantType != BlockDoublePlantEnumPlantType.FERN;
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && world.isAirBlock(blockPos.offsetUp());
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, net.minecraft.block.BlockDoublePlant.HALF_PROP, net.minecraft.block.BlockDoublePlant.VARIANT_PROP);
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        if (blockState.getValue(net.minecraft.block.BlockDoublePlant.HALF_PROP)
                == BlockDoublePlantEnumBlockHalf.UPPER) {
            BlockState blockStateValue = iBlockAccess.getBlockState(blockPos.offsetDown());
            if (blockStateValue.getBlock() == this) {
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockDoublePlant.VARIANT_PROP,
                                blockStateValue.getValue(net.minecraft.block.BlockDoublePlant.VARIANT_PROP));
            }
        }

        return blockState;
    }

    public int damageDropped(BlockState blockState) {
        return blockState.getValue(net.minecraft.block.BlockDoublePlant.HALF_PROP)
                                != BlockDoublePlantEnumBlockHalf.UPPER
                        && blockState.getValue(net.minecraft.block.BlockDoublePlant.VARIANT_PROP)
                                != BlockDoublePlantEnumPlantType.GRASS
                ? ((BlockDoublePlantEnumPlantType)
                                blockState.getValue(net.minecraft.block.BlockDoublePlant.VARIANT_PROP))
                        .getMeta()
                : 0;
    }

    public void placeAt(
            World world,
            BlockPos blockPos,
            BlockDoublePlantEnumPlantType blockDoublePlantEnumPlantType,
            int i) {
        world.setBlockState(
                blockPos,
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.HALF_PROP,
                                BlockDoublePlantEnumBlockHalf.LOWER)
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.VARIANT_PROP, blockDoublePlantEnumPlantType),
                i);
        world.setBlockState(
                blockPos.offsetUp(),
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.HALF_PROP,
                                BlockDoublePlantEnumBlockHalf.UPPER),
                i);
    }

    public BlockDoublePlant() {
        super(Material.vine);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.VARIANT_PROP,
                                BlockDoublePlantEnumPlantType.SUNFLOWER)
                        .withProperty(
                                net.minecraft.block.BlockDoublePlant.HALF_PROP,
                                BlockDoublePlantEnumBlockHalf.LOWER));
        this.setHardness(0.0F);
        this.setStepSound(net.minecraft.block.Block.soundTypeGrass);
        this.setUnlocalizedName("doublePlant");
    }
}
