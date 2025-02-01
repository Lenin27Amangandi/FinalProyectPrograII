package BusinessLogic.services;

import DataAccess.DAO.UsuarioDAO;
import DataAccess.DTO.UsuarioDTO;
import DataAccess.IUsuarioDAO;

import java.util.List;

public class UsuarioService {

    private final IUsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO(); // Inicia la instancia de UsuarioDAO
    }

    /**
     * Inserta un nuevo usuario en la base de datos
     * @param usuarioDTO el objeto UsuarioDTO con los datos del usuario
     */
    public void insertarUsuario(UsuarioDTO usuarioDTO) {
        // Aquí puedes agregar validaciones o lógica adicional si es necesario
        usuarioDAO.insertarUsuario(usuarioDTO);
    }

    /**
     * Actualiza los datos de un usuario en la base de datos
     * @param usuarioDTO el objeto UsuarioDTO con los datos actualizados
     */
    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
        // Aquí puedes agregar validaciones o lógica adicional si es necesario
        usuarioDAO.actualizarUsuario(usuarioDTO);
    }

    /**
     * Elimina un usuario de la base de datos (cambia su estado a 'E' de eliminado)
     * @param idUsuario el ID del usuario que se eliminará
     */
    public void eliminarUsuario(int idUsuario) {
        // Lógica de negocio antes de eliminar, si es necesario
        usuarioDAO.eliminarUsuario(idUsuario);
    }

    /**
     * Recupera un usuario por su identificacion
     * @param identificacion la identificacion del usuario
     * @return un objeto UsuarioDTO con los datos del usuario
     */
    public UsuarioDTO obtenerUsuarioPorIdentificacion(String identificacion) {
        // Aquí puedes agregar lógica adicional o validaciones antes de obtener el usuario
        return usuarioDAO.obtenerUsuarioPorIdentificacion(identificacion);
    }

    /**
     * Recupera todos los usuarios activos
     * @return una lista de objetos UsuarioDTO
     */
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioDAO.obtenerTodosUsuarios();
    }

    /**
     * Recupera un usuario por su ID
     * @param idUsuario el ID del usuario
     * @return un objeto UsuarioDTO con los datos del usuario
     */
    public UsuarioDTO obtenerUsuarioPorId(int idUsuario) {
        return usuarioDAO.obtenerUsuarioPorId(idUsuario);
    }
}
