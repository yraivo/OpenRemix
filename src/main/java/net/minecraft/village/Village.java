package net.minecraft.village;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Village {
    private int tickCounter;
    private BlockPos centerHelper;
    private final List villageDoorInfoList = Lists.newArrayList();
    private int villageRadius;
    private World worldObj;

    private BlockPos center;
    private TreeMap playerReputation;

    private int numVillagers;
    private int lastAddDoorTimestamp;
    private List villageAgressors;
    private int numIronGolems;
    private static String[] packedTextFragments;
    private int noBreedTicks;
    private static boolean kS;

    public boolean isPlayerReputationTooLow(String text) {
        return this.getReputationForPlayer(text) <= -15;
    }

    private void updateNumVillagers() {
        List entitiesWithinAABB =
                this.worldObj.getEntitiesWithinAABB(
                        EntityVillager.class,
                        new BoundingBox(
                                this.center.getX() - this.villageRadius,
                                this.center.getY() - 4,
                                this.center.getZ() - this.villageRadius,
                                this.center.getX() + this.villageRadius,
                                this.center.getY() + 4,
                                this.center.getZ() + this.villageRadius));
        this.numVillagers = entitiesWithinAABB.size();
        if (this.numVillagers == 0) {
            this.playerReputation.clear();
        }
    }

    private void updateVillageRadiusAndCenter() {
        boolean flag1 = dS();
        int i = this.villageDoorInfoList.size();
        boolean flag = flag1;
        if (i == 0) {
            this.center = new BlockPos(0, 0, 0);
            this.villageRadius = 0;
            if (!flag) {
                return;
            }
        }

        this.center =
                new BlockPos(
                        this.centerHelper.getX() / i,
                        this.centerHelper.getY() / i,
                        this.centerHelper.getZ() / i);
        int j = 0;

        for (java.util.Iterator<?> __recoveryIterator171_2760 =
                        (this.villageDoorInfoList).iterator();
                (__recoveryIterator171_2760).hasNext(); ) {
            VillageDoorInfo villageDoorInfo = (VillageDoorInfo) (__recoveryIterator171_2760).next();
            j = Math.max(villageDoorInfo.getDistanceToDoorBlockSq(this.center), j);
            if (flag) {
                break;
            }
        }

        this.villageRadius = Math.max(32, (int) Math.sqrt(j) + 1);
    }

    public Village(World world) {
        this.centerHelper = BlockPos.ORIGIN;
        this.center = BlockPos.ORIGIN;
        this.playerReputation = new TreeMap();
        this.villageAgressors = Lists.newArrayList();
        this.worldObj = world;
    }

    public int getNumVillageDoors() {
        return this.villageDoorInfoList.size();
    }

    public VillageDoorInfo getNearestDoor(BlockPos blockPos) {
        boolean flag1 = dS();
        VillageDoorInfo villageDoorInfo = null;
        boolean flag = flag1;
        int MAX_VALUE = Integer.MAX_VALUE;

        for (java.util.Iterator<?> __recoveryIterator171_3548 =
                        (this.villageDoorInfoList).iterator();
                (__recoveryIterator171_3548).hasNext(); ) {
            VillageDoorInfo villageDoorInfoValue =
                    (VillageDoorInfo) (__recoveryIterator171_3548).next();
            int j = villageDoorInfoValue.getDistanceToDoorBlockSq(blockPos);
            if (j < MAX_VALUE) {
                villageDoorInfo = villageDoorInfoValue;
                MAX_VALUE = j;
            }

            if (flag) {
                break;
            }
        }

        return villageDoorInfo;
    }

    static {
        net.minecraft.village.Village.packedTextFragments = new String[10];
        net.minecraft.village.Village.packedTextFragments[0] = new String("X");
        net.minecraft.village.Village.packedTextFragments[1] = new String("Y");
        net.minecraft.village.Village.packedTextFragments[2] = new String("Z");
        net.minecraft.village.Village.packedTextFragments[3] = new String("S");
        net.minecraft.village.Village.packedTextFragments[4] = new String("X");
        net.minecraft.village.Village.packedTextFragments[5] = new String("Y");
        net.minecraft.village.Village.packedTextFragments[6] = new String("Z");
        net.minecraft.village.Village.packedTextFragments[7] = new String("S");
        method_a8745b073fa8adfa1857(false);
    }

    public List getVillageDoorInfoList() {
        return this.villageDoorInfoList;
    }

    public void writeVillageDataToNBT(CompoundTag compoundTag) {
        compoundTag.setInteger("PopSize", this.numVillagers);
        boolean flag1 = dI();
        compoundTag.setInteger("Radius", this.villageRadius);
        compoundTag.setInteger("Golems", this.numIronGolems);
        compoundTag.setInteger("Stable", this.lastAddDoorTimestamp);
        compoundTag.setInteger("Tick", this.tickCounter);
        compoundTag.setInteger("MTick", this.noBreedTicks);
        compoundTag.setInteger("CX", this.center.getX());
        compoundTag.setInteger("CY", this.center.getY());
        compoundTag.setInteger("CZ", this.center.getZ());
        compoundTag.setInteger("ACX", this.centerHelper.getX());
        compoundTag.setInteger("ACY", this.centerHelper.getY());
        boolean flag = flag1;
        compoundTag.setInteger("ACZ", this.centerHelper.getZ());
        NBTTagList nbtTagList = new NBTTagList();

        for (java.util.Iterator<?> __recoveryIterator171_29304 =
                        (this.villageDoorInfoList).iterator();
                (__recoveryIterator171_29304).hasNext(); ) {
            VillageDoorInfo villageDoorInfo =
                    (VillageDoorInfo) (__recoveryIterator171_29304).next();
            CompoundTag compoundTagValue = new CompoundTag();
            compoundTagValue.setInteger(
                    packedTextFragments[4], villageDoorInfo.getDoorBlockPos().getX());
            compoundTagValue.setInteger(
                    packedTextFragments[5], villageDoorInfo.getDoorBlockPos().getY());
            compoundTagValue.setInteger(
                    packedTextFragments[6], villageDoorInfo.getDoorBlockPos().getZ());
            compoundTagValue.setInteger("IDX", villageDoorInfo.getInsideOffsetX());
            compoundTagValue.setInteger("IDZ", villageDoorInfo.getInsideOffsetZ());
            compoundTagValue.setInteger("TS", villageDoorInfo.getInsidePosY());
            nbtTagList.appendTag(compoundTagValue);
            if (!flag) {
                Entity.setLegacyGlobalFlowMarker(new String[2]);
                break;
            }
        }

        compoundTag.setTag("Doors", nbtTagList);
        NBTTagList nbtTagListValue = new NBTTagList();

        for (java.util.Iterator<?> __recoveryIterator171_30675 =
                        (this.playerReputation.keySet()).iterator();
                (__recoveryIterator171_30675).hasNext(); ) {
            String text = (String) (__recoveryIterator171_30675).next();
            CompoundTag compoundTagValue2 = new CompoundTag();
            compoundTagValue2.setString("Name", text);
            compoundTagValue2.setInteger(
                    packedTextFragments[7], (Integer) this.playerReputation.get(text));
            nbtTagListValue.appendTag(compoundTagValue2);
            if (!flag) {
                break;
            }
        }

        compoundTag.setTag("Players", nbtTagListValue);
    }

    private void removeDeadAndOutOfRangeDoors() {
        boolean flag1 = dI();
        byte b0 = 0;
        boolean flag = flag1;
        int i = this.worldObj.rand.nextInt(50) == 0 ? 1 : 0;
        Iterator iterator = this.villageDoorInfoList.iterator();

        while (iterator.hasNext()) {
            VillageDoorInfo villageDoorInfo = (VillageDoorInfo) iterator.next();
            if (i != 0) {
                villageDoorInfo.resetDoorOpeningRestrictionCounter();
            }

            if (!this.isWoodDoor(villageDoorInfo.getDoorBlockPos())
                    || Math.abs(this.tickCounter - villageDoorInfo.getInsidePosY()) > 1200) {
                this.centerHelper =
                        this.centerHelper.add(villageDoorInfo.getDoorBlockPos().multiply(-1));
                b0 = 1;
                villageDoorInfo.setIsDetachedFromVillageFlag(true);
                iterator.remove();
            }

            if (!flag) {
                break;
            }
        }

        if (b0 != 0) {
            this.updateVillageRadiusAndCenter();
        }
    }

    private boolean method_b3ac579b9cc69b79a53d(BlockPos blockPos, BlockPos blockPosValue) {
        boolean flag = dI();
        if (!World.doesBlockHaveSolidTopSurface(this.worldObj, blockPosValue.offsetDown())) {
            return false;
        }

        int i = blockPosValue.getX() - blockPos.getX() / 2;
        int j = blockPosValue.getZ() - blockPos.getZ() / 2;
        int k = i;

        while (k < i + blockPos.getX()) {
            int l = blockPosValue.getY();

            while (true) {
                if (l < blockPosValue.getY() + blockPos.getY()) {
                    int i1 = j;

                    while (i1 < j + blockPos.getZ()) {
                        if (this.worldObj
                                .getBlockState(new BlockPos(k, l, i1))
                                .getBlock()
                                .isNormalCube()) {
                            return false;
                        }

                        i1++;
                        if (!flag) {
                            break;
                        }
                    }

                    l++;
                    if (flag) {
                        continue;
                    }
                }

                k++;
                if (!flag) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    // region String decoding support

    // endregion

    private void removeDeadAndOldAgressors() {
        boolean flag1 = dI();
        Iterator iterator = this.villageAgressors.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            VillageVillageAgressor villageVillageAgressor =
                    (VillageVillageAgressor) iterator.next();
            if (!villageVillageAgressor.agressor.isEntityAlive()
                    || Math.abs(this.tickCounter - villageVillageAgressor.agressionTime) > 300) {
                iterator.remove();
            }

            if (!flag) {
                break;
            }
        }
    }

    public int getNumVillagers() {
        return this.numVillagers;
    }

    public int getReputationForPlayer(String text) {
        java.lang.Integer integer = (java.lang.Integer) this.playerReputation.get(text);
        return integer != null ? integer : 0;
    }

    public int getTicksSinceLastDoorAdding() {
        return this.tickCounter - this.lastAddDoorTimestamp;
    }

    public void tick(int tickCounterValue) {
        this.tickCounter = tickCounterValue;
        this.removeDeadAndOutOfRangeDoors();
        this.removeDeadAndOldAgressors();
        if (tickCounterValue % 20 == 0) {
            this.updateNumVillagers();
        }

        if (tickCounterValue % 30 == 0) {
            this.updateNumIronGolems();
        }

        int j = this.numVillagers / 10;
        if (this.numIronGolems < j
                && this.villageDoorInfoList.size() > 20
                && this.worldObj.rand.nextInt(7000) == 0) {
            net.minecraft.util.Vec3 vec3 = this.func_179862_a(this.center, 2, 4, 2);
            if (vec3 != null) {
                net.minecraft.entity.monster.EntityIronGolem entityIronGolem = new net.minecraft.entity.monster.EntityIronGolem(this.worldObj);
                entityIronGolem.setPosition(vec3.x, vec3.y, vec3.z);
                this.worldObj.spawnEntityInWorld(entityIronGolem);
                this.numIronGolems++;
            }
        }
    }

    public void setDefaultPlayerReputation(int i) {
        boolean flag1 = dS();
        Iterator iterator = this.playerReputation.keySet().iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            this.setReputationForPlayer(text, i);
            if (flag) {
                break;
            }
        }
    }

    public VillageDoorInfo getExistedDoor(BlockPos blockPos) {
        if (this.center.distanceSq(blockPos) > this.villageRadius * this.villageRadius) {
            return null;
        }

        for (java.util.Iterator<?> __recoveryIterator171_37451 =
                        (this.villageDoorInfoList).iterator();
                (__recoveryIterator171_37451).hasNext(); ) {
            VillageDoorInfo villageDoorInfo =
                    (VillageDoorInfo) (__recoveryIterator171_37451).next();
            if (villageDoorInfo.getDoorBlockPos().getX() == blockPos.getX()
                    && villageDoorInfo.getDoorBlockPos().getZ() == blockPos.getZ()
                    && Math.abs(villageDoorInfo.getDoorBlockPos().getY() - blockPos.getY()) <= 1) {
                return villageDoorInfo;
            }
        }

        return null;
    }

    public void addOrRenewAgressor(LivingEntity livingEntity) {
        for (java.util.Iterator<?> __recoveryIterator171_38956 =
                        ((java.lang.Iterable<net.minecraft.village.VillageVillageAgressor>)
                                        (java.lang.Iterable<?>) (this.villageAgressors))
                                .iterator();
                (__recoveryIterator171_38956).hasNext(); ) {
            net.minecraft.village.VillageVillageAgressor villageVillageAgressor =
                    (net.minecraft.village.VillageVillageAgressor) (__recoveryIterator171_38956).next();
            if (villageVillageAgressor.agressor == livingEntity) {
                villageVillageAgressor.agressionTime = this.tickCounter;
                return;
            }
        }

        this.villageAgressors.add(
                new net.minecraft.village.VillageVillageAgressor(this, livingEntity, this.tickCounter));
    }

    public static boolean dS() {
        return kS;
    }

    public BlockPos getCenter() {
        return this.center;
    }

    public void setWorld(World world) {
        this.worldObj = world;
    }

    private void updateNumIronGolems() {
        List entitiesWithinAABB =
                this.worldObj.getEntitiesWithinAABB(
                        EntityIronGolem.class,
                        new BoundingBox(
                                this.center.getX() - this.villageRadius,
                                this.center.getY() - 4,
                                this.center.getZ() - this.villageRadius,
                                this.center.getX() + this.villageRadius,
                                this.center.getY() + 4,
                                this.center.getZ() + this.villageRadius));
        this.numIronGolems = entitiesWithinAABB.size();
    }

    public VillageDoorInfo getDoorInfo(BlockPos blockPos) {
        VillageDoorInfo villageDoorInfo = null;
        boolean flag1 = dS();
        int MAX_VALUE = Integer.MAX_VALUE;
        boolean flag = flag1;

        for (java.util.Iterator<?> __recoveryIterator171_40822 =
                        (this.villageDoorInfoList).iterator();
                (__recoveryIterator171_40822).hasNext(); ) {
            VillageDoorInfo villageDoorInfoValue =
                    (VillageDoorInfo) (__recoveryIterator171_40822).next();
            int j;
            {
                j = villageDoorInfoValue.getDistanceToDoorBlockSq(blockPos);
                if (j > 256) {
                    j *= 1000;
                    if (!(!flag)) {

                        j = villageDoorInfoValue.getDoorOpeningRestrictionCounter();
                    }
                } else {

                    j = villageDoorInfoValue.getDoorOpeningRestrictionCounter();
                }
            }

            if (j < MAX_VALUE) {
                villageDoorInfo = villageDoorInfoValue;
                MAX_VALUE = j;
            }

            if (flag) {
                break;
            }
        }

        return villageDoorInfo;
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        kS = flag;
    }

    public boolean isAnnihilated() {
        return this.villageDoorInfoList.isEmpty();
    }

    public Village() {
        this.centerHelper = BlockPos.ORIGIN;
        this.center = BlockPos.ORIGIN;
        this.playerReputation = new TreeMap();
        this.villageAgressors = Lists.newArrayList();
    }

    public void addVillageDoorInfo(VillageDoorInfo villageDoorInfo) {
        this.villageDoorInfoList.add(villageDoorInfo);
        this.centerHelper = this.centerHelper.add(villageDoorInfo.getDoorBlockPos());
        this.updateVillageRadiusAndCenter();
        this.lastAddDoorTimestamp = villageDoorInfo.getInsidePosY();
    }

    public boolean isMatingSeason() {
        return this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600;
    }

    public static boolean dI() {
        boolean flag = dS();
        return !flag;
    }

    public PlayerEntity getNearestTargetPlayer(LivingEntity livingEntity) {
        double MAX_VALUE = Double.MAX_VALUE;
        boolean flag1 = dI();
        PlayerEntity playerEntity = null;
        boolean flag = flag1;

        for (java.util.Iterator<?> __recoveryIterator171_42748 =
                        (this.playerReputation.keySet()).iterator();
                (__recoveryIterator171_42748).hasNext(); ) {
            String text = (String) (__recoveryIterator171_42748).next();
            if (this.isPlayerReputationTooLow(text)) {
                PlayerEntity playerEntityValue = this.worldObj.getPlayerEntityByName(text);
                if (playerEntityValue != null) {
                    double distanceSqToEntity =
                            playerEntityValue.getDistanceSqToEntity(livingEntity);
                    if (distanceSqToEntity <= MAX_VALUE) {
                        playerEntity = playerEntityValue;
                        MAX_VALUE = distanceSqToEntity;
                    }
                }
            }

            if (!flag) {
                break;
            }
        }

        return playerEntity;
    }

    private boolean isWoodDoor(BlockPos blockPos) {
        Block block = this.worldObj.getBlockState(blockPos).getBlock();
        return block instanceof BlockDoor ? block.getMaterial() == Material.wood : false;
    }

    private Vec3 func_179862_a(BlockPos blockPos, int i, int j, int k) {
        boolean flag1 = dS();
        int l = 0;
        boolean flag = flag1;

        while (l < 10) {
            BlockPos blockPosValue =
                    blockPos.add(
                            this.worldObj.rand.nextInt(16) - 8,
                            this.worldObj.rand.nextInt(6) - 3,
                            this.worldObj.rand.nextInt(16) - 8);
            if (this.func_179866_a(blockPosValue)
                    && this.method_b3ac579b9cc69b79a53d(new BlockPos(i, j, k), blockPosValue)) {
                return new Vec3(blockPosValue.getX(), blockPosValue.getY(), blockPosValue.getZ());
            }

            l++;
            if (flag) {
                break;
            }
        }

        return null;
    }

    public void readVillageDataFromNBT(CompoundTag compoundTag) {
        this.numVillagers = compoundTag.getInteger("PopSize");
        this.villageRadius = compoundTag.getInteger("Radius");
        boolean flag1 = dS();
        this.numIronGolems = compoundTag.getInteger("Golems");
        this.lastAddDoorTimestamp = compoundTag.getInteger("Stable");
        this.tickCounter = compoundTag.getInteger("Tick");
        boolean flag = flag1;
        this.noBreedTicks = compoundTag.getInteger("MTick");
        this.center =
                new BlockPos(
                        compoundTag.getInteger("CX"),
                        compoundTag.getInteger("CY"),
                        compoundTag.getInteger("CZ"));
        this.centerHelper =
                new BlockPos(
                        compoundTag.getInteger("ACX"),
                        compoundTag.getInteger("ACY"),
                        compoundTag.getInteger("ACZ"));
        NBTTagList nbtTagList = compoundTag.getTagList("Doors", 10);
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            VillageDoorInfo villageDoorInfo =
                    new VillageDoorInfo(
                            new BlockPos(
                                    compoundTagValue.getInteger(packedTextFragments[0]),
                                    compoundTagValue.getInteger(packedTextFragments[1]),
                                    compoundTagValue.getInteger(packedTextFragments[2])),
                            compoundTagValue.getInteger("IDX"),
                            compoundTagValue.getInteger("IDZ"),
                            compoundTagValue.getInteger("TS"));
            this.villageDoorInfoList.add(villageDoorInfo);
            i++;
            if (flag) {
                break;
            }
        }

        NBTTagList nbtTagListValue = compoundTag.getTagList("Players", 10);
        int j = 0;

        while (j < nbtTagListValue.tagCount()) {
            CompoundTag compoundTagValue2 = nbtTagListValue.getCompoundTagAt(j);
            this.playerReputation.put(
                    compoundTagValue2.getString("Name"),
                    compoundTagValue2.getInteger(packedTextFragments[3]));
            j++;
            if (flag) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            method_a8745b073fa8adfa1857(!flag);
        }
    }

    public int setReputationForPlayer(String text, int i) {
        int j = this.getReputationForPlayer(text);
        int k = MathHelper.clamp(j + i, -30, 10);
        this.playerReputation.put(text, k);
        return k;
    }

    public int getVillageRadius() {
        return this.villageRadius;
    }

    // region String decoding support

    // endregion

    public void endMatingSeason() {
        this.noBreedTicks = this.tickCounter;
    }

    public LivingEntity findNearestVillageAggressor(LivingEntity livingEntity) {
        boolean flag1 = dS();
        double MAX_VALUE = Double.MAX_VALUE;
        boolean flag = flag1;
        VillageVillageAgressor villageVillageAgressor = null;
        int i = 0;

        while (i < this.villageAgressors.size()) {
            VillageVillageAgressor villageVillageAgressorValue =
                    (VillageVillageAgressor) this.villageAgressors.get(i);
            double distanceSqToEntity =
                    villageVillageAgressorValue.agressor.getDistanceSqToEntity(livingEntity);
            if (distanceSqToEntity <= MAX_VALUE) {
                villageVillageAgressor = villageVillageAgressorValue;
                MAX_VALUE = distanceSqToEntity;
            }

            i++;
            if (flag) {
                break;
            }
        }

        return villageVillageAgressor != null ? villageVillageAgressor.agressor : null;
    }

    public boolean func_179866_a(BlockPos blockPos) {
        return this.center.distanceSq(blockPos) < this.villageRadius * this.villageRadius;
    }
}
