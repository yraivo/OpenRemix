package net.minecraft.village;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class VillageCollection extends WorldSavedData {
    private List villageList;
    private List newDoors;

    private final List villagerPositionsList = Lists.newArrayList();
    private int tickCounter;

    private World worldObj;

    private VillageDoorInfo checkDoorExistence(BlockPos blockPos) {
        for (java.util.Iterator<?> __recoveryIterator171_1055 = (this.newDoors).iterator();
                (__recoveryIterator171_1055).hasNext(); ) {
            VillageDoorInfo villageDoorInfo = (VillageDoorInfo) (__recoveryIterator171_1055).next();
            if (villageDoorInfo.getDoorBlockPos().getX() == blockPos.getX()
                    && villageDoorInfo.getDoorBlockPos().getZ() == blockPos.getZ()
                    && Math.abs(villageDoorInfo.getDoorBlockPos().getY() - blockPos.getY()) <= 1) {
                return villageDoorInfo;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_1634 = (this.villageList).iterator();
                (__recoveryIterator171_1634).hasNext(); ) {
            Village village = (Village) (__recoveryIterator171_1634).next();
            VillageDoorInfo villageDoorInfoValue = village.getExistedDoor(blockPos);
            if (villageDoorInfoValue != null) {
                return villageDoorInfoValue;
            }
        }

        return null;
    }

    private void addToNewDoorsList(BlockPos blockPos) {
        Facing facing = BlockDoor.getFacing(this.worldObj, blockPos);
        Facing facingValue = facing.getOpposite();
        int i = this.countBlocksCanSeeSky(blockPos, facing, 5);
        int j = this.countBlocksCanSeeSky(blockPos, facingValue, i + 1);
        if (i != j) {
            this.newDoors.add(
                    new VillageDoorInfo(blockPos, i < j ? facing : facingValue, this.tickCounter));
        }
    }

    private boolean positionInList(BlockPos blockPos) {
        for (java.util.Iterator<?> __recoveryIterator171_2927 =
                        (this.villagerPositionsList).iterator();
                (__recoveryIterator171_2927).hasNext(); ) {
            BlockPos blockPosValue = (BlockPos) (__recoveryIterator171_2927).next();
            if (blockPosValue.equals(blockPos)) {
                return true;
            }
        }

        return false;
    }

    private int countBlocksCanSeeSky(BlockPos blockPos, Facing facing, int i) {
        boolean flag1 = Village.dI();
        int j = 0;
        boolean flag = flag1;
        int k = 1;

        while (k <= 5) {
            if (this.worldObj.isAgainstSky(blockPos.offset(facing, k))) {
                if (++j >= i) {
                    return j;
                }
            }

            k++;
            if (!flag) {
                break;
            }
        }

        return j;
    }

    public void setWorldsForAll(World bhTValue) {
        boolean flag1 = Village.dI();
        this.worldObj = bhTValue;
        Iterator iterator = this.villageList.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            Village village = (Village) iterator.next();
            village.setWorld(bhTValue);
            if (!flag) {
                break;
            }
        }
    }


    public VillageCollection(World bhTValue) {
        super(fileNameForProvider(bhTValue.provider));
        this.newDoors = Lists.newArrayList();
        this.villageList = Lists.newArrayList();
        this.worldObj = bhTValue;
        this.markDirty();
    }

    public void readFromNBT(CompoundTag compoundTag) {
        boolean flag1 = Village.dS();
        this.tickCounter = compoundTag.getInteger("Tick");
        boolean flag = flag1;
        NBTTagList nbtTagList = compoundTag.getTagList("Villages", 10);
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
            Village village = new Village();
            village.readVillageDataFromNBT(compoundTagValue);
            this.villageList.add(village);
            i++;
            if (flag) {
                break;
            }
        }
    }

    private void addDoorsAround(BlockPos blockPos) {
        byte b0 = 16;
        boolean flag1 = Village.dI();
        byte b1 = 4;
        boolean flag = flag1;
        byte b2 = 16;
        int i = -b0;

        while (i < b0) {
            int j = -b1;

            while (true) {
                if (j < b1) {
                    int k = -b2;

                    while (k < b2) {
                        BlockPos blockPosValue = blockPos.add(i, j, k);
                        if (this.isWoodDoor(blockPosValue)) {
                            VillageDoorInfo villageDoorInfo =
                                    this.checkDoorExistence(blockPosValue);
                            if (villageDoorInfo == null) {
                                this.addToNewDoorsList(blockPosValue);
                                if (!(flag)) {

                                    villageDoorInfo.setLastActivityTimestamp(this.tickCounter);
                                }
                            } else {

                                villageDoorInfo.setLastActivityTimestamp(this.tickCounter);
                            }
                        }

                        k++;
                        if (!flag) {
                            break;
                        }
                    }

                    j++;
                    if (flag) {
                        continue;
                    }
                }

                i++;
                if (!flag) {
                    return;
                }
                break;
            }
        }
    }

    public void tick() {
        boolean flag1 = Village.dI();
        this.tickCounter++;
        Iterator iterator = this.villageList.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            Village village = (Village) iterator.next();
            village.tick(this.tickCounter);
            if (!flag) {
                break;
            }
        }

        this.cj();
        this.cQ();
        this.cS();
        if (this.tickCounter % 400 == 0) {
            this.markDirty();
        }
    }

    public Village getNearestVillage(BlockPos blockPos, int i) {
        boolean flag1 = Village.dS();
        Village village = null;
        double MAX_VALUE = Float.MAX_VALUE;
        boolean flag = flag1;

        for (java.util.Iterator<?> __recoveryIterator171_32934 = (this.villageList).iterator();
                (__recoveryIterator171_32934).hasNext(); ) {
            Village villageValue = (Village) (__recoveryIterator171_32934).next();
            double d1 = villageValue.getCenter().distanceSq(blockPos);
            if (d1 < MAX_VALUE) {
                float f = i + villageValue.getVillageRadius();
                if (d1 <= f * f) {
                    village = villageValue;
                    MAX_VALUE = d1;
                }
            }

            if (flag) {
                break;
            }
        }

        return village;
    }

    private void cQ() {
        if (!this.villagerPositionsList.isEmpty()) {
            this.addDoorsAround((BlockPos) this.villagerPositionsList.remove(0));
        }
    }

    public void addToVillagerPositionList(BlockPos blockPos) {
        if (this.villagerPositionsList.size() <= 64 && !this.positionInList(blockPos)) {
            this.villagerPositionsList.add(blockPos);
        }
    }

    private void cj() {
        boolean flag1 = Village.dI();
        Iterator iterator = this.villageList.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            Village village = (Village) iterator.next();
            if (village.isAnnihilated()) {
                iterator.remove();
                this.markDirty();
            }

            if (!flag) {
                break;
            }
        }
    }

    public void writeToNBT(CompoundTag compoundTag) {
        boolean flag1 = Village.dI();
        compoundTag.setInteger("Tick", this.tickCounter);
        NBTTagList nbtTagList = new NBTTagList();
        Iterator iterator = this.villageList.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            Village village = (Village) iterator.next();
            CompoundTag compoundTagValue = new CompoundTag();
            village.writeVillageDataToNBT(compoundTagValue);
            nbtTagList.appendTag(compoundTagValue);
            if (!flag) {
                break;
            }
        }

        compoundTag.setTag("Villages", nbtTagList);
    }

    public List getVillageList() {
        return this.villageList;
    }

    public static String fileNameForProvider(WorldProvider worldProvider) {
        return "villages" + worldProvider.getInternalNameSuffix();
    }

    public VillageCollection(String text) {
        super(text);
        this.newDoors = Lists.newArrayList();
        this.villageList = Lists.newArrayList();
    }

    private boolean isWoodDoor(BlockPos blockPos) {
        Block block = this.worldObj.getBlockState(blockPos).getBlock();
        return block instanceof BlockDoor ? block.getMaterial() == Material.wood : false;
    }

    private void cS() {
        boolean flag1 = Village.dS();
        int i = 0;
        boolean flag = flag1;

        while (i < this.newDoors.size()) {
            VillageDoorInfo villageDoorInfo = (VillageDoorInfo) this.newDoors.get(i);
            Village village = this.getNearestVillage(villageDoorInfo.getDoorBlockPos(), 32);
            if (village == null) {
                village = new Village(this.worldObj);
                this.villageList.add(village);
                this.markDirty();
            }

            village.addVillageDoorInfo(villageDoorInfo);
            i++;
            if (flag) {
                break;
            }
        }

        this.newDoors.clear();
    }
}
