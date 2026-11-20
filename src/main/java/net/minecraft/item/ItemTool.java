package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import java.util.Set;

public class ItemTool extends Item {
    private float damageVsEntity;

    protected float efficiencyOnProperMaterial = 4.0F;
    private static String s;
    private Set effectiveBlocksTool;
    protected ItemToolMaterial toolMaterial;

    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }

    // region String decoding support

    // endregion

    public boolean hitEntity(
            ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntityValue) {
        itemStack.damageItem(2, livingEntityValue);
        return true;
    }

    // region String decoding support

    // endregion

    protected ItemTool(float f, ItemToolMaterial buEValue, Set set) {
        this.toolMaterial = buEValue;
        this.effectiveBlocksTool = set;
        this.maxStackSize = 1;
        this.setMaxDamage(buEValue.getMaxUses());
        this.efficiencyOnProperMaterial = buEValue.getEfficiencyOnProperMaterial();
        this.damageVsEntity = f + buEValue.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStackValue) {
        return this.toolMaterial.getRepairItem() == itemStackValue.getItem()
                ? true
                : super.getIsRepairable(itemStack, itemStackValue);
    }

    public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(
                SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(net.minecraft.item.Item.itemModifierUUID, s, this.damageVsEntity, 0));
        return multimap;
    }

    static {
        s = "Tool modifier";
    }

    public float getStrVsBlock(ItemStack itemStack, Block block) {
        return this.effectiveBlocksTool.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
    }

    public ItemToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }

    public boolean isFull3D() {
        return true;
    }

    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }

    public boolean onBlockDestroyed(
            ItemStack itemStack,
            World world,
            Block block,
            BlockPos blockPos,
            LivingEntity livingEntity) {
        if (block.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem(1, livingEntity);
        }

        return true;
    }
}
