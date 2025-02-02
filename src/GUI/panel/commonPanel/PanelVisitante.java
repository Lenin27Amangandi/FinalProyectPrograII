package GUI.panel.commonPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;

import DataAccess.DAO.PinturaDAO;
import DataAccess.DTO.PinturaDTO; 
import utils.Estilo.*;

    public class PanelVisitante extends JPanel {
        private JTextArea resultadoArea;
        private JTextArea tituloArea;  
        private JLabel imagenPinturaLabel;
        private PinturaDAO pinturaDAO; 
    
        public PanelVisitante(JFrame parentFrame) {
            setLayout(new BorderLayout());
            setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
            
            pinturaDAO = new PinturaDAO();
    
            BackgroundPanel backgroundPanel = new BackgroundPanel("src/utils/Resources/logos/FondoVisitante.png");
            backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
            backgroundPanel.setBackground(new Color(0, 0, 0, 1));

            add(backgroundPanel, BorderLayout.CENTER);
    
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);
            
            JButton volverButton = ComponentFactory.crearBotonIcono("back.png", _ -> {
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
    
            JPanel escaneoPanel = ComponentFactory.crearPanelTransparenteConLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            escaneoPanel.setOpaque(false);
    
            JTextField codigoInput = new JTextField();
            codigoInput.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
            codigoInput.setPreferredSize(new Dimension(200, 40));
            codigoInput.setOpaque(false);
            // codigoInput.setSelectedTextColor(Color.WHITE); 
            // codigoInput.setCaretColor(Color.WHITE); 
            codigoInput.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO); 
    
            JButton buscarButton = ComponentFactory.crearBotonIcono("buscarpaint.png", e -> {
                try {
                    buscarPintura(codigoInput);
                } catch (SQLException ex) {
                    ex.printStackTrace();
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
            detallePanel.setPreferredSize(new Dimension(480, 300)); 
    
            tituloArea = new JTextArea();
            tituloArea.setFont(EstiloFuenteYColor.FUENTE_TITULO_SIDEBAR); 
            tituloArea.setEditable(false);
            tituloArea.setFocusable(false);
        
            tituloArea.setBackground(new Color(0, 0, 0, 1)); 
            tituloArea.setSelectedTextColor(Color.WHITE); 
            tituloArea.setLineWrap(true);
            tituloArea.setWrapStyleWord(true);
            tituloArea.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO);

            tituloArea.setPreferredSize(new Dimension(200, 25)); 
            tituloArea.setMinimumSize(new Dimension(150, 25)); 

            JScrollPane tituloScrollPane = new JScrollPane(tituloArea);
            tituloScrollPane.setPreferredSize(new Dimension(200, 25));
            tituloScrollPane.setBorder(null);
            tituloScrollPane.setOpaque(false);
            tituloScrollPane.getViewport().setOpaque(false); 
            detallePanel.add(tituloScrollPane);

            resultadoArea = new JTextArea();
            EstiloFuenteYColor.aplicarEstiloFondoYTexto(resultadoArea);
            resultadoArea.setEditable(false);
            resultadoArea.setFocusable(false); 
            resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO.deriveFont(30f)); 
            resultadoArea.setSelectedTextColor(Color.WHITE); 
            resultadoArea.setLineWrap(true);
            resultadoArea.setWrapStyleWord(true);
            resultadoArea.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO);
    
            JScrollPane detalleScrollPane = new JScrollPane(resultadoArea);
            detalleScrollPane.setPreferredSize(new Dimension(480, 300)); 
            detalleScrollPane.setBorder(null);
            detalleScrollPane.setOpaque(false);
            detalleScrollPane.getViewport().setOpaque(false); 
            detallePanel.add(detalleScrollPane);
    
            JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
            contentPanel.setOpaque(false);
            contentPanel.add(imagePanel);
            contentPanel.add(detallePanel);
    
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
            tituloArea.setText(pinturaDTO.getTitulo());
        
            resultadoArea.setText("");
            resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO); 
            resultadoArea.append("Autor: " + pinturaDTO.getNombreAutor() + "\n");
            resultadoArea.append("Año: " + pinturaDTO.getAnio() + "\n");
            resultadoArea.append("" + pinturaDTO.getcategoria() + "\n");
            resultadoArea.append("\n" + pinturaDTO.getDescripcion() + "\n");
            resultadoArea.append("\n" + pinturaDTO.getSalas() + "\n");
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
