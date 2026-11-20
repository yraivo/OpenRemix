package remix.compat.legacy.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtectedResource {
    PermissionSpec[] permissions() default {};

    String resourceIdentifier() default "invalid-resource";
}
