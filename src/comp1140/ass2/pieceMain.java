package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 22/08/15.
 */


public class pieceMain {
    public static void main(String[] args) {


        TimsPiece myPiece = new TimsPiece(Shape.T, Colour.Red);
        System.out.println(myPiece);

        TimsPiece yourPiece = new TimsPiece(Shape.A, Colour.Blue);
        Coordinate[] yourCoords;
        yourCoords = yourPiece.getCoordinates(new Coordinate(1,1), 'C');
        System.out.println(yourPiece);


        for (Coordinate c: yourCoords) {
            System.out.println(c);
        }

        Coordinate[] myCoords;
        myCoords = myPiece.getCoordinates(new Coordinate(5, 5), 'F');

        System.out.println(myPiece);

        for (Coordinate c: myCoords) {
            System.out.println(c);
        }

        /*myPiece.rotatePiece();

        System.out.println(myPiece);
        myPiece.orientatePiece(2);
        System.out.println(myPiece);

        Coordinate myCoord = new Coordinate(1,2);
        System.out.println(myCoord);
        Coordinate newCoord = myCoord.shiftCoordinate(new Coordinate(-2, 5));
        System.out.println(myCoord);
        System.out.println(newCoord);*/

        Coordinate myCoord = new Coordinate(1,2);
        Coordinate yourCoord = new Coordinate(1,2);
        System.out.println(myCoord.equals(yourCoord));
    }
}
