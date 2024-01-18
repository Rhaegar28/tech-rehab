package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private float mediaFeedback;
    private int numeroFeedback;
    private Ricambio ricambioCorrente;

    private TechRehab() {
        this.riparazioni = new HashMap<>();
        this.ricambi = new HashMap<>();
        this.clienti = new HashMap<>();
        this.fatture = new HashMap<>();
        loadRicambi();
        loadClienti();
        clienti.get(1).loadDispositivi1();
        clienti.get(2).loadDispositivi2();
        this.numeroFeedback = 0;
        this.mediaFeedback = 0;
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

    public float getMediaFeedback() {
        return mediaFeedback;
    }

    public Map<Integer, Riparazione> getRiparazioni() {
        return riparazioni;
    }

    public Map<Integer, Fattura> getFatture() {
        return fatture;
    }
    
    public Map<String, Ricambio> getRicambi() {
        return ricambi;
    }

    public Map<Integer, Cliente> getClienti() {
        return clienti;
    }    

    public void setRicambi(Map<String, Ricambio> ricambi) {
        this.ricambi = ricambi;
    }

    public void loadRicambi() {
        ricambi.put("DP124353dd", new Ricambio("DP124353dd", "Display", 300.0f,1));
        ricambi.put("B2353", new Ricambio("B2353", "Batteria", 80.0f,6));
        ricambi.put("FC887", new Ricambio("FC887", "Fotocamera", 306.35f,9));
    }

    public void loadClienti() {
        clienti.put(1, new Cliente("Daniele", "Russo", "3331234567", "daniele.russo@gmail.com"));
        clienti.put(2, new Cliente("Nunzio", "Fornitto", "3337654321", "nunzio.fornitto@gmail.com"));
        clienti.put(3, new Cliente("Angelo", "Frasca", "3331573244", "angelo.frasca@libero.it"));
    }

    public void nuovoPreventivo(String serialeDispositivo, int IDCliente) {
        try {
            clienteCorrente = clienti.get(IDCliente);
            if (clienteCorrente == null) {
                throw new Exception("Errore: Cliente non trovato.");
            }
            clienteCorrente.nuovoPreventivo(serialeDispositivo);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore: Si è verificato un errore imprevisto.");
        }
    }

    public void aggiungiGuasto(String serialeRicambio) {
        if (clienteCorrente == null) {
            throw new IllegalStateException("Nessun cliente corrente. Devi prima creare un preventivo.");
        }
        Ricambio ricambioSelezionato = ricambi.get(serialeRicambio);
        clienteCorrente.aggiungiGuasto(ricambioSelezionato);
    }

    public void definisciPriorita(boolean priorita) {
        clienteCorrente.definisciPriorita(priorita);
    }

    public void definisciOreLavoroPreviste(float orePreviste) {
        clienteCorrente.definisciOreLavoroPreviste(orePreviste);
    }

    public void definisciDataPrevistaConsegna(LocalDate dataPrevistaConsegna) {
        clienteCorrente.definisciDataPrevistaConsegna(dataPrevistaConsegna);
    }

    public Preventivo confermaPreventivo() {
        return clienteCorrente.confermaPreventivo();
    }

    public void accettaPreventivo(String descrizioneRiparazione, int codicePreventivo) {
        Riparazione r = clienteCorrente.nuovaRiparazione(descrizioneRiparazione, codicePreventivo);

        // Registrazione dei provider di servizi nel Subject Riparazione (Pattern
        // Observer Pull)
        Observer email = new EmailProviderObserver(r);
        Observer sms = new SMSProviderObserver(r);
        r.attach(email);
        r.attach(sms);
        // Fine registrazione

        riparazioni.put(r.getCodice(), r);
        clienteCorrente = null;
    }

    public void rifiutaPreventivo(int codicePreventivo) {
        clienteCorrente.rifiutaPreventivo(codicePreventivo);
        clienteCorrente = null;
    }

    public List<Riparazione> ottieniRiparazioniInCorso() {
        return riparazioni.values()
                .stream()
                .filter(r -> r.getStato().equals("In lavorazione") || r.getStato().equals("In carico"))
                .sorted(Comparator.comparingInt(r -> r.getPreventivo().getPriorita() ? 0 : 1))
                .collect(Collectors.toList());
    }

    public List<Riparazione> ottieniRiparazioniCompletate() {
        return riparazioni.values()
                .stream()
                .filter(r -> r.getStato().equals("Completato"))
                .sorted(Comparator.comparingInt(r -> r.getPreventivo().getPriorita() ? 0 : 1))
                .collect(Collectors.toList());
    }

    public Riparazione selezionaAggiornaRiparazione(int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.aggiornaStato();
        return r;
    }

    public void definisciOreManodopera(int codiceRiparazione, float oreManodopera) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.setOreManodopera(oreManodopera);
    }

    public Riparazione terminaRiparazione(int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.aggiornaStato();
        r.getPreventivo().getListaRicambi().forEach(ricambio -> ricambio.setQuantita(ricambio.getQuantita()-1));
        return r;
    }
    public boolean verificaQuantitaRicambi(int codiceRiparazione){
        Riparazione r = riparazioni.get(codiceRiparazione);
        for(Ricambio ricambio : r.getPreventivo().getListaRicambi()){
            if(ricambio.getQuantita()==0){
                return false; 
            }
        }
        return true;
    }

    public void sospendiRiparazione(int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.sospendi();
    }

    public void inserisciDispositivo(int IDCliente, String marca, String modello, String seriale,
            LocalDate fineGaranzia) {
        clienteCorrente = clienti.get(IDCliente);
        clienteCorrente.inserisciDispositivo(marca, modello, seriale, fineGaranzia);
    }

    public void confermaInserimentoDispositivo() {
        clienteCorrente.confermaInserimentoDispositivo();
        clienteCorrente = null;
    }

    public void modificaDispositivo(int IDCliente, String seriale, LocalDate fineGaranzia) {
        clienti.get(IDCliente).modificaDispositivo(seriale, fineGaranzia);
    }

    public void rimuoviDispositivo(int IDCliente, Scanner scanner) throws Exception {
        clienti.get(IDCliente).rimuoviDispositivo(scanner);
    }

    public Dispositivo ricercaDispositivo(int IDCliente, String seriale) {
        return clienti.get(IDCliente).ricercaDispositivo(seriale);
    }

    public void emettiFattura(int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.emettiFattura();
        fatturaCorrente = r.getFattura();
    }

    public void confermaFattura() {
        if (fatturaCorrente != null) {
            fatture.put(fatturaCorrente.getCodice(), fatturaCorrente);
            fatturaCorrente.stampaFattura();
            fatturaCorrente = null;
        }
    }

    public void consegnaDispositivo(int codicePreventivo) {
        for (Riparazione riparazione : riparazioni.values()) {
            if (riparazione.getStato() == "Completato" && riparazione.getPreventivo().getCodice() == codicePreventivo) {
                riparazione.aggiornaStato();
                riparazione.stampaRiparazione();
            }
        }
    }

    public void presaInCaricoRiparazione(int codicePreventivo) {
        for (Riparazione riparazione : riparazioni.values()) {
            if (riparazione.getStato() == null && riparazione.getPreventivo().getCodice() == codicePreventivo) {
                riparazione.aggiornaStato();
            }
        }
    }

    public Cliente inserisciCliente(String nome, String cognome, String telefono, String email) {
        clienteCorrente = new Cliente(nome, cognome, telefono, email);
        return clienteCorrente;
    }

    public void modificaCliente(int ID, String telefono, String email) {
        clienteCorrente = clienti.get(ID);
        clienteCorrente.updateCliente(telefono, email);
        clienteCorrente = null;
    }

    public void confermaInserimentoCliente() {
        clienti.put(clienteCorrente.getID(), clienteCorrente);
        clienteCorrente = null;
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

        System.out.println("Sei sicuro di voler rimuovere il cliente " + clienteRimosso.getNome() + " "
                + clienteRimosso.getCognome() + "? [S/N]");

        String response = scanner.next();
        if ("S".equalsIgnoreCase(response)) {
            clienti.remove(IDDaRimuovere);
            System.out.println("Cliente rimosso con successo.");
        } else {
            System.out.println("Rimozione annullata.");
        }
    }

    public Cliente ricercaCliente(int ID) {
        return clienti.get(ID);
    }

    public void richiestaFeedback(int IDCliente, int codiceRiparazione) {
        clienteCorrente = clienti.get(IDCliente);
        EmailProviderObserver email = new EmailProviderObserver();
        email.richiestaFeedback(clienteCorrente.getEmail(), codiceRiparazione);
        SMSProviderObserver sms = new SMSProviderObserver();
        sms.richiestaFeedback(clienteCorrente.getTelefono(), codiceRiparazione);
        clienteCorrente = null;
    }

    public void aggiornaFeedback(int feedback) {
        mediaFeedback = mediaFeedback * numeroFeedback;
        numeroFeedback++;
        mediaFeedback = mediaFeedback + feedback;
        mediaFeedback = mediaFeedback / numeroFeedback;
    }
 
    public void registraFeedback(int feedback, int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.setFeedback(feedback);
        aggiornaFeedback(feedback);
    }

    public List<Riparazione> ricercaRiparazioniPerCliente(int idCliente) {
        List<Riparazione> riparazioni = new ArrayList<>();

        Cliente cliente = clienti.get(idCliente);
        if (cliente != null) {
            riparazioni.addAll(cliente.ottieniRiparazioni());
        }

        return riparazioni;
    }

    public int ricercaRiparazione(int idCliente) {
        List<Riparazione> risultatoRicerca = ricercaRiparazioniPerCliente(idCliente);

        if (risultatoRicerca.isEmpty()) {
            System.out.println("Nessuna riparazione trovata per il cliente con ID " + idCliente);
        } else {
            System.out.println("Riparazioni per il cliente con ID " + idCliente + ":");
            for (Riparazione riparazione : risultatoRicerca) {
                System.out.println("Codice riparazione "+ riparazione.getCodice());
            }
        }
        return risultatoRicerca.size();
    }

    public void selezionaRiparazione(int codiceRiparazione) {
        Riparazione r = riparazioni.get(codiceRiparazione);
        r.stampaRiparazione();
    }

    public Ricambio inserisciRicambio(String seriale, String nome, float prezzo, int quantita) {
        ricambioCorrente = new Ricambio(seriale, nome, prezzo, quantita);
        return ricambioCorrente;
    }

    public void modificaRicambio(String seriale, float prezzo, int quantita) {
        ricambioCorrente = ricambi.get(seriale);
        ricambioCorrente.updateRicambio(prezzo, quantita);
        ricambioCorrente = null;
    }

    public void confermaInserimentoRicambio() {
        ricambi.put(ricambioCorrente.getSeriale(), ricambioCorrente);
        ricambioCorrente = null;
    }

    public void rimuoviRicambio(Scanner scanner) throws Exception {
        if (ricambi.isEmpty()) {
            System.out.println("Nessun ricambio registrato.");
            return;
        }

        System.out.println("Lista dei ricambi registrati:");
        for (Ricambio ricambio : ricambi.values()) {
            System.out.println(ricambio.getSeriale() + ": " + ricambio.getNome() + ", " + ricambio.getPrezzo() + " euro, quantità: " + ricambio.getQuantita());
        }

        System.out.print("Inserisci il seriale del ricambio da rimuovere: ");
        String serialeDaRimuovere = scanner.next();

        if (!ricambi.containsKey(serialeDaRimuovere)) {
            throw new Exception("Ricambio non trovato con seriale: " + serialeDaRimuovere);
        }

        Ricambio ricambioRimosso = ricambi.get(serialeDaRimuovere);

        System.out.println("Sei sicuro di voler rimuovere il ricambio " + ricambioRimosso.getSeriale() + " " + ricambioRimosso.getNome() + "? [S/N]");

        String response = scanner.next();
        if ("S".equalsIgnoreCase(response)) {
            ricambi.remove(serialeDaRimuovere);
            System.out.println("Ricambio rimosso con successo.");
        } else {
            System.out.println("Rimozione annullata.");
        }
    }

    public Ricambio ricercaRicambio(String seriale) {
        return ricambi.get(seriale);
    }
}
