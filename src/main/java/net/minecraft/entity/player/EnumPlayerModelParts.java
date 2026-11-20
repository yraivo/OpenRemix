package net.minecraft.entity.player;

import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;

public enum EnumPlayerModelParts {
    CAPE("CAPE", 0, 0, "cape"),
    JACKET("JACKET", 1, 1, "jacket"),
    LEFT_SLEEVE("LEFT_SLEEVE", 2, 2, "left_sleeve"),
    RIGHT_SLEEVE("RIGHT_SLEEVE", 3, 3, "right_sleeve"),
    LEFT_PANTS_LEG("LEFT_PANTS_LEG", 4, 4, "left_pants_leg"),
    RIGHT_PANTS_LEG("RIGHT_PANTS_LEG", 5, 5, "right_pants_leg"),
    HAT("HAT", 6, 6, "hat");
    private static EnumPlayerModelParts[] agK;

    private int partMask;

    private int partId;
    private String partName;

    private ChatComponent field_179339_k;

    // region String decoding support

    // endregion

    public int getPartId() {
        return this.partId;
    }

    static {
        agK =
                new EnumPlayerModelParts[] {
                    net.minecraft.entity.player.EnumPlayerModelParts.CAPE,
                    net.minecraft.entity.player.EnumPlayerModelParts.JACKET,
                    net.minecraft.entity.player.EnumPlayerModelParts.LEFT_SLEEVE,
                    net.minecraft.entity.player.EnumPlayerModelParts.RIGHT_SLEEVE,
                    net.minecraft.entity.player.EnumPlayerModelParts.LEFT_PANTS_LEG,
                    net.minecraft.entity.player.EnumPlayerModelParts.RIGHT_PANTS_LEG,
                    net.minecraft.entity.player.EnumPlayerModelParts.HAT
                };
    }

    // region String decoding support

    // endregion

    public int getPartMask() {
        return this.partMask;
    }

    public String getPartName() {
        return this.partName;
    }

    // region String decoding support

    // endregion

    EnumPlayerModelParts(String text, int k, int j, String textValue) {
        this.partId = j;
        this.partMask = 1 << j;
        this.partName = textValue;
        this.field_179339_k =
                new ChatComponentTranslation("options.modelPart." + textValue, new Object[0]);
    }

    public ChatComponent func_179326_d() {
        return this.field_179339_k;
    }
}
