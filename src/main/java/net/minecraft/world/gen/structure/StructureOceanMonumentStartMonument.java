package net.minecraft.world.gen.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import com.google.common.collect.Sets;

import java.util.Random;
import java.util.Set;

public class StructureOceanMonumentStartMonument extends StructureStart {
    private Set aKF = Sets.newHashSet();

    private boolean f;
    private static String[] packedTextFragments;

    private void method_904d236fa04a44a2b5ac(World world, Random random, int i, int j) {
        random.setSeed(world.getSeed());
        long k = random.nextLong();
        long l = random.nextLong();
        long i1 = i * k;
        long j1 = j * l;
        random.setSeed(i1 ^ j1 ^ world.getSeed());
        int k1 = i * 16 + 8 - 29;
        int l1 = j * 16 + 8 - 29;
        Facing facing = FacingPlane.HORIZONTAL.random(random);
        this.components.add(new StructureOceanMonumentPiecesMonumentBuilding(random, k1, l1, facing));
        this.cj();
        this.f = true;
    }

    static {
        net.minecraft.world.gen.structure.StructureOceanMonumentStartMonument.packedTextFragments = new String[5];
        net.minecraft.world.gen.structure.StructureOceanMonumentStartMonument.packedTextFragments[0] = new String("X");
        net.minecraft.world.gen.structure.StructureOceanMonumentStartMonument.packedTextFragments[1] = new String("Z");
        net.minecraft.world.gen.structure.StructureOceanMonumentStartMonument.packedTextFragments[2] = new String("X");
        net.minecraft.world.gen.structure.StructureOceanMonumentStartMonument.packedTextFragments[3] = new String("Z");
    }

    public StructureOceanMonumentStartMonument(World world, Random random, int i, int j) {
        super(i, j);
        this.method_904d236fa04a44a2b5ac(world, random, i, j);
    }

    public void readFromNBT(CompoundTag compoundTag) {
        int k = StructureComponent.cR();
        super.readFromNBT(compoundTag);
        int i = k;
        if (compoundTag.hasKey("Processed", 9)) {
            NBTTagList nbtTagList = compoundTag.getTagList("Processed", 10);
            int j = 0;

            while (j < nbtTagList.tagCount()) {
                CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(j);
                this.aKF.add(
                        new ChunkCoordIntPair(
                                compoundTagValue.getInteger(packedTextFragments[2]),
                                compoundTagValue.getInteger(packedTextFragments[3])));
                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public void generateStructure(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        if (!this.f) {
            this.components.clear();
            this.method_904d236fa04a44a2b5ac(
                    world, random, this.getChunkPosX(), this.getChunkPosZ());
        }

        super.generateStructure(world, random, structureBoundingBox);
    }

    public boolean method_8f6348b78777a95640a1(ChunkCoordIntPair chunkCoordIntPair) {
        return this.aKF.contains(chunkCoordIntPair)
                ? false
                : super.method_8f6348b78777a95640a1(chunkCoordIntPair);
    }

    public void writeToNBT(CompoundTag compoundTag) {
        int j = StructureComponent.cR();
        super.writeToNBT(compoundTag);
        int i = j;
        NBTTagList nbtTagList = new NBTTagList();

        for (java.util.Iterator<?> __recoveryIterator171_27156 = (this.aKF).iterator();
                (__recoveryIterator171_27156).hasNext(); ) {
            ChunkCoordIntPair chunkCoordIntPair =
                    (ChunkCoordIntPair) (__recoveryIterator171_27156).next();
            CompoundTag compoundTagValue = new CompoundTag();
            compoundTagValue.setInteger(packedTextFragments[0], chunkCoordIntPair.chunkXPos);
            compoundTagValue.setInteger(packedTextFragments[1], chunkCoordIntPair.chunkZPos);
            nbtTagList.appendTag(compoundTagValue);
            if (i == 0) {
                break;
            }
        }

        compoundTag.setTag("Processed", nbtTagList);
    }

    public void method_21c9d6746214a41e69d7(ChunkCoordIntPair chunkCoordIntPair) {
        super.method_21c9d6746214a41e69d7(chunkCoordIntPair);
        this.aKF.add(chunkCoordIntPair);
    }

    public StructureOceanMonumentStartMonument() {}
}
