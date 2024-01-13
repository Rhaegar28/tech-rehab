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
    private Map<Integer, Cliente> clienti;
    private Map<Integer, Fattura> fatture;
    private Fattura fatturaCorrente;
    private Cliente clienteCorrente;

    private TechRehab() {
        this.riparazioni = new HashMap<>();
        this.ricambi = new HashMap<>();
        this.clienti = new HashMap<>();
        this.fatture = new HashMap<>();
        loadRicambi();
        loadClienti();
        clienti.get(1).loadDispositivi1();
        clienti.get(2).loadDispositivi2();
    }

    public static TechRehab getInstance() {
        if (techRehab == null) {
            techRehab = new TechRehab();
        }
        return techRehab;
    }
    public Cliente getClienteCorrente() {
        return clienteCorrente;
    }
    public Map<Integer, Riparazione> getRiparazioni() {
        return riparazioni;
    }
    public Map<Integer, Fattura> getFatture() {
        return fatture;
    }
    public void loadRicambi() {
        ricambi.put("DP124353dd", new Ricambio("DP124353dd", "Display", 300.0f));
        ricambi.put("B2353", new Ricambio("B2353", "Batteria", 80.0f));
        ricambi.put("FC887", new Ricambio("FC887", "Fotocamera", 306.35f));
    }

    public void loadClienti() {
        clienti.put(1, new Cliente("Mario", "Rossi", "3331234567", "mario.rossi@gmail.com"));
        clienti.put(2, new Cliente("Luigi", "Verdi", "3337654321", "luigi.verdi@gmail.com"));
        clienti.put(3, new Cliente("Angelo", "Frasca", "3331573244", "angelo.frasca@libero.it"));
    }

    public void nuovoPreventivo(String serialeDispositivo, int IDCliente) {
        try {
            clienteCorrente=clienti.get(IDCliente);
            if (clienteCorrente == null) {
                throw new Exception("Errore: Cliente non trovato.");
            }
            clienteCorrente.nuovoPreventivo(serialeDispositivo);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore: Si è verificato un errore imprevisto.");
        }
    }

    public void aggiungiGuasto(String serialeRicambio){
        Ricambio ricambioSelezionato=ricambi.get(serialeRicambio);
        clienteCorrente.aggiungiGuasto(ricambioSelezionato);
    }

    public void definisciPriorita(boolean priorita){
        clienteCorrente.definisciPriorita(priorita);
    }

    public void definisciOreLavoroPreviste(float orePreviste){
        clienteCorrente.definisciOreLavoroPreviste(orePreviste);
    }

    public void definisciDataPrevistaConsegna(LocalDate dataPrevistaConsegna){
        clienteCorrente.definisciDataPrevistaConsegna(dataPrevistaConsegna);
    }

    public Preventivo confermaPreventivo(){
        return clienteCorrente.confermaPreventivo();
    }

    public void accettaPreventivo(String descrizioneRiparazione, int codicePreventivo){
        Riparazione r = clienteCorrente.nuovaRiparazione(descrizioneRiparazione, codicePreventivo);
       
        //Registrazione dei provider di servizi nel Subject Riparazione (Pattern Observer Pull)
        Observer email = new EmailProviderObserver(r);
	    Observer sms = new SMSProviderObserver(r);
        r.attach(email);
        r.attach(sms);
        //Fine registrazione
        
        riparazioni.put(r.getCodice(),r);
        clienteCorrente = null;
    }

    public void rifiutaPreventivo(int codicePreventivo){
        clienteCorrente.rifiutaPreventivo(codicePreventivo);
        clienteCorrente = null;
    }

    public List<Riparazione> ottieniRiparazioniInCorso(){
        return riparazioni.values()
                .stream()
                .filter(r->r.getStato().equals("In lavorazione") || r.getStato().equals("In carico"))
                .sorted(Comparator.comparingInt(r -> r.getPreventivo().getPriorita() ? 0 : 1))
                .collect(Collectors.toList());
    }

    public List<Riparazione> ottieniRiparazioniCompletate(){
        return riparazioni.values()
                .stream()
                .filter(r->r.getStato().equals("Completato"))
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
    public void sospendiRiparazione(int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.setStato("In sospeso");
    }

    public void inserisciDispositivo(int IDCliente, String marca, String modello, String seriale, LocalDate fineGaranzia){
        clienteCorrente = clienti.get(IDCliente);
        clienteCorrente.inserisciDispositivo(marca, modello, seriale, fineGaranzia);
    }

    public void confermaInserimentoDispositivo(){
        clienteCorrente.confermaInserimentoDispositivo();
        clienteCorrente = null;
    }

    public void modificaDispositivo(int IDCliente, String seriale, LocalDate fineGaranzia){
        clienti.get(IDCliente).modificaDispositivo(seriale, fineGaranzia);
    }

    public void rimuoviDispositivo(int IDCliente, Scanner scanner) throws Exception {
        clienti.get(IDCliente).rimuoviDispositivo(scanner);
    }

    public Dispositivo ricercaDispositivo(int IDCliente, String seriale){
        return clienti.get(IDCliente).ricercaDispositivo(seriale);
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

    public void modificaCliente(int ID, String telefono, String email){
        clienteCorrente = clienti.get(ID);
        clienteCorrente.updateCliente(telefono, email);
        clienteCorrente = null;
    }

    public void confermaInserimentoCliente(){
        clienti.put(clienteCorrente.getID(), clienteCorrente);
        clienteCorrente =  null;
    }

    public void rimuoviCliente(Scanner scanner) throws Exception {
        if (clienti.isEmpty()) {
            System.out.println("Nessun cliente registrato.");
            return;
        }
    
        System.out.println("Lista dei clienti registrati:");
        for (Cliente cliente : clienti.values()) {
            System.out.println(cliente.getID() + ": " + cliente.getNome() + " " + cliente.getCognome());
        }
    
        System.out.print("Inserisci l'ID del cliente da rimuovere: ");
        int IDDaRimuovere = scanner.nextInt();
    
        if (!clienti.containsKey(IDDaRimuovere)) {
            throw new Exception("Cliente non trovato con l'ID: " + IDDaRimuovere);
        }
    
        Cliente clienteRimosso = clienti.get(IDDaRimuovere);
    
        System.out.println("Sei sicuro di voler rimuovere il cliente " + clienteRimosso.getNome() + " " + clienteRimosso.getCognome() + "? [S/N]");
    
        String response = scanner.next();
        if ("S".equalsIgnoreCase(response)) {
            clienti.remove(IDDaRimuovere);
            System.out.println("Cliente rimosso con successo.");
        } else {
            System.out.println("Rimozione annullata.");
        }
    }   

    public Cliente ricercaCliente(int ID){
        return clienti.get(ID);
    }
}
