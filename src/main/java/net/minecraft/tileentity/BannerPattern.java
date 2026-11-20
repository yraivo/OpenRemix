package net.minecraft.tileentity;

import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum BannerPattern {
    BASE(initializeConstantState(), 0, "base", constantTextFragment(1)),
    SQUARE_BOTTOM_LEFT("SQUARE_BOTTOM_LEFT", 1, "square_bottom_left", "bl", "   ", "   ", "#  "),
    SQUARE_BOTTOM_RIGHT("SQUARE_BOTTOM_RIGHT", 2, "square_bottom_right", "br", "   ", "   ", "  #"),
    SQUARE_TOP_LEFT("SQUARE_TOP_LEFT", 3, "square_top_left", "tl", "#  ", "   ", "   "),
    SQUARE_TOP_RIGHT("SQUARE_TOP_RIGHT", 4, "square_top_right", "tr", "  #", "   ", "   "),
    STRIPE_BOTTOM("STRIPE_BOTTOM", 5, "stripe_bottom", "bs", "   ", "   ", "###"),
    STRIPE_TOP("STRIPE_TOP", 6, "stripe_top", "ts", "###", "   ", "   "),
    STRIPE_LEFT("STRIPE_LEFT", 7, "stripe_left", "ls", "#  ", "#  ", "#  "),
    STRIPE_RIGHT("STRIPE_RIGHT", 8, "stripe_right", "rs", "  #", "  #", "  #"),
    STRIPE_CENTER("STRIPE_CENTER", 9, "stripe_center", "cs", " # ", " # ", " # "),
    STRIPE_MIDDLE("STRIPE_MIDDLE", 10, "stripe_middle", "ms", "   ", "###", "   "),
    STRIPE_DOWNRIGHT("STRIPE_DOWNRIGHT", 11, "stripe_downright", "drs", "#  ", " # ", "  #"),
    STRIPE_DOWNLEFT("STRIPE_DOWNLEFT", 12, "stripe_downleft", "dls", "  #", " # ", "#  "),
    STRIPE_SMALL("STRIPE_SMALL", 13, "small_stripes", "ss", "# #", "# #", "   "),
    CROSS("CROSS", 14, "cross", "cr", "# #", " # ", "# #"),
    STRAIGHT_CROSS("STRAIGHT_CROSS", 15, "straight_cross", "sc", " # ", "###", " # "),
    TRIANGLE_BOTTOM("TRIANGLE_BOTTOM", 16, "triangle_bottom", "bt", "   ", " # ", "# #"),
    TRIANGLE_TOP("TRIANGLE_TOP", 17, "triangle_top", "tt", "# #", " # ", "   "),
    TRIANGLES_BOTTOM("TRIANGLES_BOTTOM", 18, "triangles_bottom", "bts", "   ", "# #", " # "),
    TRIANGLES_TOP("TRIANGLES_TOP", 19, "triangles_top", "tts", " # ", "# #", "   "),
    DIAGONAL_LEFT("DIAGONAL_LEFT", 20, "diagonal_left", "ld", "## ", "#  ", "   "),
    DIAGONAL_RIGHT("DIAGONAL_RIGHT", 21, "diagonal_up_right", "rd", "   ", "  #", " ##"),
    DIAGONAL_LEFT_MIRROR(
            "DIAGONAL_LEFT_MIRROR", 22, "diagonal_up_left", "lud", "   ", "#  ", "## "),
    DIAGONAL_RIGHT_MIRROR(
            "DIAGONAL_RIGHT_MIRROR", 23, "diagonal_right", "rud", " ##", "  #", "   "),
    CIRCLE_MIDDLE("CIRCLE_MIDDLE", 24, "circle", "mc", "   ", " # ", "   "),
    RHOMBUS_MIDDLE("RHOMBUS_MIDDLE", 25, "rhombus", "mr", " # ", "# #", " # "),
    HALF_VERTICAL("HALF_VERTICAL", 26, "half_vertical", "vh", "## ", "## ", "## "),
    HALF_HORIZONTAL("HALF_HORIZONTAL", 27, "half_horizontal", "hh", "###", "###", "   "),
    HALF_VERTICAL_MIRROR(
            "HALF_VERTICAL_MIRROR", 28, "half_vertical_right", "vhr", " ##", " ##", " ##"),
    HALF_HORIZONTAL_MIRROR(
            "HALF_HORIZONTAL_MIRROR", 29, "half_horizontal_bottom", "hhb", "   ", "###", "###"),
    BORDER("BORDER", 30, "border", "bo", "###", "# #", "###"),
    CURLY_BORDER("CURLY_BORDER", 31, "curly_border", "cbo", new ItemStack(Blocks.vine)),
    CREEPER("CREEPER", 32, "creeper", "cre", new ItemStack(Items.skull, 1, 4)),
    GRADIENT("GRADIENT", 33, "gradient", "gra", "# #", " # ", " # "),
    GRADIENT_UP("GRADIENT_UP", 34, "gradient_up", "gru", " # ", " # ", "# #"),
    BRICKS("BRICKS", 35, "bricks", "bri", new ItemStack(Blocks.brick_block)),
    SKULL("SKULL", 36, "skull", "sku", new ItemStack(Items.skull, 1, 1)),
    FLOWER(
            "FLOWER",
            37,
            "flower",
            "flo",
            new ItemStack(Blocks.red_flower, 1, BlockFlowerEnumFlowerType.OXEYE_DAISY.getMeta())),
    MOJANG("MOJANG", 38, "mojang", "moj", new ItemStack(Items.golden_apple, 1, 1));
    private static BannerPattern[] WQ;

    private static BannerPattern[] WO;

    private static String[] packedTextFragments;
    private String patternName;
    private String patternID;
    private ItemStack patternCraftingStack;
    private String[] craftingLayers = new String[3];

    BannerPattern(String text, int j, String textValue, String textValue2, ItemStack itemStack) {
        this(text, j, textValue, textValue2);
        this.patternCraftingStack = itemStack;
    }

    BannerPattern(
            String text,
            int j,
            String textValue,
            String textValue2,
            String textValue3,
            String textValue4,
            String textValue5) {
        this(text, j, textValue, textValue2);
        this.craftingLayers[0] = textValue3;
        this.craftingLayers[1] = textValue4;
        this.craftingLayers[2] = textValue5;
    }

    public static BannerPattern getPatternByID(String text) {
        MobSpawnerBaseLogic.GU();

        BannerPattern[] bannerPatternArray = values();
        int length = bannerPatternArray.length;
        int j = 0;

        while (j < length) {
            BannerPattern bannerPattern = bannerPatternArray[j];
            if (bannerPattern.patternID.equals(text)) {
                return bannerPattern;
            }

            j++;
        }

        return null;
    }

    static {
        WO =
                new BannerPattern[] {
                    net.minecraft.tileentity.BannerPattern.BASE,
                    net.minecraft.tileentity.BannerPattern.SQUARE_BOTTOM_LEFT,
                    net.minecraft.tileentity.BannerPattern.SQUARE_BOTTOM_RIGHT,
                    net.minecraft.tileentity.BannerPattern.SQUARE_TOP_LEFT,
                    net.minecraft.tileentity.BannerPattern.SQUARE_TOP_RIGHT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_BOTTOM,
                    net.minecraft.tileentity.BannerPattern.STRIPE_TOP,
                    net.minecraft.tileentity.BannerPattern.STRIPE_LEFT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_RIGHT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_CENTER,
                    net.minecraft.tileentity.BannerPattern.STRIPE_MIDDLE,
                    net.minecraft.tileentity.BannerPattern.STRIPE_DOWNRIGHT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_DOWNLEFT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_SMALL,
                    net.minecraft.tileentity.BannerPattern.CROSS,
                    net.minecraft.tileentity.BannerPattern.STRAIGHT_CROSS,
                    net.minecraft.tileentity.BannerPattern.TRIANGLE_BOTTOM,
                    net.minecraft.tileentity.BannerPattern.TRIANGLE_TOP,
                    net.minecraft.tileentity.BannerPattern.TRIANGLES_BOTTOM,
                    net.minecraft.tileentity.BannerPattern.TRIANGLES_TOP,
                    net.minecraft.tileentity.BannerPattern.DIAGONAL_LEFT,
                    net.minecraft.tileentity.BannerPattern.DIAGONAL_RIGHT,
                    net.minecraft.tileentity.BannerPattern.DIAGONAL_LEFT_MIRROR,
                    net.minecraft.tileentity.BannerPattern.DIAGONAL_RIGHT_MIRROR,
                    net.minecraft.tileentity.BannerPattern.CIRCLE_MIDDLE,
                    net.minecraft.tileentity.BannerPattern.RHOMBUS_MIDDLE,
                    net.minecraft.tileentity.BannerPattern.HALF_VERTICAL,
                    net.minecraft.tileentity.BannerPattern.HALF_HORIZONTAL,
                    net.minecraft.tileentity.BannerPattern.HALF_VERTICAL_MIRROR,
                    net.minecraft.tileentity.BannerPattern.HALF_HORIZONTAL_MIRROR,
                    net.minecraft.tileentity.BannerPattern.BORDER,
                    net.minecraft.tileentity.BannerPattern.CURLY_BORDER,
                    net.minecraft.tileentity.BannerPattern.CREEPER,
                    net.minecraft.tileentity.BannerPattern.GRADIENT,
                    net.minecraft.tileentity.BannerPattern.GRADIENT_UP,
                    net.minecraft.tileentity.BannerPattern.BRICKS,
                    net.minecraft.tileentity.BannerPattern.SKULL,
                    net.minecraft.tileentity.BannerPattern.FLOWER,
                    net.minecraft.tileentity.BannerPattern.MOJANG
                };
        WQ =
                new BannerPattern[] {
                    net.minecraft.tileentity.BannerPattern.BASE,
                    net.minecraft.tileentity.BannerPattern.SQUARE_BOTTOM_LEFT,
                    net.minecraft.tileentity.BannerPattern.SQUARE_BOTTOM_RIGHT,
                    net.minecraft.tileentity.BannerPattern.SQUARE_TOP_LEFT,
                    net.minecraft.tileentity.BannerPattern.SQUARE_TOP_RIGHT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_BOTTOM,
                    net.minecraft.tileentity.BannerPattern.STRIPE_TOP,
                    net.minecraft.tileentity.BannerPattern.STRIPE_LEFT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_RIGHT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_CENTER,
                    net.minecraft.tileentity.BannerPattern.STRIPE_MIDDLE,
                    net.minecraft.tileentity.BannerPattern.STRIPE_DOWNRIGHT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_DOWNLEFT,
                    net.minecraft.tileentity.BannerPattern.STRIPE_SMALL,
                    net.minecraft.tileentity.BannerPattern.CROSS,
                    net.minecraft.tileentity.BannerPattern.STRAIGHT_CROSS,
                    net.minecraft.tileentity.BannerPattern.TRIANGLE_BOTTOM,
                    net.minecraft.tileentity.BannerPattern.TRIANGLE_TOP,
                    net.minecraft.tileentity.BannerPattern.TRIANGLES_BOTTOM,
                    net.minecraft.tileentity.BannerPattern.TRIANGLES_TOP,
                    net.minecraft.tileentity.BannerPattern.DIAGONAL_LEFT,
                    net.minecraft.tileentity.BannerPattern.DIAGONAL_RIGHT,
                    net.minecraft.tileentity.BannerPattern.DIAGONAL_LEFT_MIRROR,
                    net.minecraft.tileentity.BannerPattern.DIAGONAL_RIGHT_MIRROR,
                    net.minecraft.tileentity.BannerPattern.CIRCLE_MIDDLE,
                    net.minecraft.tileentity.BannerPattern.RHOMBUS_MIDDLE,
                    net.minecraft.tileentity.BannerPattern.HALF_VERTICAL,
                    net.minecraft.tileentity.BannerPattern.HALF_HORIZONTAL,
                    net.minecraft.tileentity.BannerPattern.HALF_VERTICAL_MIRROR,
                    net.minecraft.tileentity.BannerPattern.HALF_HORIZONTAL_MIRROR,
                    net.minecraft.tileentity.BannerPattern.BORDER,
                    net.minecraft.tileentity.BannerPattern.CURLY_BORDER,
                    net.minecraft.tileentity.BannerPattern.CREEPER,
                    net.minecraft.tileentity.BannerPattern.GRADIENT,
                    net.minecraft.tileentity.BannerPattern.GRADIENT_UP,
                    net.minecraft.tileentity.BannerPattern.BRICKS,
                    net.minecraft.tileentity.BannerPattern.SKULL,
                    net.minecraft.tileentity.BannerPattern.FLOWER,
                    net.minecraft.tileentity.BannerPattern.MOJANG
                };
    }

    // region String decoding support

    // endregion

    public String getPatternName() {
        return this.patternName;
    }

    public String[] getCraftingLayers() {
        return this.craftingLayers;
    }

    BannerPattern(String text, int j, String textValue, String textValue2) {
        this.patternName = textValue;
        this.patternID = textValue2;
    }

    public String getPatternID() {
        return this.patternID;
    }

    public boolean hasCraftingStack() {
        return this.patternCraftingStack != null;
    }

    // region String decoding support

    // endregion

    public boolean hasValidCrafting() {
        return this.patternCraftingStack != null || this.craftingLayers[0] != null;
    }

    // region String decoding support

    // endregion

    public ItemStack getCraftingStack() {
        return this.patternCraftingStack;
    }

    private static BannerPattern[] __recovery_values171() {
        return WO.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.tileentity.BannerPattern.packedTextFragments = new String[2];
        net.minecraft.tileentity.BannerPattern.packedTextFragments[1] = new String("b");
        return "BASE";
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
