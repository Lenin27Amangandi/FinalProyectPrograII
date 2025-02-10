import javax.swing.*;

import Framework.RAConfig;
import GUI.AppMuseoUI;

public class App {
    public static void main(String[] args) {

        try {
            System.out.println("Haz iniciado la App de ArtVision!! :)");
            JFrame frame = new JFrame("ArtVision");
            ImageIcon icono = new ImageIcon(App.class.getResource("/utils/icons/museo-britanico.png"));
            if (icono.getImage() == null) {
                throw new NullPointerException("No se encontró el icono.");
            }
            frame.setIconImage(icono.getImage());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            AppMuseoUI app = new AppMuseoUI(frame);
            app.iniciarApp();
            frame.setVisible(true);
        } catch (Exception e) {
            RAConfig.showMsgError("Ocurrió un error al iniciar la app: " + e.getMessage());
        }
    }
}
