package com.example;

public class SMSProviderObserver implements Observer {
    
    private Riparazione subject;
    private String observerState;

    public SMSProviderObserver(Riparazione subject) {
        this.subject = subject;
    }
    public SMSProviderObserver(){
    }
    public void richiestaFeedback(String telefono, int codiceRiparazione){
        System.out.println("SMS: Richiesta feedback per riparazione " + codiceRiparazione + " inviata a " + telefono);

    }
    
    @Override
    public void update() {
        observerState = subject.getStato();
        System.out.println("SMS: Riparazione è nello stato " + observerState);
    }
    public String getObserverState() {
        return observerState;
    }
 
}
