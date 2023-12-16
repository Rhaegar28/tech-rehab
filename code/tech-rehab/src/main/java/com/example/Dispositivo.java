package com.example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Dispositivo {
    private String modello;
    private String marca;
    private String seriale;
    private Date fineGaranzia;
    private Map<String,Preventivo> preventivi;
    private Preventivo preventivoCorrente;
    
    public String getModello() {
        return modello;
    }
    public void setModello(String modello) {
        this.modello = modello;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getSeriale() {
        return seriale;
    }
    public void setSeriale(String seriale) {
        this.seriale = seriale;
    }
    public Date getFinegaranzia() {
        return fineGaranzia;
    }
    public void setFinegaranzia(Date fineGaranzia) {
        this.fineGaranzia = fineGaranzia;
    }

    public Dispositivo(String modello, String marca, String seriale, Date fineGaranzia) {
        this.modello = modello;
        this.marca = marca;
        this.seriale = seriale;
        this.fineGaranzia = fineGaranzia;
        this.preventivi=new HashMap<>();
    }
    
}
