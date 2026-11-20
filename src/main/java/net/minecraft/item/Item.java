package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartEnumMinecartType;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.item.ItemRegisterItemsFunction;
import net.minecraft.item.ItemRegisterItemsFunction10;
import net.minecraft.item.ItemRegisterItemsFunction11;
import net.minecraft.item.ItemRegisterItemsFunction12;
import net.minecraft.item.ItemRegisterItemsFunction13;
import net.minecraft.item.ItemRegisterItemsFunction14;
import net.minecraft.item.ItemRegisterItemsFunction15;
import net.minecraft.item.ItemRegisterItemsFunction16;
import net.minecraft.item.ItemRegisterItemsFunction17;
import net.minecraft.item.ItemRegisterItemsFunction2;
import net.minecraft.item.ItemRegisterItemsFunction3;
import net.minecraft.item.ItemRegisterItemsFunction4;
import net.minecraft.item.ItemRegisterItemsFunction5;
import net.minecraft.item.ItemRegisterItemsFunction6;
import net.minecraft.item.ItemRegisterItemsFunction7;
import net.minecraft.item.ItemRegisterItemsFunction8;
import net.minecraft.item.ItemRegisterItemsFunction9;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Item {
    private Item containerItem;

    private static int legacyItemFlowMarker;
    private static Map BLOCK_TO_ITEM;
    private String unlocalizedName;
    protected boolean bFull3D;
    private String potionEffect;
    private int maxDamage;
    public static RegistryNamespaced itemRegistry;
    private static String[] packedTextFragments;
    private CreativeTabs tabToDisplayOn;

    protected boolean hasSubtypes;
    protected static UUID itemModifierUUID;
    protected int maxStackSize = 64;
    protected static Random itemRand;

    public Item setFull3D() {
        this.bFull3D = true;
        return this;
    }

    public boolean hasEffect(ItemStack itemStack) {
        return itemStack.isItemEnchanted();
    }

    public Multimap getItemAttributeModifiers() {
        return HashMultimap.create();
    }

    public EnumRarity getRarity(ItemStack itemStack) {
        return itemStack.isItemEnchanted() ? EnumRarity.RARE : EnumRarity.COMMON;
    }

    public static void registerItems() {
        registerItemBlock(
                Blocks.stone,
                new ItemMultiTexture(Blocks.stone, Blocks.stone, new ItemRegisterItemsFunction11())
                        .aO("stone"));
        registerItemBlock(Blocks.grass, new ItemColored(Blocks.grass, false));
        registerItemBlock(
                Blocks.dirt,
                new ItemMultiTexture(Blocks.dirt, Blocks.dirt, new ItemRegisterItemsFunction4())
                        .aO("dirt"));
        registerItemBlock(Blocks.cobblestone);
        registerItemBlock(
                Blocks.planks,
                new ItemMultiTexture(Blocks.planks, Blocks.planks, new ItemRegisterItemsFunction10())
                        .aO("wood"));
        registerItemBlock(
                Blocks.sapling,
                new ItemMultiTexture(
                                Blocks.sapling, Blocks.sapling, new ItemRegisterItemsFunction7())
                        .aO("sapling"));
        registerItemBlock(Blocks.bedrock);
        registerItemBlock(
                Blocks.sand,
                new ItemMultiTexture(Blocks.sand, Blocks.sand, new ItemRegisterItemsFunction17())
                        .aO("sand"));
        registerItemBlock(Blocks.gravel);
        registerItemBlock(Blocks.gold_ore);
        registerItemBlock(Blocks.iron_ore);
        registerItemBlock(Blocks.coal_ore);
        registerItemBlock(
                Blocks.log,
                new ItemMultiTexture(Blocks.log, Blocks.log, new ItemRegisterItemsFunction5())
                        .aO("log"));
        registerItemBlock(
                Blocks.log2,
                new ItemMultiTexture(Blocks.log2, Blocks.log2, new ItemRegisterItemsFunction6())
                        .aO("log"));
        registerItemBlock(
                Blocks.leaves, new ItemLeaves(Blocks.leaves).aO("leaves"));
        registerItemBlock(
                Blocks.leaves2, new ItemLeaves(Blocks.leaves2).aO("leaves"));
        registerItemBlock(
                Blocks.sponge,
                new ItemMultiTexture(Blocks.sponge, Blocks.sponge, new ItemRegisterItemsFunction13())
                        .aO("sponge"));
        String text = ItemStack.getLegacyItemStackFlowMarker();
        registerItemBlock(Blocks.glass);
        registerItemBlock(Blocks.lapis_ore);
        registerItemBlock(Blocks.lapis_block);
        registerItemBlock(Blocks.dispenser);
        registerItemBlock(
                Blocks.sandstone,
                new ItemMultiTexture(
                                Blocks.sandstone,
                                Blocks.sandstone,
                                new ItemRegisterItemsFunction16())
                        .aO("sandStone"));
        registerItemBlock(Blocks.noteblock);
        registerItemBlock(Blocks.golden_rail);
        registerItemBlock(Blocks.detector_rail);
        registerItemBlock(
                Blocks.sticky_piston, new ItemPiston(Blocks.sticky_piston));
        registerItemBlock(Blocks.web);
        registerItemBlock(
                Blocks.tallgrass,
                new ItemColored(Blocks.tallgrass, true)
                        .setSubtypeNames(new String[] {"shrub", "grass", "fern"}));
        registerItemBlock(Blocks.deadbush);
        registerItemBlock(Blocks.piston, new ItemPiston(Blocks.piston));
        registerItemBlock(Blocks.wool, new ItemCloth(Blocks.wool).aO("cloth"));
        registerItemBlock(
                Blocks.yellow_flower,
                new ItemMultiTexture(
                                Blocks.yellow_flower,
                                Blocks.yellow_flower,
                                new ItemRegisterItemsFunction9())
                        .aO("flower"));
        registerItemBlock(
                Blocks.red_flower,
                new ItemMultiTexture(
                                Blocks.red_flower,
                                Blocks.red_flower,
                                new ItemRegisterItemsFunction2())
                        .aO("rose"));
        registerItemBlock(Blocks.brown_mushroom);
        registerItemBlock(Blocks.red_mushroom);
        registerItemBlock(Blocks.gold_block);
        registerItemBlock(Blocks.iron_block);
        registerItemBlock(
                Blocks.stone_slab,
                new ItemSlab(
                                Blocks.stone_slab, Blocks.stone_slab, Blocks.double_stone_slab)
                        .aO("stoneSlab"));
        registerItemBlock(Blocks.brick_block);
        registerItemBlock(Blocks.tnt);
        registerItemBlock(Blocks.bookshelf);
        registerItemBlock(Blocks.mossy_cobblestone);
        registerItemBlock(Blocks.obsidian);
        registerItemBlock(Blocks.torch);
        registerItemBlock(Blocks.mob_spawner);
        registerItemBlock(Blocks.oak_stairs);
        registerItemBlock(Blocks.chest);
        registerItemBlock(Blocks.diamond_ore);
        registerItemBlock(Blocks.diamond_block);
        registerItemBlock(Blocks.crafting_table);
        String textValue = text;
        registerItemBlock(Blocks.farmland);
        registerItemBlock(Blocks.furnace);
        registerItemBlock(Blocks.lit_furnace);
        registerItemBlock(Blocks.ladder);
        registerItemBlock(Blocks.rail);
        registerItemBlock(Blocks.stone_stairs);
        registerItemBlock(Blocks.lever);
        registerItemBlock(Blocks.stone_pressure_plate);
        registerItemBlock(Blocks.wooden_pressure_plate);
        registerItemBlock(Blocks.redstone_ore);
        registerItemBlock(Blocks.redstone_torch);
        registerItemBlock(Blocks.stone_button);
        registerItemBlock(Blocks.snow_layer, new ItemSnow(Blocks.snow_layer));
        registerItemBlock(Blocks.ice);
        registerItemBlock(Blocks.snow);
        registerItemBlock(Blocks.cactus);
        registerItemBlock(Blocks.clay);
        registerItemBlock(Blocks.jukebox);
        registerItemBlock(Blocks.oak_fence);
        registerItemBlock(Blocks.spruce_fence);
        registerItemBlock(Blocks.birch_fence);
        registerItemBlock(Blocks.jungle_fence);
        registerItemBlock(Blocks.dark_oak_fence);
        registerItemBlock(Blocks.acacia_fence);
        registerItemBlock(Blocks.pumpkin);
        registerItemBlock(Blocks.netherrack);
        registerItemBlock(Blocks.soul_sand);
        registerItemBlock(Blocks.glowstone);
        registerItemBlock(Blocks.lit_pumpkin);
        registerItemBlock(Blocks.trapdoor);
        registerItemBlock(
                Blocks.monster_egg,
                new ItemMultiTexture(
                                Blocks.monster_egg,
                                Blocks.monster_egg,
                                new ItemRegisterItemsFunction15())
                        .aO("monsterStoneEgg"));
        registerItemBlock(
                Blocks.stonebrick,
                new ItemMultiTexture(
                                Blocks.stonebrick,
                                Blocks.stonebrick,
                                new ItemRegisterItemsFunction3())
                        .aO("stonebricksmooth"));
        registerItemBlock(Blocks.brown_mushroom_block);
        registerItemBlock(Blocks.red_mushroom_block);
        registerItemBlock(Blocks.iron_bars);
        registerItemBlock(Blocks.glass_pane);
        registerItemBlock(Blocks.melon_block);
        registerItemBlock(Blocks.vine, new ItemColored(Blocks.vine, false));
        registerItemBlock(Blocks.oak_fence_gate);
        registerItemBlock(Blocks.spruce_fence_gate);
        registerItemBlock(Blocks.birch_fence_gate);
        registerItemBlock(Blocks.jungle_fence_gate);
        registerItemBlock(Blocks.dark_oak_fence_gate);
        registerItemBlock(Blocks.acacia_fence_gate);
        registerItemBlock(Blocks.brick_stairs);
        registerItemBlock(Blocks.stone_brick_stairs);
        registerItemBlock(Blocks.mycelium);
        registerItemBlock(Blocks.waterlily, new ItemLilyPad(Blocks.waterlily));
        registerItemBlock(Blocks.nether_brick);
        registerItemBlock(Blocks.nether_brick_fence);
        registerItemBlock(Blocks.nether_brick_stairs);
        registerItemBlock(Blocks.enchanting_table);
        registerItemBlock(Blocks.end_portal_frame);
        registerItemBlock(Blocks.end_stone);
        registerItemBlock(Blocks.dragon_egg);
        registerItemBlock(Blocks.redstone_lamp);
        registerItemBlock(
                Blocks.wooden_slab,
                new ItemSlab(
                                Blocks.wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab)
                        .aO("woodSlab"));
        registerItemBlock(Blocks.sandstone_stairs);
        registerItemBlock(Blocks.emerald_ore);
        registerItemBlock(Blocks.ender_chest);
        registerItemBlock(Blocks.tripwire_hook);
        registerItemBlock(Blocks.emerald_block);
        registerItemBlock(Blocks.spruce_stairs);
        registerItemBlock(Blocks.birch_stairs);
        registerItemBlock(Blocks.jungle_stairs);
        registerItemBlock(Blocks.command_block);
        registerItemBlock(Blocks.beacon);
        registerItemBlock(
                Blocks.cobblestone_wall,
                new ItemMultiTexture(
                                Blocks.cobblestone_wall,
                                Blocks.cobblestone_wall,
                                new ItemRegisterItemsFunction14())
                        .aO("cobbleWall"));
        registerItemBlock(Blocks.wooden_button);
        registerItemBlock(Blocks.anvil, new ItemAnvilBlock(Blocks.anvil).aO("anvil"));
        registerItemBlock(Blocks.trapped_chest);
        registerItemBlock(Blocks.light_weighted_pressure_plate);
        registerItemBlock(Blocks.heavy_weighted_pressure_plate);
        registerItemBlock(Blocks.daylight_detector);
        registerItemBlock(Blocks.redstone_block);
        registerItemBlock(Blocks.quartz_ore);
        registerItemBlock(Blocks.hopper);
        registerItemBlock(
                Blocks.quartz_block,
                new ItemMultiTexture(
                                Blocks.quartz_block,
                                Blocks.quartz_block,
                                new String[] {"default", "chiseled", "lines"})
                        .aO("quartzBlock"));
        registerItemBlock(Blocks.quartz_stairs);
        registerItemBlock(Blocks.activator_rail);
        registerItemBlock(Blocks.dropper);
        registerItemBlock(
                Blocks.stained_hardened_clay,
                new ItemCloth(Blocks.stained_hardened_clay)
                        .aO("clayHardenedStained"));
        registerItemBlock(Blocks.barrier);
        registerItemBlock(Blocks.iron_trapdoor);
        registerItemBlock(Blocks.hay_block);
        registerItemBlock(
                Blocks.carpet, new ItemCloth(Blocks.carpet).aO("woolCarpet"));
        registerItemBlock(Blocks.hardened_clay);
        registerItemBlock(Blocks.coal_block);
        registerItemBlock(Blocks.packed_ice);
        registerItemBlock(Blocks.acacia_stairs);
        registerItemBlock(Blocks.dark_oak_stairs);
        registerItemBlock(Blocks.slime_block);
        registerItemBlock(
                Blocks.double_plant,
                new ItemDoublePlant(
                                Blocks.double_plant,
                                Blocks.double_plant,
                                new ItemRegisterItemsFunction12())
                        .aO("doublePlant"));
        registerItemBlock(
                Blocks.stained_glass,
                new ItemCloth(Blocks.stained_glass).aO("stainedGlass"));
        registerItemBlock(
                Blocks.stained_glass_pane,
                new ItemCloth(Blocks.stained_glass_pane).aO("stainedGlassPane"));
        registerItemBlock(
                Blocks.prismarine,
                new ItemMultiTexture(
                                Blocks.prismarine,
                                Blocks.prismarine,
                                new ItemRegisterItemsFunction())
                        .aO("prismarine"));
        registerItemBlock(Blocks.sea_lantern);
        registerItemBlock(
                Blocks.red_sandstone,
                new ItemMultiTexture(
                                Blocks.red_sandstone,
                                Blocks.red_sandstone,
                                new ItemRegisterItemsFunction8())
                        .aO("redSandStone"));
        registerItemBlock(Blocks.red_sandstone_stairs);
        registerItemBlock(
                Blocks.stone_slab2,
                new ItemSlab(
                                Blocks.stone_slab2, Blocks.stone_slab2, Blocks.double_stone_slab2)
                        .aO("stoneSlab2"));
        registerItem(
                256,
                "iron_shovel",
                new ItemSpade(ItemToolMaterial.IRON).setUnlocalizedName("shovelIron"));
        registerItem(
                257,
                "iron_pickaxe",
                new ItemPickaxe(ItemToolMaterial.IRON).setUnlocalizedName("pickaxeIron"));
        registerItem(
                258,
                "iron_axe",
                new ItemAxe(ItemToolMaterial.IRON).setUnlocalizedName("hatchetIron"));
        registerItem(
                259,
                "flint_and_steel",
                new ItemFlintAndSteel().setUnlocalizedName("flintAndSteel"));
        registerItem(260, "apple", new ItemFood(4, 0.3F, false).setUnlocalizedName("apple"));
        registerItem(261, "bow", new ItemBow().setUnlocalizedName("bow"));
        registerItem(
                262,
                "arrow",
                new Item().setUnlocalizedName("arrow").setCreativeTab(CreativeTabs.tabCombat));
        registerItem(263, "coal", new ItemCoal().setUnlocalizedName("coal"));
        registerItem(
                264,
                "diamond",
                new Item().setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                265,
                "iron_ingot",
                new Item()
                        .setUnlocalizedName("ingotIron")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                266,
                "gold_ingot",
                new Item()
                        .setUnlocalizedName("ingotGold")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                267,
                "iron_sword",
                new SwordItem(ItemToolMaterial.IRON).setUnlocalizedName("swordIron"));
        registerItem(
                268,
                "wooden_sword",
                new SwordItem(ItemToolMaterial.WOOD).setUnlocalizedName("swordWood"));
        registerItem(
                269,
                "wooden_shovel",
                new ItemSpade(ItemToolMaterial.WOOD).setUnlocalizedName("shovelWood"));
        registerItem(
                270,
                "wooden_pickaxe",
                new ItemPickaxe(ItemToolMaterial.WOOD).setUnlocalizedName("pickaxeWood"));
        registerItem(
                271,
                "wooden_axe",
                new ItemAxe(ItemToolMaterial.WOOD).setUnlocalizedName("hatchetWood"));
        registerItem(
                272,
                "stone_sword",
                new SwordItem(ItemToolMaterial.STONE).setUnlocalizedName("swordStone"));
        registerItem(
                273,
                "stone_shovel",
                new ItemSpade(ItemToolMaterial.STONE).setUnlocalizedName("shovelStone"));
        registerItem(
                274,
                "stone_pickaxe",
                new ItemPickaxe(ItemToolMaterial.STONE).setUnlocalizedName("pickaxeStone"));
        registerItem(
                275,
                "stone_axe",
                new ItemAxe(ItemToolMaterial.STONE).setUnlocalizedName("hatchetStone"));
        registerItem(
                276,
                "diamond_sword",
                new SwordItem(ItemToolMaterial.EMERALD).setUnlocalizedName("swordDiamond"));
        registerItem(
                277,
                "diamond_shovel",
                new ItemSpade(ItemToolMaterial.EMERALD).setUnlocalizedName("shovelDiamond"));
        registerItem(
                278,
                "diamond_pickaxe",
                new ItemPickaxe(ItemToolMaterial.EMERALD).setUnlocalizedName("pickaxeDiamond"));
        registerItem(
                279,
                "diamond_axe",
                new ItemAxe(ItemToolMaterial.EMERALD).setUnlocalizedName("hatchetDiamond"));
        registerItem(
                280,
                "stick",
                new Item()
                        .setFull3D()
                        .setUnlocalizedName("stick")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                281,
                "bowl",
                new Item().setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(282, "mushroom_stew", new ItemSoup(6).setUnlocalizedName("mushroomStew"));
        registerItem(
                283,
                "golden_sword",
                new SwordItem(ItemToolMaterial.GOLD).setUnlocalizedName("swordGold"));
        registerItem(
                284,
                "golden_shovel",
                new ItemSpade(ItemToolMaterial.GOLD).setUnlocalizedName("shovelGold"));
        registerItem(
                285,
                "golden_pickaxe",
                new ItemPickaxe(ItemToolMaterial.GOLD).setUnlocalizedName("pickaxeGold"));
        registerItem(
                286,
                "golden_axe",
                new ItemAxe(ItemToolMaterial.GOLD).setUnlocalizedName("hatchetGold"));
        registerItem(
                287,
                "string",
                new ItemReed(Blocks.tripwire)
                        .setUnlocalizedName("string")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                288,
                "feather",
                new Item().setUnlocalizedName("feather").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                289,
                "gunpowder",
                new Item()
                        .setUnlocalizedName("sulphur")
                        .setPotionEffect(PotionHelper.gunpowderEffect)
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                290,
                "wooden_hoe",
                new ItemHoe(ItemToolMaterial.WOOD).setUnlocalizedName("hoeWood"));
        registerItem(
                291,
                "stone_hoe",
                new ItemHoe(ItemToolMaterial.STONE).setUnlocalizedName("hoeStone"));
        registerItem(
                292, "iron_hoe", new ItemHoe(ItemToolMaterial.IRON).setUnlocalizedName("hoeIron"));
        registerItem(
                293,
                "diamond_hoe",
                new ItemHoe(ItemToolMaterial.EMERALD).setUnlocalizedName("hoeDiamond"));
        registerItem(
                294,
                "golden_hoe",
                new ItemHoe(ItemToolMaterial.GOLD).setUnlocalizedName("hoeGold"));
        registerItem(
                295,
                "wheat_seeds",
                new ItemSeeds(Blocks.wheat, Blocks.farmland).setUnlocalizedName("seeds"));
        registerItem(
                296,
                "wheat",
                new Item().setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(297, "bread", new ItemFood(5, 0.6F, false).setUnlocalizedName("bread"));
        registerItem(
                298,
                "leather_helmet",
                new ItemArmor(ItemArmorArmorMaterial.LEATHER, 0, 0)
                        .setUnlocalizedName("helmetCloth"));
        registerItem(
                299,
                "leather_chestplate",
                new ItemArmor(ItemArmorArmorMaterial.LEATHER, 0, 1)
                        .setUnlocalizedName("chestplateCloth"));
        registerItem(
                300,
                "leather_leggings",
                new ItemArmor(ItemArmorArmorMaterial.LEATHER, 0, 2)
                        .setUnlocalizedName("leggingsCloth"));
        registerItem(
                301,
                "leather_boots",
                new ItemArmor(ItemArmorArmorMaterial.LEATHER, 0, 3)
                        .setUnlocalizedName("bootsCloth"));
        registerItem(
                302,
                "chainmail_helmet",
                new ItemArmor(ItemArmorArmorMaterial.CHAIN, 1, 0)
                        .setUnlocalizedName("helmetChain"));
        registerItem(
                303,
                "chainmail_chestplate",
                new ItemArmor(ItemArmorArmorMaterial.CHAIN, 1, 1)
                        .setUnlocalizedName("chestplateChain"));
        registerItem(
                304,
                "chainmail_leggings",
                new ItemArmor(ItemArmorArmorMaterial.CHAIN, 1, 2)
                        .setUnlocalizedName("leggingsChain"));
        registerItem(
                305,
                "chainmail_boots",
                new ItemArmor(ItemArmorArmorMaterial.CHAIN, 1, 3).setUnlocalizedName("bootsChain"));
        registerItem(
                306,
                "iron_helmet",
                new ItemArmor(ItemArmorArmorMaterial.IRON, 2, 0).setUnlocalizedName("helmetIron"));
        registerItem(
                307,
                "iron_chestplate",
                new ItemArmor(ItemArmorArmorMaterial.IRON, 2, 1)
                        .setUnlocalizedName("chestplateIron"));
        registerItem(
                308,
                "iron_leggings",
                new ItemArmor(ItemArmorArmorMaterial.IRON, 2, 2)
                        .setUnlocalizedName("leggingsIron"));
        registerItem(
                309,
                "iron_boots",
                new ItemArmor(ItemArmorArmorMaterial.IRON, 2, 3).setUnlocalizedName("bootsIron"));
        registerItem(
                310,
                "diamond_helmet",
                new ItemArmor(ItemArmorArmorMaterial.DIAMOND, 3, 0)
                        .setUnlocalizedName("helmetDiamond"));
        registerItem(
                311,
                "diamond_chestplate",
                new ItemArmor(ItemArmorArmorMaterial.DIAMOND, 3, 1)
                        .setUnlocalizedName("chestplateDiamond"));
        registerItem(
                312,
                "diamond_leggings",
                new ItemArmor(ItemArmorArmorMaterial.DIAMOND, 3, 2)
                        .setUnlocalizedName("leggingsDiamond"));
        registerItem(
                313,
                "diamond_boots",
                new ItemArmor(ItemArmorArmorMaterial.DIAMOND, 3, 3)
                        .setUnlocalizedName("bootsDiamond"));
        registerItem(
                314,
                "golden_helmet",
                new ItemArmor(ItemArmorArmorMaterial.GOLD, 4, 0).setUnlocalizedName("helmetGold"));
        registerItem(
                315,
                "golden_chestplate",
                new ItemArmor(ItemArmorArmorMaterial.GOLD, 4, 1)
                        .setUnlocalizedName("chestplateGold"));
        registerItem(
                316,
                "golden_leggings",
                new ItemArmor(ItemArmorArmorMaterial.GOLD, 4, 2)
                        .setUnlocalizedName("leggingsGold"));
        registerItem(
                317,
                "golden_boots",
                new ItemArmor(ItemArmorArmorMaterial.GOLD, 4, 3).setUnlocalizedName("bootsGold"));
        registerItem(
                318,
                "flint",
                new Item().setUnlocalizedName("flint").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                319, "porkchop", new ItemFood(3, 0.3F, true).setUnlocalizedName("porkchopRaw"));
        registerItem(
                320,
                "cooked_porkchop",
                new ItemFood(8, 0.8F, true).setUnlocalizedName("porkchopCooked"));
        registerItem(
                321,
                "painting",
                new ItemHangingEntity(EntityPainting.class).setUnlocalizedName("painting"));
        registerItem(
                322,
                "golden_apple",
                new ItemAppleGold(4, 1.2F, false)
                        .setAlwaysEdible()
                        .setPotionEffect(Potion.REGENERATION.id, 5, 1, 1.0F)
                        .setUnlocalizedName("appleGold"));
        registerItem(323, "sign", new ItemSign().setUnlocalizedName("sign"));
        registerItem(
                324, "wooden_door", new ItemDoor(Blocks.oak_door).setUnlocalizedName("doorOak"));
        Item item = new ItemBucket(Blocks.air).setUnlocalizedName("bucket").setMaxStackSize(16);
        registerItem(325, "bucket", item);
        registerItem(
                326,
                "water_bucket",
                new ItemBucket(Blocks.flowing_water)
                        .setUnlocalizedName("bucketWater")
                        .setContainerItem(item));
        registerItem(
                327,
                "lava_bucket",
                new ItemBucket(Blocks.flowing_lava)
                        .setUnlocalizedName("bucketLava")
                        .setContainerItem(item));
        registerItem(
                328,
                "minecart",
                new ItemMinecart(EntityMinecartEnumMinecartType.RIDEABLE)
                        .setUnlocalizedName("minecart"));
        registerItem(329, "saddle", new ItemSaddle().setUnlocalizedName("saddle"));
        registerItem(
                330, "iron_door", new ItemDoor(Blocks.iron_door).setUnlocalizedName("doorIron"));
        registerItem(
                331,
                "redstone",
                new ItemRedstone()
                        .setUnlocalizedName("redstone")
                        .setPotionEffect(PotionHelper.redstoneEffect));
        registerItem(332, "snowball", new ItemSnowball().setUnlocalizedName("snowball"));
        registerItem(333, "boat", new ItemBoat().setUnlocalizedName("boat"));
        registerItem(
                334,
                "leather",
                new Item().setUnlocalizedName("leather").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                335,
                "milk_bucket",
                new ItemBucketMilk().setUnlocalizedName("milk").setContainerItem(item));
        registerItem(
                336,
                "brick",
                new Item().setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                337,
                "clay_ball",
                new Item().setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                338,
                "reeds",
                new ItemReed(Blocks.reeds)
                        .setUnlocalizedName("reeds")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                339,
                "paper",
                new Item().setUnlocalizedName("paper").setCreativeTab(CreativeTabs.tabMisc));
        registerItem(
                340,
                "book",
                new ItemBook().setUnlocalizedName("book").setCreativeTab(CreativeTabs.tabMisc));
        registerItem(
                341,
                "slime_ball",
                new Item().setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.tabMisc));
        registerItem(
                342,
                "chest_minecart",
                new ItemMinecart(EntityMinecartEnumMinecartType.CHEST)
                        .setUnlocalizedName("minecartChest"));
        registerItem(
                343,
                "furnace_minecart",
                new ItemMinecart(EntityMinecartEnumMinecartType.FURNACE)
                        .setUnlocalizedName("minecartFurnace"));
        registerItem(344, "egg", new ItemEgg().setUnlocalizedName("egg"));
        registerItem(
                345,
                "compass",
                new Item().setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools));
        registerItem(346, "fishing_rod", new ItemFishingRod().setUnlocalizedName("fishingRod"));
        registerItem(
                347,
                "clock",
                new Item().setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools));
        registerItem(
                348,
                "glowstone_dust",
                new Item()
                        .setUnlocalizedName("yellowDust")
                        .setPotionEffect(PotionHelper.glowstoneEffect)
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                349,
                "fish",
                new ItemFishFood(false).setUnlocalizedName("fish").setHasSubtypes(true));
        registerItem(
                350,
                "cooked_fish",
                new ItemFishFood(true).setUnlocalizedName("fish").setHasSubtypes(true));
        registerItem(351, "dye", new ItemDye().setUnlocalizedName("dyePowder"));
        registerItem(
                352,
                "bone",
                new Item()
                        .setUnlocalizedName("bone")
                        .setFull3D()
                        .setCreativeTab(CreativeTabs.tabMisc));
        registerItem(
                353,
                "sugar",
                new Item()
                        .setUnlocalizedName("sugar")
                        .setPotionEffect(PotionHelper.sugarEffect)
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                354,
                "cake",
                new ItemReed(Blocks.cake)
                        .setMaxStackSize(1)
                        .setUnlocalizedName("cake")
                        .setCreativeTab(CreativeTabs.tabFood));
        registerItem(355, "bed", new ItemBed().setMaxStackSize(1).setUnlocalizedName("bed"));
        registerItem(
                356,
                "repeater",
                new ItemReed(Blocks.unpowered_repeater)
                        .setUnlocalizedName("diode")
                        .setCreativeTab(CreativeTabs.tabRedstone));
        registerItem(357, "cookie", new ItemFood(2, 0.1F, false).setUnlocalizedName("cookie"));
        registerItem(358, "filled_map", new ItemMap().setUnlocalizedName("map"));
        registerItem(359, "shears", new ItemShears().setUnlocalizedName("shears"));
        registerItem(360, "melon", new ItemFood(2, 0.3F, false).setUnlocalizedName("melon"));
        registerItem(
                361,
                "pumpkin_seeds",
                new ItemSeeds(Blocks.pumpkin_stem, Blocks.farmland)
                        .setUnlocalizedName("seeds_pumpkin"));
        registerItem(
                362,
                "melon_seeds",
                new ItemSeeds(Blocks.melon_stem, Blocks.farmland)
                        .setUnlocalizedName("seeds_melon"));
        registerItem(363, "beef", new ItemFood(3, 0.3F, true).setUnlocalizedName("beefRaw"));
        registerItem(
                364, "cooked_beef", new ItemFood(8, 0.8F, true).setUnlocalizedName("beefCooked"));
        registerItem(
                365,
                "chicken",
                new ItemFood(2, 0.3F, true)
                        .setPotionEffect(Potion.HUNGER.id, 30, 0, 0.3F)
                        .setUnlocalizedName("chickenRaw"));
        registerItem(
                366,
                "cooked_chicken",
                new ItemFood(6, 0.6F, true).setUnlocalizedName("chickenCooked"));
        registerItem(
                367,
                "rotten_flesh",
                new ItemFood(4, 0.1F, true)
                        .setPotionEffect(Potion.HUNGER.id, 30, 0, 0.8F)
                        .setUnlocalizedName("rottenFlesh"));
        registerItem(368, "ender_pearl", new ItemEnderPearl().setUnlocalizedName("enderPearl"));
        registerItem(
                369,
                "blaze_rod",
                new Item()
                        .setUnlocalizedName("blazeRod")
                        .setCreativeTab(CreativeTabs.tabMaterials)
                        .setFull3D());
        registerItem(
                370,
                "ghast_tear",
                new Item()
                        .setUnlocalizedName("ghastTear")
                        .setPotionEffect("+0-1-2-3&4-4+13")
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(
                371,
                "gold_nugget",
                new Item()
                        .setUnlocalizedName("goldNugget")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                372,
                "nether_wart",
                new ItemSeeds(Blocks.nether_wart, Blocks.soul_sand)
                        .setUnlocalizedName("netherStalkSeeds")
                        .setPotionEffect("+4"));
        registerItem(373, "potion", new ItemPotion().setUnlocalizedName("potion"));
        registerItem(374, "glass_bottle", new ItemGlassBottle().setUnlocalizedName("glassBottle"));
        registerItem(
                375,
                "spider_eye",
                new ItemFood(2, 0.8F, false)
                        .setPotionEffect(Potion.POISON.id, 5, 0, 1.0F)
                        .setUnlocalizedName("spiderEye")
                        .setPotionEffect(PotionHelper.spiderEyeEffect));
        registerItem(
                376,
                "fermented_spider_eye",
                new Item()
                        .setUnlocalizedName("fermentedSpiderEye")
                        .setPotionEffect(PotionHelper.fermentedSpiderEyeEffect)
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(
                377,
                "blaze_powder",
                new Item()
                        .setUnlocalizedName("blazePowder")
                        .setPotionEffect(PotionHelper.blazePowderEffect)
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(
                378,
                "magma_cream",
                new Item()
                        .setUnlocalizedName("magmaCream")
                        .setPotionEffect(PotionHelper.magmaCreamEffect)
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(
                379,
                "brewing_stand",
                new ItemReed(Blocks.brewing_stand)
                        .setUnlocalizedName("brewingStand")
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(
                380,
                "cauldron",
                new ItemReed(Blocks.cauldron)
                        .setUnlocalizedName("cauldron")
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(381, "ender_eye", new ItemEnderEye().setUnlocalizedName("eyeOfEnder"));
        registerItem(
                382,
                "speckled_melon",
                new Item()
                        .setUnlocalizedName("speckledMelon")
                        .setPotionEffect(PotionHelper.speckledMelonEffect)
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(383, "spawn_egg", new ItemMonsterPlacer().setUnlocalizedName("monsterPlacer"));
        registerItem(384, "experience_bottle", new ItemExpBottle().setUnlocalizedName("expBottle"));
        registerItem(385, "fire_charge", new ItemFireball().setUnlocalizedName("fireball"));
        registerItem(
                386,
                "writable_book",
                new ItemWritableBook()
                        .setUnlocalizedName("writingBook")
                        .setCreativeTab(CreativeTabs.tabMisc));
        registerItem(
                387,
                "written_book",
                new ItemEditableBook().setUnlocalizedName("writtenBook").setMaxStackSize(16));
        registerItem(
                388,
                "emerald",
                new Item().setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                389,
                "item_frame",
                new ItemHangingEntity(EntityItemFrame.class).setUnlocalizedName("frame"));
        registerItem(
                390,
                "flower_pot",
                new ItemReed(Blocks.flower_pot)
                        .setUnlocalizedName("flowerPot")
                        .setCreativeTab(CreativeTabs.tabDecorations));
        registerItem(
                391,
                "carrot",
                new ItemSeedFood(3, 0.6F, Blocks.carrots, Blocks.farmland)
                        .setUnlocalizedName("carrots"));
        registerItem(
                392,
                "potato",
                new ItemSeedFood(1, 0.3F, Blocks.potatoes, Blocks.farmland)
                        .setUnlocalizedName("potato"));
        registerItem(
                393,
                "baked_potato",
                new ItemFood(5, 0.6F, false).setUnlocalizedName("potatoBaked"));
        registerItem(
                394,
                "poisonous_potato",
                new ItemFood(2, 0.3F, false)
                        .setPotionEffect(Potion.POISON.id, 5, 0, 0.6F)
                        .setUnlocalizedName("potatoPoisonous"));
        registerItem(395, "map", new ItemEmptyMap().setUnlocalizedName("emptyMap"));
        registerItem(
                396,
                "golden_carrot",
                new ItemFood(6, 1.2F, false)
                        .setUnlocalizedName("carrotGolden")
                        .setPotionEffect(PotionHelper.goldenCarrotEffect)
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(397, "skull", new ItemSkull().setUnlocalizedName("skull"));
        registerItem(
                398,
                "carrot_on_a_stick",
                new ItemCarrotOnAStick().setUnlocalizedName("carrotOnAStick"));
        registerItem(
                399,
                "nether_star",
                new ItemSimpleFoiled()
                        .setUnlocalizedName("netherStar")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                400,
                "pumpkin_pie",
                new ItemFood(8, 0.3F, false)
                        .setUnlocalizedName("pumpkinPie")
                        .setCreativeTab(CreativeTabs.tabFood));
        registerItem(401, "fireworks", new ItemFirework().setUnlocalizedName("fireworks"));
        registerItem(
                402,
                "firework_charge",
                new ItemFireworkCharge()
                        .setUnlocalizedName("fireworksCharge")
                        .setCreativeTab(CreativeTabs.tabMisc));
        registerItem(
                403,
                "enchanted_book",
                new ItemEnchantedBook().setMaxStackSize(1).setUnlocalizedName("enchantedBook"));
        registerItem(
                404,
                "comparator",
                new ItemReed(Blocks.unpowered_comparator)
                        .setUnlocalizedName("comparator")
                        .setCreativeTab(CreativeTabs.tabRedstone));
        registerItem(
                405,
                "netherbrick",
                new Item()
                        .setUnlocalizedName("netherbrick")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                406,
                "quartz",
                new Item()
                        .setUnlocalizedName("netherquartz")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                407,
                "tnt_minecart",
                new ItemMinecart(EntityMinecartEnumMinecartType.TNT)
                        .setUnlocalizedName("minecartTnt"));
        registerItem(
                408,
                "hopper_minecart",
                new ItemMinecart(EntityMinecartEnumMinecartType.HOPPER)
                        .setUnlocalizedName("minecartHopper"));
        registerItem(
                409,
                "prismarine_shard",
                new Item()
                        .setUnlocalizedName("prismarineShard")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                410,
                "prismarine_crystals",
                new Item()
                        .setUnlocalizedName("prismarineCrystals")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(411, "rabbit", new ItemFood(3, 0.3F, true).setUnlocalizedName("rabbitRaw"));
        registerItem(
                412,
                "cooked_rabbit",
                new ItemFood(5, 0.6F, true).setUnlocalizedName("rabbitCooked"));
        registerItem(413, "rabbit_stew", new ItemSoup(10).setUnlocalizedName("rabbitStew"));
        registerItem(
                414,
                "rabbit_foot",
                new Item()
                        .setUnlocalizedName("rabbitFoot")
                        .setPotionEffect(PotionHelper.rabbitFootEffect)
                        .setCreativeTab(CreativeTabs.tabBrewing));
        registerItem(
                415,
                "rabbit_hide",
                new Item()
                        .setUnlocalizedName("rabbitHide")
                        .setCreativeTab(CreativeTabs.tabMaterials));
        registerItem(
                416,
                "armor_stand",
                new ItemArmorStand().setUnlocalizedName("armorStand").setMaxStackSize(16));
        registerItem(
                417,
                "iron_horse_armor",
                new Item()
                        .setUnlocalizedName("horsearmormetal")
                        .setMaxStackSize(1)
                        .setCreativeTab(CreativeTabs.tabMisc));
        registerItem(
                418,
                "golden_horse_armor",
                new Item()
                        .setUnlocalizedName("horsearmorgold")
                        .setMaxStackSize(1)
                        .setCreativeTab(CreativeTabs.tabMisc));
        registerItem(
                419,
                "diamond_horse_armor",
                new Item()
                        .setUnlocalizedName("horsearmordiamond")
                        .setMaxStackSize(1)
                        .setCreativeTab(CreativeTabs.tabMisc));
        registerItem(420, "lead", new ItemLead().setUnlocalizedName("leash"));
        registerItem(421, "name_tag", new ItemNameTag().setUnlocalizedName("nameTag"));
        registerItem(
                422,
                "command_block_minecart",
                new ItemMinecart(EntityMinecartEnumMinecartType.COMMAND_BLOCK)
                        .setUnlocalizedName("minecartCommandBlock")
                        .setCreativeTab((CreativeTabs) null));
        registerItem(423, "mutton", new ItemFood(2, 0.3F, true).setUnlocalizedName("muttonRaw"));
        registerItem(
                424,
                "cooked_mutton",
                new ItemFood(6, 0.8F, true).setUnlocalizedName("muttonCooked"));
        registerItem(425, "banner", new ItemBanner().aO("banner"));
        registerItem(
                427,
                "spruce_door",
                new ItemDoor(Blocks.spruce_door).setUnlocalizedName("doorSpruce"));
        registerItem(
                428, "birch_door", new ItemDoor(Blocks.birch_door).setUnlocalizedName("doorBirch"));
        registerItem(
                429,
                "jungle_door",
                new ItemDoor(Blocks.jungle_door).setUnlocalizedName("doorJungle"));
        registerItem(
                430,
                "acacia_door",
                new ItemDoor(Blocks.acacia_door).setUnlocalizedName("doorAcacia"));
        registerItem(
                431,
                "dark_oak_door",
                new ItemDoor(Blocks.dark_oak_door).setUnlocalizedName("doorDarkOak"));
        registerItem(2256, "record_13", new ItemRecord("13").setUnlocalizedName("record"));
        registerItem(2257, "record_cat", new ItemRecord("cat").setUnlocalizedName("record"));
        registerItem(2258, "record_blocks", new ItemRecord("blocks").setUnlocalizedName("record"));
        registerItem(2259, "record_chirp", new ItemRecord("chirp").setUnlocalizedName("record"));
        registerItem(2260, "record_far", new ItemRecord("far").setUnlocalizedName("record"));
        registerItem(2261, "record_mall", new ItemRecord("mall").setUnlocalizedName("record"));
        registerItem(
                2262, "record_mellohi", new ItemRecord("mellohi").setUnlocalizedName("record"));
        registerItem(2263, "record_stal", new ItemRecord("stal").setUnlocalizedName("record"));
        registerItem(2264, "record_strad", new ItemRecord("strad").setUnlocalizedName("record"));
        registerItem(2265, "record_ward", new ItemRecord("ward").setUnlocalizedName("record"));
        registerItem(2266, "record_11", new ItemRecord("11").setUnlocalizedName("record"));
        registerItem(2267, "record_wait", new ItemRecord("wait").setUnlocalizedName("record"));
        if (textValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    // region String decoding support

    // endregion

    private static NumberFormatException method_a0514df28616b2633822(
            NumberFormatException numberformatexception) {
        return numberformatexception;
    }

    public boolean canItemEditBlocks() {
        return false;
    }

    public Item setMaxStackSize(int maxStackSizeValue) {
        this.maxStackSize = maxStackSizeValue;
        return this;
    }

    public CreativeTabs getCreativeTab() {
        return this.tabToDisplayOn;
    }

    public static int getLegacyItemFlowMarker() {
        return net.minecraft.item.Item.legacyItemFlowMarker;
    }

    public static void setLegacyItemFlowMarker(int i) {
        net.minecraft.item.Item.legacyItemFlowMarker = i;
    }

    protected Item setHasSubtypes(boolean hasSubtypesValue) {
        this.hasSubtypes = hasSubtypesValue;
        return this;
    }

    private static void registerItem(int i, String text, Item item) {
        registerItem(i, new ResourceLocation(text), item);
    }

    public boolean canHarvestBlock(Block block) {
        return false;
    }

    public boolean isMap() {
        return false;
    }

    public static int getIdFromItem(Item item) {
        return item == null ? 0 : net.minecraft.item.Item.itemRegistry.getIDForObject(item);
    }

    public int getItemEnchantability() {
        return 0;
    }

    public boolean hitEntity(
            ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntityValue) {
        return false;
    }

    public Item setCreativeTab(CreativeTabs aYlValue) {
        this.tabToDisplayOn = aYlValue;
        return this;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return "item." + this.unlocalizedName;
    }

    public Item getContainerItem() {
        return this.containerItem;
    }

    public boolean isDamageable() {
        return this.maxDamage > 0 && !this.hasSubtypes;
    }

    public void onCreated(ItemStack itemStack, World world, PlayerEntity playerEntity) {}

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        return itemStack;
    }

    public int getMaxItemUseDuration(ItemStack itemStack) {
        return 0;
    }

    public void onPlayerStoppedUsing(
            ItemStack itemStack, World world, PlayerEntity playerEntity, int i) {}

    // region String decoding support

    // endregion

    private static void registerItem(int i, ResourceLocation resourceLocation, Item item) {
        net.minecraft.item.Item.itemRegistry.register(i, resourceLocation, item);
    }

    public String getUnlocalizedName() {
        return "item." + this.unlocalizedName;
    }

    public boolean getHasSubtypes() {
        return this.hasSubtypes;
    }

    // region Static initialization and string tables
    static {
        net.minecraft.item.Item.packedTextFragments = new String[3];
        net.minecraft.item.Item.packedTextFragments[0] = new String("");
        net.minecraft.item.Item.packedTextFragments[1] = new String("");
        setLegacyItemFlowMarker(119);
        net.minecraft.item.Item.itemRegistry = new RegistryNamespaced();
        net.minecraft.item.Item.BLOCK_TO_ITEM = Maps.newHashMap();
        net.minecraft.item.Item.itemModifierUUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
        net.minecraft.item.Item.itemRand = new Random();
    }

    // endregion

    public boolean aR() {
        return true;
    }

    public int getMetadata(int i) {
        return 0;
    }

    protected RayTraceResult getMovingObjectPositionFromPlayer(
            World world, PlayerEntity playerEntity, boolean flag) {
        float fx =
                playerEntity.prevRotationPitch
                        + (playerEntity.rotationPitch - playerEntity.prevRotationPitch);
        float f1 =
                playerEntity.prevRotationYaw
                        + (playerEntity.rotationYaw - playerEntity.prevRotationYaw);
        double d0 = playerEntity.prevPosX + (playerEntity.posX - playerEntity.prevPosX);
        double d1 =
                playerEntity.prevPosY
                        + (playerEntity.posY - playerEntity.prevPosY)
                        + playerEntity.getEyeHeight();
        double d2 = playerEntity.prevPosZ + (playerEntity.posZ - playerEntity.prevPosZ);
        Vec3 vec3 = new Vec3(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f4 = -MathHelper.cos(-fx * (float) (Math.PI / 180.0));
        float f5 = MathHelper.sin(-fx * (float) (Math.PI / 180.0));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = 5.0;
        Vec3 vec3Value = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
        return world.rayTraceBlocks(vec3, vec3Value, flag, !flag, false);
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        return (packedTextFragments[1]
                        + StatCollector.translateToLocal(
                                this.getUnlocalizedNameInefficiently(itemStack) + ".name"))
                .trim();
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        return false;
    }

    public Item setContainerItem(Item brdValue) {
        this.containerItem = brdValue;
        return this;
    }

    public static Item getItemById(int i) {
        return (Item) net.minecraft.item.Item.itemRegistry.getObjectById(i);
    }

    public boolean isItemTool(ItemStack itemStack) {
        return this.getItemStackLimit() == 1 && this.isDamageable();
    }

    public static Item getByNameOrId(String text) {
        Item item = (Item) net.minecraft.item.Item.itemRegistry.getObject(new ResourceLocation(text));
        if (item == null) {
            try {
                return getItemById(Integer.parseInt(text));
            } catch (NumberFormatException numberformatexception) {
            }
        }

        return item;
    }

    public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean flag) {}

    public boolean isFull3D() {
        return this.bFull3D;
    }

    public String getUnlocalizedNameInefficiently(ItemStack itemStack) {
        String text = this.getUnlocalizedName(itemStack);
        return text == null ? packedTextFragments[0] : StatCollector.translateToLocal(text);
    }

    public ItemStack onItemUseFinish(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        return itemStack;
    }

    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStackValue) {
        return false;
    }

    private static void registerItemBlock(Block block) {
        registerItemBlock(block, new ItemBlock(block));
    }

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        return 16777215;
    }

    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.NONE;
    }

    public boolean itemInteractionForEntity(
            ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity) {
        return false;
    }

    protected Item setPotionEffect(String text) {
        this.potionEffect = text;
        return this;
    }

    public boolean onBlockDestroyed(
            ItemStack itemStack,
            World world,
            Block block,
            BlockPos blockPos,
            LivingEntity livingEntity) {
        return false;
    }

    public static int xP() {
        int i = getLegacyItemFlowMarker();
        return i == 0 ? 25 : 0;
    }

    public boolean hasContainerItem() {
        return this.containerItem != null;
    }

    public boolean updateItemStackNBT(CompoundTag compoundTag) {
        return false;
    }

    // region String decoding support

    // endregion

    public int getMaxDamage() {
        return this.maxDamage;
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

    public boolean isPotionIngredient(ItemStack itemStack) {
        return this.getPotionEffect(itemStack) != null;
    }

    protected static void registerItemBlock(Block block, Item item) {
        registerItem(
                Block.getIdFromBlock(block),
                (ResourceLocation) Block.blockRegistry.getNameForObject(block),
                item);
        net.minecraft.item.Item.BLOCK_TO_ITEM.put(block, item);
    }

    public boolean shouldRotateAroundWhenRendering() {
        return false;
    }

    public void addInformation(
            ItemStack itemStack, PlayerEntity playerEntity, List list, boolean flag) {}

    public int getItemStackLimit() {
        return this.maxStackSize;
    }

    public String getPotionEffect(ItemStack itemStack) {
        return this.potionEffect;
    }

    public float getStrVsBlock(ItemStack itemStack, Block block) {
        return 1.0F;
    }

    protected Item setMaxDamage(int maxDamageValue) {
        this.maxDamage = maxDamageValue;
        return this;
    }

    public static Item getItemFromBlock(Block block) {
        return (Item) net.minecraft.item.Item.BLOCK_TO_ITEM.get(block);
    }

    public Item setUnlocalizedName(String text) {
        this.unlocalizedName = text;
        return this;
    }
}
