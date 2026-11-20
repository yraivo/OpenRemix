package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.ResourceLocation;

public class FlatLayerInfo {
    private static String[] packedTextFragments;
    private int layerMinimumY;
    private static String e;
    private int a;
    private BlockState Ju;
    private int layerCount = 1;

    // region String decoding support

    // endregion

    public void setMinY(int layerMinimumYValue) {
        this.layerMinimumY = layerMinimumYValue;
    }

    public int getLayerCount() {
        return this.layerCount;
    }

    private int getFillBlockMeta() {
        return this.Ju.getBlock().getMetaFromState(this.Ju);
    }

    public int getMinY() {
        return this.layerMinimumY;
    }

    @Override
    public String toString() {
        String text;
        if (this.a >= 3) {
            ResourceLocation nameForObject =
                    (ResourceLocation) Block.blockRegistry.getNameForObject(this.Fc());
            text = nameForObject == null ? e : nameForObject.toString();
            if (this.layerCount > 1) {
                text = this.layerCount + packedTextFragments[0] + text;
            }
        } else {
            text = Integer.toString(Block.getIdFromBlock(this.Fc()));
            if (this.layerCount > 1) {
                text = this.layerCount + packedTextFragments[1] + text;
            }
        }

        int fillBlockMeta = this.getFillBlockMeta();
        if (fillBlockMeta > 0) {
            text = text + packedTextFragments[2] + fillBlockMeta;
        }

        return text;
    }

    // region String decoding support

    // endregion

    public BlockState ID() {
        return this.Ju;
    }

    public FlatLayerInfo(int i, Block block) {
        this(3, i, block);
    }

    // region String decoding support

    // endregion

    public FlatLayerInfo(int i, int j, Block block) {
        this.a = i;
        this.layerCount = j;
        this.Ju = block.getDefaultState();
    }

    // region String decoding support

    // endregion

    private Block Fc() {
        return this.Ju.getBlock();
    }

    public FlatLayerInfo(int i, int j, Block block, int k) {
        this(i, j, block);
        this.Ju = block.getStateFromMeta(k);
    }

    static {
        net.minecraft.world.gen.FlatLayerInfo.packedTextFragments = new String[4];
        net.minecraft.world.gen.FlatLayerInfo.packedTextFragments[0] = new String("*");
        net.minecraft.world.gen.FlatLayerInfo.packedTextFragments[1] = new String("x");
        net.minecraft.world.gen.FlatLayerInfo.packedTextFragments[2] = new String(":");
        net.minecraft.world.gen.FlatLayerInfo.packedTextFragments[3] = new String("1\u0014JO");
        e = "null";
    }
}
