package comp1140.ass2.Game;

import comp1140.ass2.Scenes.Game;
import javafx.scene.Parent;
import javafx.scene.control.Cell;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by nosha on 1/09/15.
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***
 * Edited later by Tim:
 *  - click handling
 *  - use of cellSprite
 */
public abstract class GridSprite extends GridPane {
    int col;
    int row;
    //int ysize;
    int xsize;

    Game parent;
    Colour color;
    ArrayList<PieceSprite> pieceSprites = new ArrayList<>();

    public GridSprite() {}

    public GridSprite(int col, int row, int xsize, Colour color, Game parent) {
        this.parent = parent;
        this.col = col;
        this.row = row;
        //this.xsize = Math.floorDiv(width-10, row)-1;
        //this.ysize = Math.floorDiv(height-10, col)-1;
        this.xsize = xsize;
        this.color = color;

        //this.setHgap(1);
        //this.setVgap(1);

        //puts a neutral cellSprite at every location
        for(int j=0;j<col;j++) {
            for (int i = 0; i < row; i++) {
                CellSprite cell = new CellSprite(xsize, xsize, color,this); //double width, double height, Colour color, PieceSprite pieceSprite) {
                this.add(cell, i, j);
            }
        }

        for (PieceSprite pieceSprite : pieceSprites) {
            addToGridPane(pieceSprite);
        }
    }

    /**
     * Takes a piece and adds each cell pane to gridpane
     *
     * @param pieceSprite
     */
    private void addToGridPane(PieceSprite pieceSprite) {
        for (int i = 0; i<pieceSprite.CELL_COUNT; i++) {
            this.add(pieceSprite.cells[i], pieceSprite.coordinates[i].getX(), pieceSprite.coordinates[i].getY());
        }
    }

    /**
     * Takes a piece and removes each cell pane form gridpane
     *
     * @param pieceSprite
     */
    private void removeFromGridPane(PieceSprite pieceSprite) {
        if(pieceSprite==null) return;
        for (int i = 0; i<pieceSprite.CELL_COUNT; i++) {
            this.getChildren().remove(pieceSprite.cells[i]);
        }
    }

    /**
     * Adds a piece to the collection of pieces as well as the grid pane itself
     *
     * @param pieceSprite
     */
    public void addPieceSprite(PieceSprite pieceSprite) {
        pieceSprites.add(pieceSprite);
        addToGridPane(pieceSprite);
    }

    /**
     * Removes a piece from the collection of pieces as well as form the grid pane itself
     *
     * @param pieceSprite
     */
    public void removePieceSprite(PieceSprite pieceSprite) {
        pieceSprites.remove(pieceSprite);
        removeFromGridPane(pieceSprite);
    }

    /**
     * Overrideable function which is utilised in responding appropriately to onClick events
     *
     * @param pieceSprite
     */
    public void isClicked(PieceSprite pieceSprite) {
        System.out.println("GridSprite was clicked in - please override this function and tell me what to do!");
    }


    /**
     * Overrideable function which is utilised in responding appropriately to onClick events
     *
     * @param cellSprite
     */
    public void isClicked(CellSprite cellSprite) {
        // Do nothing;
    }

    /**
     * Overrideable function which is utilised in responding appropriately to onClick events
     *
     * @param pieceSprite
     */
    public void isRightClicked(PieceSprite pieceSprite) {
        System.out.println("-- -- -- in Default GridSprite RightClick for pieceSprite");
        // Do nothing;
    }

    /**
     * Overrideable function which is utilised in responding appropriately to onClick events
     *
     * @param cellSprite
     */
    public void isRightClicked(CellSprite cellSprite) {
        System.out.println("-- -- -- in Default GridSprite RightClick for cellSprite");
        // Do nothing;
    }

    /**
     * Overrideable function which is utilised in responding appropriately to hover events
     *
     * @param cell
     */
    public void isHovered(CellSprite cell) {
    }

    /**
     * Overrideable function which is utilised in responding appropriately to unhover events
     */
    public void isUnhovered() {
    }

}
