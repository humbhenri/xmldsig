package com.example;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class InputTest {

    private InputStream resource;

    @Before
    public void setup() {
        this.resource = getClass().getClassLoader().getResourceAsStream("test.xml"); 
    }
    
    @Test
    public void readFile() {
        var inputTest = new Input(resource);
        assertTrue(inputTest.ok());
    }

}
