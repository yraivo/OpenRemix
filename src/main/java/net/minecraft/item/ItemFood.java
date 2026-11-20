package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemFood extends Item {
    private int potionAmplifier;
    private int healAmount;
    private int potionId;
    private float saturationModifier;

    private float potionEffectProbability;
    private int potionDuration;
    public final int itemUseDuration = 32;
    private static String jT;
    private boolean isWolfsFavoriteMeat;
    private boolean alwaysEdible;

    // region String decoding support

    // endregion

    public float getSaturationModifier(ItemStack itemStack) {
        return this.saturationModifier;
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (playerEntity.canEat(this.alwaysEdible)) {
            playerEntity.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }

        return itemStack;
    }

    public ItemFood(int i, boolean flag) {
        this(i, 0.6F, flag);
    }

    public int getMaxItemUseDuration(ItemStack itemStack) {
        return 32;
    }

    public int getHealAmount(ItemStack itemStack) {
        return this.healAmount;
    }

    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }

    protected void onFoodEaten(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (!world.isRemote
                && this.potionId > 0
                && world.rand.nextFloat() < this.potionEffectProbability) {
            playerEntity.addPotionEffect(
                    new PotionEffect(
                            this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }

    public ItemFood setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }

    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.EAT;
    }

    public ItemFood(
            int healAmountValue, float saturationModifierValue, boolean isWolfsFavoriteMeatValue) {
        this.healAmount = healAmountValue;
        this.isWolfsFavoriteMeat = isWolfsFavoriteMeatValue;
        this.saturationModifier = saturationModifierValue;
        this.setCreativeTab(CreativeTabs.tabFood);
    }

    public ItemStack onItemUseFinish(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        itemStack.stackSize--;
        playerEntity.getFoodStats().addStats(this, itemStack);
        world.playSoundAtEntity(playerEntity, jT, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(itemStack, world, playerEntity);
        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }

    static {
        jT = "random.burp";
    }

    public ItemFood setPotionEffect(int potionIdValue, int j, int k, float awpValue) {
        this.potionId = potionIdValue;
        this.potionDuration = j;
        this.potionAmplifier = k;
        this.potionEffectProbability = awpValue;
        return this;
    }

    // region String decoding support

    // endregion
}
