package remix.module.player;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import remix.util.time.ElapsedTimer;
import net.minecraft.client.gui.GuiScreen;
import remix.util.inventory.InventoryItemHelper;
import remix.util.inventory.EquipmentSelection;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/** ChestStealer module: player events and 5 settings. */
public final class ChestStealerModule extends ModuleBase {
    private final EquipmentSelection equipment = new EquipmentSelection();
    private final List<Slot> lootCandidates = new ArrayList<>(54);
    private List<Item> alwaysUsefulItems;
    private Setting autoCloseSetting;
    private Setting chestCheckSetting;
    private Setting randomSetting;
    private Setting smartSkipSetting;
    private final Setting delaySetting = new Setting("Delay", this, 150.0, 0.0, 1000.0, 50.0);
    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        GuiScreen currentScreen = minecraft.currentScreen;
        if (!(currentScreen instanceof GuiChest)) return;
        GuiChest guiChest = (GuiChest) currentScreen;
        ContainerChest chest = (ContainerChest) guiChest.inventorySlots;
        minecraft.playerController.updateController();
        double delay = this.delaySetting.asDouble();
        if (!this.chestCheckSetting.asBoolean() || this.isChestMenu(chest)) {
            if (this.timer.hasElapsed((long) delay)) {
                this.lootCandidates.clear();
                try {
                    int size = chest.getLowerChestInventory().getSizeInventory();
                    boolean smartSkip = this.smartSkipSetting.asBoolean();
                    boolean captured = false;
                    for (int index = 0; index < size; index++) {
                        Slot slot = chest.getSlot(index);
                        ItemStack stack = slot.getStack();
                        if (stack == null) continue;
                        if (smartSkip && !captured) {
                            this.equipment.captureCombined(minecraft.player.inventoryContainer, chest,
                                    InvManagerModule.toolComparator, InvManagerModule.weaponComparator,
                                    InvManagerModule.bowComparator);
                            captured = true;
                        }
                        if (!smartSkip || this.isUsefulLoot(stack, chest)) this.lootCandidates.add(slot);
                    }
                    while (!this.lootCandidates.isEmpty()) {
                        int index = this.randomSetting.asBoolean()
                                ? ThreadLocalRandom.current().nextInt(this.lootCandidates.size()) : 0;
                        Slot slot = this.lootCandidates.remove(index);
                        if (!slot.getHasStack()) continue;
                        InventoryItemHelper.clickSlot(guiChest, slot.slotNumber, 0, 1);
                        minecraft.playerController.updateController();
                        this.timer.reset();
                        if (delay != 0.0) break;
                    }
                } finally {
                    this.lootCandidates.clear();
                    this.equipment.clear();
                }
            }
            if (this.autoCloseSetting.asBoolean() && this.timer.elapsedAndReset((long) delay)) {
                minecraft.player.closeScreen();
            }
        }
    }
    public ChestStealerModule() {
        super("ChestStealer", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.autoCloseSetting = new Setting("AutoClose", this, true);
        this.randomSetting = new Setting("Random", this, true);
        this.smartSkipSetting = new Setting("SmartSkip", this, true);
        this.chestCheckSetting = new Setting("ChestCheck", this, true);
        this.setName("Chest Stealer");
        this.alwaysUsefulItems =
                Arrays.asList(
                        Items.ender_pearl,
                        Item.getItemFromBlock(Blocks.ice),
                        Item.getItemFromBlock(Blocks.packed_ice),
                        Items.arrow);
    }
    private boolean isChestMenu(ContainerChest chestContainer) {
        String text = chestContainer.getLowerChestInventory().getName();
        return text.contains("Chest") || text.contains("LOW");
    }
    private boolean isUsefulLoot(ItemStack stack, Container container) {
        if (stack == null) {
            return false;
        }
        Item item = stack.getItem();
        return this.equipment.contains(stack)
                || this.alwaysUsefulItems.contains(item)
                || item instanceof ItemPotion
                        && !InventoryItemHelper.isStackValid(stack)
                || InventoryItemHelper.isBlock(item)
                || item instanceof ItemFood;
    }
    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.RECEIVED
                && event.getPacket() instanceof S2DPacketOpenWindow) {
            this.timer.reset();
        }
    }
}
