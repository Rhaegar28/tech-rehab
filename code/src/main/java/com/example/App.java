package com.example;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        TechRehab techRehab = TechRehab.getInstance();
        Scanner scanner = new Scanner(System.in);

        int scelta;
        do {
            System.out.println("Menu:");
            System.out.println("1. Gestisci dispositivo");
            System.out.println("2. Emissione preventivo");
            System.out.println("3. Riparazione dispositivo");
            System.out.println("0. Esci");

            System.out.print("Inserisci il numero corrispondente all'azione desiderata: ");
            try {
                scelta = scanner.nextInt();

                switch (scelta) {
                    case 1:
                        gestisciDispositivo(techRehab, scanner);
                        break;
                    case 2:
                        emettiPreventivo(techRehab, scanner);
                        break;
                    case 3:
                        riparaDispositivo(techRehab, scanner);
                        break;
                    case 0:
                        System.out.println("Uscita dal programma.");
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input non valido. Inserisci un numero intero.");
                scanner.next(); // Pulisce il buffer di input
                scelta = -1;
            } catch (DateTimeException e) {
                System.out.println("Errore nella gestione della data: " + e.getMessage());
                scelta = -1;
            } catch (Exception e) {
                System.out.println("Si è verificato un errore durante l'esecuzione: " + e.getMessage());
                scelta = -1;
            }

        } while (scelta != 0);

        scanner.close();
    }

    private static void gestisciDispositivo(TechRehab techRehab, Scanner scanner) throws Exception {
        int scelta;
        do {
            System.out.println("Menu:");
            System.out.println("1. Inserisci dispositivo");
            System.out.println("2. Ricerca dispositivo");
            System.out.println("3. Modifica dispositivo");
            System.out.println("4. Rimuovi dispositivo");
            System.out.println("0. Esci");

            System.out.print("Inserisci il numero corrispondente all'azione desiderata: ");
            scelta = scanner.nextInt();

            switch (scelta) {
                case 1:
                    inserisciDispositivo(techRehab, scanner);
                    break;
                case 2:
                    ricercaDispositivo(techRehab, scanner);
                    break;
                case 3:
                    modificaDispositivo(techRehab, scanner);
                    break;
                case 4:
                try {
                    techRehab.rimuoviDispositivo();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                    break;
                case 0:
                    System.out.println("Uscita dal menu gestione dispositivo.");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }

        } while (scelta != 0);
    }

    private static void inserisciDispositivo(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci la marca del dispositivo: ");
        String marca = scanner.next();
        System.out.print("Inserisci il modello del dispositivo: ");
        String modello = scanner.next();
        System.out.print("Inserisci il seriale del dispositivo: ");
        String seriale = scanner.next();
        System.out.print("Inserisci la data di fine garanzia (YYYY-MM-DD): ");
        try {
            LocalDate fineGaranzia = LocalDate.parse(scanner.next());
            techRehab.inserisciDispositivo(marca, modello, seriale, fineGaranzia);
            techRehab.confermaInserimentoDispositivo();
            System.out.println("Dispositivo inserito con successo.");
        } catch (DateTimeParseException e) {
            System.out.println("Formato data non valido. Utilizza il formato YYYY-MM-DD.");
        }
    }

    private static void ricercaDispositivo(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci il seriale del dispositivo da cercare: ");
        String seriale = scanner.next();
        Dispositivo dispositivoRicercato = techRehab.ricercaDispositivo(seriale);
        if (dispositivoRicercato != null) {
            System.out.println("Dispositivo trovato: " + dispositivoRicercato.getMarca() + " " + dispositivoRicercato.getModello() + " " + dispositivoRicercato.getFinegaranzia());
        } else {
            System.out.println("Dispositivo non trovato");
        }
    }

    private static void modificaDispositivo(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci il seriale del dispositivo da modificare: ");
        String seriale = scanner.next();
        System.out.print("Inserisci la nuova marca del dispositivo: ");
        String marca = scanner.next();
        System.out.print("Inserisci il nuovo modello del dispositivo: ");
        String modello = scanner.next();
        System.out.print("Inserisci la nuova data di fine garanzia (YYYY-MM-DD): ");
        LocalDate fineGaranzia = LocalDate.parse(scanner.next());

        techRehab.modificaDispositivo(seriale, marca, modello, fineGaranzia);
        System.out.println("Dispositivo modificato con successo.");
    }



    private static void emettiPreventivo(TechRehab techRehab, Scanner scanner) {
        int codicePreventivo;
        nuovoPreventivo(techRehab, scanner);
        while (true) {
            System.out.println("Vuoi aggiungere un guasto al preventivo? [Y/N]");
            String risposta = scanner.next().toUpperCase();
            if (risposta.equals("Y")) {
                try {
                    aggiungiGuasto(techRehab, scanner);
                } catch (InputMismatchException e) {
                    System.out.println("Input non valido. Inserisci un valore corretto.");
                    scanner.next();
                }
            } else if (risposta.equals("N")) {
                scanner.nextLine(); // Pulisce il buffer di input
                break;
            } else {
                System.out.println("Scelta non valida. Riprova.");
            }
        }
        definisciPriorita(techRehab, scanner);
        definisciOreLavoroPreviste(techRehab, scanner);
        definisciDataPrevistaConsegna(techRehab, scanner);
        codicePreventivo = confermaPreventivo(techRehab, scanner);
        accettaPreventivo(techRehab, codicePreventivo, scanner);
    }

    private static void nuovoPreventivo(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci il seriale del dispositivo per il nuovo preventivo: ");
        String serialeDispositivo = scanner.next();
        techRehab.nuovoPreventivo(serialeDispositivo);
        System.out.println("Nuovo preventivo creato per il dispositivo con seriale " + serialeDispositivo + ".");
    }

    private static void aggiungiGuasto(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci il seriale del ricambio da aggiungere al preventivo: ");
        String serialeRicambio = scanner.next();
        techRehab.aggiungiGuasto(serialeRicambio);
        System.out.println("Guasto aggiunto al preventivo con successo.");
    }

    private static void definisciPriorita(TechRehab techRehab, Scanner scanner) {
        System.out.print("Definisci la priorità del preventivo (true/false): ");
        String inputPriorita = scanner.nextLine();
        try {
            boolean priorita = Boolean.parseBoolean(inputPriorita);
            techRehab.definisciPriorita(priorita);
            System.out.println("Priorità del preventivo definita con successo.");
        } catch (InputMismatchException e) {
            System.out.println("Input non valido. Inserisci un valore corretto per la priorita.");
        }
    }

    private static void definisciOreLavoroPreviste(TechRehab techRehab, Scanner scanner) {
        System.out.print("Definisci le ore di lavoro previste per il preventivo: ");
        String inputOreLavoroPreviste = scanner.nextLine();
        try {
            float oreLavoroPreviste = Float.parseFloat(inputOreLavoroPreviste);
            techRehab.definisciOreLavoroPreviste(oreLavoroPreviste);
            System.out.println("Ore di lavoro previste per il preventivo definite con successo.");
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un valore numerico corretto per le ore di lavoro.");
        }
    }
    

    private static void definisciDataPrevistaConsegna(TechRehab techRehab, Scanner scanner) {
        System.out.print("Definisci la data prevista di consegna del preventivo (YYYY-MM-DD): ");
        String inputDataPrevistaConsegna = scanner.nextLine();
        try {
            LocalDate dataPrevistaConsegna = LocalDate.parse(inputDataPrevistaConsegna);
            techRehab.definisciDataPrevistaConsegna(dataPrevistaConsegna);
            System.out.println("Data prevista di consegna del preventivo definita con successo.");
        } catch (InputMismatchException e) {
            System.out.println("Input non valido. Inserisci un valore corretto per la data.");
        }
    }

    private static int confermaPreventivo(TechRehab techRehab, Scanner scanner) {
        Preventivo p = techRehab.confermaPreventivo();
        p.stampaPreventivo();
        System.out.println("Preventivo confermato con successo.");
        return p.getCodice();
    }

    private static void accettaPreventivo(TechRehab techRehab, int codicePreventivo, Scanner scanner) {
        System.out.print("Inserisci la descrizione della riparazione: ");
        String descrizioneRiparazione = scanner.nextLine();
        techRehab.accettaPreventivo(descrizioneRiparazione, codicePreventivo);
        System.out.println("Preventivo accettato e riparazione creata con successo.");
    }

    private static void riparaDispositivo(TechRehab techRehab, Scanner scanner) {
        int codiceRiparazione;

        visualizzaRiparazioniInCorso(techRehab);
        codiceRiparazione = selezionaRiparazione(techRehab, scanner);
        definisciOreManodopera(techRehab, codiceRiparazione, scanner);
        terminaRiparazione(techRehab, codiceRiparazione);
    }

    private static void visualizzaRiparazioniInCorso(TechRehab techRehab) {
        List<Riparazione> riparazioniInCorso = techRehab.ottieniRiparazioni();
        System.out.println("Riparazioni in corso:");
        for (Riparazione riparazione : riparazioniInCorso) {
            System.out.println("Codice: " + riparazione.getCodice() + ", Descrizione: " + riparazione.getDescrizione() + ", Stato: " + riparazione.getStato());
        }
    }

    private static int selezionaRiparazione(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci il codice della riparazione da selezionare: ");
        int codiceRiparazione = scanner.nextInt();
        Riparazione riparazioneSelezionata = techRehab.selezionaRiparazione(codiceRiparazione);
        System.out.println("Riparazione selezionata con successo.");
        return riparazioneSelezionata.getCodice();
    }

    private static void definisciOreManodopera(TechRehab techRehab, int codiceRiparazione, Scanner scanner) {
        System.out.print("Inserisci le ore di manodopera impiegate per la riparazione: ");
        float oreManodopera = scanner.nextFloat();
        techRehab.definisciOreManodopera(codiceRiparazione, oreManodopera);
        System.out.println("Ore di manodopera impiegate per la riparazione definite con successo.");
    }

    private static void terminaRiparazione(TechRehab techRehab, int codiceRiparazione) {
        Riparazione r = techRehab.terminaRiparazione(codiceRiparazione);
        r.stampaRiparazione();
        System.out.println("Riparazione terminata con successo.");
    }
}