package GUI.Forms.usuarioPanel;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import DataAccess.DAO.UsuarioDAO;
import DataAccess.DTO.UsuarioDTO;
import DataAccess.DataHelper.DbHelper;
import Framework.RAConfig;
import GUI.Estilo.ComponentFactory;
import GUI.Estilo.EstiloFuenteYColor;

public class UsuarioPanel extends JPanel {
    private JTable tablaUsuarios;
    private JButton btnAgregarUsuario;
    private JButton btnModificarUsuario;
    private JButton btnEliminarUsuario;
    private UsuarioDAO usuarioDAO;
    private JPanel panelFormulario;
    private JPasswordField txtPassword;
    @SuppressWarnings("unused")
    private boolean modificando = false;
    private JTextField txtNombre, txtIdentificacion, txtUsername;

    public UsuarioPanel(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        setLayout(new BorderLayout());
        setBackground(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);

        tablaUsuarios = new JTable();
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> mostrarFormularioModificar());
        tablaUsuarios.setRowHeight(25);
        tablaUsuarios.setGridColor(EstiloFuenteYColor.COLOR_FONDO_SIDEBAR);
        tablaUsuarios.setFont(EstiloFuenteYColor.FUENTE_CAMPO_TEXTO);

        JTableHeader header = tablaUsuarios.getTableHeader();
        header.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        header.setForeground(EstiloFuenteYColor.COLOR_TEXTO);
        header.setFont(EstiloFuenteYColor.FUENTE_TABLA);

        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        scrollUsuarios.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollUsuarios, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        btnAgregarUsuario = ComponentFactory.crearBoton("Agregar", e -> mostrarFormularioAgregar());
        btnModificarUsuario = ComponentFactory.crearBoton("Modificar", e -> activarModoModificar());
        btnEliminarUsuario = ComponentFactory.crearBoton("Eliminar", e -> eliminarUsuario());

        panelBotones.add(btnAgregarUsuario);
        panelBotones.add(btnModificarUsuario);
        panelBotones.add(btnEliminarUsuario);
        add(panelBotones, BorderLayout.NORTH);

        panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBackground(EstiloFuenteYColor.COLOR_FONDO_CLARO);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(panelFormulario, BorderLayout.SOUTH);

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioDAO.obtenerTodosUsuarios();
    
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Rol");
        model.addColumn("Identificación");
    
