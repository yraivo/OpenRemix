package remix.compat.legacy.crypto;

import net.minecraft.entity.Entity;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public final class RsaKeyPairProvider extends AbstractKeyPairProvider {
    public static String b;

    private static final int c = 1024;
    private KeyPairHolder keyPairHolder;

    public RsaKeyPairProvider(int i) throws NoSuchAlgorithmException {
        this(b, i);
    }

    public RsaKeyPairProvider() throws NoSuchAlgorithmException {
        this(b, 1024);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public KeyPairHolder adM() {
        boolean flag1 = KeyPairHolder.isKeyLoaded();
        KeyPairHolder bVsValue =
                new KeyPairHolder(this.getKeyPairGenerator().genKeyPair());
        boolean flag = flag1;
        if (this.keyPairHolder != null) {
            return this.keyPairHolder;
        }

        this.keyPairHolder = bVsValue;
        if (flag) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return this.keyPairHolder;
    }

    static {
        b = new String("RSA");
    }

    public RsaKeyPairProvider(String text, int i) throws NoSuchAlgorithmException {
        super(KeyPairGenerator.getInstance(text), i);
    }
}
