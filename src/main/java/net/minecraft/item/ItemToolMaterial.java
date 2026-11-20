package net.minecraft.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public enum ItemToolMaterial {
    WOOD("WOOD", 0, 0, 59, 2.0F, 0.0F, 15),
    STONE("STONE", 1, 1, 131, 4.0F, 1.0F, 5),
    IRON("IRON", 2, 2, 250, 6.0F, 2.0F, 14),
    EMERALD("EMERALD", 3, 3, 1561, 8.0F, 3.0F, 10),
    GOLD("GOLD", 4, 0, 32, 12.0F, 0.0F, 22);
    private static ItemToolMaterial[] aaA;

    private float damageVsEntity;
    private int maxUses;

    private int harvestLevel;
    private int enchantability;
    private float efficiencyOnProperMaterial;

    static {
        aaA =
                new ItemToolMaterial[] {
                    net.minecraft.item.ItemToolMaterial.WOOD,
                    net.minecraft.item.ItemToolMaterial.STONE,
                    net.minecraft.item.ItemToolMaterial.IRON,
                    net.minecraft.item.ItemToolMaterial.EMERALD,
                    net.minecraft.item.ItemToolMaterial.GOLD
                };
    }

    public float getEfficiencyOnProperMaterial() {
        return this.efficiencyOnProperMaterial;
    }

    // region String decoding support

    // endregion

    public Item getRepairItem() {
        return this == net.minecraft.item.ItemToolMaterial.WOOD
                ? Item.getItemFromBlock(Blocks.planks)
                : (this == net.minecraft.item.ItemToolMaterial.STONE
                        ? Item.getItemFromBlock(Blocks.cobblestone)
                        : (this == net.minecraft.item.ItemToolMaterial.GOLD
                                ? Items.gold_ingot
                                : (this == net.minecraft.item.ItemToolMaterial.IRON
                                        ? Items.iron_ingot
                                        : (this == net.minecraft.item.ItemToolMaterial.EMERALD
                                                ? Items.diamond
                                                : null))));
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public float getDamageVsEntity() {
        return this.damageVsEntity;
    }

    // region String decoding support

    // endregion

    ItemToolMaterial(
            String text,
            int i1,
            int j,
            int k,
            float efficiencyOnProperMaterialValue,
            float damageVsEntityValue,
            int l) {
        this.harvestLevel = j;
        this.maxUses = k;
        this.efficiencyOnProperMaterial = efficiencyOnProperMaterialValue;
        this.damageVsEntity = damageVsEntityValue;
        this.enchantability = l;
    }

    public int getMaxUses() {
        return this.maxUses;
    }
}
