package com.englishacademy.models.services;

import com.englishacademy.models.Aula;
import com.englishacademy.models.Curso;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de consulta de aulas con datos hardcodeados en memoria.
 *
 * NOTA: Las 5 aulas de la academia son fijas y no cambian. Este servicio
 * proporciona acceso a ellas e implementa la lógica de validación de disponibilidad (CU013).
 *
 * No hay operaciones CRUD de aulas — solo lectura y validación de conflictos de horario.
 */
public class AulaService {

    private static AulaService instancia;
    private List<Aula> aulas;

    private AulaService() {
        this.aulas = new ArrayList<>();
        inicializarAulas();
    }

    /**
     * Obtiene la instancia única del servicio (patrón Singleton).
     *
     * @return instancia de AulaService
     */
    public static AulaService getInstance() {
        if (instancia == null) {
            instancia = new AulaService();
        }
        return instancia;
    }

    private void inicializarAulas() {
        aulas.add(new Aula(1, "Aula 1", 20));
        aulas.add(new Aula(2, "Aula 2", 20));
        aulas.add(new Aula(3, "Aula 3", 20));
        aulas.add(new Aula(4, "Aula 4", 20));
        aulas.add(new Aula(5, "Aula 5", 20));
    }

    public List<Aula> obtenerTodos() {
        return new ArrayList<>(aulas);
    }

    public Aula obtener(int idAula) {
        return aulas.stream()
                .filter(a -> a.getIdAula() == idAula)
                .findFirst()
                .orElse(null);
    }

    /**
     * Valida la disponibilidad de un aula en un horario determinado.
     *
     * Implementa CU013: Valida que no haya solapamiento de cursos en la misma aula
     * para el mismo día y horario.
     *
     * Parámetro idCursoExcluir: al editar un curso, excluye su propio ID de la validación
     * para no bloquearse a sí mismo.
     *
     * @param idAula el ID del aula a validar
     * @param dia el día de la semana (ej: "Lunes", "Martes")
     * @param horaInicio hora inicio solicitada
     * @param horaFin hora fin solicitada
     * @param idCursoExcluir ID del curso actual (si edición), para excluirlo de conflictos
     * @return true si el aula está disponible en ese horario, false si hay solapamiento
     */
    public boolean validarDisponibilidad(int idAula, String dia, Time horaInicio, Time horaFin, int idCursoExcluir) {
        CursoService cursoService = CursoService.getInstance();

        for (Curso curso : cursoService.obtenerTodos()) {
            if (curso.getIdCurso() == idCursoExcluir) {
                continue;
            }

            if (curso.getIdAula() != idAula || !curso.getDiaInicio().equals(dia)) {
                continue;
            }

            if (curso.getHoraInicio() != null && curso.getHoraFin() != null) {
                if (horaInicio.before(curso.getHoraFin()) && horaFin.after(curso.getHoraInicio())) {
                    return false;
                }
            }
        }

        return true;
    }
}
