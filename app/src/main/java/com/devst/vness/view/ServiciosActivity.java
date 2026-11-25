package com.devst.vness.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.devst.vness.R;

// Pantalla principal de selección de servicios tras iniciar sesión
public class ServiciosActivity extends AppCompatActivity {

    private Button btnServicioRegistroCivil, btnServicioPersonalTrainer, btnServicioDomestico, btnCerrarSesionServicios;
    private String rolActual = "USUARIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        btnServicioRegistroCivil = findViewById(R.id.btnServicioRegistroCivil);
        btnServicioPersonalTrainer = findViewById(R.id.btnServicioPersonalTrainer);
        btnServicioDomestico = findViewById(R.id.btnServicioDomestico);
        btnCerrarSesionServicios = findViewById(R.id.btnCerrarSesionServicios);

        // Recuperar el rol que viene desde el Login
        if (getIntent().hasExtra("ROL_USUARIO")) {
            rolActual = getIntent().getStringExtra("ROL_USUARIO");
        }

        // 1. Opción principal: Ir al CRUD de Registro Civil (MainActivity)
        btnServicioRegistroCivil.setOnClickListener(v -> {
            Intent intent = new Intent(ServiciosActivity.this, MainActivity.class);
            intent.putExtra("ROL_USUARIO", rolActual);
            startActivity(intent);
        });

        // 2. Opción Personal Trainer -> Mensaje Premium y botón para volver
        btnServicioPersonalTrainer.setOnClickListener(v -> {
            mostrarMensajePremium("Soporte Personal Trainer");
        });

        // 3. Opción Doméstica / Conversación -> Mensaje Premium y botón para volver
        btnServicioDomestico.setOnClickListener(v -> {
            mostrarMensajePremium("Asistente Doméstico");
        });

        // Botón para cerrar sesión
        btnCerrarSesionServicios.setOnClickListener(v -> {
            Intent intent = new Intent(ServiciosActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    // Método auxiliar para mostrar el mensaje requerido con el botón de volver
    private void mostrarMensajePremium(String modulo) {
        new AlertDialog.Builder(this)
                .setTitle("⭐ Módulo " + modulo)
                .setMessage("Esta es una Opción premium que se desarrollará más adelante por favor contáctate con los desarrolladores y quédate pendiente a las nuevas actualizaciones!")
                .setPositiveButton("Volver", (dialog, which) -> {
                    dialog.dismiss(); // Cierra la alerta y vuelve a la vista anterior
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}