package it.epicode.biblioteca.servizio;


import it.epicode.biblioteca.exception.ISBNNotFoundException;
import it.epicode.biblioteca.modello.ElementoCatalogo;
import it.epicode.biblioteca.modello.Libro;
import it.epicode.biblioteca.modello.Rivista;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Archivio {
    private List<ElementoCatalogo> catalogo = new ArrayList<>();

    public void aggiungiElemento(ElementoCatalogo elemento) {
        if (catalogo.stream().anyMatch(e -> e.getIsbn().equals(elemento.getIsbn()))) {
            throw new IllegalArgumentException("Elemento con ISBN già esistente.");
        }
        catalogo.add(elemento);
    }

    public void rimuoviElemento(String isbn) {
        if (!catalogo.removeIf(e -> e.getIsbn().equals(isbn))) {
            throw new ISBNNotFoundException("Elemento con ISBN " + isbn + " non trovato.");
        }
    }

    public ElementoCatalogo ricercaPerIsbn(String isbn) {
        return catalogo.stream()
                .filter(e -> e.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new ISBNNotFoundException("Elemento con ISBN " + isbn + " non trovato."));
    }

    public List<ElementoCatalogo> ricercaPerAnno(int anno) {
        return catalogo.stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    public List<Libro> ricercaPerAutore(String autore) {
        return catalogo.stream()
                .filter(e -> e instanceof Libro && ((Libro) e).getAutore().equalsIgnoreCase(autore))
                .map(e -> (Libro) e)
                .collect(Collectors.toList());
    }

    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) {
        rimuoviElemento(isbn);
        aggiungiElemento(nuovoElemento);
    }

    public void statistiche() {
        long numeroLibri = catalogo.stream().filter(e -> e instanceof Libro).count();
        long numeroRiviste = catalogo.stream().filter(e -> e instanceof Rivista).count();
        int totalePagine = catalogo.stream().mapToInt(ElementoCatalogo::getNumeroPagine).sum();
        double mediaPagine = catalogo.stream().mapToInt(ElementoCatalogo::getNumeroPagine).average().orElse(0);

        System.out.println("Numero di libri: " + numeroLibri);
        System.out.println("Numero di riviste: " + numeroRiviste);
        System.out.println("Totale pagine: " + totalePagine);
        System.out.println("Media pagine: " + mediaPagine);
    }
}
