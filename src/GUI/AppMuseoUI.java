package GUI;

import javax.swing.*;

import DataAccess.DAO.UsuarioDAO;
import GUI.panel.commonPanel.InicioPanel;
import GUI.panel.commonPanel.SplashScreenPanel;


public class AppMuseoUI {
    private final JFrame frame;

    /**
     * Constructor de la clase AppMuseo.
     * 
     * @param parentFrame El marco principal que contendra los componentes de la aplicacion.
     */
    public AppMuseoUI(JFrame parentFrame) {
        this.frame = parentFrame;
    }

    public void iniciarApp(){
        mostrarSpash();
    }

    private void mostrarSpash(){
        JFrame loadingFrame = new JFrame("Cargando la App...");
        loadingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadingFrame.setUndecorated(true);
        loadingFrame.setSize(300, 300);
        loadingFrame.setLocationRelativeTo(null);

        SplashScreenPanel loadingPanel = new SplashScreenPanel("Bienvenido!!");
        loadingFrame.setContentPane(loadingPanel);
        loadingFrame.setVisible(true);

        new Thread(() -> {
            try {
                // Llamamos a CrearDefaultUser() antes de continuar con el inicio de la app
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.CrearDefaultUser();  // Crear o verificar el usuario por defecto

                Thread.sleep(3550); // Simulamos la espera por la animación de carga
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                loadingPanel.stopLoadingAnimation();
                loadingFrame.dispose();
                mostrarInicio();  // Ahora mostramos el panel de inicio
            });
        }).start();
    }

    private void mostrarInicio(){
        frame.add(new InicioPanel(frame));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
