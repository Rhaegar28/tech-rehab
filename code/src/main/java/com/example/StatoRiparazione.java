package com.example;

public abstract class StatoRiparazione {

    Riparazione riparazione;
    
    public StatoRiparazione(Riparazione riparazione) {
        this.riparazione = riparazione;
    }

    public void sospendi() {
        riparazione.setStato(new StatoRiparazioneInSospeso(riparazione));
    }

    public abstract void aggiornaStato();
    
    public abstract String stato();
}
