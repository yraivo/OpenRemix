package remix.module.render;

import remix.module.render.BlockAnimationMode;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** BlockAnimation module: render events and 4 settings. */
public final class BlockAnimationModule extends ModuleBase {
    public Setting xSetting;
    private static String[] packedTextFragments;
    public final Setting<BlockAnimationMode> modeSetting =
            new Setting("Mode", this, BlockAnimationMode.ONEDOTSEVEN);

    public Setting ySetting;

    public Setting zSetting;

    public BlockAnimationModule() {
        super(
                "BlockAnimation",
                new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.xSetting = new Setting(packedTextFragments[0], this, 0.18, -1.0, 1.0, 0.01);
        this.ySetting = new Setting(packedTextFragments[1], this, -0.05, -1.0, 1.0, 0.01);
        this.zSetting = new Setting(packedTextFragments[2], this, -0.09, -1.0, 1.0, 0.01);
        this.setName("Block Animation");
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (this.isEnabled()) {
            this.toggle();
        }
    }

    // region Static initialization and string tables
    static {
        remix.module.render.BlockAnimationModule.packedTextFragments = new String[4];
        remix.module.render.BlockAnimationModule.packedTextFragments[0] = new String("X");
        remix.module.render.BlockAnimationModule.packedTextFragments[1] = new String("Y");
        remix.module.render.BlockAnimationModule.packedTextFragments[2] = new String("Z");
    }

    // endregion

    public String getSuffix() {
        return ((BlockAnimationMode) this.modeSetting.getValue()).getDisplayName();
    }
}
