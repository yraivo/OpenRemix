package net.minecraft.block;

import com.google.common.collect.Maps;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

import java.util.Map;
import java.util.Random;

public class BlockFire extends Block {

    public static PropertyBool WEST;
    public static PropertyBool EAST;
    public static PropertyBool NORTH;
    public static PropertyBool FLIP;
    private Map flammabilities;
    public static PropertyBool ALT;
    public static PropertyInteger AGE;
    public static PropertyBool SOUTH;
    public static PropertyInteger UPPER;

    private final Map encouragements = Maps.newIdentityHashMap();

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public void setFireInfo(Block block, int i, int j) {
        this.encouragements.put(block, i);
        this.flammabilities.put(block, j);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (world.provider.getDimensionId() > 0 || !Blocks.portal.func_176548_d(world, blockPos)) {
            if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                    && !this.canNeighborCatchFire(world, blockPos)) {
                world.setBlockToAir(blockPos);
                if (i == 0) {
                    return;
                }
            }

            world.scheduleUpdate(blockPos, this, this.tickRate(world) + world.rand.nextInt(10));
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public static void init() {
        Blocks.fire.setFireInfo(Blocks.planks, 5, 20);
        Blocks.fire.setFireInfo(Blocks.double_wooden_slab, 5, 20);
        Blocks.fire.setFireInfo(Blocks.wooden_slab, 5, 20);
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        Blocks.fire.setFireInfo(Blocks.oak_fence_gate, 5, 20);
        int i = j;
        Blocks.fire.setFireInfo(Blocks.spruce_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.birch_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.jungle_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.dark_oak_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.acacia_fence_gate, 5, 20);
        Blocks.fire.setFireInfo(Blocks.oak_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.spruce_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.birch_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.jungle_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.dark_oak_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.acacia_fence, 5, 20);
        Blocks.fire.setFireInfo(Blocks.oak_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.birch_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.spruce_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.jungle_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.log, 5, 5);
        Blocks.fire.setFireInfo(Blocks.log2, 5, 5);
        Blocks.fire.setFireInfo(Blocks.leaves, 30, 60);
        Blocks.fire.setFireInfo(Blocks.leaves2, 30, 60);
        Blocks.fire.setFireInfo(Blocks.bookshelf, 30, 20);
        Blocks.fire.setFireInfo(Blocks.tnt, 15, 100);
        Blocks.fire.setFireInfo(Blocks.tallgrass, 60, 100);
        Blocks.fire.setFireInfo(Blocks.double_plant, 60, 100);
        Blocks.fire.setFireInfo(Blocks.yellow_flower, 60, 100);
        Blocks.fire.setFireInfo(Blocks.red_flower, 60, 100);
        Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
        Blocks.fire.setFireInfo(Blocks.wool, 30, 60);
        Blocks.fire.setFireInfo(Blocks.vine, 15, 100);
        Blocks.fire.setFireInfo(Blocks.coal_block, 5, 5);
        Blocks.fire.setFireInfo(Blocks.hay_block, 60, 20);
        Blocks.fire.setFireInfo(Blocks.carpet, 60, 20);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            BlockPlanksEnumType.setLegacyBlockFlowToken(++i);
        }
    }

    private int getFlammability(Block block) {
        Integer integer = (Integer) this.flammabilities.get(block);
        return integer == null ? 0 : integer;
    }

    public boolean isCollidable() {
        return false;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick")) {
            if (!this.canPlaceBlockAt(world, blockPos)) {
                world.setBlockToAir(blockPos);
            }

            Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
            int j = block == Blocks.netherrack ? 1 : 0;
            if (world.provider instanceof WorldProviderEnd && block == Blocks.bedrock) {
                j = 1;
            }

            if (j == 0 && world.isRaining() && this.canDie(world, blockPos)) {
                world.setBlockToAir(blockPos);
                if (i == 0) {
                    return;
                }
            }

            int k = (Integer) blockState.getValue(net.minecraft.block.BlockFire.AGE);
            if (k < 15) {
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockFire.AGE, k + random.nextInt(3) / 2);
                world.setBlockState(blockPos, blockState, 4);
            }

