package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneEnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDataMapInfo;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;

import java.util.List;

public class ItemMap extends ItemMapBase {

    private static String[] packedTextFragments;

    protected ItemMap() {
        this.setHasSubtypes(true);
    }

    public void onCreated(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (itemStack.hasTagCompound() && itemStack.getTag().getBoolean("map_is_scaling")) {
            MapData mapData = Items.filled_map.getMapData(itemStack, world);
            itemStack.setItemDamage(world.getUniqueDataId("map"));
            MapData mapDataValue = new MapData("map_" + itemStack.getItemDamage());
            mapDataValue.scale = (byte) (mapData.scale + 1);
            if (mapDataValue.scale > 4) {
                mapDataValue.scale = 4;
            }

            mapDataValue.calculateMapCenter(mapData.xCenter, mapData.zCenter, mapDataValue.scale);
            mapDataValue.dimension = mapData.dimension;
            mapDataValue.markDirty();
            world.setItemData("map_" + itemStack.getItemDamage(), mapDataValue);
        }
    }

    static {
        net.minecraft.item.ItemMap.packedTextFragments = new String[4];
        net.minecraft.item.ItemMap.packedTextFragments[0] = new String("/");
        net.minecraft.item.ItemMap.packedTextFragments[1] = new String(")");
    }

