import PNG.ImageFile;
import PNG.PNG;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println(" Usage: ping filename");
            return;
        }

	    String filename = args[0];
        ImageFile imageFile;

        // Load file as byte buffer
        try {
            imageFile = new ImageFile(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Decode image file
        BufferedImage bufferedImage = null;
        try {
            PNG image = new PNG(imageFile);
            bufferedImage = image.toBufferedImage();
            while(bufferedImage.getWidth() > 600 || bufferedImage.getHeight() > 600) {
                bufferedImage = resize(bufferedImage, bufferedImage.getWidth()/2,bufferedImage.getHeight()/2);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (PNG.PNGException e) {
            e.printStackTrace();
            return;
        }

        // Show decoded image file
        Display display = null;
        if(bufferedImage != null) {
            display = new Display(bufferedImage);
        }


        JFrame frmMain = new JFrame();
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(display.getWidth());
        System.out.println(display.getHeight());
        frmMain.setSize(Math.max(200,display.getWidth()),20+Math.max(200,display.getHeight()));

        frmMain.add(display);
        frmMain.setVisible(true);

        Graphics g = display.getGraphics();




        BufferedImage bufferedImage1 = defaultPNG(filename);
        while(bufferedImage1.getWidth() > 600 || bufferedImage1.getHeight() > 600) {
            bufferedImage1 = resize(bufferedImage1, bufferedImage1.getWidth()/2,bufferedImage1.getHeight()/2);
        }

        Display display1 = new Display(bufferedImage1);
        System.out.println("type0: "+bufferedImage.getType());
        System.out.println("type1: "+bufferedImage1.getType());
        JFrame frmMain2 = new JFrame();
        frmMain2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(display1.getWidth());
        System.out.println(display1.getHeight());
        frmMain2.setSize(Math.max(200,display1.getWidth()),20+Math.max(200,display1.getHeight()));

        frmMain2.add(display1);
        frmMain2.setVisible(true);

        Graphics g2 = display1.getGraphics();


    }


    public static BufferedImage defaultPNG(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}