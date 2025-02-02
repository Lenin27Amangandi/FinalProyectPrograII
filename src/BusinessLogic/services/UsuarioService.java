package BusinessLogic.services;

import DataAccess.DAO.UsuarioDAO;
import DataAccess.DTO.UsuarioDTO;
import DataAccess.IUsuarioDAO;
import java.util.List;

public class UsuarioService {

    private final IUsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void insertarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO == null || usuarioDTO.getNombre().isEmpty()) {
                throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
            }
            if (usuarioDTO.getIdentificacion().length() != 13) {
                throw new IllegalArgumentException("La identificación debe tener 13 dígitos.");
            }
            usuarioDAO.insertarUsuario(usuarioDTO);
        } catch (Exception e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            throw new RuntimeException("Error al insertar usuario.", e);
        }
    }

    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO == null || usuarioDTO.getIdUsuarios() <= 0) {
                throw new IllegalArgumentException("ID de usuario no válido.");
            }
            usuarioDAO.actualizarUsuario(usuarioDTO);
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            throw new RuntimeException("Error al actualizar usuario.", e);
        }
    }

    public void eliminarUsuario(int idUsuario) {
        try {
            if (idUsuario <= 0) {
                throw new IllegalArgumentException("ID de usuario no válido.");
            }
            usuarioDAO.eliminarUsuario(idUsuario);
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            throw new RuntimeException("Error al eliminar usuario.", e);
        }
    }

    public UsuarioDTO obtenerUsuarioPorIdentificacion(String identificacion) {
        try {
            if (identificacion == null || identificacion.trim().isEmpty()) {
                throw new IllegalArgumentException("La identificación no puede estar vacía.");
            }
            return usuarioDAO.obtenerUsuarioPorIdentificacion(identificacion);
        } catch (Exception e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            throw new RuntimeException("Error al obtener usuario.", e);
        }
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        try {
            return usuarioDAO.obtenerTodosUsuarios();
        } catch (Exception e) {
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
            throw new RuntimeException("Error al obtener usuarios.", e);
        }
    }

    public UsuarioDTO obtenerUsuarioPorId(int idUsuario) {
        try {
            if (idUsuario <= 0) {
                throw new IllegalArgumentException("ID de usuario no válido.");
            }
            return usuarioDAO.obtenerUsuarioPorId(idUsuario);
        } catch (Exception e) {
            System.err.println("Error al obtener usuario por ID: " + e.getMessage());
            throw new RuntimeException("Error al obtener usuario por ID.", e);
        }
    }
}
