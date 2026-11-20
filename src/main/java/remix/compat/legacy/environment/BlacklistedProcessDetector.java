package remix.compat.legacy.environment;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;
import remix.util.task.NamedValueHolder;

import java.util.Arrays;

public final class BlacklistedProcessDetector extends AbstractProcessCommandReader<String> {

    public static FluentListWrapper bPc;

    // region String decoding support

    // endregion

    static {
        bPc =
                FluentCollections.method_e2cb25ad6dbcdd481b22(
                        Arrays.asList(
                                new String("processhacker"),
                                new String("wireshark"),
                                new String("fiddler"),
                                new String("mitmproxy"),
                                new String("charles"),
                                new String("burpsuitecommunity"),
                                new String("tcpviewer"),
                                new String("httpanalyzer"),
                                new String("socksa"),
                                new String("\\u6b63\\u5728\\u6355\\u83b7"),
                                new String("procexp")));
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public BlacklistedProcessDetector(NamedValueHolder namedValueHolder) {
        super(namedValueHolder);
    }
}
