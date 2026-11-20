package net.minecraft.enchantment;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Map;

public abstract class Enchantment {
    public static Enchantment unbreaking;
    public static Enchantment depthStrider;
    public static Enchantment lure;
    public static Enchantment infinity;
    public static Enchantment smite;
    public static Enchantment aquaAffinity;
    public static Enchantment flame;
    public static Enchantment fireAspect;
    private static Map<ResourceLocation, Enchantment> locationEnchantments;
    public static Enchantment blastProtection;
    public int effectId;
    private static String[] packedTextFragments;
    public static Enchantment projectileProtection;
    public static Enchantment power;
    public static Enchantment baneOfArthropods;
    protected String name;
    public EnumEnchantmentType type;

    public static Enchantment[] enchantmentsBookList;
    public static Enchantment fireProtection;
    public static Enchantment efficiency;
    public static Enchantment featherFalling;
    public static Enchantment protection;
    public static Enchantment luckOfTheSea;
    public static Enchantment thorns;

    public static Enchantment silkTouch;
    public static Enchantment knockback;
    public static Enchantment punch;
    public static Enchantment fortune;
    public static Enchantment respiration;
    private static Enchantment[] enchantmentsList;
    public static Enchantment sharpness;
    private int weight;
    public static Enchantment looting;

    static {
        net.minecraft.enchantment.Enchantment.packedTextFragments = new String[3];
        net.minecraft.enchantment.Enchantment.packedTextFragments[0] = new String(" ");
        net.minecraft.enchantment.Enchantment.enchantmentsList = new Enchantment[256];
        net.minecraft.enchantment.Enchantment.locationEnchantments = Maps.newHashMap();
        net.minecraft.enchantment.Enchantment.protection = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
        net.minecraft.enchantment.Enchantment.fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
        net.minecraft.enchantment.Enchantment.featherFalling = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
        net.minecraft.enchantment.Enchantment.blastProtection =
                new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
        net.minecraft.enchantment.Enchantment.projectileProtection = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
        net.minecraft.enchantment.Enchantment.respiration = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
        net.minecraft.enchantment.Enchantment.aquaAffinity =
                new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
        net.minecraft.enchantment.Enchantment.thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
        net.minecraft.enchantment.Enchantment.depthStrider = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
        net.minecraft.enchantment.Enchantment.sharpness = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
        net.minecraft.enchantment.Enchantment.smite = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
        net.minecraft.enchantment.Enchantment.baneOfArthropods = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
        net.minecraft.enchantment.Enchantment.knockback =
                new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
        net.minecraft.enchantment.Enchantment.fireAspect =
                new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
        net.minecraft.enchantment.Enchantment.looting =
                new EnchantmentLootBonus(
                        21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
        net.minecraft.enchantment.Enchantment.efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
        net.minecraft.enchantment.Enchantment.silkTouch =
                new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
        net.minecraft.enchantment.Enchantment.unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
        net.minecraft.enchantment.Enchantment.fortune =
                new EnchantmentLootBonus(
                        35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
        net.minecraft.enchantment.Enchantment.power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
        net.minecraft.enchantment.Enchantment.punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
        net.minecraft.enchantment.Enchantment.flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
        net.minecraft.enchantment.Enchantment.infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
        net.minecraft.enchantment.Enchantment.luckOfTheSea =
                new EnchantmentLootBonus(
                        61,
                        new ResourceLocation("luck_of_the_sea"),
                        2,
                        EnumEnchantmentType.FISHING_ROD);
        net.minecraft.enchantment.Enchantment.lure =
                new EnchantmentFishingSpeed(
                        62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
        ArrayList arraylist = Lists.newArrayList();
        for (Enchantment class_032f2c006b93f86b4ef5 : net.minecraft.enchantment.Enchantment.enchantmentsList) {
            if (class_032f2c006b93f86b4ef5 != null) {
                arraylist.add(class_032f2c006b93f86b4ef5);
            }
        }
        net.minecraft.enchantment.Enchantment.enchantmentsBookList =
                ((net.minecraft.enchantment.Enchantment[]) arraylist.toArray(new Enchantment[arraylist.size()]));
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean canApplyTogether(Enchantment enchantment) {
        return this != enchantment;
    }

    public int getMinEnchantability(int i) {
        return 1 + i * 10;
    }

    public int getMaxLevel() {
        return 1;
    }

    public String getTranslatedName(int i) {
        String text = StatCollector.translateToLocal(this.getName());
        return text
                + packedTextFragments[0]
                + StatCollector.translateToLocal("enchantment.level." + i);
    }

    protected Enchantment(
            int effectIdValue,
            ResourceLocation resourceLocation,
            int j,
            EnumEnchantmentType aZVValue) {
        this.effectId = effectIdValue;
        this.weight = j;
        this.type = aZVValue;
        if (net.minecraft.enchantment.Enchantment.enchantmentsList[effectIdValue] != null) {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }

        net.minecraft.enchantment.Enchantment.enchantmentsList[effectIdValue] = this;
        net.minecraft.enchantment.Enchantment.locationEnchantments.put(resourceLocation, this);
    }

    public float calcDamageByCreature(int i, CreatureAttribute creatureAttribute) {
        return 0.0F;
    }

    public int getMaxEnchantability(int i) {
        return this.getMinEnchantability(i) + 5;
    }

    public boolean canApply(ItemStack itemStack) {
        return this.type.canEnchantItem(itemStack.getItem());
    }

    public void onEntityDamaged(LivingEntity livingEntity, Entity entity, int i) {}

    public static String[] y() {
        EnchantmentHelper.b();

        String[] astring1 = new String[net.minecraft.enchantment.Enchantment.locationEnchantments.size()];

        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_15546 =
                        (net.minecraft.enchantment.Enchantment.locationEnchantments.keySet()).iterator();
                (__recoveryIterator171_15546).hasNext(); ) {
            ResourceLocation resourceLocation =
                    (ResourceLocation) (__recoveryIterator171_15546).next();
            astring1[i++] = resourceLocation.toString();
        }

        return astring1;
    }

    public Enchantment setName(String text) {
        this.name = text;
        return this;
    }

    public int getMinLevel() {
        return 1;
    }

    public int calcModifierDamage(int i, DamageSource damageSource) {
        return 0;
    }

    public static Enchantment getEnchantmentById(int i) {
        return i >= 0 && i < net.minecraft.enchantment.Enchantment.enchantmentsList.length
                ? net.minecraft.enchantment.Enchantment.enchantmentsList[i]
                : null;
    }

    public static Enchantment getEnchantmentByLocation(String text) {
        return ((net.minecraft.enchantment.Enchantment)
                net.minecraft.enchantment.Enchantment.locationEnchantments.get(new ResourceLocation(text)));
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public String getName() {
        return "enchantment." + this.name;
    }

    public void onUserHurt(LivingEntity livingEntity, Entity entity, int i) {}
}
