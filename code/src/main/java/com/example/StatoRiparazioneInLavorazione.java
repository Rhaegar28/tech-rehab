package com.example;

public class StatoRiparazioneInLavorazione extends StatoRiparazione {
    
    public StatoRiparazioneInLavorazione(Riparazione riparazione) {
        super(riparazione);
    }

    @Override
    public void aggiornaStato() {
        riparazione.setStato(new StatoRiparazioneCompletato(riparazione));
    }

    public String stato() {
        return "In lavorazione";
    }

}
