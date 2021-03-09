package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App 
{
    public static void main( String[] args )
    {
        if (args.length == 0) {
            System.out.println("An xml file is needed");
            return;
        }
        try {
            var input = new Input(new FileInputStream(args[0]));
            var pkp = new PublicKeyPair();
            var signer = new Signer(input, pkp.create());
            signer.generateXMLSignature();
            System.out.println("Digital signature generated: ");
            input.print(System.out);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
