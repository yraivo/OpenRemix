package remix.module.player;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;

/** KeepRotations module: player events and 0 settings. */
public final class KeepRotationsModule extends ModuleBase {

    public KeepRotationsModule() {
        super(
                "KeepRotations",
                new ModuleDefaults(ModuleCategory.PLAYER).visible(false));
        this.setName("Keep Rotations");
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (minecraft.player != null && minecraft.world != null) {
            if (event.getDirection() == PacketDirection.RECEIVED
                    && event.getPacket() instanceof S08PacketPlayerPosLook) {
                S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
                packet.yaw = minecraft.player.rotationYaw;
                packet.pitch = minecraft.player.rotationPitch;
            }
        }
    }


}
