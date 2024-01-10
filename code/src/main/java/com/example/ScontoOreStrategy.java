package com.example;

public class ScontoOreStrategy implements Strategy{
    
    public float calcolaCostoH(float prezzoOrario) {
        return prezzoOrario*0.8f;
    }   
}
