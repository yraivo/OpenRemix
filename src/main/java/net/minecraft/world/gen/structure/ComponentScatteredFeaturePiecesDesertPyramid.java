package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockSandStoneEnumType;
import net.minecraft.block.BlockStoneSlabEnumType;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesFeature;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class ComponentScatteredFeaturePiecesDesertPyramid
        extends ComponentScatteredFeaturePiecesFeature {
    private boolean[] bMf = new boolean[4];
    private static List itemsToGenerateInTemple;

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int l2 = StructureComponent.cR();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                -4,
                0,
                this.scatteredFeatureSizeX - 1,
                0,
                this.scatteredFeatureSizeZ - 1,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        int i = l2;
        int j = 1;

        while (j <= 9) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    j,
                    j,
                    j,
                    this.scatteredFeatureSizeX - 1 - j,
                    j,
                    this.scatteredFeatureSizeZ - 1 - j,
                    Blocks.sandstone.getDefaultState(),
                    Blocks.sandstone.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    j + 1,
                    j,
                    j + 1,
                    this.scatteredFeatureSizeX - 2 - j,
                    j,
                    this.scatteredFeatureSizeZ - 2 - j,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
            j++;
            if (i == 0) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
                break;
            }
        }

        j = 0;

        label69:
        while (j < this.scatteredFeatureSizeX) {
            int k = 0;

            while (true) {
                if (k < this.scatteredFeatureSizeZ) {
                    byte b0 = -5;
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.sandstone.getDefaultState(),
                            j,
                            b0,
                            k,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    break label69;
                }
                break;
            }
        }

        j = this.getMetadataWithOffset(Blocks.sandstone_stairs, 3);
        int j2 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 2);
        int k2 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 0);
        int l = this.getMetadataWithOffset(Blocks.sandstone_stairs, 1);
        int i1 = ~EnumDyeColor.ORANGE.getDyeColorDamage() & 15;
        int j1 = ~EnumDyeColor.BLUE.getDyeColorDamage() & 15;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                4,
                9,
                4,
                Blocks.sandstone.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                10,
                1,
                3,
                10,
                3,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.sandstone_stairs.getStateFromMeta(j), 2, 10, 0, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(j2),
                2,
                10,
                4,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(k2),
                0,
                10,
                2,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.sandstone_stairs.getStateFromMeta(l), 4, 10, 2, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 5,
                0,
                0,
                this.scatteredFeatureSizeX - 1,
                9,
                4,
                Blocks.sandstone.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 4,
                10,
                1,
                this.scatteredFeatureSizeX - 2,
                10,
                3,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(j),
                this.scatteredFeatureSizeX - 3,
                10,
                0,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(j2),
                this.scatteredFeatureSizeX - 3,
                10,
                4,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(k2),
                this.scatteredFeatureSizeX - 5,
                10,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(l),
                this.scatteredFeatureSizeX - 1,
                10,
                2,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                0,
                0,
                12,
                4,
                4,
                Blocks.sandstone.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                1,
                0,
                11,
                3,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                9,
                1,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                9,
                2,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                9,
                3,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                10,
                3,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                11,
                3,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                11,
                2,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                11,
                1,
                1,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                1,
                8,
                3,
                3,
                Blocks.sandstone.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                2,
                8,
                2,
                2,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                12,
                1,
                1,
                16,
                3,
                3,
                Blocks.sandstone.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                12,
                1,
                2,
                16,
                2,
                2,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                4,
                5,
                this.scatteredFeatureSizeX - 6,
                4,
                this.scatteredFeatureSizeZ - 6,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                4,
                9,
                11,
                4,
                11,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                1,
                8,
                8,
                3,
                8,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                12,
                1,
                8,
                12,
                3,
                8,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                1,
                12,
                8,
                3,
                12,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                12,
                1,
                12,
                12,
                3,
                12,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                5,
                4,
                4,
                11,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 5,
                1,
                5,
                this.scatteredFeatureSizeX - 2,
                4,
                11,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                7,
                9,
                6,
                7,
                11,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 7,
                7,
                9,
                this.scatteredFeatureSizeX - 7,
                7,
                11,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                5,
                9,
                5,
                7,
                11,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 6,
                5,
                9,
                this.scatteredFeatureSizeX - 6,
                7,
                11,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.setBlockState(world, Blocks.air.getDefaultState(), 5, 5, 10, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 5, 6, 10, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 6, 6, 10, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.air.getDefaultState(),
                this.scatteredFeatureSizeX - 6,
                5,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.air.getDefaultState(),
                this.scatteredFeatureSizeX - 6,
                6,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.air.getDefaultState(),
                this.scatteredFeatureSizeX - 7,
                6,
                10,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                4,
                4,
                2,
                6,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 3,
                4,
                4,
                this.scatteredFeatureSizeX - 3,
                6,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.sandstone_stairs.getStateFromMeta(j), 2, 4, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.sandstone_stairs.getStateFromMeta(j), 2, 3, 4, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(j),
                this.scatteredFeatureSizeX - 3,
                4,
                5,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(j),
                this.scatteredFeatureSizeX - 3,
                3,
                4,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                3,
                2,
                2,
                3,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 3,
                1,
                3,
                this.scatteredFeatureSizeX - 2,
                2,
                3,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.sandstone_stairs.getDefaultState(), 1, 1, 2, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getDefaultState(),
                this.scatteredFeatureSizeX - 2,
                1,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(BlockStoneSlabEnumType.SAND.getMetadata()),
                1,
                2,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(BlockStoneSlabEnumType.SAND.getMetadata()),
                this.scatteredFeatureSizeX - 2,
                2,
                2,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.sandstone_stairs.getStateFromMeta(l), 2, 1, 2, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone_stairs.getStateFromMeta(k2),
                this.scatteredFeatureSizeX - 3,
                1,
                2,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                3,
                5,
                4,
                3,
                18,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 5,
                3,
                5,
                this.scatteredFeatureSizeX - 5,
                3,
                17,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                1,
                5,
                4,
                2,
                16,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.scatteredFeatureSizeX - 6,
                1,
                5,
                this.scatteredFeatureSizeX - 5,
                2,
                16,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        int k1 = 5;

        while (k1 <= 17) {
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    4,
                    1,
                    k1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(
                            BlockSandStoneEnumType.CHISELED.getMetadata()),
                    4,
                    2,
                    k1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    this.scatteredFeatureSizeX - 5,
                    1,
                    k1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(
                            BlockSandStoneEnumType.CHISELED.getMetadata()),
                    this.scatteredFeatureSizeX - 5,
                    2,
                    k1,
                    structureBoundingBox);
            k1 += 2;
            if (i == 0) {
                break;
            }
        }

        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                10,
                0,
                7,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                10,
                0,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                9,
                0,
                9,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                11,
                0,
                9,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                8,
                0,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                12,
                0,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                7,
                0,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                13,
                0,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                9,
                0,
                11,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                11,
                0,
                11,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                10,
                0,
                12,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                10,
                0,
                13,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(j1),
                10,
                0,
                10,
                structureBoundingBox);
        k1 = 0;

        while (k1 <= this.scatteredFeatureSizeX - 1) {
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    2,
                    1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    2,
                    2,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    2,
                    3,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    3,
                    1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    3,
                    2,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    3,
                    3,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    4,
                    1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(
                            BlockSandStoneEnumType.CHISELED.getMetadata()),
                    k1,
                    4,
                    2,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    4,
                    3,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    5,
                    1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    5,
                    2,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    5,
                    3,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    6,
                    1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(
                            BlockSandStoneEnumType.CHISELED.getMetadata()),
                    k1,
                    6,
                    2,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    6,
                    3,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    7,
                    1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    7,
                    2,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    7,
                    3,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    8,
                    1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    8,
                    2,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    8,
                    3,
                    structureBoundingBox);
            k1 += this.scatteredFeatureSizeX - 1;
            if (i == 0) {
                break;
            }
        }

        k1 = 2;

        while (k1 <= this.scatteredFeatureSizeX - 3) {
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1 - 1,
                    2,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    2,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1 + 1,
                    2,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1 - 1,
                    3,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    3,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1 + 1,
                    3,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1 - 1,
                    4,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(
                            BlockSandStoneEnumType.CHISELED.getMetadata()),
                    k1,
                    4,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1 + 1,
                    4,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1 - 1,
                    5,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    5,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1 + 1,
                    5,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1 - 1,
                    6,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(
                            BlockSandStoneEnumType.CHISELED.getMetadata()),
                    k1,
                    6,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1 + 1,
                    6,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1 - 1,
                    7,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1,
                    7,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stained_hardened_clay.getStateFromMeta(i1),
                    k1 + 1,
                    7,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1 - 1,
                    8,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1,
                    8,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                    k1 + 1,
                    8,
                    0,
                    structureBoundingBox);
            k1 += this.scatteredFeatureSizeX - 3 - 2;
            if (i == 0) {
                break;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                4,
                0,
                12,
                6,
                0,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.setBlockState(world, Blocks.air.getDefaultState(), 8, 6, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 12, 6, 0, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                9,
                5,
                0,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.CHISELED.getMetadata()),
                10,
                5,
                0,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stained_hardened_clay.getStateFromMeta(i1),
                11,
                5,
                0,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                -14,
                8,
                12,
                -11,
                12,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                -10,
                8,
                12,
                -10,
                12,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.CHISELED.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.CHISELED.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                -9,
                8,
                12,
                -9,
                12,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                -8,
                8,
                12,
                -1,
                12,
                Blocks.sandstone.getDefaultState(),
                Blocks.sandstone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                -11,
                9,
                11,
                -1,
                11,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.setBlockState(
                world,
                Blocks.stone_pressure_plate.getDefaultState(),
                10,
                -11,
                10,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                -13,
                9,
                11,
                -13,
                11,
                Blocks.tnt.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.setBlockState(world, Blocks.air.getDefaultState(), 8, -11, 10, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 8, -10, 10, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.CHISELED.getMetadata()),
                7,
                -10,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                7,
                -11,
                10,
                structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 12, -11, 10, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 12, -10, 10, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.CHISELED.getMetadata()),
                13,
                -10,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                13,
                -11,
                10,
                structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 10, -11, 8, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 10, -10, 8, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.CHISELED.getMetadata()),
                10,
                -10,
                7,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                10,
                -11,
                7,
                structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 10, -11, 12, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 10, -10, 12, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.CHISELED.getMetadata()),
                10,
                -10,
                13,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.sandstone.getStateFromMeta(BlockSandStoneEnumType.SMOOTH.getMetadata()),
                10,
                -11,
                13,
                structureBoundingBox);

        for (java.util.Iterator<?> __recoveryIterator171_39564 =
                        (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_39564).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_39564).next();
            if (!this.bMf[facing.getHorizontalIndex()]) {
                int l1 = facing.getFrontOffsetX() * 2;
                int i2 = facing.getFrontOffsetZ() * 2;
                this.bMf[facing.getHorizontalIndex()] =
                        this.generateChestContents(
                                world,
                                structureBoundingBox,
                                random,
                                10 + l1,
                                -11,
                                10 + i2,
                                WeightedRandomChestContent.func_177629_a(
                                        net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesDesertPyramid
                                                .itemsToGenerateInTemple,
                                        new WeightedRandomChestContent[] {
                                            Items.enchanted_book.getRandomEnchantedBook(random)
                                        }),
                                2 + random.nextInt(5));
            }

            if (i == 0) {
                break;
            }
        }

        return true;
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.bMf[0] = compoundTag.getBoolean("hasPlacedChest0");
        this.bMf[1] = compoundTag.getBoolean("hasPlacedChest1");
        this.bMf[2] = compoundTag.getBoolean("hasPlacedChest2");
        this.bMf[3] = compoundTag.getBoolean("hasPlacedChest3");
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("hasPlacedChest0", this.bMf[0]);
        compoundTag.setBoolean("hasPlacedChest1", this.bMf[1]);
        compoundTag.setBoolean("hasPlacedChest2", this.bMf[2]);
        compoundTag.setBoolean("hasPlacedChest3", this.bMf[3]);
    }

    public ComponentScatteredFeaturePiecesDesertPyramid() {}

    public ComponentScatteredFeaturePiecesDesertPyramid(Random random, int i, int j) {
        super(random, i, 64, j, 21, 15, 21);
    }

    static {
        net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesDesertPyramid.itemsToGenerateInTemple =
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
    }
}
