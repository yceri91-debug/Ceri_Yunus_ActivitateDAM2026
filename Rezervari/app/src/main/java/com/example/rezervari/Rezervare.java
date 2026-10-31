package com.example.rezervari;

import java.io.Serializable;

public class Rezervare implements Serializable {
    private String nume;
    private String telefon;
    private String data;
    private String ora;
    private int numarPersoane;
    private int masa;

    public Rezervare(String nume, String telefon, String data, String ora,
                     int numarPersoane, int masa) {
        this.nume = nume;
        this.telefon = telefon;
        this.data = data;
        this.ora = ora;
        this.numarPersoane = numarPersoane;
        this.masa = masa;
    }

    public String getNume() { return nume; }
    public String getTelefon() { return telefon; }
    public String getData() { return data; }
    public String getOra() { return ora; }
    public int getNumarPersoane() { return numarPersoane; }
    public int getMasa() { return masa; }

    @Override
    public String toString() {
        return nume + " (" + telefon + ") | " + data + " " + ora
                + " | " + numarPersoane + " persoane | Masa " + masa;
    }
}