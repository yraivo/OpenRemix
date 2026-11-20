package remix.module.render;

import remix.event.annotation.Subscribe;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.util.render.WorldBrightnessOverride;

/** FullBright changes client rendering without replacing game potion effects. */
public final class FullBrightModule extends ModuleBase {
    private final Setting<FullBrightMode> modeSetting =
            new Setting("Mode", this, FullBrightMode.WORLD);
    private final WorldBrightnessOverride brightnessOverride = new WorldBrightnessOverride();

    public FullBrightModule() {
        super("FullBright", new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.setName("Full Bright");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.clearWorldState();
    }

    public void clearWorldState() {
        this.brightnessOverride.release();
    }

    public boolean isNightVisionActive() {
        return this.isEnabled()
                && this.modeSetting.getValue() == FullBrightMode.NIGHT_VISION
                && minecraft.player != null
                && minecraft.world != null;
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (minecraft.world == null
                || minecraft.player == null
                || this.modeSetting.getValue() != FullBrightMode.WORLD) {
            this.clearWorldState();
            return;
        }
        this.brightnessOverride.apply(minecraft.world.provider);
    }
}
