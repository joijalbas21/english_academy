package com.englishacademy.models.services;

import com.englishacademy.models.Curso;
import com.englishacademy.utils.ValidacionesHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de gestión de cursos con persistencia en memoria.
 *
 * NOTA: Por ahora, los datos se almacenan en una lista en memoria y se pierden
 * al reiniciar la aplicación. En futuras iteraciones, se implementará persistencia
 * en base de datos MySQL mediante DAOs.
 */
public class CursoService {

    private static CursoService instancia;
    private List<Curso> cursos;
    private int nextId = 3;

    private CursoService() {
        this.cursos = new ArrayList<>();
        inicializarConDatos();
    }

    /**
     * Obtiene la instancia única del servicio (patrón Singleton).
     *
     * @return instancia de CursoService
     */
    public static CursoService getInstance() {
        if (instancia == null) {
            instancia = new CursoService();
        }
        return instancia;
    }

    private void inicializarConDatos() {
        cursos.add(new Curso(1, "English Basics", "Beginner", "Curso introductorio de inglés", 1, null, null, null));
        cursos.add(new Curso(2, "Intermediate English", "Intermediate", "Curso de nivel intermedio", 1, null, null, null));
    }

    /**
     * Registra un nuevo curso validando sus datos.
     *
     * @param curso el curso a registrar
     * @throws IllegalArgumentException si los datos no son válidos
     */
    public void registrar(Curso curso) {
        validarCurso(curso);
        curso.setIdCurso(nextId++);
        cursos.add(curso);
    }

    /**
     * Modifica un curso existente validando sus datos.
     *
     * @param curso el curso con datos actualizados
     * @throws IllegalArgumentException si los datos no son válidos o el curso no existe
     */
    public void modificar(Curso curso) {
        validarCurso(curso);
        Optional<Curso> existente = cursos.stream()
                .filter(c -> c.getIdCurso() == curso.getIdCurso())
                .findFirst();

        if (existente.isPresent()) {
            Curso c = existente.get();
            c.setNombre(curso.getNombre());
            c.setNivel(curso.getNivel());
            c.setDescripcion(curso.getDescripcion());
            c.setIdCicloLectivo(curso.getIdCicloLectivo());
            c.setDiaInicio(curso.getDiaInicio());
            c.setHoraInicio(curso.getHoraInicio());
            c.setHoraFin(curso.getHoraFin());
        } else {
            throw new IllegalArgumentException("Curso no encontrado");
        }
    }

    /**
     * Elimina un curso de la lista por su ID.
     *
     * @param idCurso el ID del curso a eliminar
     */
    public void eliminar(int idCurso) {
        cursos.removeIf(c -> c.getIdCurso() == idCurso);
    }

    /**
     * Obtiene un curso por su ID.
     *
     * @param idCurso el ID del curso
     * @return el curso encontrado, o null si no existe
     */
    public Curso obtener(int idCurso) {
        return cursos.stream()
                .filter(c -> c.getIdCurso() == idCurso)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene la lista de todos los cursos registrados.
     *
     * @return copia de la lista de cursos
     */
    public List<Curso> obtenerTodos() {
        return new ArrayList<>(cursos);
    }

    public void validarCurso(Curso curso) {
        ValidacionesHelper.validarNombre(curso.getNombre(), "El nombre");
        validarNivel(curso.getNivel());
        validarDescripcion(curso.getDescripcion());
    }

    /**
     * Valida el nivel del curso.
     *
     * Parámetro correcto: 3-30 caracteres, letras, números y espacios.
     * Ej: "Beginner", "Intermediate", "Advanced", "Level 1"
     *
     * @param nivel el nivel a validar
     * @throws IllegalArgumentException si el nivel no es válido
     */
    private void validarNivel(String nivel) {
        if (nivel == null || nivel.trim().isEmpty()) {
            throw new IllegalArgumentException("El nivel es incorrecto.");
        }
        if (nivel.length() < 3 || nivel.length() > 30) {
            throw new IllegalArgumentException("El nivel es incorrecto.");
        }
        if (!nivel.matches("[a-zA-Z0-9áéíóúñÁÉÍÓÚÑ\\s]+")) {
            throw new IllegalArgumentException("El nivel es incorrecto.");
        }
    }

    /**
     * Valida la descripción del curso.
     *
     * Parámetro correcto: 0-200 caracteres (opcional).
     * Ej: "Curso introductorio", "Nivel avanzado de gramática"
     *
     * @param descripcion la descripción a validar
     * @throws IllegalArgumentException si la descripción no es válida
     */
    private void validarDescripcion(String descripcion) {
        if (descripcion != null && descripcion.length() > 200) {
            throw new IllegalArgumentException("La descripción es incorrecto.");
        }
    }
}
