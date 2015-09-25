package comp1140.ass2.Game;

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
    int ysize;
    int xsize;
    Color color;
    ArrayList<PieceSprite> pieceSprites = new ArrayList<>();

    CellState[][] grid;

    public GridSprite() {}

    public GridSprite(int col, int row, int width, int height, Color color) {

        this.col = col;
        this.row = row;
        this.xsize = Math.floorDiv(width-10, row)-1;
        this.ysize = Math.floorDiv(height-10, col)-1;
        this.color = color;
        grid = new CellState[col][width];

        this.setHgap(1);
        this.setVgap(1);
        for(int j=0;j<col;j++) {
            for (int i = 0; i < row; i++) {
                this.add(new Rectangle(xsize, ysize, color), i, j);
            }
        }

        for (PieceSprite pieceSprite : pieceSprites) {
            addToGridPane(pieceSprite);
        }
    }

    public void setCell(Coordinate coord, CellState state) {
        Color colori = Color.BLACK;
        switch (state) {
            case Blue: colori = Color.BLUE; break;
            case Yellow: colori = Color.YELLOW; break;
            case Red: colori = Color.RED; break;
            case Green: colori = Color.GREEN; break;
            case Empty: colori = color; break;
        }
        this.add(new Rectangle(xsize, ysize, colori), coord.getY(), coord.getX());
    }

    public void setCells(Coordinate[] coords, CellState state) {
        for( Coordinate coord: coords) {
            setCell(coord, state);
        }
    }
    
    private void addToGridPane(PieceSprite pieceSprite) {
        for (int i = 0; i<pieceSprite.CELL_COUNT; i++) {
            this.add(pieceSprite.cells[i], pieceSprite.coordinates[i].getX(), pieceSprite.coordinates[i].getY());
        }
    }

    private void removeFromGridPane(PieceSprite pieceSprite) {
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
}
