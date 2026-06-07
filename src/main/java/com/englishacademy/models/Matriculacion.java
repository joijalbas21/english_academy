package com.englishacademy.models;

import java.util.Date;

public class Matriculacion {
    public Date fechaInscripcion;
    public String estado;

    public Matriculacion() {
    }

    public Matriculacion(Date fechaInscripcion, String estado) {
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Matriculacion{" +
                "fechaInscripcion=" + fechaInscripcion +
                ", estado='" + estado + '\'' +
                '}';
    }
}
