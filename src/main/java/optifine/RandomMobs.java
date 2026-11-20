package optifine;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class RandomMobs {
    private static String[] DEPENDANT_SUFFIXES;
    private static boolean initialized;
    private static RenderGlobal renderGlobal;

    private static boolean working;
    public static String te;
    private static Map locationProperties;
    private static Random random;

    public static String f;
    public static String h;
    public static String g;

    private static ResourceLocation getPropertyLocation(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocationValue = getMcpatcherLocation(resourceLocation);
        if (resourceLocationValue == null) {
            return null;
        }

        String text = resourceLocationValue.getResourceDomain();
        String textValue = resourceLocationValue.getResourcePath();
        String textValue2 = textValue;
        if (textValue.endsWith(".png")) {
            textValue2 = textValue.substring(0, textValue.length() - ".png".length());
        }

        String textValue3 = textValue2 + ".properties";
        ResourceLocation resourceLocationValue2 = new ResourceLocation(text, textValue3);
        if (OptiFineConfig.hasResource(resourceLocationValue2)) {
            return resourceLocationValue2;
        }

        String textValue4 = getParentPath(textValue2);
        if (textValue4 == null) {
            return null;
        }

        ResourceLocation resourceLocationValue3 =
                new ResourceLocation(text, textValue4 + ".properties");
        return OptiFineConfig.hasResource(resourceLocationValue3) ? resourceLocationValue3 : null;
    }

    public static ResourceLocation getMcpatcherLocation(ResourceLocation resourceLocation) {
        String text = resourceLocation.getResourcePath();
        if (!text.startsWith("textures/entity/")) {
            return null;
        }

        String textValue = "mcpatcher/mob/" + text.substring("textures/entity/".length());
        return new ResourceLocation(resourceLocation.getResourceDomain(), textValue);
    }

    private static void initialize() {
        int k = ReflectorMethod.cR();
        optifine.RandomMobs.renderGlobal = OptiFineConfig.getRenderGlobal();
        int i = k;
        if (optifine.RandomMobs.renderGlobal != null) {
            optifine.RandomMobs.initialized = true;
            ArrayList arrayList = new ArrayList();
            arrayList.add("bat");
            arrayList.add("blaze");
            arrayList.add("cat/black");
            arrayList.add("cat/ocelot");
            arrayList.add("cat/red");
            arrayList.add("cat/siamese");
            arrayList.add("chicken");
            arrayList.add("cow/cow");
            arrayList.add("cow/mooshroom");
            arrayList.add("creeper/creeper");
            arrayList.add("enderman/enderman");
            arrayList.add("enderman/enderman_eyes");
            arrayList.add("ghast/ghast");
            arrayList.add("ghast/ghast_shooting");
            arrayList.add("iron_golem");
            arrayList.add("pig/pig");
            arrayList.add("sheep/sheep");
            arrayList.add("sheep/sheep_fur");
            arrayList.add("silverfish");
            arrayList.add("skeleton/skeleton");
            arrayList.add("skeleton/wither_skeleton");
            arrayList.add("slime/slime");
            arrayList.add("slime/magmacube");
            arrayList.add("snowman");
            arrayList.add("spider/cave_spider");
            arrayList.add("spider/spider");
            arrayList.add("spider_eyes");
            arrayList.add("squid");
            arrayList.add("villager/villager");
            arrayList.add("villager/butcher");
            arrayList.add("villager/farmer");
            arrayList.add("villager/librarian");
            arrayList.add("villager/priest");
            arrayList.add("villager/smith");
            arrayList.add("wither/wither");
            arrayList.add("wither/wither_armor");
            arrayList.add("wither/wither_invulnerable");
            arrayList.add("wolf/wolf");
            arrayList.add("wolf/wolf_angry");
            arrayList.add("wolf/wolf_collar");
            arrayList.add("wolf/wolf_tame");
            arrayList.add("zombie_pigman");
            arrayList.add("zombie/zombie");
            arrayList.add("zombie/zombie_villager");
            int j = 0;

            while (j < arrayList.size()) {
                String text = (String) arrayList.get(j);
                String textValue = "textures/entity/" + text + ".png";
                ResourceLocation resourceLocation = new ResourceLocation(textValue);
                if (!OptiFineConfig.hasResource(resourceLocation)) {
                    OptiFineConfig.warn("Not found: " + resourceLocation);
                }

                getProperties(resourceLocation);
                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    static {
        te = "mcpatcher/mob/";
        f = ".png";
        h = "textures/entity/";
        g = ".properties";
        optifine.RandomMobs.locationProperties = new HashMap();
        optifine.RandomMobs.renderGlobal = null;
        optifine.RandomMobs.initialized = false;
        optifine.RandomMobs.random = new Random();
        optifine.RandomMobs.working = false;
        optifine.RandomMobs.DEPENDANT_SUFFIXES =
                new String[] {
                    "_armor",
                    "_eyes",
                    "_exploding",
                    "_shooting",
                    "_fur",
                    "_eyes",
                    "_invulnerable",
                    "_angry",
                    "_tame",
                    "_collar"
                };
    }

    private static RandomMobsProperties makeProperties(ResourceLocation resourceLocation) {
        String text = resourceLocation.getResourcePath();
        ResourceLocation resourceLocationValue = getPropertyLocation(resourceLocation);
        if (resourceLocationValue != null) {
            RandomMobsProperties randomMobsProperties =
                    parseProperties(resourceLocationValue, resourceLocation);
            if (randomMobsProperties != null) {
                return randomMobsProperties;
            }
        }

        ResourceLocation[] resourceLocationArray = getTextureVariants(resourceLocation);
        return new RandomMobsProperties(text, resourceLocationArray);
    }

    public static ResourceLocation getLocationIndexed(ResourceLocation resourceLocation, int i) {
        if (resourceLocation == null) {
            return null;
        }

        String text = resourceLocation.getResourcePath();
        int j = text.lastIndexOf(46);
        if (j < 0) {
            return null;
        }

        String textValue = text.substring(0, j);
        String textValue2 = text.substring(j);
        String textValue3 = textValue + i + textValue2;
        return new ResourceLocation(resourceLocation.getResourceDomain(), textValue3);
    }

    public static void entityLoaded(Entity entity, World world) {
        if (entity instanceof EntityLiving && world != null) {
            EntityLiving entityLiving = (EntityLiving) entity;
            entityLiving.spawnPosition = entityLiving.getPosition();
            entityLiving.spawnBiome = world.getBiomeGenForCoords(entityLiving.spawnPosition);
            WorldServer worldServer = OptiFineConfig.getWorldServer();
            if (worldServer != null) {
                Entity entityValue = worldServer.getEntityByID(entity.getEntityId());
                if (entityValue instanceof EntityLiving) {
                    EntityLiving entityLivingValue = (EntityLiving) entityValue;
                    UUID uuid = entityLivingValue.getUniqueId();
                    long leastSignificantBits = uuid.getLeastSignificantBits();

                    int j = (int) (leastSignificantBits & 2147483647L);
                    entityLiving.randomMobsId = j;
                }
            }
        }
    }

    private static FileNotFoundException method_fd3f0eb1dd2c178d63ce(
            FileNotFoundException filenotfoundexception) {
        return filenotfoundexception;
    }

    public static void worldChanged(World world, World worldValue) {
        int i = ReflectorMethod.f();
        if (worldValue != null) {
            List loadedEntities = worldValue.getLoadedEntities();
            int j = 0;

            while (j < loadedEntities.size()) {
                Entity entity = (Entity) loadedEntities.get(j);
                entityLoaded(entity, worldValue);
                j++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    public static ResourceLocation getTextureLocation(ResourceLocation resourceLocation) {
        if (optifine.RandomMobs.working) {
            return resourceLocation;
        }

        ResourceLocation resourceLocationValue;
        try {
            optifine.RandomMobs.working = true;
            if (!optifine.RandomMobs.initialized) {
                initialize();
            }

            if (optifine.RandomMobs.renderGlobal == null) {
                return resourceLocation;
            }

            Entity azY = optifine.RandomMobs.renderGlobal.renderedEntity;
            if (!(azY instanceof EntityLiving)) {
                return resourceLocation;
            }

            EntityLiving entityLiving = (EntityLiving) azY;
            String text = resourceLocation.getResourcePath();
            if (!text.startsWith("textures/entity/")) {
                return resourceLocation;
            }

            RandomMobsProperties properties = getProperties(resourceLocation);
            if (properties != null) {
                return properties.getTextureLocation(resourceLocation, entityLiving);
            }

            resourceLocationValue = resourceLocation;
        } finally {
            optifine.RandomMobs.working = false;
        }

        return resourceLocationValue;
    }

    private static String getParentPath(String text) {
        int k = ReflectorMethod.cR();
        int j = 0;
        int i = k;

        while (j < optifine.RandomMobs.DEPENDANT_SUFFIXES.length) {
            String textValue = optifine.RandomMobs.DEPENDANT_SUFFIXES[j];
            if (text.endsWith(textValue)) {
                return text.substring(0, text.length() - textValue.length());
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return null;
    }

    private static ResourceLocation[] getTextureVariants(ResourceLocation resourceLocation) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(resourceLocation);
        int l = ReflectorMethod.cR();
        ResourceLocation resourceLocationValue = getMcpatcherLocation(resourceLocation);
        int i = l;
        if (resourceLocationValue == null) {
            return null;
        }

        int j = 1;

        while (j < arrayList.size() + 10) {
            int k = j + 1;
            ResourceLocation resourceLocationValue2 = getLocationIndexed(resourceLocationValue, k);
            if (OptiFineConfig.hasResource(resourceLocationValue2)) {
                arrayList.add(resourceLocationValue2);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        if (arrayList.size() <= 1) {
            return null;
        }

        ResourceLocation[] resourceLocationArray =
                ((net.minecraft.util.ResourceLocation[])
                        arrayList.toArray(new ResourceLocation[arrayList.size()]));
        OptiFineConfig.dbg(
                "RandomMobs: "
                        + resourceLocation.getResourcePath()
                        + ", variants: "
                        + resourceLocationArray.length);
        return resourceLocationArray;
    }

    private static RandomMobsProperties parseProperties(
            ResourceLocation resourceLocation, ResourceLocation resourceLocationValue) {
        try {
            String text = resourceLocation.getResourcePath();
            OptiFineConfig.dbg(
                    "RandomMobs: "
                            + resourceLocationValue.getResourcePath()
                            + ", variants: "
                            + text);
            InputStream inputstream = OptiFineConfig.getResourceStream(resourceLocation);
            if (inputstream == null) {
                OptiFineConfig.warn("RandomMobs properties not found: " + text);
                return null;
            }

            Properties properties = new Properties();
            properties.load(inputstream);
            inputstream.close();
            RandomMobsProperties randomMobsProperties =
                    new RandomMobsProperties(properties, text, resourceLocationValue);

            try {
                if (false)
                    throw (java.io.FileNotFoundException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (!randomMobsProperties.isValid(text)) {
                    return null;
                }
            } catch (FileNotFoundException filenotfoundexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_fd3f0eb1dd2c178d63ce(filenotfoundexception));
            }

            return randomMobsProperties;
        } catch (FileNotFoundException filenotfoundexception1) {
            OptiFineConfig.warn(
                    "RandomMobs file not found: " + resourceLocationValue.getResourcePath());
            return null;
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            return null;
        }
    }

    private static RandomMobsProperties getProperties(ResourceLocation resourceLocation) {
        String text = resourceLocation.getResourcePath();
        RandomMobsProperties randomMobsProperties =
                (RandomMobsProperties) optifine.RandomMobs.locationProperties.get(text);
        if (randomMobsProperties == null) {
            randomMobsProperties = makeProperties(resourceLocation);
            optifine.RandomMobs.locationProperties.put(text, randomMobsProperties);
        }

        return randomMobsProperties;
    }

    public static void resetTextures() {
        optifine.RandomMobs.locationProperties.clear();
        if (OptiFineConfig.isRandomMobs()) {
            initialize();
        }
    }
}
