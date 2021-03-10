package com.example;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class PublicKeyPair {

    public KeyPair create() {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return kpg.generateKeyPair();
    }

    public KeyPair loadPrivateAndPublicKey(InputStream privateKey, InputStream publicKey) {
        try {
            return new KeyPair(getPublicKey(publicKey), getPrivateKey(privateKey));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RSAPrivateKey getPrivateKey(InputStream in) throws Exception {
        var bytes = in.readAllBytes();
        var spec = new PKCS8EncodedKeySpec(bytes);
        var kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) kf.generatePrivate(spec);
    }

    private RSAPublicKey getPublicKey(InputStream in) throws Exception {
        var keyBytes = in.readAllBytes();
        var spec = new X509EncodedKeySpec(keyBytes);
        var kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(spec);
    }
}
