package com.fjas.simsaturno;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FechaHoraActivity extends AppCompatActivity {

    Button btnElegirFecha, btnElegirHora, btnAhora, btnSimuladorSaturno, btnAtras;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_hora);

        // Vincular botones del layout
        btnElegirFecha = findViewById(R.id.btnElegirFecha);
        btnElegirHora = findViewById(R.id.btnElegirHora);
        btnAhora = findViewById(R.id.btnAhora);
        btnSimuladorSaturno = findViewById(R.id.btnSimuladorSaturno);
        btnAtras = findViewById(R.id.btnAtras);

        // Inicializar calendario con fecha y hora actuales
        calendar = Calendar.getInstance();

        // Mostrar fecha y hora actuales en los botones
        mostrarFechaHora();

        // Botón para elegir fecha
        btnElegirFecha.setOnClickListener(v -> {
            int año = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(FechaHoraActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        mostrarFechaHora();
                    }, año, mes, dia);
            datePickerDialog.show();
        });

        // Botón para elegir hora
        btnElegirHora.setOnClickListener(v -> {
            int hora = calendar.get(Calendar.HOUR_OF_DAY);
            int minuto = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(FechaHoraActivity.this,
                    (view, hourOfDay, minute1) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute1);
                        mostrarFechaHora();
                    }, hora, minuto, true);
            timePickerDialog.show();
        });

        // Botón "Ahora" para poner fecha y hora actuales
        btnAhora.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            mostrarFechaHora();
        });

        // Botón "Simulador de Saturno" (todavía sin función)
        btnSimuladorSaturno.setOnClickListener(v -> {
            // Formatear fecha y hora para enviar
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

            String fecha = formatoFecha.format(calendar.getTime());
            String hora = formatoHora.format(calendar.getTime());

            // Crear intent y pasar datos
            Intent intent = new Intent(FechaHoraActivity.this, SimuladorSaturnoActivity.class);
            intent.putExtra("fechaSeleccionada", fecha);
            intent.putExtra("horaSeleccionada", hora);
            startActivity(intent);
        });

        // Botón "Atrás"
        btnAtras.setOnClickListener(v -> finish());
    }

    // Mostrar fecha y hora seleccionadas en los botones
    private void mostrarFechaHora() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

        String fechaFormateada = formatoFecha.format(calendar.getTime());
        String horaFormateada = formatoHora.format(calendar.getTime());

        btnElegirFecha.setText(fechaFormateada);
        btnElegirHora.setText(horaFormateada);
    }
}
