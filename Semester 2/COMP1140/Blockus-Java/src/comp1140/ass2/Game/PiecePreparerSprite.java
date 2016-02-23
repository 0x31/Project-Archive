package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * @author Tim on 23/09/2015.
 * @author ***REMOVED***, minor edits to work with Board/Panel
 */
public class PiecePreparerSprite extends GridSprite {

    private PieceSprite thePieceSprite;
    private boolean active;

    /**
     * Creates new PiecePreparer
     * @param col the number of columns in the grid
     * @param row the number of rows in the grid
     * @param size the dimension for displaying cells
     * @param color the default background colour
     * @param parent the Game class which instantiates this class
     */
    public PiecePreparerSprite(int col, int row, int size, Colour color, Game parent) {
        super(col, row, size, color, parent);
        eventWatcher();
        active = false;

        Pane rotate = new CellSprite(xsize, xsize, "Rotate",this);
        add(rotate, 0, 0);

        Pane flip = new CellSprite(xsize, xsize, "Flip",this);
        add(flip, 4, 0);
        flip.setOnMouseClicked(event -> {
            if(!active) return;
            flipPiece();

            /* Counter what eventWatcher will do */
            rotatePiece();
            rotatePiece();
            rotatePiece();

         });

    }

    /**
     * Add a shape to the preparer through addPiece
     * @param shape the shape to show
     * @param c the colour of the shape
     */
    public void addShape(Shape shape, Colour c) {
        removePiece();
        Piece piece = new Piece(shape, c);
        addPiece(piece);
    }
    

    /**
     * Add a shape to the preparer though addPiece
     * @param shape the shape to show
     * @param c the colour of the shape
     * @param orientation the orientation to initialise it to
     */
    public void addShape(Shape shape, Colour c, char orientation) {
        removePiece();
        Piece piece = new Piece(shape, c);
        piece.initialisePiece(new Coordinate(0,0), orientation);
        addPiece(piece);
    }

    /**
     * Adds a piece to the preparer
     * @param piece the piece to add to the preparer
     */
    private void addPiece(Piece piece) {

        piece.setXY(new Coordinate(0, 0));
        int minX=0;
        int minY=0;
        int maxX=0;
        int maxY=0;
        for(Coordinate coord : piece.getOccupiedCells()) {
            minX = Math.min(minX, coord.getX());
            minY = Math.min(minY, coord.getY());
            maxX = Math.max(maxX, coord.getX());
            maxY = Math.max(maxY, coord.getY());
        }
        int newX = /* Set to 0,0 */ -minX + /* Use up any leftover space*/ (int) Math.floor((5 - (maxX - minX)) / 2);
        int newY = /* Set to 0,0 */ -minY + /* Use up any leftover space*/ (int) Math.floor((5 - (maxY - minY)) / 2);

        piece.setXY(new Coordinate(newX,newY));
        thePieceSprite = new PieceSprite(piece, xsize, this);
        this.addPieceSprite(thePieceSprite);


    }


    /**
     * Used to pass along the current piece to the board
     * @return the piece shown in the preparer
     */
    public Piece getPiece() {
        if(thePieceSprite==null) return null;
        return thePieceSprite.piece;
    }

    /**
     * Flip the piece being shown
     */
    public void flipPiece() {
        Piece piece = thePieceSprite.piece;
        piece.movePiece(0, true);
        removePiece();
        addPiece(piece);
    }

    /**
     * Rotate the piece being shown
     */
    public void rotatePiece() {
        Piece piece = thePieceSprite.piece;
        piece.movePiece(1, false);
        removePiece();
        addPiece(piece);
    }

    /**
     * Watch for clicks on the entire preparer for rotating and flipping the piece
     */
    private void eventWatcher() {
        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if(!active) return;
                flipPiece();
            } else {
                if(!active) return;
                rotatePiece();
            }
        });
    }

    /**
     * Remove the piece from the preparer
     */
    public void removePiece() {
        removePieceSprite(thePieceSprite);
        thePieceSprite = null;
    }

    /**
     * Set active if the player is Human, inactive if otherwise
     * @param active whether or not to set the preparer to active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

}
