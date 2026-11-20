package remix.compat.legacy.security;

import remix.compat.legacy.auth.RoleTierLongProbe;
import remix.util.reflection.ReflectiveFieldAccessor;

import net.minecraft.entity.Entity;

import java.util.Optional;

public final class FriendManagerInitSecurityRule extends SecurityCheckRule {
    public CheckResultStatus abg() {
        boolean flag1 = RoleTierLongProbe.isLegacyRoleLongFlowEnabled();
        CheckResultStatus checkResultStatus = super.abg();
        boolean flag = flag1;
        if (checkResultStatus != CheckResultStatus.bYl) {
            Optional optional =
                    SecurityCheckHelper.bYy.findClassByLegacyId(473092578);
            optional.ifPresent(
                    ((java.util.function.Consumer<remix.util.reflection.ReflectiveClassWrapper>)
                            (reflectiveClassWrapper -> {
                                try {
                                    ReflectiveFieldAccessor reflectiveFieldAccessor =
                                            SecurityCheckHelper.clientServicesClass
                                                    .findFieldOfWrappedType(
                                                            reflectiveClassWrapper);
                                    reflectiveFieldAccessor.a(reflectiveClassWrapper.Qu());
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            })));
            CheckResultStatus checkResultStatusValue = CheckResultStatus.bYv;
            this.setValue(checkResultStatusValue);
            checkResultStatus = checkResultStatusValue;
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            RoleTierLongProbe.setLegacyRoleLongFlowEnabled(!flag);
        }

        return checkResultStatus;
    }

    public Object getValue() {
        return this.abg();
    }
}
