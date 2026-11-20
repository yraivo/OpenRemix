package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

abstract class StructureNetherBridgePiece extends StructureComponent {
    protected static final List netherFortressChestContents =
            Lists.newArrayList(
                    new WeightedRandomChestContent[] {
                        new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5),
                        new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5),
                        new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15),
                        new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5),
                        new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5),
                        new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5),
                        new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5),
                        new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10),
                        new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8),
                        new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5),
                        new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3),
                        new WeightedRandomChestContent(
                                Item.getItemFromBlock(Blocks.obsidian), 0, 2, 4, 2)
                    });

    private int getTotalWeight(List list) {
        int k = StructureComponent.cR();
        byte b0 = 0;
        int i = k;
        int j = 0;

        for (java.util.Iterator<?> __recoveryIterator171_1641 = (list).iterator();
                (__recoveryIterator171_1641).hasNext(); ) {
            StructureNetherBridgePiecesPieceWeight structureNetherBridgePiecesPieceWeight =
                    (StructureNetherBridgePiecesPieceWeight) (__recoveryIterator171_1641).next();
            if (structureNetherBridgePiecesPieceWeight.dD > 0
                    && structureNetherBridgePiecesPieceWeight.c
                            < structureNetherBridgePiecesPieceWeight.dD) {
                b0 = 1;
            }

            j += structureNetherBridgePiecesPieceWeight.cz;
            if (i == 0) {
                break;
            }
        }

        return b0 != 0 ? j : -1;
    }

    private StructureNetherBridgePiece func_175871_a(
            StructureNetherBridgePiecesStart structureNetherBridgePiecesStart,
            List list,
            List listValue,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        int j2 = StructureComponent.cR();
        int j1 = this.getTotalWeight(list);
        int i1 = j2;
        int k1 = j1 > 0 && l <= 30 ? 1 : 0;
        int l1 = 0;

        while (l1 < 5 && k1 != 0) {
            l1++;
            int i2 = random.nextInt(j1);
            Iterator iterator = list.iterator();

            while (true) {
                label45:
                if (iterator.hasNext()) {
                    StructureNetherBridgePiecesPieceWeight bMq =
                            (StructureNetherBridgePiecesPieceWeight) iterator.next();
                    i2 -= bMq.cz;
                    if (i2 < 0) {
                        if (!bMq.method_3baace372e02b00ebb50(l)
                                || bMq
                                                == structureNetherBridgePiecesStart
                                                        .theNetherBridgePieceWeight
                                        && !bMq.e
                                        && i1 != 0) {
                            break label45;
                        }

                        StructureNetherBridgePiece structureNetherBridgePiece =
                                StructureNetherBridgePieces.method_190167b43866a26537fc(
                                        bMq, listValue, random, i, j, k, facing, l);
                        if (structureNetherBridgePiece != null) {
                            bMq.c++;
                            structureNetherBridgePiecesStart.theNetherBridgePieceWeight = bMq;
                            if (!bMq.aY()) {
                                list.remove(bMq);
                            }

                            return structureNetherBridgePiece;
                        }
                    }

                    if (i1 != 0) {
                        continue;
                    }
                }

                if (i1 == 0) {
                    return StructureNetherBridgePiecesEnd.method_12abd4d1ac3751bacef7(
                            listValue, random, i, j, k, facing, l);
                }
                break;
            }
        }

        return StructureNetherBridgePiecesEnd.method_12abd4d1ac3751bacef7(
                listValue, random, i, j, k, facing, l);
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {}

    protected StructureComponent getNextComponentX(
            StructureNetherBridgePiecesStart structureNetherBridgePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            boolean flag) {
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.WEST,
                            this.getComponentType(),
                            flag);
                case SOUTH:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.WEST,
                            this.getComponentType(),
                            flag);
                case WEST:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            this.getComponentType(),
                            flag);
                case EAST:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            this.getComponentType(),
                            flag);
            }
        }

        return null;
    }

    private StructureComponent func_175870_a(
            StructureNetherBridgePiecesStart p171_0,
            List list,
            Random random,
            int p171_3,
            int p171_4,
            int p171_5,
            Facing facing,
            int p171_7,
            boolean p171_8) {
        if (java.lang.Math.abs(p171_3 - p171_0.getBoundingBox().minX) <= 112
                && java.lang.Math.abs(p171_5 - p171_0.getBoundingBox().minZ) <= 112) {
            java.util.List listValue = p171_0.vl;
            if (p171_8) {
                listValue = p171_0.secondaryWeights;
            }

            net.minecraft.world.gen.structure.StructureNetherBridgePiece
                    structureNetherBridgePiece =
                            this.func_175871_a(
                                    p171_0,
                                    listValue,
                                    list,
                                    random,
                                    p171_3,
                                    p171_4,
                                    p171_5,
                                    facing,
                                    p171_7 + 1);
            if (structureNetherBridgePiece != null) {
                list.add(structureNetherBridgePiece);
                p171_0.iy.add(structureNetherBridgePiece);
            }

            return structureNetherBridgePiece;
        } else {
            return net.minecraft.world.gen.structure.StructureNetherBridgePiecesEnd
                    .method_12abd4d1ac3751bacef7(
                            list, random, p171_3, p171_4, p171_5, facing, p171_7);
        }
    }

    protected static boolean isAboveGround(StructureBoundingBox structureBoundingBox) {
        return structureBoundingBox != null && structureBoundingBox.minY > 10;
    }

    public StructureNetherBridgePiece() {}

    protected StructureNetherBridgePiece(int i) {
        super(i);
    }

    protected StructureComponent getNextComponentNormal(
            StructureNetherBridgePiecesStart structureNetherBridgePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            boolean flag) {
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + i,
                            this.boundingBox.minY + j,
                            this.boundingBox.minZ - 1,
                            this.coordBaseMode,
                            this.getComponentType(),
                            flag);
                case SOUTH:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + i,
                            this.boundingBox.minY + j,
                            this.boundingBox.maxZ + 1,
                            this.coordBaseMode,
                            this.getComponentType(),
                            flag);
                case WEST:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + j,
                            this.boundingBox.minZ + i,
                            this.coordBaseMode,
                            this.getComponentType(),
                            flag);
                case EAST:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + j,
                            this.boundingBox.minZ + i,
                            this.coordBaseMode,
                            this.getComponentType(),
                            flag);
            }
        }

        return null;
    }

    protected StructureComponent getNextComponentZ(
            StructureNetherBridgePiecesStart structureNetherBridgePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            boolean flag) {
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.EAST,
                            this.getComponentType(),
                            flag);
                case SOUTH:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.EAST,
                            this.getComponentType(),
                            flag);
                case WEST:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            this.getComponentType(),
                            flag);
                case EAST:
                    return this.func_175870_a(
                            structureNetherBridgePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            this.getComponentType(),
                            flag);
            }
        }

        return null;
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {}
}
