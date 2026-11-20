package net.minecraft.network;

import java.util.concurrent.Callable;

class ServerPacketTypeCrashDetail implements Callable {
    Packet field_180227_a;
    NetHandlerPlayServer field_180226_b;

    ServerPacketTypeCrashDetail(NetHandlerPlayServer bvJValue, Packet bvjValue) {
        this.field_180226_b = bvJValue;
        this.field_180227_a = bvjValue;
    }

    @Override
    public Object call() {
        return this.func_180225_a();
    }

    public String func_180225_a() {
        return this.field_180227_a.getClass().getCanonicalName();
    }
}
