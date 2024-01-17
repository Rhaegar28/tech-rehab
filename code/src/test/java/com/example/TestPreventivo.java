package com.example;
import static org.junit.Assert.*;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPreventivo {

    private Preventivo preventivo;

    @Before
    public void setUp() {
        Dispositivo d = new Dispositivo("modello", "marca", "seriale", LocalDate.of(2024, 10, 2));
        preventivo = new Preventivo(d);
    }
    @After
    public void tearDown() {
        preventivo = null;
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
    public void testGetRiparazione() {
        assertNull(preventivo.getRiparazione());

        Riparazione riparazione = preventivo.nuovaRiparazione("DescrizioneTest");
        assertNotNull(preventivo.getRiparazione());
        assertEquals(riparazione, preventivo.getRiparazione());
    }

    @Test
    public void testAggiungiGuasto() {
        Ricambio ricambio = new Ricambio("123", "TestRicambio", 50.0f,1);
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

    @Test
    public void testSetCostoPrevistoSenzaGaranzia() {
        Dispositivo dispositivo = new Dispositivo("S7", "Samsung", "123456789", LocalDate.of(2020,01,01));
        Preventivo preventivo = new Preventivo(dispositivo);
        preventivo.setOreLavoroPreviste(5);
        preventivo.setPriorita(true);
        Ricambio ricambio1 = new Ricambio("1234","Display",40,1);
        Ricambio ricambio2 = new Ricambio("12457","Batteria",20,1);
        preventivo.aggiungiGuasto(ricambio1);
        preventivo.aggiungiGuasto(ricambio2);
        preventivo.setCostoPrevisto();
        float costoPrevisto = 40.0f * 5.0f * (1.0f + 0.2f) + ricambio1.getPrezzo() + ricambio2.getPrezzo();
        assertEquals(costoPrevisto, preventivo.getCostoPrevisto(), 0.01);
    }
    
    @Test
    public void testSetCostoPrevistoConGaranzia() {
        Dispositivo dispositivo = new Dispositivo("S7", "Samsung", "123456789", LocalDate.of(2020,01,01));
        Preventivo preventivo = new Preventivo(dispositivo);
        preventivo.setDataEmissione(dispositivo.getFineGaranzia().minusDays(1));
        Ricambio ricambio = new Ricambio("1234","Display",40,1);
        preventivo.aggiungiGuasto(ricambio);
        preventivo.setCostoPrevisto();
        assertEquals(0.0f, preventivo.getCostoPrevisto(), 0.01);
    }

}
