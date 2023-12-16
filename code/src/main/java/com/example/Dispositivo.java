package com.example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Dispositivo {
    private String modello;
    private String marca;
    private String seriale;
    private Date fineGaranzia;
    private Map<Integer,Preventivo> preventivi;
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
    
    public void nuovoPreventivo(){
        preventivoCorrente=new Preventivo();
    }

    public void aggiungiGuasto(Ricambio ricambio){
        preventivoCorrente.aggiungiGuasto(ricambio);
        
    }
    public void definisciPriorita(boolean priorita){
        preventivoCorrente.setPriorita(priorita);

    }
    public Preventivo confermaPreventivo(){
        int codicePreventivo=preventivoCorrente.getCodice();
        preventivi.put(codicePreventivo,preventivoCorrente);
        preventivoCorrente=null;
        return preventivi.get(codicePreventivo);
    }
    public Riparazione nuovaRiparazione(String descrizioneRiparazione, int codicePreventivo){
        return preventivi.get(codicePreventivo).nuovaRiparazione(descrizioneRiparazione);
    }

    public void updateDispositivo(String marca, String modello, Date fineGaranzia){
        setMarca(marca);
        setModello(modello);
        setFinegaranzia(fineGaranzia);
    }


    
}
