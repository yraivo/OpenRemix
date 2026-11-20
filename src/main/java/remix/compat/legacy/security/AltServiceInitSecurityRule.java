package remix.compat.legacy.security;

import remix.compat.legacy.environment.VmFileExceptionProbe;
import remix.util.reflection.ReflectiveClassWrapper;
import remix.util.reflection.ReflectiveFieldAccessor;

import java.util.Optional;

public final class AltServiceInitSecurityRule extends SecurityCheckRule {
    public CheckResultStatus abg() {
        VmFileExceptionProbe.method_d87039b07bd6961907f2();

        CheckResultStatus checkResultStatus = super.abg();

        if (checkResultStatus != CheckResultStatus.bYl) {
            ReflectiveClassWrapper bYB = SecurityCheckHelper.clientServicesClass;
            Optional optional = SecurityCheckHelper.bYy.findClassByLegacyId(39423);
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

        return checkResultStatus;
    }

    public Object getValue() {
        return this.abg();
    }
}
