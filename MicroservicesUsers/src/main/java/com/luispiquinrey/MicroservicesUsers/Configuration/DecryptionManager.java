package com.luispiquinrey.MicroservicesUsers.Configuration;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DecryptionManager {

    private PrivateKey privateKey;

    @Value("${spring.app.privateKeySSL}")
    private String privateKeyBase64;

    @PostConstruct
    public void initPrivateKey() throws Exception {
        byte[] decodedKey = decode(privateKeyBase64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(keySpec);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String decrypt(String base64EncryptedMessage) throws Exception {
        if (privateKey == null) {
            throw new IllegalStateException("Private key not initialized. Call initPrivateKey() first.");
        }

        byte[] encryptedBytes = decode(base64EncryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}