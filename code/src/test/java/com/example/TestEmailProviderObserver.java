package com.example;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class TestEmailProviderObserver {

    @Test
    public void testUpdateFatturaEmessa() {
        Fattura fattura = new Fattura();
        EmailProviderObserver emailObserver = new EmailProviderObserver(fattura);
        fattura.attach(emailObserver);
        fattura.setStato(true);
        assertEquals(true, emailObserver.getObserverStateFattura());
        assertNull(emailObserver.getObserverStateRiparazione());
    }

    @Test
    public void testUpdateRiparazioneStato() {
        Dispositivo dispositivo = new Dispositivo("S7", "Samsung", "12548", LocalDate.parse("2023-02-01"));
        Preventivo preventivo = new Preventivo(dispositivo);
        Riparazione riparazione = new Riparazione("Dispositivo non si accende il display",preventivo);
        EmailProviderObserver emailObserver = new EmailProviderObserver(riparazione);
        riparazione.attach(emailObserver);
        riparazione.setStato("In corso");
        assertEquals("In corso", emailObserver.getObserverStateRiparazione());
        assertNull(emailObserver.getObserverStateFattura());
    }
}
