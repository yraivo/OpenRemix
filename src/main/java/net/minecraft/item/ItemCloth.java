package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemCloth extends ItemBlock {
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    public ItemCloth(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return super.getUnlocalizedName()
                + packedTextFragments[0]
                + EnumDyeColor.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }

    public int getMetadata(int i) {
        return i;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.item.ItemCloth.packedTextFragments[0] = new String(".");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }
}
