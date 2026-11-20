package remix.compat.legacy.invocation;

public class ClassLookupReflectiveTargetSupplier extends ReflectiveTargetSupplier {


    private static ClassNotFoundException a(ClassNotFoundException classnotfoundexception) {
        return classnotfoundexception;
    }

    public Object getValue() {
        return super.getValue();
    }
}
