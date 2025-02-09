package GUI.Forms.adminPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import DataAccess.DAO.UsuarioDAO;
import Framework.RAConfig;
import GUI.Estilo.*;
import GUI.Forms.commonPanel.InicioPanel;

public class IngresoAdminPanel extends JPanel {
    private final JFrame parentFrame;
    // private boolean modoOscuro = false; 
    // private JButton modoOscurButton;


    public IngresoAdminPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout(10, 10));
        setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);

        JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tituloPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        JLabel titulo = EstiloFuenteYColor.crearTitulo("Ingreso a Administración");
        tituloPanel.add(titulo);

        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        volverPanel.setOpaque(false);

        JButton volverButton = ComponentFactory.crearBotonIcono("back.png", e -> volverAGInicioPanel());
        volverButton.setPreferredSize(new Dimension(40, 40)); 
        volverButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        volverPanel.add(volverButton);

        // JPanel modoOscuroPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // modoOscuroPanel.setOpaque(false);

        // modoOscurButton = new JButton("☀");
        // modoOscurButton.setPreferredSize(new Dimension(30, 30));
        // modoOscurButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // modoOscurButton.addActionListener(e -> cambiarModo());

        // modoOscuroPanel.add(modoOscurButton);

        volverPanel.add(volverButton);


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        // topPanel.add(modoOscuroPanel, BorderLayout.EAST);
        topPanel.add(volverPanel, BorderLayout.WEST);
        topPanel.add(tituloPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel centroPanel = new JPanel();
        centroPanel.setLayout(new GridBagLayout());
        centroPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.CENTER; 

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usuarioLabel = EstiloFuenteYColor.crearTituloSecundario("Usuario:");
        centroPanel.add(usuarioLabel, gbc);

        gbc.gridx = 1;
        JTextField usernameField = ComponentFactory.crearCampoTextoUsuario();
        usernameField.setOpaque(false);
        usernameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDES_LOGGIN));
        centroPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = EstiloFuenteYColor.crearTituloSecundario("Contraseña:");
        centroPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = ComponentFactory.crearCampoTextoPassword();
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDES_LOGGIN)); 
        centroPanel.add(passwordField, gbc);

        add(centroPanel, BorderLayout.CENTER);

        JPanel botonesPanelInferior = new JPanel();
        botonesPanelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));  
        botonesPanelInferior.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        JButton loginButton = ComponentFactory.crearBotonConTextoYIcono("Iniciar Sesión", "ingresar.png", e -> iniciarSesion(usernameField, passwordField));
        JButton scanButton = ComponentFactory.crearBotonConTextoYIcono("Escanear Credencial", "credencial.png", e -> mostrarVentanaEscanearCredencial());

        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        scanButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginButton.setContentAreaFilled(false); 
        loginButton.setBorderPainted(false);  
        loginButton.setOpaque(true);          

        scanButton.setContentAreaFilled(false);  
        scanButton.setBorderPainted(false);     
        scanButton.setOpaque(true);             

        loginButton.setPreferredSize(new Dimension(250, 40)); 
        scanButton.setPreferredSize(new Dimension(250, 40));   

        loginButton.setIconTextGap(20);  
        scanButton.setIconTextGap(20);   

        loginButton.setHorizontalAlignment(SwingConstants.LEFT);
        scanButton.setHorizontalAlignment(SwingConstants.LEFT);

        botonesPanelInferior.add(loginButton);
        botonesPanelInferior.add(scanButton);

        add(botonesPanelInferior, BorderLayout.SOUTH);

        botonesPanelInferior.revalidate();
        botonesPanelInferior.repaint();

        passwordField.addActionListener(e -> iniciarSesion(usernameField, passwordField));
    }

    private void iniciarSesion(JTextField usernameField, JPasswordField passwordField) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
    
        if (username.isEmpty() || password.isEmpty()) {
            RAConfig.showMsgError("Por favor, complete todos los campos.");
            return;
        }
    
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean authenticated = usuarioDAO.verificarCredencialesYEstado(username, password);
            if (authenticated) {
                int idUsuario = usuarioDAO.obtenerIdPorUsuario(username);
    
                String rol = usuarioDAO.obtenerRolPorId(idUsuario);
                if (rol != null && (rol.equals("Administrador") || rol.equals("Supervisor"))) {
                    System.out.println("Ingreso como " + rol);
                    RAConfig.showMsg("Inicio de sesión exitoso como " + rol);
                    irPanelAdmin(rol);
                } else {
                    RAConfig.showMsgError("Usuario o Contraseña INCORRECTOS!");
                }
            } else {
                RAConfig.showMsgError("Usuario o Contraseña INCORRECTOS!");
            }
        } catch (Exception e) {
            RAConfig.showMsgError("Error al conectar con la base de datos.");
        }
    }
    

    private void verificarYAutenticarCredencial(JTextField identificacionField, JDialog dialog) {
        String identificacion = identificacionField.getText();
        if (identificacion.length() < 13) {
            return;
        }
    
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean authenticated = usuarioDAO.verificarCredencialesPorIdentificacionYEstado(identificacion);
            if (authenticated) {
                int idUsuario = usuarioDAO.obtenerIdPorIdentificacion(identificacion);
                
                String rol = usuarioDAO.obtenerRolPorId(idUsuario);
                if (rol != null && (rol.equals("Administrador") || rol.equals("Supervisor"))) {
                    RAConfig.showMsg("Inicio de sesion exitoso como " + rol);
                    dialog.dispose();
                    irPanelAdmin(rol);
                } else {
                    RAConfig.showMsgError("No tiene permisos de administrador.");
                }

            } else {
                RAConfig.showMsgError("ID de credencial incorrecto.");
                identificacionField.setText(""); 
            }
        } catch (Exception e) {
            RAConfig.showMsgError("Error al conectar con la base de datos.");
        }
    }
    

    private void mostrarVentanaEscanearCredencial() {
        JDialog dialog = new JDialog(parentFrame, "🎨 ArtVision", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);

        JLabel titulo = EstiloFuenteYColor.crearTituloSecundario("Escanee su Credencial:");
        dialog.add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = EstiloFuenteYColor.crearPanelTransparente();
        JTextField idField = ComponentFactory.crearCampoTextoUsuario();
        panelCentral.add(idField);
        dialog.add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = EstiloFuenteYColor.crearPanelTransparente();
        JButton cancelButton = ComponentFactory.crearBoton("Cancelar", e -> dialog.dispose());
        panelInferior.add(cancelButton);
        dialog.add(panelInferior, BorderLayout.SOUTH);
        idField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarYAutenticarCredencial(idField, dialog);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarYAutenticarCredencial(idField, dialog);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarYAutenticarCredencial(idField, dialog);
            }
        });

        dialog.setVisible(true);
    }

    private void irPanelAdmin(String rol) {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new AdminPanel(parentFrame, rol));  
        parentFrame.revalidate();
        parentFrame.repaint();
    }
    
    // private void cambiarModo(){
    //     modoOscuro = !modoOscuro;
    //     if(modoOscuro){
    //         setBackground(EstiloFuenteYColor.COLOR_FONDO_OSCURO);
    //         modoOscurButton.setText("🌙");
    //     } else{
    //         setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
    //         modoOscurButton.setText("☀");
    //     }
    //     revalidate();
    //     repaint();
    // }

    private void volverAGInicioPanel() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new InicioPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

}
