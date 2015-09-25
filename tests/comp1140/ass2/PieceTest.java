package comp1140.ass2;

import comp1140.ass2.Game.Colour;
import comp1140.ass2.Game.Coordinate;
import comp1140.ass2.Game.Piece;
import comp1140.ass2.Game.Shape;
import org.junit.Test;

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
        myPiece.initialisePiece(new Coordinate(5,3), 'C');
        myCoords = myPiece.getOccupiedCells();

        Piece yourPiece = new Piece(Shape.S, Colour.Red);
        Coordinate[] yourCoords;
        yourPiece.initialisePiece(new Coordinate(0,0), 'A');
        yourPiece.movePiece(new Coordinate(5, 3), 2, false);
        yourCoords = yourPiece.getOccupiedCells();


        for (int i = 0; i < myPiece.shape.getCellNumber(); i++) {
            assertTrue(myCoords[i].equals(yourCoords[i]));
        }
    }
}
