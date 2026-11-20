package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponentSerializer;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import java.util.List;

public class ItemEditableBook extends Item {

    public static boolean validBookTagContents(CompoundTag compoundTag) {
        if (!ItemWritableBook.isNBTValid(compoundTag)) {
            return false;
        }

        if (!compoundTag.hasKey("title", 8)) {
            return false;
        }

        String text = compoundTag.getString("title");
        return text != null && text.length() <= 32 ? compoundTag.hasKey("author", 8) : false;
    }


    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (!world.isRemote) {
            this.resolveContents(itemStack, playerEntity);
        }

        playerEntity.displayGUIBook(itemStack);
        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }

    public ItemEditableBook() {
        this.setMaxStackSize(1);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            CompoundTag tag = itemStack.getTag();
            String text = tag.getString("title");
            if (!StringUtils.isNullOrEmpty(text)) {
                return text;
            }
        }

        return super.getItemStackDisplayName(itemStack);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }

    @Override
    public void addInformation(
            ItemStack itemStack, PlayerEntity playerEntity, List list, boolean flag) {
        if (itemStack.hasTagCompound()) {
            CompoundTag tag = itemStack.getTag();
            String text = tag.getString("author");
            if (!StringUtils.isNullOrEmpty(text)) {
                list.add(
                        EnumChatFormatting.GRAY
                                + StatCollector.translateToLocalFormatted(
                                        "book.byAuthor", new Object[] {text}));
            }

            list.add(
                    EnumChatFormatting.GRAY
                            + StatCollector.translateToLocal(
                                    "book.generation." + tag.getInteger("generation")));
        }
    }

    private void resolveContents(ItemStack itemStack, PlayerEntity playerEntity) {
        String text = ItemStack.getLegacyItemStackFlowMarker();

        try {
            if (itemStack == null || itemStack.getTag() == null) {
                return;
            }
        } catch (Exception exception3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception3));
        }

        CompoundTag tag = itemStack.getTag();

        try {
            if (tag.getBoolean("resolved")) {
                return;
            }

            tag.setBoolean("resolved", true);
            if (!validBookTagContents(tag)) {
                return;
            }
        } catch (Exception exception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception2));
        }

        NBTTagList nbtTagList = tag.getTagList("pages", 8);
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            String textValue = nbtTagList.getStringTagAt(i);

            Object object;
            try {
                ChatComponent chatComponent = IChatComponentSerializer.jsonToComponent(textValue);
                object =
                        ChatComponentProcessor.processComponent(
                                playerEntity, chatComponent, playerEntity);
            } catch (Exception exception) {
                object = new ChatComponentText(textValue);
            }

            nbtTagList.set(
                    i,
                    new NBTTagString(
                            IChatComponentSerializer.componentToJson((ChatComponent) object)));
            i++;
            if (text != null) {
                break;
            }
        }

        try {
            tag.setTag("pages", nbtTagList);
            if (!(playerEntity instanceof EntityPlayerMP)
                    || playerEntity.getCurrentEquippedItem() != itemStack) {
                return;
            }
        } catch (Exception exception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
        }

        Slot slot =
                playerEntity.openContainer.getSlotFromInventory(
                        playerEntity.inventory, playerEntity.inventory.currentItem);
        ((EntityPlayerMP) playerEntity)
                .playerNetServerHandler.sendPacket(
                        new S2FPacketSetSlot(0, slot.slotNumber, itemStack));
    }

    public static int getGeneration(ItemStack itemStack) {
        return itemStack.getTag().getInteger("generation");
    }
}
