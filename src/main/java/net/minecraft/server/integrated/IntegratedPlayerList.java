package net.minecraft.server.integrated;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

import com.mojang.authlib.GameProfile;

import java.net.SocketAddress;

public class IntegratedPlayerList extends ServerConfigurationManager {
    private static String jU;
    private CompoundTag hostPlayerData;

    public MinecraftServer getServerInstance() {
        return this.RE();
    }

    protected void writePlayerData(EntityPlayerMP entityPlayerMP) {
        if (entityPlayerMP.getName().equals(this.RE().getServerOwner())) {
            this.hostPlayerData = new CompoundTag();
            entityPlayerMP.writeToNBT(this.hostPlayerData);
        }

        super.writePlayerData(entityPlayerMP);
    }

    public CompoundTag getHostPlayerData() {
        return this.hostPlayerData;
    }

    // region String decoding support

    // endregion

    static {
        jU = "That name is already taken.";
    }

    public String allowUserToConnect(
            SocketAddress socketaddress, GameProfile gameprofile) {
        return gameprofile.getName().equalsIgnoreCase(this.RE().getServerOwner())
                        && this.getPlayerByUsername(gameprofile.getName()) != null
                ? jU
                : super.allowUserToConnect(socketaddress, gameprofile);
    }

    public IntegratedServer RE() {
        return (IntegratedServer) super.getServerInstance();
    }

    // region String decoding support

    // endregion

    public IntegratedPlayerList(IntegratedServer integratedServer) {
        super(integratedServer);
        this.a(10);
    }
}
