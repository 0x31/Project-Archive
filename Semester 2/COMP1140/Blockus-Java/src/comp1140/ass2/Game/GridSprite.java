package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

/**
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***, 01/09/15
 * @author Tim, updating:
 *  - click handling
 *  - use of cellSprite
 *
 *  GridSprite represents a grid of cells as a JavaFX GridPane
 *  It is an abstract class, extended by Board, Panel and PiecePreparerSprite
 */
abstract class GridSprite extends GridPane {
    Game parent;
    public int xsize;
    private final ArrayList<PieceSprite> pieceSprites = new ArrayList<>();

    GridSprite() {}

    /**
     * Creates a new GridPane to display cells
     * @param col the number of columns in the grid
     * @param row the number of rows in the grid
     * @param xsize the dimension for displaying cells
     * @param color the default background colour
     * @param parent the Game class which instantiates this class
     */
    GridSprite(int col, int row, int xsize, Colour color, Game parent) {
        this.parent = parent;
        this.xsize = xsize;

        /* Puts a neutral cellSprite at every location */
        for(int j=0;j<col;j++) {
            for (int i = 0; i < row; i++) {
                CellSprite cell = new CellSprite(xsize, xsize, color,this);
                this.add(cell, i, j);
            }
        }
        for (PieceSprite pieceSprite : pieceSprites)
            addToGridPane(pieceSprite);
    }

    /**
     * Takes a PieceSprite and adds each CellSprite pane to the GridSprite
     * @param pieceSprite the piece being added to the GridSprite
     */
    private void addToGridPane(PieceSprite pieceSprite) {
        for (int i = 0; i<pieceSprite.CELL_COUNT; i++) {
            int x = pieceSprite.coordinates[i].getX();
            int y = pieceSprite.coordinates[i].getY();
            if(y>=20 || y<0 || x>=20 || x<0)
                continue;
            this.add(pieceSprite.cells[i], pieceSprite.coordinates[i].getX(), pieceSprite.coordinates[i].getY());
        }
    }

    /**
     * Add a PieceSprite to the GridSprite
     * @param pieceSprite the piece to be added
     */
    void addPieceSprite(PieceSprite pieceSprite) {
        pieceSprites.add(pieceSprite);
        addToGridPane(pieceSprite);
    }

    /**
     * Removes a given PieceSprite form the GridSprite
     * @param pieceSprite the piece to remove
     */
    void removePieceSprite(PieceSprite pieceSprite) {
        pieceSprites.remove(pieceSprite);
        if(pieceSprite==null) return;
        for (int i = 0; i<pieceSprite.CELL_COUNT; i++)
            this.getChildren().remove(pieceSprite.cells[i]);
    }

    /**
     * (override-able) respond appropriately to an onClick event
     * @param pieceSprite the PieceSprite being clicked upon
     */
    public void isClicked(PieceSprite pieceSprite) {}

    /**
     * (override-able) respond appropriately to an onClick event
     * @param cellSprite the CellSprite being clicked upon
     */
    public void isClicked(CellSprite cellSprite) {}

    /**
     * (override-able) respond appropriately to a right-click event
     * @param pieceSprite the PieceSprite being clicked upon
     */
    public void isRightClicked(PieceSprite pieceSprite) {}

    /**
     * (override-able) respond appropriately to a right-click event
     * @param cellSprite the CellSprite being clicked upon
     */
    public void isRightClicked(CellSprite cellSprite) {}

    /**
     * (override-able) respond appropriately to hover events
     * @param cell the CellSprite being hovered over
     */
    public void isHovered(CellSprite cell) {}

    /**
     * (override-able) respond appropriately to un-hovering
     */
    public void isUnhovered() {}

}
