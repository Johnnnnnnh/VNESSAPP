package com.devst.vness.model;

// Modelo que representa los usuarios y sus roles en el sistema Vness
public class Usuario {
    private String user;
    private String password;
    private String rol; // "ADMIN", "SUPERVISOR", "USUARIO"

    public Usuario(String username, String password, String rol) {
        this.user = username;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername() { return user; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
}