package com.fjas.simsaturno;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class InformacionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    InformacionAdapter adapter;
    ArrayList<InformacionSatelite> lista;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        recyclerView = findViewById(R.id.recyclerViewInformacion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        lista = obtenerInformacionDesdeDB();
        adapter = new InformacionAdapter(lista);
        recyclerView.setAdapter(adapter);

        Button btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v -> finish());
    }

    private ArrayList<InformacionSatelite> obtenerInformacionDesdeDB() {
        ArrayList<InformacionSatelite> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_INFO_SATELITES,
                null, null, null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE));
                double magnitud = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MAGNITUD));
                double diametro = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIAMETRO));
                String descubridor = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCUBRIDOR));
                int anio = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ANIO));

                lista.add(new InformacionSatelite(nombre, magnitud, diametro, descubridor, anio));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }
}




