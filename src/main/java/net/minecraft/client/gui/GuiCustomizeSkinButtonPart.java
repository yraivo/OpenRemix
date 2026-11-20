package net.minecraft.client.gui;

import net.minecraft.entity.player.EnumPlayerModelParts;

class GuiCustomizeSkinButtonPart extends GuiButton {
    GuiCustomizeSkin aqV;
    private EnumPlayerModelParts playerModelParts;

    GuiCustomizeSkinButtonPart(
            GuiCustomizeSkin guiCustomizeSkin,
            int i,
            int j,
            int k,
            int l,
            int i1,
            EnumPlayerModelParts enumPlayerModelParts,
            Object object) {
        this(guiCustomizeSkin, i, j, k, l, i1, enumPlayerModelParts);
    }

    static EnumPlayerModelParts method_bf86ebcfac415b97d359(
            GuiCustomizeSkinButtonPart guiCustomizeSkinButtonPart) {
        return guiCustomizeSkinButtonPart.playerModelParts;
    }

    private GuiCustomizeSkinButtonPart(
            GuiCustomizeSkin aqVValue,
            int i,
            int j,
            int k,
            int l,
            int i1,
            EnumPlayerModelParts aqWValue) {
        super(
                i,
                j,
                k,
                l,
                i1,
                GuiCustomizeSkin.method_f1d7a08e803801cf35ab(aqVValue, aqWValue));
        this.aqV = aqVValue;
        this.playerModelParts = aqWValue;
    }
}
