package optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

import java.util.HashMap;
import java.util.Map;

public class PlayerConfigurations {

    private static Map mapConfigurations;

    static {
        optifine.PlayerConfigurations.mapConfigurations = null;
    }

    public static synchronized PlayerConfiguration getPlayerConfiguration(
            AbstractClientPlayer abstractClientPlayer) {
        String text = abstractClientPlayer.getNameClear();
        if (text == null) {
            return null;
        }

        PlayerConfiguration playerConfiguration =
                (PlayerConfiguration) getMapConfigurations().get(text);
        if (playerConfiguration == null) {
            playerConfiguration = new PlayerConfiguration();
            getMapConfigurations().put(text, playerConfiguration);
            PlayerConfigurationReceiver playerConfigurationReceiver =
                    new PlayerConfigurationReceiver(text);
            String textValue = "http://s.optifine.net/users/" + text + ".cfg";
            FileDownloadThread fileDownloadThread =
                    new FileDownloadThread(textValue, playerConfigurationReceiver);
            fileDownloadThread.start();
        }

        return playerConfiguration;
    }

    public static synchronized void setPlayerConfiguration(
            String text, PlayerConfiguration playerConfiguration) {
        getMapConfigurations().put(text, playerConfiguration);
    }

    private static Map getMapConfigurations() {
        if (optifine.PlayerConfigurations.mapConfigurations == null) {
            optifine.PlayerConfigurations.mapConfigurations = new HashMap();
        }

        return optifine.PlayerConfigurations.mapConfigurations;
    }

    public static void renderPlayerItems(
            ModelBiped modelBiped,
            AbstractClientPlayer abstractClientPlayer,
            float f,
            float f1) {
        PlayerConfiguration playerConfiguration =
                getPlayerConfiguration(abstractClientPlayer);
        if (playerConfiguration != null) {
            playerConfiguration.renderPlayerItems(
                    modelBiped, abstractClientPlayer, f, f1);
        }
    }
}
