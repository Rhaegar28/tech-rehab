package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class TestCliente {
    private Cliente cliente;
    @Before
    public void setUp() {
        cliente = new Cliente("Angelo", "Frasca", "3331573244", "angelofrasca@gmail.com");
    }
    @After
    public void tearDown() {
        cliente = null;
    }
    @Test
    public void testGetSetID() {
        cliente.setID(1);
        assertEquals(1, cliente.getID());
    }
    @Test
    public void testGetSetNome() {
        cliente.setNome("Angelo");
        assertEquals("Angelo", cliente.getNome());
    }
    @Test
    public void testGetSetCognome() {
        cliente.setCognome("Frasca");
        assertEquals("Frasca", cliente.getCognome());
    }
    @Test
    public void testGetSetTelefono() {
        cliente.setTelefono("3331573244");
        assertEquals("3331573244", cliente.getTelefono());
    }
    @Test
    public void testGetSetEmail() {
        cliente.setEmail("angelofrasca@gmail.com");
        assertEquals("angelofrasca@gmail.com", cliente.getEmail());
    }
    @Test
    public void testUpdate() {
        cliente.updateCliente("3331573244", "angelofrasca@gmail.com");
        assertEquals("3331573244", cliente.getTelefono());
        assertEquals("angelofrasca@gmail.com", cliente.getEmail());
    }
 
    @Test
    public void testInserisciDispositivo() {
        cliente.inserisciDispositivo("Xiaomi","Mi11T","SHFT12",LocalDate.parse("2023-12-31"));
        cliente.confermaInserimentoDispositivo();
        assertNotNull(cliente.getDispositivi().get("SHFT12"));
    }
   
    @Test
    public void testModificaDispositivo() {
        cliente.inserisciDispositivo("Xiaomi","Mi11T","SHFT12",LocalDate.parse("2023-12-31"));
        cliente.confermaInserimentoDispositivo();
        cliente.modificaDispositivo("SHFT12",LocalDate.parse("2023-10-31"));
        assertNotNull(cliente.getDispositivi().get("SHFT12"));
    }
 
    @Test
    public void testRicercaDispositivo() {
        cliente.inserisciDispositivo("Xiaomi","Mi11T","SHFT12",LocalDate.parse("2023-12-31"));
        cliente.confermaInserimentoDispositivo();
        assertNotNull(cliente.ricercaDispositivo("SHFT12"));
    }
    @Test
    public void testDefinisciPriorita() {
        cliente.inserisciDispositivo("Xiaomi","Mi11T","SHFT12",LocalDate.parse("2023-12-31"));
        cliente.confermaInserimentoDispositivo();
        cliente.nuovoPreventivo("SHFT12");
        cliente.definisciPriorita(true);
        assertTrue(cliente.getDispositivoSelezionato().getPreventivoCorrente().getPriorita());
    }
}