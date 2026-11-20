package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

import remix.compat.legacy.auth.GuestLicenseSession;
import remix.compat.legacy.auth.LicenseProfileRecord;
import remix.compat.legacy.auth.LicenseSession;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

public class DefaultPasteProfileFetcher implements PasteProfileFetcher {
    private AbstractRemotePasteEndpoint abstractRemotePasteEndpoint;
    private static Entity[] b;

    public AbstractRemotePasteEndpoint getAbstractRemotePasteEndpoint() {
        return this.abstractRemotePasteEndpoint;
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public DefaultPasteProfileFetcher(AbstractRemotePasteEndpoint bPoValue) {
        this.abstractRemotePasteEndpoint = bPoValue;
    }

    static {
        method_da2ac5c94686ee11e9e6();
    }

    public FluentListWrapper fetchProfileLines(LicenseSession licenseSession) throws Exception {
        method_da2ac5c94686ee11e9e6();

        try {
            if (!(licenseSession instanceof GuestLicenseSession)) {
                return FluentCollections.ax();
            }
        } catch (Exception exception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
        }

        FluentListWrapper fluentListWrapper =
                this.abstractRemotePasteEndpoint.fetchParsedProfiles();
        FluentListWrapper fluentListWrapperValue = FluentCollections.ax();

        for (java.util.Iterator<?> __recoveryIterator171_1903 =
                        (fluentListWrapper.asList()).iterator();
                (__recoveryIterator171_1903).hasNext(); ) {
            LicenseProfileRecord licenseProfileRecord =
                    (LicenseProfileRecord) (__recoveryIterator171_1903).next();
            try {
                if (licenseProfileRecord
                        .getLicenseSession()
                        .getClientIdentityToken()
                        .method_c60693f457aa70be7160()
                        .equalsIgnoreCase(
                                licenseSession
                                        .getClientIdentityToken()
                                        .method_c60693f457aa70be7160())) {
                    fluentListWrapperValue.add(licenseProfileRecord);
                }
            } catch (Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
            }
        }

        return fluentListWrapperValue;
    }
}
