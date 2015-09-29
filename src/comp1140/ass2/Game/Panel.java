package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by nosha on 25/09/15.
 */
public final class Panel extends GridSprite {

    ArrayList<PieceSprite> pieceSprites = new ArrayList<>();
    public ArrayList<Shape> shapes= new ArrayList<>();
    Colour color;
    public PieceSprite temporary = null;
    Game parent;
    int size;
    boolean active = false;


    public Panel(int col, int row, int size, Colour color, Game parent, boolean vertical) {
        super(col, row, size, color, parent);
        this.size = size;
        this.parent = parent;
        this.color = color;
        for(Node node : this.getChildren()) {
            node.setOpacity(0.2);
        }
        this.setActive(false);


        // Note: Rotating the panel with setRotation didn't work out well, hence this
        if(vertical) {

            addPiece(Shape.A, 'H', 0,0);
            addPiece(Shape.B, 'H', 2,0);
            addPiece(Shape.J, 'H', 5,0);
            addPiece(Shape.C, 'H', 0,2);
            addPiece(Shape.E, 'H', 4,2);
            addPiece(Shape.Q, 'D', 7,4);
            addPiece(Shape.K, 'D', 0,4);
            addPiece(Shape.F, 'G', 5,4);
            addPiece(Shape.M, 'B', 2,7);
            addPiece(Shape.H, 'H', 8,6);
            addPiece(Shape.T, 'H', 1,9);

            addPiece(Shape.P, 'G', 5,10);
            addPiece(Shape.R, 'G', 7,11);
            addPiece(Shape.O, 'G', 0,14);
            addPiece(Shape.U, 'F', 4,13);
            addPiece(Shape.G, 'E', 9,13);
            addPiece(Shape.S, 'F', 2,15);
            addPiece(Shape.I, 'B', 6,17);
            addPiece(Shape.L, 'G', 1,19);
            addPiece(Shape.D, 'E', 5,18);
            addPiece(Shape.N, 'G', 8,19);


        }
        else {
            addPiece(Shape.A, 'A', 0,0);
            addPiece(Shape.B, 'A', 0,2);
            addPiece(Shape.J, 'A', 0,5);
            addPiece(Shape.C, 'A', 2,0);
            addPiece(Shape.E, 'A', 2,4);
            addPiece(Shape.Q, 'E', 4,7);
            addPiece(Shape.K, 'E', 4,0);
            addPiece(Shape.F, 'D', 4,5);
            addPiece(Shape.M, 'G', 7,2);
            addPiece(Shape.H, 'A', 6,8);
            addPiece(Shape.T, 'A', 9,1);
            addPiece(Shape.P, 'B', 10,5);
            addPiece(Shape.R, 'B', 11,7);
            addPiece(Shape.O, 'B', 14, 0);
            addPiece(Shape.U, 'A', 13,4);
            addPiece(Shape.G, 'D', 13,9);
            addPiece(Shape.S, 'A', 15,2);
            addPiece(Shape.I, 'E', 17,6);
            addPiece(Shape.L, 'B', 19,1);
            addPiece(Shape.D, 'D', 18,5);
            addPiece(Shape.N, 'B', 19,8);




            /*

            addPiece(Shape.B, 'A', 1, 3);
            addPiece(Shape.C, 'A', 1, 6);
            addPiece(Shape.E, 'A', 3, 1);
            addPiece(Shape.J, 'A', 5, 1);
            addPiece(Shape.G, 'A', 3, 6);
            addPiece(Shape.D, 'A', 7, 1);
            addPiece(Shape.F, 'A', 7, 6);
            addPiece(Shape.H, 'A', 10, 1);
            addPiece(Shape.I, 'A', 10, 4);
            addPiece(Shape.J, 'A', 14, 1);
            addPiece(Shape.K, 'A', 14, 1);
            addPiece(Shape.L, 'A', 14, 1);
            addPiece(Shape.M, 'A', 14, 1);
            addPiece(Shape.N, 'A', 14, 1);
            addPiece(Shape.O, 'A', 14, 1);
            addPiece(Shape.P, 'A', 14, 1);
            addPiece(Shape.Q, 'A', 14, 1);
            addPiece(Shape.R, 'A', 14, 1);
            addPiece(Shape.S, 'A', 14, 1);
            addPiece(Shape.T, 'A', 14, 1);
            addPiece(Shape.U, 'A', 14, 1);
            */
        }
    }

    public void addPiece(Shape shape, char orientation, int x, int y) {
        Piece myPiece = new Piece(shape, color);
        myPiece.initialisePiece(new Coordinate(x, y),orientation);
        PieceSprite myPieceSprite = new PieceSprite(myPiece, xsize, this);
        pieceSprites.add(myPieceSprite);
        shapes.add(shape);
        this.addPieceSprite(myPieceSprite);
    }

    public void removePiece(Shape shape) {
        //int index = pieces.indexOf(piece);
        int index = shapes.indexOf(shape);
        if(index==-1) return;
        //System.out.println(pieceSprites.size()+ ", "+pieces.size());
        PieceSprite sprite = pieceSprites.get(index);
        pieceSprites.remove(index);
        shapes.remove(index);
        //pieces.remove(index);
        this.removePieceSprite(sprite);
    }

    public void isClicked(PieceSprite sprite) {
        if(!active) {
            return;
        }
        if(temporary != null) {
            pieceSprites.add(temporary);
            shapes.add(temporary.piece.shape);
            //pieces.add(temporary.piece);
            this.addPieceSprite(temporary);
        }
        temporary = sprite;
        this.removePieceSprite(sprite);
        parent.piecePreparer.addShape(sprite.piece.shape, color);
        pieceSprites.remove(sprite);
        //pieces.remove(sprite.piece);
        shapes.remove(sprite.piece.shape);
    }

    public void setActive(boolean active) {
        this.active = active;
        if(active) {
            this.setOpacity(1);
        }
        else {
            this.setOpacity(0.4);
        }
    }

    public  void lock() {
        System.out.println(this.color.toString() + " player can't play anymore");
        /*Rectangle cover = new Rectangle(size * row, size*col, Color.valueOf("rgba(0, 0, 0, 0.45)"));
        cover.setLayoutX(10);
        cover.setLayoutY(10);
        this.getChildren().add(cover);
        */
    }
}
