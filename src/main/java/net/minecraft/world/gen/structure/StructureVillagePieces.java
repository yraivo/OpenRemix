package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePiecesChurch;
import net.minecraft.world.gen.structure.StructureVillagePiecesField1;
import net.minecraft.world.gen.structure.StructureVillagePiecesField2;
import net.minecraft.world.gen.structure.StructureVillagePiecesHall;
import net.minecraft.world.gen.structure.StructureVillagePiecesHouse1;
import net.minecraft.world.gen.structure.StructureVillagePiecesHouse2;
import net.minecraft.world.gen.structure.StructureVillagePiecesHouse3;
import net.minecraft.world.gen.structure.StructureVillagePiecesHouse4Garden;
import net.minecraft.world.gen.structure.StructureVillagePiecesPath;
import net.minecraft.world.gen.structure.StructureVillagePiecesStart;
import net.minecraft.world.gen.structure.StructureVillagePiecesTorch;
import net.minecraft.world.gen.structure.StructureVillagePiecesWell;
import net.minecraft.world.gen.structure.StructureVillagePiecesWoodHut;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class StructureVillagePieces {

    public static List getStructureVillageWeightedPieceList(Random random, int i) {
        ArrayList arrayList = Lists.newArrayList();
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesHouse4Garden.class,
                        4,
                        MathHelper.nextIntInclusive(random, 2 + i, 4 + i * 2)));
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesChurch.class,
                        20,
                        MathHelper.nextIntInclusive(random, 0 + i, 1 + i)));
        int k = StructureComponent.cR();
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesHouse1.class,
                        20,
                        MathHelper.nextIntInclusive(random, 0 + i, 2 + i)));
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesWoodHut.class,
                        3,
                        MathHelper.nextIntInclusive(random, 2 + i, 5 + i * 3)));
        int j = k;
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesHall.class,
                        15,
                        MathHelper.nextIntInclusive(random, 0 + i, 2 + i)));
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesField1.class,
                        3,
                        MathHelper.nextIntInclusive(random, 1 + i, 4 + i)));
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesField2.class,
                        3,
                        MathHelper.nextIntInclusive(random, 2 + i, 4 + i * 2)));
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesHouse2.class,
                        15,
                        MathHelper.nextIntInclusive(random, 0, 1 + i)));
        arrayList.add(
                new StructureVillagePiecesPieceWeight(
                        StructureVillagePiecesHouse3.class,
                        8,
                        MathHelper.nextIntInclusive(random, 0 + i, 3 + i * 2)));
        Iterator iterator = arrayList.iterator();

        while (iterator.hasNext()) {
            if (((StructureVillagePiecesPieceWeight) iterator.next()).villagePiecesLimit == 0) {
                iterator.remove();
                if (j == 0) {
                    break;
                }
            }
        }

        return arrayList;
    }

    public static void registerVillagePieces() {
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesHouse1.class, "ViBH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesField1.class, "ViDF");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesField2.class, "ViF");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesTorch.class, "ViL");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesHall.class, "ViPH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesHouse4Garden.class, "ViSH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesWoodHut.class, "ViSmH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesChurch.class, "ViST");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesHouse2.class, "ViS");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesStart.class, "ViStart");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesPath.class, "ViSR");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesHouse3.class, "ViTRH");
        MapGenStructureIO.registerStructureComponent(StructureVillagePiecesWell.class, "ViW");
    }

    private static StructureComponent findNextComponentVillagePath(
            StructureVillagePiecesStart structureVillagePiecesStart,
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

        if (Math.abs(i - structureVillagePiecesStart.getBoundingBox().minX) <= 112
                && Math.abs(k - structureVillagePiecesStart.getBoundingBox().minZ) <= 112) {
            StructureVillagePiecesVillage nextComponent =
                    findNextComponent(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l + 1);
            if (nextComponent != null) {
                int i1 =
                        (nextComponent.boundingBox.minX
                                        + nextComponent.boundingBox.maxX)
                                / 2;
                int j1 =
                        (nextComponent.boundingBox.minZ
                                        + nextComponent.boundingBox.maxZ)
                                / 2;
                int k1 =
                        nextComponent.boundingBox.maxX
                                - nextComponent.boundingBox.minX;
                int l1 =
                        nextComponent.boundingBox.maxZ
                                - nextComponent.boundingBox.minZ;
                int i2 = k1 > l1 ? k1 : l1;
                if (structureVillagePiecesStart
                        .getWorldChunkManager()
                        .areBiomesViable(i1, j1, i2 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    list.add(nextComponent);
                    structureVillagePiecesStart.mc.add(nextComponent);
                    return nextComponent;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static StructureComponent method_3f00e5c935f0d75ee343(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        return findNextComponentNormal(
                structureVillagePiecesStart, list, random, i, j, k, facing, l);
    }

    private static StructureVillagePiecesVillage findNextComponent(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        int i2 = StructureComponent.cR();
        int j1 = getTotalWeight(structureVillagePiecesStart.structureVillageWeightedPieceList);
        int i1 = i2;
        if (j1 <= 0) {
            return null;
        }

        int k1 = 0;

        label50:
        while (k1 < 5) {
            k1++;
            int l1 = random.nextInt(j1);
            Iterator iterator = structureVillagePiecesStart.structureVillageWeightedPieceList.iterator();

            while (true) {
                label46:
                if (iterator.hasNext()) {
                    StructureVillagePiecesPieceWeight bMO = (StructureVillagePiecesPieceWeight) iterator.next();
                    l1 -= bMO.villagePieceWeight;
                    if (l1 < 0) {
                        if (!bMO.canSpawnMoreVillagePiecesOfType(l)
                                || bMO == structureVillagePiecesStart.structVillagePieceWeight
                                        && structureVillagePiecesStart.structureVillageWeightedPieceList.size() > 1
                                        && i1 != 0) {
                            break label46;
                        }

                        StructureVillagePiecesVillage andCreateComponentFactory =
                                findAndCreateComponentFactory(
                                        structureVillagePiecesStart,
                                        bMO,
                                        list,
                                        random,
                                        i,
                                        j,
                                        k,
                                        facing,
                                        l);
                        if (andCreateComponentFactory != null) {
                            bMO.villagePiecesSpawned++;
                            structureVillagePiecesStart.structVillagePieceWeight = bMO;
                            if (!bMO.canSpawnMoreVillagePieces()) {
                                structureVillagePiecesStart.structureVillageWeightedPieceList.remove(bMO);
                            }

                            return andCreateComponentFactory;
                        }
                    }

                    if (i1 != 0) {
                        continue;
                    }
                }

                if (i1 == 0) {
                    break label50;
                }
                break;
            }
        }

        StructureBoundingBox structureBoundingBox =
                StructureVillagePiecesTorch.findPieceBox(
                        structureVillagePiecesStart, list, random, i, j, k, facing);
        return structureBoundingBox != null
                ? new StructureVillagePiecesTorch(
                        structureVillagePiecesStart, l, random, structureBoundingBox, facing)
                : null;
    }

    private static StructureVillagePiecesVillage findAndCreateComponentFactory(
            StructureVillagePiecesStart structureVillagePiecesStart,
            StructureVillagePiecesPieceWeight structureVillagePiecesPieceWeight,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        Class oclass = structureVillagePiecesPieceWeight.villagePieceClass;
        Object object = null;
        if (oclass == StructureVillagePiecesHouse4Garden.class) {
            object =
                    StructureVillagePiecesHouse4Garden.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        } else if (oclass == StructureVillagePiecesChurch.class) {
            object =
                    StructureVillagePiecesChurch.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        } else if (oclass == StructureVillagePiecesHouse1.class) {
            object =
                    StructureVillagePiecesHouse1.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        } else if (oclass == StructureVillagePiecesWoodHut.class) {
            object =
                    StructureVillagePiecesWoodHut.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        } else if (oclass == StructureVillagePiecesHall.class) {
            object =
                    StructureVillagePiecesHall.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        } else if (oclass == StructureVillagePiecesField1.class) {
            object =
                    StructureVillagePiecesField1.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        } else if (oclass == StructureVillagePiecesField2.class) {
            object =
                    StructureVillagePiecesField2.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        } else if (oclass == StructureVillagePiecesHouse2.class) {
            object =
                    StructureVillagePiecesHouse2.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        } else if (oclass == StructureVillagePiecesHouse3.class) {
            object =
                    StructureVillagePiecesHouse3.createPiece(
                            structureVillagePiecesStart, list, random, i, j, k, facing, l);
        }

        return (StructureVillagePiecesVillage) object;
    }

    static StructureComponent method_ed85da7976afa3b7220a(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        return findNextComponentVillagePath(
                structureVillagePiecesStart, list, random, i, j, k, facing, l);
    }

    private static StructureComponent findNextComponentNormal(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        if (l > 3 + structureVillagePiecesStart.terrainType) {
            return null;
        }

        if (Math.abs(i - structureVillagePiecesStart.getBoundingBox().minX) <= 112
                && Math.abs(k - structureVillagePiecesStart.getBoundingBox().minZ) <= 112) {
            StructureBoundingBox structureBoundingBox =
                    StructureVillagePiecesPath.findPieceBox(
                            structureVillagePiecesStart, list, random, i, j, k, facing);
            if (structureBoundingBox != null && structureBoundingBox.minY > 10) {
                StructureVillagePiecesPath structureVillagePiecesPath =
                        new StructureVillagePiecesPath(
                                structureVillagePiecesStart,
                                l,
                                random,
                                structureBoundingBox,
                                facing);
                int i1 =
                        (structureVillagePiecesPath.boundingBox.minX
                                        + structureVillagePiecesPath.boundingBox.maxX)
                                / 2;
                int j1 =
                        (structureVillagePiecesPath.boundingBox.minZ
                                        + structureVillagePiecesPath.boundingBox.maxZ)
                                / 2;
                int k1 =
                        structureVillagePiecesPath.boundingBox.maxX
                                - structureVillagePiecesPath.boundingBox.minX;
                int l1 =
                        structureVillagePiecesPath.boundingBox.maxZ
                                - structureVillagePiecesPath.boundingBox.minZ;
                int i2 = k1 > l1 ? k1 : l1;
                if (structureVillagePiecesStart
                        .getWorldChunkManager()
                        .areBiomesViable(i1, j1, i2 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    list.add(structureVillagePiecesPath);
                    structureVillagePiecesStart.kI.add(structureVillagePiecesPath);
                    return structureVillagePiecesPath;
                }
            }

            return null;
        } else {
            return null;
        }
    }


    private static int getTotalWeight(List list) {
        int k = StructureComponent.f();
        byte b0 = 0;
        int i = k;
        int j = 0;

        for (java.util.Iterator<?> __recoveryIterator171_39257 = (list).iterator();
                (__recoveryIterator171_39257).hasNext(); ) {
            StructureVillagePiecesPieceWeight structureVillagePiecesPieceWeight =
                    (StructureVillagePiecesPieceWeight) (__recoveryIterator171_39257).next();
            if (structureVillagePiecesPieceWeight.villagePiecesLimit > 0
                    && structureVillagePiecesPieceWeight.villagePiecesSpawned < structureVillagePiecesPieceWeight.villagePiecesLimit) {
                b0 = 1;
            }

            j += structureVillagePiecesPieceWeight.villagePieceWeight;
            if (i != 0) {
                break;
            }
        }

        return b0 != 0 ? j : -1;
    }
}
