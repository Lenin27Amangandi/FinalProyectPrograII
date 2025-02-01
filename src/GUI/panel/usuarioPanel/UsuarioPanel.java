package GUI.panel.usuarioPanel;

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
import utils.Estilo.ComponentFactory;
import utils.Estilo.EstiloFuenteYColor;

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
        tablaUsuarios.getSelectionModel().addListSelectionListener(_ -> mostrarFormularioModificar());
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
        btnAgregarUsuario = ComponentFactory.crearBoton("Agregar", _ -> mostrarFormularioAgregar());
        btnModificarUsuario = ComponentFactory.crearBoton("Modificar", _ -> activarModoModificar());
        btnEliminarUsuario = ComponentFactory.crearBoton("Eliminar", _ -> eliminarUsuario());

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
    
        JButton btnGuardar = ComponentFactory.crearBotonExito("Guardar", _ -> {
            try {
                agregarUsuario(comboRoles);  
            } catch (HeadlessException | SQLException e) {
                e.printStackTrace();
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
                JOptionPane.showMessageDialog(this, "Error: La identificación ya está registrada en otro usuario.");
                return;
            }
    
            if (usuarioDAO.existeUsername(connection, username)) {
                JOptionPane.showMessageDialog(this, "Error: El nombre de usuario ya existe.");
                return;
            }
    
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setNombre(nombre);
            usuario.setIdRol(idRol);
            usuario.setIdentificacion(identificacion);
            usuario.setUsername(username);
            usuario.setPassword(password);
    
            usuarioDAO.insertarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuario agregado correctamente.");
    
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
            panelFormulario.add(txtIdentificacion);

            JButton btnModificar = ComponentFactory.crearBoton("Modificar", _ -> {
                try {
                    modificarUsuario(row, comboRolesModificar);  
                } catch (SQLException e) {
                    e.printStackTrace();
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
                JOptionPane.showMessageDialog(this, "Usuario modificado.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar usuario.");
            }

            cargarUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Se ha producido un error al modificar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea Eliminar este usuario?", "Confirmar Eliminar Usuario", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (usuarioDAO.desactivarUsuario(identificacionUsuario)) {
                        JOptionPane.showMessageDialog(this, "Usuario Eliminado correctamente.");
                        cargarUsuarios(); 
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al Eliminar el usuario.");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al intentar Eliminar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario.");
        }
    }
}
