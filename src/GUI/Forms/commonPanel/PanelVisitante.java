package GUI.Forms.commonPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.formdev.flatlaf.ui.FlatScrollBarUI;

import DataAccess.DAO.PinturaDAO;
import DataAccess.DTO.PinturaDTO;
import Framework.RAConfig;
import GUI.Estilo.*;

public class PanelVisitante extends JPanel {
        private JTextArea resultadoArea;
        private JTextArea tituloArea;  
        private JLabel imagenPinturaLabel;
        private PinturaDAO pinturaDAO; 
    
        public PanelVisitante(JFrame parentFrame) throws UnsupportedLookAndFeelException {

            setLayout(new BorderLayout());
            setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
            
            pinturaDAO = new PinturaDAO();
    
            BackgroundPanel backgroundPanel = new BackgroundPanel("src/utils/logos/FondoVisitante.png");
            backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
            backgroundPanel.setBackground(new Color(0, 0, 0, 1));

            add(backgroundPanel, BorderLayout.CENTER);
    
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);
            
            JButton volverButton = ComponentFactory.crearBotonIcono("back.png", e -> {
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
            codigoInput.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO); 
    
            JButton buscarButton = ComponentFactory.crearBotonIcono("buscarpaint.png", e -> {
                try {
                    buscarPintura(codigoInput);
                } catch (SQLException ex) {
                    RAConfig.showMsgError("Error al buscar la Pintura");
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
            detallePanel.setPreferredSize(new Dimension(400, 300)); 
    
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
            resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO); 
            resultadoArea.setSelectedTextColor(Color.WHITE); 
            resultadoArea.setLineWrap(true);
            resultadoArea.setWrapStyleWord(true);
            resultadoArea.setBorder(EstiloBordes.BORDE_INFERIOR_CAMPO_TEXTO);
    
           JScrollPane detalleScrollPane = new JScrollPane(resultadoArea);
            detalleScrollPane.setPreferredSize(new Dimension(350, 150)); 
            detalleScrollPane.setBorder(null);
            detalleScrollPane.setOpaque(false);
            detalleScrollPane.getViewport().setOpaque(false);

            detalleScrollPane.getVerticalScrollBar().setUI(new FlatScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = new Color(100, 100, 100); 
                    this.trackColor = new Color(60, 63, 65);
                }
            });
            detalleScrollPane.getHorizontalScrollBar().setUI(new FlatScrollBarUI());

            detalleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            detalleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            UIManager.put("ScrollBar.thumbArc", 10); 
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2)); 
            UIManager.put("ScrollBar.width", 8); 

            detallePanel.add(detalleScrollPane);

    
            JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
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

        // private void buscarPintura(JTextField codigoInput) throws SQLException {
        //     String codigoBarras = codigoInput.getText();
        //     if (codigoBarras.isEmpty()) {
        //         RAConfig.showMsgError("Por favor, ingrese un código de barras.");
        //         return;
        //     }
        
        //     PinturaDTO pinturaDTO = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
        //     if (pinturaDTO != null) {
        //         mostrarDetallesPintura(pinturaDTO);
        //         mostrarImagenPintura(pinturaDTO);
        //     } else {
        //         RAConfig.showMsgError("No se encontro ninguna imagen con ese codigo de Barras!!");
        //         imagenPinturaLabel.setIcon(null);
        //     }
        //     codigoInput.setText("");
        //     revalidate();  
        //     repaint();     
        // }



        private void buscarPintura(JTextField codigoInput) throws SQLException {
            String codigoBarras = codigoInput.getText();
            if (codigoBarras.isEmpty()) {
                RAConfig.showMsgError("Por favor, ingrese un código de barras.");
                return;
            }
        
            PinturaDTO pinturaDTO = pinturaDAO.obtenerPinturaPorCodigoBarras(codigoBarras);
            if (pinturaDTO != null) {
                mostrarDetallesPintura(pinturaDTO);
                mostrarImagenPintura(pinturaDTO);
            } else {
                // RAConfig.showMsgError("No se encontró ninguna imagen con ese código de barras!");
                // Mostrar imagen por defecto si no se encuentra la pintura
                mostrarImagenPorDefecto();
            tituloArea.setText("");
            resultadoArea.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO_Defecto);
            resultadoArea.setText("No se encontró ninguna imagen con ese código de barras!");
            }
            codigoInput.setText("");  
            revalidate();  
            repaint();     
        }
        
        // Método para mostrar una imagen por defecto
        private void mostrarImagenPorDefecto() {
            String imagenPath = "src/utils/logos/pintingDefect.jpg";  // Ruta de la imagen por defecto
            File imagenFile = new File(imagenPath);
            if (imagenFile.exists()) {
                try {
                    Image img = ImageIO.read(imagenFile);
                    ImageIcon icon = new ImageIcon(img.getScaledInstance(300, 400, Image.SCALE_SMOOTH));  // Ajustar tamaño si es necesario
                    imagenPinturaLabel.setIcon(icon);
                } catch (IOException e) {
                    imagenPinturaLabel.setIcon(null);  // En caso de error, no mostrar ninguna imagen
                    e.printStackTrace();
                }
            } else {
                imagenPinturaLabel.setIcon(null);  // En caso de que no exista la imagen por defecto
            }
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
            String imagenPath = "src/utils/paintings/" + pinturaDTO.getCodigoBarras() + ".jpg";
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
