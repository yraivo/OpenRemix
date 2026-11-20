package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;

import net.minecraft.util.Facing;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class StructureStrongholdPieces {
    static int totalWeight;
    private static Class strongComponentType;
    private static StructureStrongholdPiecesStones strongholdStones;

    private static List structurePieceList;

    private static StructureStrongholdPiecesPieceWeight[] pieceWeightArray;

    private static StructureStrongholdPiecesStronghold findNextPiece(
            StructureStrongholdPiecesStairs2 structureStrongholdPiecesStairs2,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        int i1 = StructureComponent.cR();
        if (!canAddStructurePieces()) {
            return null;
        }

        if (net.minecraft.world.gen.structure.StructureStrongholdPieces.strongComponentType
                != null) {
            StructureStrongholdPiecesStronghold structureStrongholdPiecesStronghold =
                    findAndCreatePieceFactory(
                            net.minecraft.world.gen.structure.StructureStrongholdPieces
                                    .strongComponentType,
                            list,
                            random,
                            i,
                            j,
                            k,
                            facing,
                            l);
            net.minecraft.world.gen.structure.StructureStrongholdPieces.strongComponentType = null;
            if (structureStrongholdPiecesStronghold != null) {
                return structureStrongholdPiecesStronghold;
            }
        }

        int k1 = 0;

        label56:
        while (k1 < 5) {
            k1++;
            int j1 =
                    random.nextInt(
                            net.minecraft.world.gen.structure.StructureStrongholdPieces
                                    .totalWeight);
            Iterator iterator =
                    net.minecraft.world.gen.structure.StructureStrongholdPieces.structurePieceList
                            .iterator();

            while (true) {
                label52:
                if (iterator.hasNext()) {
                    StructureStrongholdPiecesPieceWeight bNk =
                            (StructureStrongholdPiecesPieceWeight) iterator.next();
                    j1 -= bNk.pieceWeight;
                    if (j1 < 0) {
                        if (!bNk.canSpawnMoreStructuresOfType(l)
                                || bNk == structureStrongholdPiecesStairs2.strongholdPieceWeight
                                        && i1 != 0) {
                            break label52;
                        }

                        StructureStrongholdPiecesStronghold
                                structureStrongholdPiecesStrongholdValue =
                                        findAndCreatePieceFactory(
                                                bNk.pieceClass, list, random, i, j, k, facing, l);
                        if (structureStrongholdPiecesStrongholdValue != null) {
                            bNk.instancesSpawned++;
                            structureStrongholdPiecesStairs2.strongholdPieceWeight = bNk;
                            if (!bNk.canSpawnMoreStructures()) {
                                net.minecraft.world.gen.structure.StructureStrongholdPieces
                                        .structurePieceList
                                        .remove(bNk);
                            }

                            return structureStrongholdPiecesStrongholdValue;
                        }
                    }

                    if (i1 != 0) {
                        continue;
                    }
                }

                if (i1 == 0) {
                    break label56;
                }
                break;
            }
        }

        StructureBoundingBox structureBoundingBox =
                StructureStrongholdPiecesCorridor.createPiece(list, random, i, j, k, facing);
        return structureBoundingBox != null && structureBoundingBox.minY > 1
                ? new StructureStrongholdPiecesCorridor(l, random, structureBoundingBox, facing)
                : null;
    }

    public static StructureStrongholdPiecesStones access$200() {
        return net.minecraft.world.gen.structure.StructureStrongholdPieces.strongholdStones;
    }

    private static boolean canAddStructurePieces() {
        int j = StructureComponent.f();
        byte b0 = 0;
        int i = j;
        net.minecraft.world.gen.structure.StructureStrongholdPieces.totalWeight = 0;

        for (java.util.Iterator<?> __recoveryIterator171_5509 =
                        (net.minecraft.world.gen.structure.StructureStrongholdPieces
                                        .structurePieceList)
                                .iterator();
                (__recoveryIterator171_5509).hasNext(); ) {
            StructureStrongholdPiecesPieceWeight structureStrongholdPiecesPieceWeight =
                    (StructureStrongholdPiecesPieceWeight) (__recoveryIterator171_5509).next();
            if (structureStrongholdPiecesPieceWeight.instancesLimit > 0
                    && structureStrongholdPiecesPieceWeight.instancesSpawned
                            < structureStrongholdPiecesPieceWeight.instancesLimit) {
                b0 = 1;
            }

            net.minecraft.world.gen.structure.StructureStrongholdPieces.totalWeight =
                    net.minecraft.world.gen.structure.StructureStrongholdPieces.totalWeight
                            + structureStrongholdPiecesPieceWeight.pieceWeight;
            if (i != 0) {
                break;
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    private static StructureStrongholdPiecesStronghold findAndCreatePieceFactory(
            Class oclass, List list, Random random, int i, int j, int k, Facing facing, int l) {
        Object object = null;
        if (oclass == StructureStrongholdPiecesStraight.class) {
            object =
                    StructureStrongholdPiecesStraight.createPiece(list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesPrison.class) {
            object = StructureStrongholdPiecesPrison.createPiece(list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesLeftTurn.class) {
            object =
                    StructureStrongholdPiecesLeftTurn.createPiece(list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesRightTurn.class) {
            object =
                    StructureStrongholdPiecesRightTurn.createPiece(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesRoomCrossing.class) {
            object =
                    StructureStrongholdPiecesRoomCrossing.createPiece(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesStairsStraight.class) {
            object =
                    StructureStrongholdPiecesStairsStraight.createPiece(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesStairs.class) {
            object = StructureStrongholdPiecesStairs.createPiece(list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesCrossing.class) {
            object =
                    StructureStrongholdPiecesCrossing.createPiece(list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesChestCorridor.class) {
            object =
                    StructureStrongholdPiecesChestCorridor.createPiece(
                            list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesLibrary.class) {
            object = StructureStrongholdPiecesLibrary.createPiece(list, random, i, j, k, facing, l);
        } else if (oclass == StructureStrongholdPiecesPortalRoom.class) {
            object =
                    StructureStrongholdPiecesPortalRoom.createPiece(
                            list, random, i, j, k, facing, l);
        }

        return (StructureStrongholdPiecesStronghold) object;
    }

    static Class access$102(Class oclass) {
        net.minecraft.world.gen.structure.StructureStrongholdPieces.strongComponentType = oclass;
        return oclass;
    }

    public static void registerStrongholdPieces() {
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesChestCorridor.class, "SHCC");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesCorridor.class, "SHFC");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesCrossing.class, "SH5C");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesLeftTurn.class, "SHLT");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesLibrary.class, "SHLi");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesPortalRoom.class, "SHPR");
        MapGenStructureIO.registerStructureComponent(StructureStrongholdPiecesPrison.class, "SHPH");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesRightTurn.class, "SHRT");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesRoomCrossing.class, "SHRC");
        MapGenStructureIO.registerStructureComponent(StructureStrongholdPiecesStairs.class, "SHSD");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesStairs2.class, "SHStart");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesStraight.class, "SHS");
        MapGenStructureIO.registerStructureComponent(
                StructureStrongholdPiecesStairsStraight.class, "SHSSD");
    }

    private static StructureComponent findNextComponent(
            StructureStrongholdPiecesStairs2 structureStrongholdPiecesStairs2,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        if (l > 50) {
            return null;
        }

        if (Math.abs(i - structureStrongholdPiecesStairs2.getBoundingBox().minX) <= 112
                && Math.abs(k - structureStrongholdPiecesStairs2.getBoundingBox().minZ) <= 112) {
            StructureStrongholdPiecesStronghold structureStrongholdPiecesStronghold =
                    findNextPiece(
                            structureStrongholdPiecesStairs2, list, random, i, j, k, facing, l + 1);
            if (structureStrongholdPiecesStronghold != null) {
                list.add(structureStrongholdPiecesStronghold);
                structureStrongholdPiecesStairs2.iy.add(structureStrongholdPiecesStronghold);
            }

            return structureStrongholdPiecesStronghold;
        } else {
            return null;
        }
    }

    public static void prepareStructurePieces() {
        net.minecraft.world.gen.structure.StructureStrongholdPieces.structurePieceList =
                Lists.newArrayList();
        StructureStrongholdPiecesPieceWeight[] bMYValue =
                net.minecraft.world.gen.structure.StructureStrongholdPieces.pieceWeightArray;
        int l = StructureComponent.cR();
        int j = bMYValue.length;
        int i = l;
        int k = 0;

        while (k < j) {
            StructureStrongholdPiecesPieceWeight structureStrongholdPiecesPieceWeight = bMYValue[k];
            structureStrongholdPiecesPieceWeight.instancesSpawned = 0;
            net.minecraft.world.gen.structure.StructureStrongholdPieces.structurePieceList.add(
                    structureStrongholdPiecesPieceWeight);
            k++;
            if (i == 0) {
                break;
            }
        }

        net.minecraft.world.gen.structure.StructureStrongholdPieces.strongComponentType = null;
    }

    static StructureComponent access$000(
            StructureStrongholdPiecesStairs2 structureStrongholdPiecesStairs2,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        return findNextComponent(
                structureStrongholdPiecesStairs2, list, random, i, j, k, facing, l);
    }

    static {
        net.minecraft.world.gen.structure.StructureStrongholdPieces.pieceWeightArray =
                new StructureStrongholdPiecesPieceWeight[] {
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesStraight.class, 40, 0),
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesPrison.class, 5, 5),
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesLeftTurn.class, 20, 0),
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesRightTurn.class, 20, 0),
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesRoomCrossing.class, 10, 6),
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesStairsStraight.class, 5, 5),
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesStairs.class, 5, 5),
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesCrossing.class, 5, 4),
                    new StructureStrongholdPiecesPieceWeight(
                            StructureStrongholdPiecesChestCorridor.class, 5, 4),
                    new StructureStrongholdPiecesPieceWeight1(
                            StructureStrongholdPiecesLibrary.class, 10, 2),
                    new StructureStrongholdPiecesPieceWeightArray(
                            StructureStrongholdPiecesPortalRoom.class, 20, 1)
                };
        net.minecraft.world.gen.structure.StructureStrongholdPieces.strongholdStones =
                new StructureStrongholdPiecesStones(null);
    }
}
