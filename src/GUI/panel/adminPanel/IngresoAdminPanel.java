package GUI.panel.adminPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import utils.Estilo.*;
import DataAccess.DAO.UsuarioDAO;
import GUI.panel.commonPanel.InicioPanel;

public class IngresoAdminPanel extends JPanel {
    private final JFrame parentFrame;

    public IngresoAdminPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout(10, 10));
        setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);

        JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  
        JLabel titulo = EstiloFuenteYColor.crearTitulo("Panel de Administrador");
        tituloPanel.add(titulo);  

        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        volverPanel.setOpaque(false);  

        JButton volverButton = ComponentFactory.crearBotonConIcono("src/utils/Resources/icons/back.png", _ -> volverAGInicioPanel());
        volverButton.setPreferredSize(new Dimension(40, 40)); 

        volverPanel.add(volverButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
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
        centroPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = EstiloFuenteYColor.crearTituloSecundario("Contraseña:");
        centroPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = ComponentFactory.crearCampoTextoPassword();
        centroPanel.add(passwordField, gbc);

        add(centroPanel, BorderLayout.CENTER);

        JPanel botonesPanelInferior = new JPanel();
        botonesPanelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));  
        botonesPanelInferior.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);

        JButton loginButton = ComponentFactory.crearBotonConTextoYIcono("Iniciar Sesión", "ingresar.png", _ -> iniciarSesion(usernameField, passwordField));
        JButton scanButton = ComponentFactory.crearBotonConTextoYIcono("Escanear Credencial", "credencial.png", _ -> mostrarVentanaEscanearCredencial());

        botonesPanelInferior.add(loginButton);
        botonesPanelInferior.add(scanButton);

        add(botonesPanelInferior, BorderLayout.SOUTH);

        botonesPanelInferior.revalidate();
        botonesPanelInferior.repaint();


        passwordField.addActionListener(_ -> iniciarSesion(usernameField, passwordField));
    }

    private void iniciarSesion(JTextField usernameField, JPasswordField passwordField) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        System.out.println("Usuario ingresado: " + username);
        System.out.println("Contraseña ingresada: " + password);

        if (username.isEmpty() || password.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.", "Error");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean authenticated = usuarioDAO.verificarCredenciales(username, password);

        if (authenticated) {
            mostrarMensaje("Inicio de sesión exitoso.", "Éxito");
            irPanelAdmin();
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos.", "Error");
        }
    }

    private void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarVentanaEscanearCredencial() {
        JDialog dialog = new JDialog(parentFrame, "Escanear Credencial", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JLabel titulo = EstiloFuenteYColor.crearTituloSecundario("Escanee o ingrese el ID:");
        dialog.add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = EstiloFuenteYColor.crearPanelTransparente();
        JTextField idField = ComponentFactory.crearCampoTextoUsuario();
        panelCentral.add(idField);
        dialog.add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = EstiloFuenteYColor.crearPanelTransparente();
        JButton cancelButton = ComponentFactory.crearBotonConTextoYIcono("Cancelar", "cancelar.png", _ -> dialog.dispose());
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

    private void verificarYAutenticarCredencial(JTextField identificacionField, JDialog dialog) {
        String identificacion = identificacionField.getText();
        if (identificacion.length() == 13) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean authenticated = usuarioDAO.verificarCredencialesPorId(identificacion);

            if (authenticated) {
                mostrarMensaje("Inicio de sesión exitoso.", "Éxito");
                dialog.dispose();
                irPanelAdmin();
            } else {
                mostrarMensaje("ID de credencial incorrecto.", "Error");
                identificacionField.setText(""); 
            }
        } else {
            mostrarMensaje("El ID debe tener exactamente 13 caracteres.", "Error");
        }
    }

    private void irPanelAdmin() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new AdminPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void volverAGInicioPanel() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new InicioPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
