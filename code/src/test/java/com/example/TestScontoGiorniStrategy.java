package com.example;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestScontoGiorniStrategy {
    //Test del metodo calcCostoH della classe ScontoGiorniStrategy con vari scenari
    private ScontoGiorniStrategy scontoGiorniStrategy;
    @Before
    public void setUp() {
        scontoGiorniStrategy = new ScontoGiorniStrategy();
    }
    @After
    public void tearDown() {
        scontoGiorniStrategy = null;
    }
    @Test
    public void testCalcolaCostoH() {
        float prezzoOrario = 10.0f;
        float risultato = scontoGiorniStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(8.5f, risultato, 0.01);
    }

    @Test
    public void testCalcolaCostoHConPrezzoZero() {
        float prezzoOrario = 0.0f;
        float risultato = scontoGiorniStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(0.0f, risultato, 0.01);
    }

    @Test
    public void testCalcolaCostoHConPrezzoNegativo() {
        float prezzoOrario = -10.0f;
        float risultato = scontoGiorniStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(-8.5f, risultato, 0.01);
    }
    @Test
    public void testCalcolaCostoHConPrezzoMaggiore() {
        float prezzoOrario = 20.0f;
        float risultato = scontoGiorniStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(17.0f, risultato, 0.01); 
    }

    @Test
    public void testCalcolaCostoHConPrezzoDecimali() {
        float prezzoOrario = 7.5f;
        float risultato = scontoGiorniStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(6.375f, risultato, 0.01);
    }
}
