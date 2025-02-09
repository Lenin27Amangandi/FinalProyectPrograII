package GUI.panel.commonPanel;

import utils.Estilo.*;
import javax.swing.*;
import GUI.panel.adminPanel.IngresoAdminPanel;
import java.awt.*;

public class InicioPanel extends JPanel {
    private final JFrame parentFrame;
    private boolean sidebarVisible = false;
    private JPanel sidebarPanel;

    public InicioPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel("src/utils/Resources/logos/InicioImage.png");
        backgroundPanel.setLayout(new BorderLayout());

        sidebarPanel = createSidebar();
        sidebarPanel.setVisible(sidebarVisible);

        JButton textIconButton = new JButton("☰ Menú");
        textIconButton.setFocusPainted(false);
        textIconButton.setBackground(EstiloFuenteYColor.COLOR_BOTON_TOGGLE);
        textIconButton.setForeground(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);
        textIconButton.setFont(EstiloFuenteYColor.FUENTE_BOTON_TOGGLE);
        textIconButton.setBorder(EstiloBordes.BORDE_BOTON_SIDEBAR);
        textIconButton.setToolTipText("Abrir menú");
        textIconButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        textIconButton.addActionListener(e -> toggleSidebar());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(textIconButton);

        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(sidebarPanel, BorderLayout.LINE_START);

        add(backgroundPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 0));
        panel.setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);

        JLabel titleLabel = ComponentFactory.crearTituloSidebar("Opciones");

        JButton visitarButton = ComponentFactory.crearBotonSidebar("Visitar Museo", e -> {
            try {
                irModoVisitante();
            } catch (UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            }
        });
        visitarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        JButton adminButton = ComponentFactory.crearBotonSidebar("Administración", e -> irLogin());
        adminButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        panel.add(Box.createVerticalStrut(20));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(visitarButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(adminButton);

        return panel;
    }

    private void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
        sidebarPanel.setVisible(sidebarVisible);
        revalidate();
        repaint();
    }

    private void irModoVisitante() throws UnsupportedLookAndFeelException {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new PanelVisitante(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void irLogin() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new IngresoAdminPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
