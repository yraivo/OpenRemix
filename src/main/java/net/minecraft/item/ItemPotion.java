package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ItemPotion extends Item {
    private static String[] packedTextFragments;

    private static Map aqF;
    private static Map SUB_ITEMS_CACHE;

    public int getColorFromDamage(int i) {
        return PotionHelper.getLiquidColor(i, false);
    }

    public boolean hasEffect(ItemStack itemStack) {
        List list = getPotionEffects(itemStack);
        return list != null && !list.isEmpty();
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        if (itemStack.getItemDamage() == 0) {
            return StatCollector.translateToLocal("item.emptyPotion.name").trim();
        }

        String text = packedTextFragments[0];
        if (isSplash(itemStack.getItemDamage())) {
            text =
                    StatCollector.translateToLocal("potion.prefix.grenade").trim()
                            + packedTextFragments[1];
        }

        List list = getPotionEffects(itemStack);
        if (list != null && !list.isEmpty()) {
            String textValue = ((PotionEffect) list.get(0)).getEffectName();
            textValue = textValue + ".postfix";
            return text + StatCollector.translateToLocal(textValue).trim();
        } else {
            String textValue2 = PotionHelper.getPotionPrefix(itemStack.getItemDamage());
            return StatCollector.translateToLocal(textValue2).trim()
                    + packedTextFragments[2]
                    + super.getItemStackDisplayName(itemStack);
        }
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (isSplash(itemStack.getItemDamage())) {
            if (!playerEntity.capabilities.isCreativeMode) {
                itemStack.stackSize--;
            }

            world.playSoundAtEntity(
                    playerEntity,
                    "random.bow",
                    0.5F,
                    0.4F / (net.minecraft.item.Item.itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityPotion(world, playerEntity, itemStack));
            }

            playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            return itemStack;
        } else {
            playerEntity.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            return itemStack;
        }
    }

    public int getMaxItemUseDuration(ItemStack itemStack) {
        return 32;
    }

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        return i > 0 ? 16777215 : this.getColorFromDamage(itemStack.getItemDamage());
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        super.getSubItems(item, creativeTabs, list);
        String textValue = text;
        if (net.minecraft.item.ItemPotion.SUB_ITEMS_CACHE.isEmpty()) {
            int j = 0;

            while (j <= 15) {
                int i = 0;

                while (i <= 1) {
                    int k;
                    {
                        if (i == 0) {
                            k = j | 8192;
                            if (!(textValue == null)) {

                                k = j | 16384;
                            }
                        } else {

                            k = j | 16384;
                        }
                    }

                    int l = 0;

                    while (l <= 2) {
                        int i1;
                        i1 = k;
                        if (l != 0) {
                            if (l == 1) {
                                i1 = k | 32;
                                if (!(textValue == null)) {

                                    if (l == 2) {
                                        i1 = k | 64;
                                    }
                                }
                            } else {

                                if (l == 2) {
                                    i1 = k | 64;
                                }
                            }
                        }

                        List listValue =
                                PotionHelper.getPotionEffects(i1, false);
                        if (listValue != null && !listValue.isEmpty()) {
                            net.minecraft.item.ItemPotion.SUB_ITEMS_CACHE.put(listValue, i1);
                        }

                        l++;
                        if (textValue != null) {
                            break;
                        }
                    }

                    i++;
                    if (textValue != null) {
                        break;
                    }
                }

                j++;
                if (textValue != null) {
                    break;
                }
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_26812 = (net.minecraft.item.ItemPotion.SUB_ITEMS_CACHE.values()).iterator();
                (__recoveryIterator171_26812).hasNext(); ) {
            int j1 = ((java.lang.Integer) (__recoveryIterator171_26812).next()).intValue();
            list.add(new ItemStack(item, 1, j1));
            if (textValue != null) {
                break;
            }
        }
    }

    public ItemPotion() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }

    public ItemStack onItemUseFinish(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (!playerEntity.capabilities.isCreativeMode) {
            itemStack.stackSize--;
        }

        if (!world.isRemote) {
            List list = getPotionEffects(itemStack);
            if (list != null) {
                for (java.util.Iterator<?> __recoveryIterator171_27851 = (list).iterator();
                        (__recoveryIterator171_27851).hasNext(); ) {
                    PotionEffect potionEffect = (PotionEffect) (__recoveryIterator171_27851).next();
                    playerEntity.addPotionEffect(new PotionEffect(potionEffect));
                    if (text != null) {
                        break;
                    }
                }
            }
        }

        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        if (!playerEntity.capabilities.isCreativeMode) {
            if (itemStack.stackSize <= 0) {
                return new ItemStack(Items.glass_bottle);
            }

            playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }

        return itemStack;
    }

    public static List<PotionEffect> getPotionEffects(ItemStack itemStack) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (itemStack.hasTagCompound() && itemStack.getTag().hasKey("CustomPotionEffects", 9)) {
            ArrayList arrayList = Lists.newArrayList();
            NBTTagList nbtTagList = itemStack.getTag().getTagList("CustomPotionEffects", 10);
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                CompoundTag compoundTag = nbtTagList.getCompoundTagAt(i);
                PotionEffect potionEffect = PotionEffect.readCustomPotionEffectFromNBT(compoundTag);
                if (potionEffect != null) {
                    arrayList.add(potionEffect);
                }

                i++;
                if (text != null) {
                    break;
                }
            }

            return arrayList;
        } else {
            List list = (List) aqF.get(itemStack.getItemDamage());
            if (list == null) {
                list =
                        PotionHelper.getPotionEffects(
                                itemStack.getItemDamage(), false);
                aqF.put(itemStack.getItemDamage(), list);
            }

            return list;
        }
    }

    static {
        net.minecraft.item.ItemPotion.packedTextFragments = new String[8];
        net.minecraft.item.ItemPotion.packedTextFragments[0] = new String("");
        net.minecraft.item.ItemPotion.packedTextFragments[1] = new String(" ");
        net.minecraft.item.ItemPotion.packedTextFragments[2] = new String(" ");
        net.minecraft.item.ItemPotion.packedTextFragments[3] = new String(" ");
        net.minecraft.item.ItemPotion.packedTextFragments[4] = new String(")");
        net.minecraft.item.ItemPotion.packedTextFragments[5] = new String("");
        aqF = Maps.newHashMap();
        net.minecraft.item.ItemPotion.SUB_ITEMS_CACHE = Maps.newLinkedHashMap();
    }

    public static boolean isSplash(int i) {
        return (i & 16384) != 0;
    }

    public void addInformation(
            ItemStack itemStack, PlayerEntity playerEntity, List list, boolean flag) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (itemStack.getItemDamage() != 0) {
            HashMultimap hashmultimap;
            label86:
            {
                List listValue = getPotionEffects(itemStack);
                hashmultimap = HashMultimap.create();
                if (listValue != null && !listValue.isEmpty()) {
                    Iterator iterator = listValue.iterator();

                    do {
                        if (!iterator.hasNext()) {
                            break label86;
                        }

                        PotionEffect potionEffect = (PotionEffect) iterator.next();
                        Object object =
                                StatCollector.translateToLocal(potionEffect.getEffectName()).trim();
                        Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                        Map map = potion.getAttributeModifierMap();
                        if (map != null && map.size() > 0) {
                            for (java.util.Iterator<?> __recoveryIterator171_35269 =
                                            (map.entrySet()).iterator();
                                    (__recoveryIterator171_35269).hasNext(); ) {
                                Entry entry = (Entry) (__recoveryIterator171_35269).next();
                                AttributeModifier value = (AttributeModifier) entry.getValue();
                                AttributeModifier attributeModifier =
                                        new AttributeModifier(
                                                value.getName(),
                                                potion.getAttributeModifierAmount(
                                                        potionEffect.getAmplifier(), value),
                                                value.getOperation());
                                hashmultimap.put(
                                        ((IAttribute) entry.getKey()).getAttributeUnlocalizedName(),
                                        attributeModifier);
                                if (text != null) {
                                    break;
                                }
                            }
                        }

                        if (potionEffect.getAmplifier() > 0) {
                            object =
                                    object
                                            + packedTextFragments[3]
                                            + StatCollector.translateToLocal(
                                                            "potion.potency."
                                                                    + potionEffect.getAmplifier())
                                                    .trim();
                        }

                        if (potionEffect.getDuration() > 20) {
                            object =
                                    object
                                            + " ("
                                            + Potion.getDurationString(potionEffect)
                                            + packedTextFragments[4];
                        }

                        if (potion.isBadEffect()) {
                            list.add(EnumChatFormatting.RED + (String) object);
                            if (text == null) {
                                continue;
                            }
                        }

                        list.add(EnumChatFormatting.GRAY + (String) object);
                    } while (text == null);
                }

                Object object1 = StatCollector.translateToLocal("potion.empty").trim();
                list.add(EnumChatFormatting.GRAY + (String) object1);
            }

            if (!hashmultimap.isEmpty()) {
                list.add(packedTextFragments[5]);
                list.add(
                        EnumChatFormatting.DARK_PURPLE
                                + StatCollector.translateToLocal("potion.effects.whenDrank"));

                for (java.util.Iterator<?> __recoveryIterator171_37800 =
                                (hashmultimap.entries()).iterator();
                        (__recoveryIterator171_37800).hasNext(); ) {
                    Entry entry1 = (Entry) (__recoveryIterator171_37800).next();
                    AttributeModifier attributeModifierValue;
                    double d0;
                    double d1;
                    {
                        attributeModifierValue = (AttributeModifier) entry1.getValue();
                        d0 = attributeModifierValue.getAmount();
                        if (attributeModifierValue.getOperation() != 1
                                && attributeModifierValue.getOperation() != 2) {
                            d1 = attributeModifierValue.getAmount();
                            if (!(text == null)) {

                                d1 = attributeModifierValue.getAmount() * 100.0;
                            }
                        } else {

                            d1 = attributeModifierValue.getAmount() * 100.0;
                        }
                    }

                    label45:
                    {
                        if (d0 > 0.0) {
                            list.add(
                                    EnumChatFormatting.BLUE
                                            + StatCollector.translateToLocalFormatted(
                                                    "attribute.modifier.plus."
                                                            + attributeModifierValue.getOperation(),
                                                    new Object[] {
                                                        ItemStack.DECIMALFORMAT.format(d1),
                                                        StatCollector.translateToLocal(
                                                                "attribute.name."
                                                                        + (String) entry1.getKey())
                                                    }));
                            if (text == null) {
                                break label45;
                            }
                        }

                        if (d0 < 0.0) {
                            d1 *= -1.0;
                            list.add(
                                    EnumChatFormatting.RED
                                            + StatCollector.translateToLocalFormatted(
                                                    "attribute.modifier.take."
                                                            + attributeModifierValue.getOperation(),
                                                    new Object[] {
                                                        ItemStack.DECIMALFORMAT.format(d1),
                                                        StatCollector.translateToLocal(
                                                                "attribute.name."
                                                                        + (String) entry1.getKey())
                                                    }));
                        }
                    }

                    if (text != null) {
                        break;
                    }
                }
            }
        }
    }

    public List getEffects(int i) {
        List list = (List) aqF.get(i);
        if (list == null) {
            list = PotionHelper.getPotionEffects(i, false);
            aqF.put(i, list);
        }

        return list;
    }

    public boolean isEffectInstant(int i) {
        List list = this.getEffects(i);
        if (list != null && !list.isEmpty()) {
            for (java.util.Iterator<?> __recoveryIterator171_41605 = (list).iterator();
                    (__recoveryIterator171_41605).hasNext(); ) {
                PotionEffect potionEffect = (PotionEffect) (__recoveryIterator171_41605).next();
                if (Potion.potionTypes[potionEffect.getPotionID()].isInstant()) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.DRINK;
    }
}
