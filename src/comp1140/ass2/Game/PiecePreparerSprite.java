package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;

/**
 * Created by Tim on 23/09/2015.
 */
public class PiecePreparerSprite extends GridSprite {

    public PieceSprite thePieceSprite;

    public PiecePreparerSprite(int col, int row, int width, int height, Colour color, Game parent) {
        super(col, row, width, height, color, parent);

        Piece myPiece = new Piece(Shape.E, Colour.Red);
        myPiece.shape.movePiece(new Coordinate(4, 4), 0, false);
        thePieceSprite = new PieceSprite(myPiece, xsize, Colour.Red, this);
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
        System.out.println("-- -- -- PiecePreparer was clicked in\n");

        /*for (CellSprite c : pieceSprite.getCellSprites()) {
            c.setFill(Color.BLUEVIOLET);
        }*/

        removePieceSprite(pieceSprite);

        pieceSprite.piece.shape.movePiece(1, false);

        addPieceSprite(pieceSprite);
    }

    public void removePiece() {
        removePieceSprite(thePieceSprite);
        thePieceSprite = null;
    }
}
