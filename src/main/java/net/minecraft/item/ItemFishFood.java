package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemFishFood extends ItemFood {
    private static String[] packedTextFragments;

    private boolean cooked;

    public String getPotionEffect(ItemStack itemStack) {
        return ItemFishFoodFishType.getFishTypeForItemStack(itemStack)
                        == ItemFishFoodFishType.PUFFERFISH
                ? PotionHelper.pufferfishEffect
                : null;
    }

    public ItemFishFood(boolean cookedValue) {
        super(0, 0.0F, false);
        this.cooked = cookedValue;
    }

    public int getHealAmount(ItemStack itemStack) {
        ItemFishFoodFishType fishTypeForItemStack =
                ItemFishFoodFishType.getFishTypeForItemStack(itemStack);
        return this.cooked && fishTypeForItemStack.getCookable()
                ? fishTypeForItemStack.getCookedHealAmount()
                : fishTypeForItemStack.getUncookedHealAmount();
    }

    static {
        net.minecraft.item.ItemFishFood.packedTextFragments = new String[3];
        net.minecraft.item.ItemFishFood.packedTextFragments[0] = new String(".");
        net.minecraft.item.ItemFishFood.packedTextFragments[1] = new String(".");
    }

    protected void onFoodEaten(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        ItemFishFoodFishType fishTypeForItemStack =
                ItemFishFoodFishType.getFishTypeForItemStack(itemStack);
        if (fishTypeForItemStack == ItemFishFoodFishType.PUFFERFISH) {
            playerEntity.addPotionEffect(new PotionEffect(Potion.POISON.id, 1200, 3));
            playerEntity.addPotionEffect(new PotionEffect(Potion.HUNGER.id, 300, 2));
            playerEntity.addPotionEffect(new PotionEffect(Potion.CONFUSION.id, 300, 1));
        }

        super.onFoodEaten(itemStack, world, playerEntity);
    }

    public float getSaturationModifier(ItemStack itemStack) {
        ItemFishFoodFishType fishTypeForItemStack =
                ItemFishFoodFishType.getFishTypeForItemStack(itemStack);
        return this.cooked && fishTypeForItemStack.getCookable()
                ? fishTypeForItemStack.getCookedSaturationModifier()
                : fishTypeForItemStack.getUncookedSaturationModifier();
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        ItemFishFoodFishType[] itemFishFoodFishTypeArray = ItemFishFoodFishType.values();
        String text = ItemStack.getLegacyItemStackFlowMarker();
        int length = itemFishFoodFishTypeArray.length;
        int j = 0;
        String textValue = text;

        while (j < length) {
            ItemFishFoodFishType itemFishFoodFishType = itemFishFoodFishTypeArray[j];
            if (!this.cooked || itemFishFoodFishType.getCookable()) {
                list.add(
                        new ItemStack(this, 1, itemFishFoodFishType.getMetadata()));
            }

            j++;
            if (textValue != null) {
                break;
            }
        }
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        ItemFishFoodFishType fishTypeForItemStack =
                ItemFishFoodFishType.getFishTypeForItemStack(itemStack);
        return this.getUnlocalizedName()
                + packedTextFragments[0]
                + fishTypeForItemStack.getUnlocalizedNamePart()
                + packedTextFragments[1]
                + (this.cooked && fishTypeForItemStack.getCookable() ? "cooked" : "raw");
    }
}
