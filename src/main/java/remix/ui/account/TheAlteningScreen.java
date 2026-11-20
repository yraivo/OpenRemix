package remix.ui.account;

import net.minecraft.client.gui.GuiScreen;
import remix.ui.widget.ColoredGuiButton;
import remix.ui.widget.PlaceholderGuiTextField;
import remix.ui.widget.ScrollableGuiSlot;
import remix.util.render.Render2D3DUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.ui.font.ClientFontRenderer;

import com.thealtening.api.TheAltening;
import com.thealtening.api.data.AccountData;

import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class TheAlteningScreen extends GuiScreen {
    private static String[] packedTextFragments;
    protected static ClientFontRenderer titleFont;

    public List<GuiTextField> apiKeyFields;
    public HashMap<Integer, String> fieldPlaceholders;
    public GuiScreen parentScreen;
    public static final int x = -14145496;
    public static int w;
    private final String screenTitle = "\u00a7fThe " + EnumChatFormatting.RESET + "Altening";
    private static ResourceLocation ALTENING_LOGO;
    protected static ClientFontRenderer statusFont;

    protected void actionPerformed(GuiButton button) throws IOException {
        boolean legacyAccountFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        if (!button.enabled) {
            return;
        }

        RemixClient.INSTANCE.getClientServices();
        AltServiceSwitcher service = ClientServices.altServiceSwitcher;
        AltLoginManager login = ClientServices.altLoginManager;
        switch (button.id) {
            case 0:
                generateAccount(login, service, legacyAccountFlow);
                if (!legacyAccountFlow) {
                    return;
                }
            case 2:
                this.mc.displayGuiScreen(this.parentScreen);
                if (!legacyAccountFlow) {
                    return;
                }
                break;
            case 3:
                break;
            default:
                return;
        }

        String token = this.apiKeyFields.get(0).getText();
        new Thread(() -> storeApiToken(login, service, token)).start();
    }

    private static void generateAccount(AltLoginManager login, AltServiceSwitcher service, boolean legacyAccountFlow) {
        if (!service.getApiToken().isEmpty()) {
            AccountData account = service.getTheAltening().generateAccount();
            if (account != null) {
                login.loginWithCredentials(account.getToken(), "RemixOP");
            }
            if (account == null || legacyAccountFlow) {
                login.setStatusMessage("\u00a74Failed\u00a7f getting alt token!");
            }
            if (!legacyAccountFlow) {
                return;
            }
        }
        login.setStatusMessage("\u00a7fPlease set your" + EnumChatFormatting.RESET + " token\u00a7f!");
    }

    private static void storeApiToken(AltLoginManager login, AltServiceSwitcher service, String token) {
        boolean legacyAccountFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        login.setStatusMessage("\u00a7eTrying to set token...");
        boolean hasApi = service.getTheAltening() != null;
        if (!hasApi) {
            RemixClient.INSTANCE.getClientServices();
            ClientServices.altServiceSwitcher.setTheAltening(new TheAltening(token));
        }
        if (hasApi || legacyAccountFlow) {
            service.getTheAltening().apiToken = token;
        }
        login.setStatusMessage("Successfully\u00a7f set the token!");
        service.setApiToken(token);
    }

    protected void keyTyped(char character, int keyCode) throws IOException {
        boolean legacyInputFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        for (GuiTextField field : this.apiKeyFields) {
            field.textboxKeyTyped(character, keyCode);
            if (legacyInputFlow) {
                break;
            }
        }
        if (this.apiKeyFields.get(0).isFocused() && (keyCode == 28 || keyCode == 156)) {
            this.actionPerformed(this.buttonList.get(2));
        }
        super.keyTyped(character, keyCode);
    }

    public void updateScreen() {
        boolean legacyInputFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        for (GuiTextField field : this.apiKeyFields) {
            field.updateCursorCounter();
            if (legacyInputFlow) {
                break;
            }
        }
        super.updateScreen();
    }

    static {
        remix.ui.account.TheAlteningScreen.packedTextFragments = new String[3];
        remix.ui.account.TheAlteningScreen.packedTextFragments[0] = new String("ODgpsb");
        w = new Color(-14935012).brighter().getRGB();
        remix.ui.account.TheAlteningScreen.statusFont = RemixClient.fonts.roboto19;
        remix.ui.account.TheAlteningScreen.titleFont = RemixClient.fonts.roboto32;
        remix.ui.account.TheAlteningScreen.ALTENING_LOGO = new ResourceLocation("remix/gui/altmanager/TheAlteningColored.png");
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.apiKeyFields.clear();
        int i = this.height / 2 + 5;
        this.buttonList.add(new ColoredGuiButton(0, this.width / 2 - 100, i, "Generate"));
        i += 23;
        this.buttonList.add(
                new ColoredGuiButton(3, this.width / 2 + 10, i, 90, 20, "Set API key"));
        this.apiKeyFields.add(
                new PlaceholderGuiTextField(
                        0, this.fontRendererObj, this.width / 2 - 100, i, 100, 16));
        i += 23;
        this.buttonList.add(new ColoredGuiButton(2, this.width / 2 - 100, i, "Back"));
        this.apiKeyFields.get(0).setFocused(true);
        this.fieldPlaceholders.put(0, "API key");
    }

    public TheAlteningScreen(GuiScreen guiScreen) {
        this.apiKeyFields = new ArrayList<>();
        this.fieldPlaceholders = new HashMap<>();
        this.parentScreen = guiScreen;
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean legacyInputFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        for (GuiTextField field : this.apiKeyFields) {
            field.mouseClicked(mouseX, mouseY, mouseButton);
            if (legacyInputFlow) {
                break;
            }
        }
    }

    public void drawScreen(int i, int j, float f) {
        Gui.drawRect(0.0, 0.0, this.width, this.height, -14145496);
        int k = this.width / 2;
        int l = this.height / 2 - 94;
        int rgb = new Color(-14671840).darker().getRGB();
        Gui.drawRect(
                this.width / 2 - 110, l + 15, this.width / 2 + 108, this.height / 2 + 82 - 2, rgb);
        Render2D3DUtils.drawShadow(
                this.width / 2 - 110 - 2,
                l + 15 - 2,
                this.width / 2 + 108 + 2,
                this.height / 2 + 82,
                0.2F);
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        Render2D3DUtils.drawBorder(
                this.width / 2 - 110 - 2,
                l + 15 - 2,
                this.width / 2 + 108 + 2,
                this.height / 2 + 82,
                1.0,
                -14671840);
        Render2D3DUtils.drawTextureModalWithPalette(
                remix.ui.account.TheAlteningScreen.ALTENING_LOGO, k - 17, l + 20, 74.0F, 84.0F, new int[] {0, 0, 0, 30});
        Render2D3DUtils.drawTextureModalWithPalette(
                remix.ui.account.TheAlteningScreen.ALTENING_LOGO, k - 18.5F, l + 19, 74.0F, 84.0F, new int[] {255, 255, 255, 255});
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        AltLoginManager altLogin = ClientServices.altLoginManager;
        String textValue = text;
        int j1 = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor();
        remix.ui.account.TheAlteningScreen.titleFont.drawCenteredString(this.screenTitle, this.width / 2, l + 63, -12465804);
        Gui.drawRect(
                this.width / 2 - 26, l + 82, this.width / 2 + 26, l + 82 + 1.2, -12465804);
        remix.ui.account.TheAlteningScreen.statusFont.drawCenteredString(
                altLogin.getStatusMessage(),
                this.width / 2,
                l + 86,
                j1);

        for (GuiTextField guiTextField : this.apiKeyFields) {
            guiTextField.drawTextBox();
            if (guiTextField.getText().length() == 0 && !guiTextField.isFocused()) {
                remix.ui.account.TheAlteningScreen.statusFont.drawString(
                        this.fieldPlaceholders.get(guiTextField.getId()),
                        guiTextField.xPosition + 4,
                        guiTextField.yPosition + guiTextField.height / 3.4F,
                        Render2D3DUtils.interpolateColor(
                                16777215, -16777216, 0.5F));
            }

            if (textValue != null) {
                break;
            }
        }

        super.drawScreen(i, j, f);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ScrollableGuiSlot.setLegacySlotFlowMarker(packedTextFragments[0]);
        }
    }
}
