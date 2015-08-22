package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 22/08/15.
 */
public class testMain {
    public static void main(String[] args) {


        TimsPiece myPiece = new TimsPiece(Shape.T);
        System.out.println(myPiece);

        myPiece.setCoordinates(new Coordinate(5, 5), 5);
        System.out.println(myPiece);

        /*myPiece.rotatePiece();

        System.out.println(myPiece);
        myPiece.orientatePiece(2);
        System.out.println(myPiece);

        Coordinate myCoord = new Coordinate(1,2);
        System.out.println(myCoord);
        Coordinate newCoord = myCoord.shiftCoordinate(new Coordinate(-2, 5));
        System.out.println(myCoord);
        System.out.println(newCoord);*/
    }
}
