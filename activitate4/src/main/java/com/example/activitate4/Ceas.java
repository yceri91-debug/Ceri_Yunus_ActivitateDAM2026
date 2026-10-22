package com.example.activitate4;

public class Ceas {
    private String marca;
    private String culoare;
    private float pret;

    public Ceas(String marca, String culoare, float pret){
        this.marca = marca;
        this.culoare = culoare;
        this.pret = pret;
    }

    public String getMarca() {
        return marca;
    }

    public String getCuloare() {
        return culoare;
    }

    public float getPret(){
        return pret;
    }

    @Override
    public String toString(){
        return "Marca: " + marca + "Culoare: " + culoare + "Pret: " + pret;


    }

    Ceas ceas = new Ceas("Casio", "Negru", 250);




}