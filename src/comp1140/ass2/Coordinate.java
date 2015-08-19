package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 19/08/15.
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate fromString(String letters) {
        return new Coordinate(letters.charAt(0)-'A',letters.charAt(1)-'A');
    }
}
