package com.example;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TechRehab {
    private static TechRehab techRehab;
    private Map<Integer, Riparazione> riparazioni;
    private Map<String, Ricambio> ricambi;
    private Map<String, Dispositivo> dispositivi;
    private Dispositivo dispositivoSelezionato;

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
        dispositivi.put("SM-G930", new Dispositivo("S7", "Samsung", "SM-G930", Date.valueOf("2018-12-31")));
        dispositivi.put("SM-S918B", new Dispositivo("S23 ultra", "Samsung", "SM-S918B", Date.valueOf("2025-12-31")));
        dispositivi.put("23116PN5BC", new Dispositivo("14 ultra", "Xiaomi", "23116PN5BC", Date.valueOf("2023-12-14")));
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
    public Preventivo confermaPreventivo(){
        return dispositivoSelezionato.confermaPreventivo();
    }
    public void accettaPreventivo(String descrizioneRiparazione, int codicePreventivo){
        Riparazione r=dispositivoSelezionato.nuovaRiparazione(descrizioneRiparazione,codicePreventivo);
        riparazioni.put(r.getCodice(),r);
        dispositivoSelezionato=null;
    }

}
