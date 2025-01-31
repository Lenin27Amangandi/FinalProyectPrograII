package GUI.panel.commonPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import BusinessLogic.entities.Pintura;
import DataAccess.DAO.PinturaDAO;
import utils.Estilo.*;

public class PanelVisitante extends JPanel {
    private JTextArea resultadoArea;
    private JLabel imagenPinturaLabel;
    private PinturaDAO pinturaDAO; // Reutilización del DAO

    public PanelVisitante(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        
        // Crear DAO una vez para reutilizar
        pinturaDAO = new PinturaDAO();

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/utils/Resources/logos/FondoVisitante.png");
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        add(backgroundPanel, BorderLayout.CENTER);

        // Panel superior con botón de volver y título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton volverButton = ComponentFactory.crearBotonPanelVisitante("\u2190 Volver", _ -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new InicioPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        JLabel tituloLabel = EstiloFuenteYColor.crearTextoPrincipal("Escanee la Pintura por favor");
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(volverButton, BorderLayout.WEST);
        volverButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor de mano
        topPanel.add(tituloLabel, BorderLayout.CENTER);
        backgroundPanel.add(topPanel);

        // Panel de escaneo con campo de texto y botón de búsqueda
        JPanel escaneoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        escaneoPanel.setOpaque(false);

        JTextField codigoInput = new JTextField();
        codigoInput.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        codigoInput.setPreferredSize(new Dimension(200, 40));
        codigoInput.setBackground(new Color(0, 0, 0, 10)); // Fondo ligeramente transparente
        codigoInput.setSelectedTextColor(Color.WHITE); // Color de texto seleccionado
        codigoInput.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO); // Aplica solo el borde inferior

        JButton buscarButton = ComponentFactory.crearBotonIcono("buscarpaint.png", _ -> buscarPintura(codigoInput));

        buscarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor de mano

        escaneoPanel.add(codigoInput);
        escaneoPanel.add(buscarButton);
        backgroundPanel.add(escaneoPanel);

        // Panel para la imagen (Preview)
        JPanel previewPanel = new JPanel();
        previewPanel.setOpaque(false);
        previewPanel.setLayout(new BorderLayout());
        previewPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        imagenPinturaLabel = new JLabel();
        imagenPinturaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenPinturaLabel.setOpaque(false); // Asegura que el fondo sea transparente

        previewPanel.add(imagenPinturaLabel, BorderLayout.CENTER);
        backgroundPanel.add(previewPanel);

        // Panel de detalles
        JPanel detallePanel = new JPanel(new BorderLayout());
        detallePanel.setOpaque(false);
        detallePanel.setPreferredSize(new Dimension(100, 100));
        
        // Crear JTextArea con fondo ligeramente transparente
        resultadoArea = new JTextArea();
        EstiloFuenteYColor.aplicarEstiloFondoYTexto(resultadoArea);  // Asegúrate de aplicar el estilo
        resultadoArea.setEditable(false);
        resultadoArea.setFocusable(false); // Evitar selección de texto
        resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        resultadoArea.setBackground(new Color(0, 0, 0, 10)); // Fondo ligeramente transparente
        resultadoArea.setSelectedTextColor(Color.WHITE); // Color de texto seleccionado
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);
        
        // Crear un borde con solo la parte inferior visible
        resultadoArea.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO); // Aplica solo el borde inferior

        // Crear JScrollPane con JTextArea
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Hace que el fondo del scrollpane sea transparente
        detallePanel.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.add(detallePanel);

        // Evento para código de barras ingresado
        codigoInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String codigoBarras = codigoInput.getText();
                if (codigoBarras.length() == 13) {
                    buscarPintura(codigoInput);
                }
            }
        });
    }

    private void buscarPintura(JTextField codigoInput) {
        String codigoBarras = codigoInput.getText();
        if (codigoBarras.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código de barras.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Pintura pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
        if (pintura != null) {
            // Llamar por separado para mostrar los detalles y la imagen
            mostrarDetallesPintura(pintura);
            mostrarImagenPintura(pintura);
        } else {
            resultadoArea.setText("No se encontró ninguna pintura con el código de barras ingresado.");
            imagenPinturaLabel.setIcon(null);
        }
    }
    
    private void mostrarDetallesPintura(Pintura pintura) {
        // Mostrar detalles de la pintura en el JTextArea
        String detallesTexto = String.format(
                "Título: %s\nAutor: %s\nAño: %d\nDescripción: %s\nUbicación: %s",
                pintura.getTitulo(), pintura.getAutor(), pintura.getAnio(), pintura.getDescripcion(), pintura.getUbicacion());
        resultadoArea.setText(detallesTexto);
    }
    
    private void mostrarImagenPintura(Pintura pintura) {
        // Mostrar imagen de la pintura
        String imagenPath = "src/utils/Resources/paintings/" + pintura.getCodigoBarras() + ".jpg";
        File imagenFile = new File(imagenPath);
        if (imagenFile.exists()) {
            try {
                // Cargar la imagen
                Image img = ImageIO.read(imagenFile);
                // Ajustar el tamaño de la imagen
                ImageIcon icon = new ImageIcon(img.getScaledInstance(250, 350, Image.SCALE_SMOOTH));
                // Actualizar el JLabel con la imagen
                imagenPinturaLabel.setIcon(icon);
            } catch (IOException e) {
                imagenPinturaLabel.setIcon(null); // Si hay error al cargar, poner el icono en null
            }
        } else {
            imagenPinturaLabel.setIcon(null); // Si no se encuentra la imagen, poner el icono en null
        }
    }
}
