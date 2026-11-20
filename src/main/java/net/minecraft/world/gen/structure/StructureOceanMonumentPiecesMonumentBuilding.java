package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;

import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StructureOceanMonumentPiecesMonumentBuilding
        extends StructureOceanMonumentPiecesPiece {
    private StructureOceanMonumentPiecesRoomDefinition bMZ;
    private List nM = Lists.newArrayList();
    private StructureOceanMonumentPiecesRoomDefinition bNa;

    private void generateWaterBox(
            boolean flag,
            int i,
            World world,
            Random random,
            StructureBoundingBox structureBoundingBox) {
        int k1 = StructureComponent.cR();

        int j = k1;
        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, i, 0, i + 23, 20)) {
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 0, 0, 0, i + 24, 0, 20, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 0, 1, 0, i + 24, 10, 20, bBJ, bBJ, false);
            int k = 0;

            while (k < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        i + k,
                        k + 1,
                        k,
                        i + k,
                        k + 1,
                        20,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        i + k + 7,
                        k + 5,
                        k + 7,
                        i + k + 7,
                        k + 5,
                        20,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        i + 17 - k,
                        k + 5,
                        k + 7,
                        i + 17 - k,
                        k + 5,
                        20,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        i + 24 - k,
                        k + 1,
                        k,
                        i + 24 - k,
                        k + 1,
                        20,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        i + k + 1,
                        k + 1,
                        k,
                        i + 23 - k,
                        k + 1,
                        k,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        i + k + 8,
                        k + 5,
                        k + 7,
                        i + 16 - k,
                        k + 5,
                        k + 7,
                        aOh,
                        aOh,
                        false);
                k++;
                if (j == 0) {
                    break;
                }
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, i + 4, 4, 4, i + 6, 4, 20, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 7, 4, 4, i + 17, 4, 6, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 18, 4, 4, i + 20, 4, 20, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 11, 8, 11, i + 13, 8, 20, bLt, bLt, false);
            this.setBlockState(world, bLU, i + 12, 9, 12, structureBoundingBox);
            this.setBlockState(world, bLU, i + 12, 9, 15, structureBoundingBox);
            this.setBlockState(world, bLU, i + 12, 9, 18, structureBoundingBox);
            k = flag ? i + 19 : i + 5;
            int l = flag ? i + 5 : i + 19;
            int i1 = 20;

            while (i1 >= 5) {
                this.setBlockState(world, bLU, k, 5, i1, structureBoundingBox);
                i1 -= 3;
                if (j == 0) {
                    break;
                }
            }

            i1 = (byte) 19;

            while (i1 >= 7) {
                this.setBlockState(world, bLU, l, 5, i1, structureBoundingBox);
                i1 -= 3;
                if (j == 0) {
                    break;
                }
            }

            i1 = 0;

            while (i1 < 4) {
                int j1 = flag ? i + (24 - (17 - i1 * 3)) : i + 17 - i1 * 3;
                this.setBlockState(world, bLU, j1, 5, 5, structureBoundingBox);
                i1++;
                if (j == 0) {
                    break;
                }
            }

            this.setBlockState(world, bLU, l, 5, 5, structureBoundingBox);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 11, 1, 12, i + 13, 7, 12, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, i + 12, 1, 11, i + 12, 7, 13, bLt, bLt, false);
        }
    }

    private void generateMiddleRoof(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 21, 21, 36, 36)) {
            this.fillWithBlocks(world, structureBoundingBox, 21, 0, 22, 36, 0, 36, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 21, 1, 22, 36, 23, 36, bBJ, bBJ, false);
            int j = 0;

            while (j < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        21 + j,
                        13 + j,
                        21 + j,
                        36 - j,
                        13 + j,
                        21 + j,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        21 + j,
                        13 + j,
                        36 - j,
                        36 - j,
                        13 + j,
                        36 - j,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        21 + j,
                        13 + j,
                        22 + j,
                        21 + j,
                        13 + j,
                        35 - j,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        36 - j,
                        13 + j,
                        22 + j,
                        36 - j,
                        13 + j,
                        35 - j,
                        aOh,
                        aOh,
                        false);
                j++;
                if (i != 0) {
                    break;
                }
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, 25, 16, 25, 32, 16, 32, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 25, 17, 25, 25, 19, 25, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 32, 17, 25, 32, 19, 25, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 25, 17, 32, 25, 19, 32, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 32, 17, 32, 32, 19, 32, aOh, aOh, false);
            this.setBlockState(world, aOh, 26, 20, 26, structureBoundingBox);
            this.setBlockState(world, aOh, 27, 21, 27, structureBoundingBox);
            this.setBlockState(world, bLV, 27, 20, 27, structureBoundingBox);
            this.setBlockState(world, aOh, 26, 20, 31, structureBoundingBox);
            this.setBlockState(world, aOh, 27, 21, 30, structureBoundingBox);
            this.setBlockState(world, bLV, 27, 20, 30, structureBoundingBox);
            this.setBlockState(world, aOh, 31, 20, 31, structureBoundingBox);
            this.setBlockState(world, aOh, 30, 21, 30, structureBoundingBox);
            this.setBlockState(world, bLV, 30, 20, 30, structureBoundingBox);
            this.setBlockState(world, aOh, 31, 20, 26, structureBoundingBox);
            this.setBlockState(world, aOh, 30, 21, 27, structureBoundingBox);
            this.setBlockState(world, bLV, 30, 20, 27, structureBoundingBox);
            this.fillWithBlocks(
                    world, structureBoundingBox, 28, 21, 27, 29, 21, 27, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 27, 21, 28, 27, 21, 29, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 28, 21, 30, 29, 21, 30, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 30, 21, 28, 30, 21, 29, bLt, bLt, false);
        }
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.generateWaterBox(false, 0, world, random, structureBoundingBox);
        this.generateWaterBox(true, 33, world, random, structureBoundingBox);
        this.generateEntrance(world, random, structureBoundingBox);
        this.generateLowerRoof(world, random, structureBoundingBox);
        this.generateMiddleRoof(world, random, structureBoundingBox);
        this.generateUpperRoof(world, random, structureBoundingBox);
        int l1 = StructureComponent.f();
        this.generateWing(world, random, structureBoundingBox);
        int i = l1;
        this.generateCore(world, random, structureBoundingBox);
        int j = 0;

        label81:
        while (j < 7) {
            int k = 0;

            while (true) {
                if (k < 7) {
                    if (k == 0 && j == 3) {
                        k = 6;
                    }

                    int l = j * 9;
                    int i1 = k * 9;
                    int j1 = 0;

                    while (j1 < 4) {
                        int k1 = 0;

                        while (k1 < 4) {
                            this.setBlockState(
                                    world, aOh, l + j1, 0, i1 + k1, structureBoundingBox);
                            this.replaceAirAndLiquidDownwards(
                                    world, aOh, l + j1, -1, i1 + k1, structureBoundingBox);
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

                    {
                        if (j != 0 && j != 6) {
                            k += 6;
                            if (!(i == 0)) {

                                k++;
                            }
                        } else {

                            k++;
                        }
                    }

                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label81;
                }
                break;
            }
        }

        j = 0;

        while (j < 5) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    -1 - j,
                    0 + j * 2,
                    -1 - j,
                    -1 - j,
                    23,
                    58 + j,
                    bBJ,
                    bBJ,
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    58 + j,
                    0 + j * 2,
                    -1 - j,
                    58 + j,
                    23,
                    58 + j,
                    bBJ,
                    bBJ,
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0 - j,
                    0 + j * 2,
                    -1 - j,
                    57 + j,
                    23,
                    -1 - j,
                    bBJ,
                    bBJ,
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0 - j,
                    0 + j * 2,
                    58 + j,
                    57 + j,
                    23,
                    58 + j,
                    bBJ,
                    bBJ,
                    false);
            j++;
            if (i != 0) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_12239 = (this.nM).iterator();
                (__recoveryIterator171_12239).hasNext(); ) {
            StructureOceanMonumentPiecesPiece structureOceanMonumentPiecesPiece =
                    (StructureOceanMonumentPiecesPiece) (__recoveryIterator171_12239).next();
            if (structureOceanMonumentPiecesPiece
                    .getBoundingBox()
                    .intersectsWith(structureBoundingBox)) {
                structureOceanMonumentPiecesPiece.addComponentParts(
                        world, random, structureBoundingBox);
            }

            if (i != 0) {
                break;
            }
        }

        return true;
    }

    private List makeRoomChunkList(Random random) {
        StructureOceanMonumentPiecesRoomDefinition[]
                structureOceanMonumentPiecesRoomDefinitionArray =
                        new StructureOceanMonumentPiecesRoomDefinition[75];
        int k5 = StructureComponent.f();
        int j = 0;
        int i = k5;

        label169:
        while (j < 5) {
            int k = 0;

            while (true) {
                if (k < 4) {
                    byte b0 = 0;
                    int l = getRoomIndex(j, b0, k);
                    structureOceanMonumentPiecesRoomDefinitionArray[l] =
                            new StructureOceanMonumentPiecesRoomDefinition(l);
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label169;
                }
                break;
            }
        }

        j = 0;

        label156:
        while (j < 5) {
            int k2 = 0;

            while (true) {
                if (k2 < 4) {
                    byte b1 = 1;
                    int j3 = getRoomIndex(j, b1, k2);
                    structureOceanMonumentPiecesRoomDefinitionArray[j3] =
                            new StructureOceanMonumentPiecesRoomDefinition(j3);
                    k2++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label156;
                }
                break;
            }
        }

        j = 1;

        label143:
        while (j < 4) {
            int l2 = 0;

            while (true) {
                if (l2 < 2) {
                    byte b2 = 2;
                    int k3 = getRoomIndex(j, b2, l2);
                    structureOceanMonumentPiecesRoomDefinitionArray[k3] =
                            new StructureOceanMonumentPiecesRoomDefinition(k3);
                    l2++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label143;
                }
                break;
            }
        }

        this.bMZ = structureOceanMonumentPiecesRoomDefinitionArray[mF];
        j = 0;

        label130:
        while (j < 5) {
            int i3 = 0;

            while (true) {
                if (i3 < 5) {
                    int l1 = 0;

                    while (l1 < 3) {
                        int l3 = getRoomIndex(j, l1, i3);
                        if (structureOceanMonumentPiecesRoomDefinitionArray[l3] != null) {
                            for (Facing facing : Facing.values()) {
                                int i1 = j + facing.getFrontOffsetX();
                                int j1 = l1 + facing.getFrontOffsetY();
                                int k1 = i3 + facing.getFrontOffsetZ();
                                if (i1 >= 0 && i1 < 5 && k1 >= 0 && k1 < 5 && j1 >= 0 && j1 < 3) {
                                    int i2 = getRoomIndex(i1, j1, k1);
                                    if (structureOceanMonumentPiecesRoomDefinitionArray[i2]
                                            != null) {
                                        if (k1 != i3) {
                                            structureOceanMonumentPiecesRoomDefinitionArray[l3]
                                                    .method_e47fe69d6ddf5ffe6746(
                                                            facing.getOpposite(),
                                                            structureOceanMonumentPiecesRoomDefinitionArray[
                                                                    i2]);
                                            if (!(i == 0)) {

                                                structureOceanMonumentPiecesRoomDefinitionArray[l3]
                                                        .method_e47fe69d6ddf5ffe6746(
                                                                facing,
                                                                structureOceanMonumentPiecesRoomDefinitionArray[
                                                                        i2]);
                                            }
                                        } else {

                                            structureOceanMonumentPiecesRoomDefinitionArray[l3]
                                                    .method_e47fe69d6ddf5ffe6746(
                                                            facing,
                                                            structureOceanMonumentPiecesRoomDefinitionArray[
                                                                    i2]);
                                        }
                                    }
                                }

                                if (i != 0) {
                                    break;
                                }
                            }
                        }

                        l1++;
                        if (i != 0) {
                            break;
                        }
                    }

                    i3++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label130;
                }
                break;
            }
        }

        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition;
        structureOceanMonumentPiecesRoomDefinitionArray[mE].method_e47fe69d6ddf5ffe6746(
                Facing.UP,
                structureOceanMonumentPiecesRoomDefinition =
                        new StructureOceanMonumentPiecesRoomDefinition(1003));
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinitionValue;
        structureOceanMonumentPiecesRoomDefinitionArray[rW].method_e47fe69d6ddf5ffe6746(
                Facing.SOUTH,
                structureOceanMonumentPiecesRoomDefinitionValue =
                        new StructureOceanMonumentPiecesRoomDefinition(1001));
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinitionValue2;
        structureOceanMonumentPiecesRoomDefinitionArray[rC].method_e47fe69d6ddf5ffe6746(
                Facing.SOUTH,
                structureOceanMonumentPiecesRoomDefinitionValue2 =
                        new StructureOceanMonumentPiecesRoomDefinition(1002));
        structureOceanMonumentPiecesRoomDefinition.d = true;
        structureOceanMonumentPiecesRoomDefinitionValue.d = true;
        structureOceanMonumentPiecesRoomDefinitionValue2.d = true;
        this.bMZ.e = true;
        this.bNa =
                structureOceanMonumentPiecesRoomDefinitionArray[
                        getRoomIndex(random.nextInt(4), 0, 2)];
        this.bNa.d = true;
        this.bNa.bMV[Facing.EAST.getIndex()].d = true;
        this.bNa.bMV[Facing.NORTH.getIndex()].d = true;
        this.bNa.bMV[Facing.EAST.getIndex()].bMV[Facing.NORTH.getIndex()].d = true;
        this.bNa.bMV[Facing.UP.getIndex()].d = true;
        this.bNa.bMV[Facing.EAST.getIndex()].bMV[Facing.UP.getIndex()].d = true;
        this.bNa.bMV[Facing.NORTH.getIndex()].bMV[Facing.UP.getIndex()].d = true;
        this.bNa
                        .bMV[Facing.EAST.getIndex()]
                        .bMV[Facing.NORTH.getIndex()]
                        .bMV[Facing.UP.getIndex()]
                        .d =
                true;
        ArrayList arrayList = Lists.newArrayList();
        StructureOceanMonumentPiecesRoomDefinition[]
                structureOceanMonumentPiecesRoomDefinitionArrayValue =
                        structureOceanMonumentPiecesRoomDefinitionArray;
        int length = structureOceanMonumentPiecesRoomDefinitionArray.length;
        int j4 = 0;

        while (j4 < length) {
            StructureOceanMonumentPiecesRoomDefinition
                    structureOceanMonumentPiecesRoomDefinitionValue3 =
                            structureOceanMonumentPiecesRoomDefinitionArrayValue[j4];
            if (structureOceanMonumentPiecesRoomDefinitionValue3 != null) {
                structureOceanMonumentPiecesRoomDefinitionValue3.method_8d9b536bcdc9991c48fb();
                arrayList.add(structureOceanMonumentPiecesRoomDefinitionValue3);
            }

            j4++;
            if (i != 0) {
                break;
            }
        }

        structureOceanMonumentPiecesRoomDefinition.method_8d9b536bcdc9991c48fb();
        Collections.shuffle(arrayList, random);
        int j5 = 1;

        label80:
        for (java.util.Iterator<?> __recoveryIterator171_19300 = (arrayList).iterator();
                (__recoveryIterator171_19300).hasNext(); ) {
            StructureOceanMonumentPiecesRoomDefinition
                    structureOceanMonumentPiecesRoomDefinitionValue4 =
                            (StructureOceanMonumentPiecesRoomDefinition)
                                    (__recoveryIterator171_19300).next();
            int j2 = 0;
            int k4 = 0;

            while (true) {
                if (j2 < 2 && k4 < 5) {
                    k4++;
                    int l4 = random.nextInt(6);
                    if (!structureOceanMonumentPiecesRoomDefinitionValue4.aCM[l4]) {
                        continue;
                    }

                    int index = Facing.getFront(l4).getOpposite().getIndex();
                    structureOceanMonumentPiecesRoomDefinitionValue4.aCM[l4] = false;
                    structureOceanMonumentPiecesRoomDefinitionValue4.bMV[l4].aCM[index] = false;
                    if (structureOceanMonumentPiecesRoomDefinitionValue4
                                    .method_3baace372e02b00ebb50(j5++)
                            && structureOceanMonumentPiecesRoomDefinitionValue4.bMV[l4]
                                    .method_3baace372e02b00ebb50(j5++)) {
                        j2++;
                        if (i == 0) {
                            continue;
                        }
                    }

                    structureOceanMonumentPiecesRoomDefinitionValue4.aCM[l4] = true;
                    structureOceanMonumentPiecesRoomDefinitionValue4.bMV[l4].aCM[index] = true;
                    if (i == 0) {
                        continue;
                    }
                }

                if (i != 0) {
                    break label80;
                }
                break;
            }
        }

        arrayList.add(structureOceanMonumentPiecesRoomDefinition);
        arrayList.add(structureOceanMonumentPiecesRoomDefinitionValue);
        arrayList.add(structureOceanMonumentPiecesRoomDefinitionValue2);
        return arrayList;
    }

    private void generateWing(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 7, 21, 13, 50)) {
            this.fillWithBlocks(world, structureBoundingBox, 7, 0, 21, 13, 0, 50, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 21, 13, 10, 50, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 11, 8, 21, 13, 8, 53, bLt, bLt, false);
            int j = 0;

            while (j < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        j + 7,
                        j + 5,
                        21,
                        j + 7,
                        j + 5,
                        54,
                        aOh,
                        aOh,
                        false);
                j++;
                if (i == 0) {
                    break;
                }
            }

            byte b0 = 21;

            while (b0 <= 45) {
                this.setBlockState(world, bLU, 12, 9, b0, structureBoundingBox);
                b0 += 3;
                if (i == 0) {
                    break;
                }
            }
        }

        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 44, 21, 50, 54)) {
            this.fillWithBlocks(world, structureBoundingBox, 44, 0, 21, 50, 0, 50, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 44, 1, 21, 50, 10, 50, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 44, 8, 21, 46, 8, 53, bLt, bLt, false);
            int k = 0;

            while (k < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        50 - k,
                        k + 5,
                        21,
                        50 - k,
                        k + 5,
                        54,
                        aOh,
                        aOh,
                        false);
                k++;
                if (i == 0) {
                    break;
                }
            }

            byte b1 = 21;

            while (b1 <= 45) {
                this.setBlockState(world, bLU, 45, 9, b1, structureBoundingBox);
                b1 += 3;
                if (i == 0) {
                    break;
                }
            }
        }

        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 8, 44, 49, 54)) {
            this.fillWithBlocks(world, structureBoundingBox, 14, 0, 44, 43, 0, 50, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 14, 1, 44, 43, 10, 50, bBJ, bBJ, false);
            int l = 12;

            while (l <= 45) {
                this.setBlockState(world, bLU, l, 9, 45, structureBoundingBox);
                this.setBlockState(world, bLU, l, 9, 52, structureBoundingBox);
                if (l == 12 || l == 18 || l == 24 || l == 33 || l == 39 || l == 45) {
                    this.setBlockState(world, bLU, l, 9, 47, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 9, 50, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 10, 45, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 10, 46, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 10, 51, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 10, 52, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 11, 47, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 11, 50, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 12, 48, structureBoundingBox);
                    this.setBlockState(world, bLU, l, 12, 49, structureBoundingBox);
                }

                l += 3;
                if (i == 0) {
                    break;
                }
            }

            l = 0;

            while (l < 3) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        8 + l,
                        5 + l,
                        54,
                        49 - l,
                        5 + l,
                        54,
                        bLt,
                        bLt,
                        false);
                l++;
                if (i == 0) {
                    break;
                }
            }

            this.fillWithBlocks(world, structureBoundingBox, 11, 8, 54, 46, 8, 54, aOh, aOh, false);
            this.fillWithBlocks(world, structureBoundingBox, 14, 8, 44, 43, 8, 53, bLt, bLt, false);
        }
    }

    private void generateCore(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 14, 21, 20, 43)) {
            this.fillWithBlocks(world, structureBoundingBox, 14, 0, 21, 20, 0, 43, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 14, 1, 22, 20, 14, 43, bBJ, bBJ, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 18, 12, 22, 20, 12, 39, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 18, 12, 21, 20, 12, 21, aOh, aOh, false);
            int j = 0;

            while (j < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        j + 14,
                        j + 9,
                        21,
                        j + 14,
                        j + 9,
                        43 - j,
                        aOh,
                        aOh,
                        false);
                j++;
                if (i == 0) {
                    break;
                }
            }

            byte b0 = 23;

            while (b0 <= 39) {
                this.setBlockState(world, bLU, 19, 13, b0, structureBoundingBox);
                b0 += 3;
                if (i == 0) {
                    break;
                }
            }
        }

        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 37, 21, 43, 43)) {
            this.fillWithBlocks(world, structureBoundingBox, 37, 0, 21, 43, 0, 43, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 37, 1, 22, 43, 14, 43, bBJ, bBJ, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 37, 12, 22, 39, 12, 39, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 37, 12, 21, 39, 12, 21, aOh, aOh, false);
            int k = 0;

            while (k < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        43 - k,
                        k + 9,
                        21,
                        43 - k,
                        k + 9,
                        43 - k,
                        aOh,
                        aOh,
                        false);
                k++;
                if (i == 0) {
                    break;
                }
            }

            byte b1 = 23;

            while (b1 <= 39) {
                this.setBlockState(world, bLU, 38, 13, b1, structureBoundingBox);
                b1 += 3;
                if (i == 0) {
                    break;
                }
            }
        }

        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 15, 37, 42, 43)) {
            this.fillWithBlocks(world, structureBoundingBox, 21, 0, 37, 36, 0, 43, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 21, 1, 37, 36, 14, 43, bBJ, bBJ, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 21, 12, 37, 36, 12, 39, bLt, bLt, false);
            int l = 0;

            while (l < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        15 + l,
                        l + 9,
                        43 - l,
                        42 - l,
                        l + 9,
                        43 - l,
                        aOh,
                        aOh,
                        false);
                l++;
                if (i == 0) {
                    break;
                }
            }

            byte b2 = 21;

            while (b2 <= 36) {
                this.setBlockState(world, bLU, b2, 13, 38, structureBoundingBox);
                b2 += 3;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    private void generateLowerRoof(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 15, 20, 42, 21)) {
            this.fillWithBlocks(world, structureBoundingBox, 15, 0, 21, 42, 0, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 26, 1, 21, 31, 3, 21, bBJ, bBJ, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 21, 12, 21, 36, 12, 21, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 17, 11, 21, 40, 11, 21, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 16, 10, 21, 41, 10, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 7, 21, 42, 9, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 16, 6, 21, 41, 6, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 17, 5, 21, 40, 5, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 21, 4, 21, 36, 4, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 22, 3, 21, 26, 3, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 31, 3, 21, 35, 3, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 23, 2, 21, 25, 2, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 32, 2, 21, 34, 2, 21, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 28, 4, 20, 29, 4, 21, aOh, aOh, false);
            this.setBlockState(world, aOh, 27, 3, 21, structureBoundingBox);
            this.setBlockState(world, aOh, 30, 3, 21, structureBoundingBox);
            this.setBlockState(world, aOh, 26, 2, 21, structureBoundingBox);
            this.setBlockState(world, aOh, 31, 2, 21, structureBoundingBox);
            this.setBlockState(world, aOh, 25, 1, 21, structureBoundingBox);
            this.setBlockState(world, aOh, 32, 1, 21, structureBoundingBox);
            int j = 0;

            while (j < 7) {
                this.setBlockState(world, bLX, 28 - j, 6 + j, 21, structureBoundingBox);
                this.setBlockState(world, bLX, 29 + j, 6 + j, 21, structureBoundingBox);
                j++;
                if (i == 0) {
                    break;
                }
            }

            j = 0;

            while (j < 4) {
                this.setBlockState(world, bLX, 28 - j, 9 + j, 21, structureBoundingBox);
                this.setBlockState(world, bLX, 29 + j, 9 + j, 21, structureBoundingBox);
                j++;
                if (i == 0) {
                    break;
                }
            }

            this.setBlockState(world, bLX, 28, 12, 21, structureBoundingBox);
            this.setBlockState(world, bLX, 29, 12, 21, structureBoundingBox);
            j = 0;

            while (j < 3) {
                this.setBlockState(world, bLX, 22 - j * 2, 8, 21, structureBoundingBox);
                this.setBlockState(world, bLX, 22 - j * 2, 9, 21, structureBoundingBox);
                this.setBlockState(world, bLX, 35 + j * 2, 8, 21, structureBoundingBox);
                this.setBlockState(world, bLX, 35 + j * 2, 9, 21, structureBoundingBox);
                j++;
                if (i == 0) {
                    break;
                }
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, 15, 13, 21, 42, 15, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 1, 21, 15, 6, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 16, 1, 21, 16, 5, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 17, 1, 21, 20, 4, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 21, 1, 21, 21, 3, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 22, 1, 21, 22, 2, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 23, 1, 21, 24, 1, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 42, 1, 21, 42, 6, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 41, 1, 21, 41, 5, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 37, 1, 21, 40, 4, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 36, 1, 21, 36, 3, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 35, 1, 21, 35, 2, 21, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 33, 1, 21, 34, 1, 21, bBJ, bBJ, false);
        }
    }

    public StructureOceanMonumentPiecesMonumentBuilding(
            Random random, int i, int j, Facing facing) {
        this(random, i, j, facing, StructureComponent.f());
    }

    private StructureOceanMonumentPiecesMonumentBuilding(
            Random random, int i, int j, Facing aAcValue, int initialFlowToken) {
        super(0);

        int l1 = initialFlowToken;

        this.coordBaseMode = aAcValue;
        int k = l1;
        switch (this.coordBaseMode) {
            case NORTH:
            case SOUTH:
                this.boundingBox = new StructureBoundingBox(i, 39, j, i + 58 - 1, 61, j + 58 - 1);
                if (k == 0) {
                    break;
                }
            default:
                this.boundingBox = new StructureBoundingBox(i, 39, j, i + 58 - 1, 61, j + 58 - 1);
        }

        List list = this.makeRoomChunkList(random);
        this.bMZ.d = true;
        this.nM.add(new StructureOceanMonumentPiecesEntryRoom(this.coordBaseMode, this.bMZ));
        this.nM.add(
                new StructureOceanMonumentPiecesMonumentCoreRoom(
                        this.coordBaseMode, this.bNa, random));
        ArrayList arrayList = Lists.newArrayList();
        arrayList.add(
                new StructureOceanMonumentPiecesXYDoubleRoomFitHelper(
                        (OceanMonumentAccessMarker) null));
        arrayList.add(
                new StructureOceanMonumentPiecesYZDoubleRoomFitHelper(
                        (OceanMonumentAccessMarker) null));
        arrayList.add(
                new StructureOceanMonumentPiecesZDoubleRoomFitHelper(
                        (OceanMonumentAccessMarker) null));
        arrayList.add(
                new StructureOceanMonumentPiecesXDoubleRoomFitHelper(
                        (OceanMonumentAccessMarker) null));
        arrayList.add(
                new StructureOceanMonumentPiecesYDoubleRoomFitHelper(
                        (OceanMonumentAccessMarker) null));
        arrayList.add(
                new StructureOceanMonumentPiecesFitSimpleRoomTopHelper(
                        (OceanMonumentAccessMarker) null));
        arrayList.add(
                new StructureOceanMonumentPiecesFitSimpleRoomHelper(
                        (OceanMonumentAccessMarker) null));

        for (java.util.Iterator<?> __recoveryIterator171_38461 = (list).iterator();
                (__recoveryIterator171_38461).hasNext(); ) {
            StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition =
                    (StructureOceanMonumentPiecesRoomDefinition)
                            (__recoveryIterator171_38461).next();
            if (!structureOceanMonumentPiecesRoomDefinition.d
                    && !structureOceanMonumentPiecesRoomDefinition.method_b417d7500ec59d69e373()) {
                for (java.util.Iterator<?> __recoveryIterator171_38649 = (arrayList).iterator();
                        (__recoveryIterator171_38649).hasNext(); ) {
                    StructureOceanMonumentPiecesMonumentRoomFitHelper
                            structureOceanMonumentPiecesMonumentRoomFitHelper =
                                    (StructureOceanMonumentPiecesMonumentRoomFitHelper)
                                            (__recoveryIterator171_38649).next();
                    if (structureOceanMonumentPiecesMonumentRoomFitHelper
                            .method_9f371031dfea5fef8e92(
                                    structureOceanMonumentPiecesRoomDefinition)) {
                        this.nM.add(
                                structureOceanMonumentPiecesMonumentRoomFitHelper
                                        .method_4a728e1e54c66005ef26(
                                                this.coordBaseMode,
                                                structureOceanMonumentPiecesRoomDefinition,
                                                random));
                        if (k == 0) {
                            break;
                        }
                    }

                    if (k != 0) {
                        break;
                    }
                }
            }

            if (k != 0) {
                break;
            }
        }

        int minY = this.boundingBox.minY;
        int j1 = this.getXWithOffset(9, 22);
        int k1 = this.getZWithOffset(9, 22);

        for (java.util.Iterator<?> __recoveryIterator171_39365 = (this.nM).iterator();
                (__recoveryIterator171_39365).hasNext(); ) {
            StructureOceanMonumentPiecesPiece structureOceanMonumentPiecesPiece =
                    (StructureOceanMonumentPiecesPiece) (__recoveryIterator171_39365).next();
            structureOceanMonumentPiecesPiece.getBoundingBox().offset(j1, minY, k1);
            if (k != 0) {
                break;
            }
        }

        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.func_175899_a(
                        this.getXWithOffset(1, 1),
                        this.getYWithOffset(1),
                        this.getZWithOffset(1, 1),
                        this.getXWithOffset(23, 21),
                        this.getYWithOffset(8),
                        this.getZWithOffset(23, 21));
        StructureBoundingBox structureBoundingBoxValue =
                StructureBoundingBox.func_175899_a(
                        this.getXWithOffset(34, 1),
                        this.getYWithOffset(1),
                        this.getZWithOffset(34, 1),
                        this.getXWithOffset(56, 21),
                        this.getYWithOffset(8),
                        this.getZWithOffset(56, 21));
        StructureBoundingBox structureBoundingBoxValue2 =
                StructureBoundingBox.func_175899_a(
                        this.getXWithOffset(22, 22),
                        this.getYWithOffset(13),
                        this.getZWithOffset(22, 22),
                        this.getXWithOffset(35, 35),
                        this.getYWithOffset(17),
                        this.getZWithOffset(35, 35));
        int l = random.nextInt();
        this.nM.add(
                new StructureOceanMonumentPiecesWingRoom(
                        this.coordBaseMode, structureBoundingBox, l++));
        this.nM.add(
                new StructureOceanMonumentPiecesWingRoom(
                        this.coordBaseMode, structureBoundingBoxValue, l++));
        this.nM.add(
                new StructureOceanMonumentPiecesPenthouse(
                        this.coordBaseMode, structureBoundingBoxValue2));
    }

    private void generateEntrance(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 22, 5, 35, 17)) {
            this.fillWithBlocks(world, structureBoundingBox, 25, 0, 0, 32, 8, 20, bBJ, bBJ, false);
            int j = 0;

            while (j < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        24,
                        2,
                        5 + j * 4,
                        24,
                        4,
                        5 + j * 4,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        22,
                        4,
                        5 + j * 4,
                        23,
                        4,
                        5 + j * 4,
                        aOh,
                        aOh,
                        false);
                this.setBlockState(world, aOh, 25, 5, 5 + j * 4, structureBoundingBox);
                this.setBlockState(world, aOh, 26, 6, 5 + j * 4, structureBoundingBox);
                this.setBlockState(world, bLV, 26, 5, 5 + j * 4, structureBoundingBox);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        33,
                        2,
                        5 + j * 4,
                        33,
                        4,
                        5 + j * 4,
                        aOh,
                        aOh,
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        34,
                        4,
                        5 + j * 4,
                        35,
                        4,
                        5 + j * 4,
                        aOh,
                        aOh,
                        false);
                this.setBlockState(world, aOh, 32, 5, 5 + j * 4, structureBoundingBox);
                this.setBlockState(world, aOh, 31, 6, 5 + j * 4, structureBoundingBox);
                this.setBlockState(world, bLV, 31, 5, 5 + j * 4, structureBoundingBox);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        27,
                        6,
                        5 + j * 4,
                        30,
                        6,
                        5 + j * 4,
                        bLt,
                        bLt,
                        false);
                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    private void generateUpperRoof(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 0, 21, 6, 58)) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 21, 6, 0, 57, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 21, 6, 7, 57, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 4, 21, 6, 4, 53, bLt, bLt, false);
            int j = 0;

            while (j < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        j,
                        j + 1,
                        21,
                        j,
                        j + 1,
                        57 - j,
                        aOh,
                        aOh,
                        false);
                j++;
                if (i == 0) {
                    break;
                }
            }

            int k = 23;

            while (k < 53) {
                this.setBlockState(world, bLU, 5, 5, k, structureBoundingBox);
                k += 3;
                if (i == 0) {
                    break;
                }
            }

            this.setBlockState(world, bLU, 5, 5, 52, structureBoundingBox);
            k = 0;

            while (k < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        k,
                        k + 1,
                        21,
                        k,
                        k + 1,
                        57 - k,
                        aOh,
                        aOh,
                        false);
                k++;
                if (i == 0) {
                    break;
                }
            }

            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 52, 6, 3, 52, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 51, 5, 3, 53, bLt, bLt, false);
        }

        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 51, 21, 58, 58)) {
            this.fillWithBlocks(world, structureBoundingBox, 51, 0, 21, 57, 0, 57, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 51, 1, 21, 57, 7, 57, bBJ, bBJ, false);
            this.fillWithBlocks(world, structureBoundingBox, 51, 4, 21, 53, 4, 53, bLt, bLt, false);
            int l = 0;

            while (l < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        57 - l,
                        l + 1,
                        21,
                        57 - l,
                        l + 1,
                        57 - l,
                        aOh,
                        aOh,
                        false);
                l++;
                if (i == 0) {
                    break;
                }
            }

            byte b0 = 23;

            while (b0 < 53) {
                this.setBlockState(world, bLU, 52, 5, b0, structureBoundingBox);
                b0 += 3;
                if (i == 0) {
                    break;
                }
            }

            this.setBlockState(world, bLU, 52, 5, 52, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 51, 1, 52, 53, 3, 52, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 52, 1, 51, 52, 3, 53, bLt, bLt, false);
        }

        if (this.doesChunkIntersectBoundingBox(structureBoundingBox, 0, 51, 57, 57)) {
            this.fillWithBlocks(world, structureBoundingBox, 7, 0, 51, 50, 0, 57, bLt, bLt, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 51, 50, 10, 57, bBJ, bBJ, false);
            int i1 = 0;

            while (i1 < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        i1 + 1,
                        i1 + 1,
                        57 - i1,
                        56 - i1,
                        i1 + 1,
                        57 - i1,
                        aOh,
                        aOh,
                        false);
                i1++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public StructureOceanMonumentPiecesMonumentBuilding() {
        this.nM = com.google.common.collect.Lists.newArrayList();
    }
}
