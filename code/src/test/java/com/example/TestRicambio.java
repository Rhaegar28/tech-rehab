package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

public class TestRicambio {

    private Ricambio ricambio;

    @Test
    public void testGetCodice() {
        Ricambio ricambio = new Ricambio("12345", "Display", 10.90f,2);
        assertEquals("12345", ricambio.getSeriale());
    }

    @Test
    public void testGetDescrizione() {
        Ricambio ricambio = new Ricambio("12345", "Batteria", 11.99f,3);
        assertEquals("Batteria", ricambio.getNome());
    }

    @Test
    public void testGetPrezzo() {
        Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f,6);
        assertEquals(8.99, ricambio.getPrezzo(), 0.01);
    }

    @Test
    public void testSetCodice() {
        Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f,7);
        ricambio.setSeriale("12345");
        assertEquals("12345", ricambio.getSeriale());
    }

    @Test
    public void testSetDescrizione() {
        Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f,2);
        ricambio.setNome("Batteria");
        assertEquals("Batteria", ricambio.getNome());
    }

    @Test
    public void testSetPrezzo() {
    Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f,1);
    ricambio.setPrezzo(8.99f);
    assertEquals(8.99, ricambio.getPrezzo(), 0.01);
    }

    @Test
    public void testUpdate() {
        Ricambio ricambio = new Ricambio("12345", "USB-C", 8.99f,6);
        ricambio.updateRicambio(12.99f, 15);
        assertEquals(12.99f, ricambio.getPrezzo(), 0.01);
        assertEquals(15, ricambio.getQuantita());
    }
}
