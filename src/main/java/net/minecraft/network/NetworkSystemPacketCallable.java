package net.minecraft.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkSystem;

import java.util.concurrent.Callable;

public class NetworkSystemPacketCallable implements Callable {
    NetworkSystem networkSystem;
    NetworkManager networkManager;

    @Override
    public Object call() {
        return this.method_e0e3092a59fee58fae52();
    }

    public String method_e0e3092a59fee58fae52() {
        return this.networkManager.toString();
    }

    public NetworkSystemPacketCallable(NetworkSystem baQValue, NetworkManager awSValue) {
        this.networkSystem = baQValue;
        this.networkManager = awSValue;
    }
}
