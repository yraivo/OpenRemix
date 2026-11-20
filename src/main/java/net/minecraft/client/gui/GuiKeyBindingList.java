package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class GuiKeyBindingList extends GuiListExtended {
    private int maxListLabelWidth;
    private GuiControls aru;
    private GuiListExtendedIGuiListEntry[] listEntries;
    private MinecraftClient mc;

    protected int getScrollBarX() {
        return super.getScrollBarX() + 15;
    }

    public GuiListExtendedIGuiListEntry getListEntry(int i) {
        return this.listEntries[i];
    }

    static MinecraftClient method_b082c7fed71fca36020d(GuiKeyBindingList guiKeyBindingList) {
        return guiKeyBindingList.mc;
    }

    static GuiControls method_f61acc621d19cc3073d3(GuiKeyBindingList guiKeyBindingList) {
        return guiKeyBindingList.aru;
    }

    public GuiKeyBindingList(GuiControls guiControls, MinecraftClient minecraftClient) {
        this(guiControls, minecraftClient, Gui.getLegacyGuiRenderMarker());
    }

    private GuiKeyBindingList(
            GuiControls aruValue, MinecraftClient arwValue, String[] initialFlowMarker) {
        super(arwValue, aruValue.width, aruValue.height, 63, aruValue.height - 32, 20);

        String[] astring1 = initialFlowMarker;

        this.maxListLabelWidth = 0;
        this.aru = aruValue;
        this.mc = arwValue;
        KeyBinding[] keyBindingArray =
                (KeyBinding[]) ArrayUtils.clone(arwValue.gameSettings.keyBindings);
        this.listEntries =
                new GuiListExtendedIGuiListEntry
                        [keyBindingArray.length + KeyBinding.getKeybinds().size()];
        Arrays.sort(keyBindingArray);
        int i = 0;
        String text = null;
        KeyBinding[] keyBindingArrayValue = keyBindingArray;
        int j = keyBindingArray.length;
        String[] astring = astring1;
        int k = 0;

        while (k < j) {
            KeyBinding keyBinding = keyBindingArrayValue[k];
            String textValue = keyBinding.getKeyCategory();
            if (!textValue.equals(text)) {
                text = textValue;
                this.listEntries[i++] = new GuiKeyBindingListCategoryEntry(this, textValue);
            }

            int l =
                    arwValue.fontRendererObj.getStringWidth(
                            I18n.format(keyBinding.getKeyDescription(), new Object[0]));
            if (l > this.maxListLabelWidth) {
                this.maxListLabelWidth = l;
            }

            this.listEntries[i++] = new GuiKeyBindingListKeyEntry(this, keyBinding, null);
            k++;
            if (astring != null) {
                break;
            }
        }
    }

    protected int getSize() {
        return this.listEntries.length;
    }

    public int cM() {
        return super.cM() + 32;
    }

    static int method_2aa46adbeba92fbcd711(GuiKeyBindingList guiKeyBindingList) {
        return guiKeyBindingList.maxListLabelWidth;
    }
}
