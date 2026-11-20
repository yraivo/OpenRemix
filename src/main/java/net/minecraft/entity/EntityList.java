package net.minecraft.entity;

import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartEnumMinecartType;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityList {

    public static Map entityEggs;
    private static Map classToStringMapping;
    private static Map stringToClassMapping;
    private static Logger logger;

    private static Map idToClassMapping;
    private static Map stringToIDMapping;
    private static Map classToIDMapping;

    public static String getEntityString(Entity entity) {
        return (String) net.minecraft.entity.EntityList.classToStringMapping.get(entity.getClass());
    }

    public static int getIDFromString(String text) {
        Integer integer = (Integer) net.minecraft.entity.EntityList.stringToIDMapping.get(text);
        return integer == null ? 90 : integer;
    }

    public static int getEntityID(Entity entity) {
        Integer integer = (Integer) net.minecraft.entity.EntityList.classToIDMapping.get(entity.getClass());
        return integer == null ? 0 : integer;
    }

    public static List getEntityNameList() {
        Set set = net.minecraft.entity.EntityList.stringToClassMapping.keySet();
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        ArrayList arrayList = Lists.newArrayList();
        Iterator iterator = set.iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            Class oclass = (Class) net.minecraft.entity.EntityList.stringToClassMapping.get(text);
            if ((oclass.getModifiers() & 1024) != 1024) {
                arrayList.add(text);
            }

            if (astring != null) {
                break;
            }
        }

        arrayList.add("LightningBolt");
        return arrayList;
    }

    public static Entity createEntityFromNBT(CompoundTag compoundTag, World world) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Entity entity = null;
        String[] astring = astring1;
        if ("Minecart".equals(compoundTag.getString("id"))) {
            compoundTag.setString(
                    "id",
                    EntityMinecartEnumMinecartType.byNetworkID(compoundTag.getInteger("Type"))
                            .getName());
            compoundTag.removeTag("Type");
        }

        try {
            Class oclass =
                    (Class) net.minecraft.entity.EntityList.stringToClassMapping.get(compoundTag.getString("id"));
            if (oclass != null) {
                entity = (Entity) oclass.getConstructor(World.class).newInstance(world);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (entity != null) {
            entity.readFromNBT(compoundTag);
            if (astring == null) {
                return entity;
            }
        }

        net.minecraft.entity.EntityList.logger.warn("Skipping Entity with id " + compoundTag.getString("id"));
        return entity;
    }

    public static Class getClassFromID(int i) {
        return (Class) net.minecraft.entity.EntityList.idToClassMapping.get(i);
    }

    public static Entity createEntityByName(String text, World world) {
        Entity entity = null;

        try {
            Class oclass = (Class) net.minecraft.entity.EntityList.stringToClassMapping.get(text);
            if (oclass != null) {
                entity = (Entity) oclass.getConstructor(World.class).newInstance(world);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return entity;
    }

    private static void addMapping(Class oclass, String text, int i, int j, int k) {
        addMapping(oclass, text, i);
        net.minecraft.entity.EntityList.entityEggs.put(i, new EntityListEntityEggInfo(i, j, k));
    }

    public static void method_8d9b536bcdc9991c48fb() {}

    public static String getStringFromID(int i) {
        return (String) net.minecraft.entity.EntityList.classToStringMapping.get(getClassFromID(i));
    }

    private static void addMapping(Class oclass, String text, int i) {
        if (net.minecraft.entity.EntityList.stringToClassMapping.containsKey(text)) {
            throw new IllegalArgumentException("ID is already registered: " + text);
        }

        if (net.minecraft.entity.EntityList.idToClassMapping.containsKey(i)) {
            throw new IllegalArgumentException("ID is already registered: " + i);
        }

        if (i == 0) {
            throw new IllegalArgumentException("Cannot register to reserved id: " + i);
        }

        if (oclass == null) {
            throw new IllegalArgumentException("Cannot register null clazz for id: " + i);
        }

        net.minecraft.entity.EntityList.stringToClassMapping.put(text, oclass);
        net.minecraft.entity.EntityList.classToStringMapping.put(oclass, text);
        net.minecraft.entity.EntityList.idToClassMapping.put(i, oclass);
        net.minecraft.entity.EntityList.classToIDMapping.put(oclass, i);
        net.minecraft.entity.EntityList.stringToIDMapping.put(text, i);
    }

    public static boolean isStringEntityName(Entity entity, String text) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        String textValue = getEntityString(entity);
        String[] astring = astring1;
        if (textValue == null && entity instanceof PlayerEntity) {
            textValue = "Player";
            if (astring == null) {
                return text.equals(textValue);
            }
        }

        if (textValue == null && entity instanceof EntityLightningBolt) {
            textValue = "LightningBolt";
        }

        return text.equals(textValue);
    }

    static {
        net.minecraft.entity.EntityList.logger = LogManager.getLogger();
        net.minecraft.entity.EntityList.stringToClassMapping = Maps.newHashMap();
        net.minecraft.entity.EntityList.classToStringMapping = Maps.newHashMap();
        net.minecraft.entity.EntityList.idToClassMapping = Maps.newHashMap();
        net.minecraft.entity.EntityList.classToIDMapping = Maps.newHashMap();
        net.minecraft.entity.EntityList.stringToIDMapping = Maps.newHashMap();
        net.minecraft.entity.EntityList.entityEggs = Maps.newLinkedHashMap();
        addMapping(EntityItem.class, "Item", 1);
        addMapping(EntityXPOrb.class, "XPOrb", 2);
        addMapping(EntityLeashKnot.class, "LeashKnot", 8);
        addMapping(EntityPainting.class, "Painting", 9);
        addMapping(EntityArrow.class, "Arrow", 10);
        addMapping(EntitySnowball.class, "Snowball", 11);
        addMapping(EntityLargeFireball.class, "Fireball", 12);
        addMapping(EntitySmallFireball.class, "SmallFireball", 13);
        addMapping(EntityEnderPearl.class, "ThrownEnderpearl", 14);
        addMapping(EntityEnderEye.class, "EyeOfEnderSignal", 15);
        addMapping(EntityPotion.class, "ThrownPotion", 16);
        addMapping(EntityExpBottle.class, "ThrownExpBottle", 17);
        addMapping(EntityItemFrame.class, "ItemFrame", 18);
        addMapping(EntityWitherSkull.class, "WitherSkull", 19);
        addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
        addMapping(EntityFallingBlock.class, "FallingSand", 21);
        addMapping(EntityFireworkRocket.class, "FireworksRocketEntity", 22);
        addMapping(EntityArmorStand.class, "ArmorStand", 30);
        addMapping(EntityBoat.class, "Boat", 41);
        addMapping(
                EntityMinecartEmpty.class, EntityMinecartEnumMinecartType.RIDEABLE.getName(), 42);
        addMapping(EntityMinecartChest.class, EntityMinecartEnumMinecartType.CHEST.getName(), 43);
        addMapping(
                EntityMinecartFurnace.class, EntityMinecartEnumMinecartType.FURNACE.getName(), 44);
        addMapping(EntityMinecartTNT.class, EntityMinecartEnumMinecartType.TNT.getName(), 45);
        addMapping(EntityMinecartHopper.class, EntityMinecartEnumMinecartType.HOPPER.getName(), 46);
        addMapping(
                EntityMinecartMobSpawner.class,
                EntityMinecartEnumMinecartType.SPAWNER.getName(),
                47);
        addMapping(
                EntityMinecartCommandBlock.class,
                EntityMinecartEnumMinecartType.COMMAND_BLOCK.getName(),
                40);
        addMapping(EntityLiving.class, "Mob", 48);
        addMapping(MonsterEntity.class, "Monster", 49);
        addMapping(EntityCreeper.class, "Creeper", 50, 894731, 0);
        addMapping(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        addMapping(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        addMapping(EntityGiantZombie.class, "Giant", 53);
        addMapping(EntityZombie.class, "Zombie", 54, 44975, 7969893);
        addMapping(SlimeEntity.class, "Slime", 55, 5349438, 8306542);
        addMapping(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        addMapping(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        addMapping(EntityEnderman.class, "Enderman", 58, 1447446, 0);
        addMapping(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        addMapping(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        addMapping(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        addMapping(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
        addMapping(EntityDragon.class, "EnderDragon", 63);
        addMapping(EntityWither.class, "WitherBoss", 64);
        addMapping(BatEntity.class, "Bat", 65, 4996656, 986895);
        addMapping(EntityWitch.class, "Witch", 66, 3407872, 5349438);
        addMapping(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
        addMapping(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
        addMapping(EntityPig.class, "Pig", 90, 15771042, 14377823);
        addMapping(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);
        addMapping(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        addMapping(SquidEntity.class, "Squid", 94, 2243405, 7375001);
        addMapping(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
        addMapping(EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
        addMapping(EntitySnowman.class, "SnowMan", 97);
        addMapping(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
        addMapping(EntityIronGolem.class, "VillagerGolem", 99);
        addMapping(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
        addMapping(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
        addMapping(EntityVillager.class, "Villager", 120, 5651507, 12422002);
        addMapping(EntityEnderCrystal.class, "EnderCrystal", 200);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static boolean isStringValidEntityName(String text) {
        return "Player".equals(text) || getEntityNameList().contains(text);
    }

    public static Entity createEntityByID(int i, World world) {
        Entity entity = null;

        try {
            Class oclass = getClassFromID(i);
            if (oclass != null) {
                entity = (Entity) oclass.getConstructor(World.class).newInstance(world);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (entity == null) {
            net.minecraft.entity.EntityList.logger.warn("Skipping Entity with id " + i);
        }

        return entity;
    }
}
