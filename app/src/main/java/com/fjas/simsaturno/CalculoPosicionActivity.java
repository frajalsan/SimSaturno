package com.fjas.simsaturno;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CalculoPosicionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PeriodoAdapter adapter;
    ArrayList<PeriodoSatelite> lista;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_posicion);

        recyclerView = findViewById(R.id.recyclerViewPeriodos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        lista = obtenerPeriodos();
        adapter = new PeriodoAdapter(lista);
        recyclerView.setAdapter(adapter);

        Button btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v -> finish());
    }

    private ArrayList<PeriodoSatelite> obtenerPeriodos() {
        ArrayList<PeriodoSatelite> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_PERIODOS,
                null, null, null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE));
                double periodo = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PERIODO));
                String fechaHora = cursor.getString(cursor.getColumnIndexOrThrow("fecha_hora"));  // <-- aquí
                lista.add(new PeriodoSatelite(nombre, periodo, fechaHora));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }
}
