package net.minecraft.stats;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonSerializableSet;

import com.google.common.collect.Lists;

import java.util.List;

public class AchievementList {
    public static Achievement overpowered;
    public static Achievement buildBetterPickaxe;
    public static Achievement snipeSkeleton;
    public static Achievement portal;

    public static Achievement buildSword;
    public static Achievement theEnd2;
    public static Achievement flyPig;
    public static Achievement mineWood;
    public static Achievement killCow;
    public static Achievement potion;
    public static Achievement ghast;
    public static Achievement cookFish;
    public static Achievement bakeCake;
    public static Achievement killWither;
    public static Achievement breedCow;
    public static Achievement exploreAllBiomes;
    public static Achievement diamondsToYou;
    public static Achievement fullBeacon;
    public static Achievement openInventory;
    public static Achievement buildPickaxe;
    public static int maxDisplayColumn;
    public static Achievement acquireIron;
    public static Achievement onARail;
    public static int minDisplayColumn;
    public static Achievement theEnd;
    public static Achievement killEnemy;
    public static List achievementList;
    public static Achievement bookcase;
    public static Achievement makeBread;
    public static Achievement diamonds;
    public static Achievement overkill;
    public static Achievement buildFurnace;
    public static Achievement buildHoe;
    public static Achievement blazeRod;
    public static Achievement buildWorkBench;
    public static Achievement enchantments;
    public static int maxDisplayRow;
    public static int minDisplayRow;
    public static Achievement spawnWither;

    // region String decoding support

    // endregion

    public static void init() {}

    // region String decoding support

    // endregion

