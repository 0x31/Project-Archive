package comp1140.ass2;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by ***REMOVED*** on 31/08/15.
 */
public class CellSprite {
    private int cellDim;
    private Colour colour;
 //   int xPos;
//    int yPos;
    Rectangle cell;

    CellSprite(int cellDim, Colour colour, int xPos, int yPos) {
        this.cellDim = cellDim;
        this.colour  = colour;
//        this.xPos = xPos;
//        this.yPos = yPos;
        cell = new Rectangle(xPos, yPos, cellDim, cellDim);
        cell.setFill(Color.RED);
        switch (colour) {
            case Blue:
                cell.setFill(Color.BLUE);
                break;
            case Yellow:
                cell.setFill(Color.YELLOW);
                break;
            case Red:
                cell.setFill(Color.RED);
                break;
            case Green:
                cell.setFill(Color.GREEN);
                break;
        }
    }
    public Rectangle getShape() {return cell;}
}
