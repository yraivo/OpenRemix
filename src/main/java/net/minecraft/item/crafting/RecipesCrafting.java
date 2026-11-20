package net.minecraft.item.crafting;

import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartzEnumType;
import net.minecraft.block.BlockRedSandstoneEnumType;
import net.minecraft.block.BlockSandEnumType;
import net.minecraft.block.BlockSandStoneEnumType;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneEnumType;
import net.minecraft.block.BlockStoneSlabEnumType;
import net.minecraft.block.BlockStoneSlabNewEnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class RecipesCrafting {

    private static String[] packedTextFragments;

    public void addRecipes(CraftingManager craftingManager) {
        craftingManager.addRecipe(
                new ItemStack(Blocks.chest),
                new Object[] {"###", "# #", "###", '#', Blocks.planks});
        craftingManager.addRecipe(
                new ItemStack(Blocks.trapped_chest),
                new Object[] {"#-", '#', Blocks.chest, '-', Blocks.tripwire_hook});
        craftingManager.addRecipe(
                new ItemStack(Blocks.ender_chest),
                new Object[] {"###", "#E#", "###", '#', Blocks.obsidian, 'E', Items.ender_eye});
        craftingManager.addRecipe(
                new ItemStack(Blocks.furnace),
                new Object[] {"###", "# #", "###", '#', Blocks.cobblestone});
        craftingManager.addRecipe(
                new ItemStack(Blocks.crafting_table),
                new Object[] {"##", "##", '#', Blocks.planks});
        craftingManager.addRecipe(
                new ItemStack(Blocks.sandstone),
                new Object[] {
                    "##",
                    "##",
                    '#',
                    new ItemStack(Blocks.sand, 1, BlockSandEnumType.SAND.getMetadata())
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.red_sandstone),
                new Object[] {
                    "##",
                    "##",
                    '#',
                    new ItemStack(Blocks.sand, 1, BlockSandEnumType.RED_SAND.getMetadata())
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.sandstone, 4, BlockSandStoneEnumType.SMOOTH.getMetadata()),
                new Object[] {
                    "##",
                    "##",
                    '#',
                    new ItemStack(Blocks.sandstone, 1, BlockSandStoneEnumType.DEFAULT.getMetadata())
                });
        craftingManager.addRecipe(
                new ItemStack(
                        Blocks.red_sandstone,
                        4,
                        BlockRedSandstoneEnumType.SMOOTH.getMetaFromState()),
                new Object[] {
                    "##",
                    "##",
                    '#',
                    new ItemStack(
                            Blocks.red_sandstone,
                            1,
                            BlockRedSandstoneEnumType.DEFAULT.getMetaFromState())
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.sandstone, 1, BlockSandStoneEnumType.CHISELED.getMetadata()),
                new Object[] {
                    packedTextFragments[0],
                    packedTextFragments[1],
                    '#',
                    new ItemStack(Blocks.stone_slab, 1, BlockStoneSlabEnumType.SAND.getMetadata())
                });
        craftingManager.addRecipe(
                new ItemStack(
                        Blocks.red_sandstone,
                        1,
                        BlockRedSandstoneEnumType.CHISELED.getMetaFromState()),
                new Object[] {
                    packedTextFragments[2],
                    packedTextFragments[3],
                    '#',
                    new ItemStack(
                            Blocks.stone_slab2,
                            1,
                            BlockStoneSlabNewEnumType.RED_SANDSTONE.getMetadata())
                });
        craftingManager.addRecipe(
                new ItemStack(
                        Blocks.quartz_block, 1, BlockQuartzEnumType.CHISELED.getMetaFromState()),
                new Object[] {
                    packedTextFragments[4],
                    packedTextFragments[5],
                    '#',
                    new ItemStack(Blocks.stone_slab, 1, BlockStoneSlabEnumType.QUARTZ.getMetadata())
                });
        craftingManager.addRecipe(
                new ItemStack(
                        Blocks.quartz_block, 2, BlockQuartzEnumType.LINES_Y.getMetaFromState()),
                new Object[] {
                    packedTextFragments[6],
                    packedTextFragments[7],
                    '#',
                    new ItemStack(
                            Blocks.quartz_block, 1, BlockQuartzEnumType.DEFAULT.getMetaFromState())
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.stonebrick, 4),
                new Object[] {
                    "##",
                    "##",
                    '#',
                    new ItemStack(Blocks.stone, 1, BlockStoneEnumType.STONE.getMetaFromState())
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CHISELED_META),
                new Object[] {
                    packedTextFragments[8],
                    packedTextFragments[9],
                    '#',
                    new ItemStack(
                            Blocks.stone_slab, 1, BlockStoneSlabEnumType.SMOOTHBRICK.getMetadata())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.MOSSY_META),
                new Object[] {Blocks.stonebrick, Blocks.vine});
        craftingManager.addShapelessRecipe(
                new ItemStack(Blocks.mossy_cobblestone, 1),
                new Object[] {Blocks.cobblestone, Blocks.vine});
        craftingManager.addRecipe(
                new ItemStack(Blocks.iron_bars, 16),
                new Object[] {"###", "###", '#', Items.iron_ingot});
        craftingManager.addRecipe(
                new ItemStack(Blocks.glass_pane, 16),
                new Object[] {"###", "###", '#', Blocks.glass});
        craftingManager.addRecipe(
                new ItemStack(Blocks.redstone_lamp, 1),
                new Object[] {" R ", "RGR", " R ", 'R', Items.redstone, 'G', Blocks.glowstone});
        craftingManager.addRecipe(
                new ItemStack(Blocks.beacon, 1),
                new Object[] {
                    "GGG",
                    "GSG",
                    "OOO",
                    'G',
                    Blocks.glass,
                    'S',
                    Items.nether_star,
                    'O',
                    Blocks.obsidian
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.nether_brick, 1),
                new Object[] {"NN", "NN", 'N', Items.netherbrick});
        craftingManager.addRecipe(
                new ItemStack(Blocks.stone, 2, BlockStoneEnumType.DIORITE.getMetaFromState()),
                new Object[] {"CQ", "QC", 'C', Blocks.cobblestone, 'Q', Items.quartz});
        craftingManager.addShapelessRecipe(
                new ItemStack(Blocks.stone, 1, BlockStoneEnumType.GRANITE.getMetaFromState()),
                new Object[] {
                    new ItemStack(Blocks.stone, 1, BlockStoneEnumType.DIORITE.getMetaFromState()),
                    Items.quartz
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Blocks.stone, 2, BlockStoneEnumType.ANDESITE.getMetaFromState()),
                new Object[] {
                    new ItemStack(Blocks.stone, 1, BlockStoneEnumType.DIORITE.getMetaFromState()),
                    Blocks.cobblestone
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.dirt, 4, BlockDirtDirtType.COARSE_DIRT.getMetadata()),
                new Object[] {
                    "DG",
                    "GD",
                    'D',
                    new ItemStack(Blocks.dirt, 1, BlockDirtDirtType.DIRT.getMetadata()),
                    'G',
                    Blocks.gravel
                });
        craftingManager.addRecipe(
                new ItemStack(
                        Blocks.stone, 4, BlockStoneEnumType.DIORITE_SMOOTH.getMetaFromState()),
                new Object[] {
                    "SS",
                    "SS",
                    'S',
                    new ItemStack(Blocks.stone, 1, BlockStoneEnumType.DIORITE.getMetaFromState())
                });
        craftingManager.addRecipe(
                new ItemStack(
                        Blocks.stone, 4, BlockStoneEnumType.GRANITE_SMOOTH.getMetaFromState()),
                new Object[] {
                    "SS",
                    "SS",
                    'S',
                    new ItemStack(Blocks.stone, 1, BlockStoneEnumType.GRANITE.getMetaFromState())
                });
        craftingManager.addRecipe(
                new ItemStack(
                        Blocks.stone, 4, BlockStoneEnumType.ANDESITE_SMOOTH.getMetaFromState()),
                new Object[] {
                    "SS",
                    "SS",
                    'S',
                    new ItemStack(Blocks.stone, 1, BlockStoneEnumType.ANDESITE.getMetaFromState())
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.prismarine, 1, BlockPrismarine.ROUGHMETA),
                new Object[] {"SS", "SS", 'S', Items.prismarine_shard});
        craftingManager.addRecipe(
                new ItemStack(Blocks.prismarine, 1, BlockPrismarine.BRICKSMETA),
                new Object[] {"SSS", "SSS", "SSS", 'S', Items.prismarine_shard});
        craftingManager.addRecipe(
                new ItemStack(Blocks.prismarine, 1, BlockPrismarine.DARKMETA),
                new Object[] {
                    "SSS",
                    "SIS",
                    "SSS",
                    'S',
                    Items.prismarine_shard,
                    'I',
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage())
                });
        craftingManager.addRecipe(
                new ItemStack(Blocks.sea_lantern, 1, 0),
                new Object[] {
                    "SCS", "CCC", "SCS", 'S', Items.prismarine_shard, 'C', Items.prismarine_crystals
                });
    }

    static {
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments = new String[12];
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[0] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[1] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[2] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[3] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[4] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[5] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[6] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[7] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[8] = new String("#");
        net.minecraft.item.crafting.RecipesCrafting.packedTextFragments[9] = new String("#");
    }
}
