package remix.compat.legacy.lifecycle;

import remix.client.ClientServicesProvider;
import remix.compat.legacy.codec.EncryptedSerializableBox;
import remix.compat.legacy.security.DecryptionGuardCondition;

import remix.client.AbstractClientServices;

public final class EncryptedClientServicesProvider<T extends AbstractClientServices>
        implements ClientServicesProvider<T> {
    private EncryptedSerializableBox encryptedSerializableBox;
    private T abstractClientServices;

    public T getAbstractClientServices() {
        return this.abstractClientServices != null
                ? this.abstractClientServices
                : (this.abstractClientServices =
                        (T) this.encryptedSerializableBox.method_0ddad1007a047eedaabe(EncryptedSerializableBox.bwV));
    }

    public EncryptedClientServicesProvider(
            T class_d57c2eaec0881361742d,
            DecryptionGuardCondition... decryptionGuardConditionArray) {
        try {
            this.encryptedSerializableBox =
                    new EncryptedSerializableBox(
                            class_d57c2eaec0881361742d, decryptionGuardConditionArray);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
