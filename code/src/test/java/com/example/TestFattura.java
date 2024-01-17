package com.example;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

public class TestFattura {

    private Fattura fattura;

    @Before
    public void setUp() {
        fattura = new Fattura();
    }
    @After
    public void tearDown() {
        fattura = null;
    }

    @Test
    public void testGetCodice() {
        fattura.setCodice(1);
        assertEquals(1, fattura.getCodice());
    }

    @Test
    public void testGetDataEmissione() {
        assertNotNull(fattura.getDataEmissione());
    }

    @Test
    public void testSetDataEmissione() {
        LocalDate nuovaData = LocalDate.of(2022, 1, 1);
        fattura.setDataEmissione(nuovaData);
        assertEquals(nuovaData, fattura.getDataEmissione());
    }

    @Test
    public void testGetStato() {
        assertNull(fattura.getStato());
    }

    @Test
    public void testSetStato() {
        fattura.setStato(true);
        assertTrue(fattura.getStato());
    }

    @Test
    public void testSetCostoDefinitivo() {
        float nuovoCosto = 100.50f;
        fattura.setCostoDefinitivo(nuovoCosto);
        assertEquals(nuovoCosto, fattura.getCostoDefinitivo(),0);
    }
}
