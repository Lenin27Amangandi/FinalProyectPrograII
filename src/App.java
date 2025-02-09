import java.awt.HeadlessException;
import javax.swing.*;

import Framework.RAConfig;
import GUI.AppMuseoUI;

public class App {
    public static void main(String[] args) {

        try {
            System.out.println("Haz iniciado la App de ArtVision!! :)");

            JFrame frame = new JFrame("ArtVision");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            AppMuseoUI app = new AppMuseoUI(frame);
            app.iniciarApp();
            
        } catch (HeadlessException e) {
            RAConfig.showMsgError("Ocurrió un error al iniciar la app: " + e.getMessage());
        }

    }
}
