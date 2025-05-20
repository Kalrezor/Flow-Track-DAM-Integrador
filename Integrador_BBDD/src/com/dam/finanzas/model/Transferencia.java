package com.dam.finanzas.model;

public class Transferencia {
    private int idOrigen;
    private int idDestino;
    private double monto;
    private String descripcion;

    public Transferencia(int idOrigen, int idDestino, double monto, String descripcion) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
