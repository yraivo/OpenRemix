package remix.util.task;

public class NamedValueHolder {
    private static String c;
    private Object value;
    private String name;
    private static String[] packedTextFragments;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_cee993844cce4b97ed62() == null) {
            b(packedTextFragments[0]);
        }
    }

    public Object getValue() {
        return this.value;
    }

    public Object setValue(Object object) {
        this.value = (java.lang.Object) object;
        return (java.lang.Object) object;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.util.task.NamedValueHolder.packedTextFragments[0] = new String("HbhBec");
    }

    // endregion

    public static String method_cee993844cce4b97ed62() {
        return c;
    }

    public NamedValueHolder(Object object, String text) {
        this.value = (java.lang.Object) object;
        this.name = text;
    }

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    public static void b(String text) {
        c = text;
    }
}
