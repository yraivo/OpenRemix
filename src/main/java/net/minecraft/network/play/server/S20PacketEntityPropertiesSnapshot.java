package net.minecraft.network.play.server;

import java.util.Collection;

public class S20PacketEntityPropertiesSnapshot {
    S20PacketEntityProperties bxL;
    private Collection bxM;
    private String a;
    private double um;

    public double getBaseValue() {
        return this.um;
    }

    public Collection getModifiers() {
        return this.bxM;
    }

    public S20PacketEntityPropertiesSnapshot(
            S20PacketEntityProperties bxLValue, String text, double d0, Collection bxMValue) {
        this.bxL = bxLValue;
        this.a = text;
        this.um = d0;
        this.bxM = bxMValue;
    }

    public String getAttributeName() {
        return this.a;
    }
}