    public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean flag) {
        if (!world.isRemote) {
            MapData mapData = this.getMapData(itemStack, world);
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                mapData.updateVisiblePlayers(playerEntity, itemStack);
            }

            if (flag) {
                this.updateMapData(world, entity, mapData);
            }
        }
    }

    public Packet createMapDataPacket(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        return this.getMapData(itemStack, world).getMapPacket(itemStack, world, playerEntity);
    }

    public void addInformation(
            ItemStack itemStack, PlayerEntity playerEntity, List list, boolean flag) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        MapData mapData = this.getMapData(itemStack, playerEntity.world);
        String textValue = text;
        if (flag) {
            if (mapData == null) {
                list.add("Unknown map");
                if (textValue == null) {
                    return;
                }
            }

            list.add("Scaling at 1:" + (1 << mapData.scale));
            list.add(
                    "(Level "
                            + mapData.scale
                            + packedTextFragments[0]
                            + 4
                            + packedTextFragments[1]);
        }
    }

    public MapData getMapData(ItemStack itemStack, World world) {
        String text = "map_" + itemStack.getItemDamage();
        MapData mapData = (MapData) world.loadItemData(MapData.class, text);
        if (mapData == null && !world.isRemote) {
            itemStack.setItemDamage(world.getUniqueDataId("map"));
            text = "map_" + itemStack.getItemDamage();
            mapData = new MapData(text);
            mapData.scale = 3;
            mapData.calculateMapCenter(
                    world.getWorldInfo().getSpawnX(),
                    world.getWorldInfo().getSpawnZ(),
                    mapData.scale);
            mapData.dimension = (byte) world.provider.getDimensionId();
            mapData.markDirty();
            world.setItemData(text, mapData);
        }

        return mapData;
    }

    public static MapData loadMapData(int i, World world) {
        String text = "map_" + i;
        MapData mapData = (MapData) world.loadItemData(MapData.class, text);
        if (mapData == null) {
            mapData = new MapData(text);
            world.setItemData(text, mapData);
        }

        return mapData;
    }

    public void updateMapData(World world, Entity entity, MapData mapData) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (world.provider.getDimensionId() == mapData.dimension
                && entity instanceof PlayerEntity) {
            int i = 1 << mapData.scale;
            int j = mapData.xCenter;
            int k = mapData.zCenter;
            int l = MathHelper.floor(entity.posX - j) / i + 64;
            int i1 = MathHelper.floor(entity.posZ - k) / i + 64;
            int j1 = 128 / i;
            if (world.provider.getHasNoSky()) {
                j1 /= 2;
            }

            MapDataMapInfo mapDataMapInfo = mapData.getMapInfo((PlayerEntity) entity);
            mapDataMapInfo.sR++;
            byte b0 = 0;
            int k1 = l - j1 + 1;

            while (k1 < l + j1) {
                if ((k1 & 15) == (mapDataMapInfo.sR & 15) || b0 != 0) {
                    b0 = 0;
                    double d0 = 0.0;
                    int l1 = i1 - j1 - 1;

                    while (l1 < i1 + j1) {
                        if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
                            int i2 = k1 - l;
                            int j2 = l1 - i1;
                            int k2 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2) ? 1 : 0;
                            int l2 = (j / i + k1 - 64) * i;
                            int i3 = (k / i + l1 - 64) * i;
                            HashMultiset hashmultiset = HashMultiset.create();
                            Chunk chunkFromBlockCoords =
                                    world.getChunkFromBlockCoords(new BlockPos(l2, 0, i3));
                            if (!chunkFromBlockCoords.isEmpty()) {
                                int l3;
                                double d1;
                                label156:
                                {
                                    int j3 = l2 & 15;
                                    int k3 = i3 & 15;
                                    l3 = 0;
                                    d1 = 0.0;
                                    if (world.provider.getHasNoSky()) {
                                        label138:
                                        {
                                            int i4 = l2 + i3 * 231871;
                                            i4 = i4 * i4 * 31287121 + i4 * 11;
                                            if ((i4 >> 20 & 1) == 0) {
                                                hashmultiset.add(
                                                        Blocks.dirt.getMapColor(
                                                                Blocks.dirt
                                                                        .getDefaultState()
                                                                        .withProperty(
                                                                                BlockDirt.VARIANT,
                                                                                BlockDirtDirtType
                                                                                        .DIRT)),
                                                        10);
                                                if (text == null) {
                                                    break label138;
                                                }
                                            }

                                            hashmultiset.add(
                                                    Blocks.stone.getMapColor(
                                                            Blocks.stone
                                                                    .getDefaultState()
                                                                    .withProperty(
                                                                            BlockStone.VARIANT_PROP,
                                                                            BlockStoneEnumType
                                                                                    .STONE)),
                                                    100);
                                        }

                                        d1 = 100.0;
                                        if (text == null) {
                                            break label156;
                                        }
                                    }

                                    int i5 = 0;

                                    while (i5 < i) {
                                        int j4 = 0;

                                        while (j4 < i) {
                                            int k4 =
                                                    chunkFromBlockCoords.getHeight(i5 + j3, j4 + k3)
                                                            + 1;
                                            BlockState blockState = Blocks.air.getDefaultState();
                                            if (k4 > 1) {
                                                do {
                                                    blockState =
                                                            chunkFromBlockCoords.getBlockState(
                                                                    new BlockPos(
                                                                            i5 + j3, --k4,
                                                                            j4 + k3));
                                                } while (blockState
                                                                        .getBlock()
                                                                        .getMapColor(blockState)
                                                                == MapColor.stoneColor
                                                        && k4 > 0);

                                                if (k4 > 0
                                                        && blockState
                                                                .getBlock()
                                                                .getMaterial()
                                                                .isLiquid()) {
                                                    int l4 = k4 - 1;

                                                    Block block;
                                                    do {
                                                        block =
                                                                chunkFromBlockCoords.getBlock(
                                                                        i5 + j3, l4--, j4 + k3);
                                                        l3++;
                                                    } while (l4 > 0
                                                            && block.getMaterial().isLiquid());
                                                }
                                            }

                                            d1 += (double) k4 / (i * i);
                                            hashmultiset.add(
                                                    blockState.getBlock().getMapColor(blockState));
                                            j4++;
                                            if (text != null) {
                                                break;
                                            }
                                        }

                                        i5++;
                                        if (text != null) {
                                            break;
                                        }
                                    }
                                }

                                l3 /= i * i;
                                double d2 = (d1 - d0) * 4.0 / (i + 4) + ((k1 + l1 & 1) - 0.5) * 0.4;
                                byte b2 = 1;
                                if (d2 > 0.6) {
                                    b2 = 2;
                                }

                                if (d2 < -0.6) {
                                    b2 = 0;
                                }

                                MapColor first =
                                        (MapColor)
                                                Iterables.getFirst(
                                                        Multisets.copyHighestCountFirst(
                                                                hashmultiset),
                                                        MapColor.stoneColor);
                                if (first == MapColor.waterColor) {
                                    d2 = l3 * 0.1 + (k1 + l1 & 1) * 0.2;
                                    b2 = 1;
                                    if (d2 < 0.5) {
                                        b2 = 2;
                                    }

                                    if (d2 > 0.9) {
                                        b2 = 0;
                                    }
                                }

                                d0 = d1;
                                if (l1 >= 0
                                        && i2 * i2 + j2 * j2 < j1 * j1
                                        && (k2 == 0 || (k1 + l1 & 1) != 0)) {
                                    byte b3 = mapData.colors[k1 + l1 * 128];
                                    byte b1 = (byte) (first.colorIndex * 4 + b2);
                                    if (b3 != b1) {
                                        mapData.colors[k1 + l1 * 128] = b1;
                                        mapData.updateMapData(k1, l1);
                                        b0 = 1;
                                    }
                                }
                            }
                        }

                        l1++;
                        if (text != null) {
                            break;
                        }
                    }
                }

                k1++;
                if (text != null) {
                    break;
                }
            }
        }
    }
}
