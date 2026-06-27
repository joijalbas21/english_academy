package com.englishacademy.models.services;

import com.englishacademy.infra.DatabaseConnection;
import com.englishacademy.models.Profesor;
import com.englishacademy.utils.ValidacionesHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de gestión de profesores con persistencia en base de datos MySQL.
 * Utiliza JDBC para acceder a la tabla "profesores" en la base de datos.
 */
public class ProfesorService {

    private static ProfesorService instancia;

    private ProfesorService() {
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

    /**
     * Registra un nuevo profesor validando sus datos e insertándolo en la base de datos.
     *
     * @param profesor el profesor a registrar
     * @throws IllegalArgumentException si los datos no son válidos
     * @throws RuntimeException si hay un error de base de datos
     */
    public void registrar(Profesor profesor) {
        validarProfesor(profesor);
        String sql = "INSERT INTO profesores (nombre, apellido, dni, email) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellido());
            ps.setInt(3, profesor.getDni());
            ps.setString(4, profesor.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    profesor.setIdProfesor(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar profesor en la base de datos", e);
        }
    }

    /**
     * Modifica un profesor existente validando sus datos y actualizándolo en la base de datos.
     *
     * @param profesor el profesor con datos actualizados
     * @throws IllegalArgumentException si los datos no son válidos o el profesor no existe
     * @throws RuntimeException si hay un error de base de datos
     */
    public void modificar(Profesor profesor) {
        validarProfesor(profesor);
        String sql = "UPDATE profesores SET nombre=?, apellido=?, dni=?, email=? WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellido());
            ps.setInt(3, profesor.getDni());
            ps.setString(4, profesor.getEmail());
            ps.setInt(5, profesor.getIdProfesor());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new IllegalArgumentException("Profesor no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar profesor en la base de datos", e);
        }
    }

    /**
     * Elimina un profesor de la base de datos por su ID.
     *
     * @param idProfesor el ID del profesor a eliminar
     * @throws RuntimeException si hay un error de base de datos
     */
    public void eliminar(int idProfesor) {
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement psCursos = conn.prepareStatement("UPDATE cursos SET id_profesor=NULL WHERE id_profesor=?")) {
            psCursos.setInt(1, idProfesor);
            psCursos.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al desasignar profesor de los cursos", e);
        }

        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM profesores WHERE id=?")) {
            ps.setInt(1, idProfesor);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar profesor de la base de datos", e);
        }
    }

    /**
     * Obtiene un profesor por su ID desde la base de datos.
     *
     * @param idProfesor el ID del profesor
     * @return el profesor encontrado, o null si no existe
     * @throws RuntimeException si hay un error de base de datos
     */
    public Profesor obtener(int idProfesor) {
        String sql = "SELECT * FROM profesores WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProfesor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProfesor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener profesor de la base de datos", e);
        }
        return null;
    }

    /**
     * Obtiene la lista de todos los profesores registrados en la base de datos.
     *
     * @return lista de profesores
     * @throws RuntimeException si hay un error de base de datos
     */
    public List<Profesor> obtenerTodos() {
        List<Profesor> profesores = new ArrayList<>();
        String sql = "SELECT * FROM profesores";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                profesores.add(mapearProfesor(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener lista de profesores de la base de datos", e);
        }
        return profesores;
    }

    /**
     * Mapea una fila de ResultSet a un objeto Profesor.
     */
    private Profesor mapearProfesor(ResultSet rs) throws SQLException {
        return new Profesor(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getInt("dni"),
                rs.getString("email")
        );
    }

    /**
     * Valida los datos de un profesor según las reglas de negocio.
     *
     * @param profesor el profesor a validar
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public void validarProfesor(Profesor profesor) {
        ValidacionesHelper.validarNombrePersona(profesor.getNombre(), "El nombre");
        ValidacionesHelper.validarNombrePersona(profesor.getApellido(), "El apellido");
        ValidacionesHelper.validarDNI(profesor.getDni());
        ValidacionesHelper.validarEmail(profesor.getEmail());
    }
}
