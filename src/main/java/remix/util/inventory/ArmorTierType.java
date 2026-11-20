package remix.util.inventory;

import remix.util.time.ElapsedTimer;

import net.minecraft.item.ItemArmorArmorMaterial;

public enum ArmorTierType {
    LEATHER(ItemArmorArmorMaterial.LEATHER, 50),
    GOLD(ItemArmorArmorMaterial.GOLD, 100),
    CHAIN(ItemArmorArmorMaterial.CHAIN, 200),
    IRON(ItemArmorArmorMaterial.IRON, 300),
    DIAMOND(ItemArmorArmorMaterial.DIAMOND, 400);

    private int cz;

    private ItemArmorArmorMaterial cD;

    // region String decoding support

    // endregion

    public int method_7eec03da329a017ec143() {
        return this.cz;
    }


    // region String decoding support

    // endregion

    public ItemArmorArmorMaterial method_ffcb011f4d6a40083fe9() {
        return this.cD;
    }

    // region String decoding support

    // endregion

    public static int getMaterialWeight(ItemArmorArmorMaterial itemArmorArmorMaterial) {
        ArmorTierType[] armorTierTypeArray = values();
        ElapsedTimer.fM();

        int i = 0;
        int j = armorTierTypeArray.length;

        while (i < j) {
            ArmorTierType armorTierType = armorTierTypeArray[i];
            if (armorTierType.method_ffcb011f4d6a40083fe9()
                    == itemArmorArmorMaterial) {
                return armorTierType.method_7eec03da329a017ec143();
            }

            i++;
        }

        return 0;
    }

    ArmorTierType(ItemArmorArmorMaterial itemArmorArmorMaterial, int j) {
        this.cz = j;
        this.cD = itemArmorArmorMaterial;
    }
}
