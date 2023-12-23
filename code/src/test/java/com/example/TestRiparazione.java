package com.example;


import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class TestRiparazione {
    private Riparazione riparazione;

    @Before
    public void setUp() {
        riparazione = new Riparazione("Display rotto, sostituire il display");
    }
    @Test
    public void testGetSetDescrizione() {
        String descrizione = "Display rotto sostituire il display";
        riparazione.setDescrizione(descrizione);
        assertEquals(descrizione, riparazione.getDescrizione());
    }
    
}