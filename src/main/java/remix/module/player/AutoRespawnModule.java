package remix.module.player;

import net.minecraft.client.gui.GuiGameOver;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;

/** AutoRespawn module: player events and 0 settings. */
public final class AutoRespawnModule extends ModuleBase {

    public AutoRespawnModule() {
        super("AutoRespawn", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.setName("Auto Respawn");
    }


    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (minecraft.currentScreen instanceof GuiGameOver) {
            minecraft.displayGuiScreen(null);
            minecraft.player.respawnPlayer();
        }
    }
}
