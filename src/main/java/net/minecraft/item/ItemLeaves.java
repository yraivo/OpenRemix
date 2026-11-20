package net.minecraft.item;

import net.minecraft.block.BlockLeaves;

public class ItemLeaves extends ItemBlock {
    private static String[] packedTextFragments;
    private BlockLeaves leaves;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.item.ItemLeaves.packedTextFragments[0] = new String(".");
    }

    // endregion

    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName()
                + packedTextFragments[0]
                + this.leaves.getWoodType(itemStack.getItemDamage()).getUnlocalizedName();
    }

    // region String decoding support

    // endregion

    public ItemLeaves(BlockLeaves btBValue) {
        super(btBValue);
        this.leaves = btBValue;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int i) {
        return i | 4;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        return this.leaves.getRenderColor(this.leaves.getStateFromMeta(itemStack.getItemDamage()));
    }
}
