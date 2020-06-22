
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JSplashScreen extends Object {
    
    public void show() {
        JFrame j = new JFrame();
        // make the frame half the height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        j.setBounds(0, 0, 775, 340);

        // here's the part where i center the jframe on screen
        j.setLocationRelativeTo(null);
        JPanel p = new JPanel();
        p.setBounds(j.getBounds());
        j.add(p);
        j.setVisible(true);
        int theX = -9;
        Graphics g = p.getGraphics();
        try {
            Image image = ImageIO.read(getClass().getResourceAsStream("splashscreen.jpg"));
            g.drawImage(image, 0, 0, 775, 340, null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(3000);
        } catch(Exception e) {
            e.printStackTrace();
        }
        j.dispose();
    }
}