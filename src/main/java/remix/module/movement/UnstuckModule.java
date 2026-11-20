package remix.module.movement;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** Counts closely spaced server corrections; while the correction burst is active, suppresses movement and outgoing movement packets until the configured delay expires. */
public final class UnstuckModule extends ModuleBase {

    private int correctionBurstCount;

    private Setting automatedSetting;
    private final Setting delaySetting = new Setting("Delay", this, 500.0, 50.0, 1000.0, 50.0);

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        Packet packet = event.getPacket();

        if (event.getDirection() == PacketDirection.SEND) {
            if (packet instanceof PlayerMovementPacket
                    && (this.isCorrectionBurstActive() || !this.automatedSetting.asBoolean())) {
                event.setCancelled(true);
            }
        } else if (event.getDirection() == PacketDirection.RECEIVED) {
            if (packet instanceof S08PacketPlayerPosLook) {
                if (!this.timer.hasElapsed((long) this.delaySetting.asDouble())) {
                    this.correctionBurstCount++;
                } else {
                    this.correctionBurstCount = 0;
                }
                this.timer.reset();
            }
        }
    }

    private boolean isCorrectionBurstActive() {
        return this.correctionBurstCount > 5
                && !this.timer.hasElapsed((long) this.delaySetting.asDouble());
    }

    public UnstuckModule() {
        super("Unstuck", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));
        this.automatedSetting = new Setting("Automated", this, true);
    }

    @Subscribe(mc = MoveEvent.class, priority = SubscriberPriority.HIGHEST)
    public void onMove(MoveEvent event) {
        if (this.isCorrectionBurstActive() || !this.automatedSetting.asBoolean()) {
            LocalPlayer player = minecraft.player;
            event.setX(player.motionX = 0.0);
            event.setY(player.motionY = 0.0);
            event.setZ(player.motionZ = 0.0);
        }
    }
}
