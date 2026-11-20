package remix.util.inventory;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockNote;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmorArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;

import remix.util.time.ElapsedTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class InventoryItemHelper {

    private static MinecraftClient minecraft;

    public static int getBestWeaponHotbarSlot() {
        LocalPlayer player = remix.util.inventory.InventoryItemHelper.minecraft.player;
        ElapsedTimer.fM();

        InventoryPlayer bdI = player.inventory;
        int currentItem = bdI.currentItem;

        int j = -1;
        float f = 1.0F;
        int k = 0;

        while (k < 9) {
            bdI.currentItem = k;
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null) {
                float f1 = getItemDamage(heldItem);
                if (f1 > f) {
                    f = f1;
                    j = k;
                }
            }

            k++;
        }

        return j != -1 ? j : currentItem;
    }

    public static int getEnchantmentScore(ItemStack itemStack) {
        ElapsedTimer.fM();

        if (itemStack == null) {
            return 0;
        }

        int i = 0;
        NBTTagList nbtTagList = itemStack.getEnchantmentTagList();
        if (nbtTagList != null) {
            int j = 0;
            int k = nbtTagList.tagCount();

            while (j < k) {
                CompoundTag compoundTag = nbtTagList.getCompoundTagAt(j);
                int l = compoundTag.getInteger("lvl");
                int integer = compoundTag.getInteger("id");
                i +=
                        ToolEnchantmentType.getEnchantmentWeight(
                                        Enchantment.getEnchantmentById(integer))
                                * l;
                j++;
            }
        }

        return i;
    }

    public static ItemStack[] getBestArmorSet(java.util.List list) {
        ItemStack[] itemStackArray = new ItemStack[4];
        ElapsedTimer.fM();

        int i = 0;
        int j = list.size();

        while (i < j) {
            List listValue = ((Container) list.get(i)).inventorySlots;
            int k = 0;
            int l = listValue.size();

            while (true) {
                if (k < l) {
                    Slot slot = (Slot) listValue.get(k);
                    if (slot.getHasStack()) {
                        ItemStack itemStack = slot.getStack();
                        Item item = itemStack.getItem();
                        if (item instanceof ItemArmor) {
                            ItemArmor itemArmor = (ItemArmor) item;
                            int armorType = itemArmor.armorType;
                            ItemStack itemStackValue = itemStackArray[armorType];
                            if (itemStackValue == null
                                    || getArmorProtection(itemStack)
                                            > getArmorProtection(itemStackValue)) {
                                itemStackArray[armorType] = itemStack;
                            }
                        }
                    }

                    k++;

                    continue;
                }

                i++;

                break;
            }
        }

        return itemStackArray;
    }

    public static ItemSlot findItemSlotRange(Item item, int i, int j) {
        return findSlotMatching(
                item,
                i,
                j,
                ((java.util.function.Predicate<net.minecraft.item.Item>) (itemValue -> true)));
    }

    public static double getArmorProtection(ItemStack itemStack) {
        int damageReduceAmount = ((ItemArmor) itemStack.getItem()).damageReduceAmount;
        damageReduceAmount +=
                EnchantmentHelper.getEnchantmentDamageModifier(
                        new ItemStack[] {itemStack},
                        DamageSource.causePlayerDamage(
                                remix.util.inventory.InventoryItemHelper.minecraft.player));
        return damageReduceAmount;
    }

    public static ItemSlot findBestToolForBlockRange(BlockPos blockPos, int i, int j) {
        BlockState blockState =
                remix.util.inventory.InventoryItemHelper.minecraft.world.getBlockState(blockPos);
        LocalPlayer player = remix.util.inventory.InventoryItemHelper.minecraft.player;
        Container bdq = player.inventoryContainer;
        ElapsedTimer.fM();

        float f = 1.0F;

        ItemSlot itemSlot =
                new ItemSlot(player.getCurrentEquippedItem(), player.inventory.currentItem + 36);
        int k = j;

        while (k >= i) {
            ItemStack itemStack = bdq.getSlot(k).getStack();
            if (itemStack != null
                    && blockState != Blocks.air
                    && getStrVsBlock(blockState, itemStack) > f) {
                itemSlot = new ItemSlot(itemStack, k);
                f = getStrVsBlock(blockState, itemStack);
            }

            k--;
        }

        return itemSlot;
    }

    private static float getStrVsBlock(BlockState blockState, ItemStack itemStack) {
        float strVsBlock = itemStack.getStrVsBlock(blockState.getBlock());
        int effectId = Enchantment.efficiency.effectId;
        if (strVsBlock != 1.0F
                && EnchantmentHelper.getEnchantments(itemStack).containsKey(effectId)) {
            int j = EnchantmentHelper.getEnchantmentLevel(effectId, itemStack);
            return strVsBlock + (j * j + 1);
        } else {
            return strVsBlock;
        }
    }

    public static float getAttackDamageVsEntity(ItemStack itemStack, Entity entity) {
        ElapsedTimer.fM();

        float f = 1.0F;

        if (itemStack != null) {
            Multimap multimap = itemStack.getAttributeModifiers();
            if (!multimap.isEmpty()) {
                for (java.util.Iterator<?> __recoveryIterator171_28422 =
                                (multimap.get(
                                                SharedMonsterAttributes.attackDamage
                                                        .getAttributeUnlocalizedName()))
                                        .iterator();
                        (__recoveryIterator171_28422).hasNext(); ) {
                    AttributeModifier attributeModifier =
                            (AttributeModifier) (__recoveryIterator171_28422).next();
                    f = (float) (f + attributeModifier.getAmount());
                }
            }
        }

        CreatureAttribute creatureAttribute =
                entity instanceof LivingEntity
                        ? ((LivingEntity) entity).getCreatureAttribute()
                        : CreatureAttribute.UNDEFINED;
        return f + EnchantmentHelper.getEnchantmentDamageBonus(itemStack, creatureAttribute);
    }

    public static void clickSlot(GuiContainer guiContainer, int i, int j, int k) {
        Slot slot = guiContainer.inventorySlots.getSlot(i);
        guiContainer.handleMouseClick(slot, slot.slotNumber, j, k);
    }

    public static ItemSlot findItemSlotWithPredicate(
            Item item, java.util.function.Predicate predicate) {
        LocalPlayer player = remix.util.inventory.InventoryItemHelper.minecraft.player;
        InventoryPlayer bdI = player.inventory;
        ItemSlot itemSlot = new ItemSlot(player.getCurrentEquippedItem(), bdI.currentItem + 36);
        ElapsedTimer.fM();

        byte b0 = 0;

        while (b0 < 9) {
            bdI.currentItem = b0;
            ItemStack heldItem = player.getHeldItem();
            if (heldItem != null) {
                Item itemValue = heldItem.getItem();
                if (itemValue == item && predicate.test(itemValue)) {
                    itemSlot = new ItemSlot(heldItem, b0);
                }
            }

            b0++;
        }

        return itemSlot;
    }

    public static double getWeaponDamageScore(ItemStack itemStack) {
        double d0 = 0.0;
        if (itemStack != null) {
            Optional optional = Optional.empty();
            Collection collection = itemStack.getAttributeModifiers().values();
            Iterator iterator = collection.iterator();
            if (iterator.hasNext()) {
                AttributeModifier attributeModifier = (AttributeModifier) iterator.next();
                optional = Optional.of(attributeModifier);
            }

            if (optional.isPresent()) {
                d0 = ((AttributeModifier) optional.get()).getAmount();
            }

            d0 +=
                    EnchantmentHelper.getEnchantmentDamageBonus(
                            itemStack, CreatureAttribute.UNDEFINED);
        }

        return d0;
    }

    static {
        remix.util.inventory.InventoryItemHelper.minecraft = MinecraftClient.getMinecraft();
    }

    public static ItemStack[] getBestArmorFromPlayerInventory() {
        ItemStack[] itemStackArray = new ItemStack[4];
        ElapsedTimer.fM();

        Container bdq =
                remix.util.inventory.InventoryItemHelper.minecraft.player.inventoryContainer;

        if (bdq != null) {
            List inventorySlots = bdq.inventorySlots;
            int i = 0;
            int j = inventorySlots.size();

            while (i < j) {
                Slot slot = (Slot) inventorySlots.get(i);
                if (slot.getHasStack()) {
                    ItemStack itemStack = slot.getStack();
                    Item item = itemStack.getItem();
                    if (item instanceof ItemArmor) {
                        ItemArmor itemArmor = (ItemArmor) item;
                        int k = itemArmor.armorType;
                        ItemStack itemStackValue = itemStackArray[k];
                        if (itemStackValue == null
                                || getArmorProtection(itemStack)
                                        > getArmorProtection(itemStackValue)) {
                            itemStackArray[k] = itemStack;
                        }
                    }
                }

                i++;
            }
        }

        return itemStackArray;
    }

    public static ItemSlot findItemSlot(Item item) {
        return findItemSlotWithPredicate(
                item,
                ((java.util.function.Predicate<net.minecraft.item.Item>) (itemValue -> true)));
    }

    public static ItemSlot findBestToolForBlock(BlockPos blockPos) {
        BlockState blockState =
                remix.util.inventory.InventoryItemHelper.minecraft.world.getBlockState(blockPos);
        LocalPlayer player = remix.util.inventory.InventoryItemHelper.minecraft.player;
        Container bdq = player.inventoryContainer;
        ItemSlot itemSlot =
                new ItemSlot(player.getCurrentEquippedItem(), player.inventory.currentItem + 36);
        float f = 0.1F;
        ElapsedTimer.fM();

        int i = 36;

        while (i < 45) {
            ItemStack itemStack = bdq.getSlot(i).getStack();
            if (itemStack != null
                    && blockState != Blocks.air
                    && getStrVsBlock(blockState, itemStack) > f) {
                itemSlot = new ItemSlot(itemStack, i - 36);
                f = getStrVsBlock(blockState, itemStack);
            }

            i++;
        }

        return itemSlot;
    }

    public static float getDamageVsLiving(LivingEntity livingEntity, ItemStack itemStack) {
        return calculateTotalDamage(livingEntity, itemStack, 0.0F);
    }

    public static float getItemDamage(ItemStack itemStack) {
        return getAttackDamageVsEntity(itemStack, null);
    }

    public static float calculateTotalDamage(
            LivingEntity livingEntity, ItemStack itemStack, float f2) {
        ElapsedTimer.fM();

        LocalPlayer player = remix.util.inventory.InventoryItemHelper.minecraft.player;

        DamageSource damageSource = DamageSource.causePlayerDamage(player);
        float baseValue =
                (float)
                        player.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                                .getBaseValue();
        if (livingEntity.isEntityInvulnerable(damageSource)
                || remix.util.inventory.InventoryItemHelper.minecraft.world.isRemote
                || livingEntity == player) {
            return 0.0F;
        }

        if (itemStack == null) {
            return baseValue;
        }

        Multimap multimap = itemStack.getAttributeModifiers();

        for (java.util.Iterator<?> __recoveryIterator171_40365 =
                        (multimap.get(
                                        SharedMonsterAttributes.attackDamage
                                                .getAttributeUnlocalizedName()))
                                .iterator();
                (__recoveryIterator171_40365).hasNext(); ) {
            AttributeModifier attributeModifier =
                    (AttributeModifier) (__recoveryIterator171_40365).next();
            baseValue = (float) (baseValue + attributeModifier.getAmount());
        }

        float f1 =
                EnchantmentHelper.getEnchantmentDamageBonus(
                        itemStack, livingEntity.getCreatureAttribute());
        if (!(baseValue > 0.0F) && !(f1 > 0.0F)) {
            return 0.0F;
        }

        boolean aop = player.velocityChanged;
        int i =
                aop
                                && player.fallDistance > 0.0F
                                && !player.onGround
                                && !player.isOnLadder()
                                && !player.isInWater()
                                && !player.isPotionActive(Potion.BLINDNESS)
                                && !player.isRiding()
                        ? 1
                        : 0;
        i = i != 0 && !player.isSprinting() ? 1 : 0;
        if (i != 0) {
            baseValue *= 1.5F;
        }

        baseValue += f1;
        if (livingEntity.hurtResistantTime > livingEntity.maxHurtResistantTime / 2.0F
                && baseValue <= livingEntity.lastDamage) {
            return 0.0F;
        }

        baseValue = livingEntity.applyArmorCalculations(damageSource, baseValue);
        baseValue = livingEntity.applyPotionDamageCalculations(damageSource, baseValue);
        return Math.max(baseValue - livingEntity.getAbsorptionAmount(), 0.0F);
    }

    public static ItemSlot findBestSlot(Object object, Comparator comparator) {
        ElapsedTimer.fM();

        ItemSlot itemSlot = new ItemSlot();

        ArrayList arrayList =
                new ArrayList(
                        remix.util.inventory.InventoryItemHelper.minecraft
                                .player
                                .inventoryContainer
                                .inventorySlots);
        Collections.reverse(arrayList);

        for (java.util.Iterator<?> __recoveryIterator171_42636 = (arrayList).iterator();
                (__recoveryIterator171_42636).hasNext(); ) {
            Slot slot = (Slot) (__recoveryIterator171_42636).next();
            ItemStack itemStack = slot.getStack();
            if (itemStack != null && itemStack.getItem().getClass().equals(object)) {
                int i = comparator.compare(itemStack, itemSlot.itemStack);
                if (i > 0
                        || i == 0
                                && getEnchantmentScore(itemStack)
                                        > getEnchantmentScore(itemSlot.itemStack)) {
                    itemSlot = new ItemSlot(itemStack, slot.slotNumber);
                }
            }
        }

        return itemSlot;
    }

    public static boolean isStackValid(ItemStack itemStack) {
        ElapsedTimer.fM();

        if (itemStack != null && itemStack.getItem() instanceof ItemPotion) {
            ItemPotion item = (ItemPotion) itemStack.getItem();
            if (ItemPotion.isSplash(itemStack.getMetadata()) && item.hasEffect(itemStack)) {
                Potion[] bzT = Potion.potionTypes;
                List list = ItemPotion.getPotionEffects(itemStack);
                int i = 0;
                int j = list.size();

                while (i < j) {
                    Potion potion = bzT[((PotionEffect) list.get(i)).getPotionID()];
                    if (potion.isBadEffect()) {
                        return true;
                    }

                    i++;
                }
            }
        }

        return false;
    }

    public static boolean isBlock(Item item) {
        if (!(item instanceof ItemBlock)) {
            return false;
        }

        Block block = ((ItemBlock) item).getBlock();
        return (block.isFullBlock()
                        || block instanceof BlockIce
                        || block instanceof BlockPackedIce
                        || block instanceof BlockGlass)
                && !(block instanceof BlockNote)
                && !(block instanceof BlockSand)
                && !(block instanceof BlockTNT)
                && !(block instanceof BlockFurnace)
                && !(block instanceof BlockEnchantmentTable)
                && block != Blocks.crafting_table;
    }

    public static int getArmorSlotIndex(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof ItemArmor)) {
            return 0;
        }

        ItemArmor itemArmor = (ItemArmor) item;
        ItemArmorArmorMaterial armorMaterial = itemArmor.getArmorMaterial();
        int i = ArmorTierType.getMaterialWeight(armorMaterial);
        int j = getEnchantmentScore(itemStack);
        return i + j;
    }

    public static ItemSlot findSlotMatching(
            Item item, int i, int j, java.util.function.Predicate predicate) {
        LocalPlayer player = remix.util.inventory.InventoryItemHelper.minecraft.player;
        InventoryPlayer bdI = player.inventory;
        Container bdq = player.inventoryContainer;
        ElapsedTimer.fM();

        ItemSlot itemSlot = new ItemSlot(player.getCurrentEquippedItem(), bdI.currentItem + 36);

        int k = j;

        while (k >= i) {
            ItemStack itemStack = bdq.getSlot(k).getStack();
            if (itemStack != null) {
                Item itemValue = itemStack.getItem();
                if (itemValue == item && predicate.test(itemValue)) {
                    itemSlot = new ItemSlot(itemStack, k);
                }
            }

            k--;
        }

        return itemSlot;
    }

    public static int getArmorSlotForIndex(int i) {
        InventoryPlayer bdI = remix.util.inventory.InventoryItemHelper.minecraft.player.inventory;
        ElapsedTimer.fM();

        int j = 0;

        while (j < 9) {
            ItemStack itemStack = bdI.getStackInSlot(j);
            if (itemStack != null && Item.getIdFromItem(itemStack.getItem()) == i) {
                return j;
            }

            j++;
        }

        return -1;
    }

    public static ItemSlot findBestSlotFromList(
            Object object, Comparator comparator, java.util.List list) {
        ElapsedTimer.fM();

        ItemSlot itemSlot = new ItemSlot();

        for (java.util.Iterator<?> __recoveryIterator171_49372 = (list).iterator();
                (__recoveryIterator171_49372).hasNext(); ) {
            Container container = (Container) (__recoveryIterator171_49372).next();
            Iterator iterator = container.inventorySlots.iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Slot slot = (Slot) iterator.next();
                    ItemStack itemStack = slot.getStack();
                    if (itemStack != null && itemStack.getItem().getClass().equals(object)) {
                        int i = comparator.compare(itemStack, itemSlot.itemStack);
                        if (i > 0
                                || i == 0
                                        && getEnchantmentScore(itemStack)
                                                > getEnchantmentScore(itemSlot.itemStack)) {
                            itemSlot = new ItemSlot(itemStack, slot.slotNumber);
                        }
                    }

                    continue;
                }

                break;
            }
        }

        return itemSlot;
    }

    public static boolean isMainInventoryFull() {
        byte b0 = 1;
        ElapsedTimer.fM();

        Container bdq =
                remix.util.inventory.InventoryItemHelper.minecraft.player.inventoryContainer;
        int i = 9;

        while (i <= 44) {
            Slot slot = bdq.getSlot(i);
            if (!slot.getHasStack()) {
                b0 = 0;
            }

            i++;
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public static double getItemValue(ItemStack itemStack) {
        double d0 = 0.0;
        if (itemStack != null && itemStack.getItem() instanceof ItemBow) {
            d0 = 0.5;
            int effectId = Enchantment.power.effectId;
            if (EnchantmentHelper.getEnchantments(itemStack).containsKey(effectId)) {
                int j = EnchantmentHelper.getEnchantmentLevel(effectId, itemStack);
                d0 += j * 0.5;
            }
        }

        return d0;
    }

    public static ItemSlot findBestWeaponSlot(int i, int j, Entity entity) {
        LocalPlayer player = remix.util.inventory.InventoryItemHelper.minecraft.player;
        ElapsedTimer.fM();

        Container bdq = player.inventoryContainer;

        ItemSlot itemSlot =
                new ItemSlot(player.getCurrentEquippedItem(), player.inventory.currentItem + 36);
        float f = 1.0F;
        int k = j;

        while (k >= i) {
            ItemStack itemStack = bdq.getSlot(k).getStack();
            if (itemStack != null) {
                float f1 = getAttackDamageVsEntity(itemStack, entity);
                if (f1 > f) {
                    f = f1;
                    itemSlot = new ItemSlot(itemStack, k);
                }
            }

            k--;
        }

        return itemSlot;
    }
}
