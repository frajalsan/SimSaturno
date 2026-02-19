package com.fjas.simsaturno;

public class InformacionSatelite {
    private String nombre;
    private double magnitud;
    private double diametro;
    private String descubridor;
    private int anio;

    public InformacionSatelite(String nombre, double magnitud, double diametro, String descubridor, int anio) {
        this.nombre = nombre;
        this.magnitud = magnitud;
        this.diametro = diametro;
        this.descubridor = descubridor;
        this.anio = anio;
    }

    public String getNombre() { return nombre; }
    public double getMagnitud() { return magnitud; }
    public double getDiametro() { return diametro; }
    public String getDescubridor() { return descubridor; }
    public int getAnio() { return anio; }
}
