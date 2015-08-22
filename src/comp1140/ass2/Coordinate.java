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

    public Coordinate shiftCoordinate(Coordinate shift) {
        return new Coordinate (x + shift.x, y + shift.y);
    }

    /*
       flips the coordinate across the y=0 axis
     */
    public Coordinate flipCoordinate() {
        return new Coordinate (-x, y);
    }

    /*
      rotates the coordinate clockwise about (0,0)
     */
    public Coordinate rotateCoordinate() {
        return new Coordinate (-y, x);
    }

    public Boolean equals(Coordinate o) {
        return (x == o.x && y == o.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
