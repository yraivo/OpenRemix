package optifine;

import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class CustomSky {

    private static CustomSkyLayer[][] worldSkyLayers;

    public static boolean hasSkyLayers(World world) {
        if (optifine.CustomSky.worldSkyLayers == null) {
            return false;
        } else if (OptiFineConfig.getGameSettings().renderDistanceChunks < 8) {
            return false;
        } else {
            int dimensionId = world.provider.getDimensionId();
            if (dimensionId >= 0 && dimensionId < optifine.CustomSky.worldSkyLayers.length) {
                CustomSkyLayer[] customSkyLayerArray = optifine.CustomSky.worldSkyLayers[dimensionId];
                return customSkyLayerArray == null ? false : customSkyLayerArray.length > 0;
            } else {
                return false;
            }
        }
    }

    private static FileNotFoundException method_fd3f0eb1dd2c178d63ce(
            FileNotFoundException filenotfoundexception) {
        return filenotfoundexception;
    }

    public static void renderSky(
            World world, TextureManager textureManager, float f, float f1) {
        int i = ReflectorMethod.cR();
        if (optifine.CustomSky.worldSkyLayers != null
                && OptiFineConfig.getGameSettings().renderDistanceChunks >= 8) {
            int j = world.provider.getDimensionId();
            if (j >= 0 && j < optifine.CustomSky.worldSkyLayers.length) {
                CustomSkyLayer[] customSkyLayerArray = optifine.CustomSky.worldSkyLayers[j];
                if (customSkyLayerArray != null) {
                    long k = world.getWorldTime();

                    int l = (int) (k % 24000L);
                    int i1 = 0;

                    while (i1 < customSkyLayerArray.length) {
                        CustomSkyLayer customSkyLayer = customSkyLayerArray[i1];
                        if (customSkyLayer.isActive(world, l)) {
                            customSkyLayer.render(l, f, f1);
                        }

                        i1++;
                        if (i == 0) {
                            break;
                        }
                    }

                    Blender.clearBlend(f1);
                }
            }
        }
    }

    public static void reset() {
        optifine.CustomSky.worldSkyLayers = (CustomSkyLayer[][]) null;
    }

    static {
        optifine.CustomSky.worldSkyLayers = (CustomSkyLayer[][]) null;
    }

    public static void update() {
        reset();
        if (OptiFineConfig.isCustomSky()) {
            optifine.CustomSky.worldSkyLayers = readCustomSkies();
        }
    }

    private static CustomSkyLayer[][] readCustomSkies() {
        CustomSkyLayer[][] customSkyLayerArrayArray = new CustomSkyLayer[10][0];
        int j1 = ReflectorMethod.f();
        String text = "mcpatcher/sky/world";
        int j = -1;
        int k = 0;
        int i = j1;

        while (k < customSkyLayerArrayArray.length) {
            String textValue = text + k + "/sky";
            ArrayList arrayList = new ArrayList();
            int l = 1;

            while (l < 1000) {
                String textValue2 = textValue + l + ".properties";

                label68:
                {
                    InputStream inputstream;
                    {
                        boolean runGuardedCode;
                        try {
                            ResourceLocation resourceLocation = new ResourceLocation(textValue2);
                            inputstream = OptiFineConfig.getResourceStream(resourceLocation);
                            runGuardedCode = !(inputstream != null);
                        } catch (FileNotFoundException filenotfoundexception1) {
                            break;
                        } catch (IOException ioexception1) {
                            ioexception1.printStackTrace();
                            break label68;
                        }
                        if (runGuardedCode) {

                            if (i == 0) {
                                break;
                            }
                        }
                    }

                    try {
                        Properties properties = new Properties();
                        properties.load(inputstream);
                        inputstream.close();
                        OptiFineConfig.dbg("CustomSky properties: " + textValue2);
                        String textValue3 = textValue + l + ".png";
                        CustomSkyLayer customSkyLayer = new CustomSkyLayer(properties, textValue3);
                        if (customSkyLayer.isValid(textValue2)) {
                            ResourceLocation resourceLocationValue =
                                    new ResourceLocation(customSkyLayer.source);
                            ITextureObject iTextureObject =
                                    TextureUtils.getTexture(resourceLocationValue);
                            if (iTextureObject == null) {
                                OptiFineConfig.log(
                                        "CustomSky: Texture not found: " + resourceLocationValue);
                                if (!(i == 0)) {

                                    customSkyLayer.textureId = iTextureObject.getGlTextureId();
                                    arrayList.add(customSkyLayer);
                                    inputstream.close();
                                }
                            } else {

                                customSkyLayer.textureId = iTextureObject.getGlTextureId();
                                arrayList.add(customSkyLayer);
                                inputstream.close();
                            }
                        }
                    } catch (FileNotFoundException filenotfoundexception) {
                        break;
                    } catch (IOException ioexception) {
                        ioexception.printStackTrace();
                    }
                }

                l++;
                if (i != 0) {
                    break;
                }
            }

            if (arrayList.size() > 0) {
                CustomSkyLayer[] customSkyLayerArray =
                        ((optifine.CustomSkyLayer[])
                                arrayList.toArray(new CustomSkyLayer[arrayList.size()]));
                customSkyLayerArrayArray[k] = customSkyLayerArray;
                j = k;
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        if (j < 0) {
            return (CustomSkyLayer[][]) null;
        }

        k = j + 1;
        CustomSkyLayer[][] customSkyLayerArrayArrayValue = new CustomSkyLayer[k][0];
        int i1 = 0;

        while (i1 < customSkyLayerArrayArrayValue.length) {
            customSkyLayerArrayArrayValue[i1] = customSkyLayerArrayArray[i1];
            i1++;
            if (i != 0) {
                break;
            }
        }

        return customSkyLayerArrayArrayValue;
    }
}
