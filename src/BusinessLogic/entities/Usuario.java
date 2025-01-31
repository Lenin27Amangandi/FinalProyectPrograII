package BusinessLogic.entities;

public class Usuario {
    private int    idUsuarios;
    private String nombre;
    private String rol;
    private String identificacion;
    private String username;
    private String password;
    


    /**
     * Constructor que representa un usuario en el sistema
     * @param idUsuarios
     * @param nombre
     * @param rol
     * @param username
     */
    public Usuario(int idUsuarios, String nombre, String rol, String identificacion, String username,String password) {
        this.idUsuarios = idUsuarios;
        this.nombre = nombre;
        this.rol = rol;
        this.identificacion = identificacion;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Getters y Setters de el constructor
     * @return
     */
    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
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

}
