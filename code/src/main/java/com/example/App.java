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
            System.out.println("4. Gestisci cliente");
            System.out.println("5. Emissione fattura");
            System.out.println("6. Consegna Dispositivo");
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
                    case 4:
                        gestisciCliente(techRehab, scanner);
                        break;
                    case 5:
                        emettiFattura(techRehab, scanner);
                        break;
                    case 6:
                        consegnaDispositivo(techRehab, scanner);
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
        System.out.print("Inserisci l'ID del cliente: ");
        int IDCliente = scanner.nextInt();
        do {
            System.out.println("Menu:");
            System.out.println("1. Inserisci dispositivo");
            System.out.println("2. Ricerca dispositivo");
            System.out.println("3. Modifica dispositivo");
            System.out.println("4. Rimuovi dispositivo");
            System.out.println("0. Esci");

            System.out.print("Inserisci il numero corrispondente all'azione desiderata: ");
            scanner.nextLine(); // Pulisce il buffer di input
            scelta = scanner.nextInt();

            switch (scelta) {
                case 1:
                    inserisciDispositivo(IDCliente, techRehab, scanner);
                    break;
                case 2:
                    ricercaDispositivo(IDCliente, techRehab, scanner);
                    break;
                case 3:
                    modificaDispositivo(IDCliente, techRehab, scanner);
                    break;
                case 4:
                    try {
                        techRehab.rimuoviDispositivo(IDCliente, scanner);
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

    private static void inserisciDispositivo(int IDCliente, TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci la marca del dispositivo: ");
        String marca = scanner.next();
        scanner.nextLine(); // Pulisce il buffer di input
        System.out.print("Inserisci il modello del dispositivo: ");
        String modello = scanner.nextLine();
        System.out.print("Inserisci il seriale del dispositivo: ");
        String seriale = scanner.next();
        System.out.print("Inserisci la data di fine garanzia (YYYY-MM-DD): ");
        try {
            LocalDate fineGaranzia = LocalDate.parse(scanner.next());
            techRehab.inserisciDispositivo(IDCliente, marca, modello, seriale, fineGaranzia);
            techRehab.confermaInserimentoDispositivo();
            System.out.println("Dispositivo inserito con successo.");
        } catch (DateTimeParseException e) {
            System.out.println("Formato data non valido. Utilizza il formato YYYY-MM-DD.");
        }
    }

    private static void ricercaDispositivo(int IDCliente, TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci il seriale del dispositivo da cercare: ");
        String seriale = scanner.next();
        Dispositivo dispositivoRicercato = techRehab.ricercaDispositivo(IDCliente, seriale);
        if (dispositivoRicercato != null) {
            System.out.println("Dispositivo trovato: " + dispositivoRicercato.getMarca() + " "
                    + dispositivoRicercato.getModello() + " " + dispositivoRicercato.getFineGaranzia());
        } else {
            System.out.println("Dispositivo non trovato");
        }
    }

    private static void modificaDispositivo(int IDCliente, TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci il seriale del dispositivo da modificare: ");
        String seriale = scanner.next();
        if (techRehab.ricercaDispositivo(IDCliente, seriale) == null) {
            System.out.println("Errore: Seriale del dispositivo non valido. Modifica non effettuata.");
            return;
        }
        System.out.print("Inserisci la nuova data di fine garanzia (YYYY-MM-DD): ");
        LocalDate fineGaranzia = LocalDate.parse(scanner.next());

        techRehab.modificaDispositivo(IDCliente, seriale, fineGaranzia);
        System.out.println("Dispositivo modificato con successo.");
    }

    private static void gestisciCliente(TechRehab techRehab, Scanner scanner) throws Exception {
        int scelta;
        do {
            System.out.println("Menu:");
            System.out.println("1. Inserisci cliente");
            System.out.println("2. Ricerca cliente");
            System.out.println("3. Modifica cliente");
            System.out.println("4. Rimuovi cliente");
            System.out.println("0. Esci");

            System.out.print("Inserisci il numero corrispondente all'azione desiderata: ");
            scelta = scanner.nextInt();

            switch (scelta) {
                case 1:
                    inserisciCliente(techRehab, scanner);
                    break;
                case 2:
                    ricercaCliente(techRehab, scanner);
                    break;
                case 3:
                    modificaCliente(techRehab, scanner);
                    break;
                case 4:
                    try {
                        techRehab.rimuoviCliente(scanner);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Uscita dal menu gestione cliente.");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }

        } while (scelta != 0);
    }

    private static void inserisciCliente(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci il nome del cliente: ");
        String nome = scanner.next();
        System.out.print("Inserisci il cognome del cliente: ");
        String cognome = scanner.next();
        System.out.print("Inserisci il numero di telefono del cliente: ");
        String numeroTelefono = scanner.next();
        System.out.print("Inserisci l'email del cliente: ");
        String email = scanner.next();

        techRehab.inserisciCliente(nome, cognome, numeroTelefono, email);
        techRehab.confermaInserimentoCliente();
        System.out.println("Cliente inserito con successo.");
    }

    private static void ricercaCliente(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci l' ID del cliente da cercare: ");
        int ID = scanner.nextInt();
        Cliente clienteRicercato = techRehab.ricercaCliente(ID);
        if (clienteRicercato != null) {
            System.out.println("Cliente trovato: " + clienteRicercato.getNome() + " " + clienteRicercato.getCognome()
                    + " " + clienteRicercato.getEmail());
        } else {
            System.out.println("Cliente non trovato");
        }
    }

    private static void modificaCliente(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci l'ID del cliente da modificare: ");
        int ID = scanner.nextInt();
        if (techRehab.ricercaCliente(ID) == null) {
            System.out.println("Errore: ID del cliente non valido. Modifica non effettuata.");
            return;
        }
        System.out.print("Inserisci il nuovo Telefono del cliente: ");
        String telefono = scanner.next();
        System.out.print("Inserisci il nuovo Email del cliente: ");
        String email = scanner.next();

        techRehab.modificaCliente(ID, telefono, email);
        System.out.println("Cliente modificato con successo.");
    }

    private static void emettiPreventivo(TechRehab techRehab, Scanner scanner) {
        int codicePreventivo;
        String risposta;
        if (nuovoPreventivo(techRehab, scanner)) {
            while (true) {
                System.out.println("Vuoi aggiungere un guasto al preventivo? [S/N]");
                risposta = scanner.next();
                if (risposta.equalsIgnoreCase("S")) {
                    try {
                        aggiungiGuasto(techRehab, scanner);
                    } catch (InputMismatchException e) {
                        System.out.println("Input non valido. Inserisci un valore corretto.");
                        scanner.next();
                    }
                } else if (risposta.equalsIgnoreCase("N")) {
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

            System.out.println("Vuoi accettare il preventivo? [S/N]");
            while (true) {
                risposta = scanner.next();
                if (risposta.equalsIgnoreCase("S")) {
                    accettaPreventivo(techRehab, codicePreventivo, scanner);
                    techRehab.presaInCaricoRiparazione(codicePreventivo);
                    break;
                } else if (risposta.equalsIgnoreCase("N")) {
                    scanner.nextLine(); // Pulisce il buffer di input
                    techRehab.rifiutaPreventivo(codicePreventivo);
                    break;
                } else {
                    System.out.println("Scelta non valida. Riprova.");
                }
            }
        }
    }

    private static void emettiFattura(TechRehab techRehab, Scanner scanner) {
        int codiceRiparazione;

        visualizzaRiparazioniCompletate(techRehab);
        System.out.println("Inserisci il codice di riparazione per cui emettere la fattura: ");
        codiceRiparazione = scanner.nextInt();

        techRehab.emettiFattura(codiceRiparazione);
        techRehab.confermaFattura();
    }

    private static void consegnaDispositivo(TechRehab techRehab, Scanner scanner) {
        System.out.println("Inserisci il codice del Preventivo relativo al dispositivo da consegnare: ");
        int codicePreventivo = scanner.nextInt();
        techRehab.consegnaDispositivo(codicePreventivo);
    }

    private static boolean nuovoPreventivo(TechRehab techRehab, Scanner scanner) {
        System.out.print("Inserisci l'ID del cliente proprietario del dispositivo: ");
        int IDCliente = scanner.nextInt();

        System.out.print("Inserisci il seriale del dispositivo per il nuovo preventivo: ");
        String serialeDispositivo = scanner.next();
        if (techRehab.ricercaDispositivo(IDCliente, serialeDispositivo) == null) {
            System.out.println("Errore: Seriale del dispositivo non valido. Preventivo non creato.");
            return false;
        }
        techRehab.nuovoPreventivo(serialeDispositivo, IDCliente);
        System.out.println("Nuovo preventivo creato per il dispositivo con seriale " + serialeDispositivo + ".");
        return true;
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
        System.out.print("Definisci la data prevista di consegna del dispositivo (YYYY-MM-DD): ");
        String inputDataPrevistaConsegna = scanner.nextLine();
        try {
            LocalDate dataPrevistaConsegna = LocalDate.parse(inputDataPrevistaConsegna);
            techRehab.definisciDataPrevistaConsegna(dataPrevistaConsegna);
            System.out.println("Data prevista di consegna del dispositivo definita con successo.");
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
        scanner.nextLine(); // Pulisce il buffer di input
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

    private static void visualizzaRiparazioniCompletate(TechRehab techRehab) {
        List<Riparazione> riparazioniCompletate = techRehab.ottieniRiparazioniCompletate();
        System.out.println("Riparazioni completate:");
        for (Riparazione riparazione : riparazioniCompletate) {
            System.out.println("Codice: " + riparazione.getCodice() + ", Descrizione: " + riparazione.getDescrizione()
                    + ", Stato: " + riparazione.getStato());
        }
    }

    private static void visualizzaRiparazioniInCorso(TechRehab techRehab) {
        List<Riparazione> riparazioniInCorso = techRehab.ottieniRiparazioniInCorso();
        System.out.println("Riparazioni in corso:");
        for (Riparazione riparazione : riparazioniInCorso) {
            System.out.println("Codice: " + riparazione.getCodice() + ", Descrizione: " + riparazione.getDescrizione()
                    + ", Stato: " + riparazione.getStato());
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