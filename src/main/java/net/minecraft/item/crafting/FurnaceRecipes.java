package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFoodFishType;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Maps;

import java.util.Map;

public class FurnaceRecipes {
    private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
    private Map smeltingList = Maps.newHashMap();
    private Map experienceList = Maps.newHashMap();

    private boolean compareItemStacks(ItemStack itemStack, ItemStack itemStackValue) {
        return itemStackValue.getItem() == itemStack.getItem()
                && (itemStackValue.getItemDamage() == 32767
                        || itemStackValue.getItemDamage() == itemStack.getItemDamage());
    }

    public float getSmeltingExperience(ItemStack itemStack) {
        for (java.util.Iterator<?> typedIterator118 =
                        ((java.util.Iterator)
                                (((java.util.Set)
                                                (((java.util.Set)
                                                        (((java.util.Map) (this.experienceList)).entrySet()))))
                                        .iterator()));
                ((boolean) (((java.util.Iterator) (typedIterator118)).hasNext())); ) {
            java.util.Map.Entry entry =
                    (java.util.Map.Entry)
                            ((java.lang.Object) (((java.util.Iterator) (typedIterator118)).next()));
            if (this.compareItemStacks(itemStack, (net.minecraft.item.ItemStack) entry.getKey())) {
                return (java.lang.Float) entry.getValue();
            }
        }

        return 0.0F;
    }

    public void addSmelting(Item item, ItemStack itemStack, float f) {
        this.addSmeltingRecipe(new ItemStack(item, 1, 32767), itemStack, f);
    }

    public static FurnaceRecipes instance() {
        return net.minecraft.item.crafting.FurnaceRecipes.smeltingBase;
    }

    public void addSmeltingRecipeForBlock(Block block, ItemStack itemStack, float f) {
        this.addSmelting(Item.getItemFromBlock(block), itemStack, f);
    }

    private FurnaceRecipes() {
        this.addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7F);
        this.addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0F);
        this.addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0F);
        this.addSmeltingRecipeForBlock(Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
        this.addSmelting(
                Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35F);
        this.addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35F);
        this.addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35F);
        this.addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35F);
        this.addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35F);
        this.addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1F);
        this.addSmeltingRecipe(
                new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.DEFAULT_META),
                new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CRACKED_META),
                0.1F);
        this.addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3F);
        this.addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35F);
        this.addSmeltingRecipeForBlock(
                Blocks.cactus,
                new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeColorDamage()),
                0.2F);
        this.addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15F);
        this.addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15F);
        this.addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0F);
        this.addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35F);
        this.addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1F);
        this.addSmeltingRecipe(
                new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.15F);

        for (ItemFishFoodFishType itemFishFoodFishType : ItemFishFoodFishType.values()) {
            if (itemFishFoodFishType.getCookable()) {
                this.addSmeltingRecipe(
                        new ItemStack(
                                Items.fish, 1, itemFishFoodFishType.getMetadata()),
                        new ItemStack(
                                Items.cooked_fish,
                                1,
                                itemFishFoodFishType.getMetadata()),
                        0.35F);
            }
        }

        this.addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1F);
        this.addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7F);
        this.addSmeltingRecipeForBlock(
                Blocks.lapis_ore,
                new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()),
                0.2F);
        this.addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2F);
    }

    public void addSmeltingRecipe(
            ItemStack itemStack, ItemStack itemStackValue, float p171_2) {
        this.smeltingList.put(itemStack, itemStackValue);
        this.experienceList.put(itemStackValue, p171_2);
    }

    public ItemStack getSmeltingResult(ItemStack itemStack) {
        for (java.util.Iterator<?> typedIterator95 =
                        ((java.util.Iterator)
                                (((java.util.Set)
                                                (((java.util.Set)
                                                        (((java.util.Map) (this.smeltingList)).entrySet()))))
                                        .iterator()));
                ((boolean) (((java.util.Iterator) (typedIterator95)).hasNext())); ) {
            java.util.Map.Entry entry =
                    (java.util.Map.Entry)
                            ((java.lang.Object) (((java.util.Iterator) (typedIterator95)).next()));
            if (this.compareItemStacks(itemStack, (net.minecraft.item.ItemStack) entry.getKey())) {
                return (net.minecraft.item.ItemStack) entry.getValue();
            }
        }

        return null;
    }

    public Map getSmeltingList() {
        return this.smeltingList;
    }
}
