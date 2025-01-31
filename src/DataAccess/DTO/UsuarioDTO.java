package DataAccess.DTO;

public class UsuarioDTO {

    private int idUsuario;
    private String nombre;
    private String rol;
    private String identificacion;
    private String username;
    private String password;  // Aunque no es recomendable transferir contraseñas en texto claro, lo dejo por ahora para el ejemplo.

    // Constructor
    public UsuarioDTO(int idUsuario, String nombre, String rol, String identificacion, String username, String password) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
        this.identificacion = identificacion;
        this.username = username;
        this.password = password;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
