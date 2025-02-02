package DataAccess;

import DataAccess.DTO.UsuarioDTO;
import java.util.List;

public interface IUsuarioDAO {
    
    void insertarUsuario(UsuarioDTO usuario);
    
    boolean actualizarUsuario(UsuarioDTO usuario);
    
    boolean eliminarUsuario(int idUsuario);
    
    UsuarioDTO obtenerUsuarioPorIdentificacion(String identificacion);
    
    List<UsuarioDTO> obtenerTodosUsuarios();
    
    UsuarioDTO obtenerUsuarioPorId(int idUsuario);

    UsuarioDTO obtenerUsuarioPorCredenciales(int idCredenciales);
}
