package com.ARGuide.MainMenu.Models;

import java.util.Date;

/**
 * Created by AsX on 4/12/2020.
 */

public class Cale {
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

    public int getDirectie() { return this.Directie;}

    public void setCodQR(String codQR) {
        CodQR = codQR;
    }

    public void setEtaj(int Etaj) { this.Etaj = Etaj;}

    public void setDirectie(int directie) {this.Directie = directie;}





    private String Denumire;
    private String Cod;
    private String CodQR;
    private Date DataAdaugare;
    private int Etaj;
    private int Directie;


    public Cale(String nume, String cod, String codQR, int etaj, int directie){
        this.setDenumire(nume);
        this.setCod(cod);
        this.setCodQR(codQR);
        this.setEtaj(etaj);
        this.setDirectie(directie);
    }
}
