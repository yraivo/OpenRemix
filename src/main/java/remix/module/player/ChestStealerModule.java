package remix.module.player;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import remix.util.time.ElapsedTimer;
import net.minecraft.client.gui.GuiScreen;
import remix.util.inventory.InventoryItemHelper;
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



    private List<Item> alwaysUsefulItems;

    private Setting autoCloseSetting;

    private Setting chestCheckSetting;

    private Setting randomSetting;

    private Setting smartSkipSetting;

    private final Setting delaySetting = new Setting("Delay", this, 150.0, 0.0, 1000.0, 50.0);



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        GuiScreen currentScreen = minecraft.currentScreen;



        if (currentScreen instanceof GuiChest) {

            GuiChest guiChest =

                    (GuiChest) currentScreen;

            ContainerChest att = (ContainerChest) guiChest.inventorySlots;

            ArrayList arrayList = new ArrayList();

            minecraft.playerController.updateController();

            ElapsedTimer timerValue = this.timer;

            double delay = this.delaySetting.asDouble();

            if (!this.chestCheckSetting.asBoolean() || this.isChestMenu(att)) {

                if (timerValue.hasElapsed((long) delay)) {

                    int sizeInventory = att.getLowerChestInventory().getSizeInventory();

                    boolean smartSkip = this.smartSkipSetting.asBoolean();

                    int j = 0;



                    while (j < sizeInventory) {

                        Slot slot = att.getSlot(j);

                        ItemStack itemStack = slot.getStack();

                        if (!smartSkip || this.isUsefulLoot(itemStack, att)) {

                            arrayList.add(slot);

                        }



                        j++;

                    }



                    j = arrayList.size();



                    while (j != 0) {

                        Slot slotValue =

                                this.randomSetting.asBoolean()

                                        ? (Slot)

                                                arrayList.get(

                                                        ThreadLocalRandom.current().nextInt(j))

                                        : (Slot) arrayList.get(0);

                        InventoryItemHelper.clickSlot(

                                guiChest, slotValue.slotNumber, 0, 1);

                        minecraft.playerController.updateController();

                        arrayList.remove(slotValue);

                        j = delay == 0.0 ? arrayList.size() : 0;

                        timerValue.reset();

                    }

                }



                if (this.autoCloseSetting.asBoolean() && timerValue.elapsedAndReset((long) delay)) {

                    minecraft.player.closeScreen();

                }

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



        List list = Arrays.asList(minecraft.player.inventoryContainer, container);

        List listValue =

                Arrays.asList(InventoryItemHelper.getBestArmorSet(list));

        ItemStack itemStack =

                InventoryItemHelper.findBestSlotFromList(

                                ItemPickaxe.class, InvManagerModule.toolComparator, list)

                        .itemStack;

        ItemStack itemStackValue =

                InventoryItemHelper.findBestSlotFromList(

                                ItemAxe.class, InvManagerModule.toolComparator, list)

                        .itemStack;

        ItemStack itemStackValue2 =

                InventoryItemHelper.findBestSlotFromList(

                                SwordItem.class, InvManagerModule.weaponComparator, list)

                        .itemStack;

        ItemStack itemStackValue3 =

                InventoryItemHelper.findBestSlotFromList(

                                ItemSpade.class, InvManagerModule.toolComparator, list)

                        .itemStack;

        ItemStack itemStackValue4 =

                InventoryItemHelper.findBestSlotFromList(

                                ItemBow.class, InvManagerModule.bowComparator, list)

                        .itemStack;

        Item item = stack.getItem();

        return stack == itemStack

                || stack == itemStackValue

                || stack == itemStackValue2

                || stack == itemStackValue3

                || stack == itemStackValue4

                || listValue.contains(stack)

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

