package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class StructureStrongholdPiecesStronghold extends StructureComponent {
    protected StructureStrongholdPiecesStrongholdDoor bNb =
            StructureStrongholdPiecesStrongholdDoor.OPENING;

    protected StructureComponent getNextComponentX(
            StructureStrongholdPiecesStairs2 structureStrongholdPiecesStairs2,
            List list,
            Random random,
            int i,
            int j) {
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.WEST,
                            this.getComponentType());
                case SOUTH:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.WEST,
                            this.getComponentType());
                case WEST:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            this.getComponentType());
                case EAST:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            this.getComponentType());
            }
        }

        return null;
    }

    protected StructureComponent getNextComponentZ(
            StructureStrongholdPiecesStairs2 structureStrongholdPiecesStairs2,
            List list,
            Random random,
            int i,
            int j) {
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.EAST,
                            this.getComponentType());
                case SOUTH:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.EAST,
                            this.getComponentType());
                case WEST:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            this.getComponentType());
                case EAST:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            this.getComponentType());
            }
        }

        return null;
    }

    protected StructureStrongholdPiecesStronghold(int i) {
        super(i);
    }


    protected void writeStructureToNBT(CompoundTag compoundTag) {
        compoundTag.setString("EntryDoor", this.bNb.name());
    }

    protected void placeDoor(
            World world,
            Random random,
            StructureBoundingBox structureBoundingBox,
            StructureStrongholdPiecesStrongholdDoor structureStrongholdPiecesStrongholdDoor,
            int i,
            int j,
            int k) {
        int l = StructureComponent.cR();
        switch (structureStrongholdPiecesStrongholdDoor) {
            case OPENING:
            default:
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        i,
                        j,
                        k,
                        i + 3 - 1,
                        j + 3 - 1,
                        k,
                        Blocks.air.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
                if (l != 0) {
                    break;
                }
            case WOOD_DOOR:
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), i, j, k, structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i,
                        j + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i + 1,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i + 2,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i + 2,
                        j + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i + 2,
                        j,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.oak_door.getDefaultState(),
                        i + 1,
                        j,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.oak_door.getStateFromMeta(8),
                        i + 1,
                        j + 1,
                        k,
                        structureBoundingBox);
                if (l != 0) {
                    break;
                }
            case GRATES:
                this.setBlockState(
                        world, Blocks.air.getDefaultState(), i + 1, j, k, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.air.getDefaultState(), i + 1, j + 1, k, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.iron_bars.getDefaultState(), i, j, k, structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.iron_bars.getDefaultState(),
                        i,
                        j + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.iron_bars.getDefaultState(),
                        i,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.iron_bars.getDefaultState(),
                        i + 1,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.iron_bars.getDefaultState(),
                        i + 2,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.iron_bars.getDefaultState(),
                        i + 2,
                        j + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.iron_bars.getDefaultState(),
                        i + 2,
                        j,
                        k,
                        structureBoundingBox);
                if (l != 0) {
                    break;
                }
            case IRON_DOOR:
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), i, j, k, structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i,
                        j + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i + 1,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i + 2,
                        j + 2,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i + 2,
                        j + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        i + 2,
                        j,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.iron_door.getDefaultState(),
                        i + 1,
                        j,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.iron_door.getStateFromMeta(8),
                        i + 1,
                        j + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stone_button.getStateFromMeta(
                                this.getMetadataWithOffset(Blocks.stone_button, 4)),
                        i + 2,
                        j + 1,
                        k + 1,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stone_button.getStateFromMeta(
                                this.getMetadataWithOffset(Blocks.stone_button, 3)),
                        i + 2,
                        j + 1,
                        k - 1,
                        structureBoundingBox);
        }
    }

    protected StructureStrongholdPiecesStrongholdDoor getRandomDoor(Random random) {
        int i = random.nextInt(5);
        switch (i) {
            case 0:
            case 1:
            default:
                return StructureStrongholdPiecesStrongholdDoor.OPENING;
            case 2:
                return StructureStrongholdPiecesStrongholdDoor.WOOD_DOOR;
            case 3:
                return StructureStrongholdPiecesStrongholdDoor.GRATES;
            case 4:
                return StructureStrongholdPiecesStrongholdDoor.IRON_DOOR;
        }
    }

    protected StructureComponent getNextComponentNormal(
            StructureStrongholdPiecesStairs2 structureStrongholdPiecesStairs2,
            List list,
            Random random,
            int i,
            int j) {
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX + i,
                            this.boundingBox.minY + j,
                            this.boundingBox.minZ - 1,
                            this.coordBaseMode,
                            this.getComponentType());
                case SOUTH:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX + i,
                            this.boundingBox.minY + j,
                            this.boundingBox.maxZ + 1,
                            this.coordBaseMode,
                            this.getComponentType());
                case WEST:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + j,
                            this.boundingBox.minZ + i,
                            this.coordBaseMode,
                            this.getComponentType());
                case EAST:
                    return StructureStrongholdPieces.access$000(
                            structureStrongholdPiecesStairs2,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + j,
                            this.boundingBox.minZ + i,
                            this.coordBaseMode,
                            this.getComponentType());
            }
        }

        return null;
    }

    public StructureStrongholdPiecesStronghold() {}

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        this.bNb =
                StructureStrongholdPiecesStrongholdDoor.valueOf(compoundTag.getString("EntryDoor"));
    }

    protected static boolean canStrongholdGoDeeper(StructureBoundingBox structureBoundingBox) {
        return structureBoundingBox != null && structureBoundingBox.minY > 10;
    }
}
