package com.fjas.simsaturno;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.*;

public class InsertarObservacionActivity extends AppCompatActivity {

    Spinner spinnerSatelites;
    EditText etObservacion;
    Button btnGuardar, btnAtras;

    DatabaseHelper dbHelper;
    List<String> listaSatelites = new ArrayList<>();
    ArrayAdapter<String> adapterSatelites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_observacion);

        spinnerSatelites = findViewById(R.id.spinnerSatelites);
        etObservacion = findViewById(R.id.etObservacion);
        btnGuardar = findViewById(R.id.btnGuardarObservacion);
        btnAtras = findViewById(R.id.btnAtras);

        dbHelper = new DatabaseHelper(this);

        cargarSatelites();

        adapterSatelites = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaSatelites);
        adapterSatelites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSatelites.setAdapter(adapterSatelites);

        btnGuardar.setOnClickListener(v -> guardarObservacion());

        btnAtras.setOnClickListener(v -> finish());
    }

    private void cargarSatelites() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("datos_orbitales", new String[]{"nombre"}, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                listaSatelites.add(nombre);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "No hay satélites registrados aún", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarObservacion() {
        String satelite = spinnerSatelites.getSelectedItem().toString();
        String observacion = etObservacion.getText().toString().trim();

        if (observacion.isEmpty()) {
            Toast.makeText(this, "Por favor escribe la observación", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener fecha y hora actual
        String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String hora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        // Insertar en la base de datos
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SATELITE, satelite);
        values.put(DatabaseHelper.COLUMN_OBSERVACION, observacion);
        values.put(DatabaseHelper.COLUMN_FECHA, fecha);
        values.put(DatabaseHelper.COLUMN_HORA, hora);

        long id = db.insert(DatabaseHelper.TABLE_OBSERVACIONES, null, values);

        if (id != -1) {
            Toast.makeText(this, "Observación guardada", Toast.LENGTH_SHORT).show();
            etObservacion.setText(""); // Limpiar campo
        } else {
            Toast.makeText(this, "Error al guardar observación", Toast.LENGTH_SHORT).show();
        }
    }
}
