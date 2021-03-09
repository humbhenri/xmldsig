package com.example;

import static org.junit.Assert.assertNotNull;

import java.security.KeyPair;

import org.junit.Test;

public class PublicKeyPairTest {
    
    @Test
    public void createPublicKeyPair() {
        var pkp = new PublicKeyPair();
        KeyPair kp = pkp.create();
        assertNotNull(kp.getPrivate());
        assertNotNull(kp.getPublic());
    }
}
