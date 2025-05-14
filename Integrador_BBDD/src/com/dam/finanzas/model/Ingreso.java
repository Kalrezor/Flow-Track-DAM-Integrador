package com.dam.finanzas.model;

public class Ingreso {
    private int idIngreso;
    private int idUsuario;
    private String descripcion;
    private double monto;
    private String fecha;

    public Ingreso(int idIngreso, int idUsuario, String descripcion, double monto, String fecha) {
        this.idIngreso = idIngreso;
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
