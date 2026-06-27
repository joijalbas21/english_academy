package com.englishacademy.models.services;

import com.englishacademy.infra.DatabaseConnection;
import com.englishacademy.models.Alumno;
import com.englishacademy.utils.ValidacionesHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de gestión de alumnos con persistencia en base de datos MySQL.
 * Utiliza JDBC para acceder a la tabla "alumnos" en la base de datos.
 */
public class AlumnoService {

    private static AlumnoService instancia;

    private AlumnoService() {
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

    /**
     * Registra un nuevo alumno validando sus datos e insertándolo en la base de datos.
     *
     * @param alumno el alumno a registrar
     * @throws IllegalArgumentException si los datos no son válidos
     * @throws RuntimeException si hay un error de base de datos
     */
    public void registrar(Alumno alumno) {
        validarAlumno(alumno);
        String sql = "INSERT INTO alumnos (nombre, apellido, dni, email, telefono) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setInt(3, alumno.getDni());
            ps.setString(4, alumno.getEmail());
            ps.setString(5, alumno.getTelefono());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    alumno.setIdAlumno(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar alumno en la base de datos", e);
        }
    }

    /**
     * Modifica un alumno existente validando sus datos y actualizándolo en la base de datos.
     *
     * @param alumno el alumno con datos actualizados
     * @throws IllegalArgumentException si los datos no son válidos o el alumno no existe
     * @throws RuntimeException si hay un error de base de datos
     */
    public void modificar(Alumno alumno) {
        validarAlumno(alumno);
        String sql = "UPDATE alumnos SET nombre=?, apellido=?, dni=?, email=?, telefono=? WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setInt(3, alumno.getDni());
            ps.setString(4, alumno.getEmail());
            ps.setString(5, alumno.getTelefono());
            ps.setInt(6, alumno.getIdAlumno());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new IllegalArgumentException("Alumno no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar alumno en la base de datos", e);
        }
    }

    /**
     * Elimina un alumno de la base de datos por su ID.
     *
     * @param idAlumno el ID del alumno a eliminar
     * @throws RuntimeException si hay un error de base de datos
     */
    public void eliminar(int idAlumno) {
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement psMatriculas = conn.prepareStatement("DELETE FROM matriculas WHERE id_alumno=?")) {
            psMatriculas.setInt(1, idAlumno);
            psMatriculas.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar matriculaciones del alumno", e);
        }

        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM alumnos WHERE id=?")) {
            ps.setInt(1, idAlumno);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar alumno de la base de datos", e);
        }
    }

    /**
     * Obtiene un alumno por su ID desde la base de datos.
     *
     * @param idAlumno el ID del alumno
     * @return el alumno encontrado, o null si no existe
     * @throws RuntimeException si hay un error de base de datos
     */
    public Alumno obtener(int idAlumno) {
        String sql = "SELECT * FROM alumnos WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAlumno(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener alumno de la base de datos", e);
        }
        return null;
    }

    /**
     * Obtiene la lista de todos los alumnos registrados en la base de datos.
     *
     * @return lista de alumnos
     * @throws RuntimeException si hay un error de base de datos
     */
    public List<Alumno> obtenerTodos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                alumnos.add(mapearAlumno(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener lista de alumnos de la base de datos", e);
        }
        return alumnos;
    }

    /**
     * Mapea una fila de ResultSet a un objeto Alumno.
     */
    private Alumno mapearAlumno(ResultSet rs) throws SQLException {
        return new Alumno(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getInt("dni"),
                rs.getString("email"),
                rs.getString("telefono")
        );
    }

    /**
     * Valida los datos de un alumno según las reglas de negocio.
     *
     * @param alumno el alumno a validar
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public void validarAlumno(Alumno alumno) {
        ValidacionesHelper.validarNombrePersona(alumno.getNombre(), "El nombre");
        ValidacionesHelper.validarNombrePersona(alumno.getApellido(), "El apellido");
        ValidacionesHelper.validarDNI(alumno.getDni());
        ValidacionesHelper.validarEmail(alumno.getEmail());
        ValidacionesHelper.validarTelefono(alumno.getTelefono());
    }
}
