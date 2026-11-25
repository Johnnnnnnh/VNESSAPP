package com.devst.vness.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

// Helper para administrar la creacion y actualizacion de la base de datos SQLite
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    // Nombre de la base de datos exigido por el enunciado
    private static final String DATABASE_NAME = "Bodega.db";
    private static final int DATABASE_VERSION = 1;

    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creacion de la tabla productos según requerimiento original
        db.execSQL("CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, stock INTEGER, precio REAL)");

        // Creacion de la tabla usuarios para el control de roles de Vness
        db.execSQL("CREATE TABLE usuarios (username TEXT PRIMARY KEY, password TEXT, rol TEXT)");

        // Insertar usuarios de prueba por defecto
        db.execSQL("INSERT INTO usuarios VALUES ('admin', '1234', 'ADMIN')");
        db.execSQL("INSERT INTO usuarios VALUES ('super', '1234', 'SUPERVISOR')");
        db.execSQL("INSERT INTO usuarios VALUES ('user', '1234', 'USUARIO')");

        // Datos iniciales de prueba para trámites
        db.execSQL("INSERT INTO productos (nombre, stock, precio) VALUES ('Cédula de Identidad', 15, 3820.0)");
        db.execSQL("INSERT INTO productos (nombre, stock, precio) VALUES ('Pasaporte 32 Páginas', 10, 69660.0)");
        db.execSQL("INSERT INTO productos (nombre, stock, precio) VALUES ('Certificado Nacimiento', 99, 0.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}