package com.fjas.simsaturno;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;

public class DatosOrbitalesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatosOrbitalesAdapter adapter;
    ArrayList<DatoOrbital> listaDatos;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_orbitales);

        recyclerView = findViewById(R.id.recyclerViewDatosOrbitales);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        listaDatos = obtenerDatosDesdeDB();
        adapter = new DatosOrbitalesAdapter(listaDatos);
        recyclerView.setAdapter(adapter);

        Button btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v -> {
            finish(); // Vuelve al activity anterior (DatosActivity)
        });

    }



    private ArrayList<DatoOrbital> obtenerDatosDesdeDB() {
        ArrayList<DatoOrbital> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_DATOS_ORBITALES,
                null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE));
            double radio = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RADIO_ORBITAL));
            double peri = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PERIODICIDAD));

            lista.add(new DatoOrbital(id, nombre, radio, peri));
        }

        cursor.close();
        return lista;
    }
}