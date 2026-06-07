package com.englishacademy.models.services;

import com.englishacademy.models.Matriculacion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio de gestión de matriculaciones (inscripción de alumnos en cursos).
 *
 * NOTA: Por ahora, los datos se almacenan en una lista en memoria y se pierden
 * al reiniciar la aplicación. En futuras iteraciones, se implementará persistencia
 * en base de datos MySQL.
 */
public class MatriculacionService {

    private static MatriculacionService instancia;
    private List<Matriculacion> matriculaciones;

    private MatriculacionService() {
        this.matriculaciones = new ArrayList<>();
    }

    /**
     * Obtiene la instancia única del servicio (patrón Singleton).
     *
     * @return instancia de MatriculacionService
     */
    public static MatriculacionService getInstance() {
        if (instancia == null) {
            instancia = new MatriculacionService();
        }
        return instancia;
    }

    /**
     * Inscribe un alumno en un curso.
     *
     * @param idAlumno el ID del alumno
     * @param idCurso el ID del curso
     */
    public void inscribir(int idAlumno, int idCurso) {
        if (!estaInscripto(idAlumno, idCurso)) {
            Matriculacion m = new Matriculacion(idAlumno, idCurso, new Date(), "Activa");
            matriculaciones.add(m);
        }
    }

    /**
     * Verifica si un alumno ya está inscripto en un curso.
     *
     * @param idAlumno el ID del alumno
     * @param idCurso el ID del curso
     * @return true si ya está inscripto
     */
    public boolean estaInscripto(int idAlumno, int idCurso) {
        return matriculaciones.stream()
                .anyMatch(m -> m.getIdAlumno() == idAlumno && m.getIdCurso() == idCurso);
    }

    /**
     * Obtiene la lista de cursos en los que está inscripto un alumno.
     *
     * @param idAlumno el ID del alumno
     * @return lista de IDs de cursos
     */
    public List<Integer> obtenerCursosAlumno(int idAlumno) {
        List<Integer> cursos = new ArrayList<>();
        for (Matriculacion m : matriculaciones) {
            if (m.getIdAlumno() == idAlumno) {
                cursos.add(m.getIdCurso());
            }
        }
        return cursos;
    }

    /**
     * Obtiene la lista de alumnos inscritos en un curso.
     *
     * @param idCurso el ID del curso
     * @return lista de IDs de alumnos
     */
    public List<Integer> obtenerAlumnosCurso(int idCurso) {
        List<Integer> alumnos = new ArrayList<>();
        for (Matriculacion m : matriculaciones) {
            if (m.getIdCurso() == idCurso) {
                alumnos.add(m.getIdAlumno());
            }
        }
        return alumnos;
    }

    /**
     * Elimina la inscripción de un alumno en un curso.
     *
     * @param idAlumno el ID del alumno
     * @param idCurso el ID del curso
     */
    public void desinscribir(int idAlumno, int idCurso) {
        matriculaciones.removeIf(m -> m.getIdAlumno() == idAlumno && m.getIdCurso() == idCurso);
    }

    /**
     * Obtiene todas las matriculaciones.
     *
     * @return lista de todas las matriculaciones
     */
    public List<Matriculacion> obtenerTodas() {
        return new ArrayList<>(matriculaciones);
    }
}
