package DataAccess;

import BusinessLogic.entities.*;
import java.util.List;

public interface IPinturaDAO {

    /**
     * Inserta una nueva pintura en la base de datos.
     * 
     * @param pintura Objeto Pintura a insertar.
     * @return true si la pintura fue insertada correctamente, false en caso contrario.
     */
    boolean insertarPintura(Pintura pintura);

    /**
     * Actualiza una pintura existente en la base de datos.
     * 
     * @param pintura Objeto Pintura con los nuevos datos a actualizar.
     * @return true si la pintura fue actualizada correctamente, false en caso contrario.
     */
    boolean actualizarPintura(Pintura pintura);

    /**
     * Elimina una pintura de la base de datos utilizando su código de barras.
     * 
     * @param codigoBarras Código de barras de la pintura a eliminar.
     * @return true si la pintura fue eliminada correctamente, false en caso contrario.
     */
    boolean eliminarPintura(String codigoBarras);

    /**
     * Obtiene una pintura desde la base de datos utilizando su código de barras.
     * 
     * @param codigoBarras Código de barras de la pintura.
     * @return Objeto Pintura correspondiente al código de barras proporcionado.
     */
    Pintura obtenerPinturaPorCodigoBarras(String codigoBarras);

    /**
     * Obtiene todas las pinturas desde la base de datos.
     * 
     * @return Lista de objetos Pintura.
     */
    List<Pintura> obtenerTodasLasPinturas();
}
