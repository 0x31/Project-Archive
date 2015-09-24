package comp1140.ass2;


import javafx.scene.paint.Color;

/**
 * Created by ***REMOVED*** on 31/08/15.
 */
public class PieceSprite {
    final int CELL_COUNT;
    final int CELL_DIM;
    
    Piece piece;
    Color color;
    
    GridSprite gridSprite;
    CellSprite[] cells;
    Coordinate[] coordinates;

    public PieceSprite (Piece piece, int CELL_DIM, Colour colour, GridSprite gridSprite) {
        this.CELL_COUNT = piece.shape.getCellNumber();
        this.piece = piece;
        this.gridSprite = gridSprite;
        this.color = getFillFromPlayer(colour);
        this.CELL_DIM = CELL_DIM;
        this.cells = new CellSprite[CELL_COUNT];
        this.coordinates = piece.shape.getOccupiedCells();

        for (int i = 0; i<CELL_COUNT; i++) {
            cells[i] = new CellSprite(CELL_DIM,CELL_DIM,colour, this);
        }
    }

    public CellSprite[] getCellSprites() {
        return cells;
    }

    public void isClicked(CellSprite cellSprite) {
        System.out.println("-- -- Piece was clicked");
        gridSprite.isClicked(this);
    }

    private Color getFillFromPlayer(Colour color) {
        if      (color == Colour.Blue)      return Color.BLUE;
        else if (color == Colour.Red)       return Color.RED;
        else if (color == Colour.Green)     return Color.GREEN;
        else if (color == Colour.Yellow)    return Color.YELLOW;
        return Color.GREY;
    }
}