package DataAccess;

import DataAccess.DTO.PinturaDTO;

import java.sql.SQLException;
import java.util.List;

public interface IPinturaDAO {

    // Insertar una nueva pintura
    void insertarPintura(PinturaDTO pintura) throws SQLException;

    // Actualizar los datos de una pintura
    void actualizarPintura(PinturaDTO pintura);

    // Eliminar una pintura (actualiza su estado a 'E')
    void eliminarPintura(int idPintura);

    // Obtener pintura por código de barras
    PinturaDTO obtenerPinturaPorCodigoBarras(String codigoBarras);

    // Obtener todas las pinturas
    List<PinturaDTO> obtenerTodasLasPinturas();

    // Obtener una pintura por su ID
    PinturaDTO obtenerPinturaPorId(int idPintura);
}
