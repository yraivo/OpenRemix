package net.minecraft.item;

import net.minecraft.init.Items;

public enum ItemArmorArmorMaterial {
    LEATHER("LEATHER", 0, "leather", 5, new int[] {1, 3, 2, 1}, 15),
    CHAIN("CHAIN", 1, "chainmail", 15, new int[] {2, 5, 4, 1}, 12),
    IRON("IRON", 2, "iron", 15, new int[] {2, 6, 5, 2}, 9),
    GOLD("GOLD", 3, "gold", 7, new int[] {2, 5, 3, 1}, 25),
    DIAMOND("DIAMOND", 4, "diamond", 33, new int[] {3, 8, 6, 3}, 10);
    private static ItemArmorArmorMaterial[] aaI;

    private int enchantability;
    private String name;
    private int[] damageReductionAmountArray;
    private int maxDamageFactor;

    public String getName() {
        return this.name;
    }

    public int getDamageReductionAmount(int i) {
        return this.damageReductionAmountArray[i];
    }

    public int getDurability(int i) {
        return ItemArmor.access$000()[i] * this.maxDamageFactor;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public Item getRepairItem() {
        return this == net.minecraft.item.ItemArmorArmorMaterial.LEATHER
                ? Items.leather
                : (this == net.minecraft.item.ItemArmorArmorMaterial.CHAIN
                        ? Items.iron_ingot
                        : (this == net.minecraft.item.ItemArmorArmorMaterial.GOLD
                                ? Items.gold_ingot
                                : (this == net.minecraft.item.ItemArmorArmorMaterial.IRON
                                        ? Items.iron_ingot
                                        : (this == net.minecraft.item.ItemArmorArmorMaterial.DIAMOND
                                                ? Items.diamond
                                                : null))));
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    static {
        aaI =
                new ItemArmorArmorMaterial[] {
                    net.minecraft.item.ItemArmorArmorMaterial.LEATHER,
                    net.minecraft.item.ItemArmorArmorMaterial.CHAIN,
                    net.minecraft.item.ItemArmorArmorMaterial.IRON,
                    net.minecraft.item.ItemArmorArmorMaterial.GOLD,
                    net.minecraft.item.ItemArmorArmorMaterial.DIAMOND
                };
    }

    ItemArmorArmorMaterial(String text, int l, String textValue, int j, int[] aint, int k) {
        this.name = textValue;
        this.maxDamageFactor = j;
        this.damageReductionAmountArray = aint;
        this.enchantability = k;
    }
}
