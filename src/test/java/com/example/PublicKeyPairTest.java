package com.example;

import static org.junit.Assert.assertNotNull;

import java.io.DataInputStream;
import java.security.KeyPair;

import org.junit.Ignore;
import org.junit.Test;

public class PublicKeyPairTest {
    
    @Test
    public void createPublicKeyPair() {
        var pkp = new PublicKeyPair();
        KeyPair kp = pkp.create();
        assertNotNull(kp.getPrivate());
        assertNotNull(kp.getPublic());
    }

    @Test
    public void loadPrivateAndPublicKey() {
        var pkp = new PublicKeyPair();
        var privateKey = getClass().getClassLoader().getResourceAsStream("key.der");
        var publicKey = getClass().getClassLoader().getResourceAsStream("public.der");
        KeyPair kp = pkp.loadPrivateAndPublicKey(privateKey, publicKey);
        assertNotNull(kp.getPrivate());
        assertNotNull(kp.getPublic());
    }

}
