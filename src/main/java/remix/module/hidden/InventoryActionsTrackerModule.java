package remix.module.hidden;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatusEnumState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

import remix.client.RemixClient;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.event.impl.PacketEvent;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.ModuleMetadata;
import remix.util.time.ElapsedTimer;

public final class InventoryActionsTrackerModule extends ModuleMetadata {

    public static ElapsedTimer inventoryActionTimer;

    private static String moduleName;

    public static boolean inventoryOpenStatusSent;

    // region String decoding support

    // endregion





    @Subscribe(mc = PacketEvent.class, priority = SubscriberPriority.HIGH)
    public void onPacket(PacketEvent event) {

        Packet packet = event.getPacket();

        switch (event.getDirection()) {
            case RECEIVED:
                if (packet instanceof S2DPacketOpenWindow) {

                    remix.module.hidden.InventoryActionsTrackerModule.inventoryActionTimer.reset();
                }

                break;

            case SEND:
                if (packet instanceof C0EPacketClickWindow) {

                    remix.module.hidden.InventoryActionsTrackerModule.inventoryActionTimer.reset();

                    return;
                }

                if (packet instanceof C0DPacketCloseWindow) {

                    remix.module.hidden.InventoryActionsTrackerModule.inventoryOpenStatusSent =
                            false;

                    return;
                }

                if (packet instanceof C16PacketClientStatus) {

                    C16PacketClientStatus c16PacketClientStatus = (C16PacketClientStatus) packet;

                    if (c16PacketClientStatus.getStatus()
                            == C16PacketClientStatusEnumState.OPEN_INVENTORY_ACHIEVEMENT) {

                        if (!remix.module.hidden.InventoryActionsTrackerModule
                                .inventoryOpenStatusSent) {

                            remix.module.hidden.InventoryActionsTrackerModule
                                            .inventoryOpenStatusSent =
                                    true;

                            return;
                        }

                        event.setCancelled(true);
                    }
                }

                break;
        }
    }

    public InventoryActionsTrackerModule() {

        super(
                remix.module.hidden.InventoryActionsTrackerModule.moduleName,
                new ModuleDefaults(ModuleCategory.HIDDEN).visible(false));

        RemixClient.eventBus.register(this);
    }


    static {
        remix.module.hidden.InventoryActionsTrackerModule.moduleName = "InventoryActions";

        remix.module.hidden.InventoryActionsTrackerModule.inventoryActionTimer = new ElapsedTimer();
    }
}
