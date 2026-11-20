package remix.module.player;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemShears;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.util.BlockPos;

import remix.client.ClientServices;
import remix.event.PacketDirection;
import remix.event.annotation.Subscribe;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.hidden.InventoryActionsTrackerModule;
import remix.module.world.FuckerModule;
import remix.setting.Setting;
import remix.util.inventory.InventoryItemHelper;
import remix.util.inventory.ItemSlot;

/** AutoTool module: player events and 8 settings. */
public final class AutoToolModule extends ModuleBase {

    private Setting slotSetting;

    private Setting attackSetting;

    private Setting blockSetting;

    private ItemSlot selectedTool;

    private final Setting grabDelaySetting =
            new Setting("GrabDelay", this, 150.0, 0.0, 1000.0, 50.0);

    private Setting swordOnlySetting;

    private Setting switchBackSetting;

    private Setting interactSetting;

    private int previousHotbarSlot;

    private boolean toolEquipped;

    private Setting fromInventorySetting;





    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {

        if (minecraft.player != null && minecraft.world != null) {

            if (event.getDirection() == PacketDirection.SENT) {

                int i = ClientServices.moduleManager.aura.currentTarget != null ? 1 : 0;

                Container bdq = minecraft.player.inventoryContainer;

                int j =
                        this.fromInventorySetting.asBoolean()
                                        && minecraft.player.openContainer == bdq
                                ? 9
                                : 36;

                byte b0 = 44;

                if (event.getPacket() instanceof DiggingPacket && i == 0) {

                    DiggingPacket packet = (DiggingPacket) event.getPacket();

                    if (packet.getStatus() == DiggingAction.START_DESTROY_BLOCK
                            && !minecraft.player.capabilities.isCreativeMode) {

                        BlockPos blockPos = packet.getPosition();

                        if (this.blockSetting.asBoolean()) {

                            this.selectedTool =
                                    InventoryItemHelper.findBestToolForBlockRange(blockPos, j, b0);

                            this.toolEquipped = false;

                            this.timer.reset();
                        }
                    }

                    return;
                }

                if (event.getPacket() instanceof UseEntityPacket) {

                    UseEntityPacket packetValue = (UseEntityPacket) event.getPacket();

                    Entity entity = packetValue.getEntityFromWorld(minecraft.world);

                    boolean attack = this.attackSetting.asBoolean();

                    boolean interact = this.interactSetting.asBoolean();

                    if (entity instanceof LivingEntity && (attack || interact)) {

                        switch (packetValue.getAction()) {
                            case INTERACT:
                                if (!interact) {

                                    return;
                                }

                                this.selectedTool =
                                        entity instanceof EntityCow
                                                ? (minecraft.player
                                                                        .isCreativeGameModeFromPlayerInfo()
                                                                && ((EntityCow) entity).isChild()
                                                        ? null
                                                        : InventoryItemHelper.findSlotMatching(
                                                                Items.bucket,
                                                                j,
                                                                b0,
                                                                ((java.util.function.Predicate<
                                                                                net.minecraft.item
                                                                                        .ItemBucket>)
                                                                        (itemBucket ->
                                                                                itemBucket
                                                                                                .getContainedBlock()
                                                                                        == Blocks
                                                                                                .air))))
                                                : (entity instanceof EntitySheep
                                                        ? (((EntitySheep) entity).getSheared()
                                                                        && ((EntitySheep) entity)
                                                                                .isChild()
                                                                ? null
                                                                : InventoryItemHelper
                                                                        .findItemSlotRange(
                                                                                Items.shears,
                                                                                j,
                                                                                b0))
                                                        : null);

                                if (this.selectedTool == null) {

                                    break;
                                }

                                this.toolEquipped = false;

                                this.timer.reset();

                                break;

                            case ATTACK:
                                if (!attack) {

                                    return;
                                }

                                byte b1 = 0;

                                if (this.swordOnlySetting.asBoolean()) {

                                    this.selectedTool =
                                            InventoryItemHelper.findBestSlot(
                                                    SwordItem.class,
                                                    InvManagerModule.weaponComparator);
                                }

                                if (this.selectedTool.itemStack == null) {

                                    this.selectedTool =
                                            InventoryItemHelper.findBestWeaponSlot(j, b0, entity);
                                }

                                this.toolEquipped = false;

                                this.timer.reset();
                        }
                    }
                }
            }
        }
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (minecraft.playerController.getIsHittingBlock()) {

            this.timer.reset();
        }

        FuckerModule fucker = ClientServices.moduleManager.fucker;

        int i =
                !minecraft.playerController.getIsHittingBlock()
                                && (!fucker.isEnabled() || fucker.targetBlock == null)
                        ? 0
                        : 1;

        if (!this.toolEquipped && this.selectedTool != null) {

            if (this.previousHotbarSlot == -1) {

                this.previousHotbarSlot = minecraft.player.inventory.currentItem;
            }

            if (this.selectedTool.slotIndex < 36) {

                if (!InventoryActionsTrackerModule.inventoryActionTimer.hasElapsed(
                        (long) this.grabDelaySetting.asDouble())) {

                    return;
                }

                GuiInventory guiInventory =
                        minecraft.currentScreen instanceof GuiInventory
                                ? (GuiInventory) minecraft.currentScreen
                                : new GuiInventory(minecraft.player);

                minecraft.playerController.updateController();

                InventoryItemHelper.clickSlot(
                        guiInventory,
                        this.selectedTool.slotIndex,
                        (int) this.slotSetting.asDouble() - 1,
                        2);

                this.selectedTool.slotIndex = 36 + (int) this.slotSetting.asDouble() - 1;

                InventoryActionsTrackerModule.inventoryActionTimer.reset();
            }

            minecraft.player.inventory.currentItem = this.selectedTool.slotIndex - 36;

            this.toolEquipped = true;

            this.timer.reset();

            return;
        }

        if (this.previousHotbarSlot != -1
                && this.switchBackSetting.asBoolean()
                && i == 0
                && this.toolEquipped) {

            if (this.timer.hasElapsed(150L)) {

                if (this.selectedTool != null && this.selectedTool.itemStack != null) {

                    int j = ClientServices.moduleManager.aura.currentTarget == null ? 1 : 0;

                    Item item = this.selectedTool.itemStack.getItem();

                    if (!(item instanceof SwordItem)
                            && !(item instanceof ItemBucket)
                            && !(item instanceof ItemShears)
                            && j != 0) {

                        minecraft.player.inventory.currentItem = this.previousHotbarSlot;
                    }
                }

                this.selectedTool = null;

                this.previousHotbarSlot = -1;
            }
        }
    }

    public AutoToolModule() {

        super("AutoTool", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));

        this.slotSetting = new Setting("Slot", this, 8.0, 1.0, 9.0, 1.0);

        this.fromInventorySetting = new Setting("FromInventory", this, true);

        this.switchBackSetting = new Setting("SwitchBack", this, true);

        this.blockSetting = new Setting("Block", this, true);

        this.attackSetting = new Setting("Attack", this, true);

        this.interactSetting = new Setting("Interact", this, true);

        this.swordOnlySetting = new Setting("SwordOnly", this, true);

        this.previousHotbarSlot = -1;

        this.setName("Auto Tool");
    }


}
