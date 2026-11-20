package net.minecraft.world.gen.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class MapGenVillageStart extends StructureStart {

    private boolean hasMoreThanTwoComponents;

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.setBoolean("Valid", this.hasMoreThanTwoComponents);
    }

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.hasMoreThanTwoComponents = compoundTag.getBoolean("Valid");
    }

    public boolean isSizeableStructure() {
        return this.hasMoreThanTwoComponents;
    }

    public MapGenVillageStart(World world, Random random, int i, int j, int k) {
        super(i, j);
        List list = StructureVillagePieces.getStructureVillageWeightedPieceList(random, k);
        int l1 = StructureComponent.cR();
        StructureVillagePiecesStart structureVillagePiecesStart =
                new StructureVillagePiecesStart(
                        world.getWorldChunkManager(),
                        0,
                        random,
                        (i << 4) + 2,
                        (j << 4) + 2,
                        list,
                        k);
        this.components.add(structureVillagePiecesStart);
        structureVillagePiecesStart.buildComponent(
                structureVillagePiecesStart, this.components, random);
        int l = l1;
        List listValue = structureVillagePiecesStart.kI;
        List listValue2 = structureVillagePiecesStart.mc;

        while (!listValue.isEmpty() || !listValue2.isEmpty()) {
            {
                if (listValue.isEmpty()) {
                    int i1 = random.nextInt(listValue2.size());
                    StructureComponent structureComponent =
                            (StructureComponent) listValue2.remove(i1);
                    structureComponent.buildComponent(
                            structureVillagePiecesStart, this.components, random);
                    if (!(l != 0)) {

                        int j1 = random.nextInt(listValue.size());
                        StructureComponent structureComponentValue =
                                (StructureComponent) listValue.remove(j1);
                        structureComponentValue.buildComponent(
                                structureVillagePiecesStart, this.components, random);
                    }
                } else {

                    int j1 = random.nextInt(listValue.size());
                    StructureComponent structureComponentValue =
                            (StructureComponent) listValue.remove(j1);
                    structureComponentValue.buildComponent(
                            structureVillagePiecesStart, this.components, random);
                }
            }

            if (l == 0) {
                break;
            }
        }

        this.cj();
        int k1 = 0;

        for (java.util.Iterator<?> __recoveryIterator171_3312 = (this.components).iterator();
                (__recoveryIterator171_3312).hasNext(); ) {
            StructureComponent structureComponentValue2 =
                    (StructureComponent) (__recoveryIterator171_3312).next();
            if (!(structureComponentValue2 instanceof StructureVillagePiecesRoad)) {
                k1++;
            }

            if (l == 0) {
                break;
            }
        }

        this.hasMoreThanTwoComponents =
                remix.compat.compiler.JvmBooleanSlots.fromInt((k1 > 2 ? 1 : 0));
    }


    public MapGenVillageStart() {}
}
