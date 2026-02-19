package com.fjas.simsaturno;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaturnoInclinacion {

    private static final double ANGULO_MAXIMO = 26.73;

	// Son las medias en dias, que dura cada estacion del año en Saturno, 2528.5 Otoño, 2482.5 Invierno, 2840.25 Primavera, 2903.5 Verano
    private static final double[] periodos = {2528.5, 2482.5, 2840.25, 2903.5};   
    private static final double cicloTotal;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Date fechaReferencia;

    static {
        cicloTotal = periodos[0] + periodos[1] + periodos[2] + periodos[3];
        try {
            fechaReferencia = sdf.parse("2025-05-06");
        } catch (ParseException e) {
            e.printStackTrace();
            fechaReferencia = new Date();
        }
    }

    public static double calcularInclinacion(String fechaStr) {
        try {
            Date fechaActual = sdf.parse(fechaStr);
            long diffMs = fechaActual.getTime() - fechaReferencia.getTime();
            double diffDias = diffMs / (1000.0 * 60 * 60 * 24);

            Log.d("Saturno", "Días desde referencia: " + diffDias);

            double diasEnCiclo = diffDias % cicloTotal;
            if (diasEnCiclo < 0) diasEnCiclo += cicloTotal;

            double acumulado = 0;
            for (int i = 0; i < periodos.length; i++) {
                if (diasEnCiclo < acumulado + periodos[i]) {
                    double avance = (diasEnCiclo - acumulado) / periodos[i];


                    double angulo;

                    switch (i) {
                        case 0: // 0 → +MAX
                            angulo = ANGULO_MAXIMO * avance;
                            break;
                        case 1: // +MAX → 0
                            angulo = ANGULO_MAXIMO * (1 - avance);
                            break;
                        case 2: // 0 → -MAX
                            angulo = -ANGULO_MAXIMO * avance;
                            break;
                        case 3: // -MAX → 0
                            angulo = -ANGULO_MAXIMO * (1 - avance);
                            break;
                        default:
                            angulo = 0;
                    }

                   // double angulo = (i % 2 == 0) ? ANGULO_MAXIMO * avance : ANGULO_MAXIMO * (1 - avance);

                    Log.d("Saturno", "Periodo: " + i + ", avance: " + avance + ", ángulo: " + angulo);
                    return angulo;
                }
                acumulado += periodos[i];
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("Saturno", "Fallo en el cálculo, devolviendo 0");
        return 0;
    }



}
