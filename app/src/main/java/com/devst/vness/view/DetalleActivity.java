package com.devst.vness.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devst.vness.R;
import com.devst.vness.viewmodel.VnessViewModel;

// Activity para actualizar o eliminar registros filtrados por ID
public class DetalleActivity extends AppCompatActivity {

    private EditText etId, etNombre, etStock, etPrecio;
    private Button btnActualizar, btnEliminar;
    private VnessViewModel viewModel;
    private int idProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        etId = findViewById(R.id.etDetId);
        etNombre = findViewById(R.id.etDetNombre);
        etStock = findViewById(R.id.etDetStock);
        etPrecio = findViewById(R.id.etDetPrecio);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        viewModel = new ViewModelProvider(this).get(VnessViewModel.class);

        // Recuperar datos pasados por el Intent
        if(getIntent().hasExtra("ID")) {
            idProducto = getIntent().getIntExtra("ID", -1);
            etId.setText(String.valueOf(idProducto));
            etNombre.setText(getIntent().getStringExtra("NOMBRE"));
            etStock.setText(String.valueOf(getIntent().getIntExtra("STOCK", 0)));
            etPrecio.setText(String.valueOf(getIntent().getDoubleExtra("PRECIO", 0.0)));
        }

        // Operacion Actualizar (UPDATE)
        btnActualizar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            int stock = Integer.parseInt(etStock.getText().toString().trim());
            double precio = Double.parseDouble(etPrecio.getText().toString().trim());

            viewModel.actualizarProducto(idProducto, nombre, stock, precio);
            Toast.makeText(this, "Registro actualizado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Operacion Eliminar (DELETE)
        btnEliminar.setOnClickListener(v -> {
            viewModel.eliminarProducto(idProducto);
            Toast.makeText(this, "Registro eliminado de Bodega.db", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}