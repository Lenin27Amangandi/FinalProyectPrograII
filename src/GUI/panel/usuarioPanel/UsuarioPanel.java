package GUI.panel.usuarioPanel;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BusinessLogic.entities.Usuario;
import DataAccess.DAO.UsuarioDAO;
import utils.Estilo.ComponentFactory;

public class UsuarioPanel extends JPanel {

    private JTable tablaUsuarios;
    private JButton btnAgregarUsuario;
    private JButton btnModificarUsuario;
    private JButton btnEliminarUsuario;
    private UsuarioDAO usuarioDAO;
    private JPanel panelFormulario;
    private JTextField txtNombre, txtRol, txtIdentificacion, txtUsername;
    private JPasswordField txtPassword;
    private boolean modificando = false;

    public UsuarioPanel(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        setLayout(new BorderLayout());

        // Tabla de usuarios
        tablaUsuarios = new JTable();
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Asegúrate de que solo se seleccione una fila
        tablaUsuarios.getSelectionModel().addListSelectionListener(_ -> mostrarFormularioModificar()); // Añadido listener
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        add(scrollUsuarios, BorderLayout.CENTER);

        // Panel de formulario dinámico
        panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2));  // Para 5 campos y un botón
        add(panelFormulario, BorderLayout.SOUTH);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));

        btnAgregarUsuario = ComponentFactory.crearBotonPanelVisitante("Agregar", _ -> mostrarFormularioAgregar());
        btnModificarUsuario = ComponentFactory.crearBotonPanelVisitante("Modificar", _ -> activarModoModificar());
        btnEliminarUsuario = ComponentFactory.crearBotonPanelVisitante("Eliminar", _ -> eliminarUsuario());

        panelBotones.add(btnAgregarUsuario);
        panelBotones.add(btnModificarUsuario);
        panelBotones.add(btnEliminarUsuario);

        add(panelBotones, BorderLayout.NORTH);

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Rol");
        model.addColumn("Identificación");

        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{usuario.getNombre(), usuario.getRol(), usuario.getIdentificacion()});
        }

        tablaUsuarios.setModel(model);
        tablaUsuarios.setRowHeight(20);
    }

    private void mostrarFormularioAgregar() {
        // Limpiar el formulario y agregar los campos necesarios
        panelFormulario.removeAll();
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Rol:"));
        txtRol = new JTextField();
        panelFormulario.add(txtRol);

        panelFormulario.add(new JLabel("Identificación:"));
        txtIdentificacion = new JTextField();
        panelFormulario.add(txtIdentificacion);

        panelFormulario.add(new JLabel("Nombre de usuario:"));
        txtUsername = new JTextField();
        panelFormulario.add(txtUsername);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(_ -> agregarUsuario());
        panelFormulario.add(btnGuardar);

        revalidate();
        repaint();
    }

    private void mostrarFormularioModificar() {
        if (modificando) { // Solo muestra el formulario de modificar si está en modo de modificación
            int row = tablaUsuarios.getSelectedRow();
            if (row != -1) {
                String nombre = (String) tablaUsuarios.getValueAt(row, 0); // Nombre
                String rol = (String) tablaUsuarios.getValueAt(row, 1);   // Rol
                String identificacion = (String) tablaUsuarios.getValueAt(row, 2); // Identificación
        
                // Limpiar el formulario y agregar los campos con los valores de la fila seleccionada
                panelFormulario.removeAll();
        
                panelFormulario.add(new JLabel("Nombre:"));
                txtNombre = new JTextField(nombre);
                panelFormulario.add(txtNombre);
        
                panelFormulario.add(new JLabel("Rol:"));
                txtRol = new JTextField(rol);
                panelFormulario.add(txtRol);
        
                panelFormulario.add(new JLabel("Identificación:"));
                txtIdentificacion = new JTextField(identificacion);
                panelFormulario.add(txtIdentificacion);
        
                // Eliminar los campos "Nombre de usuario" y "Contraseña"
                // panelFormulario.add(new JLabel("Nombre de usuario:"));
                // txtUsername = new JTextField();  
                // panelFormulario.add(txtUsername);
        
                // panelFormulario.add(new JLabel("Contraseña:"));
                // txtPassword = new JPasswordField();  
                // panelFormulario.add(txtPassword);
        
                JButton btnModificar = new JButton("Modificar");
                btnModificar.addActionListener(_ -> modificarUsuario(row)); // Usamos el índice de la fila seleccionada
                panelFormulario.add(btnModificar);
        
                revalidate();
                repaint();
            } else {
                // Si no se seleccionó ninguna fila
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario.");
            }
        }
    }
    

    private void modificarUsuario(int row) {
        String identificacion = (String) tablaUsuarios.getValueAt(row, 2); // Ahora obtenemos la Identificación
        
        String nuevoNombre = txtNombre.getText();
        String nuevoRol = txtRol.getText();
        
        // Llama a la validación de credencial y a la lógica de modificación
        String credencial = pedirCredencial();
        if (credencial == null || !validarCredencial(credencial)) {
            JOptionPane.showMessageDialog(this, "No es posible realizar la acción solicitada sin autorización del DUEÑO.");
            return;
        }
    
        // Aquí solo pasamos el nombre y rol, sin el username
        boolean result = usuarioDAO.modificarUsuario(identificacion, nuevoNombre, nuevoRol, credencial);
        if (result) {
            JOptionPane.showMessageDialog(this, "Usuario modificado.");
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar usuario.");
        }
    
        modificando = false; // Vuelve al estado normal después de modificar
        cargarUsuarios(); // Actualiza la tabla
    }
    

    private void activarModoModificar() {
        modificando = true; // Establece el estado de "modificar"
        mostrarFormularioModificar(); // Muestra el formulario
    }

    private void agregarUsuario() {
        String nombre = txtNombre.getText();
        String rol = txtRol.getText();
        String identificacion = txtIdentificacion.getText();
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // Pide la credencial para validar
        String credencial = pedirCredencial();
        if (credencial == null || !validarCredencial(credencial)) {
            JOptionPane.showMessageDialog(this, "No es posible realizar la acción solicitada sin autorización del DUEÑO.");
            return;
        }

        if (usuarioDAO.verificarCredencialesPorId(identificacion)) {
            JOptionPane.showMessageDialog(this, "La identificación ya está registrada.");
            return;
        }

        Usuario usuario = new Usuario(0, nombre, rol, identificacion, username, password);
        boolean result = usuarioDAO.insertarUsuario(usuario);
        JOptionPane.showMessageDialog(this, result ? "Usuario agregado correctamente." : "Error al agregar usuario.");

        cargarUsuarios();
    }

    private void eliminarUsuario() {
        int row = tablaUsuarios.getSelectedRow();
        if (row != -1) {
            // Obtenemos la identificación de la columna 2 (Identificación)
            String identificacion = (String) tablaUsuarios.getValueAt(row, 2);

            // Llamar al DAO para eliminar el usuario
            boolean result = usuarioDAO.eliminarUsuario(identificacion);
            if (result) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario.");
            }
        }
    }

    private String pedirCredencial() {
        return JOptionPane.showInputDialog(this, "Escanee su credencial para continuar:");
    }

    private boolean validarCredencial(String identificacion) {
        String credencialValida = obtenerCredencialValida();

        return credencialValida != null && credencialValida.equals(identificacion);
    }

    private String obtenerCredencialValida() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/utils/Resources/config/config.properties")) {
            prop.load(fis);
            return prop.getProperty("identificacion");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}