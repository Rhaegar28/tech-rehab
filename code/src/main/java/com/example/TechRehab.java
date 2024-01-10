package com.example;


import java.time.LocalDate;
import java.util.Comparator;
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
    private Map<Integer, Cliente> clienti;
    private Map<Integer, Fattura> fatture;
    private Dispositivo dispositivoSelezionato;
    private Dispositivo dispositivoCorrente; 
    private Fattura fatturaCorrente;
    private Cliente clienteCorrente;

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

    public Dispositivo getDispositivoSelezionato() {
        return dispositivoSelezionato;
    }
    public Map<Integer, Riparazione> getRiparazioni() {
        return riparazioni;
    }
    public Map<String, Dispositivo> getDispositivi() {
        return dispositivi;
    }
    public Map<Integer, Fattura> getFatture() {
        return fatture;
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

    public void nuovoPreventivo(String serialeDispositivo) {
        try {
            dispositivoSelezionato=dispositivi.get(serialeDispositivo);
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore TechRehab: Dispositivo non trovato.");
            }
            dispositivoSelezionato.nuovoPreventivo();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore TechRehab: Si è verificato un errore imprevisto.");
        }
    }

    public void aggiungiGuasto(String serialeRicambio){
        Ricambio ricambioSelezionato=ricambi.get(serialeRicambio);
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore TechRehab: Dispositivo non trovato.");
            }
            dispositivoSelezionato.aggiungiGuasto(ricambioSelezionato);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore TechRehab: Si è verificato un errore imprevisto.");
        }
    }
    public void definisciPriorita(boolean priorita){
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore TechRehab: Dispositivo non trovato.");
            }
            dispositivoSelezionato.definisciPriorita(priorita);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore TechRehab: Si è verificato un errore imprevisto.");
        }
    }
    public void definisciOreLavoroPreviste(float orePreviste){
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore TechRehab: Dispositivo non trovato.");
            }
            dispositivoSelezionato.definisciOreLavoroPreviste(orePreviste);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore TechRehab: Si è verificato un errore imprevisto.");
        }
    }
    public void definisciDataPrevistaConsegna(LocalDate dataPrevistaConsegna){
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore TechRehab: Dispositivo non trovato.");
            }
            dispositivoSelezionato.definisciDataPrevistaConsegna(dataPrevistaConsegna);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore TechRehab: Si è verificato un errore imprevisto.");
        }
    }  
    public Preventivo confermaPreventivo(){
            return dispositivoSelezionato.confermaPreventivo();
    }
    public void accettaPreventivo(String descrizioneRiparazione, int codicePreventivo){
        Riparazione r=dispositivoSelezionato.nuovaRiparazione(descrizioneRiparazione,codicePreventivo);
        
        //Registrazione dei provider di servizi nel Subject Riparazione (Pattern Observer Pull)
        Observer email = new EmailProviderObserver(r);
	    Observer sms = new SMSProviderObserver(r);
        r.attach(email);
        r.attach(sms);
        //Fine registrazione
        
        riparazioni.put(r.getCodice(),r);
        dispositivoSelezionato=null;
    }

    public List<Riparazione> ottieniRiparazioni(){
        return riparazioni.values()
                .stream()
                .filter(r->r.getStato().equals("In lavorazione") || r.getStato().equals("In carico"))
                .sorted(Comparator.comparingInt(r -> r.getPreventivo().getPriorita() ? 0 : 1))
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
        r.setDataFineRiparazione(LocalDate.now());
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

    public void rimuoviDispositivo() throws Exception {
        if (dispositivi.isEmpty()) {
            System.out.println("Nessun dispositivo registrato.");
            return;
        }
    
        System.out.println("Lista dei dispositivi registrati:");
        for (Dispositivo dispositivo : dispositivi.values()) {
            System.out.println(dispositivo.getSeriale() + ": " + dispositivo.getMarca() + " " + dispositivo.getModello());
        }
    
        System.out.print("Inserisci il seriale del dispositivo da rimuovere: ");
        Scanner scanner = new Scanner(System.in);
        String serialeDaRimuovere = scanner.next();
    
        if (!dispositivi.containsKey(serialeDaRimuovere)) {
            scanner.close();
            throw new Exception("Dispositivo non trovato con il seriale: " + serialeDaRimuovere);
        }
    
        Dispositivo dispositivoRimosso = dispositivi.get(serialeDaRimuovere);
    
        System.out.println("Sei sicuro di voler rimuovere il dispositivo " + dispositivoRimosso.getMarca() + " " + dispositivoRimosso.getModello() + "? [Y/N]");
    
        String response = scanner.next();
        if ("Y".equalsIgnoreCase(response)) {
            dispositivi.remove(serialeDaRimuovere);
            System.out.println("Dispositivo rimosso con successo.");
        } else {
            System.out.println("Rimozione annullata.");
        }
        scanner.close();
    }

    public Dispositivo ricercaDispositivo(String seriale){
        return dispositivi.get(seriale);
    }

    public void emettiFattura(int codiceRiparazione){
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.emettiFattura();
        fatturaCorrente = r.getFattura();
    }

    public void confermaFattura(){
        if (fatturaCorrente != null) {
            fatture.put(fatturaCorrente.getCodice(), fatturaCorrente);
            fatturaCorrente.stampaFattura();
            fatturaCorrente = null;
        }
    }

    public void consegnaDispositivo(int codicePreventivo){
        for (Riparazione riparazione : riparazioni.values()) {
            if (riparazione.getStato() == "Completato" && riparazione.getPreventivo().getCodice() == codicePreventivo) {
                riparazione.setStato("Consegnato");
                riparazione.stampaRiparazione();
            }
        }
    }

    public void presaInCaricoRiparazione(int codicePreventivo){
        for (Riparazione riparazione : riparazioni.values()) {
            if (riparazione.getStato() == null && riparazione.getPreventivo().getCodice() == codicePreventivo) {
                riparazione.setStato("In Carico");
            }
        }
    }    

    public Cliente inserisciCliente(String nome, String cognome, String telefono, String email){
        clienteCorrente =  new Cliente(nome, cognome, telefono, email);
        return clienteCorrente;
    }

    public void modificaCliente(int ID, String nome, String cognome, String telefono, String email){
        clienteCorrente = clienti.get(ID);
        clienteCorrente.updateCliente(nome, cognome, telefono, email);
        clienteCorrente = null;
    }

    public void confermaInserimentoCliente(){
        clienti.put(clienteCorrente.getID(), clienteCorrente);
        clienteCorrente =  null;
    }

    public void rimuoviCliente() throws Exception {
        if (clienti.isEmpty()) {
            System.out.println("Nessun cliente registrato.");
            return;
        }
    
        System.out.println("Lista dei clienti registrati:");
        for (Cliente cliente : clienti.values()) {
            System.out.println(cliente.getID() + ": " + cliente.getNome() + " " + cliente.getCognome());
        }
    
        System.out.print("Inserisci l'ID del cliente da rimuovere: ");
        Scanner scanner = new Scanner(System.in);
        int IDDaRimuovere = scanner.nextInt();
    
        if (!clienti.containsKey(IDDaRimuovere)) {
            scanner.close();
            throw new Exception("Cliente non trovato con l'ID: " + IDDaRimuovere);
        }
    
        Cliente clienteRimosso = clienti.get(IDDaRimuovere);
    
        System.out.println("Sei sicuro di voler rimuovere il cliente " + clienteRimosso.getNome() + " " + clienteRimosso.getCognome() + "? [Y/N]");
    
        String response = scanner.next();
        if ("Y".equalsIgnoreCase(response)) {
            clienti.remove(IDDaRimuovere);
            System.out.println("Cliente rimosso con successo.");
        } else {
            System.out.println("Rimozione annullata.");
        }
        scanner.close();
    }   

    public Cliente ricercaCliente(int ID){
        return clienti.get(ID);
    }
}
