package remix.module.combat;

import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** Exposes the reach distance setting read by the targeting/controller hooks; it has no module event handler or additional state. */
public final class ExtendedReachModule extends ModuleBase {

    public final Setting reachSetting = new Setting("Reach", this, 5.0, 3.0, 6.0, 0.05);

    public ExtendedReachModule() {
        super("ExtendedReach", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));
        this.setName("Extended Reach");
    }


}
