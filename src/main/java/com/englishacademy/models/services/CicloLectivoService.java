package com.englishacademy.models.services;

import com.englishacademy.models.CicloLectivo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.GregorianCalendar;

/**
 * Servicio de consulta de ciclos lectivos con datos hardcodeados en memoria.
 *
 * NOTA: Este servicio está fuera del alcance de desarrollo de persistencia.
 * Por ahora, retorna ciclos lectivos hardcodeados que nunca cambiarán.
 *
 * Los ciclos lectivos son datos de referencia que se crean una única vez al año
 * y no requieren operaciones CRUD en tiempo de ejecución de la aplicación.
 */
public class CicloLectivoService {

    private static CicloLectivoService instancia;
    private List<CicloLectivo> ciclos;

    private CicloLectivoService() {
        this.ciclos = new ArrayList<>();
        inicializarConDatos();
    }

    /**
     * Obtiene la instancia única del servicio (patrón Singleton).
     *
     * @return instancia de CicloLectivoService
     */
    public static CicloLectivoService getInstance() {
        if (instancia == null) {
            instancia = new CicloLectivoService();
        }
        return instancia;
    }

    /**
     * Ciclos lectivos hardcodeados. Estos datos nunca cambiarán en memoria.
     * Para agregar nuevos ciclos, se modifica este método.
     */
    private void inicializarConDatos() {
        ciclos.add(new CicloLectivo(1, 2024,
            new GregorianCalendar(2024, 0, 15).getTime(),
            new GregorianCalendar(2024, 11, 31).getTime(),
            "Lunes", "Activo"));
        ciclos.add(new CicloLectivo(2, 2025,
            new GregorianCalendar(2025, 0, 13).getTime(),
            new GregorianCalendar(2025, 11, 19).getTime(),
            "Lunes", "Activo"));
    }

    /**
     * Obtiene la lista de todos los ciclos lectivos disponibles.
     *
     * @return copia de la lista de ciclos lectivos
     */
    public List<CicloLectivo> obtenerTodos() {
        return new ArrayList<>(ciclos);
    }

    /**
     * Obtiene un ciclo lectivo por su ID.
     *
     * @param idCicloLectivo el ID del ciclo
     * @return el ciclo encontrado, o null si no existe
     */
    public CicloLectivo obtener(int idCicloLectivo) {
        return ciclos.stream()
                .filter(c -> c.getIdCicloLectivo() == idCicloLectivo)
                .findFirst()
                .orElse(null);
    }
}
