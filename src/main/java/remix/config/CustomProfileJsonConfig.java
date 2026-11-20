package remix.config;

import com.google.gson.JsonObject;

import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleManager;
import remix.module.world.AutoConfigModule;

import java.io.File;
import java.io.IOException;

public final class CustomProfileJsonConfig extends AbstractJsonConfigFile {

    public void loadJson(JsonObject profile) throws IOException {
        // Preserve render exclusions, AutoConfig legacy state, and toggle-before-settings order.
        boolean legacyAccountMarkerCleared =
                AccountsTextConfig.getLegacyAccountConfigMarker() == null;
        if (profile == null) {
            return;
        }
        for (ModuleBase module : ModuleManager.modules) {
            if (module.getCategory() == ModuleCategory.RENDER
                    || (module instanceof AutoConfigModule && !legacyAccountMarkerCleared)) {
                continue;
            }

            String moduleName = module.getDisplayName();
            JsonObject moduleJson = ModulesJsonConfig.readObject(profile, moduleName, moduleName);
            if (moduleJson == null) {
                continue;
            }
            try {
                Boolean toggled =
                        ModulesJsonConfig.readBoolean(
                                moduleJson, "Toggled", moduleName + ".Toggled");
                if (toggled != null && toggled != module.isEnabled()) {
                    module.toggle();
                }
                ModulesJsonConfig.readModuleSettings(
                        module, moduleJson, legacyAccountMarkerCleared);
            } catch (RuntimeException failure) {
                CONFIG_LOGGER.warn(
                        "Could not apply profile module {}: {}", moduleName, failure.toString());
            }
        }
    }

    public void saveTo(java.util.List output) throws IOException {
        AccountsTextConfig.getLegacyAccountConfigMarker();
        JsonObject profile = new JsonObject();

        for (ModuleBase module : ModuleManager.modules) {
            JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("Toggled", module.isEnabled());
            ModulesJsonConfig.writeModuleSettings(module, moduleJson);
            profile.add(module.getDisplayName(), moduleJson);
        }

        output.add(profile);
    }


    public CustomProfileJsonConfig(File file1) {
        super(file1);
    }

    public void loadFrom(Object object) throws IOException {
        this.loadJson((JsonObject) object);
    }
}
