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
    private Coordinate[] coordinates;

    public int getCellNumber() {
        return cellNumber;
    }

    public Coordinate[] getCoordinates() {
        return coordinates.clone();
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
        coordinates = new Coordinate[cellNumber];
        for (int i = 0; i < cellNumber; i++) {
            coordinates[i] = coords[i];
        }
    }


}


