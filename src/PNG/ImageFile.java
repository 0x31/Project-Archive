package PNG;

import java.io.*;

/**
 * Created on 28/05/2016.
 */
public class ImageFile extends InputStream {

    String filename;
    BufferedInputStream inputStream;
    //byte[] buffer = new byte[100];
    //int to_go = 0;
    //int index = 0;

    public ImageFile(String filename) throws FileNotFoundException {
        this.filename = filename;
        inputStream = new BufferedInputStream(new FileInputStream(filename));
    }

    @Override
    public int read() throws IOException {
        /*if(to_go==0) {
            to_go = inputStream.read(buffer);
            index = 0;
        }
        if(to_go == -1) return -1;
        int r = buffer[index];
        index++;
        to_go--;
        return r & 0xFF;*/
        return (int) (inputStream.read());
    }

}
