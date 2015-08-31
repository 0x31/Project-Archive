package comp1140.ass2;

import javafx.scene.Group;

/**
 * Created by ***REMOVED*** on 31/08/15.
 */
public class PieceSprite {
    private Colour colour;
    //private Shape shape;
    private CellSprite[] cells;

    public PieceSprite(int cellDim, Piece piece) {
        this.colour = piece.colour;
        cells = new CellSprite[piece.shape.getCellNumber()];
        Coordinate cellCoords[] = piece.shape.getOccupiedCells();

        /**
         * I want a gap between cells, so I reduce the cell dimension by 10%. I also need to spread the coordinates (which in shape are only
         * 1 away from eachother) such that they are 'cellDim' away form each other.
         */
        for (int i = 0; i < piece.shape.getCellNumber(); i++) {
            if (i == 0) {
                cells[i] = new CellSprite(cellDim - cellDim / 10, Colour.Yellow, cellCoords[i].getX() + (cellCoords[i].getX() - cellCoords[0].getX()) * cellDim,
                        cellCoords[i].getY() + (cellCoords[i].getY() - cellCoords[0].getY()) * cellDim);
            } else {
                cells[i] = new CellSprite(cellDim - cellDim / 10, colour, cellCoords[i].getX() + (cellCoords[i].getX() - cellCoords[0].getX()) * cellDim,
                        cellCoords[i].getY() + (cellCoords[i].getY() - cellCoords[0].getY()) * cellDim);
            }
        }
    }

    public void AddShape(Group group) {
        for (CellSprite c : cells) {
            group.getChildren().add(c.getShape());
        }
    }
}
