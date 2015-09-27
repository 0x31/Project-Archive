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

    private void addToGridPane(PieceSprite pieceSprite) {
        for (int i = 0; i<pieceSprite.CELL_COUNT; i++) {
            this.add(pieceSprite.cells[i], pieceSprite.coordinates[i].getX(), pieceSprite.coordinates[i].getY());
        }
    }

    private void removeFromGridPane(PieceSprite pieceSprite) {
        if(pieceSprite==null) return;
        for (int i = 0; i<pieceSprite.CELL_COUNT; i++) {
            this.getChildren().remove(pieceSprite.cells[i]);
        }
    }
    
    public void addPieceSprite(PieceSprite pieceSprite) {
        pieceSprites.add(pieceSprite);
        addToGridPane(pieceSprite);
    }

    public void removePieceSprite(PieceSprite pieceSprite) {
        pieceSprites.remove(pieceSprite);
        removeFromGridPane(pieceSprite);
    }

    public void isClicked(PieceSprite pieceSprite) {
        System.out.println("GridSprite was clicked in - please override this function and tell me what to do!");
    }

    public void isClicked(CellSprite cellSprite) {
        // Do nothing;
    }

    public void isHovered(CellSprite cell) {
    }
    public void isUnhovered() {
    }

}
