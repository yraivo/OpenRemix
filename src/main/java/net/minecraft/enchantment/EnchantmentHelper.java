package net.minecraft.enchantment;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnchantmentHelper {
    private static EnchantmentHelperModifierDamage enchantmentModifierDamage;
    private static EnchantmentHelperDamageIterator ENCHANTMENT_ITERATOR_DAMAGE;

    private static EnchantmentHelperModifierLiving enchantmentModifierLiving;

    private static String[] f;
    private static EnchantmentHelperHurtIterator ENCHANTMENT_ITERATOR_HURT;

    private static Random enchantmentRand;

    // region String decoding support

    // endregion

    public static int getKnockbackModifier(LivingEntity livingEntity) {
        return getEnchantmentLevel(Enchantment.knockback.effectId, livingEntity.getHeldItem());
    }

    // region String decoding support

    // endregion

    public static int getLootingModifier(LivingEntity livingEntity) {
        return getEnchantmentLevel(Enchantment.looting.effectId, livingEntity.getHeldItem());
    }

    public static void applyThornEnchantments(LivingEntity aZk, Entity aZj) {
        net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT.attacker = aZj;
        net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT.user = aZk;
        if (aZk != null) {
            applyEnchantmentModifierArray(
                    net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT, aZk.getInventory());
        }

        if (aZj instanceof PlayerEntity) {
            applyEnchantmentModifier(
                    net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT, aZk.getHeldItem());
        }
    }

    public static int getEnchantmentLevel(int i, ItemStack itemStack) {
        b();

        if (itemStack == null) {
            return 0;
        }

        NBTTagList nbtTagList = itemStack.getEnchantmentTagList();
        if (nbtTagList == null) {
            return 0;
        }

        int j = 0;

        while (j < nbtTagList.tagCount()) {
            short short1 = nbtTagList.getCompoundTagAt(j).getShort("id");
            short short2 = nbtTagList.getCompoundTagAt(j).getShort("lvl");
            if (short1 == i) {
                return short2;
            }

            j++;
        }

        return 0;
    }

    public static float getEnchantmentDamageBonus(ItemStack itemStack, CreatureAttribute aZm) {
        net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierLiving.livingModifier = 0.0F;
        net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierLiving.entityLiving = aZm;
        applyEnchantmentModifier(net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierLiving, itemStack);
        return net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierLiving.livingModifier;
    }

    public static boolean getSilkTouchModifier(LivingEntity livingEntity) {
        return getEnchantmentLevel(Enchantment.silkTouch.effectId, livingEntity.getHeldItem()) > 0;
    }

    public static int getLuckOfSeaModifier(LivingEntity livingEntity) {
        return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, livingEntity.getHeldItem());
    }

    public static List buildEnchantmentList(Random random, ItemStack itemStack, int i) {
        String[] astring1 = b();
        Item item = itemStack.getItem();
        String[] astring = astring1;
        int j = item.getItemEnchantability();
        if (j <= 0) {
            return null;
        }

        j /= 2;
        j = 1 + random.nextInt((j >> 1) + 1) + random.nextInt((j >> 1) + 1);
        int k = j + i;
        float fx = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
        int l = (int) (k * (1.0F + fx) + 0.5F);
        if (l < 1) {
            l = 1;
        }

        ArrayList arrayList = null;
        Map map = mapEnchantmentData(l, itemStack);
        if (map != null && !map.isEmpty()) {
            EnchantmentData randomItem =
                    (EnchantmentData) WeightedRandom.getRandomItem(random, map.values());
            if (randomItem != null) {
                arrayList = Lists.newArrayList();
                arrayList.add(randomItem);
                int i1 = l;

                while (random.nextInt(50) <= i1) {
                    Iterator iterator = map.keySet().iterator();

                    while (iterator.hasNext()) {
                        Integer integer = (Integer) iterator.next();
                        byte b0 = 1;

                        for (java.util.Iterator<?> __recoveryIterator171_26651 =
                                        (arrayList).iterator();
                                (__recoveryIterator171_26651).hasNext(); ) {
                            EnchantmentData enchantmentData =
                                    (EnchantmentData) (__recoveryIterator171_26651).next();
                            if (!enchantmentData.enchantmentobj.canApplyTogether(
                                            Enchantment.getEnchantmentById(integer))
                                    || astring == null) {
                                b0 = 0;
                                break;
                            }
                        }

                        if (b0 == 0) {
                            iterator.remove();
                        }
                    }

                    if (!map.isEmpty()) {
                        EnchantmentData randomItemValue =
                                (EnchantmentData)
                                        WeightedRandom.getRandomItem(random, map.values());
                        arrayList.add(randomItemValue);
                    }

                    i1 >>= 1;
                }
            }
        }

        return arrayList;
    }

    public static int calcItemStackEnchantability(
            Random random, int i, int j, ItemStack itemStack) {
        Item item = itemStack.getItem();
        int k = item.getItemEnchantability();
        if (k <= 0) {
            return 0;
        }

        if (j > 15) {
            j = 15;
        }

        int l = random.nextInt(8) + 1 + (j >> 1) + random.nextInt(j + 1);
        return i == 0 ? Math.max(l / 3, 1) : (i == 1 ? l * 2 / 3 + 1 : Math.max(l, j * 2));
    }

    public static boolean getAquaAffinityModifier(LivingEntity livingEntity) {
        return getMaxEnchantmentLevel(
                        Enchantment.aquaAffinity.effectId, livingEntity.getInventory())
                > 0;
    }

    public static void b(String[] astring) {
        f = astring;
    }

    public static int getEnchantmentModifierDamage(ItemStack[] itemStackArray, DamageSource aZl) {
        net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier = 0;
        b();
        net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.source = aZl;
        applyEnchantmentModifierArray(
                net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage, itemStackArray);
        if (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier > 25) {
            net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier = 25;
        }

        int i =
                (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier + 1 >> 1)
                        + net.minecraft.enchantment.EnchantmentHelper.enchantmentRand.nextInt(
                                (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier
                                                >> 1)
                                        + 1);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new String[4]);
        }

        return i;
    }

    public static int getLureModifier(LivingEntity livingEntity) {
        return getEnchantmentLevel(Enchantment.lure.effectId, livingEntity.getHeldItem());
    }

    // region Static initialization and string tables
    static {
        String[] astring1 = new String[1];
        b(astring1);
        net.minecraft.enchantment.EnchantmentHelper.enchantmentRand = new Random();
        net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage =
                new EnchantmentHelperModifierDamage(null);
        net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierLiving =
                new EnchantmentHelperModifierLiving(null);
        net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT = new EnchantmentHelperHurtIterator(null);
        net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE =
                new EnchantmentHelperDamageIterator(null);
    }

    // endregion

    public static int getEnchantmentDamageModifier(ItemStack[] itemStackArray, DamageSource aZl) {
        net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier = 0;
        b();

        net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.source = aZl;
        applyEnchantmentModifierArray(
                net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage, itemStackArray);

        if (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier > 25) {
            net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier = 25;

            return (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier + 1 >> 1)
                    + (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier >> 1)
                    + 1;
        }

        if (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier < 0) {
            net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier = 0;
        }

        return (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier + 1 >> 1)
                + (net.minecraft.enchantment.EnchantmentHelper.enchantmentModifierDamage.damageModifier >> 1)
                + 1;
    }

    private static void applyEnchantmentModifierArray(
            EnchantmentHelperIModifier enchantmentHelperIModifier, ItemStack[] itemStackArray) {
        b();

        ItemStack[] itemStackArrayValue = itemStackArray;
        int length = itemStackArray.length;

        int j = 0;

        while (j < length) {
            ItemStack itemStack = itemStackArrayValue[j];
            applyEnchantmentModifier(enchantmentHelperIModifier, itemStack);
            j++;
        }
    }

    public static Map getEnchantments(ItemStack itemStack) {
        b();

        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();

        NBTTagList nbtTagList =
                itemStack.getItem() == Items.enchanted_book
                        ? Items.enchanted_book.getEnchantments(itemStack)
                        : itemStack.getEnchantmentTagList();
        if (nbtTagList != null) {
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                short short1 = nbtTagList.getCompoundTagAt(i).getShort("id");
                short short2 = nbtTagList.getCompoundTagAt(i).getShort("lvl");
                linkedhashmap.put(Integer.valueOf(short1), Integer.valueOf(short2));
                i++;
            }
        }

        return linkedhashmap;
    }

    public static int getRespiration(Entity entity) {
        return getMaxEnchantmentLevel(Enchantment.respiration.effectId, entity.getInventory());
    }

    public static Map mapEnchantmentData(int i, ItemStack itemStack) {
        Item item = itemStack.getItem();
        b();

        HashMap hashmap = null;

        int j = itemStack.getItem() == Items.book ? 1 : 0;

        for (Enchantment enchantment : Enchantment.enchantmentsBookList) {
            if (enchantment != null && (enchantment.type.canEnchantItem(item) || j != 0)) {
                int k = enchantment.getMinLevel();

                while (k <= enchantment.getMaxLevel()) {
                    if (i >= enchantment.getMinEnchantability(k)
                            && i <= enchantment.getMaxEnchantability(k)) {
                        if (hashmap == null) {
                            hashmap = Maps.newHashMap();
                        }

                        hashmap.put(enchantment.effectId, new EnchantmentData(enchantment, k));
                    }

                    k++;
                }
            }
        }

        return hashmap;
    }

    public static ItemStack addRandomEnchantment(Random random, ItemStack itemStack, int i) {
        b();

        List list = buildEnchantmentList(random, itemStack, i);

        int j = itemStack.getItem() == Items.book ? 1 : 0;
        if (j != 0) {
            itemStack.setItem(Items.enchanted_book);
        }

        if (list != null) {
            for (java.util.Iterator<?> __recoveryIterator171_38275 = (list).iterator();
                    (__recoveryIterator171_38275).hasNext(); ) {
                EnchantmentData enchantmentData =
                        (EnchantmentData) (__recoveryIterator171_38275).next();
                {
                    if (j != 0) {
                        Items.enchanted_book.addEnchantment(itemStack, enchantmentData);

                        
                    } else {

                        itemStack.addEnchantment(
                                enchantmentData.enchantmentobj, enchantmentData.enchantmentLevel);
                    }
                }
            }
        }

        return itemStack;
    }

    public static void setEnchantments(Map map, ItemStack itemStack) {
        b();

        NBTTagList nbtTagList = new NBTTagList();

        for (java.util.Iterator<?> __recoveryIterator171_39619 = (map.keySet()).iterator();
                (__recoveryIterator171_39619).hasNext(); ) {
            int i = ((java.lang.Integer) (__recoveryIterator171_39619).next()).intValue();
            Enchantment enchantment = Enchantment.getEnchantmentById(i);
            if (enchantment != null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.setShort("id", (short) i);
                compoundTag.setShort("lvl", (short) ((Integer) map.get(i)).intValue());
                nbtTagList.appendTag(compoundTag);
                if (itemStack.getItem() == Items.enchanted_book) {
                    Items.enchanted_book.addEnchantment(
                            itemStack, new EnchantmentData(enchantment, (Integer) map.get(i)));
                }
            }
        }

        if (nbtTagList.tagCount() > 0) {
            if (itemStack.getItem() == Items.enchanted_book) {
                return;
            }

            itemStack.setTagInfo("ench", nbtTagList);

            return;
        }

        if (itemStack.hasTagCompound()) {
            itemStack.getTag().removeTag("ench");
        }
    }

    public static void applyArthropodEnchantments(LivingEntity aZk, Entity aZj) {
        net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE.user = aZk;
        net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE.target = aZj;
        if (aZk != null) {
            applyEnchantmentModifierArray(
                    net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE, aZk.getInventory());
        }

        if (aZk instanceof PlayerEntity) {
            applyEnchantmentModifier(
                    net.minecraft.enchantment.EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE, aZk.getHeldItem());
        }
    }

    public static ItemStack getEnchantedItem(Enchantment enchantment, LivingEntity livingEntity) {
        ItemStack[] itemStackArray = livingEntity.getInventory();
        int length = itemStackArray.length;
        b();

        int j = 0;

        while (j < length) {
            ItemStack itemStack = itemStackArray[j];
            if (itemStack != null && getEnchantmentLevel(enchantment.effectId, itemStack) > 0) {
                return itemStack;
            }

            j++;
        }

        return null;
    }

    public static int getFireAspectModifier(LivingEntity livingEntity) {
        return getEnchantmentLevel(Enchantment.fireAspect.effectId, livingEntity.getHeldItem());
    }

    private static void applyEnchantmentModifier(
            EnchantmentHelperIModifier enchantmentHelperIModifier, ItemStack itemStack) {
        b();

        if (itemStack != null) {
            NBTTagList nbtTagList = itemStack.getEnchantmentTagList();
            if (nbtTagList != null) {
                int i = 0;

                while (i < nbtTagList.tagCount()) {
                    short short1 = nbtTagList.getCompoundTagAt(i).getShort("id");
                    short short2 = nbtTagList.getCompoundTagAt(i).getShort("lvl");
                    if (Enchantment.getEnchantmentById(short1) != null) {
                        enchantmentHelperIModifier.calculateModifier(
                                Enchantment.getEnchantmentById(short1), short2);
                    }

                    i++;
                }
            }
        }
    }

    public static String[] b() {
        return f;
    }

    public static int getDepthStriderModifier(Entity entity) {
        return getMaxEnchantmentLevel(Enchantment.depthStrider.effectId, entity.getInventory());
    }

    public static int getMaxEnchantmentLevel(int i, ItemStack[] itemStackArray) {
        b();

        if (itemStackArray == null) {
            return 0;
        }

        int j = 0;
        ItemStack[] itemStackArrayValue = itemStackArray;
        int k = itemStackArray.length;
        int l = 0;

        while (l < k) {
            ItemStack itemStack = itemStackArrayValue[l];
            int enchantmentLevel = getEnchantmentLevel(i, itemStack);
            if (enchantmentLevel > j) {
                j = enchantmentLevel;
            }

            l++;
        }

        return j;
    }

    public static int getEfficiencyModifier(LivingEntity livingEntity) {
        return getEnchantmentLevel(Enchantment.efficiency.effectId, livingEntity.getHeldItem());
    }

    public static int getFortuneModifier(LivingEntity livingEntity) {
        return getEnchantmentLevel(Enchantment.fortune.effectId, livingEntity.getHeldItem());
    }
}
