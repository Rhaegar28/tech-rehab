package com.example;

public class Ricambio {
    private String seriale;
    private String nome;
    private float prezzo;

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

    public Ricambio(String seriale, String nome, float prezzo) {
        this.seriale = seriale;
        this.nome = nome;
        this.prezzo = prezzo;
    }
}
