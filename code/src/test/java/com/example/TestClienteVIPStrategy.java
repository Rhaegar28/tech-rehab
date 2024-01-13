package com.example;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestClienteVIPStrategy{
    
    //Test del metodo calcCostoH della classe ClienteVIPStrategy con vari scenari
    private ClienteVIPStrategy clienteVIPStrategy;
    @Before
    public void setUp() {
        clienteVIPStrategy = new ClienteVIPStrategy();
    }
    @Test
    public void testCalcolaCostoH() {
        float prezzoOrario = 10.0f;
        float risultato = clienteVIPStrategy.calcolaCostoH(prezzoOrario);

        assertEquals(12.0f, risultato, 0.01);
    }

    @Test
    public void testCalcolaCostoHConPrezzoZero() {
        float prezzoOrario = 0.0f;
        float risultato = clienteVIPStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(0.0f, risultato, 0.01); 
    }

    @Test
    public void testCalcolaCostoHConPrezzoNegativo() {
        float prezzoOrario = -10.0f;
        float risultato = clienteVIPStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(-12.0f, risultato, 0.01);
    }
    
    @Test
    public void testCalcolaCostoHConPrezzoMaggiore() {
        float prezzoOrario = 20.0f;
        float risultato = clienteVIPStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(24.0f, risultato, 0.01); 
    }

    @Test
    public void testCalcolaCostoHConPrezzoDecimali() {
        float prezzoOrario = 7.5f;
        float risultato = clienteVIPStrategy.calcolaCostoH(prezzoOrario);
        assertEquals(9.0f, risultato, 0.01); 
    }

}
