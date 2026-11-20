package remix.config;

import net.minecraft.entity.Entity;
import remix.ui.account.AltServiceSwitcher;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thealtening.api.TheAltening;

import java.io.File;
import java.io.IOException;

public final class ClientInfoJsonConfig extends AbstractJsonConfigFile {

    public void loadFrom(Object object) throws IOException {
        this.loadJson((JsonObject) object);
    }

    public void loadJson(JsonObject config) throws IOException {
        if (config == null) {
            return;
        }

        String prefix = readString(config, "Prefix");
        if (prefix != null) {
            ((ClientServices) RemixClient.INSTANCE.getClientServices()).commandPrefix = prefix;
        }

        String apiToken = readString(config, "TheAlteningToken");
        if (apiToken != null) {
            RemixClient.INSTANCE.getClientServices();
            AltServiceSwitcher service = ClientServices.altServiceSwitcher;
            // Loading local settings does not need to send the token to the license endpoint.
            service.setTheAltening(new TheAltening(apiToken));
            service.setApiToken(apiToken);
        }

        String customName = readString(config, "CustomName");
        if (customName != null) {
            RemixClient.INSTANCE.getClientServices();
            ModuleManager modules = ClientServices.moduleManager;
            if (modules != null && !isPreviousDefaultWatermark(customName)) {
                modules.hud.watermarkRenderer.watermarkText = customName;
            }
        }
    }

    private static String readString(JsonObject config, String key) {
        JsonElement value = config.get(key);
        return value == null || value.isJsonNull() ? null : value.getAsString();
    }

    private static boolean isPreviousDefaultWatermark(String name) {
        String prefix = ClientServices.CLIENT_NAME.charAt(0)
                + "\u00a77" + ClientServices.CLIENT_NAME.substring(1) + " v";
        if (!name.startsWith(prefix)) {
            return false;
        }
        switch (name.substring(prefix.length())) {
            case "1.6.5":
            case "1.6.6":
            case "1.6.7":
            case "1.6.8":
            case "1.6.9":
            case "1.7.0":
            case "1.7.1":
                return true;
            default:
                return false;
        }
    }

    public void saveTo(java.util.List entries) throws IOException {
        JsonObject config = new JsonObject();
        AccountsTextConfig.getLegacyAccountConfigMarker();
        ClientServices services = (ClientServices) RemixClient.INSTANCE.getClientServices();
        config.addProperty("Prefix", services.commandPrefix);
        ModuleManager modules = ClientServices.moduleManager;
        if (modules != null) {
            config.addProperty("CustomName", modules.hud.watermarkRenderer.watermarkText);
        }
        config.addProperty("TheAlteningToken", ClientServices.altServiceSwitcher.getApiToken());
        entries.add(config);
        // Keep the original interaction with account-config initialization.
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AccountsTextConfig.setLegacyAccountConfigMarker(new int[2]);
        }
    }

    public ClientInfoJsonConfig(File file1) {
        super(file1);
    }
}
