package remix.compat.legacy.security;

import remix.compat.legacy.crypto.ClassFileHasher;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentIterableList;
import remix.util.collection.FluentListWrapper;
import remix.util.collection.FluentMapWrapper;
import remix.util.task.NamedValueHolder;
import remix.util.task.OneShotNamedValueHolder;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SecurityCheckRunner {
    private StringBuilder stringBuilder;
    private static String[] packedTextFragments;
    private int cz;
    private int sR;
    private final FluentMapWrapper fluentMapWrapper = FluentCollections.newMutableMap();
    private SecurityCheckBatch bSV;
    private SecurityCheckBatch bSW;
    private String g;
    private static Object aZp;

    public SecurityCheckBatch getPreviousCheckBatch() {
        return this.bSV;
    }

    // region String decoding support

    // endregion

    public SecurityCheckBatch abL() {
        return this.bSW;
    }

    public int f() {
        return this.cz;
    }

    public void method_07fe5d638b5ce784a6de(Object aZpValue, SecurityCheckBatch bSWValue) {
        String[] astring = SecurityCheckManager.kM();
        if (bSWValue == null && this.bSW != null) {
            bSWValue = this.bSW;
        }

        SecurityCheckBatch bSVValue = this.bSW;
        this.bSW = bSWValue;
        this.bSV = bSVValue;

        for (java.util.Iterator<?> __recoveryIterator171_2680 = (bSWValue.getFluentIterableList()).iterator();
                (__recoveryIterator171_2680).hasNext(); ) {
            SecurityCheckSelection securityCheckSelection =
                    (SecurityCheckSelection) (__recoveryIterator171_2680).next();
            FluentIterableList fluentIterableList =
                    securityCheckSelection.abO();
            Iterator iterator = fluentIterableList.iterator();

            while (true) {
                if (iterator.hasNext()) {
                    SecurityPoolProvider securityPoolProvider =
                            (SecurityPoolProvider) iterator.next();

                    for (java.util.Iterator<?> __recoveryIterator171_3147 =
                                    (securityCheckSelection.abR()).iterator();
                            (__recoveryIterator171_3147).hasNext(); ) {
                        SecurityCheckEntry securityCheckEntry =
                                (SecurityCheckEntry) (__recoveryIterator171_3147).next();
                        Object object1 =
                                securityPoolProvider.method_a6372c5460944673d5f1(
                                        securityCheckEntry);

                        for (java.util.Iterator<?> __recoveryIterator171_3385 =
                                        (securityPoolProvider.getFluentIterableList()).iterator();
                                (__recoveryIterator171_3385).hasNext(); ) {
                            IndexedValuePool indexedValuePool =
                                    (IndexedValuePool)
                                            (__recoveryIterator171_3385).next();
                            if (indexedValuePool.mc()
                                    == securityCheckEntry.getClass()) {
                                FluentListWrapper fluentListWrapper =
                                        FluentCollections.ax();
                                fluentListWrapper.add(
                                        indexedValuePool.bH(
                                                indexedValuePool.aW()));
                                int[] aint = indexedValuePool.xZ();
                                int length = aint.length;
                                int j = 0;

                                while (j < length) {
                                    int k = aint[j];
                                    indexedValuePool.a(indexedValuePool.aW());
                                    indexedValuePool.b(k);
                                    fluentListWrapper.add(
                                            indexedValuePool.cy());
                                    j++;
                                    if (astring != null) {
                                        Entity.setLegacyGlobalFlowMarker(new String[1]);
                                        break;
                                    }
                                }

                                SecurityRulePair securityRulePair =
                                        securityCheckEntry.getSecurityRulePair();
                                Object object2 =
                                        fluentListWrapper.asList().contains(object1)
                                                ? securityRulePair
                                                        .method_dd944bba6a15824bf1ba(
                                                                new OneShotNamedValueHolder(
                                                                        ClassFileHasher
                                                                                .bXm,
                                                                        null,
                                                                        packedTextFragments[0]))
                                                : securityRulePair
                                                        .method_b68b253a075c1969fddb(
                                                                new NamedValueHolder(
                                                                        SecurityManagerRegistry
                                                                                .INSTANCE,
                                                                        packedTextFragments[1]));
                                this.sR++;
                                aZp = aZpValue;
                                label70:
                                if (this.sR < 14
                                        && (object2.equals(CheckResultStatus.bYl)
                                                || object2.equals(
                                                        CheckResultStatus.bYv))) {
                                    String text = String.valueOf(object2);
                                    char[] achar = text.toCharArray();
                                    this.stringBuilder.append(achar[0]);
                                    List list =
                                            (List) this.fluentMapWrapper.getEntry(bSWValue);
                                    if (list != null) {
                                        list.add(securityCheckEntry);
                                        this.cz++;
                                        if (astring == null) {
                                            break label70;
                                        }
                                    }

                                    list = new ArrayList();
                                    list.add(securityCheckEntry);
                                    this.cz++;
                                    this.fluentMapWrapper.putEntry(bSWValue, list);
                                } else {
                                    if (this.g != null && this.g.isEmpty()) {}

                                    this.g = this.stringBuilder.toString();
                                }
                            }

                            if (astring != null) {
                                break;
                            }
                        }

                        if (astring != null) {
                            break;
                        }
                    }

                    if (astring == null) {
                        continue;
                    }
                }

                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public String q() {
        return this.g;
    }

    public void method_8d9b536bcdc9991c48fb() {
        this.method_07fe5d638b5ce784a6de(null, null);
    }

    public SecurityCheckRunner(SecurityCheckBatch securityCheckBatch) {
        this(securityCheckBatch, null);
    }

    public FluentMapWrapper getFluentMapWrapper() {
        return this.fluentMapWrapper;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public SecurityCheckRunner(
            SecurityCheckBatch bSWValue, SecurityCheckBatch bSVValue) {
        this.bSW = bSWValue;
        this.bSV = bSVValue;
        this.stringBuilder = new StringBuilder();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.security.SecurityCheckRunner.packedTextFragments[0] = new String("");
        remix.compat.legacy.security.SecurityCheckRunner.packedTextFragments[1] = new String("");
    }
    // endregion

    // region String decoding support

    // endregion
}
