package com.example;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

import org.junit.Before;

public class TestTechRehab {

    private TechRehab techRehab;

    @Before
    public void setUp() {
        techRehab = TechRehab.getInstance();
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
        assertNotNull(techRehab.getRiparazioni().get(preventivo.getCodice()));
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
}
