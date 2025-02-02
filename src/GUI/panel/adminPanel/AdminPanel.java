package GUI.panel.adminPanel;

import DataAccess.DAO.*;
import GUI.panel.commonPanel.InicioPanel;
import GUI.panel.pinturaPanel.PinturaPanel;
import GUI.panel.usuarioPanel.UsuarioPanel;
import utils.Estilo.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class AdminPanel extends JPanel {

    private UsuarioPanel usuarioPanel;
    private PinturaPanel pinturaPanel;
    private JPanel mainPanel;
    private JPanel sidebar;
    private JButton toggleButton;
    private boolean sidebarVisible = false;
    private JButton btnVolver;

    private JFrame parentFrame;

    public AdminPanel(JFrame parentFrame, String rol) {
        this.parentFrame = parentFrame;
    
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PinturaDAO pinturaDAO = new PinturaDAO();
        usuarioPanel = new UsuarioPanel(usuarioDAO);
        pinturaPanel = new PinturaPanel(pinturaDAO);
    
        setLayout(new BorderLayout());
        setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);
    
        sidebar = createSidebar();
    
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR); 
    
        mostrarInicio();
    
        mainPanel.add(usuarioPanel, "Usuarios");
        mainPanel.add(pinturaPanel, "Pinturas");
    
        add(mainPanel, BorderLayout.CENTER);
    
        toggleButton = ComponentFactory.crearBotonSidebar("☰", e -> toggleSidebar());
        toggleButton.setFocusPainted(false);
        toggleButton.setBackground(EstiloFuenteYColor.COLOR_BOTON_SIDEBAR);
        toggleButton.setForeground(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);
        toggleButton.setFont(EstiloFuenteYColor.FUENTE_BOTON_SIDEBAR);
        toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(true);
        topPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);
        topPanel.add(toggleButton);
        add(topPanel, BorderLayout.NORTH);
    
        JButton btnHome = ComponentFactory.crearBotonSidebar("Inicio", e -> mostrarInicio());
        JButton btnUsuarios = ComponentFactory.crearBotonSidebar("Usuarios", e -> mostrarUsuarios());
        JButton btnPinturas = ComponentFactory.crearBotonSidebar("Pinturas", e -> mostrarPinturas());
        btnVolver = ComponentFactory.crearBotonSidebar("Salir", e -> volver());
    
        if ("Supervisor".equals(rol)) {
            btnUsuarios.setEnabled(false);
        }
    
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = cargarImagen("/utils/Resources/logos/InicioAdmin.png");
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        }
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
        JPanel inicioPanel = new JPanel() {
            private final Image img = cargarImagen("/utils/Resources/logos/InicioAdmin.png");
    
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        inicioPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);
        mainPanel.add(inicioPanel, "Inicio");
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "Inicio");
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

    private Image cargarImagen(String ruta) {
        try {
            return ImageIO.read(getClass().getResource(ruta)); 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void volver() {
        if (!(parentFrame.getContentPane().getComponent(0) instanceof InicioPanel)) {
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new InicioPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }
    
}
