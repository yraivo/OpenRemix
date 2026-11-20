package remix.compat.legacy.security;

import remix.compat.legacy.auth.RoleTierFloatProbe;
import remix.util.reflection.ReflectiveClassWrapper;
import remix.util.reflection.ReflectiveFieldAccessor;

import java.util.Optional;

public final class PortAndProxyInitSecurityRule extends SecurityCheckRule {
    public Object getValue() {
        return this.abg();
    }

    public CheckResultStatus abg() {
        RoleTierFloatProbe.getLegacyRoleFloatProbeMarker();

        CheckResultStatus checkResultStatus = super.abg();

        if (checkResultStatus != CheckResultStatus.bYl) {
            ReflectiveClassWrapper bYB = SecurityCheckHelper.clientServicesClass;
            Optional optional = SecurityCheckHelper.bYy.findClassByLegacyId(498156);
            optional.ifPresent(
                    ((java.util.function.Consumer<remix.util.reflection.ReflectiveClassWrapper>)
                            (reflectiveClassWrapper -> {
                                try {
                                    ReflectiveFieldAccessor reflectiveFieldAccessor =
                                            bYB.findFieldOfWrappedType(
                                                    reflectiveClassWrapper);
                                    reflectiveFieldAccessor.a(reflectiveClassWrapper.Qu());
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            })));
            Optional optional1 = SecurityCheckHelper.bYy.findClassByLegacyId(578);
            optional1.ifPresent(
                    ((java.util.function.Consumer<remix.util.reflection.ReflectiveClassWrapper>)
                            (reflectiveClassWrapper -> {
                                try {
                                    ReflectiveFieldAccessor reflectiveFieldAccessor =
                                            bYB.findFieldOfWrappedType(
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

        return checkResultStatus;
    }
}
