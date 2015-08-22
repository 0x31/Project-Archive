package comp1140.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by ***REMOVED*** on 22/08/15.
 */
public enum Shape {
    A(new Coordinate( 0, 0), null                 , null                 , null                 , null                 ),
    B(new Coordinate( 0, 0), new Coordinate( 0, 1), null                 , null                 , null                 ),
    T(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 2, 1), new Coordinate( 1, 2)),
    U(new Coordinate( 0, 0), new Coordinate(-1, 1), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 0, 2));

    private Coordinate homeCoordinates[] = new Coordinate[5];
    private Coordinate rotatedCoordinates[] = new Coordinate[5];
//still unsure how to handle ArrayLists
//    private List<Coordinate> listOfHomeCoordinates;

    Shape(Coordinate coord0, Coordinate coord1, Coordinate coord2, Coordinate coord3, Coordinate coord4) {
         if (coord0 != null) homeCoordinates[0] = coord0;
         if (coord1 != null) homeCoordinates[1] = coord1;
         if (coord2 != null) homeCoordinates[2] = coord2;
         if (coord3 != null) homeCoordinates[3] = coord3;
         if (coord4 != null) homeCoordinates[4] = coord4;
//still unsure how to handle ArrayLists
//         listOfHomeCoordinates = new ArrayList<>(Arrays.asList(homeCoordinates));
    }

    public Coordinate[] getHomeCoordinates() {
        return homeCoordinates;
    }

    public void flipPiece() {
        for (int i = 0; i < 5; i++) {
            if (homeCoordinates[i] != null) {
                homeCoordinates[i] = homeCoordinates[i].flipCoordinate();
            }
        }
    }
    /*
      rotates the piece 90 degrees clockwise around (0,0)
     */
    public void rotatePiece() {
        for (int i = 0; i < 5; i++) {
            if (homeCoordinates[i] != null) {
                homeCoordinates[i] = homeCoordinates[i].rotateCoordinate();
            }
        }
    }

    public void orientatePiece(int option) {
        switch (option) {
            case 0:
                break;
            case 1:
                rotatePiece();
                break;
            case 2:
                rotatePiece();
                rotatePiece();
                break;
            case 3:
                rotatePiece();
                rotatePiece();
                rotatePiece();
                break;
            case 4:
                flipPiece();
                break;
            case 5:
                flipPiece();
                rotatePiece();
                break;
            case 6:
                flipPiece();
                rotatePiece();
                rotatePiece();
                break;
            case 7:
                flipPiece();
                rotatePiece();
                rotatePiece();
                rotatePiece();
                break;
        }
    }
    /*
     shifts the piece across x and down y
     */
    public void shiftPiece(Coordinate shift) {
        for (int i = 0; i < 5; i++) {
            if (homeCoordinates[i] != null) {
                homeCoordinates[i] = homeCoordinates[i].shiftCoordinate(shift);
            }
        }
    }

    @Override
    public String toString() {
        String retString = super.toString();
        for(int j = 0; j<5; j++) {
            if (homeCoordinates[j] != null) {
                retString = retString + " " + homeCoordinates[j];
            }
        }
        return retString;
    }
}


