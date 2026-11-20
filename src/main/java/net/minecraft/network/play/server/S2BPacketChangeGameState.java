package net.minecraft.network.play.server;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S2BPacketChangeGameState implements Packet {
    private int state;
    public static String[] MESSAGE_NAMES;

    private float field_149141_c;

    public float getValue() {
        return this.field_149141_c;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerPlayClient) packetHandler);
    }

    // region String decoding support

    // endregion

    public S2BPacketChangeGameState() {}

    public void processPacket(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleChangeGameState(this);
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.state);
        packetBuffer.writeFloat(this.field_149141_c);
    }

    static {
        net.minecraft.network.play.server.S2BPacketChangeGameState.MESSAGE_NAMES = new String[] {"tile.bed.notValid"};
    }

    // region String decoding support

    // endregion

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.state = packetBuffer.readUnsignedByte();
        this.field_149141_c = packetBuffer.readFloat();
    }

    public int getGameState() {
        return this.state;
    }

    public S2BPacketChangeGameState(int stateValue, float f) {
        this.state = stateValue;
        this.field_149141_c = f;
    }
}