            world.scheduleUpdate(blockPos, this, this.tickRate(world) + random.nextInt(10));
            if (j == 0) {
                if (!this.canNeighborCatchFire(world, blockPos)) {
                    if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                            || k > 3) {
                        world.setBlockToAir(blockPos);
                    }

                    return;
                }

                if (!this.canCatchFire(world, blockPos.offsetDown())
                        && k == 15
                        && random.nextInt(4) == 0) {
                    world.setBlockToAir(blockPos);
                    return;
                }
            }

            boolean blockinHighHumidity = world.isBlockinHighHumidity(blockPos);
            byte b0 = 0;
            if (blockinHighHumidity) {
                b0 = -50;
            }

            this.catchOnFire(world, blockPos.offsetEast(), 300 + b0, random, k);
            this.catchOnFire(world, blockPos.offsetWest(), 300 + b0, random, k);
            this.catchOnFire(world, blockPos.offsetDown(), 250 + b0, random, k);
            this.catchOnFire(world, blockPos.offsetUp(), 250 + b0, random, k);
            this.catchOnFire(world, blockPos.offsetNorth(), 300 + b0, random, k);
            this.catchOnFire(world, blockPos.offsetSouth(), 300 + b0, random, k);
            int l = -1;

            while (l <= 1) {
                int i1 = -1;

                while (i1 <= 1) {
                    int j1 = -1;

                    while (j1 <= 4) {
                        if (l != 0 || j1 != 0 || i1 != 0) {
                            int k1 = 100;
                            if (j1 > 1) {
                                k1 += (j1 - 1) * 100;
                            }

                            BlockPos blockPosValue = blockPos.add(l, j1, i1);
                            int l1 = this.getNeighborEncouragement(world, blockPosValue);
                            if (l1 > 0) {
                                int i2 =
                                        (l1 + 40 + world.getDifficulty().getDifficultyId() * 7)
                                                / (k + 30);
                                if (blockinHighHumidity) {
                                    i2 /= 2;
                                }

                                if (i2 > 0
                                        && random.nextInt(k1) <= i2
                                        && (!world.isRaining()
                                                || !this.canDie(world, blockPosValue))) {
                                    int j2 = k + random.nextInt(5) / 4;
                                    if (j2 > 15) {
                                        j2 = 15;
                                    }

                                    world.setBlockState(
                                            blockPosValue,
                                            blockState.withProperty(
                                                    net.minecraft.block.BlockFire.AGE, j2),
                                            3);
                                }
                            }
                        }

                        j1++;
                        if (i != 0) {
                            break;
                        }
                    }

                    i1++;
                    if (i != 0) {
                        break;
                    }
                }

                l++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    public MapColor getMapColor(BlockState blockState) {
        return MapColor.tntColor;
    }

    protected boolean canDie(World world, BlockPos blockPos) {
        return world.canLightningStrike(blockPos)
                || world.canLightningStrike(blockPos.offsetWest())
                || world.canLightningStrike(blockPos.offsetEast())
                || world.canLightningStrike(blockPos.offsetNorth())
                || world.canLightningStrike(blockPos.offsetSouth());
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        int x = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        if (!World.doesBlockHaveSolidTopSurface(iBlockAccess, blockPos.offsetDown())
                && !Blocks.fire.canCatchFire(iBlockAccess, blockPos.offsetDown())) {
            int l = (x + j + k & 1) == 1 ? 1 : 0;
            int i1 = (x / 2 + j / 2 + k / 2 & 1) == 1 ? 1 : 0;
            byte b0 = 0;
            if (this.canCatchFire(iBlockAccess, blockPos.offsetUp())) {
                b0 = (byte) (l != 0 ? 1 : 2);
            }

            return blockState
                    .withProperty(
                            net.minecraft.block.BlockFire.NORTH,
                            this.canCatchFire(iBlockAccess, blockPos.offsetNorth()))
                    .withProperty(
                            net.minecraft.block.BlockFire.EAST,
                            this.canCatchFire(iBlockAccess, blockPos.offsetEast()))
                    .withProperty(
                            net.minecraft.block.BlockFire.SOUTH,
                            this.canCatchFire(iBlockAccess, blockPos.offsetSouth()))
                    .withProperty(
                            net.minecraft.block.BlockFire.WEST,
                            this.canCatchFire(iBlockAccess, blockPos.offsetWest()))
                    .withProperty(net.minecraft.block.BlockFire.UPPER, Integer.valueOf(b0))
                    .withProperty(
                            net.minecraft.block.BlockFire.FLIP,
                            Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(i1)))
                    .withProperty(
                            net.minecraft.block.BlockFire.ALT,
                            Boolean.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(l)));
        } else {
            return this.getDefaultState();
        }
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    static {
        net.minecraft.block.BlockFire.AGE = PropertyInteger.create("age", 0, 15);
        net.minecraft.block.BlockFire.FLIP = PropertyBool.create("flip");
        net.minecraft.block.BlockFire.ALT = PropertyBool.create("alt");
        net.minecraft.block.BlockFire.NORTH = PropertyBool.create("north");
        net.minecraft.block.BlockFire.EAST = PropertyBool.create("east");
        net.minecraft.block.BlockFire.SOUTH = PropertyBool.create("south");
        net.minecraft.block.BlockFire.WEST = PropertyBool.create("west");
        net.minecraft.block.BlockFire.UPPER = PropertyInteger.create("upper", 0, 2);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockFire.AGE,
                    net.minecraft.block.BlockFire.NORTH,
                    net.minecraft.block.BlockFire.EAST,
                    net.minecraft.block.BlockFire.SOUTH,
                    net.minecraft.block.BlockFire.WEST,
                    net.minecraft.block.BlockFire.UPPER,
                    net.minecraft.block.BlockFire.FLIP,
                    net.minecraft.block.BlockFire.ALT
                });
    }

    private int getNeighborEncouragement(World world, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isAirBlock(blockPos)) {
            return 0;
        }

        int j = 0;

        for (Facing facing : Facing.values()) {
            j =
                    Math.max(
                            this.getEncouragement(
                                    world.getBlockState(blockPos.offset(facing)).getBlock()),
                            j);
            if (i == 0) {
                break;
            }
        }

        return j;
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockFire.AGE);
    }

    public boolean canCatchFire(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return this.getEncouragement(iBlockAccess.getBlockState(blockPos).getBlock()) > 0;
    }

    public boolean isFullCube() {
        return false;
    }

    private boolean canNeighborCatchFire(World world, BlockPos blockPos) {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        Facing[] facingArray = Facing.values();
        int j = facingArray.length;
        int k = 0;
        int i = l;

        while (k < j) {
            Facing facing = facingArray[k];
            if (this.canCatchFire(world, blockPos.offset(facing))) {
                return true;
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        return false;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockFire.AGE, i);
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (random.nextInt(24) == 0) {
            world.playSound(
                    blockPos.getX() + 0.5F,
                    blockPos.getY() + 0.5F,
                    blockPos.getZ() + 0.5F,
                    "fire.fire",
                    1.0F + random.nextFloat(),
                    random.nextFloat() * 0.7F + 0.3F,
                    false);
        }

        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                && !Blocks.fire.canCatchFire(world, blockPos.offsetDown())) {
            if (Blocks.fire.canCatchFire(world, blockPos.offsetWest())) {
                int j = 0;

                while (j < 2) {
                    double d0 = blockPos.getX() + random.nextDouble() * 0.1F;
                    double d1 = blockPos.getY() + random.nextDouble();
                    double d2 = blockPos.getZ() + random.nextDouble();
                    world.spawnParticle(
                            EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
                    j++;
                    if (i != 0) {
                        break;
                    }
                }
            }

            if (Blocks.fire.canCatchFire(world, blockPos.offsetEast())) {
                int k = 0;

                while (k < 2) {
                    double d3 = blockPos.getX() + 1 - random.nextDouble() * 0.1F;
                    double d8 = blockPos.getY() + random.nextDouble();
                    double d13 = blockPos.getZ() + random.nextDouble();
                    world.spawnParticle(
                            EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0, 0.0, 0.0, new int[0]);
                    k++;
                    if (i != 0) {
                        break;
                    }
                }
            }

            if (Blocks.fire.canCatchFire(world, blockPos.offsetNorth())) {
                int l = 0;

                while (l < 2) {
                    double d4 = blockPos.getX() + random.nextDouble();
                    double d9 = blockPos.getY() + random.nextDouble();
                    double d14 = blockPos.getZ() + random.nextDouble() * 0.1F;
                    world.spawnParticle(
                            EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0, 0.0, 0.0, new int[0]);
                    l++;
                    if (i != 0) {
                        break;
                    }
                }
            }

            if (Blocks.fire.canCatchFire(world, blockPos.offsetSouth())) {
                int i1 = 0;

                while (i1 < 2) {
                    double d5 = blockPos.getX() + random.nextDouble();
                    double d10 = blockPos.getY() + random.nextDouble();
                    double d15 = blockPos.getZ() + 1 - random.nextDouble() * 0.1F;
                    world.spawnParticle(
                            EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0, 0.0, 0.0, new int[0]);
                    i1++;
                    if (i != 0) {
                        break;
                    }
                }
            }

            if (!Blocks.fire.canCatchFire(world, blockPos.offsetUp())) {
                return;
            }

            int j1 = 0;

            do {
                if (j1 >= 2) {
                    return;
                }

                double d6 = blockPos.getX() + random.nextDouble();
                double d11 = blockPos.getY() + 1 - random.nextDouble() * 0.1F;
                double d16 = blockPos.getZ() + random.nextDouble();
                world.spawnParticle(
                        EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0, 0.0, 0.0, new int[0]);
                j1++;
            } while (i == 0);
        }

        int k1 = 0;

        while (k1 < 3) {
            double d7 = blockPos.getX() + random.nextDouble();
            double d12 = blockPos.getY() + random.nextDouble() * 0.5 + 0.5;
            double d17 = blockPos.getZ() + random.nextDouble();
            world.spawnParticle(
                    EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0, 0.0, 0.0, new int[0]);
            k1++;
            if (i != 0) {
                break;
            }
        }
    }

    protected BlockFire() {
        super(Material.fire);
        this.flammabilities = Maps.newIdentityHashMap();
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockFire.AGE, 0)
                        .withProperty(net.minecraft.block.BlockFire.FLIP, false)
                        .withProperty(net.minecraft.block.BlockFire.ALT, false)
                        .withProperty(net.minecraft.block.BlockFire.NORTH, false)
                        .withProperty(net.minecraft.block.BlockFire.EAST, false)
                        .withProperty(net.minecraft.block.BlockFire.SOUTH, false)
                        .withProperty(net.minecraft.block.BlockFire.WEST, false)
                        .withProperty(net.minecraft.block.BlockFire.UPPER, 0));
        this.setTickRandomly(true);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                || this.canNeighborCatchFire(world, blockPos);
    }

    private int getEncouragement(Block block) {
        Integer integer = (Integer) this.encouragements.get(block);
        return integer == null ? 0 : integer;
    }

    public boolean requiresUpdates() {
        return false;
    }

    private void catchOnFire(World world, BlockPos blockPos, int i, Random random, int j) {
        int j1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int l = this.getFlammability(world.getBlockState(blockPos).getBlock());
        int k = j1;
        if (random.nextInt(i) < l) {
            BlockState blockState;
            {
                blockState = world.getBlockState(blockPos);
                if (random.nextInt(j + 10) < 5 && !world.canLightningStrike(blockPos)) {
                    int i1 = j + random.nextInt(5) / 4;
                    if (i1 > 15) {
                        i1 = 15;
                    }

                    world.setBlockState(
                            blockPos,
                            this.getDefaultState()
                                    .withProperty(net.minecraft.block.BlockFire.AGE, i1),
                            3);
                    if (!(k == 0)) {

                        world.setBlockToAir(blockPos);
                    }
                } else {

                    world.setBlockToAir(blockPos);
                }
            }

            if (blockState.getBlock() == Blocks.tnt) {
                Blocks.tnt.onBlockDestroyedByPlayer(
                        world, blockPos, blockState.withProperty(BlockTNT.EXPLODE, true));
            }
        }
    }

    public int tickRate(World world) {
        return 30;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                && !this.canNeighborCatchFire(world, blockPos)) {
            world.setBlockToAir(blockPos);
        }
    }
}
