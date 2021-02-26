package com.ARGuide.MainMenu.Models;

import java.util.Date;

/**
 * Created by AsX on 4/12/2020.
 */

public class Locatie {
    public String getDenumire() {
        return Denumire;
    }

    public int getEtaj() {return Etaj;}

    public void setDenumire(String nume) {
        Denumire = nume;
    }

    public String getCod() {
        return Cod;
    }

    public void setCod(String cod) {
        Cod = cod;
    }

    public String getCodQR() {
        return CodQR;
    }

    public void setCodQR(String codQR) {
        CodQR = codQR;
    }

    public void setEtaj(int Etaj) { this.Etaj = Etaj;}



    private String Denumire;
    private String Cod;
    private String CodQR;
    private Date DataAdaugare;
    private int Etaj;


    public Locatie(String nume, String cod, String codQR, int etaj){
        this.setDenumire(nume);
        this.setCod(cod);
        this.setCodQR(codQR);
        this.setEtaj(etaj);
    }
}
