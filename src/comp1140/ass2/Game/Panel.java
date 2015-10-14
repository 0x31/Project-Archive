package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.Node;
import java.util.ArrayList;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***, 25/09/15, from code written by Holly in old class
 * @author Holly on 30/09/15
 * @author by Tim on 14/10/15, updating:
 *   - repaired vertical panel layout to neatly show pieces
 *
 * Panel displays the available pieces of a player
 */
public final class Panel extends GridSprite {

    public final ArrayList<Shape> shapes= new ArrayList<>();
    public final ArrayList<Shape> activeShapes = new ArrayList<>();
    public PieceSprite temporary = null;

    private final ArrayList<PieceSprite> pieceSprites = new ArrayList<>();
    private final Colour color;
    private final Game parent;
    private boolean active = false;

    /**
     * Creates a new Panel to display the pieces of a player
     * @param col the number of columns in the grid
     * @param row the number of rows in the grid
     * @param size the dimension for displaying cells
     * @param color the default background colour
     * @param parent the Game class which instantiates this class
     * @param vertical boolean representing whether the Panel is vertical (blue,yellow) or horizontal (red,green)
     */
    public Panel(int col, int row, int size, Colour color, Game parent, boolean vertical) {
        super(col, row, size, color, parent);
        this.parent = parent;
        this.color = color;
        for(Node node : this.getChildren())
            node.setOpacity(0.2);
        this.setActive(false);

        if(vertical) {
            addPiece(Shape.A, 'H', 0, 0);
            addPiece(Shape.B, 'H', 2,0);
            addPiece(Shape.J, 'H', 5,0);
            addPiece(Shape.C, 'H', 0,2);
            addPiece(Shape.E, 'H', 4,2);
            addPiece(Shape.Q, 'D', 7,4);
            addPiece(Shape.K, 'D', 0,4);
            addPiece(Shape.F, 'E', 5,4);
            addPiece(Shape.M, 'B', 2,7);
            addPiece(Shape.H, 'H', 8,6);
            addPiece(Shape.T, 'H', 1,9);
            addPiece(Shape.P, 'G', 5,10);
            addPiece(Shape.R, 'G', 7,11);
            addPiece(Shape.O, 'G', 0,14);
            addPiece(Shape.U, 'F', 6,13);
            addPiece(Shape.G, 'E', 9,13);
            addPiece(Shape.S, 'F', 4,17);
            addPiece(Shape.I, 'B', 7,15);
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
        }
    }

    /**
     * Adds a Shape to the Panel
     * @param shape the shape to add to the Panel
     * @param orientation the orientation to set the shape to
     * @param x the x-value to move the shape to
     * @param y the y-value to move the shape to
     */
    private void addPiece(Shape shape, char orientation, int x, int y) {
        Piece myPiece = new Piece(shape, color);
        myPiece.initialisePiece(new Coordinate(x, y), orientation);
        PieceSprite myPieceSprite = new PieceSprite(myPiece, xsize, this);
        pieceSprites.add(myPieceSprite);
        shapes.add(shape);
        activeShapes.add(shape);
        this.addPieceSprite(myPieceSprite);
    }

    /**
     * Removes a Shape from the Panel
     * @param shape the shape to remove from the Panel
     */
    public void removePiece(Shape shape) {
        int index = shapes.indexOf(shape);
        if(index==-1) return;
        PieceSprite sprite = pieceSprites.get(index);
        pieceSprites.remove(index);
        shapes.remove(index);
        activeShapes.remove(shape);
        this.removePieceSprite(sprite);
    }

    /**
     * Handle click events by passing the selected piece to the PiecePreparer
     * @param sprite the sprite triggering the click event
     */
    public void isClicked(PieceSprite sprite) {
        if(!active || !activeShapes.contains(sprite.piece.shape)) return;
        if(temporary != null) {
            pieceSprites.add(temporary);
            shapes.add(temporary.piece.shape);
            activeShapes.add(temporary.piece.shape);
            this.addPieceSprite(temporary);
        }
        temporary = sprite;
        this.removePieceSprite(sprite);
        parent.piecePreparer.addShape(sprite.piece.shape, color);
        pieceSprites.remove(sprite);
        shapes.remove(sprite.piece.shape);
        activeShapes.remove(sprite.piece.shape);
    }

    /**
     * If the Panel is un-active, set the opacity to 40%
     * @param active a boolean representing whether the Panel is active
     */
    public void setActive(boolean active) {
        this.active = active;
        this.setOpacity(active?1:0.4);
    }

    /**
     * If a piece can't be placed, make it non-clickable and dim it
     * @param shape the unplayable piece
     */
    public void lockShape(Shape shape) {
        activeShapes.remove(shape);
        pieceSprites.get(shapes.indexOf(shape)).setOpacity(0.4);
    }

    /**
     * Reverts lockShape(Shape shape)
     * @param shape the shape to undim
     */
    public void unlockShape(Shape shape) {
        if(!activeShapes.contains(shape)) {
            activeShapes.add(shape);
            pieceSprites.get(shapes.indexOf(shape)).setOpacity(1);
        }
    }

    /**
     * If the user is a Human, display a Pass button before skipping them
     */
    public  void lock(boolean isHuman) {
        if(!isHuman) return;

        /*Rectangle cover = new Rectangle(size * row, size*col, Color.valueOf("rgba(0, 0, 0, 0.45)"));
        cover.setLayoutX(10);
        cover.setLayoutY(10);
        this.getChildren().add(cover);
        */
    }

}
