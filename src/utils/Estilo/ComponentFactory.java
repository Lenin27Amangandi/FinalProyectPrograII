package utils.Estilo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComponentFactory {
    private static final String ICONS_PATH = "src/utils/Resources/icons/";

    // Método para crear botones en el Sidebar
    public static JButton crearBoton(String texto, ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON_SIDEBAR);
    
        // Establecer el color del texto a blanco
        boton.setForeground(EstiloFuenteYColor.COLOR_ERROR);
    
        // Hacer el botón transparente
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setBorder(null);
        setHandCursor(boton);
    
        boton.addActionListener(action);
        return boton;
    }

    // Método para crear botones en el Sidebar
    public static JButton crearBotonSidebar(String texto, ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON_SIDEBAR);
    
        // Establecer el color del texto a blanco
        boton.setForeground(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);
    
        // Hacer el botón transparente
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setBorder(null);
        setHandCursor(boton);
    
        boton.addActionListener(action);
        return boton;
    }

    public static JButton crearBotonPanelVisitante(String texto, ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON);
        boton.setForeground(EstiloFuenteYColor.COLOR_TEXTO);
    
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setBorder(null);
    
        boton.addActionListener(action);
        return boton;
    }

    // Método en ComponentFactory para crear un botón con solo icono
    public static JButton crearBotonIcono(String iconPath, ActionListener action) {
        ImageIcon icon = new ImageIcon(ICONS_PATH + iconPath);  // Usar ruta fija para el icono
        JButton boton = new JButton(icon);

        boton.setText("");  
        boton.setFocusPainted(false);  // Eliminar el borde de enfoque
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);  // Eliminar el borde del botón
        boton.setOpaque(false);  // Hacerlo transparente (opcional)
        boton.setBorder(null);
        
        // Añadir la acción
        boton.addActionListener(action);
        return boton;
    }

    public static JButton crearBotonConTextoYIcono(String texto, String iconPath, ActionListener accion) {
        JButton boton = new JButton(texto);
    
        // Establecer fuente y colores usando EstiloUtil
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON);
        boton.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        boton.setForeground(EstiloFuenteYColor.COLOR_TEXTO);
    
        // Crear el icono si se proporciona el path
        ImageIcon icon = new ImageIcon(ICONS_PATH + iconPath);  // Usar ruta fija para el icono
        if (icon != null) {
            boton.setIcon(icon);
            boton.setHorizontalTextPosition(SwingConstants.RIGHT);  
            boton.setIconTextGap(10);  // Espacio entre el icono y el texto
        }
    
        boton.setPreferredSize(boton.getPreferredSize());  // Esto debería forzar el ajuste al contenido
        boton.revalidate();  // Forzar el revalidado del botón
        boton.repaint();  // Forzar la repintura del botón
    
        // Añadir acción al botón
        boton.addActionListener(accion);
        
        return boton;
    }

    // Método para crear un título en el Sidebar
    public static JLabel crearTituloSidebar(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(EstiloFuenteYColor.FUENTE_TITULO_SIDEBAR);
        titulo.setForeground(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(EstiloBordes.BORDE_TITULO_SIDEBAR);
        return titulo;
    }

    // Método para crear un campo de texto con borde y tamaño personalizado
    public static JTextField crearCampoTexto(String texto) {
        JTextField campo = new JTextField(texto);
        campo.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        campo.setPreferredSize(new Dimension(250, 40));
        campo.setBorder(EstiloBordes.BORDE_CAMPO_TEXTO);
        return campo;
    }

    // Método para crear un panel con bordes
    public static JPanel crearPanelConBorde(Color colorBorde) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(colorBorde, 2));
        return panel;
    }

    public static JTextField crearCampoTextoUsuario() {
        JTextField campoTexto = new JTextField(15);
        campoTexto.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        campoTexto.setOpaque(false);
        campoTexto.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDES_LOGGIN));
        campoTexto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EstiloFuenteYColor.COLOR_TITULO, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campoTexto.setPreferredSize(new Dimension(200, 30));
        return campoTexto;
    }
    
    public static JPasswordField crearCampoTextoPassword() {
        JPasswordField campoPassword = new JPasswordField(15);
        campoPassword.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        campoPassword.setOpaque(false);
        campoPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDES_LOGGIN));
        campoPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EstiloFuenteYColor.COLOR_TITULO, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campoPassword.setPreferredSize(new Dimension(200, 30));
        return campoPassword;
    }

    public static JButton crearBotonConCursor(String texto, ActionListener accion) {
        JButton button = new JButton(texto);
        button.addActionListener(accion);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Configura el cursor aquí
        return button;
    }

    public static JPanel crearPanelTransparenteConLayout(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        return panel;
    }

    // Métodos de optimización añadidos:

    public static JTextField crearCampoTextoTransparente(String texto) {
        JTextField campo = new JTextField(texto);
        campo.setOpaque(false);
        campo.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        campo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDE));
        return campo;
    }

    public static JPasswordField crearCampoTextoPasswordTransparente() {
        JPasswordField campo = new JPasswordField();
        campo.setOpaque(false);
        campo.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        campo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDE));
        return campo;
    }

    public static JButton crearBotonConTextoYIconoConCursor(String texto, String iconPath, ActionListener accion) {
        JButton boton = new JButton(texto);
        ImageIcon icon = new ImageIcon(ICONS_PATH + iconPath);
        if (icon != null) {
            boton.setIcon(icon);
            boton.setHorizontalTextPosition(SwingConstants.RIGHT);
            boton.setIconTextGap(10);
        }
        boton.setPreferredSize(new Dimension(250, 40));
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON);
        boton.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        boton.setForeground(EstiloFuenteYColor.COLOR_TEXTO);
        boton.addActionListener(accion);
        setHandCursor(boton);  // Establecer el cursor de mano
        return boton;
    }

    public static JPanel crearPanelTransparenteConLayoutYBorde(LayoutManager layout, Color colorBorde) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createLineBorder(colorBorde, 1));
        return panel;
    }

    public static void setHandCursor(JButton button) {
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
