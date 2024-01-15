package com.example;

public class StatoRiparazioneConsegnato extends StatoRiparazione {

    public StatoRiparazioneConsegnato(Riparazione riparazione) {
        super(riparazione);
    }

    @Override
    public void aggiornaStato() {
        riparazione.setStato(new StatoRiparazioneCompletato(riparazione));
    }

    @Override
    public String stato() {
        return "In consegna";
    }

}
