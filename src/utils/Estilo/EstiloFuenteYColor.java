package utils.Estilo;

import javax.swing.*;
import java.awt.*;

public class EstiloFuenteYColor {
    // Colores
    public static final Color COLOR_FONDO_CLARO = new Color(245, 245, 245);
    public static final Color COLOR_FONDO_OSCURO = new Color(25, 25, 25);

    public static final Color COLOR_TITULO = new Color(0, 102, 204);
    public static final Color COLOR_TEXTO = Color.BLACK;
    public static final Color COLOR_ERROR = new Color(255, 69, 58);
    public static final Color COLOR_EXITO = new Color(50, 205, 50);
    public static final Color COLOR_VOLVER = new Color(255, 102, 102);
    public static final Color COLOR_SELECCIONAR = new Color(84, 153, 199);
    public static final Color COLOR_FONDO_SIDEBAR = new Color(40, 40, 40);
    public static final Color COLOR_BOTON_SIDEBAR = new Color(60, 60, 60);
    public static final Color COLOR_BOTON_TOGGLE = new Color(30, 30, 30);
    public static final Color COLOR_LOGGIN = new Color(0, 51, 153);
    public static final Color COLOR_BORDES_LOGGIN = Color.BLUE;
    public static final Color COLOR_BORDE = Color.BLACK;
    public static final Color COLOR_TEXTO_BLANCO = Color.WHITE;

    // Fuentes
    public static final Font FUENTE_TITULO = new Font("Serif", Font.BOLD, 30);
    public static final Font FUENTE_BOTON = new Font("Serif", Font.PLAIN, 14);
    public static final Font FUENTE_CAMPO_TEXTO = new Font("Serif", Font.PLAIN, 14);
    public static final Font FUENTE_TABLA = new Font("Serif", Font.BOLD, 14);
    public static final Font FUENTE_LOGIN = new Font("Serif", Font.BOLD, 12);
    public static final Font FUENTE_TITULO_SIDEBAR = new Font("Serif", Font.BOLD, 18);
    public static final Font FUENTE_BOTON_SIDEBAR = new Font("Serif", Font.PLAIN, 17);
    public static final Font FUENTE_BOTON_TOGGLE = new Font("Serif", Font.BOLD, 16);

    public static void aplicarEstiloFondoYTexto(JTextField campo) {
        campo.setFont(FUENTE_CAMPO_TEXTO);
        campo.setBackground(new Color(0, 0, 0, 5)); 
        campo.setSelectedTextColor(Color.WHITE);
    }

    public static void aplicarEstiloFondoYTexto(JTextArea area) {
        if (area != null) {
            area.setFont(FUENTE_CAMPO_TEXTO);
            area.setBackground(new Color(0, 0, 0, 5));  
            area.setSelectedTextColor(Color.WHITE);
        }
    }
    
    public static JPanel crearPanelTransparente() {
        JPanel panel = new JPanel();
        panel.setOpaque(false); 
        return panel;
    }

    public static JLabel crearTextoPrincipal(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_TITULO_SIDEBAR);
        label.setForeground(COLOR_TEXTO);
        return label;
    }
    public static JLabel crearTextoFormularios(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_TABLA);
        label.setForeground(COLOR_TEXTO);
        return label;
    }

    public static JLabel crearTextoSecundario(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_BOTON);
        label.setForeground(COLOR_TEXTO);
        return label;
    }

    public static JLabel crearTituloSecundario(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_TITULO_SIDEBAR);
        label.setForeground(COLOR_TEXTO);
        return label;
    }
    public static JLabel crearTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_TITULO);
        label.setForeground(COLOR_TEXTO);
        return label;
    }


}
