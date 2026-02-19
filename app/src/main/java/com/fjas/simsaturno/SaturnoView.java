package com.fjas.simsaturno;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ScaleGestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;



public class SaturnoView extends View {

    private Paint paint;
    private Calendar fechaSeleccionada = null;

    private float scaleFactor = 1.0f;
    private final float MIN_ZOOM = 0.2f;
    private final float MAX_ZOOM = 3.0f;

    private ScaleGestureDetector scaleDetector;

    private float focusX = 0f;
    private float focusY = 0f;



    private float offsetX = 0f;
    private float offsetY = 0f;

    private float lastTouchX;
    private float lastTouchY;

    private boolean isPanning = false;




    private float escalaview = 666.66666f;

    private float inclinacionFactor = 1.0f; // valor por defecto


    private InfoSatelite infoRhea = null; // conserva referencia si la necesitas
    private DatabaseHelper dbHelper;

    // Lista de satélites (nueva)
    private List<Satelite> satelites = new ArrayList<>();
    private Satelite sateliteSeleccionado = null; // satélite actualmente seleccionado (null si ninguno)


    // Variables antiguas (se mantienen por compatibilidad)
    private float ultimaPosicionSatX = -1;
    private float ultimaPosicionSatY = -1;


    // Formato para enviar la fecha a SaturnoInclinacion
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);



    public SaturnoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private float[] convertirPantallaAMundo(float touchX, float touchY) {
        float zoomCenterX = (focusX == 0f && focusY == 0f) ? getWidth() / 2f : focusX;
        float zoomCenterY = (focusY == 0f && focusX == 0f) ? getHeight() / 2f : focusY;

        // Invertir la transformación: primero deshacer el translate, luego el scale (con centro)
        float x = (touchX - offsetX - zoomCenterX + offsetX) / scaleFactor + (zoomCenterX - offsetX);
        float y = (touchY - offsetY - zoomCenterY + offsetY) / scaleFactor + (zoomCenterY - offsetY);

        return new float[]{x, y};
    }


    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Scale detector (mantengo tu lógica)
        scaleDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                scaleFactor *= detector.getScaleFactor();
                scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));

                // Guardamos el punto de foco
                focusX = detector.getFocusX();
                focusY = detector.getFocusY();

                invalidate();
                return true;
            }
        });


        // Inicializar DBHelper y cargar la info de satélites
        dbHelper = new DatabaseHelper(context);



        // Cargar todos los satélites (en orden: más cercano -- más lejano)
        String[] nombres = new String[]{"Mimas", "Encélado", "Tethys", "Dione", "Rhea", "Titán", "Japeto"};
        satelites.clear();
        for (String nombre : nombres) {
            double radio = dbHelper.getRadioOrbital(nombre);         // km
            double periodo = dbHelper.getPeriodoSatelite(nombre);    // días
            InfoSatelite info = dbHelper.getInfoSatelite(nombre);
            // Si hay datos válidos añadimos el satélite
            if (radio > 0 && periodo > 0 && info != null) {
                Satelite s = new Satelite(nombre, radio, periodo, info);
                satelites.add(s);
            }
        }
    }

    public void setFecha(Calendar fecha) {
        this.fechaSeleccionada = fecha;
        invalidate();
    }

    // Metodo que recibe la cifra de factor de la inclinacion de Saturno, ya pasada por el seno del angulo
    public void setInclinacionFactor(float factor) {
        this.inclinacionFactor = factor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Guardar canvas y aplicar escala
        canvas.save();

// 1. Aplicar desplazamiento (pan)
        canvas.translate(offsetX, offsetY);

// 2. Aplicar zoom centrado en el punto de foco
        float zoomCenterX = (focusX == 0f && focusY == 0f) ? getWidth() / 2f : focusX;
        float zoomCenterY = (focusY == 0f && focusX == 0f) ? getHeight() / 2f : focusY;
        canvas.scale(scaleFactor, scaleFactor, zoomCenterX - offsetX, zoomCenterY - offsetY);

        // Fondo negro
        canvas.drawColor(Color.BLACK);

        int width = getWidth();
        int height = getHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        // Radio de Saturno
        float planetRadius = 87.348f;


        float ringWidth = 420.63f;  // eje mayor horizontal (ancho de anillos)

        float ringHeight = ringWidth * inclinacionFactor;    // eje menor vertical (altura sin perspectiva)


        // *************************************************************************************
        int veranoInvierno = 200;

        if (inclinacionFactor < 0.000f) {
            veranoInvierno = 180;
        } else if (inclinacionFactor > 0.000f) {
            veranoInvierno = 0;
        }


        float varia;

        // Colores
        int colorAnilloA = Color.rgb(206, 206, 206);
        int colorAnilloB = Color.rgb(125, 125, 125);
        int colorAnilloC = Color.rgb(68, 68, 68);
        int colorOrbitas = Color.rgb(128, 18, 18);
        int colorPlaneta = Color.rgb(251, 219, 95);

        // Pintar planeta (círculo)
        paint.setColor(colorPlaneta);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, planetRadius, paint);

        // Pintar anillosA (elipses)
        paint.setColor(colorAnilloA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);


        // AnillosA
        for (float v : new float[]{1f, 0.886f, 0.88f, 0.876f, 0.8558f, 0.838f, 0.83f, 0.82f, 0.81f, 0.80f, 0.79f, 0.785f, 0.78f, 0.77f, 0.75f, 0.74f, 0.6561f}) {
            varia = v;
            float left = centerX - (ringWidth * varia) / 2f;
            float top = centerY - (ringHeight * varia) / 2f;
            float right = centerX + (ringWidth * varia) / 2f;
            float bottom = centerY + (ringHeight * varia) / 2f;

            RectF oval = new RectF(left, top, right, bottom);
            canvas.drawOval(oval, paint);
        }


        // Pintar anillosB (elipses)
        paint.setColor(colorAnilloB);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        for (float v : new float[]{0.9756f, 0.97f, 0.965f, 0.96f, 0.955f, 0.95f, 0.945f, 0.94f, 0.935f, 0.93f, 0.92f, 0.915f, 0.91f, 0.902f, 0.90f, 0.895f, 0.89f, 0.8715f}) {
            varia = v;
            float left = centerX - (ringWidth * varia) / 2f;
            float top = centerY - (ringHeight * varia) / 2f;
            float right = centerX + (ringWidth * varia) / 2f;
            float bottom = centerY + (ringHeight * varia) / 2f;

            RectF oval = new RectF(left, top, right, bottom);
            canvas.drawOval(oval, paint);
        }


        // Pintar anillosC (elipses)
        paint.setColor(colorAnilloC);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        for (float v : new float[]{0.8558f, 0.76f, 0.73f, 0.72f, 0.71f, 0.70f, 0.69f, 0.68f, 0.67f, 0.66f, 0.665f, 0.65f, 0.628f, 0.62f, 0.608f, 0.5732f}) {
            varia = v;
            float left = centerX - (ringWidth * varia) / 2f;
            float top = centerY - (ringHeight * varia) / 2f;
            float right = centerX + (ringWidth * varia) / 2f;
            float bottom = centerY + (ringHeight * varia) / 2f;

            RectF oval = new RectF(left, top, right, bottom);
            canvas.drawOval(oval, paint);
        }




        // Pintar orbitas satelites (elipses)
        paint.setColor(colorOrbitas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.5f);

        // Orbitas (dibujadas con factores, las dejamos como estaban)
        for (float v : new float[]{25.40f, 8.7147f, 3.76007f, 2.6888f, 2.1039f, 1.7003f, 1.3265f}) {
            varia = v;
            float left = centerX - (ringWidth * varia) / 2f;
            float top = centerY - (ringHeight * varia) / 2f;
            float right = centerX + (ringWidth * varia) / 2f;
            float bottom = centerY + (ringHeight * varia) / 2f;

            RectF oval = new RectF(left, top, right, bottom);
            canvas.drawOval(oval, paint);
        }




        // ---------------------------
        // Dibujar todos los satélites (si hay fecha seleccionada)
        // ---------------------------
        if (fechaSeleccionada != null && satelites != null && !satelites.isEmpty()) {

            long millisSeleccionados = fechaSeleccionada.getTimeInMillis();

            // Recorremos los satélites en el orden ya cargado (Mimas -> Japeto)
            for (Satelite s : satelites) {

                // Si por algún motivo faltan datos, saltar
                if (s.periodoDias <= 0 || s.radioOrbitalKm <= 0) continue;

                // Obtener la fecha de referencia específica de este satélite
                Calendar base = dbHelper.getFechaReferencia(s.nombre);
                if (base == null) continue; // saltar si no hay fecha válida

                long millisBase = base.getTimeInMillis();

                double periodoMillis = s.periodoDias * 24 * 60 * 60 * 1000;
                long diffMillis = millisSeleccionados - millisBase;
                double moduloMillis = ((diffMillis % periodoMillis) + periodoMillis) % periodoMillis;
                double fraccionPeriodo = moduloMillis / periodoMillis;

                double anguloRev = fraccionPeriodo * 360;

                float cx = getWidth() / 2f;
                float cy = getHeight() / 2f;
                float radioOrbt = (float) (s.radioOrbitalKm / escalaview);   // Radio orbita escalado
                double rad = Math.toRadians(anguloRev);

                double b = radioOrbt * Math.sin(rad);
                float pasardoblefloat = (float) b;

                float satX = (float) (cx + radioOrbt * Math.cos(rad));
                float satY = cy + pasardoblefloat * inclinacionFactor;

                // Guardar posición actual del satélite (útil para toques)
                s.x = satX;
                s.y = satY;

                // También actualizar las últimas coordenadas (manteniendo compatibilidad)
                ultimaPosicionSatX = satX;
                ultimaPosicionSatY = satY;

                // Dibujar satélite
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(satX, satY, 6, paint);

                // Dibujar nombre (abreviatura)
                paint.setColor(Color.LTGRAY);
                paint.setTextSize(28);
                paint.setTextAlign(Paint.Align.CENTER);
                String etiqueta = s.getAbreviatura();
                canvas.drawText(etiqueta, satX, satY - 12, paint);
            }

            // Mostrar info si hay satélite seleccionado
            if (sateliteSeleccionado != null) {
                float satX = sateliteSeleccionado.x;
                float satY = sateliteSeleccionado.y;

                paint.setColor(Color.argb(180, 0, 0, 0)); // fondo negro semitransparente
                paint.setStyle(Paint.Style.FILL);

                float boxWidth = 240;
                float boxHeight = 120;

                float rectLeft = satX - boxWidth / 2;
                float rectTop = satY - 130;
                float rectRight = satX + boxWidth / 2;
                float rectBottom = rectTop + boxHeight;

                canvas.drawRoundRect(new RectF(rectLeft, rectTop, rectRight, rectBottom), 12, 12, paint);

                // Texto dentro del cuadro
                paint.setColor(Color.WHITE);
                paint.setTextSize(22);
                paint.setTextAlign(Paint.Align.LEFT);

                float textX = rectLeft + 12;
                float textY = rectTop + 28;

                InfoSatelite info = sateliteSeleccionado.info;
                if (info != null) {
                    // Mostrar info del satélite (desde la tabla informacion_satelites)
                    canvas.drawText("Nombre: " + info.getNombre(), textX, textY, paint);
                    canvas.drawText("Magnitud: " + info.getMagnitud(), textX, textY + 24, paint);
                    canvas.drawText("Diámetro: " + info.getDiametro() + " km", textX, textY + 48, paint);
                    canvas.drawText("Descubridor: " + info.getDescubridor(), textX, textY + 72, paint);
                    canvas.drawText("Año: " + info.getAnio(), textX, textY + 96, paint);
                } else {
                    canvas.drawText("Sin datos del satélite", textX, textY, paint);
                }
            }
        }


        // Semihemisferio del planeta por delante del anillo
        paint.setColor(colorPlaneta);
        paint.setStyle(Paint.Style.FILL);

        RectF planetRect = new RectF(centerX - planetRadius, centerX - planetRadius, centerX + planetRadius, centerX + planetRadius);
        // (Tu código original usaba centerX/centerY; corregimos a rect con centerX/centerY)
        planetRect = new RectF(centerX - planetRadius, centerY - planetRadius, centerX + planetRadius, centerY + planetRadius);
        canvas.drawArc(planetRect, veranoInvierno, 180, true, paint);


        // Restaurar canvas a estado original (sin escala)
        canvas.restore();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event); // zoom

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // Inicio de gesto con un dedo
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                isPanning = true;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!scaleDetector.isInProgress() && isPanning) {
                    float dx = event.getX() - lastTouchX;
                    float dy = event.getY() - lastTouchY;

                    offsetX += dx;
                    offsetY += dy;

                    lastTouchX = event.getX();
                    lastTouchY = event.getY();

                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isPanning = false;
                break;
        }

        // También permite seleccionar satélites (no lo toques mucho)
        if (event.getAction() == MotionEvent.ACTION_DOWN && fechaSeleccionada != null && satelites != null && !satelites.isEmpty()) {
            float touchX = event.getX();
            float touchY = event.getY();

// Convertir coordenadas de pantalla a coordenadas del mundo real del canvas
            float[] worldCoords = convertirPantallaAMundo(touchX, touchY);
            float worldX = worldCoords[0];
            float worldY = worldCoords[1];


            for (Satelite s : satelites) {
                float distancia = (float) Math.hypot(worldX - s.x, worldY - s.y);

                if (distancia < 30) { // Ajusta el umbral si quieres hacerlo más o menos sensible
                    if (sateliteSeleccionado == s) {
                        sateliteSeleccionado = null;
                    } else {
                        sateliteSeleccionado = s;
                    }
                    invalidate();
                    return true;
                }
            }



            if (sateliteSeleccionado != null) {
                sateliteSeleccionado = null;
                invalidate();
            }
        }

        return true;
    }





    // Clase interna que representa cada satélite y contiene sus datos + posición
    private static class Satelite {
        String nombre;
        double radioOrbitalKm;
        double periodoDias;
        InfoSatelite info;
        float x;
        float y;

        Satelite(String nombre, double radioOrbitalKm, double periodoDias, InfoSatelite info) {
            this.nombre = nombre;
            this.radioOrbitalKm = radioOrbitalKm;
            this.periodoDias = periodoDias;
            this.info = info;
            this.x = -1;
            this.y = -1;
        }

        String getAbreviatura() {
            if (nombre == null) return "";
            if (nombre.length() >= 2) return nombre.substring(0, 2);
            return nombre;
        }
    }

}
