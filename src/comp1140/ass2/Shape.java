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
    C(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), null                 , null                 ),
    D(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), null                 , null                 ),
    E(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate( 0, 3), null                 ),
    F(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate( 1, 2), null                 ),
    G(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 0, 2), null                 ),
    H(new Coordinate( 0, 0), new Coordinate( 1, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), null                 ),
    I(new Coordinate( 0, 0), new Coordinate( 1, 0), new Coordinate( 1, 1), new Coordinate( 2, 1), null                 ),
    J(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate( 0, 3), new Coordinate( 0, 4)),
    K(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate(-1, 3), new Coordinate( 0, 3)),
    L(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate(-1, 2), new Coordinate( 0, 2), new Coordinate(-1, 3)),
    M(new Coordinate( 0, 0), new Coordinate(-1, 1), new Coordinate( 0, 1), new Coordinate(-1, 2), new Coordinate( 0, 2)),
    N(new Coordinate( 0, 0), new Coordinate( 1, 0), new Coordinate( 1, 1), new Coordinate( 0, 2), new Coordinate( 1, 2)),
    O(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 0, 2), new Coordinate( 0, 3)),
    P(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate(-1, 1), new Coordinate( 0, 2), new Coordinate( 1, 2)),
    Q(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate( 1, 2), new Coordinate( 2, 2)),
    R(new Coordinate( 0, 0), new Coordinate( 1, 0), new Coordinate( 1, 1), new Coordinate( 2, 1), new Coordinate( 2, 2)),
    S(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 2, 1), new Coordinate( 2, 2)),
    T(new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 2, 1), new Coordinate( 1, 2)),
    U(new Coordinate( 0, 0), new Coordinate(-1, 1), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 0, 2));

    private Coordinate homeCoordinates[] = new Coordinate[5];
    private Coordinate rotatedCoordinates[] = new Coordinate[5];

    Shape(Coordinate coord0, Coordinate coord1, Coordinate coord2, Coordinate coord3, Coordinate coord4) {
         if (coord0 != null) homeCoordinates[0] = coord0;
         if (coord1 != null) homeCoordinates[1] = coord1;
         if (coord2 != null) homeCoordinates[2] = coord2;
         if (coord3 != null) homeCoordinates[3] = coord3;
         if (coord4 != null) homeCoordinates[4] = coord4;
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

    public void orientatePiece(char orientation) {
        switch (orientation) {
            case 'A':
                break;
            case 'B':
                rotatePiece();
                break;
            case 'C':
                rotatePiece();
                rotatePiece();
                break;
            case 'D':
                rotatePiece();
                rotatePiece();
                rotatePiece();
                break;
            case 'E':
                flipPiece();
                break;
            case 'F':
                flipPiece();
                rotatePiece();
                break;
            case 'G':
                flipPiece();
                rotatePiece();
                rotatePiece();
                break;
            case 'H':
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


