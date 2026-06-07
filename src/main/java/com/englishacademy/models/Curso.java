package com.englishacademy.models;

import java.sql.Time;

public class Curso {
    private int idCurso;
    private String nombre;
    private String nivel;
    private String descripcion;
    private int idCicloLectivo;
    private String diaInicio;
    private Time horaInicio;
    private Time horaFin;

    public Curso() {
    }

    public Curso(int idCurso, String nombre, String nivel, String descripcion, int idCicloLectivo,
                 String diaInicio, Time horaInicio, Time horaFin) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.idCicloLectivo = idCicloLectivo;
        this.diaInicio = diaInicio;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCicloLectivo() {
        return idCicloLectivo;
    }

    public void setIdCicloLectivo(int idCicloLectivo) {
        this.idCicloLectivo = idCicloLectivo;
    }

    public String getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(String diaInicio) {
        this.diaInicio = diaInicio;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
}
