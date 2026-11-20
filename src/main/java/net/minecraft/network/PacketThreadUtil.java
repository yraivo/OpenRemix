package net.minecraft.network;

import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
    public static void checkThreadAndEnqueue(
            Packet packet, PacketHandler packetHandler, IThreadListener iThreadListener) {
        try {
            if (!iThreadListener.isCallingFromMinecraftThread()) {
                iThreadListener.addScheduledTask(
                        new CheckThreadAndEnqueueRunnable(packet, packetHandler));
                throw ThreadQuickExitException.bNE;
            }
        } catch (ThreadQuickExitException threadQuickExitException) {
            throw method_076682ed613f72d00295(threadQuickExitException);
        }
    }

    private static ThreadQuickExitException method_076682ed613f72d00295(
            ThreadQuickExitException threadQuickExitException) {
        return threadQuickExitException;
    }

    // Helper used only by this class.
    public static final class CheckThreadAndEnqueueRunnable implements java.lang.Runnable {
        net.minecraft.network.Packet bvj;
        net.minecraft.network.PacketHandler bvk;

        public CheckThreadAndEnqueueRunnable(
                net.minecraft.network.Packet bvjValue,
                net.minecraft.network.PacketHandler bvkValue) {
            this.bvj = bvjValue;
            this.bvk = bvkValue;
        }

        @java.lang.Override
        public void run() {
            this.bvj.processPacket(this.bvk);
        }
    }
}
