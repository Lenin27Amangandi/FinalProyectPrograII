package BusinessLogic.entities;

import java.time.LocalDateTime;

public class Pintura {
    private int idPintura;
    private String titulo;
    private int anio;
    private String descripcion;
    private String codigoBarras;
    private int idCategoria;
    private int idAutor;
    private int idSeccion;
    private String imagen;
    private String estado;
    private LocalDateTime fechaCrea;
    private LocalDateTime fechaModifica;

    public static final String ESTADO_ACTIVO = "A";
    public static final String ESTADO_INACTIVO = "I";
    public static final String ESTADO_ELIMINADO = "E";

    public Pintura() {
        this.estado = ESTADO_ACTIVO;  // Valor predeterminado
    }

    public Pintura(String titulo, int anio, String descripcion, String codigoBarras, int idCategoria, int idAutor, int idSeccion, String imagen, String estado, LocalDateTime fechaCrea, LocalDateTime fechaModifica) {
        this.titulo = titulo;
        this.anio = anio;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
        this.idCategoria = idCategoria;
        this.idAutor = idAutor;
        this.idSeccion = idSeccion;
        this.imagen = imagen;
        this.estado = estado != null ? estado : ESTADO_ACTIVO;  // Si estado es null, poner valor predeterminado
        this.fechaCrea = LocalDateTime.now();
        this.fechaModifica = LocalDateTime.now();
    }

    // Getters y Setters

    public int getIdPintura() {
        return idPintura;
    }

    public void setIdPintura(int idPintura) {
        this.idPintura = idPintura;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        int anioActual = java.time.Year.now().getValue();
        if (anio <= 0 || anio > anioActual) {
            throw new IllegalArgumentException("El año debe estar entre 1 y " + anioActual);
        }
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de barras no puede estar vacío.");
        }
        this.codigoBarras = codigoBarras;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        if (idCategoria <= 0) throw new IllegalArgumentException("ID de categoría no válido.");
        this.idCategoria = idCategoria;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (!estado.equals(ESTADO_ACTIVO) && !estado.equals(ESTADO_INACTIVO) && !estado.equals(ESTADO_ELIMINADO)) {
            throw new IllegalArgumentException("Estado inválido.");
        }
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
