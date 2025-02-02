package DataAccess.DAO;

import DataAccess.DataHelper.DbHelper;
import DataAccess.IUsuarioDAO;
import DataAccess.DTO.UsuarioDTO;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class UsuarioDAO extends DbHelper implements IUsuarioDAO {
    private static final String INSERT_USUARIO = "INSERT INTO Usuarios (nombre, identificacion, idCredenciales, idRol, estado, fechaCrea, fechaModifica) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_USUARIO = "UPDATE Usuarios SET estado = 'E', fechaModifica = ? WHERE idUsuarios = ?";
    private static final String SELECT_USUARIO_BY_ID = "SELECT * FROM Usuarios WHERE idUsuarios = ?";
    private static final String SELECT_USUARIO_BY_IDENTIFICACION = "SELECT * FROM Usuarios WHERE identificacion = ?";
    private static final String SELECT_ALL_USUARIOS = 
    "SELECT U.idUsuarios, U.nombre, U.identificacion, U.idCredenciales, U.estado, " +
    "U.fechaCrea, U.fechaModifica, R.nombreRol " +
    "FROM Usuarios U " +
    "JOIN Roles R ON U.idRol = R.idRol " +
    "WHERE U.estado != 'E'";
    private static final String SELECT_USUARIO_BY_CREDENCIALES = "SELECT * FROM Usuarios WHERE idCredenciales = ?";
    private static final String SELECT_ROLE_BY_NAME = "SELECT idRol FROM Roles WHERE nombreRol = ?";

    public UsuarioDAO() {
        super();
        verificarRolesPorDefecto();
        cargarUsuarioPorDefecto();
    }

    private void verificarRolesPorDefecto() {
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement psAdmin = connection.prepareStatement(SELECT_ROLE_BY_NAME)) {
            psAdmin.setString(1, "Administrador");
            ResultSet rsAdmin = psAdmin.executeQuery();
            if (!rsAdmin.next()) {
                try (PreparedStatement psInsert = connection.prepareStatement("INSERT INTO Roles (nombreRol) VALUES ('Administrador')")) {
                    psInsert.executeUpdate();
                }
            }
            psAdmin.setString(1, "Supervisor");
            ResultSet rsSupervisor = psAdmin.executeQuery();
            if (!rsSupervisor.next()) {
                try (PreparedStatement psInsert = connection.prepareStatement("INSERT INTO Roles (nombreRol) VALUES ('Supervisor')")) {
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarUsuarioPorDefecto() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/utils/Resources/config/config.properties")) {
            properties.load(fis);
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            String nombre = properties.getProperty("nombre");
            String rol = properties.getProperty("rol");
            String identificacion = properties.getProperty("identificacion");
            System.out.println("username: " + username);
            System.out.println("password: " + password);
            System.out.println("nombre: " + nombre);
            System.out.println("rol: " + rol);
            System.out.println("identificacion: " + identificacion);
            if (obtenerUsuarioPorIdentificacion(identificacion) == null) {
                insertarUsuarioDesdeConfig(username, password, nombre, rol, identificacion);
            } else {
                System.out.println("El usuario ya existe en la base de datos.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean verificarRolPorId(int idUsuario) {
        String query = "SELECT idRol FROM Usuarios WHERE idUsuarios = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idRol = rs.getInt("idRol");
                    return idRol == 1 || idRol == 2; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int obtenerIdPorUsuario(String username) {
        String query = "SELECT u.idUsuarios FROM Usuarios u JOIN Credenciales c ON u.idCredenciales = c.idCredenciales WHERE c.username = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idUsuarios");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }
    private void insertarUsuarioDesdeConfig(String username, String password, String nombre, String rol, String identificacion) {
        try (Connection connection = DbHelper.getConnection()) {
            if (existeUsername(connection, username)) {
                System.out.println("El username ya existe. No se insertará.");
                return; 
            }
            int idCredenciales = insertarCredenciales(username, password, connection);
            int idRol = obtenerRolId(rol, connection);
            String insertUsuario = "INSERT INTO Usuarios (nombre, identificacion, idCredenciales, idRol, estado, fechaCrea, fechaModifica) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psUsuario = connection.prepareStatement(insertUsuario)) {
                psUsuario.setString(1, nombre);
                psUsuario.setString(2, identificacion);
                psUsuario.setInt(3, idCredenciales); 
                psUsuario.setInt(4, idRol);           
                psUsuario.setString(5, "A");         
                psUsuario.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                psUsuario.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                psUsuario.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String obtenerNombreRolPorIdUsuario(int idUsuario) {
        String query = "SELECT R.nombreRol FROM Usuarios U " +
                       "JOIN Roles R ON U.idRol = R.idRol " +
                       "WHERE U.idUsuarios = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombreRol");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    private int insertarCredenciales(String username, String password, Connection connection) throws SQLException {
        String insertCredenciales = "INSERT INTO Credenciales (username, password) VALUES (?, ?)";
        try (PreparedStatement psCredenciales = connection.prepareStatement(insertCredenciales, Statement.RETURN_GENERATED_KEYS)) {
            psCredenciales.setString(1, username);
            psCredenciales.setString(2, password);
            psCredenciales.executeUpdate();
            ResultSet generatedKeys = psCredenciales.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); 
            }
        }
        return -1;  
    }
    public int obtenerRolId(String nombrerol, Connection connection) throws SQLException {
        String selectRol = "SELECT idRol FROM Roles WHERE nombreRol = ?";
        try (PreparedStatement ps = connection.prepareStatement(selectRol)) {
            ps.setString(1, nombrerol);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idRol");
            } else {
                String insertRol = "INSERT INTO Roles (nombreRol) VALUES (?)";
                try (PreparedStatement psInsert = connection.prepareStatement(insertRol, Statement.RETURN_GENERATED_KEYS)) {
                    psInsert.setString(1, nombrerol);
                    psInsert.executeUpdate();
                    ResultSet generatedKeys = psInsert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return -1;  
    }
    @Override
    public void insertarUsuario(UsuarioDTO usuario) {
        if (usuario.getEstado() == null) {
            usuario.setEstado("A");
        }
        try (Connection connection = DbHelper.getConnection()) {
            connection.setAutoCommit(false); 
            int idCredenciales = insertarCredenciales(usuario.getUsername(), usuario.getPassword(), connection);
            if (idCredenciales == -1) {
                throw new SQLException("Error al obtener idCredenciales.");
            }
            try (PreparedStatement ps = connection.prepareStatement(INSERT_USUARIO)) {
                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getIdentificacion());
                ps.setInt(3, idCredenciales);
                ps.setInt(4, usuario.getIdRol());
                ps.setString(5, usuario.getEstado());
                LocalDateTime fechaCrea = usuario.getFechaCrea() != null ? usuario.getFechaCrea() : LocalDateTime.now();
                LocalDateTime fechaModifica = usuario.getFechaModifica() != null ? usuario.getFechaModifica() : LocalDateTime.now();
                ps.setTimestamp(6, Timestamp.valueOf(fechaCrea));
                ps.setTimestamp(7, Timestamp.valueOf(fechaModifica));
                ps.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean existeIdentificacion(Connection connection, String identificacion) throws SQLException {
        String query = "SELECT COUNT(*) FROM Usuarios WHERE identificacion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, identificacion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        }
        return false;
    }    
    @Override
    public boolean actualizarUsuario(UsuarioDTO usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, idRol = ? WHERE identificacion = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setInt(2, usuario.getIdRol());
            ps.setString(3, usuario.getIdentificacion());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;  
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean eliminarUsuario(int idUsuario) {
        UsuarioDTO usuario = obtenerUsuarioPorId(idUsuario); 
        if (usuario == null) {
            return false;  
        }
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USUARIO)) {
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, idUsuario);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean desactivarUsuario(String identificacion) {
        String sql = "UPDATE usuarios SET estado = 'E' WHERE identificacion = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, identificacion);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<UsuarioDTO> obtenerTodosUsuarios() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USUARIOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setIdUsuarios(rs.getInt("idUsuarios"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setIdentificacion(rs.getString("identificacion"));
                usuario.setIdCredenciales(rs.getInt("idCredenciales"));
                usuario.setEstado(rs.getString("estado"));
                usuario.setFechaCrea(rs.getTimestamp("fechaCrea").toLocalDateTime());
                usuario.setFechaModifica(rs.getTimestamp("fechaModifica").toLocalDateTime());
                usuario.setNombreRol(rs.getString("nombreRol")); 
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    @Override
    public UsuarioDTO obtenerUsuarioPorId(int idUsuario) {
        UsuarioDTO usuario = null;
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USUARIO_BY_ID)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new UsuarioDTO();
                    usuario.setIdUsuarios(rs.getInt("idUsuarios"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setIdentificacion(rs.getString("identificacion"));
                    usuario.setIdCredenciales(rs.getInt("idCredenciales"));
                    usuario.setIdRol(rs.getInt("idRol"));
                    usuario.setEstado(rs.getString("estado"));
                    usuario.setFechaCrea(rs.getTimestamp("fechaCrea").toLocalDateTime());
                    usuario.setFechaModifica(rs.getTimestamp("fechaModifica").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    @Override
    public UsuarioDTO obtenerUsuarioPorIdentificacion(String identificacion) {
        UsuarioDTO usuario = null;
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USUARIO_BY_IDENTIFICACION)) {
            ps.setString(1, identificacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new UsuarioDTO();
                    usuario.setIdUsuarios(rs.getInt("idUsuarios"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setIdentificacion(rs.getString("identificacion"));
                    usuario.setIdCredenciales(rs.getInt("idCredenciales"));
                    usuario.setIdRol(rs.getInt("idRol"));
                    usuario.setEstado(rs.getString("estado"));
                    usuario.setFechaCrea(rs.getTimestamp("fechaCrea").toLocalDateTime());
                    usuario.setFechaModifica(rs.getTimestamp("fechaModifica").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    @Override
    public UsuarioDTO obtenerUsuarioPorCredenciales(int idCredenciales) {
        UsuarioDTO usuario = null;
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USUARIO_BY_CREDENCIALES)) {
            ps.setInt(1, idCredenciales);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new UsuarioDTO();
                    usuario.setIdUsuarios(rs.getInt("idUsuarios"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setIdentificacion(rs.getString("identificacion"));
                    usuario.setIdCredenciales(rs.getInt("idCredenciales"));
                    usuario.setIdRol(rs.getInt("idRol"));
                    usuario.setEstado(rs.getString("estado"));
                    usuario.setFechaCrea(rs.getTimestamp("fechaCrea").toLocalDateTime());
                    usuario.setFechaModifica(rs.getTimestamp("fechaModifica").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    public boolean verificarCredencialesYEstado(String username, String password) {
        String query = "SELECT u.estado FROM Credenciales c " +
        "JOIN Usuarios u ON c.idCredenciales = u.idCredenciales " + 
        "WHERE c.username = ? AND c.password = ?";
        try (Connection connection = DbHelper.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password); 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    String estado = rs.getString("estado");
                    return "A".equals(estado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
        public boolean verificarCredencialesPorIdentificacionYEstado(String identificacion) {
            String query = "SELECT COUNT(*) FROM Usuarios WHERE identificacion = ? AND estado = 'A'";
            try (Connection connection = DbHelper.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, identificacion);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    public boolean existeUsername(Connection connection, String username) throws SQLException {
        String selectUsername = "SELECT * FROM Credenciales WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(selectUsername)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    
}
