package net.minecraft.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptManager {

    private static Logger LOGGER;

    private static byte[] cipherOperation(int i, Key key, byte[] abyte) {
        try {
            return createTheCipherInstance(i, key.getAlgorithm(), key).doFinal(abyte);
        } catch (IllegalBlockSizeException illegalblocksizeexception) {
            illegalblocksizeexception.printStackTrace();
        } catch (BadPaddingException badpaddingexception) {
            badpaddingexception.printStackTrace();
        }

        net.minecraft.util.CryptManager.LOGGER.error("Cipher data failed!");
        return null;
    }

    private static Cipher createTheCipherInstance(int i, String text, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(text);
            cipher.init(i, key);
            return cipher;
        } catch (InvalidKeyException invalidkeyexception) {
            invalidkeyexception.printStackTrace();
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            nosuchalgorithmexception.printStackTrace();
        } catch (NoSuchPaddingException nosuchpaddingexception) {
            nosuchpaddingexception.printStackTrace();
        }

        net.minecraft.util.CryptManager.LOGGER.error("Cipher creation failed!");
        return null;
    }

    public static byte[] decryptData(Key key, byte[] abyte) {
        return cipherOperation(2, key, abyte);
    }

    public static SecretKey createNewSharedKey() {
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
            keygenerator.init(128);
            return keygenerator.generateKey();
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            throw new Error(nosuchalgorithmexception);
        }
    }

    public static byte[] encryptData(Key key, byte[] abyte) {
        return cipherOperation(1, key, abyte);
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
            keypairgenerator.initialize(1024);
            return keypairgenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            nosuchalgorithmexception.printStackTrace();
            net.minecraft.util.CryptManager.LOGGER.error("Key pair generation failed!");
            return null;
        }
    }

    private static byte[] digestOperation(String text, byte[]... abyte) {
        String textValue = Vec3i.getLegacyCoordinateFlowMarker();

        try {
            MessageDigest messagedigest = MessageDigest.getInstance(text);
            byte[][] abyte1 = abyte;
            int length = abyte.length;
            int j = 0;

            while (j < length) {
                byte[] abyte2 = abyte1[j];
                messagedigest.update(abyte2);
                j++;
                if (textValue == null) {
                    break;
                }
            }

            return messagedigest.digest();
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            nosuchalgorithmexception.printStackTrace();
            return null;
        }
    }

    public static byte[] getServerIdHash(String text, PublicKey publickey, SecretKey secretkey) {
        try {
            return digestOperation(
                    "SHA-1",
                    text.getBytes("ISO_8859_1"),
                    secretkey.getEncoded(),
                    publickey.getEncoded());
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
            unsupportedencodingexception.printStackTrace();
            return null;
        }
    }

    static {
        net.minecraft.util.CryptManager.LOGGER = LogManager.getLogger();
    }

    public static PublicKey decodePublicKey(byte[] abyte) {
        try {
            X509EncodedKeySpec x509encodedkeyspec = new X509EncodedKeySpec(abyte);
            KeyFactory keyfactory = KeyFactory.getInstance("RSA");
            return keyfactory.generatePublic(x509encodedkeyspec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException nosuchalgorithmexception) {
            net.minecraft.util.CryptManager.LOGGER.error("Public key reconstitute failed!");
            return null;
        }
    }

    public static SecretKey decryptSharedKey(PrivateKey privatekey, byte[] abyte) {
        return new SecretKeySpec(decryptData(privatekey, abyte), "AES");
    }

    public static Cipher createNetCipherInstance(int i, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(i, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (GeneralSecurityException generalsecurityexception) {
            throw new RuntimeException(generalsecurityexception);
        }
    }
}
