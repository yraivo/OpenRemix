package net.minecraft.item;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringSerializable;

public enum EnumDyeColor implements StringSerializable {
    WHITE("WHITE", 0, 0, 15, "white", "white", MapColor.snowColor, EnumChatFormatting.WHITE),
    ORANGE("ORANGE", 1, 1, 14, "orange", "orange", MapColor.adobeColor, EnumChatFormatting.GOLD),
    MAGENTA("MAGENTA", 2, 2, 13, "magenta", "magenta", MapColor.magentaColor, EnumChatFormatting.AQUA),
    LIGHT_BLUE(
            "LIGHT_BLUE", 3, 3, 12, "light_blue", "lightBlue", MapColor.lightBlueColor, EnumChatFormatting.BLUE),
    YELLOW("YELLOW", 4, 4, 11, "yellow", "yellow", MapColor.yellowColor, EnumChatFormatting.YELLOW),
    LIME("LIME", 5, 5, 10, "lime", "lime", MapColor.limeColor, EnumChatFormatting.GREEN),
    PINK("PINK", 6, 6, 9, "pink", "pink", MapColor.pinkColor, EnumChatFormatting.LIGHT_PURPLE),
    GRAY("GRAY", 7, 7, 8, "gray", "gray", MapColor.grayColor, EnumChatFormatting.DARK_GRAY),
    SILVER("SILVER", 8, 8, 7, "silver", "silver", MapColor.silverColor, EnumChatFormatting.GRAY),
    CYAN("CYAN", 9, 9, 6, "cyan", "cyan", MapColor.cyanColor, EnumChatFormatting.DARK_AQUA),
    PURPLE("PURPLE", 10, 10, 5, "purple", "purple", MapColor.purpleColor, EnumChatFormatting.DARK_PURPLE),
    BLUE("BLUE", 11, 11, 4, "blue", "blue", MapColor.blueColor, EnumChatFormatting.DARK_BLUE),
    BROWN("BROWN", 12, 12, 3, "brown", "brown", MapColor.brownColor, EnumChatFormatting.GOLD),
    GREEN("GREEN", 13, 13, 2, "green", "green", MapColor.greenColor, EnumChatFormatting.DARK_GREEN),
    RED("RED", 14, 14, 1, "red", "red", MapColor.redColor, EnumChatFormatting.DARK_RED),
    BLACK("BLACK", 15, 15, 0, "black", "black", MapColor.blackColor, EnumChatFormatting.BLACK);
    private static EnumDyeColor[] META_LOOKUP;

    private static EnumDyeColor[] DYE_DMG_LOOKUP;

    private static EnumDyeColor[] ZN;

    private MapColor mapColor;
    private EnumChatFormatting chatColor;
    private int dyeDamage;
    private int meta;
    private String unlocalizedName;
    private String name;

    public static EnumDyeColor byDyeDamage(int i) {
        if (i < 0 || i >= net.minecraft.item.EnumDyeColor.DYE_DMG_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.item.EnumDyeColor.DYE_DMG_LOOKUP[i];
    }

    public int getDyeColorDamage() {
        return this.dyeDamage;
    }

    public int getMetadata() {
        return this.meta;
    }

    public static EnumDyeColor byMetadata(int i) {
        if (i < 0 || i >= net.minecraft.item.EnumDyeColor.META_LOOKUP.length) {
            i = 0;
        }

        return net.minecraft.item.EnumDyeColor.META_LOOKUP[i];
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.unlocalizedName;
    }

    static {
        net.minecraft.item.EnumDyeColor.META_LOOKUP = new EnumDyeColor[values().length];
        net.minecraft.item.EnumDyeColor.DYE_DMG_LOOKUP = new EnumDyeColor[values().length];
        ZN =
                new EnumDyeColor[] {
                    net.minecraft.item.EnumDyeColor.WHITE,
                    net.minecraft.item.EnumDyeColor.ORANGE,
                    net.minecraft.item.EnumDyeColor.MAGENTA,
                    net.minecraft.item.EnumDyeColor.LIGHT_BLUE,
                    net.minecraft.item.EnumDyeColor.YELLOW,
                    net.minecraft.item.EnumDyeColor.LIME,
                    net.minecraft.item.EnumDyeColor.PINK,
                    net.minecraft.item.EnumDyeColor.GRAY,
                    net.minecraft.item.EnumDyeColor.SILVER,
                    net.minecraft.item.EnumDyeColor.CYAN,
                    net.minecraft.item.EnumDyeColor.PURPLE,
                    net.minecraft.item.EnumDyeColor.BLUE,
                    net.minecraft.item.EnumDyeColor.BROWN,
                    net.minecraft.item.EnumDyeColor.GREEN,
                    net.minecraft.item.EnumDyeColor.RED,
                    net.minecraft.item.EnumDyeColor.BLACK
                };
        for (EnumDyeColor class_9e3faad6991b4253d049 : values()) {
            net.minecraft.item.EnumDyeColor.META_LOOKUP[class_9e3faad6991b4253d049.getMetadata()] =
                    class_9e3faad6991b4253d049;
            net.minecraft.item.EnumDyeColor.DYE_DMG_LOOKUP[class_9e3faad6991b4253d049.getDyeColorDamage()] =
                    class_9e3faad6991b4253d049;
        }
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    // region String decoding support

    // endregion

    public MapColor getMapColor() {
        return this.mapColor;
    }

    // region String decoding support

    // endregion

    EnumDyeColor(
            String text,
            int l,
            int j,
            int k,
            String textValue,
            String textValue2,
            MapColor aafValue,
            EnumChatFormatting aagValue) {
        this.meta = j;
        this.dyeDamage = k;
        this.name = textValue;
        this.unlocalizedName = textValue2;
        this.mapColor = aafValue;
        this.chatColor = aagValue;
    }
}
