package DataAccess;

import BusinessLogic.entities.*;
import java.util.List;

public interface IUsuarioDAO {
    
    // Agrega un nuevo usuario
    String agregarUsuario(String nombre, String rol, String identificacion, String username, String password);

    // Obtiene una lista de todos los usuarios
    List<Usuario> obtenerUsuarios();
    
    // Verifica las credenciales de un usuario
    boolean verificarCredenciales(String username, String password);
    
    // Verifica si un usuario existe por su ID
    boolean verificarCredencialesPorId(String id);

    // Inserta un nuevo usuario
    boolean insertarUsuario(Usuario usuario);

    // Busca un usuario por ID, nombre o username
    Usuario buscarUsuario(String criterio);

    // Modifica los detalles de un usuario existente
    boolean modificarUsuario(String id, String nombre, String rol, String username);

    // Elimina un usuario
    boolean eliminarUsuario(String identificacion);
}
