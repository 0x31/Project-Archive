package PNG.Filter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nosha on 30/05/2016.
 */
public class PaethFilter extends Filter {

    public PaethFilter(InputStream imageFile, int length) {
        this.imageFile = imageFile;
        this.length = length;
    }

    public int PaethPredictor (int a, int b, int c) {
        int p = a + b - c;
        int pa = Math.abs(p - a);
        int pb = Math.abs(p - b);
        int pc = Math.abs(p - c);

        if (pa <= pb && pa<=pc) return a;
        else if (pb <= pc) return b;
        else return c;
    }

    @Override
    public int read(int left, int top, int lefttop) throws IOException {
        if(count<length) {
            int r = imageFile.read();
            count++;
            if(r==-1) return -1;
            return (int) (r+ + PaethPredictor(left, top, lefttop)) % 256;
        }
        return -1;
    }
}
