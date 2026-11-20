package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;

public class GuiPageButtonListGuiEntry implements GuiListExtendedIGuiListEntry {
    private Gui asy;
    private Gui asz;
    private Gui asA;
    private final MinecraftClient ec = MinecraftClient.getMinecraft();

    private void method_cd28dbc96ff87210caf3(Gui gui, int i, int j, int k) {
        if (gui != null && gui instanceof GuiButton) {
            this.method_89b50416ca1be082f723((GuiButton) gui, i, j, k);
        }
    }

    private void method_89b50416ca1be082f723(GuiButton guiButton, int i, int j, int k) {
        guiButton.mouseReleased(i, j);
    }

    public boolean mousePressed(int l, int i, int j, int k, int i1, int j1) {
        boolean flag = this.method_b5f349f48a3c95d09b87(this.asz, i, j, k);
        boolean flag1 = this.method_b5f349f48a3c95d09b87(this.asA, i, j, k);
        return flag || flag1;
    }

    private void method_f0a7891bdbd983bd87a3(Gui gui, int i, int j, int k, boolean flag) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (gui != null) {
            if (gui instanceof GuiButton) {
                this.method_44b69682648cc17d45ca((GuiButton) gui, i, j, k, flag);
                if (astring == null) {
                    return;
                }
            }

            if (gui instanceof GuiTextField) {
                this.method_64a652dc527c5b1a94d3((GuiTextField) gui, i, flag);
                if (astring == null) {
                    return;
                }
            }

            if (gui instanceof GuiLabel) {
                this.method_b95124ce72b80ec33588((GuiLabel) gui, i, j, k, flag);
            }
        }
    }

    private void method_64a652dc527c5b1a94d3(
            GuiTextField guiTextField, int yPosition, boolean flag) {
        guiTextField.yPosition = yPosition;
        if (!flag) {
            guiTextField.drawTextBox();
        }
    }

    private void method_b95124ce72b80ec33588(GuiLabel guiLabel, int i, int j, int k, boolean flag) {
        guiLabel.mu = i;
        if (!flag) {
            guiLabel.drawLabel(this.ec, j, k);
        }
    }

    private void method_432a60a64e860d51d239(GuiTextField asyValue, int i, int j, int k) {
        asyValue.mouseClicked(i, j, k);
        if (asyValue.isFocused()) {
            this.asy = asyValue;
        }
    }

    static Gui access$000(GuiPageButtonListGuiEntry guiPageButtonListGuiEntry) {
        return guiPageButtonListGuiEntry.asy;
    }

    public Gui vi() {
        return this.asz;
    }

    public void setSelected(int j, int k, int i) {
        this.method_f0a7891bdbd983bd87a3(this.asz, i, 0, 0, true);
        this.method_f0a7891bdbd983bd87a3(this.asA, i, 0, 0, true);
    }

    public GuiPageButtonListGuiEntry(Gui aszValue, Gui asAValue) {
        this.asz = aszValue;
        this.asA = asAValue;
    }

    public void mouseReleased(int l, int i, int j, int k, int i1, int j1) {
        this.method_cd28dbc96ff87210caf3(this.asz, i, j, k);
        this.method_cd28dbc96ff87210caf3(this.asA, i, j, k);
    }

    private boolean method_7d468180d20a42873474(GuiButton asyValue, int i, int j, int k) {
        boolean flag = asyValue.mousePressed(this.ec, i, j);
        if (flag) {
            this.asy = asyValue;
        }

        return flag;
    }

    private void method_44b69682648cc17d45ca(
            GuiButton guiButton, int yPosition, int j, int k, boolean flag) {
        guiButton.yPosition = yPosition;
        if (!flag) {
            guiButton.drawButton(this.ec, j, k);
        }
    }

    private boolean method_b5f349f48a3c95d09b87(Gui gui, int i, int j, int k) {
        if (gui == null) {
            return false;
        }

        if (gui instanceof GuiButton) {
            return this.method_7d468180d20a42873474((GuiButton) gui, i, j, k);
        }

        if (gui instanceof GuiTextField) {
            this.method_432a60a64e860d51d239((GuiTextField) gui, i, j, k);
        }

        return false;
    }

    public void drawEntry(int l, int i1, int i, int j1, int k1, int j, int k, boolean flag) {
        this.method_f0a7891bdbd983bd87a3(this.asz, i, j, k, false);
        this.method_f0a7891bdbd983bd87a3(this.asA, i, j, k, false);
    }

    public Gui vj() {
        return this.asA;
    }
}
