package DataAccess.DataHelper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase abstracta DbHelper para gestionar la conexión a la base de datos SQLite.
 * Implementa el patrón Singleton para garantizar una única instancia de la conexión.
 */
public abstract class DbHelper {

    private static final Logger LOGGER = Logger.getLogger(DbHelper.class.getName());
    private static final String DB_URL = "jdbc:sqlite:database/museo.db";
    private static final String DRIVER = "org.sqlite.JDBC";

    private static Connection connection;

    static {
        verificarBaseDeDatos();
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            if (connection != null) {
                LOGGER.info("Conexión exitosa a la base de datos.");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "El controlador JDBC no se encontró.", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos.", e);
        }
    }

    protected DbHelper() {
        // Constructor protegido para evitar instanciación directa.
    }

    /**
     * Obtiene la conexión única a la base de datos.
     * 
     * @return La conexión actual.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                LOGGER.info("Intentando reabrir la conexión a la base de datos.");
                connection = DriverManager.getConnection(DB_URL);
            }
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la conexión.", e);
            return null;
        }
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.info("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cerrar la conexión.", e);
        }
    }

    /**
     * Verifica si la base de datos existe en la ruta especificada.
     */
    private static void verificarBaseDeDatos() {
        File dbFile = new File("database/museo.db");
        if (!dbFile.exists()) {
            LOGGER.severe("La base de datos no existe en la ruta: " + dbFile.getAbsolutePath());
        }
    }
}
