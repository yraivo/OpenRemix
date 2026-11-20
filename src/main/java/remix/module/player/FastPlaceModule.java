package remix.module.player;

import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** FastPlace module: player events and 1 settings. */
public final class FastPlaceModule extends ModuleBase {

    private Setting delaySetting = new Setting("Delay", this, 0.0, 0.0, 6.0, 1.0);

    public FastPlaceModule() {
        super("FastPlace", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.setName("Fast Place");
    }


    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        int delay = (int) this.delaySetting.asDouble();
        if (minecraft.rightClickDelayTimer > delay) {
            minecraft.rightClickDelayTimer = delay;
        }
    }
}
