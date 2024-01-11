package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Preventivo {
    private static final float COSTO_MANODOPERA = 40.0f;
    private static final float SOVRAPPREZZO_VIP = 0.2f;

    private static int count=0;
    private int codice;
    private LocalDate dataEmissione;
    private LocalDate dataPrevistaConsegna;
    private float oreLavoroPreviste;
    private boolean priorita;
    private float costoPrevisto;
    private List <Ricambio> listaRicambi;
    private Riparazione riparazione;

    private Dispositivo dispositivo;

    public int getCodice() {
        return codice;
    }
    public void setCodice(int codice) {
        this.codice = codice;
    }
    public LocalDate getDataEmissione() {
        return dataEmissione;
    }
    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }
    public LocalDate getDataPrevistaConsegna() {
        return dataPrevistaConsegna;
    }
    public void setDataPrevistaConsegna(LocalDate dataPrevistaConsegna) {
        this.dataPrevistaConsegna = dataPrevistaConsegna;
    }
    public float getOreLavoroPreviste() {
        return oreLavoroPreviste;
    }
    public void setOreLavoroPreviste(float oreLavoroPreviste) {
        this.oreLavoroPreviste = oreLavoroPreviste;
    }
    public boolean getPriorita() {
        return priorita;
    }
    public void setPriorita(boolean priorita) {
        this.priorita = priorita;
    }
    public float getCostoPrevisto() {
        return costoPrevisto;
    }
    public void setCostoPrevisto(float costoPrevisto) {
        this.costoPrevisto = costoPrevisto;
    }
    public List<Ricambio> getListaRicambi() {
        return listaRicambi;
    }
    public void setListaRicambi(List<Ricambio> listaRicambi) {
        this.listaRicambi = listaRicambi;
    }
    public Riparazione getRiparazione() {
        return riparazione;
    }
    public void setRiparazione(Riparazione riparazione) {
        this.riparazione = riparazione;
    }

    public Preventivo(Dispositivo dispositivo) {
        this.codice=++count;
        this.dataEmissione=LocalDate.now();
        this.listaRicambi=  new ArrayList<>();
        this.dispositivo=dispositivo;
    }
    public void aggiungiGuasto(Ricambio ricambio){
        this.listaRicambi.add(ricambio);
    }

    public Riparazione nuovaRiparazione(String descrizioneRiparazione){
        this.riparazione = new Riparazione(descrizioneRiparazione, this);
        return riparazione;
    }

    public void stampaPreventivo() {
        System.out.println("Preventivo: " + this.codice);
        System.out.println("Data emissione: " + this.dataEmissione);
        System.out.println("Data prevista consegna: " + this.dataPrevistaConsegna);
        System.out.println("Ore lavoro previste: " + this.oreLavoroPreviste);
        System.out.println("Priorità: " + this.priorita);
        System.out.println("Costo previsto: " + this.costoPrevisto);
        System.out.println("Lista ricambi: ");
        for (Ricambio r:listaRicambi){
            System.out.println(r.getSeriale() + " " + r.getNome() + " " + r.getPrezzo());
        }
    }

    public void setCostoPrevisto() {
        if (verificaGaranzia()) {
            this.costoPrevisto = 0;
        }
        else {
            float costo = COSTO_MANODOPERA * oreLavoroPreviste;
            if (priorita) {
                costo += costo * SOVRAPPREZZO_VIP;
            }
            costo += getPrezziRicambi();
            this.costoPrevisto = costo;
        }
    }
    
    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public float getPrezziRicambi() {
        float costo = 0;
        for (Ricambio r:listaRicambi){
            costo += r.getPrezzo();
        }
        return costo;
    }

    private boolean verificaGaranzia() {
        if (this.dataEmissione.isBefore(dispositivo.getFineGaranzia())) {
            return true;
        } else {
            return false;
        }
    }
}
