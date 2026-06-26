package com.englishacademy.models.services;

import com.englishacademy.infra.DatabaseConnection;
import com.englishacademy.models.Matriculacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio de gestión de matriculaciones con persistencia en base de datos MySQL.
 * Utiliza JDBC para acceder a la tabla "matriculas" en la base de datos.
 */
public class MatriculacionService {

    private static MatriculacionService instancia;

    private MatriculacionService() {
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
     * Inscribe un alumno en un curso si no está ya inscripto.
     *
     * @param idAlumno el ID del alumno
     * @param idCurso el ID del curso
     * @throws RuntimeException si hay un error de base de datos
     */
    public void inscribir(int idAlumno, int idCurso) {
        if (estaInscripto(idAlumno, idCurso)) {
            return;
        }
        String sql = "INSERT INTO matriculas (id_alumno, id_curso, fecha_inscripcion, estado) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ps.setInt(2, idCurso);
            ps.setDate(3, new java.sql.Date(new Date().getTime()));
            ps.setString(4, "Activa");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al inscribir alumno en el curso", e);
        }
    }

    /**
     * Verifica si un alumno ya está inscripto en un curso.
     *
     * @param idAlumno el ID del alumno
     * @param idCurso el ID del curso
     * @return true si ya está inscripto
     * @throws RuntimeException si hay un error de base de datos
     */
    public boolean estaInscripto(int idAlumno, int idCurso) {
        String sql = "SELECT COUNT(*) FROM matriculas WHERE id_alumno=? AND id_curso=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ps.setInt(2, idCurso);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar inscripción", e);
        }
        return false;
    }

    /**
     * Obtiene la lista de IDs de cursos en los que está inscripto un alumno.
     *
     * @param idAlumno el ID del alumno
     * @return lista de IDs de cursos
     * @throws RuntimeException si hay un error de base de datos
     */
    public List<Integer> obtenerCursosAlumno(int idAlumno) {
        List<Integer> cursos = new ArrayList<>();
        String sql = "SELECT id_curso FROM matriculas WHERE id_alumno=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cursos.add(rs.getInt("id_curso"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener cursos del alumno", e);
        }
        return cursos;
    }

    /**
     * Obtiene la lista de IDs de alumnos inscritos en un curso.
     *
     * @param idCurso el ID del curso
     * @return lista de IDs de alumnos
     * @throws RuntimeException si hay un error de base de datos
     */
    public List<Integer> obtenerAlumnosCurso(int idCurso) {
        List<Integer> alumnos = new ArrayList<>();
        String sql = "SELECT id_alumno FROM matriculas WHERE id_curso=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    alumnos.add(rs.getInt("id_alumno"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener alumnos del curso", e);
        }
        return alumnos;
    }

    /**
     * Elimina la inscripción de un alumno en un curso.
     *
     * @param idAlumno el ID del alumno
     * @param idCurso el ID del curso
     * @throws RuntimeException si hay un error de base de datos
     */
    public void desinscribir(int idAlumno, int idCurso) {
        String sql = "DELETE FROM matriculas WHERE id_alumno=? AND id_curso=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ps.setInt(2, idCurso);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al desinscribir alumno del curso", e);
        }
    }

    /**
     * Obtiene todas las matriculaciones registradas en la base de datos.
     *
     * @return lista de todas las matriculaciones
     * @throws RuntimeException si hay un error de base de datos
     */
    public List<Matriculacion> obtenerTodas() {
        List<Matriculacion> matriculaciones = new ArrayList<>();
        String sql = "SELECT * FROM matriculas";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                matriculaciones.add(new Matriculacion(
                        rs.getInt("id_alumno"),
                        rs.getInt("id_curso"),
                        rs.getDate("fecha_inscripcion"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las matriculaciones", e);
        }
        return matriculaciones;
    }
}
