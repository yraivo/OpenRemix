package remix.compat.legacy.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionSpec {
    String permissionName() default "access";

    ResourcePriority priority() default ResourcePriority.NORMAL;
}
