package com.englishacademy.models;

import java.util.Date;

public class Matriculacion {
    private int idAlumno;
    private int idCurso;
    private Date fechaInscripcion;
    private String estado;

    public Matriculacion() {
    }

    public Matriculacion(int idAlumno, int idCurso, Date fechaInscripcion, String estado) {
        this.idAlumno = idAlumno;
        this.idCurso = idCurso;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
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
                "idAlumno=" + idAlumno +
                ", idCurso=" + idCurso +
                ", fechaInscripcion=" + fechaInscripcion +
                ", estado='" + estado + '\'' +
                '}';
    }
}
