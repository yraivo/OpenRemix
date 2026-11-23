package remix.module.player;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.client.gui.GuiScreen;
import remix.module.player.InvManagerMode;
import remix.util.inventory.InventoryItemHelper;
import remix.util.inventory.ItemSlot;
import remix.util.inventory.EquipmentSelection;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/** InvManager module: player events and 9 settings. */
public final class InvManagerModule extends ModuleBase {
    private final EquipmentSelection equipment = new EquipmentSelection();
    private List<Item> projectileItems;
    private Setting armorSetting;

    private Setting delaySetting;
    private List<Item> uhcItems;
    public static Comparator<ItemStack> weaponComparator;
    private List<Item> alwaysUsefulItems;
    private Setting cleanerSetting;
    private static String[] packedTextFragments;

    public static Comparator<ItemStack> toolComparator;
    public static Comparator<ItemStack> bowComparator;
    private Setting swordSlotSetting;
    private final Setting<InvManagerMode> modeSetting =
            new Setting("Mode", this, InvManagerMode.VANILLA);
    private Setting uhcSetting;
    private long actionDelayMillis;
    private Setting projectilesSetting;
    private int armorTypeIndex;
    private Setting swordSetting;
    private Setting colorCodesSetting;

    private boolean isUsefulItem(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        Item item = stack.getItem();
        return this.equipment.contains(stack)
                || this.alwaysUsefulItems.contains(item)
                || this.uhcSetting.asBoolean() && this.uhcItems.contains(item)
                || this.projectilesSetting.asBoolean() && this.projectileItems.contains(item)
                || ClientServices.moduleManager.autoMLG.isEnabled()
                        && (item == Items.water_bucket || item == Items.bucket)
                || this.colorCodesSetting.asBoolean()
                        && stack.getDisplayName().contains(packedTextFragments[0])
                || item instanceof ItemFood
                || item instanceof ItemPotion && !InventoryItemHelper.isStackValid(stack)
                || InventoryItemHelper.isBlock(item);
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.SEND
                && event.getPacket() instanceof C0EPacketClickWindow) {
            this.timer.reset();
        }
    }

    // region Static initialization and string tables
    static {
        remix.module.player.InvManagerModule.packedTextFragments = new String[3];
        remix.module.player.InvManagerModule.packedTextFragments[0] = new String("\u00a7");
        weaponComparator =
                Comparator.comparingDouble(InventoryItemHelper::getItemDamage);
        toolComparator =
                Comparator.comparingDouble(InventoryItemHelper::getWeaponDamageScore);
        bowComparator =
                Comparator.comparingDouble(InventoryItemHelper::getItemValue);
    }

    // endregion

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        minecraft.playerController.updateController();
        GuiScreen currentScreen = minecraft.currentScreen;
        boolean inInv = currentScreen instanceof GuiInventory;
        boolean openInvOnly = this.modeSetting.getValue() == InvManagerMode.OPENINV;

        if (inInv || (!openInvOnly && minecraft.inGameHasFocus)) {
            if (!this.timer.hasElapsed(this.actionDelayMillis)) {
                return;
            }

            LocalPlayer player = minecraft.player;
            Container container = player.inventoryContainer;
            this.actionDelayMillis = (long) this.delaySetting.asDouble();

            if (this.swordSetting.asBoolean()) {
                ItemSlot itemSlot = InventoryItemHelper.findBestSlot(SwordItem.class, weaponComparator);
                ItemStack itemStack = itemSlot.itemStack;
                double swordSlot = this.swordSlotSetting.asDouble();
                if (itemStack != null
                        && itemSlot.slotIndex - 35 != swordSlot
                        && InventoryItemHelper.getItemDamage(itemStack)
                                > InventoryItemHelper.getItemDamage(
                                        container.getSlot(35 + (int) swordSlot).getStack())) {
                    InventoryItemHelper.clickSlot(this.inventoryScreen(), itemSlot.slotIndex, (int) swordSlot - 1, 2);
                    this.timer.reset();
                    return;
                }
            }

            if (this.armorSetting.asBoolean()) {
                int bestArmorSlot = -1;
                int highestScore = 0;
                int currentType = this.armorTypeIndex;

                for (int j = 5; j <= 44; j++) {
                    Slot slot = container.getSlot(j);
                    if (slot != null && slot.getStack() != null) {
                        ItemStack stack = slot.getStack();
                        Item item = stack.getItem();
                        if (item instanceof ItemArmor && (Item.getIdFromItem(item) - 2) % 4 == currentType) {
                            int score = InventoryItemHelper.getArmorSlotIndex(stack);
                            if (score > highestScore) {
                                highestScore = score;
                                bestArmorSlot = j;
                            }
                        }
                    }
                }

                if (bestArmorSlot != -1 && bestArmorSlot >= 9) {
                    Slot currentArmorSlot = container.getSlot(5 + this.armorTypeIndex);
                    if (currentArmorSlot != null && currentArmorSlot.getHasStack()) {
                        if (InventoryItemHelper.isMainInventoryFull()) {
                            InventoryItemHelper.clickSlot(this.inventoryScreen(), 5 + this.armorTypeIndex, 1, 4);
                        } else {
                            InventoryItemHelper.clickSlot(this.inventoryScreen(), 5 + this.armorTypeIndex, 0, 1);
                        }
                    } else {
                        InventoryItemHelper.clickSlot(this.inventoryScreen(), bestArmorSlot, 0, 1);
                        this.armorTypeIndex++;
                    }

                    this.timer.reset();
                    return;
                } else {
                    this.armorTypeIndex++;
                }

                this.armorTypeIndex %= 4;
            }

            if (this.cleanerSetting.asBoolean()) {
                boolean captured = false;
                try {
                    List inventorySlots = container.inventorySlots;
                    for (int index = 0; index < inventorySlots.size(); index++) {
                        Slot slot = (Slot) inventorySlots.get(index);
                        ItemStack stack = slot.getStack();
                        if (stack == null) continue;
                        if (!captured) {
                            this.equipment.capturePlayer(container, toolComparator, weaponComparator, bowComparator);
                            captured = true;
                        }
                        if (!this.isUsefulItem(stack)) {
                            InventoryItemHelper.clickSlot(this.inventoryScreen(), slot.slotNumber, 1, 4);
                            this.timer.reset();
                            return;
                        }
                    }
                } finally {
                    this.equipment.clear();
                }
            }

            return;
        }

        if (currentScreen instanceof GuiChest) {
            this.actionDelayMillis = (long) Math.max(350.0, this.delaySetting.asDouble());
            this.timer.reset();
        }
    }

    private GuiInventory inventoryScreen() {
        return minecraft.currentScreen instanceof GuiInventory
                ? (GuiInventory) minecraft.currentScreen : new GuiInventory(minecraft.player);
    }

    public InvManagerModule() {
        super("InvManager", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.delaySetting = new Setting("Delay", this, 100.0, 0.0, 1000.0, 50.0);
        this.swordSlotSetting = new Setting("SwordSlot", this, 1.0, 1.0, 9.0, 1.0);
        this.armorSetting = new Setting("Armor", this, true);
        this.swordSetting = new Setting("Sword", this, true);
        this.cleanerSetting = new Setting("Cleaner", this, true);
        this.colorCodesSetting = new Setting("ColorCodes", this, true);
        this.projectilesSetting = new Setting("Projectiles", this, true);
        this.uhcSetting = new Setting("UHC", this, true);
        this.projectileItems = Arrays.asList(Items.egg, Items.snowball);
        this.alwaysUsefulItems =
                Arrays.asList(
                        Items.ender_pearl,
                        Item.getItemFromBlock(Blocks.ice),
                        Item.getItemFromBlock(Blocks.packed_ice),
                        Items.arrow);
        this.uhcItems =
                Arrays.asList(
                        Items.diamond,
                        Items.iron_ingot,
                        Items.gold_ingot,
                        Items.coal,
                        Items.stick,
                        Item.getItemFromBlock(Blocks.enchanting_table));
        this.setName("Inventory Manager");
    }
}
