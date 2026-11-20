package net.minecraft.client.renderer;

import net.minecraft.util.Facing;

enum BlockModelRendererVertexTranslations {
    DOWN("DOWN", 0, "DOWN", 0, 0, 1, 2, 3),
    UP("UP", 1, "UP", 1, 2, 3, 0, 1),
    NORTH("NORTH", 2, "NORTH", 2, 3, 0, 1, 2),
    SOUTH("SOUTH", 3, "SOUTH", 3, 0, 1, 2, 3),
    WEST("WEST", 4, "WEST", 4, 3, 0, 1, 2),
    EAST("EAST", 5, "EAST", 5, 1, 2, 3, 0);
    private static BlockModelRendererVertexTranslations[] aPN;

    private static BlockModelRendererVertexTranslations[] aPI;

    private int c;
    private int f;
    private int dD;
    private int ef;

    static {
        aPN = new BlockModelRendererVertexTranslations[6];
        aPI = new BlockModelRendererVertexTranslations[] {net.minecraft.client.renderer.BlockModelRendererVertexTranslations.DOWN, net.minecraft.client.renderer.BlockModelRendererVertexTranslations.UP, net.minecraft.client.renderer.BlockModelRendererVertexTranslations.NORTH, net.minecraft.client.renderer.BlockModelRendererVertexTranslations.SOUTH, net.minecraft.client.renderer.BlockModelRendererVertexTranslations.WEST, net.minecraft.client.renderer.BlockModelRendererVertexTranslations.EAST};
        aPN[Facing.DOWN.getIndex()] = net.minecraft.client.renderer.BlockModelRendererVertexTranslations.DOWN;
        aPN[Facing.UP.getIndex()] = net.minecraft.client.renderer.BlockModelRendererVertexTranslations.UP;
        aPN[Facing.NORTH.getIndex()] = net.minecraft.client.renderer.BlockModelRendererVertexTranslations.NORTH;
        aPN[Facing.SOUTH.getIndex()] = net.minecraft.client.renderer.BlockModelRendererVertexTranslations.SOUTH;
        aPN[Facing.WEST.getIndex()] = net.minecraft.client.renderer.BlockModelRendererVertexTranslations.WEST;
        aPN[Facing.EAST.getIndex()] = net.minecraft.client.renderer.BlockModelRendererVertexTranslations.EAST;
    }

    static int method_5e44e384bdf398a9ff0b(BlockModelRendererVertexTranslations blockModelRendererVertexTranslations) {
        return blockModelRendererVertexTranslations.c;
    }

    // region String decoding support

    // endregion

    static int method_21001c61e52a69c76f37(BlockModelRendererVertexTranslations blockModelRendererVertexTranslations) {
        return blockModelRendererVertexTranslations.dD;
    }

    static int method_942b59fb10bacb28166e(BlockModelRendererVertexTranslations blockModelRendererVertexTranslations) {
        return blockModelRendererVertexTranslations.f;
    }

    // region String decoding support

    // endregion

    public static BlockModelRendererVertexTranslations getVertexTranslations(Facing facing) {
        return aPN[facing.getIndex()];
    }

    // region String decoding support

    // endregion

    BlockModelRendererVertexTranslations(
            String text, int j1, String textValue, int k1, int j, int k, int l, int i1) {
        this.c = j;
        this.dD = k;
        this.ef = l;
        this.f = i1;
    }

    static int method_8d996f8725e0b61d3bae(BlockModelRendererVertexTranslations blockModelRendererVertexTranslations) {
        return blockModelRendererVertexTranslations.ef;
    }
}
