package optifine;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PlayerConfigurationReceiver implements IFileDownloadListener {

    private String player = null;

    public void fileDownloadFinished(String text, byte[] abyte, Throwable throwable) {
        if (abyte != null) {
            try {
                String textValue = new String(abyte, "ASCII");
                JsonParser jsonparser = new JsonParser();
                JsonElement jsonelement = jsonparser.parse(textValue);
                PlayerConfigurationParser playerConfigurationParser =
                        new PlayerConfigurationParser(this.player);
                PlayerConfiguration playerConfiguration =
                        playerConfigurationParser.parsePlayerConfiguration(jsonelement);

                try {
                    if (playerConfiguration != null) {
                        playerConfiguration.setInitialized(true);
                        PlayerConfigurations.setPlayerConfiguration(
                                this.player, playerConfiguration);
                    }
                } catch (Exception exception) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
                }
            } catch (Exception exception1) {
                OptiFineConfig.dbg(
                        "Error parsing configuration: "
                                + text
                                + ", "
                                + exception1.getClass().getName()
                                + ": "
                                + exception1.getMessage());
            }
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public PlayerConfigurationReceiver(String text) {
        this.player = text;
    }


}
