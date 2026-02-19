package com.fjas.simsaturno;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DatosActivity extends AppCompatActivity {

    Button btnDatosOrbitales, btnInformacion;
    Button btnCalculoPosicion, btnInsertarObservaciones, btnObservaciones;

    Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        // Botones de la primera fila
        btnDatosOrbitales = findViewById(R.id.btnDatosOrbitales);
        btnInformacion = findViewById(R.id.btnInformacion);

        // Botones de la segunda fila
        btnCalculoPosicion = findViewById(R.id.btnCalculoPosicion);
        btnInsertarObservaciones = findViewById(R.id.btnInsertarObservaciones);
        btnObservaciones = findViewById(R.id.btnObservaciones);

        // Boton abajo del todo
        btnAtras = findViewById(R.id.btnAtras);

        // Aquí puedes agregar los listeners si quieres:
        btnDatosOrbitales.setOnClickListener(v -> {
            Intent intent = new Intent(DatosActivity.this, DatosOrbitalesActivity.class);
            startActivity(intent);
        });

        Button btnInformacion = findViewById(R.id.btnInformacion);
        btnInformacion.setOnClickListener(v -> {
            Intent intent = new Intent(DatosActivity.this, InformacionActivity.class);
            startActivity(intent);
        });


        Button btnCalculo = findViewById(R.id.btnCalculoPosicion);
        btnCalculo.setOnClickListener(v -> {
            Intent intent = new Intent(DatosActivity.this, CalculoPosicionActivity.class);
            startActivity(intent);
        });

        btnInsertarObservaciones.setOnClickListener(v -> {
            Intent intent = new Intent(DatosActivity.this, InsertarObservacionActivity.class);
            startActivity(intent);
        });

        btnObservaciones.setOnClickListener(v -> {
            Intent intent = new Intent(DatosActivity.this, ObservacionesActivity.class);
            startActivity(intent);
        });


        btnAtras.setOnClickListener(v -> {
            finish(); // Cierra esta Activity y vuelve a la anterior
        });
    }
}