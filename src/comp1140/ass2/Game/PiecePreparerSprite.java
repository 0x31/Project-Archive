package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.paint.Color;

/**
 * Created by Tim on 23/09/2015.
 * Some edits by ***REMOVED*** to work with Board/Panel
 */
public class PiecePreparerSprite extends GridSprite {

    public PieceSprite thePieceSprite;
    public boolean active;

    public PiecePreparerSprite(int col, int row, int size, Colour color, Game parent) {
        super(col, row, size, color, parent);
    }

    /**
     * Adds shape to collection of pieces
     * See below for more details and parameters
     * @param shape
     * @param c
     */
    public void addShape(Shape shape, Colour c) {
        removePiece();
        Piece piece = new Piece(shape, c);
        addPiece(piece);
    }

    /**
     * Add a shape to the preparer though addPiece
     * @param shape the shape to show
     * @param c the colour to show (not important)
     * @param orientation the orientation to initialise it to
     */
    public void addShape(Shape shape, Colour c, char orientation) {
        removePiece();
        Piece piece = new Piece(shape, c);
        piece.initialisePiece(new Coordinate(0,0), orientation);
        addPiece(piece);
    }

    /**
     * Adds a Piece to a preparer
     * @param piece the piece to add
     */
    public void addPiece(Piece piece) {

        piece.setXY(new Coordinate(0, 0));
        int minX=0;
        int minY=0;
        int maxX=0;
        int maxY=0;
        for(Coordinate coord : piece.occupiedCells) {
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
        Piece thePiece = thePieceSprite.piece;
        return thePiece;
    }

    /**
     * Checks for clicks to rotate the piece
     * @param pieceSprite which sprite was clicked (there's only one, so can be removed in the future)
     */
    public void isClicked(PieceSprite pieceSprite) {
        if(!active) {
            return;
        }

        removePiece();
        Piece piece = pieceSprite.piece;
        piece.movePiece(1, false);
        if(parent.NO_RIGHT_CLICK) {
            if(piece.rotation==0)
                piece.movePiece(0,true);
        }
        addPiece(piece);

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
