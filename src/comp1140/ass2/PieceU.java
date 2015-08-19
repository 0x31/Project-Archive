package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 19/08/15.
 */
public final class PieceU extends Piece {

    public PieceU(Colour c) {
        String[] coordinates =
                { " # " ,
                  "###" ,
                  " # " };
        occupiedCells = stringToCoordinates(coordinates);
    }

}
