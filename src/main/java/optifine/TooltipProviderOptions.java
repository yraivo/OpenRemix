package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class TooltipProviderOptions {
    private static String[] packedTextFragments;
    private GuiScreen guiScreen = null;
    private int lastMouseY;
    private static String e;
    private long mouseStillTime;
    private int lastMouseX = 0;

    public TooltipProviderOptions(GuiScreen guiScreen) {
        this.lastMouseY = 0;

        this.mouseStillTime = 0L;
        this.guiScreen = guiScreen;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private GuiButton getSelectedButton(int i, int j, List list) {
        int l1 = ReflectorMethod.cR();
        int l = 0;
        int k = l1;

        while (l < list.size()) {
            GuiButton guiButton = (GuiButton) list.get(l);
            int i1 = GuiVideoSettings.getButtonWidth(guiButton);
            int j1 = GuiVideoSettings.getButtonHeight(guiButton);
            int k1 =
                    i >= guiButton.xPosition
                                    && j >= guiButton.yPosition
                                    && i < guiButton.xPosition + i1
                                    && j < guiButton.yPosition + j1
                            ? 1
                            : 0;
            if (k1 != 0) {
                return guiButton;
            }

            l++;
            if (k == 0) {
                break;
            }
        }

        return null;
    }

    private static String[] getTooltipLinesForOption(GameSettingsOptions gameSettingsOptions) {
        return getTooltipLines(gameSettingsOptions.getEnumString());
    }

    public void drawTooltips(int lastMouseXValue, int j, List list) {
        int k = ReflectorMethod.f();
        if (Math.abs(lastMouseXValue - this.lastMouseX) <= 5
                && Math.abs(j - this.lastMouseY) <= 5) {
            short short1 = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + short1) {
                int l = this.guiScreen.width / 2 - 150;
                int i1 = this.guiScreen.height / 6 - 7;
                if (j <= i1 + 98) {
                    i1 += 105;
                }

                int j1 = l + 150 + 150;
                int k1 = i1 + 84 + 10;
                GuiButton guiButton = this.getSelectedButton(lastMouseXValue, j, list);
                if (guiButton instanceof IOptionControl) {
                    IOptionControl iOptionControl =
                            (IOptionControl) guiButton;
                    GameSettingsOptions gameSettingsOptions = iOptionControl.getOption();
                    String[] astring = getTooltipLinesForOption(gameSettingsOptions);
                    if (astring == null) {
                        return;
                    }

                    GuiVideoSettings.drawGradientRect(
                            this.guiScreen, l, i1, j1, k1, -536870912, -536870912);
                    int l1 = 0;

                    while (l1 < astring.length) {
                        String text = astring[l1];
                        int i2 = 14540253;
                        if (text.endsWith(packedTextFragments[0])) {
                            i2 = 16719904;
                        }

                        FontRenderer anp = MinecraftClient.getMinecraft().fontRendererObj;
                        anp.drawStringWithShadow(text, l + 5, i1 + 5 + l1 * 11, i2);
                        l1++;
                        if (k != 0) {
                            break;
                        }
                    }
                }
            }

            if (k == 0) {
                return;
            }
        }

        this.lastMouseX = lastMouseXValue;
        this.lastMouseY = j;
        this.mouseStillTime = System.currentTimeMillis();
    }

    private static String[] getTooltipLines(String text) {
        int k = ReflectorMethod.f();
        ArrayList arrayList = new ArrayList();
        int j = 0;
        int i = k;

        while (j < 10) {
            String textValue = text + e + (j + 1);
            String textValue2 = Lang.get(textValue, (String) null);
            if (textValue2 == null && i == 0) {
                break;
            }

            arrayList.add(textValue2);
            j++;
            if (i != 0) {
                break;
            }
        }

        return arrayList.size() <= 0
                ? null
                : ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    static {
        optifine.TooltipProviderOptions.packedTextFragments = new String[2];
        optifine.TooltipProviderOptions.packedTextFragments[0] = new String("!");
        optifine.TooltipProviderOptions.packedTextFragments[1] = new String("`?\u0004\r\u0000Bw>e");
        e = ".tooltip.";
    }
}
