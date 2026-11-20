package net.minecraft.block;

import com.google.common.base.Predicate;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockPatternPatternHelper;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSkull extends BlockContainer {
    private static Predicate IS_WITHER_SKELETON;
    public static PropertyDirection FACING;

    private BlockPattern witherBasePattern;
    public static PropertyBool NODROP;

    private BlockPattern witherPattern;

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((Facing) blockState.getValue(net.minecraft.block.BlockSkull.FACING))
                                .getIndex();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockSkull.NODROP)) {
            i |= 8;
        }

        return i;
    }

    public boolean canDispenserPlace(World world, BlockPos blockPos, ItemStack itemStack) {
        return itemStack.getItemDamage() == 1
                        && blockPos.getY() >= 2
                        && world.getDifficulty() != EnumDifficulty.PEACEFUL
                        && !world.isRemote
                ? this.getWitherBasePattern().match(world, blockPos) != null
                : false;
    }

    public void onBlockHarvested(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (playerEntity.capabilities.isCreativeMode) {
            blockState = blockState.withProperty(net.minecraft.block.BlockSkull.NODROP, true);
            world.setBlockState(blockPos, blockState, 4);
        }

        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    protected BlockSkull() {
        super(Material.circuits);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockSkull.FACING, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockSkull.NODROP, false));
        this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
    }

    protected BlockPattern getWitherBasePattern() {
        if (this.witherBasePattern == null) {
            this.witherBasePattern =
                    FactoryBlockPattern.start()
                            .aisle(new String[] {"   ", "###", "~#~"})
                            .where(
                                    '#',
                                    BlockWorldState.hasState(
                                            BlockStateHelper.forBlock(Blocks.soul_sand)))
                            .where(
                                    '~',
                                    BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air)))
                            .build();
        }

        return this.witherBasePattern;
    }

    static {
        net.minecraft.block.BlockSkull.FACING = PropertyDirection.create("facing");
        net.minecraft.block.BlockSkull.NODROP = PropertyBool.create("nodrop");
        net.minecraft.block.BlockSkull.IS_WITHER_SKELETON = new IsWitherSkeletonPredicate();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockSkull.FACING, net.minecraft.block.BlockSkull.NODROP
                });
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {}

    public Item getItem(World world, BlockPos blockPos) {
        return Items.skull;
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.isRemote) {
            if (!(Boolean) blockState.getValue(net.minecraft.block.BlockSkull.NODROP)) {
                TileEntity tileEntity = world.getTileEntity(blockPos);
                if (tileEntity instanceof TileEntitySkull) {
                    TileEntitySkull tileEntitySkull = (TileEntitySkull) tileEntity;
                    ItemStack itemStack =
                            new ItemStack(Items.skull, 1, this.getDamageValue(world, blockPos));
                    if (tileEntitySkull.getSkullType() == 3
                            && tileEntitySkull.getPlayerProfile() != null) {
                        itemStack.setTag(new CompoundTag());
                        CompoundTag compoundTag = new CompoundTag();
                        NBTUtil.writeGameProfile(compoundTag, tileEntitySkull.getPlayerProfile());
                        itemStack.getTag().setTag("SkullOwner", compoundTag);
                    }

                    spawnAsEntity(world, blockPos, itemStack);
                }
            }

            super.breakBlock(world, blockPos, blockState);
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.skull;
    }

    public void checkWitherSpawn(World world, BlockPos blockPos, TileEntitySkull tileEntitySkull) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (tileEntitySkull.getSkullType() == 1
                && blockPos.getY() >= 2
                && world.getDifficulty() != EnumDifficulty.PEACEFUL
                && !world.isRemote) {
            BlockPattern blockPattern = this.getWitherPattern();
            BlockPatternPatternHelper blockPatternPatternHelper =
                    blockPattern.match(world, blockPos);
            if (blockPatternPatternHelper != null) {
                int j = 0;

                while (j < 3) {
                    BlockWorldState blockWorldState =
                            blockPatternPatternHelper.translateOffset(j, 0, 0);
                    world.setBlockState(
                            blockWorldState.getPos(),
                            blockWorldState
                                    .getBlockState()
                                    .withProperty(net.minecraft.block.BlockSkull.NODROP, true),
                            2);
                    j++;
                    if (i == 0) {
                        break;
                    }
                }

                j = 0;

                while (j < blockPattern.method_7eec03da329a017ec143()) {
                    int l = 0;

                    while (l < blockPattern.method_9b417d27949cba33a0d0()) {
                        BlockWorldState blockWorldStateValue =
                                blockPatternPatternHelper.translateOffset(j, l, 0);
                        world.setBlockState(
                                blockWorldStateValue.getPos(), Blocks.air.getDefaultState(), 2);
                        l++;
                        if (i == 0) {
                            break;
                        }
                    }

                    j++;
                    if (i == 0) {
                        break;
                    }
                }

                BlockPos blockPosValue =
                        blockPatternPatternHelper.translateOffset(1, 0, 0).getPos();
                EntityWither entityWither = new EntityWither(world);
                BlockPos blockPosValue2 =
                        blockPatternPatternHelper.translateOffset(1, 2, 0).getPos();
                entityWither.setLocationAndAngles(
                        blockPosValue2.getX() + 0.5,
                        blockPosValue2.getY() + 0.55,
                        blockPosValue2.getZ() + 0.5,
                        blockPatternPatternHelper.getFinger().getAxis() == FacingAxis.X
                                ? 0.0F
                                : 90.0F,
                        0.0F);
                entityWither.renderYawOffset =
                        blockPatternPatternHelper.getFinger().getAxis() == FacingAxis.X
                                ? 0.0F
                                : 90.0F;
                entityWither.func_82206_m();

                for (java.util.Iterator<?> __recoveryIterator171_15083 =
                                (world.getEntitiesWithinAABB(
                                                PlayerEntity.class,
                                                entityWither
                                                        .getEntityBoundingBox()
                                                        .expand(50.0, 50.0, 50.0)))
                                        .iterator();
                        (__recoveryIterator171_15083).hasNext(); ) {
                    PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_15083).next();
                    playerEntity.triggerAchievement(AchievementList.spawnWither);
                    if (i == 0) {
                        break;
                    }
                }

                world.spawnEntityInWorld(entityWither);
                int i1 = 0;

                while (i1 < 120) {
                    world.spawnParticle(
                            EnumParticleTypes.SNOWBALL,
                            blockPosValue.getX() + world.rand.nextDouble(),
                            blockPosValue.getY() - 2 + world.rand.nextDouble() * 3.9,
                            blockPosValue.getZ() + world.rand.nextDouble(),
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    i1++;
                    if (i == 0) {
                        break;
                    }
                }

                i1 = 0;

                while (i1 < blockPattern.method_7eec03da329a017ec143()) {
                    int k = 0;

                    while (k < blockPattern.method_9b417d27949cba33a0d0()) {
                        BlockWorldState blockWorldStateValue2 =
                                blockPatternPatternHelper.translateOffset(i1, k, 0);
                        world.notifyNeighborsRespectDebug(
                                blockWorldStateValue2.getPos(), Blocks.air);
                        k++;
                        if (i == 0) {
                            break;
                        }
                    }

                    i1++;
                    if (i == 0) {
                        break;
                    }
                }
            }
        }
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockSkull.FACING, Facing.getFront(i & 7))
                .withProperty(net.minecraft.block.BlockSkull.NODROP, (i & 8) > 0);
    }

    protected BlockPattern getWitherPattern() {
        if (this.witherPattern == null) {
            this.witherPattern =
                    FactoryBlockPattern.start()
                            .aisle(new String[] {"^^^", "###", "~#~"})
                            .where(
                                    '#',
                                    BlockWorldState.hasState(
                                            BlockStateHelper.forBlock(Blocks.soul_sand)))
                            .where('^', net.minecraft.block.BlockSkull.IS_WITHER_SKELETON)
                            .where(
                                    '~',
                                    BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air)))
                            .build();
        }

        return this.witherPattern;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        switch ((Facing)
                iBlockAccess
                        .getBlockState(blockPos)
                        .getValue(net.minecraft.block.BlockSkull.FACING)) {
            case UP:
            default:
                this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
                if (i != 0) {
                    break;
                }
            case NORTH:
                this.setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
                if (i != 0) {
                    break;
                }
            case SOUTH:
                this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
                if (i != 0) {
                    break;
                }
            case WEST:
                this.setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
                if (i != 0) {
                    break;
                }
            case EAST:
                this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
        }
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntitySkull();
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockSkull.FACING, livingEntity.getHorizontalFacing())
                .withProperty(net.minecraft.block.BlockSkull.NODROP, false);
    }

    public int getDamageValue(World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntitySkull
                ? ((TileEntitySkull) tileEntity).getSkullType()
                : super.getDamageValue(world, blockPos);
    }

    // Helper used only by this class.
    public static final class IsWitherSkeletonPredicate
            implements com.google.common.base.Predicate {
        public boolean method_b0c2b0505f743fbab8ae(
                net.minecraft.block.state.BlockWorldState blockWorldState) {
            return blockWorldState.getBlockState().getBlock() == net.minecraft.init.Blocks.skull
                    && blockWorldState.getTileEntity()
                            instanceof net.minecraft.tileentity.TileEntitySkull
                    && ((net.minecraft.tileentity.TileEntitySkull) blockWorldState.getTileEntity())
                                    .getSkullType()
                            == 1;
        }

        public boolean apply(java.lang.Object p171_0) {
            return this.method_b0c2b0505f743fbab8ae(
                    (net.minecraft.block.state.BlockWorldState) p171_0);
        }
    }
}
