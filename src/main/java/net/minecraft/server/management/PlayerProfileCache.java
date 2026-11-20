package net.minecraft.server.management;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class PlayerProfileCache {
    private File usercacheFile;
    private MinecraftServer mcServer;
    private Map uuidToProfileEntryMap;

    private static ParameterizedType TYPE;
    protected Gson gson;
    public static SimpleDateFormat dateFormat;
    private LinkedList gameProfiles;
    private final Map usernameToProfileEntryMap = Maps.newHashMap();

    private PlayerProfileCacheProfileEntry getByUUID(UUID uuid) {
        PlayerProfileCacheProfileEntry playerProfileCacheProfileEntry =
                (PlayerProfileCacheProfileEntry) this.uuidToProfileEntryMap.get(uuid);
        if (playerProfileCacheProfileEntry != null) {
            GameProfile gameprofile = playerProfileCacheProfileEntry.getGameProfile();
            this.gameProfiles.remove(gameprofile);
            this.gameProfiles.addFirst(gameprofile);
        }

        return playerProfileCacheProfileEntry;
    }

    public GameProfile getProfileByUUID(UUID uuid) {
        PlayerProfileCacheProfileEntry playerProfileCacheProfileEntry =
                (PlayerProfileCacheProfileEntry) this.uuidToProfileEntryMap.get(uuid);
        return playerProfileCacheProfileEntry == null
                ? null
                : playerProfileCacheProfileEntry.getGameProfile();
    }

    public void save() {
        String text = this.gson.toJson(this.getEntriesWithLimit(1000));
        BufferedWriter bufferedwriter = null;

        try {
            bufferedwriter = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
            bufferedwriter.write(text);
            return;
        } catch (FileNotFoundException filenotfoundexception) {
            return;
        } catch (IOException ioexception) {
        } finally {
            IOUtils.closeQuietly(bufferedwriter);
        }
    }

    private List getEntriesWithLimit(int i) {
        ServerConfigurationManager.kr();

        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_3146 =
                        (Lists.newArrayList(Iterators.limit(this.gameProfiles.iterator(), i)))
                                .iterator();
                (__recoveryIterator171_3146).hasNext(); ) {
            GameProfile gameprofile = (GameProfile) (__recoveryIterator171_3146).next();
            PlayerProfileCacheProfileEntry byUUID = this.getByUUID(gameprofile.getId());
            if (byUUID != null) {
                arrayList.add(byUUID);
            }
        }

        return arrayList;
    }

    private static GameProfile getGameProfile(MinecraftServer minecraftServer, String text) {
        GameProfile[] agameprofile = new GameProfile[1];
        PlayerProfileCacheGetGameProfileProfileLookupCallback
                playerProfileCacheGetGameProfileProfileLookupCallback =
                        new PlayerProfileCacheGetGameProfileProfileLookupCallback(agameprofile);
        minecraftServer
                .getGameProfileRepository()
                .findProfilesByNames(
                        new String[] {text},
                        Agent.MINECRAFT,
                        playerProfileCacheGetGameProfileProfileLookupCallback);
        if (!minecraftServer.isServerInOnlineMode() && agameprofile[0] == null) {
            UUID uuid = PlayerEntity.getUUID(new GameProfile((UUID) null, text));
            GameProfile gameprofile = new GameProfile(uuid, text);
            playerProfileCacheGetGameProfileProfileLookupCallback.onProfileLookupSucceeded(
                    gameprofile);
        }

        return agameprofile[0];
    }

    // region String decoding support

    // endregion

    public PlayerProfileCache(MinecraftServer bzfValue, File file1) {
        this.uuidToProfileEntryMap = Maps.newHashMap();
        this.gameProfiles = Lists.newLinkedList();
        this.mcServer = bzfValue;
        this.usercacheFile = file1;
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeHierarchyAdapter(
                PlayerProfileCacheProfileEntry.class, new PlayerProfileCacheSerializer(this, null));
        this.gson = gsonbuilder.create();
        this.load();
    }

    static {
        net.minecraft.server.management.PlayerProfileCache.dateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        net.minecraft.server.management.PlayerProfileCache.TYPE =
                new PlayerProfileCacheTypeParameterizedType();
    }

    // region String decoding support

    // endregion

    private void addEntry(GameProfile gameprofile, Date date) {
        ServerConfigurationManager.kr();

        UUID uuid = gameprofile.getId();

        if (date == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(2, 1);
            date = calendar.getTime();
        }

        {
            String text = gameprofile.getName().toLowerCase(Locale.ROOT);
            PlayerProfileCacheProfileEntry playerProfileCacheProfileEntry =
                    new PlayerProfileCacheProfileEntry(this, gameprofile, date, null);
            if (this.uuidToProfileEntryMap.containsKey(uuid)) {
                PlayerProfileCacheProfileEntry playerProfileCacheProfileEntryValue =
                        (PlayerProfileCacheProfileEntry) this.uuidToProfileEntryMap.get(uuid);
                this.usernameToProfileEntryMap.remove(
                        playerProfileCacheProfileEntryValue
                                .getGameProfile()
                                .getName()
                                .toLowerCase(Locale.ROOT));
                this.usernameToProfileEntryMap.put(
                        gameprofile.getName().toLowerCase(Locale.ROOT),
                        playerProfileCacheProfileEntry);
                this.gameProfiles.remove(gameprofile);

            } else {

                this.uuidToProfileEntryMap.put(uuid, playerProfileCacheProfileEntry);
                this.usernameToProfileEntryMap.put(text, playerProfileCacheProfileEntry);
            }
        }

        this.gameProfiles.addFirst(gameprofile);
    }

    public void addEntry(GameProfile gameprofile) {
        this.addEntry(gameprofile, (Date) null);
    }

    public GameProfile getGameProfileForUsername(String text) {
        ServerConfigurationManager.kr();

        String textValue = text.toLowerCase(Locale.ROOT);
        PlayerProfileCacheProfileEntry playerProfileCacheProfileEntry =
                (PlayerProfileCacheProfileEntry) this.usernameToProfileEntryMap.get(textValue);

        if (playerProfileCacheProfileEntry != null
                && new Date().getTime()
                        >= PlayerProfileCacheProfileEntry.access$200(playerProfileCacheProfileEntry)
                                .getTime()) {
            this.uuidToProfileEntryMap.remove(
                    playerProfileCacheProfileEntry.getGameProfile().getId());
            this.usernameToProfileEntryMap.remove(
                    playerProfileCacheProfileEntry
                            .getGameProfile()
                            .getName()
                            .toLowerCase(Locale.ROOT));
            this.gameProfiles.remove(playerProfileCacheProfileEntry.getGameProfile());
            playerProfileCacheProfileEntry = null;
        }

        {
            if (playerProfileCacheProfileEntry != null) {
                GameProfile gameprofile = playerProfileCacheProfileEntry.getGameProfile();
                this.gameProfiles.remove(gameprofile);
                this.gameProfiles.addFirst(gameprofile);

            } else {

                GameProfile gameprofile1 = getGameProfile(this.mcServer, textValue);
                if (gameprofile1 != null) {
                    this.addEntry(gameprofile1);
                    playerProfileCacheProfileEntry =
                            (PlayerProfileCacheProfileEntry)
                                    this.usernameToProfileEntryMap.get(textValue);
                }
            }
        }

        this.save();
        return playerProfileCacheProfileEntry == null
                ? null
                : playerProfileCacheProfileEntry.getGameProfile();
    }

    private static FileNotFoundException method_fd3f0eb1dd2c178d63ce(
            FileNotFoundException filenotfoundexception) {
        return filenotfoundexception;
    }

    public String[] getUsernames() {
        ArrayList arrayList = Lists.newArrayList(this.usernameToProfileEntryMap.keySet());
        return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    public void load() {
        ServerConfigurationManager.kr();

        List list = null;
        BufferedReader bufferedreader = null;

        label66:
        {
            try {
                bufferedreader = Files.newReader(this.usercacheFile, Charsets.UTF_8);
                list =
                        (List)
                                this.gson.fromJson(
                                        bufferedreader,
                                        net.minecraft.server.management.PlayerProfileCache.TYPE);
                break label66;
            } catch (FileNotFoundException filenotfoundexception1) {
            } finally {
                IOUtils.closeQuietly(bufferedreader);
            }

            return;
        }

        if (list != null) {
            this.usernameToProfileEntryMap.clear();
            this.uuidToProfileEntryMap.clear();
            this.gameProfiles.clear();

            for (java.util.Iterator<?> __recoveryIterator171_14185 =
                            (Lists.reverse(list)).iterator();
                    (__recoveryIterator171_14185).hasNext(); ) {
                PlayerProfileCacheProfileEntry playerProfileCacheProfileEntry =
                        (PlayerProfileCacheProfileEntry) (__recoveryIterator171_14185).next();
                try {
                    if (false)
                        throw (java.io.FileNotFoundException)
                                null; // Original JVM checked handler; no emitted instructions.
                    if (playerProfileCacheProfileEntry != null) {
                        this.addEntry(
                                playerProfileCacheProfileEntry.getGameProfile(),
                                playerProfileCacheProfileEntry.getExpirationDate());
                    }
                } catch (FileNotFoundException filenotfoundexception) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(
                            method_fd3f0eb1dd2c178d63ce(filenotfoundexception));
                }
            }
        }
    }
}
