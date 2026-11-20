package net.minecraft.client.settings;

import net.minecraft.util.MathHelper;

public enum GameSettingsOptions {
    INVERT_MOUSE(
            initializeConstantState(), 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true),
    SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false),
    FOV("FOV", 2, "FOV", 2, "options.fov", true, false, 30.0F, 110.0F, 1.0F),
    GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false),
    SATURATION("SATURATION", 4, "SATURATION", 4, "options.saturation", true, false),
    RENDER_DISTANCE(
            "RENDER_DISTANCE",
            5,
            "RENDER_DISTANCE",
            5,
            "options.renderDistance",
            true,
            false,
            2.0F,
            16.0F,
            1.0F),
    VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true),
    ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph", false, true),
    FRAMERATE_LIMIT(
            "FRAMERATE_LIMIT",
            8,
            "FRAMERATE_LIMIT",
            8,
            "options.framerateLimit",
            true,
            false,
            0.0F,
            260.0F,
            5.0F),
    FBO_ENABLE("FBO_ENABLE", 9, "FBO_ENABLE", 9, "options.fboEnable", false, true),
    RENDER_CLOUDS("RENDER_CLOUDS", 10, "RENDER_CLOUDS", 10, "options.renderClouds", false, true),
    aRM("GRAPHICS", 11, "GRAPHICS", 11, "options.graphics", false, false),
    aRU("AMBIENT_OCCLUSION", 12, "AMBIENT_OCCLUSION", 12, "options.ao", false, false),
    aTo("GUI_SCALE", 13, "GUI_SCALE", 13, "options.guiScale", false, false),
    PARTICLES("PARTICLES", 14, "PARTICLES", 14, "options.particles", false, false),
    CHAT_VISIBILITY(
            "CHAT_VISIBILITY", 15, "CHAT_VISIBILITY", 15, "options.chat.visibility", false, false),
    CHAT_COLOR("CHAT_COLOR", 16, "CHAT_COLOR", 16, "options.chat.color", false, true),
    CHAT_LINKS("CHAT_LINKS", 17, "CHAT_LINKS", 17, "options.chat.links", false, true),
    CHAT_OPACITY("CHAT_OPACITY", 18, "CHAT_OPACITY", 18, "options.chat.opacity", true, false),
    CHAT_LINKS_PROMPT(
            "CHAT_LINKS_PROMPT",
            19,
            "CHAT_LINKS_PROMPT",
            19,
            "options.chat.links.prompt",
            false,
            true),
    SNOOPER_ENABLED("SNOOPER_ENABLED", 20, "SNOOPER_ENABLED", 20, "options.snooper", false, true),
    USE_FULLSCREEN("USE_FULLSCREEN", 21, "USE_FULLSCREEN", 21, "options.fullscreen", false, true),
    ENABLE_VSYNC("ENABLE_VSYNC", 22, "ENABLE_VSYNC", 22, "options.vsync", false, true),
    USE_VBO("USE_VBO", 23, "USE_VBO", 23, "options.vbo", false, true),
    TOUCHSCREEN("TOUCHSCREEN", 24, "TOUCHSCREEN", 24, "options.touchscreen", false, true),
    CHAT_SCALE("CHAT_SCALE", 25, "CHAT_SCALE", 25, "options.chat.scale", true, false),
    CHAT_WIDTH("CHAT_WIDTH", 26, "CHAT_WIDTH", 26, "options.chat.width", true, false),
    CHAT_HEIGHT_FOCUSED(
            "CHAT_HEIGHT_FOCUSED",
            27,
            "CHAT_HEIGHT_FOCUSED",
            27,
            "options.chat.height.focused",
            true,
            false),
    CHAT_HEIGHT_UNFOCUSED(
            "CHAT_HEIGHT_UNFOCUSED",
            28,
            "CHAT_HEIGHT_UNFOCUSED",
            28,
            "options.chat.height.unfocused",
            true,
            false),
    MIPMAP_LEVELS(
            "MIPMAP_LEVELS",
            29,
            "MIPMAP_LEVELS",
            29,
            "options.mipmapLevels",
            true,
            false,
            0.0F,
            4.0F,
            1.0F),
    FORCE_UNICODE_FONT(
            "FORCE_UNICODE_FONT",
            30,
            "FORCE_UNICODE_FONT",
            30,
            "options.forceUnicodeFont",
            false,
            true),
    STREAM_BYTES_PER_PIXEL(
            "STREAM_BYTES_PER_PIXEL",
            31,
            "STREAM_BYTES_PER_PIXEL",
            31,
            "options.stream.bytesPerPixel",
            true,
            false),
    STREAM_VOLUME_MIC(
            "STREAM_VOLUME_MIC",
            32,
            "STREAM_VOLUME_MIC",
            32,
            "options.stream.micVolumne",
            true,
            false),
    STREAM_VOLUME_SYSTEM(
            "STREAM_VOLUME_SYSTEM",
            33,
            "STREAM_VOLUME_SYSTEM",
            33,
            "options.stream.systemVolume",
            true,
            false),
    STREAM_KBPS("STREAM_KBPS", 34, "STREAM_KBPS", 34, "options.stream.kbps", true, false),
    STREAM_FPS("STREAM_FPS", 35, "STREAM_FPS", 35, "options.stream.fps", true, false),
    STREAM_COMPRESSION(
            "STREAM_COMPRESSION",
            36,
            "STREAM_COMPRESSION",
            36,
            "options.stream.compression",
            false,
            false),
    STREAM_SEND_METADATA(
            "STREAM_SEND_METADATA",
            37,
            "STREAM_SEND_METADATA",
            37,
            "options.stream.sendMetadata",
            false,
            true),
    STREAM_CHAT_ENABLED(
            "STREAM_CHAT_ENABLED",
            38,
            "STREAM_CHAT_ENABLED",
            38,
            "options.stream.chat.enabled",
            false,
            false),
    STREAM_CHAT_USER_FILTER(
            "STREAM_CHAT_USER_FILTER",
            39,
            "STREAM_CHAT_USER_FILTER",
            39,
            "options.stream.chat.userFilter",
            false,
            false),
    STREAM_MIC_TOGGLE_BEHAVIOR(
            "STREAM_MIC_TOGGLE_BEHAVIOR",
            40,
            "STREAM_MIC_TOGGLE_BEHAVIOR",
            40,
            "options.stream.micToggleBehavior",
            false,
            false),
    BLOCK_ALTERNATIVES(
            "BLOCK_ALTERNATIVES",
            41,
            "BLOCK_ALTERNATIVES",
            41,
            "options.blockAlternatives",
            false,
            true),
    REDUCED_DEBUG_INFO(
            "REDUCED_DEBUG_INFO",
            42,
            "REDUCED_DEBUG_INFO",
            42,
            "options.reducedDebugInfo",
            false,
            true),
    FOG_FANCY("FOG_FANCY", 43, constantTextFragment(1), 999, "of.options.FOG_FANCY", false, false),
    FOG_START("FOG_START", 44, constantTextFragment(2), 999, "of.options.FOG_START", false, false),
    MIPMAP_TYPE(
            "MIPMAP_TYPE",
            45,
            constantTextFragment(3),
            999,
            "of.options.MIPMAP_TYPE",
            true,
            false,
            0.0F,
            3.0F,
            1.0F),
    SMOOTH_FPS(
            "SMOOTH_FPS", 46, constantTextFragment(4), 999, "of.options.SMOOTH_FPS", false, false),
    CLOUDS("CLOUDS", 47, constantTextFragment(5), 999, "of.options.CLOUDS", false, false),
    CLOUD_HEIGHT(
            "CLOUD_HEIGHT",
            48,
            constantTextFragment(6),
            999,
            "of.options.CLOUD_HEIGHT",
            true,
            false),
    TREES("TREES", 49, constantTextFragment(7), 999, "of.options.TREES", false, false),
    RAIN("RAIN", 50, constantTextFragment(8), 999, "of.options.RAIN", false, false),
    ANIMATED_WATER(
            "ANIMATED_WATER",
            51,
            constantTextFragment(9),
            999,
            "of.options.ANIMATED_WATER",
            false,
            false),
    ANIMATED_LAVA(
            "ANIMATED_LAVA",
            52,
            constantTextFragment(10),
            999,
            "of.options.ANIMATED_LAVA",
            false,
            false),
    ANIMATED_FIRE(
            "ANIMATED_FIRE",
            53,
            constantTextFragment(11),
            999,
            "of.options.ANIMATED_FIRE",
            false,
            false),
    ANIMATED_PORTAL(
            "ANIMATED_PORTAL",
            54,
            constantTextFragment(12),
            999,
            "of.options.ANIMATED_PORTAL",
            false,
            false),
    AO_LEVEL("AO_LEVEL", 55, constantTextFragment(13), 999, "of.options.AO_LEVEL", true, false),
    LAGOMETER("LAGOMETER", 56, constantTextFragment(14), 999, "of.options.LAGOMETER", false, false),
    SHOW_FPS("SHOW_FPS", 57, constantTextFragment(15), 999, "of.options.SHOW_FPS", false, false),
    AUTOSAVE_TICKS(
            "AUTOSAVE_TICKS",
            58,
            constantTextFragment(16),
            999,
            "of.options.AUTOSAVE_TICKS",
            false,
            false),
    BETTER_GRASS(
            "BETTER_GRASS",
            59,
            constantTextFragment(17),
            999,
            "of.options.BETTER_GRASS",
            false,
            false),
    ANIMATED_REDSTONE(
            "ANIMATED_REDSTONE",
            60,
            constantTextFragment(18),
            999,
            "of.options.ANIMATED_REDSTONE",
            false,
            false),
    ANIMATED_EXPLOSION(
            "ANIMATED_EXPLOSION",
            61,
            constantTextFragment(19),
            999,
            "of.options.ANIMATED_EXPLOSION",
            false,
            false),
    ANIMATED_FLAME(
            "ANIMATED_FLAME",
            62,
            constantTextFragment(20),
            999,
            "of.options.ANIMATED_FLAME",
            false,
            false),
    ANIMATED_SMOKE(
            "ANIMATED_SMOKE",
            63,
            constantTextFragment(21),
            999,
            "of.options.ANIMATED_SMOKE",
            false,
            false),
    WEATHER("WEATHER", 64, constantTextFragment(22), 999, "of.options.WEATHER", false, false),
    SKY("SKY", 65, constantTextFragment(23), 999, "of.options.SKY", false, false),
    STARS("STARS", 66, constantTextFragment(24), 999, "of.options.STARS", false, false),
    SUN_MOON("SUN_MOON", 67, constantTextFragment(25), 999, "of.options.SUN_MOON", false, false),
    VIGNETTE("VIGNETTE", 68, constantTextFragment(26), 999, "of.options.VIGNETTE", false, false),
    CHUNK_UPDATES(
            "CHUNK_UPDATES",
            69,
            constantTextFragment(27),
            999,
            "of.options.CHUNK_UPDATES",
            false,
            false),
    CHUNK_UPDATES_DYNAMIC(
            "CHUNK_UPDATES_DYNAMIC",
            70,
            constantTextFragment(28),
            999,
            "of.options.CHUNK_UPDATES_DYNAMIC",
            false,
            false),
    TIME("TIME", 71, constantTextFragment(29), 999, "of.options.TIME", false, false),
    CLEAR_WATER(
            "CLEAR_WATER",
            72,
            constantTextFragment(30),
            999,
            "of.options.CLEAR_WATER",
            false,
            false),
    SMOOTH_WORLD(
            "SMOOTH_WORLD",
            73,
            constantTextFragment(31),
            999,
            "of.options.SMOOTH_WORLD",
            false,
            false),
    VOID_PARTICLES(
            "VOID_PARTICLES",
            74,
            constantTextFragment(32),
            999,
            "of.options.VOID_PARTICLES",
            false,
            false),
    WATER_PARTICLES(
            "WATER_PARTICLES",
            75,
            constantTextFragment(33),
            999,
            "of.options.WATER_PARTICLES",
            false,
            false),
    RAIN_SPLASH(
            "RAIN_SPLASH",
            76,
            constantTextFragment(34),
            999,
            "of.options.RAIN_SPLASH",
            false,
            false),
    PORTAL_PARTICLES(
            "PORTAL_PARTICLES",
            77,
            constantTextFragment(35),
            999,
            "of.options.PORTAL_PARTICLES",
            false,
            false),
    POTION_PARTICLES(
            "POTION_PARTICLES",
            78,
            constantTextFragment(36),
            999,
            "of.options.POTION_PARTICLES",
            false,
            false),
    FIREWORK_PARTICLES(
            "FIREWORK_PARTICLES",
            79,
            constantTextFragment(37),
            999,
            "of.options.FIREWORK_PARTICLES",
            false,
            false),
    PROFILER("PROFILER", 80, constantTextFragment(38), 999, "of.options.PROFILER", false, false),
    DRIPPING_WATER_LAVA(
            "DRIPPING_WATER_LAVA",
            81,
            constantTextFragment(39),
            999,
            "of.options.DRIPPING_WATER_LAVA",
            false,
            false),
    BETTER_SNOW(
            "BETTER_SNOW",
            82,
            constantTextFragment(40),
            999,
            "of.options.BETTER_SNOW",
            false,
            false),
    FULLSCREEN_MODE(
            "FULLSCREEN_MODE",
            83,
            constantTextFragment(41),
            999,
            "of.options.FULLSCREEN_MODE",
            false,
            false),
    ANIMATED_TERRAIN(
            "ANIMATED_TERRAIN",
            84,
            constantTextFragment(42),
            999,
            "of.options.ANIMATED_TERRAIN",
            false,
            false),
    SWAMP_COLORS(
            "SWAMP_COLORS",
            85,
            constantTextFragment(43),
            999,
            "of.options.SWAMP_COLORS",
            false,
            false),
    RANDOM_MOBS(
            "RANDOM_MOBS",
            86,
            constantTextFragment(44),
            999,
            "of.options.RANDOM_MOBS",
            false,
            false),
    SMOOTH_BIOMES(
            "SMOOTH_BIOMES",
            87,
            constantTextFragment(45),
            999,
            "of.options.SMOOTH_BIOMES",
            false,
            false),
    CUSTOM_FONTS(
            "CUSTOM_FONTS",
            88,
            constantTextFragment(46),
            999,
            "of.options.CUSTOM_FONTS",
            false,
            false),
    CUSTOM_COLORS(
            "CUSTOM_COLORS",
            89,
            constantTextFragment(47),
            999,
            "of.options.CUSTOM_COLORS",
            false,
            false),
    SHOW_CAPES(
            "SHOW_CAPES", 90, constantTextFragment(48), 999, "of.options.SHOW_CAPES", false, false),
    CONNECTED_TEXTURES(
            "CONNECTED_TEXTURES",
            91,
            constantTextFragment(49),
            999,
            "of.options.CONNECTED_TEXTURES",
            false,
            false),
    CUSTOM_ITEMS(
            "CUSTOM_ITEMS",
            92,
            constantTextFragment(50),
            999,
            "of.options.CUSTOM_ITEMS",
            false,
            false),
    AA_LEVEL(
            "AA_LEVEL",
            93,
            constantTextFragment(51),
            999,
            "of.options.AA_LEVEL",
            true,
            false,
            0.0F,
            16.0F,
            1.0F),
    AF_LEVEL(
            "AF_LEVEL",
            94,
            constantTextFragment(52),
            999,
            "of.options.AF_LEVEL",
            true,
            false,
            1.0F,
            16.0F,
            1.0F),
    ANIMATED_TEXTURES(
            "ANIMATED_TEXTURES",
            95,
            constantTextFragment(53),
            999,
            "of.options.ANIMATED_TEXTURES",
            false,
            false),
    NATURAL_TEXTURES(
            "NATURAL_TEXTURES",
            96,
            constantTextFragment(54),
            999,
            "of.options.NATURAL_TEXTURES",
            false,
            false),
    HELD_ITEM_TOOLTIPS(
            "HELD_ITEM_TOOLTIPS",
            97,
            constantTextFragment(55),
            999,
            "of.options.HELD_ITEM_TOOLTIPS",
            false,
            false),
    DROPPED_ITEMS(
            "DROPPED_ITEMS",
            98,
            constantTextFragment(56),
            999,
            "of.options.DROPPED_ITEMS",
            false,
            false),
    LAZY_CHUNK_LOADING(
            "LAZY_CHUNK_LOADING",
            99,
            constantTextFragment(57),
            999,
            "of.options.LAZY_CHUNK_LOADING",
            false,
            false),
    CUSTOM_SKY(
            "CUSTOM_SKY",
            100,
            constantTextFragment(58),
            999,
            "of.options.CUSTOM_SKY",
            false,
            false),
    FAST_MATH(
            "FAST_MATH", 101, constantTextFragment(59), 999, "of.options.FAST_MATH", false, false),
    FAST_RENDER(
            "FAST_RENDER",
            102,
            constantTextFragment(60),
            999,
            "of.options.FAST_RENDER",
            false,
            false),
    TRANSLUCENT_BLOCKS(
            "TRANSLUCENT_BLOCKS",
            103,
            constantTextFragment(61),
            999,
            "of.options.TRANSLUCENT_BLOCKS",
            false,
            false),
    DYNAMIC_FOV(
            "DYNAMIC_FOV",
            104,
            constantTextFragment(62),
            999,
            "of.options.DYNAMIC_FOV",
            false,
            false),
    DYNAMIC_LIGHTS(
            "DYNAMIC_LIGHTS",
            105,
            constantTextFragment(63),
            999,
            "of.options.DYNAMIC_LIGHTS",
            false,
            false);
    private static GameSettingsOptions[] aSv;

    private static GameSettingsOptions[] aRW;

    private static GameSettingsOptions[] aRT;

    private float valueMin;
    private float valueMax;
    private float valueStep;
    private boolean enumBoolean;
    private static String[] packedTextFragments;
    private String enumString;
    private boolean enumFloat;

    static float access$100(GameSettingsOptions gameSettingsOptions) {
        return gameSettingsOptions.valueMin;
    }

    public int returnEnumOrdinal() {
        return this.ordinal();
    }

    public float getValueMax() {
        return this.valueMax;
    }

    protected float snapToStep(float f) {
        if (this.valueStep > 0.0F) {
            f = this.valueStep * Math.round(f / this.valueStep);
        }

        return f;
    }

    // region String decoding support

    // endregion

    public float denormalizeValue(float f) {
        return this.snapToStepClamp(
                this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp(f, 0.0F, 1.0F));
    }

    public static GameSettingsOptions getEnumOptions(int i) {
        GameSettings.BK();

        GameSettingsOptions[] gameSettingsOptionsArray = values();

        for (GameSettingsOptions gameSettingsOptions : gameSettingsOptionsArray) {
            if (gameSettingsOptions.returnEnumOrdinal() == i) {
                return gameSettingsOptions;
            }
        }

        return null;
    }

    public boolean getEnumFloat() {
        return this.enumFloat;
    }

    public boolean getEnumBoolean() {
        return this.enumBoolean;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        aSv =
                new GameSettingsOptions[] {
                    net.minecraft.client.settings.GameSettingsOptions.INVERT_MOUSE,
                    net.minecraft.client.settings.GameSettingsOptions.SENSITIVITY,
                    net.minecraft.client.settings.GameSettingsOptions.FOV,
                    net.minecraft.client.settings.GameSettingsOptions.GAMMA,
                    net.minecraft.client.settings.GameSettingsOptions.SATURATION,
                    net.minecraft.client.settings.GameSettingsOptions.RENDER_DISTANCE,
                    net.minecraft.client.settings.GameSettingsOptions.VIEW_BOBBING,
                    net.minecraft.client.settings.GameSettingsOptions.ANAGLYPH,
                    net.minecraft.client.settings.GameSettingsOptions.FRAMERATE_LIMIT,
                    net.minecraft.client.settings.GameSettingsOptions.FBO_ENABLE,
                    net.minecraft.client.settings.GameSettingsOptions.RENDER_CLOUDS,
                    aRM,
                    aRU,
                    aTo,
                    net.minecraft.client.settings.GameSettingsOptions.PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_VISIBILITY,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_COLOR,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_LINKS,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_OPACITY,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_LINKS_PROMPT,
                    net.minecraft.client.settings.GameSettingsOptions.SNOOPER_ENABLED,
                    net.minecraft.client.settings.GameSettingsOptions.USE_FULLSCREEN,
                    net.minecraft.client.settings.GameSettingsOptions.ENABLE_VSYNC,
                    net.minecraft.client.settings.GameSettingsOptions.USE_VBO,
                    net.minecraft.client.settings.GameSettingsOptions.TOUCHSCREEN,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_SCALE,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_WIDTH,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_HEIGHT_FOCUSED,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_HEIGHT_UNFOCUSED,
                    net.minecraft.client.settings.GameSettingsOptions.MIPMAP_LEVELS,
                    net.minecraft.client.settings.GameSettingsOptions.FORCE_UNICODE_FONT,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_BYTES_PER_PIXEL,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_VOLUME_MIC,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_VOLUME_SYSTEM,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_KBPS,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_FPS,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_COMPRESSION,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_SEND_METADATA,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_CHAT_ENABLED,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_CHAT_USER_FILTER,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_MIC_TOGGLE_BEHAVIOR,
                    net.minecraft.client.settings.GameSettingsOptions.BLOCK_ALTERNATIVES,
                    net.minecraft.client.settings.GameSettingsOptions.REDUCED_DEBUG_INFO,
                    net.minecraft.client.settings.GameSettingsOptions.FOG_FANCY,
                    net.minecraft.client.settings.GameSettingsOptions.FOG_START,
                    net.minecraft.client.settings.GameSettingsOptions.MIPMAP_TYPE,
                    net.minecraft.client.settings.GameSettingsOptions.SMOOTH_FPS,
                    net.minecraft.client.settings.GameSettingsOptions.CLOUDS,
                    net.minecraft.client.settings.GameSettingsOptions.CLOUD_HEIGHT,
                    net.minecraft.client.settings.GameSettingsOptions.TREES,
                    net.minecraft.client.settings.GameSettingsOptions.RAIN,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_WATER,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_LAVA,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_FIRE,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_PORTAL,
                    net.minecraft.client.settings.GameSettingsOptions.AO_LEVEL,
                    net.minecraft.client.settings.GameSettingsOptions.LAGOMETER,
                    net.minecraft.client.settings.GameSettingsOptions.SHOW_FPS,
                    net.minecraft.client.settings.GameSettingsOptions.AUTOSAVE_TICKS,
                    net.minecraft.client.settings.GameSettingsOptions.BETTER_GRASS,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_REDSTONE,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_EXPLOSION,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_FLAME,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_SMOKE,
                    net.minecraft.client.settings.GameSettingsOptions.WEATHER,
                    net.minecraft.client.settings.GameSettingsOptions.SKY,
                    net.minecraft.client.settings.GameSettingsOptions.STARS,
                    net.minecraft.client.settings.GameSettingsOptions.SUN_MOON,
                    net.minecraft.client.settings.GameSettingsOptions.VIGNETTE,
                    net.minecraft.client.settings.GameSettingsOptions.CHUNK_UPDATES,
                    net.minecraft.client.settings.GameSettingsOptions.CHUNK_UPDATES_DYNAMIC,
                    net.minecraft.client.settings.GameSettingsOptions.TIME,
                    net.minecraft.client.settings.GameSettingsOptions.CLEAR_WATER,
                    net.minecraft.client.settings.GameSettingsOptions.SMOOTH_WORLD,
                    net.minecraft.client.settings.GameSettingsOptions.VOID_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.WATER_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.RAIN_SPLASH,
                    net.minecraft.client.settings.GameSettingsOptions.PORTAL_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.POTION_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.FIREWORK_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.PROFILER,
                    net.minecraft.client.settings.GameSettingsOptions.DRIPPING_WATER_LAVA,
                    net.minecraft.client.settings.GameSettingsOptions.BETTER_SNOW,
                    net.minecraft.client.settings.GameSettingsOptions.FULLSCREEN_MODE,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_TERRAIN,
                    net.minecraft.client.settings.GameSettingsOptions.SWAMP_COLORS,
                    net.minecraft.client.settings.GameSettingsOptions.RANDOM_MOBS,
                    net.minecraft.client.settings.GameSettingsOptions.SMOOTH_BIOMES,
                    net.minecraft.client.settings.GameSettingsOptions.CUSTOM_FONTS,
                    net.minecraft.client.settings.GameSettingsOptions.CUSTOM_COLORS,
                    net.minecraft.client.settings.GameSettingsOptions.SHOW_CAPES,
                    net.minecraft.client.settings.GameSettingsOptions.CONNECTED_TEXTURES,
                    net.minecraft.client.settings.GameSettingsOptions.CUSTOM_ITEMS,
                    net.minecraft.client.settings.GameSettingsOptions.AA_LEVEL,
                    net.minecraft.client.settings.GameSettingsOptions.AF_LEVEL,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_TEXTURES,
                    net.minecraft.client.settings.GameSettingsOptions.NATURAL_TEXTURES,
                    net.minecraft.client.settings.GameSettingsOptions.HELD_ITEM_TOOLTIPS,
                    net.minecraft.client.settings.GameSettingsOptions.DROPPED_ITEMS,
                    net.minecraft.client.settings.GameSettingsOptions.LAZY_CHUNK_LOADING,
                    net.minecraft.client.settings.GameSettingsOptions.CUSTOM_SKY,
                    net.minecraft.client.settings.GameSettingsOptions.FAST_MATH,
                    net.minecraft.client.settings.GameSettingsOptions.FAST_RENDER,
                    net.minecraft.client.settings.GameSettingsOptions.TRANSLUCENT_BLOCKS,
                    net.minecraft.client.settings.GameSettingsOptions.DYNAMIC_FOV,
                    net.minecraft.client.settings.GameSettingsOptions.DYNAMIC_LIGHTS
                };
        aRW =
                new GameSettingsOptions[] {
                    net.minecraft.client.settings.GameSettingsOptions.INVERT_MOUSE,
                    net.minecraft.client.settings.GameSettingsOptions.SENSITIVITY,
                    net.minecraft.client.settings.GameSettingsOptions.FOV,
                    net.minecraft.client.settings.GameSettingsOptions.GAMMA,
                    net.minecraft.client.settings.GameSettingsOptions.SATURATION,
                    net.minecraft.client.settings.GameSettingsOptions.RENDER_DISTANCE,
                    net.minecraft.client.settings.GameSettingsOptions.VIEW_BOBBING,
                    net.minecraft.client.settings.GameSettingsOptions.ANAGLYPH,
                    net.minecraft.client.settings.GameSettingsOptions.FRAMERATE_LIMIT,
                    net.minecraft.client.settings.GameSettingsOptions.FBO_ENABLE,
                    net.minecraft.client.settings.GameSettingsOptions.RENDER_CLOUDS,
                    aRM,
                    aRU,
                    aTo,
                    net.minecraft.client.settings.GameSettingsOptions.PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_VISIBILITY,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_COLOR,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_LINKS,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_OPACITY,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_LINKS_PROMPT,
                    net.minecraft.client.settings.GameSettingsOptions.SNOOPER_ENABLED,
                    net.minecraft.client.settings.GameSettingsOptions.USE_FULLSCREEN,
                    net.minecraft.client.settings.GameSettingsOptions.ENABLE_VSYNC,
                    net.minecraft.client.settings.GameSettingsOptions.USE_VBO,
                    net.minecraft.client.settings.GameSettingsOptions.TOUCHSCREEN,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_SCALE,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_WIDTH,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_HEIGHT_FOCUSED,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_HEIGHT_UNFOCUSED,
                    net.minecraft.client.settings.GameSettingsOptions.MIPMAP_LEVELS,
                    net.minecraft.client.settings.GameSettingsOptions.FORCE_UNICODE_FONT,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_BYTES_PER_PIXEL,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_VOLUME_MIC,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_VOLUME_SYSTEM,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_KBPS,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_FPS,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_COMPRESSION,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_SEND_METADATA,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_CHAT_ENABLED,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_CHAT_USER_FILTER,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_MIC_TOGGLE_BEHAVIOR,
                    net.minecraft.client.settings.GameSettingsOptions.BLOCK_ALTERNATIVES,
                    net.minecraft.client.settings.GameSettingsOptions.REDUCED_DEBUG_INFO
                };
        aRT =
                new GameSettingsOptions[] {
                    net.minecraft.client.settings.GameSettingsOptions.INVERT_MOUSE,
                    net.minecraft.client.settings.GameSettingsOptions.SENSITIVITY,
                    net.minecraft.client.settings.GameSettingsOptions.FOV,
                    net.minecraft.client.settings.GameSettingsOptions.GAMMA,
                    net.minecraft.client.settings.GameSettingsOptions.SATURATION,
                    net.minecraft.client.settings.GameSettingsOptions.RENDER_DISTANCE,
                    net.minecraft.client.settings.GameSettingsOptions.VIEW_BOBBING,
                    net.minecraft.client.settings.GameSettingsOptions.ANAGLYPH,
                    net.minecraft.client.settings.GameSettingsOptions.FRAMERATE_LIMIT,
                    net.minecraft.client.settings.GameSettingsOptions.FBO_ENABLE,
                    net.minecraft.client.settings.GameSettingsOptions.RENDER_CLOUDS,
                    aRM,
                    aRU,
                    aTo,
                    net.minecraft.client.settings.GameSettingsOptions.PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_VISIBILITY,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_COLOR,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_LINKS,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_OPACITY,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_LINKS_PROMPT,
                    net.minecraft.client.settings.GameSettingsOptions.SNOOPER_ENABLED,
                    net.minecraft.client.settings.GameSettingsOptions.USE_FULLSCREEN,
                    net.minecraft.client.settings.GameSettingsOptions.ENABLE_VSYNC,
                    net.minecraft.client.settings.GameSettingsOptions.USE_VBO,
                    net.minecraft.client.settings.GameSettingsOptions.TOUCHSCREEN,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_SCALE,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_WIDTH,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_HEIGHT_FOCUSED,
                    net.minecraft.client.settings.GameSettingsOptions.CHAT_HEIGHT_UNFOCUSED,
                    net.minecraft.client.settings.GameSettingsOptions.MIPMAP_LEVELS,
                    net.minecraft.client.settings.GameSettingsOptions.FORCE_UNICODE_FONT,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_BYTES_PER_PIXEL,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_VOLUME_MIC,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_VOLUME_SYSTEM,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_KBPS,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_FPS,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_COMPRESSION,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_SEND_METADATA,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_CHAT_ENABLED,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_CHAT_USER_FILTER,
                    net.minecraft.client.settings.GameSettingsOptions.STREAM_MIC_TOGGLE_BEHAVIOR,
                    net.minecraft.client.settings.GameSettingsOptions.BLOCK_ALTERNATIVES,
                    net.minecraft.client.settings.GameSettingsOptions.REDUCED_DEBUG_INFO,
                    net.minecraft.client.settings.GameSettingsOptions.FOG_FANCY,
                    net.minecraft.client.settings.GameSettingsOptions.FOG_START,
                    net.minecraft.client.settings.GameSettingsOptions.MIPMAP_TYPE,
                    net.minecraft.client.settings.GameSettingsOptions.SMOOTH_FPS,
                    net.minecraft.client.settings.GameSettingsOptions.CLOUDS,
                    net.minecraft.client.settings.GameSettingsOptions.CLOUD_HEIGHT,
                    net.minecraft.client.settings.GameSettingsOptions.TREES,
                    net.minecraft.client.settings.GameSettingsOptions.RAIN,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_WATER,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_LAVA,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_FIRE,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_PORTAL,
                    net.minecraft.client.settings.GameSettingsOptions.AO_LEVEL,
                    net.minecraft.client.settings.GameSettingsOptions.LAGOMETER,
                    net.minecraft.client.settings.GameSettingsOptions.SHOW_FPS,
                    net.minecraft.client.settings.GameSettingsOptions.AUTOSAVE_TICKS,
                    net.minecraft.client.settings.GameSettingsOptions.BETTER_GRASS,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_REDSTONE,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_EXPLOSION,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_FLAME,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_SMOKE,
                    net.minecraft.client.settings.GameSettingsOptions.WEATHER,
                    net.minecraft.client.settings.GameSettingsOptions.SKY,
                    net.minecraft.client.settings.GameSettingsOptions.STARS,
                    net.minecraft.client.settings.GameSettingsOptions.SUN_MOON,
                    net.minecraft.client.settings.GameSettingsOptions.VIGNETTE,
                    net.minecraft.client.settings.GameSettingsOptions.CHUNK_UPDATES,
                    net.minecraft.client.settings.GameSettingsOptions.CHUNK_UPDATES_DYNAMIC,
                    net.minecraft.client.settings.GameSettingsOptions.TIME,
                    net.minecraft.client.settings.GameSettingsOptions.CLEAR_WATER,
                    net.minecraft.client.settings.GameSettingsOptions.SMOOTH_WORLD,
                    net.minecraft.client.settings.GameSettingsOptions.VOID_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.WATER_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.RAIN_SPLASH,
                    net.minecraft.client.settings.GameSettingsOptions.PORTAL_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.POTION_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.FIREWORK_PARTICLES,
                    net.minecraft.client.settings.GameSettingsOptions.PROFILER,
                    net.minecraft.client.settings.GameSettingsOptions.DRIPPING_WATER_LAVA,
                    net.minecraft.client.settings.GameSettingsOptions.BETTER_SNOW,
                    net.minecraft.client.settings.GameSettingsOptions.FULLSCREEN_MODE,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_TERRAIN,
                    net.minecraft.client.settings.GameSettingsOptions.SWAMP_COLORS,
                    net.minecraft.client.settings.GameSettingsOptions.RANDOM_MOBS,
                    net.minecraft.client.settings.GameSettingsOptions.SMOOTH_BIOMES,
                    net.minecraft.client.settings.GameSettingsOptions.CUSTOM_FONTS,
                    net.minecraft.client.settings.GameSettingsOptions.CUSTOM_COLORS,
                    net.minecraft.client.settings.GameSettingsOptions.SHOW_CAPES,
                    net.minecraft.client.settings.GameSettingsOptions.CONNECTED_TEXTURES,
                    net.minecraft.client.settings.GameSettingsOptions.CUSTOM_ITEMS,
                    net.minecraft.client.settings.GameSettingsOptions.AA_LEVEL,
                    net.minecraft.client.settings.GameSettingsOptions.AF_LEVEL,
                    net.minecraft.client.settings.GameSettingsOptions.ANIMATED_TEXTURES,
                    net.minecraft.client.settings.GameSettingsOptions.NATURAL_TEXTURES,
                    net.minecraft.client.settings.GameSettingsOptions.HELD_ITEM_TOOLTIPS,
                    net.minecraft.client.settings.GameSettingsOptions.DROPPED_ITEMS,
                    net.minecraft.client.settings.GameSettingsOptions.LAZY_CHUNK_LOADING,
                    net.minecraft.client.settings.GameSettingsOptions.CUSTOM_SKY,
                    net.minecraft.client.settings.GameSettingsOptions.FAST_MATH,
                    net.minecraft.client.settings.GameSettingsOptions.FAST_RENDER,
                    net.minecraft.client.settings.GameSettingsOptions.TRANSLUCENT_BLOCKS,
                    net.minecraft.client.settings.GameSettingsOptions.DYNAMIC_FOV,
                    net.minecraft.client.settings.GameSettingsOptions.DYNAMIC_LIGHTS
                };
    }

    GameSettingsOptions(
            String text,
            int j,
            String textValue,
            int k,
            String textValue2,
            boolean flag,
            boolean flag1,
            float valueMinValue,
            float valueMaxValue,
            float valueStepValue) {
        this.enumString = textValue2;
        this.enumFloat = flag;
        this.enumBoolean = flag1;
        this.valueMin = valueMinValue;
        this.valueMax = valueMaxValue;
        this.valueStep = valueStepValue;
    }

    public String getEnumString() {
        return this.enumString;
    }

    static float access$000(GameSettingsOptions gameSettingsOptions) {
        return gameSettingsOptions.valueMax;
    }

    public void setValueMax(float valueMaxValue) {
        this.valueMax = valueMaxValue;
    }

    public float normalizeValue(float f) {
        return MathHelper.clamp(
                (this.snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin),
                0.0F,
                1.0F);
    }

    // region String decoding support

    // endregion

    GameSettingsOptions(
            String text,
            int j,
            String textValue,
            int k,
            String textValue2,
            boolean flag,
            boolean flag1) {
        this(text, j, textValue, k, textValue2, flag, flag1, 0.0F, 1.0F, 0.0F);
    }

    public float snapToStepClamp(float f) {
        f = this.snapToStep(f);
        return MathHelper.clamp(f, this.valueMin, this.valueMax);
    }

    private static GameSettingsOptions[] __recovery_values171() {
        return aSv.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments = new String[64];
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[1] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[2] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[3] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[4] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[5] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[6] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[7] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[8] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[9] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[10] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[11] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[12] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[13] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[14] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[15] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[16] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[17] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[18] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[19] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[20] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[21] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[22] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[23] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[24] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[25] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[26] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[27] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[28] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[29] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[30] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[31] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[32] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[33] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[34] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[35] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[36] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[37] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[38] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[39] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[40] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[41] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[42] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[43] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[44] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[45] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[46] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[47] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[48] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[49] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[50] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[51] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[52] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[53] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[54] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[55] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[56] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[57] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[58] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[59] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[60] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[61] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[62] = new String("");
        net.minecraft.client.settings.GameSettingsOptions.packedTextFragments[63] = new String("");
        return "INVERT_MOUSE";
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
