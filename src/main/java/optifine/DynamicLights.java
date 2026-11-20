package optifine;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DynamicLights {
    private static final int mF = 8;
    private static final double ul = 56.25;
    private static Map<Integer, DynamicLight> mapDynamicLights = new HashMap<>();
    private static final int sR = 8;
    private static final double un = 7.5;
    private static long timeUpdateMs = 0L;
    private static final int tB = 8;
    private static final int ef = 15;
    private static final int Ao = 10;
    private static final int mu = 13;
    private static final int f = 15;

    public static double getLightLevel(BlockPos blockPos) {
        double d0 = 0.0;
        int k = ReflectorMethod.f();
        Map map = optifine.DynamicLights.mapDynamicLights;
        Map map1 = optifine.DynamicLights.mapDynamicLights;
        synchronized (optifine.DynamicLights.mapDynamicLights) {
        }
        int i = k;
        boolean flag = false /* VF: Semaphore variable */;

        try {
            flag = true;
            Collection collection = optifine.DynamicLights.mapDynamicLights.values();
            Iterator iterator = collection.iterator();

            while (iterator.hasNext() || i != 0) {
                DynamicLight dynamicLight = (DynamicLight) iterator.next();
                int j = dynamicLight.getLastLightLevel();
                if (j > 0) {
                    double d1 = dynamicLight.getLastPosX();
                    double d2 = dynamicLight.getLastPosY();
                    double d3 = dynamicLight.getLastPosZ();
                    double d4 = blockPos.getX() - d1;
                    double d5 = blockPos.getY() - d2;
                    double d6 = blockPos.getZ() - d3;
                    double d7 = d4 * d4 + d5 * d5 + d6 * d6;
                    if (dynamicLight.isUnderwater() && !OptiFineConfig.isClearWater()) {
                        j = OptiFineConfig.limit(j - 2, 0, 15);
                        d7 *= 2.0;
                    }

                    if (d7 <= 56.25) {
                        double d8 = Math.sqrt(d7);
                        double d9 = 1.0 - d8 / 7.5;
                        double d10 = d9 * j;
                        if (d10 > d0) {
                            d0 = d10;
                        }
                    }
                }

                if (i != 0) {
                    break;
                }
            }

            flag = false;
        } finally {
            if (flag) {
            }
        }

        return OptiFineConfig.limit(d0, 0.0, 15.0);
    }

    public static int getCombinedLight(BlockPos blockPos, int p171_1) {
        double lightLevel = optifine.DynamicLights.getLightLevel(blockPos);
        return optifine.DynamicLights.getCombinedLight(lightLevel, p171_1);
    }

    public static void entityRemoved(Entity entity, RenderGlobal renderGlobal) {
        java.util.Map map = optifine.DynamicLights.mapDynamicLights;
        synchronized (optifine.DynamicLights.mapDynamicLights) {
            optifine.DynamicLight dynamicLight =
                    (optifine.DynamicLight)
                            optifine.DynamicLights.mapDynamicLights.remove(
                                    optifine.IntegerCache.valueOf(
                                            entity.getEntityId()));
            if (dynamicLight != null) {
                dynamicLight.updateLitChunks(renderGlobal);
            }
        }
    }

    public static void update(RenderGlobal renderGlobal) {
        int k = ReflectorMethod.f();
        long j = System.currentTimeMillis();
        int i = k;

        if (j >= optifine.DynamicLights.timeUpdateMs + 50L) {
            optifine.DynamicLights.timeUpdateMs = j;
            Map map = optifine.DynamicLights.mapDynamicLights;
            synchronized (optifine.DynamicLights.mapDynamicLights) {
                updateMapDynamicLights(renderGlobal);
                if (optifine.DynamicLights.mapDynamicLights.size() > 0) {
                    for (DynamicLight dynamicLight :
                            optifine.DynamicLights.mapDynamicLights.values()) {
                        dynamicLight.update(renderGlobal);
                        if (i != 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public static int getLightLevel(ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }

        Item item = itemStack.getItem();
        if (item instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock) item;
            Block block = itemBlock.getBlock();
            if (block != null) {
                return block.getLightValue();
            }
        }

        return item == Items.lava_bucket
                ? Blocks.lava.getLightValue()
                : (item == Items.blaze_rod || item == Items.blaze_powder
                        ? 10
                        : (item == Items.glowstone_dust
                                ? 8
                                : (item == Items.prismarine_crystals
                                        ? 8
                                        : (item == Items.magma_cream
                                                ? 8
                                                : (item == Items.nether_star
                                                        ? Blocks.beacon.getLightValue() / 2
                                                        : 0)))));
    }

    public static int getCombinedLight(Entity entity, int p171_1) {
        double lightLevel = optifine.DynamicLights.getLightLevel(entity);
        return optifine.DynamicLights.getCombinedLight(lightLevel, p171_1);
    }

    public static ItemStack getItemStack(EntityItem entityItem) {
        return entityItem.getDataWatcher().getWatchableObjectItemStack(10);
    }

    public static int getLightLevel(Entity entity) {
        if (entity == OptiFineConfig.getMinecraft().getRenderViewEntity() && !OptiFineConfig.isDynamicHandLight()) {
            return 0;
        }

        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            if (playerEntity.isSpectator()) {
                return 0;
            }
        }

        if (entity.isBurning()) {
            return 15;
        }

        if (entity instanceof EntityFireball) {
            return 15;
        }

        if (entity instanceof EntityTNTPrimed) {
            return 15;
        }

        if (entity instanceof EntityBlaze) {
            EntityBlaze entityBlaze = (EntityBlaze) entity;
            return entityBlaze.func_70845_n() ? 15 : 10;
        }

        if (entity instanceof EntityMagmaCube) {
            EntityMagmaCube entityMagmaCube = (EntityMagmaCube) entity;
            return entityMagmaCube.squishFactor > 0.6 ? 13 : 8;
        }

        if (entity instanceof EntityCreeper) {
            EntityCreeper entityCreeper = (EntityCreeper) entity;
            if (entityCreeper.getCreeperFlashIntensity(0.0F) > 0.001) {
                return 15;
            }
        }

        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            ItemStack itemStack = livingEntity.getHeldItem();
            int i = getLightLevel(itemStack);
            ItemStack itemStackValue = livingEntity.getEquipmentInSlot(4);
            int j = getLightLevel(itemStackValue);
            return Math.max(i, j);
        } else if (entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem) entity;
            ItemStack itemStackValue2 = getItemStack(entityItem);
            return getLightLevel(itemStackValue2);
        } else {
            return 0;
        }
    }

    static {
    }

    public static int getCount() {
        java.util.Map map = optifine.DynamicLights.mapDynamicLights;
        synchronized (optifine.DynamicLights.mapDynamicLights) {
            return optifine.DynamicLights.mapDynamicLights.size();
        }
    }

    public static void entityAdded(Entity entity, RenderGlobal renderGlobal) {}

    public static int getCombinedLight(double p171_0, int p171_1) {
        if (p171_0 > 0.0) {
            int j = (int) (p171_0 * 16.0);
            int k = p171_1 & 0xFF;
            if (j > k) {
                p171_1 &= -256;
                p171_1 |= j;
            }
        }

        return p171_1;
    }

    public static void clear() {
        java.util.Map map = optifine.DynamicLights.mapDynamicLights;
        synchronized (optifine.DynamicLights.mapDynamicLights) {
            optifine.DynamicLights.mapDynamicLights.clear();
        }
    }

    public static void removeLights(RenderGlobal renderGlobal) {
        Map map = optifine.DynamicLights.mapDynamicLights;
        int j = ReflectorMethod.f();
        Map map1 = optifine.DynamicLights.mapDynamicLights;
        synchronized (optifine.DynamicLights.mapDynamicLights) {
        }
        int i = j;

        try {
            Collection collection = optifine.DynamicLights.mapDynamicLights.values();
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                DynamicLight dynamicLight = (DynamicLight) iterator.next();
                iterator.remove();
                dynamicLight.updateLitChunks(renderGlobal);
                if (i != 0) {
                    break;
                }
            }

        } finally {
        }
    }

    private static void updateMapDynamicLights(RenderGlobal renderGlobal) {
        int k = ReflectorMethod.cR();
        ClientWorld clientWorld = renderGlobal.getWorld();
        int i = k;
        if (clientWorld != null) {
            for (Entity entity : clientWorld.getLoadedEntities()) {
                label24:
                {
                    int j = getLightLevel(entity);
                    if (j > 0) {
                        Integer integer =
                                IntegerCache.valueOf(
                                        entity.getEntityId());
                        DynamicLight dynamicLight =
                                optifine.DynamicLights.mapDynamicLights.get(integer);
                        if (dynamicLight != null) {
                            break label24;
                        }

                        dynamicLight = new DynamicLight(entity);
                        optifine.DynamicLights.mapDynamicLights.put(integer, dynamicLight);
                        if (i != 0) {
                            break label24;
                        }
                    }

                    Integer integer1 =
                            IntegerCache.valueOf(
                                    entity.getEntityId());
                    DynamicLight dynamicLightValue =
                            optifine.DynamicLights.mapDynamicLights.remove(integer1);
                    if (dynamicLightValue != null) {
                        dynamicLightValue.updateLitChunks(renderGlobal);
                    }
                }

                if (i == 0) {
                    break;
                }
            }
        }
    }
}
