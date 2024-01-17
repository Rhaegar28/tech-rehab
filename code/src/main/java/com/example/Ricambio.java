package com.example;

public class Ricambio {
    private String seriale;
    private String nome;
    private float prezzo;
    private int quantita;

    public String getSeriale() {
        return seriale;
    }
    public void setSeriale(String seriale) {
        this.seriale = seriale;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public float getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    public Ricambio(String seriale, String nome, float prezzo, int quantita) {
        this.seriale = seriale;
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita=quantita;
    }

    public void updateRicambio(float prezzo, int quantita){
        setPrezzo(prezzo);
        setQuantita(quantita);
    }
}