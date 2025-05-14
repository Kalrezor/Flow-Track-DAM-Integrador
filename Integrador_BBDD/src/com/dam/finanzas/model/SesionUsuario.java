package com.dam.finanzas.model;

public class SesionUsuario {
    private static SesionUsuario instancia;
    private int idUsuarioActual;
    private String nombreUsuario;

    private SesionUsuario() {
        // Constructor privado para evitar la creación de instancias desde fuera de la clase
    }

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public int getIdUsuarioActual() {
        return idUsuarioActual;
    }

    public void setIdUsuarioActual(int idUsuarioActual) {
        this.idUsuarioActual = idUsuarioActual;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}

