package net.minecraft.village;

import net.minecraft.entity.EntityLivingSpawnPlacementType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class VillageSiege {
    private int dD;
    private boolean b;
    private int ef;
    private int Ao;
    private World worldObj;
    private int mu;
    private int c = -1;
    private Village theVillage;
    private int sR;

    private boolean spawnZombie() {
        boolean flag1 = Village.dI();
        List playerEntities = this.worldObj.playerEntities;
        Iterator iterator = playerEntities.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            PlayerEntity playerEntity = (PlayerEntity) iterator.next();
            if (!playerEntity.isSpectator()) {
                this.theVillage =
                        this.worldObj
                                .getVillageCollection()
                                .getNearestVillage(new BlockPos(playerEntity), 1);
                if (this.theVillage != null
                        && this.theVillage.getNumVillageDoors() >= 10
                        && this.theVillage.getTicksSinceLastDoorAdding() >= 20
                        && this.theVillage.getNumVillagers() >= 20) {
                    BlockPos blockPos = this.theVillage.getCenter();
                    float villageRadius = this.theVillage.getVillageRadius();
                    byte b0 = 0;
                    int i = 0;

                    while (i < 10) {
                        float f1 = this.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F;
                        this.Ao =
                                blockPos.getX() + (int) (MathHelper.cos(f1) * villageRadius * 0.9);
                        this.sR = blockPos.getY();
                        this.mu =
                                blockPos.getZ() + (int) (MathHelper.sin(f1) * villageRadius * 0.9);
                        b0 = 0;

                        for (java.util.Iterator<?> __recoveryIterator171_2056 =
                                        (this.worldObj.getVillageCollection().getVillageList())
                                                .iterator();
                                (__recoveryIterator171_2056).hasNext(); ) {
                            Village village = (Village) (__recoveryIterator171_2056).next();
                            if (village != this.theVillage
                                    && village.func_179866_a(
                                            new BlockPos(this.Ao, this.sR, this.mu))) {
                                b0 = 1;
                                if (flag) {
                                    break;
                                }
                            }

                            if (!flag) {
                                break;
                            }
                        }

                        if (b0 == 0) {
                            break;
                        }

                        i++;
                        if (!flag) {
                            break;
                        }
                    }

                    if (b0 != 0) {
                        return false;
                    }

                    Vec3 vec3 = this.n(new BlockPos(this.Ao, this.sR, this.mu));
                    if (vec3 != null) {
                        this.ef = 0;
                        this.dD = 20;
                        return true;
                    }
                }
            }

            if (!flag) {
                break;
            }
        }

        return false;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private Vec3 n(BlockPos blockPos) {
        boolean flag1 = Village.dI();
        int i = 0;
        boolean flag = flag1;

        while (i < 10) {
            BlockPos blockPosValue =
                    blockPos.add(
                            this.worldObj.rand.nextInt(16) - 8,
                            this.worldObj.rand.nextInt(6) - 3,
                            this.worldObj.rand.nextInt(16) - 8);
            if (this.theVillage.func_179866_a(blockPosValue)
                    && SpawnerAnimals.canCreatureTypeSpawnAtLocation(
                            EntityLivingSpawnPlacementType.ON_GROUND,
                            this.worldObj,
                            blockPosValue)) {
                return new Vec3(blockPosValue.getX(), blockPosValue.getY(), blockPosValue.getZ());
            }

            i++;
            if (!flag) {
                break;
            }
        }

        return null;
    }

    public VillageSiege(World world) {
        this.worldObj = world;
    }

    public void tick() {
        boolean flag = Village.dI();
        if (this.worldObj.isDaytime()) {
            this.c = 0;
            if (flag) {
                return;
            }
        }

        if (this.c != 2) {
            if (this.c == 0) {
                float celestialAngle = this.worldObj.getCelestialAngle(0.0F);
                if (celestialAngle < 0.5 || celestialAngle > 0.501) {
                    return;
                }

                this.c = this.worldObj.rand.nextInt(10) == 0 ? 1 : 2;
                this.b = false;
                if (this.c == 2) {
                    return;
                }
            }

            if (this.c != -1) {
                if (!this.b) {
                    if (!this.spawnZombie()) {
                        return;
                    }

                    this.b = true;
                }

                if (this.ef > 0) {
                    this.ef--;
                    if (flag) {
                        return;
                    }
                }

                this.ef = 2;
                if (this.dD > 0) {
                    this.method_d87039b07bd6961907f2();
                    this.dD--;
                    if (flag) {
                        return;
                    }
                }

                this.c = 2;
            }
        }
    }

    private boolean method_d87039b07bd6961907f2() {
        Vec3 vec3 = this.n(new BlockPos(this.Ao, this.sR, this.mu));

        try {
            if (vec3 == null) {
                return false;
            }
        } catch (Exception exception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
        }

        EntityZombie entityZombie;
        try {
            entityZombie = new EntityZombie(this.worldObj);
            entityZombie.onInitialSpawn(
                    this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)),
                    (IEntityLivingData) null);
            entityZombie.setVillager(false);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        entityZombie.setLocationAndAngles(
                vec3.x, vec3.y, vec3.z, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
        this.worldObj.spawnEntityInWorld(entityZombie);
        BlockPos blockPos = this.theVillage.getCenter();
        entityZombie.setHomePosAndDistance(blockPos, this.theVillage.getVillageRadius());
        return true;
    }
}
