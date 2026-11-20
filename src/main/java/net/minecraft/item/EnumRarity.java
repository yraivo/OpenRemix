package net.minecraft.item;

import net.minecraft.util.EnumChatFormatting;

public enum EnumRarity {
    COMMON("COMMON", 0, EnumChatFormatting.WHITE, "Common"),
    UNCOMMON("UNCOMMON", 1, EnumChatFormatting.YELLOW, "Uncommon"),
    RARE("RARE", 2, EnumChatFormatting.AQUA, "Rare"),
    EPIC("EPIC", 3, EnumChatFormatting.LIGHT_PURPLE, "Epic");

    private static EnumRarity[] aak;

    public EnumChatFormatting rarityColor;

    public String rarityName;

    static {
        aak =
                new EnumRarity[] {
                    net.minecraft.item.EnumRarity.COMMON,
                    net.minecraft.item.EnumRarity.UNCOMMON,
                    net.minecraft.item.EnumRarity.RARE,
                    net.minecraft.item.EnumRarity.EPIC
                };
    }

    EnumRarity(String text, int j, EnumChatFormatting aalValue, String aarValue) {
        this.rarityColor = aalValue;
        this.rarityName = aarValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
