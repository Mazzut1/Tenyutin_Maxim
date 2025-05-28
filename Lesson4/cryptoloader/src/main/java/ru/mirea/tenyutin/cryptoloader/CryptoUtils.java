package ru.mirea.tenyutin.cryptoloader;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public final class CryptoUtils {

    private CryptoUtils() {}

    public static SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec(kg.generateKey().getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String msg, SecretKey key) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            return c.doFinal(msg.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptMsg(byte[] cipher, SecretKey key) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(cipher));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}