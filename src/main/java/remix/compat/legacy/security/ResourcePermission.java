package remix.compat.legacy.security;

import java.io.Serializable;

public class ResourcePermission<R> implements Serializable {
    public static String c;
    public static String a;
    private R resource;
    private static boolean standardPermissionFlow;
    public static String b;

    private String permissionName;
    private ResourcePriority resourcePriority;

    public ResourcePermission(R object, String text, ResourcePriority bTGValue) {
        this.resource = (R) object;
        this.permissionName = text;
        this.resourcePriority = bTGValue;
    }

    public static boolean isLegacyPermissionFlow() {
        boolean flag = isStandardPermissionFlow();
        return !flag;
    }

    // region String decoding support

    // endregion

    public int getPriorityRank() {
        return this.resourcePriority.getRank();
    }

    public R getResource() {
        return this.resource;
    }

    public static void setStandardPermissionFlow(boolean flag) {
        remix.compat.legacy.security.ResourcePermission.standardPermissionFlow = flag;
    }

    // region String decoding support

    // endregion

    public String getPermissionName() {
        return this.permissionName;
    }

    static {
        setStandardPermissionFlow(true);
        a = "access";
        b = new String("interaction");
        c = new String("modification");
    }

    public static boolean isStandardPermissionFlow() {
        return remix.compat.legacy.security.ResourcePermission.standardPermissionFlow;
    }
}
