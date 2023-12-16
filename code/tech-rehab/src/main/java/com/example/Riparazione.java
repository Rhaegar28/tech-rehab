package com.example;



public class Riparazione {
    private int codice;
    private String descrizione;
    private String stato;
    private float oreManodopera;

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
    public String getStato() {
        return stato;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }
    public float getOreManodopera() {
        return oreManodopera;
    }
    public void setOreManodopera(float oreManodopera) {
        this.oreManodopera = oreManodopera;
    }
    public Riparazione(int codice, String descrizione, String stato, float oreManodopera) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.stato = stato;
        this.oreManodopera = oreManodopera;
    }
    

}
