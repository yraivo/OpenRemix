package net.minecraft.world.storage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;

public class MapDataMapInfo {
    private int minY;
    public PlayerEntity entityplayerObj;
    private int maxY;
    MapData field_176107_c;
    private int maxX;
    private int Ao;
    public int sR;
    private int minX;
    private boolean field_176105_d;

    public MapDataMapInfo(MapData bNNValue, PlayerEntity bBbValue) {
        this.field_176107_c = bNNValue;
        this.field_176105_d = true;
        this.minX = 0;
        this.minY = 0;
        this.maxX = 127;
        this.maxY = 127;
        this.entityplayerObj = bBbValue;
    }

    public Packet getPacket(ItemStack itemStack) {
        if (this.field_176105_d) {
            this.field_176105_d = false;
            return new S34PacketMaps(
                    itemStack.getItemDamage(),
                    this.field_176107_c.scale,
                    this.field_176107_c.playersVisibleOnMap.values(),
                    this.field_176107_c.colors,
                    this.minX,
                    this.minY,
                    this.maxX + 1 - this.minX,
                    this.maxY + 1 - this.minY);
        } else {
            return this.Ao++ % 5 == 0
                    ? new S34PacketMaps(
                            itemStack.getItemDamage(),
                            this.field_176107_c.scale,
                            this.field_176107_c.playersVisibleOnMap.values(),
                            this.field_176107_c.colors,
                            0,
                            0,
                            0,
                            0)
                    : null;
        }
    }

    public void update(int i, int j) {
        WorldInfo.YP();

        if (this.field_176105_d) {
            this.minX = Math.min(this.minX, i);
            this.minY = Math.min(this.minY, j);
            this.maxX = Math.max(this.maxX, i);
            this.maxY = Math.max(this.maxY, j);

            return;
        }

        this.field_176105_d = true;
        this.minX = i;
        this.minY = j;
        this.maxX = i;
        this.maxY = j;
    }
}
