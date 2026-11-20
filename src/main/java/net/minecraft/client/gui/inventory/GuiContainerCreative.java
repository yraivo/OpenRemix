package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.inventory.CreativePlayerInventorySlot;

import com.google.common.collect.Lists;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GuiContainerCreative extends InventoryEffectRenderer {
    private static int selectedTabIndex;
    private boolean wasClicking;
    private boolean clearSearchOnNextKey;
    private Slot trashSlot;
    private CreativeCrafting creativeInventoryListener;
    private static ResourceLocation creativeInventoryTabs;
    private int currentTabPage;
    private static InventoryBasic catalogInventory;
    private float currentScroll;
    private static String[] packedTextFragments;
    private GuiTextField searchField;

    private boolean isScrolling;
    private List savedCatalogSlots;
    private int lastTabPage;

    protected void mouseReleased(int i, int j, int k) {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (k == 0) {
            int l = i - this.guiLeft;
            int i1 = j - this.guiTop;

            for (CreativeTabs creativeTabs : CreativeTabs.creativeTabArray) {
                if (this.isMouseOverTab(creativeTabs, l, i1)) {
                    this.setCurrentCreativeTab(creativeTabs);
                    return;
                }

                if (aint != null) {
                    break;
                }
            }
        }

        super.mouseReleased(i, j, k);
    }

    public void drawScreen(int i, int j, float f) {
        boolean angValue = Mouse.isButtonDown(0);
        int k = this.guiLeft;
        int l = this.guiTop;
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        int i1 = k + 175;
        int j1 = l + 18;
        int k1 = i1 + 14;
        int[] aint = aint1;
        int l1 = j1 + 112;
        if (!this.wasClicking && angValue && i >= i1 && j >= j1 && i < k1 && j < l1) {
            this.isScrolling = this.needsScrollBars();
        }

        if (!angValue) {
            this.isScrolling = false;
        }

        this.wasClicking = angValue;
        if (this.isScrolling) {
            this.currentScroll = (j - j1 - 7.5F) / (l1 - j1 - 15.0F);
            this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
            ((CreativeCatalogContainer) this.inventorySlots)
                    .scrollTo(this.currentScroll);
        }

        super.drawScreen(i, j, f);
        int end = this.endTabIndexOnPage();
        for (int index = this.firstTabIndexOnPage(); index < end; index++) {
            CreativeTabs creativeTabs = CreativeTabs.creativeTabArray[index];
            if (creativeTabs != null
                            && this.drawTabTooltipIfHovered(creativeTabs, i, j)
                            && aint == null
                    || aint != null) {
                break;
            }
        }

        if (this.trashSlot != null
                && net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex
                        == CreativeTabs.tabInventory.getTabIndex()
                && this.isPointInRegion(
                        this.trashSlot.xDisplayPosition,
                        this.trashSlot.yDisplayPosition,
                        16,
                        16,
                        i,
                        j)) {
            this.drawCreativeTabHoveringText(I18n.format("inventory.binSlot", new Object[0]), i, j);
        }

        if (this.lastTabPage != 0) {
            String text = String.format("%d / %d", this.currentTabPage + 1, this.lastTabPage + 1);
            int k2 = this.fontRendererObj.getStringWidth(text);
            GlStateManager.disableLighting();
            this.zLevel = 300.0F;
            this.itemRender.zLevel = 300.0F;
            this.fontRendererObj.drawString(
                    text, this.guiLeft + this.xSize / 2 - k2 / 2, this.guiTop - 44, -1);
            this.zLevel = 0.0F;
            this.itemRender.zLevel = 0.0F;
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
    }

    private int firstTabIndexOnPage() {
        return this.currentTabPage == 0 ? 0 : this.currentTabPage * 10 + 2;
    }

    private int endTabIndexOnPage() {
        return Math.min(CreativeTabs.creativeTabArray.length, (this.currentTabPage + 1) * 10 + 2);
    }

    private void setCurrentCreativeTab(CreativeTabs tab) {
        boolean legacyCreativeFlow = CreativeCrafting.getLegacyCreativeFlowMarker() != null;
        int previousTabIndex = selectedTabIndex;
        selectedTabIndex = tab.getTabIndex();
        CreativeCatalogContainer container = (CreativeCatalogContainer) this.inventorySlots;
        this.dragSplittingSlots.clear();
        container.itemList.clear();
        tab.addDisplayedItems(container.itemList);

        if (tab == CreativeTabs.tabInventory) {
            this.showPlayerInventorySlots(container, legacyCreativeFlow);
        }
        if ((tab != CreativeTabs.tabInventory || legacyCreativeFlow)
                && previousTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
            container.inventorySlots = this.savedCatalogSlots;
            this.savedCatalogSlots = null;
        }
        this.updateSearchFieldForTab(tab, legacyCreativeFlow);
        this.currentScroll = 0.0F;
        container.scrollTo(0.0F);
    }

    private void showPlayerInventorySlots(CreativeCatalogContainer container, boolean legacyCreativeFlow) {
        Container playerContainer = this.mc.player.inventoryContainer;
        if (this.savedCatalogSlots == null) {
            this.savedCatalogSlots = container.inventorySlots;
        }
        container.inventorySlots = Lists.newArrayList();
        for (int slotIndex = 0; slotIndex < playerContainer.inventorySlots.size(); slotIndex++) {
            CreativePlayerInventorySlot slot = new CreativePlayerInventorySlot(
                    this, (Slot) playerContainer.inventorySlots.get(slotIndex), slotIndex);
            container.inventorySlots.add(slot);
            this.positionPlayerInventorySlot(slot, slotIndex, legacyCreativeFlow);
            if (legacyCreativeFlow) {
                break;
            }
        }
        this.trashSlot = new Slot(net.minecraft.client.gui.inventory.GuiContainerCreative.catalogInventory, 0, 173, 112);
        container.inventorySlots.add(this.trashSlot);
    }

    private void positionPlayerInventorySlot(Slot slot, int index, boolean legacyCreativeFlow) {
        if (index >= 5 && index < 9) {
            int armorIndex = index - 5;
            slot.xDisplayPosition = 9 + armorIndex / 2 * 54;
            slot.yDisplayPosition = 6 + armorIndex % 2 * 27;
            if (!legacyCreativeFlow) {
                return;
            }
        }
        if (index < 5) {
            slot.xDisplayPosition = -2000;
            slot.yDisplayPosition = -2000;
            if (!legacyCreativeFlow) {
                return;
            }
        }
        int inventoryIndex = index - 9;
        slot.xDisplayPosition = 9 + inventoryIndex % 9 * 18;
        slot.yDisplayPosition = index >= 36 && !legacyCreativeFlow ? 112 : 54 + inventoryIndex / 9 * 18;
    }

    private void updateSearchFieldForTab(CreativeTabs tab, boolean legacyCreativeFlow) {
        if (this.searchField == null) {
            return;
        }
        if (tab == CreativeTabs.tabAllSearch) {
            this.searchField.setVisible(true);
            this.searchField.setCanLoseFocus(false);
            this.searchField.setFocused(true);
            this.searchField.setText("");
            this.updateCreativeSearch();
            if (!legacyCreativeFlow) {
                return;
            }
        }
        this.searchField.setVisible(false);
        this.searchField.setCanLoseFocus(true);
        this.searchField.setFocused(false);
    }

    @Override
    public void handleMouseClick(Slot slot, int slotId, int mouseButton, int clickMode) {
        boolean legacyCreativeFlow = CreativeCrafting.getLegacyCreativeFlowMarker() != null;
        this.clearSearchOnNextKey = true;
        boolean shiftClick = clickMode == 1;
        if (slotId == -999 && clickMode == 0) {
            clickMode = 4; // Outside the container is a throw, rather than a slot pickup.
        }

        boolean inventoryTab = selectedTabIndex == CreativeTabs.tabInventory.getTabIndex();
        if (slot == null && !inventoryTab && clickMode != 5
                && this.dropCreativeCursorStack(mouseButton, legacyCreativeFlow)) {
            return;
        }
        if (slot == this.trashSlot && shiftClick && this.clearCreativeInventory(legacyCreativeFlow)) {
            return;
        }
        if (inventoryTab && this.clickPlayerInventorySlot(slot, slotId, mouseButton, clickMode, legacyCreativeFlow)) {
            return;
        }
        if (clickMode != 5 && slot != null && slot.inventory == net.minecraft.client.gui.inventory.GuiContainerCreative.catalogInventory
                && this.clickCreativeCatalogSlot(slot, mouseButton, clickMode, shiftClick, legacyCreativeFlow)) {
            return;
        }

        this.inventorySlots.slotClick(slot == null ? slotId : slot.slotNumber, mouseButton, clickMode, this.mc.player);
        if (Container.getDragEvent(mouseButton) == 2) {
            for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
                this.mc.playerController.sendSlotPacket(
                        this.inventorySlots.getSlot(45 + hotbarSlot).getStack(), 36 + hotbarSlot);
                if (legacyCreativeFlow) {
                    break;
                }
            }
            if (!legacyCreativeFlow) {
                return;
            }
        }
        if (slot != null) {
            ItemStack updatedStack = this.inventorySlots.getSlot(slot.slotNumber).getStack();
            int playerSlotId = slot.slotNumber - this.inventorySlots.inventorySlots.size() + 45;
            this.mc.playerController.sendSlotPacket(updatedStack, playerSlotId);
        }
    }

    private boolean dropCreativeCursorStack(int mouseButton, boolean legacyCreativeFlow) {
        InventoryPlayer inventory = this.mc.player.inventory;
        ItemStack carried = inventory.getItemStack();
        if (carried == null) {
            return true;
        }
        if (mouseButton == 0) {
            this.dropCreativeStack(carried);
            inventory.setItemStack(null);
            return true;
        }
        if (mouseButton != 1) {
            return true;
        }
        this.dropCreativeStack(carried.splitStack(1));
        if (carried.stackSize != 0) {
            return true;
        }
        inventory.setItemStack(null);
        return !legacyCreativeFlow;
    }

    private boolean clearCreativeInventory(boolean legacyCreativeFlow) {
        for (int index = 0; index < this.mc.player.inventoryContainer.getInventory().size(); index++) {
            this.mc.playerController.sendSlotPacket(null, index);
            if (legacyCreativeFlow) {
                return false;
            }
        }
        return true;
    }

    private boolean clickPlayerInventorySlot(Slot slot, int slotId, int mouseButton, int clickMode,
                                             boolean legacyCreativeFlow) {
        InventoryPlayer inventory = this.mc.player.inventory;
        if (slot == this.trashSlot) {
            inventory.setItemStack(null);
            if (!legacyCreativeFlow) {
                return true;
            }
        }
        if (clickMode == 4 && slot != null && slot.getHasStack()) {
            this.dropCreativeStack(slot.decrStackSize(mouseButton == 0 ? 1 : slot.getStack().getMaxStackSize()));
            if (!legacyCreativeFlow) {
                return true;
            }
        }
        if (clickMode == 4 && inventory.getItemStack() != null) {
            this.dropCreativeStack(inventory.getItemStack());
            inventory.setItemStack(null);
            if (!legacyCreativeFlow) {
                return true;
            }
        }

        int actualSlotId = slot == null ? slotId
                : CreativePlayerInventorySlot.getBackingSlot((CreativePlayerInventorySlot) slot).slotNumber;
        this.mc.player.inventoryContainer.slotClick(actualSlotId, mouseButton, clickMode, this.mc.player);
        this.mc.player.inventoryContainer.detectAndSendChanges();
        return !legacyCreativeFlow;
    }

    private boolean clickCreativeCatalogSlot(Slot slot, int mouseButton, int clickMode,
                                              boolean shiftClick, boolean legacyCreativeFlow) {
        InventoryPlayer inventory = this.mc.player.inventory;
        ItemStack carried = inventory.getItemStack();
        ItemStack catalogItem = slot.getStack();
        if (clickMode == 2) {
            if (catalogItem != null && mouseButton >= 0 && mouseButton < 9) {
                ItemStack copy = catalogItem.copy();
                copy.stackSize = copy.getMaxStackSize();
                inventory.setInventorySlotContents(mouseButton, copy);
                this.mc.player.inventoryContainer.detectAndSendChanges();
            }
            return true;
        }
        if (clickMode == 3) {
            if (carried == null && slot.getHasStack()) {
                ItemStack copy = catalogItem.copy();
                copy.stackSize = copy.getMaxStackSize();
                inventory.setItemStack(copy);
            }
            return true;
        }
        if (clickMode == 4) {
            if (catalogItem != null) {
                ItemStack copy = catalogItem.copy();
                copy.stackSize = mouseButton == 0 ? 1 : copy.getMaxStackSize();
                this.dropCreativeStack(copy);
            }
            return true;
        }
        this.adjustCatalogCursorStack(inventory, carried, catalogItem, mouseButton, shiftClick, legacyCreativeFlow);
        return !legacyCreativeFlow;
    }

    private void adjustCatalogCursorStack(InventoryPlayer inventory, ItemStack carried, ItemStack catalogItem,
                                          int mouseButton, boolean shiftClick, boolean legacyCreativeFlow) {
        if (carried != null && catalogItem != null && carried.isItemEqual(catalogItem)) {
            if (mouseButton == 0) {
                if (shiftClick) {
                    carried.stackSize = carried.getMaxStackSize();
                    if (!legacyCreativeFlow) {
                        return;
                    }
                }
                if (carried.stackSize >= carried.getMaxStackSize()) {
                    return;
                }
                carried.stackSize++;
                if (!legacyCreativeFlow) {
                    return;
                }
            }

            if (carried.stackSize <= 1) {
                inventory.setItemStack(null);
                if (!legacyCreativeFlow) {
                    return;
                }
            }
            carried.stackSize--;
            if (!legacyCreativeFlow) {
                return;
            }
        }
        if (catalogItem != null && carried == null) {
            inventory.setItemStack(ItemStack.copyItemStack(catalogItem));
            if (!shiftClick) {
                return;
            }
            ItemStack copy = inventory.getItemStack();
            copy.stackSize = copy.getMaxStackSize();
            if (!legacyCreativeFlow) {
                return;
            }
        }
        inventory.setItemStack(null);
    }

    private void dropCreativeStack(ItemStack stack) {
        this.mc.player.dropPlayerItemWithRandomChoice(stack, true);
        this.mc.playerController.sendPacketDropItem(stack);
    }

    public GuiContainerCreative(PlayerEntity playerEntity) {
        super(new CreativeCatalogContainer(playerEntity));
        playerEntity.openContainer = this.inventorySlots;
        this.allowUserInput = true;
        this.ySize = 136;
        this.xSize = 195;
    }

    public void updateScreen() {
        if (!this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.player));
        }

        this.updateActivePotionEffects();
    }

    static InventoryBasic getCatalogInventory() {
        return net.minecraft.client.gui.inventory.GuiContainerCreative.catalogInventory;
    }

    static {
        net.minecraft.client.gui.inventory.GuiContainerCreative.packedTextFragments = new String[7];
        net.minecraft.client.gui.inventory.GuiContainerCreative.packedTextFragments[0] = new String(">");
        net.minecraft.client.gui.inventory.GuiContainerCreative.packedTextFragments[1] = new String("<");
        net.minecraft.client.gui.inventory.GuiContainerCreative.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.inventory.GuiContainerCreative.packedTextFragments[3] = new String("");
        net.minecraft.client.gui.inventory.GuiContainerCreative.packedTextFragments[4] = new String("");
        net.minecraft.client.gui.inventory.GuiContainerCreative.creativeInventoryTabs =
                new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
        net.minecraft.client.gui.inventory.GuiContainerCreative.catalogInventory = new InventoryBasic("tmp", true, 45);
        net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
    }

    protected void renderToolTip(ItemStack itemStack, int i, int j) {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex
                == CreativeTabs.tabAllSearch.getTabIndex()) {
            List list =
                    itemStack.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips);
            CreativeTabs creativeTab = itemStack.getItem().getCreativeTab();
            if (creativeTab == null && itemStack.getItem() == Items.enchanted_book) {
                Map map = EnchantmentHelper.getEnchantments(itemStack);
                if (map.size() == 1) {
                    Enchantment enchantment =
                            Enchantment.getEnchantmentById((Integer) map.keySet().iterator().next());

                    for (CreativeTabs creativeTabs : CreativeTabs.creativeTabArray) {
                        if (creativeTabs != null
                                && creativeTabs.hasRelevantEnchantmentType(enchantment.type)) {
                            creativeTab = creativeTabs;
                            if (aint == null) {
                                break;
                            }
                        }

                        if (aint != null) {
                            break;
                        }
                    }
                }
            }

            if (creativeTab != null) {
                list.add(
                        1,
                        packedTextFragments[4]
                                + EnumChatFormatting.BOLD
                                + EnumChatFormatting.BLUE
                                + I18n.format(creativeTab.getTranslatedTabLabel(), new Object[0]));
            }

            int k = 0;

            while (k < list.size()) {
                if (k == 0) {
                    list.set(k, itemStack.getRarity().rarityColor + (String) list.get(k));
                }
                if (k != 0 || aint != null) {
                    list.set(k, EnumChatFormatting.GRAY + (String) list.get(k));
                }

                k++;
                if (aint != null) {
                    break;
                }
            }

            this.drawHoveringText(list, i, j);
            if (aint == null) {
                return;
            }
        }

        super.renderToolTip(itemStack, i, j);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int eventDWheel = Mouse.getEventDWheel();
        if (eventDWheel != 0 && this.needsScrollBars()) {
            int j =
                    ((CreativeCatalogContainer) this.inventorySlots).itemList.size()
                                    / 9
                            - 5
                            + 1;
            if (eventDWheel > 0) {
                eventDWheel = 1;
            }

            if (eventDWheel < 0) {
                eventDWheel = -1;
            }

            this.currentScroll = (float) (this.currentScroll - (double) eventDWheel / j);
            this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
            ((CreativeCatalogContainer) this.inventorySlots)
                    .scrollTo(this.currentScroll);
        }
    }

    private void updateCreativeSearch() {
        boolean legacyCreativeFlow = CreativeCrafting.getLegacyCreativeFlowMarker() != null;
        CreativeCatalogContainer container = (CreativeCatalogContainer) this.inventorySlots;
        List<ItemStack> results = container.itemList;
        results.clear();

        for (Object registryEntry : Item.itemRegistry) {
            Item item = (Item) registryEntry;
            if (item != null && item.getCreativeTab() != null) {
                item.getSubItems(item, null, results);
            }
            if (legacyCreativeFlow) {
                break;
            }
        }
        for (Enchantment enchantment : Enchantment.enchantmentsBookList) {
            if (enchantment != null && enchantment.type != null) {
                Items.enchanted_book.getAll(enchantment, results);
            }
            if (legacyCreativeFlow) {
                break;
            }
        }

        String query = this.searchField.getText().toLowerCase();
        for (Iterator<ItemStack> entries = results.iterator(); entries.hasNext();) {
            ItemStack stack = entries.next();
            if (!this.tooltipMatchesSearch(stack, query, legacyCreativeFlow)) {
                entries.remove();
            }
            if (legacyCreativeFlow) {
                break;
            }
        }
        this.currentScroll = 0.0F;
        container.scrollTo(0.0F);
    }

    private boolean tooltipMatchesSearch(ItemStack stack, String query, boolean legacyCreativeFlow) {
        for (String line : stack.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips)) {
            if (EnumChatFormatting.getTextWithoutFormattingCodes(line).toLowerCase().contains(query)
                    || legacyCreativeFlow) {
                return true;
            }
        }
        return false;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        RenderHelper.enableGUIStandardItemLighting();
        CreativeTabs creativeTabs =
                CreativeTabs.creativeTabArray[net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex];
        int[] aint = aint1;
        int end = this.endTabIndexOnPage();
        for (int index = this.firstTabIndexOnPage(); index < end; index++) {
            CreativeTabs creativeTabsValue = CreativeTabs.creativeTabArray[index];
            this.mc
                    .getTextureManager()
                    .bindTexture(net.minecraft.client.gui.inventory.GuiContainerCreative.creativeInventoryTabs);
            if (creativeTabsValue != null
                    && creativeTabsValue.getTabIndex()
                            != net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex) {
                this.drawCreativeTab(creativeTabsValue);
            }

            if (aint != null) {
                break;
            }
        }

        if (this.currentTabPage != 0) {
            if (creativeTabs != CreativeTabs.tabAllSearch) {
                this.mc
                        .getTextureManager()
                        .bindTexture(net.minecraft.client.gui.inventory.GuiContainerCreative.creativeInventoryTabs);
                this.drawCreativeTab(CreativeTabs.tabAllSearch);
            }

            if (creativeTabs != CreativeTabs.tabInventory) {
                this.mc
                        .getTextureManager()
                        .bindTexture(net.minecraft.client.gui.inventory.GuiContainerCreative.creativeInventoryTabs);
                this.drawCreativeTab(CreativeTabs.tabInventory);
            }
        }

        this.mc
                .getTextureManager()
                .bindTexture(
                        new ResourceLocation(
                                "textures/gui/container/creative_inventory/tab_"
                                        + creativeTabs.getBackgroundImageName()));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.searchField.drawTextBox();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int k1 = this.guiLeft + 175;
        int i1 = this.guiTop + 18;
        int j1 = i1 + 112;
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.inventory.GuiContainerCreative.creativeInventoryTabs);
        if (creativeTabs.shouldHidePlayerInventory()) {
            this.drawTexturedModalRect(
                    k1,
                    i1 + (int) ((j1 - i1 - 17) * this.currentScroll),
                    232 + (this.needsScrollBars() ? 0 : 12),
                    0,
                    12,
                    15);
        }

        if (creativeTabs != null && creativeTabs.getTabPage() == this.currentTabPage
                || creativeTabs == CreativeTabs.tabAllSearch
                || creativeTabs == CreativeTabs.tabInventory) {
            this.drawCreativeTab(creativeTabs);
            if (creativeTabs == CreativeTabs.tabInventory) {
                GuiInventory.drawEntityOnScreen(
                        this.guiLeft + 43,
                        this.guiTop + 45,
                        20,
                        this.guiLeft + 43 - i,
                        this.guiTop + 45 - 30 - j,
                        this.mc.player);
            }

            if (Entity.getLegacyGlobalFlowMarker() != null) {
                CreativeCrafting.setLegacyCreativeFlowMarker(new int[3]);
            }
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.mc.player != null && this.mc.player.inventory != null) {
            this.mc.player.inventoryContainer.removeCraftingFromCrafters(this.creativeInventoryListener);
        }

        Keyboard.enableRepeatEvents(false);
    }

    public void initGui() {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (this.mc.playerController.isInCreativeMode()) {
            this.lastTabPage = 0;
            for (CreativeTabs tab : CreativeTabs.creativeTabArray) {
                if (tab != null) {
                    this.lastTabPage = Math.max(this.lastTabPage, tab.getTabPage());
                }
            }
            this.currentTabPage = Math.min(this.currentTabPage, this.lastTabPage);
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents(true);
            if (this.lastTabPage > 0) {
                this.buttonList.add(
                        new GuiButton(
                                101,
                                this.guiLeft + this.xSize - 33,
                                this.guiTop - 50,
                                35,
                                20,
                                packedTextFragments[0]));
                this.buttonList.add(
                        new GuiButton(
                                102,
                                this.B - this.xSize,
                                this.guiTop - 50,
                                35,
                                20,
                                packedTextFragments[1]));
            }
            this.searchField =
                    new GuiTextField(
                            0,
                            this.fontRendererObj,
                            this.guiLeft + 82,
                            this.guiTop + 6,
                            89,
                            this.fontRendererObj.FONT_HEIGHT);
            this.searchField.setMaxStringLength(15);
            this.searchField.setEnableBackgroundDrawing(false);
            this.searchField.setVisible(false);
            this.searchField.setTextColor(16777215);
            int j = net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex;
            net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex = -1;
            CreativeTabs selectedTab =
                    j >= 0 && j < CreativeTabs.creativeTabArray.length
                            ? CreativeTabs.creativeTabArray[j]
                            : null;
            this.setCurrentCreativeTab(selectedTab != null ? selectedTab : CreativeTabs.tabBlock);
            this.creativeInventoryListener = new CreativeCrafting(this.mc);
            this.mc.player.inventoryContainer.onCraftGuiOpened(this.creativeInventoryListener);
            if (aint == null) {
                return;
            }
        }

        this.mc.displayGuiScreen(new GuiInventory(this.mc.player));
    }

    protected void keyTyped(char c0, int i) throws IOException {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex
                != CreativeTabs.tabAllSearch.getTabIndex()) {
            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
                this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
                if (aint == null) {
                    return;
                }
            }

            super.keyTyped(c0, i);
            if (aint == null) {
                return;
            }
        }

        if (this.clearSearchOnNextKey) {
            this.clearSearchOnNextKey = false;
            this.searchField.setText(packedTextFragments[2]);
        }

        if (!this.checkHotbarKeys(i)) {
            if (this.searchField.textboxKeyTyped(c0, i)) {
                this.updateCreativeSearch();
                if (aint == null) {
                    return;
                }
            }

            super.keyTyped(c0, i);
        }
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        CreativeTabs creativeTabs =
                CreativeTabs.creativeTabArray[net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex];
        if (creativeTabs.drawInForegroundOfTab()) {
            GlStateManager.disableBlend();
            this.fontRendererObj.drawString(
                    I18n.format(creativeTabs.getTranslatedTabLabel(), new Object[0]),
                    8,
                    6,
                    4210752);
        }
    }

    protected boolean isMouseOverTab(CreativeTabs tab, int mouseX, int mouseY) {
        boolean legacyTabFlow = CreativeCrafting.getLegacyCreativeFlowMarker() != null;
        if (tab == null || (tab.getTabPage() != this.currentTabPage
                && tab != CreativeTabs.tabAllSearch && tab != CreativeTabs.tabInventory)) {
            return false;
        }

        int left = this.getTabLocalX(tab.getTabColumn(), true, legacyTabFlow);
        int top = this.getTabHitY(tab, legacyTabFlow);
        return mouseX >= left && mouseX <= left + 28 && mouseY >= top && mouseY <= top + 32;
    }

    protected void drawCreativeTab(CreativeTabs tab) {
        boolean legacyTabFlow = CreativeCrafting.getLegacyCreativeFlowMarker() != null;
        boolean selected = tab.getTabIndex() == selectedTabIndex;
        boolean topRow = tab.isTopRow();
        int column = tab.getTabColumn();
        int tabX = this.guiLeft + this.getTabLocalX(column, false, legacyTabFlow);
        int tabY = this.guiTop - (topRow ? 28 : 0);
        int textureY = selected ? 32 : 0;
        if (!topRow || legacyTabFlow) {
            tabY += this.ySize - 4;
            textureY += 64;
        }

        GlStateManager.disableLighting();
        this.drawTexturedModalRect(tabX, tabY, column * 28, textureY, 28, 32);
        this.zLevel = 100.0F;
        this.itemRender.zLevel = 100.0F;
        int iconX = tabX + 6;
        int iconY = tabY + 8 + (topRow ? 1 : -1);
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        ItemStack icon = tab.getIconItemStack();
        this.itemRender.renderItemAndEffectIntoGUI(icon, iconX, iconY);
        this.itemRender.renderItemOverlays(this.fontRendererObj, icon, iconX, iconY);
        GlStateManager.disableLighting();
        this.itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    private int getTabLocalX(int column, boolean hitArea, boolean legacyTabFlow) {
        int left = column == 5 ? this.xSize - 28 + (hitArea ? 2 : 0) : column * 28;
        return column > 0 && (column != 5 || legacyTabFlow) ? left + column : left;
    }

    private int getTabHitY(CreativeTabs tab, boolean legacyTabFlow) {
        return tab.isTopRow() && !legacyTabFlow ? -32 : this.ySize;
    }

    private boolean needsScrollBars() {
        return net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex
                        != CreativeTabs.tabInventory.getTabIndex()
                && CreativeTabs.creativeTabArray[net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex]
                        .shouldHidePlayerInventory()
                && ((CreativeCatalogContainer) this.inventorySlots).hasScrollableItems();
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (k == 0) {
            int l = i - this.guiLeft;
            int i1 = j - this.guiTop;

            for (CreativeTabs creativeTabs : CreativeTabs.creativeTabArray) {
                if (this.isMouseOverTab(creativeTabs, l, i1)) {
                    return;
                }

                if (aint != null) {
                    break;
                }
            }
        }

        super.mouseClicked(i, j, k);
    }

    public int getSelectedTabIndex() {
        return net.minecraft.client.gui.inventory.GuiContainerCreative.selectedTabIndex;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.player.getStatFileWriter()));
        }

        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
        }

        if (guiButton.id == 101) {
            this.currentTabPage = Math.min(this.currentTabPage + 1, this.lastTabPage);
        }

        if (guiButton.id == 102) {
            this.currentTabPage = Math.max(this.currentTabPage - 1, 0);
        }
    }

    protected boolean drawTabTooltipIfHovered(CreativeTabs tab, int mouseX, int mouseY) {
        boolean legacyTabFlow = CreativeCrafting.getLegacyCreativeFlowMarker() != null;
        int left = this.getTabLocalX(tab.getTabColumn(), true, legacyTabFlow);
        int top = this.getTabHitY(tab, legacyTabFlow);
        if (!this.isPointInRegion(left + 3, top + 3, 23, 27, mouseX, mouseY)) {
            return false;
        }

        this.drawCreativeTabHoveringText(I18n.format(tab.getTranslatedTabLabel()), mouseX, mouseY);
        return true;
    }

}
