package util;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class BackgroundDrawer {

    private Graphics graphics;
    
    public BackgroundDrawer(Graphics g) {
        graphics = g;
    }
    
    public void show (int month) throws Exception
    
    {
        String pic = "";


        if(month == 1)
            pic = "background.jpg";

        else if(month == 2)
            pic = "background2.jpg";

        else if(month == 3)
            pic = "background3.jpg";

        else if(month == 4)
            pic = "background4.jpg";

        else if(month == 5)
            pic = "background5.jpg";

        else if(month == 6)
            pic = "background6.jpg";

        else if(month == 7)
            pic = "background7.jpg";

        else if(month == 8)
            pic = "background8.jpg";

        else if(month == 9)
            pic = "background9.jpg";

        else if(month == 10)
            pic = "background10.jpg";

        else if(month == 11)
            pic = "background11.jpg";

        else if(month == 12)
            pic = "background12.jpg";

        Image image_ = ImageIO.read(
                getClass().getResourceAsStream("notes.png"));
        graphics.drawImage(image_, 520, 60, 40, 40, null);

        Image image = ImageIO.read(
                getClass().getResourceAsStream(pic));
        graphics.drawImage(image, 680, 49, 550, 710, null);

        //draw left/right buttons
        Image image2 = ImageIO.read(
                getClass().getResourceAsStream("lft.png"));
        graphics.drawImage(image2, 580, 70, 33, 33, null);

        Image image4 = ImageIO.read(
                getClass().getResourceAsStream("rgh.png"));
        graphics.drawImage(image4, 615, 70, 33, 33, null);
    }

    public void show2(int month) throws Exception
    
    {
        String pic = "";


        if(month == 1)
            pic = "background.jpg";

        else if(month == 2)
            pic = "background2.jpg";

        else if(month == 3)
            pic = "background3.jpg";

        else if(month == 4)
            pic = "background4.jpg";

        else if(month == 5)
            pic = "background5.jpg";

        else if(month == 6)
            pic = "background6.jpg";

        else if(month == 7)
            pic = "background7.jpg";

        else if(month == 8)
            pic = "background8.jpg";

        else if(month == 9)
            pic = "background9.jpg";

        else if(month == 10)
            pic = "background10.jpg";

        else if(month == 11)
            pic = "background11.jpg";

        else if(month == 12)
            pic = "background12.jpg";


        Image image_ = ImageIO.read(
                getClass().getResourceAsStream("notes.png"));
        graphics.drawImage(image_, 520, 60, 40, 40, null);

        Image image = ImageIO.read(
                getClass().getResourceAsStream(pic));
        graphics.drawImage(image, 0, 49, 530, 950, null);

        //draw left/right buttons
        Image image2 = ImageIO.read(
                getClass().getResourceAsStream("lft.png"));
        graphics.drawImage(image2, 580, 70, 33, 33, null);

        Image image4 = ImageIO.read(
                getClass().getResourceAsStream("rgh.png"));
        graphics.drawImage(image4, 615, 70, 33, 33, null);
    }
}
