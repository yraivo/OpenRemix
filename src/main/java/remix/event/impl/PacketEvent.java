package remix.event.impl;

import remix.event.CancellableEvent;
import remix.event.PacketDirection;

import net.minecraft.network.Packet;

public class PacketEvent extends CancellableEvent {
    private static boolean e;
    private PacketDirection direction;
    private Packet packet;

    public PacketDirection getDirection() {
        return this.direction;
    }

    public void setPacket(Packet packetValue) {
        this.packet = packetValue;
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        e = flag;
    }

    // region Static initialization and string tables
    static {
        method_d87039b07bd6961907f2();
    }

    // endregion

    public static boolean method_d87039b07bd6961907f2() {
        return e;
    }

    public PacketEvent(PacketDirection directionValue, Packet packetValue) {
        this.packet = packetValue;
        this.direction = directionValue;
    }

    public static boolean d() {
        boolean flag = method_d87039b07bd6961907f2();
        return !flag;
    }

    public Packet getPacket() {
        return this.packet;
    }
}
