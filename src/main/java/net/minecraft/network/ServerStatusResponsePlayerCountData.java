package net.minecraft.network;

import com.mojang.authlib.GameProfile;

public class ServerStatusResponsePlayerCountData {
    private int maxPlayers;
    private int onlinePlayerCount;
    private GameProfile[] players;

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setPlayers(GameProfile[] agameprofile) {
        this.players = agameprofile;
    }

    public ServerStatusResponsePlayerCountData(int maxPlayersValue, int j) {
        this.maxPlayers = maxPlayersValue;
        this.onlinePlayerCount = j;
    }

    public int getOnlinePlayerCount() {
        return this.onlinePlayerCount;
    }

    public GameProfile[] getPlayers() {
        return this.players;
    }
}
