package com.example;

import java.util.Map;

public class Cliente {
    
    private static int count=0;

    private int ID;
    private String nome;
    private String cognome;
    private String telefono;
    private String email;

    public Cliente(String nome, String cognome, String telefono, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.ID=++count;
    }

    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        this.ID = iD;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome= nome;
    }   
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome= cognome;
    }
    public String getTelefono() {
        return telefono;
    }   
    public void setTelefono(String telefono) {
        this.telefono= telefono;
    }   
    public String getEmail() {
        return email;
    }       
    public void setEmail(String email) {
        this.email= email;
    }

    public void updateCliente(String nome, String cognome, String telefono, String email){
        if (nome != null)
            setNome(nome);
        if (cognome != null)
            setCognome(cognome);
        if (telefono != null)
            setTelefono(telefono);
        if (email != null)
            setEmail(email);            
    }    
}
