package GUI.Forms.pinturaPanel;

import DataAccess.DAO.PinturaDAO;
import DataAccess.DTO.PinturaDTO;
import Framework.RAConfig;
import Framework.UsuarioBLException;
import GUI.Estilo.ComponentFactory;
import GUI.Estilo.EstiloFuenteYColor;

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
        btnAgregarPintura = ComponentFactory.crearBoton("Agregar", e -> mostrarFormularioAgregar());
        btnModificarPintura = ComponentFactory.crearBoton("Modificar", e -> {
            try {
                activarModoModificar();
            } catch (UsuarioBLException ex) {
                RAConfig.showMsgError("Error al ir al Modificar");
            }
        });
        btnEliminarPintura = ComponentFactory.crearBoton("Eliminar", e -> {
            try {
                eliminarPintura();
            } catch (UsuarioBLException | SQLException ex) {
                RAConfig.showMsgError("Error al ir a Eliminar");
            }
        });
                        
        panelBotones.add(btnAgregarPintura);
        panelBotones.add(btnModificarPintura);
        panelBotones.add(btnEliminarPintura);
        add(panelBotones, BorderLayout.NORTH);
                        
        cargarPinturas();
                        
        tablaPinturas.getSelectionModel().addListSelectionListener(e -> {
            try {
                mostrarImagenSeleccionada();
            } catch (UsuarioBLException | SQLException ex) {
                RAConfig.showMsgError("Error al Mostar la imagen seleccionada");
            }
        });
        tablaPinturas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                try {
                    activarModoModificar();
                } catch (UsuarioBLException e) {
                    RAConfig.showMsgError("ERROR!!!");
                }
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
                    
            JButton btnGuardar = ComponentFactory.crearBotonExito("Guardar", e -> {
                try {
                    agregarPintura();
                } catch (UsuarioBLException | SQLException ex) {
                    RAConfig.showMsgError("Error al ir al Agregar pintura");
                }
                });
            
            panelFormulario.add(btnGuardar);
                            
            revalidate();
            repaint();
        }

    private void activarModoModificar() throws UsuarioBLException {
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
                        
                    JButton btnActualizar = ComponentFactory.crearBoton("Actualizar", e -> {
                        try {
                            actualizarPintura(pintura.getIdPintura());
                        } catch (UsuarioBLException | HeadlessException | SQLException ex) {
                            RAConfig.showMsgError("Error al actualizar Pintura");
                        }
                        });
                    
                    panelFormulario.add(btnActualizar);
                        
                    panelContenido.add(panelFormulario, BorderLayout.SOUTH);
                    panelContenido.revalidate();
                    panelContenido.repaint();
                }
            } else {
                RAConfig.showMsg("Seleccione una pintura para modificar.");
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
                    RAConfig.showMsgError("El año debe ser un número válido.");
                }
            } catch (NumberFormatException e) {
                RAConfig.showMsgError("El año debe ser un número válido.");
                return;
            }

            int idAutor = pinturaDAO.obtenerIdAutorPorNombre(autor);
            int idCategoria = pinturaDAO.obtenerIdCategoriaPorNombre(categoria);
            int idSala = pinturaDAO.obtenerIdSalaPorNombre(sala);
    
            if (idAutor == -1 || idCategoria == -1 || idSala == -1) {
                RAConfig.showMsgError( "Autor, Categoría o Sala no encontrados en la base de datos.");
                return;
            }
    
            String imagen = "src/Resources/paintings/" + codigoBarras + ".jpg";
    
            if (titulo.isEmpty() || descripcion.isEmpty() || codigoBarras.isEmpty() || !new File(imagen).exists()) {
                RAConfig.showMsgError("Todos los campos deben ser completados y la imagen debe existir.");
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
                "A"
            );
    
            try {
                PinturaDAO pinturaDAO = new PinturaDAO();
                pinturaDAO.insertarPintura(nuevaPinturaDTO);
                RAConfig.showMsg( "Pintura agregada exitosamente.");
                cargarPinturas(); 
            } catch (Exception e) {
                RAConfig.showMsgError("Error al agregar la pintura: " + e.getMessage());
            }
        }

    private void mostrarImagenSeleccionada() throws SQLException {
        int row = tablaPinturas.getSelectedRow();
        if (row != -1) {
            String codigoBarras = (String) tablaPinturas.getValueAt(row, 0);
            PinturaDTO pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
            if (pintura != null) {
                String rutaImagen = "src/utils/paintings/" + codigoBarras + ".jpg";
                File archivoImagen = new File(rutaImagen);
                if (archivoImagen.exists()) {
                    ImageIcon imagenIcon = new ImageIcon(rutaImagen);
                    Image img = imagenIcon.getImage();
                    Image nuevaImagen = img.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                    lblImagenPreview.setIcon(new ImageIcon(nuevaImagen));
                } else {
                    lblImagenPreview.setIcon(null);
                    RAConfig.showMsgError("Imagen no encontrada.");
                }
            }

        }
    }
    
    private void eliminarPintura() throws SQLException {
        int row = tablaPinturas.getSelectedRow();
        if (row != -1) {
            String codigoBarras = (String) tablaPinturas.getValueAt(row, 0);
            PinturaDTO pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
            if (pintura != null) {
                // int opcion = JOptionPane.showConfirmDialog(this,
                //     "¿Estás seguro de que deseas eliminar esta pintura?", 
                //     "Confirmar eliminación", 
                //     JOptionPane.YES_NO_OPTION, 
                //     JOptionPane.WARNING_MESSAGE);
                boolean opcion = RAConfig.showConfirmYesNo("Estas seguro de eliminar la Pintura??");
                if (opcion == true) {
                    try {
                        pinturaDAO.actualizarEstadoPintura(pintura.getIdPintura(), "E");
                        RAConfig.showMsg("Pintura eliminada con Exito.");
                        cargarPinturas();
                    } catch (SQLException e) {
                        RAConfig.showMsgError("Error al eliminar la pintura: " + e.getMessage());
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
            RAConfig.showMsgError("El año debe ser un numero valido");
        }
    } catch (NumberFormatException e) {
        RAConfig.showMsgError("El año debe ser un numero valido!!");
        return;
    }
    
        String imagen = "src/utils/paintings/" + codigoBarras + ".jpg";
        
        if (titulo.isEmpty() || autor.isEmpty() || descripcion.isEmpty() || codigoBarras.isEmpty() || categoria.isEmpty() || sala.isEmpty() || !new File(imagen).exists()) {
            RAConfig.showMsgError("Todos los campos deben ser completados");
            return;
        }
        
        int idAutor = pinturaDAO.obtenerIdAutorPorNombre(autor);  
        int idCategoria = pinturaDAO.obtenerIdCategoriaPorNombre(categoria); 
        int idSala = pinturaDAO.obtenerIdSalaPorNombre(sala);  
        
        if (idAutor == -1 || idCategoria == -1 || idSala == -1) {
            RAConfig.showMsgError("Autor, Categoría o Sala no encontrados en la base de datos.");
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
                                                      "A"); 
    
        pinturaDAO.actualizarPintura(pinturaModificada);
        RAConfig.showMsg("Pintura modificada exitosamente.");
        cargarPinturas(); 
    }
    
}