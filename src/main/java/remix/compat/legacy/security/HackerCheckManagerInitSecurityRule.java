package remix.compat.legacy.security;

import remix.compat.legacy.auth.UserIdIntegerProbe;
import remix.util.reflection.ReflectiveClassWrapper;
import remix.util.reflection.ReflectiveFieldAccessor;

import net.minecraft.entity.Entity;

import java.util.Optional;

public class HackerCheckManagerInitSecurityRule extends SecurityCheckRule {
    public Object getValue() {
        return this.abg();
    }

    public CheckResultStatus abg() {
        boolean flag1 = UserIdIntegerProbe.isLegacyUserIdProbeFlowDisabled();
        CheckResultStatus checkResultStatus = super.abg();
        boolean flag = flag1;
        if (checkResultStatus != CheckResultStatus.bYl) {
            ReflectiveClassWrapper bYB = SecurityCheckHelper.clientServicesClass;
            Optional optional =
                    SecurityCheckHelper.bYy.findClassByLegacyId(890632321);
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
            CheckResultStatus checkResultStatusValue = CheckResultStatus.bYv;
            this.setValue(checkResultStatusValue);
            checkResultStatus = checkResultStatusValue;
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            UserIdIntegerProbe.setLegacyUserIdProbeFlowEnabled(!flag);
        }

        return checkResultStatus;
    }
}
