package remix.compat.legacy.security;

import remix.compat.legacy.environment.RunningProcessesThreatProbe;
import remix.util.reflection.ReflectiveClassWrapper;
import remix.util.reflection.ReflectiveFieldAccessor;
import remix.util.reflection.ReflectiveInstantiationException;

import remix.client.RemixClient;
import remix.event.EventBus;

import java.util.Optional;

public final class CreativeTabAndKeybindInitSecurityRule extends SecurityCheckRule {
    public Object getValue() {
        return this.abg();
    }

    public CheckResultStatus abg() {
        RunningProcessesThreatProbe.d();

        CheckResultStatus checkResultStatus = super.abg();

        if (checkResultStatus != CheckResultStatus.bYl) {
            SecurityCheckHelper.bYy
                    .findClassByLegacyId(92640)
                    .ifPresent(
                            ((java.util.function.Consumer<remix.util.reflection.ReflectiveClassWrapper>)
                                    (reflectiveClassWrapper -> {
                                        try {
                                            reflectiveClassWrapper.Qu();
                                        } catch (
                                                ReflectiveInstantiationException
                                                        reflectiveInstantiationException) {
                                            reflectiveInstantiationException.printStackTrace();
                                        }
                                    })));
            ReflectiveClassWrapper bYD = SecurityCheckHelper.remixClientClass;
            Optional optional =
                    SecurityCheckHelper.bYy.findClassByLegacyId(739457204);
            ReflectiveFieldAccessor reflectiveFieldAccessor =
                    bYD.findFieldOfType(EventBus.class);
            optional.ifPresent(
                    ((java.util.function.Consumer<remix.util.reflection.ReflectiveClassWrapper>)
                            (reflectiveClassWrapper -> {
                                Object object = null;

                                try {
                                    object =
                                            reflectiveFieldAccessor
                                                    .getField()
                                                    .get(RemixClient.INSTANCE);
                                } catch (IllegalAccessException illegalaccessexception) {
                                    illegalaccessexception.printStackTrace();
                                }

                                try {
                                    reflectiveClassWrapper.method_d2c3a5ad661e2c5b1fe4(
                                            new Object[] {object}, new Class[] {EventBus.class});
                                } catch (ReflectiveInstantiationException reflectiveInstantiationException) {
                                    reflectiveInstantiationException.printStackTrace();
                                }
                            })));
            CheckResultStatus checkResultStatusValue = CheckResultStatus.bYv;
            this.setValue(checkResultStatusValue);
            checkResultStatus = checkResultStatusValue;
        }

        return checkResultStatus;
    }
}
