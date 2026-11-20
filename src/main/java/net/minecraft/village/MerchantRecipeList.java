package net.minecraft.village;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.util.ArrayList;

public class MerchantRecipeList extends ArrayList {

    public void readRecipiesFromTags(CompoundTag compoundTag) {
        NBTTagList nbtTagList = compoundTag.getTagList("Recipes", 10);
        boolean flag1 = Village.dS();
        int i = 0;
        boolean flag = flag1;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            this.add(new MerchantRecipe(compoundTagValue));
            i++;
            if (flag) {
                break;
            }
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public static MerchantRecipeList readFromBuf(PacketBuffer packetBuffer) throws IOException {
        MerchantRecipeList merchantRecipeList = new MerchantRecipeList();
        boolean flag2 = Village.dS();
        int i = packetBuffer.readByte() & 255;
        int j = 0;
        boolean flag = flag2;

        while (j < i) {
            ItemStack itemStack = packetBuffer.readItemStackFromBuffer();
            ItemStack itemStackValue = packetBuffer.readItemStackFromBuffer();
            ItemStack itemStackValue2 = null;
            if (packetBuffer.readBoolean()) {
                itemStackValue2 = packetBuffer.readItemStackFromBuffer();
            }

            boolean flag1 = packetBuffer.readBoolean();
            int k = packetBuffer.readInt();
            int l = packetBuffer.readInt();
            MerchantRecipe merchantRecipe =
                    new MerchantRecipe(itemStack, itemStackValue2, itemStackValue, k, l);

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (flag1) {
                    merchantRecipe.compensateToolUses();
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
            }

            merchantRecipeList.add(merchantRecipe);
            j++;
            if (flag) {
                break;
            }
        }

        return merchantRecipeList;
    }

    public MerchantRecipe canRecipeBeUsed(ItemStack itemStack, ItemStack itemStackValue, int i) {
        boolean flag = Village.dS();
        if (i > 0 && i < this.size()) {
            MerchantRecipe merchantRecipe = (MerchantRecipe) this.get(i);
            return !ItemStack.areItemsEqual(itemStack, merchantRecipe.getItemToBuy())
                            || (itemStackValue != null || merchantRecipe.hasSecondItemToBuy())
                                    && (!merchantRecipe.hasSecondItemToBuy()
                                            || !ItemStack.areItemsEqual(
                                                    itemStackValue,
                                                    merchantRecipe.getSecondItemToBuy()))
                            || itemStack.stackSize < merchantRecipe.getItemToBuy().stackSize
                            || merchantRecipe.hasSecondItemToBuy()
                                    && itemStackValue.stackSize
                                            < merchantRecipe.getSecondItemToBuy().stackSize
                    ? null
                    : merchantRecipe;
        }

        for (int j = 0; j < this.size(); j++) {
            MerchantRecipe merchantRecipeValue = (MerchantRecipe) this.get(j);
            if (ItemStack.areItemsEqual(itemStack, merchantRecipeValue.getItemToBuy())
                    && itemStack.stackSize >= merchantRecipeValue.getItemToBuy().stackSize
                    && (!merchantRecipeValue.hasSecondItemToBuy() && itemStackValue == null
                            || merchantRecipeValue.hasSecondItemToBuy()
                                    && ItemStack.areItemsEqual(
                                            itemStackValue,
                                            merchantRecipeValue.getSecondItemToBuy())
                                    && itemStackValue.stackSize
                                            >= merchantRecipeValue.getSecondItemToBuy()
                                                    .stackSize)) {
                return merchantRecipeValue;
            }
            break;
        }

        return null;
    }


    public void writeToBuf(PacketBuffer packetBuffer) {
        boolean flag1 = Village.dS();
        packetBuffer.writeByte((byte) (this.size() & 0xFF));
        boolean flag = flag1;
        int i = 0;

        while (i < this.size()) {
            MerchantRecipe merchantRecipe = (MerchantRecipe) this.get(i);
            packetBuffer.writeItemStackToBuffer(merchantRecipe.getItemToBuy());
            packetBuffer.writeItemStackToBuffer(merchantRecipe.getItemToSell());
            ItemStack itemStack = merchantRecipe.getSecondItemToBuy();
            packetBuffer.writeBoolean(itemStack != null);
            if (itemStack != null) {
                packetBuffer.writeItemStackToBuffer(itemStack);
            }

            packetBuffer.writeBoolean(merchantRecipe.isRecipeDisabled());
            packetBuffer.writeInt(merchantRecipe.getToolUses());
            packetBuffer.writeInt(merchantRecipe.getMaxTradeUses());
            i++;
            if (flag) {
                break;
            }
        }
    }

    public MerchantRecipeList() {}

    public CompoundTag getRecipiesAsTags() {
        boolean flag1 = Village.dI();
        CompoundTag compoundTag = new CompoundTag();
        NBTTagList nbtTagList = new NBTTagList();
        int i = 0;
        boolean flag = flag1;

        while (i < this.size()) {
            MerchantRecipe merchantRecipe = (MerchantRecipe) this.get(i);
            nbtTagList.appendTag(merchantRecipe.writeToTags());
            i++;
            if (!flag) {
                break;
            }
        }

        compoundTag.setTag("Recipes", nbtTagList);
        return compoundTag;
    }

    public MerchantRecipeList(CompoundTag compoundTag) {
        this.readRecipiesFromTags(compoundTag);
    }
}
