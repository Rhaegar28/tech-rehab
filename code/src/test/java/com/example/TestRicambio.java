package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestRicambio {

    @Test
    public void testGetCodice() {
        Ricambio ricambio = new Ricambio("12345", "Display", 10.90f);
        assertEquals("12345", ricambio.getSeriale());
    }

    @Test
    public void testGetDescrizione() {
        Ricambio ricambio = new Ricambio("12345", "Batteria", 11.99f);
        assertEquals("Batteria", ricambio.getNome());
    }

    @Test
    public void testGetPrezzo() {
        Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f);
        assertEquals(8.99, ricambio.getPrezzo(), 0.01);
    }

    @Test
    public void testSetCodice() {
        Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f);
        ricambio.setSeriale("12345");
        assertEquals("12345", ricambio.getSeriale());
    }

    @Test
    public void testSetDescrizione() {
        Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f);
        ricambio.setNome("Batteria");
        assertEquals("Batteria", ricambio.getNome());
    }

    @Test
    public void testSetPrezzo() {
    Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f);
    ricambio.setPrezzo(8.99f);
    assertEquals(8.99, ricambio.getPrezzo(), 0.01);
    }
}
