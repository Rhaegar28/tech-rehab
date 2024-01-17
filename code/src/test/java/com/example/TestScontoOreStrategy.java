package com.example;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestScontoOreStrategy {
    //Test del metodo calcCostoH della classe ScontoOreStrategy con vari scenari
    private ScontoOreStrategy scontoOreStrategy;
    @Before
    public void setUp() {
        scontoOreStrategy = new ScontoOreStrategy();
    }
    @After
    public void tearDown() {
        scontoOreStrategy = null;
    }
    
    @Test
    public void testCalcolaCostoH() {
        float prezzoOrario = 10.0f;
        float risultato = scontoOreStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(9.0f, risultato, 0.01); 
    }

    @Test
    public void testCalcolaCostoHConPrezzoZero() {
        float prezzoOrario = 0.0f;
        float risultato = scontoOreStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(0.0f, risultato, 0.01); 
    }

    @Test
    public void testCalcolaCostoHConPrezzoNegativo() {
        float prezzoOrario = -10.0f;
        float risultato = scontoOreStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(-9.0f, risultato, 0.01); 
    }
    @Test
    public void testCalcolaCostoHConPrezzoMaggiore() {
        float prezzoOrario = 20.0f;
        float risultato = scontoOreStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(18.0f, risultato, 0.01); 
    }

    @Test
    public void testCalcolaCostoHConPrezzoDecimali() {
        float prezzoOrario = 7.5f;
        float risultato = scontoOreStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(6.75f, risultato, 0.01);
    }
}
