package com.pfc.android.revisionesapp.modelos;

import androidx.annotation.NonNull;

public class Equipo {
    private String id;
    private String nombre;
    private Long tipoProducto;
    private String marca;
    private String modelo;
    private Long nSerie;
    private double peso;
    private double dimensiones;
    private String ubicacion;

    public Equipo() {
    }

    public Equipo(String id, String nombre, Long tipoProducto, String marca, String modelo, Long nSerie, double peso,
                  double dimensiones, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.marca = marca;
        this.modelo = modelo;
        this.nSerie = nSerie;
        this.peso = peso;
        this.dimensiones = dimensiones;
        this.ubicacion = ubicacion;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Long getTipoProducto() {
        return tipoProducto;
    }
    public void setTipoProducto(Long tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public Long getnSerie() {
        return nSerie;
    }
    public void setnSerie(Long nSerie) {
        this.nSerie = nSerie;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public double getDimensiones() {
        return dimensiones;
    }
    public void setDimensiones(double dimensiones) {
        this.dimensiones = dimensiones;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipoProducto=" + tipoProducto +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", nSerie=" + nSerie +
                ", peso=" + peso +
                ", dimensiones=" + dimensiones +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
