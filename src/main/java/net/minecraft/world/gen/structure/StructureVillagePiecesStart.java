package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;

import net.minecraft.world.biome.WorldChunkManager;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesStart extends StructureVillagePiecesWell {
    public WorldChunkManager worldChunkMngr;
    public boolean inDesert;
    public List mc = Lists.newArrayList();
    public List kI = Lists.newArrayList();
    public StructureVillagePiecesPieceWeight structVillagePieceWeight;
    public int terrainType;
    public List structureVillageWeightedPieceList;

    public StructureVillagePiecesStart() {}

    public WorldChunkManager getWorldChunkManager() {
        return this.worldChunkMngr;
    }

    public StructureVillagePiecesStart(
            WorldChunkManager bMNValue,
            int p171_1,
            Random random,
            int p171_3,
            int p171_4,
            List list,
            int p171_6) {
        super(null, 0, random, p171_3, p171_4);
        this.worldChunkMngr = bMNValue;
        this.structureVillageWeightedPieceList = list;
        this.terrainType = p171_6;
        net.minecraft.world.biome.BiomeGenBase biomeGenBase =
                bMNValue.getBiomeGenerator(
                        new net.minecraft.util.BlockPos(p171_3, 0, p171_4),
                        net.minecraft.world.biome.BiomeGenBase.field_180279_ad);
        this.inDesert =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (biomeGenBase != net.minecraft.world.biome.BiomeGenBase.desert
                                        && biomeGenBase
                                                != net.minecraft.world.biome.BiomeGenBase
                                                        .desertHills
                                ? 0
                                : 1));
        this.setDesertVillage(this.inDesert);
    }
}
