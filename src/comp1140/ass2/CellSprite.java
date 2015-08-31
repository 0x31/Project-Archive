package comp1140.ass2;

import javafx.scene.effect.BlendMode;
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

        // needed to get the (shitty) bordering effect to work
        cell.setBlendMode(BlendMode.SRC_OVER);

        if (colour == null) {
            cell.setFill(Color.BLACK);
        } else {
            switch (colour) {
                case Blue:
                    cell.setFill(Color.DODGERBLUE);
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
    }
    public Rectangle getShape() {return cell;}
}
