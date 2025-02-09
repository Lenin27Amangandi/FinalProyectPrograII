package GUI.Estilo;

import javax.swing.*;
import javax.swing.border.Border;

public class EstiloBordes {
    public static final Border BORDE_BOTON_SIDEBAR = BorderFactory.createEmptyBorder(10, 20, 10, 20);
    public static final Border BORDE_TITULO_SIDEBAR = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    
    public static final Border BORDE_CAMPO_TEXTO = BorderFactory.createLineBorder(EstiloFuenteYColor.COLOR_TEXTO, 2);

    public static final Border BORDE_INFERIOR_CAMPO_TEXTO = BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_TEXTO);
}
