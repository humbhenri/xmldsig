package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Input {

    private InputStream resource;
    private Node doc;

    public Input(InputStream resource) {
        this.resource = resource;        
    }

    public boolean ok() {
        return resource != null;
    }

    public Node getDoc() {
        if (this.doc != null) {
            return this.doc;
        }
        var dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            this.doc = dbf.newDocumentBuilder().parse(resource);
            return this.doc;
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(OutputStream os) {
        var tf = TransformerFactory.newInstance();
        try {
            var trans = tf.newTransformer();
            trans.transform(new DOMSource(getDoc()), new StreamResult(os));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

}
