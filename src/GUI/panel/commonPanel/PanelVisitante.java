package GUI.panel.commonPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DataAccess.DAO.PinturaDAO;
import DataAccess.DTO.PinturaDTO; 
import utils.Estilo.*;

public class PanelVisitante extends JPanel {
    private JTextArea resultadoArea;
    private JLabel imagenPinturaLabel;
    private PinturaDAO pinturaDAO; 

    public PanelVisitante(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        
        pinturaDAO = new PinturaDAO();

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/utils/Resources/logos/FondoVisitante.png");
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        add(backgroundPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JButton volverButton = ComponentFactory.crearBotonPanelVisitante("\u2190 Volver", _ -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new InicioPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        JLabel tituloLabel = EstiloFuenteYColor.crearTextoPrincipal("Escanee la Pintura por favor");
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(volverButton, BorderLayout.WEST);
        volverButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        topPanel.add(tituloLabel, BorderLayout.CENTER);
        backgroundPanel.add(topPanel);

        JPanel escaneoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        escaneoPanel.setOpaque(false);

        JTextField codigoInput = new JTextField();
        codigoInput.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        codigoInput.setPreferredSize(new Dimension(200, 40));
        codigoInput.setBackground(new Color(0, 0, 0, 10)); 
        codigoInput.setSelectedTextColor(Color.WHITE); 
        codigoInput.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO); 

        JButton buscarButton = ComponentFactory.crearBotonIcono("buscarpaint.png", _ -> {
            try {
                buscarPintura(codigoInput);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        buscarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        escaneoPanel.add(codigoInput);
        escaneoPanel.add(buscarButton);
        backgroundPanel.add(escaneoPanel);

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setOpaque(false);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setOpaque(false);
        imagenPinturaLabel = new JLabel();
        imagenPinturaLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        imagenPinturaLabel.setOpaque(false); 
        imagePanel.add(imagenPinturaLabel);

        JPanel detallePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        detallePanel.setOpaque(false);
        detallePanel.setPreferredSize(new Dimension(500, 300)); 

        resultadoArea = new JTextArea();
        EstiloFuenteYColor.aplicarEstiloFondoYTexto(resultadoArea);
        resultadoArea.setEditable(false);
        resultadoArea.setFocusable(false); 
        resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO.deriveFont(20f)); 
        resultadoArea.setBackground(new Color(0, 0, 0, 10)); 
        resultadoArea.setSelectedTextColor(Color.WHITE); 
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);
        resultadoArea.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO);

        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setPreferredSize(new Dimension(500, 300)); // Ajustar el tamaño del scroll
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); 
        detallePanel.add(scrollPane);

        // Crear un panel contenedor para los dos paneles (imagen y detalles)
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        contentPanel.setOpaque(false);
        contentPanel.add(imagePanel);
        contentPanel.add(detallePanel);

        // Añadir el panel principal con la imagen y los detalles al backgroundPanel
        mainContentPanel.add(contentPanel, BorderLayout.CENTER);
        backgroundPanel.add(mainContentPanel);

        codigoInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String codigoBarras = codigoInput.getText();
                if (codigoBarras.length() == 13) {
                    try {
                        buscarPintura(codigoInput);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void buscarPintura(JTextField codigoInput) throws SQLException {
        String codigoBarras = codigoInput.getText();
        if (codigoBarras.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código de barras.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        PinturaDTO pinturaDTO = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
        if (pinturaDTO != null) {
            mostrarDetallesPintura(pinturaDTO);
            mostrarImagenPintura(pinturaDTO);
        } else {
            resultadoArea.setText("No se encontró ninguna pintura con el código de barras ingresado.");
            imagenPinturaLabel.setIcon(null);
        }
    
        revalidate();  
        repaint();     
    }
    

    private void mostrarDetallesPintura(PinturaDTO pinturaDTO) {
        // Limpiar el JTextArea antes de agregar nuevos detalles
        resultadoArea.setText("");
    
        // Establecer el título con un formato especial (negrita, tamaño más grande)
        resultadoArea.setFont(EstiloFuenteYColor.FUENTE_TITULO_SIDEBAR); // Fuente del título más grande
        resultadoArea.append(pinturaDTO.getTitulo() + "\n\n");
    
        // Establecer el estilo para el resto del texto (texto normal)
        resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO); // Fuente del texto normal
        resultadoArea.append("Autor: " + pinturaDTO.getNombreAutor() + "\n");
        resultadoArea.append("Año: " + pinturaDTO.getAnio() + "\n");
        resultadoArea.append("Descripción: " + pinturaDTO.getDescripcion() + "\n");
        resultadoArea.append("\n" + pinturaDTO.getSalas() + "\n");
        resultadoArea.append("" + pinturaDTO.getcategoria() + "\n");
    }
    


    private void mostrarImagenPintura(PinturaDTO pinturaDTO) {
        String imagenPath = "src/utils/Resources/paintings/" + pinturaDTO.getCodigoBarras() + ".jpg";
        File imagenFile = new File(imagenPath);
        if (imagenFile.exists()) {
            try {
                Image img = ImageIO.read(imagenFile);
                ImageIcon icon = new ImageIcon(img.getScaledInstance(300, 400, Image.SCALE_SMOOTH));
                imagenPinturaLabel.setIcon(icon);
            } catch (IOException e) {
                imagenPinturaLabel.setIcon(null); 
                e.printStackTrace();
            }
        } else {
            imagenPinturaLabel.setIcon(null); 
        }
    }
}
