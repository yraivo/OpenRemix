package net.minecraft.enchantment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemTool;
import net.minecraft.item.SwordItem;

public enum EnumEnchantmentType {
    ALL("ALL", 0),
    ARMOR("ARMOR", 1),
    ARMOR_FEET("ARMOR_FEET", 2),
    ARMOR_LEGS("ARMOR_LEGS", 3),
    ARMOR_TORSO("ARMOR_TORSO", 4),
    ARMOR_HEAD("ARMOR_HEAD", 5),
    WEAPON("WEAPON", 6),
    DIGGER("DIGGER", 7),
    FISHING_ROD("FISHING_ROD", 8),
    BREAKABLE("BREAKABLE", 9),
    BOW("BOW", 10);

    private static EnumEnchantmentType[] aeK;

    // region String decoding support

    // endregion

    public boolean canEnchantItem(Item item) {
        if (this == net.minecraft.enchantment.EnumEnchantmentType.ALL) {
            return true;
        }

        if (this == net.minecraft.enchantment.EnumEnchantmentType.BREAKABLE && item.isDamageable()) {
            return true;
        }

        if (item instanceof ItemArmor) {
            if (this == net.minecraft.enchantment.EnumEnchantmentType.ARMOR) {
                return true;
            }

            ItemArmor itemArmor = (ItemArmor) item;
            return itemArmor.armorType == 0
                    ? this == net.minecraft.enchantment.EnumEnchantmentType.ARMOR_HEAD
                    : (itemArmor.armorType == 2
                            ? this == net.minecraft.enchantment.EnumEnchantmentType.ARMOR_LEGS
                            : (itemArmor.armorType == 1
                                    ? this == net.minecraft.enchantment.EnumEnchantmentType.ARMOR_TORSO
                                    : (itemArmor.armorType == 3
                                            ? this == net.minecraft.enchantment.EnumEnchantmentType.ARMOR_FEET
                                            : false)));
        } else {
            return item instanceof SwordItem
                    ? this == net.minecraft.enchantment.EnumEnchantmentType.WEAPON
                    : (item instanceof ItemTool
                            ? this == net.minecraft.enchantment.EnumEnchantmentType.DIGGER
                            : (item instanceof ItemBow
                                    ? this == net.minecraft.enchantment.EnumEnchantmentType.BOW
                                    : (item instanceof ItemFishingRod
                                            ? this == net.minecraft.enchantment.EnumEnchantmentType.FISHING_ROD
                                            : false)));
        }
    }

    // region String decoding support

    // endregion

    EnumEnchantmentType(String text, int j) {}

    static {
        aeK =
                new EnumEnchantmentType[] {
                    net.minecraft.enchantment.EnumEnchantmentType.ALL,
                    net.minecraft.enchantment.EnumEnchantmentType.ARMOR,
                    net.minecraft.enchantment.EnumEnchantmentType.ARMOR_FEET,
                    net.minecraft.enchantment.EnumEnchantmentType.ARMOR_LEGS,
                    net.minecraft.enchantment.EnumEnchantmentType.ARMOR_TORSO,
                    net.minecraft.enchantment.EnumEnchantmentType.ARMOR_HEAD,
                    net.minecraft.enchantment.EnumEnchantmentType.WEAPON,
                    net.minecraft.enchantment.EnumEnchantmentType.DIGGER,
                    net.minecraft.enchantment.EnumEnchantmentType.FISHING_ROD,
                    net.minecraft.enchantment.EnumEnchantmentType.BREAKABLE,
                    net.minecraft.enchantment.EnumEnchantmentType.BOW
                };
    }
}
