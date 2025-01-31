package BusinessLogic.services;

import BusinessLogic.entities.Usuario;
import DataAccess.IUsuarioDAO;

public class UsuarioService {

    private IUsuarioDAO usuarioDAO;

    public UsuarioService(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public String agregarUsuario(String nombre, String rol, String identificacion, String username, String password) {
        // Validaciones de negocio
        if (nombre == null || nombre.isEmpty()) {
            return "El nombre es obligatorio.";
        }
        if (rol == null || rol.isEmpty()) {
            return "El rol es obligatorio.";
        }
        
        // Llamada a la capa de acceso a datos
        return usuarioDAO.agregarUsuario(nombre, rol, identificacion, username, password);
    }

    public boolean modificarUsuario(String id, String nombre, String rol, String username) {
        // Lógica adicional de negocio
        if (id == null || id.isEmpty()) {
            return false;
        }
        
        return usuarioDAO.modificarUsuario(id, nombre, rol, username);
    }

    public Usuario obtenerUsuarioPorId(String id) {
        return usuarioDAO.buscarUsuario(id);
    }
}
