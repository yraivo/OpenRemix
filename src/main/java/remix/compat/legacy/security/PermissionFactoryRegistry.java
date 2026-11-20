package remix.compat.legacy.security;

import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.function.BiFunction;

public final class PermissionFactoryRegistry {
    private Map<String, BiFunction<Class<?>, Integer, ResourcePermission<?>>> permissionFactories;

    public ResourcePermission<?> createPermission(
            Class<?> oclass, String text, int i) {
        boolean flag1 = ResourcePermission.isStandardPermissionFlow();
        BiFunction bifunction = this.permissionFactories.get(text);
        boolean flag = flag1;
        if (bifunction != null) {
            ResourcePermission resourcePermission =
                    (ResourcePermission) bifunction.apply(oclass, i);
            if (!flag) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
            }

            return resourcePermission;
        } else {
            throw new MissingPermissionException(text);
        }
    }

    public PermissionFactoryRegistry(
            Map<String, BiFunction<Class<?>, Integer, ResourcePermission<?>>> map) {
        this.permissionFactories = map;
    }

}
