package comp1140.ass2.Game;


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

    /**
     * Creates an array of CellSprites (image viewers) to render onto a GridSprite
     * @param piece the piece to show
     * @param CELL_DIM the size of the cells
     * @param gridSprite the gridsprite it belongs to
     */
    public PieceSprite (Piece piece, int CELL_DIM, GridSprite gridSprite) {
        this.CELL_COUNT = piece.shape.getCellNumber();
        Colour colour = piece.colour;
        this.piece = piece;
        this.gridSprite = gridSprite;
        this.color = getFillFromPlayer(colour);
        this.CELL_DIM = CELL_DIM;
        this.cells = new CellSprite[CELL_COUNT];
        this.coordinates = piece.getOccupiedCells();
        for (int i = 0; i<CELL_COUNT; i++) {
            cells[i] = new CellSprite(CELL_DIM,CELL_DIM,colour, this);
        }
    }

    /**
     * @return cells, the array of CellSprites
     */
    public CellSprite[] getCellSprites() {
        return cells;
    }

    /**
     * Handles cells being clicked
     * @param cellSprite which cell was clicked
     */
    public void isClicked(CellSprite cellSprite) {
        //System.out.println("-- -- Piece was clicked");
        gridSprite.isClicked(this);
    }

    /**
     * Handles cells being mouseover-ed
     * @param cellSprite which cell was mouseover-ed
     */
    public void isHovered(CellSprite cellSprite) {
        gridSprite.isHovered(cellSprite);
    }

    /**
     * Convert a Colour to a JavaFX Color
     * @param color the Colour to convert
     * @return the converted JavaFX Color
     */
    private Color getFillFromPlayer(Colour color) {
        if      (color == Colour.Blue)      return Color.BLUE;
        else if (color == Colour.Red)       return Color.RED;
        else if (color == Colour.Green)     return Color.GREEN;
        else if (color == Colour.Yellow)    return Color.YELLOW;
        return Color.GREY;
    }

    /**
     * Set the opacity of each CellSprite
     * @param opacity the opacity to set the cellsprites to
     */
    public void setOpacity(double opacity) {
        for(CellSprite cell : cells) {
            cell.setOpacity(opacity);
        }
    }
}