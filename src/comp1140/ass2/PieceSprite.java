package comp1140.ass2;

import javafx.scene.Group;

/**
 * Created by ***REMOVED*** on 31/08/15.
 */
public class PieceSprite {
    private Colour colour;
    //private Shape shape;
    private CellSprite[] cells;
    private CellSprite[] backgroundCells;

    public PieceSprite(int cellDim, Piece piece) {
        this.colour = piece.colour;
        cells = new CellSprite[piece.shape.getCellNumber()];
        backgroundCells = new CellSprite[piece.shape.getCellNumber()];
        Coordinate cellCoords[] = piece.shape.getOccupiedCells();

        /**
         * I want a gap between cells, so I reduce the cell dimension by 10%. I also need to spread the coordinates (which in shape are only
         * 1 away from each other) such that they are 'cellDim' away form each other.
         * Home cell is black for debugging reasons
         */

        //ToDo: get the cells sized appropriately such that one orientation of a monimo occupies the identical pixels as any other orientation
        for (int i = 0; i < piece.shape.getCellNumber(); i++) {
            if (i == 0) {
                cells[i] = new CellSprite(cellDim - cellDim/ 5, null,
                        //ToDo: this method of spacing the cells implicitly inserts a pixel between cells. FIX THIS!!!
                        cellCoords[i].getX() + (cellCoords[i].getX() - cellCoords[0].getX()) * cellDim + cellDim/10,
                        cellCoords[i].getY() + (cellCoords[i].getY() - cellCoords[0].getY()) * cellDim + cellDim/10 );
                //My attempt to add a cell border. kinda works. Should probably have bordering happen in CellSprite
                //I've used null here to allow the inclusion of black colours.
                backgroundCells[i] = new CellSprite(cellDim, null,
                        cellCoords[i].getX() + (cellCoords[i].getX() - cellCoords[0].getX()) * cellDim,
                        cellCoords[i].getY() + (cellCoords[i].getY() - cellCoords[0].getY()) * cellDim);
            } else {
                cells[i] = new CellSprite(cellDim - cellDim / 5, colour,
                        cellCoords[i].getX() + (cellCoords[i].getX() - cellCoords[0].getX()) * cellDim + cellDim/10,
                        cellCoords[i].getY() + (cellCoords[i].getY() - cellCoords[0].getY()) * cellDim + cellDim/10);
                backgroundCells[i] = new CellSprite(cellDim, null,
                        cellCoords[i].getX() + (cellCoords[i].getX() - cellCoords[0].getX()) * cellDim,
                        cellCoords[i].getY() + (cellCoords[i].getY() - cellCoords[0].getY()) * cellDim);
            }
        }
    }

    public void AddShape(Group group) {
        for (CellSprite c : backgroundCells) {
            group.getChildren().add(c.getShape());
        }
        for (CellSprite c : cells) {
            group.getChildren().add(c.getShape());
        }
    }

    public void RemoveShape(Group group) {
        for (CellSprite c: cells) {
            group.getChildren().remove(c.getShape());
        }
        for (CellSprite c: backgroundCells) {
            group.getChildren().remove(c.getShape());
        }

    }
}
