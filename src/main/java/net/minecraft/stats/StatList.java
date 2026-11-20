package net.minecraft.stats;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityListEntityEggInfo;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatList {
    public static StatBase distanceByBoatStat;
    public static List objectMineStats;
    public static List allStats;
    public static StatBase[] objectUseStats;
    public static StatBase damageDealtStat;
    public static StatBase timeSinceDeathStat;
    public static StatBase timesTalkedToVillagerStat;
    public static StatBase[] objectCraftStats;
    public static StatBase[] objectBreakStats;
    public static List generalStats;
    protected static Map oneShotStats;
    public static StatBase jumpStat;
    public static StatBase distanceSprintedStat;
    public static StatBase distanceByMinecartStat;
    public static StatBase distanceByHorseStat;

    public static StatBase distanceByPigStat;
    public static StatBase[] mineBlockStatArray;
    public static StatBase dropStat;
    public static StatBase timesTradedWithVillagerStat;
    public static StatBase distanceWalkedStat;
    public static StatBase minutesPlayedStat;
    public static StatBase distanceFlownStat;
    public static List itemStats;
    public static StatBase deathsStat;
    public static StatBase treasureFishedStat;
    public static StatBase damageTakenStat;

    public static StatBase distanceFallenStat;
    public static StatBase mobKillsStat;
    public static StatBase distanceCrouchedStat;
    public static StatBase animalsBredStat;
    public static StatBase distanceClimbedStat;
    public static StatBase leaveGameStat;
    public static StatBase playerKillsStat;
    public static StatBase distanceSwumStat;
    public static StatBase fishCaughtStat;
    public static StatBase junkFishedStat;
    public static StatBase distanceDoveStat;

    private static void initMiningStats() {
        Entity[] entityArray = StatBase.GU();
        Iterator iterator = Block.blockRegistry.iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            Block block = (Block) iterator.next();
            Item item = Item.getItemFromBlock(block);
            if (item != null) {
                int idFromBlock = Block.getIdFromBlock(block);
                String text = method_c030771551ecabcd01e8(item);
                if (text != null && block.getEnableStats()) {
                    net.minecraft.stats.StatList.mineBlockStatArray[idFromBlock] =
                            new StatCrafting(
                                            "stat.mineBlock.",
                                            text,
                                            new ChatComponentTranslation(
                                                    "stat.mineBlock",
                                                    new Object[] {new ItemStack(block).getChatComponent()}),
                                            item)
                                    .registerStat();
                    net.minecraft.stats.StatList.objectMineStats.add((StatCrafting) net.minecraft.stats.StatList.mineBlockStatArray[idFromBlock]);
                }
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        replaceAllSimilarBlocks(net.minecraft.stats.StatList.mineBlockStatArray);
    }

    private static void replaceAllSimilarBlocks(StatBase[] statBaseArray) {
        mergeStatBases(statBaseArray, Blocks.water, Blocks.flowing_water);
        mergeStatBases(statBaseArray, Blocks.lava, Blocks.flowing_lava);
        mergeStatBases(statBaseArray, Blocks.lit_pumpkin, Blocks.pumpkin);
        mergeStatBases(statBaseArray, Blocks.lit_furnace, Blocks.furnace);
        mergeStatBases(statBaseArray, Blocks.lit_redstone_ore, Blocks.redstone_ore);
        mergeStatBases(statBaseArray, Blocks.powered_repeater, Blocks.unpowered_repeater);
        mergeStatBases(statBaseArray, Blocks.powered_comparator, Blocks.unpowered_comparator);
        mergeStatBases(statBaseArray, Blocks.redstone_torch, Blocks.unlit_redstone_torch);
        mergeStatBases(statBaseArray, Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
        mergeStatBases(statBaseArray, Blocks.double_stone_slab, Blocks.stone_slab);
        mergeStatBases(statBaseArray, Blocks.double_wooden_slab, Blocks.wooden_slab);
        mergeStatBases(statBaseArray, Blocks.double_stone_slab2, Blocks.stone_slab2);
        mergeStatBases(statBaseArray, Blocks.grass, Blocks.dirt);
        mergeStatBases(statBaseArray, Blocks.farmland, Blocks.dirt);
    }

    static {
        net.minecraft.stats.StatList.oneShotStats = Maps.newHashMap();
        net.minecraft.stats.StatList.allStats = Lists.newArrayList();
        net.minecraft.stats.StatList.generalStats = Lists.newArrayList();
        net.minecraft.stats.StatList.itemStats = Lists.newArrayList();
        net.minecraft.stats.StatList.objectMineStats = Lists.newArrayList();
        net.minecraft.stats.StatList.leaveGameStat =
                new StatBasic(
                                "stat.leaveGame",
                                new ChatComponentTranslation("stat.leaveGame", new Object[0]))
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.minutesPlayedStat =
                new StatBasic(
                                "stat.playOneMinute",
                                new ChatComponentTranslation("stat.playOneMinute", new Object[0]),
                                StatBase.timeStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.timeSinceDeathStat =
                new StatBasic(
                                "stat.timeSinceDeath",
                                new ChatComponentTranslation("stat.timeSinceDeath", new Object[0]),
                                StatBase.timeStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceWalkedStat =
                new StatBasic(
                                "stat.walkOneCm",
                                new ChatComponentTranslation("stat.walkOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceCrouchedStat =
                new StatBasic(
                                "stat.crouchOneCm",
                                new ChatComponentTranslation("stat.crouchOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceSprintedStat =
                new StatBasic(
                                "stat.sprintOneCm",
                                new ChatComponentTranslation("stat.sprintOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceSwumStat =
                new StatBasic(
                                "stat.swimOneCm",
                                new ChatComponentTranslation("stat.swimOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceFallenStat =
                new StatBasic(
                                "stat.fallOneCm",
                                new ChatComponentTranslation("stat.fallOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceClimbedStat =
                new StatBasic(
                                "stat.climbOneCm",
                                new ChatComponentTranslation("stat.climbOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceFlownStat =
                new StatBasic(
                                "stat.flyOneCm",
                                new ChatComponentTranslation("stat.flyOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceDoveStat =
                new StatBasic(
                                "stat.diveOneCm",
                                new ChatComponentTranslation("stat.diveOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceByMinecartStat =
                new StatBasic(
                                "stat.minecartOneCm",
                                new ChatComponentTranslation("stat.minecartOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceByBoatStat =
                new StatBasic(
                                "stat.boatOneCm",
                                new ChatComponentTranslation("stat.boatOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceByPigStat =
                new StatBasic(
                                "stat.pigOneCm",
                                new ChatComponentTranslation("stat.pigOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.distanceByHorseStat =
                new StatBasic(
                                "stat.horseOneCm",
                                new ChatComponentTranslation("stat.horseOneCm", new Object[0]),
                                StatBase.distanceStatType)
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.jumpStat =
                new StatBasic(
                                "stat.jump",
                                new ChatComponentTranslation("stat.jump", new Object[0]))
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.dropStat =
                new StatBasic(
                                "stat.drop",
                                new ChatComponentTranslation("stat.drop", new Object[0]))
                        .initIndependentStat()
                        .registerStat();
        net.minecraft.stats.StatList.damageDealtStat =
                new StatBasic(
                                "stat.damageDealt",
                                new ChatComponentTranslation("stat.damageDealt", new Object[0]),
                                StatBase.bCW)
                        .registerStat();
        net.minecraft.stats.StatList.damageTakenStat =
                new StatBasic(
                                "stat.damageTaken",
                                new ChatComponentTranslation("stat.damageTaken", new Object[0]),
                                StatBase.bCW)
                        .registerStat();
        net.minecraft.stats.StatList.deathsStat =
                new StatBasic(
                                "stat.deaths",
                                new ChatComponentTranslation("stat.deaths", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.mobKillsStat =
                new StatBasic(
                                "stat.mobKills",
                                new ChatComponentTranslation("stat.mobKills", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.animalsBredStat =
                new StatBasic(
                                "stat.animalsBred",
                                new ChatComponentTranslation("stat.animalsBred", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.playerKillsStat =
                new StatBasic(
                                "stat.playerKills",
                                new ChatComponentTranslation("stat.playerKills", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.fishCaughtStat =
                new StatBasic(
                                "stat.fishCaught",
                                new ChatComponentTranslation("stat.fishCaught", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.junkFishedStat =
                new StatBasic(
                                "stat.junkFished",
                                new ChatComponentTranslation("stat.junkFished", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.treasureFishedStat =
                new StatBasic(
                                "stat.treasureFished",
                                new ChatComponentTranslation("stat.treasureFished", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.timesTalkedToVillagerStat =
                new StatBasic(
                                "stat.talkedToVillager",
                                new ChatComponentTranslation(
                                        "stat.talkedToVillager", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.timesTradedWithVillagerStat =
                new StatBasic(
                                "stat.tradedWithVillager",
                                new ChatComponentTranslation(
                                        "stat.tradedWithVillager", new Object[0]))
                        .registerStat();
        net.minecraft.stats.StatList.mineBlockStatArray = new StatBase[4096];
        net.minecraft.stats.StatList.objectCraftStats = new StatBase[32000];
        net.minecraft.stats.StatList.objectUseStats = new StatBase[32000];
        net.minecraft.stats.StatList.objectBreakStats = new StatBase[32000];
    }

    private static void initItemDepleteStats() {
        Entity[] entityArray = StatBase.GU();
        Iterator iterator = Item.itemRegistry.iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            Item item = (Item) iterator.next();
            if (item != null) {
                int idFromItem = Item.getIdFromItem(item);
                String text = method_c030771551ecabcd01e8(item);
                if (text != null && item.isDamageable()) {
                    net.minecraft.stats.StatList.objectBreakStats[idFromItem] =
                            new StatCrafting(
                                            "stat.breakItem.",
                                            text,
                                            new ChatComponentTranslation(
                                                    "stat.breakItem",
                                                    new Object[] {new ItemStack(item).getChatComponent()}),
                                            item)
                                    .registerStat();
                }
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        replaceAllSimilarBlocks(net.minecraft.stats.StatList.objectBreakStats);
    }

    public static StatBase getOneShotStat(String text) {
        return (StatBase) net.minecraft.stats.StatList.oneShotStats.get(text);
    }

    private static void mergeStatBases(StatBase[] statBaseArray, Block block, Block blockValue) {
        Entity[] entityArray = StatBase.GU();
        int idFromBlock = Block.getIdFromBlock(block);
        Entity[] entityArrayValue = entityArray;
        int j = Block.getIdFromBlock(blockValue);
        if (statBaseArray[idFromBlock] != null && statBaseArray[j] == null) {
            statBaseArray[j] = statBaseArray[idFromBlock];
            if (entityArrayValue == null) {
                return;
            }
        }

        net.minecraft.stats.StatList.allStats.remove(statBaseArray[idFromBlock]);
        net.minecraft.stats.StatList.objectMineStats.remove(statBaseArray[idFromBlock]);
        net.minecraft.stats.StatList.generalStats.remove(statBaseArray[idFromBlock]);
        statBaseArray[idFromBlock] = statBaseArray[j];
    }

    private static void initStats() {
        Entity[] entityArray = StatBase.GU();
        Iterator iterator = Item.itemRegistry.iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            Item item = (Item) iterator.next();
            if (item != null) {
                int idFromItem = Item.getIdFromItem(item);
                String text = method_c030771551ecabcd01e8(item);
                if (text != null) {
                    net.minecraft.stats.StatList.objectUseStats[idFromItem] =
                            new StatCrafting(
                                            "stat.useItem.",
                                            text,
                                            new ChatComponentTranslation(
                                                    "stat.useItem",
                                                    new Object[] {new ItemStack(item).getChatComponent()}),
                                            item)
                                    .registerStat();
                    if (!(item instanceof ItemBlock)) {
                        net.minecraft.stats.StatList.itemStats.add((StatCrafting) net.minecraft.stats.StatList.objectUseStats[idFromItem]);
                    }
                }
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        replaceAllSimilarBlocks(net.minecraft.stats.StatList.objectUseStats);
    }

    public static void init() {
        initMiningStats();
        initStats();
        initItemDepleteStats();
        initCraftableStats();
        AchievementList.init();
        EntityList.method_8d9b536bcdc9991c48fb();
    }

    private static void initCraftableStats() {
        Entity[] entityArray = StatBase.GU();
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = CraftingManager.getInstance().getRecipeList().iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            IRecipe iRecipe = (IRecipe) iterator.next();
            if (iRecipe.getRecipeOutput() != null) {
                hashset.add(iRecipe.getRecipeOutput().getItem());
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_44653 =
                        (FurnaceRecipes.instance().getSmeltingList().values()).iterator();
                (__recoveryIterator171_44653).hasNext(); ) {
            ItemStack itemStack = (ItemStack) (__recoveryIterator171_44653).next();
            hashset.add(itemStack.getItem());
            if (entityArrayValue != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_44922 = (hashset).iterator();
                (__recoveryIterator171_44922).hasNext(); ) {
            Item item = (Item) (__recoveryIterator171_44922).next();
            if (item != null) {
                int idFromItem = Item.getIdFromItem(item);
                String text = method_c030771551ecabcd01e8(item);
                if (text != null) {
                    net.minecraft.stats.StatList.objectCraftStats[idFromItem] =
                            new StatCrafting(
                                            "stat.craftItem.",
                                            text,
                                            new ChatComponentTranslation(
                                                    "stat.craftItem",
                                                    new Object[] {new ItemStack(item).getChatComponent()}),
                                            item)
                                    .registerStat();
                }
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        replaceAllSimilarBlocks(net.minecraft.stats.StatList.objectCraftStats);
    }

    public static StatBase getStatEntityKilledBy(EntityListEntityEggInfo entityListEntityEggInfo) {
        String text = EntityList.getStringFromID(entityListEntityEggInfo.spawnedID);
        return text == null
                ? null
                : new StatBase(
                                "stat.entityKilledBy." + text,
                                new ChatComponentTranslation(
                                        "stat.entityKilledBy",
                                        new Object[] {
                                            new ChatComponentTranslation(
                                                    "entity." + text + ".name", new Object[0])
                                        }))
                        .registerStat();
    }

    private static String method_c030771551ecabcd01e8(Item item) {
        ResourceLocation nameForObject =
                (ResourceLocation) Item.itemRegistry.getNameForObject(item);
        return nameForObject != null ? nameForObject.toString().replace(':', '.') : null;
    }

    public static StatBase getStatKillEntity(EntityListEntityEggInfo entityListEntityEggInfo) {
        String text = EntityList.getStringFromID(entityListEntityEggInfo.spawnedID);
        return text == null
                ? null
                : new StatBase(
                                "stat.killEntity." + text,
                                new ChatComponentTranslation(
                                        "stat.entityKill",
                                        new Object[] {
                                            new ChatComponentTranslation(
                                                    "entity." + text + ".name", new Object[0])
                                        }))
                        .registerStat();
    }
}
