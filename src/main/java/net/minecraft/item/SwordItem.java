package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

public class SwordItem extends Item {

    private static String kK;
    private float attackDamage;
    private ItemToolMaterial repairMaterial;

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        playerEntity.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }

    public int getItemEnchantability() {
        return this.repairMaterial.getEnchantability();
    }

    public float getDamageVsEntity() {
        return this.repairMaterial.getDamageVsEntity();
    }

    public int getMaxItemUseDuration(ItemStack itemStack) {
        return 72000;
    }

    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStackValue) {
        return this.repairMaterial.getRepairItem() == itemStackValue.getItem()
                ? true
                : super.getIsRepairable(itemStack, itemStackValue);
    }

    public boolean hitEntity(
            ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntityValue) {
        itemStack.damageItem(1, livingEntityValue);
        return true;
    }

    // region String decoding support

    // endregion

    public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(
                SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(net.minecraft.item.Item.itemModifierUUID, kK, this.attackDamage, 0));
        return multimap;
    }

    public SwordItem(ItemToolMaterial buBValue) {
        this.repairMaterial = buBValue;
        this.maxStackSize = 1;
        this.setMaxDamage(buBValue.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.attackDamage = 4.0F + buBValue.getDamageVsEntity();
    }

    public float getStrVsBlock(ItemStack itemStack, Block block) {
        if (block == Blocks.web) {
            return 15.0F;
        }

        Material material = block.getMaterial();
        return material != Material.plants
                        && material != Material.vine
                        && material != Material.coral
                        && material != Material.leaves
                        && material != Material.gourd
                ? 1.0F
                : 1.5F;
    }

    public boolean onBlockDestroyed(
            ItemStack itemStack,
            World world,
            Block block,
            BlockPos blockPos,
            LivingEntity livingEntity) {
        if (block.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem(2, livingEntity);
        }

        return true;
    }

    public String getToolMaterialName() {
        return this.repairMaterial.toString();
    }

    // region String decoding support

    // endregion

    public boolean isFull3D() {
        return true;
    }

    public boolean canHarvestBlock(Block block) {
        return block == Blocks.web;
    }

    // region Static initialization and string tables
    static {
        kK = "Weapon modifier";
    }

    // endregion

    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.BLOCK;
    }
}
