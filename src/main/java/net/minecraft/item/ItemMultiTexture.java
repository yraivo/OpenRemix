package net.minecraft.item;

import net.minecraft.block.Block;

import com.google.common.base.Function;

public class ItemMultiTexture extends ItemBlock {
    protected Block theBlock;
    private static String[] packedTextFragments;
    protected Function nameFunction;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.item.ItemMultiTexture.packedTextFragments[0] = new String(".");
    }

    // endregion

    public ItemMultiTexture(Block block, Block blockValue, String[] astring) {
        this(block, blockValue, new ItemMetadataNameFunction(astring));
    }

    public int getMetadata(int i) {
        return i;
    }

    // region String decoding support

    // endregion

    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName()
                + packedTextFragments[0]
                + (String) this.nameFunction.apply(itemStack);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public ItemMultiTexture(Block block, Block bmcValue, Function function) {
        super(block);
        this.theBlock = bmcValue;
        this.nameFunction = function;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
}
