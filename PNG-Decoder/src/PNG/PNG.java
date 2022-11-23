package PNG;

import PNG.Filter.*;

import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 28/05/2016.
 * Based on PNG specification:
 *      http://libpng.org/pub/png/spec/1.2/png-1.2.pdf
 *
 */
public class PNG {

    InputStream imageFile;

    BufferedImage bufferedImage;

    long width;
    long height;
    int bit_depth;
    int color_type;
    int compression_method;
    int filter_method;
    int interlace_method;

    public static class PNGException extends Exception {
        public PNGException(String message) {
            super(message);
        }
    }

    public PNG(InputStream imageFile) throws IOException, PNGException {
        this.imageFile = imageFile;
        decode();
    }

    void parseIHDR(Chunk ihdr) throws IOException {
        System.out.println("IHDR.");
        width = ihdr.read(4);
        height = ihdr.read(4);
        System.out.println("w: "+width+", h: " + height);
        bit_depth = ihdr.read();
        System.out.println("depth: "+bit_depth);
        color_type = ihdr.read();
        System.out.println("type: "+color_type);
        compression_method = ihdr.read();
        filter_method = ihdr.read();
        interlace_method = ihdr.read();
        System.out.println("interlace: "+interlace_method);
        bufferedImage = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_ARGB);
    }

    void parsetEXt(Chunk text) throws IOException {
        System.out.println("tEXt.");
        String before = "";
        String after = "";
        int s;
        while( (s=text.read()) != 0 ) {
            before+=(char) s;
        }
        text.skip();
        System.out.println(before);
    }

    boolean firstIDAT = true;

    InflaterStream inflater;

    int[] previousLine;
    int[] currentLine;
    Filter filter;
    int currentRow;
    int currentCol;
    int c;

    void parseIDAT(Chunk data) throws IOException {
        //System.out.println("IDAT.");
        //System.out.println(data.length);

        int bpp = 1;
        if(color_type==4) bpp = 4;
        if(color_type==2) bpp = bit_depth / 3 * 2;
        if(color_type==2) bpp = bit_depth / 8 * 3;
        if(color_type==6) bpp = bit_depth / 8 * 4;
        if(firstIDAT) {
            inflater = new InflaterStream(data);
            previousLine = new int[(int) width * bpp];
            currentLine = new int[(int) width * bpp];
            filter = null;
            currentRow = -1;
            currentCol = 0;
            c = 0;
            firstIDAT = false;
        } else {
            inflater.in = data;
        }

        while(inflater.available()==1) {
            if(c%(width*bpp+1)==0) {
                //System.out.println("Row: "+(currentRow+1)+" vs "+height);
                int[] swap = previousLine;
                previousLine = currentLine;
                currentLine = swap;
                if(c>0) {
                    for(int i=0;i<width*bpp;i+=bpp) {
                        int r = 0, g=0, b=0, a=255;
                        if(color_type == 6) {
                            if(bpp == 8) {
                                r = (int) ((previousLine[(int) (i + 0)] * 1 + 0 * previousLine[(int) (i + 1)]) );
                                g = (int) ((previousLine[(int) (i + 2)] * 1 + 0 * previousLine[(int) (i + 3)]) );
                                b = (int) ((previousLine[(int) (i + 4)] * 1 + 0 * previousLine[(int) (i + 5)]) );
                                a = (int) ((previousLine[(int) (i + 6)] * 1 + 0 * previousLine[(int) (i + 7)]) );
                            } else {
                                r = previousLine[(int) (i + 0)];
                                g = previousLine[(int) (i + 1)];
                                b = previousLine[(int) (i + 2)];
                                a = previousLine[(int) (i + 3)];
                            }
                        } else if (color_type == 2) {
                            if(bpp == 6) {
                                r = (int) ((previousLine[(int) (i + 0)] * 1 + 0 * previousLine[(int) (i + 1)]) );
                                g = (int) ((previousLine[(int) (i + 2)] * 1 + 0 * previousLine[(int) (i + 3)]) );
                                b = (int) ((previousLine[(int) (i + 4)] * 1 + 0 * previousLine[(int) (i + 5)]) );
                            } else {
                                r = previousLine[(int) (i + 0)];
                                g = previousLine[(int) (i + 1)];
                                b = previousLine[(int) (i + 2)];
                            }
                        }
                        else {
                            r = previousLine[(int) (i + 0)];
                        }
                        int col = (a << 24) | (r << 16) | (g << 8) | b;
                        bufferedImage.setRGB(i/bpp,currentRow,col);
                    }
                }
                currentRow++;
                currentCol=0;

                int filtern = inflater.read();
                if (filtern==-1) break;
               switch(filtern) {
                    case 0: filter = new NoneFilter(inflater, (int) width*bpp); break;
                    case 1: filter = new SubFilter(inflater, (int) width*bpp); break;
                    case 2: filter = new UpFilter(inflater, (int) width*bpp); break;
                    case 3: filter = new AverageFilter(inflater, (int) width*bpp); break;
                    case 4: filter = new PaethFilter(inflater, (int) width*bpp); break;
                    default: filter = new NoneFilter(inflater, (int) width*bpp); break;
                }
            } else {
                int prev_up = previousLine[currentCol*bpp+(c-currentRow-1)%bpp];
                int prev_left = 0;
                int prev_leftup = 0;
                if(currentCol>0) {
                    prev_left = currentLine[(currentCol-1)*bpp+(c-currentRow-1)%bpp];
                    prev_leftup = previousLine[(currentCol-1)*bpp+(c-currentRow-1)%bpp];
                }
                int re=0;
                try {
                    re = filter.read(prev_left, prev_up, prev_leftup) & 0xFF;
                } catch (EOFException e) {
                    break;
                }
                currentLine[currentCol*bpp+(c-currentRow-1)%bpp]=re;
                if((c-currentRow-1)%bpp==bpp-1) {
                    currentCol++;
                }
            }

            c++;
        }
    }

    void parsePLTE(Chunk plte) throws IOException {
        System.out.println("PLTE.");
        int[] bytes = new int[(int) (plte.length)];
        for(int i = 0; i<plte.length; i++) {
            bytes[i] = (int) plte.read();
            System.out.println(bytes[i]);
        }
    }


    // Decode from byte buffer to BufferedImage
    void decode() throws IOException, PNGException {
        int next;
        int[] header = new int[] {137, 80, 78, 71, 13, 10, 26, 10};
        for(int i = 0; i < header.length; i++) {
            next = imageFile.read();
            if(next != header[i]) {
                throw new PNGException("Invalid header. Expected "+header[i]+", got "+next);
            }
        }

        boolean parsedIEND = false;
        int count = 0;
        while(!parsedIEND) {
            Chunk chunk = new Chunk(imageFile);
            if(chunk.toString().equals("IHDR")) {
                parseIHDR(chunk);
            } else if(chunk.toString().equals("tEXt")) {
                parsetEXt(chunk);
            } else if(chunk.toString().equals("IDAT")) {
                parseIDAT(chunk);
            } else if(chunk.toString().equals("PLTE")) {
                parsePLTE(chunk);
            } else if(chunk.toString().equals("IEND")) {
                System.out.println(chunk.toString());
                parsedIEND = true;
            } else {
                System.out.println("CHUNK: "+chunk.toString());
                chunk.skip();
            }
            count++;
        }

    }


    public BufferedImage toBufferedImage() {
        return bufferedImage;
    }

}
