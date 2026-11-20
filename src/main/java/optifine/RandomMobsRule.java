package optifine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import remix.util.math.MathUtils2;

public class RandomMobsRule {
    private ResourceLocation baseResLoc = null;
    private RangeListInt heights;
    private ResourceLocation[] resourceLocations;
    private int[] skins = null;
    public int sumAllWeights;

    public int[] sumWeights;
    private int[] weights;

    private BiomeGenBase[] biomes;

    public ResourceLocation getTextureLocation(ResourceLocation resourceLocation, int i) {
        int j1 = ReflectorMethod.f();
        int k = 0;
        int j = j1;
        if (this.weights == null) {
            k = i % this.resourceLocations.length;
            if (j == 0) {
                return this.resourceLocations[k];
            }
        }

        int l = i % this.sumAllWeights;
        int i1 = 0;

        while (i1 < this.sumWeights.length) {
            if (this.sumWeights[i1] > l) {
                k = i1;
                if (j == 0) {
                    break;
                }
            }

            i1++;
            if (j != 0) {
                break;
            }
        }

        return this.resourceLocations[k];
    }

    public RandomMobsRule(
            ResourceLocation resourceLocation,
            int[] aint,
            int[] aint1,
            BiomeGenBase[] biomeGenBaseArray,
            RangeListInt heightsValue) {
        this.resourceLocations = null;
        this.weights = null;
        this.biomes = null;
        this.heights = null;
        this.sumWeights = null;
        this.sumAllWeights = 1;
        this.baseResLoc = resourceLocation;
        this.skins = aint;
        this.weights = aint1;
        this.biomes = biomeGenBaseArray;
        this.heights = heightsValue;
    }

    public boolean isValid(String text) {
        this.resourceLocations = new ResourceLocation[this.skins.length];
        int k1 = ReflectorMethod.f();
        ResourceLocation resourceLocation = RandomMobs.getMcpatcherLocation(this.baseResLoc);
        int i = k1;
        if (resourceLocation == null) {
            OptiFineConfig.warn("Invalid path: " + this.baseResLoc.getResourcePath());
            return false;
        }

        int j = 0;

        while (j < this.resourceLocations.length) {
            label77:
            {
                int k = this.skins[j];
                if (k <= 1) {
                    this.resourceLocations[j] = this.baseResLoc;
                    if (i == 0) {
                        break label77;
                    }
                }

                ResourceLocation resourceLocationValue =
                        RandomMobs.getLocationIndexed(resourceLocation, k);
                if (resourceLocationValue == null) {
                    OptiFineConfig.warn("Invalid path: " + this.baseResLoc.getResourcePath());
                    return false;
                }

                if (!OptiFineConfig.hasResource(resourceLocationValue)) {
                    OptiFineConfig.warn(
                            "Texture not found: " + resourceLocationValue.getResourcePath());
                    return false;
                }

                this.resourceLocations[j] = resourceLocationValue;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        if (this.weights != null) {
            if (this.weights.length > this.resourceLocations.length) {
                OptiFineConfig.warn("More weights defined than skins, trimming weights: " + text);
                int[] aint = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, 0, aint, 0, aint.length);
                this.weights = aint;
            }

            if (this.weights.length < this.resourceLocations.length) {
                OptiFineConfig.warn("Less weights defined than skins, expanding weights: " + text);
                int[] aint1 = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
                int i1 = MathUtils2.getAverage(this.weights);
                int l = this.weights.length;

                while (l < aint1.length) {
                    aint1[l] = i1;
                    l++;
                    if (i != 0) {
                        break;
                    }
                }

                this.weights = aint1;
            }

            this.sumWeights = new int[this.weights.length];
            j = 0;
            int j1 = 0;

            while (j1 < this.weights.length) {
                if (this.weights[j1] < 0) {
                    OptiFineConfig.warn("Invalid weight: " + this.weights[j1]);
                    return false;
                }

                j += this.weights[j1];
                this.sumWeights[j1] = j;
                j1++;
                if (i != 0) {
                    break;
                }
            }

            this.sumAllWeights = j;
            if (this.sumAllWeights <= 0) {
                OptiFineConfig.warn("Invalid sum of all weights: " + j);
                this.sumAllWeights = 1;
            }
        }

        return true;
    }

    public boolean matches(EntityLiving entityLiving) {
        return !Matches.biome(entityLiving.spawnBiome, this.biomes)
                ? false
                : (this.heights != null && entityLiving.spawnPosition != null
                        ? this.heights.isInRange(entityLiving.spawnPosition.getY())
                        : true);
    }


}
