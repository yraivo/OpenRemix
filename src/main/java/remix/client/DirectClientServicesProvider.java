package remix.client;

import remix.compat.legacy.codec.EncryptedSerializableBox;

import net.minecraft.entity.Entity;
import remix.client.AbstractClientServices;

public class DirectClientServicesProvider<T extends AbstractClientServices>
        implements ClientServicesProvider<T> {
    private T abstractClientServices;
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.client.DirectClientServicesProvider.packedTextFragments[0] = new String("h6ODnc");
    }

    // endregion

    public T getAbstractClientServices() {
        return this.abstractClientServices;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public DirectClientServicesProvider(T class_d57c2eaec0881361742d) {
        EncryptedSerializableBox.method_c60693f457aa70be7160();
        this.abstractClientServices = (T) class_d57c2eaec0881361742d;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            EncryptedSerializableBox.b(packedTextFragments[0]);
        }
    }

    // region String decoding support

    // endregion
}
