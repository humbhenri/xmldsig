package com.example;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyPair;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length != 1 || args.length != 3) {
            System.out.println("Usage: xmldsig INPUT\nxmldsig INPUT PRIVATE_KEY PUBLIC_KEY");
            return;
        }
        try (InputStream xmlFile = new FileInputStream(args[0])) {
            getKeyPair(new Input(xmlFile), args);
        }
    }

    private static void getKeyPair(Input input, String[] args) throws Exception {
        var pkp = new PublicKeyPair();
        KeyPair kp;
        if (args.length == 3) {
            try (InputStream privateKey = new FileInputStream(args[1]);
                    InputStream publicKey = new FileInputStream(args[2])) {
                kp = pkp.loadPrivateAndPublicKey(privateKey, publicKey);
                generateSignature(input, kp);
            }
        } else {
            kp = pkp.create();
            generateSignature(input, kp);
        }
    }

    private static void generateSignature(Input input, KeyPair kp) {
        var signer = new Signer(input, kp);
        signer.generateXMLSignature();
        System.out.println("Digital signature generated: ");
        input.print(System.out);
    }
}
