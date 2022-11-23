import PNG.PNG;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by nosha on 30/05/2016.
 */
@RunWith(Parameterized.class)
public class MainTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        String suiteDir = "tests/suite/";
        ArrayList<Object[]> arrayList = new ArrayList();
        File directory = new File(suiteDir);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                arrayList.add(new Object[] { suiteDir + file.getName() });
                //System.out.println(file.getName());
            }
        }
        return arrayList;
        /*return Arrays.asList(new Object[][] {
                { "png/check.png" }, { "png/tree.png" }, { "png/test.png" }
        });*/
    }

    private String filename;

    public MainTest(String filename) {
        this.filename = filename;
    }

    private BufferedImage defaultPNG(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    @Test
    public void test() throws IOException, PNG.PNGException {

        System.out.println("Testin "+filename);
        PNG png = new PNG(new FileInputStream(filename));
        BufferedImage cstm_png = png.toBufferedImage();

        BufferedImage deft_png = defaultPNG(filename);

        assertEquals(cstm_png.getWidth(), deft_png.getWidth());
        assertEquals(cstm_png.getHeight(), deft_png.getHeight());

        int crc = 0;
        for(int i = 0; i < cstm_png.getWidth(); i++) {
            for(int j = 0; j < cstm_png.getHeight(); j++) {
                int cstm_rgb = cstm_png.getRGB(i,j);
                int deft_rgb = deft_png.getRGB(i,j);
                if((Math.abs((cstm_rgb+0.1)/(deft_rgb+0.1))-1)>0.05) {
                    crc += Math.abs(cstm_png.getRGB(i,j) - deft_png.getRGB(i,j));
                    //System.out.println("cstm: "+cstm_rgb+", deft: "+deft_rgb);
                }
            }
        }
        assertEquals(crc,0);
    }

}