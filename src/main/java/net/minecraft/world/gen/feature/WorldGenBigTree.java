package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLogEnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WorldGenBigTree extends WorldGenAbstractTree {
    double branchSlope;
    List field_175948_j;
    double leafDensity;
    int height;
    private World world;
    int heightLimit;
    private Random rand;
    int trunkSize;
    private BlockPos basePos = BlockPos.ORIGIN;
    private static String[] packedTextFragments;
    int heightLimitLimit;
    double heightAttenuation = 0.618;
    double scaleWidth;
    int leafDistanceLimit;

    void generateLeaves() {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        Iterator iterator = this.field_175948_j.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            WorldGenBigTreeFoliageCoordinates worldGenBigTreeFoliageCoordinates =
                    (WorldGenBigTreeFoliageCoordinates) iterator.next();
            this.generateLeafNode(worldGenBigTreeFoliageCoordinates);
            if (textValue == null) {
                break;
            }
        }
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    void generateLeafLayer(BlockPos blockPos, float f, Block block) {
        int i = (int) (f + 0.618);
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int j = -i;
        String textValue = text;

        while (j <= i) {
            int k = -i;

            while (true) {
                if (k <= i) {
                    if (Math.pow(Math.abs(j) + 0.5, 2.0) + Math.pow(Math.abs(k) + 0.5, 2.0)
                            <= f * f) {
                        BlockPos blockPosValue = blockPos.add(j, 0, k);
                        Material material =
                                this.world.getBlockState(blockPosValue).getBlock().getMaterial();
                        if (material == Material.AIR || material == Material.leaves) {
                            this.func_175905_a(this.world, blockPosValue, block, 0);
                        }
                    }

                    k++;
                    if (textValue != null) {
                        continue;
                    }
                }

                j++;
                if (textValue == null) {
                    return;
                }
                break;
            }
        }
    }

    float K(int i) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        if (i < this.heightLimit * 0.3F) {
            return -1.0F;
        }

        float fx = this.heightLimit / 2.0F;
        float f1 = fx - i;
        float f2 = MathHelper.sqrt(fx * fx - f1 * f1);
        if (f1 == 0.0F) {
            f2 = fx;
            if (text != null) {
                return f2 * 0.5F;
            }
        }

        return Math.abs(f1) >= fx ? 0.0F : f2 * 0.5F;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.world.gen.feature.WorldGenBigTree.packedTextFragments[0] = new String("gUjZu");
    }

    // endregion

    boolean leafNodeNeedsBase(int i) {
        return i >= this.heightLimit * 0.2;
    }

    void generateLeafNodeBases() {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        Iterator iterator = this.field_175948_j.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            WorldGenBigTreeFoliageCoordinates worldGenBigTreeFoliageCoordinates =
                    (WorldGenBigTreeFoliageCoordinates) iterator.next();
            int i = worldGenBigTreeFoliageCoordinates.aW();
            BlockPos blockPos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());
            if (this.leafNodeNeedsBase(i - this.basePos.getY())) {
                this.func_175937_a(blockPos, worldGenBigTreeFoliageCoordinates, Blocks.log);
            }

            if (textValue == null) {
                break;
            }
        }
    }

    float leafSize(int i) {
        return i >= 0 && i < this.leafDistanceLimit
                ? (i != 0 && i != this.leafDistanceLimit - 1 ? 3.0F : 2.0F)
                : -1.0F;
    }

    void generateLeafNodeList() {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        this.height = (int) (this.heightLimit * this.heightAttenuation);
        String textValue = text;
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }

        int i = (int) (1.382 + Math.pow(this.leafDensity * this.heightLimit / 13.0, 2.0));
        if (i < 1) {
            i = 1;
        }

        int j = this.basePos.getY() + this.height;
        int k = this.heightLimit - this.leafDistanceLimit;
        this.field_175948_j = Lists.newArrayList();
        this.field_175948_j.add(new WorldGenBigTreeFoliageCoordinates(this.basePos.offsetUp(k), j));

        while (k >= 0) {
            float fx = this.K(k);
            if (fx >= 0.0F) {
                int l = 0;

                while (l < i) {
                    double d0 = this.scaleWidth * fx * (this.rand.nextFloat() + 0.328);
                    double d1 = this.rand.nextFloat() * 2.0F * Math.PI;
                    double d2 = d0 * Math.sin(d1) + 0.5;
                    double d3 = d0 * Math.cos(d1) + 0.5;
                    BlockPos blockPos = this.basePos.add(d2, k - 1, d3);
                    BlockPos blockPosValue = blockPos.offsetUp(this.leafDistanceLimit);
                    if (this.checkBlockLine(blockPos, blockPosValue) == -1) {
                        int deltaX = this.basePos.getX() - blockPos.getX();
                        int j1 = this.basePos.getZ() - blockPos.getZ();
                        double d4 =
                                blockPos.getY()
                                        - Math.sqrt(deltaX * deltaX + j1 * j1) * this.branchSlope;
                        int k1 = d4 > j ? j : (int) d4;
                        BlockPos blockPosValue2 =
                                new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());
                        if (this.checkBlockLine(blockPosValue2, blockPos) == -1) {
                            this.field_175948_j.add(
                                    new WorldGenBigTreeFoliageCoordinates(
                                            blockPos, blockPosValue2.getY()));
                        }
                    }

                    l++;
                    if (textValue == null) {
                        break;
                    }
                }
            }

            k--;
            if (textValue == null) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            WorldGenerator.b(packedTextFragments[0]);
        }
    }

    private BlockLogEnumAxis getLogAxis(
            BlockPos blockPos, BlockPos blockPosValue) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        BlockLogEnumAxis blockLogEnumAxis = BlockLogEnumAxis.Y;
        int i = Math.abs(blockPosValue.getX() - blockPos.getX());
        int j = Math.abs(blockPosValue.getZ() - blockPos.getZ());
        String textValue = text;
        int k = Math.max(i, j);
        if (k > 0) {
            if (i == k) {
                blockLogEnumAxis = BlockLogEnumAxis.X;
                if (textValue != null) {
                    return blockLogEnumAxis;
                }
            }

            if (j == k) {
                blockLogEnumAxis = BlockLogEnumAxis.Z;
            }
        }

        return blockLogEnumAxis;
    }

    void generateTrunk() {
        BlockPos bDNValue = this.basePos;
        BlockPos blockPos = this.basePos.offsetUp(this.height);
        Block bmI = Blocks.log;
        this.func_175937_a(bDNValue, blockPos, bmI);
        if (this.trunkSize == 2) {
            this.func_175937_a(bDNValue.offsetEast(), blockPos.offsetEast(), bmI);
            this.func_175937_a(
                    bDNValue.offsetEast().offsetSouth(), blockPos.offsetEast().offsetSouth(), bmI);
            this.func_175937_a(bDNValue.offsetSouth(), blockPos.offsetSouth(), bmI);
        }
    }

    private int getGreatestDistance(BlockPos blockPos) {
        int i = MathHelper.abs(blockPos.getX());
        int j = MathHelper.abs(blockPos.getY());
        int k = MathHelper.abs(blockPos.getZ());
        return k > i && k > j ? k : (j > i ? j : i);
    }

    void generateLeafNode(BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        int i = 0;
        String textValue = text;

        while (i < this.leafDistanceLimit) {
            this.generateLeafLayer(
                    blockPos.offsetUp(i), this.leafSize(i), Blocks.leaves);
            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    int checkBlockLine(BlockPos blockPos, BlockPos blockPosValue) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        BlockPos blockPosValue2 =
                blockPosValue.add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        int i = this.getGreatestDistance(blockPosValue2);
        float fx = (float) blockPosValue2.getX() / i;
        float f1 = (float) blockPosValue2.getY() / i;
        String textValue = text;
        float f2 = (float) blockPosValue2.getZ() / i;
        if (i == 0) {
            return -1;
        }

        int j = 0;

        while (j <= i) {
            BlockPos blockPosValue3 = blockPos.add(0.5F + j * fx, 0.5F + j * f1, 0.5F + j * f2);
            if (!this.func_150523_a(this.world.getBlockState(blockPosValue3).getBlock())) {
                return j;
            }

            j++;
            if (textValue == null) {
                break;
            }
        }

        return -1;
    }

    private boolean validTreeLocation() {
        Block block = this.world.getBlockState(this.basePos.offsetDown()).getBlock();
        if (block != Blocks.dirt && block != Blocks.grass && block != Blocks.farmland) {
            return false;
        }

        int heightLimitValue =
                this.checkBlockLine(this.basePos, this.basePos.offsetUp(this.heightLimit - 1));
        if (heightLimitValue == -1) {
            return true;
        }

        if (heightLimitValue < 6) {
            return false;
        }

        this.heightLimit = heightLimitValue;
        return true;
    }

    public boolean generate(World bhwValue, Random random, BlockPos bDNValue) {
        this.world = bhwValue;
        this.basePos = bDNValue;
        this.rand = new Random(random.nextLong());
        if (this.heightLimit == 0) {
            this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
        }

        if (!this.validTreeLocation()) {
            return false;
        }

        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateTrunk();
        this.generateLeafNodeBases();
        return true;
    }

    public void func_175904_e() {
        this.leafDistanceLimit = 5;
    }

    public WorldGenBigTree(boolean flag) {
        super(flag);
        this.branchSlope = 0.381;
        this.scaleWidth = 1.0;
        this.leafDensity = 1.0;
        this.trunkSize = 1;
        this.heightLimitLimit = 12;
        this.leafDistanceLimit = 4;
    }

    void func_175937_a(BlockPos blockPos, BlockPos blockPosValue, Block block) {
        String text = WorldGenerator.method_c60693f457aa70be7160();
        BlockPos blockPosValue2 =
                blockPosValue.add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        int i = this.getGreatestDistance(blockPosValue2);
        String textValue = text;
        float fx = (float) blockPosValue2.getX() / i;
        float f1 = (float) blockPosValue2.getY() / i;
        float f2 = (float) blockPosValue2.getZ() / i;
        int j = 0;

        while (j <= i) {
            BlockPos blockPosValue3 = blockPos.add(0.5F + j * fx, 0.5F + j * f1, 0.5F + j * f2);
            BlockLogEnumAxis blockLogEnumAxis =
                    this.getLogAxis(blockPos, blockPosValue3);
            this.setBlockAndNotifyAdequately(
                    this.world,
                    blockPosValue3,
                    block.getDefaultState().withProperty(BlockLog.AXIS_PROP, blockLogEnumAxis));
            j++;
            if (textValue == null) {
                break;
            }
        }
    }
}
