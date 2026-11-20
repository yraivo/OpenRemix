package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.ServerPlayPacketHandler;

import java.io.IOException;

public class C13PacketPlayerAbilities implements Packet {
    private boolean invulnerable;
    private boolean creativeMode;
    private boolean flying;
    private float walkSpeed;
    private float flySpeed;
    private boolean allowFlying;

    public void func_180761_a(ServerPlayPacketHandler serverPlayPacketHandler) {
        serverPlayPacketHandler.processPlayerAbilities(this);
    }

    public void setWalkSpeed(float walkSpeedValue) {
        this.walkSpeed = walkSpeedValue;
    }

    public boolean isAllowFlying() {
        return this.allowFlying;
    }

    public void setFlying(boolean flyingValue) {
        this.flying = flyingValue;
    }

    public void setInvulnerable(boolean invulnerableValue) {
        this.invulnerable = invulnerableValue;
    }

    public C13PacketPlayerAbilities(PlayerCapabilities playerCapabilities) {
        this.setInvulnerable(playerCapabilities.disableDamage);
        this.setFlying(playerCapabilities.isFlying);
        this.setAllowFlying(playerCapabilities.allowFlying);
        this.setCreativeMode(playerCapabilities.isCreativeMode);
        this.setFlySpeed(playerCapabilities.getFlySpeed());
        this.setWalkSpeed(playerCapabilities.getWalkSpeed());
    }

    public boolean isCreativeMode() {
        return this.creativeMode;
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

    public C13PacketPlayerAbilities() {}

    public void setCreativeMode(boolean creativeModeValue) {
        this.creativeMode = creativeModeValue;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180761_a((ServerPlayPacketHandler) packetHandler);
    }

    public void setAllowFlying(boolean allowFlyingValue) {
        this.allowFlying = allowFlyingValue;
    }

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int j = PlayerMovementPacket.cM();
        byte b0 = packetBuffer.readByte();
        int i = j;

        C13PacketPlayerAbilities c13PacketPlayerAbilities,
                c13PacketPlayerAbilitiesValue,
                c13PacketPlayerAbilitiesValue2,
                c13PacketPlayerAbilitiesValue3;
        byte b1;
        label61:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                c13PacketPlayerAbilities = this;
                if ((b0 & 1) > 0) {
                    b1 = 1;
                    break label61;
                }
            } catch (IOException ioexception4) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception4));
            }

            b1 = 0;
        }

        label54:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                c13PacketPlayerAbilities.setInvulnerable(
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
                c13PacketPlayerAbilitiesValue = this;
                if ((b0 & 2) > 0) {
                    b1 = 1;
                    break label54;
                }
            } catch (IOException ioexception3) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception3));
            }

            b1 = 0;
        }

        label47:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                c13PacketPlayerAbilitiesValue.setFlying(
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
                c13PacketPlayerAbilitiesValue2 = this;
                if ((b0 & 4) > 0) {
                    b1 = 1;
                    break label47;
                }
            } catch (IOException ioexception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception2));
            }

            b1 = 0;
        }

        label40:
        {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                c13PacketPlayerAbilitiesValue2.setAllowFlying(
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
                c13PacketPlayerAbilitiesValue3 = this;
                if ((b0 & 8) > 0) {
                    b1 = 1;
                    break label40;
                }
            } catch (IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
            }

            b1 = 0;
        }

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            c13PacketPlayerAbilitiesValue3.setCreativeMode(
                    remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
            this.setFlySpeed(packetBuffer.readFloat());
            this.setWalkSpeed(packetBuffer.readFloat());
            if (i != 0) {
                Entity.setLegacyGlobalFlowMarker(new String[5]);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public void setFlySpeed(float flySpeedValue) {
        this.flySpeed = flySpeedValue;
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
