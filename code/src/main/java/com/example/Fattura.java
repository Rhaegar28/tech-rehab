package com.example;

import java.time.LocalDate;

public class Fattura extends Subject {
    
    private static int count=0;
    private int codice;
    private Boolean stato;
    private LocalDate dataEmissione;
    private float costDefinitivo;

    public Fattura() {
        this.codice=++count;
        this.dataEmissione=LocalDate.now(); 
    }

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
    public float getCostoDefinitivo() {
        return costDefinitivo;
    }
    public Boolean getStato() {
        return stato;
    }
    public void setStato(Boolean stato) {
        this.stato = stato;
        notifyObservers();
    }    

    public void setCostoDefinitivo(float costDefinitivo) {
        this.costDefinitivo = costDefinitivo;
    }
    public void stampaFattura() {
        System.out.println("Codice: " + codice);
        System.out.println("Data emissione: " + dataEmissione);
        System.out.println("Costo: " + costDefinitivo);
    }

}
