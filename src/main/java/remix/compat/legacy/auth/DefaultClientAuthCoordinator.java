package remix.compat.legacy.auth;

import remix.compat.legacy.crypto.InvalidSignatureKeyException;
import remix.compat.legacy.crypto.RsaKeyPairProvider;
import remix.compat.legacy.crypto.SignedTokenPayload;
import remix.compat.legacy.crypto.SignedTokenVerifier;
import remix.compat.legacy.environment.DefaultHardwareFingerprintCollector;
import remix.util.collection.FluentListWrapper;

import net.minecraft.entity.Entity;
import remix.client.AbstractClientBootstrap;
import remix.client.ClientServices;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

public final class DefaultClientAuthCoordinator implements ClientAuthCoordinator {
    public static AuthVerificationOutcome lastVerificationOutcome;

    public AuthVerificationOutcome chooseLegacyStartupOutcome(
            AbstractClientBootstrap abstractClientBootstrap) {
        ClientServices.getLegacyServiceMarker();
        int[] aint = new int[] {12, 9, 4, 5};
        AuthVerificationOutcome authVerificationOutcome =
                AuthVerificationOutcome.values()[aint[new Random().nextInt(aint.length)]];
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ClientServices.setLegacyServiceMarker(new int[3]);
        }

        return authVerificationOutcome;
    }

    public AuthVerificationOutcome applyLegacyVerificationResult(
            AbstractClientBootstrap abstractClientBootstrap,
            AuthVerificationResult authVerificationResult)
            throws NoSuchAlgorithmException, InvalidSignatureKeyException {
        int[] aint1 = ClientServices.getLegacyServiceMarker();
        LicenseProfileRecord licenseProfileRecord = authVerificationResult.getLicenseProfileRecord();
        int[] aint = aint1;
        if (!licenseProfileRecord.hasNoSession()
                && !licenseProfileRecord.isGuestSession()
                && !licenseProfileRecord.isFallbackSession()) {
            FluentListWrapper fluentListWrapper =
                    licenseProfileRecord.getFluentListWrapper();
            DefaultHardwareFingerprintCollector defaultHardwareFingerprintCollector =
                    (DefaultHardwareFingerprintCollector) licenseProfileRecord.getLicenseSession().getClientIdentityToken().acv();
            int i = 0;

            while (i < fluentListWrapper.size()) {
                SignedTokenVerifier signedTokenVerifier =
                        defaultHardwareFingerprintCollector.bI(i);
                byte[] abyte =
                        SignedTokenPayload.bTA.decode(
                                (String) fluentListWrapper.getElementAt(i));
                X509EncodedKeySpec x509encodedkeyspec = new X509EncodedKeySpec(abyte);
                KeyFactory keyfactory = KeyFactory.getInstance(RsaKeyPairProvider.b);

                PublicKey publickey;
                try {
                    publickey = keyfactory.generatePublic(x509encodedkeyspec);
                } catch (InvalidKeySpecException invalidkeyspecexception) {
                    abstractClientBootstrap.handleLegacyLicenseFailure();
                    remix.compat.legacy.auth.DefaultClientAuthCoordinator.lastVerificationOutcome = AuthVerificationOutcome.FAILURE;
                    throw new InvalidSignatureKeyException();
                }

                signedTokenVerifier.verifyWithPublicKey(publickey);
                remix.compat.legacy.auth.DefaultClientAuthCoordinator.lastVerificationOutcome = AuthVerificationOutcome.FAILURE_1;
                i++;
                if (aint != null) {
                    break;
                }
            }

            return remix.compat.legacy.auth.DefaultClientAuthCoordinator.lastVerificationOutcome != AuthVerificationOutcome.FAILURE && remix.compat.legacy.auth.DefaultClientAuthCoordinator.lastVerificationOutcome == AuthVerificationOutcome.FAILURE_1
                    ? AuthVerificationOutcome.SUCCESS
                    : remix.compat.legacy.auth.DefaultClientAuthCoordinator.lastVerificationOutcome;
        } else {
            return AuthVerificationOutcome.INTERRUPTED_4;
        }
    }

    public AuthVerificationOutcome getLastVerificationOutcome() {
        return remix.compat.legacy.auth.DefaultClientAuthCoordinator.lastVerificationOutcome;
    }
}
