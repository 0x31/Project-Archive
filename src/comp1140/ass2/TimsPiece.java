package comp1140.ass2;

import java.util.List;

/**
 * Created by ***REMOVED*** on 22/08/15.
 */
public class TimsPiece {
    Shape shape;


    public TimsPiece(Shape id) {
        this.shape = id;
    }

    /*
       Takes the piece's intended home coordinate, and it's orientation [int: 0 - 8]
       and returns a list of intended coordinates.
     */
    public Coordinate[] getCoordinates(Coordinate homeCoord, int orientation) {
        setCoordinates(homeCoord, orientation);
        return shape.getHomeCoordinates();
    }

    public void setCoordinates(Coordinate homeCoord, int orientation) {
        shape.orientatePiece(orientation);
        shape.shiftPiece(homeCoord);
    }

    private void orientatePiece(int option) {
        shape.orientatePiece(option);
    }


    @Override
    public String toString() {
        return shape.toString();
    }
}
