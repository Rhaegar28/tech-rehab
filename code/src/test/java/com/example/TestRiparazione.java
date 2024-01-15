package com.example;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Before;


public class TestRiparazione {
    private Riparazione riparazione;
    private Dispositivo d;

    @Before
    public void setUp() {
        d = new Dispositivo("modello", "marca", "seriale", LocalDate.of(2024, 10, 2));        
        riparazione = new Riparazione("Display rotto, sostituire il display", new Preventivo(d));
    }
    @Test
    public void testGetSetDescrizione() {
        String descrizione = "Display rotto sostituire il display";
        riparazione.setDescrizione(descrizione);
        assertEquals(descrizione, riparazione.getDescrizione());
    }
    @Test
    public void testGetSetPreventivo() {
        Preventivo p = new Preventivo(d);
        riparazione.setPreventivo(p);
        assertEquals(p, riparazione.getPreventivo());
    }

    @Test
    public void testGetSetStato() {
        String stato = "Consegnato";
        //riparazione.consegnato();
        Preventivo p = new Preventivo(d);
        riparazione.setPreventivo(p);
        riparazione.setStato(new StatoRiparazioneCompletato(riparazione));
        riparazione.aggiornaStato();
        assertEquals(stato, riparazione.getStato());
    }
    
}