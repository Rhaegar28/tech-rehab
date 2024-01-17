package com.example;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

public class TestSMSProviderObserver{

    private Riparazione riparazione;
    private SMSProviderObserver smsObserver;
    private Preventivo preventivo;
    private Dispositivo dispositivo;

    @Before
    public void setUp() {
        dispositivo = new Dispositivo("S7", "Samsung", "12548", LocalDate.parse("2023-02-01"));
        preventivo = new Preventivo(dispositivo);
        riparazione = new Riparazione("Dispositivo non si accende il display",preventivo);
        smsObserver = new SMSProviderObserver(riparazione);
        riparazione.attach(smsObserver);
    }
    @After
    public void tearDown() {
        riparazione = null;
        smsObserver = null;
        preventivo = null;
        dispositivo = null;
    }
    
    @Test
    public void testUpdate() {
        assertNull(smsObserver.getObserverState());
        riparazione.setStato(new StatoRiparazioneInCarico(riparazione));
        assertEquals("In carico", smsObserver.getObserverState());
        riparazione.aggiornaStato();
        assertEquals("In lavorazione", smsObserver.getObserverState());
    }

}
