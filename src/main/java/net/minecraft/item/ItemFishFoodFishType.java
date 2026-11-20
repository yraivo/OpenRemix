package net.minecraft.item;

import com.google.common.collect.Maps;

import java.util.Map;

public enum ItemFishFoodFishType {
    COD("COD", 0, 0, "cod", 2, 0.1F, 5, 0.6F),
    SALMON("SALMON", 1, 1, "salmon", 2, 0.1F, 6, 0.8F),
    CLOWNFISH("CLOWNFISH", 2, 2, "clownfish", 1, 0.1F),
    PUFFERFISH("PUFFERFISH", 3, 3, "pufferfish", 1, 0.1F);

    private static ItemFishFoodFishType[] aaJ;

    private float uncookedSaturationModifier;
    private String unlocalizedNamePart;
    private int meta;
    private int cookedHealAmount;
    private float cookedSaturationModifier;
    private boolean cookable = false;
    private static Map itemDamageToFishTypeMap;
    private int uncookedHealAmount;

    public float getCookedSaturationModifier() {
        return this.cookedSaturationModifier;
    }

    public int getUncookedHealAmount() {
        return this.uncookedHealAmount;
    }

    public boolean getCookable() {
        return this.cookable;
    }

    // region String decoding support

    // endregion

    public int getCookedHealAmount() {
        return this.cookedHealAmount;
    }

    public int getMetadata() {
        return this.meta;
    }

    public String getUnlocalizedNamePart() {
        return this.unlocalizedNamePart;
    }

    static {
        aaJ =
                new ItemFishFoodFishType[] {
                    net.minecraft.item.ItemFishFoodFishType.COD,
                    net.minecraft.item.ItemFishFoodFishType.SALMON,
                    net.minecraft.item.ItemFishFoodFishType.CLOWNFISH,
                    net.minecraft.item.ItemFishFoodFishType.PUFFERFISH
                };
        net.minecraft.item.ItemFishFoodFishType.itemDamageToFishTypeMap = Maps.newHashMap();
        for (ItemFishFoodFishType class_474900fe8528d8c63f25 : values()) {
            net.minecraft.item.ItemFishFoodFishType.itemDamageToFishTypeMap.put(
                    class_474900fe8528d8c63f25.getMetadata(),
                    class_474900fe8528d8c63f25);
        }
    }

    // region String decoding support

    // endregion

    public static ItemFishFoodFishType getFishTypeForItemStack(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemFishFood
                ? byMetadata(itemStack.getItemDamage())
                : net.minecraft.item.ItemFishFoodFishType.COD;
    }

    public float getUncookedSaturationModifier() {
        return this.uncookedSaturationModifier;
    }

    ItemFishFoodFishType(
            String text, int i1, int j, String textValue, int k, float f, int l, float f1) {
        this.meta = j;
        this.unlocalizedNamePart = textValue;
        this.uncookedHealAmount = k;
        this.uncookedSaturationModifier = f;
        this.cookedHealAmount = l;
        this.cookedSaturationModifier = f1;
        this.cookable = true;
    }

    ItemFishFoodFishType(String text, int l, int j, String textValue, int k, float f) {
        this.meta = j;
        this.unlocalizedNamePart = textValue;
        this.uncookedHealAmount = k;
        this.uncookedSaturationModifier = f;
        this.cookedHealAmount = 0;
        this.cookedSaturationModifier = 0.0F;
        this.cookable = false;
    }

    // region String decoding support

    // endregion

    public static ItemFishFoodFishType byMetadata(int i) {
        ItemFishFoodFishType itemFishFoodFishType =
                (ItemFishFoodFishType) net.minecraft.item.ItemFishFoodFishType.itemDamageToFishTypeMap.get(i);
        return itemFishFoodFishType == null ? net.minecraft.item.ItemFishFoodFishType.COD : itemFishFoodFishType;
    }
}
