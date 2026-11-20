package remix.module.render;

import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** NoRender module: render events and 4 settings. */
public final class NoRenderModule extends ModuleBase {

    public Setting signSetting;

    public final Setting hologramSetting = new Setting("Hologram", this, true);
    public final Setting particlesSetting = new Setting("Particles", this, true);
    public final Setting tntSetting = new Setting("TNT", this, true);

    public NoRenderModule() {
        super(
                "NoRender",
                new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.signSetting = new Setting("Sign", this, true);
        this.setName("No Render");
    }

    public boolean hidesSignText() {
        return this.isEnabled() && this.signSetting.asBoolean();
    }

    public boolean allowsArmorStandNameTags() {
        // In 1.7.1 the Hologram toggle allows name tags when NoRender is active.
        return !this.isEnabled() || this.hologramSetting.asBoolean();
    }
}
