package com.devst.vness.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devst.vness.R;
import com.devst.vness.model.Usuario;
import com.devst.vness.viewmodel.VnessViewModel;

// Activity encargada de la autenticacion y control de los 3 tipos de usuario
public class LoginActivity extends AppCompatActivity {

    private EditText etUser, etPass;
    private Button btnLogin;
    private VnessViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);

        viewModel = new ViewModelProvider(this).get(VnessViewModel.class);

        // Se usa setOnClickListener
        btnLogin.setOnClickListener(v -> {
            String usuarioStr = etUser.getText().toString().trim();
            String passwordStr = etPass.getText().toString().trim();

            if(usuarioStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validar usuario mediante el ViewModel y Repositorio
            Usuario usuario = viewModel.iniciarSesion(usuarioStr, passwordStr);

            if(usuario != null) {
                Toast.makeText(this, "Bienvenido: " + usuario.getRol(), Toast.LENGTH_SHORT).show();

                // Redirige a ServiciosActivity en lugar de MainActivity
                Intent intent = new Intent(LoginActivity.this, ServiciosActivity.class);
                intent.putExtra("ROL_USUARIO", usuario.getRol());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}