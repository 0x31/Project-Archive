package comp1140.ass2;

import java.util.List;

/**
 * Created by ***REMOVED*** on 22/08/15.
 * TO DO:
 *        - make interface nicer, at the moment one can only move and orinetate a piece once.
 *          It might be necessary/nicer to be able to say "Shift piece down one, rotate once".
 *        - maybe have a different methods. one for initialising based on input, and one for
 *          manipulating the piece from there.
 */
public class TimsPiece {
    Shape shape;
    Colour colour;

    public TimsPiece(Shape id, Colour c) {
        shape = id; colour = c;
    }

    /*
       Takes the piece's intended base coordinate, and it's orientation [int: 0 - 8]
       and returns a list of intended coordinates.
     */

    public Coordinate[] getCoordinates(Coordinate homeCoord, char orientation) {
        setCoordinates(homeCoord, orientation);
        return shape.getHomeCoordinates();
    }

    private void setCoordinates(Coordinate homeCoord, char orientation) {
        shape.orientatePiece(orientation);
        shape.shiftPiece(homeCoord);
    }

    @Override
    public String toString() {
        return shape.toString();
    }
}
