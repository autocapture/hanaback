package com.aimskr.ac2.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

import java.util.Base64;

public class AES256Cipher {
    // 비밀키
    private static final String SECRET_KEY = "4nzalkjGeY";

    // 암호화
    public static String encrypt(String text, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] key = new byte[16];
            int i = 0;

            for(byte b : secretKey.getBytes()) {
                key[i++%16] ^= b;
            }

            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            return new String(Hex.encodeHex(cipher.doFinal(text.getBytes("UTF-8")))).toUpperCase();
        } catch(Exception e) {
            return text;
        }
    }

    // 복호화
    public static String decrypt(String encryptedText, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] key = new byte[16];
            int i = 0;

            for(byte b : secretKey.getBytes()) {
                key[i++%16] ^= b;
            }

            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
            return new String(cipher.doFinal(Hex.decodeHex(encryptedText.toCharArray())));
        } catch(Exception e) {
            return encryptedText;
        }
    }
}
