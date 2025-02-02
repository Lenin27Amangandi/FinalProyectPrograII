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

        JPanel previewPanel = new JPanel();
        previewPanel.setOpaque(false);
        previewPanel.setLayout(new BorderLayout());
        previewPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        imagenPinturaLabel = new JLabel();
        imagenPinturaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenPinturaLabel.setOpaque(false); 

        previewPanel.add(imagenPinturaLabel, BorderLayout.CENTER);
        backgroundPanel.add(previewPanel);

        JPanel detallePanel = new JPanel(new BorderLayout());
        detallePanel.setOpaque(false);
        detallePanel.setPreferredSize(new Dimension(100, 100));
        
        resultadoArea = new JTextArea();
        EstiloFuenteYColor.aplicarEstiloFondoYTexto(resultadoArea);
        resultadoArea.setEditable(false);
        resultadoArea.setFocusable(false); 
        resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        resultadoArea.setBackground(new Color(0, 0, 0, 10)); 
        resultadoArea.setSelectedTextColor(Color.WHITE); 
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);
        
        resultadoArea.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO);

        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); 
        detallePanel.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.add(detallePanel);

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
        String detallesTexto = String.format(
                "Título: %s\nAutor: %s\nAño: %d\nDescripción: %s\nUbicación: %s\nCategoría: %s",
                pinturaDTO.getTitulo(), 
                pinturaDTO.getNombreAutor(),  
                pinturaDTO.getAnio(), 
                pinturaDTO.getDescripcion(), 
                pinturaDTO.getSalas(), 
                pinturaDTO.getcategoria()  
        );
        resultadoArea.setText(detallesTexto);
    }
    

    private void mostrarImagenPintura(PinturaDTO pinturaDTO) {
        String imagenPath = "src/utils/Resources/paintings/" + pinturaDTO.getCodigoBarras() + ".jpg";
        File imagenFile = new File(imagenPath);
        if (imagenFile.exists()) {
            try {
                Image img = ImageIO.read(imagenFile);
                ImageIcon icon = new ImageIcon(img.getScaledInstance(250, 350, Image.SCALE_SMOOTH));
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
