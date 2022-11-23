package PNG.Filter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nosha on 30/05/2016.
 */
public abstract class Filter extends InputStream {
    int count = 0;
    int length;
    InputStream imageFile;

    @Override
    public int read() throws IOException {
        if(count<length) {
            int r = imageFile.read();
            count++;
            if(r==-1) return -1;
            return r;
        }
        return -1;
    }
    public abstract int read(int left, int top, int lefttop) throws IOException;
}
