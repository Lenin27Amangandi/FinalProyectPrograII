package DataAccess.DAO;

import DataAccess.IUsuarioDAO;
import DataAccess.DataHelper.DbHelper;
import BusinessLogic.entities.Usuario;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Implementación de IUsuarioDAO para manejar operaciones en la tabla Usuarios.
 */
public class UsuarioDAO extends DbHelper implements IUsuarioDAO {


        // Método para crear el usuario por defecto
    public void CrearDefaultUser() {
        // Ruta del archivo config.properties
        String configFile = "src/utils/Resources/config/config.properties";
        Properties properties = new Properties();

        // Cargar el archivo de configuración
        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de configuración: " + e.getMessage());
            return;
        }

        // Obtener las propiedades del archivo
        String username = properties.getProperty("usuario");
        String password = properties.getProperty("password");
        String nombre = properties.getProperty("nombre");
        String rol = properties.getProperty("rol");
        String identificacion = properties.getProperty("identificacion");

        // Verificar si el usuario ya existe en la base de datos
        if (verificarCredenciales(username, password)) {
            System.out.println("El usuario por defecto ya existe.");
        } else {
            // Si no existe, lo creamos
            String mensaje = agregarUsuario(nombre, rol, identificacion, username, password);
            System.out.println(mensaje);
        }
    }

    @Override
    public String agregarUsuario(String nombre, String rol, String identificacion, String username, String password) {
        Connection conn = DbHelper.getConnection();
        if (conn == null) return "Error: Conexión a la base de datos no disponible.";

        try {
            String insertCredenciales = "INSERT INTO Credenciales (username, password) VALUES (?, ?)";
            PreparedStatement credStmt = conn.prepareStatement(insertCredenciales, Statement.RETURN_GENERATED_KEYS);
            credStmt.setString(1, username);
            credStmt.setString(2, password);
            credStmt.executeUpdate();

            ResultSet rs = credStmt.getGeneratedKeys();
            rs.next();
            int idCredenciales = rs.getInt(1);

            String insertUsuarios = "INSERT INTO Usuarios (nombre, rol, identificacion, idCredenciales) VALUES (?, ?, ?, ?)";
            PreparedStatement userStmt = conn.prepareStatement(insertUsuarios);
            userStmt.setString(1, nombre);
            userStmt.setString(2, rol);
            userStmt.setString(3, identificacion);
            userStmt.setInt(4, idCredenciales);
            userStmt.executeUpdate();

            return "Usuario agregado correctamente.";
        } catch (SQLException e) {
            return "Error al agregar usuario: " + e.getMessage();
        }
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT identificacion, nombre, rol FROM Usuarios";

        try (Connection conn = DbHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    0,
                    rs.getString("nombre"),
                    rs.getString("rol"),
                    rs.getString("identificacion"),
                    null,
                    null
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public boolean verificarCredenciales(String username, String password) {
        String query = "SELECT u.idUsuarios FROM Usuarios u " +
                       "INNER JOIN Credenciales c ON u.idCredenciales = c.idCredenciales " +
                       "WHERE c.username = ? AND c.password = ?";

        try (Connection conn = DbHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al verificar credenciales: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean verificarCredencialesPorId(String id) {
        String query = "SELECT * FROM Usuarios WHERE identificacion = ?";

        try (Connection conn = DbHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al verificar credenciales por ID: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertarUsuario(Usuario usuario) {
        String sqlCredenciales = "INSERT INTO Credenciales (username, password) VALUES (?, ?)";
        String sqlUsuario = "INSERT INTO Usuarios (nombre, rol, identificacion, idCredenciales) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbHelper.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCredenciales = conn.prepareStatement(sqlCredenciales, Statement.RETURN_GENERATED_KEYS)) {
                stmtCredenciales.setString(1, usuario.getUsername());
                stmtCredenciales.setString(2, usuario.getPassword());
                int rowsInsertedCredenciales = stmtCredenciales.executeUpdate();

                if (rowsInsertedCredenciales > 0) {
                    try (ResultSet generatedKeys = stmtCredenciales.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int idCredenciales = generatedKeys.getInt(1);

                            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                                stmtUsuario.setString(1, usuario.getNombre());
                                stmtUsuario.setString(2, usuario.getRol());
                                stmtUsuario.setString(3, usuario.getIdentificacion());
                                stmtUsuario.setInt(4, idCredenciales);

                                int rowsInsertedUsuario = stmtUsuario.executeUpdate();
                                if (rowsInsertedUsuario > 0) {
                                    conn.commit();
                                    return true;
                                } else {
                                    conn.rollback();
                                }
                            }
                        } else {
                            conn.rollback();
                        }
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Error al insertar usuario: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Usuario buscarUsuario(String criterio) {
        String sql = "SELECT u.idUsuarios, u.nombre, u.rol, u.identificacion, c.username, c.password " +
                     "FROM Usuarios u " +
                     "JOIN Credenciales c ON u.idCredenciales = c.idCredenciales " +
                     "WHERE u.idUsuarios = ? OR u.identificacion = ? OR c.username = ?";

        try (Connection conn = DbHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, criterio);
            stmt.setString(2, criterio);
            stmt.setString(3, criterio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("idUsuarios"),
                    rs.getString("nombre"),
                    rs.getString("rol"),
                    rs.getString("identificacion"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean modificarUsuario(String id, String nombre, String rol, String username) {
        String sqlActualizarUsuarios = "UPDATE Usuarios SET nombre = ?, rol = ? WHERE identificacion = ?";

        try (Connection conn = DbHelper.getConnection();
             PreparedStatement pstmtUsuarios = conn.prepareStatement(sqlActualizarUsuarios)) {

            pstmtUsuarios.setString(1, nombre);
            pstmtUsuarios.setString(2, rol);
            pstmtUsuarios.setString(3, id);

            int filasActualizadas = pstmtUsuarios.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(String identificacion) {
        // Consultar el idCredenciales del usuario
        String sqlBuscarIdCredenciales = "SELECT idCredenciales FROM Usuarios WHERE identificacion = ?";
        String sqlEliminarUsuario = "DELETE FROM Usuarios WHERE identificacion = ?";
        String sqlEliminarCredenciales = "DELETE FROM Credenciales WHERE idCredenciales = ?";

        try (Connection conn = DbHelper.getConnection()) {
            // Comenzamos la transacción
            conn.setAutoCommit(false);

            // Buscar el idCredenciales del usuario
            int idCredenciales = -1;
            try (PreparedStatement pstmtBuscarId = conn.prepareStatement(sqlBuscarIdCredenciales)) {
                pstmtBuscarId.setString(1, identificacion);
                ResultSet rs = pstmtBuscarId.executeQuery();
                if (rs.next()) {
                    idCredenciales = rs.getInt("idCredenciales");
                } else {
                    // Si no se encuentra el usuario, lanzamos un error
                    return false;
                }
            }

            // Eliminar las credenciales del usuario
            try (PreparedStatement pstmtEliminarCredenciales = conn.prepareStatement(sqlEliminarCredenciales)) {
                pstmtEliminarCredenciales.setInt(1, idCredenciales);
                pstmtEliminarCredenciales.executeUpdate();
            }

            // Eliminar el usuario
            try (PreparedStatement pstmtEliminarUsuario = conn.prepareStatement(sqlEliminarUsuario)) {
                pstmtEliminarUsuario.setString(1, identificacion);
                int filasEliminadas = pstmtEliminarUsuario.executeUpdate();

                if (filasEliminadas > 0) {
                    // Si la eliminación del usuario fue exitosa, confirmamos la transacción
                    conn.commit();
                    return true;
                } else {
                    // Si no se eliminó el usuario, deshacemos la transacción
                    conn.rollback();
                    return false;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

}
