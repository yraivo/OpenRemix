package net.minecraft.stats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class StatisticsFile extends StatFileWriter {

    private final Set field_150888_e = Sets.newHashSet();
    private File statsFile;
    private boolean field_150886_g;
    private MinecraftServer mcServer;
    private int field_150885_f = -300;

    private static Logger logger;

    public void saveStatFile() {
        try {
            FileUtils.writeStringToFile(this.statsFile, dumpJson(this.statsData));
        } catch (IOException ioexception) {
            net.minecraft.stats.StatisticsFile.logger.error("Couldn't save stats", ioexception);
        }
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public void readStatFile() {
        if (this.statsFile.isFile()) {
            try {
                this.statsData.clear();
                this.statsData.putAll(this.parseJson(FileUtils.readFileToString(this.statsFile)));
            } catch (IOException ioexception) {
                net.minecraft.stats.StatisticsFile.logger.error(
                        "Couldn't read statistics file " + this.statsFile, ioexception);
            } catch (JsonParseException jsonparseexception) {
                net.minecraft.stats.StatisticsFile.logger.error(
                        "Couldn't parse statistics file " + this.statsFile, jsonparseexception);
            }
        }
    }

    static {
        net.minecraft.stats.StatisticsFile.logger = LogManager.getLogger();
    }

    public boolean func_150879_e() {
        return this.field_150886_g;
    }

    public StatisticsFile(MinecraftServer bvIValue, File file1) {
        this.field_150886_g = false;
        this.mcServer = bvIValue;
        this.statsFile = file1;
    }

    public Set func_150878_c() {
        HashSet hashset = Sets.newHashSet(this.field_150888_e);
        this.field_150888_e.clear();
        this.field_150886_g = false;
        return hashset;
    }

    public void unlockAchievement(PlayerEntity playerEntity, StatBase statBase, int i) {
        int j = statBase.isAchievement() ? this.writeStat(statBase) : 0;
        super.unlockAchievement(playerEntity, statBase, i);
        this.field_150888_e.add(statBase);
        if (statBase.isAchievement() && j == 0 && i > 0) {
            this.field_150886_g = true;
            if (this.mcServer.isAnnouncingPlayerAchievements()) {
                this.mcServer
                        .getConfigurationManager()
                        .sendChatMsg(
                                new ChatComponentTranslation(
                                        "chat.type.achievement",
                                        new Object[] {
                                            playerEntity.getDisplayName(), statBase.func_150955_j()
                                        }));
            }
        }

        if (statBase.isAchievement() && j > 0 && i == 0) {
            this.field_150886_g = true;
            if (this.mcServer.isAnnouncingPlayerAchievements()) {
                this.mcServer
                        .getConfigurationManager()
                        .sendChatMsg(
                                new ChatComponentTranslation(
                                        "chat.type.achievement.taken",
                                        new Object[] {
                                            playerEntity.getDisplayName(), statBase.func_150955_j()
                                        }));
            }
        }
    }

    public Map parseJson(String text) {
        Entity[] entityArray = StatBase.GU();
        JsonElement jsonelement = new JsonParser().parse(text);
        Entity[] entityArrayValue = entityArray;
        if (!jsonelement.isJsonObject()) {
            return Maps.newHashMap();
        }

        JsonObject jsonobject = jsonelement.getAsJsonObject();
        HashMap hashmap = Maps.newHashMap();

        for (java.util.Iterator<?> __recoveryIterator171_29630 = (jsonobject.entrySet()).iterator();
                (__recoveryIterator171_29630).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_29630).next();
            label56:
            {
                StatBase oneShotStat = StatList.getOneShotStat((String) entry.getKey());
                if (oneShotStat != null) {
                    TupleIntJsonSerializable tupleIntJsonSerializable;
                    label53:
                    {
                        tupleIntJsonSerializable = new TupleIntJsonSerializable();
                        if (((JsonElement) entry.getValue()).isJsonPrimitive()
                                && ((JsonElement) entry.getValue())
                                        .getAsJsonPrimitive()
                                        .isNumber()) {
                            tupleIntJsonSerializable.setIntegerValue(
                                    ((JsonElement) entry.getValue()).getAsInt());
                            if (entityArrayValue == null) {
                                break label53;
                            }
                        }

                        if (((JsonElement) entry.getValue()).isJsonObject()) {
                            JsonObject jsonobject1 =
                                    ((JsonElement) entry.getValue()).getAsJsonObject();
                            if (jsonobject1.has("value")
                                    && jsonobject1.get("value").isJsonPrimitive()
                                    && jsonobject1.get("value").getAsJsonPrimitive().isNumber()) {
                                tupleIntJsonSerializable.setIntegerValue(
                                        jsonobject1.getAsJsonPrimitive("value").getAsInt());
                            }

                            if (jsonobject1.has("progress")
                                    && oneShotStat.func_150954_l() != null) {
                                try {
                                    Constructor constructor =
                                            oneShotStat.func_150954_l().getConstructor();
                                    IJsonSerializable iJsonSerializable =
                                            (IJsonSerializable) constructor.newInstance();
                                    iJsonSerializable.fromJson(jsonobject1.get("progress"));
                                    tupleIntJsonSerializable.setJsonSerializableValue(
                                            iJsonSerializable);
                                } catch (Throwable throwable) {
                                    net.minecraft.stats.StatisticsFile.logger.warn(
                                            "Invalid statistic progress in " + this.statsFile,
                                            throwable);
                                }
                            }
                        }
                    }

                    hashmap.put(oneShotStat, tupleIntJsonSerializable);
                    if (entityArrayValue == null) {
                        break label56;
                    }
                }

                net.minecraft.stats.StatisticsFile.logger.warn(
                        "Invalid statistic in "
                                + this.statsFile
                                + ": Don't know what "
                                + (String) entry.getKey()
                                + " is");
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        return hashmap;
    }

    public void sendAchievements(EntityPlayerMP entityPlayerMP) {
        HashMap hashmap = Maps.newHashMap();
        Entity[] entityArray = StatBase.GU();
        Iterator iterator = AchievementList.achievementList.iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            Achievement achievement = (Achievement) iterator.next();
            if (this.hasAchievementUnlocked(achievement)) {
                hashmap.put(achievement, this.writeStat(achievement));
                this.field_150888_e.remove(achievement);
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashmap));
    }

    public static String dumpJson(Map map) {
        Entity[] entityArray = StatBase.GU();
        JsonObject jsonobject = new JsonObject();
        Entity[] entityArrayValue = entityArray;

        for (java.util.Iterator<?> __recoveryIterator171_33707 = (map.entrySet()).iterator();
                (__recoveryIterator171_33707).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_33707).next();
            {
                if (((TupleIntJsonSerializable) entry.getValue()).getJsonSerializableValue()
                        != null) {
                    JsonObject jsonobject1 = new JsonObject();
                    jsonobject1.addProperty(
                            "value",
                            ((TupleIntJsonSerializable) entry.getValue()).getIntegerValue());

                    try {
                        jsonobject1.add(
                                "progress",
                                ((TupleIntJsonSerializable) entry.getValue())
                                        .getJsonSerializableValue()
                                        .getSerializableElement());
                    } catch (Throwable throwable) {
                        net.minecraft.stats.StatisticsFile.logger.warn(
                                "Couldn't save statistic "
                                        + ((StatBase) entry.getKey()).getStatName()
                                        + ": error serializing progress",
                                throwable);
                    }

                    jsonobject.add(((StatBase) entry.getKey()).statId, jsonobject1);
                    if (!(entityArrayValue == null)) {

                        jsonobject.addProperty(
                                ((StatBase) entry.getKey()).statId,
                                ((TupleIntJsonSerializable) entry.getValue()).getIntegerValue());
                    }
                } else {

                    jsonobject.addProperty(
                            ((StatBase) entry.getKey()).statId,
                            ((TupleIntJsonSerializable) entry.getValue()).getIntegerValue());
                }
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        return jsonobject.toString();
    }

    public void method_5a6071bb02cc6d17b169(EntityPlayerMP entityPlayerMP) {
        Entity[] entityArray = StatBase.GU();
        int i = this.mcServer.getTickCounter();
        Entity[] entityArrayValue = entityArray;
        HashMap hashmap = Maps.newHashMap();
        if (this.field_150886_g || i - this.field_150885_f > 300) {
            this.field_150885_f = i;

            for (java.util.Iterator<?> __recoveryIterator171_35879 =
                            (this.func_150878_c()).iterator();
                    (__recoveryIterator171_35879).hasNext(); ) {
                StatBase statBase = (StatBase) (__recoveryIterator171_35879).next();
                hashmap.put(statBase, this.writeStat(statBase));
                if (entityArrayValue != null) {
                    break;
                }
            }
        }

        entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashmap));
    }

    public void l() {
        Entity[] entityArray = StatBase.GU();
        Iterator iterator = this.statsData.keySet().iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            StatBase statBase = (StatBase) iterator.next();
            this.field_150888_e.add(statBase);
            if (entityArrayValue != null) {
                break;
            }
        }
    }
}
