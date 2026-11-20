package net.minecraft.profiler;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import optifine.Lagometer;
import optifine.OptiFineConfig;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Profiler {
    private static String[] packedTextFragments;

    private String profilingSection;
    private static String te;
    private static int rz;
    private static int kZ;
    private static Logger logger;
    private boolean ie;
    private Map profilingMap;
    private static int nO;
    private static String mC;
    public boolean profilingEnabled;
    private static int rC;
    private final List sectionList = Lists.newArrayList();
    private static String sT;

    private final List timestampList = Lists.newArrayList();
    private static String kG;
    private static Entity[] bAD;
    private static int jM;
    private static String vI;
    public boolean hZ;

    public void endSection() {
        cB();

        if (this.ie && this.profilingEnabled) {
            long k;
            {
                long i = System.nanoTime();
                long j = (Long) this.timestampList.remove(this.timestampList.size() - 1);
                this.sectionList.remove(this.sectionList.size() - 1);
                k = i - j;
                if (this.profilingMap.containsKey(this.profilingSection)) {
                    this.profilingMap.put(
                            this.profilingSection,
                            (Long) this.profilingMap.get(this.profilingSection) + k);

                    
                } else {

                    this.profilingMap.put(this.profilingSection, k);
                }
            }

            if (k > 100000000L) {
                net.minecraft.profiler.Profiler.logger.warn(
                        "Something's taking too long! '"
                                + this.profilingSection
                                + "' took aprox "
                                + k / 1000000.0
                                + " ms");
            }

            this.profilingSection =
                    !this.sectionList.isEmpty()
                            ? (String) this.sectionList.get(this.sectionList.size() - 1)
                            : packedTextFragments[3];
        }
    }

    public List getProfilingData(String text) {
        cB();

        this.ie = this.hZ;

        if (!this.ie) {
            return new ArrayList<>(Arrays.asList(new ProfilerResult("root", 0.0, 0.0)));
        }

        if (!this.profilingEnabled) {
            return null;
        }

        long i1;
        if (this.profilingMap.containsKey("root")) {
            i1 = (Long) this.profilingMap.get("root");
        } else {

            i1 = 0L;
        }

        long i = i1;
        long j1;
        if (this.profilingMap.containsKey(text)) {
            j1 = (Long) this.profilingMap.get(text);
        } else {

            j1 = -1L;
        }

        long j = j1;
        ArrayList arrayList = Lists.newArrayList();
        if (text.length() > 0) {
            text = text + packedTextFragments[4];
        }

        long k = 0L;

        for (java.util.Iterator<?> __recoveryIterator171_4076 =
                        (this.profilingMap.keySet()).iterator();
                (__recoveryIterator171_4076).hasNext(); ) {
            String textValue = (String) (__recoveryIterator171_4076).next();
            if (textValue.length() > text.length()
                    && textValue.startsWith(text)
                    && textValue.indexOf(packedTextFragments[5], text.length() + 1) < 0) {
                k += ((java.lang.Long) this.profilingMap.get(textValue));
            }
        }

        float f = (float) k;
        if (k < j) {
            k = j;
        }

        if (i < k) {
            i = k;
        }

        for (java.util.Iterator<?> __recoveryIterator171_4492 =
                        (this.profilingMap.keySet()).iterator();
                (__recoveryIterator171_4492).hasNext(); ) {
            String textValue2 = (String) (__recoveryIterator171_4492).next();
            if (textValue2.length() > text.length()
                    && textValue2.startsWith(text)
                    && textValue2.indexOf(packedTextFragments[6], text.length() + 1) < 0) {
                long l = (Long) this.profilingMap.get(textValue2);
                double d0 = l * 100.0 / k;
                double d1 = l * 100.0 / i;
                String textValue3 = textValue2.substring(text.length());
                arrayList.add(new ProfilerResult(textValue3, d0, d1));
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_5005 =
                        (this.profilingMap.keySet()).iterator();
                (__recoveryIterator171_5005).hasNext(); ) {
            String textValue4 = (String) (__recoveryIterator171_5005).next();
            Map map = this.profilingMap;
            long j2 = (Long) this.profilingMap.get(textValue4);

            j2 *= 950L;

            map.put(textValue4, j2 / 1000L);
        }

        if ((float) k > f) {
            arrayList.add(
                    new ProfilerResult(
                            "unspecified",
                            ((float) k - f) * 100.0 / k,
                            ((float) k - f) * 100.0 / i));
        }

        Collections.sort(arrayList);
        arrayList.add(0, new ProfilerResult(text, 100.0, k * 100.0 / i));
        return arrayList;
    }

    static {
        net.minecraft.profiler.Profiler.packedTextFragments = new String[9];
        net.minecraft.profiler.Profiler.packedTextFragments[0] = new String("");
        net.minecraft.profiler.Profiler.packedTextFragments[1] = new String("");
        net.minecraft.profiler.Profiler.packedTextFragments[2] = new String(".");
        net.minecraft.profiler.Profiler.packedTextFragments[3] = new String("");
        net.minecraft.profiler.Profiler.packedTextFragments[4] = new String(".");
        net.minecraft.profiler.Profiler.packedTextFragments[5] = new String(".");
        net.minecraft.profiler.Profiler.packedTextFragments[6] = new String(".");
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[4];
        b(aclass_b6df81f333fa31dc98ea);
        kG = "display";
        sT = "tick";
        te = "scheduledExecutables";
        vI = "preRenderErrors";
        mC = "render";
        net.minecraft.profiler.Profiler.logger = LogManager.getLogger();
        rC = "scheduledExecutables".hashCode();
        jM = "tick".hashCode();
        rz = "preRenderErrors".hashCode();
        kZ = "render".hashCode();
        nO = "display".hashCode();
    }

    public Profiler() {
        this.profilingSection = packedTextFragments[0];
        this.profilingMap = Maps.newHashMap();
        this.hZ = true;
        this.ie = this.hZ;
    }

    public static Entity[] cB() {
        return bAD;
    }

    // region String decoding support

    // endregion

    public void clearProfiling() {
        this.profilingMap.clear();
        this.profilingSection = packedTextFragments[1];
        this.sectionList.clear();
        this.ie = this.hZ;
    }

    public static void b(Entity[] bADValue) {
        bAD = bADValue;
    }

    public void startSection(String text) {
        Entity[] entityArray;
        entityArray = cB();
        if (Lagometer.isActive()) {
            int i = text.hashCode();
            if (i == rC && text.equals("scheduledExecutables")) {
                Lagometer.timerScheduledExecutables.start();

                
            } else {

                if (i == jM && text.equals("tick") && OptiFineConfig.isMinecraftThread()) {
                    Lagometer.timerScheduledExecutables.end();
                    Lagometer.timerTick.start();


                } else {

                    if (i == rz && text.equals("preRenderErrors")) {
                        Lagometer.timerTick.end();
                    }
                }
            }
        }

        if (OptiFineConfig.isFastRender()) {
            int j = text.hashCode();
            if (j == kZ && text.equals("render")) {
                GlStateManager.clearEnabled = false;

                
            } else {

                if (j == nO && text.equals("display")) {
                    GlStateManager.clearEnabled = true;
                }
            }
        }

        if (this.ie && this.profilingEnabled) {
            if (this.profilingSection.length() > 0) {
                this.profilingSection = this.profilingSection + packedTextFragments[2];
            }

            this.profilingSection = this.profilingSection + text;
            this.sectionList.add(this.profilingSection);
            this.timestampList.add(System.nanoTime());
        }
    }

    // region String decoding support

    // endregion

    public String getNameOfLastSection() {
        return this.sectionList.size() == 0
                ? "[UNKNOWN]"
                : (String) this.sectionList.get(this.sectionList.size() - 1);
    }

    // region String decoding support

    // endregion

    public void endStartSection(String text) {
        if (this.ie) {
            this.endSection();
            this.startSection(text);
        }
    }

    // region String decoding support

    // endregion
}
