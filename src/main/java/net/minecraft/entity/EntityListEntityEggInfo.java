package net.minecraft.entity;

import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class EntityListEntityEggInfo {
    public int secondaryColor;
    public int primaryColor;
    public StatBase field_151513_e;
    public StatBase field_151512_d;
    public int spawnedID;

    public EntityListEntityEggInfo(int spawnedIDValue, int j, int k) {
        this.spawnedID = spawnedIDValue;
        this.primaryColor = j;
        this.secondaryColor = k;
        this.field_151512_d = StatList.getStatKillEntity(this);
        this.field_151513_e = StatList.getStatEntityKilledBy(this);
    }
}
