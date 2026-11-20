package remix.module.movement;

import net.minecraft.client.entity.LocalPlayer;
import remix.event.MotionPhase;
import remix.util.movement.MovementUtils;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.impl.SprintStateEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** Uses one shouldSprint predicate for player state, sprint events and optional movement multiplication; respects food, collision, item-use and sneak conditions. */
public final class SprintModule extends ModuleBase {
    public Setting multiJumpSetting;

    public Setting keepSprintSetting;

    public final Setting multiplierSetting = new Setting("Multiplier", this, 1.0, 1.0, 3.0, 0.2);
    private Setting multiSprintSetting;

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        if (event.getPhase() == MotionPhase.PRE
                && this.shouldSprint()
                && !minecraft.player.isWet()
                && ClientServices.moduleManager.freeCam.isDisabled()
                && ClientServices.moduleManager.flight.isDisabled()
                && ClientServices.moduleManager.speed.isDisabled()) {
            double multiplier = this.multiplierSetting.asDouble();
            event.setX(event.getX() * multiplier);
            event.setZ(event.getZ() * multiplier);
        }
    }


    public SprintModule() {
        super(
                "Sprint",
                new ModuleDefaults(ModuleCategory.MOVEMENT).visible(false));
        this.multiJumpSetting = new Setting("MultiJump", this, true);
        this.multiSprintSetting = new Setting("MultiSprint", this, true);
        this.keepSprintSetting = new Setting("KeepSprint", this, false);
    }

    private boolean shouldSprint() {
        LocalPlayer player = minecraft.player;
        NoSlowDownModule noSlowDown = ClientServices.moduleManager.noSlowDown;
        return player == null
                ? false
                : !player.collidedHorizontally
                        && player.getFoodStats().getFoodLevel() > 5
                        && (!player.isUsingItem() || noSlowDown != null && noSlowDown.isEnabled())
                        && (player.movementInput != null && player.movementInput.moveForward > 0.0F
                                || this.multiSprintSetting.asBoolean() && MovementUtils.isMoving())
                        && !player.isSneaking();
    }

    @Subscribe(mc = SprintStateEvent.class)
    public void onSprintState(SprintStateEvent event) {
        event.setSprinting(this.shouldSprint());
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        minecraft.player.setSprinting(this.shouldSprint());
    }
}
