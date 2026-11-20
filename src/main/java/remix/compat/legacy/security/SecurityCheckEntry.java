package remix.compat.legacy.security;

public interface SecurityCheckEntry<T, S, F> {
    SecurityRulePair<S, F> getSecurityRulePair();

    T cy();
}
