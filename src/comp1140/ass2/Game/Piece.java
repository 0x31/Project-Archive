package comp1140.ass2.Game;

/**
 * @author Tim ***REMOVED***, ***REMOVED***
 * Minor edits by ***REMOVED***, ***REMOVED***
 *
 */
public class Piece {
    public Shape shape;
    public Colour colour;
    Coordinate[] occupiedCells;
    int cellNumber;
    int rotation=0;
    boolean flip = false;

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

    /**
     * Initialises a Piece object from an string encoded move and a colour
     *
     * @param move a string encoding of a move which is 4 characters long
     * @param c    the colour of the player making the move
     */
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
    private void flipPiece() {
        Coordinate origin = new Coordinate (occupiedCells[0].getX(), occupiedCells[0].getY());
        for (int i = 0; i < cellNumber; i++) occupiedCells[i] = occupiedCells[i].flipCoordinate();
        Coordinate shift = new Coordinate (origin.getX() - occupiedCells[0].getX(),
                origin.getY() - occupiedCells[0].getY());
        this.shiftPiece(shift);
        flip = !flip;
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
        rotation = (rotation+1)%4;
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
     * Creates a clone of piece
     *
     * @return a new piece object with identical field values
     */
    public Piece clone() {
        Piece piece = new Piece(this.shape,this.colour);
        piece.movePiece(rotation, flip);
        return piece;
    }

    /**
     * This function encapsulates all transformations: translation, rotation, reflection.
     * If only a translation is required, set rotateClockwise to 0 and flip to false.
     * If no translation is required, simply omit the shift parameter.
     *
     * @param rotateClockwise   an int determining the number of times the piece is to be rotated clockwise
     * @param flip              a boolean determining if the piece is to be relfected in the y direction about
     *                          the piece's homecell
     * @return void         while also changing occupiedCells
     */
    public void movePiece(int rotateClockwise, boolean flip) {
        if (flip) flipPiece();
        for (int i = 0; i < rotateClockwise; i++) rotatePiece();
    }

    /**
     * Moves a piece to a new coordinate location with a new orientation
     *
     * @param newCoord         new location of home cell
     * @param rotateClockwise  new rotation
     * @param flip             whether to flip the piece or not
     *
     * @return void     while also changing occupiedCels
     */
    public void movePiece(Coordinate newCoord, int rotateClockwise, boolean flip) {
        setXY(newCoord);
        if (flip) flipPiece();
        for (int i = 0; i < rotateClockwise; i++) rotatePiece();
    }

    /**
     * Sets a new location for the piece by first shifting it to the origin, then shifting
     * it to the new desired location
     *
     * @param newCoord  new location of home cell
     *
     * @return void     while also changing occupiedCels
     */
    public void setXY(Coordinate newCoord) {
        shiftPiece(this.occupiedCells[0].times(-1));
        shiftPiece(newCoord);
    }

    @Override
    public String toString() {
        String shapeS = shape.toString();
        String orienS = Character.toString((char) (((flip) ? 4 : 0) + rotation + 'A'));
        String coordS = occupiedCells[0].toString();
        return shapeS + orienS + coordS;
    }

    /**
     * Piece's toString function, currently used for debugging.
     * @return String a string representation of the piece, made up of the colour,
     *         shape and coordinates of each cell.
     */


}
