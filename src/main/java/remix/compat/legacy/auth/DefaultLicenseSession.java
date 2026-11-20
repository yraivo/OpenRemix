package remix.compat.legacy.auth;

import remix.compat.legacy.security.ResourcePermission;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultLicenseSession extends AbstractLicenseSession {

    private FluentMapWrapper fluentMapWrapper;
    private static String sT;
    private LicenseStateHolder licenseStateHolder =
            new LicenseStateHolder(LicenseValidationState.INVALID);

    // region String decoding support

    // endregion

    static {
        sT = "Uj/2e1hP1FjOB5Q4Cjo=";
    }

    public boolean method_b417d7500ec59d69e373() {
        return this.fluentMapWrapper != null && !((Map) this.fluentMapWrapper.Qu()).isEmpty();
    }

    private Map<String, ResourcePermission<?>> lN() {
        HashMap hashmap = null;
        ResourcePermission[] resourcePermissionArray = this.getUserRoleTier().afv();
        int[] aint1 = AbstractLicenseSession.method_650532ba484532b3efb0();
        int length = resourcePermissionArray.length;
        int[] aint = aint1;
        int j = 0;

        while (j < length) {
            ResourcePermission resourcePermission = resourcePermissionArray[j];
            String text = resourcePermission.getPermissionName();
            if (hashmap == null) {
                hashmap = new HashMap();
            }

            label49:
            {
                if (hashmap.containsKey(text)) {
                    label45:
                    {
                        ResourcePermission resourcePermissionValue =
                                (ResourcePermission) hashmap.get(text);
                        int k =
                                resourcePermissionValue.getResource() == resourcePermission.getResource()
                                        ? 1
                                        : 0;
                        int l =
                                resourcePermissionValue.getPriorityRank()
                                                == resourcePermission
                                                        .getPriorityRank()
                                        ? 1
                                        : 0;
                        if (k != 0
                                && l == 0
                                && resourcePermissionValue.getPriorityRank()
                                        < resourcePermission
                                                .getPriorityRank()) {
                            hashmap.put(text, resourcePermission);
                            if (aint == null) {
                                break label45;
                            }
                        }

                        if (k == 0) {
                            hashmap.put(
                                    String.format(
                                            new String("%s-resource=%s"),
                                            text,
                                            resourcePermission.getResource()),
                                    resourcePermission);
                        }
                    }

                    if (aint == null) {
                        break label49;
                    }
                }

                hashmap.put(text, resourcePermission);
            }

            j++;
            if (aint != null) {
                break;
            }
        }

        return hashmap == null ? Collections.emptyMap() : hashmap;
    }

    public FluentMapWrapper Zy() {
        if (!this.method_b417d7500ec59d69e373()) {
            this.l();
        }

        return this.fluentMapWrapper;
    }

    public LicenseStateHolder getLicenseStateHolder() {
        return this.licenseStateHolder;
    }

    // region String decoding support

    // endregion

    public DefaultLicenseSession(
            ClientIdentityToken clientIdentityToken,
            UserRoleTier userRoleTier) {
        super(clientIdentityToken, userRoleTier);
    }

    public void l() {
        this.fluentMapWrapper = FluentCollections.method_a09b51431db5c67c4348(this.lN());
    }
}
