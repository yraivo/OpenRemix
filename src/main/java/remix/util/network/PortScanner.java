package remix.util.network;

import remix.command.AbstractClientCommand;

import net.minecraft.entity.Entity;
import remix.command.ClientCommandManager;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class PortScanner {
    private static boolean scanning;
    private static ExecutorService probeExecutor;

    private static int lastRequestedPort;

    private static String[] packedTextFragments;
    public static List<Integer> openPorts;
    private static int submittedProbeCount;

    public void scanPortRange(String text, int i, int j, int k, int l) {
        if (i < 1 || i > 65535 || j < 1 || j > 65535 || i > j || l < 1 || l > 32 || k < 1
                || k > 30000) {
            throw new IllegalArgumentException(
                    "Invalid port range, thread count, or connect timeout");
        }
        int i1 = AbstractClientCommand.getLegacyCommandFlowMarker();
        if (!remix.util.network.PortScanner.openPorts.isEmpty()) {
            remix.util.network.PortScanner.openPorts.clear();
        }

        remix.util.network.PortScanner.scanning = true;
        remix.util.network.PortScanner.probeExecutor = Executors.newFixedThreadPool(l);
        this.runPortProbes(text, i, j, k);
        remix.util.network.PortScanner.lastRequestedPort = j;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AbstractClientCommand.setLegacyCommandFlowMarker(++i1);
        }
    }

    static {
        remix.util.network.PortScanner.packedTextFragments = new String[3];
        remix.util.network.PortScanner.packedTextFragments[0] = new String(":");
        remix.util.network.PortScanner.openPorts = new ArrayList<>();
    }

    private void runPortProbes(String address, int firstPort, int lastPort, int timeout) {
        String host = address.split(packedTextFragments[0])[0];
        List<Future<Boolean>> probes = new ArrayList<>();
        boolean legacyFirstProbeOnly = AbstractClientCommand.getStandardCommandFlowToken() == 0;
        for (int port = firstPort; port <= lastPort; port++) {
            probes.add(this.submitPortProbe(remix.util.network.PortScanner.probeExecutor, host, port, timeout));
            remix.util.network.PortScanner.submittedProbeCount++;
            if (legacyFirstProbeOnly) {
                break;
            }
        }
        remix.util.network.PortScanner.probeExecutor.shutdown();

        int openPortCount = 0;
        for (Future<Boolean> probe : probes) {
            try {
                remix.util.network.PortScanner.scanning = true;
                boolean portOpen = probe.get();
                if (portOpen || legacyFirstProbeOnly) {
                    openPortCount++;
                }
            } catch (InterruptedException | ExecutionException failure) {
                remix.util.network.PortScanner.scanning = false;
                failure.printStackTrace();
            }
            if (legacyFirstProbeOnly) {
                break;
            }
        }

        System.out.println(
                "There are " + openPortCount + " open ports on host " + host
                        + " (probed with a timeout of " + timeout + "ms)");
    }

    private Future<Boolean> submitPortProbe(
            ExecutorService executorservice, String text, int i, int j) {
        return executorservice.submit(() -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(text, i), j);
                socket.close();
                remix.util.network.PortScanner.openPorts.add(i);
                ClientCommandManager.printMessage("Open port found: " + i);
                return true;
            } catch (Exception exception) {
                return false;
            }
        });
    }

    private PortScanner() {}

    public static PortScanner create() {
        return new PortScanner();
    }

    private void stopPortScan() {
        remix.util.network.PortScanner.scanning = false;
        remix.util.network.PortScanner.probeExecutor.shutdownNow();
    }

    private String formatScanProgress() {
        return String.format("Scanned ports: " + remix.util.network.PortScanner.submittedProbeCount + "/%s!", remix.util.network.PortScanner.lastRequestedPort);
    }
}
