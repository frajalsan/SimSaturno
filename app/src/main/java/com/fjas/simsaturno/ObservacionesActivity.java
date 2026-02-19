package com.fjas.simsaturno;

import android.database.Cursor;
import android.database.sqlite.*;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import java.util.ArrayList;
import java.util.List;

public class ObservacionesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ObservacionAdapter adapter;
    List<Observacion> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observaciones);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewObservaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarObservaciones();

        adapter = new ObservacionAdapter(lista);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnAtras).setOnClickListener(v -> finish());
    }

    private void cargarObservaciones() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_OBSERVACIONES,
                null, null, null, null, null, DatabaseHelper.COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String satelite = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SATELITE));
                String texto = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_OBSERVACION));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HORA));

                lista.add(new Observacion(satelite, texto, fecha, hora));
            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}

