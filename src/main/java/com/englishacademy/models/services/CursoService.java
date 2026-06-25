package com.englishacademy.models.services;

import com.englishacademy.infra.DatabaseConnection;
import com.englishacademy.models.Curso;
import com.englishacademy.utils.JdbcHelper;
import com.englishacademy.utils.ValidacionesHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de gestión de cursos con persistencia en base de datos MySQL.
 * Utiliza JDBC para acceder a la tabla "cursos" en la base de datos.
 */
public class CursoService {

    private static CursoService instancia;

    private CursoService() {
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

    /**
     * Registra un nuevo curso validando sus datos e insertándolo en la base de datos.
     *
     * @param curso el curso a registrar
     * @throws IllegalArgumentException si los datos no son válidos
     * @throws RuntimeException si hay un error de base de datos
     */
    public void registrar(Curso curso) {
        validarCurso(curso);
        String sql = "INSERT INTO cursos (nombre, nivel, descripcion, id_ciclo_lectivo, diaSemana, horaInicio, horaFin, id_aula, id_profesor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, curso.getNombre());
            ps.setString(2, curso.getNivel());
            ps.setString(3, curso.getDescripcion());
            ps.setInt(4, curso.getIdCicloLectivo());
            ps.setString(5, curso.getDiaInicio());
            ps.setTime(6, curso.getHoraInicio());
            ps.setTime(7, curso.getHoraFin());
            JdbcHelper.setIntOrNull(ps, 8, curso.getIdAula());
            JdbcHelper.setIntOrNull(ps, 9, curso.getIdProfesor());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    curso.setIdCurso(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar curso en la base de datos", e);
        }
    }

    /**
     * Modifica un curso existente validando sus datos y actualizándolo en la base de datos.
     *
     * @param curso el curso con datos actualizados
     * @throws IllegalArgumentException si los datos no son válidos o el curso no existe
     * @throws RuntimeException si hay un error de base de datos
     */
    public void modificar(Curso curso) {
        validarCurso(curso);
        String sql = "UPDATE cursos SET nombre=?, nivel=?, descripcion=?, id_ciclo_lectivo=?, diaSemana=?, horaInicio=?, horaFin=?, id_aula=?, id_profesor=? WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNombre());
            ps.setString(2, curso.getNivel());
            ps.setString(3, curso.getDescripcion());
            ps.setInt(4, curso.getIdCicloLectivo());
            ps.setString(5, curso.getDiaInicio());
            ps.setTime(6, curso.getHoraInicio());
            ps.setTime(7, curso.getHoraFin());
            JdbcHelper.setIntOrNull(ps, 8, curso.getIdAula());
            JdbcHelper.setIntOrNull(ps, 9, curso.getIdProfesor());
            ps.setInt(10, curso.getIdCurso());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new IllegalArgumentException("Curso no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar curso en la base de datos", e);
        }
    }

    /**
     * Elimina un curso de la base de datos por su ID.
     *
     * @param idCurso el ID del curso a eliminar
     * @throws RuntimeException si hay un error de base de datos
     */
    public void eliminar(int idCurso) {
        String sql = "DELETE FROM cursos WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar curso de la base de datos", e);
        }
    }

    /**
     * Obtiene un curso por su ID desde la base de datos.
     *
     * @param idCurso el ID del curso
     * @return el curso encontrado, o null si no existe
     * @throws RuntimeException si hay un error de base de datos
     */
    public Curso obtener(int idCurso) {
        String sql = "SELECT * FROM cursos WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCurso(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener curso de la base de datos", e);
        }
        return null;
    }

    /**
     * Obtiene la lista de todos los cursos registrados en la base de datos.
     *
     * @return lista de cursos
     * @throws RuntimeException si hay un error de base de datos
     */
    public List<Curso> obtenerTodos() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cursos.add(mapearCurso(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener lista de cursos de la base de datos", e);
        }
        return cursos;
    }

    /**
     * Mapea una fila de ResultSet a un objeto Curso.
     */
    private Curso mapearCurso(ResultSet rs) throws SQLException {
        return new Curso(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("nivel"),
                rs.getString("descripcion"),
                rs.getInt("id_ciclo_lectivo"),
                rs.getString("diaSemana"),
                rs.getTime("horaInicio"),
                rs.getTime("horaFin"),
                rs.getInt("id_aula"),
                rs.getInt("id_profesor")
        );
    }

    /**
     * Valida los datos de un curso según las reglas de negocio.
     *
     * @param curso el curso a validar
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public void validarCurso(Curso curso) {
        ValidacionesHelper.validarNombre(curso.getNombre(), "El nombre");
        validarDescripcion(curso.getDescripcion());
    }

    private void validarDescripcion(String descripcion) {
        if (descripcion != null && descripcion.length() > 200) {
            throw new IllegalArgumentException("La descripción es incorrecto.");
        }
    }
}
