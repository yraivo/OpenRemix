package remix.event.impl;

import remix.event.CancellableEvent;
import remix.event.PacketDirection;

public class ChatMessageEvent extends CancellableEvent {
    private String message;
    private PacketDirection packetDirection;

    public PacketDirection getPacketDirection() {
        return this.packetDirection;
    }

    public ChatMessageEvent(String text, PacketDirection packetDirection) {
        this.message = text;
        this.packetDirection = packetDirection;
    }

    public void setMessage(String text) {
        this.message = text;
    }

    public String getMessage() {
        return this.message;
    }
}
