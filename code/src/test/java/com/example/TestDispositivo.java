package com.example;


import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Before;

public class TestDispositivo {
    Dispositivo dispositivo;
    @Before
    public void setUp() {
        dispositivo = new Dispositivo("Samsung","S21","5265",LocalDate.parse("2021-12-12"));
    }
    @Test
    public void testSetModello() {
        dispositivo.setModello("S23");
        String modello = dispositivo.getModello();
        assertEquals("S23", modello);
    }

    @Test
    public void testSetMarca() {
        dispositivo.setMarca("Xiaomi");
        String marca = dispositivo.getMarca();
        assertEquals("Xiaomi", marca);
    }
    @Test
    public void testSetSeriale() {
        dispositivo.setSeriale("S512");
        String seriale = dispositivo.getSeriale();
        assertEquals("S512", seriale);
    }
    
    @Test
    public void testGaranzia() {
        LocalDate dataFineGaranzia = LocalDate.parse("2023-12-31");
        dispositivo.setFinegaranzia(dataFineGaranzia);
        LocalDate garanzia = dispositivo.getFinegaranzia();
        assertEquals(dataFineGaranzia, garanzia);
    }

    @Test
    public void testSetModelloConValoreNull() {
    dispositivo.setModello(null);
    assertNull(dispositivo.getModello());
    }

    @Test
    public void testSetMarcaConValoreVuoto() {
        dispositivo.setMarca("");
        assertEquals("", dispositivo.getMarca());
    }

}