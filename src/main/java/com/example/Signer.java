package com.example;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

public class Signer {

    private Input input;
    private KeyPair kp;

    public Signer(Input input, KeyPair kp) {
        this.input = input;
        this.kp = kp;
    }

    public void generateXMLSignature() {
        try {
            var xmlSigFac = XMLSignatureFactory.getInstance("DOM");
            var digestMethod = xmlSigFac.newDigestMethod(DigestMethod.SHA1, null);
            var transform = xmlSigFac.newTransform(Transform.ENVELOPED, 
                (TransformParameterSpec) null);
            var ref = xmlSigFac.newReference("", digestMethod, 
                Collections.singletonList(transform), null, null);
            var canonMethod = xmlSigFac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, 
                (C14NMethodParameterSpec) null);
            var sigMethod = xmlSigFac.newSignatureMethod("http://www.w3.org/2000/09/xmldsig#rsa-sha1", null);
            var signedInfo = xmlSigFac.newSignedInfo(canonMethod, sigMethod, Collections.singletonList(ref));         
            var kif = xmlSigFac.getKeyInfoFactory();
            var kv = kif.newKeyValue(this.kp.getPublic());
            var keyInfo = kif.newKeyInfo(Collections.singletonList(kv));
            var signature = xmlSigFac.newXMLSignature(signedInfo, keyInfo);
            signature.sign(new DOMSignContext(kp.getPrivate(), input.getDoc().getFirstChild()));
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | KeyException | MarshalException | XMLSignatureException e) {
            throw new RuntimeException(e);
        }
    }

}
