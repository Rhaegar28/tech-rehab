package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Cliente {
    
    private static int count=0;

    private int ID;
    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private Map<String, Dispositivo> dispositivi;
    private Dispositivo dispositivoSelezionato;
    private Dispositivo dispositivoCorrente;

    public Cliente(String nome, String cognome, String telefono, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.ID=++count;
        this.dispositivi = new HashMap<>();
    }

    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        this.ID = iD;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome= nome;
    }   
    public String getCognome() {
        return cognome;
    }
    public Dispositivo getDispositivoCorrente() {
        return dispositivoCorrente;
    }
    public Dispositivo getDispositivoSelezionato() {
        return dispositivoSelezionato;
    }
    public void setCognome(String cognome) {
        this.cognome= cognome;
    }
    public String getTelefono() {
        return telefono;
    }   
    public void setTelefono(String telefono) {
        this.telefono= telefono;
    }   
    public String getEmail() {
        return email;
    }       
    public void setEmail(String email) {
        this.email= email;
    }
    public Map<String, Dispositivo> getDispositivi() {
        return dispositivi;
    }


    public void updateCliente(String telefono, String email){
        setTelefono(telefono);
        setEmail(email);
    }

    public void loadDispositivi1() {
        dispositivi.put("SM-S918B", new Dispositivo("S23 ultra", "Samsung", "SM-S918B", LocalDate.parse("2025-06-12")));
        dispositivi.put("23116PN5BC", new Dispositivo("14 ultra", "Xiaomi", "23116PN5BC", LocalDate.parse("2023-12-31")));
    }

    public void loadDispositivi2() {
        dispositivi.put("SM-G930", new Dispositivo("S7", "Samsung", "SM-G930", LocalDate.parse("2018-12-31")));
        dispositivi.put("SM-S918B", new Dispositivo("S23 ultra", "Samsung", "SM-S918B", LocalDate.parse("2026-12-31")));
    }

    public void nuovoPreventivo(String serialeDispositivo) {
        try {
            dispositivoSelezionato=dispositivi.get(serialeDispositivo);
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore: Dispositivo non trovato.");
            }
            dispositivoSelezionato.nuovoPreventivo();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore: Si è verificato un errore imprevisto.");
        }
    }

    public void aggiungiGuasto(Ricambio ricambio){
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore: Dispositivo non trovato.");
            }
            dispositivoSelezionato.aggiungiGuasto(ricambio);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore: Si è verificato un errore imprevisto.");
        }
    }

    public void definisciPriorita(boolean priorita){
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore: Dispositivo non trovato.");
            }
            dispositivoSelezionato.definisciPriorita(priorita);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore: Si è verificato un errore imprevisto.");
        }
    }

    public void definisciOreLavoroPreviste(float orePreviste){
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore: Dispositivo non trovato.");
            }
            dispositivoSelezionato.definisciOreLavoroPreviste(orePreviste);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore: Si è verificato un errore imprevisto.");
        }
    }

    public void definisciDataPrevistaConsegna(LocalDate dataPrevistaConsegna){
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore: Dispositivo non trovato.");
            }
            dispositivoSelezionato.definisciDataPrevistaConsegna(dataPrevistaConsegna);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore: Si è verificato un errore imprevisto.");
        }
    }

    public Preventivo confermaPreventivo(){
        return dispositivoSelezionato.confermaPreventivo();
    }

    public Riparazione nuovaRiparazione(String descrizioneRiparazione, int codicePreventivo){
        Riparazione r = dispositivoSelezionato.nuovaRiparazione(descrizioneRiparazione,codicePreventivo);
        dispositivoSelezionato=null;
        return r;
    }

    public void rifiutaPreventivo(int codicePreventivo){
        try {
            if (dispositivoSelezionato == null) {
                throw new Exception("Errore: Dispositivo non trovato.");
            }
            dispositivoSelezionato.cancellaPreventivo(codicePreventivo);
            dispositivoSelezionato=null;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore: Si è verificato un errore imprevisto.");
        }
    }

    public void inserisciDispositivo(String marca, String modello, String seriale, LocalDate fineGaranzia){
        dispositivoCorrente =  new Dispositivo(modello, marca, seriale, fineGaranzia);
    }

    public void confermaInserimentoDispositivo(){
        dispositivi.put(dispositivoCorrente.getSeriale(), dispositivoCorrente);
        dispositivoCorrente =  null;
    }

    public void modificaDispositivo(String seriale, LocalDate fineGaranzia){
        dispositivoCorrente = dispositivi.get(seriale);
        dispositivoCorrente.setFinegaranzia(fineGaranzia);
        dispositivoCorrente = null;
    }

    public void rimuoviDispositivo(Scanner scanner) throws Exception {
        if (dispositivi.isEmpty()) {
            System.out.println("Nessun dispositivo registrato.");
            return;
        }
    
        System.out.println("Lista dei dispositivi registrati:");
        for (Dispositivo dispositivo : dispositivi.values()) {
            System.out.println(dispositivo.getSeriale() + ": " + dispositivo.getMarca() + " " + dispositivo.getModello());
        }
    
        System.out.print("Inserisci il seriale del dispositivo da rimuovere: ");
        String serialeDaRimuovere = scanner.next();
    
        if (!dispositivi.containsKey(serialeDaRimuovere)) {
            throw new Exception("Dispositivo non trovato con il seriale: " + serialeDaRimuovere);
        }
    
        Dispositivo dispositivoRimosso = dispositivi.get(serialeDaRimuovere);
    
        System.out.println("Sei sicuro di voler rimuovere il dispositivo " + dispositivoRimosso.getMarca() + " " + dispositivoRimosso.getModello() + "? [S/N]");
    
        String response = scanner.next();

        if ("S".equalsIgnoreCase(response)) {
            dispositivi.remove(serialeDaRimuovere);
            System.out.println("Dispositivo rimosso con successo.");
        } else {
            System.out.println("Rimozione annullata.");
        }
    }

    public Dispositivo ricercaDispositivo(String seriale){
        return dispositivi.get(seriale);
    }

    public List<Riparazione> ottieniRiparazioni() {
        List<Riparazione> riparazioni = new ArrayList<>();
        for (Dispositivo dispositivo : dispositivi.values()) {
            riparazioni.addAll(dispositivo.ottieniRiparazioni());
        }
        return riparazioni;
    }
}

