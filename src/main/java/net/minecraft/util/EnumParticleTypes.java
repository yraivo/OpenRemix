package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Map;

public enum EnumParticleTypes {
    EXPLOSION_NORMAL(initializeConstantState(), 0, "explode", 0, true),
    EXPLOSION_LARGE("EXPLOSION_LARGE", 1, "largeexplode", 1, true),
    EXPLOSION_HUGE("EXPLOSION_HUGE", 2, "hugeexplosion", 2, true),
    FIREWORKS_SPARK("FIREWORKS_SPARK", 3, "fireworksSpark", 3, false),
    WATER_BUBBLE("WATER_BUBBLE", 4, "bubble", 4, false),
    WATER_SPLASH("WATER_SPLASH", 5, "splash", 5, false),
    WATER_WAKE("WATER_WAKE", 6, "wake", 6, false),
    SUSPENDED("SUSPENDED", 7, "suspended", 7, false),
    SUSPENDED_DEPTH("SUSPENDED_DEPTH", 8, "depthsuspend", 8, false),
    CRIT("CRIT", 9, "crit", 9, false),
    CRIT_MAGIC("CRIT_MAGIC", 10, "magicCrit", 10, false),
    SMOKE_NORMAL("SMOKE_NORMAL", 11, "smoke", 11, false),
    SMOKE_LARGE("SMOKE_LARGE", 12, "largesmoke", 12, false),
    SPELL("SPELL", 13, "spell", 13, false),
    SPELL_INSTANT("SPELL_INSTANT", 14, "instantSpell", 14, false),
    SPELL_MOB("SPELL_MOB", 15, "mobSpell", 15, false),
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 16, "mobSpellAmbient", 16, false),
    SPELL_WITCH("SPELL_WITCH", 17, "witchMagic", 17, false),
    DRIP_WATER("DRIP_WATER", 18, "dripWater", 18, false),
    DRIP_LAVA("DRIP_LAVA", 19, "dripLava", 19, false),
    VILLAGER_ANGRY("VILLAGER_ANGRY", 20, "angryVillager", 20, false),
    VILLAGER_HAPPY("VILLAGER_HAPPY", 21, "happyVillager", 21, false),
    TOWN_AURA("TOWN_AURA", 22, "townaura", 22, false),
    NOTE("NOTE", 23, "note", 23, false),
    PORTAL("PORTAL", 24, "portal", 24, false),
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 25, "enchantmenttable", 25, false),
    FLAME("FLAME", 26, "flame", 26, false),
    LAVA("LAVA", 27, "lava", 27, false),
    FOOTSTEP("FOOTSTEP", 28, "footstep", 28, false),
    CLOUD("CLOUD", 29, "cloud", 29, false),
    REDSTONE("REDSTONE", 30, "reddust", 30, false),
    SNOWBALL("SNOWBALL", 31, "snowballpoof", 31, false),
    SNOW_SHOVEL("SNOW_SHOVEL", 32, "snowshovel", 32, false),
    SLIME("SLIME", 33, "slime", 33, false),
    HEART("HEART", 34, "heart", 34, false),
    BARRIER("BARRIER", 35, "barrier", 35, false),
    ITEM_CRACK("ITEM_CRACK", 36, "iconcrack_", 36, false, 2),
    BLOCK_CRACK("BLOCK_CRACK", 37, "blockcrack_", 37, false, 1),
    BLOCK_DUST("BLOCK_DUST", 38, "blockdust_", 38, false, 1),
    WATER_DROP("WATER_DROP", 39, "droplet", 39, false),
    ITEM_TAKE("ITEM_TAKE", 40, "take", 40, false),
    MOB_APPEARANCE("MOB_APPEARANCE", 41, "mobappearance", 41, true);
    private static EnumParticleTypes[] Zg;

    private static EnumParticleTypes[] Zf;

    private int argumentCount;
    private static String[] packedTextFragments;
    private static Map PARTICLES;
    private boolean shouldIgnoreRange;
    private String particleName;
    private static String[] PARTICLE_NAMES;
    private int particleID;

    public static EnumParticleTypes getParticleFromId(int i) {
        return (EnumParticleTypes) net.minecraft.util.EnumParticleTypes.PARTICLES.get(i);
    }

    // region String decoding support

    // endregion

    public int getParticleID() {
        return this.particleID;
    }

    static {
        Zf =
                new EnumParticleTypes[] {
                    net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,
                    net.minecraft.util.EnumParticleTypes.EXPLOSION_LARGE,
                    net.minecraft.util.EnumParticleTypes.EXPLOSION_HUGE,
                    net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK,
                    net.minecraft.util.EnumParticleTypes.WATER_BUBBLE,
                    net.minecraft.util.EnumParticleTypes.WATER_SPLASH,
                    net.minecraft.util.EnumParticleTypes.WATER_WAKE,
                    net.minecraft.util.EnumParticleTypes.SUSPENDED,
                    net.minecraft.util.EnumParticleTypes.SUSPENDED_DEPTH,
                    net.minecraft.util.EnumParticleTypes.CRIT,
                    net.minecraft.util.EnumParticleTypes.CRIT_MAGIC,
                    net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL,
                    net.minecraft.util.EnumParticleTypes.SMOKE_LARGE,
                    net.minecraft.util.EnumParticleTypes.SPELL,
                    net.minecraft.util.EnumParticleTypes.SPELL_INSTANT,
                    net.minecraft.util.EnumParticleTypes.SPELL_MOB,
                    net.minecraft.util.EnumParticleTypes.SPELL_MOB_AMBIENT,
                    net.minecraft.util.EnumParticleTypes.SPELL_WITCH,
                    net.minecraft.util.EnumParticleTypes.DRIP_WATER,
                    net.minecraft.util.EnumParticleTypes.DRIP_LAVA,
                    net.minecraft.util.EnumParticleTypes.VILLAGER_ANGRY,
                    net.minecraft.util.EnumParticleTypes.VILLAGER_HAPPY,
                    net.minecraft.util.EnumParticleTypes.TOWN_AURA,
                    net.minecraft.util.EnumParticleTypes.NOTE,
                    net.minecraft.util.EnumParticleTypes.PORTAL,
                    net.minecraft.util.EnumParticleTypes.ENCHANTMENT_TABLE,
                    net.minecraft.util.EnumParticleTypes.FLAME,
                    net.minecraft.util.EnumParticleTypes.LAVA,
                    net.minecraft.util.EnumParticleTypes.FOOTSTEP,
                    net.minecraft.util.EnumParticleTypes.CLOUD,
                    net.minecraft.util.EnumParticleTypes.REDSTONE,
                    net.minecraft.util.EnumParticleTypes.SNOWBALL,
                    net.minecraft.util.EnumParticleTypes.SNOW_SHOVEL,
                    net.minecraft.util.EnumParticleTypes.SLIME,
                    net.minecraft.util.EnumParticleTypes.HEART,
                    net.minecraft.util.EnumParticleTypes.BARRIER,
                    net.minecraft.util.EnumParticleTypes.ITEM_CRACK,
                    net.minecraft.util.EnumParticleTypes.BLOCK_CRACK,
                    net.minecraft.util.EnumParticleTypes.BLOCK_DUST,
                    net.minecraft.util.EnumParticleTypes.WATER_DROP,
                    net.minecraft.util.EnumParticleTypes.ITEM_TAKE,
                    net.minecraft.util.EnumParticleTypes.MOB_APPEARANCE
                };
        Zg =
                new EnumParticleTypes[] {
                    net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL,
                    net.minecraft.util.EnumParticleTypes.EXPLOSION_LARGE,
                    net.minecraft.util.EnumParticleTypes.EXPLOSION_HUGE,
                    net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK,
                    net.minecraft.util.EnumParticleTypes.WATER_BUBBLE,
                    net.minecraft.util.EnumParticleTypes.WATER_SPLASH,
                    net.minecraft.util.EnumParticleTypes.WATER_WAKE,
                    net.minecraft.util.EnumParticleTypes.SUSPENDED,
                    net.minecraft.util.EnumParticleTypes.SUSPENDED_DEPTH,
                    net.minecraft.util.EnumParticleTypes.CRIT,
                    net.minecraft.util.EnumParticleTypes.CRIT_MAGIC,
                    net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL,
                    net.minecraft.util.EnumParticleTypes.SMOKE_LARGE,
                    net.minecraft.util.EnumParticleTypes.SPELL,
                    net.minecraft.util.EnumParticleTypes.SPELL_INSTANT,
                    net.minecraft.util.EnumParticleTypes.SPELL_MOB,
                    net.minecraft.util.EnumParticleTypes.SPELL_MOB_AMBIENT,
                    net.minecraft.util.EnumParticleTypes.SPELL_WITCH,
                    net.minecraft.util.EnumParticleTypes.DRIP_WATER,
                    net.minecraft.util.EnumParticleTypes.DRIP_LAVA,
                    net.minecraft.util.EnumParticleTypes.VILLAGER_ANGRY,
                    net.minecraft.util.EnumParticleTypes.VILLAGER_HAPPY,
                    net.minecraft.util.EnumParticleTypes.TOWN_AURA,
                    net.minecraft.util.EnumParticleTypes.NOTE,
                    net.minecraft.util.EnumParticleTypes.PORTAL,
                    net.minecraft.util.EnumParticleTypes.ENCHANTMENT_TABLE,
                    net.minecraft.util.EnumParticleTypes.FLAME,
                    net.minecraft.util.EnumParticleTypes.LAVA,
                    net.minecraft.util.EnumParticleTypes.FOOTSTEP,
                    net.minecraft.util.EnumParticleTypes.CLOUD,
                    net.minecraft.util.EnumParticleTypes.REDSTONE,
                    net.minecraft.util.EnumParticleTypes.SNOWBALL,
                    net.minecraft.util.EnumParticleTypes.SNOW_SHOVEL,
                    net.minecraft.util.EnumParticleTypes.SLIME,
                    net.minecraft.util.EnumParticleTypes.HEART,
                    net.minecraft.util.EnumParticleTypes.BARRIER,
                    net.minecraft.util.EnumParticleTypes.ITEM_CRACK,
                    net.minecraft.util.EnumParticleTypes.BLOCK_CRACK,
                    net.minecraft.util.EnumParticleTypes.BLOCK_DUST,
                    net.minecraft.util.EnumParticleTypes.WATER_DROP,
                    net.minecraft.util.EnumParticleTypes.ITEM_TAKE,
                    net.minecraft.util.EnumParticleTypes.MOB_APPEARANCE
                };
        net.minecraft.util.EnumParticleTypes.PARTICLES = Maps.newHashMap();
        ArrayList arraylist = Lists.newArrayList();
        for (EnumParticleTypes class_c77bd92d839e26551b86 : values()) {
            net.minecraft.util.EnumParticleTypes.PARTICLES.put(
                    class_c77bd92d839e26551b86.getParticleID(), class_c77bd92d839e26551b86);
            if (!class_c77bd92d839e26551b86.getParticleName().endsWith(packedTextFragments[1])) {
                arraylist.add(class_c77bd92d839e26551b86.getParticleName());
            }
        }
        net.minecraft.util.EnumParticleTypes.PARTICLE_NAMES =
                (String[]) arraylist.toArray(new String[arraylist.size()]);
    }

    public static String[] getParticleNames() {
        return net.minecraft.util.EnumParticleTypes.PARTICLE_NAMES;
    }

    EnumParticleTypes(
            String text, int l, String textValue, int j, boolean shouldIgnoreRangeValue, int k) {
        this.particleName = textValue;
        this.particleID = j;
        this.shouldIgnoreRange = shouldIgnoreRangeValue;
        this.argumentCount = k;
    }

    public int getArgumentCount() {
        return this.argumentCount;
    }

    public boolean getShouldIgnoreRange() {
        return this.shouldIgnoreRange;
    }

    public boolean hasArguments() {
        return this.argumentCount > 0;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    EnumParticleTypes(String text, int j, String textValue, int k, boolean flag) {
        this(text, j, textValue, k, flag, 0);
    }

    public String getParticleName() {
        return this.particleName;
    }

    private static EnumParticleTypes[] __recovery_values171() {
        return Zf.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.util.EnumParticleTypes.packedTextFragments = new String[2];
        net.minecraft.util.EnumParticleTypes.packedTextFragments[1] = new String("_");
        return "EXPLOSION_NORMAL";
    }
}
