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

    /**
     * Initialises a Piece object from a shape id and a colour
     * @param id the piece shape, e.g. shape.D
     * @param c  one of: Red, Blue, Green, Yellow
     */
    public Piece(Shape id, Colour c) {
        shape = id; colour = c;
    }
    /**
     * Piece's toString function, currently used for debugging.
     * @return String a string representation of the piece, made up of the colour,
     *         shape and coordinates of each cell.
     */
    @Override
    public String toString() {
        return colour + " " + shape.toString();
    }
}
