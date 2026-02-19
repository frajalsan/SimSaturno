package com.fjas.simsaturno;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Button btnSimuladorSaturno = findViewById(R.id.btnSimuladorSaturno);
        Button btnDatos = findViewById(R.id.btnDatos);

        btnSimuladorSaturno.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalActivity.this, FechaHoraActivity.class);
            startActivity(intent);
        });

        btnDatos.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalActivity.this, DatosActivity.class);
            startActivity(intent);
        });
    }
}