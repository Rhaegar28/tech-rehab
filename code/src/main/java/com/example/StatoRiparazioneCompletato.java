package com.example;

import java.time.LocalDate;

public class StatoRiparazioneCompletato extends StatoRiparazione {

    public StatoRiparazioneCompletato(Riparazione riparazione) {
        super(riparazione);
        riparazione.setDataFineRiparazione(LocalDate.now());
    }

    @Override
    public void aggiornaStato() {
        riparazione.setStato(new StatoRiparazioneRecensito(riparazione));
    }

    @Override
    public String stato() {
        return "In completato";
    }

}
