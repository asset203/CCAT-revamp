package com.asset.ccat.user_management.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Component
public class Encryptor {
    private static final String SECRET_KEY = "3d6f8b8d9c5e4f7a2c1b9e3d5f8a9b0d6c7e8f4a1b2c3d4e5f6a7b8c9d0e1f2";

    public static String decrypt(String encryptedText) throws Exception {
        byte[] cipherData = Base64.getDecoder().decode(encryptedText);

        byte[] saltPrefix = "Salted__".getBytes();
        byte[] salt = Arrays.copyOfRange(cipherData, 8, 16);

        // Derive key and IV from password and salt
        byte[] keyAndIv = EVP_BytesToKey(SECRET_KEY.getBytes("UTF-8"), salt, 32, 16);
        byte[] key = Arrays.copyOfRange(keyAndIv, 0, 32);  // AES-256 requires a 32-byte key
        byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);  // 16-byte IV

        // Initialize cipher for AES decryption (CBC mode, PKCS5Padding)
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));

        // Decrypt the data (excluding the 16-byte "Salted__" header)
        byte[] encryptedBytes = Arrays.copyOfRange(cipherData, 16, cipherData.length);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, "UTF-8");
    }

    private static byte[] EVP_BytesToKey(byte[] password, byte[] salt, int keyLen, int ivLen) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] keyAndIv = new byte[keyLen + ivLen];
        byte[] hash = new byte[0];

        int i = 0;
        while (i < keyAndIv.length) {
            md.update(hash);
            md.update(password);
            md.update(salt);
            hash = md.digest();

            int remaining = keyAndIv.length - i;
            int copyLen = Math.min(hash.length, remaining);
            System.arraycopy(hash, 0, keyAndIv, i, copyLen);
            i += copyLen;
        }
        return keyAndIv;
    }
}
