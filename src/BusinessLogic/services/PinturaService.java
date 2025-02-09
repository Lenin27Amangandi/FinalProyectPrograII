package BusinessLogic.services;

import DataAccess.DAO.PinturaDAO;
import DataAccess.DTO.PinturaDTO;
import Framework.PinturaBLException;

import java.util.List;
import java.util.logging.Logger;

public class PinturaService {

    private static final Logger logger = Logger.getLogger(PinturaService.class.getName());
    private PinturaDAO pinturaDAO;

    public PinturaService() {
        this.pinturaDAO = new PinturaDAO();
    }

    public void insertarPintura(PinturaDTO pintura) {
        if (pintura == null || pintura.getTitulo() == null || pintura.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("El título de la pintura no puede estar vacío.");
        }
        try {
            pinturaDAO.insertarPintura(pintura);
        } catch (PinturaBLException e) {
            logger.severe("Error al insertar pintura: " + e.getMessage());
            throw new PinturaBLException("Error al insertar pintura.", e);
        }
    }

    public void actualizarPintura(PinturaDTO pintura) {
        if (pintura == null || pintura.getIdPintura() <= 0) {
            throw new IllegalArgumentException("ID de pintura no válido.");
        }
        try {
            pinturaDAO.actualizarPintura(pintura);
        } catch (PinturaBLException e) {
            logger.severe("Error al actualizar pintura: " + e.getMessage());
            throw new PinturaBLException("Error al actualizar pintura.", e);
        }
    }

    public void eliminarPintura(int idPintura) {
        if (idPintura <= 0) {
            throw new IllegalArgumentException("ID de pintura no válido.");
        }
        try {
            pinturaDAO.eliminarPintura(idPintura);
        } catch (PinturaBLException e) {
            logger.severe("Error al eliminar pintura: " + e.getMessage());
            throw new PinturaBLException("Error al eliminar pintura.", e);
        }
    }

    public PinturaDTO obtenerPinturaPorCodigoBarras(String codigoBarras) {
        if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de barras no puede estar vacío.");
        }
        try {
            return pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
        } catch (PinturaBLException e) {
            logger.severe("Error al obtener pintura por código de barras: " + e.getMessage());
            throw new PinturaBLException("Error al obtener pintura.", e);
        }
    }

    public List<PinturaDTO> obtenerTodasLasPinturas() {
        try {
            return pinturaDAO.obtenerTodasLasPinturas();
        } catch (PinturaBLException e) {
            logger.severe("Error al obtener todas las pinturas: " + e.getMessage());
            throw new PinturaBLException("Error al obtener todas las pinturas.", e);
        }
    }

    public PinturaDTO obtenerPinturaPorId(int idPintura) {
        if (idPintura <= 0) {
            throw new IllegalArgumentException("ID de pintura no válido.");
        }
        try {
            return pinturaDAO.obtenerPinturaPorId(idPintura);
        } catch (PinturaBLException e) {
            logger.severe("Error al obtener pintura por ID: " + e.getMessage());
            throw new PinturaBLException("Error al obtener pintura.", e);
        }
    }
}
