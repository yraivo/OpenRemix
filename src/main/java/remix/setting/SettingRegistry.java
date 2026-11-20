package remix.setting;

import remix.module.ModuleMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class SettingRegistry {
    private static boolean legacySingleEntryMode;
    private final List<Setting> list = new ArrayList<>();
    private static final Map<ModuleMetadata, List<Setting>> settingsByModuleCache = new WeakHashMap<>();

    public List<Setting> getSettingsByModule(ModuleMetadata module) {
        boolean scanAllSettings = shouldScanAllEntries();
        if (settingsByModuleCache.containsKey(module)) {
            return settingsByModuleCache.get(module);
        }
        List<Setting> matches = new ArrayList<>();
        for (int index = 0, size = this.list.size(); index < size; index++) {
            Setting setting = this.list.get(index);
            if (setting.getOwner().equals(module)) {
                matches.add(setting);
            }
            if (!scanAllSettings) {
                break;
            }
        }
        settingsByModuleCache.put(module, matches);
        return matches;
    }

    public Setting getSettingByName(ModuleMetadata module, String name) {
        List<?> settings = module.getSettings();
        boolean scanAllSettings = shouldScanAllEntries();
        for (int index = 0, size = settings.size(); index < size; index++) {
            Setting setting = (Setting) settings.get(index);
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
            if (!scanAllSettings) {
                break;
            }
        }
        return null;
    }

    public List<Setting> getList() {
        return this.list;
    }

    public static void setLegacySingleEntryMode(boolean flag) {
        remix.setting.SettingRegistry.legacySingleEntryMode = flag;
    }

    public static boolean shouldScanAllEntries() {
        boolean flag = isLegacySingleEntryMode();
        return !flag;
    }

    public static boolean isLegacySingleEntryMode() {
        return remix.setting.SettingRegistry.legacySingleEntryMode;
    }

    public void registerSetting(Setting setting) {
        this.list.add(setting);
    }

    static {
        setLegacySingleEntryMode(false);
    }

    public Setting findSetting(ModuleMetadata module, String name) {
        boolean scanAllSettings = shouldScanAllEntries();
        for (int index = 0, size = this.list.size(); index < size; index++) {
            Setting setting = this.list.get(index);
            if (setting.getOwner().equals(module) && setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
            if (!scanAllSettings) {
                break;
            }
        }
        return null;
    }

    private SettingRegistry() {}

    public static SettingRegistry create() {
        return new SettingRegistry();
    }
}
