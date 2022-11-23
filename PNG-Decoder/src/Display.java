import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created on 28/05/2016.
 */

class Display extends Canvas {

    BufferedImage bufferedImage;

    public Display(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void paint(Graphics g)
    {
        int x = Math.max(0,(200-bufferedImage.getWidth())/2);
        int y = Math.max(0,(200-bufferedImage.getHeight())/2);
        g.drawImage(bufferedImage, x, y, null);
        Dimension s = getSize();
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public Dimension getSize() {
        return new Dimension(getWidth(),getHeight());
    }
}

