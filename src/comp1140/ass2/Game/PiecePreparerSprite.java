package comp1140.ass2.Game;

import javafx.scene.paint.Color;

/**
 * Created by Tim on 23/09/2015.
 */
public class PiecePreparerSprite extends GridSprite {

    public PiecePreparerSprite(int col, int row, int width, int height, Color color) {
        super(col, row, width, height, color);

        Piece myPiece1 = new Piece(Shape.B, Colour.Blue);
        myPiece1.shape.movePiece(new Coordinate(0,0), 0, false);
        PieceSprite myPieceSprite1 = new PieceSprite(myPiece1, xsize, Colour.Yellow, this);
        this.addPieceSprite(myPieceSprite1);


        Piece myPiece = new Piece(Shape.E, Colour.Red);
        myPiece.shape.movePiece(new Coordinate(4,4), 0, false);
        PieceSprite myPieceSprite = new PieceSprite(myPiece, xsize, Colour.Yellow, this);
        this.addPieceSprite(myPieceSprite);
        System.out.println("There is one piece in pieceSprites");
    }

    public void showPiece(PieceSprite pieceSprite) {
        //show desired piece
    }

    public void isClicked(PieceSprite pieceSprite) {
        System.out.println("-- -- -- PiecePreparer was clicked in\n");

        /*for (CellSprite c : pieceSprite.getCellSprites()) {
            c.setFill(Color.BLUEVIOLET);
        }*/

        pieceSprite.gridSprite.removePieceSprite(pieceSprite);

        pieceSprite.piece.shape.movePiece(1, false);

        pieceSprite.gridSprite.addPieceSprite(pieceSprite);
    }
}
