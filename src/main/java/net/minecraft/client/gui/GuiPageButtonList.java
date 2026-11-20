package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.IntHashMap;

import com.google.common.collect.Lists;

import java.util.List;

public class GuiPageButtonList extends GuiListExtended {
    private int B;
    private IntHashMap asD;
    private GuiPageButtonListGuiResponder asE;
    private List rp;
    private Gui asF;
    private GuiPageButtonListGuiListEntry[][] asG;
    private final List xU = Lists.newArrayList();
    private static String[] packedTextFragments;

    private void method_6b802a8dba45a02bfb5a(int i, int j) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GuiPageButtonListGuiListEntry[] guiPageButtonListGuiListEntryArray = this.asG[i];
        String[] astring = astring1;

        for (GuiPageButtonListGuiListEntry guiPageButtonListGuiListEntry :
                guiPageButtonListGuiListEntryArray) {
            if (guiPageButtonListGuiListEntry != null) {
                this.method_cb3cf46e25d6d9101740(
                        (Gui)
                                this.asD.lookup(
                                        guiPageButtonListGuiListEntry
                                                .method_9b417d27949cba33a0d0()),
                        false);
            }

            if (astring != null) {
                break;
            }
        }

        for (GuiPageButtonListGuiListEntry guiPageButtonListGuiListEntryValue : this.asG[j]) {
            if (guiPageButtonListGuiListEntryValue != null) {
                this.method_cb3cf46e25d6d9101740(
                        (Gui)
                                this.asD.lookup(
                                        guiPageButtonListGuiListEntryValue
                                                .method_9b417d27949cba33a0d0()),
                        true);
            }

            if (astring != null) {
                break;
            }
        }
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public Gui vm() {
        return this.asF;
    }

    public int getSize() {
        return this.xU.size();
    }

    private void method_8d9b536bcdc9991c48fb() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GuiPageButtonListGuiListEntry[][] asGValue = this.asG;
        int length = asGValue.length;
        String[] astring = astring1;
        int j = 0;

