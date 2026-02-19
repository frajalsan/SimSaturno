package com.fjas.simsaturno;

public class InfoSatelite {
    private String nombre;
    private float magnitud;
    private float diametro;
    private String descubridor;
    private int anio;

    public InfoSatelite(String nombre, float magnitud, float diametro, String descubridor, int anio) {
        this.nombre = nombre;
        this.magnitud = magnitud;
        this.diametro = diametro;
        this.descubridor = descubridor;
        this.anio = anio;
    }

    public String getNombre() {
        return nombre;
    }

    public float getMagnitud() {
        return magnitud;
    }

    public float getDiametro() {
        return diametro;
    }

    public String getDescubridor() {
        return descubridor;
    }

    public int getAnio() {
        return anio;
    }
}
