package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

public class GuiKeyBindingListKeyEntry implements GuiListExtendedIGuiListEntry {
    private KeyBinding keybinding;
    GuiKeyBindingList ast;

    private GuiButton btnReset;
    private GuiButton btnChangeKeyBinding;
    private String keyDesc;


    private GuiKeyBindingListKeyEntry(GuiKeyBindingList astValue, KeyBinding assValue) {
        this.ast = astValue;
        this.keybinding = assValue;
        this.keyDesc = I18n.format(assValue.getKeyDescription(), new Object[0]);
        this.btnChangeKeyBinding =
                new GuiButton(
                        0, 0, 0, 75, 18, I18n.format(assValue.getKeyDescription(), new Object[0]));
        this.btnReset =
                new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
    }

    public boolean mousePressed(int k, int i, int j, int l, int i1, int j1) {
        if (this.btnChangeKeyBinding.mousePressed(
                GuiKeyBindingList.method_b082c7fed71fca36020d(this.ast), i, j)) {
            GuiKeyBindingList.method_f61acc621d19cc3073d3(this.ast).buttonId = this.keybinding;
            return true;
        } else if (this.btnReset.mousePressed(
                GuiKeyBindingList.method_b082c7fed71fca36020d(this.ast), i, j)) {
            GuiKeyBindingList.method_b082c7fed71fca36020d(this.ast)
                    .gameSettings
                    .setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
            KeyBinding.resetKeyBindingArrayAndHash();
            return true;
        } else {
            return false;
        }
    }

    public void setSelected(int i, int j, int k) {}

    public void drawEntry(int k1, int i, int j, int l1, int k, int l, int i1, boolean flag) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        int j1 =
                GuiKeyBindingList.method_f61acc621d19cc3073d3(this.ast).buttonId == this.keybinding
                        ? 1
                        : 0;
        GuiKeyBindingList.method_b082c7fed71fca36020d(this.ast)
                .fontRendererObj
                .drawString(
                        this.keyDesc,
                        i + 90 - GuiKeyBindingList.method_2aa46adbeba92fbcd711(this.ast),
                        j
                                + k / 2
                                - GuiKeyBindingList.method_b082c7fed71fca36020d(this.ast)
                                                .fontRendererObj
                                                .FONT_HEIGHT
                                        / 2,
                        16777215);
        this.btnReset.xPosition = i + 190;
        this.btnReset.yPosition = j;
        this.btnReset.enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault()
                                ? 1
                                : 0));
        this.btnReset.drawButton(GuiKeyBindingList.method_b082c7fed71fca36020d(this.ast), l, i1);
        this.btnChangeKeyBinding.xPosition = i + 105;
        this.btnChangeKeyBinding.yPosition = j;
        this.btnChangeKeyBinding.displayString =
                GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
        byte b0 = 0;
        if (this.keybinding.getKeyCode() != 0) {
            for (KeyBinding keyBinding :
                    GuiKeyBindingList.method_b082c7fed71fca36020d(this.ast)
                            .gameSettings
                            .keyBindings) {
                if (keyBinding != this.keybinding
                        && keyBinding.getKeyCode() == this.keybinding.getKeyCode()) {
                    b0 = 1;
                    if (astring == null) {
                        break;
                    }
                }

                if (astring != null) {
                    break;
                }
            }
        }

        {
            if (j1 != 0) {
                this.btnChangeKeyBinding.displayString =
                        EnumChatFormatting.WHITE
                                + "> "
                                + EnumChatFormatting.YELLOW
                                + this.btnChangeKeyBinding.displayString
                                + EnumChatFormatting.WHITE
                                + " <";
                if (!(astring == null)) {

                    if (b0 != 0) {
                        this.btnChangeKeyBinding.displayString =
                                EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
                    }
                }
            } else {

                if (b0 != 0) {
                    this.btnChangeKeyBinding.displayString =
                            EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
                }
            }
        }

        this.btnChangeKeyBinding.drawButton(
                GuiKeyBindingList.method_b082c7fed71fca36020d(this.ast), l, i1);
    }

    GuiKeyBindingListKeyEntry(
            GuiKeyBindingList guiKeyBindingList, KeyBinding keyBinding, Object object) {
        this(guiKeyBindingList, keyBinding);
    }

    public void mouseReleased(int k, int i, int j, int l, int i1, int j1) {
        this.btnChangeKeyBinding.mouseReleased(i, j);
        this.btnReset.mouseReleased(i, j);
    }
}
