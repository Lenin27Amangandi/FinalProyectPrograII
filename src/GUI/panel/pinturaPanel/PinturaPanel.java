package GUI.panel.pinturaPanel;

import DataAccess.DAO.PinturaDAO;
import DataAccess.DTO.PinturaDTO;
import utils.Estilo.ComponentFactory;
import utils.Estilo.EstiloFuenteYColor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDateTime;

public class PinturaPanel extends JPanel {

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

        panelContenido = new JPanel();
        panelContenido.setLayout(new BorderLayout());

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
                        
        panelFormulario = new JPanel(new GridLayout(8, 2));
                        
        JPanel panelImagen = new JPanel(new BorderLayout());
        lblImagenPreview = new JLabel();
        lblImagenPreview.setPreferredSize(new Dimension(400, 400));
        panelImagen.add(lblImagenPreview, BorderLayout.CENTER);
                        
        panelContenido.add(panelTabla, BorderLayout.CENTER); 
        panelContenido.add(panelFormulario, BorderLayout.SOUTH);
                        
        add(panelContenido, BorderLayout.CENTER);
        add(panelImagen, BorderLayout.EAST);
                        
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregarPintura = ComponentFactory.crearBoton("Agregar", _ -> mostrarFormularioAgregar());
        btnModificarPintura = ComponentFactory.crearBoton("Modificar", _ -> activarModoModificar());
        btnEliminarPintura = ComponentFactory.crearBoton("Eliminar", _ -> eliminarPintura());
                        
        panelBotones.add(btnAgregarPintura);
        panelBotones.add(btnModificarPintura);
        panelBotones.add(btnEliminarPintura);
        add(panelBotones, BorderLayout.NORTH);
                        
        cargarPinturas();
                        
        tablaPinturas.getSelectionModel().addListSelectionListener(_ -> mostrarImagenSeleccionada());
        tablaPinturas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                activarModoModificar();
            }
        });
    }
                    
        private void mostrarFormularioAgregar() {

            panelFormulario.removeAll();  
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
                    
            lblImagenPreview.setPreferredSize(new Dimension(200, 200));
                    
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
                    String autor = pinturaDAO.obtenerNombreAutorPorId(pintura.getIdAutor());
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
                    String categoria = pinturaDAO.obtenerNombreCategoriaPorId(pintura.getIdCategoria());
                    txtCategoria = ComponentFactory.crearCampoTextoTransparente(categoria);
                    panelFormulario.add(txtCategoria);
                        
                    panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Sala:"));
                    String sala = pinturaDAO.obtenerNombreSalaPorId(pintura.getIdSala());
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
                        
                    panelContenido.add(panelFormulario, BorderLayout.SOUTH);
                    panelContenido.revalidate();
                    panelContenido.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una pintura para modificar.");
            }
        }
                                            
        private void cargarPinturas() {
            List<PinturaDTO> pinturas = pinturaDAO.obtenerPinturasResumen(); 
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Código Barras");
            model.addColumn("Título");
            model.addColumn("Autor");
            model.addColumn("Año");
        
            for (PinturaDTO pintura : pinturas) {
                String autor = pinturaDAO.obtenerNombreAutorPorId(pintura.getIdAutor());
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
    
            int idAutor = pinturaDAO.obtenerIdAutorPorNombre(autor);
            int idCategoria = pinturaDAO.obtenerIdCategoriaPorNombre(categoria);
            int idSala = pinturaDAO.obtenerIdSalaPorNombre(sala);
    
            if (idAutor == -1 || idCategoria == -1 || idSala == -1) {
                JOptionPane.showMessageDialog(this, "Autor, Categoría o Sala no encontrados en la base de datos.");
                return;
            }
    
            String imagen = "src/utils/Resources/paintings/" + codigoBarras + ".jpg";
    
            if (titulo.isEmpty() || descripcion.isEmpty() || codigoBarras.isEmpty() || !new File(imagen).exists()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados y la imagen debe existir.");
                return;
            }
    
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
    
            try {
                PinturaDAO pinturaDAO = new PinturaDAO();
                pinturaDAO.insertarPintura(nuevaPinturaDTO);
                JOptionPane.showMessageDialog(this, "Pintura agregada exitosamente.");
                cargarPinturas(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al agregar la pintura: " + e.getMessage());
                e.printStackTrace();
            }
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
        
        int idAutor = pinturaDAO.obtenerIdAutorPorNombre(autor);  
        int idCategoria = pinturaDAO.obtenerIdCategoriaPorNombre(categoria); 
        int idSala = pinturaDAO.obtenerIdSalaPorNombre(sala);  
        
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