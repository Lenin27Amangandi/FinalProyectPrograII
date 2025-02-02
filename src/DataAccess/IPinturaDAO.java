package DataAccess;

import DataAccess.DTO.PinturaDTO;

import java.sql.SQLException;
import java.util.List;

public interface IPinturaDAO {

    void insertarPintura(PinturaDTO pintura) throws SQLException;

    void actualizarPintura(PinturaDTO pintura) throws SQLException;

    void eliminarPintura(int idPintura) throws SQLException;

    PinturaDTO obtenerPinturaPorCodigoBarras(String codigoBarras) throws SQLException;

    List<PinturaDTO> obtenerTodasLasPinturas() throws SQLException;

    PinturaDTO obtenerPinturaPorId(int idPintura) throws SQLException;
}
