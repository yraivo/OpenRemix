package net.minecraft.client.renderer.block.model;

import net.minecraft.util.Facing;

enum ItemModelGeneratorSpanFacing {
    UP("UP", 0, Facing.UP, 0, -1),
    DOWN("DOWN", 1, Facing.DOWN, 0, 1),
    LEFT("LEFT", 2, Facing.EAST, -1, 0),
    RIGHT("RIGHT", 3, Facing.WEST, 1, 0);
    private static ItemModelGeneratorSpanFacing[] aQu;

    private Facing facing;
    private int field_178374_g;
    private int field_178373_f;

    private boolean func_178369_d() {
        return this == net.minecraft.client.renderer.block.model.ItemModelGeneratorSpanFacing.DOWN
                || this == net.minecraft.client.renderer.block.model.ItemModelGeneratorSpanFacing.UP;
    }

    ItemModelGeneratorSpanFacing(String text, int l, Facing aAHValue, int j, int k) {
        this.facing = aAHValue;
        this.field_178373_f = j;
        this.field_178374_g = k;
    }

    public int func_178372_b() {
        return this.field_178373_f;
    }

    // region String decoding support

    // endregion

    static boolean access$000(ItemModelGeneratorSpanFacing itemModelGeneratorSpanFacing) {
        return itemModelGeneratorSpanFacing.func_178369_d();
    }

    static {
        aQu =
                new ItemModelGeneratorSpanFacing[] {
                    net.minecraft.client.renderer.block.model.ItemModelGeneratorSpanFacing.UP,
                    net.minecraft.client.renderer.block.model.ItemModelGeneratorSpanFacing.DOWN,
                    net.minecraft.client.renderer.block.model.ItemModelGeneratorSpanFacing.LEFT,
                    net.minecraft.client.renderer.block.model.ItemModelGeneratorSpanFacing.RIGHT
                };
    }

    public int func_178371_c() {
        return this.field_178374_g;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public Facing getFacing() {
        return this.facing;
    }
}
