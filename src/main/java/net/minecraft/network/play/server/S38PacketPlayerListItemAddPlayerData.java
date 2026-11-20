package net.minecraft.network.play.server;

import net.minecraft.util.ChatComponent;
import net.minecraft.world.WorldSettingsGameType;

import com.mojang.authlib.GameProfile;

public class S38PacketPlayerListItemAddPlayerData {
    S38PacketPlayerListItem field_179968_a;
    private int ping;
    private GameProfile profile;
    private ChatComponent displayName;
    private WorldSettingsGameType gamemode;

    public GameProfile getProfile() {
        return this.profile;
    }

    public int getPing() {
        return this.ping;
    }

    public S38PacketPlayerListItemAddPlayerData(
            S38PacketPlayerListItem bxsValue,
            GameProfile gameprofile,
            int pingValue,
            WorldSettingsGameType avVValue,
            ChatComponent agLValue) {
        this.field_179968_a = bxsValue;
        this.profile = gameprofile;
        this.ping = pingValue;
        this.gamemode = avVValue;
        this.displayName = agLValue;
    }

    public ChatComponent getDisplayName() {
        return this.displayName;
    }

    public WorldSettingsGameType getGameMode() {
        return this.gamemode;
    }
}
