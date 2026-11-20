package net.minecraft.client.gui;

import net.minecraft.util.ChatComponent;

public class ChatLine {
    private int chatLineID;
    private ChatComponent lineString;
    private int updateCounterCreated;

    public ChatLine(int i, ChatComponent aqMValue, int j) {
        this.lineString = aqMValue;
        this.updateCounterCreated = i;
        this.chatLineID = j;
    }

    public int getUpdatedCounter() {
        return this.updateCounterCreated;
    }

    public int getChatLineID() {
        return this.chatLineID;
    }

    public ChatComponent getChatComponent() {
        return this.lineString;
    }
}
