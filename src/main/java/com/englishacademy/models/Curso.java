package com.englishacademy.models;

import java.sql.Timestamp;

public class Curso {
    public int idCurso;
    public String nombre;
    public String nivel;
    public String descripcion;
    public String diaInicio;
    public Timestamp horaInicio;
    public Timestamp horaFin;

    public Curso() {
    }

    public Curso(int idCurso, String nombre, String nivel, String descripcion, String diaInicio,
                 Timestamp horaInicio, Timestamp horaFin) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.nivel = nivel;
        this.descripcion = descripcion;
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

    public String getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(String diaInicio) {
        this.diaInicio = diaInicio;
    }

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Timestamp getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", nombre='" + nombre + '\'' +
                ", nivel='" + nivel + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", diaInicio='" + diaInicio + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}
