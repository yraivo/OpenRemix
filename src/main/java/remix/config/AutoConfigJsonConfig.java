package remix.config;

import remix.server.ServerConfigRule;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.world.AutoConfigModule;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

public final class AutoConfigJsonConfig extends AbstractJsonConfigFile {

    public AutoConfigJsonConfig(File configFile) {
        super(configFile);
    }

    public void loadJson(JsonObject config) throws IOException {
        if (config == null) {
            return;
        }
        String profile = readString(config, "config", "");
        String serverName = readString(config, "name", "");
        String address = readString(config, "ip", "");
        int port = config.has("port") ? config.get("port").getAsInt() : 25565;
        String gameType = readString(config, "hypixel_game_type", "_");
        boolean loadInLobby = config.has("load_in_lobby") && config.get("load_in_lobby").getAsBoolean();

        RemixClient.INSTANCE.getClientServices();
        AutoConfigModule autoConfig = ClientServices.moduleManager.autoConfig;
        if (!profile.isEmpty()) {
            autoConfig.serverConfigs.put(new ServerConfigRule(serverName, address, loadInLobby, gameType, port), profile);
        }
    }

    public void loadFrom(Object object) throws IOException {
        this.loadJson((JsonObject) object);
    }

    private static String readString(JsonObject config, String key, String fallback) {
        return config.has(key) ? config.get(key).getAsString() : fallback;
    }

    public void saveTo(java.util.List output) throws IOException {
        RemixClient.INSTANCE.getClientServices();
        AccountsTextConfig.getLegacyAccountConfigMarker();
        AutoConfigModule autoConfig = ClientServices.moduleManager.autoConfig;
        for (Entry<ServerConfigRule, String> entry : autoConfig.serverConfigs.entrySet()) {
            ServerConfigRule rule = entry.getKey();
            JsonObject ruleJson = new JsonObject();
            ruleJson.addProperty("config", entry.getValue());
            ruleJson.addProperty("name", rule.getName());
            ruleJson.addProperty("ip", rule.getIp());
            ruleJson.addProperty("port", rule.getPort());
            ruleJson.addProperty("hypixel_game_type", rule.getHypixelGameType());
            ruleJson.addProperty("load_in_lobby", rule.isLoadInLobby());
            output.add(ruleJson);
        }
    }
}
