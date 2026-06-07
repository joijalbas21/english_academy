package com.englishacademy.infra;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Gestor de conexión a la base de datos MySQL usando el patrón Singleton.
 *
 * Esta clase proporciona una única instancia de {@link java.sql.Connection}
 * que se reutiliza en toda la aplicación. La conexión se inicializa de forma
 * "lazy" (perezosa) la primera vez que se solicita y se mantiene abierta
 * durante el ciclo de vida de la aplicación.
 *
 * La configuración de conexión se lee desde el archivo {@code db.properties}
 * ubicado en {@code src/main/resources/}, que contiene:
 * <ul>
 *   <li>{@code db.url}: URL de conexión JDBC (ej: jdbc:mysql://localhost:3306/english_academy)</li>
 *   <li>{@code db.user}: Usuario de MySQL (ej: root)</li>
 *   <li>{@code db.password}: Contraseña de MySQL</li>
 * </ul>
 *
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>
 * Connection conn = DatabaseConnection.getConnection();
 * Statement stmt = conn.createStatement();
 * </pre>
 *
 * @see java.sql.Connection
 * @see java.sql.DriverManager
 */
public class DatabaseConnection {

    private static Connection connection;

    /**
     * Constructor privado para prevenir instanciación.
     *
     * El acceso a la conexión debe realizarse únicamente a través del método
     * estático {@link #getConnection()}.
     */
    private DatabaseConnection() {}

    /**
     * Obtiene la conexión única (singleton) a la base de datos.
     *
     * Si es la primera vez que se llama a este método, se establece la conexión
     * leyendo la configuración de {@code db.properties}. En subsecuentes llamadas,
     * se retorna la misma conexión reutilizada.
     *
     * @return una instancia única de {@link java.sql.Connection} a MySQL
     * @throws RuntimeException si no se puede cargar el archivo {@code db.properties}
     *         o si falla la conexión a la base de datos
     */
    public static Connection getConnection() {
        if (connection == null) {
            try (InputStream input = DatabaseConnection.class
                    .getResourceAsStream("/db.properties")) {
                Properties props = new Properties();
                props.load(input);
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                throw new RuntimeException("No se pudo conectar a la base de datos", e);
            }
        }
        return connection;
    }
}