        while (j < length) {
            GuiPageButtonListGuiListEntry[] guiPageButtonListGuiListEntryArray = asGValue[j];
            byte b0 = 0;

            while (true) {
                if (b0 < guiPageButtonListGuiListEntryArray.length) {
                    GuiPageButtonListGuiListEntry guiPageButtonListGuiListEntry =
                            guiPageButtonListGuiListEntryArray[b0];
                    GuiPageButtonListGuiListEntry guiPageButtonListGuiListEntryValue =
                            b0 < guiPageButtonListGuiListEntryArray.length - 1
                                    ? guiPageButtonListGuiListEntryArray[b0 + 1]
                                    : null;
                    Gui gui =
                            this.method_c405191781cda70764b3(
                                    guiPageButtonListGuiListEntry,
                                    0,
                                    guiPageButtonListGuiListEntryValue == null);
                    Gui guiValue =
                            this.method_c405191781cda70764b3(
                                    guiPageButtonListGuiListEntryValue,
                                    160,
                                    guiPageButtonListGuiListEntry == null);
                    GuiPageButtonListGuiEntry guiPageButtonListGuiEntry =
                            new GuiPageButtonListGuiEntry(gui, guiValue);
                    this.xU.add(guiPageButtonListGuiEntry);
                    if (guiPageButtonListGuiListEntry != null && gui != null) {
                        this.asD.addKey(
                                guiPageButtonListGuiListEntry.method_9b417d27949cba33a0d0(), gui);
                        if (gui instanceof GuiTextField) {
                            this.rp.add((GuiTextField) gui);
                        }
                    }

                    if (guiPageButtonListGuiListEntryValue != null && guiValue != null) {
                        this.asD.addKey(
                                guiPageButtonListGuiListEntryValue.method_9b417d27949cba33a0d0(),
                                guiValue);
                        if (guiValue instanceof GuiTextField) {
                            this.rp.add((GuiTextField) guiValue);
                        }
                    }

                    b0 += 2;
                    if (astring == null) {
                        continue;
                    }
                }

                j++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    private void method_cb3cf46e25d6d9101740(Gui gui, boolean visible) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (gui instanceof GuiButton) {
            ((GuiButton) gui).visible = visible;
            if (astring == null) {
                return;
            }
        }

        if (gui instanceof GuiTextField) {
            ((GuiTextField) gui).setVisible(visible);
            if (astring == null) {
                return;
            }
        }

        if (gui instanceof GuiLabel) {
            ((GuiLabel) gui).visible = visible;
        }
    }

    public boolean mouseClicked(int p171_0, int p171_1, int p171_2) {
        boolean flag = super.mouseClicked(p171_0, p171_1, p171_2);
        int l = this.getSlotIndexFromScreenCoords(p171_0, p171_1);
        if (l >= 0) {
            net.minecraft.client.gui.GuiPageButtonListGuiEntry guiPageButtonListGuiEntry = this.Q(l);
            if (this.asF != net.minecraft.client.gui.GuiPageButtonListGuiEntry.access$000(guiPageButtonListGuiEntry)
                    && this.asF != null
                    && this.asF instanceof net.minecraft.client.gui.GuiTextField) {
                ((net.minecraft.client.gui.GuiTextField) this.asF).setFocused(false);
            }

            this.asF = net.minecraft.client.gui.GuiPageButtonListGuiEntry.access$000(guiPageButtonListGuiEntry);
        }

        return flag;
    }

    public GuiListExtendedIGuiListEntry getListEntry(int i) {
        return this.Q(i);
    }

    protected int getScrollBarX() {
        return super.getScrollBarX() + 32;
    }

    public GuiPageButtonList(
            MinecraftClient minecraftClient,
            int i,
            int j,
            int k,
            int l,
            int i1,
            GuiPageButtonListGuiResponder asEValue,
            GuiPageButtonListGuiListEntry[]... asGValue) {
        super(minecraftClient, i, j, k, l, i1);
        this.asD = new IntHashMap();
        this.rp = Lists.newArrayList();
        this.asE = asEValue;
        this.asG = asGValue;
        this.kS = false;
        this.method_8d9b536bcdc9991c48fb();
        this.method_784e95624d6d70fe7f50();
    }

    private void method_784e95624d6d70fe7f50() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.xU.clear();
        int b0 = 0;
        String[] astring = astring1;

        while (b0 < this.asG[this.B].length) {
            GuiPageButtonListGuiListEntry guiPageButtonListGuiListEntry = this.asG[this.B][b0];
            GuiPageButtonListGuiListEntry guiPageButtonListGuiListEntryValue =
                    b0 < this.asG[this.B].length - 1 ? this.asG[this.B][b0 + 1] : null;
            Gui gui =
                    (Gui)
                            this.asD.lookup(
                                    guiPageButtonListGuiListEntry.method_9b417d27949cba33a0d0());
            Gui guiValue =
                    guiPageButtonListGuiListEntryValue != null
                            ? (Gui)
                                    this.asD.lookup(
                                            guiPageButtonListGuiListEntryValue
                                                    .method_9b417d27949cba33a0d0())
                            : null;
            GuiPageButtonListGuiEntry guiPageButtonListGuiEntry =
                    new GuiPageButtonListGuiEntry(gui, guiValue);
            this.xU.add(guiPageButtonListGuiEntry);
            b0 += 2;
            if (astring != null) {
                break;
            }
        }
    }

    private GuiListButton method_4eb082051771067e3c10(
            int i, int j, GuiPageButtonListGuiButtonEntry guiPageButtonListGuiButtonEntry) {
        GuiListButton guiListButton =
                new GuiListButton(
                        this.asE,
                        guiPageButtonListGuiButtonEntry.method_9b417d27949cba33a0d0(),
                        i,
                        j,
                        guiPageButtonListGuiButtonEntry.method_c60693f457aa70be7160(),
                        guiPageButtonListGuiButtonEntry.aY());
        guiListButton.visible = guiPageButtonListGuiButtonEntry.isEnabled();
        return guiListButton;
    }

