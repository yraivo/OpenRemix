package remix.compat.legacy.security;

import remix.compat.legacy.auth.LicensePermissionRule;
import remix.compat.legacy.auth.LicenseSession;

import net.minecraft.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class PermissionCheckGroup {
    private String groupName;
    private Set<LicensePermissionRule> failedRules;
    private Set<LicensePermissionRule> configuredRules;
    private static String d;

    public Set<LicensePermissionRule> getConfiguredRules() {
        return this.configuredRules;
    }

    public Set<LicensePermissionRule> getFailedRules() {
        return this.failedRules;
    }

    public boolean hasConfiguredRules() {
        return this.configuredRules != null && !this.configuredRules.isEmpty();
    }

    public boolean hasObligatoryFailure() {
        boolean scanAllRules = ResourcePermission.isStandardPermissionFlow();
        if (this.failedRules != null) {
            for (LicensePermissionRule rule : this.failedRules) {
                if (rule.isObligatory()) {
                    return true;
                }
                if (!scanAllRules) {
                    break;
                }
            }
        }
        return false;
    }

    // region String decoding support

    // endregion

    public void addRule(LicensePermissionRule licensePermissionRule) {
        if (this.configuredRules == null) {
            this.configuredRules = new HashSet<>();
        }

        this.configuredRules.add(licensePermissionRule);
    }

    public String getGroupName() {
        return this.groupName;
    }

    public boolean passesLicenseChecks(LicenseSession licenseSession) {
        boolean scanAllRules = ResourcePermission.isStandardPermissionFlow();
        boolean mandatoryFailure = false;
        if (this.hasConfiguredRules()) {
            boolean previousOrCurrentRuleFailed = false;
            for (LicensePermissionRule rule : this.getConfiguredRules()) {
                // Preserve the original cumulative failure state and always evaluate the current rule.
                previousOrCurrentRuleFailed |= !rule.passesLicenseRule(licenseSession);
                if (previousOrCurrentRuleFailed) {
                    this.recordFailedRule(rule);
                }
                if (!scanAllRules) {
                    break;
                }
            }
            mandatoryFailure = this.hasObligatoryFailure();
        }
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ResourcePermission.setStandardPermissionFlow(!scanAllRules);
        }
        return !mandatoryFailure;
    }

    public void recordFailedRule(LicensePermissionRule licensePermissionRule) {
        if (this.failedRules == null) {
            this.failedRules = new HashSet<>();
        }

        this.failedRules.add(licensePermissionRule);
    }

    public PermissionCheckGroup(String text) {
        this.groupName = String.format(new String("%s_check"), text);
    }

    static {
        d = "RudpZVJDmnw=";
    }

    // region String decoding support

    // endregion
}
