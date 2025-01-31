package DataAccess.DAO;

import DataAccess.IPinturaDAO;
import DataAccess.DataHelper.DbHelper;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import BusinessLogic.entities.Pintura;

public class PinturaDAO extends DbHelper implements IPinturaDAO {

    public PinturaDAO(){
        super();
    }

    @Override
    public boolean actualizarPintura(Pintura pintura) {
        String query = "UPDATE Pinturas SET titulo = ?, autor = ?, anio = ?, descripcion = ?, categoria = ?, ubicacion = ?, imagen = ? WHERE codigoBarras = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
        
            stmt.setString(1, pintura.getTitulo());     // Título
            stmt.setString(2, pintura.getAutor());      // Autor
            stmt.setInt(3, pintura.getAnio());          // Año
            stmt.setString(4, pintura.getDescripcion()); // Descripción
            stmt.setString(5, pintura.getCategoria());  // Categoría
            stmt.setString(6, pintura.getUbicacion());  // Ubicación
            stmt.setString(7, pintura.getImagen());     // Imagen
            stmt.setString(8, pintura.getCodigoBarras());  // Código de barras como clave
        
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la pintura: " + e.getMessage());
            return false;
        }
    }
    

    @Override
    public boolean eliminarPintura(String codigoBarras) {
        String query = "DELETE FROM pinturas WHERE codigobarras = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, codigoBarras);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la pintura: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertarPintura(Pintura pintura) {
        String sql = "INSERT INTO Pinturas (titulo, autor, anio, descripcion, codigoBarras, categoria, ubicacion, imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        File imagenFile = new File(pintura.getImagen());
        if (!imagenFile.exists()) {
            System.err.println("La imagen no existe en la ruta especificada.");
            return false;
        }
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        
                stmt.setString(1, pintura.getTitulo());     // Título
                stmt.setString(2, pintura.getAutor());      // Autor
                stmt.setInt(3, pintura.getAnio());          // Año
                stmt.setString(4, pintura.getDescripcion()); // Descripción
                stmt.setString(5, pintura.getCodigoBarras()); // Código de barras
                stmt.setString(6, pintura.getCategoria());  // Categoría
                stmt.setString(7, pintura.getUbicacion());  // Ubicación
                stmt.setString(8, pintura.getImagen());     // Imagen
                
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Pintura insertada correctamente.");
                return true;
            } else {
                System.err.println("No se pudo insertar la pintura.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar la pintura: " + e.getMessage());
            return false;
        }
    }


    @Override
    public Pintura obtenerPinturaPorCodigoBarras(String codigoBarras) {
        String query = "SELECT titulo, autor, anio, descripcion, codigobarras, categoria, ubicacion, imagen FROM Pinturas WHERE codigobarras = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, codigoBarras);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pintura(
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("anio"),
                    rs.getString("descripcion"),
                    rs.getString("codigobarras"),
                    rs.getString("categoria"),
                    rs.getString("ubicacion"),
                    rs.getString("imagen")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pintura por código de barras: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pintura> obtenerTodasLasPinturas() {
        List<Pintura> pinturas = new ArrayList<>();
        String query = "SELECT titulo, autor, anio, descripcion, codigoBarras, categoria, ubicacion, imagen FROM Pinturas";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pintura pintura = new Pintura();
                pintura.setTitulo(rs.getString("titulo"));
                pintura.setAutor(rs.getString("autor"));
                pintura.setAnio(rs.getInt("anio"));
                pintura.setDescripcion(rs.getString("descripcion"));
                pintura.setCodigoBarras(rs.getString("codigoBarras"));
                pintura.setCategoria(rs.getString("categoria"));
                pintura.setUbicacion(rs.getString("ubicacion"));
                pintura.setImagen(rs.getString("imagen"));

                pinturas.add(pintura);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las pinturas: " + e.getMessage());
        }

        return pinturas;
    }

    
}
