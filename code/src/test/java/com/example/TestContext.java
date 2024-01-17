package com.example;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestContext {
    Context context;

    @Before
    public void setUp() {
        context = new Context();
    }

    @After
    public void tearDown() {
        context = null;
    }

    @Test
    public void testEseguiStrategyScontoGiorni() {
        context.setStrategy(new ScontoGiorniStrategy());
        float prezzoOrario = 10.0f;
        float risultato = context.eseguiStrategy(prezzoOrario);
        assertEquals(8.5f, risultato, 0.01); 
    }

    @Test
    public void testEseguiStrategyScontoOre() {
        context.setStrategy(new ScontoOreStrategy());
        float prezzoOrario = 10.0f;
        float risultato = context.eseguiStrategy(prezzoOrario);
        assertEquals(9.0f, risultato, 0.01); 
    }

    @Test
    public void testEseguiStrategyClienteVIP() {
        context.setStrategy(new ClienteVIPStrategy());
        float prezzoOrario = 10.0f;
        float risultato = context.eseguiStrategy(prezzoOrario);
        assertEquals(12.0f, risultato, 0.01); 
    }

}
