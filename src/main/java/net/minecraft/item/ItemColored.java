package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemColored extends ItemBlock {
    private String[] subtypeNames;
    private Block coloredBlock;
    private static String[] packedTextFragments;

    public ItemColored setSubtypeNames(String[] astring) {
        this.subtypeNames = astring;
        return this;
    }

    public ItemColored(Block bmcValue, boolean flag) {
        super(bmcValue);
        this.coloredBlock = bmcValue;
        if (flag) {
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
    }

    public int getMetadata(int i) {
        return i;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.item.ItemColored.packedTextFragments[0] = new String(".");
    }

    // endregion

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        return this.coloredBlock.getRenderColor(this.coloredBlock.getStateFromMeta(itemStack.getItemDamage()));
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        if (this.subtypeNames == null) {
            return super.getUnlocalizedName(itemStack);
        }

        int itemDamage = itemStack.getItemDamage();
        return itemDamage >= 0 && itemDamage < this.subtypeNames.length
                ? super.getUnlocalizedName(itemStack) + packedTextFragments[0] + this.subtypeNames[itemDamage]
                : super.getUnlocalizedName(itemStack);
    }

    // region String decoding support

    // endregion
}
