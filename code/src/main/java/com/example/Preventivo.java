package com.example;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Preventivo {
    private static int count=0;
    private int codice;
    private String descrizione;
    private LocalDate dataEmissione;
    private LocalDate dataPrevistaConsegna;
    private float oreLavoroPreviste;
    private boolean priorita;
    private float costoPrevisto;
    private List <Ricambio> listaRicambi;
    private Riparazione riparazione;

    public int getCodice() {
        return codice;
    }
    public void setCodice(int codice) {
        this.codice = codice;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public LocalDate getDataEmissione() {
        return dataEmissione;
    }
    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }
    public  LocalDate getDataPrecistaConsegna() {
        return dataPrevistaConsegna;
    }
    public void setDataPrecistaConsegna(LocalDate dataPrevistaConsegna) {
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
  /*   public Preventivo(int codice, String descrizione, Date dataEmissione, Date dataPrevistaConsegna, float oreLavoroPreviste, boolean priorita) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.dataEmissione = dataEmissione;
        this.dataPrevistaConsegna = dataPrevistaConsegna;
        this.oreLavoroPreviste = oreLavoroPreviste;
        this.priorita = priorita;
        this.listaRicambi=  new ArrayList<>();
    }*/


    public Preventivo() {
        this.codice=++count;
        this.listaRicambi=  new ArrayList<>();
    }
    public void aggiungiGuasto(Ricambio ricambio){
        this.listaRicambi.add(ricambio);
    }

    public Riparazione nuovaRiparazione(String descrizioneRiparazione){
        Riparazione r=new Riparazione(descrizioneRiparazione);
        this.riparazione=r;
        return r;
    }




    
}
