package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerEnumChatVisibility;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import remix.event.impl.ChatMessageEvent;
import remix.event.PacketDirection;
import remix.client.ClientServices;
import remix.client.RemixClient;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class GuiNewChat extends Gui {

    private int repeatCount;
    private String lastPlainMessage;
    private MinecraftClient mc;
    private int scrollPos;

    private static Logger logger;
    private boolean isScrolled;
    private static String[] packedTextFragments;
    private List<ChatLine> wrappedChatLines;
    private List<ChatLine> chatLines;
    private int deduplicatedLineId;
    private final List<String> sentMessages = Lists.newArrayList();

    public void printChatMessage(ChatComponent chatComponent) {
        this.printChatMessageWithOptionalDeletion(chatComponent, 0);
    }

    private void setChatLine(ChatComponent message, int lineId, int updateCounter, boolean displayOnly) {
        boolean legacyChatFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (lineId != 0) {
            this.deleteChatLine(lineId);
        }

        int wrapWidth = MathHelper.floor(this.getChatWidth() / this.getChatScale());
        List<ChatComponent> lines = GuiUtilRenderComponents.splitText(
                message, wrapWidth, this.mc.fontRendererObj, false, false);
        boolean chatOpen = this.getChatOpen();
        for (ChatComponent line : lines) {
            if (chatOpen && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }
            this.wrappedChatLines.add(0, new ChatLine(updateCounter, line, lineId));
            if (legacyChatFlow) {
                break;
            }
        }
        trimChatHistory(this.wrappedChatLines, legacyChatFlow);
        if (!displayOnly) {
            this.chatLines.add(0, new ChatLine(updateCounter, message, lineId));
            trimChatHistory(this.chatLines, legacyChatFlow);
        }
    }

    private static void trimChatHistory(List<ChatLine> lines, boolean legacyChatFlow) {
        while (lines.size() > 100) {
            lines.remove(lines.size() - 1);
            if (legacyChatFlow) {
                break;
            }
        }
    }

    public ChatComponent getChatComponent(int rawMouseX, int rawMouseY) {
        boolean legacyHitTest = Gui.getLegacyGuiRenderMarker() != null;
        if (!this.getChatOpen()) {
            return null;
        }
        ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int scaleFactor = resolution.getScaleFactor();
        float chatScale = this.getChatScale();
        int chatX = MathHelper.floor((rawMouseX / scaleFactor - 3) / chatScale);
        int chatY = MathHelper.floor((rawMouseY / scaleFactor - 27) / chatScale);
        int visibleLines = Math.min(this.getLineCount(), this.wrappedChatLines.size());
        int fontHeight = this.mc.fontRendererObj.FONT_HEIGHT;
        if (chatX < 0 || chatY < 0 || chatX > MathHelper.floor(this.getChatWidth() / this.getChatScale())
                || chatY >= (fontHeight + 1) * visibleLines) {
            return null;
        }

        int lineIndex = chatY / fontHeight + this.scrollPos;
        if (lineIndex < 0 || lineIndex >= this.wrappedChatLines.size()) {
            return null;
        }
        int endX = 0;
        ChatComponent line = this.wrappedChatLines.get(lineIndex).getChatComponent();
        for (Object part : line) {
            if (part instanceof ChatComponentText) {
                ChatComponentText text = (ChatComponentText) part;
                String styled = GuiUtilRenderComponents.applyChatColorPreference(
                        text.getChatComponentText_TextValue(), false);
                endX += this.mc.fontRendererObj.getStringWidth(styled);
                if (endX > chatX) {
                    return text;
                }
            }
            if (legacyHitTest) {
                break;
            }
        }
        return null;
    }

    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }

    public int getChatWidth() {
        return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
    }

    public static int calculateChatboxWidth(float f) {
        short short1 = 320;
        byte b0 = 40;
        return MathHelper.floor(f * (short1 - b0) + b0);
    }

    public void deleteChatLine(int lineId) {
        boolean legacyChatFlow = Gui.getLegacyGuiRenderMarker() != null;
        removeChatLine(this.wrappedChatLines, lineId, legacyChatFlow);
        removeChatLine(this.chatLines, lineId, true);
    }

    private static void removeChatLine(List<ChatLine> lines, int lineId, boolean firstMatchOnly) {
        for (Iterator<ChatLine> entries = lines.iterator(); entries.hasNext();) {
            if (entries.next().getChatLineID() == lineId) {
                entries.remove();
                if (firstMatchOnly) {
                    return;
                }
            }
        }
    }

    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof GuiChat;
    }

    public int getLineCount() {
        return this.getChatHeight() / 9;
    }

    public void refreshChat() {
        this.wrappedChatLines.clear();
        this.resetScroll();
        boolean legacyChatFlow = Gui.getLegacyGuiRenderMarker() != null;
        for (int index = this.chatLines.size() - 1; index >= 0; index--) {
            ChatLine line = this.chatLines.get(index);
            this.setChatLine(line.getChatComponent(), line.getChatLineID(), line.getUpdatedCounter(), true);
            if (legacyChatFlow) {
                break;
            }
        }
    }

    public void printChatMessageWithOptionalDeletion(ChatComponent message, int lineId) {
        ChatMessageEvent event = new ChatMessageEvent(message.getUnformattedText(), PacketDirection.RECEIVED);
        boolean legacyChatFlow = Gui.getLegacyGuiRenderMarker() != null;
        RemixClient.eventBus.post(event);
        if (lineId == 0) {
            // Remix allocates a replaceable ID and re-enters with the display text after grouping.
            this.printDeduplicatedMessage(message, legacyChatFlow);
            event.cancel();
        }
        if (!event.isCancelled()) {
            this.setChatLine(message, lineId, this.mc.ingameGUI.getUpdateCounter(), false);
            logger.info("[CHAT] " + message.getUnformattedText());
        }
    }

    private void printDeduplicatedMessage(ChatComponent message, boolean legacyChatFlow) {
        GuiNewChat chat = MinecraftClient.getMinecraft().ingameGUI.getChatGUI();
        String plainText = message.getUnformattedText();
        boolean repeated = this.lastPlainMessage.equals(plainText);
        if (repeated) {
            chat.deleteChatLine(this.deduplicatedLineId);
            this.repeatCount++;
            this.lastPlainMessage = plainText;
            message.appendText(EnumChatFormatting.GRAY + " (x" + this.repeatCount + ")");
        }
        if (!repeated || legacyChatFlow) {
            this.repeatCount = 1;
            this.lastPlainMessage = message.getUnformattedText();
        }
        this.deduplicatedLineId++;
        chat.printChatMessageWithOptionalDeletion(message, this.deduplicatedLineId);
        if (this.deduplicatedLineId > 256) {
            this.deduplicatedLineId = 0;
        }
    }

    public void scroll(int i) {
        this.scrollPos += i;
        int j = this.wrappedChatLines.size();
        if (this.scrollPos > j - this.getLineCount()) {
            this.scrollPos = j - this.getLineCount();
        }

        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }

    public List<String> getSentMessages() {
        return this.sentMessages;
    }

    public void clearChatMessages() {
        this.wrappedChatLines.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }

    public GuiNewChat(MinecraftClient aoXValue) {
        this.chatLines = Lists.newArrayList();
        this.wrappedChatLines = Lists.newArrayList();
        this.lastPlainMessage = packedTextFragments[0];
        this.mc = aoXValue;
    }

    public void addToSentMessages(String text) {
        if (this.sentMessages.isEmpty()
                || !((String) this.sentMessages.get(this.sentMessages.size() - 1)).equals(text)) {
            this.sentMessages.add(text);
        }
    }

    public void drawChat(int updateCounter) {
        boolean legacyChatFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (this.mc.gameSettings.chatVisibility == EntityPlayerEnumChatVisibility.HIDDEN) {
            return;
        }

        int visibleLineLimit = this.getLineCount();
        int wrappedLineCount = this.wrappedChatLines.size();
        float opacity = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
        if (wrappedLineCount == 0) {
            return;
        }

        boolean chatOpen = this.getChatOpen();
        float scale = this.getChatScale();
        int chatWidth = MathHelper.ceil(this.getChatWidth() / scale);
        GlStateManager.pushMatrix();
        GlStateManager.translate(2.0F, 20.0F, 0.0F);
        GlStateManager.scale(scale, scale, 1.0F);

        int renderedLines = 0;
        for (int index = 0; index + this.scrollPos < this.wrappedChatLines.size() && index < visibleLineLimit; index++) {
            ChatLine line = this.wrappedChatLines.get(index + this.scrollPos);
            if (line != null) {
                int age = updateCounter - line.getUpdatedCounter();
                if (age < 200 || chatOpen) {
                    double fade = MathHelper.clamp((1.0 - age / 200.0) * 10.0, 0.0, 1.0);
                    int lineAlpha = chatOpen ? 255 : (int) (255.0 * (fade * fade));
                    lineAlpha = (int) (lineAlpha * opacity);
                    renderedLines++;
                    if (lineAlpha > 3) {
                        this.drawChatLine(line.getChatComponent(), -index * 9, chatWidth, lineAlpha);
                    }
                }
            }
            if (legacyChatFlow) {
                break;
            }
        }

        RemixClient.INSTANCE.getClientServices();
        ClientServices.moduleManager.hud.moduleListRenderer.visibleChatLines = renderedLines;
        if (chatOpen) {
            this.drawChatScrollBar(wrappedLineCount, renderedLines);
        }
        GlStateManager.popMatrix();
    }

    private void drawChatLine(ChatComponent line, int y, int width, int alpha) {
        drawRect(0, y - 9, width + 4, y, alpha / 2 << 24);
        GlStateManager.enableBlend();
        this.mc.fontRendererObj.drawStringWithShadow(line.getFormattedText(), 0, y - 8, 16777215 + (alpha << 24));
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

    private void drawChatScrollBar(int wrappedLineCount, int renderedLines) {
        int fontHeight = this.mc.fontRendererObj.FONT_HEIGHT;
        GlStateManager.translate(-3.0F, 0.0F, 0.0F);
        int contentHeight = wrappedLineCount * (fontHeight + 1);
        int viewportHeight = renderedLines * (fontHeight + 1);
        int offset = this.scrollPos * viewportHeight / wrappedLineCount;
        int thumbHeight = viewportHeight * viewportHeight / contentHeight;
        if (contentHeight != viewportHeight) {
            int opacity = offset > 0 ? 170 : 96;
            int thumbColor = this.isScrolled ? 13382451 : 3355562;
            drawRect(0.0, -offset, 2.0, -offset - thumbHeight, thumbColor + (opacity << 24));
            drawRect(2.0, -offset, 1.0, -offset - thumbHeight, 13421772 + (opacity << 24));
        }
    }

    public float getChatScale() {
        return this.mc.gameSettings.chatScale;
    }

    public int getChatHeight() {
        return calculateChatboxHeight(
                this.getChatOpen()
                        ? this.mc.gameSettings.chatHeightFocused
                        : this.mc.gameSettings.chatHeightUnfocused);
    }

    public static int calculateChatboxHeight(float f) {
        short short1 = 180;
        byte b0 = 20;
        return MathHelper.floor(f * (short1 - b0) + b0);
    }

    static {
        net.minecraft.client.gui.GuiNewChat.packedTextFragments = new String[3];
        net.minecraft.client.gui.GuiNewChat.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiNewChat.packedTextFragments[1] = new String(")");
        net.minecraft.client.gui.GuiNewChat.logger = LogManager.getLogger();
    }
}
