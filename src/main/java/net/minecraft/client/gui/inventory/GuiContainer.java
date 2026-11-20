package net.minecraft.client.gui.inventory;

import com.google.common.collect.Sets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public abstract class GuiContainer extends GuiScreen {
    private ItemStack shiftClickedSlot;
    private ItemStack draggedStack;
    private boolean doubleClick;
    private int BY;
    protected int xSize = 176;
    private Slot returningStackDestSlot;
    protected boolean dragSplitting;
    private Slot atp;
    protected int ySize = 166;
    private ItemStack returningStack;
    private Slot clickedSlot;
    private int dragSplittingLimit;
    private long dragItemDropDelay;
    protected int guiTop;
    public Container inventorySlots;
    private int T;
    private long returningStackTime;
    private long lastClickTime;
    protected int guiLeft;
    private Slot atw;
    private boolean ignoreMouseUp;
    private boolean isRightMouseClick;
    private int H;
    private static String[] packedTextFragments;
    public static ResourceLocation inventoryBackground;
    private int touchUpY;
    protected final Set dragSplittingSlots = Sets.newHashSet();
    private Slot theSlot;
    protected int B;
    private int lD;

    public void onGuiClosed() {
        if (this.mc.player != null) {
            this.inventorySlots.onContainerClosed(this.mc.player);
        }
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {}

    private static IOException method_02836a3a008661c0ef77(IOException ioexception) {
        return ioexception;
    }

    static {
        net.minecraft.client.gui.inventory.GuiContainer.packedTextFragments = new String[5];
        net.minecraft.client.gui.inventory.GuiContainer.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.inventory.GuiContainer.packedTextFragments[1] = new String("0");
        net.minecraft.client.gui.inventory.GuiContainer.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.inventory.GuiContainer.packedTextFragments[3] = new String("");
        net.minecraft.client.gui.inventory.GuiContainer.packedTextFragments[4] =
                new String("\u001ddH&7 8\u001a.W'+}>\u0006oD3+<8\u001b.Y<473\u001dnB+l\"3\u000e");
        net.minecraft.client.gui.inventory.GuiContainer.inventoryBackground =
                new ResourceLocation("textures/gui/container/inventory.png");
    }

    private void drawItemStack(ItemStack itemStack, int i, int j, String text) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        this.itemRender.renderItemAndEffectIntoGUI(itemStack, i, j);
        this.itemRender.renderItemOverlayIntoGUI(
                this.fontRendererObj, itemStack, i, j - (this.draggedStack == null ? 0 : 8), text);
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
    }

    protected boolean checkHotbarKeys(int i) {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (this.mc.player.inventory.getItemStack() == null && this.theSlot != null) {
            int j = 0;

            while (j < 9) {
                if (i == this.mc.gameSettings.keyBindsHotbar[j].getKeyCode()) {
                    this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, j, 2);
                    return true;
                }

                j++;
                if (aint != null) {
                    break;
                }
            }
        }

        return false;
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        int[] aint;
        int l;
        Slot atpValue;
        long atvValue;
        byte b1;
        label169:
        {
            int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
            this.checkHotbarKeys(k - 100);
            aint = aint1;
            super.mouseClicked(i, j, k);
            l = k == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100 ? 1 : 0;
            atpValue = this.getSlotAtPosition(i, j);
            atvValue = MinecraftClient.getSystemTime();
            if (this.atp == atpValue) {
                long k2 = atvValue - this.lastClickTime;

                if (k2 < 250L && this.BY == k) {
                    b1 = 1;
                    break label169;
                }
            }

            b1 = 0;
        }

        this.doubleClick = remix.compat.compiler.JvmBooleanSlots.fromInt(b1);
        this.ignoreMouseUp = false;
        if (k == 0 || k == 1 || l != 0) {
            int j1 = this.guiLeft;
            int k1 = this.guiTop;
            int l1 = i >= j1 && j >= k1 && i < j1 + this.xSize && j < k1 + this.ySize ? 0 : 1;
            int i2 = -1;
            if (atpValue != null) {
                i2 = atpValue.slotNumber;
            }

            if (l1 != 0) {
                i2 = -999;
            }

            if (this.mc.gameSettings.touchscreen
                    && l1 != 0
                    && this.mc.player.inventory.getItemStack() == null) {
                this.mc.displayGuiScreen((GuiScreen) null);
                return;
            }

            label147:
            if (i2 != -1) {
                if (this.mc.gameSettings.touchscreen) {
                    if (atpValue != null && atpValue.getHasStack()) {
                        this.clickedSlot = atpValue;
                        this.draggedStack = null;
                        this.isRightMouseClick =
                                remix.compat.compiler.JvmBooleanSlots.fromInt((k == 1 ? 1 : 0));
                        if (aint == null) {
                            break label147;
                        }
                    }

                    this.clickedSlot = null;
                    if (aint == null) {
                        break label147;
                    }
                }

                label137:
                if (!this.dragSplitting) {
                    if (this.mc.player.inventory.getItemStack() == null) {
                        label186:
                        {
                            if (k == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                                this.handleMouseClick(atpValue, i2, k, 3);
                                if (aint == null) {
                                    break label186;
                                }
                            }

                            byte b0;
                            {
                                int j2 =
                                        i2 == -999
                                                        || !Keyboard.isKeyDown(42)
                                                                && !Keyboard.isKeyDown(54)
                                                ? 0
                                                : 1;
                                b0 = 0;
                                if (j2 != 0) {
                                    this.shiftClickedSlot =
                                            atpValue != null && atpValue.getHasStack()
                                                    ? atpValue.getStack()
                                                    : null;
                                    b0 = 1;
                                    if (!(aint == null)) {

                                        if (i2 == -999) {
                                            b0 = 4;
                                        }
                                    }
                                } else {

                                    if (i2 == -999) {
                                        b0 = 4;
                                    }
                                }
                            }

                            this.handleMouseClick(atpValue, i2, k, b0);
                        }

                        this.ignoreMouseUp = true;
                        if (aint == null) {
                            break label137;
                        }
                    }

                    this.dragSplitting = true;
                    this.lD = k;
                    this.dragSplittingSlots.clear();
                    if (k == 0) {
                        this.dragSplittingLimit = 0;
                        if (aint == null) {
                            break label137;
                        }
                    }

                    if (k == 1) {
                        this.dragSplittingLimit = 1;
                        if (aint == null) {
                            break label137;
                        }
                    }

                    if (k == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                        this.dragSplittingLimit = 2;
                    }
                }
            }
        }

        this.atp = atpValue;
        this.lastClickTime = atvValue;
        this.BY = k;
    }

    protected void mouseClickMove(int i, int j, int k, long l1) {
        Slot atwValue = this.getSlotAtPosition(i, j);
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        ItemStack itemStack = this.mc.player.inventory.getItemStack();
        int[] aint = aint1;
        if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (k != 0 && k != 1) {
                return;
            }

            if (this.draggedStack == null) {
                if (atwValue == this.clickedSlot) {
                    return;
                }

                this.draggedStack = this.clickedSlot.getStack().copy();
                if (aint == null) {
                    return;
                }
            }

            if (this.draggedStack.stackSize <= 1
                    || atwValue == null
                    || !Container.canAddItemToSlot(atwValue, this.draggedStack, false)) {
                return;
            }

            label52:
            {
                long l = MinecraftClient.getSystemTime();
                if (this.atw == atwValue) {
                    long i1 = l - this.dragItemDropDelay;

                    if (i1 <= 500L) {
                        break label52;
                    }

                    this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                    this.handleMouseClick(atwValue, atwValue.slotNumber, 1, 0);
                    this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);

                    this.dragItemDropDelay = l + 750L;
                    this.draggedStack.stackSize--;
                    if (aint == null) {
                        break label52;
                    }
                }

                this.atw = atwValue;
                this.dragItemDropDelay = l;
            }

            if (aint == null) {
                return;
            }
        }

        if (this.dragSplitting
                && atwValue != null
                && itemStack != null
                && itemStack.stackSize > this.dragSplittingSlots.size()
                && Container.canAddItemToSlot(atwValue, itemStack, true)
                && atwValue.isItemValid(itemStack)
                && this.inventorySlots.canDragIntoSlot(atwValue)) {
            this.dragSplittingSlots.add(atwValue);
            this.updateDragSplitting();
        }
    }

    public void updateScreen() {
        super.updateScreen();
        if (!this.mc.player.isEntityAlive() || this.mc.player.isDead) {
            this.mc.player.closeScreen();
        }
    }

    private void updateDragSplitting() {
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        ItemStack itemStack = this.mc.player.inventory.getItemStack();
        int[] aint = aint1;
        if (itemStack != null && this.dragSplitting) {
            this.T = itemStack.stackSize;

            for (java.util.Iterator<?> __recoveryIterator171_13609 =
                            (this.dragSplittingSlots).iterator();
                    (__recoveryIterator171_13609).hasNext(); ) {
                Slot slot = (Slot) (__recoveryIterator171_13609).next();
                ItemStack itemStackValue = itemStack.copy();
                int i = slot.getStack() == null ? 0 : slot.getStack().stackSize;
                Container.computeStackSize(
                        this.dragSplittingSlots, this.dragSplittingLimit, itemStackValue, i);
                if (itemStackValue.stackSize > itemStackValue.getMaxStackSize()) {
                    itemStackValue.stackSize = itemStackValue.getMaxStackSize();
                }

                if (itemStackValue.stackSize > slot.getItemStackLimit(itemStackValue)) {
                    itemStackValue.stackSize = slot.getItemStackLimit(itemStackValue);
                }

                this.T = this.T - (itemStackValue.stackSize - i);
                if (aint != null) {
                    break;
                }
            }
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    protected abstract void drawGuiContainerBackgroundLayer(float f, int i, int j);

    protected void mouseReleased(int i, int j, int k) {
        Slot slot = this.getSlotAtPosition(i, j);
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        int l = this.guiLeft;
        int[] aint = aint1;
        int guiTopValue = this.guiTop;
        int j1 =
                i >= l && j >= guiTopValue && i < l + this.xSize && j < guiTopValue + this.ySize
                        ? 0
                        : 1;
        int k1 = -1;
        if (slot != null) {
            k1 = slot.slotNumber;
        }

        if (j1 != 0) {
            k1 = -999;
        }

        label202:
        {
            if (this.doubleClick
                    && slot != null
                    && k == 0
                    && this.inventorySlots.canMergeSlot((ItemStack) null, slot)) {
                label183:
                {
                    if (isShiftKeyDown()) {
                        if (slot == null
                                || slot.inventory == null
                                || this.shiftClickedSlot == null) {
                            break label183;
                        }

                        Iterator iterator = this.inventorySlots.inventorySlots.iterator();

                        while (true) {
                            if (!iterator.hasNext()) {
                                break label183;
                            }

                            Slot slotValue = (Slot) iterator.next();
                            if (slotValue != null
                                    && slotValue.canTakeStack(this.mc.player)
                                    && slotValue.getHasStack()
                                    && slotValue.inventory == slot.inventory
                                    && Container.canAddItemToSlot(
                                            slotValue, this.shiftClickedSlot, true)) {
                                this.handleMouseClick(slotValue, slotValue.slotNumber, k, 1);
                                if (aint != null) {
                                    break;
                                }
                            }
                        }
                    }

                    this.handleMouseClick(slot, k1, k, 6);
                }

                this.doubleClick = false;

                this.lastClickTime = 0L;
                if (aint == null) {
                    break label202;
                }
            }

            if (this.dragSplitting && this.lD != k) {
                this.dragSplitting = false;
                this.dragSplittingSlots.clear();
                this.ignoreMouseUp = true;
                return;
            }

            if (this.ignoreMouseUp) {
                this.ignoreMouseUp = false;
                return;
            }

            if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
                if (k != 0 && k != 1) {
                    break label202;
                }

                if (this.draggedStack == null && slot != this.clickedSlot) {
                    this.draggedStack = this.clickedSlot.getStack();
                }

                label136:
                {
                    boolean flag = Container.canAddItemToSlot(slot, this.draggedStack, false);
                    if (k1 != -1 && this.draggedStack != null && flag) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, k, 0);
                        this.handleMouseClick(slot, k1, 0, 0);
                        if (this.mc.player.inventory.getItemStack() != null) {
                            this.handleMouseClick(
                                    this.clickedSlot, this.clickedSlot.slotNumber, k, 0);
                            this.H = i - l;
                            this.touchUpY = j - guiTopValue;
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = this.draggedStack;
                            this.returningStackTime = MinecraftClient.getSystemTime();
                            if (aint == null) {
                                break label136;
                            }
                        }

                        this.returningStack = null;
                        if (aint == null) {
                            break label136;
                        }
                    }

                    if (this.draggedStack != null) {
                        this.H = i - l;
                        this.touchUpY = j - guiTopValue;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = MinecraftClient.getSystemTime();
                    }
                }

                this.draggedStack = null;
                this.clickedSlot = null;
                if (aint == null) {
                    break label202;
                }
            }

            if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
                this.handleMouseClick(
                        (Slot) null,
                        -999,
                        Container.method_e0b8a2f32478f200146b(0, this.dragSplittingLimit),
                        5);

                for (java.util.Iterator<?> __recoveryIterator171_19893 =
                                (this.dragSplittingSlots).iterator();
                        (__recoveryIterator171_19893).hasNext(); ) {
                    Slot slotValue2 = (Slot) (__recoveryIterator171_19893).next();
                    this.handleMouseClick(
                            slotValue2,
                            slotValue2.slotNumber,
                            Container.method_e0b8a2f32478f200146b(1, this.dragSplittingLimit),
                            5);
                    if (aint != null) {
                        break;
                    }
                }

                this.handleMouseClick(
                        (Slot) null,
                        -999,
                        Container.method_e0b8a2f32478f200146b(2, this.dragSplittingLimit),
                        5);
                if (aint == null) {
                    break label202;
                }
            }

            if (this.mc.player.inventory.getItemStack() != null) {
                if (k == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                    this.handleMouseClick(slot, k1, k, 3);
                    if (!(aint == null)) {

                        int l1 =
                                k1 == -999 || !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)
                                        ? 0
                                        : 1;
                        if (l1 != 0) {
                            this.shiftClickedSlot =
                                    slot != null && slot.getHasStack() ? slot.getStack() : null;
                        }

                        this.handleMouseClick(slot, k1, k, l1 != 0 ? 1 : 0);
                    }
                } else {

                    int l1 =
                            k1 == -999 || !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)
                                    ? 0
                                    : 1;
                    if (l1 != 0) {
                        this.shiftClickedSlot =
                                slot != null && slot.getHasStack() ? slot.getStack() : null;
                    }

                    this.handleMouseClick(slot, k1, k, l1 != 0 ? 1 : 0);
                }
            }
        }

        if (this.mc.player.inventory.getItemStack() == null) {

            this.lastClickTime = 0L;
        }

        this.dragSplitting = false;
    }

    public GuiContainer(Container attValue) {
        this.inventorySlots = attValue;
        this.ignoreMouseUp = true;
    }

    private Slot getSlotAtPosition(int i, int j) {
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        int k = 0;
        int[] aint = aint1;

        while (k < this.inventorySlots.inventorySlots.size()) {
            Slot slot = (Slot) this.inventorySlots.inventorySlots.get(k);
            if (this.isMouseOverSlot(slot, i, j)) {
                return slot;
            }

            k++;
            if (aint != null) {
                break;
            }
        }

        return null;
    }

    public void drawScreen(int i, int j, float f) {
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        this.drawDefaultBackground();
        int[] aint = aint1;
        int k = this.guiLeft;
        int l = this.guiTop;
        this.drawGuiContainerBackgroundLayer(f, i, j);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(i, j, f);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(k, l, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        this.theSlot = null;
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(
                OpenGlHelper.lightmapTexUnit, short1 / 1.0F, short2 / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int j1 = 0;

        while (j1 < this.inventorySlots.inventorySlots.size()) {
            Slot atzValue = (Slot) this.inventorySlots.inventorySlots.get(j1);
            this.drawSlot(atzValue);
            if (this.isMouseOverSlot(atzValue, i, j) && atzValue.canBeHovered()) {
                this.theSlot = atzValue;
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                int k1 = atzValue.xDisplayPosition;
                int yDisplayPosition = atzValue.yDisplayPosition;
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(
                        k1,
                        yDisplayPosition,
                        k1 + 16,
                        yDisplayPosition + 16,
                        -2130706433,
                        -2130706433);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }

            j1++;
            if (aint != null) {
                break;
            }
        }

        RenderHelper.disableStandardItemLighting();
        this.drawGuiContainerForegroundLayer(i, j);
        RenderHelper.enableGUIStandardItemLighting();
        InventoryPlayer bdI = this.mc.player.inventory;
        ItemStack itemStack = this.draggedStack == null ? bdI.getItemStack() : this.draggedStack;
        if (itemStack != null) {
            String text;
            int j2;
            byte b0;
            label53:
            {
                b0 = 8;
                j2 = this.draggedStack == null ? 8 : 16;
                text = null;
                if (this.draggedStack != null && this.isRightMouseClick) {
                    itemStack = itemStack.copy();
                    itemStack.stackSize = MathHelper.ceil(itemStack.stackSize / 2.0F);
                    if (aint == null) {
                        break label53;
                    }
                }

                if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
                    itemStack = itemStack.copy();
                    itemStack.stackSize = this.T;
                    if (itemStack.stackSize == 0) {
                        text =
                                packedTextFragments[0]
                                        + EnumChatFormatting.YELLOW
                                        + packedTextFragments[1];
                    }
                }
            }

            this.drawItemStack(itemStack, i - k - b0, j - l - j2, text);
        }

        if (this.returningStack != null) {
            float f1 = (float) (MinecraftClient.getSystemTime() - this.returningStackTime) / 100.0F;
            if (f1 >= 1.0F) {
                f1 = 1.0F;
                this.returningStack = null;
            }

            int k2 = this.returningStackDestSlot.xDisplayPosition - this.H;
            int l2 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
            int l1 = this.H + (int) (k2 * f1);
            int i2 = this.touchUpY + (int) (l2 * f1);
            this.drawItemStack(this.returningStack, l1, i2, (String) null);
        }

        GlStateManager.popMatrix();
        if (bdI.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            ItemStack itemStackValue = this.theSlot.getStack();
            this.renderToolTip(itemStackValue, i, j);
        }

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
    }

    public void initGui() {
        super.initGui();
        this.mc.player.openContainer = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.B = (this.width + this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private boolean isMouseOverSlot(Slot slot, int i, int j) {
        return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, i, j);
    }

    private void drawSlot(Slot slot) {
        int i;
        int j;
        ItemStack itemStack;
        byte b0;
        int k;
        String text;
        label64:
        {
            i = slot.xDisplayPosition;
            int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
            j = slot.yDisplayPosition;
            itemStack = slot.getStack();
            int[] aint = aint1;
            b0 = 0;
            k =
                    slot == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick
                            ? 1
                            : 0;
            ItemStack itemStackValue = this.mc.player.inventory.getItemStack();
            text = null;
            if (slot == this.clickedSlot
                    && this.draggedStack != null
                    && this.isRightMouseClick
                    && itemStack != null) {
                itemStack = itemStack.copy();
                itemStack.stackSize /= 2;
                if (aint == null) {
                    break label64;
                }
            }

            if (this.dragSplitting
                    && this.dragSplittingSlots.contains(slot)
                    && itemStackValue != null) {
                if (this.dragSplittingSlots.size() == 1) {
                    return;
                }

                if (Container.canAddItemToSlot(slot, itemStackValue, true)
                        && this.inventorySlots.canDragIntoSlot(slot)) {
                    itemStack = itemStackValue.copy();
                    b0 = 1;
                    Container.computeStackSize(
                            this.dragSplittingSlots,
                            this.dragSplittingLimit,
                            itemStack,
                            slot.getStack() == null ? 0 : slot.getStack().stackSize);
                    if (itemStack.stackSize > itemStack.getMaxStackSize()) {
                        text =
                                EnumChatFormatting.YELLOW
                                        + packedTextFragments[2]
                                        + itemStack.getMaxStackSize();
                        itemStack.stackSize = itemStack.getMaxStackSize();
                    }

                    if (itemStack.stackSize > slot.getItemStackLimit(itemStack)) {
                        text =
                                EnumChatFormatting.YELLOW
                                        + packedTextFragments[3]
                                        + slot.getItemStackLimit(itemStack);
                        itemStack.stackSize = slot.getItemStackLimit(itemStack);
                    }
                } else {
                    this.dragSplittingSlots.remove(slot);
                    this.updateDragSplitting();
                }
            }
        }

        this.zLevel = 100.0F;
        this.itemRender.zLevel = 100.0F;
        if (itemStack == null) {
            String textValue = slot.getSlotTexture();
            if (textValue != null) {
                TextureAtlasSprite atlasSprite =
                        this.mc.getTextureMapBlocks().getAtlasSprite(textValue);
                GlStateManager.disableLighting();
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModalRect(i, j, atlasSprite, 16, 16);
                GlStateManager.enableLighting();
                k = 1;
            }
        }

        if (k == 0) {
            if (b0 != 0) {
                drawRect(i, j, i + 16, j + 16, -2130706433);
            }

            GlStateManager.enableDepth();
            this.itemRender.renderItemAndEffectIntoGUI(itemStack, i, j);
            this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemStack, i, j, text);
        }

        this.itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    // region String decoding support

    // endregion

    protected void keyTyped(char c0, int i) throws IOException {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.player.closeScreen();
        }

        this.checkHotbarKeys(i);
        if (this.theSlot != null && this.theSlot.getHasStack()) {
            if (i == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
                if (aint == null) {
                    return;
                }
            }

            if (i == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
                this.handleMouseClick(
                        this.theSlot, this.theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
            }
        }
    }

    protected boolean isPointInRegion(int i, int j, int k, int l, int i1, int j1) {
        int k1 = this.guiLeft;
        int l1 = this.guiTop;
        i1 -= k1;
        j1 -= l1;
        return i1 >= i - 1 && i1 < i + k + 1 && j1 >= j - 1 && j1 < j + l + 1;
    }

    public void handleMouseClick(Slot slot, int i, int j, int k) {
        if (slot != null) {
            i = slot.slotNumber;
        }

        this.mc.playerController.windowClick(this.inventorySlots.windowId, i, j, k, this.mc.player);
    }
}
