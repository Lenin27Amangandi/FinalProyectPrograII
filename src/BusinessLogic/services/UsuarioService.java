package BusinessLogic.services;

import DataAccess.IUsuarioDAO;
import DataAccess.DAO.UsuarioDAO;
import DataAccess.DTO.UsuarioDTO;
import Framework.UsuarioException;

import java.util.List;

public class UsuarioService {

    private final IUsuarioDAO usuarioDAO;

    // Se inyecta la dependencia desde afuera en lugar de crearla internamente
    public UsuarioService(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public UsuarioService() {
        this(new UsuarioDAO());
    }

    public void insertarUsuario(UsuarioDTO usuarioDTO) {
        try {
            validarUsuario(usuarioDTO);
            usuarioDAO.insertarUsuario(usuarioDTO);
        } catch (Exception e) {
            throw new Framework.UsuarioException("Error al insertar usuario.", e);
        }
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        try {
            return usuarioDAO.obtenerTodosUsuarios();
        } catch (Exception e) {
            throw new Framework.UsuarioException("Error al obtener la lista de usuarios.", e);
        }
    }

    private void validarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            throw new Framework.UsuarioException("El usuario no puede ser nulo.");
        }
        if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().trim().isEmpty()) {
            throw new Framework.UsuarioException("El nombre del usuario no puede estar vacío.");
        }
        if (usuarioDTO.getIdentificacion() == null || usuarioDTO.getIdentificacion().length() != 13) {
            throw new Framework.UsuarioException("La identificación debe tener 13 dígitos.");
        }
    }
}
