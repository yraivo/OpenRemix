package net.minecraft.world.storage;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class MapData extends WorldSavedData {
    public int xCenter;
    public byte dimension;

    public byte scale;
    public List playersArrayList;
    private Map playersHashMap;
    public int zCenter;
    private static String[] packedTextFragments;
    public byte[] colors = new byte[16384];
    public Map playersVisibleOnMap;

    static {
        net.minecraft.world.storage.MapData.packedTextFragments = new String[4];
        net.minecraft.world.storage.MapData.packedTextFragments[0] = new String("x");
        net.minecraft.world.storage.MapData.packedTextFragments[1] = new String("z");
    }

    public Packet getMapPacket(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        MapDataMapInfo mapDataMapInfo = (MapDataMapInfo) this.playersHashMap.get(playerEntity);
        return mapDataMapInfo == null
                ? null
                : mapDataMapInfo.getPacket(itemStack);
    }

    private void updateDecorations(
            int i, World world, String text, double d0, double d1, double d2) {
        byte b0;
        byte b1;
        byte b3;
        {
            int j = 1 << this.scale;
            float f = (float) (d0 - this.xCenter) / j;
            WorldInfo.YP();

            float f1 = (float) (d1 - this.zCenter) / j;
            b0 = (byte) (f * 2.0F + 0.5);
            b1 = (byte) (f1 * 2.0F + 0.5);

            byte b2 = 63;
            if (f >= -b2 && f1 >= -b2 && f <= b2 && f1 <= b2) {
                d2 += d2 < 0.0 ? -8.0 : 8.0;
                b3 = (byte) (d2 * 16.0 / 360.0);
                if (!(this.dimension >= 0)) {

                    long l = world.getWorldInfo().getWorldTime();

                    int k = (int) (l / 10L);
                    b3 = (byte) (k * k * 34187121 + k * 121 >> 15 & 15);


                }
            } else {

                if (Math.abs(f) >= 320.0F || Math.abs(f1) >= 320.0F) {
                    this.playersVisibleOnMap.remove(text);
                    return;
                }

                i = 6;
                b3 = 0;
                if (f <= -b2) {
                    b0 = (byte) (b2 * 2 + 2.5);
                }

                if (f1 <= -b2) {
                    b1 = (byte) (b2 * 2 + 2.5);
                }

                if (f >= b2) {
                    b0 = (byte) (b2 * 2 + 1);
                }

                if (f1 >= b2) {
                    b1 = (byte) (b2 * 2 + 1);
                }
            }
        }

        this.playersVisibleOnMap.put(text, new Vec4b((byte) i, b0, b1, b3));
    }

    public MapDataMapInfo getMapInfo(PlayerEntity playerEntity) {
        MapDataMapInfo mapDataMapInfo = (MapDataMapInfo) this.playersHashMap.get(playerEntity);
        if (mapDataMapInfo == null) {
            mapDataMapInfo = new MapDataMapInfo(this, playerEntity);
            this.playersHashMap.put(playerEntity, mapDataMapInfo);
            this.playersArrayList.add(mapDataMapInfo);
        }

        return mapDataMapInfo;
    }

    public void calculateMapCenter(double d0, double d1, int i) {
        int j = 128 * (1 << i);
        int k = MathHelper.floor((d0 + 64.0) / j);
        int l = MathHelper.floor((d1 + 64.0) / j);
        this.xCenter = k * j + j / 2 - 64;
        this.zCenter = l * j + j / 2 - 64;
    }

    public MapData(String text) {
        super(text);
        this.playersArrayList = Lists.newArrayList();
        this.playersHashMap = Maps.newHashMap();
        this.playersVisibleOnMap = Maps.newLinkedHashMap();
    }

    public void updateMapData(int i, int j) {
        WorldInfo.YP();

        super.markDirty();

        for (java.util.Iterator<?> __recoveryIterator171_10340 = (this.playersArrayList).iterator();
                (__recoveryIterator171_10340).hasNext(); ) {
            MapDataMapInfo mapDataMapInfo = (MapDataMapInfo) (__recoveryIterator171_10340).next();
            mapDataMapInfo.update(i, j);
        }
    }

    public void updateVisiblePlayers(PlayerEntity playerEntity, ItemStack itemStack) {
        WorldInfo.YP();

        if (!this.playersHashMap.containsKey(playerEntity)) {
            MapDataMapInfo mapDataMapInfo = new MapDataMapInfo(this, playerEntity);
            this.playersHashMap.put(playerEntity, mapDataMapInfo);
            this.playersArrayList.add(mapDataMapInfo);
        }

        if (!playerEntity.inventory.hasItemStack(itemStack)) {
            this.playersVisibleOnMap.remove(playerEntity.getName());
        }

        int i = 0;

        while (i < this.playersArrayList.size()) {
            {
                MapDataMapInfo mapDataMapInfoValue = (MapDataMapInfo) this.playersArrayList.get(i);
                if (!mapDataMapInfoValue.entityplayerObj.isDead
                        && (mapDataMapInfoValue.entityplayerObj.inventory
                                        .hasItemStack(itemStack)
                                || itemStack.isOnItemFrame())) {
                    if (!(itemStack.isOnItemFrame()
                            || mapDataMapInfoValue.entityplayerObj.dimension != this.dimension)) {

                        this.updateDecorations(
                                0,
                                mapDataMapInfoValue.entityplayerObj.world,
                                mapDataMapInfoValue.entityplayerObj.getName(),
                                mapDataMapInfoValue.entityplayerObj.posX,
                                mapDataMapInfoValue.entityplayerObj.posZ,
                                mapDataMapInfoValue.entityplayerObj.rotationYaw);


                    }
                } else {

                    this.playersHashMap.remove(mapDataMapInfoValue.entityplayerObj);
                    this.playersArrayList.remove(mapDataMapInfoValue);
                }
            }

            i++;
        }

        if (itemStack.isOnItemFrame()) {
            EntityItemFrame itemFrame = itemStack.getItemFrame();
            BlockPos blockPos = itemFrame.getHangingPosition();
            this.updateDecorations(
                    1,
                    playerEntity.world,
                    "frame-" + itemFrame.getEntityId(),
                    blockPos.getX(),
                    blockPos.getZ(),
                    itemFrame.facingDirection.getHorizontalIndex() * 90);
        }

        if (itemStack.hasTagCompound() && itemStack.getTag().hasKey("Decorations", 9)) {
            NBTTagList nbtTagList = itemStack.getTag().getTagList("Decorations", 10);
            int j = 0;

            while (j < nbtTagList.tagCount()) {
                CompoundTag compoundTag = nbtTagList.getCompoundTagAt(j);
                if (!this.playersVisibleOnMap.containsKey(compoundTag.getString("id"))) {
                    this.updateDecorations(
                            compoundTag.getByte("type"),
                            playerEntity.world,
                            compoundTag.getString("id"),
                            compoundTag.getDouble(packedTextFragments[0]),
                            compoundTag.getDouble(packedTextFragments[1]),
                            compoundTag.getDouble("rot"));
                }

                j++;
            }
        }
    }

    public void readFromNBT(CompoundTag compoundTag) {
        this.dimension = compoundTag.getByte("dimension");
        this.xCenter = compoundTag.getInteger("xCenter");
        this.zCenter = compoundTag.getInteger("zCenter");
        this.scale = compoundTag.getByte("scale");
        WorldInfo.YP();

        this.scale = (byte) MathHelper.clamp(this.scale, 0, 4);

        short short1 = compoundTag.getShort("width");
        short short2 = compoundTag.getShort("height");
        if (short1 == 128 && short2 == 128) {
            this.colors = compoundTag.getByteArray("colors");

            return;
        }

        byte[] abyte = compoundTag.getByteArray("colors");
        this.colors = new byte[16384];
        int i = (128 - short1) / 2;
        int j = (128 - short2) / 2;
        int k = 0;

        while (k < short2) {
            int l = k + j;
            if (l >= 0 || l < 128) {
                int i1 = 0;

                while (i1 < short1) {
                    int j1 = i1 + i;
                    if (j1 >= 0 || j1 < 128) {
                        this.colors[j1 + l * 128] = abyte[i1 + k * short1];
                    }

                    i1++;
                }
            }

            k++;
        }
    }

    public void writeToNBT(CompoundTag compoundTag) {
        compoundTag.setByte("dimension", this.dimension);
        compoundTag.setInteger("xCenter", this.xCenter);
        compoundTag.setInteger("zCenter", this.zCenter);
        compoundTag.setByte("scale", this.scale);
        compoundTag.setShort("width", (short) 128);
        compoundTag.setShort("height", (short) 128);
        compoundTag.setByteArray("colors", this.colors);
    }
}
