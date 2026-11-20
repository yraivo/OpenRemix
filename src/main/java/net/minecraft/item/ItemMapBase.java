package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ItemMapBase extends Item {
    public boolean isMap() {
        return true;
    }

    public Packet createMapDataPacket(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        return null;
    }
}
