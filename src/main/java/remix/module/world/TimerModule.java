package remix.module.world;

import remix.util.movement.MovementUtils;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



/** Timer module: world events and 2 settings. */

public final class TimerModule extends ModuleBase {

    private Setting timerSetting = new Setting("Timer", this, 1.5, 0.1, 10.0, 0.1);

    private Setting whileMovingSetting = new Setting("WhileMoving", this, false);






    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        boolean whileMoving = this.whileMovingSetting.asBoolean();

        boolean moved = MovementUtils.hasMoved(minecraft.player, 0.1);

        if (!whileMoving || moved) {

            minecraft.timer.timerSpeed = (float) this.timerSetting.asDouble();

        } else {

            minecraft.timer.timerSpeed = 1.0F;

        }

    }



    public String getSuffix() {

        return String.valueOf(this.timerSetting.asDouble());

    }



    public void onDisable() {

        super.onDisable();

        minecraft.timer.timerSpeed = 1.0F;

    }



    public TimerModule() {

        super("Timer", ModuleDefaults.ofCategory(ModuleCategory.WORLD));

    }

}

