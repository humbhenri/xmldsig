package com.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

public class SignerTest {
    
    @Test
    public void generateXMLSignature() throws FileNotFoundException {
        var input = new Input(getClass().getClassLoader().getResourceAsStream("test.xml"));
        var pkp = new PublicKeyPair();
        var signer = new Signer(input, pkp.create());
        signer.generateXMLSignature();
        input.print(new FileOutputStream("/tmp/xmldsig"));
    }
}
