package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import net.minecraft.item.ItemArmorDispenserBehaviorBehaviorDefaultDispenseItem;

public class ItemArmor extends Item {

    private static IBehaviorDispenseItem dispenserBehavior;
    private static int[] maxDamageArray;
    public int damageReduceAmount;
    public int renderIndex;
    private ItemArmorArmorMaterial material;
    public int armorType;

    public static String[] EMPTY_SLOT_NAMES;

    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStackValue) {
        return this.material.getRepairItem() == itemStackValue.getItem()
                ? true
                : super.getIsRepairable(itemStack, itemStackValue);
    }

    private static UnsupportedOperationException method_1524377ab05941cf1076(
            UnsupportedOperationException unsupportedoperationexception) {
        return unsupportedoperationexception;
    }

    static {
        net.minecraft.item.ItemArmor.maxDamageArray = new int[] {11, 16, 15, 13};
        net.minecraft.item.ItemArmor.EMPTY_SLOT_NAMES =
                new String[] {
                    "minecraft:items/empty_armor_slot_helmet",
                    "minecraft:items/empty_armor_slot_chestplate",
                    "minecraft:items/empty_armor_slot_leggings",
                    "minecraft:items/empty_armor_slot_boots"
                };
        net.minecraft.item.ItemArmor.dispenserBehavior = new ItemArmorDispenserBehaviorBehaviorDefaultDispenseItem();
    }

    public void removeColor(ItemStack itemStack) {
        if (this.material == ItemArmorArmorMaterial.LEATHER) {
            CompoundTag tag = itemStack.getTag();
            if (tag != null) {
                CompoundTag compoundTag = tag.getCompoundTag("display");
                if (compoundTag.hasKey("color")) {
                    compoundTag.removeTag("color");
                }
            }
        }
    }

    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }

    public void setColor(ItemStack itemStack, int i) {
        if (this.material != ItemArmorArmorMaterial.LEATHER) {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }

        CompoundTag tag = itemStack.getTag();
        if (tag == null) {
            tag = new CompoundTag();
            itemStack.setTag(tag);
        }

        CompoundTag compoundTag = tag.getCompoundTag("display");
        if (!tag.hasKey("display", 10)) {
            tag.setTag("display", compoundTag);
        }

        compoundTag.setInteger("color", i);
    }

    public ItemArmorArmorMaterial getArmorMaterial() {
        return this.material;
    }

    public int getColor(ItemStack itemStack) {
        if (this.material != ItemArmorArmorMaterial.LEATHER) {
            return -1;
        }

        CompoundTag tag = itemStack.getTag();
        if (tag != null) {
            CompoundTag compoundTag = tag.getCompoundTag("display");
            if (compoundTag != null && compoundTag.hasKey("color", 3)) {
                return compoundTag.getInteger("color");
            }
        }

        return 10511680;
    }

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        if (i > 0) {
            return 16777215;
        }

        int j = this.getColor(itemStack);
        if (j < 0) {
            j = 16777215;
        }

        return j;
    }

    public boolean hasColor(ItemStack itemStack) {
        return this.material != ItemArmorArmorMaterial.LEATHER
                ? false
                : (!itemStack.hasTagCompound()
                        ? false
                        : (!itemStack.getTag().hasKey("display", 10)
                                ? false
                                : itemStack.getTag().getCompoundTag("display").hasKey("color", 3)));
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        int i = EntityLiving.getArmorPosition(itemStack) - 1;
        ItemStack itemStackValue = playerEntity.getCurrentArmor(i);
        if (itemStackValue == null) {
            playerEntity.setCurrentItemOrArmor(i, itemStack.copy());
            itemStack.stackSize = 0;
        }

        return itemStack;
    }

    static int[] access$000() {
        return net.minecraft.item.ItemArmor.maxDamageArray;
    }

    public ItemArmor(ItemArmorArmorMaterial btuValue, int renderIndexValue, int j) {
        this.material = btuValue;
        this.armorType = j;
        this.renderIndex = renderIndexValue;
        this.damageReduceAmount = btuValue.getDamageReductionAmount(j);
        this.setMaxDamage(btuValue.getDurability(j));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, net.minecraft.item.ItemArmor.dispenserBehavior);
    }
}
