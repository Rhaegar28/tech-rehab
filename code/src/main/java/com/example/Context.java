package com.example;

public class Context {
    
    private Strategy strategy;

    public Context(){

    }   

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }  
    
    public float eseguiStrategy(float prezzoOrario){
        return strategy.calcolaCostoH(prezzoOrario);
    }   
}
