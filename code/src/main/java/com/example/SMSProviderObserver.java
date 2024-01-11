package com.example;

public class SMSProviderObserver implements Observer {
    
    private Riparazione subject;
    private String observerState;

    public SMSProviderObserver(Riparazione subject) {
        this.subject = subject;
    }

    @Override
    public void update() {
        observerState = subject.getStato();
        System.out.println("SMS: Riparazione è nello stato " + observerState);
    }
 
}
