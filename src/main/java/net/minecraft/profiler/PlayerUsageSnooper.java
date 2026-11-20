package net.minecraft.profiler;

import com.google.common.collect.Maps;

import net.minecraft.entity.Entity;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.UUID;

public class PlayerUsageSnooper {
    private int selfCounter;
    private long minecraftStartTimeMilis;
    private IPlayerUsage playerStatsCollector;
    private Map dJ;
    private static String[] packedTextFragments;

    private boolean isRunning;
    private Object syncLock;
    private String uniqueID;
    private URL serverUrl;
    private Map dI;
    private Timer threadTrigger;

    static {
        net.minecraft.profiler.PlayerUsageSnooper.packedTextFragments = new String[3];
        net.minecraft.profiler.PlayerUsageSnooper.packedTextFragments[0] = new String("]");
    }

    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }

    public void addClientStat(String text, Object object) {
        Object syncLockValue = this.syncLock;
        synchronized (this.syncLock) {
            this.dJ.put(text, object);
        }
    }

    public void clearRunningFlag() {
        this.isRunning = false;
    }

    public void addStatToSnooper(String text, Object object) {
        Object syncLockValue = this.syncLock;
        synchronized (this.syncLock) {
            this.dI.put(text, object);
        }
    }

    static int access$308(PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.selfCounter++;
    }

    public PlayerUsageSnooper(
            String text, IPlayerUsage bAyValue, long minecraftStartTimeMilisValue) {
        Profiler.cB();

        this.dI = Maps.newHashMap();
        this.dJ = Maps.newHashMap();

        this.uniqueID = UUID.randomUUID().toString();
        this.threadTrigger = new Timer("Snooper Timer", true);
        this.syncLock = new Object();

        try {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + text + "?version=" + 2);
        } catch (MalformedURLException malformedurlexception1) {
            throw new IllegalArgumentException();
        }

        try {
            if (false)
                throw (java.net.MalformedURLException)
                        null; // Original JVM checked handler; no emitted instructions.
            this.playerStatsCollector = bAyValue;
            this.minecraftStartTimeMilis = minecraftStartTimeMilisValue;

        } catch (MalformedURLException malformedurlexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(malformedurlexception));
        }
    }

    static URL access$600(PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.serverUrl;
    }

    static IPlayerUsage access$000(PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.playerStatsCollector;
    }

    static Map method_78b4872ec9d0e59ee07e(PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.dI;
    }

    private void func_152766_h() {
        this.addJvmArgsToSnooper();
        this.addClientStat("snooper_token", this.uniqueID);
        this.addStatToSnooper("snooper_token", this.uniqueID);
        this.addStatToSnooper("os_name", System.getProperty("os.name"));
        this.addStatToSnooper("os_version", System.getProperty("os.version"));
        this.addStatToSnooper("os_architecture", System.getProperty("os.arch"));
        this.addStatToSnooper("java_version", System.getProperty("java.version"));
        this.addStatToSnooper("version", "1.8");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }

    public void addMemoryStatsToSnooper() {
        this.addStatToSnooper("memory_total", Runtime.getRuntime().totalMemory());
        this.addStatToSnooper("memory_max", Runtime.getRuntime().maxMemory());
        this.addStatToSnooper("memory_free", Runtime.getRuntime().freeMemory());
        this.addStatToSnooper("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }

    public void stopSnooper() {
        this.threadTrigger.cancel();
    }

    static Object access$100(PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.syncLock;
    }

    public boolean isSnooperRunning() {
        return false;
    }

    static String access$500(PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.uniqueID;
    }

    static Map method_f1162ad21cd003e46b2e(PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.dJ;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public Map getCurrentStats() {
        Profiler.cB();

        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();

        Object syncLockValue = this.syncLock;
        LinkedHashMap linkedhashmap1;
        synchronized (this.syncLock) {
            this.addMemoryStatsToSnooper();

            for (java.util.Iterator<?> __recoveryIterator171_33430 =
                            (this.dI.entrySet()).iterator();
                    (__recoveryIterator171_33430).hasNext(); ) {
                Entry entry = (Entry) (__recoveryIterator171_33430).next();
                linkedhashmap.put(entry.getKey(), entry.getValue().toString());
            }

            for (java.util.Iterator<?> __recoveryIterator171_33666 =
                            (this.dJ.entrySet()).iterator();
                    (__recoveryIterator171_33666).hasNext(); ) {
                Entry entry1 = (Entry) (__recoveryIterator171_33666).next();
                linkedhashmap.put(entry1.getKey(), entry1.getValue().toString());
            }

            linkedhashmap1 = linkedhashmap;
        }

        try {
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                Profiler.b(new Entity[4]);
            }

            return linkedhashmap1;
        } catch (IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(illegalargumentexception));
        }
    }

    private void addJvmArgsToSnooper() {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        Profiler.cB();

        List inputArguments = runtimemxbean.getInputArguments();

        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_34679 = (inputArguments).iterator();
                (__recoveryIterator171_34679).hasNext(); ) {
            String text = (String) (__recoveryIterator171_34679).next();
            try {
                if (text.startsWith("-X")) {
                    this.addClientStat("jvm_arg[" + i++ + packedTextFragments[0], text);
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(illegalargumentexception));
            }
        }

        this.addClientStat("jvm_args", i);
    }

    static int access$300(PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.selfCounter;
    }

    public String getUniqueID() {
        return this.uniqueID;
    }
}
