package remix.module.movement;

import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;

/** Jumps when predicted movement leaves grounded collision bounds. moduleName is the statically decoded constructor label. */
public final class ParkourModule extends ModuleBase {

    private static String moduleName;

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (minecraft.world != null
                && minecraft.player.onGround
                && !ClientServices.moduleManager.flight.isEnabled()
                && minecraft
                        .world
                        .getCollidingBoundingBoxes(
                                minecraft.player,
                                minecraft.player.boundingBox.addCoord(
                                        minecraft.player.motionX,
                                        minecraft.player.motionY,
                                        minecraft.player.motionZ))
                        .isEmpty()) {
            minecraft.player.jump();
        }
    }

    // region String decoding support

    // endregion

    public ParkourModule() {
        super(moduleName, ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));
    }

    // region Static initialization and string tables
    static {
        moduleName = "Parkour";
    }
    // endregion

    // region String decoding support

    // endregion
}
