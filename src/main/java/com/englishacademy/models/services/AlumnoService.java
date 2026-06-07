package com.englishacademy.models.services;

import com.englishacademy.models.Alumno;
import com.englishacademy.utils.ValidacionesHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de gestión de alumnos con persistencia en memoria.
 *
 * NOTA: Por ahora, los datos se almacenan en una lista en memoria y se pierden
 * al reiniciar la aplicación. En futuras iteraciones, se implementará persistencia
 * en base de datos MySQL mediante DAOs.
 */
public class AlumnoService {

    private static AlumnoService instancia;
    private List<Alumno> alumnos;
    private int nextId = 3;

    private AlumnoService() {
        this.alumnos = new ArrayList<>();
        inicializarConDatos();
    }

    /**
     * Obtiene la instancia única del servicio (patrón Singleton).
     *
     * @return instancia de AlumnoService
     */
    public static AlumnoService getInstance() {
        if (instancia == null) {
            instancia = new AlumnoService();
        }
        return instancia;
    }

    private void inicializarConDatos() {
        alumnos.add(new Alumno(1, "Joaquin", "Ijalba", 35706793, "joaquinijalba@gmail.com", "3434045828"));
        alumnos.add(new Alumno(2, "Martin", "Lopez", 30123456, "mlopez@academy.com", "1234567890"));
    }

    /**
     * Registra un nuevo alumno validando sus datos.
     *
     * @param alumno el alumno a registrar
     * @throws IllegalArgumentException si los datos no son válidos
     */
    public void registrar(Alumno alumno) {
        validarAlumno(alumno);
        alumno.setIdAlumno(nextId++);
        alumnos.add(alumno);
    }

    /**
     * Modifica un alumno existente validando sus datos.
     *
     * @param alumno el alumno con datos actualizados
     * @throws IllegalArgumentException si los datos no son válidos o el alumno no existe
     */
    public void modificar(Alumno alumno) {
        validarAlumno(alumno);
        Optional<Alumno> existente = alumnos.stream()
                .filter(a -> a.getIdAlumno() == alumno.getIdAlumno())
                .findFirst();

        if (existente.isPresent()) {
            Alumno a = existente.get();
            a.setNombre(alumno.getNombre());
            a.setApellido(alumno.getApellido());
            a.setDni(alumno.getDni());
            a.setEmail(alumno.getEmail());
            a.setTelefono(alumno.getTelefono());
        } else {
            throw new IllegalArgumentException("Alumno no encontrado");
        }
    }

    /**
     * Elimina un alumno de la lista por su ID.
     *
     * @param idAlumno el ID del alumno a eliminar
     */
    public void eliminar(int idAlumno) {
        alumnos.removeIf(a -> a.getIdAlumno() == idAlumno);
    }

    /**
     * Obtiene un alumno por su ID.
     *
     * @param idAlumno el ID del alumno
     * @return el alumno encontrado, o null si no existe
     */
    public Alumno obtener(int idAlumno) {
        return alumnos.stream()
                .filter(a -> a.getIdAlumno() == idAlumno)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene la lista de todos los alumnos registrados.
     *
     * @return copia de la lista de alumnos
     */
    public List<Alumno> obtenerTodos() {
        return new ArrayList<>(alumnos);
    }

    public void validarAlumno(Alumno alumno) {
        ValidacionesHelper.validarNombrePersona(alumno.getNombre(), "El nombre");
        ValidacionesHelper.validarNombrePersona(alumno.getApellido(), "El apellido");
        ValidacionesHelper.validarDNI(alumno.getDni());
        ValidacionesHelper.validarEmail(alumno.getEmail());
        ValidacionesHelper.validarTelefono(alumno.getTelefono());
    }
}