    private GuiSlider method_6168a377d16e387092dd(
            int i, int j, GuiPageButtonListGuiSlideEntry guiPageButtonListGuiSlideEntry) {
        GuiSlider guiSlider =
                new GuiSlider(
                        this.asE,
                        guiPageButtonListGuiSlideEntry.method_9b417d27949cba33a0d0(),
                        i,
                        j,
                        guiPageButtonListGuiSlideEntry.method_c60693f457aa70be7160(),
                        guiPageButtonListGuiSlideEntry.aN(),
                        guiPageButtonListGuiSlideEntry.aM(),
                        guiPageButtonListGuiSlideEntry.aJ(),
                        guiPageButtonListGuiSlideEntry.vl());
        guiSlider.visible = guiPageButtonListGuiSlideEntry.isEnabled();
        return guiSlider;
    }

    // region String decoding support

    // endregion

    private Gui method_c405191781cda70764b3(
            GuiPageButtonListGuiListEntry guiPageButtonListGuiListEntry,
            int p171_1,
            boolean p171_2) {
        return (net.minecraft.client.gui.Gui)
                (guiPageButtonListGuiListEntry instanceof net.minecraft.client.gui.GuiPageButtonListGuiSlideEntry
                        ? this.method_6168a377d16e387092dd(
                                this.width / 2 - 155 + p171_1,
                                0,
                                (net.minecraft.client.gui.GuiPageButtonListGuiSlideEntry)
                                        guiPageButtonListGuiListEntry)
                        : (guiPageButtonListGuiListEntry
                                        instanceof net.minecraft.client.gui.GuiPageButtonListGuiButtonEntry
                                ? this.method_4eb082051771067e3c10(
                                        this.width / 2 - 155 + p171_1,
                                        0,
                                        (net.minecraft.client.gui.GuiPageButtonListGuiButtonEntry)
                                                guiPageButtonListGuiListEntry)
                                : (guiPageButtonListGuiListEntry
                                                instanceof net.minecraft.client.gui.GuiPageButtonListEditBoxEntry
                                        ? this.method_2baabd7df239561081b0(
                                                this.width / 2 - 155 + p171_1,
                                                0,
                                                (net.minecraft.client.gui.GuiPageButtonListEditBoxEntry)
                                                        guiPageButtonListGuiListEntry)
                                        : (guiPageButtonListGuiListEntry
                                                        instanceof net.minecraft.client.gui.GuiPageButtonListGuiLabelEntry
                                                ? this.method_cde7db7a623aabbab1be(
                                                        this.width / 2 - 155 + p171_1,
                                                        0,
                                                        (net.minecraft.client.gui.GuiPageButtonListGuiLabelEntry)
                                                                guiPageButtonListGuiListEntry,
                                                        p171_2)
                                                : null))));
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiPageButtonList.packedTextFragments[0] = new String(";");
    }

    // endregion

    private GuiLabel method_cde7db7a623aabbab1be(
            int p171_0, int p171_1, GuiPageButtonListGuiLabelEntry p171_2, boolean p171_3) {
        net.minecraft.client.gui.GuiLabel guiLabel;
        {
            java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
            if (p171_3) {
                guiLabel =
                        new net.minecraft.client.gui.GuiLabel(
                                this.mc.fontRendererObj,
                                p171_2.method_9b417d27949cba33a0d0(),
                                p171_0,
                                p171_1,
                                this.width - p171_0 * 2,
                                20,
                                -1);
                if (!(astring == null)) {

                    guiLabel =
                            new net.minecraft.client.gui.GuiLabel(
                                    this.mc.fontRendererObj,
                                    p171_2.method_9b417d27949cba33a0d0(),
                                    p171_0,
                                    p171_1,
                                    150,
                                    20,
                                    -1);
                }
            } else {

                guiLabel =
                        new net.minecraft.client.gui.GuiLabel(
                                this.mc.fontRendererObj,
                                p171_2.method_9b417d27949cba33a0d0(),
                                p171_0,
                                p171_1,
                                150,
                                20,
                                -1);
            }
        }

        guiLabel.visible = p171_2.isEnabled();
        guiLabel.method_be01d1eb5e587c583ed6(p171_2.method_c60693f457aa70be7160());
        guiLabel.setCentered();
        return guiLabel;
    }

    public GuiPageButtonListGuiEntry Q(int p171_0) {
        return (net.minecraft.client.gui.GuiPageButtonListGuiEntry) this.xU.get(p171_0);
    }

