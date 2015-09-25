package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by nosha on 25/09/15.
 */
public final class Panel extends GridSprite {

    ArrayList<PieceSprite> pieceSprites = new ArrayList<>();
    Colour color;
    PieceSprite temporary = null;
    Game parent;

    public Panel(int col, int row, int width, int height, Colour color, Game parent, boolean vertical) {
        super(col, row, width, height, color, parent);
        this.parent = parent;
        this.color = color;
        for(Node node : this.getChildren()) {
            node.setOpacity(0.2);
        }


        if(vertical) {
            addPiece(Shape.A, 1, 1);
            addPiece(Shape.B, 3, 1);
            addPiece(Shape.C, 5, 1);
            addPiece(Shape.D, 7, 1);
        }
        else {
            addPiece(Shape.A, 1, 1);
            addPiece(Shape.B, 3, 1);
            addPiece(Shape.C, 5, 1);
        }
    }

    public void addPiece(Shape shape, int x, int y) {
        Piece myPiece = new Piece(shape, color);
        myPiece.shape.setXY(new Coordinate(x, y));
        PieceSprite myPieceSprite = new PieceSprite(myPiece, xsize, this);
        pieceSprites.add(myPieceSprite);
        this.addPieceSprite(myPieceSprite);
    }

    public void isClicked(PieceSprite sprite) {
        if(temporary != null) {
            pieceSprites.add(temporary);
            this.addPieceSprite(temporary);
        }
        temporary = sprite;
        this.removePieceSprite(sprite);
        parent.piecePreparer.addPiece(sprite.piece.shape,color);
        pieceSprites.remove(sprite);
    }

}
