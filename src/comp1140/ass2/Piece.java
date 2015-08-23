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
public class Piece {
    Shape shape;
    Colour colour;

    public Piece(Shape id, Colour c) {
        shape = id; colour = c;
    }

    @Override
    public String toString() {
        return colour + " " + shape.toString();
    }
}
