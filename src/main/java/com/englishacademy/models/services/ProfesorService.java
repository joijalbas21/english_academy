package com.englishacademy.models.services;

import com.englishacademy.models.Profesor;
import com.englishacademy.utils.ValidacionesHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de gestión de profesores con persistencia en memoria.
 *
 * NOTA: Por ahora, los datos se almacenan en una lista en memoria y se pierden
 * al reiniciar la aplicación. En futuras iteraciones, se implementará persistencia
 * en base de datos MySQL.
 */
public class ProfesorService {

    private static ProfesorService instancia;
    private List<Profesor> profesores;
    private int nextId = 3;

    private ProfesorService() {
        this.profesores = new ArrayList<>();
        inicializarConDatos();
    }

    /**
     * Obtiene la instancia única del servicio (patrón Singleton).
     *
     * @return instancia de ProfesorService
     */
    public static ProfesorService getInstance() {
        if (instancia == null) {
            instancia = new ProfesorService();
        }
        return instancia;
    }

    private void inicializarConDatos() {
        profesores.add(new Profesor(1, "Carlos", "González", 32654987, "carlos.gonzalez@academy.com"));
        profesores.add(new Profesor(2, "María", "Rodríguez", 28945612, "maria.rodriguez@academy.com"));
    }

    /**
     * Registra un nuevo profesor validando sus datos.
     *
     * @param profesor el profesor a registrar
     * @throws IllegalArgumentException si los datos no son válidos
     */
    public void registrar(Profesor profesor) {
        validarProfesor(profesor);
        profesor.setIdProfesor(nextId++);
        profesores.add(profesor);
    }

    /**
     * Modifica un profesor existente validando sus datos.
     *
     * @param profesor el profesor con datos actualizados
     * @throws IllegalArgumentException si los datos no son válidos o el profesor no existe
     */
    public void modificar(Profesor profesor) {
        validarProfesor(profesor);
        Optional<Profesor> existente = profesores.stream()
                .filter(p -> p.getIdProfesor() == profesor.getIdProfesor())
                .findFirst();

        if (existente.isPresent()) {
            Profesor p = existente.get();
            p.setNombre(profesor.getNombre());
            p.setApellido(profesor.getApellido());
            p.setDni(profesor.getDni());
            p.setEmail(profesor.getEmail());
        } else {
            throw new IllegalArgumentException("Profesor no encontrado");
        }
    }

    /**
     * Elimina un profesor de la lista por su ID.
     *
     * @param idProfesor el ID del profesor a eliminar
     */
    public void eliminar(int idProfesor) {
        profesores.removeIf(p -> p.getIdProfesor() == idProfesor);
    }

    /**
     * Obtiene un profesor por su ID.
     *
     * @param idProfesor el ID del profesor
     * @return el profesor encontrado, o null si no existe
     */
    public Profesor obtener(int idProfesor) {
        return profesores.stream()
                .filter(p -> p.getIdProfesor() == idProfesor)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene la lista de todos los profesores registrados.
     *
     * @return copia de la lista de profesores
     */
    public List<Profesor> obtenerTodos() {
        return new ArrayList<>(profesores);
    }

    public void validarProfesor(Profesor profesor) {
        ValidacionesHelper.validarNombrePersona(profesor.getNombre(), "El nombre");
        ValidacionesHelper.validarNombrePersona(profesor.getApellido(), "El apellido");
        ValidacionesHelper.validarDNI(profesor.getDni());
        ValidacionesHelper.validarEmail(profesor.getEmail());
    }

    public void registrarProfesor(Profesor profesor) {
        validarProfesor(profesor);
        profesor.setIdProfesor(nextId++);
        profesores.add(profesor);
    }
}
