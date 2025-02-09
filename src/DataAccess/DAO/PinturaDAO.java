package DataAccess.DAO;

import DataAccess.DataHelper.DbHelper;
import Framework.PinturaBLException;
import DataAccess.IPinturaDAO;
import DataAccess.DTO.PinturaDTO;

import java.awt.HeadlessException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PinturaDAO extends DbHelper implements IPinturaDAO {
    
    private static final String INSERT_PINTURA = "INSERT INTO Pinturas (titulo, anio, descripcion, codigoBarras, idCategoria, idAutor, idSala, imagen, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_AUTOR_ID = "SELECT idAutor FROM Autores WHERE nombreAutor = ?";
    private static final String SELECT_CATEGORIA_ID = "SELECT idCategoria FROM Categorias WHERE categoria = ?";
    private static final String SELECT_SALA_ID = "SELECT idSala FROM Salas WHERE Salas = ?";
    private static final String UPDATE_PINTURA = "UPDATE Pinturas SET titulo = ?, anio = ?, descripcion = ?, codigoBarras = ?, idCategoria = ?, idAutor = ?, idSala = ?, imagen = ?, estado = ? WHERE idPintura = ?";
    private static final String DELETE_PINTURA = "UPDATE Pinturas SET estado = 'E' WHERE idPintura = ?";
    private static final String SELECT_ALL_PINTURAS = "SELECT * FROM Pinturas WHERE estado != 'E'";
    private static final String SELECT_PINTURA_BY_ID = "SELECT * FROM Pinturas WHERE idPintura = ?";
    private static final String ACTUALIZAR_ESTADO_PINTURA = 
        "UPDATE Pinturas SET estado = ? WHERE idPintura = ?";
    
    
    public PinturaDAO() {
        super();
    }

    @Override
    public void insertarPintura(PinturaDTO pintura) throws PinturaBLException {
        try (Connection connection = DbHelper.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_PINTURA)) {
            
            ps.setString(1, pintura.getTitulo());
            ps.setInt(2, pintura.getAnio());
            ps.setString(3, pintura.getDescripcion());
            ps.setString(4, pintura.getCodigoBarras());
            ps.setInt(5, pintura.getIdCategoria());
            ps.setInt(6, pintura.getIdAutor());
            ps.setInt(7, pintura.getIdSala());
            ps.setString(8, pintura.getImagen());
            ps.setString(9, pintura.getEstado());

            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new PinturaBLException("Error al insertar pintura en la base de datos.", e);
        }
    }

    @Override
    public void actualizarPintura(PinturaDTO pintura) throws PinturaBLException {
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_PINTURA)) {
            
            ps.setString(1, pintura.getTitulo());
            ps.setInt(2, pintura.getAnio());
            ps.setString(3, pintura.getDescripcion());
            ps.setString(4, pintura.getCodigoBarras());
            ps.setInt(5, pintura.getIdCategoria());
            ps.setInt(6, pintura.getIdAutor());
            ps.setInt(7, pintura.getIdSala());
            ps.setString(8, pintura.getImagen());
            ps.setString(9, pintura.getEstado());
            ps.setInt(10, pintura.getIdPintura());
    
            ps.executeUpdate();
    
        } catch (SQLException e) {
            throw new PinturaBLException("Error al actualizar pintura en la base de datos.", e);
        }
    }

    @Override
    public void eliminarPintura(int idPintura) throws PinturaBLException {
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_PINTURA)) {
            
            ps.setInt(1, idPintura);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new PinturaBLException("Error al insertar pintura en la base de datos.", e);
        }
    }

    @Override
    public PinturaDTO obtenerPinturaPorId(int idPintura) throws PinturaBLException {
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_PINTURA_BY_ID)) {
            
            ps.setInt(1, idPintura);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToPinturaDTO(rs);
            }
            
        } catch (SQLException e) {
            throw new PinturaBLException("Error al insertar pintura en la base de datos.", e);
        }
        return null;
    }

    @Override
    public PinturaDTO obtenerPinturaPorCodigoBarras(String codigoBarras) throws PinturaBLException{
        String query = "SELECT p.idPintura, p.titulo, p.anio, p.descripcion, p.codigoBarras, " +
                       "p.idCategoria, p.idAutor, p.idSala, p.imagen, p.estado, " +
                       "a.nombreAutor, c.categoria, s.Salas " +
                       "FROM Pinturas p " +
                       "JOIN Autores a ON p.idAutor = a.idAutor " +
                       "JOIN Categorias c ON p.idCategoria = c.idCategoria " +
                       "JOIN Salas s ON p.idSala = s.idSala " +
                       "WHERE p.codigoBarras = ? AND p.estado = 'A'";  
    
        try (Connection connection = DbHelper.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, codigoBarras);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToPinturaDTO(rs);
            }
        } catch (SQLException e) {
            throw new PinturaBLException("Error al insertar pintura en la base de datos.", e);
        }
        return null;
    }
    

    @Override
    public List<PinturaDTO> obtenerTodasLasPinturas() throws PinturaBLException {
        List<PinturaDTO> pinturas = new ArrayList<>();
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PINTURAS);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                pinturas.add(mapResultSetToPinturaDTO(rs));
            }
            
        } catch (SQLException e) {
            throw new PinturaBLException("Error al insertar pintura en la base de datos.", e);
        }
        return pinturas;
    }
    
    public boolean validarIdExistente(String autor, String categoria, String sala) throws HeadlessException, SQLException {
        if (!existeAutor(autor)) {
            JOptionPane.showMessageDialog(null, "El autor no existe en la base de datos.");
            return false;
        }
        if (!existeCategoria(categoria)) {
            JOptionPane.showMessageDialog(null, "La categoría no existe en la base de datos.");
            return false;
        }
        if (!existeSala(sala)) {
            JOptionPane.showMessageDialog(null, "La sección no existe en la base de datos.");
            return false;
        }
        return true;
    }
    
    private boolean existeAutor(String autor) throws SQLException {
        return obtenerId(SELECT_AUTOR_ID, autor) != -1;
    }
    
    private boolean existeCategoria(String categoria) throws SQLException {
        return obtenerId(SELECT_CATEGORIA_ID, categoria) != -1;
    }
    
    private boolean existeSala(String sala) throws SQLException {
        return obtenerId(SELECT_SALA_ID, sala) != -1;
    }

    private int obtenerId(String selectSQL, String valor) throws SQLException {
        try (PreparedStatement ps = DbHelper.getConnection().prepareStatement(selectSQL)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public void actualizarEstadoPintura(int idPintura, String estado) throws SQLException {
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(ACTUALIZAR_ESTADO_PINTURA)) {

            ps.setString(1, estado);        
            ps.setInt(2, idPintura);        

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la pintura: " + e.getMessage(), e);
        }
    }

    public List<PinturaDTO> obtenerPinturasResumen() {
        List<PinturaDTO> pinturas = new ArrayList<>();
        String query = "SELECT * FROM pinturas WHERE estado != 'E'";  
    
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
    
            while (rs.next()) {
                PinturaDTO pintura = new PinturaDTO();
                pintura.setCodigoBarras(rs.getString("codigoBarras"));
                pintura.setTitulo(rs.getString("titulo"));
                pintura.setIdAutor(rs.getInt("idAutor"));
                pintura.setAnio(rs.getInt("anio"));
                pinturas.add(pintura);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pinturas;
    }

    private PinturaDTO mapResultSetToPinturaDTO(ResultSet rs) throws SQLException {
        PinturaDTO pintura = new PinturaDTO();
        pintura.setIdPintura(rs.getInt("idPintura"));
        pintura.setTitulo(rs.getString("titulo"));
        pintura.setAnio(rs.getInt("anio"));
        pintura.setDescripcion(rs.getString("descripcion"));
        pintura.setCodigoBarras(rs.getString("codigoBarras"));
        pintura.setIdCategoria(rs.getInt("idCategoria"));
        pintura.setIdAutor(rs.getInt("idAutor"));
        pintura.setIdSala(rs.getInt("idSala"));
        pintura.setImagen(rs.getString("imagen"));
        pintura.setEstado(rs.getString("estado"));
        
        pintura.setNombreAutor(rs.getString("nombreAutor"));  
        pintura.setcategoria(rs.getString("categoria"));     
        pintura.setSalas(rs.getString("Salas"));         
    
        return pintura;
    }

    public int obtenerIdAutorPorNombre(String nombreAutor) throws SQLException {
        String sql = "SELECT idAutor FROM Autores WHERE nombreAutor = ?";
        try (Connection connection = DbHelper.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreAutor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idAutor");  
            } else {
                String insertSql = "INSERT INTO Autores (nombreAutor) VALUES (?)";
                try (PreparedStatement insertPs = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertPs.setString(1, nombreAutor);
                    insertPs.executeUpdate();
                    ResultSet generatedKeys = insertPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); 
                    }
                }
            }
        }
        return -1; 
    }

    public int obtenerIdCategoriaPorNombre(String nombreCategoria) throws SQLException {
        String sql = "SELECT idCategoria FROM Categorias WHERE categoria = ?";
        try (Connection connection = DbHelper.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreCategoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idCategoria");  
            } else {
                String insertSql = "INSERT INTO Categorias (categoria) VALUES (?)";
                try (PreparedStatement insertPs = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertPs.setString(1, nombreCategoria);
                    insertPs.executeUpdate();
                    ResultSet generatedKeys = insertPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  
                    }
                }
            }
        }
        return -1;  
    }
    public int obtenerIdSalaPorNombre(String nombreSala) throws SQLException {
        String sql = "SELECT idSala FROM Salas WHERE Salas = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreSala);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idSala"); 
            } else {
                String insertSql = "INSERT INTO Salas (Salas) VALUES (?)";
                try (PreparedStatement insertPs = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertPs.setString(1, nombreSala);
                    insertPs.executeUpdate();
                    ResultSet generatedKeys = insertPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); 
                    }
                }
            }
        }
        return -1; 
    }

    public String obtenerNombreAutorPorId(int idAutor) {
        String sql = "SELECT nombreAutor FROM Autores WHERE idAutor = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAutor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nombreAutor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String obtenerNombreCategoriaPorId(int idCategoria) {
        String sql = "SELECT categoria FROM Categorias WHERE idCategoria = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("categoria");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String obtenerNombreSalaPorId(int idSala) {
        String sql = "SELECT Salas FROM Salas WHERE idSala = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSala);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Salas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}