package GUI.panel.pinturaPanel;

import BusinessLogic.entities.Pintura;
import DataAccess.DAO.PinturaDAO;
import utils.Estilo.ComponentFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class PinturaPanel extends JPanel {

    private JTable tablaPinturas;
    private JButton btnAgregarPintura;
    private JButton btnModificarPintura;
    private JButton btnEliminarPintura;
    private PinturaDAO pinturaDAO;
    private JPanel panelBotones;
    private JLabel lblImagenPreview;
    @SuppressWarnings("unused")
    private boolean modificando = false;

    private JPanel panelFormulario;
    private JTextField txtTitulo, txtAutor, txtAnio, txtDescripcion, txtCodigoBarras, txtCategoria, txtUbicacion;

    private JSplitPane splitPane;

    public PinturaPanel(PinturaDAO pinturaDAO) {
        this.pinturaDAO = pinturaDAO;
        setLayout(new BorderLayout());

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600);

        JPanel panelTabla = new JPanel(new BorderLayout());
        tablaPinturas = new JTable();
        JScrollPane scrollPinturas = new JScrollPane(tablaPinturas);
        panelTabla.add(scrollPinturas, BorderLayout.CENTER);

        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new BorderLayout());

        lblImagenPreview = new JLabel();
        lblImagenPreview.setPreferredSize(new Dimension(400, 400));
        panelImagen.add(lblImagenPreview, BorderLayout.CENTER);

        splitPane.setLeftComponent(panelTabla);
        splitPane.setRightComponent(panelImagen);
        add(splitPane, BorderLayout.CENTER);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregarPintura = ComponentFactory.crearBotonPanelVisitante("Agregar", _ -> mostrarFormularioAgregar());
        btnModificarPintura = ComponentFactory.crearBotonPanelVisitante("Modificar", _ -> activarModoModificar());
        btnEliminarPintura = ComponentFactory.crearBotonPanelVisitante("Eliminar", _ -> eliminarPintura());
        panelBotones.add(btnAgregarPintura);
        panelBotones.add(btnModificarPintura);
        panelBotones.add(btnEliminarPintura);
        add(panelBotones, BorderLayout.NORTH);

        cargarPinturas();

        tablaPinturas.getSelectionModel().addListSelectionListener(_ -> mostrarImagenSeleccionada());
    }

    private void mostrarFormularioAgregar() {
        panelFormulario = new JPanel();
        panelFormulario.removeAll();

        panelFormulario.setLayout(new GridLayout(8, 2));

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Año:"));
        txtAnio = new JTextField();
        panelFormulario.add(txtAnio);

        panelFormulario.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(new JLabel("Código de barras:"));
        txtCodigoBarras = new JTextField();
        panelFormulario.add(txtCodigoBarras);

        panelFormulario.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        panelFormulario.add(txtCategoria);

        panelFormulario.add(new JLabel("Ubicación:"));
        txtUbicacion = new JTextField();
        panelFormulario.add(txtUbicacion);

        lblImagenPreview = new JLabel();
        lblImagenPreview.setPreferredSize(new Dimension(200, 200));
        panelFormulario.add(lblImagenPreview);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(_ -> agregarPintura());
        panelFormulario.add(btnGuardar);

        splitPane.setRightComponent(panelFormulario);

        revalidate();
        repaint();
    }

    private void cargarPinturas() {
        List<Pintura> pinturas = pinturaDAO.obtenerTodasLasPinturas();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código Barras");
        model.addColumn("Título");
        model.addColumn("Autor");
        model.addColumn("Año");

        for (Pintura pintura : pinturas) {
            model.addRow(new Object[]{pintura.getCodigoBarras(), pintura.getTitulo(), pintura.getAutor(), pintura.getAnio()});
        }

        tablaPinturas.setModel(model);
        tablaPinturas.setRowHeight(20);
    }

    private void mostrarImagenSeleccionada() {
        int row = tablaPinturas.getSelectedRow();
        if (row != -1) {
            String codigoBarras = (String) tablaPinturas.getValueAt(row, 0);
            Pintura pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
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

    private void agregarPintura() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        int anio = Integer.parseInt(txtAnio.getText());
        String descripcion = txtDescripcion.getText();
        String codigoBarras = txtCodigoBarras.getText();
        String categoria = txtCategoria.getText();
        String ubicacion = txtUbicacion.getText();
        
        String imagen = "src/utils/Resources/paintings/" + codigoBarras + ".jpg";

        if (titulo.isEmpty() || autor.isEmpty() || descripcion.isEmpty() || codigoBarras.isEmpty() || categoria.isEmpty() || ubicacion.isEmpty() || !new File(imagen).exists()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados y la imagen debe existir.");
            return;
        }

        Pintura nuevaPintura = new Pintura(titulo, autor, anio, descripcion, codigoBarras, categoria, ubicacion, imagen);
        boolean resultado = pinturaDAO.insertarPintura(nuevaPintura);
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Pintura agregada exitosamente.");
            cargarPinturas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar pintura.");
        }
    }

    private void eliminarPintura() {
        int row = tablaPinturas.getSelectedRow();
        if (row != -1) {
            String codigoBarras = (String) tablaPinturas.getValueAt(row, 0);
            boolean result = pinturaDAO.eliminarPintura(codigoBarras);
            if (result) {
                JOptionPane.showMessageDialog(this, "Pintura eliminada.");
                cargarPinturas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar pintura.");
            }
        }
    }

    private void activarModoModificar() {
        modificando = true;
        mostrarFormularioModificar();
    }

    private void mostrarFormularioModificar() {
        int row = tablaPinturas.getSelectedRow();
        if (row != -1) {
            String codigoBarras = (String) tablaPinturas.getValueAt(row, 0);
            Pintura pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
            if (pintura != null) {
                panelFormulario = new JPanel();
                panelFormulario.removeAll();

                panelFormulario.setLayout(new GridLayout(8, 2));

                panelFormulario.add(new JLabel("Título:"));
                txtTitulo = new JTextField(pintura.getTitulo());
                panelFormulario.add(txtTitulo);

                panelFormulario.add(new JLabel("Autor:"));
                txtAutor = new JTextField(pintura.getAutor());
                panelFormulario.add(txtAutor);

                panelFormulario.add(new JLabel("Año:"));
                txtAnio = new JTextField(String.valueOf(pintura.getAnio()));
                panelFormulario.add(txtAnio);

                panelFormulario.add(new JLabel("Descripción:"));
                txtDescripcion = new JTextField(pintura.getDescripcion());
                panelFormulario.add(txtDescripcion);

                panelFormulario.add(new JLabel("Código de barras:"));
                txtCodigoBarras = new JTextField(pintura.getCodigoBarras());
                panelFormulario.add(txtCodigoBarras);

                panelFormulario.add(new JLabel("Categoría:"));
                txtCategoria = new JTextField(pintura.getCategoria());
                panelFormulario.add(txtCategoria);

                panelFormulario.add(new JLabel("Ubicación:"));
                txtUbicacion = new JTextField(pintura.getUbicacion());
                panelFormulario.add(txtUbicacion);

                lblImagenPreview = new JLabel();
                lblImagenPreview.setPreferredSize(new Dimension(200, 200));
                panelFormulario.add(lblImagenPreview);

                JButton btnGuardar = new JButton("Guardar Cambios");
                btnGuardar.addActionListener(_ -> modificarPintura(pintura.getCodigoBarras()));
                panelFormulario.add(btnGuardar);

                splitPane.setRightComponent(panelFormulario);
                revalidate();
                repaint();
            }
        }
    }

    private void modificarPintura(String codigoBarras) {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        int anio = Integer.parseInt(txtAnio.getText());
        String descripcion = txtDescripcion.getText();
        String categoria = txtCategoria.getText();
        String ubicacion = txtUbicacion.getText();
        String imagen = "src/utils/Resources/paintings/" + codigoBarras + ".jpg";

        Pintura pinturaModificada = new Pintura(titulo, autor, anio, descripcion, codigoBarras, categoria, ubicacion, imagen);
        boolean result = pinturaDAO.actualizarPintura(pinturaModificada);
        if (result) {
            JOptionPane.showMessageDialog(this, "Pintura modificada.");
            cargarPinturas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar pintura.");
        }
    }
}
