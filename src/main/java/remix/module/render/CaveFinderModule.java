package remix.module.render;

import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;

import org.lwjgl.opengl.GL11;

/** CaveFinder module: render events and 0 settings. */
public final class CaveFinderModule extends ModuleBase {


    public void onEnable() {
        super.onEnable();
        minecraft.renderGlobal.loadRenderers();
    }

    public void cullFrontFaces() {
        GL11.glCullFace(1028);
    }

    public CaveFinderModule() {
        super(
                "CaveFinder",
                new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.setName("Cave Finder");
    }

    public void cullBackFaces() {
        GL11.glCullFace(1029);
    }

    public void onDisable() {
        super.onDisable();
        minecraft.renderGlobal.loadRenderers();
    }
}
