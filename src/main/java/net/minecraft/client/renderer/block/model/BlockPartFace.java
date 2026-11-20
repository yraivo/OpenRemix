package net.minecraft.client.renderer.block.model;

import net.minecraft.util.Facing;

public class BlockPartFace {
    public int tintIndex;
    public static final Facing FACING_DEFAULT = null;
    public BlockFaceUV blockFaceUV;
    public Facing cullFace;
    public String texture;

    public BlockPartFace(
            Facing aAcValue, int i, String text, BlockFaceUV aAIValue) {
        this.cullFace = aAcValue;
        this.tintIndex = i;
        this.texture = text;
        this.blockFaceUV = aAIValue;
    }
}
