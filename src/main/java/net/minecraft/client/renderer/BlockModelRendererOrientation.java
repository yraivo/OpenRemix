package net.minecraft.client.renderer;

import net.minecraft.util.Facing;

public enum BlockModelRendererOrientation {
    DOWN("DOWN", 0, "DOWN", 0, Facing.DOWN, false),
    UP("UP", 1, "UP", 1, Facing.UP, false),
    NORTH("NORTH", 2, "NORTH", 2, Facing.NORTH, false),
    SOUTH("SOUTH", 3, "SOUTH", 3, Facing.SOUTH, false),
    WEST("WEST", 4, "WEST", 4, Facing.WEST, false),
    EAST("EAST", 5, "EAST", 5, Facing.EAST, false),
    FLIP_DOWN("FLIP_DOWN", 6, "FLIP_DOWN", 6, Facing.DOWN, true),
    FLIP_UP("FLIP_UP", 7, "FLIP_UP", 7, Facing.UP, true),
    FLIP_NORTH("FLIP_NORTH", 8, "FLIP_NORTH", 8, Facing.NORTH, true),
    FLIP_SOUTH("FLIP_SOUTH", 9, "FLIP_SOUTH", 9, Facing.SOUTH, true),
    FLIP_WEST("FLIP_WEST", 10, "FLIP_WEST", 10, Facing.WEST, true),
    FLIP_EAST("FLIP_EAST", 11, "FLIP_EAST", 11, Facing.EAST, true);

    private static BlockModelRendererOrientation[] VALUES;

    protected int cz;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.BlockModelRendererOrientation.VALUES =
                new BlockModelRendererOrientation[] {
                    net.minecraft.client.renderer.BlockModelRendererOrientation.DOWN,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.UP,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.NORTH,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.SOUTH,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.WEST,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.EAST,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.FLIP_DOWN,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.FLIP_UP,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.FLIP_NORTH,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.FLIP_SOUTH,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.FLIP_WEST,
                    net.minecraft.client.renderer.BlockModelRendererOrientation.FLIP_EAST
                };
    }

    BlockModelRendererOrientation(
            String text, int j, String textValue, int k, Facing facing, boolean flag) {
        this.cz = facing.getIndex() + (flag ? Facing.values().length : 0);
    }
}
