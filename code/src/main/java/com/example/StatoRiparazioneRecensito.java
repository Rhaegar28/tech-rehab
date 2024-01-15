package com.example;

public class StatoRiparazioneRecensito extends StatoRiparazione {
    
    public StatoRiparazioneRecensito(Riparazione riparazione) {
        super(riparazione);
    }

    @Override
    public void aggiornaStato() {
      
    }

    public String stato() {
        return "Recensito";
    }

}
