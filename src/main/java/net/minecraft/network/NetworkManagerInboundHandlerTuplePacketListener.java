package net.minecraft.network;

import io.netty.util.concurrent.GenericFutureListener;

class NetworkManagerInboundHandlerTuplePacketListener {
    private GenericFutureListener[] futureListeners;
    private Packet packet;

    public NetworkManagerInboundHandlerTuplePacketListener(
            Packet bvjValue, GenericFutureListener... agenericfuturelistener) {
        this.packet = bvjValue;
        this.futureListeners = agenericfuturelistener;
    }

    static GenericFutureListener[] getQueuedListeners(
            NetworkManagerInboundHandlerTuplePacketListener networkManagerInboundHandlerTuplePacketListener) {
        return networkManagerInboundHandlerTuplePacketListener.futureListeners;
    }

    static Packet getQueuedPacket(
            NetworkManagerInboundHandlerTuplePacketListener networkManagerInboundHandlerTuplePacketListener) {
        return networkManagerInboundHandlerTuplePacketListener.packet;
    }
}