    static {
        net.minecraft.stats.AchievementList.achievementList = Lists.newArrayList();
        net.minecraft.stats.AchievementList.openInventory =
                new Achievement(
                                "achievement.openInventory",
                                "openInventory",
                                0,
                                0,
                                Items.book,
                                (Achievement) null)
                        .func_180789_a()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.mineWood =
                new Achievement(
                                "achievement.mineWood",
                                "mineWood",
                                2,
                                1,
                                Blocks.log,
                                net.minecraft.stats.AchievementList.openInventory)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.buildWorkBench =
                new Achievement(
                                "achievement.buildWorkBench",
                                "buildWorkBench",
                                4,
                                -1,
                                Blocks.crafting_table,
                                net.minecraft.stats.AchievementList.mineWood)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.buildPickaxe =
                new Achievement(
                                "achievement.buildPickaxe",
                                "buildPickaxe",
                                4,
                                2,
                                Items.wooden_pickaxe,
                                net.minecraft.stats.AchievementList.buildWorkBench)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.buildFurnace =
                new Achievement(
                                "achievement.buildFurnace",
                                "buildFurnace",
                                3,
                                4,
                                Blocks.furnace,
                                net.minecraft.stats.AchievementList.buildPickaxe)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.acquireIron =
                new Achievement(
                                "achievement.acquireIron",
                                "acquireIron",
                                1,
                                4,
                                Items.iron_ingot,
                                net.minecraft.stats.AchievementList.buildFurnace)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.buildHoe =
                new Achievement("achievement.buildHoe", "buildHoe", 2, -3, Items.wooden_hoe, net.minecraft.stats.AchievementList.buildWorkBench)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.makeBread =
                new Achievement("achievement.makeBread", "makeBread", -1, -3, Items.bread, net.minecraft.stats.AchievementList.buildHoe)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.bakeCake =
                new Achievement("achievement.bakeCake", "bakeCake", 0, -5, Items.cake, net.minecraft.stats.AchievementList.buildHoe)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.buildBetterPickaxe =
                new Achievement(
                                "achievement.buildBetterPickaxe",
                                "buildBetterPickaxe",
                                6,
                                2,
                                Items.stone_pickaxe,
                                net.minecraft.stats.AchievementList.buildPickaxe)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.cookFish =
                new Achievement("achievement.cookFish", "cookFish", 2, 6, Items.cooked_fish, net.minecraft.stats.AchievementList.buildFurnace)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.onARail =
                new Achievement("achievement.onARail", "onARail", 2, 3, Blocks.rail, net.minecraft.stats.AchievementList.acquireIron)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.buildSword =
                new Achievement(
                                "achievement.buildSword",
                                "buildSword",
                                6,
                                -1,
                                Items.wooden_sword,
                                net.minecraft.stats.AchievementList.buildWorkBench)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.killEnemy =
                new Achievement("achievement.killEnemy", "killEnemy", 8, -1, Items.bone, net.minecraft.stats.AchievementList.buildSword)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.killCow =
                new Achievement("achievement.killCow", "killCow", 7, -3, Items.leather, net.minecraft.stats.AchievementList.buildSword)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.flyPig =
                new Achievement("achievement.flyPig", "flyPig", 9, -3, Items.saddle, net.minecraft.stats.AchievementList.killCow)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.snipeSkeleton =
                new Achievement(
                                "achievement.snipeSkeleton",
                                "snipeSkeleton",
                                7,
                                0,
                                Items.bow,
                                net.minecraft.stats.AchievementList.killEnemy)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.diamonds =
                new Achievement("achievement.diamonds", "diamonds", -1, 5, Blocks.diamond_ore, net.minecraft.stats.AchievementList.acquireIron)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.diamondsToYou =
                new Achievement(
                                "achievement.diamondsToYou",
                                "diamondsToYou",
                                -1,
                                2,
                                Items.diamond,
                                net.minecraft.stats.AchievementList.diamonds)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.portal =
                new Achievement("achievement.portal", "portal", -1, 7, Blocks.obsidian, net.minecraft.stats.AchievementList.diamonds)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.ghast =
                new Achievement("achievement.ghast", "ghast", -4, 8, Items.ghast_tear, net.minecraft.stats.AchievementList.portal)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.blazeRod =
                new Achievement("achievement.blazeRod", "blazeRod", 0, 9, Items.blaze_rod, net.minecraft.stats.AchievementList.portal)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.potion =
                new Achievement("achievement.potion", "potion", 2, 8, Items.potionitem, net.minecraft.stats.AchievementList.blazeRod)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.theEnd =
                new Achievement("achievement.theEnd", "theEnd", 3, 10, Items.ender_eye, net.minecraft.stats.AchievementList.blazeRod)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.theEnd2 =
                new Achievement("achievement.theEnd2", "theEnd2", 4, 13, Blocks.dragon_egg, net.minecraft.stats.AchievementList.theEnd)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.enchantments =
                new Achievement(
                                "achievement.enchantments",
                                "enchantments",
                                -4,
                                4,
                                Blocks.enchanting_table,
                                net.minecraft.stats.AchievementList.diamonds)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.overkill =
                new Achievement("achievement.overkill", "overkill", -4, 1, Items.diamond_sword, net.minecraft.stats.AchievementList.enchantments)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.bookcase =
                new Achievement("achievement.bookcase", "bookcase", -3, 6, Blocks.bookshelf, net.minecraft.stats.AchievementList.enchantments)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.breedCow =
                new Achievement("achievement.breedCow", "breedCow", 7, -5, Items.wheat, net.minecraft.stats.AchievementList.killCow)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.spawnWither =
                new Achievement(
                                "achievement.spawnWither",
                                "spawnWither",
                                7,
                                12,
                                new ItemStack(Items.skull, 1, 1),
                                net.minecraft.stats.AchievementList.theEnd2)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.killWither =
                new Achievement(
                                "achievement.killWither",
                                "killWither",
                                7,
                                10,
                                Items.nether_star,
                                net.minecraft.stats.AchievementList.spawnWither)
                        .func_180788_c();
        net.minecraft.stats.AchievementList.fullBeacon =
                new Achievement("achievement.fullBeacon", "fullBeacon", 7, 8, Blocks.beacon, net.minecraft.stats.AchievementList.killWither)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.exploreAllBiomes =
                new Achievement(
                                "achievement.exploreAllBiomes",
                                "exploreAllBiomes",
                                4,
                                8,
                                Items.diamond_boots,
                                net.minecraft.stats.AchievementList.theEnd)
                        .func_180787_a(JsonSerializableSet.class)
                        .setSpecial()
                        .func_180788_c();
        net.minecraft.stats.AchievementList.overpowered =
                new Achievement(
                                "achievement.overpowered",
                                "overpowered",
                                6,
                                4,
                                Items.golden_apple,
                                net.minecraft.stats.AchievementList.buildBetterPickaxe)
                        .setSpecial()
                        .func_180788_c();
    }
}
