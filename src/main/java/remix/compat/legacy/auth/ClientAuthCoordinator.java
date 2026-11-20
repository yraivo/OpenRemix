package remix.compat.legacy.auth;

import remix.compat.legacy.crypto.InvalidSignatureKeyException;

import remix.client.AbstractClientBootstrap;

import java.security.NoSuchAlgorithmException;

public interface ClientAuthCoordinator {
    AuthVerificationOutcome getLastVerificationOutcome();

    AuthVerificationOutcome chooseLegacyStartupOutcome(
            AbstractClientBootstrap abstractClientBootstrap);

    AuthVerificationOutcome applyLegacyVerificationResult(
            AbstractClientBootstrap abstractClientBootstrap,
            AuthVerificationResult authVerificationResult)
            throws NoSuchAlgorithmException, InvalidSignatureKeyException;
}
