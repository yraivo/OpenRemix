package optifine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Properties;

public class RandomMobsProperties {

    public ResourceLocation[] resourceLocations;

    public String name = null;

    public String basePath = null;
    public RandomMobsRule[] rules;

    private RangeListInt parseMinMaxHeight(Properties properties, int i) {
        String property = properties.getProperty("minHeight." + i);
        String propertyValue = properties.getProperty("maxHeight." + i);
        if (property == null && propertyValue == null) {
            return null;
        }

        int j = 0;
        if (property != null) {
            j = OptiFineConfig.parseInt(property, -1);
            if (j < 0) {
                OptiFineConfig.warn("Invalid minHeight: " + property);
                return null;
            }
        }

        int k = 256;
        if (propertyValue != null) {
            k = OptiFineConfig.parseInt(propertyValue, -1);
            if (k < 0) {
                OptiFineConfig.warn("Invalid maxHeight: " + propertyValue);
                return null;
            }
        }

        if (k < 0) {
            OptiFineConfig.warn("Invalid minHeight, maxHeight: " + property + ", " + propertyValue);
            return null;
        } else {
            RangeListInt rangeListInt = new RangeListInt();
            rangeListInt.addRange(new RangeInt(j, k));
            return rangeListInt;
        }
    }

    public RandomMobsProperties(String text, ResourceLocation[] resourceLocationArray) {
        this.resourceLocations = null;
        this.rules = null;
        ConnectedParser connectedParser = new ConnectedParser("RandomMobs");
        this.name = connectedParser.parseName(text);
        this.basePath = connectedParser.parseBasePath(text);
        this.resourceLocations = resourceLocationArray;
    }


    public RandomMobsProperties(
            Properties properties, String text, ResourceLocation resourceLocation) {
        this.resourceLocations = null;
        this.rules = null;
        ConnectedParser connectedParser = new ConnectedParser("RandomMobs");
        this.name = connectedParser.parseName(text);
        this.basePath = connectedParser.parseBasePath(text);
        this.rules = this.parseRules(properties, resourceLocation, connectedParser);
    }

    private RandomMobsRule[] parseRules(
            Properties properties,
            ResourceLocation resourceLocation,
            ConnectedParser connectedParser) {
        int i1 = ReflectorMethod.f();
        ArrayList arrayList = new ArrayList();
        int i = i1;
        int j = properties.size();
        int k = 0;

        while (k < j) {
            int l = k + 1;
            String property = properties.getProperty("skins." + l);
            if (property != null) {
                int[] aint = connectedParser.parseIntList(property);
                int[] aint1 = connectedParser.parseIntList(properties.getProperty("weights." + l));
                BiomeGenBase[] biomeGenBaseArray =
                        connectedParser.c(properties.getProperty("biomes." + l));
                RangeListInt rangeListInt =
                        connectedParser.parseRangeListInt(properties.getProperty("heights." + l));
                if (rangeListInt == null) {
                    rangeListInt = this.parseMinMaxHeight(properties, l);
                }

                RandomMobsRule randomMobsRule =
                        new RandomMobsRule(
                                resourceLocation, aint, aint1, biomeGenBaseArray, rangeListInt);
                arrayList.add(randomMobsRule);
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        return ((optifine.RandomMobsRule[]) arrayList.toArray(new RandomMobsRule[arrayList.size()]));
    }

    public ResourceLocation getTextureLocation(
            ResourceLocation resourceLocation, EntityLiving entityLiving) {
        int i = ReflectorMethod.cR();
        if (this.rules != null) {
            int j = 0;

            while (j < this.rules.length) {
                RandomMobsRule randomMobsRule = this.rules[j];
                if (randomMobsRule.matches(entityLiving)) {
                    return randomMobsRule.getTextureLocation(
                            resourceLocation, entityLiving.randomMobsId);
                }

                j++;
                if (i == 0) {
                    break;
                }
            }
        }

        if (this.resourceLocations != null) {
            int k = entityLiving.randomMobsId;
            int l = k % this.resourceLocations.length;
            return this.resourceLocations[l];
        } else {
            return resourceLocation;
        }
    }

    public boolean isValid(String text) {
        int i = ReflectorMethod.cR();
        if (this.resourceLocations == null && this.rules == null) {
            OptiFineConfig.warn("No skins specified: " + text);
            return false;
        }

        if (this.rules != null) {
            int j = 0;

            while (j < this.rules.length) {
                RandomMobsRule randomMobsRule = this.rules[j];
                if (!randomMobsRule.isValid(text)) {
                    return false;
                }

                j++;
                if (i == 0) {
                    break;
                }
            }
        }

        if (this.resourceLocations != null) {
            int k = 0;

            while (k < this.resourceLocations.length) {
                ResourceLocation resourceLocation = this.resourceLocations[k];
                if (!OptiFineConfig.hasResource(resourceLocation)) {
                    OptiFineConfig.warn("Texture not found: " + resourceLocation.getResourcePath());
                    return false;
                }

                k++;
                if (i == 0) {
                    break;
                }
            }
        }

        return true;
    }
}
