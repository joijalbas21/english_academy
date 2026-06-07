package com.englishacademy.models;

import java.util.Date;
import java.text.SimpleDateFormat;

public class CicloLectivo {
    private int idCicloLectivo;
    private int anio;
    private Date fechaInicio;
    private Date fechaFin;
    private String diaInicio;
    private String estado;

    public CicloLectivo() {
    }

    public CicloLectivo(int idCicloLectivo, int anio, Date fechaInicio, Date fechaFin, String diaInicio, String estado) {
        this.idCicloLectivo = idCicloLectivo;
        this.anio = anio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diaInicio = diaInicio;
        this.estado = estado;
    }

    public int getIdCicloLectivo() {
        return idCicloLectivo;
    }

    public void setIdCicloLectivo(int idCicloLectivo) {
        this.idCicloLectivo = idCicloLectivo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(String diaInicio) {
        this.diaInicio = diaInicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String inicio = fechaInicio != null ? sdf.format(fechaInicio) : "N/A";
        String fin = fechaFin != null ? sdf.format(fechaFin) : "N/A";
        return inicio + " - " + fin + " - " + estado;
    }
}
