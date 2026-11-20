package net.minecraft.client.multiplayer;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;

public class ServerData {
    public String gameVersion;
    public String serverIP;
    public boolean ie;
    public String populationInfo;

    public long pingToServer;
    public String playerList;

    private String serverIcon;
    public String serverMOTD;
    private ServerDataServerResourceMode resourceMode;
    public int version = 47;

    public String serverName;

    public void setResourceMode(ServerDataServerResourceMode awzValue) {
        this.resourceMode = awzValue;
    }

    public static ServerData getServerDataFromNBTCompound(CompoundTag compoundTag) {
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();
        ServerData serverData =
                new ServerData(compoundTag.getString("name"), compoundTag.getString("ip"));
        Entity[] entityArrayValue = entityArray;
        if (compoundTag.hasKey("icon", 8)) {
            serverData.setBase64EncodedIconData(compoundTag.getString("icon"));
        }

        if (compoundTag.hasKey("acceptTextures", 1)) {
            if (compoundTag.getBoolean("acceptTextures")) {
                serverData.setResourceMode(ServerDataServerResourceMode.ENABLED);
                if (entityArrayValue == null) {
                    return serverData;
                }
            }

            serverData.setResourceMode(ServerDataServerResourceMode.DISABLED);
            if (entityArrayValue == null) {
                return serverData;
            }
        }

        serverData.setResourceMode(ServerDataServerResourceMode.PROMPT);
        return serverData;
    }

    public ServerDataServerResourceMode getResourceMode() {
        return this.resourceMode;
    }

    public ServerData(String text, String textValue) {
        this.gameVersion = "1.8";
        this.resourceMode = ServerDataServerResourceMode.PROMPT;
        this.serverName = text;
        this.serverIP = textValue;
    }

    public void copyFrom(ServerData serverData) {
        this.serverIP = serverData.serverIP;
        this.serverName = serverData.serverName;
        this.setResourceMode(serverData.getResourceMode());
        this.serverIcon = serverData.serverIcon;
    }


    public CompoundTag getNBTCompound() {
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();
        CompoundTag compoundTag = new CompoundTag();
        Entity[] entityArrayValue = entityArray;
        compoundTag.setString("name", this.serverName);
        compoundTag.setString("ip", this.serverIP);
        if (this.serverIcon != null) {
            compoundTag.setString("icon", this.serverIcon);
        }

        if (this.resourceMode == ServerDataServerResourceMode.ENABLED) {
            compoundTag.setBoolean("acceptTextures", true);
            if (entityArrayValue == null) {
                return compoundTag;
            }
        }

        if (this.resourceMode == ServerDataServerResourceMode.DISABLED) {
            compoundTag.setBoolean("acceptTextures", false);
        }

        return compoundTag;
    }

    public void setBase64EncodedIconData(String text) {
        this.serverIcon = text;
    }

    public String getBase64EncodedIconData() {
        return this.serverIcon;
    }
}
