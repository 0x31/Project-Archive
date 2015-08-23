package comp1140.ass2;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by ***REMOVED*** on 22/08/15.
 */
public class PiecesTest {
    public void checkGetCoordinates() {
        TimsPiece myPiece = new TimsPiece(Shape.T, Colour.Red);

        Coordinate[] is     = myPiece.getCoordinates(new Coordinate(0,0), 'A');
        Coordinate[] should = {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 2, 1), new Coordinate( 1, 2)};
        assertTrue("Error: getCoordinatesHasFailed", is.equals(should));
    }
}
