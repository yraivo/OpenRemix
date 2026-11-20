package net.minecraft.entity;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
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

import com.google.common.collect.Maps;

import java.util.HashMap;

public class EntitySpawnPlacementRegistry {
    private static final HashMap ENTITY_PLACEMENTS = Maps.newHashMap();

    static {
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(BatEntity.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityChicken.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityCow.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityHorse.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityMooshroom.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityOcelot.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityPig.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityRabbit.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntitySheep.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntitySnowman.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(SquidEntity.class, EntityLivingSpawnPlacementType.IN_WATER);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityIronGolem.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityWolf.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityVillager.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityDragon.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityWither.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityBlaze.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityCaveSpider.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityCreeper.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityEnderman.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityEndermite.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityGhast.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityGiantZombie.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityGuardian.class, EntityLivingSpawnPlacementType.IN_WATER);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityMagmaCube.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityPigZombie.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntitySilverfish.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntitySkeleton.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(SlimeEntity.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntitySpider.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityWitch.class, EntityLivingSpawnPlacementType.ON_GROUND);
        net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.put(EntityZombie.class, EntityLivingSpawnPlacementType.ON_GROUND);
    }

    public static EntityLivingSpawnPlacementType getPlacementForEntity(Class oclass) {
        return (EntityLivingSpawnPlacementType) net.minecraft.entity.EntitySpawnPlacementRegistry.ENTITY_PLACEMENTS.get(oclass);
    }
}
