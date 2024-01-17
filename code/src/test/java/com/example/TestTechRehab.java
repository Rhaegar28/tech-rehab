package com.example;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;

public class TestTechRehab {

    private TechRehab techRehab;

    @Before
    public void setUp() {
        techRehab = TechRehab.getInstance();
    }

    @After
    public void tearDown() {
        techRehab = null;
    }

    @Test
    public void testNuovoPreventivo() {
        techRehab.nuovoPreventivo("Seriale1", 1);
        assertNotNull(techRehab.getClienteCorrente());
    }
    @Test
    public void testAggiungiGuasto() {
        techRehab.nuovoPreventivo("23116PN5BC", 1);
        techRehab.aggiungiGuasto("DP124353dd");
        assertNotNull(techRehab.getClienteCorrente().getDispositivoSelezionato().getPreventivoCorrente().getListaRicambi());
    }

    @Test
    public void testConfermaPreventivo() {
        techRehab.nuovoPreventivo("SM-G930", 2);
        techRehab.aggiungiGuasto("B2353");
        techRehab.definisciPriorita(true);
        techRehab.definisciOreLavoroPreviste(3.5f);
        techRehab.definisciDataPrevistaConsegna(LocalDate.now().plusDays(5));
        Preventivo preventivo = techRehab.confermaPreventivo();
        assertNotNull(preventivo);
    }

    @Test
    public void testAccettaPreventivo() {
        techRehab.nuovoPreventivo("23116PN5BC", 1);
        techRehab.aggiungiGuasto("FC887");
        techRehab.definisciPriorita(false);
        techRehab.definisciOreLavoroPreviste(2.0f);
        techRehab.definisciDataPrevistaConsegna(LocalDate.now().plusDays(3));
        Preventivo preventivo = techRehab.confermaPreventivo();
        techRehab.accettaPreventivo("DescrizioneRiparazione", preventivo.getCodice());
        assertNotNull(techRehab.getRiparazioni().get(1));
        assertNull(techRehab.getClienteCorrente());
    }

    @Test
    public void testRifiutaPreventivo() {
        techRehab.nuovoPreventivo("23116PN5BC", 1);
        techRehab.aggiungiGuasto("DP124353dd");
        techRehab.definisciPriorita(true);
        techRehab.definisciOreLavoroPreviste(1.5f);
        techRehab.definisciDataPrevistaConsegna(LocalDate.now().plusDays(2));
        Preventivo preventivo = techRehab.confermaPreventivo();
        techRehab.rifiutaPreventivo(preventivo.getCodice());
        assertNull(techRehab.getRiparazioni().get(preventivo.getCodice()));
        assertNull(techRehab.getClienteCorrente());
    }

    @Test
    public void testOttieniRiparazioniInCorso() {
        assertNotNull(techRehab.ottieniRiparazioniInCorso());
    }

    @Test
    public void testOttieniRiparazioniCompletate() {
        assertNotNull(techRehab.ottieniRiparazioniCompletate());
    }

    @Test
    public void testInserisciRicambio() {
        techRehab.inserisciRicambio("ASDF123", "Set di viti", 5.67f, 100);
        techRehab.confermaInserimentoRicambio();
        assertNotNull(techRehab.getRicambi().get("ASDF123"));
    }

    @Test
    public void testModificaRicambio() {
        techRehab.inserisciRicambio("ASDF123", "Set di viti", 5.67f, 100);
        techRehab.confermaInserimentoRicambio();
        techRehab.modificaRicambio("ASDF123", 12.5f, 124);
        assertEquals(12.5f, techRehab.getRicambi().get("ASDF123").getPrezzo(), 0.01);
        assertEquals(124, techRehab.getRicambi().get("ASDF123").getQuantita());
    }

    @Test
    public void testGetRicambioBySeriale() {
        Ricambio ricambio = techRehab.inserisciRicambio("ASDF123", "Set di viti", 5.67f, 100);
        techRehab.confermaInserimentoRicambio();
        Ricambio ricambioOttenuto = techRehab.getRicambi().get("ASDF123");
        assertNotNull(ricambioOttenuto);
        assertEquals(ricambio, ricambioOttenuto);
    }

    @Test
    public void testGetListaRicambi() {
        Ricambio ricambio1 = techRehab.inserisciRicambio("ASDF123", "Set di viti", 5.67f, 100);
        techRehab.confermaInserimentoRicambio();
        Ricambio ricambio2 = techRehab.inserisciRicambio("GHJK456", "Scheda video", 1280f, 2);
        techRehab.confermaInserimentoRicambio();
        assertEquals(5, techRehab.getRicambi().size());
        assertTrue(techRehab.getRicambi().containsValue(ricambio1));
        assertTrue(techRehab.getRicambi().containsValue(ricambio2));
    }
}
