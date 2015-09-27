package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.paint.Color;

/**
 * Created by Tim on 23/09/2015.
 */
public class PiecePreparerSprite extends GridSprite {

    public PieceSprite thePieceSprite;
    public boolean active;

    public PiecePreparerSprite(int col, int row, int width, int height, Colour color, Game parent) {
        super(col, row, width, height, color, parent);


    }

    /**
     *
     * @param shape
     * @param c
     * @param (orientation)
     */
    public void addShape(Shape shape, Colour c) {
        removePiece();
        Piece piece = new Piece(shape, c);
        addPiece(piece);
    }

    public void addShape(Shape shape, Colour c, char orientation) {
        removePiece();
        Piece piece = new Piece(shape, c);
        piece.initialisePiece(new Coordinate(0,0), orientation);
        addPiece(piece);
    }

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


    public void showPiece(PieceSprite pieceSprite) {
        //show desired piece
    }

    public Piece getPiece() {
        if(thePieceSprite==null) return null;
        Piece thePiece = thePieceSprite.piece;
        return thePiece;
    }

    public void isClicked(PieceSprite pieceSprite) {
        if(!active) {
            return;
        }

        removePiece();
        Piece piece = pieceSprite.piece;
        piece.movePiece(1, false);
        addPiece(piece);

    }

    public void removePiece() {
        removePieceSprite(thePieceSprite);
        thePieceSprite = null;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
