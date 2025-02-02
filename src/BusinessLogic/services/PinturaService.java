package BusinessLogic.services;

import DataAccess.DAO.PinturaDAO;
import DataAccess.DTO.PinturaDTO;
import java.sql.SQLException;
import java.util.List;

public class PinturaService {

    private PinturaDAO pinturaDAO;

    public PinturaService() {
        this.pinturaDAO = new PinturaDAO();
    }

    public void insertarPintura(PinturaDTO pintura) {
        try {
            if (pintura == null || pintura.getTitulo() == null || pintura.getTitulo().isEmpty()) {
                throw new IllegalArgumentException("El título de la pintura no puede estar vacío.");
            }
            pinturaDAO.insertarPintura(pintura);
        } catch (SQLException e) {
            System.err.println("Error al insertar pintura: " + e.getMessage());
            throw new RuntimeException("Error al insertar pintura.", e); 
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            throw e;  
        }
    }

    public void actualizarPintura(PinturaDTO pintura) {
        try {
            if (pintura == null || pintura.getIdPintura() <= 0) {
                throw new IllegalArgumentException("ID de pintura no válido.");
            }
            pinturaDAO.actualizarPintura(pintura);
        } catch (SQLException e) {
            System.err.println("Error al actualizar pintura: " + e.getMessage());
            throw new RuntimeException("Error al actualizar pintura.", e);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            throw e;
        }
    }

    public void eliminarPintura(int idPintura) {
        try {
            if (idPintura <= 0) {
                throw new IllegalArgumentException("ID de pintura no válido.");
            }
            pinturaDAO.eliminarPintura(idPintura);
        } catch (SQLException e) {
            System.err.println("Error al eliminar pintura: " + e.getMessage());
            throw new RuntimeException("Error al eliminar pintura.", e);
        }
    }

    public PinturaDTO obtenerPinturaPorCodigoBarras(String codigoBarras) {
        try {
            if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
                throw new IllegalArgumentException("El código de barras no puede estar vacío.");
            }
            return pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
        } catch (SQLException e) {
            System.err.println("Error al obtener pintura por código de barras: " + e.getMessage());
            throw new RuntimeException("Error al obtener pintura.", e);
        }
    }

    public List<PinturaDTO> obtenerTodasLasPinturas() {
        try {
            return pinturaDAO.obtenerTodasLasPinturas();
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las pinturas: " + e.getMessage());
            throw new RuntimeException("Error al obtener todas las pinturas.", e);
        }
    }

    public PinturaDTO obtenerPinturaPorId(int idPintura) {
        try {
            if (idPintura <= 0) {
                throw new IllegalArgumentException("ID de pintura no válido.");
            }
            return pinturaDAO.obtenerPinturaPorId(idPintura);
        } catch (SQLException e) {
            System.err.println("Error al obtener pintura por ID: " + e.getMessage());
            throw new RuntimeException("Error al obtener pintura.", e);
        }
    }
}
