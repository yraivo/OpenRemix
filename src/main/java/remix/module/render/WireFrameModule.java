package remix.module.render;

import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;

/** WireFrame module: render events and 0 settings. */
public final class WireFrameModule extends ModuleBase {

    public WireFrameModule() {
        super(
                "WireFrame",
                new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.setName("Wire Frame");
    }


}
