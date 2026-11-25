package com.devst.vness.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

// ¡Verifica que este import esté presente!
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devst.vness.R;
import com.devst.vness.model.Usuario;
import com.devst.vness.viewmodel.VnessViewModel;

import java.util.ArrayList;
import java.util.List;

// Actividad exclusiva del Administrador para agregar o eliminar usuarios
public class GestionUsuariosActivity extends AppCompatActivity {

    private EditText etNewUser, etNewPass, etNewRol;
    private Button btnCrearUsuario;
    private ListView listViewUsuarios;
    private VnessViewModel viewModel;
    private List<Usuario> listaUsuariosActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios);

        etNewUser = findViewById(R.id.etNewUser);
        etNewPass = findViewById(R.id.etNewPass);
        etNewRol = findViewById(R.id.etNewRol);
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);
        listViewUsuarios = findViewById(R.id.listViewUsuarios);

        viewModel = new ViewModelProvider(this).get(VnessViewModel.class);
        cargarListaUsuarios();

        // Boton para registrar un nuevo usuario
        btnCrearUsuario.setOnClickListener(v -> {
            String user = etNewUser.getText().toString().trim();
            String pass = etNewPass.getText().toString().trim();
            String rol = etNewRol.getText().toString().trim().toUpperCase();

            if (user.isEmpty() || pass.isEmpty() || rol.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!rol.equals("ADMIN") && !rol.equals("SUPERVISOR") && !rol.equals("USUARIO")) {
                Toast.makeText(this, "Rol inválido. Use: ADMIN, SUPERVISOR o USUARIO", Toast.LENGTH_LONG).show();
                return;
            }

            boolean exito = viewModel.registrarUsuario(user, pass, rol);
            if (exito) {
                Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                etNewUser.setText("");
                etNewPass.setText("");
                etNewRol.setText("");
                cargarListaUsuarios();
            } else {
                Toast.makeText(this, "Error: El usuario ya existe", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento para eliminar un usuario al hacer clic en la lista (excepto el admin principal por seguridad)
        listViewUsuarios.setOnItemClickListener((parent, view, position, id) -> {
            Usuario seleccionado = listaUsuariosActual.get(position);

            if (seleccionado.getUsername().equals("admin")) {
                Toast.makeText(this, "No se puede eliminar al Administrador principal", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.eliminarUsuario(seleccionado.getUsername());
            Toast.makeText(this, "Usuario eliminado: " + seleccionado.getUsername(), Toast.LENGTH_SHORT).show();
            cargarListaUsuarios();
        });
    }

    // Método para refrescar el ListView de usuarios
    private void cargarListaUsuarios() {
        listaUsuariosActual = viewModel.obtenerListaUsuarios();
        List<String> descripciones = new ArrayList<>();
        for (Usuario u : listaUsuariosActual) {
            descripciones.add("Usuario: " + u.getUsername() + " | Rol: " + u.getRol());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, descripciones);
        listViewUsuarios.setAdapter(adapter);
    }
}