package com.luispiquinrey.MicroservicesUsers.Configuration;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class EncryptionManager {

    private PublicKey publicKey;

    public static final String PUBLIC_KEY_OPEN_OPENSSL =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv7Lqd4OeI/w1sRC6Icg8aDLX+yIlcHKHwW3RVtagshYdRdBwuB1UfZwsCevVIBFBIPc1iHCeXQVvJqBxjitLgr9vCEavvoy7LZXChjJ7jg2uVTmNIE4GWpd8iKD4FnSfE2F9TFEesrMw7yV9/7UnDohRWE1a8Vr4ZTm8MgbGmRrrGNxXyTRMfD5yCTYxIzFRrgaJGkkmJKKaE/pr0D3xQqOjDdzUwPTAAtr4LxFYgnzzlh1cdBuaf1Ms9Kx47KXn+IiMVRB65K5KogxO+/7GbHfRF7+Ginn8nHxD+fwyWKek2P1xfAHIumg0wNTV7TKzWCx3tD4jNFHwYKz+kj9ruwIDAQAB";

    public void initPublicKey() throws Exception {
        byte[] decodedKey = decode(PUBLIC_KEY_OPEN_OPENSSL);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.publicKey = keyFactory.generatePublic(keySpec);
    }

    public byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public String encrypt(String message) throws Exception {
        if (publicKey == null) {
            throw new IllegalStateException("Public key not initialized. Call initPublicKey() first.");
        }

        byte[] messageBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(messageBytes);
        return encode(encryptedBytes);
    }
}
