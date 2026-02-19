package com.fjas.simsaturno;

public class PeriodoSatelite {
    private String nombre;
    private double periodo;
    private String fechaHora;

    public PeriodoSatelite(String nombre, double periodo, String fechaHora) {
        this.nombre = nombre;
        this.periodo = periodo;
        this.fechaHora = fechaHora;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeriodo() {
        return periodo;
    }

    public String getFechaHora() {
        return fechaHora;
    }
}

