package remix.ui.account;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.AccountsTextConfig;
import remix.ui.font.ClientFontRenderer;
import remix.ui.proxy.ProxyConfigScreen;
import remix.ui.widget.ColoredGuiButton;
import remix.ui.widget.IconGuiButton;
import remix.ui.widget.ModalTopJFileChooser1;
import remix.ui.widget.PlaceholderGuiTextField;
import remix.ui.widget.ScrollableGuiSlot;
import remix.util.render.Render2D3DUtils;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public final class AltManagerScreen extends GuiScreen {

    private boolean ym;
    private final String accountsHeading = "Accounts";
    private static ResourceLocation PROXY_ICON;
    private static String[] packedTextFragments;
    public GuiScreen parentScreen;
    public static final int C = -14671840;
    public static int A;
    public String retryIdentifier;
    public static final int B = -14145496;
    public ScrollableGuiSlot scrollableGuiSlot;
    public List<GuiTextField> credentialFields;
    public int selectedAccountIndex;
    private static ResourceLocation ALTENING_ICON;
    public HashMap<Integer, String> credentialFieldLabels;
    private static ResourceLocation IMPORT_ICON;

    protected static ClientFontRenderer statusFont;
    private AltLoginManager altLoginManager;
    public String retryPassword;

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.scrollableGuiSlot.handleMouseInput();
    }

    public AltManagerScreen(GuiScreen guiScreen) {
        this.retryPassword = packedTextFragments[0];
        this.retryIdentifier = packedTextFragments[1];
        this.credentialFields = new ArrayList<>();
        this.credentialFieldLabels = new HashMap<>();
        this.parentScreen = guiScreen;
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        this.altLoginManager = ClientServices.altLoginManager;
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        // The legacy slot marker allows the following button action to run as well.
        boolean legacyActionCascade = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        if (!button.enabled) {
            return;
        }

        switch (button.id) {
            case 0:
                this.addAccountFromFields();
                if (!legacyActionCascade) return;
            case 1:
                if (!this.loginSelectedAccount() || !legacyActionCascade) return;
            case 2:
                this.altLoginManager
                        .getAccounts()
                        .remove(this.altLoginManager.getAccounts().get(this.selectedAccountIndex));
                this.saveAccountChanges();
                if (!legacyActionCascade) return;
            case 3:
                if (this.retryIdentifier != null && !this.retryIdentifier.isEmpty()) {
                    this.altLoginManager.loginWithCredentials(
                            this.retryIdentifier, this.retryPassword);
                    if (!legacyActionCascade) return;
                }
                this.altLoginManager.setStatusMessage("\u00a7eLogin in the first place");
                if (!legacyActionCascade) return;
            case 4:
                this.loginFromFieldsOrClipboard(legacyActionCascade);
                if (!legacyActionCascade) return;
            case 5:
                this.mc.displayGuiScreen(this.parentScreen);
                if (!legacyActionCascade) return;
            case 6:
                if (!this.loginRandomAccount() || !legacyActionCascade) return;
            case 7:
                this.mc.displayGuiScreen(new TheAlteningScreen(this));
                if (!legacyActionCascade) return;
            case 8:
                if (!this.importAccountFile(legacyActionCascade) || !legacyActionCascade) return;
            case 9:
                this.mc.displayGuiScreen(new ProxyConfigScreen(this));
                return;
            default:
                return;
        }
    }

    private void addAccountFromFields() {
        String identifier = this.credentialFields.get(0).getText().trim();
        String password = this.credentialFields.get(1).getText();
        if (!identifier.isEmpty() && identifier.contains(packedTextFragments[2])) {
            String[] credentials = identifier.split(packedTextFragments[3]);
            identifier = credentials[0];
            password = credentials[1];
        }
        if (identifier.contains("@alt.com") && password.isEmpty()) {
            password = packedTextFragments[4];
        }
        this.altLoginManager.addAccountAndSave(identifier, password);
        this.saveAccountChanges();
    }

    private void saveAccountChanges() {
        ClientServices services = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ClientServices.configFileManager.saveConfig(AccountsTextConfig.class);
    }

    private boolean loginSelectedAccount() {
        AltAccountEntry selected =
                this.altLoginManager.getAccounts().get(this.selectedAccountIndex);
        if (selected.loginDisabled) {
            return false;
        }
        this.altLoginManager.login(selected);
        this.retryPassword = selected.getPassword();
        this.retryIdentifier = selected.getEmail();
        return true;
    }

    private boolean loginRandomAccount() {
        List<AltAccountEntry> accounts = this.altLoginManager.getAccounts();
        if (accounts.isEmpty()) {
            return false;
        }
        AltAccountEntry selected = accounts.get(new Random().nextInt(accounts.size()));
        if (!selected.loginDisabled) {
            this.altLoginManager.login(selected);
        }
        return true;
    }

    private void loginFromFieldsOrClipboard(boolean legacyActionCascade) {
        String identifier = this.credentialFields.get(0).getText().trim();
        String password = this.credentialFields.get(1).getText();
        boolean parsedInline = !identifier.isEmpty() && identifier.contains(packedTextFragments[5]);
        if (parsedInline) {
            String[] credentials = identifier.split(packedTextFragments[6]);
            identifier = credentials[0];
            password = credentials[1];
        }

        boolean invalidClipboard = false;
        if ((!parsedInline || legacyActionCascade) && identifier.isEmpty() && password.isEmpty()) {
            try {
                String data =
                        ((String)
                                        Toolkit.getDefaultToolkit()
                                                .getSystemClipboard()
                                                .getData(DataFlavor.stringFlavor))
                                .trim();
                boolean matchesFormat =
                        data.contains(packedTextFragments[7])
                                && !data.contains(packedTextFragments[8])
                                && !data.contains(packedTextFragments[9]);
                if (matchesFormat) {
                    String[] credentials = data.split(packedTextFragments[10]);
                    identifier = credentials[0];
                    password = credentials[1];
                }
                invalidClipboard = !matchesFormat || legacyActionCascade;
            } catch (Exception failure) {
                failure.printStackTrace();
            }
        }

        if (identifier.contains("@alt.com")) {
            password = packedTextFragments[11];
        }
        if (!identifier.isEmpty()) {
            this.altLoginManager.loginWithCredentials(identifier, password);
            // Preserve the original direct-login assignments; selected-account login uses the
            // opposite order.
            this.retryPassword = identifier;
            this.retryIdentifier = password;
            if (!legacyActionCascade) return;
        }
        if (invalidClipboard) {
            this.altLoginManager.setStatusMessage("\u00a74Invalid\u00a7f clipboard content!");
            if (!legacyActionCascade) return;
        }
        this.altLoginManager.setStatusMessage("\u00a7eClipboard and text box only");
    }

    private boolean importAccountFile(boolean legacyActionCascade) {
        ModalTopJFileChooser1 chooser =
                new ModalTopJFileChooser1(
                        this, FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFocusable(true);
        chooser.grabFocus();
        chooser.requestFocus();
        chooser.transferFocus();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(0);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
        if (chooser.isFocusable()) {
            chooser.requestFocus();
        }
        if (chooser.showOpenDialog(null) != 0) {
            return false;
        }

        File selectedFile = chooser.getSelectedFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(packedTextFragments[12]);
                String email = packedTextFragments[13];
                String username = packedTextFragments[13];
                String password = packedTextFragments[13];
                AltAccountType accountType = AltAccountType.CRACKED;
                switch (fields.length) {
                    case 1:
                        if (fields[0].contains(packedTextFragments[14]) || legacyActionCascade) {
                            return false;
                        }
                        username = fields[0];
                        break;
                    case 2:
                        email = fields[0];
                        password = fields[1];
                        accountType = AltAccountType.PREMIUM;
                        break;
                    case 3:
                        email = fields[0];
                        password = fields[1];
                        username = fields[2];
                        accountType = AltAccountType.PREMIUM;
                        break;
                }
                this.altLoginManager.addAccount(
                        new AltAccountEntry(email, username, password, accountType));
                if (legacyActionCascade) {
                    break;
                }
            }
        } catch (IOException failure) {
            failure.printStackTrace();
        }
        return true;
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        if (this.selectedAccountIndex < -1
                || this.selectedAccountIndex >= this.scrollableGuiSlot.getSize()) {
            this.selectedAccountIndex = -1;
        }

        ((GuiButton) this.buttonList.get(0)).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (((net.minecraft.client.gui.GuiTextField) this.credentialFields.get(0))
                                                .getText()
                                                .length()
                                        != 0
                                ? 1
                                : 0));
        ((GuiButton) this.buttonList.get(1)).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.selectedAccountIndex != -1 ? 1 : 0));
        ((GuiButton) this.buttonList.get(2)).enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.selectedAccountIndex != -1 ? 1 : 0));

        for (java.util.Iterator<?> __recoveryIterator171_41682 = (this.credentialFields).iterator();
                (__recoveryIterator171_41682).hasNext(); ) {
            GuiTextField guiTextField = (GuiTextField) (__recoveryIterator171_41682).next();
            guiTextField.updateCursorCounter();
            if (text != null) {
                break;
            }
        }

        super.updateScreen();
    }

    protected void keyTyped(char character, int keyCode) throws IOException {
        boolean legacyInputFlow = ScrollableGuiSlot.getLegacySlotFlowMarker() != null;
        for (GuiTextField field : this.credentialFields) {
            field.textboxKeyTyped(character, keyCode);
            if (legacyInputFlow) {
                break;
            }
        }

        GuiTextField username = this.credentialFields.get(0);
        GuiTextField password = this.credentialFields.get(1);
        boolean editing = username.isFocused() || password.isFocused();
        boolean enter = keyCode == 28 || keyCode == 156;
        this.handleAccountListKey(keyCode, editing, enter);

        if (editing) {
            if (keyCode == 15) {
                username.setFocused(!username.isFocused());
                password.setFocused(!password.isFocused());
            }
            if (enter) {
                this.actionPerformed(this.buttonList.get(0));
            }
        }
        super.keyTyped(character, keyCode);
    }

    private void handleAccountListKey(int keyCode, boolean editing, boolean enter) {
        int rowCount = this.scrollableGuiSlot.getSize();
        if (rowCount == 0) {
            return;
        }
        int step = 0;
        if (keyCode == 200 && this.selectedAccountIndex > 0) {
            step = -1;
        } else if (keyCode == 208 && this.selectedAccountIndex < rowCount - 1) {
            step = 1;
        }

        List<AltAccountEntry> accounts = this.altLoginManager.getAccounts();
        if (this.selectedAccountIndex >= 0 && this.selectedAccountIndex < accounts.size()) {
            AltAccountEntry selected = accounts.get(this.selectedAccountIndex);
            if (step != 0 && Keyboard.isKeyDown(42)) {
                accounts.remove(this.selectedAccountIndex);
                accounts.add(this.selectedAccountIndex + step, selected);
                RemixClient.INSTANCE.getClientServices();
                ClientServices.configFileManager.saveConfig(AccountsTextConfig.class);
            }
            if (enter) {
                this.altLoginManager.login(selected);
            }
            if (!editing && (keyCode == 14 || keyCode == 211)) {
                accounts.remove(selected);
            }
        }
        this.selectedAccountIndex += step;
    }

    static {
        remix.ui.account.AltManagerScreen.packedTextFragments = new String[17];
        remix.ui.account.AltManagerScreen.packedTextFragments[0] = new String("");
        remix.ui.account.AltManagerScreen.packedTextFragments[1] = new String("");
        remix.ui.account.AltManagerScreen.packedTextFragments[2] = new String(":");
        remix.ui.account.AltManagerScreen.packedTextFragments[3] = new String(":");
        remix.ui.account.AltManagerScreen.packedTextFragments[4] = new String("_");
        remix.ui.account.AltManagerScreen.packedTextFragments[5] = new String(":");
        remix.ui.account.AltManagerScreen.packedTextFragments[6] = new String(":");
        remix.ui.account.AltManagerScreen.packedTextFragments[7] = new String(":");
        remix.ui.account.AltManagerScreen.packedTextFragments[8] = new String(" ");
        remix.ui.account.AltManagerScreen.packedTextFragments[9] = new String("\n");
        remix.ui.account.AltManagerScreen.packedTextFragments[10] = new String(":");
        remix.ui.account.AltManagerScreen.packedTextFragments[11] = new String("_");
        remix.ui.account.AltManagerScreen.packedTextFragments[12] = new String(":");
        remix.ui.account.AltManagerScreen.packedTextFragments[13] = new String("");
        remix.ui.account.AltManagerScreen.packedTextFragments[14] = new String("@");
        remix.ui.account.AltManagerScreen.ALTENING_ICON =
                new ResourceLocation("remix/gui/altmanager/TheAltening.png");
        remix.ui.account.AltManagerScreen.PROXY_ICON =
                new ResourceLocation("remix/gui/altmanager/Proxy.png");
        remix.ui.account.AltManagerScreen.IMPORT_ICON =
                new ResourceLocation("remix/gui/altmanager/Import.png");
        remix.ui.account.AltManagerScreen.statusFont = RemixClient.fonts.roboto19;
        A = new Color(-14935012).brighter().getRGB();
        LookAndFeel lookandfeel = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable throwable) {
            lookandfeel = null;
        }
    }

    public void drawScreen(int i, int j, float f) {
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        Gui.drawRect(0.0, 0.0, this.width, this.height, -14145496);
        this.scrollableGuiSlot.drawScreen(i, j, f);
        Gui.drawRect(0.0, this.height, this.width, this.height - 48, -14145496);
        Gui.drawRect(0.0, 0.0, this.width, 27.0, -14145496);
        int k = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor();
        remix.ui.account.AltManagerScreen.statusFont.drawCenteredString(
                "Accounts", this.width / 2, 12 - this.fontRendererObj.FONT_HEIGHT, 16777215);
        String textValue = text;
        remix.ui.account.AltManagerScreen.statusFont.drawCenteredString(
                this.altLoginManager.getStatusMessage(), this.width / 2, 16.0F, k);
        String textValue2 =
                ((net.minecraft.client.gui.GuiTextField) this.credentialFields.get(1)).getText();

        for (java.util.Iterator<?> __recoveryIterator171_50614 = (this.credentialFields).iterator();
                (__recoveryIterator171_50614).hasNext(); ) {
            GuiTextField guiTextField = (GuiTextField) (__recoveryIterator171_50614).next();
            guiTextField.drawTextBox();
            if (guiTextField.getText().length() == 0 && !guiTextField.isFocused()) {
                remix.ui.account.AltManagerScreen.statusFont.drawString(
                        ((java.lang.String) this.credentialFieldLabels.get(guiTextField.getId())),
                        guiTextField.xPosition + 4,
                        guiTextField.yPosition + guiTextField.height / 3.4F,
                        Render2D3DUtils.interpolateColor(16777215, -16777216, 0.5F));
            }

            if (textValue != null) {
                break;
            }
        }

        ((net.minecraft.client.gui.GuiTextField) this.credentialFields.get(1)).setText(textValue2);
        remix.ui.account.AltManagerScreen.statusFont.drawString(
                "\u00a77Current account: "
                        + EnumChatFormatting.RESET
                        + this.mc.getSession().getUsername(),
                this.width / 2 - 205,
                12 - this.fontRendererObj.FONT_HEIGHT,
                k);
        remix.ui.account.AltManagerScreen.statusFont.drawString(
                "\u00a77Accounts: "
                        + EnumChatFormatting.RESET
                        + this.altLoginManager.getAccounts().size(),
                this.width / 2 - 205,
                16.0F,
                k);
        super.drawScreen(i, j, f);
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        super.mouseClicked(i, j, k);
        Iterator iterator = this.credentialFields.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            GuiTextField guiTextField = (GuiTextField) iterator.next();
            guiTextField.mouseClicked(i, j, k);
            if (textValue != null) {
                break;
            }
        }
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        String text = ScrollableGuiSlot.getLegacySlotFlowMarker();
        this.selectedAccountIndex = -1;
        this.scrollableGuiSlot =
                new AltAccountSlotList(this.mc, this.width, this.height, 28, this.height - 48, 36);
        String textValue = text;
        this.scrollableGuiSlot.registerScrollButtons(4, 5);
        this.buttonList.clear();
        this.credentialFields.clear();
        int i = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getThemeColor();
        this.buttonList.add(
                new ColoredGuiButton(0, this.width / 2 - 40, this.height - 46, 80, 20, "Add"));
        this.buttonList.add(
                new ColoredGuiButton(1, this.width / 2 - 40, this.height - 22, 80, 20, "Login"));
        this.buttonList.add(
                new ColoredGuiButton(2, this.width / 2 + 128, this.height - 46, 80, 20, "Delete"));
        this.buttonList.add(
                new ColoredGuiButton(3, this.width / 2 + 44, this.height - 46, 80, 20, "Relog"));
        this.buttonList.add(
                new ColoredGuiButton(4, this.width / 2 + 44, this.height - 22, 80, 20, "Direct"));
        this.buttonList.add(
                new ColoredGuiButton(5, this.width / 2 + 128, this.height - 22, 80, 20, "Back"));
        int j = this.width / 2 + 116;
        this.buttonList.add(
                new IconGuiButton(
                        7,
                        this.width / 2 + 116,
                        4,
                        20,
                        20,
                        40,
                        45,
                        remix.ui.account.AltManagerScreen.ALTENING_ICON));
        j += 30;
        this.buttonList.add(
                new IconGuiButton(
                        8, j, 2, 20, 20, 30, 30, remix.ui.account.AltManagerScreen.IMPORT_ICON));
        j += 30;
        this.buttonList.add(
                new IconGuiButton(
                        9, j, 2, 20, 20, 30, 30, remix.ui.account.AltManagerScreen.PROXY_ICON));
        this.credentialFields.add(
                new PlaceholderGuiTextField(
                        0,
                        this.fontRendererObj,
                        this.width / 2 - 46 - 156,
                        this.height - 48 + 2,
                        156,
                        16));
        this.credentialFields.add(
                new PlaceholderGuiTextField(
                        1,
                        this.fontRendererObj,
                        this.width / 2 - 46 - 156,
                        this.height - 22,
                        156,
                        16));
        ((net.minecraft.client.gui.GuiTextField) this.credentialFields.get(0)).setFocused(true);
        this.credentialFieldLabels.put(0, "Email/Username");
        this.credentialFieldLabels.put(1, "Password");
        ((GuiButton) this.buttonList.get(1)).enabled = false;
        ((GuiButton) this.buttonList.get(2)).enabled = false;
        if (textValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }
    }
}
