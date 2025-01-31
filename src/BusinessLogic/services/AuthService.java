package BusinessLogic.services;

import BusinessLogic.entities.Usuario;
import DataAccess.IUsuarioDAO;

public class AuthService {

    private IUsuarioDAO usuarioDAO;

    public AuthService(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean autenticarUsuario(String username, String password) {
        return usuarioDAO.verificarCredenciales(username, password);
    }

    public boolean verificarAcceso(String username, String rolRequerido) {
        // Aquí podrías agregar lógica más compleja para verificar roles y permisos
        Usuario usuario = usuarioDAO.buscarUsuario(username);
        return usuario != null && usuario.getRol().equals(rolRequerido);
    }
}
