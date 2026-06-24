package com.englishacademy.models;

public class Profesor extends Persona {

    public Profesor() {
    }

    public Profesor(int idProfesor, String nombre, String apellido, int dni, String email) {
        super(idProfesor, nombre, apellido, dni, email);
    }

    public int getIdProfesor() {
        return getId();
    }

    public void setIdProfesor(int idProfesor) {
        setId(idProfesor);
    }

    @Override
    public String getDescripcion() {
        return "Profesor: " + nombre + " " + apellido + " (DNI " + dni + ")";
    }
}
