package com.example;

public class StatoRiparazioneInCarico extends StatoRiparazione {
    
    public StatoRiparazioneInCarico(Riparazione riparazione) {
        super(riparazione);
    }

    @Override
    public void aggiornaStato() {
        riparazione.setStato(new StatoRiparazioneInLavorazione(riparazione));
    }

    public String stato() {
        return "In carico";
    }

}
