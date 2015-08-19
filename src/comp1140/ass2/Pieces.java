package comp1140.ass2;

/**
 * Created by nosha on 19/08/15.
 */
public abstract class Pieces {
    private Orientation orientation;
    private Coordinate coordinate;
    private Colour color;
    private Coordinate[] occupiedCells;

    public Coordinate[] getOccupiedCells() {
        return new Coordinate[0];
    }

}
