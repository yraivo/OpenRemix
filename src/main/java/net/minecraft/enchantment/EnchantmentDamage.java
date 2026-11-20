package net.minecraft.enchantment;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDamage extends Enchantment {
    private static int[] baseEnchantability;

    public int damageType;

    private static int[] levelEnchantability;
    private static String[] protectionName;
    private static int[] thresholdEnchantability;

    public int getMinEnchantability(int i) {
        return net.minecraft.enchantment.EnchantmentDamage.baseEnchantability[this.damageType]
                + (i - 1) * net.minecraft.enchantment.EnchantmentDamage.levelEnchantability[this.damageType];
    }

    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof EnchantmentDamage);
    }

    public float calcDamageByCreature(int i, CreatureAttribute creatureAttribute) {
        return this.damageType == 0
                ? i * 1.25F
                : (this.damageType == 1 && creatureAttribute == CreatureAttribute.UNDEAD
                        ? i * 2.5F
                        : (this.damageType == 2 && creatureAttribute == CreatureAttribute.ARTHROPOD
                                ? i * 2.5F
                                : 0.0F));
    }

    public String getName() {
        return "enchantment.damage." + net.minecraft.enchantment.EnchantmentDamage.protectionName[this.damageType];
    }

    public boolean canApply(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemAxe ? true : super.canApply(itemStack);
    }

    public EnchantmentDamage(int i, ResourceLocation resourceLocation, int j, int k) {
        super(i, resourceLocation, j, EnumEnchantmentType.WEAPON);
        this.damageType = k;
    }

    public int getMaxLevel() {
        return 5;
    }

    static {
        net.minecraft.enchantment.EnchantmentDamage.protectionName = new String[] {"all", "undead", "arthropods"};
        net.minecraft.enchantment.EnchantmentDamage.baseEnchantability = new int[] {1, 5, 5};
        net.minecraft.enchantment.EnchantmentDamage.levelEnchantability = new int[] {11, 8, 8};
        net.minecraft.enchantment.EnchantmentDamage.thresholdEnchantability = new int[] {20, 20, 20};
    }

    public void onEntityDamaged(LivingEntity livingEntity, Entity entity, int i) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntityValue = (LivingEntity) entity;
            if (this.damageType == 2
                    && livingEntityValue.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
                int j = 20 + livingEntity.getRNG().nextInt(10 * i);
                livingEntityValue.addPotionEffect(new PotionEffect(Potion.MOVE_SLOWDOWN.id, j, 3));
            }
        }
    }

    public int getMaxEnchantability(int i) {
        return this.getMinEnchantability(i)
                + net.minecraft.enchantment.EnchantmentDamage.thresholdEnchantability[this.damageType];
    }
}
