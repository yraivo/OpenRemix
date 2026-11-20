package remix.compat.legacy.security;

import remix.compat.legacy.environment.JvmArgumentsStringProbe;
import remix.util.reflection.ReflectiveClassWrapper;
import remix.util.reflection.ReflectiveFieldAccessor;

import net.minecraft.entity.Entity;

import java.util.Optional;

public final class ModulesAndConfigsInitSecurityRule extends SecurityCheckRule {
    public Object getValue() {
        return this.abg();
    }

    public CheckResultStatus abg() {
        String text = JvmArgumentsStringProbe.getLegacyProbeMarker();
        CheckResultStatus checkResultStatus = super.abg();
        String textValue = text;
        if (checkResultStatus != CheckResultStatus.bYl) {
            ReflectiveClassWrapper bYB = SecurityCheckHelper.clientServicesClass;
            Optional optional = SecurityCheckHelper.bYy.findClassByLegacyId(239075);
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
            Optional optional1 =
                    SecurityCheckHelper.bYy.findClassByLegacyId(826236236);
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
            Optional optional2 =
                    SecurityCheckHelper.bYy.findClassByLegacyId(98799793);
            optional2.ifPresent(
                    ((java.util.function.Consumer<remix.util.reflection.ReflectiveClassWrapper>)
                            (reflectiveClassWrapperValue -> {
                                try {
                                    ReflectiveFieldAccessor reflectiveFieldAccessor =
                                            bYB.findFieldOfWrappedType(
                                                    reflectiveClassWrapperValue);
                                    reflectiveFieldAccessor.a(reflectiveClassWrapperValue.Qu());
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            })));
            CheckResultStatus checkResultStatusValue = CheckResultStatus.bYv;
            this.setValue(checkResultStatusValue);
            checkResultStatus = checkResultStatusValue;
        }

        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }

        return checkResultStatus;
    }
}
