package com.sphereon.commons;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SigUtils {

    public static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    public static String hmacSha256(String data, String secret) {
        try {

            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return toBase64(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new RuntimeException("Creation of hmacSha256 signature failed", ex);
        }
    }

    public static String toBase64(byte[] bytes) {
        return ENCODER.encodeToString(bytes);
    }

    public static String toBase64(String content) {
        return ENCODER.encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }
}
