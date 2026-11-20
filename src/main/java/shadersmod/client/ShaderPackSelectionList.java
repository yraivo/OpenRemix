package shadersmod.client;

import net.minecraft.client.gui.GuiSlot;
import optifine.Lang;

import java.util.ArrayList;

class ShaderPackSelectionList extends GuiSlot {
    private long lastClickedCached;

    GuiShaders shadersGui;

    private int selectedIndex;
    private ArrayList shaderslist;

    @Override
    protected void drawSlot(int i, int k, int j, int l, int i1, int j1) {
        String text;
        {
            int[] aint1 = ShaderOption.lz();
            text = (String) this.shaderslist.get(i);
            int[] aint = aint1;
            if (text.equals(Shaders.packNameNone)) {
                text = Lang.get("of.options.shaders.packNone");
                if (!(aint == null)) {

                    if (text.equals(Shaders.packNameDefault)) {
                        text = Lang.get("of.options.shaders.packDefault");
                    }
                }
            } else {

                if (text.equals(Shaders.packNameDefault)) {
                    text = Lang.get("of.options.shaders.packDefault");
                }
            }
        }

        this.shadersGui.drawCenteredString(text, this.width / 2, j + 1, 16777215);
    }

    @Override
    protected int getScrollBarX() {
        return this.width - 6;
    }

    public ShaderPackSelectionList(GuiShaders shadersGuiValue, int i, int j, int k, int l, int i1) {
        super(shadersGuiValue.getMc(), i, j, k, l, i1);

        this.lastClickedCached = 0L;
        this.shadersGui = shadersGuiValue;
        this.updateList();
        this.amountScrolled = 0.0F;
        int j1 = this.selectedIndex * i1;
        int k1 = (l - k) / 2;
        if (j1 > k1) {
            this.scrollBy(j1 - k1);
        }
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    @Override
    protected void drawBackground() {}

    @Override
    public int cM() {
        return this.width - 20;
    }

    @Override
    protected boolean isSelected(int i) {
        return i == this.selectedIndex;
    }

    @Override
    protected void elementClicked(int selectedIndexValue, boolean flag, int j, int k) {
        if (selectedIndexValue != this.selectedIndex
                || this.lastClicked != this.lastClickedCached) {
            this.selectedIndex = selectedIndexValue;
            this.lastClickedCached = this.lastClicked;
            Shaders.setShaderPack((String) this.shaderslist.get(selectedIndexValue));
            Shaders.uninit();
            this.shadersGui.updateButtons();
        }
    }

    @Override
    protected int getSize() {
        return this.shaderslist.size();
    }


    public void updateList() {
        this.shaderslist = Shaders.listOfShaders();
        int[] aint1 = ShaderOption.lz();
        this.selectedIndex = 0;
        int[] aint = aint1;
        int selectedIndexValue = 0;
        int j = this.shaderslist.size();

        while (selectedIndexValue < j) {
            if (((String) this.shaderslist.get(selectedIndexValue))
                    .equals(Shaders.currentshadername)) {
                this.selectedIndex = selectedIndexValue;
                if (aint == null) {
                    break;
                }
            }

            selectedIndexValue++;
            if (aint != null) {
                break;
            }
        }
    }

    @Override
    protected int getContentHeight() {
        return this.getSize() * 18;
    }
}
