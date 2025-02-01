package BusinessLogic.entities;

import java.time.LocalDateTime;

public class Usuario {
    private int idUsuarios;            // ID del usuario (autoincremental)
    private String nombre;             // Nombre del usuario
    private String identificacion;     // Identificación única del usuario
    private int idCredenciales;        // ID de las credenciales (relacionado con la tabla Credenciales)
    private int idRol;                 // ID del rol (relacionado con la tabla Roles)
    private String estado;             // Estado del usuario (activo, inactivo, eliminado)
    private LocalDateTime fechaCrea;   // Fecha de creación del registro
    private LocalDateTime fechaModifica; // Fecha de última modificación del registro

    public Usuario() {}

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
    public Usuario(int idUsuarios, String nombre, String identificacion, int idCredenciales, int idRol, String estado, LocalDateTime fechaCrea, LocalDateTime fechaModifica) {
        this.idUsuarios = idUsuarios;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.idCredenciales = idCredenciales;
        this.idRol = idRol;
        this.estado = estado;
        this.fechaCrea = LocalDateTime.now();
        this.fechaModifica = LocalDateTime.now();
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
        if (identificacion == null || identificacion.length() != 13 || !identificacion.matches("\\d+")) {
            throw new IllegalArgumentException("La identificación debe contener exactamente 13 dígitos numéricos.");
        }
        this.identificacion = identificacion;
    }

    public int getIdCredenciales() {
        return idCredenciales;
    }

    public void setIdCredenciales(int idCredenciales) {
        if (idCredenciales <= 0) {
            throw new IllegalArgumentException("El ID de credenciales debe ser un número positivo.");
        }
        this.idCredenciales = idCredenciales;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        if (idRol <= 0) {
            throw new IllegalArgumentException("El ID de rol debe ser un número positivo.");
        }
        this.idRol = idRol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(LocalDateTime fechaCrea) {
        this.fechaCrea = LocalDateTime.now();
    }

    public LocalDateTime getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(LocalDateTime fechaModifica) {
        this.fechaModifica = LocalDateTime.now();
    }
}