package remix.compat.legacy.crypto;

import net.minecraft.entity.Entity;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyPairHolder {
    private static boolean keyLoaded;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public static void setKeyLoaded(boolean flag) {
        remix.compat.legacy.crypto.KeyPairHolder.keyLoaded = flag;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public static boolean isKeyLoaded() {
        return remix.compat.legacy.crypto.KeyPairHolder.keyLoaded;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public KeyPairHolder(KeyPair keypair) {
        super();
        boolean initialFlowFlag = isKeyLoaded();

        boolean flag1 = initialFlowFlag;

        boolean flag = flag1;
        this.publicKey = keypair.getPublic();
        this.privateKey = keypair.getPrivate();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setKeyLoaded(!flag);
        }
    }

    public KeyPairHolder(PublicKey publickey, PrivateKey privatekey) {
        this.publicKey = publickey;
        this.privateKey = privatekey;
    }

    public static boolean d() {
        boolean flag = isKeyLoaded();
        return !flag;
    }

    static {
        if (!d()) {
            setKeyLoaded(true);
        }
    }
}
