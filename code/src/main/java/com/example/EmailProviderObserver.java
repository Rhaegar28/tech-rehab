package com.example;

public class EmailProviderObserver implements Observer {

    private Subject subject;
    private Boolean observerStateFattura;
    private String observerStateRiparazione;

    public EmailProviderObserver(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void update() {
        
        if (subject instanceof Fattura) {
            observerStateFattura = ((Fattura)subject).getStato();
            if (observerStateFattura) {
                System.out.println("Fattura è stata emessa");
            } 
        }
        else if (subject instanceof Riparazione) {
            observerStateRiparazione = ((Riparazione)subject).getStato();
            System.out.println("email: Riparazione è nello stato " + observerStateRiparazione);
        }
        
    }
    public Boolean getObserverStateFattura() {
        return observerStateFattura;
    }
    public String getObserverStateRiparazione() {
        return observerStateRiparazione;
    }

}