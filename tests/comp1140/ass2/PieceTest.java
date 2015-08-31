package comp1140.ass2;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by ***REMOVED*** on 22/08/15.
 */
public class PieceTest {
    public static void main(String[] args) {
    }
    @Test
    public void checkGetCoordinates() {

        Piece myPiece = new Piece(Shape.S, Colour.Red);
        Coordinate[] myCoords;
        myPiece.shape.initialisePiece(new Coordinate(5,3), 'C');
        myCoords = myPiece.shape.getOccupiedCells();

        Piece yourPiece = new Piece(Shape.S, Colour.Red);
        Coordinate[] yourCoords;
        yourPiece.shape.initialisePiece(new Coordinate(0,0), 'A');
        yourPiece.shape.movePiece(new Coordinate(5, 3), 2, false);
        yourCoords = yourPiece.shape.getOccupiedCells();


        for (int i = 0; i < myPiece.shape.getCellNumber(); i++) {
            assertTrue(myCoords[i].equals(yourCoords[i]));
        }
    }
}
