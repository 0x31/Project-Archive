package comp1140.ass2.Game;

/**
 * Created by ***REMOVED*** on 22/08/15.
 */
public enum Shape {
    /**
     * An enumeration of every shape,
     * Taking in as parameters the coordinates taken up by the shape with orientation A, origin (0,0)
     */
    A(new Coordinate[] {new Coordinate( 0, 0)}),
    B(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1)}),
    C(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2)}),
    D(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1)}),
    E(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate( 0, 3)}),
    F(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate(-1, 2)}),
    G(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 0, 2)}),
    H(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 1, 0), new Coordinate( 0, 1), new Coordinate( 1, 1)}),
    I(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 1, 0), new Coordinate( 1, 1), new Coordinate( 2, 1)}),
    J(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate( 0, 3), new Coordinate( 0, 4)}),
    K(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate(-1, 3), new Coordinate( 0, 3)}),
    L(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate(-1, 2), new Coordinate( 0, 2), new Coordinate(-1, 3)}),
    M(new Coordinate[] {new Coordinate( 0, 0), new Coordinate(-1, 1), new Coordinate( 0, 1), new Coordinate(-1, 2), new Coordinate( 0, 2)}),
    N(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 1, 0), new Coordinate( 1, 1), new Coordinate( 0, 2), new Coordinate( 1, 2)}),
    O(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 0, 2), new Coordinate( 0, 3)}),
    P(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate(-1, 2), new Coordinate( 0, 2), new Coordinate( 1, 2)}),
    Q(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 0, 2), new Coordinate( 1, 2), new Coordinate( 2, 2)}),
    R(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 1, 0), new Coordinate( 1, 1), new Coordinate( 2, 1), new Coordinate( 2, 2)}),
    S(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 2, 1), new Coordinate( 2, 2)}),
    T(new Coordinate[] {new Coordinate( 0, 0), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 2, 1), new Coordinate( 1, 2)}),
    U(new Coordinate[] {new Coordinate( 0, 0), new Coordinate(-1, 1), new Coordinate( 0, 1), new Coordinate( 1, 1), new Coordinate( 0, 2)});

    private int cellNumber;
    private Coordinate homeCoordinates[];
    private Coordinate occupiedCells[];

    public int getCellNumber() {
        return cellNumber;
    }
    /**
     * the Shape is initialised using the coordinates of the shape's home position.
     * The homeCoordinates are (statically) steps.
     *
     * @param coords always equal to (0,0), and is the position of the top most, leftmost cell in base position
     *               refers to the 'base cell' of a shape
     */
    Shape(Coordinate[] coords) {
        cellNumber = coords.length;
        homeCoordinates = new Coordinate[cellNumber];  occupiedCells = new Coordinate[cellNumber];
        for(int i=0;i<cellNumber;i++) {
            homeCoordinates[i] = coords[i];            occupiedCells[i] = coords[i];
        }
    }

    /**
     * Used to access the private Coordinate array of occupiedCells
     *
     * @return occupiedCells
     */
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
        for (int i = 0; i < cellNumber; i++) occupiedCells[i] = homeCoordinates[i];
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
    public void movePiece(Coordinate shift, int rotateClockwise, boolean flip) {
        if (flip) flipPiece();
        for (int i = 0; i < rotateClockwise; i++) rotatePiece();
        shiftPiece(shift);
    }
    public void movePiece(int rotateClockwise, boolean flip) {
        if (flip) flipPiece();
        for (int i = 0; i < rotateClockwise; i++) rotatePiece();
    }
    public void setXY(Coordinate newCoord) {
        shiftPiece(this.occupiedCells[0].times(-1));
        shiftPiece(newCoord);
    }

    @Override
    public String toString() {
        String retString = super.toString() + " is at coordinates: ";
        for(int j = 0; j<cellNumber; j++) retString = retString + " " + occupiedCells[j];
        return retString;
    }
}


