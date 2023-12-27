package com.example;

public class Riparazione {
    private static int count=0;
    private int codice;
    private String descrizione;
    private String stato;
    private float oreManodopera;
    private Preventivo preventivo;

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
    public Preventivo getPreventivo() {
        return preventivo;
    }
    public void setPreventivo(Preventivo preventivo) {
        this.preventivo = preventivo;
    }

    public Riparazione(String descrizioneRiparazione, Preventivo preventivo) {
        this.preventivo=preventivo;
        this.descrizione=descrizioneRiparazione;
        this.stato="In carico";
        this.codice=++count;
    }
    
    public void stampaRiparazione() {
        System.out.println("Codice: " + codice);
        System.out.println("Descrizione: " + descrizione);
        System.out.println("Stato: " + stato);
        System.out.println("Ore manodopera: " + oreManodopera);
    }
}
