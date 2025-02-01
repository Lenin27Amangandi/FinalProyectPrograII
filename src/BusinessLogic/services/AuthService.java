// package BusinessLogic.services;

// import BusinessLogic.entities.Usuario;
// import DataAccess.IUsuarioDAO;
// import java.util.Optional;

// public class AuthService {
//     private final IUsuarioDAO usuarioDAO;

//     public AuthService(IUsuarioDAO usuarioDAO) {
//         this.usuarioDAO = usuarioDAO;
//     }

//     /**
//      * Autentica a un usuario verificando sus credenciales (username y password).
//      *
//      * @param username Nombre de usuario.
//      * @param password Contraseña del usuario.
//      * @return `true` si las credenciales son válidas, `false` en caso contrario.
//      */
//     public boolean autenticarUsuario(String username, String password) {
//         if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
//             throw new IllegalArgumentException("El usuario y la contraseña no pueden estar vacíos.");
//         }

//         return usuarioDAO.verificarCredenciales(username, password);
//     }

//     /**
//      * Verifica si un usuario tiene acceso a un recurso basado en su rol.
//      *
//      * @param username     Nombre de usuario.
//      * @param rolRequerido Rol requerido para acceder al recurso.
//      * @return `true` si el usuario tiene el rol requerido, `false` en caso contrario.
//      */
//     public boolean verificarAcceso(String username, String rolRequerido) {
//         if (username == null || username.trim().isEmpty() || rolRequerido == null || rolRequerido.trim().isEmpty()) {
//             throw new IllegalArgumentException("Parámetros inválidos para verificar acceso.");
//         }

//         Optional<Usuario> usuarioOpt = obtenerUsuarioPorUsername(username);

//         return usuarioOpt
//                 .map(usuario -> {
//                     String rolUsuario = usuarioDAO.obtenerRolPorId(usuario.getIdRol());
//                     return rolUsuario.equalsIgnoreCase(rolRequerido);
//                 })
//                 .orElse(false);
//     }

//     /**
//      * Obtiene el nombre del rol de un usuario por su ID.
//      *
//      * @param idRol ID del rol.
//      * @return Nombre del rol.
//      */
//     public String obtenerRolPorId(int idRol) {
//         if (idRol <= 0) {
//             throw new IllegalArgumentException("ID de rol inválido.");
//         }
//         return usuarioDAO.obtenerRolPorId(idRol);
//     }

//     /**
//      * Obtiene un usuario por su nombre de usuario.
//      *
//      * @param username Nombre de usuario.
//      * @return Optional con el usuario si existe, o vacío si no se encuentra.
//      */
//     private Optional<Usuario> obtenerUsuarioPorUsername(String username) {
//         if (username == null || username.trim().isEmpty()) {
//             return Optional.empty();
//         }
//         // return usuarioDAO.buscarUsuario(username);
//     }
// }
