package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesLibrary extends StructureStrongholdPiecesStronghold {

    private static List strongholdLibraryChestContents;

    private boolean isLargeRoom;

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Tall", this.isLargeRoom);
    }

    static {
        net.minecraft.world.gen.structure.StructureStrongholdPiecesLibrary
                        .strongholdLibraryChestContents =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.book, 0, 1, 3, 20),
                            new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20),
                            new WeightedRandomChestContent(Items.map, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1)
                        });
    }

    public StructureStrongholdPiecesLibrary() {}

    public StructureStrongholdPiecesLibrary(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
        this.isLargeRoom =
                remix.compat.compiler.JvmBooleanSlots.fromInt((bMdValue.getYSize() > 6 ? 1 : 0));
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }

        byte b0 = 11;
        if (!this.isLargeRoom) {
            b0 = 6;
        }

        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                13,
                b0 - 1,
                14,
                true,
                random,
                StructureStrongholdPieces.access$200());
        this.placeDoor(world, random, structureBoundingBox, this.bNb, 4, 1, 0);
        this.method_1b7f0bf882063fb4ee50(
                world,
                structureBoundingBox,
                random,
                0.07F,
                2,
                1,
                1,
                11,
                4,
                13,
                Blocks.web.getDefaultState(),
                Blocks.web.getDefaultState(),
                false);

        int j = 1;

        while (j <= 13) {
            label48:
            {
                if ((j - 1) % 4 == 0) {
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            1,
                            1,
                            j,
                            1,
                            4,
                            j,
                            Blocks.planks.getDefaultState(),
                            Blocks.planks.getDefaultState(),
                            false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            12,
                            1,
                            j,
                            12,
                            4,
                            j,
                            Blocks.planks.getDefaultState(),
                            Blocks.planks.getDefaultState(),
                            false);
                    this.setBlockState(
                            world, Blocks.torch.getDefaultState(), 2, 3, j, structureBoundingBox);
                    this.setBlockState(
                            world, Blocks.torch.getDefaultState(), 11, 3, j, structureBoundingBox);
                    if (!this.isLargeRoom) {
                        break label48;
                    }

                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            1,
                            6,
                            j,
                            1,
                            9,
                            j,
                            Blocks.planks.getDefaultState(),
                            Blocks.planks.getDefaultState(),
                            false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            12,
                            6,
                            j,
                            12,
                            9,
                            j,
                            Blocks.planks.getDefaultState(),
                            Blocks.planks.getDefaultState(),
                            false);
                    if (i == 0) {
                        break label48;
                    }
                }

                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        1,
                        1,
                        j,
                        1,
                        4,
                        j,
                        Blocks.bookshelf.getDefaultState(),
                        Blocks.bookshelf.getDefaultState(),
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        12,
                        1,
                        j,
                        12,
                        4,
                        j,
                        Blocks.bookshelf.getDefaultState(),
                        Blocks.bookshelf.getDefaultState(),
                        false);
                if (this.isLargeRoom) {
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            1,
                            6,
                            j,
                            1,
                            9,
                            j,
                            Blocks.bookshelf.getDefaultState(),
                            Blocks.bookshelf.getDefaultState(),
                            false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            12,
                            6,
                            j,
                            12,
                            9,
                            j,
                            Blocks.bookshelf.getDefaultState(),
                            Blocks.bookshelf.getDefaultState(),
                            false);
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        int k = 3;

        while (k < 12) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    3,
                    1,
                    k,
                    4,
                    3,
                    k,
                    Blocks.bookshelf.getDefaultState(),
                    Blocks.bookshelf.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    6,
                    1,
                    k,
                    7,
                    3,
                    k,
                    Blocks.bookshelf.getDefaultState(),
                    Blocks.bookshelf.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    9,
                    1,
                    k,
                    10,
                    3,
                    k,
                    Blocks.bookshelf.getDefaultState(),
                    Blocks.bookshelf.getDefaultState(),
                    false);
            k += 2;
            if (i != 0) {
                break;
            }
        }

        if (this.isLargeRoom) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    1,
                    5,
                    1,
                    3,
                    5,
                    13,
                    Blocks.planks.getDefaultState(),
                    Blocks.planks.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    10,
                    5,
                    1,
                    12,
                    5,
                    13,
                    Blocks.planks.getDefaultState(),
                    Blocks.planks.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    4,
                    5,
                    1,
                    9,
                    5,
                    2,
                    Blocks.planks.getDefaultState(),
                    Blocks.planks.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    4,
                    5,
                    12,
                    9,
                    5,
                    13,
                    Blocks.planks.getDefaultState(),
                    Blocks.planks.getDefaultState(),
                    false);
            this.setBlockState(
                    world, Blocks.planks.getDefaultState(), 9, 5, 11, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.planks.getDefaultState(), 8, 5, 11, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.planks.getDefaultState(), 9, 5, 10, structureBoundingBox);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    3,
                    6,
                    2,
                    3,
                    6,
                    12,
                    Blocks.oak_fence.getDefaultState(),
                    Blocks.oak_fence.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    10,
                    6,
                    2,
                    10,
                    6,
                    10,
                    Blocks.oak_fence.getDefaultState(),
                    Blocks.oak_fence.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    4,
                    6,
                    2,
                    9,
                    6,
                    2,
                    Blocks.oak_fence.getDefaultState(),
                    Blocks.oak_fence.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    4,
                    6,
                    12,
                    8,
                    6,
                    12,
                    Blocks.oak_fence.getDefaultState(),
                    Blocks.oak_fence.getDefaultState(),
                    false);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 9, 6, 11, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 8, 6, 11, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 9, 6, 10, structureBoundingBox);
            k = this.getMetadataWithOffset(Blocks.ladder, 3);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(k), 10, 1, 13, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(k), 10, 2, 13, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(k), 10, 3, 13, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(k), 10, 4, 13, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(k), 10, 5, 13, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(k), 10, 6, 13, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(k), 10, 7, 13, structureBoundingBox);
            byte b3 = 7;
            byte b4 = 7;
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3 - 1, 9, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3, 9, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3 - 1, 8, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3, 8, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3 - 1, 7, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3, 7, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3 - 2, 7, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3 + 1, 7, b4, structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.oak_fence.getDefaultState(),
                    b3 - 1,
                    7,
                    b4 - 1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.oak_fence.getDefaultState(),
                    b3 - 1,
                    7,
                    b4 + 1,
                    structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3, 7, b4 - 1, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), b3, 7, b4 + 1, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.torch.getDefaultState(), b3 - 2, 8, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.torch.getDefaultState(), b3 + 1, 8, b4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.torch.getDefaultState(), b3 - 1, 8, b4 - 1, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.torch.getDefaultState(), b3 - 1, 8, b4 + 1, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.torch.getDefaultState(), b3, 8, b4 - 1, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.torch.getDefaultState(), b3, 8, b4 + 1, structureBoundingBox);
        }

        this.generateChestContents(
                world,
                structureBoundingBox,
                random,
                3,
                3,
                5,
                WeightedRandomChestContent.func_177629_a(
                        net.minecraft.world.gen.structure.StructureStrongholdPiecesLibrary
                                .strongholdLibraryChestContents,
                        new WeightedRandomChestContent[] {
                            Items.enchanted_book.getRandom(random, 1, 5, 2)
                        }),
                1 + random.nextInt(4));
        if (this.isLargeRoom) {
            this.setBlockState(world, Blocks.air.getDefaultState(), 12, 9, 1, structureBoundingBox);
            this.generateChestContents(
                    world,
                    structureBoundingBox,
                    random,
                    12,
                    8,
                    1,
                    WeightedRandomChestContent.func_177629_a(
                            net.minecraft.world.gen.structure.StructureStrongholdPiecesLibrary
                                    .strongholdLibraryChestContents,
                            new WeightedRandomChestContent[] {
                                Items.enchanted_book.getRandom(random, 1, 5, 2)
                            }),
                    1 + random.nextInt(4));
        }

        return true;
    }

    public static StructureStrongholdPiecesLibrary createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -4, -1, 0, 14, 11, 15, facing);
        if (!canStrongholdGoDeeper(structureBoundingBox)
                || StructureComponent.findIntersecting(list, structureBoundingBox) != null) {
            structureBoundingBox =
                    StructureBoundingBox.getComponentToAddBoundingBox(
                            i, j, k, -4, -1, 0, 14, 6, 15, facing);
            if (!canStrongholdGoDeeper(structureBoundingBox)
                    || StructureComponent.findIntersecting(list, structureBoundingBox) != null) {
                return null;
            }
        }

        return new StructureStrongholdPiecesLibrary(l, random, structureBoundingBox, facing);
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.isLargeRoom = compoundTag.getBoolean("Tall");
    }
}
