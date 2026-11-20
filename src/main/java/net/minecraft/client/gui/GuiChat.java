package net.minecraft.client.gui;

import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;

import optifine.MathUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.command.ClientCommand;

import java.io.IOException;
import java.util.List;

public class GuiChat extends GuiScreen {
    private int sentHistoryCursor;
    private static Logger logger;
    protected GuiTextField inputField;
    private static String AUTOCOMPLETE_SEPARATOR;
    private List<String> foundPlayerNames;
    private boolean playerNamesFound;
    private boolean waitingOnAutocomplete;
    private int autocompleteIndex;
    private String historyBuffer;
    public ClientCommand suggestedCommand;
    private static String[] packedTextFragments;
    private String defaultInputFieldText;

    protected void mouseClicked(int p171_0, int p171_1, int p171_2) throws IOException {
        if (p171_2 == 0) {
            net.minecraft.util.ChatComponent chatComponent =
                    this.mc
                            .ingameGUI
                            .getChatGUI()
                            .getChatComponent(
                                    org.lwjgl.input.Mouse.getX(), org.lwjgl.input.Mouse.getY());

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                if (this.handleComponentClick(chatComponent)) {
                    return;
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(ioexception);
            }
        }

        this.inputField.mouseClicked(p171_0, p171_1, p171_2);
        super.mouseClicked(p171_0, p171_1, p171_2);
    }

    protected void keyTyped(char character, int keyCode) throws IOException {
        boolean legacyChatFlow = Gui.getLegacyGuiRenderMarker() != null;
        this.waitingOnAutocomplete = false;
        if (keyCode == 15) {
            this.autocompletePlayerNames();
        }
        if (keyCode != 15 || legacyChatFlow) {
            this.playerNamesFound = false;
        }

        if ((keyCode == 200 || keyCode == 15) && this.suggestedCommand != null) {
            String prefix =
                    ((ClientServices) RemixClient.INSTANCE.getClientServices()).commandPrefix;
            String completion = prefix + this.suggestedCommand.getName().toLowerCase();
            if (!this.inputField.getText().startsWith(completion)) {
                this.inputField.setText(completion);
            }
        }

        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
            if (!legacyChatFlow) {
                return;
            }
        }
        if (keyCode != 28 && keyCode != 156) {
            if (this.handleChatNavigationKey(keyCode) && !legacyChatFlow) {
                return;
            }
            this.inputField.textboxKeyTyped(character, keyCode);
            if (!legacyChatFlow) {
                return;
            }
        }

