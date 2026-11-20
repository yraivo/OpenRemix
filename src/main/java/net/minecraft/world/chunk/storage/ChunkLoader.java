package net.minecraft.world.chunk.storage;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader {

    private static String[] packedTextFragments;

    public static ChunkLoaderAnvilConverterData load(CompoundTag compoundTag) {
        int integer = compoundTag.getInteger("xPos");
        int j = compoundTag.getInteger("zPos");
        ChunkLoaderAnvilConverterData chunkLoaderAnvilConverterData =
                new ChunkLoaderAnvilConverterData(integer, j);
        chunkLoaderAnvilConverterData.blocks = compoundTag.getByteArray("Blocks");
        chunkLoaderAnvilConverterData.data = new NibbleArrayReader(compoundTag.getByteArray("Data"), 7);
        chunkLoaderAnvilConverterData.skyLight =
                new NibbleArrayReader(compoundTag.getByteArray("SkyLight"), 7);
        chunkLoaderAnvilConverterData.blockLight =
                new NibbleArrayReader(compoundTag.getByteArray("BlockLight"), 7);
        chunkLoaderAnvilConverterData.heightmap = compoundTag.getByteArray("HeightMap");
        chunkLoaderAnvilConverterData.terrainPopulated = compoundTag.getBoolean("TerrainPopulated");
        chunkLoaderAnvilConverterData.entities = compoundTag.getTagList("Entities", 10);
        chunkLoaderAnvilConverterData.tileEntities = compoundTag.getTagList("TileEntities", 10);
        chunkLoaderAnvilConverterData.tileTicks = compoundTag.getTagList("TileTicks", 10);

        try {
            chunkLoaderAnvilConverterData.lastUpdated = compoundTag.getLong("LastUpdate");
        } catch (ClassCastException classcastexception) {
            chunkLoaderAnvilConverterData.lastUpdated = compoundTag.getInteger("LastUpdate");
        }

        return chunkLoaderAnvilConverterData;
    }

    private static ClassCastException method_b4651e2fc58714ff8e18(
            ClassCastException classcastexception) {
        return classcastexception;
    }

    public static void convertToAnvilFormat(
            ChunkLoaderAnvilConverterData chunkLoaderAnvilConverterData,
            CompoundTag compoundTag,
            WorldChunkManager worldChunkManager) {
        String text = ExtendedBlockStorage.cJ();
        compoundTag.setInteger("xPos", chunkLoaderAnvilConverterData.x);
        String textValue = text;
        compoundTag.setInteger("zPos", chunkLoaderAnvilConverterData.z);
        compoundTag.setLong("LastUpdate", chunkLoaderAnvilConverterData.lastUpdated);
        int[] aint = new int[chunkLoaderAnvilConverterData.heightmap.length];
        int i = 0;

        while (i < chunkLoaderAnvilConverterData.heightmap.length) {
            aint[i] = chunkLoaderAnvilConverterData.heightmap[i];
            i++;
            if (textValue == null) {
                break;
            }
        }

        compoundTag.setIntArray("HeightMap", aint);
        compoundTag.setBoolean("TerrainPopulated", chunkLoaderAnvilConverterData.terrainPopulated);
        NBTTagList nbtTagList = new NBTTagList();
        int k = 0;

        while (k < 8) {
            byte b0 = 1;
            int j = 0;

            label131:
            while (j < 16 && b0 != 0) {
                int l = 0;

                while (true) {
                    if (l < 16 && b0 != 0) {
                        int i1 = 0;

                        label123:
                        {
                            while (true) {
                                if (i1 >= 16) {
                                    break label123;
                                }

                                int j1 = j << 11 | i1 << 7 | l + (k << 4);
                                byte b1 = chunkLoaderAnvilConverterData.blocks[j1];

                                try {
                                    if (b1 != 0) {
                                        break;
                                    }

                                    i1++;
                                    if (textValue == null) {
                                        break;
                                    }
                                } catch (ClassCastException classcastexception2) {
                                    throw method_b4651e2fc58714ff8e18(classcastexception2);
                                }
                            }

                            b0 = 0;
                        }

                        l++;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    j++;
                    if (textValue == null) {
                        break label131;
                    }
                    break;
                }
            }

            if (b0 == 0) {
                byte[] abyte1 = new byte[4096];
                NibbleArray nibbleArray = new NibbleArray();
                NibbleArray nibbleArrayValue = new NibbleArray();
                NibbleArray nibbleArrayValue2 = new NibbleArray();
                int k1 = 0;

                while (k1 < 16) {
                    int l1 = 0;

                    while (l1 < 16) {
                        int i2 = 0;

                        while (i2 < 16) {
                            int j2 = k1 << 11 | i2 << 7 | l1 + (k << 4);
                            byte b2 = chunkLoaderAnvilConverterData.blocks[j2];
                            abyte1[l1 << 8 | i2 << 4 | k1] = (byte) (b2 & 0xFF);
                            nibbleArray.set(
                                    k1,
                                    l1,
                                    i2,
                                    chunkLoaderAnvilConverterData.data.get(
                                            k1, l1 + (k << 4), i2));
                            nibbleArrayValue.set(
                                    k1,
                                    l1,
                                    i2,
                                    chunkLoaderAnvilConverterData.skyLight.get(
                                            k1, l1 + (k << 4), i2));
                            nibbleArrayValue2.set(
                                    k1,
                                    l1,
                                    i2,
                                    chunkLoaderAnvilConverterData.blockLight.get(
                                            k1, l1 + (k << 4), i2));
                            i2++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        l1++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    k1++;
                    if (textValue == null) {
                        break;
                    }
                }

                CompoundTag compoundTagValue = new CompoundTag();
                compoundTagValue.setByte(packedTextFragments[0], (byte) (k & 0xFF));
                compoundTagValue.setByteArray("Blocks", abyte1);
                compoundTagValue.setByteArray("Data", nibbleArray.getData());
                compoundTagValue.setByteArray("SkyLight", nibbleArrayValue.getData());
                compoundTagValue.setByteArray("BlockLight", nibbleArrayValue2.getData());
                nbtTagList.appendTag(compoundTagValue);
            }

            k++;
            if (textValue == null) {
                break;
            }
        }

        compoundTag.setTag("Sections", nbtTagList);
        byte[] abyte = new byte[256];
        int l2 = 0;

        label75:
        while (l2 < 16) {
            int k2 = 0;

            while (true) {
                if (k2 < 16) {
                    abyte[k2 << 4 | l2] =
                            (byte)
                                    (worldChunkManager.getBiomeGenerator(
                                                            new BlockPos(
                                                                    chunkLoaderAnvilConverterData.x
                                                                                    << 4
                                                                            | l2,
                                                                    0,
                                                                    chunkLoaderAnvilConverterData.z
                                                                                    << 4
                                                                            | k2),
                                                            BiomeGenBase.field_180279_ad)
                                                    .biomeID
                                            & 0xFF);
                    k2++;
                    if (textValue != null) {
                        continue;
                    }
                }

                l2++;
                if (textValue == null) {
                    break label75;
                }
                break;
            }
        }

        try {
            compoundTag.setByteArray("Biomes", abyte);
            compoundTag.setTag("Entities", chunkLoaderAnvilConverterData.entities);
            compoundTag.setTag("TileEntities", chunkLoaderAnvilConverterData.tileEntities);
            if (chunkLoaderAnvilConverterData.tileTicks != null) {
                compoundTag.setTag("TileTicks", chunkLoaderAnvilConverterData.tileTicks);
            }
        } catch (ClassCastException classcastexception1) {
            throw method_b4651e2fc58714ff8e18(classcastexception1);
        }

        try {
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                ExtendedBlockStorage.b(packedTextFragments[1]);
            }
        } catch (ClassCastException classcastexception) {
            throw method_b4651e2fc58714ff8e18(classcastexception);
        }
    }

    static {
        net.minecraft.world.chunk.storage.ChunkLoader.packedTextFragments = new String[4];
        net.minecraft.world.chunk.storage.ChunkLoader.packedTextFragments[0] = new String("Y");
        net.minecraft.world.chunk.storage.ChunkLoader.packedTextFragments[1] = new String("Qcots");
    }
}
