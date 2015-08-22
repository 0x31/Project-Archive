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
    public List<Coordinate> getCoordinates(Coordinate homeCoord, int orientation) {

        return null;
    }

    public void orientatePiece(int option) {
        shape.orientatePiece(option);
    }

    public void rotatePiece() {
        shape.flipPiece();
    }


    @Override
    public String toString() {
        return shape.toString();
    }
}
