package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;

import io.netty.buffer.Unpooled;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponentSerializer;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.exploit.BookControlModule;

import java.io.IOException;
import java.util.List;

public class GuiScreenBook extends GuiScreen {
    private static ResourceLocation bookGuiTextures;
    private static Logger logger;
    private GuiScreenBookNextPageButton buttonPreviousPage;

    private GuiButton buttonDone;
    private int updateCount;
    private boolean bookGettingSigned;
    private ItemStack bookObj;
    private GuiButton buttonFinalize;
    private GuiButton buttonCancel;
    private GuiScreenBookNextPageButton buttonNextPage;
    private List<ChatComponent> cachedPageLines;
    private boolean bookIsModified;
    private NBTTagList bookPages;
    private int bookTotalPages;
    private int cachedPageIndex;
    private int bookImageHeight;

    private String bookTitle;
    private int bookImageWidth = 192;
    private int currPage;
    private boolean bookIsUnsigned;
    private GuiButton buttonSign;
    private static String[] packedTextFragments;
    private PlayerEntity editingPlayer;

    static {
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments = new String[19];
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[3] = new String("\n");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[4] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[5] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[6] = new String("_");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[7] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[8] = new String("_");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[9] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[10] = new String("_");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[11] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[12] = new String("_");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[13] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[14] = new String("_");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[15] = new String("");
        net.minecraft.client.gui.GuiScreenBook.packedTextFragments[16] = new String("_");
        net.minecraft.client.gui.GuiScreenBook.logger = LogManager.getLogger();
        net.minecraft.client.gui.GuiScreenBook.bookGuiTextures =
                new ResourceLocation("textures/gui/book.png");
    }

    protected void keyTyped(char c0, int i) throws IOException {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        super.keyTyped(c0, i);
        String[] astring = astring1;
        if (this.bookIsUnsigned) {
            if (this.bookGettingSigned) {
                this.keyTypedInTitle(c0, i);
                if (astring == null) {
                    return;
                }
            }

            this.keyTypedInBook(c0, i);
        }
    }

    public ChatComponent getComponentAt(int mouseX, int mouseY) {
        boolean legacyHitTest = Gui.getLegacyGuiRenderMarker() != null;
        if (this.cachedPageLines == null) {
            return null;
        }
        int pageX = mouseX - (this.width - this.bookImageWidth) / 2 - 36;
        int pageY = mouseY - 34;
        int lineCount =
                Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.cachedPageLines.size());
        if (pageX < 0
                || pageX > 116
                || pageY < 0
                || pageY >= (this.mc.fontRendererObj.FONT_HEIGHT + 1) * lineCount) {
            return null;
        }

