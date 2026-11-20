package net.minecraft.client.multiplayer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagList;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public class ServerList {
    private final List servers = Lists.newArrayList();

    private static Logger logger;
    private MinecraftClient mc;

    public void addServerData(ServerData serverData) {
        this.servers.add(serverData);
    }

    public void removeServerData(int i) {
        this.servers.remove(i);
    }

    public void swapServers(int i, int j) {
        ServerData serverData = this.getServerData(i);
        this.servers.set(i, this.getServerData(j));
        this.servers.set(j, serverData);
        this.saveServerList();
    }

    static {
        net.minecraft.client.multiplayer.ServerList.logger = LogManager.getLogger();
    }

    public int countServers() {
        return this.servers.size();
    }

    public void method_f9b18da8a8fd26e52116(int i, ServerData serverData) {
        this.servers.set(i, serverData);
    }

    public void saveServerList() {
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();

        try {
            NBTTagList nbtTagList = new NBTTagList();

            for (java.util.Iterator<?> __recoveryIterator171_27257 = (this.servers).iterator();
                    (__recoveryIterator171_27257).hasNext(); ) {
                ServerData serverData = (ServerData) (__recoveryIterator171_27257).next();
                nbtTagList.appendTag(serverData.getNBTCompound());
                if (entityArray != null) {
                    break;
                }
            }

            CompoundTag compoundTag = new CompoundTag();
            compoundTag.setTag("servers", nbtTagList);
            CompressedStreamTools.safeWrite(
                    compoundTag,
                    new File(
                            remix.client.ClientPaths.serverListDirectory(this.mc.gameDirectory),
                            "servers.dat"));
        } catch (Exception exception) {
            net.minecraft.client.multiplayer.ServerList.logger.error("Couldn't save server list", exception);
        }
    }

    public ServerList(MinecraftClient minecraftClient) {
        this.mc = minecraftClient;
        this.loadServerList();
    }

    public static void saveSingleServer(ServerData serverData) {
        ServerList serverList = new ServerList(MinecraftClient.getMinecraft());
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();
        serverList.loadServerList();
        Entity[] entityArrayValue = entityArray;
        int i = 0;

        while (i < serverList.countServers()) {
            ServerData serverDataValue = serverList.getServerData(i);
            if (serverDataValue.serverName.equals(serverData.serverName)
                    && serverDataValue.serverIP.equals(serverData.serverIP)) {
                serverList.method_f9b18da8a8fd26e52116(i, serverData);
                if (entityArrayValue == null) {
                    break;
                }
            }

            i++;
            if (entityArrayValue != null) {
                break;
            }
        }

        serverList.saveServerList();
    }

    public ServerData getServerData(int i) {
        return (ServerData) this.servers.get(i);
    }

    public void loadServerList() {
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();

        try {
            this.servers.clear();
            CompoundTag compoundTag =
                    CompressedStreamTools.read(
                            new File(
                                    remix.client.ClientPaths.serverListDirectory(
                                            this.mc.gameDirectory),
                                    "servers.dat"));
            if (compoundTag == null) {
                return;
            }

            NBTTagList nbtTagList = compoundTag.getTagList("servers", 10);
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                this.servers.add(
                        ServerData.getServerDataFromNBTCompound(nbtTagList.getCompoundTagAt(i)));
                i++;
                if (entityArray != null) {
                    break;
                }
            }
        } catch (Exception exception) {
            net.minecraft.client.multiplayer.ServerList.logger.error("Couldn't load server list", exception);
        }
    }
}
