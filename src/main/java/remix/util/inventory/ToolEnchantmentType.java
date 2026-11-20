package remix.util.inventory;

import remix.util.time.ElapsedTimer;

import net.minecraft.enchantment.Enchantment;

public enum ToolEnchantmentType {
    UNBREAKING(Enchantment.unbreaking, 25),
    EFFICIENCY(Enchantment.efficiency, 50),
    SILKTOUCH(Enchantment.silkTouch, 25),
    FORTUNE(Enchantment.fortune, 25),
    PROTECTION(Enchantment.protection, 100),
    FIREPROTECTION(Enchantment.fireProtection, 50),
    BLASTPROTECTION(Enchantment.blastProtection, 30),
    PROJECTILEPROTECTION(Enchantment.projectileProtection, 30),
    RESPIRATION(Enchantment.respiration, 20),
    AQUAAFFINITY(Enchantment.aquaAffinity, 30),
    FEATHERFALLING(Enchantment.featherFalling, 50),
    THORNS(Enchantment.thorns, 40),
    DEPTHSTRIDER(Enchantment.depthStrider, 30),
    SHARPNESS(Enchantment.sharpness, 100),
    SMITE(Enchantment.smite, 50),
    BANEOFARTHROPODS(Enchantment.baneOfArthropods, 20),
    KNOCKBACK(Enchantment.knockback, 75),
    FIREASPECT(Enchantment.fireAspect, 80),
    LOOTING(Enchantment.looting, 40);

    private int cz;
    private Enchantment da;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public int method_7eec03da329a017ec143() {
        return this.cz;
    }

    public Enchantment method_34ba6a45876c69ba1285() {
        return this.da;
    }

    ToolEnchantmentType(Enchantment enchantment, int j) {
        this.da = enchantment;
        this.cz = j;
    }


    public static int getEnchantmentWeight(Enchantment enchantment) {
        ElapsedTimer.fM();

        ToolEnchantmentType[] toolEnchantmentTypeArray = values();
        int i = 0;
        int j = toolEnchantmentTypeArray.length;

        while (i < j) {
            ToolEnchantmentType toolEnchantmentType = toolEnchantmentTypeArray[i];
            if (toolEnchantmentType.method_34ba6a45876c69ba1285() == enchantment) {
                return toolEnchantmentType.method_7eec03da329a017ec143();
            }

            i++;
        }

        return 0;
    }

    // region String decoding support

    // endregion

}
