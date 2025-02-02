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

    // Insertar una pintura
    public void insertarPintura(PinturaDTO pintura) throws SQLException {
        // Aquí puedes agregar validaciones o lógica adicional si es necesario
        if (pintura != null) {
            pinturaDAO.insertarPintura(pintura);
        }
    }

    // Actualizar una pintura
    public void actualizarPintura(PinturaDTO pintura) {
        // Aquí puedes agregar validaciones o lógica adicional si es necesario
        if (pintura != null) {
            pinturaDAO.actualizarPintura(pintura);
        }
    }

    // Eliminar una pintura (cambia su estado a 'E')
    public void eliminarPintura(int idPintura) {
        // Validar o realizar alguna acción antes de eliminar
        pinturaDAO.eliminarPintura(idPintura);
    }

    // Obtener una pintura por su código de barras
    public PinturaDTO obtenerPinturaPorCodigoBarras(String codigoBarras) {
        return pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
    }

    // Obtener todas las pinturas
    public List<PinturaDTO> obtenerTodasLasPinturas() {
        return pinturaDAO.obtenerTodasLasPinturas();
    }

    // Obtener una pintura por su ID
    public PinturaDTO obtenerPinturaPorId(int idPintura) {
        return pinturaDAO.obtenerPinturaPorId(idPintura);
    }

}
