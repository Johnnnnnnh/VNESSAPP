package com.devst.vness.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.devst.vness.model.Producto;
import com.devst.vness.model.Usuario;

import java.util.ArrayList;
import java.util.List;

// Repositorio que centraliza todas las consultas SQL
public class RepositorioVness {

    private final AdminSQLiteOpenHelper dbHelper;

    public RepositorioVness(Context context) {
        this.dbHelper = new AdminSQLiteOpenHelper(context);
    }

    // Metodo para validar credenciales de usuario
    public Usuario validarUsuario(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username, password, rol FROM usuarios WHERE username=? AND password=?", new String[]{username, password});

        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            usuario = new Usuario(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        return usuario;
    }

    // Insertar un nuevo producto / trámite
    public boolean insertarProducto(String nombre, int stock, double precio) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("stock", stock);
        registro.put("precio", precio);

        long resultado = db.insert("productos", null, registro);
        return resultado != -1;
    }

    // Obtener la lista completa de productos
    public List<Producto> obtenerProductos() {
        List<Producto> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nombre, stock, precio FROM productos", null);

        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getDouble(3)
                );
                lista.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    // Obtener un producto especifico por ID
    public Producto obtenerProductoPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nombre, stock, precio FROM productos WHERE id=?", new String[]{String.valueOf(id)});

        Producto producto = null;
        if (cursor.moveToFirst()) {
            producto = new Producto(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getDouble(3));
        }
        cursor.close();
        return producto;
    }

    // Actualizar un producto por ID
    public int actualizarProducto(int id, String nombre, int stock, double precio) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("stock", stock);
        registro.put("precio", precio);

        return db.update("productos", registro, "id=?", new String[]{String.valueOf(id)});
    }

    // Eliminar un producto por ID
    public int eliminarProducto(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("productos", "id=?", new String[]{String.valueOf(id)});
    }

    // Insertar un nuevo usuario en la base de datos
    public boolean insertarUsuario(String username, String password, String rol) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("username", username);
        registro.put("password", password);
        registro.put("rol", rol);

        long resultado = db.insert("usuarios", null, registro);
        return resultado != -1; // Retorna true si se insertó correctamente
    }

    // Eliminar un usuario por su nombre de usuario (username)
    public int eliminarUsuario(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("usuarios", "username=?", new String[]{username});
    }

    // Obtener la lista de todos los usuarios registrados
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username, password, rol FROM usuarios", null);

        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                lista.add(usuario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}