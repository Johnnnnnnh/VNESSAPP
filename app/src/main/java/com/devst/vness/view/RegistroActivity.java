package com.devst.vness.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devst.vness.R;
import com.devst.vness.viewmodel.VnessViewModel;

// Activity para la insercion de nuevos registros en SQLite
public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etStock, etPrecio;
    private Button btnGuardar;
    private VnessViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.etRegNombre);
        etStock = findViewById(R.id.etRegStock);
        etPrecio = findViewById(R.id.etRegPrecio);
        btnGuardar = findViewById(R.id.btnGuardarTrámite);

        viewModel = new ViewModelProvider(this).get(VnessViewModel.class);

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String stockStr = etStock.getText().toString().trim();
            String precioStr = etPrecio.getText().toString().trim();

            if(nombre.isEmpty() || stockStr.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int stock = Integer.parseInt(stockStr);
            double precio = Double.parseDouble(precioStr);

            // Llamada al ViewModel para insertar mediante el Repositorio
            viewModel.registrarProducto(nombre, stock, precio);
            Toast.makeText(this, "Trámite registrado con éxito en Bodega.db", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}