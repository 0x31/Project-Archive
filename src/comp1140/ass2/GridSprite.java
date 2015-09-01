package comp1140.ass2;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by nosha on 1/09/15.
 */
public class GridSprite {
    int col;
    int row;
    int ysize;
    int xsize;
    Color color;
    CellState[][] grid;

    public GridSprite(int col, int row, int width, int height, Color color) {
        this.col = col;
        this.row = row;
        this.xsize = Math.floorDiv(width-10, row)-1;
        this.ysize = Math.floorDiv(height-10, col)-1;
        this.color = color;
        Board board = new Board("");
        grid = board.getGrid();
    }

    public GridSprite(int col, int row, int width, int height, Color color, String game) {
        this.col = col;
        this.row = row;
        this.xsize = Math.floorDiv(width-10, row)-1;
        this.ysize = Math.floorDiv(height-10, col)-1;
        this.color = color;
        Board board = new Board(game);
        grid = board.getGrid();
    }

    public GridPane toFX() {
        GridPane playerGrid = new GridPane();
        playerGrid.setHgap(1);
        playerGrid.setVgap(1);
        for(int j=0;j<col;j++) {
            for (int i = 0; i < row; i++) {
                Color colori =Color.BLACK;
                switch (grid[j][i]) {
                    case Blue: colori = Color.BLUE; break;
                    case Yellow: colori = Color.YELLOW; break;
                    case Red: colori = Color.RED; break;
                    case Green: colori = Color.GREEN; break;
                    case Empty: colori = color; break;
                }
                playerGrid.add(new Rectangle(xsize, ysize, colori), i, j);
            }
        }
        //root.getChildren().addAll(playerGrid);
        return playerGrid;

    }
}
