package utils.Estilo;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

public class ImageLoader {
    public static ImageIcon cargarImagenPintura(String path, int width, int height) {
        File imagenFile = new File(path);
        if (imagenFile.exists()) {
            try {
                Image img = javax.imageio.ImageIO.read(imagenFile);
                ImageIcon icon = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                return icon;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; 
    }
}
