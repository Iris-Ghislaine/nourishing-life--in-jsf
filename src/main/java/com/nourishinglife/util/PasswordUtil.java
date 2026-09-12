package com.nourishinglife.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Simple salted SHA-256 password hashing. No plaintext passwords are ever stored.
 */
public final class PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {
    }

    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hash(String rawPassword, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));
            byte[] hashed = digest.digest(rawPassword.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to hash password", e);
        }
    }

    public static boolean matches(String rawPassword, String salt, String expectedHash) {
        String actual = hash(rawPassword, salt);
        return actual.equals(expectedHash);
    }
}
