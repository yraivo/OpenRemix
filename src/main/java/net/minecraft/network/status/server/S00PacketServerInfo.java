package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.ServerStatusResponseMinecraftProtocolVersionIdentifier;
import net.minecraft.network.ServerStatusResponseMinecraftProtocolVersionIdentifierSerializer;
import net.minecraft.network.ServerStatusResponsePlayerCountData;
import net.minecraft.network.ServerStatusResponsePlayerCountDataSerializer;
import net.minecraft.network.ServerStatusResponseSerializer;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChatStyleSerializer;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponentSerializer;

import java.io.IOException;

public class S00PacketServerInfo implements Packet {
    private ServerStatusResponse response;
    private static String[] packedTextFragments;
    private static final Gson GSON =
            new GsonBuilder()
                    .registerTypeAdapter(
                            ServerStatusResponseMinecraftProtocolVersionIdentifier.class,
                            new ServerStatusResponseMinecraftProtocolVersionIdentifierSerializer())
                    .registerTypeAdapter(
                            ServerStatusResponsePlayerCountData.class,
                            new ServerStatusResponsePlayerCountDataSerializer())
                    .registerTypeAdapter(
                            ServerStatusResponse.class, new ServerStatusResponseSerializer())
                    .registerTypeHierarchyAdapter(
                            ChatComponent.class, new IChatComponentSerializer())
                    .registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyleSerializer())
                    .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
                    .create();

    public void processPacket(PacketHandler packetHandler) {
        this.processPacket((INetHandlerStatusClient) packetHandler);
    }

    public S00PacketServerInfo(ServerStatusResponse bxgValue) {
        this.response = bxgValue;
    }

    public void processPacket(INetHandlerStatusClient iNetHandlerStatusClient) {
        iNetHandlerStatusClient.handleServerInfo(this);
    }

    // region String decoding support

    // endregion

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        S01PacketPong.method_c60693f457aa70be7160();
        packetBuffer.writeString(
                net.minecraft.network.status.server.S00PacketServerInfo.GSON.toJson(this.response));

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                S01PacketPong.b(packedTextFragments[0]);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.network.status.server.S00PacketServerInfo.packedTextFragments[0] =
                new String("NXnryb");
    }

    // endregion

    public ServerStatusResponse getResponse() {
        return this.response;
    }

    public S00PacketServerInfo() {}

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        String text = S01PacketPong.method_c60693f457aa70be7160();
        this.response =
                (ServerStatusResponse)
                        net.minecraft.network.status.server.S00PacketServerInfo.GSON.fromJson(
                                packetBuffer.readStringFromBuffer(32767),
                                ServerStatusResponse.class);
        String textValue = text;

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (textValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[2]);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }
}
