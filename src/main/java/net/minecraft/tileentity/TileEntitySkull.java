package net.minecraft.tileentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.util.UUID;

public class TileEntitySkull extends TileEntity {

    private int skullRotation;

    private GameProfile playerProfile = null;

    private int skullType;

    public void readFromNBT(CompoundTag compoundTag) {
        MobSpawnerBaseLogic.GU();

        super.readFromNBT(compoundTag);
        this.skullType = compoundTag.getByte("SkullType");

        this.skullRotation = compoundTag.getByte("Rot");
        if (this.skullType == 3) {
            if (compoundTag.hasKey("Owner", 10)) {
                this.playerProfile =
                        NBTUtil.readGameProfileFromNBT(compoundTag.getCompoundTag("Owner"));

                return;
            }

            if (compoundTag.hasKey("ExtraType", 8)) {
                String text = compoundTag.getString("ExtraType");
                if (!StringUtils.isNullOrEmpty(text)) {
                    this.playerProfile = new GameProfile((UUID) null, text);
                    this.updatePlayerProfile();
                }
            }
        }
    }

    public GameProfile getPlayerProfile() {
        return this.playerProfile;
    }

    private void updatePlayerProfile() {
        this.playerProfile = updateGameprofile(this.playerProfile);
        this.markDirty();
    }

    public void setPlayerProfile(GameProfile gameprofile) {
        this.skullType = 3;
        this.playerProfile = gameprofile;
        this.updatePlayerProfile();
    }


    public int getSkullType() {
        return this.skullType;
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.setByte("SkullType", (byte) (this.skullType & 0xFF));
        compoundTag.setByte("Rot", (byte) (this.skullRotation & 0xFF));
        if (this.playerProfile != null) {
            CompoundTag compoundTagValue = new CompoundTag();
            NBTUtil.writeGameProfile(compoundTagValue, this.playerProfile);
            compoundTag.setTag("Owner", compoundTagValue);
        }
    }

    public Packet getDescriptionPacket() {
        CompoundTag compoundTag = new CompoundTag();
        this.writeToNBT(compoundTag);
        return new S35PacketUpdateTileEntity(this.pos, 4, compoundTag);
    }

    public int getSkullRotation() {
        return this.skullRotation;
    }

    public static GameProfile updateGameprofile(GameProfile gameprofile) {
        if (gameprofile != null && !StringUtils.isNullOrEmpty(gameprofile.getName())) {
            if (gameprofile.isComplete() && gameprofile.getProperties().containsKey("textures")) {
                return gameprofile;
            }

            if (MinecraftServer.getServer() == null) {
                return gameprofile;
            }

            GameProfile gameprofile1 =
                    MinecraftServer.getServer()
                            .getPlayerProfileCache()
                            .getGameProfileForUsername(gameprofile.getName());
            if (gameprofile1 == null) {
                return gameprofile;
            }

            Property property =
                    (Property)
                            Iterables.getFirst(gameprofile1.getProperties().get("textures"), null);
            if (property == null) {
                gameprofile1 =
                        MinecraftServer.getServer()
                                .getMinecraftSessionService()
                                .fillProfileProperties(gameprofile1, true);
            }

            return gameprofile1;
        } else {
            return gameprofile;
        }
    }

    public void setType(int skullTypeValue) {
        this.skullType = skullTypeValue;
        this.playerProfile = null;
    }

    public void setSkullRotation(int skullRotationValue) {
        this.skullRotation = skullRotationValue;
    }
}
