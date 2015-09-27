package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.paint.Color;

/**
 * Created by Tim on 23/09/2015.
 */
public class PiecePreparerSprite extends GridSprite {

    public PieceSprite thePieceSprite;

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
        piece.setXY(new Coordinate(-minX + (int) Math.floor((5-(maxX-minX))/2), -minY+(int) Math.floor((5-(maxY-minY))/2)));
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
        if(!parent.players[parent.currentPlayer].isHuman()) {
            return;
        }
        System.out.println("-- -- -- PiecePreparer was clicked in\n");

        /*for (CellSprite c : pieceSprite.getCellSprites()) {
            c.setFill(Color.BLUEVIOLET);
        }*/

        removePiece();
        Piece piece = pieceSprite.piece;
        piece.movePiece(1, false);
        addPiece(piece);


        /*
        removePieceSprite(pieceSprite);
        pieceSprite.piece

        pieceSprite.piece.movePiece(1, false);

        addPieceSprite(pieceSprite);
        */
    }

    public void removePiece() {
        removePieceSprite(thePieceSprite);
        thePieceSprite = null;
    }
}
