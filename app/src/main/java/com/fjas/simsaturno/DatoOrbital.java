package com.fjas.simsaturno;


public class DatoOrbital {
    private int id;
    private String nombre;
    private double radioOrbital;
    private double periodicidad;

    public DatoOrbital(int id, String nombre, double radioOrbital, double periodicidad) {
        this.id = id;
        this.nombre = nombre;
        this.radioOrbital = radioOrbital;
        this.periodicidad = periodicidad;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getRadioOrbital() { return radioOrbital; }
    public double getPeriodicidad() { return periodicidad; }
}
