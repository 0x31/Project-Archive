package comp1140.ass2.Game;

/**
 * Created by ***REMOVED*** on 22/08/15.
 * TO DO:
 *        - make interface nicer, at the moment one can only move and orinetate a piece once.
 *          It might be necessary/nicer to be able to say "Shift piece down one, rotate once".
 *        - maybe have a different methods. one for initialising based on input, and one for
 *          manipulating the piece from there.
 */
public class Piece {
    public Shape shape;
    Colour colour;
    Coordinate[] occupiedCells;
    int cellNumber;

    /**
     * Initialises a Piece object from a shape id and a colour
     * @param id the piece shape, e.g. shape.D
     * @param c  one of: Red, Blue, Green, Yellow
     */
    public Piece(Shape id, Colour c) {
        shape = id; colour = c;
        occupiedCells = shape.getCoordinates();
        cellNumber = shape.getCellNumber();
    }

    public Piece(String move, Colour c) {
        int pieceChar = move.charAt(0)-'A';
        char rotation = move.charAt(1);
        int x         = move.charAt(2)-'A';
        int y         = move.charAt(3)-'A';
        Coordinate coord = new Coordinate(x, y);
        shape = Shape.values()[pieceChar];
        occupiedCells = shape.getCoordinates();
        cellNumber = shape.getCellNumber();
        initialisePiece(coord, rotation);
        colour = c;
    }

    public Coordinate[] getOccupiedCells() {
        return occupiedCells;
    }

    /**
     * Flips a piece in the y direction, maintaining the coordinate of the piece's base cell
     *
     * @return void  while changing occupiedCells
     */
    /* flips piece over the x=0 axis, maintaining the piece's origin */
    private void flipPiece() {
        Coordinate origin = new Coordinate (occupiedCells[0].getX(), occupiedCells[0].getY());
        for (int i = 0; i < cellNumber; i++) occupiedCells[i] = occupiedCells[i].flipCoordinate();
        Coordinate shift = new Coordinate (origin.getX() - occupiedCells[0].getX(),
                origin.getY() - occupiedCells[0].getY());
        this.shiftPiece(shift);
    }

    /**
     * Rotates a piece about the piece's origin cell
     *
     * @return void  while also changing occupiedCells
     */
    private void rotatePiece() {
        Coordinate origin = new Coordinate (occupiedCells[0].getX(), occupiedCells[0].getY());
        for (int i = 0; i < cellNumber; i++) occupiedCells[i] = occupiedCells[i].rotateCoordinate();
        Coordinate shift = new Coordinate (origin.getX() - occupiedCells[0].getX(),
                origin.getY() - occupiedCells[0].getY());
        this.shiftPiece(shift);
    }

    /**
     *
     * Given a four character String and the identifying number of the current player,
     * sets the grid coordinates to that player's colour where the piece is played.
     *
     * @param orientation   a character denoting the desired orientation. 'A','B','C','D' are rotation by 0, 90, 180
     *                      and 270 degrees respectively.
     *                      'E','F','G','H' are reflections in the y axis followed by rotations of 0, 90, 180 and 270
     *                      degrees respectively
     * @return void  while also changing occupiedCells
     */
    private void orientatePiece(char orientation) {
        if(orientation>'D') flipPiece();
        for (int i = 0; i < (orientation-'A')%4; i++) rotatePiece();
    }

    /**
     * Translates a piece a certain number of cells in the x direction and a certain number
     * of cells in the y direction
     *
     * @param shift     a coordinate object representing the desired shift amount in the x and y direction
     * @return void     while also changing occupiedCells
     */
    private void shiftPiece(Coordinate shift) {
        for (int i = 0; i < cellNumber; i++) occupiedCells[i] = occupiedCells[i].shiftCoordinate(shift);
    }

    /**
     * Used to set the initial position of the piece. Can be used multiple times and can be a handy
     * 'reset' function.
     *
     * @param origin        a coordinate which sets where the base cell will go
     * @param orientation   a char from 'A' - 'H' which sets the initial orientation of the piece
     * @return void         while also changing occupiedCells
     */
    public void initialisePiece(Coordinate origin, char orientation) {
        occupiedCells = shape.getCoordinates();
        orientatePiece(orientation);
        shiftPiece(origin);
    }

    /**
     * This function encapsulates all transformations: translation, rotation, reflection.
     * If only a translation is required, set rotateClockwise to 0 and flip to false.
     * If no translation is required, simply omit the shift parameter.
     *
     * @param shift             a coordinate which sets how much to translate
     * @param rotateClockwise   an int determining the number of times the piece is to be rotated clockwise
     * @param flip              a boolean determining if the piece is to be relfected in the y direction about
     *                          the piece's homecell
     * @return void         while also changing occupiedCells
     */
    public void movePiece(int rotateClockwise, boolean flip) {
        if (flip) flipPiece();
        for (int i = 0; i < rotateClockwise; i++) rotatePiece();
    }

    public void movePiece(Coordinate newCoord, int rotateClockwise, boolean flip) {
        setXY(newCoord);
        if (flip) flipPiece();
        for (int i = 0; i < rotateClockwise; i++) rotatePiece();
    }

    public void setXY(Coordinate newCoord) {
        shiftPiece(this.occupiedCells[0].times(-1));
        shiftPiece(newCoord);
    }

    @Override
    public String toString() {
        String retString = shape.name() + " is at coordinates: ";
        for(int j = 0; j<cellNumber; j++) retString = retString + ", " + occupiedCells[j];
        return retString;
    }
    /**
     * Piece's toString function, currently used for debugging.
     * @return String a string representation of the piece, made up of the colour,
     *         shape and coordinates of each cell.
     */


}
