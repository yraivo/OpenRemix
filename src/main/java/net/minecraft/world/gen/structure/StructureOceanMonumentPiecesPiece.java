package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public abstract class StructureOceanMonumentPiecesPiece extends StructureComponent {
    protected static final BlockState bBJ;
    protected static final int mF;
    protected static final BlockState bLU;
    protected static final int rC;
    protected static final BlockState aOh;
    protected static final BlockState bLV;
    protected StructureOceanMonumentPiecesRoomDefinition bLW;
    protected static final BlockState bLX;
    protected static final int rW;
    protected static final int mE;
    protected static final BlockState bLt;

    static {
        bLt = Blocks.prismarine.getStateFromMeta(BlockPrismarine.ROUGHMETA);
        aOh = Blocks.prismarine.getStateFromMeta(BlockPrismarine.BRICKSMETA);
        bLX = Blocks.prismarine.getStateFromMeta(BlockPrismarine.DARKMETA);
        bLU = StructureOceanMonumentPiecesPiece.aOh;
        bLV = Blocks.sea_lantern.getDefaultState();
        bBJ = Blocks.water.getDefaultState();
        mF = getRoomIndex(2, 0, 0);
        mE = getRoomIndex(2, 2, 0);
        rW = getRoomIndex(0, 1, 0);
        rC = getRoomIndex(4, 1, 0);
    }

    protected StructureOceanMonumentPiecesPiece(
            int i,
            Facing facing,
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition,
            int j,
            int k,
            int l) {
        this(
                i,
                facing,
                structureOceanMonumentPiecesRoomDefinition,
                j,
                k,
                l,
                StructureComponent.f());
    }

    private StructureOceanMonumentPiecesPiece(
            int i,
            Facing aAcValue,
            StructureOceanMonumentPiecesRoomDefinition bLWValue,
            int j,
            int k,
            int l,
            int initialFlowToken) {
        super(i);

        int i1;
        int k1;
        int l1;
        int i2;
        {
            int j2 = initialFlowToken;

            this.coordBaseMode = aAcValue;
            this.bLW = bLWValue;
            int j1 = bLWValue.a;
            k1 = j1 % 5;
            l1 = j1 / 5 % 5;
            i2 = j1 / 25;
            i1 = j2;
            if (aAcValue != Facing.NORTH && aAcValue != Facing.SOUTH) {
                this.boundingBox =
                        new StructureBoundingBox(0, 0, 0, l * 8 - 1, k * 4 - 1, j * 8 - 1);
                if (!(i1 == 0)) {

                    this.boundingBox =
                            new StructureBoundingBox(0, 0, 0, j * 8 - 1, k * 4 - 1, l * 8 - 1);
                }
            } else {

                this.boundingBox =
                        new StructureBoundingBox(0, 0, 0, j * 8 - 1, k * 4 - 1, l * 8 - 1);
            }
        }

        switch (aAcValue) {
            case NORTH:
                this.boundingBox.offset(k1 * 8, i2 * 4, -(l1 + l) * 8 + 1);
                if (i1 == 0) {
                    break;
                }
            case SOUTH:
                this.boundingBox.offset(k1 * 8, i2 * 4, l1 * 8);
                if (i1 == 0) {
                    break;
                }
            case WEST:
                this.boundingBox.offset(-(l1 + l) * 8 + 1, i2 * 4, k1 * 8);
                if (i1 == 0) {
                    break;
                }
            default:
                this.boundingBox.offset(l1 * 8, i2 * 4, k1 * 8);
        }
    }

    protected boolean doesChunkIntersectBoundingBox(
            StructureBoundingBox structureBoundingBox,
            int p171_1,
            int p171_2,
            int p171_3,
            int p171_4) {
        int xWithOffset = this.getXWithOffset(p171_1, p171_2);
        int j1 = this.getZWithOffset(p171_1, p171_2);
        int k1 = this.getXWithOffset(p171_3, p171_4);
        int l1 = this.getZWithOffset(p171_3, p171_4);
        return structureBoundingBox.intersectsWith(
                java.lang.Math.min(xWithOffset, k1),
                java.lang.Math.min(j1, l1),
                java.lang.Math.max(xWithOffset, k1),
                java.lang.Math.max(j1, l1));
    }

    protected void method_693bdf1dd0a98b17a651(
            World world, StructureBoundingBox structureBoundingBox, int i, int j, boolean flag) {
        int k = StructureComponent.cR();
        if (flag) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    i + 0,
                    0,
                    j + 0,
                    i + 2,
                    0,
                    j + 8 - 1,
                    bLt,
                    bLt,
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    i + 5,
                    0,
                    j + 0,
                    i + 8 - 1,
                    0,
                    j + 8 - 1,
                    bLt,
                    bLt,
                    false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 3, 0, j + 0, i + 4, 0, j + 2, bLt, bLt, false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    i + 3,
                    0,
                    j + 5,
                    i + 4,
                    0,
                    j + 8 - 1,
                    bLt,
                    bLt,
                    false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 3, 0, j + 2, i + 4, 0, j + 2, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 3, 0, j + 5, i + 4, 0, j + 5, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 2, 0, j + 3, i + 2, 0, j + 4, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 5, 0, j + 3, i + 5, 0, j + 4, aOh, aOh, false);
            if (k != 0) {
                return;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                i + 0,
                0,
                j + 0,
                i + 8 - 1,
                0,
                j + 8 - 1,
                bLt,
                bLt,
                false);
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {}

    protected void method_0f930b19b46d08e97b6e(
            World world,
            StructureBoundingBox structureBoundingBox,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1,
            BlockState blockState) {
        int k2 = StructureComponent.f();
        int l1 = j;
        int k1 = k2;

        while (l1 <= i1) {
            int i2 = i;

            while (true) {
                if (i2 <= l) {
                    int j2 = k;

                    while (j2 <= j1) {
                        if (this.getBlockStateFromPos(world, i2, l1, j2, structureBoundingBox)
                                == bBJ) {
                            this.setBlockState(world, blockState, i2, l1, j2, structureBoundingBox);
                        }

                        j2++;
                        if (k1 != 0) {
                            break;
                        }
                    }

                    i2++;
                    if (k1 == 0) {
                        continue;
                    }
                }

                l1++;
                if (k1 != 0) {
                    return;
                }
                break;
            }
        }
    }

    protected static final int getRoomIndex(int p171_0, int p171_1, int p171_2) {
        return p171_1 * 25 + p171_2 * 5 + p171_0;
    }

    public StructureOceanMonumentPiecesPiece(Facing aAcValue, StructureBoundingBox bMdValue) {
        super(1);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {}

    public StructureOceanMonumentPiecesPiece() {
        super(0);
    }

    protected boolean method_073023fc34d438b4ac6c(
            World world, StructureBoundingBox structureBoundingBox, int i, int j, int k) {
        int l = this.getXWithOffset(i, k);
        int yWithOffset = this.getYWithOffset(j);
        int j1 = this.getZWithOffset(i, k);
        if (structureBoundingBox.isVecInside(new BlockPos(l, yWithOffset, j1))) {
            EntityGuardian entityGuardian = new EntityGuardian(world);
            entityGuardian.setElder(true);
            entityGuardian.heal(entityGuardian.getMaxHealth());
            entityGuardian.setLocationAndAngles(l + 0.5, yWithOffset, j1 + 0.5, 0.0F, 0.0F);
            entityGuardian.onInitialSpawn(
                    world.getDifficultyForLocation(new BlockPos(entityGuardian)),
                    (IEntityLivingData) null);
            world.spawnEntityInWorld(entityGuardian);
            return true;
        } else {
            return false;
        }
    }

    public StructureOceanMonumentPiecesPiece(int i) {
        super(i);
    }
}
