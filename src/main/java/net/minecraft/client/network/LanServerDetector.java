package net.minecraft.client.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class LanServerDetector {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicInteger awG = new AtomicInteger(0);

    static Logger access$100() {
        return net.minecraft.client.network.LanServerDetector.logger;
    }

    static AtomicInteger access$000() {
        return awG;
    }
}