        for (UsuarioDTO usuario : usuarios) {
            model.addRow(new Object[]{
                usuario.getNombre(),
                usuario.getNombreRol(), 
                usuario.getIdentificacion()
            });
        }
        tablaUsuarios.setModel(model);
    }

    private void mostrarFormularioAgregar() {
        panelFormulario.removeAll();

        panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Nombre:"));
        txtNombre = ComponentFactory.crearCampoTextoTransparente("");  
        panelFormulario.add(txtNombre);
    
        panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Rol:"));
        JComboBox<String> comboRoles = new JComboBox<>(new String[]{"Administrador", "Supervisor"});
        panelFormulario.add(comboRoles);
    
        panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Identificación:"));
        txtIdentificacion = ComponentFactory.crearCampoTextoTransparente("");  
        panelFormulario.add(txtIdentificacion);

        panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Nombre de usuario:"));
        txtUsername = ComponentFactory.crearCampoTextoTransparente("");  
        panelFormulario.add(txtUsername);
        
        panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Contraseña:"));
        txtPassword = ComponentFactory.crearCampoTextoPasswordTransparente();  
        panelFormulario.add(txtPassword);
    
        JButton btnGuardar = ComponentFactory.crearBotonExito("Guardar", e -> {
            try {
                agregarUsuario(comboRoles);  
            } catch (HeadlessException | SQLException ex) {
                RAConfig.showMsgError("Error al ir al agregar Usuarios");
            }
        });
        panelFormulario.add(btnGuardar);
    
        revalidate();
        repaint();
    }

    private void agregarUsuario(JComboBox<String> comboRoles) throws HeadlessException, SQLException {
        String nombre = txtNombre.getText();
        String nombreRol = (String) comboRoles.getSelectedItem();
        String identificacion = txtIdentificacion.getText();
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
    
        try (Connection connection = DbHelper.getConnection()) {
            int idRol = usuarioDAO.obtenerRolId(nombreRol, connection);
    
            if (usuarioDAO.existeIdentificacion(connection, identificacion)) {
                RAConfig.showMsgError("Error: La identificación ya está registrada en otro usuario.");
                return;
            }
    
            if (usuarioDAO.existeUsername(connection, username)) {
                RAConfig.showMsgError("Error: El nombre de usuario ya existe.");
                return;
            }
    
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setNombre(nombre);
            usuario.setIdRol(idRol);
            usuario.setIdentificacion(identificacion);
            usuario.setUsername(username);
            usuario.setPassword(password);
    
            usuarioDAO.insertarUsuario(usuario);
            RAConfig.showMsg( "Usuario agregado correctamente.");
    
            cargarUsuarios();
        }
    }

    private void mostrarFormularioModificar() {
        int row = tablaUsuarios.getSelectedRow();
        
        if (row != -1) {
            String nombre = (String) tablaUsuarios.getValueAt(row, 0);
            String rol = (String) tablaUsuarios.getValueAt(row, 1);
            String identificacion = (String) tablaUsuarios.getValueAt(row, 2);

            panelFormulario.removeAll();


            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Nombre:"));
            txtNombre = ComponentFactory.crearCampoTextoTransparente(nombre); 
            panelFormulario.add(txtNombre);

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Rol:"));
            JComboBox<String> comboRolesModificar = new JComboBox<>(new String[]{"Administrador", "Supervisor"});
            comboRolesModificar.setSelectedItem(rol);  
            panelFormulario.add(comboRolesModificar);

            panelFormulario.add(EstiloFuenteYColor.crearTextoFormularios("Identificación:"));
            txtIdentificacion = ComponentFactory.crearCampoTextoTransparente(identificacion); 
            txtIdentificacion.setEditable(false); 
            panelFormulario.add(txtIdentificacion);

            JButton btnModificar = ComponentFactory.crearBoton("Modificar", e -> {
                try {
                    modificarUsuario(row, comboRolesModificar);  
                } catch (SQLException ex) {
                    RAConfig.showMsgError("Error al ir a Modificar");
                }
            });

            panelFormulario.add(btnModificar);

            revalidate();
            repaint();
        }
    }

    private void modificarUsuario(int row, JComboBox<String> comboRoles) throws SQLException {
        String identificacion = (String) tablaUsuarios.getValueAt(row, 2); 
        String nuevoNombre = txtNombre.getText();
        String nuevoRol = (String) comboRoles.getSelectedItem();  

        try (Connection connection = DbHelper.getConnection()) {
            int idRol = usuarioDAO.obtenerRolId(nuevoRol, connection);  

            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setIdentificacion(identificacion);  
            usuario.setNombre(nuevoNombre);
            usuario.setIdRol(idRol);  

            if (usuarioDAO.actualizarUsuario(usuario)) {
                RAConfig.showMsg("Usuario modificado.");
            } else {
                RAConfig.showMsgError( "Error al modificar usuario.");
            }

            cargarUsuarios();
        } catch (SQLException e) {
            RAConfig.showMsgError("Se ha producido un error al modificar el usuario: " + e.getMessage());
        }
    }

    private void activarModoModificar() {
        modificando = true;
        mostrarFormularioModificar();
    }

    private void eliminarUsuario() {
        int row = tablaUsuarios.getSelectedRow();
        if (row != -1) {
            String identificacionUsuario = (String) tablaUsuarios.getValueAt(row, 2); 
            try {
                boolean confirm = RAConfig.showConfirmYesNo("Esta seguro que desea Eliminar este Usuario?");
                if (confirm == true) {
                    if (usuarioDAO.desactivarUsuario(identificacionUsuario)) {
                        RAConfig.showMsg( "Usuario Eliminado correctamente.");
                        cargarUsuarios(); 
                    } else {
                        RAConfig.showMsgError("Error al Eliminar el usuario.");
                    }
                }
            } catch (Exception e) {
                RAConfig.showMsgError("Error al intentar Eliminar el usuario: " + e.getMessage());
            }
        } else {
            RAConfig.showMsg( "Por favor, selecciona un usuario.");
        }
    }
}
