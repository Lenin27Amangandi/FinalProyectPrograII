package DataAccess.DTO;

import java.time.LocalDateTime;

public class PinturaDTO {
    private int idPintura;
    private String titulo;
    private int anio;
    private String descripcion;
    private String codigoBarras;
    private int idCategoria;
    private int idAutor;
    private String nombreAutor;  // Nuevo atributo para el nombre del autor
    private String categoria;  // Atributo para el nombre de la categoría
    private String Salas;  // Atributo para el nombre de la sala
    private int idSala;
    private String imagen;
    private String estado;
    private LocalDateTime fechaCrea;
    private LocalDateTime fechaModifica;

    public PinturaDTO() {}

    public PinturaDTO(int idPintura, String titulo, int anio, String descripcion, String codigoBarras, 
                      int idCategoria, String categoria, int idAutor, String nombreAutor, 
                      String Salas, int idSala, String imagen, String estado, 
                      LocalDateTime fechaCrea, LocalDateTime fechaModifica) {
        this.idPintura = idPintura;
        this.titulo = titulo;
        this.anio = anio;
        this.descripcion = descripcion;
        this.codigoBarras = codigoBarras;
        this.idCategoria = idCategoria;
        this.categoria = categoria;
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;  // Asignar el nombre del autor
        this.Salas = Salas;  // Asignar el nombre de la sala
        this.idSala = idSala;
        this.imagen = imagen;
        this.estado = estado;
        this.fechaCrea = fechaCrea;
        this.fechaModifica = fechaModifica;
    }

    // Getters and Setters

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
        this.codigoBarras = codigoBarras;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getcategoria() {
        return categoria;  // Getter para el nombre de la categoría
    }

    public void setcategoria(String categoria) {
        this.categoria = categoria;  // Setter para el nombre de la categoría
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;  // Getter para el nombre del autor
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;  // Setter para el nombre del autor
    }

    public String getSalas() {
        return Salas;  // Getter para el nombre de la sala
    }

    public void setSalas(String Salas) {
        this.Salas = Salas;  // Setter para el nombre de la sala
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
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
        this.estado = estado;
    }

    public LocalDateTime getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(LocalDateTime fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public LocalDateTime getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(LocalDateTime fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    @Override
    public String toString() {
        return "PinturaDTO{" +
                "idPintura=" + idPintura +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anio +
                ", descripcion='" + descripcion + '\'' +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", idCategoria=" + idCategoria +
                ", categoria='" + categoria + '\'' +
                ", idAutor=" + idAutor +
                ", nombreAutor='" + nombreAutor + '\'' +  // Mostrar el nombre del autor
                ", Salas='" + Salas + '\'' +  // Mostrar el nombre de la sala
                ", idSala=" + idSala +
                ", imagen='" + imagen + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaCrea=" + fechaCrea +
                ", fechaModifica=" + fechaModifica +
                '}';
    }
}
