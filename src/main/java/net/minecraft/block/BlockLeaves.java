package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

import java.util.Random;

public abstract class BlockLeaves extends BlockLeavesBase {
    protected boolean isTransparent;
    protected int iconIndex;
    public static PropertyBool DECAYABLE;
    int[] surroundings;

    public static PropertyBool CHECK_DECAY;

    public boolean isOpaqueCube() {
        return !this.fancyGraphics;
    }

    public abstract BlockPlanksEnumType getWoodType(int i);

    // region String decoding support

    // endregion

    public int getRenderColor(BlockState blockState) {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    public BlockLeaves() {
        super(Material.leaves, false);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setStepSound(net.minecraft.block.Block.soundTypeGrass);
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        return BiomeColorHelper.getFoliageColorAtPos(iBlockAccess, blockPos);
    }

    // region String decoding support

    // endregion

    public void setGraphicsLevel(boolean akoValue) {
        this.isTransparent = akoValue;
        this.fancyGraphics = akoValue;
        this.iconIndex = akoValue ? 0 : 1;
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (world.canLightningStrike(blockPos.offsetUp())
                && !World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown())
                && random.nextInt(15) == 1) {
            double d0 = blockPos.getX() + random.nextFloat();
            double d1 = blockPos.getY() - 0.05;
            double d2 = blockPos.getZ() + random.nextFloat();
            world.spawnParticle(
                    EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
        }
    }

    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColor(0.5, 1.0);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        byte b0 = 1;
        int j = b0 + 1;
        int i2 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int k = blockPos.getX();
        int l = blockPos.getY();
        int i = i2;
        int z = blockPos.getZ();
        if (world.isAreaLoaded(
                new BlockPos(k - j, l - j, z - j), new BlockPos(k + j, l + j, z + j))) {
            int j1 = -b0;

            while (j1 <= b0) {
                int k1 = -b0;

                while (k1 <= b0) {
                    int l1 = -b0;

                    while (l1 <= b0) {
                        BlockPos blockPosValue = blockPos.add(j1, k1, l1);
                        BlockState blockStateValue = world.getBlockState(blockPosValue);
                        if (blockStateValue.getBlock().getMaterial() == Material.leaves
                                && !(Boolean)
                                        blockStateValue.getValue(net.minecraft.block.BlockLeaves.CHECK_DECAY)) {
                            world.setBlockState(
                                    blockPosValue,
                                    blockStateValue.withProperty(
                                            net.minecraft.block.BlockLeaves.CHECK_DECAY, true),
                                    4);
                        }

                        l1++;
                        if (i != 0) {
                            break;
                        }
                    }

                    k1++;
                    if (i != 0) {
                        break;
                    }
                }

                j1++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    static {
        net.minecraft.block.BlockLeaves.DECAYABLE = PropertyBool.create("decayable");
        net.minecraft.block.BlockLeaves.CHECK_DECAY = PropertyBool.create("check_decay");
    }

    protected int getSaplingDropChance(BlockState blockState) {
        return 20;
    }

    public int quantityDropped(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.sapling);
    }

    private void destroy(World world, BlockPos blockPos) {
        this.dropBlockAsItem(world, blockPos, world.getBlockState(blockPos), 0);
        world.setBlockToAir(blockPos);
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        if (!world.isRemote) {
            int j = this.getSaplingDropChance(blockState);
            if (i > 0) {
                j -= 2 << i;
                if (j < 10) {
                    j = 10;
                }
            }

            if (world.rand.nextInt(j) == 0) {
                Item item = this.getItemDropped(blockState, world.rand, i);
                spawnAsEntity(
                        world, blockPos, new ItemStack(item, 1, this.damageDropped(blockState)));
            }

            j = 200;
            if (i > 0) {
                j -= 10 << i;
                if (j < 40) {
                    j = 40;
                }
            }

            this.dropApple(world, blockPos, blockState, j);
        }
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return this.isTransparent ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
    }

    public boolean isVisuallyOpaque() {
        return false;
    }

    protected void dropApple(World world, BlockPos blockPos, BlockState blockState, int i) {}

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isRemote
                && (Boolean) blockState.getValue(net.minecraft.block.BlockLeaves.CHECK_DECAY)
                && (Boolean) blockState.getValue(net.minecraft.block.BlockLeaves.DECAYABLE)) {
            byte b0 = 4;
            int j = b0 + 1;
            int k = blockPos.getX();
            int l = blockPos.getY();
            int z = blockPos.getZ();
            byte b1 = 32;
            int j1 = b1 * b1;
            int k1 = b1 / 2;
            if (this.surroundings == null) {
                this.surroundings = new int[b1 * b1 * b1];
            }

            if (world.isAreaLoaded(
                    new BlockPos(k - j, l - j, z - j), new BlockPos(k + j, l + j, z + j))) {
                int l1 = -b0;

                label128:
                while (l1 <= b0) {
                    int i2 = -b0;

                    while (true) {
                        if (i2 <= b0) {
                            int j2 = -b0;

                            while (j2 <= b0) {
                                label118:
                                {
                                    Block block =
                                            world.getBlockState(
                                                            new BlockPos(k + l1, l + i2, z + j2))
                                                    .getBlock();
                                    if (block != Blocks.log && block != Blocks.log2) {
                                        if (block.getMaterial() == Material.leaves) {
                                            this.surroundings[
                                                            (l1 + k1) * j1
                                                                    + (i2 + k1) * b1
                                                                    + j2
                                                                    + k1] =
                                                    -2;
                                            if (i != 0) {
                                                break label118;
                                            }
                                        }

                                        this.surroundings[
                                                        (l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] =
                                                -1;
                                        if (i != 0) {
                                            break label118;
                                        }
                                    }

                                    this.surroundings[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] =
                                            0;
                                }

                                j2++;
                                if (i == 0) {
                                    break;
                                }
                            }

                            i2++;
                            if (i != 0) {
                                continue;
                            }
                        }

                        l1++;
                        if (i == 0) {
                            break label128;
                        }
                        break;
                    }
                }

                l1 = 1;

                while (l1 <= 4) {
                    int l2 = -b0;

                    while (l2 <= b0) {
                        int i3 = -b0;

                        while (i3 <= b0) {
                            int j3 = -b0;

                            while (j3 <= b0) {
                                if (this.surroundings[(l2 + k1) * j1 + (i3 + k1) * b1 + j3 + k1]
                                        == l1 - 1) {
                                    if (this.surroundings[
                                                    (l2 + k1 - 1) * j1 + (i3 + k1) * b1 + j3 + k1]
                                            == -2) {
                                        this.surroundings[
                                                        (l2 + k1 - 1) * j1
                                                                + (i3 + k1) * b1
                                                                + j3
                                                                + k1] =
                                                l1;
                                    }

                                    if (this.surroundings[
                                                    (l2 + k1 + 1) * j1 + (i3 + k1) * b1 + j3 + k1]
                                            == -2) {
                                        this.surroundings[
                                                        (l2 + k1 + 1) * j1
                                                                + (i3 + k1) * b1
                                                                + j3
                                                                + k1] =
                                                l1;
                                    }

                                    if (this.surroundings[
                                                    (l2 + k1) * j1 + (i3 + k1 - 1) * b1 + j3 + k1]
                                            == -2) {
                                        this.surroundings[
                                                        (l2 + k1) * j1
                                                                + (i3 + k1 - 1) * b1
                                                                + j3
                                                                + k1] =
                                                l1;
                                    }

                                    if (this.surroundings[
                                                    (l2 + k1) * j1 + (i3 + k1 + 1) * b1 + j3 + k1]
                                            == -2) {
                                        this.surroundings[
                                                        (l2 + k1) * j1
                                                                + (i3 + k1 + 1) * b1
                                                                + j3
                                                                + k1] =
                                                l1;
                                    }

                                    if (this.surroundings[
                                                    (l2 + k1) * j1 + (i3 + k1) * b1 + (j3 + k1 - 1)]
                                            == -2) {
                                        this.surroundings[
                                                        (l2 + k1) * j1
                                                                + (i3 + k1) * b1
                                                                + (j3 + k1 - 1)] =
                                                l1;
                                    }

                                    if (this.surroundings[
                                                    (l2 + k1) * j1 + (i3 + k1) * b1 + j3 + k1 + 1]
                                            == -2) {
                                        this.surroundings[
                                                        (l2 + k1) * j1
                                                                + (i3 + k1) * b1
                                                                + j3
                                                                + k1
                                                                + 1] =
                                                l1;
                                    }
                                }

                                j3++;
                                if (i == 0) {
                                    break;
                                }
                            }

                            i3++;
                            if (i == 0) {
                                break;
                            }
                        }

                        l2++;
                        if (i == 0) {
                            break;
                        }
                    }

                    l1++;
                    if (i == 0) {
                        break;
                    }
                }
            }

            int k2 = this.surroundings[k1 * j1 + k1 * b1 + k1];
            if (k2 >= 0) {
                world.setBlockState(
                        blockPos, blockState.withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, false), 4);
                if (i != 0) {
                    return;
                }
            }

            this.destroy(world, blockPos);
        }
    }
}
