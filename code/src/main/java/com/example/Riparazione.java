package com.example;

import java.time.LocalDate;
import java.time.Period;

public class Riparazione extends Subject {

    private static final float costoManodoperaH = 40f;
    private static int count=0;
    private int codice;
    private String descrizione;
    private StatoRiparazione stato;
    private float oreManodopera;
    private Preventivo preventivo;
    private LocalDate dataFineRiparazione;
    private Fattura fattura;
    private int feedback;


    public int getFeedback() {
        return feedback;
    }
    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }
    
    public int getCodice() {
        return codice;
    }
    public void setCodice(int codice) {
        this.codice = codice;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
   
    public String getStato() {
        return stato.stato();
    }

    public void setStato(StatoRiparazione sr) {
        this.stato = sr;
        notifyObservers();
    }

    public float getOreManodopera() {
        return oreManodopera;
    }
    public void setOreManodopera(float oreManodopera) {
        this.oreManodopera = oreManodopera;
    }
    public Preventivo getPreventivo() {
        return preventivo;
    }
    public void setPreventivo(Preventivo preventivo) {
        this.preventivo = preventivo;
    }

    public LocalDate getDataFineRiparazione() {
        return dataFineRiparazione;
    } 

    public void setDataFineRiparazione(LocalDate dataFineRiparazione) {
        this.dataFineRiparazione = dataFineRiparazione;
    }

    public Riparazione(String descrizioneRiparazione, Preventivo preventivo) {
        this.preventivo=preventivo;
        this.descrizione=descrizioneRiparazione;
        //this.stato="In carico";
        this.stato = new StatoRiparazioneInCarico(this);
        this.codice=++count;
    }
    
    public void stampaRiparazione() {
        System.out.println("Codice: " + codice);
        System.out.println("Descrizione: " + descrizione);
        System.out.println("Stato: " + stato.stato());
        System.out.println("Ore manodopera: " + oreManodopera);
    }

    public void emettiFattura() {
        fattura = new Fattura();
        calcolaCosto();
        //Registrazione dei provider di servizi nel Subject Fattura (Pattern Observer Pull)
        Observer email = new EmailProviderObserver(fattura);
        fattura.attach(email);
        fattura.setStato(true);
        //Fine registrazione        
    }   

    private float calcolaCostoDefinitivo(float cost) {

        float costoRicambi = preventivo.getPrezziRicambi();

        return this.oreManodopera * cost + costoRicambi;
    }

    public void calcolaCosto(){

        float cost ;

        //Gestione Strategy
        Context context = new Context();
        
        if (preventivo.getCostoPrevisto() == 0) {
            cost = 0;
            fattura.setCostoDefinitivo(cost);

        } else {

            boolean priorita = preventivo.getPriorita();
            float oreLavoroPreviste = preventivo.getOreLavoroPreviste();
            LocalDate dataPrevistaConsegna = preventivo.getDataPrevistaConsegna();
            cost = costoManodoperaH;

            if (priorita == false && (this.oreManodopera - oreLavoroPreviste) <= 0 
            && (Period.between(dataPrevistaConsegna, this.dataFineRiparazione).getDays()) < 3) {

                //Regola di Dominio R1
                fattura.setCostoDefinitivo(calcolaCostoDefinitivo(cost));

            } else {
                
                if (priorita == true) {
                    //Regola di Dominio R2
                    context.setStrategy(new ClienteVIPStrategy());
                    cost = context.eseguiStrategy(cost);
                }

                if ((this.oreManodopera - oreLavoroPreviste) > 0) {
                    //Regola di Dominio R5
                    context.setStrategy(new ScontoOreStrategy());
                    cost = context.eseguiStrategy(cost);
                }
              
                if ((Period.between(dataPrevistaConsegna, this.dataFineRiparazione).getDays()) >= 3) {
                    //Regola di Dominio R4
                    context.setStrategy(new ScontoGiorniStrategy());
                    cost = context.eseguiStrategy(cost);
                }
                
                fattura.setCostoDefinitivo(Math.round((calcolaCostoDefinitivo(cost) * 100.00) / 100.00));
            }
            
        }   

    }

    public Fattura getFattura() {
        return fattura;
    }

    public void aggiornaStato(){
        stato.aggiornaStato();
    }  

    public void sospendi(){
        stato.sospendi();
    }

}
