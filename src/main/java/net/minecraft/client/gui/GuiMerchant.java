package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GuiMerchant extends GuiContainer {
    private GuiMerchantMerchantButton previousButton;
    private static Logger logger;

    private ChatComponent chatComponent;

    private int selectedMerchantRecipe;
    private static ResourceLocation MERCHANT_GUI_TEXTURE;
    private GuiMerchantMerchantButton nextButton;
    private IMerchant merchant;

    @Override
    public void updateScreen() {
        super.updateScreen();
        MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.player);
        if (recipes != null) {
            this.nextButton.enabled =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (this.selectedMerchantRecipe < recipes.size() - 1 ? 1 : 0));
            this.previousButton.enabled =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (this.selectedMerchantRecipe > 0 ? 1 : 0));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int l, int i1) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc
                .getTextureManager()
                .bindTexture(net.minecraft.client.gui.GuiMerchant.MERCHANT_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.player);
        if (recipes != null && !recipes.isEmpty()) {
            int k = this.selectedMerchantRecipe;
            if (k < 0 || k >= recipes.size()) {
                return;
            }

            MerchantRecipe merchantRecipe = (MerchantRecipe) recipes.get(k);
            if (merchantRecipe.isRecipeDisabled()) {
                this.mc
                        .getTextureManager()
                        .bindTexture(net.minecraft.client.gui.GuiMerchant.MERCHANT_GUI_TEXTURE);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableLighting();
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.buttonList.add(
                this.nextButton = new GuiMerchantMerchantButton(1, i + 120 + 27, j + 24 - 1, true));
        this.buttonList.add(
                this.previousButton =
                        new GuiMerchantMerchantButton(2, i + 36 - 19, j + 24 - 1, false));
        this.nextButton.enabled = false;
        this.previousButton.enabled = false;
    }

    public IMerchant getMerchant() {
        return this.merchant;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        byte b0;
        {
            String[] astring1 = Gui.getLegacyGuiRenderMarker();
            b0 = 0;
            String[] astring = astring1;
            if (guiButton == this.nextButton) {
                this.selectedMerchantRecipe++;
                MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.player);
                if (recipes != null && this.selectedMerchantRecipe >= recipes.size()) {
                    this.selectedMerchantRecipe = recipes.size() - 1;
                }

                b0 = 1;
                if (!(astring == null)) {

                    if (guiButton == this.previousButton) {
                        this.selectedMerchantRecipe--;
                        if (this.selectedMerchantRecipe < 0) {
                            this.selectedMerchantRecipe = 0;
                        }

                        b0 = 1;
                    }
                }
            } else {

                if (guiButton == this.previousButton) {
                    this.selectedMerchantRecipe--;
                    if (this.selectedMerchantRecipe < 0) {
                        this.selectedMerchantRecipe = 0;
                    }

                    b0 = 1;
                }
            }
        }

        if (b0 != 0) {
            ((ContainerMerchant) this.inventorySlots)
                    .setCurrentRecipeIndex(this.selectedMerchantRecipe);
            PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.selectedMerchantRecipe);
            this.mc
                    .getNetworkHandler()
                    .addToSendQueue(new C17PacketCustomPayload("MC|TrSel", packetBuffer));
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        super.drawScreen(i, j, f);
        MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.player);
        String[] astring = astring1;
        if (recipes != null && !recipes.isEmpty()) {
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            int selectedMerchantRecipeValue = this.selectedMerchantRecipe;
            MerchantRecipe merchantRecipe =
                    (MerchantRecipe) recipes.get(selectedMerchantRecipeValue);
            ItemStack itemStack = merchantRecipe.getItemToBuy();
            ItemStack itemStackValue = merchantRecipe.getSecondItemToBuy();
            ItemStack itemStackValue2 = merchantRecipe.getItemToSell();
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableColorMaterial();
            GlStateManager.enableLighting();
            this.itemRender.zLevel = 100.0F;
            this.itemRender.renderItemAndEffectIntoGUI(itemStack, k + 36, l + 24);
            this.itemRender.renderItemOverlays(this.fontRendererObj, itemStack, k + 36, l + 24);
            if (itemStackValue != null) {
                this.itemRender.renderItemAndEffectIntoGUI(itemStackValue, k + 62, l + 24);
                this.itemRender.renderItemOverlays(
                        this.fontRendererObj, itemStackValue, k + 62, l + 24);
            }

            label52:
            {
                this.itemRender.renderItemAndEffectIntoGUI(itemStackValue2, k + 120, l + 24);
                this.itemRender.renderItemOverlays(
                        this.fontRendererObj, itemStackValue2, k + 120, l + 24);
                this.itemRender.zLevel = 0.0F;
                GlStateManager.disableLighting();
                if (this.isPointInRegion(36, 24, 16, 16, i, j) && itemStack != null) {
                    this.renderToolTip(itemStack, i, j);
                    if (astring == null) {
                        break label52;
                    }
                }

                if (itemStackValue != null
                        && this.isPointInRegion(62, 24, 16, 16, i, j)
                        && itemStackValue != null) {
                    this.renderToolTip(itemStackValue, i, j);
                    if (astring == null) {
                        break label52;
                    }
                }

                if (itemStackValue2 != null
                        && this.isPointInRegion(120, 24, 16, 16, i, j)
                        && itemStackValue2 != null) {
                    this.renderToolTip(itemStackValue2, i, j);
                    if (astring == null) {
                        break label52;
                    }
                }

                if (merchantRecipe.isRecipeDisabled()
                        && (this.isPointInRegion(83, 21, 28, 21, i, j)
                                || this.isPointInRegion(83, 51, 28, 21, i, j))) {
                    this.drawCreativeTabHoveringText(
                            I18n.format("merchant.deprecated", new Object[0]), i, j);
                }
            }

            GlStateManager.popMatrix();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
        }
    }

    static {
        net.minecraft.client.gui.GuiMerchant.logger = LogManager.getLogger();
        net.minecraft.client.gui.GuiMerchant.MERCHANT_GUI_TEXTURE =
                new ResourceLocation("textures/gui/container/villager.png");
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String text = this.chatComponent.getUnformattedText();
        this.fontRendererObj.drawString(
                text, this.xSize / 2 - this.fontRendererObj.getStringWidth(text) / 2, 6, 4210752);
        this.fontRendererObj.drawString(
                I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    public GuiMerchant(InventoryPlayer inventoryPlayer, IMerchant atSValue, World world) {
        super(new ContainerMerchant(inventoryPlayer, atSValue, world));
        this.merchant = atSValue;
        this.chatComponent = atSValue.getDisplayName();
    }

    static ResourceLocation access$000() {
        return net.minecraft.client.gui.GuiMerchant.MERCHANT_GUI_TEXTURE;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
