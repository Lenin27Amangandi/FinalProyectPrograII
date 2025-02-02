package utils.Estilo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComponentFactory {
    private static final String ICONS_PATH = "src/utils/Resources/icons/";

    public static JButton crearBoton(String texto, ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON_SIDEBAR);
    
        boton.setForeground(EstiloFuenteYColor.COLOR_ERROR);
    
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setBorder(null);
        setHandCursor(boton);
    
        boton.addActionListener(action);
        return boton;
    }

    public static JButton crearBotonExito(String texto, ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON_SIDEBAR);
    
        boton.setForeground(EstiloFuenteYColor.COLOR_EXITO);
    
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setBorder(null);
        setHandCursor(boton);
    
        boton.addActionListener(action);
        return boton;
    }

    public static JButton crearBotonSidebar(String texto, ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON_SIDEBAR);
    
        boton.setForeground(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);
    
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

    public static JButton crearBotonIcono(String iconPath, ActionListener action) {
        ImageIcon icon = new ImageIcon(ICONS_PATH + iconPath); 
        JButton boton = new JButton(icon);

        boton.setText("");  
        boton.setFocusPainted(false);  
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);  
        boton.setOpaque(false);  
        boton.setBorder(null);
        
        boton.addActionListener(action);
        return boton;
    }

    public static JButton crearBotonConTextoYIcono(String texto, String iconPath, ActionListener accion) {
        JButton boton = new JButton(texto);
    
        boton.setFont(EstiloFuenteYColor.FUENTE_BOTON);
        boton.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        boton.setForeground(EstiloFuenteYColor.COLOR_TEXTO);
    
        ImageIcon icon = new ImageIcon(ICONS_PATH + iconPath);  
        if (icon != null) {
            boton.setIcon(icon);
            boton.setHorizontalTextPosition(SwingConstants.RIGHT);  
            boton.setIconTextGap(10);
        }
    
        boton.setPreferredSize(boton.getPreferredSize());
        boton.revalidate(); 
        boton.repaint();  
    
        boton.addActionListener(accion);
        
        return boton;
    }

    public static JLabel crearTituloSidebar(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(EstiloFuenteYColor.FUENTE_TITULO_SIDEBAR);
        titulo.setForeground(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(EstiloBordes.BORDE_TITULO_SIDEBAR);
        return titulo;
    }

    public static JTextField crearCampoTexto(String texto) {
        JTextField campo = new JTextField(texto);
        campo.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);
        campo.setPreferredSize(new Dimension(250, 40));
        campo.setBorder(EstiloBordes.BORDE_CAMPO_TEXTO);
        return campo;
    }

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
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        return button;
    }

    public static JPanel crearPanelTransparenteConLayout(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        return panel;
    }

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
        setHandCursor(boton);  
        return boton;
    }

    public static JPanel crearPanelTransparenteConLayoutYBorde(LayoutManager layout, Color colorBorde) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDE));
        return panel;
    }

    public static void setHandCursor(JButton button) {
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
