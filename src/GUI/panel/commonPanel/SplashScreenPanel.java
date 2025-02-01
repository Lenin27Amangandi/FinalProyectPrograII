package GUI.panel.commonPanel;

import utils.Estilo.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SplashScreenPanel extends JPanel {

    private int angle = 0;
    private Timer timer;
    private int progress = 0;
    private static final int MAX_ANGLE = 1080;
    private static final int MAX_PROGRESS = 100;
    private Image logo;

    /**
     * Constructor que inicializa el panel de carga con un mensaje de bienvenida y configura
     * el fondo, el logo y la animación de carga.
     *
     * @param mensajeBienvenida El mensaje de bienvenida que se mostrará en el panel.
     */
    public SplashScreenPanel(String mensajeBienvenida) {
        setLayout(null);
        setBackground(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);

        try {
            logo = ImageIO.read(new File("src/utils/Resources/logos/ArtVisionLogo.png"));
            if (logo == null) {
                System.err.println("No se pudo cargar la imagen: " + logo);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            logo = createDefaultImage();
        }
        startLoadingAnimation();
    }

    /**
     * Método que crea una imagen predeterminada en caso de que no se cargue la imagen principal.
     * 
     * @return Imagen predeterminada.
     */
    private Image createDefaultImage() {
        int width = 100, height = 100;
        BufferedImage defaultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = defaultImage.createGraphics();
        g2d.setColor(EstiloFuenteYColor.COLOR_BOTON_SIDEBAR);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(EstiloFuenteYColor.COLOR_TEXTO_BLANCO);
        g2d.drawString("Logo no disponible", 10, 50);
        g2d.dispose();
        return defaultImage;
    }

    /**
     * Método que inicia la animación de carga. Este método crea un temporizador que actualiza
     * el ángulo de rotación para el círculo animado y el porcentaje de carga.
     */
    private void startLoadingAnimation() {
        timer = new Timer(50, e -> {
            angle += 20;
            if (angle >= MAX_ANGLE) {
                angle = MAX_ANGLE;
                timer.stop();
            }

            progress = (int) ((double) angle / MAX_ANGLE * MAX_PROGRESS);
            repaint();
        });
        timer.start();
    }

    /**
     * Método que detiene la animación de carga cuando se llama, deteniendo el temporizador
     * y finalizando el progreso de carga.
     */
    public void stopLoadingAnimation() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * Método sobrecargado de paintComponent que se encarga de dibujar el contenido del panel:
     * 1. Logo centrado en la pantalla.
     * 2. Un círculo animado con el progreso de carga.
     * 3. El porcentaje de carga dentro del círculo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el logo
        int logoWidth = 360;
        int logoHeight = 360;

        int x = (getWidth() - logoWidth) / 2;
        int y = (getHeight() - logoHeight) / 2 - 30;

        g2d.drawImage(logo.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH), x, y, this);

        // Dibujar el círculo de progreso
        int circleRadius = 15;
        int centerX = getWidth() / 2;
        int centerY = getHeight() - 25;

        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(EstiloFuenteYColor.COLOR_LOGGIN); // Color del círculo
        g2d.drawArc(centerX - circleRadius, centerY - circleRadius, circleRadius * 2, circleRadius * 2, angle % 360, 270);

        // Dibujar el texto del porcentaje
        String percentageText = progress + "%";
        FontMetrics fm = g2d.getFontMetrics(EstiloFuenteYColor.FUENTE_LOGIN);
        int textWidth = fm.stringWidth(percentageText);
        int textHeight = fm.getHeight();

        g2d.setColor(EstiloFuenteYColor.COLOR_LOGGIN);
        g2d.setFont(EstiloFuenteYColor.FUENTE_LOGIN);
        g2d.drawString(percentageText, centerX - textWidth / 2, centerY + textHeight / 3);
    }
}
