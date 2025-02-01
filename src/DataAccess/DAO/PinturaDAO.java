package DataAccess.DAO;

import DataAccess.DataHelper.DbHelper;
import DataAccess.IPinturaDAO;
import DataAccess.DTO.PinturaDTO;

import java.awt.HeadlessException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PinturaDAO extends DbHelper implements IPinturaDAO {
    
    private static final String INSERT_PINTURA = "INSERT INTO Pinturas (titulo, anio, descripcion, codigoBarras, idCategoria, idAutor, idSala, imagen, estado, fechaCrea, fechaModifica) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_AUTOR_ID = "SELECT idAutor FROM Autores WHERE nombreAutor = ?";
    private static final String SELECT_CATEGORIA_ID = "SELECT idCategoria FROM Categorias WHERE categoria = ?";
    private static final String SELECT_SALA_ID = "SELECT idSala FROM Salas WHERE Salas = ?";
    private static final String UPDATE_PINTURA = "UPDATE Pinturas SET titulo = ?, anio = ?, descripcion = ?, codigoBarras = ?, idCategoria = ?, idAutor = ?, idSala = ?, imagen = ?, estado = ?, fechaModifica = ? WHERE idPintura = ?";
    private static final String DELETE_PINTURA = "UPDATE Pinturas SET estado = 'E', fechaModifica = ? WHERE idPintura = ?";
    private static final String SELECT_PINTURA_BY_CODIGO = "SELECT * FROM Pinturas WHERE codigoBarras = ?";
    private static final String SELECT_ALL_PINTURAS = "SELECT * FROM Pinturas WHERE estado != 'E'";
    private static final String SELECT_PINTURA_BY_ID = "SELECT * FROM Pinturas WHERE idPintura = ?";
    private static final String ACTUALIZAR_ESTADO_PINTURA = 
        "UPDATE Pinturas SET estado = ? WHERE idPintura = ?";
    
    
    public PinturaDAO() {
        super();
    }

    @Override
    public void insertarPintura(PinturaDTO pintura) {
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
            ps.setTimestamp(10, Timestamp.valueOf(pintura.getFechaCrea()));
            ps.setTimestamp(11, Timestamp.valueOf(pintura.getFechaModifica()));
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validarIdExistente(String autor, String categoria, String sala) throws HeadlessException, SQLException {
        // Verificar si el autor existe
        if (!existeAutor(autor)) {
            JOptionPane.showMessageDialog(null, "El autor no existe en la base de datos.");
            return false;
        }
        // Verificar si la categoría existe
        if (!existeCategoria(categoria)) {
            JOptionPane.showMessageDialog(null, "La categoría no existe en la base de datos.");
            return false;
        }
        // Verificar si la sección existe
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

    @Override
    public void actualizarPintura(PinturaDTO pintura) {
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
            ps.setTimestamp(10, Timestamp.valueOf(pintura.getFechaModifica()));
            ps.setInt(11, pintura.getIdPintura());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEstadoPintura(int idPintura, String estado) throws SQLException {
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(ACTUALIZAR_ESTADO_PINTURA)) {

            // Establecemos los parámetros
            ps.setString(1, estado);         // El nuevo estado ("E" en este caso)
            ps.setInt(2, idPintura);         // El ID de la pintura que queremos actualizar

            // Ejecutamos la actualización
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado de la pintura: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarPintura(int idPintura) {
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_PINTURA)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, idPintura);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PinturaDTO obtenerPinturaPorId(int idPintura) {
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_PINTURA_BY_ID)) {
            
            ps.setInt(1, idPintura);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToPinturaDTO(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PinturaDTO obtenerPinturaPorCodigoBarras(String codigoBarras) {
        try (Connection connection = DbHelper.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(SELECT_PINTURA_BY_CODIGO)) {
            ps.setString(1, codigoBarras);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToPinturaDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PinturaDTO> obtenerTodasLasPinturas() {
        List<PinturaDTO> pinturas = new ArrayList<>();
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PINTURAS);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                pinturas.add(mapResultSetToPinturaDTO(rs));
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
        pintura.setFechaCrea(rs.getTimestamp("fechaCrea").toLocalDateTime());
        pintura.setFechaModifica(rs.getTimestamp("fechaModifica").toLocalDateTime());
        return pintura;
    }
}
