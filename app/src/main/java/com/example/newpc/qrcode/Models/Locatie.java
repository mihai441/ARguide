package com.example.newpc.qrcode.Models;

/**
 * Created by AsX on 4/12/2020.
 */

public class Locatie {
    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
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

    private String Nume;
    private String Cod;
    private String CodQR;

    public Locatie(String nume, String cod, String codQR){
        this.setNume(nume);
        this.setCod(cod);
        this.setCodQR(codQR);
    }
}
