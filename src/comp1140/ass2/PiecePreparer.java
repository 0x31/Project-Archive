package comp1140.ass2;

import javafx.scene.paint.Color;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Tim on 31/08/2015.
 */
public class PiecePreparer extends GridSprite {

    public Piece myPiece;

    public PiecePreparer(int col, int row, int width, int height, Color color) {
        super(col, row, width, height, color);
        final int CELL_DIM = 20;


        Piece myPiece = new Piece(Shape.G, Colour.Blue);
        myPiece.shape.initialisePiece(new Coordinate(1, 1), 'A');

        final Queue<PieceSprite> queue = new LinkedBlockingQueue<>();

        //@ToDo Have clickable buttons
        //@ToDo Have pretty border

        this.setCells(myPiece.shape.getOccupiedCells(), CellState.Blue);

    }
}