    public void method_f66a7ec06282ab1b380e(char p171_0, int p171_1) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (this.asF instanceof net.minecraft.client.gui.GuiTextField) {
            net.minecraft.client.gui.GuiTextField asFValue = (net.minecraft.client.gui.GuiTextField) this.asF;
            if (!net.minecraft.client.gui.GuiScreen.isKeyComboCtrlV(p171_1)) {
                if (p171_1 == 15) {
                    int k;
                    label75:
                    {
                        asFValue.setFocused(false);
                        k = this.rp.indexOf(this.asF);
                        if (net.minecraft.client.gui.GuiScreen.isShiftKeyDown()) {
                            if (k == 0) {
                                k = this.rp.size() - 1;
                                if (astring == null) {
                                    break label75;
                                }
                            }

                            k--;
                            if (astring == null) {
                                break label75;
                            }
                        }

                        if (k == this.rp.size() - 1) {
                            k = 0;
                            if (astring == null) {
                                break label75;
                            }
                        }

                        k++;
                    }

                    {
                        this.asF = (net.minecraft.client.gui.Gui) this.rp.get(k);
                        asFValue = (net.minecraft.client.gui.GuiTextField) this.asF;
                        asFValue.setFocused(true);
                        int l = asFValue.yPosition + this.slotHeight;
                        int j = asFValue.yPosition;
                        if (l > this.bottom) {
                            this.amountScrolled = this.amountScrolled + (l - this.bottom);
                            if (!(astring == null)) {

                                if (j < this.top) {
                                    this.amountScrolled = j;
                                }
                            }
                        } else {

                            if (j < this.top) {
                                this.amountScrolled = j;
                            }
                        }
                    }

                    if (astring == null) {
                        return;
                    }
                }

                asFValue.textboxKeyTyped(p171_0, p171_1);
                if (astring == null) {
                    return;
                }
            }

            java.lang.String text = net.minecraft.client.gui.GuiScreen.getClipboardString();
            java.lang.String[] astring2 =
                    text.split(net.minecraft.client.gui.GuiPageButtonList.packedTextFragments[0]);
            int l1 = this.rp.indexOf(this.asF);
            int i1 = l1;
            java.lang.String[] astring1 = astring2;
            int j1 = astring2.length;
            int k1 = 0;

            while (k1 < j1) {
                {
                    java.lang.String textValue = astring1[k1];
                    ((net.minecraft.client.gui.GuiTextField) this.rp.get(i1)).setText(textValue);
                    if (i1 == this.rp.size() - 1) {
                        i1 = 0;
                        if (!(astring == null)) {

                            i1++;
                        }
                    } else {

                        i1++;
                    }
                }

                if (i1 == l1 && astring == null) {
                    break;
                }

                k1++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    public void fF() {
        if (this.B < this.asG.length - 1) {
            int i = this.B++;
            this.method_784e95624d6d70fe7f50();
            this.method_6b802a8dba45a02bfb5a(i, this.B);
            this.amountScrolled = 0.0F;
        }
    }

    public int aW() {
        return this.asG.length;
    }

    public void cS() {
        if (this.B > 0) {
            int i = this.B--;
            this.method_784e95624d6d70fe7f50();
            this.method_6b802a8dba45a02bfb5a(i, this.B);
            this.amountScrolled = 0.0F;
        }
    }

    public int cM() {
        return 400;
    }

    private GuiTextField method_2baabd7df239561081b0(
            int p171_0, int p171_1, GuiPageButtonListEditBoxEntry guiPageButtonListEditBoxEntry) {
        net.minecraft.client.gui.GuiTextField guiTextField =
                new net.minecraft.client.gui.GuiTextField(
                        guiPageButtonListEditBoxEntry.method_9b417d27949cba33a0d0(),
                        this.mc.fontRendererObj,
                        p171_0,
                        p171_1,
                        150,
                        20);
        guiTextField.setText(guiPageButtonListEditBoxEntry.method_c60693f457aa70be7160());
        guiTextField.func_175207_a(this.asE);
        guiTextField.setVisible(guiPageButtonListEditBoxEntry.isEnabled());
        guiTextField.func_175205_a(guiPageButtonListEditBoxEntry.vk());
        return guiTextField;
    }

    public Gui R(int i) {
        return (Gui) this.asD.lookup(i);
    }

    public int method_beea3e16fe3ec0995eb9() {
        return this.B;
    }
}
