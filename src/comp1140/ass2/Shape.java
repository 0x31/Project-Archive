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
    private Coordinate occupiedCells[] = new Coordinate[5];

    Shape(Coordinate coord0, Coordinate coord1, Coordinate coord2, Coordinate coord3, Coordinate coord4) {
         if (coord0 != null) homeCoordinates[0] = coord0; occupiedCells[0] = coord0;
         if (coord1 != null) homeCoordinates[1] = coord1; occupiedCells[1] = coord1;
         if (coord2 != null) homeCoordinates[2] = coord2; occupiedCells[2] = coord2;
         if (coord3 != null) homeCoordinates[3] = coord3; occupiedCells[3] = coord3;
         if (coord4 != null) homeCoordinates[4] = coord4; occupiedCells[4] = coord4;
    }

    public Coordinate[] getOccupiedCells() {
        return occupiedCells;
    }

    /* flips piece over the x=0 axis, maintaining the piece's origin */
    private void flipPiece() {
        Coordinate origin = new Coordinate (occupiedCells[0].getX(), occupiedCells[0].getY());
        for (int i = 0; i < 5; i++) {
            if (occupiedCells[i] != null) {
                occupiedCells[i] = occupiedCells[i].flipCoordinate();
            }
        }
        Coordinate shift = new Coordinate (origin.getX() - occupiedCells[0].getX(),
                                           origin.getY() - occupiedCells[0].getY());
        this.shiftPiece(shift);
    }

    /* rotates the piece 90 degrees clockwise around piece's origin */
    private void rotatePiece() {
        Coordinate origin = new Coordinate (occupiedCells[0].getX(), occupiedCells[0].getY());
        for (int i = 0; i < 5; i++) {
            if (occupiedCells[i] != null) {
                occupiedCells[i] = occupiedCells[i].rotateCoordinate();
            }
        }
        Coordinate shift = new Coordinate (origin.getX() - occupiedCells[0].getX(),
                                           origin.getY() - occupiedCells[0].getY());
        this.shiftPiece(shift);
    }

    private void orientatePiece(char orientation) {
        //Coordinate origin = new Coordinate (occupiedCells[0].getX(), occupiedCells[0].getY());
        //^^ need to allow for orientations not in base position
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
        /*Coordinate shift = new Coordinate (origin.getX() - occupiedCells[0].getX(),
                                           origin.getY() - occupiedCells[0].getY());
        this.shiftPiece(shift);*/
    }

    /*  shifts the piece across x and down y */
    private void shiftPiece(Coordinate shift) {
        for (int i = 0; i < 5; i++) {
            if (occupiedCells[i] != null) {
                occupiedCells[i] = occupiedCells[i].shiftCoordinate(shift);
            }
        }
    }

    public void initialisePiece(Coordinate origin, char orientation) {
        for (int i = 0; i < 5; i++) {
            occupiedCells[i] = homeCoordinates[i];
        }
        orientatePiece(orientation);
        shiftPiece(origin);
    }

    public void movePiece(Coordinate shift, int rotateClockwise, boolean flip) {
        if (flip) {
            flipPiece();
        }
        for (int i = 0; i < rotateClockwise; i++) {
            rotatePiece();
        }
        shiftPiece(shift);
    }

    public void movePiece(int rotateClockwise, boolean flip) {
        if (flip) {
            flipPiece();
        }
        for (int i = 0; i < rotateClockwise; i++) {
            rotatePiece();
        }
    }

    @Override
    public String toString() {
        String retString = super.toString() + " is at coordinates: ";
        for(int j = 0; j<5; j++) {
            if (occupiedCells[j] != null) {
                retString = retString + " " + occupiedCells[j];
            }
        }
        return retString;
    }
}


