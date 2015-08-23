package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 22/08/15.
 */


public class pieceMain {
    public static void main(String[] args) {
        Piece myPiece = new Piece(Shape.S, Colour.Red);
        Coordinate[] myCoords;
        myPiece.shape.initialisePiece(new Coordinate(5,3), 'C');
        //myCoords = myPiece.shape.getOccupiedCells();
        System.out.println(myPiece);

        myPiece.shape.movePiece(3, false);
        System.out.println(myPiece);
    }
}
