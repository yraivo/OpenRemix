package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemWritableBook extends Item {

    public ItemWritableBook() {
        this.setMaxStackSize(1);
    }


    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        playerEntity.displayGUIBook(itemStack);
        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }

    public static boolean isNBTValid(CompoundTag compoundTag) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (compoundTag == null) {
            return false;
        }

        if (!compoundTag.hasKey("pages", 9)) {
            return false;
        }

        NBTTagList nbtTagList = compoundTag.getTagList("pages", 8);

        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            String textValue = nbtTagList.getStringTagAt(i);
            if (textValue == null) {
                return false;
            }

            if (textValue.length() > 32767) {
                return false;
            }
            break;
        }

        return true;
    }
}
