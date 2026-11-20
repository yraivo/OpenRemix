package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockPatternPatternHelper;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockPumpkin extends BlockDirectional {

    private BlockPattern golemBasePattern;
    private static String[] packedTextFragments;
    private BlockPattern golemPattern;
    private BlockPattern snowmanPattern;
    private BlockPattern snowmanBasePattern;

    private void createGolem(World world, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockPatternPatternHelper blockPatternPatternHelper;
        if ((blockPatternPatternHelper = this.getSnowmanPattern().match(world, blockPos)) != null) {
            int j = 0;

            while (j < this.getSnowmanPattern().method_9b417d27949cba33a0d0()) {
                BlockWorldState blockWorldState =
                        blockPatternPatternHelper.translateOffset(0, j, 0);
                world.setBlockState(blockWorldState.getPos(), Blocks.air.getDefaultState(), 2);
                j++;
                if (i == 0) {
                    break;
                }
            }

            EntitySnowman entitySnowman = new EntitySnowman(world);
            BlockPos blockPosValue = blockPatternPatternHelper.translateOffset(0, 2, 0).getPos();
            entitySnowman.setLocationAndAngles(
                    blockPosValue.getX() + 0.5,
                    blockPosValue.getY() + 0.05,
                    blockPosValue.getZ() + 0.5,
                    0.0F,
                    0.0F);
            world.spawnEntityInWorld(entitySnowman);
            int k = 0;

            while (k < 120) {
                world.spawnParticle(
                        EnumParticleTypes.SNOW_SHOVEL,
                        blockPosValue.getX() + world.rand.nextDouble(),
                        blockPosValue.getY() + world.rand.nextDouble() * 2.5,
                        blockPosValue.getZ() + world.rand.nextDouble(),
                        0.0,
                        0.0,
                        0.0,
                        new int[0]);
                k++;
                if (i == 0) {
                    break;
                }
            }

            k = 0;

            while (k < this.getSnowmanPattern().method_9b417d27949cba33a0d0()) {
                BlockWorldState blockWorldStateValue =
                        blockPatternPatternHelper.translateOffset(0, k, 0);
                world.notifyNeighborsRespectDebug(blockWorldStateValue.getPos(), Blocks.air);
                k++;
                if (i == 0) {
                    break;
                }
            }

            if (i != 0) {
                return;
            }
        }

        if ((blockPatternPatternHelper = this.getGolemPattern().match(world, blockPos)) != null) {
            int l = 0;

            while (l < this.getGolemPattern().method_7eec03da329a017ec143()) {
                int j1 = 0;

                while (j1 < this.getGolemPattern().method_9b417d27949cba33a0d0()) {
                    world.setBlockState(
                            blockPatternPatternHelper.translateOffset(l, j1, 0).getPos(),
                            Blocks.air.getDefaultState(),
                            2);
                    j1++;
                    if (i == 0) {
                        break;
                    }
                }

                l++;
                if (i == 0) {
                    break;
                }
            }

            BlockPos blockPosValue2 = blockPatternPatternHelper.translateOffset(1, 2, 0).getPos();
            EntityIronGolem entityIronGolem = new EntityIronGolem(world);
            entityIronGolem.setPlayerCreated(true);
            entityIronGolem.setLocationAndAngles(
                    blockPosValue2.getX() + 0.5,
                    blockPosValue2.getY() + 0.05,
                    blockPosValue2.getZ() + 0.5,
                    0.0F,
                    0.0F);
            world.spawnEntityInWorld(entityIronGolem);
            int i1 = 0;

            while (i1 < 120) {
                world.spawnParticle(
                        EnumParticleTypes.SNOWBALL,
                        blockPosValue2.getX() + world.rand.nextDouble(),
                        blockPosValue2.getY() + world.rand.nextDouble() * 3.9,
                        blockPosValue2.getZ() + world.rand.nextDouble(),
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

            while (i1 < this.getGolemPattern().method_7eec03da329a017ec143()) {
                int k1 = 0;

                while (k1 < this.getGolemPattern().method_9b417d27949cba33a0d0()) {
                    BlockWorldState blockWorldStateValue2 =
                            blockPatternPatternHelper.translateOffset(i1, k1, 0);
                    world.notifyNeighborsRespectDebug(blockWorldStateValue2.getPos(), Blocks.air);
                    k1++;
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

    protected BlockPattern getGolemBasePattern() {
        if (this.golemBasePattern == null) {
            this.golemBasePattern =
                    FactoryBlockPattern.start()
                            .aisle(new String[] {"~ ~", "###", "~#~"})
                            .where(
                                    '#',
                                    BlockWorldState.hasState(
                                            BlockStateHelper.forBlock(Blocks.iron_block)))
                            .where(
                                    '~',
                                    BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air)))
                            .build();
        }

        return this.golemBasePattern;
    }

    protected BlockPattern getGolemPattern() {
        if (this.golemPattern == null) {
            this.golemPattern =
                    FactoryBlockPattern.start()
                            .aisle(new String[] {"~^~", "###", "~#~"})
                            .where(
                                    '^',
                                    BlockWorldState.hasState(
                                            BlockStateHelper.forBlock(Blocks.pumpkin)))
                            .where(
                                    '#',
                                    BlockWorldState.hasState(
                                            BlockStateHelper.forBlock(Blocks.iron_block)))
                            .where(
                                    '~',
                                    BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air)))
                            .build();
        }

        return this.golemPattern;
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
                        net.minecraft.block.BlockDirectional.AGE,
                        livingEntity.getHorizontalFacing().getOpposite());
    }

    protected BlockPumpkin() {
        super(Material.gourd);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.NORTH));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.createGolem(world, blockPos);
    }

    public boolean canDispenserPlace(World world, BlockPos blockPos) {
        return this.getSnowmanBasePattern().match(world, blockPos) != null
                || this.getGolemBasePattern().match(world, blockPos) != null;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockDirectional.AGE});
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().blockMaterial.isReplaceable()
                && World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
    }

    public int getMetaFromState(BlockState blockState) {
        return ((Facing) blockState.getValue(net.minecraft.block.BlockDirectional.AGE)).getHorizontalIndex();
    }

    protected BlockPattern getSnowmanBasePattern() {
        if (this.snowmanBasePattern == null) {
            this.snowmanBasePattern =
                    FactoryBlockPattern.start()
                            .aisle(
                                    new String[] {
                                        packedTextFragments[0],
                                        packedTextFragments[1],
                                        packedTextFragments[2]
                                    })
                            .where(
                                    '#',
                                    BlockWorldState.hasState(
                                            BlockStateHelper.forBlock(Blocks.snow)))
                            .build();
        }

        return this.snowmanBasePattern;
    }

    protected BlockPattern getSnowmanPattern() {
        if (this.snowmanPattern == null) {
            this.snowmanPattern =
                    FactoryBlockPattern.start()
                            .aisle(
                                    new String[] {
                                        packedTextFragments[3],
                                        packedTextFragments[4],
                                        packedTextFragments[5]
                                    })
                            .where(
                                    '^',
                                    BlockWorldState.hasState(
                                            BlockStateHelper.forBlock(Blocks.pumpkin)))
                            .where(
                                    '#',
                                    BlockWorldState.hasState(
                                            BlockStateHelper.forBlock(Blocks.snow)))
                            .build();
        }

        return this.snowmanPattern;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockDirectional.AGE, Facing.getHorizontal(i));
    }

    static {
        net.minecraft.block.BlockPumpkin.packedTextFragments = new String[8];
        net.minecraft.block.BlockPumpkin.packedTextFragments[0] = new String(" ");
        net.minecraft.block.BlockPumpkin.packedTextFragments[1] = new String("#");
        net.minecraft.block.BlockPumpkin.packedTextFragments[2] = new String("#");
        net.minecraft.block.BlockPumpkin.packedTextFragments[3] = new String("^");
        net.minecraft.block.BlockPumpkin.packedTextFragments[4] = new String("#");
        net.minecraft.block.BlockPumpkin.packedTextFragments[5] = new String("#");
    }
}
