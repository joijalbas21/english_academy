package com.englishacademy.models;

public class Alumno extends Persona {
    private String telefono;

    public Alumno() {
    }

    public Alumno(int idAlumno, String nombre, String apellido, int dni, String email, String telefono) {
        super(idAlumno, nombre, apellido, dni, email);
        this.telefono = telefono;
    }

    public int getIdAlumno() {
        return getId();
    }

    public void setIdAlumno(int idAlumno) {
        setId(idAlumno);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String getDescripcion() {
        return "Alumno: " + nombre + " " + apellido + " (DNI " + dni + ") - Tel: " + telefono;
    }



    @Override
    public String toString() {
        return "Alumno{" +
                "idAlumno=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
