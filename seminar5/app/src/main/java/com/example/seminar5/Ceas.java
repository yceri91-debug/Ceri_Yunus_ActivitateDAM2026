package com.example.seminar5;

import java.io.Serializable;

public class Ceas implements Serializable {
    private static final long serialVersionUID = 1L;

    private String marca;
    private String culoare;
    private float pret;

    public Ceas(String marca, String culoare, float pret) {
        this.marca = marca;
        this.culoare = culoare;
        this.pret = pret;
    }

    public String getMarca() { return marca; }
    public String getCuloare() { return culoare; }
    public float getPret() { return pret; }

    public void setMarca(String marca) { this.marca = marca; }
    public void setCuloare(String culoare) { this.culoare = culoare; }
    public void setPret(float pret) { this.pret = pret; }

    @Override
    public String toString() {
        return "Marca: " + marca + ", Culoare: " + culoare + ", Preț: " + pret;
    }
}
