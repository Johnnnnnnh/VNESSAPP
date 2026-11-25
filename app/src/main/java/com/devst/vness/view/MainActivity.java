package com.devst.vness.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devst.vness.R;
import com.devst.vness.model.Producto;
import com.devst.vness.viewmodel.VnessViewModel;

import java.util.ArrayList;
import java.util.List;

// Activity Principal / Dashboard con control de roles y opciones avanzadas
public class MainActivity extends AppCompatActivity {

    private VnessViewModel viewModel;
    private ListView listView;
    private Button btnNuevo, btnConectarVnessBot, btnGestionUsuarios, btnCerrarSesion;
    private TextView tvRolInfo;
    private String rolActual = "USUARIO";
    private List<Producto> listaProductosActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar vistas del XML
        listView = findViewById(R.id.listViewTrámites);
        btnNuevo = findViewById(R.id.btnNuevoTrámite);
        btnConectarVnessBot = findViewById(R.id.btnConectarVnessBot);
        btnGestionUsuarios = findViewById(R.id.btnGestionUsuarios);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        tvRolInfo = findViewById(R.id.tvRolInfo);

        // Obtener el rol enviado desde el Login
        if(getIntent().hasExtra("ROL_USUARIO")) {
            rolActual = getIntent().getStringExtra("ROL_USUARIO");
        }
        tvRolInfo.setText("Sesion activa - Rol: " + rolActual);

        // CONTROL DE PERMISOS POR ROL:
        // 1. Si es USUARIO común, ocultamos el botón de nuevo trámite
        if(rolActual.equals("USUARIO")) {
            btnNuevo.setVisibility(View.GONE);
        }

        // 2. Si NO es ADMIN, ocultamos completamente el botón de gestión de usuarios
        if(!rolActual.equals("ADMIN")) {
            btnGestionUsuarios.setVisibility(View.GONE);
        }

        viewModel = new ViewModelProvider(this).get(VnessViewModel.class);
        listaProductosActual = new ArrayList<>();

        // Observar los datos reactivos desde el ViewModel
        viewModel.getListaProductos().observe(this, productos -> {
            listaProductosActual = productos;
            List<String> descripciones = new ArrayList<>();
            for (Producto p : productos) {
                descripciones.add("ID: " + p.getId() + " | " + p.getNombre() + "\nCupos: " + p.getStock() + " | Costo: $" + p.getPrecio());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, descripciones);
            listView.setAdapter(adapter);
        });

        // Botón para ir a la pantalla de registro (Admin y Supervisor)
        btnNuevo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        // Botón de conexión con Arduino / LED (Asistente Físico Vness)
        btnConectarVnessBot.setOnClickListener(v -> {
            Toast.makeText(this, "Buscando dispositivo hardware Vness (Pantalla LED)...", Toast.LENGTH_SHORT).show();
        });

        // Botón exclusivo de Admin para gestionar usuarios del sistema
        btnGestionUsuarios.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GestionUsuariosActivity.class);
            startActivity(intent);
        });

        // Botón para Cerrar Sesión (Devuelve al usuario a la pantalla de Login)
        btnCerrarSesion.setOnClickListener(v -> {
            Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            // Limpiamos la pila de actividades para que no pueda volver atrás con el botón de retroceso
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Evento al hacer clic en un elemento de la lista para editar/eliminar
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(rolActual.equals("USUARIO")) {
                Toast.makeText(this, "Acceso restringido para modificar registros", Toast.LENGTH_SHORT).show();
                return;
            }
            Producto seleccionado = listaProductosActual.get(position);
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
            intent.putExtra("ID", seleccionado.getId());
            intent.putExtra("NOMBRE", seleccionado.getNombre());
            intent.putExtra("STOCK", seleccionado.getStock());
            intent.putExtra("PRECIO", seleccionado.getPrecio());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.cargarProductos(); // Refrescar listado al volver a la Activity
    }
}