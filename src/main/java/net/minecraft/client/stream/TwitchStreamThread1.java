package net.minecraft.client.stream;

import net.minecraft.client.stream.IStreamAuthFailureReason;
import net.minecraft.client.stream.TwitchStream;

import com.mojang.authlib.properties.Property;

import java.io.IOException;

public class TwitchStreamThread1 extends Thread {
    Property aOH;

    TwitchStream aOJ;

    @Override
    public void run() {
        TwitchStream.access$302(this.aOJ, IStreamAuthFailureReason.ERROR);
    }


    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public TwitchStreamThread1(TwitchStream aOJValue, String text, Property property) {
        super(text);
        this.aOJ = aOJValue;
        this.aOH = property;
    }
}
