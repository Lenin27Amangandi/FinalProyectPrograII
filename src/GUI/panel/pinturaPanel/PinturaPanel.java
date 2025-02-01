package GUI.panel.pinturaPanel;

import DataAccess.DAO.PinturaDAO;
import DataAccess.DTO.PinturaDTO;
import DataAccess.DataHelper.DbHelper;
import utils.Estilo.ComponentFactory;
import utils.Estilo.EstiloFuenteYColor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PinturaPanel extends JPanel {

    private static final String INSERT_PINTURA = "INSERT INTO Pinturas " +
                                                 "(titulo," 
                                                + "anio," 
                                                + "descripcion," 
                                                + "codigoBarras," 
                                                + "idCategoria," 
                                                + "idAutor," 
                                                + "idSala," 
                                                + "imagen,"
                                                + "estado," 
                                                + "fechaCrea," 
                                                + "fechaModifica)" +
    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private JTable      tablaPinturas;
    private JButton     btnAgregarPintura;
    private JButton     btnModificarPintura;
    private JButton     btnEliminarPintura;
    private PinturaDAO  pinturaDAO;
    private JPanel      panelBotones;
    private JLabel      lblImagenPreview;
    private JPanel      panelFormulario;
    private JPanel      panelContenido;
    private JTextField  txtTitulo, 
                        txtAutor, 
                        txtAnio, 
                        txtDescripcion, 
                        txtCodigoBarras, 
                        txtCategoria, 
                        txtIdSala;
                        
    public PinturaPanel(PinturaDAO pinturaDAO) {
        this.pinturaDAO = pinturaDAO;
        setLayout(new BorderLayout());
        setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);

        // Panel que contiene la tabla y el formulario
        panelContenido = new JPanel();
        panelContenido.setLayout(new BorderLayout());

                        
        // Crear el panel de la tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        tablaPinturas = new JTable(new DefaultTableModel(new Object[]{"ID", "Título", "Autor", "Año"}, 0));
        tablaPinturas.setGridColor(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);
        tablaPinturas.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        JScrollPane scrollPinturas = new JScrollPane(tablaPinturas);
        panelTabla.add(scrollPinturas, BorderLayout.CENTER);

        JTableHeader header = tablaPinturas.getTableHeader();
        header.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        header.setForeground(EstiloFuenteYColor.COLOR_TEXTO);
        header.setFont(EstiloFuenteYColor.FUENTE_TABLA);
                        
        // Panel para el formulario
        panelFormulario = new JPanel(new GridLayout(8, 2));
                        
        // Panel para la imagen de vista previa
        JPanel panelImagen = new JPanel(new BorderLayout());
        lblImagenPreview = new JLabel();
        lblImagenPreview.setPreferredSize(new Dimension(400, 400));
        panelImagen.add(lblImagenPreview, BorderLayout.CENTER);
                        
        // Añadir la tabla y el formulario al panelContenido
        panelContenido.add(panelTabla, BorderLayout.CENTER); // Tabla arriba
        panelContenido.add(panelFormulario, BorderLayout.SOUTH); // Formulario abajo
                        
        // Agregar los paneles principales al PinturaPanel
        add(panelContenido, BorderLayout.CENTER);
        add(panelImagen, BorderLayout.EAST); // Imagen a la derecha
                        
        // Panel de botones
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregarPintura = ComponentFactory.crearBoton("Agregar", _ -> mostrarFormularioAgregar());
        btnModificarPintura = ComponentFactory.crearBoton("Modificar", _ -> activarModoModificar());
        btnEliminarPintura = ComponentFactory.crearBoton("Eliminar", _ -> eliminarPintura());
                        
        panelBotones.add(btnAgregarPintura);
        panelBotones.add(btnModificarPintura);
        panelBotones.add(btnEliminarPintura);
        add(panelBotones, BorderLayout.NORTH);
                        
        // Cargar datos en la tabla
        cargarPinturas();
                        
        // Agregar eventos de selección a la tabla
        tablaPinturas.getSelectionModel().addListSelectionListener(_ -> mostrarImagenSeleccionada());
        tablaPinturas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                activarModoModificar();
            }
        });
    }
                        
                    
        private void mostrarFormularioAgregar() {

            panelFormulario.removeAll();  // Limpiar el panel antes de agregar los campos
            panelFormulario.setLayout(new GridLayout(8, 2));

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Título:"));
            txtTitulo = ComponentFactory.crearCampoTextoTransparente("");
            panelFormulario.add(txtTitulo);

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Autor:"));
            txtAutor = ComponentFactory.crearCampoTextoTransparente("");
            panelFormulario.add(txtAutor);

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Año:"));
            txtAnio = ComponentFactory.crearCampoTextoTransparente("");
            panelFormulario.add(txtAnio);

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Descripción:"));
            txtDescripcion = ComponentFactory.crearCampoTextoTransparente("");
            panelFormulario.add(txtDescripcion);

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Código de barras:"));
            txtCodigoBarras = ComponentFactory.crearCampoTextoTransparente("");
            panelFormulario.add(txtCodigoBarras);

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Categoría:"));
            txtCategoria = ComponentFactory.crearCampoTextoTransparente("");
            panelFormulario.add(txtCategoria);

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Sala:"));
            txtIdSala = ComponentFactory.crearCampoTextoTransparente("");
            panelFormulario.add(txtIdSala);
                    
            // El preview de la imagen sigue en su lugar a la derecha
            lblImagenPreview.setPreferredSize(new Dimension(200, 200));
                    
            // Botón de guardar
            JButton btnGuardar = ComponentFactory.crearBotonExito("Guardar", _ -> {
                try {
                    agregarPintura();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                });
            
            panelFormulario.add(btnGuardar);
                            
            revalidate();
            repaint();
        }

    private void activarModoModificar() {
        int row = tablaPinturas.getSelectedRow();
            if (row != -1) {
                String codigoBarras = (String) tablaPinturas.getValueAt(row, 0);
                PinturaDTO pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
                if (pintura != null) {
                    panelFormulario.removeAll();
                    panelFormulario.setLayout(new GridLayout(8, 2));
                        
                    panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Título:"));
                    txtTitulo = ComponentFactory.crearCampoTextoTransparente(pintura.getTitulo());
                    panelFormulario.add(txtTitulo);
                        
                    panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Autor:"));
                    String autor = obtenerNombreAutorPorId(pintura.getIdAutor());
                    txtAutor = ComponentFactory.crearCampoTextoTransparente(autor);
                    panelFormulario.add(txtAutor);
                        
                    panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Año:"));
                    txtAnio = ComponentFactory.crearCampoTextoTransparente(String.valueOf(pintura.getAnio()));
                    panelFormulario.add(txtAnio);
                        
                    panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Descripción:"));
                    txtDescripcion = ComponentFactory.crearCampoTextoTransparente(pintura.getDescripcion());
                    panelFormulario.add(txtDescripcion);
                        
                    panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Código de barras:"));
                    txtCodigoBarras = ComponentFactory.crearCampoTextoTransparente(pintura.getCodigoBarras());
                    txtCodigoBarras.setEditable(false);
                    panelFormulario.add(txtCodigoBarras);
                        
                    panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Categoría:"));
                    String categoria = obtenerNombreCategoriaPorId(pintura.getIdCategoria());
                    txtCategoria = ComponentFactory.crearCampoTextoTransparente(categoria);
                    panelFormulario.add(txtCategoria);
                        
                    panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Sala:"));
                    String sala = obtenerNombreSalaPorId(pintura.getIdSala());
                    txtIdSala = ComponentFactory.crearCampoTextoTransparente(sala);
                    panelFormulario.add(txtIdSala);
                        
                    JButton btnActualizar = ComponentFactory.crearBoton("Actualizar", _ -> {
                        try {
                            actualizarPintura(pintura.getIdPintura());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        });
                    
                    panelFormulario.add(btnActualizar);
                        
                    // Asegurarse de que el panel se actualiza en la vista
                    panelContenido.add(panelFormulario, BorderLayout.SOUTH);
                    panelContenido.revalidate();
                    panelContenido.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una pintura para modificar.");
            }
        }
                                            
    private void cargarPinturas() {
        List<PinturaDTO> pinturas = pinturaDAO.obtenerTodasLasPinturas();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código Barras");
        model.addColumn("Título");
        model.addColumn("Autor");
        model.addColumn("Año");
                   
        for (PinturaDTO pintura : pinturas) {
            String autor = obtenerNombreAutorPorId(pintura.getIdAutor());
            model.addRow(new Object[]{pintura.getCodigoBarras(), 
                    pintura.getTitulo(), 
                    autor, 
                    pintura.getAnio()});
            }
                    
            tablaPinturas.setModel(model);
            tablaPinturas.setRowHeight(20);
        }

        private void agregarPintura() throws SQLException {
            String titulo =         txtTitulo.getText();
            String autor =          txtAutor.getText();
            int anio;
            String descripcion =    txtDescripcion.getText();
            String codigoBarras =   txtCodigoBarras.getText();
            String categoria =      txtCategoria.getText();
            String sala =           txtIdSala.getText();
    
            try {
                anio = Integer.parseInt(txtAnio.getText());
                if (anio < 1000 || anio > LocalDateTime.now().getYear()) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El año debe ser un número válido entre 1000 y el año actual.");
                return;
            }
    
            int idAutor = obtenerIdAutorPorNombre(autor);
            int idCategoria = obtenerIdCategoriaPorNombre(categoria);
            int idSala = obtenerIdSalaPorNombre(sala);
    
            if (idAutor == -1 || idCategoria == -1 || idSala == -1) {
                JOptionPane.showMessageDialog(this, "Autor, Categoría o Sala no encontrados en la base de datos.");
                return;
            }
    
            String imagen = "src/utils/Resources/paintings/" + codigoBarras + ".jpg";
    
            if (titulo.isEmpty() || descripcion.isEmpty() || codigoBarras.isEmpty() || !new File(imagen).exists()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados y la imagen debe existir.");
                return;
            }
    
            // Crear objeto PinturaDTO
            PinturaDTO nuevaPinturaDTO = new PinturaDTO(
                0, 
                titulo,
                anio,
                descripcion,
                codigoBarras,
                idCategoria,
                null,
                idAutor,
                null, 
                null, 
                idSala,
                imagen,
                "A",
                LocalDateTime.now(),
                LocalDateTime.now()
            );
    
            try (Connection connection = DbHelper.getConnection()) {
                connection.setAutoCommit(false);
    
                try (PreparedStatement ps = connection.prepareStatement(INSERT_PINTURA)) {
                    ps.setString(1, nuevaPinturaDTO.getTitulo());
                    ps.setInt(2, nuevaPinturaDTO.getAnio());
                    ps.setString(3, nuevaPinturaDTO.getDescripcion());
                    ps.setString(4, nuevaPinturaDTO.getCodigoBarras());
                    ps.setInt(5, nuevaPinturaDTO.getIdCategoria());
                    ps.setInt(6, nuevaPinturaDTO.getIdAutor());
                    ps.setInt(7, nuevaPinturaDTO.getIdSala());
                    ps.setString(8, nuevaPinturaDTO.getImagen());
                    ps.setString(9, nuevaPinturaDTO.getEstado());
                    ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
    
                    ps.executeUpdate();
                }
    
                connection.commit();
                JOptionPane.showMessageDialog(this, "Pintura agregada exitosamente.");
                cargarPinturas(); 
    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar la pintura: " + e.getMessage());
                e.printStackTrace();
            }
        }

    private String obtenerNombreAutorPorId(int idAutor) {
        String sql = "SELECT nombreAutor FROM Autores WHERE idAutor = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAutor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nombreAutor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    private String obtenerNombreCategoriaPorId(int idCategoria) {
        String sql = "SELECT categoria FROM Categorias WHERE idCategoria = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("categoria");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    private String obtenerNombreSalaPorId(int idSala) {
        String sql = "SELECT Salas FROM Salas WHERE idSala = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSala);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Salas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


    private void mostrarImagenSeleccionada() {
        int row = tablaPinturas.getSelectedRow();
        if (row != -1) {
            String codigoBarras = (String) tablaPinturas.getValueAt(row, 0);
            PinturaDTO pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
            if (pintura != null) {
                String rutaImagen = "src/utils/Resources/paintings/" + codigoBarras + ".jpg";
                File archivoImagen = new File(rutaImagen);
                if (archivoImagen.exists()) {
                    ImageIcon imagenIcon = new ImageIcon(rutaImagen);
                    Image img = imagenIcon.getImage();
                    Image nuevaImagen = img.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                    lblImagenPreview.setIcon(new ImageIcon(nuevaImagen));
                } else {
                    lblImagenPreview.setIcon(null);
                    JOptionPane.showMessageDialog(this, "Imagen no encontrada.");
                }
            }

        }
    }

    private int obtenerIdAutorPorNombre(String nombreAutor) throws SQLException {
        String sql = "SELECT idAutor FROM Autores WHERE nombreAutor = ?";
        try (Connection connection = DbHelper.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreAutor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idAutor");  // Autor encontrado
            } else {
                // Si no existe, insertar el nuevo autor
                String insertSql = "INSERT INTO Autores (nombreAutor) VALUES (?)";
                try (PreparedStatement insertPs = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertPs.setString(1, nombreAutor);
                    insertPs.executeUpdate();
                    ResultSet generatedKeys = insertPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); 
                    }
                }
            }
        }
        return -1; 
    }


    private int obtenerIdCategoriaPorNombre(String nombreCategoria) throws SQLException {
        String sql = "SELECT idCategoria FROM Categorias WHERE categoria = ?";
        try (Connection connection = DbHelper.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreCategoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idCategoria");  
            } else {
                // Si no existe, insertar la nueva categoría
                String insertSql = "INSERT INTO Categorias (categoria) VALUES (?)";
                try (PreparedStatement insertPs = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertPs.setString(1, nombreCategoria);
                    insertPs.executeUpdate();
                    ResultSet generatedKeys = insertPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  
                    }
                }
            }
        }
        return -1;  
    }
    

    private int obtenerIdSalaPorNombre(String nombreSala) throws SQLException {
        String sql = "SELECT idSala FROM Salas WHERE Salas = ?";
        try (Connection connection = DbHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreSala);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idSala"); 
            } else {
                String insertSql = "INSERT INTO Salas (Salas) VALUES (?)";
                try (PreparedStatement insertPs = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    insertPs.setString(1, nombreSala);
                    insertPs.executeUpdate();
                    ResultSet generatedKeys = insertPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); 
                    }
                }
            }
        }
        return -1; 
    }
    
    
    
    private void eliminarPintura() {
        int row = tablaPinturas.getSelectedRow();
        if (row != -1) {
            String codigoBarras = (String) tablaPinturas.getValueAt(row, 0);
            PinturaDTO pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
            if (pintura != null) {
                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar esta pintura?", 
                    "Confirmar eliminación", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    try {
                        pinturaDAO.actualizarEstadoPintura(pintura.getIdPintura(), "E");
                        JOptionPane.showMessageDialog(this, "Pintura eliminada con Exito.");
                        cargarPinturas();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar la pintura: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    
    private void actualizarPintura(int idPintura) throws HeadlessException, SQLException {
        String titulo =         txtTitulo.getText();
        String autor =          txtAutor.getText();
        int anio;
        String descripcion =    txtDescripcion.getText();
        String codigoBarras =   txtCodigoBarras.getText();
        String categoria =      txtCategoria.getText();
        String sala =           txtIdSala.getText();
        
        try {
            anio = Integer.parseInt(txtAnio.getText());
            if (anio < 1000 || anio > LocalDateTime.now().getYear()) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido entre 1000 y el año actual.");
            return;
        }
    
        String imagen = "src/utils/Resources/paintings/" + codigoBarras + ".jpg";
        
        if (titulo.isEmpty() || autor.isEmpty() || descripcion.isEmpty() || codigoBarras.isEmpty() || categoria.isEmpty() || sala.isEmpty() || !new File(imagen).exists()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados y la imagen debe existir.");
            return;
        }
        
        int idAutor = obtenerIdAutorPorNombre(autor);  
        int idCategoria = obtenerIdCategoriaPorNombre(categoria); 
        int idSala = obtenerIdSalaPorNombre(sala);  
        
        if (idAutor == -1 || idCategoria == -1 || idSala == -1) {
            JOptionPane.showMessageDialog(this, "Autor, Categoría o Sala no encontrados en la base de datos.");
            return;
        }
        
        PinturaDTO pinturaModificada = new PinturaDTO(idPintura, 
                                                      titulo, 
                                                      anio, 
                                                      descripcion, 
                                                      codigoBarras, 
                                                      idCategoria,  
                                                      null, 
                                                      idAutor, 
                                                      null, null, idSala, 
                                                      imagen,
                                                      "A",  
                                                      LocalDateTime.now(), 
                                                      LocalDateTime.now()); 
    
        pinturaDAO.actualizarPintura(pinturaModificada);
        JOptionPane.showMessageDialog(this, "Pintura modificada exitosamente.");
        cargarPinturas(); 
    }
    
    
}