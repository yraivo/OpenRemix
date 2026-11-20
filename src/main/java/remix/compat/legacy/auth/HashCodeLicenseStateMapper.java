package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

import remix.compat.legacy.codec.IntCodeProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class HashCodeLicenseStateMapper extends AbstractLicenseStateMapper {
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    public LicenseValidationState bL(int i) {
        String text = LicenseValidationState.d();
        Map map = this.lN();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_1049 = (map.entrySet()).iterator();
                (__recoveryIterator171_1049).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_1049).next();
            if (((IntCodeProvider) entry.getKey()).method_9b417d27949cba33a0d0() == i) {
                return (LicenseValidationState) entry.getValue();
            }

            if (textValue != null) {
                break;
            }
        }

        return null;
    }

    public LicenseValidationState method_94ac91b86d63211bb039(Class<?> oclass) {
        String text = LicenseValidationState.d();
        Map map = this.lN();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_1585 = (map.entrySet()).iterator();
                (__recoveryIterator171_1585).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_1585).next();
            if (((IntCodeProvider) entry.getKey()).method_9b417d27949cba33a0d0()
                    == oclass.hashCode()) {
                return (LicenseValidationState) entry.getValue();
            }

            if (textValue != null) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            LicenseValidationState.b(packedTextFragments[0]);
        }

        return null;
    }

    public LicenseValidationState method_4b074e12c96985c0bbb8(Class<? extends Exception> oclass) {
        String text = LicenseValidationState.d();
        Map map = this.lN();
        Iterator iterator = map.entrySet().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            if (((LicenseValidationState) entry.getValue()).afx() == oclass) {
                return (LicenseValidationState) entry.getValue();
            }

            if (textValue != null) {
                break;
            }
        }

        return null;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public HashCodeLicenseStateMapper() {
        this(LicenseValidationState.d());
    }

    private HashCodeLicenseStateMapper(String initialFlowMarker) {
        super(new HashMap());

        String text = initialFlowMarker;

        LicenseValidationState[] licenseValidationStateArray = LicenseValidationState.values();
        String textValue = text;
        int i = 0;

        while (i < licenseValidationStateArray.length) {
            LicenseValidationState licenseValidationState = licenseValidationStateArray[i];
            this.lN()
                    .put(
                            licenseValidationState::method_9b417d27949cba33a0d0,
                            licenseValidationState);
            i++;
            if (textValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[5]);
                break;
            }
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.auth.HashCodeLicenseStateMapper.packedTextFragments[0] =
                new String("U9oJv");
    }
    // endregion
}