        int lineIndex = pageY / this.mc.fontRendererObj.FONT_HEIGHT;
        if (lineIndex >= this.cachedPageLines.size()) {
            return null;
        }
        ChatComponent line = this.cachedPageLines.get(lineIndex);
        int endX = 0;
        for (Object part : line) {
            if (part instanceof ChatComponentText) {
                ChatComponentText text = (ChatComponentText) part;
                endX +=
                        this.mc.fontRendererObj.getStringWidth(
                                text.getChatComponentText_TextValue());
                if (endX > pageX) {
                    return text;
                }
            }
            if (legacyHitTest) {
                break;
            }
        }
        return null;
    }

    static ResourceLocation getBookTexture() {
        return net.minecraft.client.gui.GuiScreenBook.bookGuiTextures;
    }

    private void updateButtons() {
        this.buttonNextPage.visible =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.bookGettingSigned
                                        || this.currPage >= this.bookTotalPages - 1
                                                && !this.bookIsUnsigned
                                ? 0
                                : 1));
        this.buttonPreviousPage.visible =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (!this.bookGettingSigned && this.currPage > 0 ? 1 : 0));
        this.buttonDone.visible =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.bookIsUnsigned && this.bookGettingSigned ? 0 : 1));
        if (this.bookIsUnsigned) {
            this.buttonSign.visible =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.bookGettingSigned ? 1 : 0));
            this.buttonCancel.visible = this.bookGettingSigned;
            this.buttonFinalize.visible = this.bookGettingSigned;
            this.buttonFinalize.enabled =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (this.bookTitle.trim().length() > 0 ? 1 : 0));
        }
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        if (k == 0) {
            ChatComponent chatComponent = this.getComponentAt(i, j);
            if (this.handleComponentClick(chatComponent)) {
                return;
            }
        }

        super.mouseClicked(i, j, k);
    }

    protected boolean handleComponentClick(ChatComponent chatComponent) {
        ClickEvent clickEvent =
                chatComponent == null ? null : chatComponent.getChatStyle().getChatClickEvent();
        if (clickEvent == null) {
            return false;
        }

        if (clickEvent.getAction() == ClickEventAction.CHANGE_PAGE) {
            String text = clickEvent.getValue();

            try {
                int currPageValue = Integer.parseInt(text) - 1;
                if (currPageValue >= 0
                        && currPageValue < this.bookTotalPages
                        && currPageValue != this.currPage) {
                    this.currPage = currPageValue;
                    this.updateButtons();
                    return true;
                }
            } catch (Throwable throwable) {
            }

            return false;
        } else {
            boolean flag = super.handleComponentClick(chatComponent);
            if (flag && clickEvent.getAction() == ClickEventAction.RUN_COMMAND) {
                this.mc.displayGuiScreen((GuiScreen) null);
            }

            return flag;
        }
    }

    private void sendBookToServer(boolean sign) throws IOException {
        RemixClient.INSTANCE.getClientServices();
        boolean legacyBookFlow = Gui.getLegacyGuiRenderMarker() != null;
        BookControlModule bookControl = ClientServices.moduleManager.bookControl;
        if (!this.bookIsUnsigned || !this.bookIsModified || this.bookPages == null) {
            return;
        }

        this.trimTrailingPages(legacyBookFlow);
        boolean hasTag = this.bookObj.hasTagCompound();
        if (hasTag) {
            this.bookObj.getTag().setTag("pages", this.bookPages);
        }
        if (!hasTag || legacyBookFlow) {
            this.bookObj.setTagInfo("pages", this.bookPages);
        }

        String channel = sign ? "MC|BSign" : "MC|BEdit";
        if (sign) {
            this.signBookPages(bookControl, legacyBookFlow);
        }

        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        if (bookControl.isEnabled()) {
            this.applyBookControlItemData();
        }
        payload.writeItemStackToBuffer(this.bookObj);
        this.mc.getNetworkHandler().addToSendQueue(new C17PacketCustomPayload(channel, payload));
    }

    private void trimTrailingPages(boolean legacyBookFlow) {
        while (this.bookPages.tagCount() > 1) {
            int lastPage = this.bookPages.tagCount() - 1;
            if (!this.bookPages.getStringTagAt(lastPage).isEmpty() && !legacyBookFlow) {
                return;
            }
            this.bookPages.removeTag(lastPage);
            if (legacyBookFlow) {
                return;
            }
        }
    }

    private void signBookPages(BookControlModule bookControl, boolean legacyBookFlow) {
        this.bookObj.setTagInfo("author", new NBTTagString(this.editingPlayer.getName()));
        this.bookObj.setTagInfo("title", new NBTTagString(this.bookTitle.trim()));
        for (int pageIndex = 0; pageIndex < this.bookPages.tagCount(); pageIndex++) {
            ChatComponent page = new ChatComponentText(this.bookPages.getStringTagAt(pageIndex));
            if (bookControl.isEnabled()) {
                page = this.createBookControlPage();
                this.cachedPageLines =
                        page == null
                                ? null
                                : GuiUtilRenderComponents.splitText(
                                        page, 116, this.fontRendererObj, true, true);
            }
            this.bookPages.set(
                    pageIndex, new NBTTagString(IChatComponentSerializer.componentToJson(page)));
            if (legacyBookFlow) {
                break;
            }
        }
        this.bookObj.setItem(Items.written_book);
    }

    private ChatComponent createBookControlPage() {
        return IChatComponentSerializer.jsonToComponent(
                String.format(
                        "{\"clickEvent\":{\"action\":\"run_command\",\"value\":\"%s\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"%s\"}},\"text\":\"%s\"}",
                        BookControlModule.pageClickCommand,
                        BookControlModule.pageHoverText,
                        BookControlModule.bookPageText));
    }

    private void applyBookControlItemData() {
        this.bookObj.setStackDisplayName("\u00a7AThe Holy Bible \u00a7bv2");
        this.bookObj.addEnchantment(Enchantment.sharpness, 1000);
        this.bookObj.addEnchantment(Enchantment.looting, 1200);
        this.bookObj.addEnchantment(Enchantment.smite, 1000);
        this.bookObj.addEnchantment(Enchantment.silkTouch, 1);
        this.bookObj.addEnchantment(Enchantment.fortune, 100);
        this.bookObj.addAttributeModifier(
                "generic.maxHealth",
                new AttributeModifier("generic.maxHealth", Double.POSITIVE_INFINITY, 0));
        this.bookObj.addAttributeModifier(
                "generic.movementSpeed", new AttributeModifier("generic.movementSpeed", 0.1, 0));
    }

    public void initGui() {
        boolean legacyBookFlow = Gui.getLegacyGuiRenderMarker() != null;
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        int left = this.width / 2 - 100;
        int right = this.width / 2 + 2;
        int y = 4 + this.bookImageHeight;
        if (this.bookIsUnsigned) {
            this.buttonList.add(
                    this.buttonSign =
                            new GuiButton(3, left, y, 98, 20, I18n.format("book.signButton")));
            this.buttonList.add(
                    this.buttonDone = new GuiButton(0, right, y, 98, 20, I18n.format("gui.done")));
            this.buttonList.add(
                    this.buttonFinalize =
                            new GuiButton(5, left, y, 98, 20, I18n.format("book.finalizeButton")));
            this.buttonList.add(
                    this.buttonCancel =
                            new GuiButton(4, right, y, 98, 20, I18n.format("gui.cancel")));
        }
        if (!this.bookIsUnsigned || legacyBookFlow) {
            this.buttonList.add(
                    this.buttonDone = new GuiButton(0, left, y, 200, 20, I18n.format("gui.done")));
        }
        int bookX = (this.width - this.bookImageWidth) / 2;
        this.buttonList.add(
                this.buttonNextPage = new GuiScreenBookNextPageButton(1, bookX + 120, 156, true));
        this.buttonList.add(
                this.buttonPreviousPage =
                        new GuiScreenBookNextPageButton(2, bookX + 38, 156, false));
        this.updateButtons();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        boolean legacyBookFlow = Gui.getLegacyGuiRenderMarker() != null;
        this.mc.getTextureManager().bindTexture(bookGuiTextures);
        int bookX = (this.width - this.bookImageWidth) / 2;
        this.drawTexturedModalRect(bookX, 2, 0, 0, this.bookImageWidth, this.bookImageHeight);
        if (this.bookGettingSigned) {
            this.drawSigningPage(bookX, legacyBookFlow);
        }
        if (!this.bookGettingSigned || legacyBookFlow) {
            this.drawCurrentPage(bookX, mouseX, mouseY, legacyBookFlow);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSigningPage(int bookX, boolean legacyBookFlow) {
        String title =
                this.bookIsUnsigned
                        ? this.withBlinkingCursor(this.bookTitle, legacyBookFlow)
                        : this.bookTitle;
        int pageX = bookX + 36;
        this.drawCenteredBookText(I18n.format("book.editTitle"), pageX, 34, "");
        this.drawCenteredBookText(title, pageX, 50, "");
        this.drawCenteredBookText(
                I18n.format("book.byAuthor", this.editingPlayer.getName()),
                pageX,
                60,
                EnumChatFormatting.DARK_GRAY.toString());
        this.fontRendererObj.drawSplitString(
                I18n.format("book.finalizeWarning"), pageX, 82, 116, 0);
    }

    private void drawCenteredBookText(String text, int pageX, int y, String formatting) {
        int width = this.fontRendererObj.getStringWidth(text);
        this.fontRendererObj.drawString(formatting + text, pageX + (116 - width) / 2, y, 0);
    }

    private String withBlinkingCursor(String text, boolean legacyBookFlow) {
        if (this.updateCount / 6 % 2 == 0) {
            text += EnumChatFormatting.BLACK.toString() + "_";
            if (!legacyBookFlow) {
                return text;
            }
        }
        return text + EnumChatFormatting.GRAY + "_";
    }

    private void drawCurrentPage(int bookX, int mouseX, int mouseY, boolean legacyBookFlow) {
        String pageNumber =
                I18n.format("book.pageIndicator", this.currPage + 1, this.bookTotalPages);
        String text = this.pageGetCurrent();
        if (this.bookIsUnsigned) {
            boolean rightToLeft = this.fontRendererObj.getBidiFlag();
            if (rightToLeft) {
                text += "_";
            }
            if (!rightToLeft || legacyBookFlow) {
                text = this.withBlinkingCursor(text, legacyBookFlow);
            }
        }
        if (!this.bookIsUnsigned || legacyBookFlow) {
            this.cacheSignedPageLines(text, legacyBookFlow);
        }

        int numberWidth = this.fontRendererObj.getStringWidth(pageNumber);
        this.fontRendererObj.drawString(
                pageNumber, bookX + this.bookImageWidth - numberWidth - 44, 18, 0);
        if (this.cachedPageLines == null) {
            this.fontRendererObj.drawSplitString(text, bookX + 36, 34, 116, 0);
            return;
        }

        int visibleLines =
                Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.cachedPageLines.size());
        for (int lineIndex = 0; lineIndex < visibleLines; lineIndex++) {
            ChatComponent line = this.cachedPageLines.get(lineIndex);
            this.fontRendererObj.drawString(
                    line.getUnformattedText(),
                    bookX + 36,
                    34 + lineIndex * this.fontRendererObj.FONT_HEIGHT,
                    0);
            if (legacyBookFlow) {
                break;
            }
        }

        ChatComponent hovered = this.getComponentAt(mouseX, mouseY);
        if (hovered != null) {
            this.handleComponentHover(hovered, mouseX, mouseY);
        }
    }

    private void cacheSignedPageLines(String text, boolean legacyBookFlow) {
        if (this.cachedPageIndex == this.currPage) {
            return;
        }
        this.cachedPageLines = this.parseSignedPageLines(text, legacyBookFlow);
        this.cachedPageIndex = this.currPage;
    }

    private List<ChatComponent> parseSignedPageLines(String text, boolean legacyBookFlow) {
        if (ItemEditableBook.validBookTagContents(this.bookObj.getTag())) {
            try {
                ChatComponent page = IChatComponentSerializer.jsonToComponent(text);
                return page == null
                        ? null
                        : GuiUtilRenderComponents.splitText(
                                page, 116, this.fontRendererObj, true, true);
            } catch (JsonParseException invalidPageJson) {
                if (!legacyBookFlow) {
                    return null;
                }
            }
        }
        return Lists.newArrayList(
                new ChatComponentText(EnumChatFormatting.DARK_RED + "* Invalid book tag *"));
    }

    public void updateScreen() {
        super.updateScreen();
        this.updateCount++;
    }

    private void keyTypedInBook(char c0, int i) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (GuiScreen.isKeyComboCtrlV(i)) {
            this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
            if (astring == null) {
                return;
            }
        }

        switch (i) {
            case 14:
                String text = this.pageGetCurrent();
                if (text.length() > 0) {
                    this.pageSetCurrent(text.substring(0, text.length() - 1));
                }

                return;
            case 28:
            case 156:
                this.pageInsertIntoCurrent(packedTextFragments[3]);
                return;
            default:
                if (ChatAllowedCharacters.isAllowedCharacter(c0)) {
                    this.pageInsertIntoCurrent(Character.toString(c0));
                }
        }
    }

    private String pageGetCurrent() {
        return this.bookPages != null
                        && this.currPage >= 0
                        && this.currPage < this.bookPages.tagCount()
                ? this.bookPages.getStringTagAt(this.currPage)
                : packedTextFragments[4];
    }

    private void pageInsertIntoCurrent(String text) {
        String textValue = this.pageGetCurrent();
        String textValue2 = textValue + text;
        int i =
                this.fontRendererObj.splitStringWidth(
                        textValue2
                                + packedTextFragments[5]
                                + EnumChatFormatting.BLACK
                                + packedTextFragments[6],
                        118);
        if (i <= 128 && textValue2.length() < 256) {
            this.pageSetCurrent(textValue2);
        }
    }

    private void addBlankPage() {
        if (this.bookPages != null && this.bookPages.tagCount() < 50) {
            this.bookPages.appendTag(new NBTTagString(packedTextFragments[2]));
            this.bookTotalPages++;
            this.bookIsModified = true;
        }
    }

    public GuiScreenBook(PlayerEntity aouValue, ItemStack itemStack, boolean aorValue) {
        this.bookImageHeight = 192;
        this.bookTotalPages = 1;
        this.bookTitle = packedTextFragments[0];
        this.cachedPageIndex = -1;
        this.editingPlayer = aouValue;
        this.bookObj = itemStack;
        this.bookIsUnsigned = aorValue;
        if (itemStack.hasTagCompound()) {
            CompoundTag tag = itemStack.getTag();
            this.bookPages = tag.getTagList("pages", 8);
            if (this.bookPages != null) {
                this.bookPages = (NBTTagList) this.bookPages.copy();
                this.bookTotalPages = this.bookPages.tagCount();
                if (this.bookTotalPages < 1) {
                    this.bookTotalPages = 1;
                }
            }
        }

        if (this.bookPages == null && aorValue) {
            this.bookPages = new NBTTagList();
            this.bookPages.appendTag(new NBTTagString(packedTextFragments[1]));
            this.bookTotalPages = 1;
        }
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        boolean legacyBookFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (!button.enabled) {
            return;
        }
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(null);
                this.sendBookToServer(false);
                break;
            case 1:
                this.advanceBookPage(legacyBookFlow);
                break;
            case 2:
                if (this.currPage > 0) {
                    this.currPage--;
                }
                break;
            case 3:
                if (this.bookIsUnsigned) {
                    this.bookGettingSigned = true;
                }
                break;
            case 4:
                if (this.bookGettingSigned) {
                    this.bookGettingSigned = false;
                }
                break;
            case 5:
                if (this.bookGettingSigned) {
                    this.sendBookToServer(true);
                    this.mc.displayGuiScreen(null);
                }
                break;
            default:
        }
        this.updateButtons();
    }

    private void advanceBookPage(boolean legacyBookFlow) {
        if (this.currPage < this.bookTotalPages - 1) {
            this.currPage++;
            if (!legacyBookFlow) {
                return;
            }
        }
        if (this.bookIsUnsigned) {
            this.addBlankPage();
            if (this.currPage < this.bookTotalPages - 1) {
                this.currPage++;
            }
        }
    }

    private void pageSetCurrent(String text) {
        if (this.bookPages != null
                && this.currPage >= 0
                && this.currPage < this.bookPages.tagCount()) {
            this.bookPages.set(this.currPage, new NBTTagString(text));
            this.bookIsModified = true;
        }
    }

    private void keyTypedInTitle(char c0, int i) throws IOException {
        switch (i) {
            case 14:
                if (!this.bookTitle.isEmpty()) {
                    this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
                    this.updateButtons();
                }

                return;
            case 28:
            case 156:
                if (!this.bookTitle.isEmpty()) {
                    this.sendBookToServer(true);
                    this.mc.displayGuiScreen((GuiScreen) null);
                }

                return;
            default:
                if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(c0)) {
                    this.bookTitle = this.bookTitle + Character.toString(c0);
                    this.updateButtons();
                    this.bookIsModified = true;
                }
        }
    }
}
