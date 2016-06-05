package PNG.Filter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nosha on 30/05/2016.
 */
public class NoneFilter extends Filter {

    public NoneFilter(InputStream imageFile, int length) {
        this.imageFile = imageFile;
        this.length = length;
    }

    @Override
    public int read(int left, int top, int lefttop) throws IOException {
        return read();
    }
}
