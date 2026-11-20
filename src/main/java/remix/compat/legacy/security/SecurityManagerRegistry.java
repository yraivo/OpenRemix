package remix.compat.legacy.security;

import remix.util.collection.FluentIterableList;
import remix.util.task.PeriodicTaskScheduler;

import net.minecraft.entity.Entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum SecurityManagerRegistry implements Serializable {
    INSTANCE(null),
    ENUM_MANAGER(Enum.class, new EnumSecurityCheckManager()),
    EXCEPTION_MANAGER(Exception.class, new ExceptionSecurityCheckManager()),
    NUMBER_MANAGER(Number.class, new NumberSecurityCheckManager()),
    STRING_MANAGER(String.class, new StringSecurityCheckManager());

    private FluentIterableList<SecurityCheckManager> bXW;
    public static SecurityCheckBatch bXZ;
    private Class<?> bYb;
    private final PeriodicTaskScheduler bYd = new PeriodicTaskScheduler();
    private static SecurityCheckRunner bYf;

    public static SecurityCheckBatch bYh;
    public static SecurityCheckBatch bYi;
    private static Map<Class<?>, FluentIterableList<SecurityCheckManager>> dI;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void method_8d9b536bcdc9991c48fb() {
        SecurityCheckManager.kM();
        this.bYd.method_2b19dbb95722c187d389(new SecurityCheckTimerTask(this));
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            SecurityCheckManager.b(new String[2]);
        }
    }

    public FluentIterableList<SecurityCheckManager> abO() {
        return this.bXW;
    }

    static {
        dI = new HashMap<>();
        for (SecurityManagerRegistry class_8ded098f056586897d58 : values()) {
            dI.put(
                    class_8ded098f056586897d58.bYb,
                    new FluentIterableList(class_8ded098f056586897d58.bXW.eN()));
        }
        bYh =
                new DefaultSecurityCheckBatch(
                        new IndexedSecurityCheckSelection[] {
                            new IndexedSecurityCheckSelection(Enum.class, new Integer[] {102947}),
                            new IndexedSecurityCheckSelection(
                                    Exception.class, new Integer[] {7473453, 10924712})
                        });
        bXZ =
                new DefaultSecurityCheckBatch(
                        new IndexedSecurityCheckSelection[] {
                            new IndexedSecurityCheckSelection(String.class, new Integer[] {982986}),
                            new IndexedSecurityCheckSelection(Enum.class, new Integer[] {102947}),
                            new IndexedSecurityCheckSelection(
                                    Number.class, new Integer[] {90342634, 39806, 19034}),
                            new IndexedSecurityCheckSelection(
                                    Exception.class, new Integer[] {10924712})
                        });
        bYi =
                new DefaultSecurityCheckBatch(
                        new IndexedSecurityCheckSelection[] {
                            new IndexedSecurityCheckSelection(Enum.class, new Integer[] {102947}),
                            new IndexedSecurityCheckSelection(
                                    Exception.class, new Integer[] {10924712}),
                            new IndexedSecurityCheckSelection(
                                    Number.class, new Integer[] {83453977, 39806, 90342634})
                        });
        bYf = new SecurityCheckRunner(bYh);
    }

    public SecurityCheckRunner getPrimaryCheckRunner() {
        return bYf;
    }

    // region String decoding support

    // endregion

    public static FluentIterableList<SecurityCheckManager>
            method_6bd29f0a85a8c7569327(Class<?> oclass) {
        return dI.get(oclass);
    }

    SecurityManagerRegistry(
            Class<?> oclass, SecurityCheckManager... securityCheckManagerArray) {
        this.bYb = oclass;
        this.bXW = new FluentIterableList(securityCheckManagerArray);
    }

    public Class<?> ml() {
        return this.bYb;
    }
}
