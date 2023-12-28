package com.example;

import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class TestPreventivo {

    private Preventivo preventivo;

    @Before
    public void setUp() {
        preventivo = new Preventivo();
    }

    @Test
    public void testGetDataEmissione() {
        assertNotNull(preventivo.getDataEmissione());
    }

    @Test
    public void testGetDataPrevistaConsegna() {
        assertNull(preventivo.getDataPrevistaConsegna());
        LocalDate dataPrevistaConsegna = LocalDate.now().plusDays(7);
        preventivo.setDataPrevistaConsegna(dataPrevistaConsegna);
        assertEquals(dataPrevistaConsegna, preventivo.getDataPrevistaConsegna());
    }

    @Test
    public void testGetOreLavoroPreviste() {
        assertEquals(0.0f, preventivo.getOreLavoroPreviste(), 0.0f);
        preventivo.setOreLavoroPreviste(10.5f);
        assertEquals(10.5f, preventivo.getOreLavoroPreviste(), 0.0f);
    }

    @Test
    public void testGetPriorita() {
        assertFalse(preventivo.getPriorita());
        preventivo.setPriorita(true);
        assertTrue(preventivo.getPriorita());
    }

    @Test
    public void testGetCostoPrevisto() {
        assertEquals(0.0f, preventivo.getCostoPrevisto(), 0.0f);
        preventivo.setOreLavoroPreviste(5.0f);
        preventivo.setCostoPrevisto();
        assertEquals(200.0f, preventivo.getCostoPrevisto(), 0.0f);

        // Test con priorità
        preventivo.setPriorita(true);
        preventivo.setCostoPrevisto();
        assertEquals(240.0f, preventivo.getCostoPrevisto(), 0.0f);
    }

    @Test
    public void testGetRiparazione() {
        assertNull(preventivo.getRiparazione());

        Riparazione riparazione = preventivo.nuovaRiparazione("DescrizioneTest");
        assertNotNull(preventivo.getRiparazione());
        assertEquals(riparazione, preventivo.getRiparazione());
    }

    @Test
    public void testAggiungiGuasto() {
        Ricambio ricambio = new Ricambio("123", "TestRicambio", 50.0f);
        preventivo.aggiungiGuasto(ricambio);

        assertEquals(1, preventivo.getListaRicambi().size());
        assertEquals(ricambio, preventivo.getListaRicambi().get(0));
    }

    @Test
    public void testNuovaRiparazione() {
        assertNull(preventivo.getRiparazione());

        Riparazione riparazione = preventivo.nuovaRiparazione("DescrizioneTest");
        assertNotNull(preventivo.getRiparazione());
        assertEquals(riparazione, preventivo.getRiparazione());
        assertEquals(preventivo, riparazione.getPreventivo());
    }

}
