package com.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import java.util.Scanner;

public class TechRehab {
    private static TechRehab techRehab;
    private Map<Integer, Riparazione> riparazioni;
    private Map<String, Ricambio> ricambi;
    private Map<String, Dispositivo> dispositivi;
    private Dispositivo dispositivoSelezionato;
    private Dispositivo dispositivoCorrente; 

    private TechRehab() {
        this.riparazioni = new HashMap<>();
        this.ricambi = new HashMap<>();
        this.dispositivi = new HashMap<>();
        loadRicambi();
        loadDispositivi();
    }

    public static TechRehab getInstance() {
        if (techRehab == null) {
            techRehab = new TechRehab();
        }
        return techRehab;
    }

    public void loadRicambi() {
        ricambi.put("DP124353dd", new Ricambio("DP124353dd", "Display", 300.0f));
        ricambi.put("B2353", new Ricambio("B2353", "Batteria", 80.0f));
        ricambi.put("FC887", new Ricambio("FC887", "Fotocamera", 306.35f));
    }

    public void loadDispositivi() {
        dispositivi.put("SM-G930", new Dispositivo("S7", "Samsung", "SM-G930", LocalDate.parse("2018-12-31")));
        dispositivi.put("SM-S918B", new Dispositivo("S23 ultra", "Samsung", "SM-S918B", LocalDate.parse("2026-12-31")));
        dispositivi.put("23116PN5BC", new Dispositivo("14 ultra", "Xiaomi", "23116PN5BC", LocalDate.parse("2023-12-31")));
    }

    public void nuovoPreventivo(String serialeDispositivo){
        Dispositivo dispositivoSelezionato=dispositivi.get(serialeDispositivo);
        dispositivoSelezionato.nuovoPreventivo();
    }

    public void aggiungiGuasto(String serialeRicambio){
        Ricambio ricambioSelezionato=ricambi.get(serialeRicambio);
        dispositivoSelezionato.aggiungiGuasto(ricambioSelezionato);
    }
    public void definisciPriorita(boolean priorita){
        dispositivoSelezionato.definisciPriorita(priorita);
    }
    public void definisciOreLavoroPreviste(float orePreviste){
        dispositivoSelezionato.definisciOreLavoroPreviste(orePreviste);
    }
    public void definisciDataPrevistaConsegna(LocalDate dataPrevistaConsegna){
        dispositivoSelezionato.definisciDataPrevistaConsegna(dataPrevistaConsegna);
    }
    public Preventivo confermaPreventivo(){
        return dispositivoSelezionato.confermaPreventivo();
    }
    public void accettaPreventivo(String descrizioneRiparazione, int codicePreventivo){
        Riparazione r=dispositivoSelezionato.nuovaRiparazione(descrizioneRiparazione,codicePreventivo);
        riparazioni.put(r.getCodice(),r);
        dispositivoSelezionato=null;
    }

    public List<Riparazione> ottieniRiparazioni(){
        return riparazioni.values().stream()
                                .filter(r->r.getStato().equals("In lavorazione") 
                                    || r.getStato().equals("In carico"))
                                .collect(Collectors.toList());
    }

    public Riparazione selezionaRiparazione(int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.setStato("In lavorazione");
        return r;
    }

    public void definisciOreManodopera(int codiceRiparazione, float oreManodopera) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.setOreManodopera(oreManodopera);
    }

    public Riparazione terminaRiparazione(int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.setStato("Completato");
        return r;
    }

    public Dispositivo inserisciDispositivo(String marca, String modello, String seriale, LocalDate fineGaranzia){
        dispositivoCorrente =  new Dispositivo(modello, marca, seriale, fineGaranzia);
        return dispositivoCorrente;
    }

    public void confermaInserimentoDispositivo(){
        dispositivi.put(dispositivoCorrente.getSeriale(), dispositivoCorrente);
        dispositivoCorrente =  null;
    }

    public void modificaDispositivo(String seriale, String marca, String modello, LocalDate fineGaranzia){
        dispositivoCorrente = dispositivi.get(seriale);
        dispositivoCorrente.updateDispositivo(marca, modello, fineGaranzia);
        dispositivoCorrente = null;
    }

    public void rimuoviDispositivo(String seriale){
        dispositivoCorrente =  dispositivi.get(seriale);

        System.out.println("Sei sicuro di voler rimuovere il dispositivo " + dispositivoCorrente.getMarca() + " " + dispositivoCorrente.getModello() + "?[Y,N]");

        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        scanner.close();
     
        if(response == "Y"){
            dispositivi.remove(seriale);
        } 
    }

    public Dispositivo ricercaDispositivo(String seriale){
        return dispositivi.get(seriale);
    }
    

}
