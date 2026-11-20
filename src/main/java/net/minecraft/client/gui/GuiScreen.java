package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEventAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;

import remix.client.RemixClient;
import remix.event.PacketDirection;
import remix.event.impl.ChatMessageEvent;

import tv.twitch.chat.ChatUserInfo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback {
    public int width;
    public MinecraftClient mc = MinecraftClient.getMinecraft();
    private long lastMouseEvent;
    public FontRenderer fontRendererObj;
    private static Splitter NEWLINE_SPLITTER;
    private int touchValue;
    private GuiButton selectedButton;
    protected List<GuiButton> buttonList = Lists.newArrayList();
    private int eventButton;
    private static String legacyScreenFlowMarker;
    private static Set PROTOCOLS;

    protected List labelList = Lists.newArrayList();
    private URI clickedLinkURI;
    private static String[] packedTextFragments;

    public int height;
    public boolean allowUserInput;
    private static Logger LOGGER;
    public RenderItem itemRender;

    public boolean doesGuiPauseGame() {
        return true;
    }

    public static boolean isCtrlKeyDown() {
        return net.minecraft.client.MinecraftClient.isRunningOnMac
                ? org.lwjgl.input.Keyboard.isKeyDown(219) || org.lwjgl.input.Keyboard.isKeyDown(220)
                : org.lwjgl.input.Keyboard.isKeyDown(29) || org.lwjgl.input.Keyboard.isKeyDown(157);
    }

    // region String decoding support

    // endregion

    protected void mouseReleased(int i, int j, int k) {
        if (this.selectedButton != null && k == 0) {
            this.selectedButton.mouseReleased(i, j);
            this.selectedButton = null;
        }
    }

    public static boolean isShiftKeyDown() {
        return org.lwjgl.input.Keyboard.isKeyDown(42) || org.lwjgl.input.Keyboard.isKeyDown(54);
    }

    protected void keyTyped(char p171_0, int p171_1) throws IOException {
        if (p171_1 == 1) {
            this.mc.displayGuiScreen((net.minecraft.client.gui.GuiScreen) null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }

    public static String getLegacyScreenFlowMarker() {
        return net.minecraft.client.gui.GuiScreen.legacyScreenFlowMarker;
    }

    protected void handleComponentHover(ChatComponent component, int mouseX, int mouseY) {
        boolean legacyHoverFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (component == null) {
            return;
        }
        HoverEvent hover = component.getChatStyle().getChatHoverEvent();
        if (hover == null) {
            return;
        }

        HoverEventAction action = hover.getAction();
        ChatComponent value = hover.getValue();
        if (action == HoverEventAction.SHOW_ITEM) {
            this.drawHoveredItem(value, mouseX, mouseY, legacyHoverFlow);
        } else if (action == HoverEventAction.SHOW_ENTITY) {
            if (this.mc.gameSettings.advancedItemTooltips) {
                this.drawHoveredEntity(value, mouseX, mouseY, legacyHoverFlow);
            }
        } else if (action == HoverEventAction.SHOW_TEXT) {
            this.drawHoveringText(
                    NEWLINE_SPLITTER.splitToList(value.getFormattedText()), mouseX, mouseY);
        } else if (action == HoverEventAction.SHOW_ACHIEVEMENT) {
            this.drawHoveredStatistic(value, mouseX, mouseY, legacyHoverFlow);
        }
        GlStateManager.disableLighting();
    }

    private void drawHoveredItem(
            ChatComponent value, int mouseX, int mouseY, boolean legacyHoverFlow) {
        ItemStack stack = null;
        try {
            CompoundTag tag = JsonToNBT.getTagFromJson(value.getUnformattedText());
            if (tag != null) {
                stack = ItemStack.loadItemStackFromNBT(tag);
            }
        } catch (NBTException invalidItemTag) {
            // The normal invalid item tooltip is displayed below.
        }

        if (stack != null) {
            this.renderToolTip(stack, mouseX, mouseY);
        }
        if (stack == null || legacyHoverFlow) {
            this.drawCreativeTabHoveringText(
                    EnumChatFormatting.RED + "Invalid Item!", mouseX, mouseY);
        }
    }

    private void drawHoveredEntity(
            ChatComponent value, int mouseX, int mouseY, boolean legacyHoverFlow) {
        try {
            CompoundTag tag = JsonToNBT.getTagFromJson(value.getUnformattedText());
            if (tag != null) {
                List<String> lines = Lists.newArrayList();
                lines.add(tag.getString("name"));
                if (tag.hasKey("type", 8)) {
                    String type = tag.getString("type");
                    lines.add("Type: " + type + " (" + EntityList.getIDFromString(type) + ")");
                }
                lines.add(tag.getString("id"));
                this.drawHoveringText(lines, mouseX, mouseY);
                if (!legacyHoverFlow) {
                    return;
                }
            }
        } catch (NBTException invalidEntityTag) {
            // The normal invalid entity tooltip is displayed below.
        }
        this.drawCreativeTabHoveringText(
                EnumChatFormatting.RED + "Invalid Entity!", mouseX, mouseY);
    }

    private void drawHoveredStatistic(
            ChatComponent value, int mouseX, int mouseY, boolean legacyHoverFlow) {
        StatBase stat = StatList.getOneShotStat(value.getUnformattedText());
        if (stat != null) {
            ChatComponentTranslation type =
                    new ChatComponentTranslation(
                            "stats.tooltip.type."
                                    + (stat.isAchievement() ? "achievement" : "statistic"));
            type.getChatStyle().setItalic(true);
            List<String> lines =
                    Lists.newArrayList(
                            stat.getStatName().getFormattedText(), type.getFormattedText());
            String description =
                    stat instanceof Achievement ? ((Achievement) stat).getDescription() : null;
            if (description != null) {
                lines.addAll(this.fontRendererObj.listFormattedStringToWidth(description, 150));
            }
            this.drawHoveringText(lines, mouseX, mouseY);
        }
        if (stat == null || legacyHoverFlow) {
            this.drawCreativeTabHoveringText(
                    EnumChatFormatting.RED + "Invalid statistic/achievement!", mouseX, mouseY);
        }
    }

    public void drawScreen(int i, int j, float f) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int k = 0;
        String[] astring = astring1;

        while (k < this.buttonList.size()) {
            this.buttonList.get(k).drawButton(this.mc, i, j);
            k++;
            if (astring != null) {
                break;
            }
        }

        k = 0;

        while (k < this.labelList.size()) {
            ((GuiLabel) this.labelList.get(k)).drawLabel(this.mc, i, j);
            k++;
            if (astring != null) {
                break;
            }
        }
    }

    protected void mouseClickMove(int i, int j, int k, long l) {}

    private void openWebLink(URI uri) {
        try {
            Class oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, uri);
        } catch (Throwable throwable) {
            net.minecraft.client.gui.GuiScreen.LOGGER.error("Couldn't open link", throwable);
        }
    }

    protected void drawHoveringText(List list, int p171_1, int p171_2) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (!list.isEmpty()) {
            net.minecraft.client.renderer.GlStateManager.disableRescaleNormal();
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
            net.minecraft.client.renderer.GlStateManager.disableLighting();
            net.minecraft.client.renderer.GlStateManager.disableDepth();
            int k = 0;

            for (java.util.Iterator<?> elementsIterator =
                            ((java.util.Iterator) (((java.util.List) (list)).iterator()));
                    ((boolean) (((java.util.Iterator) (elementsIterator)).hasNext())); ) {
                java.lang.String text =
                        (java.lang.String)
                                ((java.lang.Object)
                                        (((java.util.Iterator) (elementsIterator)).next()));
                int l = this.fontRendererObj.getStringWidth(text);
                if (l > k) {
                    k = l;
                }

                if (astring != null) {
                    break;
                }
            }

            int j2 = p171_1 + 12;
            int k2 = p171_2 - 12;
            int i1 = 8;
            if (list.size() > 1) {
                i1 += 2 + (list.size() - 1) * 10;
            }

            if (j2 + k > this.width) {
                j2 -= 28 + k;
            }

            if (k2 + i1 + 6 > this.height) {
                k2 = this.height - i1 - 6;
            }

            this.zLevel = 300.0F;
            this.itemRender.zLevel = 300.0F;
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & 0xFF000000;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);
            int i2 = 0;

            while (i2 < list.size()) {
                java.lang.String textValue = (java.lang.String) list.get(i2);
                this.fontRendererObj.drawStringWithShadow(textValue, j2, k2, -1);
                if (i2 == 0) {
                    k2 += 2;
                }

                k2 += 10;
                i2++;
                if (astring != null) {
                    break;
                }
            }

            this.zLevel = 0.0F;
            this.itemRender.zLevel = 0.0F;
            net.minecraft.client.renderer.GlStateManager.enableLighting();
            net.minecraft.client.renderer.GlStateManager.enableDepth();
            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
            net.minecraft.client.renderer.GlStateManager.enableRescaleNormal();
        }
    }

    public static boolean isKeyComboCtrlC(int p171_0) {
        return p171_0 == 46 && net.minecraft.client.gui.GuiScreen.isCtrlKeyDown();
    }

    public static boolean isKeyComboCtrlV(int p171_0) {
        return p171_0 == 47 && net.minecraft.client.gui.GuiScreen.isCtrlKeyDown();
    }

    protected boolean handleComponentClick(ChatComponent component) {
        boolean legacyClickFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (component == null) {
            return false;
        }

        ClickEvent click = component.getChatStyle().getChatClickEvent();
        if (isShiftKeyDown()) {
            String insertion = component.getChatStyle().getInsertion();
            if (insertion == null) {
                return false;
            }
            this.setText(insertion, false);
            if (!legacyClickFlow) {
                return false;
            }
        }
        if (click == null) {
            return false;
        }

        ClickEventAction action = click.getAction();
        String value = click.getValue();
        if (action == ClickEventAction.OPEN_URL) {
            if (!this.mc.gameSettings.chatLinks) {
                return false;
            }
            if (this.handleChatLink(click, legacyClickFlow) || !legacyClickFlow) {
                return true;
            }
        } else if (action == ClickEventAction.OPEN_FILE) {
            this.openWebLink(new File(value).toURI());
            if (!legacyClickFlow) {
                return true;
            }
        } else if (action == ClickEventAction.SUGGEST_COMMAND) {
            this.setText(value, true);
            if (!legacyClickFlow) {
                return true;
            }
        } else if (action == ClickEventAction.RUN_COMMAND) {
            this.sendChatMessage(value, false);
            if (!legacyClickFlow) {
                return true;
            }
        } else if (action == ClickEventAction.TWITCH_USER_INFO) {
            ChatUserInfo user = this.mc.getTwitchStream().func_152926_a(value);
            if (user != null) {
                this.mc.displayGuiScreen(new GuiTwitchUserMode(this.mc.getTwitchStream(), user));
            }
            if (user == null || legacyClickFlow) {
                LOGGER.error("Tried to handle twitch user but couldn't find them!");
            }
            if (!legacyClickFlow) {
                return true;
            }
        }

        LOGGER.error("Don't know how to handle " + click);
        return true;
    }

    private boolean handleChatLink(ClickEvent click, boolean legacyClickFlow) {
        String value = click.getValue();
        try {
            URI uri = new URI(value);
            String scheme = uri.getScheme();
            if (scheme == null || !PROTOCOLS.contains(scheme.toLowerCase())) {
                throw new URISyntaxException(value, "Unsupported protocol: " + scheme);
            }
            if (this.mc.gameSettings.chatLinksPrompt) {
                this.clickedLinkURI = uri;
                this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, value, 31102009, false));
                if (!legacyClickFlow) {
                    return true;
                }
            }
            this.openWebLink(uri);
            return true;
        } catch (URISyntaxException invalidLink) {
            LOGGER.error("Can't open url for " + click, invalidLink);
            return false;
        }
    }

    public static String getClipboardString() {
        try {
            java.awt.datatransfer.Transferable transferable =
                    java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

            try {
                if (transferable == null
                        || !transferable.isDataFlavorSupported(
                                java.awt.datatransfer.DataFlavor.stringFlavor)) {
                    return net.minecraft.client.gui.GuiScreen.packedTextFragments[0];
                }
            } catch (java.lang.Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception);
            }

            return (java.lang.String)
                    transferable.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
        } catch (java.lang.Exception exception1) {
            return net.minecraft.client.gui.GuiScreen.packedTextFragments[0];
        }
    }

    public static boolean isKeyComboCtrlA(int p171_0) {
        return p171_0 == 30 && net.minecraft.client.gui.GuiScreen.isCtrlKeyDown();
    }

    public void setWorldAndResolution(MinecraftClient apiValue, int widthValue, int heightValue) {
        this.mc = apiValue;
        this.itemRender = apiValue.getRenderItem();
        this.fontRendererObj = apiValue.fontRendererObj;
        this.width = widthValue;
        this.height = heightValue;
        this.buttonList.clear();
        this.initGui();
    }

    public void initGui() {}

    public void sendChatMessage(String text) {
        this.sendChatMessage(text, true);
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (k == 0) {
            int l = 0;

            while (l < this.buttonList.size()) {
                GuiButton apmValue = this.buttonList.get(l);
                if (apmValue.mousePressed(this.mc, i, j)) {
                    this.selectedButton = apmValue;
                    apmValue.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(apmValue);
                }

                l++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    protected void setText(String text, boolean flag) {}

    public void confirmClicked(boolean p171_0, int p171_1) {
        if (p171_1 == 31102009) {
            if (p171_0) {
                this.openWebLink(this.clickedLinkURI);
            }

            this.clickedLinkURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    public void handleMouseInput() throws IOException {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
        String[] astring = astring1;
        int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int k = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
                return;
            }

            this.eventButton = k;
            this.lastMouseEvent = MinecraftClient.getSystemTime();
            this.mouseClicked(i, j, this.eventButton);
            if (astring == null) {
                return;
            }
        }

        if (k != -1) {
            if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
                return;
            }

            this.eventButton = -1;
            this.mouseReleased(i, j, k);
            if (astring == null) {
                return;
            }
        }

        if (this.eventButton != -1) {

            if (this.lastMouseEvent > 0L) {
                long l = MinecraftClient.getSystemTime() - this.lastMouseEvent;
                this.mouseClickMove(i, j, this.eventButton, l);
            }
        }
    }

    public void updateScreen() {}

    // region Static initialization and string tables
    static {
        net.minecraft.client.gui.GuiScreen.packedTextFragments = new String[4];
        net.minecraft.client.gui.GuiScreen.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiScreen.packedTextFragments[1] = new String(")");
        setLegacyScreenFlowMarker(null);
        net.minecraft.client.gui.GuiScreen.LOGGER = LogManager.getLogger();
        net.minecraft.client.gui.GuiScreen.PROTOCOLS =
                Sets.newHashSet(new String[] {"http", "https"});
        net.minecraft.client.gui.GuiScreen.NEWLINE_SPLITTER = Splitter.on('\n');
    }

    // endregion

    public void onResize(MinecraftClient minecraftClient, int p171_1, int p171_2) {
        this.setWorldAndResolution(minecraftClient, p171_1, p171_2);
    }

    public void handleKeyboardInput() throws IOException {
        char c0 = org.lwjgl.input.Keyboard.getEventCharacter();
        if (org.lwjgl.input.Keyboard.getEventKey() == 0 && c0 >= 32
                || org.lwjgl.input.Keyboard.getEventKeyState()) {
            this.keyTyped(c0, org.lwjgl.input.Keyboard.getEventKey());
        }

        this.mc.dispatchGlobalKeypresses();
    }

    public void sendChatMessage(String originalText, boolean addToHistory) {
        ChatMessageEvent event = new ChatMessageEvent(originalText, PacketDirection.SEND);
        RemixClient.eventBus.post(event);
        if (event.isCancelled()) {
            return;
        }
        if (addToHistory) {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
        }
        this.mc.player.sendChatMessage(event.getMessage());
        // The original text is part of the post-send event contract.
        RemixClient.eventBus.post(new ChatMessageEvent(originalText, PacketDirection.SENT));
    }

    public static void setLegacyScreenFlowMarker(String text) {
        net.minecraft.client.gui.GuiScreen.legacyScreenFlowMarker = text;
    }

    public void drawDefaultBackground() {
        this.drawWorldBackground(0);
    }

    protected void renderToolTip(ItemStack stack, int mouseX, int mouseY) {
        List<String> lines =
                stack.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips);
        boolean legacyTooltipFlow = Gui.getLegacyGuiRenderMarker() != null;
        for (int index = 0; index < lines.size(); index++) {
            if (index == 0) {
                lines.set(index, stack.getRarity().rarityColor + lines.get(index));
            }
            if (index != 0 || legacyTooltipFlow) {
                lines.set(index, EnumChatFormatting.GRAY + lines.get(index));
            }
            if (legacyTooltipFlow) {
                break;
            }
        }
        this.drawHoveringText(lines, mouseX, mouseY);
    }

    public static boolean isAltKeyDown() {
        return org.lwjgl.input.Keyboard.isKeyDown(56) || org.lwjgl.input.Keyboard.isKeyDown(184);
    }

    public void handleInput() throws IOException {
        boolean legacyInputFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.handleMouseInput();
                if (legacyInputFlow) {
                    break;
                }
            }
        }
        if (org.lwjgl.input.Keyboard.isCreated()) {
            while (org.lwjgl.input.Keyboard.next()) {
                this.handleKeyboardInput();
                if (legacyInputFlow) {
                    break;
                }
            }
        }
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {}

    public void drawBackground(int i) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        worldRenderer.startDrawingQuads();
        worldRenderer.setOpaqueVertexColorFromRgb(4210752);
        worldRenderer.addVertexWithUV(0.0, this.height, 0.0, 0.0, this.height / f + i);
        worldRenderer.addVertexWithUV(
                this.width, this.height, 0.0, this.width / f, this.height / f + i);
        worldRenderer.addVertexWithUV(this.width, 0.0, 0.0, this.width / f, i);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, i);
        instance.draw();
    }

    public static boolean isKeyComboCtrlX(int p171_0) {
        return p171_0 == 45 && net.minecraft.client.gui.GuiScreen.isCtrlKeyDown();
    }

    public static void copyTextToClipboard(String text) {
        if (!org.apache.commons.lang3.StringUtils.isEmpty(text)) {
            try {
                java.awt.datatransfer.StringSelection stringselection =
                        new java.awt.datatransfer.StringSelection(text);
                java.awt.Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(stringselection, (java.awt.datatransfer.ClipboardOwner) null);
            } catch (java.lang.Exception exception) {
            }
        }
    }

    public void drawWorldBackground(int p171_0) {
        java.lang.String[] astring = net.minecraft.client.gui.Gui.getLegacyGuiRenderMarker();
        if (this.mc.world != null) {
            this.drawGradientRect(0.0, 0.0, this.width, this.height, -1072689136, -804253680);
            if (astring == null) {
                return;
            }
        }

        this.drawBackground(p171_0);
    }

    protected void drawCreativeTabHoveringText(String text, int p171_1, int p171_2) {
        this.drawHoveringText(java.util.Arrays.asList(text), p171_1, p171_2);
    }

    public void onGuiClosed() {}
}
