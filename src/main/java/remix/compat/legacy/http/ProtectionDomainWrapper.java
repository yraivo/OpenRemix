package remix.compat.legacy.http;

import java.security.ProtectionDomain;

public final class ProtectionDomainWrapper {
    private ProtectionDomain protectionDomain;

    public ProtectionDomainWrapper(ProtectionDomain protectiondomain) {
        this.protectionDomain = protectiondomain;
    }

    public ProtectionDomain getProtectionDomain() {
        return this.protectionDomain;
    }

    public CodeSourceWrapper aaE() {
        return new CodeSourceWrapper(this.protectionDomain.getCodeSource());
    }
}
