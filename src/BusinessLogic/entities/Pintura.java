package BusinessLogic.entities;


public class Pintura {
    private String titulo;
    private String autor;
    private int    anio;
    private String descripcion;
    private String codigoBarras;
    private String categoria;
    private String ubicacion;
    private String imagen;

    public Pintura(){}
    /**
     * Constructor que inicializa todos los atributos de la pintura
     * @param titulo
     * @param autor
     * @param anio
     * @param descripcion
     * @param ubicacion
     * @param codigoBarras
     * @param imagen
     */
    public Pintura(String titulo, String autor, int anio, String descripcion, String codigoBarras, String categoria, String ubicacion, String imagen) {
        this.titulo =       titulo;
        this.autor =        autor;
        this.anio =         anio;
        this.descripcion =  descripcion;
        this.codigoBarras = codigoBarras;
        this.categoria =    categoria;
        this.ubicacion =    ubicacion;
        this.imagen =       imagen;
    }

    /**
     * Getters y Setters del Constructor
     * @return
     */
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        // Validar que el autor no esté vacío
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
        this.autor = autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        // Validar que el año sea razonable (por ejemplo, no negativo)
        if (anio <= 0) {
            throw new IllegalArgumentException("El año debe ser un valor positivo.");
        }
        this.anio = anio;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        // Validar que el código de barras no esté vacío
        if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de barras no puede estar vacío.");
        }
        this.codigoBarras = codigoBarras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
