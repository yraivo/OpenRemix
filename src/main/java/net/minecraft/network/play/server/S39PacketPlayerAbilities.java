package net.minecraft.network.play.server;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S39PacketPlayerAbilities implements Packet {
    private float walkSpeed;
    private boolean allowFlying;
    private float flySpeed;
    private boolean invulnerable;
    private boolean flying;
    private boolean creativeMode;

    public void setFlySpeed(float flySpeedValue) {
        this.flySpeed = flySpeedValue;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180742_a((INetHandlerPlayClient) packetHandler);
    }

    public void setFlying(boolean flyingValue) {
        this.flying = flyingValue;
    }

    public boolean isAllowFlying() {
        return this.allowFlying;
    }

    public S39PacketPlayerAbilities(PlayerCapabilities playerCapabilities) {
        this.setInvulnerable(playerCapabilities.disableDamage);
        this.setFlying(playerCapabilities.isFlying);
        this.setAllowFlying(playerCapabilities.allowFlying);
        this.setCreativeMode(playerCapabilities.isCreativeMode);
        this.setFlySpeed(playerCapabilities.getFlySpeed());
        this.setWalkSpeed(playerCapabilities.getWalkSpeed());
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public void setAllowFlying(boolean allowFlyingValue) {
        this.allowFlying = allowFlyingValue;
    }

    public void setCreativeMode(boolean creativeModeValue) {
        this.creativeMode = creativeModeValue;
    }

    public void func_180742_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handlePlayerAbilities(this);
    }

    public boolean isFlying() {
        return this.flying;
    }

    public void setInvulnerable(boolean invulnerableValue) {
        this.invulnerable = invulnerableValue;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        byte b0 = 0;
        if (this.isInvulnerable()) {
            b0 = (byte) (b0 | 1);
        }

        if (this.isFlying()) {
            b0 = (byte) (b0 | 2);
        }

        if (this.isAllowFlying()) {
            b0 = (byte) (b0 | 4);
        }

        if (this.isCreativeMode()) {
            b0 = (byte) (b0 | 8);
        }

        packetBuffer.writeByte(b0);
        packetBuffer.writeFloat(this.flySpeed);
        packetBuffer.writeFloat(this.walkSpeed);
    }

    public S39PacketPlayerAbilities() {}

    public boolean isCreativeMode() {
        return this.creativeMode;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        byte b0 = packetBuffer.readByte();

        net.minecraft.network.play.server.S39PacketPlayerAbilities s39PacketPlayerAbilities;
        byte b1;
        label53:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                s39PacketPlayerAbilities = this;
                if ((b0 & 1) > 0) {
                    b1 = 1;
                    break label53;
                }
            } catch (java.io.IOException ioexception3) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.server.S39PacketPlayerAbilities.a(ioexception3));
            }

            b1 = 0;
        }

        label46:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                s39PacketPlayerAbilities.setInvulnerable((b1 != 0));
                s39PacketPlayerAbilities = this;
                if ((b0 & 2) > 0) {
                    b1 = 1;
                    break label46;
                }
            } catch (java.io.IOException ioexception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.server.S39PacketPlayerAbilities.a(ioexception2));
            }

            b1 = 0;
        }

        label39:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                s39PacketPlayerAbilities.setFlying((b1 != 0));
                s39PacketPlayerAbilities = this;
                if ((b0 & 4) > 0) {
                    b1 = 1;
                    break label39;
                }
            } catch (java.io.IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.server.S39PacketPlayerAbilities.a(ioexception1));
            }

            b1 = 0;
        }

        label32:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                s39PacketPlayerAbilities.setAllowFlying((b1 != 0));
                s39PacketPlayerAbilities = this;
                if ((b0 & 8) > 0) {
                    b1 = 1;
                    break label32;
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.network.play.server.S39PacketPlayerAbilities.a(ioexception));
            }

            b1 = 0;
        }

        s39PacketPlayerAbilities.setCreativeMode((b1 != 0));
        this.setFlySpeed(packetBuffer.readFloat());
        this.setWalkSpeed(packetBuffer.readFloat());
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void setWalkSpeed(float walkSpeedValue) {
        this.walkSpeed = walkSpeedValue;
    }
}
