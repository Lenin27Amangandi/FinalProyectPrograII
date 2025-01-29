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

    public PanelVisitante(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
    
        // Agregamos el fondo con BackgroundPanel
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/utils/Resources/logos/FondoVisitante.png");
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);
    
        // Panel para el botón de Volver en la esquina superior izquierda
        JButton volverButton = ComponentFactory.crearBotonPanelVisitante("← Volver", _ -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new InicioPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(volverButton);
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
    
        // Panel Principal con GridBagLayout
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setOpaque(false);
        backgroundPanel.add(panelPrincipal, BorderLayout.CENTER);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        // Panel para el título "Escanee la pintura por favor"
        JPanel tituloPanel = new JPanel();
        tituloPanel.setOpaque(false);
        tituloPanel.add(EstiloFuenteYColor.crearTextoSecundario("Escanee la pintura por favor:"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(tituloPanel, gbc);
    
        // Panel de escaneo (en la parte sur del título)
        JPanel escaneoPanel = EstiloFuenteYColor.crearPanelTransparente();
        escaneoPanel.setLayout(new BoxLayout(escaneoPanel, BoxLayout.X_AXIS));  // Usamos BoxLayout horizontal
        escaneoPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
    
        // Campo de texto para ingresar el código de barras
        JTextField codigoInput = new JTextField();
        codigoInput.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        codigoInput.setPreferredSize(new Dimension(300, 40));
        codigoInput.setMaximumSize(codigoInput.getPreferredSize());
        escaneoPanel.add(codigoInput);
    
        // Espacio entre el campo de texto y el botón
        escaneoPanel.add(Box.createHorizontalStrut(10));
    
        // Botón de búsqueda para escanear la pintura (solo con icono)
        JButton buscarButton = ComponentFactory.crearBotonConIcono("src/utils/Resources/icons/buscarpaint.png", _ -> {
            String codigoBarras = codigoInput.getText();
            if (codigoBarras.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un código de barras.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            PinturaDAO pinturaDAO = new PinturaDAO();
            Pintura pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
    
            if (pintura != null) {
                mostrarDetallesPintura(pintura);
            } else {
                resultadoArea.setText("No se encontró ninguna pintura con el código de barras ingresado.");
                imagenPinturaLabel.setIcon(null);
            }
        });
    
        // Añadir el botón al panel de escaneo
        escaneoPanel.add(buscarButton);
    
        // Añadir panel de escaneo al panel principal
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(escaneoPanel, gbc);
    
        // Panel de previsualización de la imagen
        JPanel imagenPanel = new JPanel();
        imagenPanel.setLayout(new BorderLayout());
        imagenPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        imagenPinturaLabel = new JLabel("", SwingConstants.CENTER);
        imagenPanel.add(imagenPinturaLabel, BorderLayout.CENTER);
    
        // Añadir panel de imagen al panel principal
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(imagenPanel, gbc);
    
        // Panel de descripción de la pintura
        JPanel detallesPanel = EstiloFuenteYColor.crearPanelTransparente();
        detallesPanel.setLayout(new BorderLayout());
        detallesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        detallesPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
    
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        resultadoArea.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        resultadoArea.setBorder(EstiloBordes.BORDE_BOTON_SIDEBAR);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        detallesPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Añadir el panel de descripción al panel principal
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(detallesPanel, gbc);
    
        // Evento para código de barras ingresado
        codigoInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String codigoBarras = codigoInput.getText();
                if (codigoBarras.length() == 13) {
                    PinturaDAO pinturaDAO = new PinturaDAO();
                    Pintura pintura = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
    
                    if (pintura != null) {
                        mostrarDetallesPintura(pintura);
                    } else {
                        resultadoArea.setText("No se encontró ninguna pintura con el código de barras ingresado.");
                        imagenPinturaLabel.setIcon(null);
                    }
                }
            }
        });
    }
    
    private void mostrarDetallesPintura(Pintura pintura) {
        // Mostrar detalles de la pintura
        String detallesTexto =
                "Título: " + pintura.getTitulo() + "\n"
                + "Autor: " + pintura.getAutor() + "\n"
                + "Año: " + pintura.getAnio() + "\n"
                + "Descripción: " + pintura.getDescripcion() + "\n"
                + "Ubicación: " + pintura.getUbicacion();
        resultadoArea.setText(detallesTexto);
    
        // Mostrar imagen de la pintura
        String imagenPath = "resources/paintings/" + pintura.getCodigoBarras() + ".jpg";
        File imagenFile = new File(imagenPath);
        if (imagenFile.exists()) {
            try {
                Image img = ImageIO.read(imagenFile);
                ImageIcon icon = new ImageIcon(img.getScaledInstance(300, 400, Image.SCALE_SMOOTH));
                imagenPinturaLabel.setIcon(icon);
            } catch (IOException e) {
                imagenPinturaLabel.setIcon(null);
            }
        } else {
            imagenPinturaLabel.setIcon(null);
        }
    }
}    