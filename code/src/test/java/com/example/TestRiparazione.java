package com.example;


import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;


public class TestRiparazione {
    private Riparazione riparazione;

    @Before
    public void setUp() {
        Dispositivo d = new Dispositivo("modello", "marca", "seriale"
        , LocalDate.of(2024, 10, 2));        
        riparazione = new Riparazione("Display rotto, sostituire il display", new Preventivo(d));
    }
    @Test
    public void testGetSetDescrizione() {
        String descrizione = "Display rotto sostituire il display";
        riparazione.setDescrizione(descrizione);
        assertEquals(descrizione, riparazione.getDescrizione());
    }
    
}