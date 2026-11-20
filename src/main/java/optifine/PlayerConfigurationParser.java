package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerConfigurationParser {
    public static String b;
    public static String c;
    private String player = null;

    public static String d;

    public PlayerConfigurationParser(String text) {
        this.player = text;
    }

    private BufferedImage downloadTextureImage(String text) {
        String textValue = "http://s.optifine.net/" + text;

        try {
            byte[] abyte = HttpPipeline.get(textValue, MinecraftClient.getMinecraft().getProxy());
            return ImageIO.read(new ByteArrayInputStream(abyte));
        } catch (IOException ioexception) {
            OptiFineConfig.warn(
                    "Error loading item texture "
                            + text
                            + ": "
                            + ioexception.getClass().getName()
                            + ": "
                            + ioexception.getMessage());
            return null;
        }
    }

    static {
        b = "items";
        d = "active";
        c = "type";
    }

    private PlayerItemModel downloadModel(String text) {
        String textValue = "http://s.optifine.net/" + text;

        try {
            byte[] abyte = HttpPipeline.get(textValue, MinecraftClient.getMinecraft().getProxy());
            String textValue2 = new String(abyte, "ASCII");
            JsonParser jsonparser = new JsonParser();
            JsonObject jsonobject = (JsonObject) jsonparser.parse(textValue2);
            new PlayerItemParser();
            return PlayerItemParser.parseItemModel(jsonobject);
        } catch (Exception exception) {
            OptiFineConfig.warn(
                    "Error loading item model "
                            + text
                            + ": "
                            + exception.getClass().getName()
                            + ": "
                            + exception.getMessage());
            return null;
        }
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    public PlayerConfiguration parsePlayerConfiguration(JsonElement jsonelement) {
        int i = ReflectorMethod.f();

        try {
            if (jsonelement == null) {
                throw new JsonParseException("JSON object is null, player: " + this.player);
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }

        JsonObject jsonobject = (JsonObject) jsonelement;
        PlayerConfiguration playerConfiguration = new PlayerConfiguration();
        JsonArray jsonarray = (JsonArray) jsonobject.get("items");
        if (jsonarray != null) {
            int j = 0;

            while (j < jsonarray.size()) {
                JsonObject jsonobject1 = (JsonObject) jsonarray.get(j);
                boolean flag = Json.getBoolean(jsonobject1, "active", true);
                label75:
                if (flag) {
                    String text = Json.getString(jsonobject1, "type");

                    try {
                        if (text == null) {
                            OptiFineConfig.warn("Item type is null, player: " + this.player);
                            if (i == 0) {
                                break label75;
                            }
                        }
                    } catch (JsonParseException jsonparseexception3) {
                        throw method_a7757c6047e9e5ecea97(jsonparseexception3);
                    }

                    String textValue = Json.getString(jsonobject1, "model");
                    if (textValue == null) {
                        textValue = "items/" + text + "/model.cfg";
                    }

                    PlayerItemModel playerItemModel = this.downloadModel(textValue);

                    {
                        boolean runGuardedCode;
                        try {
                            if (playerItemModel == null) {
                                break label75;
                            }

                            runGuardedCode = !(playerItemModel.isUsePlayerTexture());
                        } catch (JsonParseException jsonparseexception2) {
                            throw method_a7757c6047e9e5ecea97(jsonparseexception2);
                        }
                        if (runGuardedCode) {

                            String textValue2 = Json.getString(jsonobject1, "texture");
                            if (textValue2 == null) {
                                textValue2 = "items/" + text + "/users/" + this.player + ".png";
                            }

                            BufferedImage bufferedimage = this.downloadTextureImage(textValue2);

                            try {
                                if (bufferedimage == null && i == 0) {
                                    break label75;
                                }
                            } catch (JsonParseException jsonparseexception1) {
                                throw method_a7757c6047e9e5ecea97(jsonparseexception1);
                            }

                            playerItemModel.setTextureImage(bufferedimage);
                            ResourceLocation resourceLocation =
                                    new ResourceLocation("optifine.net", textValue2);
                            playerItemModel.setTextureLocation(resourceLocation);
                        }
                    }

                    playerConfiguration.addPlayerItemModel(playerItemModel);
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }

        return playerConfiguration;
    }
}
