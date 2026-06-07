package com.englishacademy.utils;

/**
 * Contexto global de la aplicación para pasar parámetros entre vistas.
 *
 * Se utiliza como almacén temporal de datos durante la navegación entre controladores.
 * Por ejemplo, cuando se navega a la vista de edición de alumno, se almacena el ID
 * del alumno en esta clase para que el controlador destino pueda recuperarlo.
 * Se debe limpiar el contexto después de usar los datos.
 */
public class ContextoApp {
    private static Integer idAlumnoEnEdicion;

    public static Integer getIdAlumnoEnEdicion() {
        return idAlumnoEnEdicion;
    }

    public static void setIdAlumnoEnEdicion(Integer id) {
        idAlumnoEnEdicion = id;
    }

    public static void limpiar() {
        idAlumnoEnEdicion = null;
    }
}
