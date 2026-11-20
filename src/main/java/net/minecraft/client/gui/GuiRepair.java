package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import io.netty.buffer.Unpooled;

import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

public class GuiRepair extends GuiContainer implements ICrafting {
    private static ResourceLocation anvilResource;

    private GuiTextField nameField;
    private static String[] packedTextFragments;
    private ContainerRepair anvil;
    private InventoryPlayer playerInventory;

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.nameField.mouseClicked(i, j, k);
    }

    public GuiRepair(InventoryPlayer atGValue, World world) {
        super(new ContainerRepair(atGValue, world, MinecraftClient.getMinecraft().player));
        this.playerInventory = atGValue;
        this.anvil = (ContainerRepair) this.inventorySlots;
    }

    public void updateCraftingInventory(Container container, List list) {
        this.sendSlotContents(container, 0, container.getSlot(0).getStack());
    }

    static {
        net.minecraft.client.gui.GuiRepair.packedTextFragments = new String[4];
        net.minecraft.client.gui.GuiRepair.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiRepair.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiRepair.anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
    }

    public void func_175173_a(Container container, IInventory iInventory) {}

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.inventorySlots.removeCraftingFromCrafters(this);
    }

    protected void drawGuiContainerForegroundLayer(int l, int i1) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        String[] astring = astring1;
        this.fontRendererObj.drawString(
                I18n.format("container.repair", new Object[0]), 60, 6, 4210752);
        if (this.anvil.maximumCost > 0) {
            int i;
            byte b0;
            String text;
            label36:
            {
                i = 8453920;
                b0 = 1;
                text = I18n.format("container.repair.cost", new Object[] {this.anvil.maximumCost});
                if (this.anvil.maximumCost >= 40 && !this.mc.player.capabilities.isCreativeMode) {
                    text = I18n.format("container.repair.expensive", new Object[0]);
                    i = 16736352;
                    if (astring == null) {
                        break label36;
                    }
                }

                if (!this.anvil.getSlot(2).getHasStack()) {
                    b0 = 0;
                    if (astring == null) {
                        break label36;
                    }
                }

                if (!this.anvil
                        .getSlot(2)
                        .canTakeStack(this.playerInventory.player)) {
                    i = 16736352;
                }
            }

            if (b0 != 0) {
                int k;
                byte b1;
                {
                    int j = 0xFF000000 | (i & 16579836) >> 2 | i & 0xFF000000;
                    k = this.xSize - 8 - this.fontRendererObj.getStringWidth(text);
                    b1 = 67;
                    if (this.fontRendererObj.getUnicodeFlag()) {
                        drawRect(
                                k - 3, b1 - 2, this.xSize - 7, b1 + 10, -16777216);
                        drawRect(
                                k - 2, b1 - 1, this.xSize - 8, b1 + 9, -12895429);
                        if (!(astring == null)) {

                            this.fontRendererObj.drawString(text, k, b1 + 1, j);
                            this.fontRendererObj.drawString(text, k + 1, b1, j);
                            this.fontRendererObj.drawString(text, k + 1, b1 + 1, j);
                        }
                    } else {

                        this.fontRendererObj.drawString(text, k, b1 + 1, j);
                        this.fontRendererObj.drawString(text, k + 1, b1, j);
                        this.fontRendererObj.drawString(text, k + 1, b1 + 1, j);
                    }
                }

                this.fontRendererObj.drawString(text, k, b1, i);
            }
        }

        GlStateManager.enableLighting();
    }

    private void renameItem() {
        String text = this.nameField.getText();
        Slot slot = this.anvil.getSlot(0);
        if (slot != null
                && slot.getHasStack()
                && !slot.getStack().hasDisplayName()
                && text.equals(slot.getStack().getDisplayName())) {
            text = packedTextFragments[0];
        }

        this.anvil.updateItemName(text);
        this.mc.player.connection.addToSendQueue(
                new C17PacketCustomPayload(
                        "MC|ItemName", new PacketBuffer(Unpooled.buffer()).writeString(text)));
    }

    protected void keyTyped(char c0, int i) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.nameField.textboxKeyTyped(c0, i)) {
            this.renameItem();
            if (astring == null) {
                return;
            }
        }

        super.keyTyped(c0, i);
    }

    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.nameField = new GuiTextField(0, this.fontRendererObj, i + 62, j + 24, 103, 12);
        this.nameField.setTextColor(-1);
        this.nameField.setDisabledTextColour(-1);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setMaxStringLength(40);
        this.inventorySlots.removeCraftingFromCrafters(this);
        this.inventorySlots.onCraftGuiOpened(this);
    }

    public void sendProgressBarUpdate(Container container, int i, int j) {}

    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.nameField.drawTextBox();
    }

    protected void drawGuiContainerBackgroundLayer(float f, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiRepair.anvilResource);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(
                i + 59,
                j + 20,
                0,
                this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16),
                110,
                16);
        if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack())
                && !this.anvil.getSlot(2).getHasStack()) {
            this.drawTexturedModalRect(i + 99, j + 45, this.xSize, 0, 28, 21);
        }
    }

    public void sendSlotContents(Container container, int i, ItemStack itemStack) {
        if (i == 0) {
            this.nameField.setText(
                    itemStack == null ? packedTextFragments[1] : itemStack.getDisplayName());
            this.nameField.setEnabled(itemStack != null);
            if (itemStack != null) {
                this.renameItem();
            }
        }
    }
}
