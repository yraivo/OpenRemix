package remix.command;

import remix.module.exploit.VanishDetectorModule;

import java.io.IOException;
import java.util.UUID;

public class VanishCheckThread extends Thread {

    private static String[] packedTextFragments;

    private UUID uuid;

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    @Override
    public void run() {
        try {
            String text = VanishDetectorModule.lookupProfileName(this.uuid);

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (!text.equals(packedTextFragments[0])) {
                    VanishDetectorModule.getClientServices();
                    ClientCommandManager.printMessage("Player \"" + text + "\" has vanished.");
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
            }
        } catch (IOException ioexception1) {
        }
    }

    static {
        remix.command.VanishCheckThread.packedTextFragments = new String[2];
        remix.command.VanishCheckThread.packedTextFragments[0] = new String(".");
    }

    public VanishCheckThread(UUID uuid) {
        this.uuid = uuid;
    }
}
