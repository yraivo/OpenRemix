package remix.module.movement;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityActionAction;

import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.render.RotationModule;
import remix.setting.Setting;
import remix.util.movement.MovementUtils;

/**
 * Maintains sneak state either through a key binding or entity-action packets across PRE/POST
 * motion. serverSneaking records the packet-controlled state.
 */
public final class SneakModule extends ModuleBase {

    private boolean serverSneaking;

    private Setting<SneakMode> modeSetting = new Setting("Mode", this, SneakMode.PACKET);

    public SneakModule() {

        super("Sneak", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));
    }

    public void onDisable() {

        super.onDisable();

        RotationModule rotation = ClientServices.moduleManager.rotation;

        if (this.modeSetting.getValue() == SneakMode.KEYBIND) {

            if (!minecraft.gameSettings.keyBindSneak.pressed) {

                return;
            }

            minecraft.gameSettings.keyBindSneak.pressed = false;

            rotation.sneaking = false;

            return;
        }

        if (this.serverSneaking) {

            this.sendPacketWithoutEvent(
                    new C0BPacketEntityAction(
                            minecraft.player, C0BPacketEntityActionAction.STOP_SNEAKING));

            this.serverSneaking = false;
        }

        minecraft.gameSettings.keyBindSneak.pressed = true;

        rotation.sneaking = true;

        minecraft.gameSettings.keyBindSneak.pressed = false;

        rotation.sneaking = false;
    }

    public String getSuffix() {

        return ((SneakMode) this.modeSetting.getValue()).getDisplayName();
    }






    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {

        RotationModule rotation = ClientServices.moduleManager.rotation;

        switch (event.getPhase()) {
            case PRE:
                switch (((SneakMode) this.modeSetting.getValue())) {
                    case KEYBIND:
                        if (minecraft.gameSettings.keyBindSneak.isPressed()) {

                            break;
                        }

                        minecraft.gameSettings.keyBindSneak.pressed = true;

                        rotation.sneaking = true;

                        break;

                    case PACKET:
                        if (MovementUtils.isMoving()) {

                            this.sendPacketWithoutEvent(
                                    new C0BPacketEntityAction(
                                            minecraft.player,
                                            C0BPacketEntityActionAction.STOP_SNEAKING));

                            this.serverSneaking = false;

                            rotation.sneaking = false;
                        }

                    case SOLID:
                        this.sendPacketWithoutEvent(
                                new C0BPacketEntityAction(
                                        minecraft.player,
                                        C0BPacketEntityActionAction.START_SNEAKING));

                        this.serverSneaking = true;

                        rotation.sneaking = true;
                }

                break;

            case POST:
                switch (((SneakMode) this.modeSetting.getValue())) {
                    case KEYBIND:
                        if (minecraft.gameSettings.keyBindSneak.isPressed()) {

                            break;
                        }

                        minecraft.gameSettings.keyBindSneak.pressed = true;

                        rotation.sneaking = true;

                        break;

                    case PACKET:
                        this.sendPacketWithoutEvent(
                                new C0BPacketEntityAction(
                                        minecraft.player,
                                        C0BPacketEntityActionAction.START_SNEAKING));

                        this.serverSneaking = true;

                        rotation.sneaking = true;
                }
        }
    }
}
