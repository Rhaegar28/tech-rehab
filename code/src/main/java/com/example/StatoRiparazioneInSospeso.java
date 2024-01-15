package com.example;

public class StatoRiparazioneInSospeso extends StatoRiparazione {
    
    public StatoRiparazioneInSospeso(Riparazione riparazione) {
        super(riparazione);
    }

    @Override
    public void aggiornaStato() {
  
    }

    public String stato() {
        return "In sospeso";
    }

}
