package it.epicode.biblioteca;


import it.epicode.biblioteca.modello.ElementoCatalogo;
import it.epicode.biblioteca.modello.Libro;
import it.epicode.biblioteca.modello.Periodicita;
import it.epicode.biblioteca.modello.Rivista;
import it.epicode.biblioteca.servizio.Archivio;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Archivio archivio = new Archivio();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

     
        while (!exit) {
            System.out.println("\n--- Catalogo Biblioteca ---");
            System.out.println("1. Aggiungi un elemento");
            System.out.println("2. Rimuovi un elemento");
            System.out.println("3. Ricerca elemento per ISBN");
            System.out.println("4. Ricerca elementi per anno di pubblicazione");
            System.out.println("5. Ricerca libri per autore");
            System.out.println("6. Aggiorna un elemento");
            System.out.println("7. Visualizza statistiche del catalogo");
            System.out.println("8. Esci");
            System.out.print("Scegli un'opzione: ");
            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma la newline

            switch (scelta) {
                case 1 -> aggiungiElemento(scanner, archivio);
                case 2 -> rimuoviElemento(scanner, archivio);
                case 3 -> ricercaPerISBN(scanner, archivio);
                case 4 -> ricercaPerAnno(scanner, archivio);
                case 5 -> ricercaPerAutore(scanner, archivio);
                case 6 -> aggiornaElemento(scanner, archivio);
                case 7 -> archivio.statistiche();
                case 8 -> {
                    System.out.println("Uscita dal programma.");
                    exit = true;
                }
                default -> System.out.println("Opzione non valida, riprova.");
            }
        }

        scanner.close();
    }


    private static void aggiungiElemento(Scanner scanner, Archivio archivio) {
        System.out.println("\nVuoi aggiungere un:");
        System.out.println("1. Libro");
        System.out.println("2. Rivista");
        System.out.print("Scegli un'opzione: ");
        int scelta = scanner.nextInt();
        scanner.nextLine(); // Consuma la newline

        switch (scelta) {
            case 1 -> {
                System.out.print("Inserisci ISBN: ");
                String isbn = scanner.nextLine();
                System.out.print("Inserisci titolo: ");
                String titolo = scanner.nextLine();
                System.out.print("Inserisci anno di pubblicazione: ");
                int anno = scanner.nextInt();
                System.out.print("Inserisci numero di pagine: ");
                int pagine = scanner.nextInt();
                scanner.nextLine(); // Consuma la newline
                System.out.print("Inserisci autore: ");
                String autore = scanner.nextLine();
                System.out.print("Inserisci genere: ");
                String genere = scanner.nextLine();

                Libro libro = new Libro(isbn, titolo, anno, pagine, autore, genere);
                archivio.aggiungiElemento(libro);
                System.out.println("Libro aggiunto con successo!");
            }
            case 2 -> {
                System.out.print("Inserisci ISBN: ");
                String isbn = scanner.nextLine();
                System.out.print("Inserisci titolo: ");
                String titolo = scanner.nextLine();
                System.out.print("Inserisci anno di pubblicazione: ");
                int anno = scanner.nextInt();
                System.out.print("Inserisci numero di pagine: ");
                int pagine = scanner.nextInt();
                scanner.nextLine(); // Consuma la newline
                System.out.println("Scegli periodicità (1. SETTIMANALE, 2. MENSILE, 3. SEMESTRALE): ");
                int periodicitaScelta = scanner.nextInt();
                scanner.nextLine(); // Consuma la newline
                Periodicita periodicita = switch (periodicitaScelta) {
                    case 1 -> Periodicita.SETTIMANALE;
                    case 2 -> Periodicita.MENSILE;
                    case 3 -> Periodicita.SEMESTRALE;
                    default -> throw new IllegalArgumentException("Periodicità non valida.");
                };

                Rivista rivista = new Rivista(isbn, titolo, anno, pagine, periodicita);
                archivio.aggiungiElemento(rivista);
                System.out.println("Rivista aggiunta con successo!");
            }
            default -> System.out.println("Opzione non valida, ritorno al menu principale.");
        }
    }


    private static void rimuoviElemento(Scanner scanner, Archivio archivio) {
        System.out.print("\nInserisci l'ISBN dell'elemento da rimuovere: ");
        String isbn = scanner.nextLine();
        try {
            archivio.rimuoviElemento(isbn);
            System.out.println("Elemento rimosso con successo!");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    private static void ricercaPerISBN(Scanner scanner, Archivio archivio) {
        System.out.print("\nInserisci l'ISBN da cercare: ");
        String isbn = scanner.nextLine();
        try {
            ElementoCatalogo elemento = archivio.ricercaPerIsbn(isbn);
            System.out.println("Elemento trovato: " + elemento);
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    private static void ricercaPerAnno(Scanner scanner, Archivio archivio) {
        System.out.print("\nInserisci l'anno di pubblicazione: ");
        int anno = scanner.nextInt();
        scanner.nextLine(); // Consuma la newline
        var risultati = archivio.ricercaPerAnno(anno);
        if (risultati.isEmpty()) {
            System.out.println("Nessun elemento trovato per l'anno " + anno);
        } else {
            System.out.println("Elementi trovati:");
            risultati.forEach(System.out::println);
        }
    }


    private static void ricercaPerAutore(Scanner scanner, Archivio archivio) {
        System.out.print("\nInserisci il nome dell'autore: ");
        String autore = scanner.nextLine();
        var risultati = archivio.ricercaPerAutore(autore);
        if (risultati.isEmpty()) {
            System.out.println("Nessun libro trovato per l'autore " + autore);
        } else {
            System.out.println("Libri trovati:");
            risultati.forEach(System.out::println);
        }
    }


    private static void aggiornaElemento(Scanner scanner, Archivio archivio) {
        System.out.print("\nInserisci l'ISBN dell'elemento da aggiornare: ");
        String isbn = scanner.nextLine();
        try {
            System.out.println("Inserisci i nuovi dati dell'elemento.");
            aggiungiElemento(scanner, archivio); // Utilizza la stessa logica per aggiungere un elemento
            archivio.rimuoviElemento(isbn); // Rimuovi l'elemento vecchio
            System.out.println("Elemento aggiornato con successo!");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

