package DataAccess.DTO;

import java.time.LocalDateTime;

public class UsuarioDTO {
    private int idUsuarios;           
    private String nombre;            
    private String identificacion;    
    private int idCredenciales;       
    private int idRol;                 
    private String nombreRol;
    private String username;
    private String password;

    private String estado;             

    public UsuarioDTO() {}

    /**
     * Constructor que inicializa todos los atributos del usuario
     * @param idUsuarios
     * @param nombre
     * @param identificacion
     * @param idCredenciales
     * @param idRol
     * @param estado
     * @param fechaCrea
     * @param fechaModifica
     */
    public UsuarioDTO(int idUsuarios, String nombre, String identificacion, int idCredenciales, int idRol, String nombreRol, String username, String estado, LocalDateTime fechaCrea, LocalDateTime fechaModifica) {
        this.idUsuarios = idUsuarios;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.idCredenciales = idCredenciales;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.username = username;
        this.estado = estado;
       
    }

    // Getters y Setters

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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public int getIdCredenciales() {
        return idCredenciales;
    }

    public void setIdCredenciales(int idCredenciales) {
        this.idCredenciales = idCredenciales;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuarios=" + idUsuarios +
                ", nombre='" + nombre + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", idCredenciales=" + idCredenciales +
                ", idRol=" + idRol +
                ", estado='" + estado + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
