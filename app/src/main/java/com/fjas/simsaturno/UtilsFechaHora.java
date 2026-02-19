package com.fjas.simsaturno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilsFechaHora {

    /**
     * Convierte cadenas de fecha ("yyyy-MM-dd") y hora ("HH:mm") en un objeto Calendar.
     * @param fechaStr fecha en formato "yyyy-MM-dd"
     * @param horaStr hora en formato "HH:mm"
     * @return objeto Calendar con la fecha y hora combinadas, o null si hay error de parseo.
     */
    public static Calendar parseFechaHora(String fechaStr, String horaStr) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

            Date fecha = formatoFecha.parse(fechaStr);
            Date hora = formatoHora.parse(horaStr);

            Calendar calFecha = Calendar.getInstance();
            calFecha.setTime(fecha);

            Calendar calHora = Calendar.getInstance();
            calHora.setTime(hora);

            // Ajustamos la hora, minutos y segundos en la fecha
            calFecha.set(Calendar.HOUR_OF_DAY, calHora.get(Calendar.HOUR_OF_DAY));
            calFecha.set(Calendar.MINUTE, calHora.get(Calendar.MINUTE));
            calFecha.set(Calendar.SECOND, 0);
            calFecha.set(Calendar.MILLISECOND, 0);

            return calFecha;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

