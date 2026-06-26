package com.englishacademy.models.services;

import com.englishacademy.infra.DatabaseConnection;
import com.englishacademy.models.Aula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de consulta de aulas con persistencia en base de datos MySQL.
 * Solo lectura — las aulas son datos de referencia fijos sin operaciones CRUD.
 * Implementa la validación de disponibilidad de aulas (CU013).
 */
public class AulaService {

    private static AulaService instancia;

    private AulaService() {
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

    /**
     * Obtiene la lista de todas las aulas desde la base de datos.
     *
     * @return lista de aulas
     * @throws RuntimeException si hay un error de base de datos
     */
    public List<Aula> obtenerTodos() {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT * FROM aulas";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                aulas.add(mapearAula(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener lista de aulas de la base de datos", e);
        }
        return aulas;
    }

    /**
     * Obtiene un aula por su ID desde la base de datos.
     *
     * @param idAula el ID del aula
     * @return el aula encontrada, o null si no existe
     * @throws RuntimeException si hay un error de base de datos
     */
    public Aula obtener(int idAula) {
        String sql = "SELECT * FROM aulas WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAula(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener aula de la base de datos", e);
        }
        return null;
    }

    /**
     * Valida la disponibilidad de un aula en un horario determinado (CU013).
     * Consulta la base de datos para detectar solapamientos de horario en la misma aula y día.
     *
     * @param idAula el ID del aula a validar
     * @param dia el día de la semana
     * @param horaInicio hora inicio solicitada
     * @param horaFin hora fin solicitada
     * @param idCursoExcluir ID del curso actual (si es edición), para excluirlo de conflictos
     * @return true si el aula está disponible, false si hay solapamiento
     */
    public boolean validarDisponibilidad(int idAula, String dia, Time horaInicio, Time horaFin, int idCursoExcluir) {
        String sql = "SELECT COUNT(*) FROM cursos WHERE id_aula=? AND diaSemana=? AND id != ? " +
                     "AND horaInicio IS NOT NULL AND horaFin IS NOT NULL " +
                     "AND horaInicio < ? AND horaFin > ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAula);
            ps.setString(2, dia);
            ps.setInt(3, idCursoExcluir == -1 ? -1 : idCursoExcluir);
            ps.setTime(4, horaFin);
            ps.setTime(5, horaInicio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al validar disponibilidad del aula", e);
        }
        return true;
    }

    /**
     * Mapea una fila de ResultSet a un objeto Aula.
     */
    private Aula mapearAula(ResultSet rs) throws SQLException {
        return new Aula(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getInt("capacidad")
        );
    }
}
