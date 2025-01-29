package GUI.panel.adminPanel;

import DataAccess.DAO.*;
import GUI.panel.commonPanel.InicioPanel;
import GUI.panel.pinturaPanel.PinturaPanel;
import GUI.panel.usuarioPanel.UsuarioPanel;
import utils.Estilo.*;
import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    private UsuarioPanel usuarioPanel;
    private PinturaPanel pinturaPanel;
    private JPanel mainPanel;
    private JPanel sidebar;
    private JButton toggleButton;
    private boolean sidebarVisible = false;
    private JButton btnVolver;

    private JFrame parentFrame;

    public AdminPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PinturaDAO pinturaDAO = new PinturaDAO();

        usuarioPanel = new UsuarioPanel(usuarioDAO);
        pinturaPanel = new PinturaPanel(pinturaDAO);

        setLayout(new BorderLayout());

        // Crear el Sidebar
        sidebar = createSidebar();

        // Crear el panel principal con CardLayout
        mainPanel = new JPanel(new CardLayout());

        // Panel vacío por defecto
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);
        mainPanel.add(emptyPanel, "Inicio");

        // Añadir los paneles de usuarios y pinturas
        mainPanel.add(usuarioPanel, "Usuarios");
        mainPanel.add(pinturaPanel, "Pinturas");

        add(mainPanel, BorderLayout.CENTER);

        // Crear el botón para abrir y cerrar el sidebar
        toggleButton = new JButton("☰");
        toggleButton.setFocusPainted(false);
        toggleButton.setBackground(EstiloFuenteYColor.COLOR_BOTON_SIDEBAR);
        toggleButton.setForeground(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);
        toggleButton.setFont(EstiloFuenteYColor.FUENTE_BOTON_SIDEBAR);
        toggleButton.addActionListener(_ -> toggleSidebar());

        // Añadir el botón de alternar al panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(toggleButton);
        add(topPanel, BorderLayout.NORTH);

        // Crear los botones del Sidebar
        JButton btnHome = ComponentFactory.crearBotonSidebar("Home", _ -> mostrarInicio());
        JButton btnUsuarios = ComponentFactory.crearBotonSidebar("Usuarios", _ -> mostrarUsuarios());
        JButton btnPinturas = ComponentFactory.crearBotonSidebar("Pinturas", _ -> mostrarPinturas());
        btnVolver = ComponentFactory.crearBotonSidebar("← Volver", _ -> volver());

        // Añadir botones al sidebar
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(btnHome);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnUsuarios);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnPinturas);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(Box.createVerticalStrut(10)); 
        sidebar.add(btnVolver); 
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 0));
        panel.setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);
        return panel;
    }

    public void mostrarUsuarios() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "Usuarios");
    }

    public void mostrarPinturas() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "Pinturas");
    }

    public void mostrarInicio() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "Inicio");
    }

    private void toggleSidebar() {
        sidebarVisible = !sidebarVisible;
        if (sidebarVisible) {
            if (sidebar.getParent() == null) {
                add(sidebar, BorderLayout.WEST);
            }
        } else {
            remove(sidebar);
        }
        revalidate();
        repaint();
    }

    private void volver() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new InicioPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
