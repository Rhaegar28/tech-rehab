package com.example;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)   // Ordina i test in base al nome per eseguirli in ordine
public class TestTechRehab {

    private TechRehab techRehab;

    @Before
    public void setUp() {
        techRehab = TechRehab.getInstance();
    }

    @Test
    public void testANuovoPreventivo() {
        techRehab.nuovoPreventivo("SM-G930");
        assertNotNull(techRehab.getDispositivoSelezionato().getPreventivoCorrente());
    }

    @Test
    public void testBAggiungiGuasto() {
        techRehab.nuovoPreventivo("SM-G930");
        techRehab.aggiungiGuasto("DP124353dd");
        assertNotNull(techRehab.getDispositivoSelezionato().getPreventivoCorrente().getListaRicambi());
        assertEquals(1, techRehab.getDispositivoSelezionato().getPreventivoCorrente().getListaRicambi().size());
    }

    @Test
    public void testCConfermaPreventivo() {
        techRehab.nuovoPreventivo("SM-G930");
        techRehab.confermaPreventivo();
        assertNotNull(techRehab.getDispositivoSelezionato().getPreventivi());
        assertEquals(1, techRehab.getDispositivoSelezionato().getPreventivi().size());
    }

    @Test
    public void testDAccettaPreventivo() {
        techRehab.nuovoPreventivo("SM-G930");
        techRehab.confermaPreventivo();
        Preventivo preventivo = techRehab.getDispositivoSelezionato().getPreventivi().get(4);
        techRehab.accettaPreventivo("Descrizione Riparazione", preventivo.getCodice());
        assertNotNull(techRehab.getRiparazioni().get(preventivo.getRiparazione().getCodice()));
    }

    @Test
    public void testEOttieniRiparazioni() {
        assertFalse(techRehab.ottieniRiparazioni().isEmpty());
    }

}