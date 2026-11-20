package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.BlockStoneEnumType;
import net.minecraft.block.BlockStoneSlabEnumType;
import net.minecraft.block.BlockStoneSlabNewEnumType;
import net.minecraft.block.BlockWallEnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CraftingManager {

    private static CraftingManager instance;
    private static String[] packedTextFragments;

    private final List recipes = Lists.newArrayList();

    private CraftingManager() {
        new RecipesTools().addRecipes(this);
        new RecipesWeapons().addRecipes(this);
        new RecipesIngots().addRecipes(this);
        new RecipesFood().addRecipes(this);
        new RecipesCrafting().addRecipes(this);
        new RecipesArmor().addRecipes(this);
        new RecipesDyes().addRecipes(this);
        this.recipes.add(new RecipesArmorDyes());
        this.recipes.add(new RecipeBookCloning());
        this.recipes.add(new RecipesMapCloning());
        this.recipes.add(new RecipesMapExtending());
        this.recipes.add(new RecipeFireworks());
        this.recipes.add(new RecipeRepairItem());
        new RecipesBanners().addRecipes(this);
        this.addRecipe(new ItemStack(Items.paper, 3), "###", '#', Items.reeds);
        this.addShapelessRecipe(
                new ItemStack(Items.book, 1), Items.paper, Items.paper, Items.paper, Items.leather);
        this.addShapelessRecipe(
                new ItemStack(Items.writable_book, 1),
                Items.book,
                new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()),
                Items.feather);
        this.addRecipe(
                new ItemStack(Blocks.oak_fence, 3),
                "W#W",
                "W#W",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.OAK.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.birch_fence, 3),
                "W#W",
                "W#W",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.BIRCH.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.spruce_fence, 3),
                "W#W",
                "W#W",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.SPRUCE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.jungle_fence, 3),
                "W#W",
                "W#W",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.JUNGLE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.acacia_fence, 3),
                "W#W",
                "W#W",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, 4 + BlockPlanksEnumType.ACACIA.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(Blocks.dark_oak_fence, 3),
                "W#W",
                "W#W",
                '#',
                Items.stick,
                'W',
                new ItemStack(
                        Blocks.planks, 1, 4 + BlockPlanksEnumType.DARK_OAK.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(Blocks.cobblestone_wall, 6, BlockWallEnumType.NORMAL.getMetadata()),
                "###",
                "###",
                '#',
                Blocks.cobblestone);
        this.addRecipe(
                new ItemStack(Blocks.cobblestone_wall, 6, BlockWallEnumType.MOSSY.getMetadata()),
                "###",
                "###",
                '#',
                Blocks.mossy_cobblestone);
        this.addRecipe(
                new ItemStack(Blocks.nether_brick_fence, 6),
                "###",
                "###",
                '#',
                Blocks.nether_brick);
        this.addRecipe(
                new ItemStack(Blocks.oak_fence_gate, 1),
                "#W#",
                "#W#",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.OAK.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.birch_fence_gate, 1),
                "#W#",
                "#W#",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.BIRCH.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.spruce_fence_gate, 1),
                "#W#",
                "#W#",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.SPRUCE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.jungle_fence_gate, 1),
                "#W#",
                "#W#",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.JUNGLE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.acacia_fence_gate, 1),
                "#W#",
                "#W#",
                '#',
                Items.stick,
                'W',
                new ItemStack(Blocks.planks, 1, 4 + BlockPlanksEnumType.ACACIA.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(Blocks.dark_oak_fence_gate, 1),
                "#W#",
                "#W#",
                '#',
                Items.stick,
                'W',
                new ItemStack(
                        Blocks.planks, 1, 4 + BlockPlanksEnumType.DARK_OAK.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(Blocks.jukebox, 1),
                "###",
                "#X#",
                "###",
                '#',
                Blocks.planks,
                'X',
                Items.diamond);
        this.addRecipe(
                new ItemStack(Items.lead, 2),
                "~~ ",
                "~O ",
                "  ~",
                '~',
                Items.string,
                'O',
                Items.slime_ball);
        this.addRecipe(
                new ItemStack(Blocks.noteblock, 1),
                "###",
                "#X#",
                "###",
                '#',
                Blocks.planks,
                'X',
                Items.redstone);
        this.addRecipe(
                new ItemStack(Blocks.bookshelf, 1),
                "###",
                "XXX",
                "###",
                '#',
                Blocks.planks,
                'X',
                Items.book);
        this.addRecipe(new ItemStack(Blocks.snow, 1), "##", "##", '#', Items.snowball);
        this.addRecipe(new ItemStack(Blocks.snow_layer, 6), "###", '#', Blocks.snow);
        this.addRecipe(new ItemStack(Blocks.clay, 1), "##", "##", '#', Items.clay_ball);
        this.addRecipe(new ItemStack(Blocks.brick_block, 1), "##", "##", '#', Items.brick);
        this.addRecipe(new ItemStack(Blocks.glowstone, 1), "##", "##", '#', Items.glowstone_dust);
        this.addRecipe(new ItemStack(Blocks.quartz_block, 1), "##", "##", '#', Items.quartz);
        this.addRecipe(new ItemStack(Blocks.wool, 1), "##", "##", '#', Items.string);
        this.addRecipe(
                new ItemStack(Blocks.tnt, 1),
                "X#X",
                "#X#",
                "X#X",
                'X',
                Items.gunpowder,
                '#',
                Blocks.sand);
        this.addRecipe(
                new ItemStack(
                        Blocks.stone_slab, 6, BlockStoneSlabEnumType.COBBLESTONE.getMetadata()),
                "###",
                '#',
                Blocks.cobblestone);
        this.addRecipe(
                new ItemStack(Blocks.stone_slab, 6, BlockStoneSlabEnumType.STONE.getMetadata()),
                "###",
                '#',
                new ItemStack(Blocks.stone, BlockStoneEnumType.STONE.getMetaFromState()));
        this.addRecipe(
                new ItemStack(Blocks.stone_slab, 6, BlockStoneSlabEnumType.SAND.getMetadata()),
                "###",
                '#',
                Blocks.sandstone);
        this.addRecipe(
                new ItemStack(Blocks.stone_slab, 6, BlockStoneSlabEnumType.BRICK.getMetadata()),
                "###",
                '#',
                Blocks.brick_block);
        this.addRecipe(
                new ItemStack(
                        Blocks.stone_slab, 6, BlockStoneSlabEnumType.SMOOTHBRICK.getMetadata()),
                "###",
                '#',
                Blocks.stonebrick);
        this.addRecipe(
                new ItemStack(
                        Blocks.stone_slab, 6, BlockStoneSlabEnumType.NETHERBRICK.getMetadata()),
                "###",
                '#',
                Blocks.nether_brick);
        this.addRecipe(
                new ItemStack(Blocks.stone_slab, 6, BlockStoneSlabEnumType.QUARTZ.getMetadata()),
                "###",
                '#',
                Blocks.quartz_block);
        this.addRecipe(
                new ItemStack(
                        Blocks.stone_slab2,
                        6,
                        BlockStoneSlabNewEnumType.RED_SANDSTONE.getMetadata()),
                "###",
                '#',
                Blocks.red_sandstone);
        this.addRecipe(
                new ItemStack(Blocks.wooden_slab, 6, 0),
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.OAK.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.wooden_slab, 6, BlockPlanksEnumType.BIRCH.getMetadata()),
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.BIRCH.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.wooden_slab, 6, BlockPlanksEnumType.SPRUCE.getMetadata()),
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.SPRUCE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.wooden_slab, 6, BlockPlanksEnumType.JUNGLE.getMetadata()),
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.JUNGLE.getMetadata()));
        this.addRecipe(
                new ItemStack(
                        Blocks.wooden_slab, 6, 4 + BlockPlanksEnumType.ACACIA.getMetadata() - 4),
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, 4 + BlockPlanksEnumType.ACACIA.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(
                        Blocks.wooden_slab, 6, 4 + BlockPlanksEnumType.DARK_OAK.getMetadata() - 4),
                "###",
                '#',
                new ItemStack(
                        Blocks.planks, 1, 4 + BlockPlanksEnumType.DARK_OAK.getMetadata() - 4));
        this.addRecipe(new ItemStack(Blocks.ladder, 3), "# #", "###", "# #", '#', Items.stick);
        this.addRecipe(
                new ItemStack(Items.oak_door, 3),
                "##",
                "##",
                "##",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.OAK.getMetadata()));
        this.addRecipe(
                new ItemStack(Items.spruce_door, 3),
                "##",
                "##",
                "##",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.SPRUCE.getMetadata()));
        this.addRecipe(
                new ItemStack(Items.birch_door, 3),
                "##",
                "##",
                "##",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.BIRCH.getMetadata()));
        this.addRecipe(
                new ItemStack(Items.jungle_door, 3),
                "##",
                "##",
                "##",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.JUNGLE.getMetadata()));
        this.addRecipe(
                new ItemStack(Items.acacia_door, 3),
                "##",
                "##",
                "##",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.ACACIA.getMetadata()));
        this.addRecipe(
                new ItemStack(Items.dark_oak_door, 3),
                "##",
                "##",
                "##",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.DARK_OAK.getMetadata()));
        this.addRecipe(new ItemStack(Blocks.trapdoor, 2), "###", "###", '#', Blocks.planks);
        this.addRecipe(new ItemStack(Items.iron_door, 3), "##", "##", "##", '#', Items.iron_ingot);
        this.addRecipe(new ItemStack(Blocks.iron_trapdoor, 1), "##", "##", '#', Items.iron_ingot);
        this.addRecipe(
                new ItemStack(Items.sign, 3),
                "###",
                "###",
                " X ",
                '#',
                Blocks.planks,
                'X',
                Items.stick);
        this.addRecipe(
                new ItemStack(Items.cake, 1),
                "AAA",
                "BEB",
                "CCC",
                'A',
                Items.milk_bucket,
                'B',
                Items.sugar,
                'C',
                Items.wheat,
                'E',
                Items.egg);
        this.addRecipe(new ItemStack(Items.sugar, 1), packedTextFragments[0], '#', Items.reeds);
        this.addRecipe(
                new ItemStack(Blocks.planks, 4, BlockPlanksEnumType.OAK.getMetadata()),
                packedTextFragments[1],
                '#',
                new ItemStack(Blocks.log, 1, BlockPlanksEnumType.OAK.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.planks, 4, BlockPlanksEnumType.SPRUCE.getMetadata()),
                packedTextFragments[2],
                '#',
                new ItemStack(Blocks.log, 1, BlockPlanksEnumType.SPRUCE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.planks, 4, BlockPlanksEnumType.BIRCH.getMetadata()),
                packedTextFragments[3],
                '#',
                new ItemStack(Blocks.log, 1, BlockPlanksEnumType.BIRCH.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.planks, 4, BlockPlanksEnumType.JUNGLE.getMetadata()),
                packedTextFragments[4],
                '#',
                new ItemStack(Blocks.log, 1, BlockPlanksEnumType.JUNGLE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.planks, 4, 4 + BlockPlanksEnumType.ACACIA.getMetadata() - 4),
                packedTextFragments[5],
                '#',
                new ItemStack(Blocks.log2, 1, BlockPlanksEnumType.ACACIA.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(Blocks.planks, 4, 4 + BlockPlanksEnumType.DARK_OAK.getMetadata() - 4),
                packedTextFragments[6],
                '#',
                new ItemStack(Blocks.log2, 1, BlockPlanksEnumType.DARK_OAK.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(Items.stick, 4),
                packedTextFragments[7],
                packedTextFragments[8],
                '#',
                Blocks.planks);
        this.addRecipe(
                new ItemStack(Blocks.torch, 4),
                packedTextFragments[9],
                packedTextFragments[10],
                'X',
                Items.coal,
                '#',
                Items.stick);
        this.addRecipe(
                new ItemStack(Blocks.torch, 4),
                packedTextFragments[11],
                packedTextFragments[12],
                'X',
                new ItemStack(Items.coal, 1, 1),
                '#',
                Items.stick);
        this.addRecipe(new ItemStack(Items.bowl, 4), "# #", " # ", '#', Blocks.planks);
        this.addRecipe(new ItemStack(Items.glass_bottle, 3), "# #", " # ", '#', Blocks.glass);
        this.addRecipe(
                new ItemStack(Blocks.rail, 16),
                "X X",
                "X#X",
                "X X",
                'X',
                Items.iron_ingot,
                '#',
                Items.stick);
        this.addRecipe(
                new ItemStack(Blocks.golden_rail, 6),
                "X X",
                "X#X",
                "XRX",
                'X',
                Items.gold_ingot,
                'R',
                Items.redstone,
                '#',
                Items.stick);
        this.addRecipe(
                new ItemStack(Blocks.activator_rail, 6),
                "XSX",
                "X#X",
                "XSX",
                'X',
                Items.iron_ingot,
                '#',
                Blocks.redstone_torch,
                'S',
                Items.stick);
        this.addRecipe(
                new ItemStack(Blocks.detector_rail, 6),
                "X X",
                "X#X",
                "XRX",
                'X',
                Items.iron_ingot,
                'R',
                Items.redstone,
                '#',
                Blocks.stone_pressure_plate);
        this.addRecipe(new ItemStack(Items.minecart, 1), "# #", "###", '#', Items.iron_ingot);
        this.addRecipe(
                new ItemStack(Items.cauldron, 1), "# #", "# #", "###", '#', Items.iron_ingot);
        this.addRecipe(
                new ItemStack(Items.brewing_stand, 1),
                " B ",
                "###",
                '#',
                Blocks.cobblestone,
                'B',
                Items.blaze_rod);
        this.addRecipe(
                new ItemStack(Blocks.lit_pumpkin, 1),
                packedTextFragments[13],
                packedTextFragments[14],
                'A',
                Blocks.pumpkin,
                'B',
                Blocks.torch);
        this.addRecipe(
                new ItemStack(Items.chest_minecart, 1),
                packedTextFragments[15],
                packedTextFragments[16],
                'A',
                Blocks.chest,
                'B',
                Items.minecart);
        this.addRecipe(
                new ItemStack(Items.furnace_minecart, 1),
                packedTextFragments[17],
                packedTextFragments[18],
                'A',
                Blocks.furnace,
                'B',
                Items.minecart);
        this.addRecipe(
                new ItemStack(Items.tnt_minecart, 1),
                packedTextFragments[19],
                packedTextFragments[20],
                'A',
                Blocks.tnt,
                'B',
                Items.minecart);
        this.addRecipe(
                new ItemStack(Items.hopper_minecart, 1),
                packedTextFragments[21],
                packedTextFragments[22],
                'A',
                Blocks.hopper,
                'B',
                Items.minecart);
        this.addRecipe(new ItemStack(Items.boat, 1), "# #", "###", '#', Blocks.planks);
        this.addRecipe(new ItemStack(Items.bucket, 1), "# #", " # ", '#', Items.iron_ingot);
        this.addRecipe(new ItemStack(Items.flower_pot, 1), "# #", " # ", '#', Items.brick);
        this.addShapelessRecipe(
                new ItemStack(Items.flint_and_steel, 1),
                new ItemStack(Items.iron_ingot, 1),
                new ItemStack(Items.flint, 1));
        this.addRecipe(new ItemStack(Items.bread, 1), "###", '#', Items.wheat);
        this.addRecipe(
                new ItemStack(Blocks.oak_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.OAK.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.birch_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.BIRCH.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.spruce_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.SPRUCE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.jungle_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, BlockPlanksEnumType.JUNGLE.getMetadata()));
        this.addRecipe(
                new ItemStack(Blocks.acacia_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                new ItemStack(Blocks.planks, 1, 4 + BlockPlanksEnumType.ACACIA.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(Blocks.dark_oak_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                new ItemStack(
                        Blocks.planks, 1, 4 + BlockPlanksEnumType.DARK_OAK.getMetadata() - 4));
        this.addRecipe(
                new ItemStack(Items.fishing_rod, 1),
                "  #",
                " #X",
                "# X",
                '#',
                Items.stick,
                'X',
                Items.string);
        this.addRecipe(
                        new ItemStack(Items.carrot_on_a_stick, 1),
                        "# ",
                        " X",
                        '#',
                        Items.fishing_rod,
                        'X',
                        Items.carrot)
                .OV();
        this.addRecipe(
                new ItemStack(Blocks.stone_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                Blocks.cobblestone);
        this.addRecipe(
                new ItemStack(Blocks.brick_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                Blocks.brick_block);
        this.addRecipe(
                new ItemStack(Blocks.stone_brick_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                Blocks.stonebrick);
        this.addRecipe(
                new ItemStack(Blocks.nether_brick_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                Blocks.nether_brick);
        this.addRecipe(
                new ItemStack(Blocks.sandstone_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                Blocks.sandstone);
        this.addRecipe(
                new ItemStack(Blocks.red_sandstone_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                Blocks.red_sandstone);
        this.addRecipe(
                new ItemStack(Blocks.quartz_stairs, 4),
                "#  ",
                "## ",
                "###",
                '#',
                Blocks.quartz_block);
        this.addRecipe(
                new ItemStack(Items.painting, 1),
                "###",
                "#X#",
                "###",
                '#',
                Items.stick,
                'X',
                Blocks.wool);
        this.addRecipe(
                new ItemStack(Items.item_frame, 1),
                "###",
                "#X#",
                "###",
                '#',
                Items.stick,
                'X',
                Items.leather);
        this.addRecipe(
                new ItemStack(Items.golden_apple, 1, 0),
                "###",
                "#X#",
                "###",
                '#',
                Items.gold_ingot,
                'X',
                Items.apple);
        this.addRecipe(
                new ItemStack(Items.golden_apple, 1, 1),
                "###",
                "#X#",
                "###",
                '#',
                Blocks.gold_block,
                'X',
                Items.apple);
        this.addRecipe(
                new ItemStack(Items.golden_carrot, 1, 0),
                "###",
                "#X#",
                "###",
                '#',
                Items.gold_nugget,
                'X',
                Items.carrot);
        this.addRecipe(
                new ItemStack(Items.speckled_melon, 1),
                "###",
                "#X#",
                "###",
                '#',
                Items.gold_nugget,
                'X',
                Items.melon);
        this.addRecipe(
                new ItemStack(Blocks.lever, 1),
                packedTextFragments[23],
                packedTextFragments[24],
                '#',
                Blocks.cobblestone,
                'X',
                Items.stick);
        this.addRecipe(
                new ItemStack(Blocks.tripwire_hook, 2),
                packedTextFragments[25],
                packedTextFragments[26],
                packedTextFragments[27],
                '#',
                Blocks.planks,
                'S',
                Items.stick,
                'I',
                Items.iron_ingot);
        this.addRecipe(
                new ItemStack(Blocks.redstone_torch, 1),
                packedTextFragments[28],
                packedTextFragments[29],
                '#',
                Items.stick,
                'X',
                Items.redstone);
        this.addRecipe(
                new ItemStack(Items.repeater, 1),
                "#X#",
                "III",
                '#',
                Blocks.redstone_torch,
                'X',
                Items.redstone,
                'I',
                new ItemStack(Blocks.stone, 1, BlockStoneEnumType.STONE.getMetaFromState()));
        this.addRecipe(
                new ItemStack(Items.comparator, 1),
                " # ",
                "#X#",
                "III",
                '#',
                Blocks.redstone_torch,
                'X',
                Items.quartz,
                'I',
                new ItemStack(Blocks.stone, 1, BlockStoneEnumType.STONE.getMetaFromState()));
        this.addRecipe(
                new ItemStack(Items.clock, 1),
                " # ",
                "#X#",
                " # ",
                '#',
                Items.gold_ingot,
                'X',
                Items.redstone);
        this.addRecipe(
                new ItemStack(Items.compass, 1),
                " # ",
                "#X#",
                " # ",
                '#',
                Items.iron_ingot,
                'X',
                Items.redstone);
        this.addRecipe(
                new ItemStack(Items.map, 1),
                "###",
                "#X#",
                "###",
                '#',
                Items.paper,
                'X',
                Items.compass);
        this.addRecipe(
                new ItemStack(Blocks.stone_button, 1),
                packedTextFragments[30],
                '#',
                new ItemStack(Blocks.stone, 1, BlockStoneEnumType.STONE.getMetaFromState()));
        this.addRecipe(
                new ItemStack(Blocks.wooden_button, 1),
                packedTextFragments[31],
                '#',
                Blocks.planks);
        this.addRecipe(
                new ItemStack(Blocks.stone_pressure_plate, 1),
                "##",
                '#',
                new ItemStack(Blocks.stone, 1, BlockStoneEnumType.STONE.getMetaFromState()));
        this.addRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), "##", '#', Blocks.planks);
        this.addRecipe(
                new ItemStack(Blocks.heavy_weighted_pressure_plate, 1),
                "##",
                '#',
                Items.iron_ingot);
        this.addRecipe(
                new ItemStack(Blocks.light_weighted_pressure_plate, 1),
                "##",
                '#',
                Items.gold_ingot);
        this.addRecipe(
                new ItemStack(Blocks.dispenser, 1),
                "###",
                "#X#",
                "#R#",
                '#',
                Blocks.cobblestone,
                'X',
                Items.bow,
                'R',
                Items.redstone);
        this.addRecipe(
                new ItemStack(Blocks.dropper, 1),
                "###",
                "# #",
                "#R#",
                '#',
                Blocks.cobblestone,
                'R',
                Items.redstone);
        this.addRecipe(
                new ItemStack(Blocks.piston, 1),
                "TTT",
                "#X#",
                "#R#",
                '#',
                Blocks.cobblestone,
                'X',
                Items.iron_ingot,
                'R',
                Items.redstone,
                'T',
                Blocks.planks);
        this.addRecipe(
                new ItemStack(Blocks.sticky_piston, 1),
                packedTextFragments[32],
                packedTextFragments[33],
                'S',
                Items.slime_ball,
                'P',
                Blocks.piston);
        this.addRecipe(
                new ItemStack(Items.bed, 1), "###", "XXX", '#', Blocks.wool, 'X', Blocks.planks);
        this.addRecipe(
                new ItemStack(Blocks.enchanting_table, 1),
                " B ",
                "D#D",
                "###",
                '#',
                Blocks.obsidian,
                'B',
                Items.book,
                'D',
                Items.diamond);
        this.addRecipe(
                new ItemStack(Blocks.anvil, 1),
                "III",
                " i ",
                "iii",
                'I',
                Blocks.iron_block,
                'i',
                Items.iron_ingot);
        this.addRecipe(new ItemStack(Items.leather), "##", "##", '#', Items.rabbit_hide);
        this.addShapelessRecipe(
                new ItemStack(Items.ender_eye, 1), Items.ender_pearl, Items.blaze_powder);
        this.addShapelessRecipe(
                new ItemStack(Items.fire_charge, 3),
                Items.gunpowder,
                Items.blaze_powder,
                Items.coal);
        this.addShapelessRecipe(
                new ItemStack(Items.fire_charge, 3),
                Items.gunpowder,
                Items.blaze_powder,
                new ItemStack(Items.coal, 1, 1));
        this.addRecipe(
                new ItemStack(Blocks.daylight_detector),
                "GGG",
                "QQQ",
                "WWW",
                'G',
                Blocks.glass,
                'Q',
                Items.quartz,
                'W',
                Blocks.wooden_slab);
        this.addRecipe(
                new ItemStack(Blocks.hopper),
                "I I",
                "ICI",
                " I ",
                'I',
                Items.iron_ingot,
                'C',
                Blocks.chest);
        this.addRecipe(
                new ItemStack(Items.armor_stand, 1),
                "///",
                " / ",
                "/_/",
                '/',
                Items.stick,
                '_',
                new ItemStack(Blocks.stone_slab, 1, BlockStoneSlabEnumType.STONE.getMetadata()));
        Collections.sort(this.recipes, new IRecipeComparator(this));
    }

    public void addShapelessRecipe(ItemStack itemStack, Object... aobject) {
        ArrayList arrayList = Lists.newArrayList();
        Object[] aobject1 = aobject;
        int length = aobject.length;

        for (int j = 0; j < length; j++) {
            Object object = aobject1[j];

            try {
                if (object instanceof ItemStack) {
                    arrayList.add(((ItemStack) object).copy());
                    continue;
                }
            } catch (IllegalArgumentException illegalargumentexception2) {
                throw method_9a70918140b0a7642dba(illegalargumentexception2);
            }

            try {
                if (object instanceof Item) {
                    arrayList.add(new ItemStack((Item) object));
                    continue;
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }

            try {
                if (!(object instanceof Block)) {
                    throw new IllegalArgumentException(
                            "Invalid shapeless recipe: unknown type "
                                    + object.getClass().getName()
                                    + packedTextFragments[35]);
                }
            } catch (IllegalArgumentException illegalargumentexception1) {
                throw method_9a70918140b0a7642dba(illegalargumentexception1);
            }

            arrayList.add(new ItemStack((Block) object));
        }

        this.recipes.add(new ShapelessRecipes(itemStack, arrayList));
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public void addRecipe(IRecipe iRecipe) {
        this.recipes.add(iRecipe);
    }

    public ItemStack findMatchingRecipe(InventoryCrafting inventoryCrafting, World world) {
        for (java.util.Iterator<?> __recoveryIterator171_46541 = (this.recipes).iterator();
                (__recoveryIterator171_46541).hasNext(); ) {
            IRecipe iRecipe = (IRecipe) (__recoveryIterator171_46541).next();
            if (iRecipe.matches(inventoryCrafting, world)) {
                return iRecipe.getCraftingResult(inventoryCrafting);
            }
        }

        return null;
    }

    static {
        net.minecraft.item.crafting.CraftingManager.packedTextFragments = new String[38];
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[0] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[1] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[2] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[3] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[4] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[5] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[6] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[7] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[8] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[9] = new String("X");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[10] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[11] = new String("X");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[12] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[13] = new String("A");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[14] = new String("B");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[15] = new String("A");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[16] = new String("B");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[17] = new String("A");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[18] = new String("B");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[19] = new String("A");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[20] = new String("B");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[21] = new String("A");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[22] = new String("B");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[23] = new String("X");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[24] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[25] = new String("I");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[26] = new String("S");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[27] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[28] = new String("X");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[29] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[30] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[31] = new String("#");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[32] = new String("S");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[33] = new String("P");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[34] = new String("");
        net.minecraft.item.crafting.CraftingManager.packedTextFragments[35] = new String("!");
        net.minecraft.item.crafting.CraftingManager.instance = new CraftingManager();
    }

    public ShapedRecipes addRecipe(ItemStack itemStack, Object... aobject) {
        String text = packedTextFragments[34];
        int i = 0;
        int j = 0;
        int k = 0;
        if (aobject[i] instanceof String[]) {
            String[] astring = (String[]) aobject[i++];

            for (int l = 0; l < astring.length; l++) {
                String textValue = astring[l];
                k++;
                j = textValue.length();
                text = text + textValue;
            }
        } else {
            while (aobject[i] instanceof String) {
                String textValue2 = (String) aobject[i++];
                k++;
                j = textValue2.length();
                text = text + textValue2;
            }
        }

        HashMap hashmap = Maps.newHashMap();

        while (i < aobject.length) {
            Character character = (Character) aobject[i];
            ItemStack itemStackValue = null;
            if (aobject[i + 1] instanceof Item) {
                itemStackValue = new ItemStack((Item) aobject[i + 1]);
            } else if (aobject[i + 1] instanceof Block) {
                itemStackValue = new ItemStack((Block) aobject[i + 1], 1, 32767);
            } else if (aobject[i + 1] instanceof ItemStack) {
                itemStackValue = (ItemStack) aobject[i + 1];
            }

            hashmap.put(character, itemStackValue);
            i += 2;
        }

        ItemStack[] itemStackArray = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; i1++) {
            char c0 = text.charAt(i1);

            try {
                if (hashmap.containsKey(c0)) {
                    itemStackArray[i1] = ((ItemStack) hashmap.get(c0)).copy();
                    continue;
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }

            itemStackArray[i1] = null;
        }

        ShapedRecipes shapedRecipes = new ShapedRecipes(j, k, itemStackArray, itemStack);
        this.recipes.add(shapedRecipes);
        return shapedRecipes;
    }

    public List getRecipeList() {
        return this.recipes;
    }

    public ItemStack[] method_0c7013863aed82d876b8(
            InventoryCrafting inventoryCrafting, World world) {
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        Iterator iterator = this.recipes.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            IRecipe iRecipe = (IRecipe) iterator.next();

            try {
                if (iRecipe.matches(inventoryCrafting, world)) {
                    return iRecipe.getRemainingItems(inventoryCrafting);
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }

            if (flag) {
                break;
            }
        }

        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
        int i = 0;

        while (i < itemStackArray.length) {
            itemStackArray[i] = inventoryCrafting.getStackInSlot(i);
            i++;
            if (flag) {
                break;
            }
        }

        return itemStackArray;
    }

    public static CraftingManager getInstance() {
        return net.minecraft.item.crafting.CraftingManager.instance;
    }

    // Helper used only by this class.
    public static class IRecipeComparator implements java.util.Comparator {
        net.minecraft.item.crafting.CraftingManager buq;

        public int compare(
                net.minecraft.item.crafting.IRecipe iRecipe,
                net.minecraft.item.crafting.IRecipe iRecipeValue) {
            return iRecipe instanceof net.minecraft.item.crafting.ShapelessRecipes
                            && iRecipeValue instanceof net.minecraft.item.crafting.ShapedRecipes
                    ? 1
                    : (iRecipeValue instanceof net.minecraft.item.crafting.ShapelessRecipes
                                    && iRecipe instanceof net.minecraft.item.crafting.ShapedRecipes
                            ? -1
                            : (iRecipeValue.getRecipeSize() < iRecipe.getRecipeSize()
                                    ? -1
                                    : (iRecipeValue.getRecipeSize() > iRecipe.getRecipeSize()
                                            ? 1
                                            : 0)));
        }

        @java.lang.Override
        public int compare(java.lang.Object object, java.lang.Object object1) {
            return this.compare(
                    (net.minecraft.item.crafting.IRecipe) object,
                    (net.minecraft.item.crafting.IRecipe) object1);
        }

        public IRecipeComparator(net.minecraft.item.crafting.CraftingManager buqValue) {
            this.buq = buqValue;
        }
    }
}
