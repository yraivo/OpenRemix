package remix.module.player;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import remix.event.impl.ClientTickEvent;
import net.minecraft.client.gui.GuiScreen;
import remix.util.inventory.InventoryItemHelper;
import remix.util.inventory.ItemSlot;
import remix.util.movement.MovementPhysicsHelper;
import remix.util.entity.RaycastHelper;
import remix.util.entity.RotationHelper;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.hidden.InventoryActionsTrackerModule;
import remix.setting.Setting;



/** AutoMLG module: player events and 3 settings. */

public final class AutoMLGModule extends ModuleBase {



    private BlockPos placedWaterPosition;

    private Setting grabDelaySetting;

    private final Setting noFallPassiveSetting = new Setting("NoFallPassive", this, false);



    private Setting slotSetting;



    public AutoMLGModule() {

        super("AutoMLG", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));

        this.grabDelaySetting = new Setting("GrabDelay", this, 150.0, 0.0, 1000.0, 50.0);

        this.slotSetting = new Setting("Slot", this, 8.0, 1.0, 9.0, 1.0);

        this.setName("Auto MLG");

    }



    @Subscribe(mc = ClientTickEvent.class, priority = SubscriberPriority.LOW)

    public void onClientTick(ClientTickEvent event) {

        LocalPlayer player = minecraft.player;



        ClientWorld world = minecraft.world;

        int i =

                this.noFallPassiveSetting.asBoolean()

                                && ClientServices.moduleManager.noFall.isEnabled()

                        ? 0

                        : 1;

        if (i != 0

                && player != null

                && world != null

                && !minecraft.playerController.isInCreativeMode()) {

            float f = MovementPhysicsHelper.getMaxFallDistanceWithJump();

            if (player.fallDistance >= f) {

                ItemSlot itemSlot =

                        InventoryItemHelper.findItemSlotRange(

                                Items.water_bucket, 9, 44);

                if (itemSlot.slotIndex < 36) {

                    if (!InventoryActionsTrackerModule.inventoryActionTimer.hasElapsed(

                            (long) this.grabDelaySetting.asDouble())) {

                        return;

                    }



                    GuiScreen currentScreen = minecraft.currentScreen;

                    GuiInventory guiInventory =

                            currentScreen instanceof GuiInventory

                                    ? (GuiInventory) currentScreen

                                    : new GuiInventory(player);

                    InventoryItemHelper.clickSlot(

                            guiInventory,

                            itemSlot.slotIndex,

                            (int) this.slotSetting.asDouble() - 1,

                            2);

                    itemSlot.slotIndex = 36 + (int) this.slotSetting.asDouble() - 1;

                    InventoryActionsTrackerModule.inventoryActionTimer.reset();

                }



                player.inventory.currentItem = itemSlot.slotIndex - 36;

                ItemStack currentEquippedItem = player.getCurrentEquippedItem();

                if (currentEquippedItem != null

                        && currentEquippedItem.getItem() == Items.water_bucket) {

                    Vec3 vec3 =

                            MovementPhysicsHelper.interpolateEntityPosition(player, 2.0F);

                    float[] afloat3 = new float[] {player.rotationYaw, player.rotationPitch};

                    float[] afloat = RotationHelper.rotationsTo(vec3, afloat3, 0.0F);

                    RayTraceResult objectMouseOver =

                            RaycastHelper.rayTraceBlocks(6.0, 1.0F, afloat);

                    if (objectMouseOver != null && objectMouseOver.typeOfHit == RayHitType.BLOCK) {

                        minecraft.objectMouseOver = objectMouseOver;

                        player.rotationYaw = afloat[0];

                        player.rotationPitch = afloat[1];

                        minecraft.playerController.sendUseItem(player, world, currentEquippedItem);

                        this.placedWaterPosition = objectMouseOver.getBlockPos();

                        this.timer.reset();

                    }

                }



                return;

            }



            if (this.placedWaterPosition != null) {



                if (this.timer.elapsedAndReset(100L)) {

                    ItemStack currentEquippedItemValue = player.getCurrentEquippedItem();

                    if (currentEquippedItemValue != null

                            && currentEquippedItemValue.getItem() instanceof ItemBucket) {

                        float[] afloat1 = new float[] {player.rotationYaw, player.rotationPitch};

                        float[] afloat2 =

                                RotationHelper.rotationsTo(

                                        new Vec3(this.placedWaterPosition), afloat1, 0.0F);

                        RayTraceResult rayTraceResult =

                                RaycastHelper.rayTraceBlocks(6.0, 1.0F, afloat2);

                        if (rayTraceResult != null) {

                            player.rotationYaw = afloat2[0];

                            player.rotationPitch = afloat2[1];

                            minecraft.playerController.sendUseItem(

                                    player, world, currentEquippedItemValue);

                        }

                    }



                    this.placedWaterPosition = null;

                }

            }

        }

    }






}

