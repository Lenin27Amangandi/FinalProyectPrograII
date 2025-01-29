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
    
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/utils/Resources/logos/FondoVisitante.png");
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);
    
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
    
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setOpaque(false);
        backgroundPanel.add(panelPrincipal, BorderLayout.CENTER);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        JPanel tituloPanel = new JPanel();
        tituloPanel.setOpaque(false);
        tituloPanel.add(EstiloFuenteYColor.crearTextoSecundario("Escanee la pintura por favor:"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(tituloPanel, gbc);
    
        JPanel escaneoPanel = EstiloFuenteYColor.crearPanelTransparente();
        escaneoPanel.setLayout(new BoxLayout(escaneoPanel, BoxLayout.X_AXIS));  
        escaneoPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
    
        JTextField codigoInput = new JTextField();
        codigoInput.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        codigoInput.setPreferredSize(new Dimension(300, 40));
        codigoInput.setMaximumSize(codigoInput.getPreferredSize());
        escaneoPanel.add(codigoInput);
    
        escaneoPanel.add(Box.createHorizontalStrut(10));
    
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
    
        escaneoPanel.add(buscarButton);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(escaneoPanel, gbc);
    
        JPanel imagenPanel = new JPanel();
        imagenPanel.setLayout(new BorderLayout());
        imagenPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        imagenPinturaLabel = new JLabel("", SwingConstants.CENTER);
        imagenPanel.add(imagenPinturaLabel, BorderLayout.CENTER);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(imagenPanel, gbc);
    
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
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(detallesPanel, gbc);
    
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
        String detallesTexto =
                "Título: " + pintura.getTitulo() + "\n"
                + "Autor: " + pintura.getAutor() + "\n"
                + "Año: " + pintura.getAnio() + "\n"
                + "Descripción: " + pintura.getDescripcion() + "\n"
                + "Ubicación: " + pintura.getUbicacion();
        resultadoArea.setText(detallesTexto);
    
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