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

        // Crear un JPanel para el título y centrarlo
        JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // FlowLayout centrado
        tituloPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);  // Establecer el mismo fondo
        JLabel titulo = EstiloFuenteYColor.crearTitulo("Ingreso de Administrador");
        tituloPanel.add(titulo);  // Añadir el título al panel

        // Panel de botón Volver en la parte superior izquierda
        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Botón de "Volver" a la izquierda
        volverPanel.setOpaque(false);  // Establecer como transparente si deseas que el fondo no cubra el contenido

        // Usar el método crearBotonConIcono para crear el botón con solo el ícono
        JButton volverButton = ComponentFactory.crearBotonIcono("back.png", _ -> volverAGInicioPanel());
        volverButton.setPreferredSize(new Dimension(40, 40)); // Ajustar el tamaño del botón si es necesario
        volverButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Aplicar cursor de mano
        volverPanel.add(volverButton);

        // Panel contenedor para el título y el botón de "Volver"
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); // Hacer que el panel sea transparente
        topPanel.add(volverPanel, BorderLayout.WEST); // Botón de Volver a la izquierda
        topPanel.add(tituloPanel, BorderLayout.CENTER); // Título centrado

        // Añadir panel contenedor al BorderLayout.NORTH
        add(topPanel, BorderLayout.NORTH);

        // Panel central con GridBagLayout para centrar los elementos
        JPanel centroPanel = new JPanel();
        centroPanel.setLayout(new GridBagLayout());
        centroPanel.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);  // Establecer el mismo fondo

        // Definir un GridBagConstraints para alinear los elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espaciado entre componentes
        gbc.anchor = GridBagConstraints.CENTER;  // Centrado de los componentes

        // Usuario label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usuarioLabel = EstiloFuenteYColor.crearTituloSecundario("Usuario:");
        centroPanel.add(usuarioLabel, gbc);

        // Campo de texto Usuario (transparente con borde inferior)
        gbc.gridx = 1;
        JTextField usernameField = ComponentFactory.crearCampoTextoUsuario();
        usernameField.setOpaque(false); // Hacer transparente
        usernameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDES_LOGGIN)); // Borde inferior
        centroPanel.add(usernameField, gbc);

        // Contraseña label
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = EstiloFuenteYColor.crearTituloSecundario("Contraseña:");
        centroPanel.add(passwordLabel, gbc);

        // Campo de texto Contraseña (transparente con borde inferior)
        gbc.gridx = 1;
        JPasswordField passwordField = ComponentFactory.crearCampoTextoPassword();
        passwordField.setOpaque(false); // Hacer transparente
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloFuenteYColor.COLOR_BORDES_LOGGIN)); // Borde inferior
        centroPanel.add(passwordField, gbc);

        add(centroPanel, BorderLayout.CENTER);

        // Panel de botones para iniciar sesión y escanear credencial
        JPanel botonesPanelInferior = new JPanel();
        botonesPanelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));  // Alineación central para los botones de la parte inferior
        botonesPanelInferior.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO); // Establecer el mismo fondo
        // Crear botones con iconos y texto
        JButton loginButton = ComponentFactory.crearBotonConTextoYIcono("Iniciar Sesión", "ingresar.png", _ -> iniciarSesion(usernameField, passwordField));
        JButton scanButton = ComponentFactory.crearBotonConTextoYIcono("Escanear Credencial", "credencial.png", _ -> mostrarVentanaEscanearCredencial());

        // Aplicar cursor de mano a los botones
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        scanButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hacer que el fondo del botón sea transparente, pero no eliminar los iconos
        loginButton.setContentAreaFilled(false);  // Hacer el fondo transparente
        loginButton.setBorderPainted(false);     // Eliminar el borde
        loginButton.setOpaque(true);              // No hacer completamente transparente

        scanButton.setContentAreaFilled(false);  // Hacer el fondo transparente
        scanButton.setBorderPainted(false);     // Eliminar el borde
        scanButton.setOpaque(true);              // No hacer completamente transparente

        // Establecer tamaño de los botones (más largos)
        loginButton.setPreferredSize(new Dimension(250, 40));  // Ajustar el tamaño
        scanButton.setPreferredSize(new Dimension(250, 40));   // Ajustar el tamaño

        // Alinear la imagen a la izquierda y el texto a la derecha
        loginButton.setIconTextGap(20);  // Espacio entre el icono y el texto
        scanButton.setIconTextGap(20);   // Espacio entre el icono y el texto

        // Alinear el icono a la izquierda
        loginButton.setHorizontalAlignment(SwingConstants.LEFT);
        scanButton.setHorizontalAlignment(SwingConstants.LEFT);

        // Añadir botones al panel inferior
        botonesPanelInferior.add(loginButton);
        botonesPanelInferior.add(scanButton);

        // Añadir panel de botones a la parte inferior
        add(botonesPanelInferior, BorderLayout.SOUTH);

        // Llamar a revalidate() y repaint() para asegurar que los botones se ajusten correctamente
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
            irPanelAdmin();  // Ir al panel admin después de la autenticación exitosa
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

        JLabel titulo = EstiloFuenteYColor.crearTituloSecundario("Escanee su Credencial:");
        dialog.add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = EstiloFuenteYColor.crearPanelTransparente();
        JTextField idField = ComponentFactory.crearCampoTextoUsuario();
        panelCentral.add(idField);
        dialog.add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = EstiloFuenteYColor.crearPanelTransparente();
        JButton cancelButton = ComponentFactory.crearBoton("Cancelar", _ -> dialog.dispose());
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
            // Aquí se asume que la clave también se pasa como el mismo texto de identificación
            boolean authenticated = usuarioDAO.verificarCredencialesPorIdentificacion(identificacion);
    
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
