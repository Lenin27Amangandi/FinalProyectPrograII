package DataAccess;

import DataAccess.DTO.UsuarioDTO;
import java.util.List;

public interface IUsuarioDAO {
    
    // Inserta un nuevo usuario en la base de datos
    void insertarUsuario(UsuarioDTO usuario);
    
    // Actualiza los datos de un usuario existente
    boolean actualizarUsuario(UsuarioDTO usuario);
    
    // Elimina un usuario (cambia su estado a "E" de eliminado)
    boolean eliminarUsuario(int idUsuario);
    
    // Obtiene un usuario por su identificacion
    UsuarioDTO obtenerUsuarioPorIdentificacion(String identificacion);
    
    // Obtiene todos los usuarios activos
    List<UsuarioDTO> obtenerTodosUsuarios();
    
    // Obtiene un usuario por su ID
    UsuarioDTO obtenerUsuarioPorId(int idUsuario);

    UsuarioDTO obtenerUsuarioPorCredenciales(int idCredenciales);
}
