package remix.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import remix.module.ModuleBase;
import remix.module.ModuleManager;
import remix.module.render.ClickGuiModule;
import remix.setting.Setting;
import remix.setting.SettingOption;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class ModulesJsonConfig extends AbstractJsonConfigFile {

    public void loadJson(JsonObject profile) throws IOException {
        boolean legacyAccountMarkerCleared =
                AccountsTextConfig.getLegacyAccountConfigMarker() == null;
        if (profile == null) {
            return;
        }
        for (ModuleBase module : ModuleManager.modules) {
            String displayName = module.getDisplayName();
            JsonObject moduleJson = readObject(profile, displayName, displayName);
            if (moduleJson == null) {
                continue;
            }
            try {
                Boolean toggled = readBoolean(moduleJson, "Toggled", displayName + ".Toggled");
                // Keep startup/profile differences and the original enable-before-settings order.
                if (Boolean.TRUE.equals(toggled) && !module.isEnabled()) {
                    module.toggle();
                }
                Boolean visible = readBoolean(moduleJson, "Visible", displayName + ".Visible");
                if (visible != null) {
                    module.setVisible(visible);
                }
                Double bind = readNumber(moduleJson, "Bind", displayName + ".Bind");
                if (bind != null) {
                    if (bind >= Integer.MIN_VALUE
                            && bind <= Integer.MAX_VALUE
                            && bind == Math.rint(bind)) {
                        module.setKeyBind(bind.intValue());
                    } else {
                        invalid(displayName + ".Bind");
                    }
                }
                readModuleSettings(module, moduleJson, legacyAccountMarkerCleared);
            } catch (RuntimeException failure) {
                CONFIG_LOGGER.warn(
                        "Could not apply module config {}: {}", displayName, failure.toString());
            }
        }
    }

    public void saveTo(java.util.List output) throws IOException {
        boolean legacyAccountMarkerCleared =
                AccountsTextConfig.getLegacyAccountConfigMarker() == null;
        JsonObject profile = new JsonObject();
        for (ModuleBase module : ModuleManager.modules) {
            if (module instanceof ClickGuiModule && !legacyAccountMarkerCleared) {
                continue;
            }
            JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("Toggled", module.isEnabled());
            moduleJson.addProperty("Visible", module.isVisible());
            moduleJson.addProperty("Bind", module.getKeyBind());
            writeModuleSettings(module, moduleJson);
            profile.add(module.getDisplayName(), moduleJson);
        }
        output.add(profile);
    }

    static JsonObject readObject(JsonObject object, String key, String location) {
        JsonElement value = object.get(key);
        if (value == null) {
            return null;
        }
        if (!value.isJsonObject()) {
            invalid(location);
            return null;
        }
        return value.getAsJsonObject();
    }

    static Boolean readBoolean(JsonObject object, String key, String location) {
        JsonElement value = object.get(key);
        if (value == null) {
            return null;
        }
        if (value.isJsonPrimitive()) {
            String text = value.getAsString();
            if ("true".equalsIgnoreCase(text) || "false".equalsIgnoreCase(text)) {
                return Boolean.parseBoolean(text);
            }
        }
        invalid(location);
        return null;
    }

    static Double readNumber(JsonObject object, String key, String location) {
        JsonElement value = object.get(key);
        if (value == null) {
            return null;
        }
        if (value.isJsonPrimitive() && !value.getAsJsonPrimitive().isBoolean()) {
            try {
                double number = value.getAsDouble();
                if (!Double.isNaN(number) && !Double.isInfinite(number)) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
                // Report the key below and retain its current value.
            }
        }
        invalid(location);
        return null;
    }

    private static void invalid(String location) {
        CONFIG_LOGGER.warn("Ignoring invalid config value: {}", location);
    }

    static void readModuleSettings(
            ModuleBase module, JsonObject moduleJson, boolean legacyAccountMarkerCleared) {
        List<?> settings = module.getSettings();
        if (settings == null || settings.isEmpty()) {
            return;
        }
        String path = module.getDisplayName() + ".Settings";
        JsonObject settingsJson = readObject(moduleJson, "Settings", path);
        if (settingsJson == null) {
            return;
        }
        for (Object entry : settings) {
            Setting setting = (Setting) entry;
            String name = setting.getName();
            String location = path + "." + name;
            if (setting.isBoolean()) {
                Boolean value = readBoolean(settingsJson, name, location);
                if (value != null) {
                    setting.setBooleanValue(value);
                }
            } else if (setting.isNumeric()) {
                Double value = readNumber(settingsJson, name, location);
                if (value != null) {
                    setting.setNumericValue(value);
                }
            } else if (setting.isEnum()) {
                JsonElement value = settingsJson.get(name);
                if (value == null) {
                    continue;
                }
                if (value.isJsonPrimitive()
                        && value.getAsJsonPrimitive().isString()
                        && setting.findEnumValue(value.getAsString()) != null) {
                    setting.setEnumValueByName(value.getAsString());
                } else {
                    invalid(location);
                }
            }
        }
    }

    static void writeModuleSettings(ModuleBase module, JsonObject moduleJson) {
        List<?> settings = module.getSettings();
        if (settings == null || settings.isEmpty()) {
            return;
        }
        JsonObject settingsJson = new JsonObject();
        for (Object entry : settings) {
            Setting setting = (Setting) entry;
            String name = setting.getName();
            if (setting.isBoolean()) {
                settingsJson.addProperty(name, setting.asBoolean());
            } else if (setting.isNumeric()) {
                settingsJson.addProperty(name, setting.asDouble());
            } else if (setting.isEnum()) {
                settingsJson.addProperty(
                        name, ((SettingOption) setting.getValue()).getDisplayName());
            }
        }
        moduleJson.add("Settings", settingsJson);
    }

    public ModulesJsonConfig(File file1) {
        super(file1);
    }

    public void loadFrom(Object object) throws IOException {
        this.loadJson((JsonObject) object);
    }
}
