package net.minecraft.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class MobSpawnerBaseLogic {
    private String mobID;

    private int maxNearbyEntities;
    private Entity cachedEntity;

    private int minSpawnDelay;
    private MobSpawnerBaseLogicWeightedRandomMinecart randomEntity;
    private int activatingRangeFromPlayer;
    private static Entity[] Qn;
    private int spawnCount;
    private int spawnRange;
    private double mobRotation;
    private double prevMobRotation;

    private int maxSpawnDelay;
    private int spawnDelay = 20;
    private List minecartToSpawn;

    static {
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[5];
        b(aclass_b6df81f333fa31dc98ea);
    }

    public void writeToNBT(CompoundTag compoundTag) {
        compoundTag.setString("EntityId", this.getEntityNameToSpawn());
        GU();

        compoundTag.setShort("Delay", (short) this.spawnDelay);
        compoundTag.setShort("MinSpawnDelay", (short) this.minSpawnDelay);
        compoundTag.setShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
        compoundTag.setShort("SpawnCount", (short) this.spawnCount);

        compoundTag.setShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
        compoundTag.setShort("RequiredPlayerRange", (short) this.activatingRangeFromPlayer);
        compoundTag.setShort("SpawnRange", (short) this.spawnRange);
        if (this.getRandomEntity() != null) {
            compoundTag.setTag(
                    "SpawnData",
                    MobSpawnerBaseLogicWeightedRandomMinecart.access$100(this.getRandomEntity())
                            .copy());
        }

        if (this.getRandomEntity() != null || this.minecartToSpawn.size() > 0) {
            NBTTagList nbtTagList;
            {
                nbtTagList = new NBTTagList();
                if (this.minecartToSpawn.size() > 0) {
                    for (java.util.Iterator<?> __recoveryIterator171_6403 =
                                    (this.minecartToSpawn).iterator();
                            (__recoveryIterator171_6403).hasNext(); ) {
                        MobSpawnerBaseLogicWeightedRandomMinecart
                                mobSpawnerBaseLogicWeightedRandomMinecart =
                                        (MobSpawnerBaseLogicWeightedRandomMinecart)
                                                (__recoveryIterator171_6403).next();
                        nbtTagList.appendTag(mobSpawnerBaseLogicWeightedRandomMinecart.toNBT());
                    }

                    
                } else {

                    nbtTagList.appendTag(this.getRandomEntity().toNBT());
                }
            }

            compoundTag.setTag("SpawnPotentials", nbtTagList);
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new Entity[1]);
        }
    }

    private MobSpawnerBaseLogicWeightedRandomMinecart getRandomEntity() {
        return this.randomEntity;
    }

    public double getPrevMobRotation() {
        return this.prevMobRotation;
    }

    private Entity spawnNewEntity(Entity entity, boolean flag) {
        GU();

        if (this.getRandomEntity() != null) {
            CompoundTag compoundTag = new CompoundTag();
            entity.writeToNBTOptional(compoundTag);

            for (java.util.Iterator<?> __recoveryIterator171_7904 =
                            (MobSpawnerBaseLogicWeightedRandomMinecart.access$100(
                                                    this.getRandomEntity())
                                            .getKeySet())
                                    .iterator();
                    (__recoveryIterator171_7904).hasNext(); ) {
                String text = (String) (__recoveryIterator171_7904).next();
                NBTBase tag =
                        MobSpawnerBaseLogicWeightedRandomMinecart.access$100(this.getRandomEntity())
                                .getTag(text);
                compoundTag.setTag(text, tag.copy());
            }

            entity.readFromNBT(compoundTag);
            if (entity.world != null && flag) {
                entity.world.spawnEntityInWorld(entity);
            }

            Entity entityValue = entity;

            while (compoundTag.hasKey("Riding", 10)) {
                CompoundTag compoundTagValue = compoundTag.getCompoundTag("Riding");
                Entity entityValue2 =
                        EntityList.createEntityByName(
                                compoundTagValue.getString("id"), entity.world);
                if (entityValue2 != null) {
                    CompoundTag compoundTagValue2 = new CompoundTag();
                    entityValue2.writeToNBTOptional(compoundTagValue2);

                    for (java.util.Iterator<?> __recoveryIterator171_9449 =
                                    (compoundTagValue.getKeySet()).iterator();
                            (__recoveryIterator171_9449).hasNext(); ) {
                        String textValue = (String) (__recoveryIterator171_9449).next();
                        NBTBase tagValue = compoundTagValue.getTag(textValue);
                        compoundTagValue2.setTag(textValue, tagValue.copy());
                    }

                    entityValue2.readFromNBT(compoundTagValue2);
                    entityValue2.setLocationAndAngles(
                            entityValue.posX,
                            entityValue.posY,
                            entityValue.posZ,
                            entityValue.rotationYaw,
                            entityValue.rotationPitch);
                    if (entity.world != null && flag) {
                        entity.world.spawnEntityInWorld(entityValue2);
                    }

                    entityValue.mountEntity(entityValue2);
                }

                entityValue = entityValue2;
                compoundTag = compoundTagValue;
            }

            return entity;
        }

        if (entity instanceof LivingEntity && entity.world != null && flag) {
            ((EntityLiving) entity)
                    .onInitialSpawn(
                            entity.world.getDifficultyForLocation(new BlockPos(entity)),
                            (IEntityLivingData) null);
            entity.world.spawnEntityInWorld(entity);
        }

        return entity;
    }

    private String getEntityNameToSpawn() {
        if (this.getRandomEntity() == null) {
            if (this.mobID.equals("Minecart")) {
                this.mobID = "MinecartRideable";
            }

            return this.mobID;
        } else {
            return MobSpawnerBaseLogicWeightedRandomMinecart.access$000(this.getRandomEntity());
        }
    }

    public boolean setDelayToMin(int i) {
        if (i == 1 && this.getSpawnerWorld().isRemote) {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        } else {
            return false;
        }
    }

    public void updateSpawner() {
        GU();

        if (this.isActivated()) {
            BlockPos blockPos = this.getSpawnerPosition();
            if (this.getSpawnerWorld().isRemote) {
                double d1 = blockPos.getX() + this.getSpawnerWorld().rand.nextFloat();
                double d2 = blockPos.getY() + this.getSpawnerWorld().rand.nextFloat();
                double d0 = blockPos.getZ() + this.getSpawnerWorld().rand.nextFloat();
                this.getSpawnerWorld()
                        .spawnParticle(
                                EnumParticleTypes.SMOKE_NORMAL,
                                d1,
                                d2,
                                d0,
                                0.0,
                                0.0,
                                0.0,
                                new int[0]);
                this.getSpawnerWorld()
                        .spawnParticle(
                                EnumParticleTypes.FLAME, d1, d2, d0, 0.0, 0.0, 0.0, new int[0]);
                if (this.spawnDelay > 0) {
                    this.spawnDelay--;
                }

                this.prevMobRotation = this.mobRotation;
                this.mobRotation =
                        (this.mobRotation + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0;

                return;
            }

            if (this.spawnDelay == -1) {
                this.resetTimer();
            }

            if (this.spawnDelay > 0) {
                this.spawnDelay--;
                return;
            }

            byte b0 = 0;
            int i = 0;

            while (true) {
                if (i < this.spawnCount) {
                    Entity entity =
                            EntityList.createEntityByName(
                                    this.getEntityNameToSpawn(), this.getSpawnerWorld());
                    if (entity == null) {
                        return;
                    }

                    int j =
                            this.getSpawnerWorld()
                                    .getEntitiesWithinAABB(
                                            entity.getClass(),
                                            new BoundingBox(
                                                            blockPos.getX(),
                                                            blockPos.getY(),
                                                            blockPos.getZ(),
                                                            blockPos.getX() + 1,
                                                            blockPos.getY() + 1,
                                                            blockPos.getZ() + 1)
                                                    .expand(
                                                            this.spawnRange,
                                                            this.spawnRange,
                                                            this.spawnRange))
                                    .size();
                    if (j >= this.maxNearbyEntities) {
                        this.resetTimer();
                        return;
                    }

                    double d5 =
                            blockPos.getX()
                                    + (this.getSpawnerWorld().rand.nextDouble()
                                                    - this.getSpawnerWorld().rand.nextDouble())
                                            * this.spawnRange
                                    + 0.5;
                    double d3 = blockPos.getY() + this.getSpawnerWorld().rand.nextInt(3) - 1;
                    double d4 =
                            blockPos.getZ()
                                    + (this.getSpawnerWorld().rand.nextDouble()
                                                    - this.getSpawnerWorld().rand.nextDouble())
                                            * this.spawnRange
                                    + 0.5;
                    EntityLiving entityLiving =
                            entity instanceof EntityLiving ? (EntityLiving) entity : null;
                    entity.setLocationAndAngles(
                            d5, d3, d4, this.getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);
                    if (entityLiving == null
                            || entityLiving.getCanSpawnHere()
                                    && entityLiving.handleLavaMovement()) {
                        this.spawnNewEntity(entity, true);
                        this.getSpawnerWorld().playAuxSFX(2004, blockPos, 0);
                        if (entityLiving != null) {
                            entityLiving.spawnExplosionParticle();
                        }

                        b0 = 1;
                    }

                    i++;

                    continue;
                }

                if (b0 != 0) {
                    this.resetTimer();
                }
                break;
            }
        }
    }

    public void readFromNBT(CompoundTag compoundTag) {
        this.mobID = compoundTag.getString("EntityId");
        GU();

        this.spawnDelay = compoundTag.getShort("Delay");

        this.minecartToSpawn.clear();
        if (compoundTag.hasKey("SpawnPotentials", 9)) {
            NBTTagList nbtTagList = compoundTag.getTagList("SpawnPotentials", 10);
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                this.minecartToSpawn.add(
                        new MobSpawnerBaseLogicWeightedRandomMinecart(
                                this, nbtTagList.getCompoundTagAt(i)));
                i++;
            }
        }

        {
            if (compoundTag.hasKey("SpawnData", 10)) {
                this.setRandomEntity(
                        new MobSpawnerBaseLogicWeightedRandomMinecart(
                                this, compoundTag.getCompoundTag("SpawnData"), this.mobID));

                
            } else {

                this.setRandomEntity((MobSpawnerBaseLogicWeightedRandomMinecart) null);
            }
        }

        if (compoundTag.hasKey("MinSpawnDelay", 99)) {
            this.minSpawnDelay = compoundTag.getShort("MinSpawnDelay");
            this.maxSpawnDelay = compoundTag.getShort("MaxSpawnDelay");
            this.spawnCount = compoundTag.getShort("SpawnCount");
        }

        if (compoundTag.hasKey("MaxNearbyEntities", 99)) {
            this.maxNearbyEntities = compoundTag.getShort("MaxNearbyEntities");
            this.activatingRangeFromPlayer = compoundTag.getShort("RequiredPlayerRange");
        }

        if (compoundTag.hasKey("SpawnRange", 99)) {
            this.spawnRange = compoundTag.getShort("SpawnRange");
        }

        if (this.getSpawnerWorld() != null) {
            this.cachedEntity = null;
        }
    }

    public static Entity[] GU() {
        return Qn;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public abstract BlockPos getSpawnerPosition();

    public void setEntityName(String text) {
        this.mobID = text;
    }

    public Entity func_180612_a(World world) {
        if (this.cachedEntity == null) {
            Entity aDLValue = EntityList.createEntityByName(this.getEntityNameToSpawn(), world);
            if (aDLValue != null) {
                aDLValue = this.spawnNewEntity(aDLValue, false);
                this.cachedEntity = aDLValue;
            }
        }

        return this.cachedEntity;
    }

    public static void b(Entity[] entityArray) {
        Qn = entityArray;
    }

    public void setRandomEntity(MobSpawnerBaseLogicWeightedRandomMinecart bDJValue) {
        this.randomEntity = bDJValue;
    }

    public double getMobRotation() {
        return this.mobRotation;
    }

    public abstract void func_98267_a(int i);

    public MobSpawnerBaseLogic() {
        this.mobID = "Pig";
        this.minecartToSpawn = Lists.newArrayList();
        this.minSpawnDelay = 200;
        this.maxSpawnDelay = 800;
        this.spawnCount = 4;
        this.maxNearbyEntities = 6;
        this.activatingRangeFromPlayer = 16;
        this.spawnRange = 4;
    }

    private void resetTimer() {
        {
            GU();

            if (this.maxSpawnDelay <= this.minSpawnDelay) {
                this.spawnDelay = this.minSpawnDelay;

                
            } else {

                int i = this.maxSpawnDelay - this.minSpawnDelay;
                this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(i);
            }
        }

        if (this.minecartToSpawn.size() > 0) {
            this.setRandomEntity(
                    (MobSpawnerBaseLogicWeightedRandomMinecart)
                            WeightedRandom.getRandomItem(
                                    this.getSpawnerWorld().rand, this.minecartToSpawn));
        }

        this.func_98267_a(1);
    }

    public abstract World getSpawnerWorld();

    private boolean isActivated() {
        BlockPos blockPos = this.getSpawnerPosition();
        return this.getSpawnerWorld()
                .isAnyPlayerWithinRangeAt(
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 0.5,
                        blockPos.getZ() + 0.5,
                        this.activatingRangeFromPlayer);
    }
}
