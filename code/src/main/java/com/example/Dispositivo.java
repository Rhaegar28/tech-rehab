package com.example;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Dispositivo {
    private String modello;
    private String marca;
    private String seriale;
    private LocalDate fineGaranzia;
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
    public LocalDate getFineGaranzia() {
        return fineGaranzia;
    }
    public void setFinegaranzia(LocalDate fineGaranzia) {
        this.fineGaranzia = fineGaranzia;
    }
    public Preventivo getPreventivoCorrente() {
        return preventivoCorrente;
    }
    public Map<Integer, Preventivo> getPreventivi() {
        return preventivi;
    }

    public Dispositivo(String modello, String marca, String seriale, LocalDate fineGaranzia) {
        this.modello = modello;
        this.marca = marca;
        this.seriale = seriale;
        this.fineGaranzia = fineGaranzia;
        this.preventivi=new HashMap<>();
    }
    
    public void nuovoPreventivo() {
        try {
            //aggiunto parametro Dispositivo che crea il preventivo nel costruttore del Preventivo
            preventivoCorrente = new Preventivo(this);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore Dispositivo: Si è verificato un errore imprevisto.");
        }
    }

    public void aggiungiGuasto(Ricambio ricambio) {
        try {
            if (preventivoCorrente == null) {
                throw new Exception("Errore Dispositivo: Preventivo non inizializzato.");
            }

            preventivoCorrente.aggiungiGuasto(ricambio);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore Dispositivo: Si è verificato un errore imprevisto.");
        }
    }

    public void definisciPriorita(boolean priorita){
        preventivoCorrente.setPriorita(priorita);
    }

    public void definisciOreLavoroPreviste(float orePreviste){
        preventivoCorrente.setOreLavoroPreviste(orePreviste);
    }

    public void definisciDataPrevistaConsegna(LocalDate dataPrevistaConsegna){
        preventivoCorrente.setDataPrevistaConsegna(dataPrevistaConsegna);
    }

    public Preventivo confermaPreventivo(){
        int codicePreventivo=preventivoCorrente.getCodice();
        preventivoCorrente.setCostoPrevisto();
        preventivi.put(codicePreventivo,preventivoCorrente);
        preventivoCorrente=null;
        return preventivi.get(codicePreventivo);
    }

    public Riparazione nuovaRiparazione(String descrizioneRiparazione, int codicePreventivo){
        return preventivi.get(codicePreventivo).nuovaRiparazione(descrizioneRiparazione);
    }

    public void cancellaPreventivo(int codicePreventivo){
        preventivi.remove(codicePreventivo);
    }
}
