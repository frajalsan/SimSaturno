package com.fjas.simsaturno;

public class Observacion {
    private String satelite, texto, fecha, hora;

    public Observacion(String satelite, String texto, String fecha, String hora) {
        this.satelite = satelite;
        this.texto = texto;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getSatelite() { return satelite; }
    public String getTexto() { return texto; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
}
