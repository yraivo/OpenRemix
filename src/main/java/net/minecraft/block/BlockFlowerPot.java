package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFlowerPot extends BlockContainer {
    public static PropertyEnum CONTENTS;

    public static PropertyInteger LEGACY_DATA;

    // region String decoding support

    // endregion

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        TileEntityFlowerPot tileEntityFlowerPot = this.getTileEntity(world, blockPos);
        if (tileEntityFlowerPot != null && tileEntityFlowerPot.getFlowerPotItem() != null) {
            spawnAsEntity(
                    world,
                    blockPos,
                    new ItemStack(
                            tileEntityFlowerPot.getFlowerPotItem(),
                            1,
                            tileEntityFlowerPot.getFlowerPotData()));
        }

        super.breakBlock(world, blockPos, blockState);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public Item getItem(World world, BlockPos blockPos) {
        TileEntityFlowerPot tileEntityFlowerPot = this.getTileEntity(world, blockPos);
        return tileEntityFlowerPot != null && tileEntityFlowerPot.getFlowerPotItem() != null
                ? tileEntityFlowerPot.getFlowerPotItem()
                : Items.flower_pot;
    }

    public void onBlockHarvested(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
        if (playerEntity.capabilities.isCreativeMode) {
            TileEntityFlowerPot tileEntityFlowerPot = this.getTileEntity(world, blockPos);
            if (tileEntityFlowerPot != null) {
                tileEntityFlowerPot.setFlowerPotData((Item) null, 0);
            }
        }
    }

    static {
        net.minecraft.block.BlockFlowerPot.LEGACY_DATA =
                PropertyInteger.create("legacy_data", 0, 15);
        net.minecraft.block.BlockFlowerPot.CONTENTS =
                PropertyEnum.create("contents", BlockFlowerPotEnumFlowerType.class);
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockFlowerPotEnumFlowerType blockFlowerPotEnumFlowerType =
                BlockFlowerPotEnumFlowerType.EMPTY;
        int i = k;
        TileEntity tileEntity = iBlockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFlowerPot) {
            TileEntityFlowerPot tileEntityFlowerPot = (TileEntityFlowerPot) tileEntity;
            Item item = tileEntityFlowerPot.getFlowerPotItem();
            if (item instanceof ItemBlock) {
                int j = tileEntityFlowerPot.getFlowerPotData();
                Block block = Block.getBlockFromItem(item);
                if (block == Blocks.sapling) {
                    switch (BlockPlanksEnumType.byMetadata(j)) {
                        case OAK:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.OAK_SAPLING;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case SPRUCE:
                            blockFlowerPotEnumFlowerType =
                                    BlockFlowerPotEnumFlowerType.SPRUCE_SAPLING;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case BIRCH:
                            blockFlowerPotEnumFlowerType =
                                    BlockFlowerPotEnumFlowerType.BIRCH_SAPLING;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case JUNGLE:
                            blockFlowerPotEnumFlowerType =
                                    BlockFlowerPotEnumFlowerType.JUNGLE_SAPLING;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case ACACIA:
                            blockFlowerPotEnumFlowerType =
                                    BlockFlowerPotEnumFlowerType.ACACIA_SAPLING;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case DARK_OAK:
                            blockFlowerPotEnumFlowerType =
                                    BlockFlowerPotEnumFlowerType.DARK_OAK_SAPLING;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        default:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.EMPTY;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                    }
                }

                if (block == Blocks.tallgrass) {
                    switch (j) {
                        case 0:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.DEAD_BUSH;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case 2:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.FERN;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        default:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.EMPTY;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                    }
                }

                if (block == Blocks.yellow_flower) {
                    blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.DANDELION;
                    if (i != 0) {
                        return blockState.withProperty(
                                net.minecraft.block.BlockFlowerPot.CONTENTS,
                                blockFlowerPotEnumFlowerType);
                    }
                }

                if (block == Blocks.red_flower) {
                    switch (BlockFlowerEnumFlowerType.getType(BlockFlowerEnumFlowerColor.RED, j)) {
                        case POPPY:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.POPPY;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case BLUE_ORCHID:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.BLUE_ORCHID;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case ALLIUM:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.ALLIUM;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case HOUSTONIA:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.HOUSTONIA;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case RED_TULIP:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.RED_TULIP;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case ORANGE_TULIP:
                            blockFlowerPotEnumFlowerType =
                                    BlockFlowerPotEnumFlowerType.ORANGE_TULIP;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case WHITE_TULIP:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.WHITE_TULIP;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case PINK_TULIP:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.PINK_TULIP;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        case OXEYE_DAISY:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.OXEYE_DAISY;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                        default:
                            blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.EMPTY;
                            if (i != 0) {
                                return blockState.withProperty(
                                        net.minecraft.block.BlockFlowerPot.CONTENTS,
                                        blockFlowerPotEnumFlowerType);
                            }
                    }
                }

                if (block == Blocks.red_mushroom) {
                    blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.MUSHROOM_RED;
                    if (i != 0) {
                        return blockState.withProperty(
                                net.minecraft.block.BlockFlowerPot.CONTENTS,
                                blockFlowerPotEnumFlowerType);
                    }
                }

                if (block == Blocks.brown_mushroom) {
                    blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.MUSHROOM_BROWN;
                    if (i != 0) {
                        return blockState.withProperty(
                                net.minecraft.block.BlockFlowerPot.CONTENTS,
                                blockFlowerPotEnumFlowerType);
                    }
                }

                if (block == Blocks.deadbush) {
                    blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.DEAD_BUSH;
                    if (i != 0) {
                        return blockState.withProperty(
                                net.minecraft.block.BlockFlowerPot.CONTENTS,
                                blockFlowerPotEnumFlowerType);
                    }
                }

                if (block == Blocks.cactus) {
                    blockFlowerPotEnumFlowerType = BlockFlowerPotEnumFlowerType.CACTUS;
                }
            }
        }

        return blockState.withProperty(
                net.minecraft.block.BlockFlowerPot.CONTENTS, blockFlowerPotEnumFlowerType);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos)
                && World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
    }

    public int getDamageValue(World world, BlockPos blockPos) {
        TileEntityFlowerPot tileEntityFlowerPot = this.getTileEntity(world, blockPos);
        return tileEntityFlowerPot != null && tileEntityFlowerPot.getFlowerPotItem() != null
                ? tileEntityFlowerPot.getFlowerPotData()
                : 0;
    }

    public void setBlockBoundsForItemRender() {
        float f = 0.375F;
        float f1 = f / 2.0F;
        this.setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
    }

    public BlockFlowerPot() {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockFlowerPot.CONTENTS,
                                BlockFlowerPotEnumFlowerType.EMPTY)
                        .withProperty(net.minecraft.block.BlockFlowerPot.LEGACY_DATA, 0));
        this.setBlockBoundsForItemRender();
    }

    public TileEntity createNewTileEntity(World world, int i) {
        Object object = null;
        int j = 0;
        switch (i) {
            case 1:
                object = Blocks.red_flower;
                j = BlockFlowerEnumFlowerType.POPPY.getMeta();
                break;
            case 2:
                object = Blocks.yellow_flower;
                break;
            case 3:
                object = Blocks.sapling;
                j = BlockPlanksEnumType.OAK.getMetadata();
                break;
            case 4:
                object = Blocks.sapling;
                j = BlockPlanksEnumType.SPRUCE.getMetadata();
                break;
            case 5:
                object = Blocks.sapling;
                j = BlockPlanksEnumType.BIRCH.getMetadata();
                break;
            case 6:
                object = Blocks.sapling;
                j = BlockPlanksEnumType.JUNGLE.getMetadata();
                break;
            case 7:
                object = Blocks.red_mushroom;
                break;
            case 8:
                object = Blocks.brown_mushroom;
                break;
            case 9:
                object = Blocks.cactus;
                break;
            case 10:
                object = Blocks.deadbush;
                break;
            case 11:
                object = Blocks.tallgrass;
                j = BlockTallGrassEnumType.FERN.getMeta();
                break;
            case 12:
                object = Blocks.sapling;
                j = BlockPlanksEnumType.ACACIA.getMetadata();
                break;
            case 13:
                object = Blocks.sapling;
                j = BlockPlanksEnumType.DARK_OAK.getMetadata();
        }

        return new TileEntityFlowerPot(Item.getItemFromBlock((Block) object), j);
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockFlowerPot.LEGACY_DATA);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())) {
            this.dropBlockAsItem(world, blockPos, blockState, 0);
            world.setBlockToAir(blockPos);
        }
    }

    private boolean canNotContain(Block block, int i) {
        return block != Blocks.yellow_flower
                        && block != Blocks.red_flower
                        && block != Blocks.cactus
                        && block != Blocks.brown_mushroom
                        && block != Blocks.red_mushroom
                        && block != Blocks.sapling
                        && block != Blocks.deadbush
                ? block == Blocks.tallgrass && i == BlockTallGrassEnumType.FERN.getMeta()
                : true;
    }

    public boolean isFlowerPot() {
        return true;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockFlowerPot.CONTENTS,
                    net.minecraft.block.BlockFlowerPot.LEGACY_DATA
                });
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
            TileEntityFlowerPot tileEntityFlowerPot = this.getTileEntity(world, blockPos);
            if (tileEntityFlowerPot == null) {
                return false;
            }

            if (tileEntityFlowerPot.getFlowerPotItem() != null) {
                return false;
            }

            Block block = Block.getBlockFromItem(itemStack.getItem());
            if (!this.canNotContain(block, itemStack.getItemDamage())) {
                return false;
            }

            tileEntityFlowerPot.setFlowerPotData(itemStack.getItem(), itemStack.getItemDamage());
            tileEntityFlowerPot.markDirty();
            world.markBlockForUpdate(blockPos);
            if (!playerEntity.capabilities.isCreativeMode && --itemStack.stackSize <= 0) {
                playerEntity.inventory.setInventorySlotContents(
                        playerEntity.inventory.currentItem, (ItemStack) null);
            }

            return true;
        } else {
            return false;
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.flower_pot;
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        TileEntity tileEntity = iBlockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFlowerPot) {
            Item item = ((TileEntityFlowerPot) tileEntity).getFlowerPotItem();
            if (item instanceof ItemBlock) {
                return Block.getBlockFromItem(item).colorMultiplier(iBlockAccess, blockPos, i);
            }
        }

        return 16777215;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    // region String decoding support

    // endregion

    public int getRenderType() {
        return 3;
    }

    private TileEntityFlowerPot getTileEntity(World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntityFlowerPot ? (TileEntityFlowerPot) tileEntity : null;
    }
}
