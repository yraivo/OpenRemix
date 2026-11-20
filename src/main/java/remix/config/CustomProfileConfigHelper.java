package remix.config;

import net.minecraft.entity.Entity;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.setting.SettingRegistry;

import java.io.File;
import java.io.IOException;

public final class CustomProfileConfigHelper {

    public static File profileDirectory;

    public static boolean loadProfile(String text) {
        ensureProfileDirectory();
        boolean flag1 = SettingRegistry.isLegacySingleEntryMode();
        File file1 = new File(remix.config.CustomProfileConfigHelper.profileDirectory, text + ".json");
        boolean flag = flag1;
        if (file1.exists()) {
            new CustomProfileJsonConfig(file1).load();
            return true;
        }

        if (flag) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return false;
    }

    public static void saveProfile(String text) throws IOException {
        boolean flag1 = SettingRegistry.isLegacySingleEntryMode();
        ensureProfileDirectory();
        new CustomProfileJsonConfig(new File(remix.config.CustomProfileConfigHelper.profileDirectory, text + ".json")).save();
        boolean flag = flag1;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            SettingRegistry.setLegacySingleEntryMode(!flag);
        }
    }

    static {
        remix.config.CustomProfileConfigHelper.profileDirectory = new File(((ClientServices) RemixClient.INSTANCE.getClientServices()).getGameDirectoryProvider().getOrCreateConfigDirectory(), "/Config");
        ensureProfileDirectory();
    }

    private static void ensureProfileDirectory() {
        if (!remix.config.CustomProfileConfigHelper.profileDirectory.exists()) {
            remix.config.CustomProfileConfigHelper.profileDirectory.mkdir();
            remix.config.CustomProfileConfigHelper.profileDirectory.mkdirs();
        }
    }
}
