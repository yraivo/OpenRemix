package remix.config;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.setting.SettingRegistry;

import java.io.File;

public final class ConfigFileManager {
    private final FluentListWrapper configFiles = FluentCollections.ax();

    public static File configDirectory;

    public static ConfigFileManager create() {
        return new ConfigFileManager();
    }

    private ConfigFileManager() {
        this.configFiles.add(
                new PanelsJsonConfig(new File(remix.config.ConfigFileManager.configDirectory, "Panels.json")));
        this.configFiles.add(
                new AutoConfigJsonConfig(new File(remix.config.ConfigFileManager.configDirectory, "AutoConfig.json")));
        this.configFiles.add(
                new AccountsTextConfig(new File(remix.config.ConfigFileManager.configDirectory, "Accounts.txt")));
        this.configFiles.add(
                new BindsTextConfig(new File(remix.config.ConfigFileManager.configDirectory, "Sayings.txt")));
        this.configFiles.add(
                new ModulesJsonConfig(new File(remix.config.ConfigFileManager.configDirectory, "Modules.json")));
        this.configFiles.add(
                new FriendsJsonConfig(new File(remix.config.ConfigFileManager.configDirectory, "Friends.json")));
        this.configFiles.add(
                new KillsultsTextConfig(new File(remix.config.ConfigFileManager.configDirectory, "Killsults.txt")));
        this.configFiles.add(
                new ProxiesTextConfig(new File(remix.config.ConfigFileManager.configDirectory, "Proxies.txt")));
        this.configFiles.add(
                new ClientInfoJsonConfig(new File(remix.config.ConfigFileManager.configDirectory, "Info.json")));
    }

    public void saveConfigs() {
        boolean visitAllConfigs = SettingRegistry.shouldScanAllEntries();
        try {
            for (Object entry : this.configFiles.asList()) {
                ((ConfigFile) entry).save();
                if (!visitAllConfigs) {
                    break;
                }
            }
        } catch (Exception failure) {
            failure.printStackTrace();
        }
    }

    public void loadConfig(Class<? extends ConfigFile> configType) {
        boolean firstEntryOnly = SettingRegistry.isLegacySingleEntryMode();
        for (Object entry : this.configFiles.asList()) {
            ConfigFile config = (ConfigFile) entry;
            if (config.getClass() == configType) {
                config.load();
                return;
            }
            if (firstEntryOnly) {
                return;
            }
        }
    }

    public void saveConfig(Class<? extends ConfigFile> configType) {
        boolean firstEntryOnly = SettingRegistry.isLegacySingleEntryMode();
        for (Object entry : this.configFiles.asList()) {
            ConfigFile config = (ConfigFile) entry;
            if (config.getClass() == configType) {
                config.save();
                return;
            }
            if (firstEntryOnly) {
                return;
            }
        }
    }

    public void loadConfigs() {
        boolean firstEntryOnly = SettingRegistry.isLegacySingleEntryMode();
        try {
            for (Object entry : this.configFiles.asList()) {
                ((ConfigFile) entry).load();
                if (firstEntryOnly) {
                    break;
                }
            }
        } catch (Exception failure) {
            failure.printStackTrace();
        }
    }

    static {
        remix.config.ConfigFileManager.configDirectory = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getGameDirectoryProvider().getOrCreateConfigDirectory();
    }
}
