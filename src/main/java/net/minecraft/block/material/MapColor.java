package net.minecraft.block.material;

public class MapColor {

    public static MapColor adobeColor;
    public static MapColor pinkColor;
    public static MapColor aVo;
    public static MapColor emeraldColor;
    public static MapColor aVq;
    public static MapColor redColor;
    public static MapColor aVs;
    public static MapColor tntColor;
    public static MapColor aaf;
    public static MapColor yellowColor;
    public static MapColor limeColor;
    public static MapColor sandColor;
    public static MapColor lightBlueColor;
    public static MapColor blackColor;
    public static MapColor greenColor;
    public static MapColor lapisColor;
    public static MapColor aVA;
    public static MapColor purpleColor;
    public static MapColor magentaColor;
    public static MapColor brownColor;
    public static MapColor netherrackColor;
    public static MapColor cyanColor;
    public static MapColor stoneColor;
    public static MapColor aVH;
    public static MapColor obsidianColor;
    public int colorIndex;
    public int colorValue;
    private static String DZ;
    private static String[] kj;
    public static MapColor goldColor;
    public static MapColor woodColor;
    public static MapColor snowColor;
    public static MapColor dirtColor;
    public static MapColor quartzColor;
    public static MapColor diamondColor;
    public static MapColor blueColor;
    public static MapColor[] mapColorArray;
    public static MapColor grayColor;
    public static MapColor waterColor;
    public static MapColor silverColor;
    public static MapColor ironColor;

    public static String[] b() {
        return kj;
    }

    static {
        b();

        DZ = "Map colour ID must be between 0 and 63 (inclusive)";
        net.minecraft.block.material.MapColor.mapColorArray = new MapColor[64];
        net.minecraft.block.material.MapColor.stoneColor = new MapColor(0, 0);
        aVA = new MapColor(1, 8368696);
        net.minecraft.block.material.MapColor.sandColor = new MapColor(2, 16247203);
        aVo = new MapColor(3, 10987431);
        net.minecraft.block.material.MapColor.tntColor = new MapColor(4, 16711680);
        aaf = new MapColor(5, 10526975);
        net.minecraft.block.material.MapColor.ironColor = new MapColor(6, 10987431);
        aVH = new MapColor(7, 31744);
        net.minecraft.block.material.MapColor.snowColor = new MapColor(8, 16777215);
        aVq = new MapColor(9, 10791096);
        net.minecraft.block.material.MapColor.dirtColor = new MapColor(10, 12020271);
        aVs = new MapColor(11, 7368816);
        net.minecraft.block.material.MapColor.waterColor = new MapColor(12, 4210943);
        net.minecraft.block.material.MapColor.woodColor = new MapColor(13, 6837042);
        net.minecraft.block.material.MapColor.quartzColor = new MapColor(14, 16776437);
        net.minecraft.block.material.MapColor.adobeColor = new MapColor(15, 14188339);
        net.minecraft.block.material.MapColor.magentaColor = new MapColor(16, 11685080);
        net.minecraft.block.material.MapColor.lightBlueColor = new MapColor(17, 6724056);
        net.minecraft.block.material.MapColor.yellowColor = new MapColor(18, 15066419);
        net.minecraft.block.material.MapColor.limeColor = new MapColor(19, 8375321);
        net.minecraft.block.material.MapColor.pinkColor = new MapColor(20, 15892389);
        net.minecraft.block.material.MapColor.grayColor = new MapColor(21, 5000268);
        net.minecraft.block.material.MapColor.silverColor = new MapColor(22, 10066329);
        net.minecraft.block.material.MapColor.cyanColor = new MapColor(23, 5013401);
        net.minecraft.block.material.MapColor.purpleColor = new MapColor(24, 8339378);
        net.minecraft.block.material.MapColor.blueColor = new MapColor(25, 3361970);
        net.minecraft.block.material.MapColor.brownColor = new MapColor(26, 6704179);
        net.minecraft.block.material.MapColor.greenColor = new MapColor(27, 6717235);
        net.minecraft.block.material.MapColor.redColor = new MapColor(28, 10040115);
        net.minecraft.block.material.MapColor.blackColor = new MapColor(29, 1644825);
        net.minecraft.block.material.MapColor.goldColor = new MapColor(30, 16445005);
        net.minecraft.block.material.MapColor.diamondColor = new MapColor(31, 6085589);
        net.minecraft.block.material.MapColor.lapisColor = new MapColor(32, 4882687);
        net.minecraft.block.material.MapColor.emeraldColor = new MapColor(33, 55610);
        net.minecraft.block.material.MapColor.obsidianColor = new MapColor(34, 1381407);
        net.minecraft.block.material.MapColor.netherrackColor = new MapColor(35, 7340544);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static void b(String[] astring) {
        kj = astring;
    }

    public int method_c640c0ab3979b883b99e(int i) {
        b();

        short short1 = 220;

        if (i == 3) {
            short1 = 135;
        }

        if (i == 2) {
            short1 = 255;
        }

        if (i == 1) {
            short1 = 220;
        }

        if (i == 0) {
            short1 = 180;
        }

        int j = (this.colorValue >> 16 & 0xFF) * short1 / 255;
        int k = (this.colorValue >> 8 & 0xFF) * short1 / 255;
        int l = (this.colorValue & 0xFF) * short1 / 255;

        try {
            int i1 = 0xFF000000 | j << 16 | k << 8 | l;

            return i1;
        } catch (IndexOutOfBoundsException indexoutofboundsexception) {
            throw method_e58a88fb4d7636fa7240(indexoutofboundsexception);
        }
    }

    private MapColor(int colorIndexValue, int j) {
        if (colorIndexValue >= 0) {
            try {
                if (colorIndexValue <= 63) {
                    this.colorIndex = colorIndexValue;
                    this.colorValue = j;
                    net.minecraft.block.material.MapColor.mapColorArray[colorIndexValue] = this;
                    return;
                }
            } catch (IndexOutOfBoundsException indexoutofboundsexception) {
                throw method_e58a88fb4d7636fa7240(indexoutofboundsexception);
            }
        }

        throw new IndexOutOfBoundsException(DZ);
    }

    private static IndexOutOfBoundsException method_e58a88fb4d7636fa7240(
            IndexOutOfBoundsException indexoutofboundsexception) {
        return indexoutofboundsexception;
    }
}
