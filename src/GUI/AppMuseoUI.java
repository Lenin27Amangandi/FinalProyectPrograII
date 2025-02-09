package GUI;

import javax.swing.*;

import DataAccess.DAO.UsuarioDAO;
import GUI.Forms.commonPanel.InicioPanel;
import GUI.Forms.commonPanel.SplashScreenPanel;


public class AppMuseoUI {
    private final JFrame frame;

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
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.cargarUsuarioPorDefecto(); 

                Thread.sleep(3550); 
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                loadingPanel.stopLoadingAnimation();
                loadingFrame.dispose();
                mostrarInicio();  
            });
        }).start();
    }

    private void mostrarInicio(){
        frame.add(new InicioPanel(frame));
        frame.setSize(800, 610);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
