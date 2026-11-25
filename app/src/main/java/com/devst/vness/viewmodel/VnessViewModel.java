package com.devst.vness.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devst.vness.db.RepositorioVness;
import com.devst.vness.model.Producto;
import com.devst.vness.model.Usuario;

import java.util.List;

// ViewModel para exponer los datos de forma reactiva a las Activities
public class VnessViewModel extends AndroidViewModel {

    private final RepositorioVness repository;
    private final MutableLiveData<List<Producto>> listaProductosLiveData;

    public VnessViewModel(@NonNull Application application) {
        super(application);
        repository = new RepositorioVness(application);
        listaProductosLiveData = new MutableLiveData<>();
        cargarProductos();
    }

    // Retorna el LiveData observado por la vista principal
    public LiveData<List<Producto>> getListaProductos() {
        return listaProductosLiveData;
    }

    // Cargar o refrescar la lista desde el repositorio
    public void cargarProductos() {
        listaProductosLiveData.setValue(repository.obtenerProductos());
    }

    // Autenticacion de usuarios
    public Usuario iniciarSesion(String username, String password) {
        return repository.validarUsuario(username, password);
    }

    // Operacion Registrar
    public void registrarProducto(String nombre, int stock, double precio) {
        repository.insertarProducto(nombre, stock, precio);
        cargarProductos();
    }

    // Operacion Actualizar
    public void actualizarProducto(int id, String nombre, int stock, double precio) {
        repository.actualizarProducto(id, nombre, stock, precio);
        cargarProductos();
    }

    // Operacion Eliminar
    public void eliminarProducto(int id) {
        repository.eliminarProducto(id);
        cargarProductos();
    }

    // Registrar un nuevo usuario (Restringido para Admin)
    public boolean registrarUsuario(String username, String password, String rol) {
        return repository.insertarUsuario(username, password, rol);
    }

    // Eliminar un usuario (Restringido para Admin)
    public int eliminarUsuario(String username) {
        return repository.eliminarUsuario(username);
    }

    // Obtener lista de usuarios para mostrar en pantalla
    public List<Usuario> obtenerListaUsuarios() {
        return repository.obtenerUsuarios();
    }
}