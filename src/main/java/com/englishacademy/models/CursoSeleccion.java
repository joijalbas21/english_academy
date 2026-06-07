package com.englishacademy.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Modelo de utilidad para mostrar un curso con checkbox seleccionable.
 * Se usa en los popups de selección de cursos para profesor y alumno.
 */
public class CursoSeleccion {
    private Curso curso;
    private BooleanProperty seleccionado;

    public CursoSeleccion(Curso curso, boolean seleccionado) {
        this.curso = curso;
        this.seleccionado = new SimpleBooleanProperty(seleccionado);
    }

    public Curso getCurso() {
        return curso;
    }

    public boolean isSeleccionado() {
        return seleccionado.get();
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado.set(seleccionado);
    }

    public BooleanProperty seleccionadoProperty() {
        return seleccionado;
    }

    @Override
    public String toString() {
        return curso.getNombre() + " (" + curso.getNivel() + ")";
    }
}
