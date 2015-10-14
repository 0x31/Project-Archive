package comp1140.ass2.Game;


/**
 * @author Tim, ***REMOVED*** on 31/08/15.
 */
public class PieceSprite {
    final int CELL_COUNT;
    final Piece piece;

    private final GridSprite gridSprite;
    final CellSprite[] cells;
    final Coordinate[] coordinates;

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
        this.cells = new CellSprite[CELL_COUNT];
        this.coordinates = piece.getOccupiedCells();
        for (int i = 0; i<CELL_COUNT; i++) {
            cells[i] = new CellSprite(CELL_DIM,CELL_DIM,colour, this);
        }
    }

    /**
     * Handles cells being clicked
     * @param cellSprite which cell was clicked
     */
    public void isClicked(CellSprite cellSprite) {
        gridSprite.isClicked(this);
    }

    /**
     * Handles cells being right-clicked
     * @param cellSprite which cell was clicked
     */
    public void isRightClicked(CellSprite cellSprite) {
        gridSprite.isRightClicked(this);
    }

    /**
     * Handles cells being mouseover-ed
     * @param cellSprite which cell was mouseover-ed
     */
    public void isHovered(CellSprite cellSprite) {
        gridSprite.isHovered(cellSprite);
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