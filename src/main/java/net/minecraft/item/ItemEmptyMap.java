package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class ItemEmptyMap extends ItemMapBase {


    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        ItemStack itemStackValue = new ItemStack(Items.filled_map, 1, world.getUniqueDataId("map"));
        String text = "map_" + itemStackValue.getItemDamage();
        MapData mapData = new MapData(text);
        world.setItemData(text, mapData);
        mapData.scale = 0;
        mapData.calculateMapCenter(playerEntity.posX, playerEntity.posZ, mapData.scale);
        mapData.dimension = (byte) world.provider.getDimensionId();
        mapData.markDirty();
        itemStack.stackSize--;
        if (itemStack.stackSize <= 0) {
            return itemStackValue;
        }

        if (!playerEntity.inventory.addItemStackToInventory(itemStackValue.copy())) {
            playerEntity.dropPlayerItemWithRandomChoice(itemStackValue, false);
        }

        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }

    protected ItemEmptyMap() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}
