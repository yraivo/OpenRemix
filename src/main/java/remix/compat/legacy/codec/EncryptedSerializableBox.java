package remix.compat.legacy.codec;

import remix.compat.legacy.security.DecryptionGuardCondition;

import net.minecraft.entity.Entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptedSerializableBox {
    private static String f;
    private java.util.Set<DecryptionGuardCondition> set;
    private static String[] packedTextFragments;
    private java.io.Serializable serializable;
    private ValueConverter valueConverter;
    private String b;
    private static String g;
    public static byte[] bwV;

    public EncryptedSerializableBox(
            java.io.Serializable serializable,
            String text,
            byte[] abyte,
            DecryptionGuardCondition[] decryptionGuardConditionArray)
            throws Exception {
        this.b = text;
        this.method_22c87636710ca9871283(decryptionGuardConditionArray);
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

        ValueConverter bQbValue;
        try (ObjectOutputStream objectoutputstream =
                new ObjectOutputStream(bytearrayoutputstream)) {
            objectoutputstream.writeObject(serializable);
            objectoutputstream.flush();
            byte[] abyte1 =
                    this.method_5e3ff1d3334aec84fcae(bytearrayoutputstream.toByteArray(), abyte, 1);
            bQbValue =
                    ((remix.compat.legacy.codec.ValueConverter<byte[], java.io.Serializable>)
                            (abyte3 -> {
                                String textValue = method_c60693f457aa70be7160();
                                if (this.serializable != null) {
                                    return this.serializable;
                                }

                                try {
                                    ObjectInputStream objectinputstream =
                                            new ObjectInputStream(
                                                    new ByteArrayInputStream(
                                                            this.method_5e3ff1d3334aec84fcae(
                                                                    abyte1,
                                                                    ((byte[]) (abyte3)),
                                                                    2)));
                                    Throwable throwable = null;

                                    try {
                                        this.serializable = (Serializable) objectinputstream.readObject();
                                        return this.serializable;
                                    } catch (Throwable throwable1) {
                                        throwable = throwable1;
                                        throw throwable1;
                                    } finally {
                                        label73:
                                        if (objectinputstream != null) {
                                            if (throwable != null) {
                                                try {
                                                    objectinputstream.close();
                                                    break label73;
                                                } catch (Throwable throwable2) {
                                                    throwable.addSuppressed(throwable2);
                                                    if (textValue == null) {
                                                        break label73;
                                                    }
                                                }
                                            }

                                            objectinputstream.close();
                                        }
                                    }
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                    throw new NoSuchElementException();
                                }
                            }));
        } catch (IOException ioexception) {
            throw ioexception;
        }

        this.valueConverter = bQbValue;
    }

    // region String decoding support

    // endregion

    public remix.compat.legacy.codec.EncryptedSerializableBox method_22c87636710ca9871283(
            DecryptionGuardCondition... decryptionGuardConditionArray) {
        if (this.set == null && decryptionGuardConditionArray.length > 0) {
            this.set = new HashSet<>();
        }

        for (int i = 0; i < decryptionGuardConditionArray.length; i++) {
            DecryptionGuardCondition decryptionGuardCondition = decryptionGuardConditionArray[i];
            this.set.add(decryptionGuardCondition);
        }

        return this;
    }

    private byte[] method_5e3ff1d3334aec84fcae(byte[] abyte, byte[] abyte1, int i)
            throws Exception {
        Cipher cipher = Cipher.getInstance(this.b);
        cipher.init(i, new SecretKeySpec(abyte1, this.b));
        return cipher.doFinal(abyte);
    }

    public static String method_c60693f457aa70be7160() {
        return f;
    }

    public static void b(String text) {
        f = text;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public java.util.Collection Bz() {
        return Collections.unmodifiableSet(this.set);
    }

    static {
        remix.compat.legacy.codec.EncryptedSerializableBox.packedTextFragments = new String[2];
        remix.compat.legacy.codec.EncryptedSerializableBox.packedTextFragments[0] = new String("gu0W6b");
        remix.compat.legacy.codec.EncryptedSerializableBox.packedTextFragments[1] = new String("*X[9");
        if (method_c60693f457aa70be7160() != null) {
            b(packedTextFragments[0]);
        }
        g = "+aqx";
        bwV = new byte[] {97, 66, 99, 68, 101, 70, 103, 72, 105, 74, 107, 76, 109, 78, 111, 80};
    }

    public java.io.Serializable method_0ddad1007a047eedaabe(byte[] abyte) {
        String text = method_c60693f457aa70be7160();
        byte b0 = 0;
        String textValue = text;
        if (this.set != null) {
            for (DecryptionGuardCondition decryptionGuardCondition : this.set) {
                b0 |= !decryptionGuardCondition.aY() ? 1 : 0;
                if (textValue != null) {
                    Entity.setLegacyGlobalFlowMarker(new String[3]);
                    break;
                }
            }
        }

        return (Serializable) (b0 == 0 ? this.valueConverter.method_69be51ad1433bd242905(abyte) : null);
    }

    public EncryptedSerializableBox(
            java.io.Serializable serializable,
            DecryptionGuardCondition[] decryptionGuardConditionArray)
            throws Exception {
        this((Serializable) serializable, new String("AES"), bwV, decryptionGuardConditionArray);
    }

    // region String decoding support

    // endregion
}
