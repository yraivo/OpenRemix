package optifine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiScreen;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GuiMessage extends GuiScreen {
    private GuiScreen parentScreen;
    protected String confirmButtonText;
    private String messageLine1;
    private final List listLines2 = Lists.newArrayList();
    private static String yz;
    private String messageLine2;
    private int ticksUntilEnable;

    // region String decoding support

    // endregion

    public void drawScreen(int i, int j, float f) {
        int i1 = ReflectorMethod.cR();
        this.drawDefaultBackground();
        int k = i1;
        this.drawCenteredString(
                this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
        int l = 90;

        for (java.util.Iterator<?> __recoveryIterator171_1502 = (this.listLines2).iterator();
                (__recoveryIterator171_1502).hasNext(); ) {
            String text = (String) (__recoveryIterator171_1502).next();
            this.drawCenteredString(this.fontRendererObj, text, this.width / 2, l, 16777215);
            l += this.fontRendererObj.FONT_HEIGHT;
            if (k == 0) {
                break;
            }
        }

        super.drawScreen(i, j, f);
    }

    public void updateScreen() {
        int j = ReflectorMethod.cR();
        super.updateScreen();
        int i = j;
        if (--this.ticksUntilEnable == 0) {
            for (java.util.Iterator<?> __recoveryIterator171_1940 = (this.buttonList).iterator();
                    (__recoveryIterator171_1940).hasNext(); ) {
                GuiButton guiButton = (GuiButton) (__recoveryIterator171_1940).next();
                guiButton.enabled = true;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public void initGui() {
        this.buttonList.add(
                new GuiOptionButton(
                        0, this.width / 2 - 74, this.height / 6 + 96, this.confirmButtonText));
        this.listLines2.clear();
        this.listLines2.addAll(
                this.fontRendererObj.listFormattedStringToWidth(
                        this.messageLine2, this.width - 50));
    }

    public void a(int ticksUntilEnableValue) {
        this.ticksUntilEnable = ticksUntilEnableValue;
        int k = ReflectorMethod.f();
        Iterator iterator = this.buttonList.iterator();
        int j = k;

        while (iterator.hasNext()) {
            GuiButton guiButton = (GuiButton) iterator.next();
            guiButton.enabled = false;
            if (j != 0) {
                break;
            }
        }
    }

    static {
        yz = "gui.done";
    }

    public GuiMessage(GuiScreen guiScreen, String text, String textValue) {
        this.parentScreen = guiScreen;
        this.messageLine1 = text;
        this.messageLine2 = textValue;
        this.confirmButtonText = I18n.format(yz, new Object[0]);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        OptiFineConfig.getMinecraft().displayGuiScreen(this.parentScreen);
    }
}
