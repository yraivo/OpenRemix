package net.minecraft.stats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.IJsonSerializable;

import com.google.common.collect.Maps;

import java.util.Map;

public class StatFileWriter {
    protected final Map statsData = Maps.newConcurrentMap();

    public int method_177bf7e342bbdacda4b7(Achievement achievement) {
        Entity[] entityArray = StatBase.GU();
        if (this.hasAchievementUnlocked(achievement)) {
            return 0;
        }

        int i = 0;
        Achievement bCr = achievement.parentAchievement;

        while (bCr != null && !this.hasAchievementUnlocked(bCr)) {
            bCr = bCr.parentAchievement;
            i++;
            if (entityArray != null) {
                break;
            }
        }

        return i;
    }

    public boolean hasAchievementUnlocked(Achievement achievement) {
        return this.writeStat(achievement) > 0;
    }

    public IJsonSerializable method_c2fe49aaad864d954ea4(StatBase statBase) {
        net.minecraft.util.TupleIntJsonSerializable tupleIntJsonSerializable =
                (net.minecraft.util.TupleIntJsonSerializable) this.statsData.get(statBase);
        return tupleIntJsonSerializable != null
                ? tupleIntJsonSerializable.getJsonSerializableValue()
                : null;
    }

    public boolean canUnlockAchievement(Achievement achievement) {
        return achievement.parentAchievement == null
                || this.hasAchievementUnlocked(achievement.parentAchievement);
    }

    public IJsonSerializable func_150872_a(StatBase statBase, IJsonSerializable iJsonSerializable) {
        net.minecraft.util.TupleIntJsonSerializable tupleIntJsonSerializable =
                (net.minecraft.util.TupleIntJsonSerializable) this.statsData.get(statBase);
        if (tupleIntJsonSerializable == null) {
            tupleIntJsonSerializable = new net.minecraft.util.TupleIntJsonSerializable();
            this.statsData.put(statBase, tupleIntJsonSerializable);
        }

        tupleIntJsonSerializable.setJsonSerializableValue(iJsonSerializable);
        return iJsonSerializable;
    }

    public void unlockAchievement(PlayerEntity playerEntity, StatBase statBase, int p171_2) {
        net.minecraft.util.TupleIntJsonSerializable tupleIntJsonSerializable =
                (net.minecraft.util.TupleIntJsonSerializable) this.statsData.get(statBase);
        if (tupleIntJsonSerializable == null) {
            tupleIntJsonSerializable = new net.minecraft.util.TupleIntJsonSerializable();
            this.statsData.put(statBase, tupleIntJsonSerializable);
        }

        tupleIntJsonSerializable.setIntegerValue(p171_2);
    }

    public void increaseStat(
            PlayerEntity playerEntity, StatBase statBase, int p171_2) {
        if (!statBase.isAchievement() || this.canUnlockAchievement((net.minecraft.stats.Achievement) statBase)) {
            this.unlockAchievement(playerEntity, statBase, this.writeStat(statBase) + p171_2);
        }
    }

    public int writeStat(StatBase statBase) {
        net.minecraft.util.TupleIntJsonSerializable tupleIntJsonSerializable =
                (net.minecraft.util.TupleIntJsonSerializable) this.statsData.get(statBase);
        return tupleIntJsonSerializable == null ? 0 : tupleIntJsonSerializable.getIntegerValue();
    }
}
