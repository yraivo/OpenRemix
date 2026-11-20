package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEventAction;
import net.minecraft.init.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.CameraModule;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public final class ItemStack {
    private static String[] packedTextFragments;
    private Block canDestroyCacheBlock = null;

    public int stackSize;
    private boolean canPlaceOnCacheResult;
    private int itemDamage;

    public int animationsToGo;
    private boolean canDestroyCacheResult = false;
    public CompoundTag tag;
    public static DecimalFormat DECIMALFORMAT;
    private Item item;
    private static String legacyItemStackFlowMarker;
    private EntityItemFrame itemFrame;
    private Block canPlaceOnCacheBlock = null;

    public ItemStack useItemRightClick(World world, PlayerEntity playerEntity) {
        return this.getItem().onItemRightClick(this, world, playerEntity);
    }

    public float getStrVsBlock(Block block) {
        return this.getItem().getStrVsBlock(this, block);
    }

    public String getUnlocalizedName() {
        return this.item.getUnlocalizedName(this);
    }

    public boolean getIsItemStackEqual(ItemStack itemStack) {
        return this.isItemStackEqual(itemStack);
    }

    public ItemStack setStackDisplayName(String text) {
        if (this.tag == null) {
            this.tag = new CompoundTag();
        }

        if (!this.tag.hasKey("display", 10)) {
            this.tag.setTag("display", new CompoundTag());
        }

        this.tag.getCompoundTag("display").setString("Name", text);
        return this;
    }

    public boolean isItemEqual(ItemStack itemStack) {
        return itemStack != null
                && this.item == itemStack.item
                && this.itemDamage == itemStack.itemDamage;
    }

    public boolean interactWithEntity(PlayerEntity playerEntity, LivingEntity livingEntity) {
        return this.item.itemInteractionForEntity(this, playerEntity, livingEntity);
    }

    public void addLegacyAttributeModifier(AttributeModifier attributeModifier, int i) {
        if (this.tag == null) {
            this.setTag(new CompoundTag());
        }

        NBTTagList nbtTagList = new NBTTagList();
        if (this.tag.hasKey("AttributeName", 10)) {
            nbtTagList = this.tag.getTagList("AttributeName", 10);
        }

        CompoundTag compoundTag = new CompoundTag();
        compoundTag.setString("AttributeName", attributeModifier.getName());
        compoundTag.setString("Name", attributeModifier.getName());
        compoundTag.setInteger("Amount", i);
        compoundTag.setInteger("Operation", 0);
        compoundTag.setInteger(
                "UUIDLeast", (int) attributeModifier.getID().getLeastSignificantBits());
        compoundTag.setInteger(
                "UUIDMost", (int) attributeModifier.getID().getMostSignificantBits());
        nbtTagList.appendTag(compoundTag);
        this.tag.setTag("AttributeModifiers", nbtTagList);
    }

    public void readFromNBT(CompoundTag compoundTag) {
        boolean legacyFlow = getLegacyItemStackFlowMarker() != null;
        boolean hasNamedId = compoundTag.hasKey("id", 8);
        if (hasNamedId) {
            this.item = Item.getByNameOrId(compoundTag.getString("id"));
        }
        if (!hasNamedId || legacyFlow) {
            this.item = Item.getItemById(compoundTag.getShort("id"));
        }

        this.stackSize = compoundTag.getByte("Count");
        this.itemDamage = compoundTag.getShort("Damage");
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }

        if (compoundTag.hasKey("tag", 10)) {
            this.tag = compoundTag.getCompoundTag("tag");
            if (this.item != null) {
                this.item.updateItemStackNBT(this.tag);
            }
        }
    }

    public ItemStack setLoreLine(String text) {
        this.setLore(new java.lang.String[] {text});
        return this;
    }

    @Override
    public String toString() {
        return this.stackSize
                + packedTextFragments[0]
                + this.item.getUnlocalizedName()
                + packedTextFragments[1]
                + this.itemDamage;
    }

    public static boolean areItemsEqual(
            ItemStack itemStack, ItemStack itemStackValue) {
        return itemStack == null && itemStackValue == null
                || itemStack != null
                        && itemStackValue != null
                        && itemStack.isItemEqual(itemStackValue);
    }

    public void setTagInfo(String text, NBTBase nbtBase) {
        if (this.tag == null) {
            this.setTag(new CompoundTag());
        }

        this.tag.setTag(text, nbtBase);
    }

    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }

    public static boolean areItemStackTagsEqual(ItemStack itemStack, ItemStack itemStackValue) {
        return itemStack == null && itemStackValue == null
                || itemStack != null
                        && itemStackValue != null
                        && (itemStack.tag != null || itemStackValue.tag == null)
                        && (itemStack.tag == null || itemStack.tag.equals(itemStackValue.tag));
    }

    public boolean isItemStackDamageable() {
        return this.item != null
                && this.item.getMaxDamage() > 0
                && (!this.hasTagCompound() || !this.getTag().getBoolean("Unbreakable"));
    }

    public static String getLegacyItemStackFlowMarker() {
        return net.minecraft.item.ItemStack.legacyItemStackFlowMarker;
    }

    public boolean isUnenchantedTool() {
        return this.getItem().isItemTool(this) && !this.isItemEnchanted();
    }

    public void onCrafting(World world, PlayerEntity playerEntity, int i) {
        playerEntity.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], i);
        this.item.onCreated(this, world, playerEntity);
    }

    public boolean hasDisplayName() {
        return this.tag != null
                && this.tag.hasKey("display", 10)
                && this.tag.getCompoundTag("display").hasKey("Name", 8);
    }

    public void onPlayerStoppedUsing(World world, PlayerEntity playerEntity, int i) {
        this.getItem().onPlayerStoppedUsing(this, world, playerEntity, i);
    }

    public ItemStack(Item itemValue) {
        this(itemValue, 1);
    }

    private boolean isItemStackEqual(ItemStack itemStack) {
        return this.stackSize == itemStack.stackSize
                && this.item == itemStack.item
                && this.itemDamage == itemStack.itemDamage
                && (this.tag != null || itemStack.tag == null)
                && (this.tag == null || this.tag.equals(itemStack.tag));
    }

    public boolean attemptDamageItem(int i, Random random) {
        String text = getLegacyItemStackFlowMarker();
        if (!this.isItemStackDamageable()) {
            return false;
        }

        if (i > 0) {
            int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
            int k = 0;
            int l = 0;

            while (j > 0 && l < i) {
                if (EnchantmentDurability.negateDamage(this, j, random)) {
                    k++;
                }

                l++;
                if (text != null) {
                    break;
                }
            }

            i -= k;
            if (i <= 0) {
                return false;
            }
        }

        this.itemDamage += i;
        return this.itemDamage > this.getMaxDamage();
    }

    public EnumRarity getRarity() {
        return this.getItem().getRarity(this);
    }

    public NBTTagList getEnchantmentTagList() {
        return this.tag == null ? null : this.tag.getTagList("ench", 10);
    }

    public boolean onItemUse(
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        boolean flag =
                this.getItem().onItemUse(this, playerEntity, world, blockPos, facing, f, f1, f2);
        if (flag) {
            playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }

        return flag;
    }

    public ChatComponent getChatComponent() {
        ChatComponentText chatComponentText = new ChatComponentText(this.getDisplayName());
        if (this.hasDisplayName()) {
            chatComponentText.getChatStyle().setItalic(true);
        }

        ChatComponent chatComponent =
                new ChatComponentText(packedTextFragments[8])
                        .appendSibling(chatComponentText)
                        .appendText(packedTextFragments[9]);
        if (this.item != null) {
            CompoundTag compoundTag = new CompoundTag();
            this.writeToNBT(compoundTag);
            chatComponent
                    .getChatStyle()
                    .setChatHoverEvent(
                            new HoverEvent(
                                    HoverEventAction.SHOW_ITEM,
                                    new ChatComponentText(compoundTag.toString())));
            chatComponent.getChatStyle().setColor(this.getRarity().rarityColor);
        }

        return chatComponent;
    }

    public void setItemDamage(int itemDamageValue) {
        this.itemDamage = itemDamageValue;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }

    public boolean getHasSubtypes() {
        return this.item.getHasSubtypes();
    }

    public boolean isOnItemFrame() {
        return this.itemFrame != null;
    }

    public ItemStack splitStack(int i) {
        ItemStack itemStack = new ItemStack(this.item, i, this.itemDamage);
        if (this.tag != null) {
            itemStack.tag = (CompoundTag) this.tag.copy();
        }

        this.stackSize -= i;
        return itemStack;
    }

    public ItemStack setLore(String[] astring) {
        String text = getLegacyItemStackFlowMarker();
        if (this.tag == null) {
            this.tag = new CompoundTag();
        }

        if (!this.tag.hasKey("display", 10)) {
            this.tag.setTag("display", new CompoundTag());
        }

        NBTTagList nbtTagList = new NBTTagList();

        for (String textValue : astring) {
            nbtTagList.appendTag(new NBTTagString(textValue));
            if (text != null) {
                break;
            }
        }

        this.tag.getCompoundTag("display").setTag("Lore", nbtTagList);
        return this;
    }

    // region String decoding support

    // endregion

    public ItemStack onItemUseFinish(World world, PlayerEntity playerEntity) {
        return this.getItem().onItemUseFinish(this, world, playerEntity);
    }

    public boolean isStackable() {
        return this.getMaxStackSize() > 1
                && (!this.isItemStackDamageable() || !this.isItemDamaged());
    }

    public int getMaxItemUseDuration() {
        return this.getItem().getMaxItemUseDuration(this);
    }

    public boolean canEditBlocks() {
        return this.getItem().canItemEditBlocks();
    }

    public ItemStack(Block block) {
        this(block, 1);
    }

    public boolean canHarvestBlock(Block block) {
        return this.item.canHarvestBlock(block);
    }

    public ItemStack(Item itemValue, int stackSizeValue, int j) {
        this.canPlaceOnCacheResult = false;
        this.item = itemValue;
        this.stackSize = stackSizeValue;
        this.itemDamage = j;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }

    public void setItemFrame(EntityItemFrame buoValue) {
        this.itemFrame = buoValue;
    }

    public void addEnchantment(Enchantment enchantment, int i) {
        if (this.tag == null) {
            this.setTag(new CompoundTag());
        }

        if (!this.tag.hasKey("ench", 9)) {
            this.tag.setTag("ench", new NBTTagList());
        }

        NBTTagList nbtTagList = this.tag.getTagList("ench", 10);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.setShort("id", (short) enchantment.effectId);
        compoundTag.setInteger("lvl", i);
        nbtTagList.appendTag(compoundTag);
    }

    public void addAttributeModifier(String text, AttributeModifier attributeModifier) {
        if (this.tag == null) {
            this.setTag(new CompoundTag());
        }

        if (!this.tag.hasKey("AttributeModifiers", 9)) {
            this.tag.setTag("AttributeModifiers", new NBTTagList());
        }

        NBTTagList nbtTagList = this.tag.getTagList("AttributeModifiers", 10);
        CompoundTag compoundTag =
                SharedMonsterAttributes.writeAttributeModifierToNBT(attributeModifier);
        compoundTag.setString("AttributeName", text);
        nbtTagList.appendTag(compoundTag);
    }

    // region String decoding support

    // endregion

    public int getMaxDamage() {
        return this.item.getMaxDamage();
    }

    public String getDisplayName() {
        String text = this.getItem().getItemStackDisplayName(this);
        if (this.tag != null && this.tag.hasKey("display", 10)) {
            CompoundTag compoundTag = this.tag.getCompoundTag("display");
            if (compoundTag.hasKey("Name", 8)) {
                text = compoundTag.getString("Name");
            }
        }

        return text;
    }

    public boolean canPlaceOn(Block bupValue) {
        String text = getLegacyItemStackFlowMarker();
        if (bupValue == this.canPlaceOnCacheBlock) {
            return this.canPlaceOnCacheResult;
        }

        this.canPlaceOnCacheBlock = bupValue;
        if (this.hasTagCompound() && this.tag.hasKey("CanPlaceOn", 9)) {
            NBTTagList nbtTagList = this.tag.getTagList("CanPlaceOn", 8);
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                Block block = Block.getBlockFromName(nbtTagList.getStringTagAt(i));
                if (block == bupValue) {
                    this.canPlaceOnCacheResult = true;
                    return true;
                }

                i++;
                if (text != null) {
                    break;
                }
            }
        }

        this.canPlaceOnCacheResult = false;
        return false;
    }

    public void setItem(Item itemValue) {
        this.item = itemValue;
    }

    public CompoundTag writeToNBT(CompoundTag compoundTag) {
        ResourceLocation nameForObject =
                (ResourceLocation) Item.itemRegistry.getNameForObject(this.item);
        compoundTag.setString(
                "id", nameForObject == null ? "minecraft:air" : nameForObject.toString());
        compoundTag.setByte("Count", (byte) this.stackSize);
        compoundTag.setShort("Damage", (short) this.itemDamage);
        if (this.tag != null) {
            compoundTag.setTag("tag", this.tag);
        }

        return compoundTag;
    }

    public static ItemStack loadItemStackFromNBT(CompoundTag compoundTag) {
        ItemStack itemStack = new ItemStack();
        itemStack.readFromNBT(compoundTag);
        return itemStack.getItem() != null ? itemStack : null;
    }

    public int getRepairCost() {
        return this.hasTagCompound() && this.tag.hasKey("RepairCost", 3)
                ? this.tag.getInteger("RepairCost")
                : 0;
    }

    public int getItemDamage() {
        return this.itemDamage;
    }

    public void setRepairCost(int i) {
        if (!this.hasTagCompound()) {
            this.tag = new CompoundTag();
        }

        this.tag.setInteger("RepairCost", i);
    }

    public List<String> getTooltip(PlayerEntity player, boolean advanced) {
        boolean legacyTooltipFlow = getLegacyItemStackFlowMarker() != null;
        List<String> lines = Lists.newArrayList();
        lines.add(this.formatTooltipName(advanced, legacyTooltipFlow));

        int hideFlags = this.hasTagCompound() && this.tag.hasKey("HideFlags", 99)
                ? this.tag.getInteger("HideFlags") : 0;
        if ((hideFlags & 32) == 0) {
            this.item.addInformation(this, player, lines, advanced);
        }
        if (this.hasTagCompound()) {
            if ((hideFlags & 1) == 0) {
                this.appendEnchantmentTooltip(lines, legacyTooltipFlow);
            }
            this.appendDisplayTooltip(lines, advanced, legacyTooltipFlow);
        }

        Multimap<String, AttributeModifier> modifiers = this.getAttributeModifiers();
        if (!modifiers.isEmpty() && (hideFlags & 2) == 0) {
            this.appendAttributeTooltip(lines, modifiers, legacyTooltipFlow);
        }
        if (this.hasTagCompound() && this.getTag().getBoolean("Unbreakable") && (hideFlags & 4) == 0) {
            lines.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
        }
        if (this.hasTagCompound()) {
            if ((hideFlags & 8) == 0) {
                this.appendBlockRestriction(lines, "CanDestroy", "item.canBreak", legacyTooltipFlow);
            }
            if ((hideFlags & 16) == 0) {
                this.appendBlockRestriction(lines, "CanPlaceOn", "item.canPlace", legacyTooltipFlow);
            }
        }

        if (advanced) {
            this.appendAdvancedTooltip(lines);
        }
        return lines;
    }

    private String formatTooltipName(boolean advanced, boolean legacyTooltipFlow) {
        String name = this.getDisplayName();
        if (this.hasDisplayName()) {
            name = EnumChatFormatting.ITALIC + name;
        }
        name += EnumChatFormatting.RESET;
        if (advanced) {
            String suffix = name.isEmpty() ? "" : ")";
            if (!name.isEmpty()) {
                name += " (";
            }
            int itemId = Item.getIdFromItem(this.item);
            boolean hasSubtypes = this.getHasSubtypes();
            if (hasSubtypes) {
                name += String.format("#%04d/%d%s", itemId, this.itemDamage, suffix);
            }
            if (!hasSubtypes || legacyTooltipFlow) {
                name += String.format("#%04d%s", itemId, suffix);
            }
        }
        if ((!advanced || legacyTooltipFlow) && !this.hasDisplayName() && this.item == Items.filled_map) {
            name += " #" + this.itemDamage;
        }
        return name;
    }

    private void appendEnchantmentTooltip(List<String> lines, boolean legacyTooltipFlow) {
        NBTTagList enchantments = this.getEnchantmentTagList();
        if (enchantments == null) {
            return;
        }
        for (int index = 0; index < enchantments.tagCount(); index++) {
            CompoundTag tag = enchantments.getCompoundTagAt(index);
            short id = tag.getShort("id");
            short level = tag.getShort("lvl");
            Enchantment enchantment = Enchantment.getEnchantmentById(id);
            if (enchantment != null) {
                lines.add(enchantment.getTranslatedName(level));
            }
            if (legacyTooltipFlow) {
                break;
            }
        }
    }

    private void appendDisplayTooltip(List<String> lines, boolean advanced, boolean legacyTooltipFlow) {
        if (!this.tag.hasKey("display", 10)) {
            return;
        }
        CompoundTag display = this.tag.getCompoundTag("display");
        if (display.hasKey("color", 3)) {
            if (advanced) {
                lines.add("Color: #" + Integer.toHexString(display.getInteger("color")).toUpperCase());
            }
            if (!advanced || legacyTooltipFlow) {
                lines.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
            }
        }
        if (display.getTagId("Lore") == 9) {
            NBTTagList lore = display.getTagList("Lore", 8);
            for (int index = 0; index < lore.tagCount(); index++) {
                lines.add(EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.ITALIC
                        + lore.getStringTagAt(index));
                if (legacyTooltipFlow) {
                    break;
                }
            }
        }
    }

    private void appendAttributeTooltip(List<String> lines, Multimap<String, AttributeModifier> modifiers,
                                        boolean legacyTooltipFlow) {
        lines.add("");
        for (Entry<String, AttributeModifier> entry : modifiers.entries()) {
            AttributeModifier modifier = entry.getValue();
            double amount = modifier.getAmount();
            if (modifier.getID() == Item.itemModifierUUID) {
                amount += EnchantmentHelper.getEnchantmentDamageBonus(this, CreatureAttribute.UNDEFINED);
            }
            int operation = modifier.getOperation();
            double displayAmount = operation == 1 || operation == 2 || legacyTooltipFlow ? amount * 100.0 : amount;
            if (amount > 0.0) {
                lines.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted(
                        "attribute.modifier.plus." + operation, DECIMALFORMAT.format(displayAmount),
                        StatCollector.translateToLocal("attribute.name." + entry.getKey())));
            } else if (amount < 0.0) {
                lines.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
                        "attribute.modifier.take." + operation, DECIMALFORMAT.format(-displayAmount),
                        StatCollector.translateToLocal("attribute.name." + entry.getKey())));
            }
            if (legacyTooltipFlow) {
                break;
            }
        }
    }

    private void appendBlockRestriction(List<String> lines, String tagName, String heading, boolean legacyTooltipFlow) {
        if (!this.tag.hasKey(tagName, 9)) {
            return;
        }
        NBTTagList blocks = this.tag.getTagList(tagName, 8);
        if (blocks.tagCount() == 0) {
            return;
        }
        lines.add("");
        lines.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal(heading));
        for (int index = 0; index < blocks.tagCount(); index++) {
            Block block = Block.getBlockFromName(blocks.getStringTagAt(index));
            if (block != null) {
                lines.add(EnumChatFormatting.DARK_GRAY + block.getLocalizedName());
            }
            if (block == null || legacyTooltipFlow) {
                lines.add(EnumChatFormatting.DARK_GRAY + "missingno");
            }
            if (legacyTooltipFlow) {
                break;
            }
        }
    }

    private void appendAdvancedTooltip(List<String> lines) {
        if (this.isItemDamaged()) {
            lines.add("Durability: " + (this.getMaxDamage() - this.getMetadata()) + " / " + this.getMaxDamage());
        }
        lines.add(EnumChatFormatting.DARK_GRAY + Item.itemRegistry.getNameForObject(this.item).toString());
        if (this.hasTagCompound()) {
            lines.add(EnumChatFormatting.DARK_GRAY + "NBT: " + this.getTag().getKeySet().size() + " tag(s)");
        }

        // Remix adds the full NBT only to advanced tooltips when Camera's viewer is enabled.
        RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        if (this.hasTagCompound() && camera.isEnabled() && camera.nbtViewerSetting.asBoolean()) {
            Splitter.fixedLength(50).split(this.getTag().toString()).forEach(lines::add);
        }
    }

    public void hitEntity(LivingEntity livingEntity, PlayerEntity playerEntity) {
        boolean flag = this.item.hitEntity(this, livingEntity, playerEntity);
        if (flag) {
            playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
    }

    public CompoundTag getTag() {
        return this.tag;
    }

    public void setTag(CompoundTag tagValue) {
        this.tag = tagValue;
    }

    public ItemStack(Block block, int p171_1) {
        this(block, p171_1, 0);
    }

    public ItemStack(Block block, int i, int j) {
        this(Item.getItemFromBlock(block), i, j);
    }

    public void clearCustomName() {
        if (this.tag != null && this.tag.hasKey("display", 10)) {
            CompoundTag compoundTag = this.tag.getCompoundTag("display");
            compoundTag.removeTag("Name");
            if (compoundTag.hasNoTags()) {
                this.tag.removeTag("display");
                if (this.tag.hasNoTags()) {
                    this.setTag(null);
                }
            }
        }
    }

    public boolean isItemEnchanted() {
        return this.tag != null && this.tag.hasKey("ench", 9);
    }

    public EnumAction getItemUseAction() {
        return this.getItem().getItemUseAction(this);
    }

    public void updateAnimation(World world, Entity entity, int i, boolean flag) {
        if (this.animationsToGo > 0) {
            this.animationsToGo--;
        }

        this.item.onUpdate(this, world, entity, i, flag);
    }

    public Item getItem() {
        return this.item;
    }

    public boolean canDestroy(Block blAValue) {
        String text = getLegacyItemStackFlowMarker();
        if (blAValue == this.canDestroyCacheBlock) {
            return this.canDestroyCacheResult;
        }

        this.canDestroyCacheBlock = blAValue;
        if (this.hasTagCompound() && this.tag.hasKey("CanDestroy", 9)) {
            NBTTagList nbtTagList = this.tag.getTagList("CanDestroy", 8);
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                Block block = Block.getBlockFromName(nbtTagList.getStringTagAt(i));
                if (block == blAValue) {
                    this.canDestroyCacheResult = true;
                    return true;
                }

                i++;
                if (text != null) {
                    break;
                }
            }
        }

        this.canDestroyCacheResult = false;
        return false;
    }

    public static boolean areItemStacksEqual(
            ItemStack itemStack, ItemStack itemStackValue) {
        return itemStack == null && itemStackValue == null
                || itemStack != null
                        && itemStackValue != null
                        && itemStack.isItemStackEqual(itemStackValue);
    }

    public boolean isItemDamaged() {
        return this.isItemStackDamageable() && this.itemDamage > 0;
    }

    public void onBlockDestroyed(
            World world, Block block, BlockPos blockPos, PlayerEntity playerEntity) {
        boolean flag = this.item.onBlockDestroyed(this, world, block, blockPos, playerEntity);
        if (flag) {
            playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
        }
    }

    public static void setLegacyItemStackFlowMarker(String text) {
        net.minecraft.item.ItemStack.legacyItemStackFlowMarker = text;
    }

    public ItemStack(Item itemValue, int i) {
        this(itemValue, i, 0);
    }

    // region Static initialization and string tables
    static {
        net.minecraft.item.ItemStack.packedTextFragments = new String[11];
        net.minecraft.item.ItemStack.packedTextFragments[0] = new String("x");
        net.minecraft.item.ItemStack.packedTextFragments[1] = new String("@");
        net.minecraft.item.ItemStack.packedTextFragments[2] = new String("");
        net.minecraft.item.ItemStack.packedTextFragments[3] = new String(")");
        net.minecraft.item.ItemStack.packedTextFragments[4] = new String("");
        net.minecraft.item.ItemStack.packedTextFragments[5] = new String("");
        net.minecraft.item.ItemStack.packedTextFragments[6] = new String("");
        net.minecraft.item.ItemStack.packedTextFragments[7] = new String("");
        net.minecraft.item.ItemStack.packedTextFragments[8] = new String("[");
        net.minecraft.item.ItemStack.packedTextFragments[9] = new String("]");
        setLegacyItemStackFlowMarker(null);
        net.minecraft.item.ItemStack.DECIMALFORMAT = new DecimalFormat("#.###");
    }

    // endregion

    private ItemStack() {}

    public static ItemStack copyItemStack(ItemStack itemStack) {
        return itemStack == null ? null : itemStack.copy();
    }

    public CompoundTag getSubCompound(String text, boolean flag) {
        if (this.tag != null && this.tag.hasKey(text, 10)) {
            return this.tag.getCompoundTag(text);
        } else if (flag) {
            CompoundTag compoundTag = new CompoundTag();
            this.setTagInfo(text, compoundTag);
            return compoundTag;
        } else {
            return null;
        }
    }

    public EntityItemFrame getItemFrame() {
        return this.itemFrame;
    }

    public boolean hasEffect() {
        return this.getItem().hasEffect(this);
    }

    public boolean hasTagCompound() {
        return this.tag != null;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers() {
        String text = getLegacyItemStackFlowMarker();
        if (this.hasTagCompound() && this.tag.hasKey("AttributeModifiers", 9)) {
            HashMultimap hashmultimap = HashMultimap.create();
            NBTTagList nbtTagList = this.tag.getTagList("AttributeModifiers", 10);
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                CompoundTag compoundTag = nbtTagList.getCompoundTagAt(i);
                AttributeModifier attributeModifier =
                        SharedMonsterAttributes.readAttributeModifierFromNBT(compoundTag);
                if (attributeModifier != null) {
                    long j = attributeModifier.getID().getLeastSignificantBits();

                    if (j != 0L) {
                        j = attributeModifier.getID().getMostSignificantBits();

                        if (j != 0L) {
                            hashmultimap.put(
                                    compoundTag.getString("AttributeName"), attributeModifier);
                        }
                    }
                }

                i++;
                if (text != null) {
                    break;
                }
            }

            if (text == null) {
                return hashmultimap;
            }
        }

        return this.getItem().getItemAttributeModifiers();
    }

    public int getMetadata() {
        return this.itemDamage;
    }

    public void damageItem(int i, LivingEntity livingEntity) {
        if ((!(livingEntity instanceof PlayerEntity)
                        || !((PlayerEntity) livingEntity).capabilities.isCreativeMode)
                && this.isItemStackDamageable()
                && this.attemptDamageItem(i, livingEntity.getRNG())) {
            livingEntity.renderBrokenItemStack(this);
            this.stackSize--;
            if (livingEntity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) livingEntity;
                playerEntity.triggerAchievement(
                        StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
                if (this.stackSize == 0 && this.getItem() instanceof ItemBow) {
                    playerEntity.destroyCurrentEquippedItem();
                }
            }

            if (this.stackSize < 0) {
                this.stackSize = 0;
            }

            this.itemDamage = 0;
        }
    }

    public ItemStack copy() {
        ItemStack itemStack = new ItemStack(this.item, this.stackSize, this.itemDamage);
        if (this.tag != null) {
            itemStack.tag = (CompoundTag) this.tag.copy();
        }

        return itemStack;
    }
}
