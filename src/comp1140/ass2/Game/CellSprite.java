package comp1140.ass2.Game;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;import java.lang.Override;import java.lang.System;

/**
 * Created by ***REMOVED*** on 31/08/15.
 */
public class CellSprite extends Rectangle{
    //private int cellDim;
    //private Colour colour;
 //   int xPos;
//    int yPos;

    Colour color;
    Color fill;
    Object parent = null; //is part of this pieceSprite


    //Necessary constructors, won't be used
    /*
    public CellSprite() {
        super();
    }
    public CellSprite (double width, double height) {super (width, height); }
    public CellSprite (double width, double height,Paint fill) {super (width, height, fill);}
    public CellSprite (double x, double y, double width, double height) {super (x, y, width, height);}
    */

    public CellSprite (double width, double height, Colour color, Object parent) {
        super(width, height);
        this.color = color;
        this.setFill(getFillFromPlayer(color));
        this.parent = parent;

        CellSprite dummyCell = this;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(parent instanceof PieceSprite) {
                    ((PieceSprite) parent).isClicked(dummyCell);
                }
                if(parent instanceof GridSprite) {
                    ((GridSprite) parent).isClicked(dummyCell);
                }
            }
        });
    }

    private Color getFillFromPlayer(Colour color) {
        if      (color == Colour.Blue)      return Color.BLUE;
        else if (color == Colour.Red)       return Color.RED;
        else if (color == Colour.Green)     return Color.GREEN;
        else if (color == Colour.Yellow)    return Color.GOLD;
        return Color.LIGHTGRAY;
    }

}
