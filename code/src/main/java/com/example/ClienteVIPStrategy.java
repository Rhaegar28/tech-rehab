package com.example;

public class ClienteVIPStrategy implements Strategy{
    public float calcolaCostoH(float prezzoOrario) {
        return prezzoOrario*1.2f;
    }   
}
