package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesPortalRoom extends StructureStrongholdPiecesStronghold {
    private boolean hasSpawner;

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                10,
                7,
                15,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.placeDoor(
                world,
                random,
                structureBoundingBox,
                StructureStrongholdPiecesStrongholdDoor.GRATES,
                4,
                1,
                0);
        byte b0 = 6;
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                1,
                b0,
                1,
                1,
                b0,
                14,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                9,
                b0,
                1,
                9,
                b0,
                14,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                b0,
                1,
                8,
                b0,
                2,
                false,
                random,
                StructureStrongholdPieces.access$200());
        int l1 = StructureComponent.cR();
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                2,
                b0,
                14,
                8,
                b0,
                14,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                1,
                2,
                1,
                4,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                8,
                1,
                1,
                9,
                1,
                4,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                1,
                1,
                1,
                3,
                Blocks.flowing_lava.getDefaultState(),
                Blocks.flowing_lava.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                1,
                1,
                9,
                1,
                3,
                Blocks.flowing_lava.getDefaultState(),
                Blocks.flowing_lava.getDefaultState(),
                false);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                3,
                1,
                8,
                7,
                1,
                12,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                9,
                6,
                1,
                11,
                Blocks.flowing_lava.getDefaultState(),
                Blocks.flowing_lava.getDefaultState(),
                false);
        int i = l1;
        int j = 3;

        while (j < 14) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    3,
                    j,
                    0,
                    4,
                    j,
                    Blocks.iron_bars.getDefaultState(),
                    Blocks.iron_bars.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    10,
                    3,
                    j,
                    10,
                    4,
                    j,
                    Blocks.iron_bars.getDefaultState(),
                    Blocks.iron_bars.getDefaultState(),
                    false);
            j += 2;
            if (i == 0) {
                break;
            }
        }

        j = (byte) 2;

        while (j < 9) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    j,
                    3,
                    15,
                    j,
                    4,
                    15,
                    Blocks.iron_bars.getDefaultState(),
                    Blocks.iron_bars.getDefaultState(),
                    false);
            j += 2;
            if (i == 0) {
                break;
            }
        }

        j = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 3);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                5,
                6,
                1,
                7,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                2,
                6,
                6,
                2,
                7,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                3,
                7,
                6,
                3,
                7,
                false,
                random,
                StructureStrongholdPieces.access$200());
        int k = 4;

        while (k <= 6) {
            this.setBlockState(
                    world,
                    Blocks.stone_brick_stairs.getStateFromMeta(j),
                    k,
                    1,
                    4,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stone_brick_stairs.getStateFromMeta(j),
                    k,
                    2,
                    5,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stone_brick_stairs.getStateFromMeta(j),
                    k,
                    3,
                    6,
                    structureBoundingBox);
            k++;
            if (i == 0) {
                break;
            }
        }

        k = Facing.NORTH.getHorizontalIndex();
        int l = Facing.SOUTH.getHorizontalIndex();
        int horizontalIndex = Facing.EAST.getHorizontalIndex();
        int j1 = Facing.WEST.getHorizontalIndex();
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case SOUTH:
                    k = Facing.SOUTH.getHorizontalIndex();
                    l = Facing.NORTH.getHorizontalIndex();
                    if (i != 0) {
                        break;
                    }
                case WEST:
                    k = Facing.WEST.getHorizontalIndex();
                    l = Facing.EAST.getHorizontalIndex();
                    horizontalIndex = Facing.SOUTH.getHorizontalIndex();
                    j1 = Facing.NORTH.getHorizontalIndex();
                    if (i != 0) {
                        break;
                    }
                case EAST:
                    k = Facing.EAST.getHorizontalIndex();
                    l = Facing.WEST.getHorizontalIndex();
                    horizontalIndex = Facing.SOUTH.getHorizontalIndex();
                    j1 = Facing.NORTH.getHorizontalIndex();
            }
        }

        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(k)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                4,
                3,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(k)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                5,
                3,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(k)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                6,
                3,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(l)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                4,
                3,
                12,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(l)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                5,
                3,
                12,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(l)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                6,
                3,
                12,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(horizontalIndex)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                3,
                3,
                9,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(horizontalIndex)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                3,
                3,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(horizontalIndex)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                3,
                3,
                11,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(j1)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                7,
                3,
                9,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(j1)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                7,
                3,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.end_portal_frame
                        .getStateFromMeta(j1)
                        .withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
                7,
                3,
                11,
                structureBoundingBox);
        if (!this.hasSpawner) {
            int k1 = this.getYWithOffset(3);
            BlockPos blockPos =
                    new BlockPos(this.getXWithOffset(5, 6), k1, this.getZWithOffset(5, 6));
            if (structureBoundingBox.isVecInside(blockPos)) {
                this.hasSpawner = true;
                world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), 2);
                TileEntity tileEntity = world.getTileEntity(blockPos);
                if (tileEntity instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tileEntity)
                            .getSpawnerBaseLogic()
                            .setEntityName("Silverfish");
                }
            }
        }

        return true;
    }

    public StructureStrongholdPiecesPortalRoom() {}


    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        if (structureComponent != null) {
            ((StructureStrongholdPiecesStairs2) structureComponent).strongholdPortalRoom = this;
        }
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.hasSpawner = compoundTag.getBoolean("Mob");
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Mob", this.hasSpawner);
    }

    public StructureStrongholdPiecesPortalRoom(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public static StructureStrongholdPiecesPortalRoom createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -4, -1, 0, 11, 8, 16, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesPortalRoom(l, random, structureBoundingBox, facing)
                : null;
    }
}
