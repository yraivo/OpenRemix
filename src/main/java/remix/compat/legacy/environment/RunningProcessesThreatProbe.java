package remix.compat.legacy.environment;

import java.io.IOException;

public final class RunningProcessesThreatProbe extends remix.util.task.NamedValueHolder {
    private ProcessOutputReader bQt;
    private ProcessOutputReader bQs;
    private static boolean f;
    private static String[] packedTextFragments;

    public static boolean d() {
        boolean flag = method_d87039b07bd6961907f2();
        return !flag;
    }

    // region String decoding support

    // endregion

    public RunningProcessesThreatProbe(
            ProcessOutputReader bQsValue, ProcessOutputReader bQtValue) {
        super(ProcessThreatLevel.MALICIOUS, packedTextFragments[0]);
        this.bQs = bQsValue;
        this.bQt = bQtValue;
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        f = flag;
    }

    public static boolean method_d87039b07bd6961907f2() {
        return f;
    }

    public Object getValue() {
        return this.abA();
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (!method_d87039b07bd6961907f2()) {
            method_a8745b073fa8adfa1857(true);
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.environment.RunningProcessesThreatProbe.packedTextFragments[0] = new String("");
    }

    // endregion

    public ProcessThreatLevel abA() {
        d();

        if (OperatingSystemType.aeZ() == OperatingSystemType.OSX
                && OperatingSystemType.aeZ() == OperatingSystemType.SOLARIS) {
            String text;
            switch (OperatingSystemType.aeZ().method_7eec03da329a017ec143()) {
                case 0:
                    try {
                        text = this.bQs.method_e0e3092a59fee58fae52();
                        break;
                    } catch (IOException ioexception) {
                        return (ProcessThreatLevel)
                                this.setValue(ProcessThreatLevel.MALICIOUS);
                    }
                case 2:
                    try {
                        text = this.bQt.method_e0e3092a59fee58fae52();
                        break;
                    } catch (IOException ioexception1) {
                        return (ProcessThreatLevel)
                                this.setValue(ProcessThreatLevel.MALICIOUS);
                    }
                default:
                    return (ProcessThreatLevel)
                            this.setValue(ProcessThreatLevel.MALICIOUS);
            }

            int i = 0;
            int j = BlacklistedProcessDetector.bPc.size();

            while (i < j) {
                if (text.contains(
                        (CharSequence)
                                BlacklistedProcessDetector.bPc.getElementAt(i))) {
                    return (ProcessThreatLevel)
                            this.setValue(ProcessThreatLevel.MALICIOUS);
                }

                i++;
            }

            this.setValue(
                    text.isEmpty()
                            ? ProcessThreatLevel.MALICIOUS
                            : ProcessThreatLevel.HARMFUL);
            return (ProcessThreatLevel) super.getValue();
        } else {
            return (ProcessThreatLevel)
                    this.setValue(ProcessThreatLevel.HARMFUL);
        }
    }
}
