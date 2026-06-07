package com.englishacademy.models;

import java.util.Date;

public class CicloLectivo {
    public int idCicloLectivo;
    public int anio;
    public Date fechaInicio;
    public String estado;

    public CicloLectivo() {
    }

    public CicloLectivo(int idCicloLectivo, int anio, Date fechaInicio, String estado) {
        this.idCicloLectivo = idCicloLectivo;
        this.anio = anio;
        this.fechaInicio = fechaInicio;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CicloLectivo{" +
                "idCicloLectivo=" + idCicloLectivo +
                ", anio=" + anio +
                ", fechaInicio=" + fechaInicio +
                ", estado='" + estado + '\'' +
                '}';
    }
}
