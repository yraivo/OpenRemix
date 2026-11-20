package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerEnumStatus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class BlockBed extends BlockDirectional {
    public static PropertyEnum PART_PROP;

    public static PropertyBool OCCUPIED_PROP;

    private PlayerEntity getPlayerInBed(World world, BlockPos blockPos) {
        for (PlayerEntity playerEntity : world.playerEntities) {
            if (playerEntity.isPlayerSleeping() && playerEntity.playerLocation.equals(blockPos)) {
                return playerEntity;
            }
        }

        return null;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public boolean isFullCube() {
        return false;
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.bed;
    }

    public BlockBed() {
        super(Material.cloth);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockBed.PART_PROP, BlockBedEnumPartType.FOOT)
                        .withProperty(net.minecraft.block.BlockBed.OCCUPIED_PROP, false));
        this.setBedBounds();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockDirectional.AGE,
                    net.minecraft.block.BlockBed.PART_PROP,
                    net.minecraft.block.BlockBed.OCCUPIED_PROP
                });
    }

    public boolean isOpaqueCube() {
        return false;
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
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (world.isRemote) {
            return true;
        }

        if (blockState.getValue(net.minecraft.block.BlockBed.PART_PROP) != BlockBedEnumPartType.HEAD) {
            blockPos = blockPos.offset((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE));
            blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() != this) {
                return true;
            }
        }

        if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(blockPos) != BiomeGenBase.hell) {
            if ((Boolean) blockState.getValue(net.minecraft.block.BlockBed.OCCUPIED_PROP)) {
                PlayerEntity playerEntityValue = this.getPlayerInBed(world, blockPos);
                if (playerEntityValue != null) {
                    playerEntity.addChatComponentMessage(
                            new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                    return true;
                }

                blockState = blockState.withProperty(net.minecraft.block.BlockBed.OCCUPIED_PROP, false);
                world.setBlockState(blockPos, blockState, 4);
            }

            EntityPlayerEnumStatus entityPlayerEnumStatus = playerEntity.trySleep(blockPos);
            if (entityPlayerEnumStatus == EntityPlayerEnumStatus.OK) {
                blockState = blockState.withProperty(net.minecraft.block.BlockBed.OCCUPIED_PROP, true);
                world.setBlockState(blockPos, blockState, 4);
                return true;
            }

            if (entityPlayerEnumStatus == EntityPlayerEnumStatus.NOT_POSSIBLE_NOW) {
                playerEntity.addChatComponentMessage(
                        new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
                if (i != 0) {
                    return true;
                }
            }

            if (entityPlayerEnumStatus == EntityPlayerEnumStatus.NOT_SAFE) {
                playerEntity.addChatComponentMessage(
                        new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
            }

            return true;
        } else {
            world.setBlockToAir(blockPos);
            BlockPos blockPosValue =
                    blockPos.offset(
                            ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE))
                                    .getOpposite());
            if (world.getBlockState(blockPosValue).getBlock() == this) {
                world.setBlockToAir(blockPosValue);
            }

            world.newExplosion(
                    (Entity) null,
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    5.0F,
                    true,
                    true);
            return true;
        }
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE))
                                .getHorizontalIndex();
        if (blockState.getValue(net.minecraft.block.BlockBed.PART_PROP) == BlockBedEnumPartType.HEAD) {
            i |= 8;
            if ((Boolean) blockState.getValue(net.minecraft.block.BlockBed.OCCUPIED_PROP)) {
                i |= 4;
            }
        }

        return i;
    }

    static {
        net.minecraft.block.BlockBed.PART_PROP = PropertyEnum.create("part", BlockBedEnumPartType.class);
        net.minecraft.block.BlockBed.OCCUPIED_PROP = PropertyBool.create("occupied");
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return blockState.getValue(net.minecraft.block.BlockBed.PART_PROP) == BlockBedEnumPartType.HEAD
                ? null
                : Items.bed;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE);
        int i = j;
        if (blockState.getValue(net.minecraft.block.BlockBed.PART_PROP) == BlockBedEnumPartType.HEAD) {
            if (world.getBlockState(blockPos.offset(facing.getOpposite())).getBlock() == this) {
                return;
            }

            world.setBlockToAir(blockPos);
            if (i == 0) {
                return;
            }
        }

        if (world.getBlockState(blockPos.offset(facing)).getBlock() != this) {
            world.setBlockToAir(blockPos);
            if (!world.isRemote) {
                this.dropBlockAsItem(world, blockPos, blockState, 0);
            }
        }
    }

    private void setBedBounds() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    public int getMobilityFlag() {
        return 1;
    }

    public static BlockPos getSafeExitLocation(World world, BlockPos blockPos, int i) {
        Facing facing = (Facing) world.getBlockState(blockPos).getValue(net.minecraft.block.BlockDirectional.AGE);
        int i3 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int k = blockPos.getX();
        int l = blockPos.getY();
        int j = i3;
        int z = blockPos.getZ();
        int j1 = 0;

        while (j1 <= 1) {
            int k1 = k - facing.getFrontOffsetX() * j1 - 1;
            int l1 = z - facing.getFrontOffsetZ() * j1 - 1;
            int i2 = k1 + 2;
            int j2 = l1 + 2;
            int k2 = k1;

            while (true) {
                if (k2 <= i2) {
                    int l2 = l1;

                    while (l2 <= j2) {
                        BlockPos blockPosValue = new BlockPos(k2, l, l2);
                        if (hasRoomForPlayer(world, blockPosValue)) {
                            if (i <= 0) {
                                return blockPosValue;
                            }

                            i--;
                        }

                        l2++;
                        if (j != 0) {
                            break;
                        }
                    }

                    k2++;
                    if (j == 0) {
                        continue;
                    }
                }

                j1++;
                if (j != 0) {
                    return null;
                }
                break;
            }
        }

        return null;
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        if (blockState.getValue(net.minecraft.block.BlockBed.PART_PROP) == BlockBedEnumPartType.FOOT) {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, f, 0);
        }
    }

    public BlockState getStateFromMeta(int i) {
        Facing facing = Facing.getHorizontal(i);
        return (i & 8) > 0
                ? this.getDefaultState()
                        .withProperty(net.minecraft.block.BlockBed.PART_PROP, BlockBedEnumPartType.HEAD)
                        .withProperty(net.minecraft.block.BlockDirectional.AGE, facing)
                        .withProperty(net.minecraft.block.BlockBed.OCCUPIED_PROP, (i & 4) > 0)
                : this.getDefaultState()
                        .withProperty(net.minecraft.block.BlockBed.PART_PROP, BlockBedEnumPartType.FOOT)
                        .withProperty(net.minecraft.block.BlockDirectional.AGE, facing);
    }

    protected static boolean hasRoomForPlayer(World world, BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                && !world.getBlockState(blockPos).getBlock().getMaterial().isSolid()
                && !world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial().isSolid();
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        if (blockState.getValue(net.minecraft.block.BlockBed.PART_PROP) == BlockBedEnumPartType.FOOT) {
            BlockState blockStateValue =
                    iBlockAccess.getBlockState(
                            blockPos.offset(
                                    (Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE)));
            if (blockStateValue.getBlock() == this) {
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockBed.OCCUPIED_PROP,
                                blockStateValue.getValue(net.minecraft.block.BlockBed.OCCUPIED_PROP));
            }
        }

        return blockState;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.setBedBounds();
    }

    public void onBlockHarvested(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (playerEntity.capabilities.isCreativeMode
                && blockState.getValue(net.minecraft.block.BlockBed.PART_PROP) == BlockBedEnumPartType.HEAD) {
            BlockPos blockPosValue =
                    blockPos.offset(
                            ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE))
                                    .getOpposite());
            if (world.getBlockState(blockPosValue).getBlock() == this) {
                world.setBlockToAir(blockPosValue);
            }
        }
    }
}