        String message = this.inputField.getText().trim();
        if (!message.isEmpty()) {
            this.sendChatMessage(message);
        }
        this.mc.displayGuiScreen(null);
    }

    private boolean handleChatNavigationKey(int keyCode) {
        if (keyCode == 200 || keyCode == 208) {
            this.moveSentHistoryCursor(keyCode == 200 ? -1 : 1);
            return true;
        }
        if (keyCode == 201 || keyCode == 209) {
            GuiNewChat chat = this.mc.ingameGUI.getChatGUI();
            int pageStep = chat.getLineCount() - 1;
            chat.scroll(keyCode == 201 ? pageStep : -pageStep);
            return true;
        }
        return false;
    }

    private void sendAutocompleteRequest(String text, String textValue) {
        if (text.length() >= 1) {
            BlockPos blockPos = null;
            if (this.mc.objectMouseOver != null
                    && this.mc.objectMouseOver.typeOfHit == RayHitType.BLOCK) {
                blockPos = this.mc.objectMouseOver.getBlockPos();
            }

            this.mc.player.connection.addToSendQueue(new C14PacketTabComplete(text, blockPos));
            this.waitingOnAutocomplete = true;
        }
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheelDelta = Mouse.getEventDWheel();
        if (wheelDelta == 0) {
            return;
        }
        int lineStep = MathHelper.clamp(wheelDelta, -1, 1);
        if (!isShiftKeyDown()) {
            lineStep *= 7;
        }
        this.mc.ingameGUI.getChatGUI().scroll(lineStep);
    }

    public void initGui() {
        org.lwjgl.input.Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField =
                new net.minecraft.client.gui.GuiTextField(
                        0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }

    public GuiChat() {
        this.historyBuffer = net.minecraft.client.gui.GuiChat.packedTextFragments[0];
        this.sentHistoryCursor = -1;
        this.foundPlayerNames = com.google.common.collect.Lists.newArrayList();
        this.defaultInputFieldText = net.minecraft.client.gui.GuiChat.packedTextFragments[1];
    }

    public void onGuiClosed() {
        org.lwjgl.input.Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    public GuiChat(String text) {
        this.historyBuffer = net.minecraft.client.gui.GuiChat.packedTextFragments[2];
        this.sentHistoryCursor = -1;
        this.foundPlayerNames = com.google.common.collect.Lists.newArrayList();
        this.defaultInputFieldText = net.minecraft.client.gui.GuiChat.packedTextFragments[3];
        this.defaultInputFieldText = text;
    }

    protected void setText(String text, boolean p171_1) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (p171_1) {
            this.inputField.setText(text);
            if (astring == null) {
                return;
            }
        }

        this.inputField.writeText(text);
    }

    static {
        net.minecraft.client.gui.GuiChat.packedTextFragments = new String[7];
        net.minecraft.client.gui.GuiChat.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiChat.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiChat.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiChat.packedTextFragments[3] = new String("");
        net.minecraft.client.gui.GuiChat.packedTextFragments[4] = new String(" ");
        net.minecraft.client.gui.GuiChat.packedTextFragments[5] = new String(" ");
        net.minecraft.client.gui.GuiChat.packedTextFragments[6] = new String("v\\");
        net.minecraft.client.gui.GuiChat.AUTOCOMPLETE_SEPARATOR = ", ";
        net.minecraft.client.gui.GuiChat.logger = LogManager.getLogger();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(2.0, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.updateClientCommandHint(Gui.getLegacyGuiRenderMarker() != null);

        this.inputField.drawTextBox();
        ChatComponent hovered =
                this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        if (hovered != null && hovered.getChatStyle().getChatHoverEvent() != null) {
            this.handleComponentHover(hovered, mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void updateClientCommandHint(boolean legacyChatFlow) {
        String input = this.inputField.getText();
        String prefix = ((ClientServices) RemixClient.INSTANCE.getClientServices()).commandPrefix;
        if (input == null || !input.startsWith(prefix)) {
            this.suggestedCommand = null;
            return;
        }

        String trimmedInput = input.trim();
        if (trimmedInput.length() < prefix.length()) {
            this.suggestedCommand = null;
            return;
        }
        if (input.equalsIgnoreCase(prefix)) {
            this.suggestedCommand = null;
        }
        String commandText = trimmedInput.substring(prefix.length());
        int argumentSeparator = commandText.indexOf(' ');
        boolean hasArguments = argumentSeparator >= 0;
        String commandName =
                hasArguments ? commandText.substring(0, argumentSeparator) : commandText;
        this.findClientCommandHint(trimmedInput, commandName, hasArguments, legacyChatFlow);
        if (this.suggestedCommand != null) {
            String usage = this.suggestedCommand.getUsage();
            if (usage != null) {
                this.drawClientCommandHint(usage.toLowerCase());
            }
        }
        if (legacyChatFlow) {
            this.suggestedCommand = null;
        }
    }

    private void findClientCommandHint(
            String input, String commandName, boolean hasArguments, boolean legacyChatFlow) {
        double bestScore = 0.0;
        for (Object entry : ClientServices.commands.getRegistry().getCommands().asList()) {
            ClientCommand command = (ClientCommand) entry;
            double commandScore = 0.0;
            for (String alias : command.getAliases()) {
                if (hasArguments && commandName.equalsIgnoreCase(alias)) {
                    this.suggestedCommand = command;
                    if (!legacyChatFlow) {
                        return;
                    }
                }

                if ((!hasArguments || commandName.equalsIgnoreCase(alias))
                        && !alias.isEmpty()
                        && commandName.startsWith(alias.substring(0, 1))) {
                    commandScore = Math.max(commandScore, MathUtils.stringSimilarity(input, alias));
                }
                if (legacyChatFlow) {
                    break;
                }
            }
            if (!hasArguments && commandScore > bestScore) {
                bestScore = commandScore;
                this.suggestedCommand = command;
            }
            if (legacyChatFlow) {
                break;
            }
        }
    }

    private void drawClientCommandHint(String usage) {
        int x = this.inputField.xPosition;
        int y = this.inputField.yPosition;
        int hintY = y - this.inputField.height;
        drawRect(
                x - 2,
                y - 4,
                x + this.mc.fontRendererObj.getStringWidth(usage) + 2,
                hintY - 2,
                Integer.MIN_VALUE);
        this.mc.fontRendererObj.drawStringWithShadow(usage, x, hintY - 1, 4174649);
    }

    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }

    // region String decoding support

    // endregion

    public void moveSentHistoryCursor(int offset) {
        List<String> history = this.mc.ingameGUI.getChatGUI().getSentMessages();
        int end = history.size();
        int next = MathHelper.clamp(this.sentHistoryCursor + offset, 0, end);
        if (next == this.sentHistoryCursor) {
            return;
        }
        if (next == end) {
            this.sentHistoryCursor = end;
            this.inputField.setText(this.historyBuffer);
            return;
        }
        if (this.sentHistoryCursor == end) {
            this.historyBuffer = this.inputField.getText();
        }
        this.inputField.setText(history.get(next));
        this.sentHistoryCursor = next;
    }

    public void autocompletePlayerNames() {
        boolean legacyChatFlow = Gui.getLegacyGuiRenderMarker() != null;
        boolean requestNames = !this.playerNamesFound;
        if (this.playerNamesFound) {
            this.removeAutocompleteWord(this.findAutocompleteWordStart());
            if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
                this.autocompleteIndex = 0;
                requestNames = legacyChatFlow;
            }
        }
        if (requestNames) {
            int wordStart = this.findAutocompleteWordStart();
            this.foundPlayerNames.clear();
            this.autocompleteIndex = 0;
            String input = this.inputField.getText();
            this.sendAutocompleteRequest(
                    input.substring(0, this.inputField.getCursorPosition()),
                    input.substring(wordStart).toLowerCase());
            if (this.foundPlayerNames.isEmpty()) {
                return;
            }
            this.playerNamesFound = true;
            this.removeAutocompleteWord(wordStart);
        }

        if (this.foundPlayerNames.size() > 1) {
            String matches =
                    legacyChatFlow
                            ? this.foundPlayerNames.get(0)
                            : String.join(
                                    net.minecraft.client.gui.GuiChat.AUTOCOMPLETE_SEPARATOR,
                                    this.foundPlayerNames);
            this.mc
                    .ingameGUI
                    .getChatGUI()
                    .printChatMessageWithOptionalDeletion(new ChatComponentText(matches), 1);
        }
        this.inputField.writeText(this.foundPlayerNames.get(this.autocompleteIndex++));
    }

    private int findAutocompleteWordStart() {
        return this.inputField.getWordPosition(-1, this.inputField.getCursorPosition(), false);
    }

    private void removeAutocompleteWord(int wordStart) {
        this.inputField.deleteFromCursor(wordStart - this.inputField.getCursorPosition());
    }

    public void onAutocompleteResponse(String[] matches) {
        boolean legacyChatFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (!this.waitingOnAutocomplete) {
            return;
        }

        this.playerNamesFound = false;
        this.foundPlayerNames.clear();
        for (String match : matches) {
            if (!match.isEmpty()) {
                this.foundPlayerNames.add(match);
            }
            if (legacyChatFlow) {
                break;
            }
        }

        int wordStart = this.findAutocompleteWordStart();
        String currentWord = this.inputField.getText().substring(wordStart);
        String commonPrefix = org.apache.commons.lang3.StringUtils.getCommonPrefix(matches);
        if (!commonPrefix.isEmpty() && !currentWord.equalsIgnoreCase(commonPrefix)) {
            this.removeAutocompleteWord(wordStart);
            this.inputField.writeText(commonPrefix);
            if (!legacyChatFlow) {
                return;
            }
        }
        if (!this.foundPlayerNames.isEmpty()) {
            this.playerNamesFound = true;
            this.autocompletePlayerNames();
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
