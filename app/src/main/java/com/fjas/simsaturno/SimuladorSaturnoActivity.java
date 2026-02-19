package com.fjas.simsaturno;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SimuladorSaturnoActivity extends AppCompatActivity {

    private SaturnoView saturnoView;
    private TextView tvFecha;
    private TextView tvHora;

    private Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulador_saturno);

        saturnoView = findViewById(R.id.saturnoView);
        tvFecha = findViewById(R.id.tvFecha);
        tvHora = findViewById(R.id.tvHora);
        btnAtras = findViewById(R.id.btnAtras);

        // Recibir extras con las claves correctas
        String fechaSeleccionada = getIntent().getStringExtra("fechaSeleccionada");
        String horaSeleccionada = getIntent().getStringExtra("horaSeleccionada");

        // Mostrar fecha y hora
        tvFecha.setText("Fecha: " + fechaSeleccionada);
        tvHora.setText("Hora: " + horaSeleccionada);

        // Calcular inclinación y mostrarla
        if (fechaSeleccionada != null) {

            double inclinacion = SaturnoInclinacion.calcularInclinacion(fechaSeleccionada);
            float factor = (float) Math.sin(Math.toRadians(inclinacion));
            saturnoView.setInclinacionFactor(factor);
        }


        // Usar Calendar si quieres para el SaturnoView (como ya haces)
        Calendar fechaHoraCalendar = UtilsFechaHora.parseFechaHora(fechaSeleccionada, horaSeleccionada);
        if (fechaHoraCalendar != null) {
            saturnoView.setFecha(fechaHoraCalendar);
        }

        btnAtras.setOnClickListener(v -> finish());
    }

}
