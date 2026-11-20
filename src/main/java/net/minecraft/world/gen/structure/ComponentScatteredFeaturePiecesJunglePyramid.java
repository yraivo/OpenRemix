package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ComponentScatteredFeaturePiecesJunglePyramid
        extends ComponentScatteredFeaturePiecesFeature {
    private static ComponentScatteredFeaturePiecesJunglePyramidStones
            junglePyramidsRandomScatteredStones;
    private static List mc;
    private boolean mx;
    private boolean mv;

    private boolean Bb;

    private static List kI;
    private boolean sE;

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.sE = compoundTag.getBoolean("placedMainChest");
        this.mx = compoundTag.getBoolean("placedHiddenChest");
        this.mv = compoundTag.getBoolean("placedTrap1");
        this.Bb = compoundTag.getBoolean("placedTrap2");
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("placedMainChest", this.sE);
        compoundTag.setBoolean("placedHiddenChest", this.mx);
        compoundTag.setBoolean("placedTrap1", this.mv);
        compoundTag.setBoolean("placedTrap2", this.Bb);
    }

    public ComponentScatteredFeaturePiecesJunglePyramid(Random random, int i, int j) {
        super(random, i, 64, j, 12, 10, 15);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (!this.method_0e94e718e78ead49f0b8(world, structureBoundingBox, 0)) {
            return false;
        }

        int j = this.getMetadataWithOffset(Blocks.stone_stairs, 3);
        int k = this.getMetadataWithOffset(Blocks.stone_stairs, 2);
        int l = this.getMetadataWithOffset(Blocks.stone_stairs, 0);
        int metadataWithOffset = this.getMetadataWithOffset(Blocks.stone_stairs, 1);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                0,
                -4,
                0,
                this.scatteredFeatureSizeX - 1,
                0,
                this.scatteredFeatureSizeZ - 1,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                1,
                2,
                9,
                2,
                2,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                1,
                12,
                9,
                2,
                12,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                1,
                3,
                2,
                2,
                11,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                9,
                1,
                3,
                9,
                2,
                11,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                1,
                10,
                6,
                1,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                13,
                10,
                6,
                13,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                2,
                1,
                6,
                12,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                10,
                3,
                2,
                10,
                6,
                12,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                3,
                2,
                9,
                3,
                12,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                6,
                2,
                9,
                6,
                12,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                3,
                7,
                3,
                8,
                7,
                11,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                8,
                4,
                7,
                8,
                10,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithAir(world, structureBoundingBox, 3, 1, 3, 8, 2, 11);
        this.fillWithAir(world, structureBoundingBox, 4, 3, 6, 7, 3, 9);
        this.fillWithAir(world, structureBoundingBox, 2, 4, 2, 9, 5, 12);
        this.fillWithAir(world, structureBoundingBox, 4, 6, 5, 7, 6, 9);
        this.fillWithAir(world, structureBoundingBox, 5, 7, 6, 6, 7, 8);
        this.fillWithAir(world, structureBoundingBox, 5, 1, 2, 6, 2, 2);
        this.fillWithAir(world, structureBoundingBox, 5, 2, 12, 6, 2, 12);
        this.fillWithAir(world, structureBoundingBox, 5, 5, 1, 6, 5, 1);
        this.fillWithAir(world, structureBoundingBox, 5, 5, 13, 6, 5, 13);
        this.setBlockState(world, Blocks.air.getDefaultState(), 1, 5, 5, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 10, 5, 5, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 1, 5, 9, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 10, 5, 9, structureBoundingBox);
        int j1 = 0;

        while (j1 <= 14) {
            this.fillWithRandomizedBlocks(
                    world,
                    structureBoundingBox,
                    2,
                    4,
                    j1,
                    2,
                    5,
                    j1,
                    false,
                    random,
                    net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                            .junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(
                    world,
                    structureBoundingBox,
                    4,
                    4,
                    j1,
                    4,
                    5,
                    j1,
                    false,
                    random,
                    net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                            .junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(
                    world,
                    structureBoundingBox,
                    7,
                    4,
                    j1,
                    7,
                    5,
                    j1,
                    false,
                    random,
                    net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                            .junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(
                    world,
                    structureBoundingBox,
                    9,
                    4,
                    j1,
                    9,
                    5,
                    j1,
                    false,
                    random,
                    net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                            .junglePyramidsRandomScatteredStones);
            j1 += 14;
            if (i != 0) {
                break;
            }
        }

        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                5,
                6,
                0,
                6,
                6,
                0,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        j1 = (byte) 0;

        label74:
        while (j1 <= 11) {
            byte b0 = 2;

            while (true) {
                if (b0 <= 12) {
                    this.fillWithRandomizedBlocks(
                            world,
                            structureBoundingBox,
                            j1,
                            4,
                            b0,
                            j1,
                            5,
                            b0,
                            false,
                            random,
                            net.minecraft.world.gen.structure
                                    .ComponentScatteredFeaturePiecesJunglePyramid
                                    .junglePyramidsRandomScatteredStones);
                    b0 += 2;
                    if (i == 0) {
                        continue;
                    }
                }

                this.fillWithRandomizedBlocks(
                        world,
                        structureBoundingBox,
                        j1,
                        6,
                        5,
                        j1,
                        6,
                        5,
                        false,
                        random,
                        net.minecraft.world.gen.structure
                                .ComponentScatteredFeaturePiecesJunglePyramid
                                .junglePyramidsRandomScatteredStones);
                this.fillWithRandomizedBlocks(
                        world,
                        structureBoundingBox,
                        j1,
                        6,
                        9,
                        j1,
                        6,
                        9,
                        false,
                        random,
                        net.minecraft.world.gen.structure
                                .ComponentScatteredFeaturePiecesJunglePyramid
                                .junglePyramidsRandomScatteredStones);
                j1 += 11;
                if (i != 0) {
                    break label74;
                }
                break;
            }
        }

        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                7,
                2,
                2,
                9,
                2,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                9,
                7,
                2,
                9,
                9,
                2,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                7,
                12,
                2,
                9,
                12,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                9,
                7,
                12,
                9,
                9,
                12,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                9,
                4,
                4,
                9,
                4,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                7,
                9,
                4,
                7,
                9,
                4,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                9,
                10,
                4,
                9,
                10,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                7,
                9,
                10,
                7,
                9,
                10,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                5,
                9,
                7,
                6,
                9,
                7,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 5, 9, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 6, 9, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(k), 5, 9, 8, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(k), 6, 9, 8, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 4, 0, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 5, 0, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 6, 0, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 7, 0, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 4, 1, 8, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 4, 2, 9, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 4, 3, 10, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 7, 1, 8, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 7, 2, 9, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(j), 7, 3, 10, structureBoundingBox);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                9,
                4,
                1,
                9,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                7,
                1,
                9,
                7,
                1,
                9,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                10,
                7,
                2,
                10,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                5,
                4,
                5,
                6,
                4,
                5,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.setBlockState(
                world, Blocks.stone_stairs.getStateFromMeta(l), 4, 4, 5, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_stairs.getStateFromMeta(metadataWithOffset),
                7,
                4,
                5,
                structureBoundingBox);
        j1 = 0;

        while (j1 < 4) {
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(k),
                    5,
                    0 - j1,
                    6 + j1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(k),
                    6,
                    0 - j1,
                    6 + j1,
                    structureBoundingBox);
            this.fillWithAir(world, structureBoundingBox, 5, 0 - j1, 7 + j1, 6, 0 - j1, 9 + j1);
            j1++;
            if (i != 0) {
                break;
            }
        }

        this.fillWithAir(world, structureBoundingBox, 1, -3, 12, 10, -1, 13);
        this.fillWithAir(world, structureBoundingBox, 1, -3, 1, 3, -1, 13);
        this.fillWithAir(world, structureBoundingBox, 1, -3, 1, 9, -1, 5);
        byte b1 = 1;

        while (b1 <= 13) {
            this.fillWithRandomizedBlocks(
                    world,
                    structureBoundingBox,
                    1,
                    -3,
                    b1,
                    1,
                    -2,
                    b1,
                    false,
                    random,
                    net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                            .junglePyramidsRandomScatteredStones);
            b1 += 2;
            if (i != 0) {
                break;
            }
        }

        b1 = 2;

        while (b1 <= 12) {
            this.fillWithRandomizedBlocks(
                    world,
                    structureBoundingBox,
                    1,
                    -1,
                    b1,
                    3,
                    -1,
                    b1,
                    false,
                    random,
                    net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                            .junglePyramidsRandomScatteredStones);
            b1 += 2;
            if (i != 0) {
                break;
            }
        }

        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                -2,
                1,
                5,
                -2,
                1,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                7,
                -2,
                1,
                9,
                -2,
                1,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                6,
                -3,
                1,
                6,
                -3,
                1,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                6,
                -1,
                1,
                6,
                -1,
                1,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.setBlockState(
                world,
                Blocks.tripwire_hook
                        .getStateFromMeta(
                                this.getMetadataWithOffset(
                                        Blocks.tripwire_hook, Facing.EAST.getHorizontalIndex()))
                        .withProperty(BlockTripWireHook.ATTACHED, true),
                1,
                -3,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.tripwire_hook
                        .getStateFromMeta(
                                this.getMetadataWithOffset(
                                        Blocks.tripwire_hook, Facing.WEST.getHorizontalIndex()))
                        .withProperty(BlockTripWireHook.ATTACHED, true),
                4,
                -3,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, true),
                2,
                -3,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, true),
                3,
                -3,
                8,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 5, -3, 7, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 5, -3, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 5, -3, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 5, -3, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 5, -3, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 5, -3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 5, -3, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 4, -3, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 3, -3, 1, structureBoundingBox);
        if (!this.mv) {
            this.mv =
                    this.generateDispenserContents(
                            world,
                            structureBoundingBox,
                            random,
                            3,
                            -2,
                            1,
                            Facing.NORTH.getIndex(),
                            kI,
                            2);
        }

        this.setBlockState(world, Blocks.vine.getStateFromMeta(15), 3, -2, 2, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.tripwire_hook
                        .getStateFromMeta(
                                this.getMetadataWithOffset(
                                        Blocks.tripwire_hook, Facing.NORTH.getHorizontalIndex()))
                        .withProperty(BlockTripWireHook.ATTACHED, true),
                7,
                -3,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.tripwire_hook
                        .getStateFromMeta(
                                this.getMetadataWithOffset(
                                        Blocks.tripwire_hook, Facing.SOUTH.getHorizontalIndex()))
                        .withProperty(BlockTripWireHook.ATTACHED, true),
                7,
                -3,
                5,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, true),
                7,
                -3,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, true),
                7,
                -3,
                3,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, true),
                7,
                -3,
                4,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 8, -3, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 9, -3, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 9, -3, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 9, -3, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 9, -2, 4, structureBoundingBox);
        if (!this.Bb) {
            this.Bb =
                    this.generateDispenserContents(
                            world,
                            structureBoundingBox,
                            random,
                            9,
                            -2,
                            3,
                            Facing.WEST.getIndex(),
                            kI,
                            2);
        }

        this.setBlockState(world, Blocks.vine.getStateFromMeta(15), 8, -1, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.vine.getStateFromMeta(15), 8, -2, 3, structureBoundingBox);
        if (!this.sE) {
            this.sE =
                    this.generateChestContents(
                            world,
                            structureBoundingBox,
                            random,
                            8,
                            -3,
                            3,
                            WeightedRandomChestContent.func_177629_a(
                                    mc,
                                    new WeightedRandomChestContent[] {
                                        Items.enchanted_book.getRandomEnchantedBook(random)
                                    }),
                            2 + random.nextInt(5));
        }

        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 9, -3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 8, -3, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 4, -3, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 5, -2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 5, -1, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 6, -3, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 7, -2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 7, -1, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 8, -3, 5, structureBoundingBox);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                9,
                -1,
                1,
                9,
                -1,
                5,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithAir(world, structureBoundingBox, 8, -3, 8, 10, -1, 10);
        this.setBlockState(
                world,
                Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META),
                8,
                -2,
                11,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META),
                9,
                -2,
                11,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META),
                10,
                -2,
                11,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.lever.getStateFromMeta(
                        BlockLever.getMetadataForFacing(
                                Facing.getFront(
                                        this.getMetadataWithOffset(
                                                Blocks.lever, Facing.NORTH.getIndex())))),
                8,
                -2,
                12,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.lever.getStateFromMeta(
                        BlockLever.getMetadataForFacing(
                                Facing.getFront(
                                        this.getMetadataWithOffset(
                                                Blocks.lever, Facing.NORTH.getIndex())))),
                9,
                -2,
                12,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.lever.getStateFromMeta(
                        BlockLever.getMetadataForFacing(
                                Facing.getFront(
                                        this.getMetadataWithOffset(
                                                Blocks.lever, Facing.NORTH.getIndex())))),
                10,
                -2,
                12,
                structureBoundingBox);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                8,
                -3,
                8,
                8,
                -3,
                10,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                10,
                -3,
                8,
                10,
                -3,
                10,
                false,
                random,
                net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones);
        this.setBlockState(
                world, Blocks.mossy_cobblestone.getDefaultState(), 10, -2, 9, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 8, -2, 9, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 8, -2, 10, structureBoundingBox);
        this.setBlockState(
                world, Blocks.redstone_wire.getDefaultState(), 10, -1, 9, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sticky_piston.getStateFromMeta(Facing.UP.getIndex()),
                9,
                -2,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sticky_piston.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.sticky_piston, Facing.WEST.getIndex())),
                10,
                -2,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sticky_piston.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.sticky_piston, Facing.WEST.getIndex())),
                10,
                -1,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.unpowered_repeater.getStateFromMeta(
                        this.getMetadataWithOffset(
                                Blocks.unpowered_repeater, Facing.NORTH.getHorizontalIndex())),
                10,
                -2,
                10,
                structureBoundingBox);
        if (!this.mx) {
            this.mx =
                    this.generateChestContents(
                            world,
                            structureBoundingBox,
                            random,
                            9,
                            -3,
                            10,
                            WeightedRandomChestContent.func_177629_a(
                                    mc,
                                    new WeightedRandomChestContent[] {
                                        Items.enchanted_book.getRandomEnchantedBook(random)
                                    }),
                            2 + random.nextInt(5));
        }

        return true;
    }

    static {
        mc =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
                            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
                            new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15),
                            new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2),
                            new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20),
                            new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16),
                            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
                            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
                        });
        kI =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.arrow, 0, 2, 7, 30)
                        });
        net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesJunglePyramid
                        .junglePyramidsRandomScatteredStones =
                new ComponentScatteredFeaturePiecesJunglePyramidStones(
                        (ScatteredFeatureAccessMarker) null);
    }

    public ComponentScatteredFeaturePiecesJunglePyramid() {}
}
